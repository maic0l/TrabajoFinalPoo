package co.edu.uniminuto.dao;

import co.edu.uniminuto.entidades.Bicicleta;
import co.edu.uniminuto.entidades.Carro;
import co.edu.uniminuto.entidades.Moto;
import co.edu.uniminuto.entidades.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TicketDao {
    
    // Formato para manejar fechas y horas
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // Instancia de la clase de conexión a la base de datos
    Conexion conexion;
    
    // Declaración de PreparedStatement para manejar consultas preparadas
    PreparedStatement statement;
    
    // Constructor de la clase TicketDao
    public TicketDao() {
        conexion = new Conexion(); // Inicialización de la conexión a la base de datos
        this.statement = null; // Inicialización del PreparedStatement
    }
    
    //CREATE
    
    // Método para registrar un ticket en la base de datos
 public Ticket registrarTicket(Ticket ticket) {
    // Query SQL para insertar un nuevo ticket
    String query = "INSERT INTO ticket VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    Connection con = conexion.getconectar(); // Obtención de la conexión
    ResultSet resultSet = null;

    try {
        this.statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        // Preparación del PreparedStatement con el query SQL
        // Establecimiento de los parámetros del ticket en el PreparedStatement
        this.statement.setInt(1, ticket.getUsuario().getIdUsuario());
        this.statement.setString(2, ticket.getUsuario().getNombre());
        this.statement.setString(3, ticket.getUsuario().getApellido());
        this.statement.setString(4, ticket.getVehiculo().getTipo());
        this.statement.setString(5, ticket.getVehiculo().getMarca());
        this.statement.setString(6, ticket.getVehiculo().getModelo());

        // Determinación del tipo de vehículo y asignación de la placa correspondiente
        if (ticket.getVehiculo() instanceof Carro) {
            this.statement.setString(7, ((Carro) ticket.getVehiculo()).getPlaca());
        } else if (ticket.getVehiculo() instanceof Moto) {
            this.statement.setString(7, ((Moto) ticket.getVehiculo()).getPlaca());
        } else {
            this.statement.setString(7, "No aplica placa"); // Caso bicicleta
        }
        this.statement.setString(8, ticket.getEntradaDeVehiculo().format(formatter));
        this.statement.setString(9, ticket.getSalidaDeVehiculo().format(formatter));
        this.statement.setDouble(10, ticket.getPago());
        this.statement.setInt(11, ticket.getNumeroDeParqueadero());
        this.statement.setInt(12, 1); // Status inicial del ticket (activo)

        // Ejecución del insert en la base de datos
        int respuesta = this.statement.executeUpdate();

        //número de ticket generado
        resultSet = this.statement.getGeneratedKeys();
        int numeroTicket = -1; 

        if (resultSet.next()) {
            numeroTicket = resultSet.getInt(1); //número de ticket generado
        }

        if (respuesta > 0) {
            JOptionPane.showMessageDialog(null, "Datos subidos con éxito. Número de ticket: " + numeroTicket, "Información", JOptionPane.INFORMATION_MESSAGE);
        }

        } catch (SQLException e) {
            System.out.println("Error al registrar el ticket: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (this.statement != null) {
                    this.statement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
        return ticket; // Retorno del objeto ticket registrado
    }
    
    //READ
    
    // Método para consultar un ticket por su número de ticket
    public void consultarTicket(int numeroDeTicket){
        
        Connection con = conexion.getconectar(); // ovbtención de la conexión
        String query = "SELECT * FROM ticket WHERE tick_id = ?"; // Query SQL para la consulta
        
        ResultSet resultSet = null; // Resultado de la consulta
        
        try {
            // Preparación del PreparedStatement con el query SQL de consulta
            this.statement = con.prepareStatement(query);
            this.statement.setInt(1, numeroDeTicket); // Establecimiento del parámetro de número de ticket

            // Ejecución de la consulta y obtención del resultado
            resultSet = statement.executeQuery();
            
            // Procesamiento del resultado si se encontró un ticket
            if (resultSet.next()) {                
                // Mostrar la información del ticket consultado en un mensaje
                JOptionPane.showMessageDialog(null, "ID: " + resultSet.getInt("tick_id") + "\n" +"ID Usuario: " + resultSet.getInt("tick_usuario_id") + "\n" +
                                   "Nombre: " + resultSet.getString("tick_usuario_nombre") +"\n" +"Apellido: " + resultSet.getString("tick_usuario_apellido") +"\n" +
                                    "Tipo: " + resultSet.getString("tick_vehiculo_tipo") + "\n" + "Marca: " + resultSet.getString("tick_vehiculo_marca") +"\n" +
                                    "Modelo: " + resultSet.getString("tick_vehiculo_modelo") + "\n" + "Placa: " + resultSet.getString("tick_vehiculo_placa")+"\n" +
                                    "Entrada: " + resultSet.getString("tick_vehiculo_entrada") + "\n" + "Salida: " + resultSet.getString("tick_vehiculo_salida")+"\n" +
                                    "Pago total: " + resultSet.getString("tick_pago") + "\n" + "Número de parqueadero: " + resultSet.getString("tick_num_parqueadero")+"\n" +
                                    "Status: " + resultSet.getString("tick_status"),
                                    "Consulta Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {                
                JOptionPane.showMessageDialog(null,"No se encontró el número de ticket", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Error al pedir información: " + e.getMessage());
        } finally {
            // Cierre de recursos
            try {
                if (resultSet != null) resultSet.close();
                if (this.statement != null) this.statement.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
         
    // Método para consultar si un parqueadero específico está ocupado para un tipo de vehículo dado
    public boolean consultarParqueadero(int numeroDeParqueadero, String tipoDeVehiculo){
        Connection con = conexion.getconectar(); // Obtención de la conexión
        String query = "SELECT * FROM ticket WHERE tick_num_parqueadero = ? AND tick_vehiculo_tipo = ? AND tick_status = 1"; // Query SQL para la consulta
        
        try {
            // Preparación del PreparedStatement con el query SQL de consulta
            this.statement = con.prepareStatement(query);
            this.statement.setInt(1, numeroDeParqueadero); // Establecimiento del parámetro de número de parqueadero
            this.statement.setString(2, tipoDeVehiculo); // Establecimiento del parámetro de tipo de vehículo
            
            // Ejecución de la consulta y obtención del resultado
            ResultSet resultSet = this.statement.executeQuery();
            
            // Si se encuentra algún ticket para el parqueadero y tipo de vehículo especificados, mostrar un mensaje
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "El parqueadero está ocupado", "Información", JOptionPane.INFORMATION_MESSAGE);
                return true; // Retorna true si el parqueadero está ocupado
            } else {
                System.out.println("Parqueadero seleccionado correctamente");
                return false; // Retorna false si el parqueadero está disponible
            }

        } catch (SQLException e) {
            System.out.println("Error al pedir información: " + e.getMessage());
        } finally {
            // Cierre seguro de recursos
            if (con != null) {
                try {
                    this.statement.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true; // Retorna true si hubo algún error en la consulta
    }
    //UPSATE
    public void actualizarFechaSalida(LocalDateTime fechaGeneradaUsuario, String nombre, int idUsuario, int ticketNum) {
        ResultSet resultSet = null; // Resultado de la consulta
        double pago = 0; // Variable para almacenar el pago

        Connection con = null;
        // Instancias de las clases de vehículos y ticket
        Carro carro = new Carro();
        Moto moto = new Moto();
        Bicicleta bicicleta = new Bicicleta();
        Ticket ticket = new Ticket();

        try {
            con = conexion.getconectar(); // Obtención de la conexión a la base de datos

            // Query SQL para seleccionar el ticket a actualizar
            String selectQuery = "SELECT tick_vehiculo_entrada, tick_vehiculo_tipo FROM ticket WHERE tick_usuario_nombre = ? AND tick_usuario_id = ? AND tick_id = ? AND tick_status = 1";
            this.statement = con.prepareStatement(selectQuery);
            this.statement.setString(1, nombre); // Establecimiento del parámetro de nombre de usuario
            this.statement.setInt(2, idUsuario); // Establecimiento del parámetro de ID de usuario
            this.statement.setInt(3, ticketNum); // Establecimiento del parámetro de número de ticket

            // Ejecución de la consulta
            resultSet = this.statement.executeQuery();

            // Si se encontró el ticket, procesar la información
            if (resultSet.next()) {
                String entradaDeVehiculoStr = resultSet.getString("tick_vehiculo_entrada");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime entradaDeVehiculo = LocalDateTime.parse(entradaDeVehiculoStr, formatter);
                String tipoVehiculo = resultSet.getString("tick_vehiculo_tipo");

                // Según el tipo de vehículo, calcular el pago correspondiente
                switch (tipoVehiculo) {
                    case "Carro":
                        pago = carro.calcularPago(ticket.calcularMinutos(entradaDeVehiculo, fechaGeneradaUsuario));
                        break;
                    case "Moto":
                        pago = moto.calcularPago(ticket.calcularMinutos(entradaDeVehiculo, fechaGeneradaUsuario));
                        break;
                    case "Bicicleta":
                        pago = bicicleta.calcularPago(ticket.calcularMinutos(entradaDeVehiculo, fechaGeneradaUsuario));
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron tickets para actualizar", "Información", JOptionPane.INFORMATION_MESSAGE);
                return; // Salir del método si no se encontró el ticket
            }

            // Query SQL para actualizar el status y el pago del ticket
            String updateQuery = "UPDATE ticket SET tick_vehiculo_salida = ?, tick_pago = ? WHERE tick_usuario_nombre = ? AND tick_usuario_id = ? AND tick_id = ?";
            this.statement = con.prepareStatement(updateQuery);
            this.statement.setString(1, fechaGeneradaUsuario.format(formatter)); // Establecimiento del parámetro de fecha de salida
            this.statement.setDouble(2, pago); // Establecimiento del parámetro de pago
            this.statement.setString(3, nombre); // Establecimiento del parámetro de nombre de usuario
            this.statement.setInt(4, idUsuario); // Establecimiento del parámetro de ID de usuario
            this.statement.setInt(5, ticketNum); // Establecimiento del parámetro de número de ticket

            // Ejecución de la actualización
            int respuesta = this.statement.executeUpdate();

            // Mensaje de éxito si se actualizó correctamente el ticket
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Fecha actualizada con Exito!!: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron tickets para actualizar", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Error al pedir información: " + e.getMessage());
        } finally {
            // Cierre seguro de recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (this.statement != null) {
                    this.statement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error con la conexión: " + e.getMessage());
            }
        }
    }
    
    public void actualizarDato(String dato, String actualizacion, int ticketNum){
        Connection con = conexion.getconectar(); 
        String query = "UPDATE ticket SET " + dato + " = ? WHERE tick_id = ?" ;
        
        try {
            // Preparación del PreparedStatement con el query SQL de actualización
            this.statement = con.prepareStatement(query);
            this.statement.setString(1, actualizacion); 
            this.statement.setInt(2, ticketNum);
            // Establecimiento del nuevo valor para la columna especificada
            if (actualizacion instanceof String) {
                this.statement.setString(1, (String) actualizacion);
            }      
            // Ejecución del update en la base de datos
            int respuesta = this.statement.executeUpdate();

            // Mensaje de éxito si se actualizó correctamente
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Columna " + dato + " Actualizada con Éxito: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar la columna: " + e.getMessage());
        } finally {
            // Cierre seguro de recursos
            try {
                if (this.statement != null) {
                    this.statement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
    
    public void actualizarDatoId(String dato, int idusuario, int ticketNum){
        Connection con = conexion.getconectar(); 
        String query = "UPDATE ticket SET " + dato + " = ? WHERE tick_id = ?" ;
        
        try {
            // Preparación del PreparedStatement con el query SQL de actualización
            this.statement = con.prepareStatement(query);
            this.statement.setInt(1, idusuario); 
            this.statement.setInt(2, ticketNum);
               
            // Ejecución del update en la base de datos
            int respuesta = this.statement.executeUpdate();

            // Mensaje de éxito si se actualizó correctamente
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Columna " + dato + " Actualizada con Éxito: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar la columna: " + e.getMessage());
        } finally {
            // Cierre seguro de recursos
            try {
                if (this.statement != null) {
                    this.statement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
    //DELETE
    public void actualizarStatusAutomaticamente(LocalDateTime fechaTranscurrida){
        Connection con = conexion.getconectar();
        String query = "UPDATE ticket SET tick_status = 0 WHERE tick_vehiculo_salida < ? AND tick_status = 1";
        try {
            this.statement = con.prepareStatement(query);
            this.statement.setString(1, fechaTranscurrida.format(formatter));

            int respuesta = statement.executeUpdate();
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Tickets actualizados con éxito: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("No se han encontrado tickets por actualizar");
            }
        }catch (SQLException e) {
                System.out.println("Error al actualizar el status de los tickets: " + e.getMessage());

        }
    }
    // Método para borrar el status de un ticket específico dado la fecha generada por el usuario, nombre, ID del usuario y número de ticket
    public void borrarStatus(LocalDateTime fechaGeneradaUsuario, String nombre, int idUsuario, int ticketNum) {
        Connection con = null; // inicialización de la conexion
        ResultSet resultSet = null; // Resultado de la consulta

        double pago = 0; // Variable para almacenar el pago
        String entradaDeVehiculoStr;
        String tipoVehiculo;
        // instancias de las clases de vehículos y ticket
        Carro carro = new Carro();
        Moto moto = new Moto();
        Bicicleta bicicleta = new Bicicleta();
        Ticket ticket = new Ticket();
    
        try {
            con = conexion.getconectar(); // Obtención de la conexión a la base de datos

            // Query SQL para seleccionar el ticket a actualizar
            String query = "SELECT tick_vehiculo_entrada, tick_vehiculo_tipo FROM ticket WHERE tick_usuario_nombre = ? AND tick_usuario_id = ? AND tick_id = ? AND tick_status = 1";
            this.statement = con.prepareStatement(query);
            this.statement.setString(1, nombre); // Establecimiento del parámetro de nombre de usuario
            this.statement.setInt(2, idUsuario); // Establecimiento del parámetro de ID de usuario
            this.statement.setInt(3, ticketNum); // Establecimiento del parámetro de número de ticket

            // Ejecución de la consulta
            resultSet = this.statement.executeQuery();

            // Si se encontró el ticket, procesar la información
            if (resultSet.next()) {
                entradaDeVehiculoStr = resultSet.getString("tick_vehiculo_entrada");
                LocalDateTime entradaDeVehiculo = LocalDateTime.parse(entradaDeVehiculoStr, formatter);
                tipoVehiculo = resultSet.getString("tick_vehiculo_tipo");

                // Según el tipo de vehículo, calcular el pago correspondiente
                switch (tipoVehiculo) {
                    case "Carro" -> pago = carro.calcularPago(ticket.calcularMinutos(entradaDeVehiculo, fechaGeneradaUsuario));
                    case "Moto" -> pago = moto.calcularPago(ticket.calcularMinutos(entradaDeVehiculo, fechaGeneradaUsuario));
                    case "Bicicleta" -> pago = bicicleta.calcularPago(ticket.calcularMinutos(entradaDeVehiculo, fechaGeneradaUsuario));
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron tickets para actualizar", "Información", JOptionPane.INFORMATION_MESSAGE);
                return; // Salir del método si no se encontró el ticket
            }

            // Query SQL para actualizar el status y el pago del ticket
            String Uquery = "UPDATE ticket SET tick_status = 0, tick_vehiculo_salida = ?, tick_pago = ? WHERE tick_usuario_nombre = ? AND tick_usuario_id = ? AND tick_id = ?";
            this.statement = con.prepareStatement(Uquery);
            this.statement.setString(1, fechaGeneradaUsuario.format(formatter)); // Establecimiento del parámetro de fecha de salida
            this.statement.setDouble(2, pago); // Establecimiento del parámetro de pago
            this.statement.setString(3, nombre); // Establecimiento del parámetro de nombre de usuario
            this.statement.setInt(4, idUsuario); // Establecimiento del parámetro de ID de usuario
            this.statement.setInt(5, ticketNum); // Establecimiento del parámetro de número de ticket

            // Ejecución de la actualización
            int respuesta = this.statement.executeUpdate();

            // Mensaje de éxito si se actualizó correctamente el ticket
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Dato Borrado con Éxito!!! " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron tickets para actualizar", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Error al pedir información: " + e.getMessage());
        } finally {
            // Cierre seguro
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (this.statement != null) {
                    this.statement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    public void borrarTicket(String nombre, int idUsuario, int numeroDeTicket) {
    Connection con = null; // Inicialización de la conexión

    try {
        con = conexion.getconectar(); // Obtención de la conexión a la base de datos

        // Query SQL para el borrado
        String query = "DELETE FROM ticket WHERE tick_usuario_nombre = ? AND tick_usuario_id = ? AND tick_id = ?";
        
        // Preparación del PreparedStatement con el query SQL de borrado
        this.statement = con.prepareStatement(query);
        this.statement.setString(1, nombre); // Establecimiento del parámetro de nombre de usuario
        this.statement.setInt(2, idUsuario); // Establecimiento del parámetro de ID de usuario
        this.statement.setInt(3, numeroDeTicket); // Establecimiento del parámetro de número de ticket

        // Ejecución del delete en la base de datos
        int respuesta = this.statement.executeUpdate();

        // Mensaje de éxito si se borró el ticket correctamente
        if (respuesta > 0) {
            JOptionPane.showMessageDialog(null, "Dato Borrado con Éxito: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron tickets para borrar", "Información", JOptionPane.INFORMATION_MESSAGE);
        }

        } catch (SQLException e) {
            System.out.println("Error al pedir información: " + e.getMessage());
        } finally {
            try {
                if (this.statement != null) {
                    this.statement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la sesión: " + ex.getMessage());
            }
        }
    }
    
}

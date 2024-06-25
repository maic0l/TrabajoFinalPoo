
package co.edu.uniminuto.dao;

import co.edu.uniminuto.entidades.Bicicleta;
import co.edu.uniminuto.entidades.Carro;
import co.edu.uniminuto.entidades.Moto;
import co.edu.uniminuto.entidades.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class TicketDao {
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");   
    Conexion conexion;
    PreparedStatement statement;

    public TicketDao() {
        conexion = new Conexion();
        this.statement = null;
    }
    
    public Ticket registrarTicket(Ticket ticket) {
        String query = "INSERT INTO ticket VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection con = conexion.getconectar();
        
        try {
            this.statement = con.prepareStatement(query);
            this.statement.setInt(1, ticket.getUsuario().getIdUsuario());
            this.statement.setString(2, ticket.getUsuario().getNombre());
            this.statement.setString(3, ticket.getUsuario().getApellido());
            this.statement.setString(4, ticket.getVehiculo().getTipo());
            this.statement.setString(5, ticket.getVehiculo().getMarca());
            this.statement.setString(6, ticket.getVehiculo().getModelo());
                     
            if (ticket.getVehiculo() instanceof Carro) {
                this.statement.setString(7, ((Carro) ticket.getVehiculo()).getPlaca());
            } else if (ticket.getVehiculo() instanceof Moto) {
                this.statement.setString(7, ((Moto) ticket.getVehiculo()).getPlaca());
            } else {
                this.statement.setString(7, "No aplica placa");  
            }
            
            this.statement.setString(8, ticket.getEntradaDeVehiculo().format(formatter));
            this.statement.setString(9, ticket.getSalidaDeVehiculo().format(formatter));
            this.statement.setDouble(10, ticket.getPago());
            this.statement.setInt(11, ticket.getNumeroDeParqueadero());
            this.statement.setInt(12, 1);
            
            int respuesta = this.statement.executeUpdate();
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Datos subidos con éxito: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar el ticket: " + e.getMessage());
        } finally {
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
        return ticket;
    }
   
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
    public void consultarTicket(int numeroDeTicket){
        Connection con = conexion.getconectar();
        String query = "SELECT * FROM ticket WHERE tick_id = ?";
        ResultSet resultSet;
        try {
            this.statement = con.prepareStatement(query);
            this.statement.setInt(1, numeroDeTicket);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt("tick_id");
                int id_usuario = resultSet.getInt("tick_id_usuario");
                String nombreUsu = resultSet.getString("tick_usuario_nombre");  
                String vehiculoSalida = resultSet.getString("tick_vehiculo_salida");                
                String placa = resultSet.getString("tick_vehiculo_placa");    
                int status = resultSet.getInt("tick_status");

                JOptionPane.showMessageDialog(null, "ID: " + id + "\n" +
                                   "ID Usuario: " + id_usuario + "\n" +
                                   "Nombre Usuario: " + nombreUsu + "\n" +
                                   "Vehículo Salida: " + vehiculoSalida + "\n" +
                                   "Placa: " + placa + "\n" +
                                   "Status: " + status, "Consulta Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,"No se encontró el número de ticket", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Error al pedir información: " + e.getMessage());
        }
    }
    
    public void borrarTicket(String nombre, int idUsuario, int numeroDeTicket){
        Connection con = conexion.getconectar();
        String query = "DELETE FROM ticket WHERE tick_usuario_nombre = ? AND tick_id_usuario = ? AND tick_id =?";
        try {
            this.statement = con.prepareStatement(query);
            this.statement.setString(1, nombre);
            this.statement.setInt(2, idUsuario);
            this.statement.setInt(3, numeroDeTicket);
            int respuesta = this.statement.executeUpdate();
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Dato Borrado con Exito!!: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
           
        } catch (SQLException e) {
            System.out.println("Error al pedir información: " + e.getMessage());
        }
    }
    
    public void consultarParqueadero(String nombre, int idUsuario, int numeroDeTicket){
        Connection con = conexion.getconectar();
        String query = "DELETE FROM ticket WHERE tick_usuario_nombre = ? AND tick_id_usuario = ? AND tick_id =?";
        try {
            this.statement = con.prepareStatement(query);
            this.statement.setString(1, nombre);
            this.statement.setInt(2, idUsuario);
            this.statement.setInt(3, numeroDeTicket);
            int respuesta = this.statement.executeUpdate();
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Dato Borrado con Exito!!: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
            }
           
        } catch (SQLException e) {
            System.out.println("Error al pedir información: " + e.getMessage());
        }
    }
 
public void borrarStatus(LocalDateTime fechaGeneradaUsuario, String nombre, int idUsuario, int ticketNum) {
    Connection con = null;
    PreparedStatement selectStatement = null;
    PreparedStatement updateStatement = null;
    ResultSet resultSet = null;
    double pago = 0;

    Carro carro = new Carro();
    Moto moto = new Moto();
    Bicicleta bicicleta = new Bicicleta();
    Ticket ticket = new Ticket();

    try {
        con = conexion.getconectar();

        String selectQuery = "SELECT tick_vehiculo_entrada, tick_vehiculo_tipo FROM ticket WHERE tick_usuario_nombre = ? AND tick_id_usuario = ? AND tick_id = ? AND tick_status = 1";
        selectStatement = con.prepareStatement(selectQuery);
        selectStatement.setString(1, nombre);
        selectStatement.setInt(2, idUsuario);
        selectStatement.setInt(3, ticketNum);

        resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
            String entradaDeVehiculoStr = resultSet.getString("tick_vehiculo_entrada");
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime entradaDeVehiculo = LocalDateTime.parse(entradaDeVehiculoStr, formatter);
            String tipoVehiculo = resultSet.getString("tick_vehiculo_tipo");

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
            return;
        }

        String updateQuery = "UPDATE ticket SET tick_status = 0, tick_vehiculo_salida = ?, tick_pago = ? WHERE tick_usuario_nombre = ? AND tick_id_usuario = ? AND tick_id = ?";
        updateStatement = con.prepareStatement(updateQuery);
        updateStatement.setString(1, fechaGeneradaUsuario.format(formatter));
        updateStatement.setDouble(2, pago);
        updateStatement.setString(3, nombre);
        updateStatement.setInt(4, idUsuario);
        updateStatement.setInt(5, ticketNum);

        int respuesta = updateStatement.executeUpdate();

        if (respuesta > 0) {
            JOptionPane.showMessageDialog(null, "Dato Borrado con Éxito: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron tickets para actualizar", "Información", JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException e) {
        System.out.println("Error al pedir información: " + e.getMessage());
    } finally {
        // Cerrar recursos en el bloque finally para asegurar que se liberen
        try {
            if (resultSet != null) resultSet.close();
            if (selectStatement != null) selectStatement.close();
            if (updateStatement != null) updateStatement.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

}
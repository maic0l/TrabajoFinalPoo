/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package co.edu.uniminuto.parqueaderosbd;
import co.edu.uniminuto.dao.TicketDao;
import co.edu.uniminuto.dao.UsuarioDao;
import co.edu.uniminuto.entidades.Bicicleta;
import co.edu.uniminuto.entidades.Carro;
import co.edu.uniminuto.entidades.Moto;
import co.edu.uniminuto.entidades.Ticket;
import co.edu.uniminuto.entidades.Usuario;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;


public class Parqueadero{
   
    public static void main(String[] args) {
        
        //variable que ayuda a controlar las diferentes opciones:
        short opcion = -1;


        //Las variables usadas para la generacion del objeto usuario
        int idUsuario;
        String nombre;
        String apellido;

        //Las variables usadas para la generacion del objeto Vehiculo       
        String tipoVehiculo;  //moto, carro, bicicleta
        
        Carro carro; //clase hija de vehiculo
        Moto moto; //clase hija de vehiculo
        Bicicleta bicicleta; //clase hija de vehiculo
        //variables que se repiten en moto, carro y bicicleta
        String marca;
        String modelo;
        //variable que solo se repite en moto, y carro
        String placa;
        
        //Ticket      
        LocalDateTime entradaDeVehiculo; //la entrada del registro del vehiculo
        LocalDateTime fechaGeneradaUsuario; //fecha de salida dada por el usuario     
        int parqueadero;//variable sobre la seleccion del parqueadero
        Usuario usuario; //clase usuario
        double pago; //calculo de pago en minutos, entra la fehca de entrada y salida
        
        //consultar
        int ticketNum; //número de ticket
        Ticket ticket; //clase ticket
        ticket = new Ticket();
        //Dao
        //Subidas y actualizaciones de ticket en la base de datos
        UsuarioDao usuarioDao;
        TicketDao ticketDao;
        //inicializacion de los archivos Dao
        usuarioDao = new UsuarioDao();
        ticketDao = new TicketDao();
        
        //variable de fecha actual
        LocalDateTime fechaActual;    
        //actualizar datos:
        String datoPorActualizar;
        String actualizacion = ""; 
        String columnaSeleccionada = "";
        do {
            try{
                fechaActual = LocalDateTime.now().withNano(0); //fecha actual
                ticketDao.actualizarStatusAutomaticamente(fechaActual); //actualizacion automatica de status
                do {//inicio de opciones
                    opcion = Short.parseShort(JOptionPane.showInputDialog("(C)1.Crear un nuevo ticket\n(R)2.Consultar tickets\n(U)3.Actualizar ticket\n(D)4.Borrar ticket (logicamente)\n(--)0.Salir\nElija su opción: ")); 
                    if (opcion > 5 || opcion < 0) {//información sobre las opciones
                        JOptionPane.showMessageDialog(null, "Lo sentimos pero esa opción no es válida", "Aviso", JOptionPane.WARNING_MESSAGE); 
                    }
                } while (opcion > 5 || opcion < 0);//rango de opciones permito
                //las diferentes opciones:
               
                switch (opcion) {
                    case 1:
                        //validacion de datos para la creacion de la clse usuario
                            idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Documento del cliente:"));
                            nombre = JOptionPane.showInputDialog("Nombre del cliente:");
                            apellido = JOptionPane.showInputDialog("Apellido del cliente:");
                            usuario = new Usuario(idUsuario, nombre, apellido);
                            //usuarioDao.registrarUsuario(usuario); //Subida de datos 
                            
                            do {//Validacion de vehiculo, donde solo se deja ingresar como string Moto, Carro, Bicicleta
                                tipoVehiculo = JOptionPane.showInputDialog("Ingrese el tipo de vehiculo (Moto, Carro, Bicicleta):");
                                if (!tipoVehiculo.equals("Carro") && !tipoVehiculo.equals("Moto") && !tipoVehiculo.equals("Bicicleta")) {
                                    JOptionPane.showMessageDialog(null, "Lo sentimos pero esa opción no es válida, (la primera letra debe ser mayuscula)", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                }
                            } while (!tipoVehiculo.equals("Carro") && !tipoVehiculo.equals("Moto") && !tipoVehiculo.equals("Bicicleta"));
                            //continuará ejecutándose mientras el tipo de vehículo no sea "Carro", "Moto" ni "Bicicleta".
                            //validacion de datos para la creacion de la clse vehiculo
                            marca = JOptionPane.showInputDialog("Ingrese la marca del vehiculo:");
                            modelo = JOptionPane.showInputDialog("Ingrese el modelo del vehiculo:");
                            //confirmacion de parqueadero, esta necesita dos parametros; el tipo y el número del parqueadero
                            do {
                                parqueadero = Integer.parseInt(JOptionPane.showInputDialog("Número del parqueadero (1-10):"));                                       
                            } while (ticketDao.consultarParqueadero(parqueadero, tipoVehiculo) || parqueadero < 0 || parqueadero > 10);/*se consulta en la base de datos si hay algun ticket con status activo(1) y si, si hay alguno,
                            regresa true, lo cual se repetira hasta que ingrese un numero donde no este activo(1)*/

                            //metodo de generacion de salida dada por el usuario
                            fechaGeneradaUsuario = ticket.generarVariableDeTiempo(fechaActual);
                            entradaDeVehiculo = LocalDateTime.now().withNano(0);//entrada del vehiculo
                            //verificacion de tipo de vehiculo
                            switch (tipoVehiculo) {
                                case "Carro":
                                    placa = JOptionPane.showInputDialog("Ingrese la placa del vehiculo:");
                                    carro = new Carro(tipoVehiculo, marca, modelo, placa);
                                    pago = carro.calcularPago(ticket.calcularMinutos(entradaDeVehiculo, fechaGeneradaUsuario));
                                    ticket = new Ticket(entradaDeVehiculo, fechaGeneradaUsuario, usuario, carro, pago, parqueadero);
                                    break;
                                case "Moto":
                                    placa = JOptionPane.showInputDialog("Ingrese la placa del vehiculo:");
                                    moto = new Moto(tipoVehiculo, marca, modelo, placa); 
                                    pago = moto.calcularPago(ticket.calcularMinutos(entradaDeVehiculo, fechaGeneradaUsuario));
                                    ticket = new Ticket(entradaDeVehiculo, fechaGeneradaUsuario, usuario, moto, pago, parqueadero);
                                    break;
                                case "Bicicleta":
                                    bicicleta = new Bicicleta(tipoVehiculo, marca, modelo); 
                                    pago = bicicleta.calcularPago(ticket.calcularMinutos(entradaDeVehiculo, fechaGeneradaUsuario));
                                    ticket = new Ticket(entradaDeVehiculo, fechaGeneradaUsuario, usuario, bicicleta, pago, parqueadero);
                                    break;
                            }//bicicleta no necesita la variable placa
                            System.out.println(ticket.toString());//impresion de ticket por consula
                            //Crear ticket
                            ticketDao.registrarTicket(ticket);//Ssubida de datos
                            break;   
                        case 2://consultar tickets                                             
                            ticketNum = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del ticket:"));
                            ticketDao.consultarTicket(ticketNum);
                            break;
                           
                        case 3:     
                            ticketNum = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del ticket:"));
                            
                            do{
                                do{                    
                                datoPorActualizar = JOptionPane.showInputDialog("Ingrese el dato que va actulizar en el ticket (usuario o vehiculo):");
                                }while(!datoPorActualizar.equals("usuario") && !datoPorActualizar.equals("vehiculo"));

                                switch (datoPorActualizar) {
                                    case "usuario":
                                        do{                                       
                                            columnaSeleccionada = JOptionPane.showInputDialog("Ingrese el dato que va actulizar referente al usuario (nombre, apellido o id) :");
                                        }while(!columnaSeleccionada.equals("nombre") && !columnaSeleccionada.equals("apellido") && !columnaSeleccionada.equals("id"));
                                        
                                        if("id".equals(columnaSeleccionada)){
                                            columnaSeleccionada = "tick_" + datoPorActualizar +"_" + columnaSeleccionada;
                                            idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la actualización:"));
                                            ticketDao.actualizarDatoId(columnaSeleccionada, idUsuario, ticketNum);
                                        }else{
                                            columnaSeleccionada = "tick_" + datoPorActualizar +"_" + columnaSeleccionada;
                                            actualizacion = JOptionPane.showInputDialog("Ingrese la actualización:");  
                                            ticketDao.actualizarDato(columnaSeleccionada, actualizacion, ticketNum);
                                        }                                                 
                                        break;
                                    case "vehiculo":
                                        do{
                                            columnaSeleccionada = JOptionPane.showInputDialog("Ingrese el dato que va actulizar referente al vehiculo (marca, modelo o fecha de salida):");
                                        }while(!columnaSeleccionada.equals("marca") && !columnaSeleccionada.equals("modelo") && !columnaSeleccionada.equals("fecha de salida"));
                                        if("fecha de salida".equals(columnaSeleccionada)){
                                            fechaGeneradaUsuario = ticket.generarVariableDeTiempo(fechaActual);
                                            idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Documento del cliente:"));
                                            nombre = JOptionPane.showInputDialog("Nombre del cliente:");
                                            ticketDao.actualizarFechaSalida(fechaGeneradaUsuario, nombre, idUsuario, ticketNum);
                                            
                                        }else{
                                            actualizacion = JOptionPane.showInputDialog("Ingrese la actualización:");
                                            columnaSeleccionada = "tick_" + datoPorActualizar +"_" + columnaSeleccionada;
                                            ticketDao.actualizarDato(columnaSeleccionada, actualizacion, ticketNum);
                                        }
                                        break;
                                } 
                                do{
                                   opcion = Short.parseShort(JOptionPane.showInputDialog("¿quiere editar algún valor más de este ticket? 1.Si 2.No")); 
                                }while(opcion < 1 || opcion > 2);              
                            }while(opcion != 2);
                                                               
                            break;                                                        
                        case 4://borrar ticket (logicamente satus: 0)
                            nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
                            idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id del cliente:"));
                            ticketNum = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del ticket:"));
                            fechaActual = LocalDateTime.now().withNano(0);
                            ticketDao.borrarStatus(fechaActual, nombre, idUsuario, ticketNum);
                            break;                                                                                                  
                        /*case 5: //borrar completamente (físicamente)
                            nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
                            idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id del cliente:"));
                            ticketNum = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del ticket:"));
                            ticketDao.borrarTicket(nombre, idUsuario, ticketNum);
                            break;*/                                                    
                        case 0:
                            JOptionPane.showMessageDialog(null,"Saliendo del programa", "Información", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Opción inhabilitada o fuera de rango", "Aviso", JOptionPane.WARNING_MESSAGE);
                            break;
                }                                        
            }catch(Exception e){
                System.out.println("Se ha generado un error: " + e.getMessage() + " Por favor verificar"); 
                if (e.getMessage().contains("For input string")) {
                    JOptionPane.showMessageDialog(null, "Porfavor ingresar correctamente los datos solicitados", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }    
        }while(opcion != 0);
    }
}

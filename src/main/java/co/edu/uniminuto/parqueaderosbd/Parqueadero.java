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
        //opciones de navegación:
        short opcion = 0;

        //Usuario
        int idUsuario;
        String nombre;
        String apellido;

        //Vehiculo       
        String tipoVehiculo;  
        Carro carro;
        Moto moto;
        Bicicleta bicicleta;
        String marca;
        String modelo;
        String placa;
        
        //Ticket      
        LocalDateTime entradaDeVehiculo;
        LocalDateTime fechaGeneradaUsuario;
        int parqueadero;
        Usuario usuario;
        double pago;
        
        //consultar
        int ticketNum;
        Ticket ticket;
        
        //Dao
        UsuarioDao usuarioDao;
        TicketDao ticketDao;
        
        ticketDao = new TicketDao();
        ticket = new Ticket();
        //fecha actual
        LocalDateTime fechaTranscurrida;    
        
        do {
            try{
                fechaTranscurrida = LocalDateTime.now().withNano(0);
                ticketDao.actualizarStatusAutomaticamente(fechaTranscurrida);

                do {
                    opcion = Short.parseShort(JOptionPane.showInputDialog("1.Crear un nuevo ticket\n2.consultar tickets\n3.Borrar ticket (logicamente)\nElija su opción: ")); 
                    if (opcion > 5 || opcion < 0) {
                        JOptionPane.showMessageDialog(null, "Lo sentimos pero esa opción no es válida", "Advertencia", JOptionPane.WARNING_MESSAGE); 
                    }
                } while (opcion > 5 || opcion < 0);

                switch (opcion) {
                    case 1:
                            idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Documento del cliente:"));
                            nombre = JOptionPane.showInputDialog("Nombre del cliente:");
                            apellido = JOptionPane.showInputDialog("Apellido del cliente:");
                            usuario = new Usuario(idUsuario, nombre, apellido);

                            do {
                                tipoVehiculo = JOptionPane.showInputDialog("Ingrese el tipo de vehiculo:");
                                if (!tipoVehiculo.equals("Carro") && !tipoVehiculo.equals("Moto") && !tipoVehiculo.equals("Bicicleta")) {
                                    JOptionPane.showMessageDialog(null, "Lo sentimos pero esa opción no es válida, (la primera letra debe ser mayuscula)", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                }
                            } while (!tipoVehiculo.equals("Carro") && !tipoVehiculo.equals("Moto") && !tipoVehiculo.equals("Bicicleta"));

                            marca = JOptionPane.showInputDialog("Ingrese la marca del vehiculo:");
                            modelo = JOptionPane.showInputDialog("Ingrese el modelo del vehiculo:");

                            do {
                                parqueadero = Integer.parseInt(JOptionPane.showInputDialog("Número del parqueadero:"));
                                
                                if (parqueadero > 6) {                           
                                    JOptionPane.showMessageDialog(null, "Parqueadero ocupado, por favor elija otro", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                }
                            } while (parqueadero > 6);

                            fechaGeneradaUsuario = ticket.generarVariableDeTiempo(fechaTranscurrida);
                            entradaDeVehiculo = LocalDateTime.now().withNano(0);

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
                            }
                            System.out.println(ticket.toString());
                            //Crear ticket
                            ticketDao.registrarTicket(ticket);
                            break;   
                        case 2://consultar tickets
                            nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
                            idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id del cliente:"));
                            ticketNum = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del ticket:"));
                            ticketDao.consultarTicket(ticketNum);
                            break;
                        case 3://borrar ticket (logicamente satus: 0) 
                            nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
                            idUsuario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el Id del cliente:"));
                            ticketNum = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del ticket:"));
                            fechaTranscurrida = LocalDateTime.now().withNano(0);
                            ticketDao.borrarStatus(fechaTranscurrida, nombre, idUsuario, ticketNum);
                            break;                               
                        case 4://actualizar ticket
                            
                            break;                                                 
                        /*case 7: //borrar completamente (físicamente)
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
            }    
        }while(opcion != 0);
    }
}

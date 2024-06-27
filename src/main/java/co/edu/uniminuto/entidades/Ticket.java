package co.edu.uniminuto.entidades;

import co.edu.uniminuto.dao.TicketDao;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;

// Clase Ticket que representa un ticket de estacionamiento
public class Ticket {
    
    // Atributos del ticket
    private LocalDateTime entradaDeVehiculo;
    private LocalDateTime salidaDeVehiculo;
    private Usuario usuario;
    private Vehiculo vehiculo;
    private double pago;
    private int numeroDeParqueadero;
   
    // Constructor con todos los atributos
    public Ticket(LocalDateTime entradaDeVehiculo, LocalDateTime salidaDeVehiculo, Usuario usuario, Vehiculo vehiculo, double pago, int numeroDeParqueadero) {
        this.entradaDeVehiculo = entradaDeVehiculo;
        this.salidaDeVehiculo = salidaDeVehiculo;
        this.usuario = usuario;
        this.vehiculo = vehiculo;
        this.pago = pago;
        this.numeroDeParqueadero = numeroDeParqueadero;
    }

    // Constructor vacío
    public Ticket() {
    }

    // Constructor con salidaDeVehiculo como parámetro
    public Ticket(LocalDateTime salidaDeVehiculo) {
        this.salidaDeVehiculo = salidaDeVehiculo;
    }
       
    // Getters y setters para todos los atributos del ticket
    public LocalDateTime getEntradaDeVehiculo() {
        return entradaDeVehiculo;
    }

    public void setEntradaDeVehiculo(LocalDateTime entradaDeVehiculo) {
        this.entradaDeVehiculo = entradaDeVehiculo;
    }

    public LocalDateTime getSalidaDeVehiculo() {
        return salidaDeVehiculo;
    }

    public void setSalidaDeVehiculo(LocalDateTime salidaDeVehiculo) {
        this.salidaDeVehiculo = salidaDeVehiculo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public int getNumeroDeParqueadero() {
        return numeroDeParqueadero;
    }

    public void setNumeroDeParqueadero(int numeroDeParqueadero) {
        this.numeroDeParqueadero = numeroDeParqueadero;
    }        
       
    // Método para guardar la salida del vehículo con fecha y hora específicas
    public LocalDateTime guardarSalida(int año, int mes, int dias, int hora){
        this.entradaDeVehiculo = LocalDateTime.of(año, mes, dias, hora,0);
        return this.entradaDeVehiculo; 
    }
    
    // Método para calcular los minutos entre dos fechas y horas específicas
    public int calcularMinutos(LocalDateTime entradaDeVehiculo, LocalDateTime salidaDeVehiculo){        
        return (int) (Duration.between(entradaDeVehiculo, salidaDeVehiculo).toMinutes());
    }
    
    // Método para generar una variable de tiempo basada en la fecha y hora de entrada
    public LocalDateTime generarVariableDeTiempo(LocalDateTime entradaDeVehiculo){
        int horas;
        int dias;
        int minutos = entradaDeVehiculo.getMinute();
        
        do{
           dias = Integer.parseInt(JOptionPane.showInputDialog("Cuantos días (0-7): ")); 
        }while(dias < 0 || dias > 7);     
        
        do {
            horas = Integer.parseInt(JOptionPane.showInputDialog("Que hora del día (Formato 24h): "));            
        } while (horas < 0 || horas > 23 || dias == 0 && horas < entradaDeVehiculo.getHour());
        if (dias == 0){
                if (minutos >= 0 && minutos < 15){
                minutos = 15;
            }else if (minutos >= 15 && minutos < 30){
                minutos = 30;
            }else if (minutos >= 30 && minutos < 45){
                minutos = 45;
            }else if (minutos >= 45 && minutos <= 60){
                minutos = 59;
            }
        }        
        return entradaDeVehiculo.plusDays(dias).withHour(horas).withMinute(minutos).withSecond(entradaDeVehiculo.getSecond());
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket{");        
        sb.append(", usuario: '").append(usuario).append('\'');
        sb.append(", vehiculo: ").append(vehiculo);
        sb.append(", pago: ").append(pago);
        sb.append(", numeroDeParqueadero: '").append(numeroDeParqueadero).append('\'');
        sb.append("entradaDeVehiculo: ").append(entradaDeVehiculo);
        sb.append(", salidaDeVehiculo: ").append(salidaDeVehiculo);
        sb.append('}');
        return sb.toString();
    }
}
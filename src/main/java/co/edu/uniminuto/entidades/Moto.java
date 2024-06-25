package co.edu.uniminuto.entidades;

public class Moto extends Vehiculo {
    private String placa;
    private int precio = 100; // Precio de la moto por minuto

    public Moto( String tipo, String marca, String modelo, String placa) {
        super(tipo, marca, modelo);
        this.placa = placa;
    }

    public Moto() {
    }
    
    @Override
    public double calcularPago(int minutos) {
        return precio * minutos;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    
}
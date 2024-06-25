package co.edu.uniminuto.entidades;

public class Carro extends Vehiculo {
    private String placa;
    private int precio = 163; // No debería ser constante si cambia por vehículo

    public Carro( String tipo, String marca, String modelo, String placa) {
        super(tipo, marca, modelo);
        this.placa = placa;
    }

    public Carro() {
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


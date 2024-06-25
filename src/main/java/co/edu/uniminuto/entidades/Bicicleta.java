
package co.edu.uniminuto.entidades;

public class Bicicleta extends Vehiculo {
    
    private int precio = 10;

    public Bicicleta(String tipo, String marca, String modelo) {
        super(tipo, marca, modelo);
    }

    public Bicicleta() {
    }

    @Override
    public double calcularPago(int minutos) {
        return precio * minutos;
    }
       
}

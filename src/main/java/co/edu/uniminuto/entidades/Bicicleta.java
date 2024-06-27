package co.edu.uniminuto.entidades;

// Clase Bicicleta que extiende de la clase Vehiculo
public class Bicicleta extends Vehiculo {
    
    // Atributo precio con un valor por defecto de 10
    private int precio = 10;

    // Constructor que inicializa los atributos tipo, marca y modelo
    public Bicicleta(String tipo, String marca, String modelo) {
        super(tipo, marca, modelo); // Llama al constructor de la clase padre Vehiculo
    }

    // Constructor vacío por defecto
    public Bicicleta() {
    }

    @Override
    public double calcularPago(int minutos) {
        // Calcula el pago multiplicando el precio por los minutos
        return precio * minutos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bicicleta{");
        sb.append("precio=").append(precio);
        sb.append('}');
        return sb.toString();
    }
    
    
}


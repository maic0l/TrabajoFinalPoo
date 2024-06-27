package co.edu.uniminuto.entidades;

// Clase Moto que extiende de la clase Vehiculo
public class Moto extends Vehiculo {
    // Atributo placa para almacenar la placa de la moto
    private String placa;
    
    // Atributo precio con un valor por defecto de 100
    private int precio = 100;

    // Constructor que inicializa los atributos tipo, marca, modelo y placa
    public Moto(String tipo, String marca, String modelo, String placa) {
        super(tipo, marca, modelo); // Llama al constructor de la clase padre Vehiculo
        this.placa = placa; // Asigna el valor de placa
    }

    public Moto() {
    }
    
    // Método sobrescrito de la clase Vehiculo para calcular el pago en función de los minutos
    @Override
    public double calcularPago(int minutos) {
        // Calcula el pago multiplicando el precio por los minutos
        return precio * minutos;
    }

    // Getter para obtener el valor de la placa
    public String getPlaca() {
        return placa;
    }

    // Setter para establecer el valor de la placa
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Moto{");
        sb.append("placa=").append(placa);
        sb.append(", precio=").append(precio);
        sb.append('}');
        return sb.toString();
    }
    
    
}
package co.edu.uniminuto.entidades;

// Clase Carro que extiende de la clase Vehiculo
public class Carro extends Vehiculo {
    // Atributo placa para almacenar la placa del carro
    private String placa;
    
    // Atributo precio con un valor por defecto de 163
    // Este valor puede cambiar por vehículo, por lo que no es constante
    private int precio = 163;

    // Constructor que inicializa los atributos tipo, marca, modelo y placa
    public Carro(String tipo, String marca, String modelo, String placa) {
        super(tipo, marca, modelo); // Llama al constructor de la clase padre Vehiculo
        this.placa = placa; // Asigna el valor de placa
    }

    // Constructor vacío por defecto
    public Carro() {
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
        return "Carro{" + "placa=" + placa + ", precio=" + precio + '}';
    }
    
    
}


package co.edu.uniminuto.entidades;

import co.edu.uniminuto.interfaces.IVehiculo;

// Clase abstracta Vehiculo que implementa la interfaz IVehiculo
public abstract class Vehiculo implements IVehiculo {
    // Atributos protegidos para tipo, marca y modelo del vehículo
    protected String tipo;
    protected String marca;
    protected String modelo;

    // Constructor que inicializa los atributos tipo, marca y modelo
    public Vehiculo(String tipo, String marca, String modelo) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
    }

    // Constructor vacío
    public Vehiculo() {
    }    

    // Getter para obtener el tipo del vehículo
    public String getTipo() {
        return tipo;
    }

    // Setter para establecer el tipo del vehículo
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Getter para obtener la marca del vehículo
    public String getMarca() {
        return marca;
    }

    // Setter para establecer la marca del vehículo
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // Getter para obtener el modelo del vehículo
    public String getModelo() {
        return modelo;
    }

    // Setter para establecer el modelo del vehículo
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    //  toString para devolver una representación en cadena del objeto Vehiculo
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vehiculo{");
        sb.append("tipo: ").append(tipo);
        sb.append(", marca: ").append(marca);
        sb.append(", modelo: ").append(modelo);
        sb.append('}');
        return sb.toString();
    }
}


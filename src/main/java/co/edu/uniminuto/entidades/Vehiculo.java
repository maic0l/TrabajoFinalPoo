
package co.edu.uniminuto.entidades;

import co.edu.uniminuto.interfaces.IVehiculo;

public abstract class Vehiculo implements IVehiculo{
    protected String tipo;
    protected String marca;
    protected String modelo;

    public Vehiculo(String tipo, String marca, String modelo) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
    }

    public Vehiculo() {
    }    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    
}

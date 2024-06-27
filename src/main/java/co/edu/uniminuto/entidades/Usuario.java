package co.edu.uniminuto.entidades;

// Clase Usuario que representa a un usuario del sistema
public class Usuario {
    
    // Atributos del usuario
    private int idUsuario;
    private String nombre;
    private String apellido;

    // Constructor con todos los atributos
    public Usuario(int idUsuario, String nombre, String apellido) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Getter para obtener el ID del usuario
    public int getIdUsuario() {
        return idUsuario;
    }

    // Setter para establecer el ID del usuario
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Getter para obtener el nombre del usuario
    public String getNombre() {
        return nombre;
    }

    // Setter para establecer el nombre del usuario
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter para obtener el apellido del usuario
    public String getApellido() {
        return apellido;
    }

    // Setter para establecer el apellido del usuario
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append("nùmero de ID: ").append(idUsuario);
        sb.append(", nombre: ").append(nombre);
        sb.append(", apellido: ").append(apellido);
        sb.append(")");
        return sb.toString();
    }
    
    
}

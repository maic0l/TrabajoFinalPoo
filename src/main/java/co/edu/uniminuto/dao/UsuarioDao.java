package co.edu.uniminuto.dao;

import co.edu.uniminuto.entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

// Clase UsuarioDao para manejar operaciones de base de datos relacionadas con usuarios
public class UsuarioDao {

    // Instancia de la clase Conexion para manejar la conexión a la base de datos
    Conexion conexion;
    // Objeto PreparedStatement para ejecutar consultas SQL precompiladas
    PreparedStatement statement;

    // Constructor de la clase UsuarioDao
    public UsuarioDao() {
        // Inicializa la instancia de Conexion
        conexion = new Conexion();
        // Inicializa el PreparedStatement a null
        this.statement = null;
    }
    
    // Método para registrar un usuario en la base de datos
    public Usuario registrarUsuario(Usuario usuario) {
        // Obtiene una conexión a la base de datos
        Connection con = conexion.getconectar();
        // Consulta SQL para insertar un nuevo usuario
        String query = "INSERT INTO usuarios VALUES(null, ?, ?, ?)";
        try {
            // Comprueba si el PreparedStatement es null
            if (this.statement == null) {
                // Prepara la consulta SQL
                this.statement = con.prepareStatement(query);
                // Establece los valores para la consulta
                this.statement.setInt(1, usuario.getIdUsuario());
                this.statement.setString(2, usuario.getNombre());
                this.statement.setString(3, usuario.getApellido());
                // Ejecuta la consulta y obtiene la respuesta
                int respuesta = this.statement.executeUpdate();
                // Si la respuesta es mayor a 0, muestra un mensaje de éxito
                if (respuesta > 0) {
                    JOptionPane.showMessageDialog(null,"Datos subidos con éxito: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) { 
            // En caso de excepción, imprime el mensaje de error
            System.out.println("" + e.getMessage());
        } finally {  
            // Finalmente, cierra la conexión y el PreparedStatement si no son null
            if (con != null) {
                try {
                    this.statement.close();
                    con.close();
                } catch (SQLException ex) {
                    // En caso de excepción al cerrar, registra el error en el logger
                    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
                }               
            }
        }
        // Devuelve el usuario registrado
        return usuario;
    }
}

package co.edu.uniminuto.dao;

import co.edu.uniminuto.entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UsuarioDao {
    
    Conexion conexion;
    PreparedStatement statement;

    public UsuarioDao() {
        conexion = new Conexion();
        this.statement = null;
    }
    
    public Usuario registrarUsuario(Usuario usuario){
        Connection con = conexion.getconectar();
        String query = "INSERT INTO usuarios VALUES(?, ?, ?)";
        try{
            if (this.statement == null){
                this.statement = con.prepareStatement(query);
                this.statement.setInt(1, usuario.getIdUsuario());
                this.statement.setString(2, usuario.getNombre());
                this.statement.setString(3, usuario.getApellido());
                int respuesta = this.statement.executeUpdate();
                if (respuesta > 0){
                    JOptionPane.showMessageDialog(null,"Datos subidos con exito: " + respuesta, "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch(Exception e){ 
            System.out.println("" + e.getMessage());
        }finally{  
            if(con != null){
                try {
                    this.statement.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
                }               
            }
        }
        return usuario;
    }

}
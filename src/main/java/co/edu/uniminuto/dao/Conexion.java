
package co.edu.uniminuto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {
    
    private String usuario;
    private String contra;
    private String puerto;
    private String servidor;
    private String nombreDB;
    private String cadena;
    Connection conex;

    public Conexion() {
        this.usuario = "root";
        this.contra = "";
        this.puerto = "3306";
        this.servidor = "localhost";
        this.nombreDB = "registro_de_parqueaderos";
        this.cadena = "jdbc:mysql://" + this.servidor +":"+this.puerto + "/"+this.nombreDB;
        this.conex = null;
    }
    
    private Connection conectar(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conex = DriverManager.getConnection(this.cadena, this.usuario, this.contra);
            System.out.println("conexion establecida correctamente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No hubo conexión: " + e.getMessage());
            System.out.println("No hubo conexión: " + e.getMessage());
        }
        return this.conex;
    }
    
    public Connection getconectar(){
        return this.conectar();
    }
}

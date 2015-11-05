    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mindmanager.bancodedados;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author EagleTech
 */
public class criarConexao {
    
    public static Connection getConexao() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/mindmanager_bd","root","321");
        }catch(ClassNotFoundException e){
            throw new SQLException(e.getMessage());
        }
        
    }
}

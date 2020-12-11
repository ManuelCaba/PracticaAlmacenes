/*
Creamos una conexión un poco más elegante
 */

package conexionBD;
import java.sql.*;
import java.util.Properties;
/**
 *
 * @author Leo
 */
public class ConexionConProperties {
    // El parámetro tipo nos permite usar distintas conexiones con permisos diferentes, aunque sólo se ha implementado una
    public static Connection getConexion (byte tipo) throws SQLException {
        String url = "";
        Properties propiedadesConexion = new java.util.Properties();;
        if (tipo == 1){
            // Cadena de conexión
            url = "jdbc:sqlserver://localhost";
            propiedadesConexion.put("user","pepito");
            propiedadesConexion.put("password","qq");
            propiedadesConexion.put("useUnicode","true");
            propiedadesConexion.put("characterEncoding","utf8");
        }
        Connection conexionBaseDatos = DriverManager.getConnection(url,propiedadesConexion);
        return conexionBaseDatos; 
    }
    // Método sobrecargado
    public static void cerrar ( ResultSet rs ) throws SQLException {
        rs.close();
    }
    public static void cerrar ( Statement st ) throws SQLException {
        st.close();
    }
    public static void cerrar (Connection con) throws SQLException {
        con.close();
    }    
}
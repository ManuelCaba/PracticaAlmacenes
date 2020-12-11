package conexionBD;

import java.sql.*;

public class MiConexion {
    public static Connection getConexion() throws SQLException {
        // Carga la clase del driver. Lo comentamos porque no es necesario a partir de Java 6
        //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        
        // Cadena de conexión
        String url="jdbc:sqlserver://localhost;databaseName=AlmacenesLeo;user=prueba;password=123;";
        // Connection es una interface, no una clase
        Connection conexionBaseDatos = DriverManager.getConnection(url);
        
        return conexionBaseDatos; 
    }
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

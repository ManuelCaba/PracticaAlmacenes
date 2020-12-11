package conexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaConexion 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		ResultSet rs = listadoPedidosSinAsignar();

		try {
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" | "+rs.getInt(2)+" | "+rs.getInt(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*********************************************************************************************
	 * Método que devuelve un objeto ResultSet con el listado de los envios de una base de datos
	 * por asignar.
	 * Signatura: public ResultSet listadoPedidosSinAsignar();
	 * Entradas: No hay
	 * Precondiciones: No tiene
	 * Salidas: ResultSet listadoEnvios
	 * Postcondiciones: Se devolverá un objeto ResultSet asociado al nombre con el listado de
	 * 					envíos por asignar. En caso de error se lanzará una excepción SQLException.
	 **********************************************************************************************/
	public static ResultSet listadoPedidosSinAsignar()
	{
		ResultSet listadoEnvios = null;
		
		try 
		{
			Connection conexion = MiConexion.getConexion();
			
			PreparedStatement statement;
			String query = null;
			
			try {
				
				if(conexion != null)
				{
					query = "SELECT E.ID, E.NumeroContenedores, E.AlmacenPreferido FROM Envios AS E\r\n"
							+ "LEFT JOIN Asignaciones AS A ON E.ID = A.IDEnvio WHERE A.IDAlmacen IS NULL\r\n";
					
					statement = conexion.prepareStatement(query);
					listadoEnvios = statement.executeQuery();					
				}
				
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listadoEnvios;
	}
}
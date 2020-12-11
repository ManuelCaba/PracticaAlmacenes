package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexionBD.ConexionConProperties;

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
			
			//System.out.println(obtenerContenedores(rs, 5));
			System.out.println(almacenFavorito(rs, 5));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
     * Método que devuelve un objeto ResultSet con el listado de los envios de una base de datos
     * por asignar.
     * Signatura: public ResultSet listadoPedidosSinAsignar();
     * Entradas: No hay
     * Precondiciones: No tiene
     * Salidas: ResultSet listadoEnvios
     * Postcondiciones: Se devolverá un objeto ResultSet asociado al nombre con el listado de
     *                     envíos por asignar. En caso de error se lanzará una excepción SQLException.
     **/
    public static ResultSet listadoPedidosSinAsignar()
    {
        ResultSet listadoEnvios = null;

        try 
        {
            Connection conexion = ConexionConProperties.getConexion((byte) 1);

            Statement statement;
            String query = null;

            try {

                if(conexion != null)
                {
                    query = "SELECT E.ID, E.NumeroContenedores, E.AlmacenPreferido FROM Envios AS E\r\n"
                            + "LEFT JOIN Asignaciones AS A ON E.ID = A.IDEnvio WHERE A.IDAlmacen IS NULL\r\n";


                    statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    listadoEnvios = statement.executeQuery(query);
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
	
	
	public static int obtenerContenedores(ResultSet  rs, int id) {
		
		boolean coincidencia = false;
		int contenedores = 0;
		
		try {
			rs.beforeFirst();	
			
			while(rs.next() && coincidencia == false) {
				if(rs.getInt(1) == id) {
					contenedores = rs.getInt(2);
					coincidencia = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contenedores;
	}
	
	
	public static int almacenFavorito(ResultSet rs, int id) {
		
		boolean coincidencia = false;
		int almacenFavorito = 0;
		
		try {
			
			rs.beforeFirst();	
			
			while(rs.next() && coincidencia == false) {
				if(rs.getInt(1) == id) {
					almacenFavorito = rs.getInt(3);
					coincidencia = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return almacenFavorito;
	}
	
	
}
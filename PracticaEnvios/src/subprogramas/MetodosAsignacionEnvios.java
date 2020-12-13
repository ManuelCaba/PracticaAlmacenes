package subprogramas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexionBD.ConexionConProperties;

public class MetodosAsignacionEnvios {
	
	/**********************************************************************************************
     * Método que devuelve un objeto ResultSet con el listado de los envios de una base de datos
     * por asignar.
     * Signatura: public ResultSet listadoPedidosSinAsignar();
     * Entradas: No hay
     * Precondiciones: No tiene
     * Salidas: ResultSet listadoEnvios
     * Postcondiciones: Se devolverá un objeto ResultSet asociado al nombre con el listado de
     *                  envíos por asignar. En caso de error se lanzará una excepción SQLException.
     **********************************************************************************************/
    public ResultSet listadoPedidosSinAsignar()
    {
        ResultSet listadoEnvios = null;

        Statement statement;
        String query = null;

        try {
        	
        	Connection conexion = ConexionConProperties.getConexion((byte) 1);
        	
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

        return listadoEnvios;
    }
	
	/**********************************************************************************************
     * Método que devuelve un int con el número de contenedores de un envío a partir de su ID y un
     * ResultSet de envíos.
     * Signatura: public int obtenerContenedores(ResultSet rsEnvios, int id);
     * Entradas:
     * 		- ResultSet rsEnvios
     * 		- int id
     * Precondiciones: No tiene
     * Salidas: 
     * 		- int contenedores
     * Postcondiciones: Se devolverá un int con los contenedores del envío solicitado.
     **********************************************************************************************/
	public int obtenerContenedores(ResultSet rsEnvios, int id) {
		
		boolean coincidencia = false;
		int contenedores = 0;
		
		try {
			rsEnvios.beforeFirst();	
			
			while(rsEnvios.next() && coincidencia == false) {
				if(rsEnvios.getInt(1) == id) {
					contenedores = rsEnvios.getInt(2);
					coincidencia = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contenedores;
	}
	
	/**********************************************************************************************
     * Método que devuelve un int con el id del almacen preferido de un envío a partir de su ID y 
     * un ResultSet de envíos.
     * Signatura: public int almacenFavorito(ResultSet rsEnvios, int id);
     * Entradas:
     * 		- ResultSet rsEnvios
     * 		- int id
     * Precondiciones: El ResultSet debe ser dinámico
     * Salidas: 
     * 		- int almacenFavorito
     * Postcondiciones: Se devolverá un int con el id del almacen preferido del envío solicitado.
     **********************************************************************************************/
	public int almacenFavorito(ResultSet rsEnvios, int id) {
		
		boolean coincidencia = false;
		int almacenFavorito = 0;
		
		try {
			
			rsEnvios.beforeFirst();	
			
			while(rsEnvios.next() && coincidencia == false) {
				if(rsEnvios.getInt(1) == id) {
					almacenFavorito = rsEnvios.getInt(3);
					coincidencia = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return almacenFavorito;
	}
	
	/**********************************************************************************************
     * Método que asigna un envio a un almacen en la base de datos.
     * Signatura: public boolean asignarEnvio(int idEnvio, int idAlmacen);
     * Entradas:
     * 		- int idEnvio
     * 		- int idAlmacen
     * Precondiciones: No tiene
     * Salidas: 
     * 		- boolean estaAsignado
     * Postcondiciones: Se asignará un envío en el almacen solicitado. En caso de error devolverá 
     * false.
     **********************************************************************************************/
	public boolean asignarEnvio(int idEnvio, int idAlmacen) {
		
		boolean estaAsignado = true;
		String update;
		Statement statement;
		
        try {
        	
        	Connection conexion = ConexionConProperties.getConexion((byte) 1);
        	
            if(conexion != null)
            {
                update = "INSERT Asignaciones(IDEnvio, IDAlmacen)\r\n" + 
                		 "VALUES("+idEnvio+","+idAlmacen+") ";
                
                statement = conexion.createStatement();


                statement.execute(update);
            }
            
            conexion.close();

        } catch (SQLException e) 
        {
            estaAsignado = false;
        }
		
		return estaAsignado;
	}
	
	/**********************************************************************************************
     * Método que devuelve el id del almacén más cercano al almacen cuyo id se pasa por parámetros.
     * Signatura: public int almacenMasCercano(int idAlmacen);
     * Entradas:
     * 		- int idAlmacen
     * Precondiciones: No tiene
     * Salidas: 
     * 		- int idAlmacenMasCercano
     * Postcondiciones: Se devolverá el id del almacén mas cercano. En caso de no encontrarse
     * devolverá -1.
     **********************************************************************************************/
	public int almancenCercano(int idAlmacen) {
		
		int idAlmacenMasCercano = -1;
		String query;
		Statement statement;
		ResultSet rsAlmacenMasCercano;
		
        try {
        	
        	Connection conexion = ConexionConProperties.getConexion((byte) 1);
        	
            if(conexion != null)
            {
                query = "SELECT D.IDAlmacen2 FROM Distancias AS D\r\n" + 
                		"INNER JOIN\r\n" + 
                		"(\r\n" + 
                		"	SELECT IDAlmacen1, MIN(Distancia) AS MinimaDistancia FROM Distancias\r\n" + 
                		"	GROUP BY IDAlmacen1\r\n" + 
                		") AS M ON D.Distancia = M.MinimaDistancia AND D.IDAlmacen1 = M.IDAlmacen1\r\n" + 
                		"WHERE D.IDAlmacen1 = "+idAlmacen;
                
                statement = conexion.createStatement();


                rsAlmacenMasCercano = statement.executeQuery(query);
                
                if(rsAlmacenMasCercano.next())
                {
                	idAlmacenMasCercano = rsAlmacenMasCercano.getInt(1);
                }
            }
            
            conexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idAlmacenMasCercano;
	}
	
	/**********************************************************************************************
     * Método que comprueba si existe un envío en un ResultSet de envíos según su ID.
     * Signatura: public boolean existeEnvio(ResultSet rsEnvios,int idEnvio);
     * Entradas:
     * 		- ResultSet rsEnvio
     * 		- int idEnvio
     * Precondiciones: No tiene
     * Salidas: 
     * 		- boolean existeEnvio
     * Postcondiciones: Se devolverá true si el envío existe en el ResultSet pasado por parámetros.
     * En caso contrario devolverá false.
     **********************************************************************************************/
	public boolean existeEnvio(ResultSet rsEnvios,int idEnvio) {
		
		boolean existeEnvio = false;
		
        try {
        	
			rsEnvios.beforeFirst();	
			
			while(rsEnvios.next() && existeEnvio == false) {
				if(rsEnvios.getInt(1) == idEnvio) {
					existeEnvio = true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return existeEnvio;
	}

}

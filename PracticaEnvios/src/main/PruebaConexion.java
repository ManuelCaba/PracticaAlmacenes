package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import subprogramas.MetodosAsignacionEnvios;
import validaciones.Validaciones;

public class PruebaConexion 
{

	public static void main(String[] args) 
	{
		char opcion;
		int idEnvio;
		int idAlmacenFavorito;
		String almacenesComprobados;
		int idAlmacen = 0;
		boolean envioAsignado = false;
		ResultSet rsEnvios;
		
		Validaciones validaciones = new Validaciones();
		MetodosAsignacionEnvios metodos = new MetodosAsignacionEnvios();
		
		//Leer Y Validar Asignar Envio
		opcion = validaciones.leerYValidarAsignarEnvio();
		
		while(opcion == 'S')
		{
			
			rsEnvios = metodos.listadoPedidosSinAsignar();
			
			try {
				String formatoTabla = "| %-12s | %-12s | %-17s |\n";//Formato para mostrar los datos en forma de tabla
				System.out.println("+--------------+--------------+-------------------+");
				System.out.format(formatoTabla, "ID Almacen", "Contenedores", "Almacen favorito");	//Cabecera de la tabla
				System.out.println("+--------------+--------------+-------------------+");
				
				while(rsEnvios.next())
				{
					//Filas de datos
					System.out.format(formatoTabla, rsEnvios.getInt(1), rsEnvios.getInt(2), rsEnvios.getInt(3));
					System.out.println("+--------------+--------------+-------------------+");
				}
				
				idEnvio = validaciones.leerYValidarIDenvio();
				
				if(metodos.existeEnvio(rsEnvios, idEnvio))
				{
					almacenesComprobados = "";
					idAlmacen = 0;
					idAlmacenFavorito = 0;
					
					do
					{
						
						if(idAlmacenFavorito != 0 && !envioAsignado) //Si no se ha podido asignar en el anterior almacén
						{
							
							if(opcion == 'S') //Si aún quiere probar con otro almacén
							{
								//Buscar almacén más cercano
								idAlmacen = metodos.almancenCercano(idAlmacenFavorito, almacenesComprobados);
								almacenesComprobados += almacenesComprobados == "" ? idAlmacen : ","+idAlmacen;
							}
						}
						else //Si todavía no se ha buscado su almacén favorito
						{
							idAlmacenFavorito = metodos.almacenFavorito(rsEnvios, idEnvio);
							idAlmacen = idAlmacenFavorito;
						}
						
						if(idAlmacen != -1) //Si hay almacenes para asignar disponibles
						{
							opcion = validaciones.leerYValidarAsignarEnAlmacen(idAlmacen);
							
							if(opcion == 'S') //Confirmar si quiere asignarla en el almacén encontrado
							{
								envioAsignado = metodos.asignarEnvio(idEnvio, idAlmacen);
								
								if(!envioAsignado) //Si no se ha podido asignar
								{
									opcion = validaciones.leerYValidarProbarOtroAlmacen();
								}
								else
								{
									System.out.println("Envio asignado con exito!");
								}
								
							}
						}
						else
						{
							System.out.println("No hay almacenes cercanos");
						}


					}
					while(idAlmacen != 0 && !envioAsignado && opcion == 'S');
					
				}
				else
				{
					System.out.println("El envio elegido no es correcto");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			opcion = validaciones.leerYValidarAsignarEnvio();
		}
	}
	
}
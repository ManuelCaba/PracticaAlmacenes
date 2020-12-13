package validaciones;

import java.util.Scanner;

public class Validaciones {
	
	//Este metodo recogera nuestra respuesta a la pregunta que lanzamos '¿Desea asignar algun envio?'
	//Solo contemplara las opciones 'S' para Si y 'N' para No
	public char leerYValidarAsignarEnvio()
	{
		Scanner teclado = new Scanner(System.in);
		char opcion;
		
		do
		{
			System.out.print("Desea asignar algun envio? (S/N): ");
			opcion = Character.toUpperCase(teclado.next().charAt(0));
		}
		while(opcion != 'S' && opcion != 'N');
		
		return opcion;
	}
	
	//Este metodo recogera nuestra eleccion a la pregunta 'Escriba el ID del envio que desee asignar'
	//Solo contemplara numeros enteros mayores que 1
	public int leerYValidarIDenvio()
	{
		Scanner teclado = new Scanner(System.in);
		int idEnvio;
		
		do
		{
			System.out.print("Escriba el ID del envio que desee asignar: ");
			idEnvio = teclado.nextInt();
		}
		while(idEnvio < 1);
		
		return idEnvio;
	}
	
	//Este metodo recogera nuestra respuesta a la pregunta que lanzamos '¿Desea asignar el envio en el almacen x?'
	//Solo contemplara las opciones 'S' para Si y 'N' para No
	public char leerYValidarAsignarEnAlmacen(int idAlmacen)
	{
		Scanner teclado = new Scanner(System.in);
		char opcion;
		
		do
		{
			System.out.print("¿Desea asignar el envio en el almacen "+idAlmacen+"? (S/N): ");
			opcion = Character.toUpperCase(teclado.next().charAt(0));
		}
		while(opcion != 'S' && opcion != 'N');
		
		return opcion;
	}
	
	//Este metodo recogera nuestra respuesta a la pregunta que lanzamos '¿Desea probar otro almacen??'
	//Solo contemplara las opciones 'S' para Si y 'N' para No
	public char leerYValidarProbarOtroAlmacen()
	{
		Scanner teclado = new Scanner(System.in);
		char opcion;
		
		do
		{
			System.out.print("No se ha podido asignar el envio en el almacen elegido.\n¿Desea probar otro almacen? (S/N): ");
			opcion = Character.toUpperCase(teclado.next().charAt(0));
		}
		while(opcion != 'S' && opcion != 'N');
		
		return opcion;
	}
	
	

}

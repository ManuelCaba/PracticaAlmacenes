package validaciones;

import java.util.Scanner;

public class Validaciones {
	
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
	
	public char leerYValidarAsignarEnAlmacen(int idAlmacen)
	{
		Scanner teclado = new Scanner(System.in);
		char opcion;
		
		do
		{
			System.out.print("Desea asignar el envio en el almacen "+idAlmacen+"? (S/N): ");
			opcion = Character.toUpperCase(teclado.next().charAt(0));
		}
		while(opcion != 'S' && opcion != 'N');
		
		return opcion;
	}
	
	public char leerYValidarProbarOtroAlmacen()
	{
		Scanner teclado = new Scanner(System.in);
		char opcion;
		
		do
		{
			System.out.print("No se ha podido asignar el envio en el almacen elegido. /n Desea probar otro almacen? (S/N): ");
			opcion = Character.toUpperCase(teclado.next().charAt(0));
		}
		while(opcion != 'S' && opcion != 'N');
		
		return opcion;
	}
	
	

}

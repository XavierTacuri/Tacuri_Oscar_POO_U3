package ups.edu.logica;

import java.util.ArrayList;
import java.util.Scanner;

class ControlExcepciones extends Exception{

	public ControlExcepciones(String mensaje) {
		super(mensaje);
		
	}
	
}


public class IngresoDatos {

  String etapa;
  ArrayList<String> listaEquipos;
  Scanner scanner;	
	public IngresoDatos() {
	
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public ArrayList<String> getListaEquipos() {
		return listaEquipos;
	}

	public void setListaEquipos(ArrayList<String> listaEquipos) {
		this.listaEquipos = listaEquipos;
	}

	public void ingresarDatos()  {	
		scanner = new Scanner(System.in);// Crea un nuevo objeto Scanner para leer datos desde el teclado
		listaEquipos= new ArrayList<String>(); // Crea un nuevo objeto ArrayList que almacenará elementos de tipo String
		
		boolean opcionValida = false; // Bandera de control

		while (!opcionValida) {        //Inicio el bucle while que se ejecuta conforme a la Banbdera
			
			try {
				System.out.println("Ingrese la etapa"); //Mensaje en consola
				etapa = scanner.next();					//Capturo la etapa ingresa por consola
				scanner.nextLine();
				switch (etapa) { 			// Inicia una estructura switch que evalúa el valor de la variable 'etapa'
			
				case "Octavos":
		        	
		            this.ingresarEquipos(16); //Llamo el metodo ingresarEquipos conforme a la estapa ingresada
		            opcionValida = true;	  //Controld de bandera
		            break;
		            
				case "Cuartos":
		        	
		            this.ingresarEquipos(8);  //Llamo el metodo ingresarEquipos confrome a la estapa ingresada
		            opcionValida = true;	  //Control de bandera
		            break;
				case "Semifinales":
		        	
		            this.ingresarEquipos(4);  //Llamo el metodo ingresarEquipos confrome a la estapa ingresada
		            opcionValida = true;      //Control de bandera
		            break;
				default:                     //Menejo de excepciones mediante excepciones personalizadas
											//Control de que las Etapas sean las correctas	
					throw new ControlExcepciones("Error: Etapa " + etapa + " no válida." + "Vuelve a ingresar");          
				}
				
			}catch (ControlExcepciones e) {
	            System.out.println(e.getMessage()); // Mensaje de la excepcion 
	            		// limpiar el buffer
	        }
		}
}
	
	public void ingresarEquipos(int num) {
		
		for (int i=0 ; i<num; i++) {
			System.out.println("Ingrese el nombre del Equipo #" + (i+1)+ ":"); //Mensaje en consola
			listaEquipos.add(scanner.nextLine().trim());       //Capturo el Equipo ingresado por consola	
			
		}										//y lo agrego a la lista 'listaEquipos'	
		
		scanner.close();  				//Cierra el objeto Scanner
	}
}

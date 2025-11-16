package ups.edu.logica;

import java.util.ArrayList;
import java.util.Collections;

public class Sorteo {

	
	public void sorterar(ArrayList equipos, String etapa) {
		
		Collections.shuffle(equipos);  //Mezcla aleatoria de la lista equipos usando Collections.shuffle
		EmparejarRecursivo(equipos, etapa, 0); //Llamada al metodo recursivo para el emparejamiento
											   // de los partidos 
	}
	
	public void EmparejarRecursivo(ArrayList equipos, String etapa, int indice) {
		
		if (indice < equipos.size()) {   //Control de la recursividad 
			
			System.out.println("Partido: " + equipos.get(indice) + " vs " + equipos.get(indice+1)); //Visualizacion de datos en consola
			
			EmparejarRecursivo(equipos, etapa, indice+2); // Llama nuevamente al método EmparejarRecursivo de forma recursiva
		}
	}	
}

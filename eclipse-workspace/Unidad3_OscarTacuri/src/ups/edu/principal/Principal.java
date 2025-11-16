package ups.edu.principal;

import java.util.ArrayList;
import java.util.Scanner;


import ups.edu.logica.IngresoDatos;
import ups.edu.logica.Sorteo;

//autor Oscar Xavier Tacuri

public class Principal {

	
	public static void main(String[] args)  {
		
		IngresoDatos ingDat= new IngresoDatos(); //Creo un nuevo objeto de la clase IngresoDatos 
		Sorteo sorteo= new Sorteo(); //Creo un nuevo objeto de la clase Sorteo 
		
	
		ingDat.ingresarDatos(); // Llamo al método ingresarDatos() del objeto ingDat 
		
		sorteo.sorterar(ingDat.getListaEquipos(),ingDat.getEtapa());// Llamo al método soretar() del objeto ingDat 
		

	}	

}

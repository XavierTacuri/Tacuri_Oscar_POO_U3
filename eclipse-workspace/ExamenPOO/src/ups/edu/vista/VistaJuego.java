package ups.edu.vista;

import ups.edu.modelo.Casilla;
import ups.edu.modelo.Tablero;

public class VistaJuego {

    public void mostrarBanner() {
        System.out.println("=====================================");
        System.out.println("           BUSCAMINAS MVC            ");
        System.out.println("=====================================");
    }

    public void mostrarMenuPrincipal() {
        mostrarBanner();
        System.out.println("1. Nueva partida");
        System.out.println("2. Cargar partida");
        System.out.println("3. Ayuda");
        System.out.println("4. Salir");
        System.out.print("Elige una opción: ");
    }

    public void mostrarAyuda() {
        System.out.println("\nAyuda:");
        System.out.println("1. El objetivo es descubrir todas las casillas sin minas.");
        System.out.println("2. Ingresa coordenadas como 'A5' o 'C10'.");
        System.out.println("3. Para poner una bandera (marcar mina), escribe 'M' antes.");
        System.out.println("   Ejemplo: 'M A5'.");
        System.out.println("4. Escribe 'G' durante el juego para Guardar y Salir.");
        System.out.println("\nPresiona ENTER para volver al menú...");
    }

    public void mostrarTablero(Tablero tablero, boolean mostrarMinas) {
        int tam = tablero.getTamano();
        System.out.println("\n     1  2  3  4  5  6  7  8  9 10");
        System.out.println("   +------------------------------+");
        for (int i = 0; i < tam; i++) {
            char letra = (char) ('A' + i);
            System.out.print(" " + letra + " |");
            for (int j = 0; j < tam; j++) {
                Casilla c = tablero.getCasilla(i, j);
                String simbolo;

                if (c.estaDescubierta()) {
                    if (c.esMina()) {
                        simbolo = "*";
                    } else {
                        int n = c.getMinasVecinas();
                        simbolo = (n == 0) ? " " : String.valueOf(n);
                    }
                } else {
                    if (c.estaMarcada()) {
                        simbolo = "P"; // bandera
                    } else if (mostrarMinas && c.esMina()) {
                        simbolo = "*";
                    } else {
                        simbolo = ".";
                    }
                }

                System.out.print(" " + simbolo + " ");
            }
            System.out.println("|");
        }
        System.out.println("   +------------------------------+");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
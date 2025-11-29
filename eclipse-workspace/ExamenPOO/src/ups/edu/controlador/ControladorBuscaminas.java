package ups.edu.controlador;

import java.io.*;
import java.util.Scanner;

import ups.edu.modelo.CasillaDescubierta;
import ups.edu.modelo.JuegoException;
import ups.edu.modelo.Tablero;
import ups.edu.vista.VistaJuego;

public class ControladorBuscaminas {

    private Tablero tablero;
    private final VistaJuego vista;
    private final Scanner scanner;

    public ControladorBuscaminas() {
        this.vista = new VistaJuego();
        this.scanner = new Scanner(System.in);
    }

    public void ejecutarMenu() {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenuPrincipal();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    nuevaPartida();
                    break;
                case "2":
                    cargarPartida();
                    break;
                case "3":
                    vista.mostrarAyuda();
                    esperarEnter();
                    break;
                case "4":
                    salir = true;
                    break;
                default:
                    vista.mostrarMensaje("Opción no válida. Intenta de nuevo.");
                    break;
            }
        }
        vista.mostrarMensaje("Gracias por jugar. ¡Hasta luego!");
    }

    private void nuevaPartida() {
        tablero = new Tablero();
        jugar();
    }

    private void jugar() {
        boolean juegoTerminado = false;
        boolean perdio = false;

        while (!juegoTerminado) {
            vista.mostrarTablero(tablero, false);
            System.out.println("\nIngresa una jugada:");
            System.out.println("- Ejemplo descubrir: A5");
            System.out.println("- Ejemplo marcar: M A5");
            System.out.println("- G para guardar y salir");
            System.out.print("> ");

            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.equals("G")) {
                guardarPartida();
                vista.mostrarMensaje("Partida guardada. Volviendo al menú...");
                return;
            }

            try {
                if (entrada.startsWith("M ")) {
                    // Marcar / desmarcar
                    String coord = entrada.substring(2).trim();
                    int[] fc = parsearCoordenadas(coord);
                    tablero.marcarCasilla(fc[0], fc[1]);
                } else {
                    // Descubrir
                    int[] fc = parsearCoordenadas(entrada);
                    boolean pisoMina = tablero.descubrirCasilla(fc[0], fc[1]);
                    if (pisoMina) {
                        perdio = true;
                        juegoTerminado = true;
                    } else if (tablero.esVictoria()) {
                        juegoTerminado = true;
                    }
                }
            } catch (CasillaDescubierta e) {
                vista.mostrarMensaje(e.getMessage());
            } catch (JuegoException e) {
                vista.mostrarMensaje("Error: " + e.getMessage());
            } catch (Exception e) {
                vista.mostrarMensaje("Entrada no válida. Usa formato A5 o M A5.");
            }
        }

        // Mostrar tablero final
        vista.mostrarTablero(tablero, true);
        if (perdio) {
            vista.mostrarMensaje("¡BOOM! Has pisado una mina. Has perdido.");
        } else {
            vista.mostrarMensaje("¡Felicidades! Has ganado el juego.");
        }
        esperarEnter();
    }

    /**
     * Convierte una coordenada tipo "A5" o "C10" en [fila, columna]
     */
    private int[] parsearCoordenadas(String entrada) throws JuegoException {
        if (entrada.length() < 2) {
            throw new JuegoException("Formato de coordenada incorrecto.");
        }

        char letraFila = entrada.charAt(0);
        if (letraFila < 'A' || letraFila > 'Z') {
            throw new JuegoException("La fila debe ser una letra (A-J).");
        }
        int fila = letraFila - 'A';

        String numColStr = entrada.substring(1).trim();
        int col;
        try {
            col = Integer.parseInt(numColStr) - 1;
        } catch (NumberFormatException e) {
            throw new JuegoException("La columna debe ser un número.");
        }

        return new int[]{fila, col};
    }

    private void guardarPartida() {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("buscaminas.dat"))) {
            out.writeObject(tablero);
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudo guardar la partida.");
        }
    }

    private void cargarPartida() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("buscaminas.dat"))) {

            this.tablero = (Tablero) in.readObject();
            vista.mostrarMensaje("Partida cargada. ¡A jugar!");
            esperarEnter();
            jugar();

        } catch (Exception e) {
            vista.mostrarMensaje("No existe ninguna partida guardada.");
            esperarEnter();
        }
    }

    private void esperarEnter() {
        System.out.println("Presiona ENTER para continuar...");
        scanner.nextLine();
    }
}
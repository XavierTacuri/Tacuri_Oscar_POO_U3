package ups.edu.modelo;

import java.io.Serializable;
import java.util.Random;

public class Tablero implements Serializable {

    private static final int TAMANO = 10;
    private static final int NUM_MINAS = 10;

    private Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[TAMANO][TAMANO];
        inicializarTablero();
        colocarMinas();
        calcularMinasVecinas();
    }

    private void inicializarTablero() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    private void colocarMinas() {
        Random rand = new Random();
        int minasColocadas = 0;
        while (minasColocadas < NUM_MINAS) {
            int fila = rand.nextInt(TAMANO);
            int col = rand.nextInt(TAMANO);
            if (!casillas[fila][col].esMina()) {
                casillas[fila][col].ponerMina();
                minasColocadas++;
            }
        }
    }

    private void calcularMinasVecinas() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (!casillas[i][j].esMina()) {
                    int minas = contarMinasAlrededor(i, j);
                    for (int k = 0; k < minas; k++) {
                        casillas[i][j].incrementarMinasVecinas();
                    }
                }
            }
        }
    }

    private int contarMinasAlrededor(int fila, int col) {
        int contador = 0;
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < TAMANO && j >= 0 && j < TAMANO) {
                    if (casillas[i][j].esMina()) {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }

    public int getTamano() {
        return TAMANO;
    }

    public Casilla getCasilla(int fila, int col) {
        return casillas[fila][col];
    }

    private void validarCoordenadas(int fila, int col) throws JuegoException {
        if (fila < 0 || fila >= TAMANO || col < 0 || col >= TAMANO) {
            throw new JuegoException("Coordenadas fuera de rango.");
        }
    }

    /**
     * Descubre una casilla.
     * @return true si se pisa una mina (pierde), false en caso contrario.
     */
    public boolean descubrirCasilla(int fila, int col)
            throws CasillaDescubierta, JuegoException {

        validarCoordenadas(fila, col);
        Casilla c = casillas[fila][col];

        if (c.estaDescubierta()) {
            throw new CasillaDescubierta("La casilla ya está descubierta.");
        }

        if (c.estaMarcada()) {
            throw new JuegoException("No puedes descubrir una casilla marcada.");
        }

        c.descubrir();

        if (c.esMina()) {
            // Pisó una mina
            return true;
        }

        // Si no hay minas alrededor, descubre en cascada las vecinas
        if (c.getMinasVecinas() == 0) {
            descubrirVecinos(fila, col);
        }

        return false;
    }

    private void descubrirVecinos(int fila, int col) throws JuegoException, CasillaDescubierta {
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < TAMANO && j >= 0 && j < TAMANO) {
                    if (i == fila && j == col) continue;
                    Casilla vecino = casillas[i][j];
                    if (!vecino.estaDescubierta() && !vecino.esMina() && !vecino.estaMarcada()) {
                        vecino.descubrir();
                        if (vecino.getMinasVecinas() == 0) {
                            descubrirVecinos(i, j);
                        }
                    }
                }
            }
        }
    }

    public void marcarCasilla(int fila, int col) throws JuegoException {
        validarCoordenadas(fila, col);
        Casilla c = casillas[fila][col];
        if (c.estaDescubierta()) {
            throw new JuegoException("No puedes marcar una casilla descubierta.");
        }
        c.alternarMarcada();
    }

    public boolean esVictoria() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                Casilla c = casillas[i][j];
                if (!c.esMina() && !c.estaDescubierta()) {
                    return false;
                }
            }
        }
        return true;
    }
}
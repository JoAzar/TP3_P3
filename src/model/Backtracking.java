package model;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {
    private Tablero tablero;
    private List<int[][]> soluciones;

    public Backtracking(Tablero tablero) {
        this.tablero = tablero;
        this.soluciones = new ArrayList<>();
    }

    public boolean resolverSudoku() {
        return resolver(0, 0);
    }

    private boolean resolver(int fila, int col) {
        if (fila == 9) return true;

        if (!tablero.celdaVacia(fila, col))
            return resolver(siguienteFila(fila, col), siguienteCol(col));

        for (int num = 1; num <= 9; num++) {
            if (numEsValido(fila, col, num)) {
                tablero.setValor(fila, col, num);

                if (resolver(siguienteFila(fila, col), siguienteCol(col)))
                    return true;

                tablero.setValor(fila, col, 0);
            }
        }

        return false;
    }

    public List<int[][]> resolverVarias(int limite) {
        soluciones.clear();
        resolverTodas(0, 0, limite);
        return soluciones;
    }

    private void resolverTodas(int fila, int col, int limite) {
        if (soluciones.size() >= limite) return;

        if (fila == 9) {
            soluciones.add(tablero.getTablero());
            return;
        }

        if (!tablero.celdaVacia(fila, col)) {
            resolverTodas(siguienteFila(fila, col), siguienteCol(col), limite);
            return;
        }

        for (int num = 1; num <= 9; num++) {
            if (numEsValido(fila, col, num)) {
                tablero.setValor(fila, col, num);

                resolverTodas(siguienteFila(fila, col), siguienteCol(col), limite);

                tablero.setValor(fila, col, 0);
            }
        }
    }

    public String verificarTableroValidoConMensaje() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = tablero.getValor(fila, col);

                if (valor == 0)
                    return "El tablero tiene celdas vacías";

                if (valorDuplicadoEnFila(fila, col, valor))
                    return "Hay un valor duplicado en la fila";

                if (valorDuplicadoEnColumna(fila, col, valor))
                    return "Hay un valor duplicado en la columna";

                if (valorDuplicadoEnCuadricula(fila, col, valor))
                    return "Hay un valor duplicado en la subcuadrícula";
            }
        }

        return "Tablero válido";
    }

    private boolean numEsValido(int fila, int col, int valor) {
        return !valorDuplicadoEnFila(fila, col, valor)
                && !valorDuplicadoEnColumna(fila, col, valor)
                && !valorDuplicadoEnCuadricula(fila, col, valor);
    }

    protected boolean valorDuplicadoEnFila(int fila, int col, int valor) {
        for (int c = 0; c < 9; c++)
            if (c != col && tablero.getValor(fila, c) == valor)
                return true;
        return false;
    }

    protected boolean valorDuplicadoEnColumna(int fila, int col, int valor) {
        for (int f = 0; f < 9; f++)
            if (f != fila && tablero.getValor(f, col) == valor)
                return true;
        return false;
    }

    private boolean valorDuplicadoEnCuadricula(int fila, int col, int valor) {
        int subFila = (fila / 3) * 3;
        int subCol = (col / 3) * 3;

        for (int f = subFila; f < subFila + 3; f++) {
            for (int c = subCol; c < subCol + 3; c++) {
                if ((f != fila || c != col) && tablero.getValor(f, c) == valor)
                    return true;
            }
        }
        return false;
    }

    private int siguienteFila(int fila, int col) {
        return (col == 8) ? fila + 1 : fila;
    }

    private int siguienteCol(int col) {
        return (col + 1) % 9;
    }

    public int[][] getTableroResuelto() {
        return tablero.getTablero();
    }
}

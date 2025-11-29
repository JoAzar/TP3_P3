package model;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {
    private Tablero _tablero;
    private List<int[][]> _soluciones;

    public Backtracking(Tablero tablero) {
        _tablero = tablero;
        _soluciones = new ArrayList<>();
    }

    public List<int[][]> resolverVarias(int limite) {
        _soluciones.clear();
        if(!verificarTableroValido()) return _soluciones;
        resolverHastaLimite(0, 0, limite);
        return _soluciones;
    }

    private void resolverHastaLimite(int fila, int col, int limite) {
    	
        if (_soluciones.size() >= limite) return;

        if (fila == 9) {
            _soluciones.add(_tablero.getTablero());
            return;
        }

        if (!_tablero.celdaVacia(fila, col)) {
            resolverHastaLimite(siguienteFila(fila, col), siguienteCol(col), limite);
            return;
        }

        for (int num = 1; num <= 9; num++) {
            if (numEsValido(fila, col, num)) {
                _tablero.setValor(fila, col, num);
                resolverHastaLimite(siguienteFila(fila, col), siguienteCol(col), limite);
                _tablero.setValor(fila, col, 0);
            }
        }
    }

    private boolean numEsValido(int fila, int col, int valor) {
        return !valorDuplicadoEnFila(fila, col, valor) && !valorDuplicadoEnColumna(fila, col, valor)
                && !valorDuplicadoEnCuadricula(fila, col, valor);
    }

    protected boolean valorDuplicadoEnFila(int fila, int col, int valor) {
        for (int c = 0; c < 9; c++)
            if (c != col && _tablero.getValor(fila, c) == valor)
                return true;
        return false;
    }

    protected boolean valorDuplicadoEnColumna(int fila, int col, int valor) {
        for (int f = 0; f < 9; f++)
            if (f != fila && _tablero.getValor(f, col) == valor)
                return true;
        return false;
    }

    private boolean valorDuplicadoEnCuadricula(int fila, int col, int valor) {
        int subFila = (fila / 3) * 3;
        int subCol = (col / 3) * 3;

        for (int f = subFila; f < subFila + 3; f++) {
            for (int c = subCol; c < subCol + 3; c++) {
                if ((f != fila || c != col) && _tablero.getValor(f, c) == valor)
                    return true;
            }
        }
        return false;
    }
    
    public String verificarTableroValidoConMensaje() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = _tablero.getValor(fila, col);

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
    
    public boolean verificarTableroValido() {
    	return !hayDuplicadoEnFila() && !hayDuplicadoEnColumna() && !hayDuplicadoEnCuadricula();
    }
    
    public boolean hayDuplicadoEnFila() {
        for (int fila = 0; fila < 9; fila++)
            if (existeValorRepetidoEnFila(fila)) return true;
        return false;
    }
    
    public boolean existeValorRepetidoEnFila(int fila) {
        boolean[] visto = new boolean[10];
        for (int col = 0; col < 9; col++) {
            int v = _tablero.getValor(fila, col);
            if (v != 0) {
                if (visto[v]) return true;
                visto[v] = true;
            }
        }
        return false;
    }

    public boolean hayDuplicadoEnColumna() {
        for (int col = 0; col < 9; col++)
            if (existeValorRepetidoEnColumna(col)) return true;
        return false;
    }
    
    public boolean existeValorRepetidoEnColumna(int columna) {
        boolean[] visto = new boolean[10];
        for (int fila = 0; fila < 9; fila++) {
            int v = _tablero.getValor(fila, columna);
            if (v != 0) {
                if (visto[v]) return true;
                visto[v] = true;
            }
        }
        return false;
    }
    
    public boolean hayDuplicadoEnCuadricula() {
        for (int subFila = 0; subFila < 9; subFila += 3) {
            for (int subCol = 0; subCol < 9; subCol += 3) {
                if (existeDuplicadoEnSubcuadricula(subFila, subCol))
                    return true;
            }
        }
        return false;
    }
    
    private boolean existeDuplicadoEnSubcuadricula(int subFila, int subCol) {
        boolean[] visto = new boolean[10];

        for (int f = subFila; f < subFila + 3; f++) {
            for (int c = subCol; c < subCol + 3; c++) {
                int v = _tablero.getValor(f, c);
                if (v != 0) {
                    if (visto[v]) return true;
                    visto[v] = true;
                }
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
        return _tablero.getTablero();
    }

  
}

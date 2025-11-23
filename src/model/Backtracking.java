package model;

public class Backtracking {
    private Tablero _tablero;

    public Backtracking(Tablero tablero) {
        this._tablero = tablero;
    }

    public boolean resolverSudoku() {
        if (!verificarTableroValido()) return false;
        return resolver(0, 0);
    }

    public int[][] getTableroResuelto() {
        return _tablero.getTablero();
    }
		
	protected boolean verificarTableroValido() {
		for(int fila = 0; fila < _tablero.length(); fila++) {
			for(int columna = 0; columna < _tablero.length(); columna++) {
				int valor = _tablero.getValor(fila, columna);
                if(valor != 0 && (valorDuplicadoEnFila(fila, columna, valor) || valorDuplicadoEnColumna(fila, columna, valor)))
                    return false;
			}
		}
		return true;
	}
	
	public String verificarTableroValidoConMensaje() {
		for(int fila = 0; fila < _tablero.length(); fila++) {
			for(int columna = 0; columna < _tablero.length(); columna++) {
	            int valor = _tablero.getValor(fila, columna);
	            if(valor == 0) return "El tablero tiene celdas vacías";
	            if(valorDuplicadoEnFila(fila, columna, valor) || valorDuplicadoEnColumna(fila, columna, valor)) return "El tablero no es válido, hay valores duplicados";
	        }
	    }
	    return "Tablero válido";
	}
	
	protected boolean valorDuplicadoEnFila(int fila, int columna, int valor) {
		for(int columnaAChequear = 0; columnaAChequear < _tablero.length(); columnaAChequear++)
			if(columnaAChequear != columna && _tablero.getValor(fila, columnaAChequear) == valor) return true;
		return false;
	}
	
	protected boolean valorDuplicadoEnColumna(int fila, int columna, int valor) {
		for(int filaAChequear = 0; filaAChequear < _tablero.length(); filaAChequear++)
			if(filaAChequear != fila && _tablero.getValor(filaAChequear, columna) == valor) return true;
		return false;
	}
	
	private boolean resolver(int fila, int columna) {
		if(fila == 9) return true;
		if(!_tablero.celdaVacia(fila, columna)) return resolver(siguienteFila(fila, columna), siguienteColumna(columna));
		for(int num = 1; num <= 9; num++) {
			if(numEsValido(fila, columna, num)) {
				_tablero.setValor(fila, columna, num);
				if(resolver(siguienteFila(fila, columna), siguienteColumna(columna))) return true;
				_tablero.setValor(fila, columna, 0);
			}
		}
		return false;
	}
	
	private int siguienteFila(int fila, int columna) {
		return (columna == 8) ? fila + 1 : fila;
	}
	
	private int siguienteColumna(int columna) {
		return (columna + 1) % 9;
	}
	
	private boolean numEsValido(int fila, int columna, int valor) {
		return !valorDuplicadoEnFila(fila, columna, valor) && !valorDuplicadoEnColumna(fila, columna, valor) && !valorDuplicadoEnCuadricula(fila, columna, valor);
	}
	
	protected boolean validarFila(int fila, int columna, int valor) {
		for(int c = 0; c < 9; c++) {
			if(c != columna && _tablero.getValor(fila, c) == valor)
				return true;
		}
		return false;
	}
	
	private boolean valorDuplicadoEnCuadricula(int fila, int col, int valor) {
        int subFila = (fila / 3) * 3;
        int subCol = (col / 3) * 3;
        for(int f = subFila; f < subFila + 3; f++) {
            for(int c = subCol; c < subCol + 3; c++)
                if((f != fila || c != col) && _tablero.getValor(f, c) == valor) return true;
        }
        return false;
    }
	
	public int[][] getTablero() {
	    return _tablero.getTablero();
	}
}

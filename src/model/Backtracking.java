package model;

public class Backtracking {
    private Tablero _tablero;

    public Backtracking(Tablero tablero) {
        this._tablero = tablero;
    }

    public boolean resolverSudoku() {
        if (!verificarTableroValido()) return false;
        return resolver();
    }

    public int[][] getTableroResuelto() {
        return _tablero.getTablero();
    }
		
	protected boolean verificarTableroValido() {
		for(int fila = 0; fila < _tablero.length(); fila++) {
			for(int columna = 0; columna < _tablero.length(); columna++) {
				int valor = _tablero.getValor(fila, columna);
                if(valor != 0) {
                	if(valorDuplicadoEnFila(fila, columna, valor) ||
                	   valorDuplicadoEnColumna(fila, columna, valor) ||
                	   valorDuplicadoEnCuadricula(fila, columna, valor)){
                		return false;
                	}
                }
                    
			}
		}
		return true;
	}
	
	public String verificarTableroValidoConMensaje() {
		for(int fila = 0; fila < _tablero.length(); fila++) {
			for(int columna = 0; columna < _tablero.length(); columna++) {
	            int valor = _tablero.getValor(fila, columna);
	            
	            if(valor!=0) {
	            	if(valorDuplicadoEnFila(fila, columna, valor) ||
	            		valorDuplicadoEnColumna(fila, columna, valor) ||
	            		valorDuplicadoEnCuadricula(fila, columna, valor)) {
	            	return "El tablero no es válido, hay valores duplicados";
	            	}
	            }
	        }
	    }
	    return "Tablero válido";
	}
	
	protected boolean valorDuplicadoEnFila(int fila, int columna, int valor) {
		for(int columnaAChequear = 0; columnaAChequear < _tablero.length(); columnaAChequear++)
			if(columnaAChequear != columna && _tablero.getValor(fila, columnaAChequear) == valor) return true;;
		return false;
	}
	
	protected boolean valorDuplicadoEnColumna(int fila, int columna, int valor) {
		for(int filaAChequear = 0; filaAChequear < _tablero.length(); filaAChequear++)
			if(filaAChequear != fila && _tablero.getValor(filaAChequear, columna) == valor) return true;
		return false;
	}
	
	private boolean resolver() {
		int[] siguiente = encontrarCeldaConMenosCandidatos();
        if (siguiente == null) return true; 

        int fila = siguiente[0];
        int col = siguiente[1];
        int[] candidatos = obtenerCandidatosArray(fila, col);

        if (candidatos.length == 0) return false; 

        for (int num : candidatos) {
            _tablero.setValor(fila, col, num);
            if (resolver()) return true;
            _tablero.setValor(fila, col, 0);
        }
        return false;
	}
	
	private int[] obtenerCandidatosArray(int fila, int col) {
		java.util.List<Integer> lista = new java.util.ArrayList<>();
        for (int v = 1; v <= 9; v++) {
            if (numEsValido(fila, col, v)) lista.add(v);
        }
        // convertir a array primitivo
        int[] arr = new int[lista.size()];
        for (int i = 0; i < lista.size(); i++) arr[i] = lista.get(i);
        return arr;
	}

	private int contarCandidatos(int fila, int col) {
		  int cnt = 0;
	        for (int v = 1; v <= 9; v++) {
	            if (numEsValido(fila, col, v)) cnt++;
	        }
	        return cnt;
	}

	private int[] encontrarCeldaConMenosCandidatos() {
		int minCount = Integer.MAX_VALUE;
        int bestFila = -1, bestCol = -1;
        for (int f = 0; f < _tablero.length(); f++) {
            for (int c = 0; c < _tablero.length(); c++) {
                if (_tablero.celdaVacia(f, c)) {
                    int count = contarCandidatos(f, c);
                    if (count == 0) return new int[]{f, c}; 
                    if (count < minCount) {
                        minCount = count;
                        bestFila = f;
                        bestCol = c;
                        if (minCount == 1) return new int[]{bestFila, bestCol}; 
                    }
                }
            }
        }
        if (bestFila == -1) return null;
        return new int[]{bestFila, bestCol};
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

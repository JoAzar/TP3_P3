package model;

public class Backtracking {
	private int _fila;
	private int _columna;
	private int[][] _tablero;
	private int _valorDeCelda;
	private int _subFila;
	private int _subColumna;
	
	public Backtracking(int[][] tablero) {
		_tablero = tablero;
	}
	
	public boolean resolverSudoku() {
		if(!verificarTableroValido()) {
			return false;
		}
		return resolver(0, 0);
	}
	
	private boolean resolver(int fila, int columna) {
		if(fila == 9) {
			return true;
		}
		
		if(_tablero[fila][columna] != 0) {
			return resolver(siguienteFila(fila, columna), siguienteColumna(columna));
		}
		
		for(int num = 1; num <= 9; num++) {
			if(numEsValido(fila, columna, num)) {
				_tablero[fila][columna] = num;
				
				if(resolver(siguienteFila(fila, columna), siguienteColumna(columna))) {
					return true;
				}
				
				_tablero[fila][columna] = 0;
			}
		}
		
		return false;
	}
	
	private int siguienteFila(int fila, int columna) {
		if(columna == 8) return fila + 1;
		else return fila;
	}
	
	private int siguienteColumna(int columna) {
		return (columna + 1) % 9;
	}
	
	private boolean numEsValido(int fila, int columna, int valor) {
		return !validarFila(fila, columna, valor) && !validarColumna(fila, columna, valor)
				&& !validarCuadricula(fila, columna, valor);
	}
	
	protected boolean validarFila(int fila, int columna, int valor) {
		for(int c = 0; c < 9; c++) {
			if(c != columna && _tablero[fila][c] == valor) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean validarColumna(int fila, int columna, int valor) {
		for(int f = 0; f < 9; f++) {
			if(f != fila && _tablero[f][columna] == valor) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean validarCuadricula(int fila, int columna, int valor) {
	    _subFila = (fila / 3)*3;
	    _subColumna = (columna / 3)*3;
		for(int f = _subFila; f < _subFila + 3; f++) {
			for(int c = _subColumna; c < _subColumna + 3; c++) {
				if((f != fila || c != columna) && _tablero[f][c] == valor) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean verificarTableroValido() {
		for(int fila = 0; fila < _tablero.length; fila++) {
			for(int columna = 0; columna < _tablero[0].length; columna++) {
				actualizarVariables(fila, columna);
				
				if(_valorDeCelda != 0 && (valorDuplicadoEnFila() || valorDuplicadoEnColumna()
						|| valorDuplicadoEnCuadricula())) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void actualizarVariables(int fila, int columna) {
	    _fila = fila;
	    _columna = columna;
	    _valorDeCelda = _tablero[fila][columna];
	    _subFila = (fila / 3)*3;
	    _subColumna = (columna / 3)*3;
	}
	
	protected boolean valorDuplicadoEnFila() {
		for(int numAChequear = 0; numAChequear < _tablero[0].length; numAChequear++) {
			if(existeValorRepetidoEnFila(numAChequear)) return true;
		}
		return false;
	}
	
	protected boolean valorDuplicadoEnColumna() {
		for(int numAChequear = 0; numAChequear < _tablero.length; numAChequear++) {
			if(existeValorRepetidoEnColumna(numAChequear)) return true;
		}
		return false;
	}
	
	protected boolean valorDuplicadoEnCuadricula() {
		for(int i = _subFila; i < _subFila + 3; i++) {
			for(int j = _subColumna; j < _subColumna + 3; j++) {
				if(existeValorRepetidoSubfilasOSubcolumnas(i, j)) return true;
			}
		}
		return false;
	}
	
	protected boolean existeValorRepetidoEnFila(int valorAChequear) {
		return valorAChequear != _columna
			&& _tablero[_fila][valorAChequear] == _valorDeCelda;
	}
	
	protected boolean existeValorRepetidoEnColumna(int valorAChequear) {
		return valorAChequear != _fila && _tablero[valorAChequear][_columna] == _valorDeCelda;
	}
	
	protected boolean existeValorRepetidoSubfilasOSubcolumnas(int i, int j) {
		return (i != _fila || j != _columna) && _tablero[i][j] == _valorDeCelda;
	}
	
}

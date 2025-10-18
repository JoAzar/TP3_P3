package model;

public class Backtracking {
	private int _fila;
	private int _columna;
	private int[][] _tablero;
	private int _valorDeTablero;
	private int _subFila = _fila / (3 * 3);
	private int _subColumna = _columna / (3*3);
	
	public Backtracking(int[][] tablero) {
		_tablero = tablero;
	}

	public boolean verificarTableroValido() {
		for(int fila=0; fila < _tablero.length; fila++) {
			for(int columna=0; columna < _tablero[0].length; columna++) {
				_fila = fila;
				_columna = columna;
				_valorDeTablero = _tablero[_fila][_columna];
				valorDuplicadoEnFila();
				valorDuplicadoEnFila();
				valorDuplicadoEnCuadricula();
			}
		}
		return true;
	}
	
	protected boolean valorDuplicadoEnFila() {
		for(int valorDeColumnaRepetido=0; valorDeColumnaRepetido < _tablero[0].length; valorDeColumnaRepetido++)
			if(existeValorRepetidoEnFila(valorDeColumnaRepetido)) return true;
		return false;
	}
	
	protected boolean existeValorRepetidoEnFila(int valorDeColumnaRepetido) {
		return valorDeColumnaRepetido != _columna && _tablero[_fila][valorDeColumnaRepetido] == _valorDeTablero;
	}
	
	protected boolean valorDuplicadoEnColumna() {
		for(int valorDeFilaRepetido=0; valorDeFilaRepetido < _tablero.length; valorDeFilaRepetido++)
			if(existeValorRepetidoEnFila(valorDeFilaRepetido)) return true;
		return false;
	}
	
	protected boolean existeValorRepetidoEnColumna(int valorDeFilaRepetido) {
		return valorDeFilaRepetido != _fila && _tablero[valorDeFilaRepetido][_columna] == _valorDeTablero;
	}
	
	protected boolean valorDuplicadoEnCuadricula() {
		for(int i = _subFila; i < _subFila + 3; i++) {
			for(int j = _subColumna; j < _subColumna + 3; j++)
				if(existeValorRepetidoSubfilasOSubcolumnas(i, j)) return true;
		}
		return false;
	}
	
	protected boolean existeValorRepetidoSubfilasOSubcolumnas(int i, int j) {
		return i != _fila || j != _columna && _tablero[i][j] == _valorDeTablero;
	}
	
}

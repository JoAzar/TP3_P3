package model;

public class Tablero {
	private int _tamanioDeCuadricula;
	private int[][] _tablero;

	
	public Tablero(int tamanio) {
		_tamanioDeCuadricula = tamanio;
		_tablero = new int[tamanio][tamanio];
	}
	
	public boolean celdaVacia(int fila, int columna) {
		return _tablero[fila][columna] == 0;
	}
	
	public int getValor(int fila, int columna) {
		return _tablero[fila][columna];
	}
	
	public void setValor(int fila, int columna, int valor) {
		validarDatos(fila, columna, valor);
		_tablero[fila][columna] = valor;
	}
	
	protected void validarDatos(int fila, int columna, int valor) {
		if(fila < 0 || fila > 8) {
			throw new IllegalArgumentException("Fila: " + fila +" fuera de rango (1-9)");
		}
		if(columna < 0 || columna > 8) {
			throw new IllegalArgumentException("Columna: " + columna +" fuera de rango (1-9)");
		}
		if(valor < 0 || valor > 9) {
			throw new IllegalArgumentException("Valor: " + valor +" fuera de rango (1-9)");
		}
	}
	
	public int length() {
		return _tablero.length;
	}
	
	public void vaciar() {
		_tablero = new int[_tamanioDeCuadricula][_tamanioDeCuadricula];
	}
	
	public Tablero getTablero(){
		Tablero copia = new Tablero(9);
		for(int i = 0; i < _tablero.length; i++) {
			for(int j = 0; j < _tablero[i].length; j++) {
				copia.setValor(i, j, _tablero[i][j]);
			}
		}
		return copia;
	}
	
}

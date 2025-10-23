package model;

import java.util.Random;

public class Tablero {
	private int _tamanioDeCuadricula = 9;
	private int _tamanioDelTablero = _tamanioDeCuadricula*_tamanioDeCuadricula;
	private int[][] _tablero = new int[_tamanioDeCuadricula][_tamanioDeCuadricula];
	Random _random = new Random();
	
	public void generarTableroAleatorioDeValoresIngresados(int cantValores) {
		verificarCantidadDeValoresIngresadosPorUsuario(cantValores);
		int valoresAsignados = 0;
		
		while(valoresAsignados < cantValores) {
			int fila = crearValorRandom();
			int columna = crearValorRandom();
			
			if(celdaVacia(fila, columna)) {
				int valorRandomDistintoDeCero = crearValorRandom() + 1;
				_tablero[fila][columna] = valorRandomDistintoDeCero;
				valoresAsignados++;
			}
		}
	}
	
	private void verificarCantidadDeValoresIngresadosPorUsuario(int cantValores) {
		if(cantValores < 1 || cantValores > _tamanioDelTablero) {
			throw new IllegalArgumentException("Cantidad fuera de rango (1-81)");
		}
	}
	
	private int crearValorRandom() {
		return _random.nextInt(_tamanioDeCuadricula);
	}
	
	private boolean celdaVacia(int fila, int columna) {
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
		if(valor < 1 || valor > 9) {
			throw new IllegalArgumentException("Valor: " + valor +" fuera de rango (1-9)");
		}
	}
	
	public void vaciar() {
		_tablero = new int[_tamanioDeCuadricula][_tamanioDeCuadricula];
	}
	
	public int[][] getTablero(){
		int[][] copia = new int[_tablero.length][_tablero[0].length];
		for(int i = 0; i < _tablero.length; i++) {
			for(int j = 0; j < _tablero[i].length; j++) {
				copia[i][j] = _tablero[i][j];
			}
		}
		return copia;
	}
	
}

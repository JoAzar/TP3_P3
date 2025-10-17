package model;
import java.util.Random;

public class Tablero {
	private int tamanioDeCuadricula = 9;
	private int tamanioDelTablero = tamanioDeCuadricula*tamanioDeCuadricula;
	private int[][] _tablero = new int[tamanioDeCuadricula][tamanioDeCuadricula];
	Random random = new Random();
	
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
		if(cantValores < 1 || cantValores > tamanioDelTablero) throw new IllegalArgumentException("Cantidad fuera de rango (1-81)");
	}
	
	private int crearValorRandom() {
		return random.nextInt(tamanioDeCuadricula);
	}
	
	private boolean celdaVacia(int fila, int columna) {
		return _tablero[fila][columna] == 0;
	}
	
	
}

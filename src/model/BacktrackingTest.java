package model;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BacktrackingTest {
	int[][] tablero = new int[9][9];
	Backtracking backtracking = new Backtracking(tablero);
	
	
	@Test
	public void existeValorDuplicadoEnFila() {
		tablero[0][0] = 5;
		tablero[0][1] = 5;
		assertTrue(backtracking.valorDuplicadoEnFila());
	}
	
	@Test
	public void existeValorDuplicadoEnColumna() {
		tablero[0][0] = 3;
		tablero[1][0] = 3;
		assertTrue(backtracking.valorDuplicadoEnColumna());
	}
	
	@Test
	public void existeValorRepetidoEnFila() {
		tablero[0][0] = 2;
		tablero[1][0] = 3;
		tablero[2][0] = 2;
		assertTrue(backtracking.existeValorRepetidoEnFila(2));
	}
	
	@Test
	public void existeValorRepetidoEnColumna() {
		tablero[0][0] = 2;
		tablero[0][1] = 1;
		tablero[0][2] = 2;
		assertTrue(backtracking.existeValorRepetidoEnColumna(2));
	}
	
	@Test
	public void tableroValido() {
		assertTrue(backtracking.verificarTableroValido());
	}
}

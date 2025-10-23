package model;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BacktrackingTest {
	int[][] _tablero = new int[9][9];
	Backtracking _backtracking = new Backtracking(_tablero);
	
	
	@Test
	public void existeValorDuplicadoEnFila() {
		_tablero[0][0] = 5;
		_tablero[0][1] = 5;
		assertTrue(_backtracking.valorDuplicadoEnFila());
	}
	
	@Test
	public void existeValorDuplicadoEnColumna() {
		_tablero[0][0] = 3;
		_tablero[1][0] = 3;
		assertTrue(_backtracking.valorDuplicadoEnColumna());
	}
	
	@Test
	public void existeValorRepetidoEnFila() {
		_tablero[0][0] = 2;
		_tablero[1][0] = 3;
		_tablero[2][0] = 2;
		assertTrue(_backtracking.existeValorRepetidoEnFila(2));
	}
	
	@Test
	public void existeValorRepetidoEnColumna() {
		_tablero[0][0] = 2;
		_tablero[0][1] = 1;
		_tablero[0][2] = 2;
		assertTrue(_backtracking.existeValorRepetidoEnColumna(2));
	}
	
	@Test
	public void tableroValido() {
		assertTrue(_backtracking.verificarTableroValido());
	}
	
	@Test
	public void validarFila() {
	    _tablero[0][0] = 5;
	    _tablero[0][1] = 5;
	    assertTrue(_backtracking.validarFila(0, 1, 5));
	    assertFalse(_backtracking.validarFila(0, 2, 3));
	}

	@Test
	public void validarColumna() {
	    _tablero[0][0] = 7;
	    _tablero[1][0] = 7;
	    assertTrue(_backtracking.validarColumna(1, 0, 7));
	    assertFalse(_backtracking.validarColumna(2, 0, 5));
	}

	@Test
	public void validarCuadricula() {
	    _tablero[0][0] = 9;
	    _tablero[1][1] = 9;
	    assertTrue(_backtracking.validarCuadricula(0, 1, 9));
	    assertFalse(_backtracking.validarCuadricula(2, 2, 1));
	}
	
	
    @Test
    public void resolverTableroVacio() {
        boolean exito = _backtracking.resolverSudoku();
        assertTrue(exito);
        assertTrue(_backtracking.verificarTableroValido());
    }

    @Test
    public void resolverTableroParcial() {
        _tablero[0][0] = 5;
        _tablero[0][1] = 3;
        _tablero[1][0] = 6;
        _tablero[4][4] = 7;
        _tablero[8][8] = 9;

        _backtracking = new Backtracking(_tablero);

        boolean exito = _backtracking.resolverSudoku();
        assertTrue(exito);
        assertTrue(_backtracking.verificarTableroValido());
    }

    @Test
    public void tableroSinSolucion() {
        _tablero[0][0] = 5;
        _tablero[0][1] = 5;
        _backtracking = new Backtracking(_tablero);

        boolean exito = _backtracking.resolverSudoku();
        assertFalse(exito);
    }
    
}

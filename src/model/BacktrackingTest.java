package model;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import java.util.List;

public class BacktrackingTest {
	Tablero _tablero = new Tablero(9);
	Backtracking _backtracking = new Backtracking(_tablero);
	

    @Test
    public void noHayDuplicadosEnTableroVacioTest() {
        assertTrue(_backtracking.verificarTableroValido());
    }
	
	@Test
	public void existeDuplicadoEnFilaTest() {
		_tablero.setValor(0, 0, 5);
		_tablero.setValor(0, 1, 5);
		
		assertTrue(_backtracking.hayDuplicadoEnFila());
		assertFalse(_backtracking.verificarTableroValido());
	}
	
	@Test
	public void existeDuplicadoEnColumnaTest() {
		_tablero.setValor(0, 0, 3);
		_tablero.setValor(1, 0, 3);
		
		assertTrue(_backtracking.hayDuplicadoEnColumna());
		assertFalse(_backtracking.verificarTableroValido());
	}
	
    @Test
    public void duplicadoEnSubcuadriculaTest() {
        _tablero.setValor(1, 1, 3);
        _tablero.setValor(2, 0, 3);

        assertTrue(_backtracking.hayDuplicadoEnCuadricula());
        assertFalse(_backtracking.verificarTableroValido());
    }
	
	@Test
	public void tableroValido() {
		assertTrue(_backtracking.verificarTableroValido());
	}
	
    @Test
    public void verificarTableroValidoConMensajeTest() {
        _tablero.setValor(0, 0, 4);
        _tablero.setValor(0, 2, 4);

        assertEquals("Hay un valor duplicado en la fila", _backtracking.verificarTableroValidoConMensaje());
    }

    @Test
    public void resolverVariasEncuentraUnaSolucionTest() {
        crearSudokuSimple(_tablero);
        List<int[][]> soluciones = _backtracking.resolverVarias(1);

        assertEquals(1, soluciones.size());
    }

    @Test
    public void resolverVariasDevuelveSolucionesDistintasTest() {
        crearSudokuSimple(_tablero);
        List<int[][]> soluciones = _backtracking.resolverVarias(2);

        assertNotSame(soluciones.get(0), soluciones.get(1));
    }

    @Test
    public void resolverNoModificaTableroOriginalTest() {
        crearSudokuSimple(_tablero);
        int[][] copiaOriginal = _tablero.getTablero();

        _backtracking.resolverVarias(1);

        assertArrayEquals(copiaOriginal, _tablero.getTablero());
    }

    private void crearSudokuSimple(Tablero t) {
        int[][] sudoku = {
            {0,0,0, 0,0,0, 0,0,0},
            {0,0,0, 0,0,0, 0,0,0},
            {0,0,0, 0,0,0, 0,0,0},

            {0,0,0, 0,0,0, 0,0,0},
            {0,0,0, 5,0,0, 0,0,0},
            {0,0,0, 0,0,0, 0,0,0},

            {0,0,0, 0,0,0, 0,0,0},
            {0,0,0, 0,0,0, 0,0,0},
            {0,0,0, 0,0,0, 0,0,0},
        };

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                t.setValor(i, j, sudoku[i][j]);
    }
}

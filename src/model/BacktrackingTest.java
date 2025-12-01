package model;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
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
        _tablero.setValor(0, 0, 4);
        _tablero.setValor(1, 3, 7);
        _tablero.setValor(4, 4, 9);
		assertTrue(_backtracking.verificarTableroValido());
	}

    @Test
    public void resolverVariasDevuelveSolucionesDistintasTest() {
        crearSudokuSimple(_tablero);
        
        List<Tablero> soluciones = _backtracking.resolverVarias();
        
        assertTrue(soluciones.size() >= 2);
        
        Tablero sol1 = soluciones.get(0);
        Tablero sol2 = soluciones.get(1);
        
        boolean sonDistintas = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sol1.getValor(i, j) != sol2.getValor(i, j)) {
                    sonDistintas = true;
                }
            }
        }
        
        assertTrue(sonDistintas);
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

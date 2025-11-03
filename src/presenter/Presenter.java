package presenter;

import model.Tablero;
import model.Backtracking;
import view.View;
import view.ViewListener;
import javax.swing.JTextField;

public class Presenter implements ViewListener {
    private View _vista;
    private Tablero _tablero;
    private Backtracking _backtracking;

    public void inicializarInterfazPresenter(View vista) {
        _vista = vista;
        _tablero = new Tablero(9);
        _vista.crearListener(this);
    }

	@Override
	public boolean resolverSudoku(JTextField[][] celdas) {
		actualizarTableroPresenterPor(celdas);
		
		if(resolver()) {
			_vista.mostrarTablero(_backtracking.getTablero());
			return true;
		}
		else {
			return false;
		}
	}
	
	private void actualizarTableroPresenterPor(JTextField[][] celdas) {
		for(int f = 0; f < celdas.length; f++) {
			for(int c = 0; c < celdas.length; c++) {
				String num = celdas[f][c].getText();
				
				if(!num.isEmpty()) {
					_tablero.setValor(f, c, Integer.parseInt(num));
				}
				else {
					_tablero.setValor(f, c, 0);
				}
			}
		}
	}

    private boolean resolver() {
        _backtracking = new Backtracking(_tablero);
        return _backtracking.resolverSudoku();
    }
    
    @Override
    public void crearSudokuAleatorioConPistas(int cantPistas) {
        _tablero.vaciar();
        
        resolver();
        int[][] tableroResuelto = _backtracking.getTablero();
        int celdasAEliminar = 81 - cantPistas;

        java.util.Random random = new java.util.Random();
        while (celdasAEliminar > 0) {
            int fila = random.nextInt(9);
            int columna = random.nextInt(9);

            if (tableroResuelto[fila][columna] != 0) {
                tableroResuelto[fila][columna] = 0;
                celdasAEliminar--;
            }
        }

        _vista.mostrarTablero(tableroResuelto);
        _vista.mostrarMensaje("Sudoku generado con " + cantPistas + " pistas.");
    }
    
    @Override
    public void mostrarSolucion() {
        resolver();
        _vista.mostrarTablero(_backtracking.getTablero());
    }

}
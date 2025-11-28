package presenter;
import model.Tablero;
import model.Backtracking;
import view.View;
import view.ViewListener;
import javax.swing.JTextField;

public class Presenter implements ViewListener {
    private View _vista;
    private Tablero _tablero;

    public void inicializarInterfazPresenter(View vista) {
        _vista = vista;
        _tablero = new Tablero(9);
        _vista.crearListener(this);
    }
    
    @Override
    public String verificarValidezDelTableroConMensaje(JTextField[][] celdas) {
    	actualizarTableroPresenterPor(celdas);
    	Backtracking backtracking = new Backtracking(_tablero);
    	return backtracking.verificarTableroValidoConMensaje();
    }
	
	@Override
	public boolean encontrarSolucionesSudoku(JTextField[][] celdas) {
	    actualizarTableroPresenterPor(celdas);
	    
	    Backtracking backtracking = new Backtracking(_tablero);
	    if(!backtracking.verificarTableroValido()) {
	    	_vista.mostrarMensaje("El sudoku ingresado es inválido");
	    	return false;
	    }
	    
	    var soluciones = backtracking.resolverVarias(50);

        if (soluciones.isEmpty()) {
        	_vista.mostrarMensaje("No se encontraron soluciones.");
            return false;
        }

	    _vista.mostrarListaDeSoluciones(soluciones);
	    return true;
	}
	
	private void actualizarTableroPresenterPor(JTextField[][] celdas) {	
		for(int fila = 0; fila < celdas.length; fila++) {
			for(int columna = 0; columna < celdas.length; columna++) {
				String num = celdas[fila][columna].getText();
	            _tablero.setValor(fila, columna, num.isEmpty() ? 0 : Integer.parseInt(num));
			}
		}
	}
	
    @Override
    public void mostrarSolucion() {
        Backtracking backtracking = new Backtracking(_tablero);
        var soluciones = backtracking.resolverVarias(50);

        if (soluciones.isEmpty()) {
        	_vista.mostrarMensaje("No hay soluciones.");
            return;
        }

        _vista.mostrarListaDeSoluciones(soluciones);
    }
    
    @Override
    public void crearSudokuAleatorioConPistas(int cantPistas) {
        _tablero.vaciar();
        _tablero.generarTableroAleatorioDeValoresIngresados(cantPistas);
        _vista.mostrarTablero(_tablero.getTablero());
        _vista.mostrarMensaje("Sudoku generado con " + cantPistas + " pistas.");
    }

}
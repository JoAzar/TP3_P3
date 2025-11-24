package presenter;
import model.Tablero;
import model.Backtracking;
import view.View;
import view.ViewListener;

import javax.swing.JOptionPane;
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
	public boolean resolverSudoku(JTextField[][] celdas) {
		actualizarTableroPresenterPor(celdas);
		Backtracking backtracking = new Backtracking(_tablero);
		boolean resolucionDeExito = backtracking.resolverSudoku();
			if(resolucionDeExito) _vista.mostrarTablero(backtracking.getTableroResuelto());
		return resolucionDeExito;
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
    public void crearSudokuAleatorioConPistas(int cantPistas) {
        _tablero.vaciar();
        _tablero.generarTableroAleatorioDeValoresIngresados(cantPistas);
        _vista.mostrarTablero(_tablero.getTablero());
        _vista.mostrarMensaje("Sudoku generado con " + cantPistas + " pistas.");
    }
    
    @Override
    public void mostrarSolucion() {
    	String input = JOptionPane.showInputDialog(null, "¿Cuántas soluciones querés ver?");
        if (input == null) return;
        
        if (!input.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Número inválido");
            return;
        }
        int cantidad= Integer.parseInt(input);
        
        Backtracking backtracking = new Backtracking(_tablero);
        var soluciones = backtracking.resolverVarias(cantidad);

        if (soluciones.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay soluciones.");
            return;
        }

        _vista.mostrarListaDeSoluciones(soluciones);
    }

}
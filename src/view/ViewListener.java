package view;

import javax.swing.JTextField;

public interface ViewListener {
	boolean resolverSudoku(JTextField[][] celdas);
	void crearSudokuAleatorioConPistas(int cantPistas);
	public void mostrarSolucion();

}

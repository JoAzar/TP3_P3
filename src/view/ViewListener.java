package view;

import javax.swing.JTextField;

public interface ViewListener {
	String verificarValidezDelTableroConMensaje(JTextField[][] celdas);
	boolean encontrarSolucionesSudoku(JTextField[][] celdas);
	void crearSudokuAleatorioConPistas(int cantPistas);
	public void mostrarSolucion();
}

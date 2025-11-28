package view;

import javax.swing.JTextField;

public interface ViewListener {
	boolean encontrarSolucionesSudoku(JTextField[][] celdas);
	void crearSudokuAleatorioConPistas(int cantPistas);
	public void mostrarSolucion();
	String verificarValidezDelTableroConMensaje(JTextField[][] celdas);
}

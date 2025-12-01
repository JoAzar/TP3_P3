package presenter;

import model.Tablero;
import model.Backtracking;
import view.View;
import view.ViewListener;
import java.util.Random;
import javax.swing.JTextField;

public class Presenter implements ViewListener {
    private View _vista;
    private Tablero _tablero;
	Random _random = new Random();

    public void inicializarInterfazPresenter(View vista) {
        _vista = vista;
        _tablero = new Tablero(9);
        _vista.crearListener(this);
    }
    
    @Override
    public String verificarValidezDelTableroConMensaje(JTextField[][] celdas) {
    	if(!actualizarTableroPresenterPor(celdas)) {
    		return "Solo se pueden ingresar números entre 1 y 9";
    	}
    	return verificarTableroValidoConMensaje();
    }
    
	private boolean actualizarTableroPresenterPor(JTextField[][] celdas) {	
		for(int fila = 0; fila < celdas.length; fila++) {
			for(int columna = 0; columna < celdas.length; columna++) {
				String texto = celdas[fila][columna].getText().trim();
				
				if(texto.isEmpty()) {
					_tablero.setValor(fila, columna, 0);
					continue;
				}
				if(texto.matches("[1-9]")) {
					_tablero.setValor(fila, columna, Integer.parseInt(texto));
				}
				else {
					_vista.mostrarMensaje("Solo se permiten números entre 1-9");
					return false;
				}
			}
		}
		return true;
	}
    
    public String verificarTableroValidoConMensaje() {
    	Backtracking backtracking = new Backtracking(_tablero);
    	
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = _tablero.getValor(fila, col);

                if (valor == 0)
                    return "El tablero tiene celdas vacías";

                if (backtracking.valorDuplicadoEnFila(fila, col, valor))
                    return "Hay un valor duplicado en la fila";

                if (backtracking.valorDuplicadoEnColumna(fila, col, valor))
                    return "Hay un valor duplicado en la columna";

                if (backtracking.valorDuplicadoEnCuadricula(fila, col, valor))
                    return "Hay un valor duplicado en la subcuadrícula";
            }
        }

        return "Tablero válido";
    }
	
	@Override
	public boolean encontrarSolucionesSudoku(JTextField[][] celdas) {
	    if(!actualizarTableroPresenterPor(celdas)) {
	    	return false;
	    }
	    
	    Backtracking backtracking = new Backtracking(_tablero);
	    if(!backtracking.verificarTableroValido()) {
	    	_vista.mostrarMensaje("El sudoku ingresado es inválido");
	    	return false;
	    }
	    
	    var soluciones = backtracking.resolverVarias();

        if (soluciones.isEmpty()) {
        	_vista.mostrarMensaje("No se encontraron soluciones.");
            return false;
        }

	    _vista.mostrarListaDeSoluciones(soluciones);
	    return true;
	}
	
    @Override
    public void mostrarSolucion() {
        Backtracking backtracking = new Backtracking(_tablero);
        var soluciones = backtracking.resolverVarias();

        if (soluciones.isEmpty()) {
        	_vista.mostrarMensaje("No hay soluciones.");
            return;
        }

        _vista.mostrarListaDeSoluciones(soluciones);
    }
    
    @Override
    public void crearSudokuAleatorioConPistas(int cantPistas) {
    	boolean generado = false;
        
        while(!generado) {
            _tablero.vaciar();
            generarTableroAleatorioDeValoresIngresados(cantPistas);
            
            Backtracking backtracking = new Backtracking(_tablero);
            
            if(backtracking.verificarTableroValido() && !(backtracking.resolverVarias().size() == 0)) {
            	generado = true;
            	
            }
        }
        _vista.mostrarTablero(_tablero.getTablero());
        _vista.mostrarMensaje("Sudoku generado con " + cantPistas + " pistas.");
    }
    
	public void generarTableroAleatorioDeValoresIngresados(int cantValores) {
		verificarValor(cantValores);
		int valoresAsignados = 0;
		
		while(valoresAsignados < cantValores) {
			int fila = crearValorRandom();
			int columna = crearValorRandom();
			
			if(_tablero.celdaVacia(fila, columna)) {
				int valorRandomDistintoDeCero = crearValorRandom() + 1;
				_tablero.setValor(fila, columna, valorRandomDistintoDeCero);
				valoresAsignados++;
			}
		}
	}
	
	private void verificarValor(int cantValores) {
		if(cantValores < 1 || cantValores > 40) {
			throw new IllegalArgumentException("Cantidad fuera de rango (1-40)");
		}
	}
	
	private int crearValorRandom() {
		return _random.nextInt(_tablero.length());
	}

}
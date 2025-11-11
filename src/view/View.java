package view;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.text.NumberFormat;

public class View extends JFrame{
	private JPanel _panelPrincipal;
	private JPanel _contenedorBotones;
	private JPanel _panelMenu;
    private JPanel _panelTablero;
    private JPanel _panelBtnManual;
    private JPanel _panelDeOpcionDeTableroAleatorio;
	private ViewListener _listener;
    private JTextField[][] _celdas = new JTextField[9][9];
    
    public View() {
		initialize();
    }
    
    private void initialize() {
        setTitle("Resolución de Sudokus");
		setBounds(100, 100, 600, 560); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        _panelPrincipal = new JPanel(new CardLayout());
        getContentPane().add(_panelPrincipal);
        
        crearMenuPrincipal();
        crearTablero();
        mostrarPantalla("menu");
        
        setVisible(true);
    }
    
    private void crearMenuPrincipal() {
    	_panelMenu = new JPanel();
    	_panelMenu.setLayout(null);
    	
        JTextPane txtMenu = new JTextPane();
        txtMenu.setEditable(false);
        txtMenu.setBounds(143, 138, 311, 47);
        txtMenu.setBackground(UIManager.getColor("Button.background"));
        txtMenu.setFont(new Font("Arial Black", Font.BOLD, 30));
        txtMenu.setText("Sudoku Game");
        _panelMenu.add(txtMenu);
        
        JButton btnTableroVacio = new JButton("Tablero vacío");
        btnTableroVacio.setBounds(207, 261, 173, 47);
        btnTableroVacio.setFont(new Font("Arial", Font.BOLD, 14));
        btnTableroVacio.addActionListener(e -> {
        	setModoAleatorio(false);
        	mostrarPantalla("tablero");
        });
        _panelMenu.add(btnTableroVacio);
        JButton btnSudokuRandom = new JButton("Sudoku aleatorio");
        btnSudokuRandom.setBounds(193, 342, 202, 54);
        btnSudokuRandom.setFont(new Font("Arial", Font.BOLD, 14));
        btnSudokuRandom.addActionListener(e -> {
        	String input = JOptionPane.showInputDialog(this, "Ingrese la cantidad de pistas (1 a 81):");
        	
        	if(input != null && !input.isEmpty()) {
        		try {
        			int cantidadDePistas = Integer.parseInt(input);
        			if(cantidadDePistas < 1 || cantidadDePistas > 81) {
        				mostrarMensaje("Debe ingresar un número válido");
        			}else{
        				_listener.crearSudokuAleatorioConPistas(cantidadDePistas);
            			_listener.verificarValidezDelTableroConMensaje();
            			setModoAleatorio(true);
            			mostrarPantalla("tablero");
        			}
        			
        		} catch (NumberFormatException ex) {
        			mostrarMensaje("Debe ingresar un número válido");
                }
        	}
        });
        _panelMenu.add(btnSudokuRandom);
        _panelPrincipal.add(_panelMenu, "menu");
    }
    
    private void crearTablero() {
    	_panelTablero = new JPanel();
    	_panelTablero.setLayout(new BorderLayout());
    	_panelTablero.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    	
    	JPanel panelCentro = new JPanel(new GridLayout(9, 9));
    	panelCentro.setPreferredSize(new Dimension(450, 450));
    	panelCentro.setBackground(Color.BLACK);
    	
    	NumberFormat formato = NumberFormat.getIntegerInstance();
    	NumberFormatter formatter = new NumberFormatter(formato);
    	formatter.setValueClass(Integer.class);
    	formatter.setMinimum(1);
    	formatter.setMaximum(9);
    	formatter.setAllowsInvalid(true);

    	for(int fila = 0; fila < 9; fila++) {
    	    for(int columna = 0; columna < 9; columna++) {
    	        JFormattedTextField celda = new JFormattedTextField(formatter);
    	        celda.setHorizontalAlignment(JTextField.CENTER);
    	        celda.setFont(new Font("Arial", Font.BOLD, 18));
    	        int arriba = (fila % 3 == 0) ? 3 : 1;
    	        int izq = (columna % 3 == 0) ? 3 : 1;
    	        int abajo = (fila == 8) ? 3 : 1;
    	        int der = (columna == 8) ? 3 : 1;
    	        celda.setBorder(BorderFactory.createMatteBorder(arriba, izq, abajo, der, Color.BLACK));
    	        _celdas[fila][columna] = celda;
    	        panelCentro.add(celda);
    	    }
    	}
    	
    	_panelBtnManual = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    	JButton btnResolver = new JButton("Resolver tablero");
    	btnResolver.setFont(new Font("Arial", Font.BOLD, 12));
    	
    	btnResolver.addActionListener(e -> {
    		String mensaje = _listener.verificarValidezDelTableroConMensaje();
    		if(mensaje == "Tablero válido") {
    			if(_listener.resolverSudoku(_celdas))
    	            mensaje = _listener.verificarValidezDelTableroConMensaje();
            msjDelResultadoDeLaVerificacionDelTablero(mensaje);
    		}
    	});
    	
    	JButton btnVerificar = new JButton("Verificar tablero");
    	btnVerificar.setFont(new Font("Arial", Font.BOLD, 12));
    	btnVerificar.addActionListener(e -> {
    	});

    	JButton manualBtnVolver = new JButton("Menú principal");
    	manualBtnVolver.setFont(new Font("Arial", Font.BOLD, 12));
    	manualBtnVolver.addActionListener(e -> {
    		limpiarTablero();
    		mostrarPantalla("menu");
    	});
    	
    	_panelBtnManual.add(btnResolver);
    	_panelBtnManual.add(manualBtnVolver);    	
    	_panelDeOpcionDeTableroAleatorio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    	JButton btnMostrarSolucion = new JButton("Mostrar solución");
    	btnMostrarSolucion.addActionListener(e -> _listener.mostrarSolucion());
    	
    	JButton randomBtnVolver = new JButton("Menú principal");
    	randomBtnVolver.setFont(new Font("Arial", Font.BOLD, 12));
    	randomBtnVolver.addActionListener(e -> {
    		limpiarTablero();
    		mostrarPantalla("menu");
    	});
    	
    	JButton botonParaVerificarTablero = new JButton("Verificar el tablero");
        botonParaVerificarTablero.addActionListener(e -> {
	      String mensaje = _listener.verificarValidezDelTableroConMensaje();
	        msjDelResultadoDeLaVerificacionDelTablero(mensaje);
        });
    	
    	_panelDeOpcionDeTableroAleatorio.add(btnMostrarSolucion);
    	_panelDeOpcionDeTableroAleatorio.add(randomBtnVolver);
    	_panelDeOpcionDeTableroAleatorio.add(botonParaVerificarTablero);

        _contenedorBotones = new JPanel(new CardLayout());
        _contenedorBotones.add(_panelBtnManual, "manual");
        _contenedorBotones.add(_panelDeOpcionDeTableroAleatorio, "aleatorio");
        
    	_panelTablero.add(panelCentro, BorderLayout.CENTER);
    	_panelTablero.add(_contenedorBotones, BorderLayout.SOUTH);
    	_panelPrincipal.add(_panelTablero, "tablero");
    }
    
    private void mostrarPantalla(String pantalla) {
    	CardLayout cl = (CardLayout) (_panelPrincipal.getLayout());
    	cl.show(_panelPrincipal, pantalla);
    }
    
    private void limpiarTablero() {
        _panelTablero.removeAll();
        crearTablero();
    }
    
    public void mostrarTablero(int[][] tablero) {
        for (int f = 0; f < tablero.length; f++) {
        	for (int c = 0; c < tablero.length; c++) {
        		int valor = tablero[f][c];
        		if (valor == 0) _celdas[f][c].setText("");
        		else _celdas[f][c].setText(String.valueOf(valor));
                _celdas[f][c].setEditable(false);
        	}
        }
    }
    
    private void setModoAleatorio(boolean esAleatorio) {
    	CardLayout cl = (CardLayout) _contenedorBotones.getLayout();
    	cl.show(_contenedorBotones, esAleatorio ? "aleatorio": "manual");
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                _celdas[f][c].setEditable(true);
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    public void crearListener(ViewListener listener) {
        this._listener = listener;
    }
    
    public void msjDelResultadoDeLaVerificacionDelTablero(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Resultado de la Verificación", JOptionPane.INFORMATION_MESSAGE);
    }
    
}

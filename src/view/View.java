package view;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.text.NumberFormat;

public class View extends JFrame {
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
        setTitle("< Juegazos.coso >");
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
    	
        JTextPane tituloDelInicioDelJuego = new JTextPane();
        noPermitirSenialarSobreElPanel(tituloDelInicioDelJuego);
        configurarTamanioDelTituloDeInicioDelJuego(tituloDelInicioDelJuego);
        _panelMenu.add(tituloDelInicioDelJuego);
        
        JButton btnTableroVacio = new JButton("Tablero vacío");
        configurarTamanioDelBotonTableroVacio(btnTableroVacio);
        accionDelBotonTableroVacio(btnTableroVacio);
        
        _panelMenu.add(btnTableroVacio);
        JButton btnSudokuRandom = new JButton("Sudoku aleatorio");
        configurarTamanioDelBotonDeTableroSudokuRandom(btnSudokuRandom);
        accionDelBotonDeTableroSudokuRandom(btnSudokuRandom);
        _panelMenu.add(btnSudokuRandom);
        
        _panelPrincipal.add(_panelMenu, "menu");
    }
    
    private void accionDelBotonTableroVacio(JButton btnTableroVacio) {
    	btnTableroVacio.addActionListener(e -> {
        	setModoAleatorio(false);
        	mostrarPantalla("tablero");
        });		
	}
    
    private void accionDelBotonDeTableroSudokuRandom(JButton btnSudokuRandom) {
    	btnSudokuRandom.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingrese la cantidad de pistas (1 a 81):");
            	if(input != null && !input.isEmpty()) {
            		try {
            			int cantidadDePistas = Integer.parseInt(input);
            			if(cantidadDePistas < 1 || cantidadDePistas > 81) {
            				mostrarMensaje("Debe ingresar un número válido");
            			}else{
            				_listener.crearSudokuAleatorioConPistas(cantidadDePistas);
                			setModoAleatorio(true);
                			mostrarPantalla("tablero");
            			}
            			
            		} catch (NumberFormatException ex) {
            			mostrarMensaje("Debe ingresar un número válido");
                    }
            	}
            });		
	}

	private void configurarTamanioDelBotonDeTableroSudokuRandom(JButton btnSudokuRandom) {
    	btnSudokuRandom.setBounds(193, 342, 202, 54);
        btnSudokuRandom.setFont(new Font("Arial", Font.BOLD, 14));		
	}

	private void configurarTamanioDelTituloDeInicioDelJuego(JTextPane tituloDelInicioDelJuego) {
        tituloDelInicioDelJuego.setBounds(143, 138, 311, 60);
        tituloDelInicioDelJuego.setContentType("text/html");
		tituloDelInicioDelJuego.setText(
		    "<html>"
		  + "<div style='text-align:center; "
		  + "font-family:Arial Black; font-size:35px; font-weight:bold;"
		  + "background-color: black; "
		  + "color: white;'>"
		  + "Sudoku"
		  + "</div>"
		  + "</html>"
		);
        tituloDelInicioDelJuego.setOpaque(true);
        tituloDelInicioDelJuego.setBackground(UIManager.getColor("Button.background"));
        tituloDelInicioDelJuego.setAlignmentX(CENTER_ALIGNMENT);
        tituloDelInicioDelJuego.setAlignmentY(CENTER_ALIGNMENT);
	}

	private void crearTablero() {
    	_panelTablero = new JPanel();
    	_panelTablero.setLayout(new BorderLayout());
    	_panelTablero.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    	
    	JPanel panelCentral = new JPanel(new GridLayout(9, 9));
    	configurarTamanioDelPanelCentral(panelCentral);
    	armarCuadroDelTablero(panelCentral);
    	
    	_panelBtnManual = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    	
    	JButton btnResolverTablero = new JButton("Resolver tablero");
    	configurarTamanioDeBotonResolverTablero(btnResolverTablero);
    	accionDelBotonResolverTablero(btnResolverTablero);
    	noPermitirSenialarSobreElBoton(btnResolverTablero);
    	
    	JButton btnVerificar = new JButton("Verificar tablero");
    	configurarTamanioDeBotonVerificarTablero(btnVerificar);
    	accionDelBotonVerificarTablero(btnVerificar);
    	noPermitirSenialarSobreElBoton(btnVerificar);

    	JButton btnVolverAlMenuDeInicio = new JButton("Menú principal");
    	configurarTamanioDelBotonVolver(btnVolverAlMenuDeInicio);
    	accionDelBotonVolverAlTablero(btnVolverAlMenuDeInicio);
    	noPermitirSenialarSobreElBoton(btnVolverAlMenuDeInicio);
    	
    	construirPanelParaTableroAleatorio(btnResolverTablero, btnVolverAlMenuDeInicio);
    	
    	_panelDeOpcionDeTableroAleatorio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    	
    	JButton btnMostrarSolucion = new JButton("Mostrar solución");
    	accionDelBotonMostrarSolucion(btnMostrarSolucion);
    	noPermitirSenialarSobreElBoton(btnMostrarSolucion);
    	
    	JButton randomBtnVolver = new JButton("Menú principal");
    	configurarTamanioDelBotonVolverDelTableroRandom(randomBtnVolver);
    	accionDelBotonVolverDelTableroRandom(randomBtnVolver);
    	noPermitirSenialarSobreElBoton(randomBtnVolver);

    	JButton botonParaVerificarTablero = new JButton("Verificar el tablero");
    	accionDelBotonVerificarTableroRandom(botonParaVerificarTablero);
    	noPermitirSenialarSobreElBoton(botonParaVerificarTablero);
        
    	construirPanelParaTableroAleatorio(btnMostrarSolucion, randomBtnVolver, botonParaVerificarTablero);

        _contenedorBotones = new JPanel(new CardLayout());
        _contenedorBotones.add(_panelBtnManual, "tablero vacío");
        _contenedorBotones.add(_panelDeOpcionDeTableroAleatorio, "tablero aleatorio");
        
    	_panelTablero.add(panelCentral, BorderLayout.CENTER);
    	_panelTablero.add(_contenedorBotones, BorderLayout.SOUTH);
    	_panelPrincipal.add(_panelTablero, "tablero");
    }
	
	private void construirPanelParaTableroAleatorio(JButton btnResolverTablero, JButton btnVolverAlMenuDeInicio) {
		_panelBtnManual.add(btnResolverTablero);
    	_panelBtnManual.add(btnVolverAlMenuDeInicio);		
	}

	private void construirPanelParaTableroAleatorio(JButton btnMostrarSolucion, JButton randomBtnVolver, JButton botonParaVerificarTablero) {
		_panelDeOpcionDeTableroAleatorio.add(btnMostrarSolucion);
    	_panelDeOpcionDeTableroAleatorio.add(randomBtnVolver);
    	_panelDeOpcionDeTableroAleatorio.add(botonParaVerificarTablero);
	}

	private void accionDelBotonVerificarTableroRandom(JButton botonParaVerificarTableroRandom) {
		botonParaVerificarTableroRandom.addActionListener(e -> {
	        String mensajeVerificacion = _listener.verificarValidezDelTableroConMensaje(_celdas);
	        if("Tablero válido".equals(mensajeVerificacion)) {
	            boolean resuelto = _listener.resolverSudoku(_celdas);
	            mensajeVerificacion = resuelto ? "Tablero resuelto correctamente" : "Los valores ingresados no pertenecen a un tablero válido";
	        }
	        msjDelResultadoDeLaVerificacionDelTablero(mensajeVerificacion);
	    });	
	}

	private void accionDelBotonVolverDelTableroRandom(JButton randomBtnVolver) {
		randomBtnVolver.addActionListener(e -> {
    		limpiarTablero();
    		mostrarPantalla("menu");
    	});		
	}

	private void configurarTamanioDelBotonVolverDelTableroRandom(JButton randomBtnVolver) {
		randomBtnVolver.setFont(new Font("Arial", Font.BOLD, 12));		
	}

	private void accionDelBotonMostrarSolucion(JButton btnMostrarSolucion) {
		btnMostrarSolucion.addActionListener(e -> _listener.mostrarSolucion());		
	}

	private void accionDelBotonVolverAlTablero(JButton manualBtnVolver) {
		manualBtnVolver.addActionListener(e -> {
    		limpiarTablero();
    		mostrarPantalla("menu");
    	});		
	}

	private void accionDelBotonVerificarTablero(JButton btnVerificar) {
		btnVerificar.addActionListener(e -> { _listener.verificarValidezDelTableroConMensaje(_celdas);});		
	}

	private void configurarTamanioDeBotonVerificarTablero(JButton btnVerificar) {
		btnVerificar.setFont(new Font("Arial", Font.BOLD, 12));		
	}

	private void configurarTamanioDeBotonResolverTablero(JButton btnResolverTablero) {
		btnResolverTablero.setFont(new Font("Arial", Font.BOLD, 12));		
	}

	private void accionDelBotonResolverTablero(JButton btnResolverTablero) {
		btnResolverTablero.addActionListener(e -> {
	        boolean resuelto = _listener.resolverSudoku(_celdas);
	        String mensaje = resuelto ? "Tablero resuelto correctamente" : "El tablero no se puede resolver";
	        msjDelResultadoDeLaVerificacionDelTablero(mensaje);
	    });
	}
	
	private void noPermitirSenialarSobreElPanel(JTextPane panelSeleccionado) {
		panelSeleccionado.setEditable(false);   
		panelSeleccionado.setHighlighter(null);
		panelSeleccionado.setFocusable(false);
	}
    
    private void configurarTamanioDelPanelCentral(JPanel panelCentral) {
    	panelCentral.setPreferredSize(new Dimension(450, 450));
    	panelCentral.setBackground(Color.BLACK);		
	}

    private void configurarTamanioDelBotonVolver(JButton manualBtnVolver) {
    	manualBtnVolver.setFont(new Font("Arial", Font.BOLD, 12));
	}
    
    private void configurarTamanioDelBotonTableroVacio(JButton btnTableroVacio) {
    	btnTableroVacio.setBounds(207, 261, 173, 47);
        btnTableroVacio.setFont(new Font("Arial", Font.BOLD, 14));
	} 

	private void armarCuadroDelTablero(JPanel panelCentro) {
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
    }
    
    private void noPermitirSenialarSobreElBoton(JButton panelSeleccionado) {
		panelSeleccionado.setFocusable(false);
	}
    
    private void mostrarPantalla(String pantalla) {
    	CardLayout cardlayout = (CardLayout) (_panelPrincipal.getLayout());
    	cardlayout.show(_panelPrincipal, pantalla);
    }
    
    private void limpiarTablero() {
        _panelTablero.removeAll();
        crearTablero();
    }
    
    public void mostrarTablero(int[][] tablero) {
        for(int fila = 0; fila < tablero.length; fila++) {
        	for(int columna = 0; columna < tablero.length; columna++) {
        		int valor = tablero[fila][columna];
        		if(valor == 0) 
        			_celdas[fila][columna].setText("");
        		else 
        			_celdas[fila][columna].setText(String.valueOf(valor));
                	_celdas[fila][columna].setEditable(false);
        	}
        }
    }
    
    private void setModoAleatorio(boolean esAleatorio) {
    	CardLayout cardlayout = (CardLayout) _contenedorBotones.getLayout();
    	cardlayout.show(_contenedorBotones, esAleatorio ? "tablero aleatorio": "tablero vacío");
        for(int fila = 0; fila < 9; fila++) {
            for(int columna = 0; columna < 9; columna++)
                _celdas[fila][columna].setEditable(true);
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    public void crearListener(ViewListener listener) {
        this._listener = listener;
    }
    
    public void msjDelResultadoDeLaVerificacionDelTablero(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "El tablero es ", JOptionPane.INFORMATION_MESSAGE);
    }
    
}

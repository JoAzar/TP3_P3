package view;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import model.Tablero;
import java.util.List;
import java.awt.*;
import java.text.NumberFormat;

@SuppressWarnings("serial")
public class View extends JFrame {
	private JPanel _panelPrincipal;
	private JPanel _contenedorBotones;
	private JPanel _panelMenu;
    private JPanel _panelTablero;
    private JPanel _panelBtnManual;
    private JPanel _panelDeOpcionDeTableroAleatorio;
	private ViewListener _listener;
    private JTextField[][] _celdas = new JTextField[9][9];
    private JList<String> _listaSoluciones;
    private DefaultListModel<String> _modeloLista;
    private List<Tablero> _solucionesActuales;
    JDialog _ventana;
    
    public View() {
		initialize();
    }
    
    private void initialize() {
        setTitle("Resolución de Sudokus");
		setBounds(360, 100, 600, 560); 
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
        configurarBotonTableroVacio(btnTableroVacio);
        accionDelBotonTableroVacio(btnTableroVacio);
        
        _panelMenu.add(btnTableroVacio);
        JButton btnSudokuRandom = new JButton("Sudoku aleatorio");
        configurarTamanioDelBotonDeTableroSudokuRandom(btnSudokuRandom);
        accionDelBotonDeTableroSudokuRandom(btnSudokuRandom);
        _panelMenu.add(btnSudokuRandom);
        
        _panelPrincipal.add(_panelMenu, "menu");
    }
    
	private void noPermitirSenialarSobreElPanel(JTextPane panelSeleccionado) {
		panelSeleccionado.setEditable(false);   
		panelSeleccionado.setHighlighter(null);
		panelSeleccionado.setFocusable(false);
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
	
    private void configurarBotonTableroVacio(JButton btnTableroVacio) {
    	btnTableroVacio.setBounds(207, 261, 173, 47);
        btnTableroVacio.setFont(new Font("Arial", Font.BOLD, 14));
	} 
    
    private void accionDelBotonTableroVacio(JButton btnTableroVacio) {
    	btnTableroVacio.addActionListener(e -> {
        	setModoAleatorio(false);
        	mostrarPantalla("tablero");
        });		
	}
    
	private void configurarTamanioDelBotonDeTableroSudokuRandom(JButton btnSudokuRandom) {
    	btnSudokuRandom.setBounds(193, 342, 202, 54);
        btnSudokuRandom.setFont(new Font("Arial", Font.BOLD, 14));		
	}
    
    private void accionDelBotonDeTableroSudokuRandom(JButton btnSudokuRandom) {
    	btnSudokuRandom.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ingrese la cantidad de pistas (1 a 40):");
            	if(input != null && !input.isEmpty()) {
            		try {
            			int cantidadDePistas = Integer.parseInt(input);
            			if(cantidadDePistas < 1 || cantidadDePistas > 40) {
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

	private void crearTablero() {
    	_panelTablero = new JPanel();
    	_panelTablero.setLayout(new BorderLayout());
    	_panelTablero.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    	
    	JPanel panelCentral = new JPanel(new GridLayout(9, 9));
    	configurarTamanioDelPanelCentral(panelCentral);
    	armarCuadroDelTablero(panelCentral);
    	
    	_panelBtnManual = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    	
    	JButton btnResolverTablero = new JButton("Resolver tablero");
    	configurarBotonResolverTablero(btnResolverTablero);
    	accionDelBotonResolverTablero(btnResolverTablero);
    	noPermitirSenialarSobreElBoton(btnResolverTablero);
    	
    	JButton btnVerificar = new JButton("Verificar tablero");
    	configurarBotonVerificarTablero(btnVerificar);
    	accionDelBotonVerificarTablero(btnVerificar);
    	noPermitirSenialarSobreElBoton(btnVerificar);
    	
    	JButton btnReiniciarTablero = new JButton("Reiniciar tablero");
    	configurarBotonReiniciarTablero(btnReiniciarTablero);
    	noPermitirSenialarSobreElBoton(btnReiniciarTablero);
    	accionDelBotonReiniciarTablero(btnReiniciarTablero, btnResolverTablero);

    	JButton btnVolverAlMenuDeInicio = new JButton("Menú principal");
    	configurarBotonVolver(btnVolverAlMenuDeInicio);
    	accionDelBotonVolverAlTablero(btnVolverAlMenuDeInicio);
    	noPermitirSenialarSobreElBoton(btnVolverAlMenuDeInicio);
    	
    	construirPanelParaTableroManual(btnResolverTablero, btnReiniciarTablero, btnVolverAlMenuDeInicio);
    	
    	_panelDeOpcionDeTableroAleatorio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    	
    	JButton btnMostrarSolucion = new JButton("Mostrar solución");
    	configurarBotonMostrarSolucionDelTableroRandom(btnMostrarSolucion);
    	accionDelBotonMostrarSolucion(btnMostrarSolucion);
    	noPermitirSenialarSobreElBoton(btnMostrarSolucion);
    	
    	JButton randomBtnVolver = new JButton("Menú principal");
    	configurarBotonVolverDelTableroRandom(randomBtnVolver);
    	accionDelBotonVolverDelTableroRandom(randomBtnVolver);
    	noPermitirSenialarSobreElBoton(randomBtnVolver);
        
    	construirPanelParaTableroAleatorio(btnMostrarSolucion, randomBtnVolver);

        _contenedorBotones = new JPanel(new CardLayout());
        _contenedorBotones.add(_panelBtnManual, "tablero vacío");
        _contenedorBotones.add(_panelDeOpcionDeTableroAleatorio, "tablero aleatorio");
        
    	_panelTablero.add(panelCentral, BorderLayout.CENTER);
    	_panelTablero.add(_contenedorBotones, BorderLayout.SOUTH);
    	_panelPrincipal.add(_panelTablero, "tablero");
    }
	
    private void configurarTamanioDelPanelCentral(JPanel panelCentral) {
    	panelCentral.setPreferredSize(new Dimension(450, 450));
    	panelCentral.setBackground(Color.BLACK);		
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
    
	private void configurarBotonResolverTablero(JButton btnResolverTablero) {
		btnResolverTablero.setFont(new Font("Arial", Font.BOLD, 12));		
	}
	
	private void accionDelBotonResolverTablero(JButton btnResolverTablero) {
		btnResolverTablero.addActionListener(e -> {
	        boolean resuelto = _listener.encontrarSolucionesSudoku(_celdas);
	        
	        if(resuelto) {
		        btnResolverTablero.setEnabled(false);
		        mostrarMensaje("Tablero resuelto correctamente");
	        }
	    });
	}
	
    private void noPermitirSenialarSobreElBoton(JButton panelSeleccionado) {
		panelSeleccionado.setFocusable(false);
	}
    
	private void configurarBotonVerificarTablero(JButton btnVerificar) {
		btnVerificar.setFont(new Font("Arial", Font.BOLD, 12));		
	}
	
	private void accionDelBotonVerificarTablero(JButton btnVerificar) {
		btnVerificar.addActionListener(e -> { _listener.verificarValidezDelTableroConMensaje(_celdas);});		
	}
	
	private void configurarBotonReiniciarTablero(JButton btnReiniciar) {
    	btnReiniciar.setFont(new Font("Arial", Font.BOLD, 12));
	}

	private void accionDelBotonReiniciarTablero(JButton btnReiniciar, JButton btnResolverTablero) {
		btnReiniciar.addActionListener(e -> reiniciarTablero(btnResolverTablero));
	}
	
	private void reiniciarTablero(JButton btnResolverTablero) {
		if(_ventana != null) {
			cerrarVentanaDeSoluciones();
		}
		
		for (int fila = 0; fila < 9; fila++) {
			for (int columna = 0; columna < 9; columna++) {
	            JFormattedTextField celda = (JFormattedTextField) _celdas[fila][columna];
	            celda.setValue(null);
	            celda.setEditable(true);
			}
		}
		btnResolverTablero.setEnabled(true);
	}
	
    private void configurarBotonVolver(JButton manualBtnVolver) {
    	manualBtnVolver.setFont(new Font("Arial", Font.BOLD, 12));
	}
    
	private void accionDelBotonVolverAlTablero(JButton manualBtnVolver) {
		manualBtnVolver.addActionListener(e -> {
			if(_ventana != null) {
				cerrarVentanaDeSoluciones();
			}
    		limpiarTablero();
    		mostrarPantalla("menu");
    	});		
	}
	
	private void construirPanelParaTableroManual(JButton btnResolverTablero, JButton btnReiniciarTablero,
			JButton btnVolverAlMenuDeInicio) {
		_panelBtnManual.add(btnResolverTablero);
    	_panelBtnManual.add(btnReiniciarTablero);
    	_panelBtnManual.add(btnVolverAlMenuDeInicio);		
	}
	
	private void configurarBotonMostrarSolucionDelTableroRandom(JButton btnMostrarSolucion){
		btnMostrarSolucion.setFont(new Font("Arial", Font.BOLD, 12));
	}

	private void accionDelBotonMostrarSolucion(JButton btnMostrarSolucion) {
		btnMostrarSolucion.addActionListener(e -> {
			btnMostrarSolucion.setEnabled(false);
			_listener.mostrarSolucion();
		});		
	}
	
	private void configurarBotonVolverDelTableroRandom(JButton randomBtnVolver) {
		randomBtnVolver.setFont(new Font("Arial", Font.BOLD, 12));		
	}
	
	private void accionDelBotonVolverDelTableroRandom(JButton randomBtnVolver) {
		randomBtnVolver.addActionListener(e -> {
			if(_ventana != null) {
				cerrarVentanaDeSoluciones();
			}
    		limpiarTablero();
    		mostrarPantalla("menu");
    	});		
	}
	
    private void limpiarTablero() {
        _panelTablero.removeAll();
        crearTablero();
    }
    
    private void mostrarPantalla(String pantalla) {
    	CardLayout cardlayout = (CardLayout) (_panelPrincipal.getLayout());
    	cardlayout.show(_panelPrincipal, pantalla);
    }
	
	private void construirPanelParaTableroAleatorio(JButton btnMostrarSolucion, JButton randomBtnVolver) {
		_panelDeOpcionDeTableroAleatorio.add(btnMostrarSolucion);
    	_panelDeOpcionDeTableroAleatorio.add(randomBtnVolver);
	}
    
    private void setModoAleatorio(boolean esAleatorio) {
    	CardLayout cardlayout = (CardLayout) _contenedorBotones.getLayout();
    	cardlayout.show(_contenedorBotones, esAleatorio ? "tablero aleatorio": "tablero vacío");
    	modificarEditabilidadDeCeldas(!esAleatorio);
    }
    
    private void modificarEditabilidadDeCeldas(boolean esEditable) {
        for(int fila = 0; fila < 9; fila++) {
            for(int columna = 0; columna < 9; columna++) {
            	JFormattedTextField celda = (JFormattedTextField) _celdas[fila][columna];
                celda.setEditable(esEditable);
            }
        }
    }
    
    private void cerrarVentanaDeSoluciones() {
    	_ventana.dispose();
    	_ventana = null;
		_listaSoluciones.removeAll();
		_solucionesActuales.clear();
    }
    
    public void mostrarListaDeSoluciones(List<Tablero> soluciones) {
        _solucionesActuales = soluciones;
        
        _ventana = new JDialog(this, "Soluciones", false);

        _ventana.setBounds(120, 180, 250, 400);
        _ventana.setLayout(new BorderLayout());

        _modeloLista = new DefaultListModel<>();

        for (int i = 0; i < soluciones.size(); i++) {
            _modeloLista.addElement("Solución " + (i + 1));
        }

        _listaSoluciones = new JList<>(_modeloLista);

        _listaSoluciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = _listaSoluciones.getSelectedIndex();
                mostrarTablero(_solucionesActuales.get(index));
            }
        });

        _ventana.add(new JScrollPane(_listaSoluciones), BorderLayout.CENTER);
        _ventana.setVisible(true);
    }
    
    public void mostrarTablero(Tablero tablero) {
        for(int fila = 0; fila < tablero.length(); fila++) {
        	for(int columna = 0; columna < tablero.length(); columna++) {
        		JFormattedTextField celda = (JFormattedTextField) _celdas[fila][columna];
        		int valor = tablero.getValor(fila, columna);
        		if(valor == 0) {
        			celda.setValue(null);
        		}
        		else {
        			celda.setValue(valor);
        		}
        		celda.setEditable(false);
        	}
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    public String inputDeUsuario(String mensaje) {
    	return JOptionPane.showInputDialog(null, mensaje);
    }
    
    public void crearListener(ViewListener listener) {
        this._listener = listener;
    }
    
}

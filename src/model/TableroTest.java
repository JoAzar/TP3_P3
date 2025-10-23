package model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TableroTest {
    Tablero tablero = new Tablero();

    @Test
    public void generarTableroAleatorioCantidad() {
        tablero.generarTableroAleatorioDeValoresIngresados(10);
        int cont = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tablero.getValor(i, j) != 0) cont++;
            }
        }
        assertEquals(10, cont);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantidadFueraDeRango() {
        tablero.generarTableroAleatorioDeValoresIngresados(100);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void validarDatosFila() {
    	tablero.validarDatos(10, 1, 1);
     }
    
    @Test(expected = IllegalArgumentException.class)
    public void validarDatosColumna() {
    	tablero.validarDatos(1, -10, 1);
     }
    
    @Test(expected = IllegalArgumentException.class)
    public void validarDatosValor() {
    	tablero.validarDatos(1, 1, 10);
     }

    @Test
    public void vaciar() {
        tablero.generarTableroAleatorioDeValoresIngresados(5);
        tablero.vaciar();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(0, tablero.getValor(i, j));
            }
        }
    }

    @Test
    public void setYGetValor() {
        tablero.setValor(2, 3, 7);
        assertEquals(7, tablero.getValor(2, 3));
    }

    @Test
    public void getTableroDevuelveCopia() {
        tablero.setValor(0, 0, 5);
        int[][] copia = tablero.getTablero();
        copia[0][0] = 9;

        assertEquals(5, tablero.getValor(0, 0));
    }

}

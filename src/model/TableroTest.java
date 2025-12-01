package model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TableroTest {
    Tablero _tablero = new Tablero(9);
    
    @Test(expected = IllegalArgumentException.class)
    public void validarDatosFila() {
    	_tablero.validarDatos(10, 1, 1);
     }
    
    @Test(expected = IllegalArgumentException.class)
    public void validarDatosColumna() {
    	_tablero.validarDatos(1, -10, 1);
     }
    
    @Test(expected = IllegalArgumentException.class)
    public void validarDatosValor() {
    	_tablero.validarDatos(1, 1, 10);
     }

    @Test
    public void setYGetValor() {
        _tablero.setValor(2, 3, 7);
        assertEquals(7, _tablero.getValor(2, 3));
    }

    @Test
    public void getTableroDevuelveCopia() {
        _tablero.setValor(0, 0, 5);
        Tablero copia = _tablero.getTablero();
        copia.setValor(0, 0, 9);

        assertEquals(5, _tablero.getValor(0, 0));
        assertNotEquals(_tablero, copia);
    }
    
    @Test
    public void getTableroCopiaCorrectamenteTest() {
        _tablero.setValor(3, 4, 8);
        _tablero.setValor(7, 2, 6);
        
        Tablero copia = _tablero.getTablero();
        
        assertEquals(8, copia.getValor(3, 4));
        assertEquals(6, copia.getValor(7, 2));
    }
    
    @Test
    public void celdaVaciaTest() {
        assertEquals(true, _tablero.celdaVacia(0, 0));
        _tablero.setValor(0, 0, 5);
        assertEquals(false, _tablero.celdaVacia(0, 0));
    }
    
    @Test
    public void vaciarTest() {
        _tablero.setValor(1, 1, 7);
        _tablero.vaciar();
        assertEquals(0, _tablero.getValor(1, 1));
    }

}

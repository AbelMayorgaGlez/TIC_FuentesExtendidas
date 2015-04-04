package es.unileon.tic.fuentesExtendidas.interfaz;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/** 
 * Clase que representa un campo de texto en el que solo se pueden introducir digitos
 * @author Abel Mayorga Gonzalez.
 * @author Alberto Rodriguez Gomez.
 */
public class JTextFieldEnteroPositivo extends JTextField{
    /** Devuelve el modelo personalizado */
    protected Document createDefaultModel(){
    	return new PlainDocumentEnteroPositivo();
    }

    /** Modelo personalizado que solo permite secuencias de digitos*/
    protected class PlainDocumentEnteroPositivo extends PlainDocument{
		/** Inserta caracteres previa validacion*/
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
		    char[] fuente = str.toCharArray();
		    char[] resultado = new char[fuente.length];
		    int j = 0;
		    
		    for (int i = 0; i < fuente.length; i++){
			if ((fuente[i] >= '0' && fuente[i] <= '9')){
			    resultado[j++] = fuente[i];
			} else {
			    Toolkit.getDefaultToolkit().beep();
			}
		    }
		    super.insertString(offs, new String(resultado,0,j),a);
		}
    }
}
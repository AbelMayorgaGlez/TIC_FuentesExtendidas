package es.unileon.tic.fuentesExtendidas.interfaz;
import javax.swing.*;
import java.awt.event.*;

/** 
 * Clase que representa el cuadro de dialogo Nuevo 
 * @author Abel Mayorga Gonzalez
 * @author Alberto Rodriguez Gomez
 */
public class NuevoDialog extends JDialog{
    private JLabel lProbabilidad,lOrden;
    private JTextFieldEnteroPositivo jTFProbabilidad,jTFOrden;
    private JButton btnAceptar,btnCancelar;
    private GUI padre;

    /** Constructor de clase */
    public NuevoDialog(JFrame parent, boolean modal){
		super(parent,modal);
		padre = (GUI)parent;
		initComponents();
    }

    /** Inicia los componentes del cuadro de dialogo*/
    private void initComponents(){
		setSize(390,210);
		setResizable(false);
		getContentPane().setLayout(null);
	
		lProbabilidad = new JLabel("Probabilidad 1/");
		getContentPane().add(lProbabilidad);
		lProbabilidad.setBounds(30,30,200,30);
	
		lOrden = new JLabel("Calcular hasta orden:");
		getContentPane().add(lOrden);
		lOrden.setBounds(30,90,200,30);
	
		jTFProbabilidad = new JTextFieldEnteroPositivo();
		jTFProbabilidad.setText("2");
		getContentPane().add(jTFProbabilidad);
		jTFProbabilidad.setBounds(260,30,100,30);
	
		jTFOrden = new JTextFieldEnteroPositivo();
		jTFOrden.setText("10");
		getContentPane().add(jTFOrden);
		jTFOrden.setBounds(260,90,100,30);
	
		FocusListener fl = new FocusAdapter(){
			/* Selecciona todo el texto al obtener el foco */
			public void focusGained(FocusEvent evt){
			    ((JTextFieldEnteroPositivo)evt.getSource()).selectAll();
			}
		    };
	
		jTFProbabilidad.addFocusListener(fl);
		jTFOrden.addFocusListener(fl);
	
		btnAceptar = new JButton("Aceptar");
		getContentPane().add(btnAceptar);
		btnAceptar.setBounds(85,140,100,30);
		btnAceptar.addActionListener(new ActionListener(){
			/* Envia los datos introducidos si estos se pueden transformar a numeros. Si no, muestra un mensaje de error*/
			public void actionPerformed(ActionEvent evt){
			    try{
			    	mandarResultados(Integer.parseInt(jTFProbabilidad.getText()),Integer.parseInt(jTFOrden.getText()));
			    } catch (NumberFormatException e){
			    	JOptionPane.showMessageDialog(null, e.getMessage());		       
			    }
			}
		    });
		/* El boton por defecto es el boton aceptar */
		getRootPane().setDefaultButton(btnAceptar);
	
		btnCancelar = new JButton("Cancelar");
		getContentPane().add(btnCancelar);
		btnCancelar.setBounds(205,140,100,30);
		btnCancelar.addActionListener(new ActionListener(){
			/* Destruye el cuadro de dialogo */
			public void actionPerformed(ActionEvent evt){
			    dispose();
			}
		    });
    }

    /** Comprueba los datos y si son correctos, los envia */
    private void mandarResultados(int prob,int ord){
    	//Comprueba que el orden sea calculable dentro del rango de los numeros long.
	    if(Math.log(Long.MAX_VALUE)/Math.log(prob)<ord){
		    JOptionPane.showMessageDialog(this,"Orden demasiado alto para esa probabilidad. El limite es " + (int)Math.floor(Math.log(Long.MAX_VALUE)/Math.log(prob)),"Error",JOptionPane.ERROR_MESSAGE);
		    jTFOrden.requestFocus();
		//Comprueba que el orden sea calculable por el sistema, ya que se puede desbordar la pila de recursion.
	    } else if(ord>14){
	    	JOptionPane.showMessageDialog(this,"Orden demasiado alto para poder ser calculado.","Error",JOptionPane.ERROR_MESSAGE);
		    	jTFOrden.requestFocus();
	    } else {
	    	padre.calcular(prob,ord);
		}
    }
}


package es.unileon.tic.fuentesExtendidas.interfaz;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import es.unileon.tic.fuentesExtendidas.huffman.*;
import es.unileon.tic.fuentesExtendidas.auxiliar.*;

/**
 * Clase que representa la interfaz grafica de usuario.
 * @author Abel Mayorga Gonzalez
 * @author Alberto Rodriguez Gomez
 */
public class GUI extends JFrame{

	private JMenuBar barraDeMenus;
	private JMenu menuArchivo, menuEdicion, menuAyuda;
	private JMenuItem menuItemNuevo, menuItemSalir, menuItemLimpiar, menuItemAyuda;
	private JPanel panelDeInformacion;
    private JLabel lProb, lOrd;
	private JTabbedPane panelConPestanyas;
	private NuevoDialog nuevo;
	private java.util.List<Fuente> _lista;
	private Grafico _graf;
	private String ayudaMsg = "Fuentes Extendidas, por Abel Mayorga y Alberto Rodriguez\nIr a Inicio->Nuevo para calcular nuevas fuentes";

	/**
	 * Constructor.   
	 */
	public GUI(){
		setSize(800,600);//Tamanyo inicial de la ventana.
		setExtendedState(JFrame.MAXIMIZED_BOTH);//Si el sistema operativo lo admite, que salga maximizada.
		setTitle("Fuentes Extendidas");
		initComponents();
	}
	/**
	 * Inicia los componentes de la GUI.
	 */
	public void initComponents(){
		_lista = new ArrayList<Fuente>();

		barraDeMenus = new JMenuBar();
		setJMenuBar(barraDeMenus);

		menuArchivo = new JMenu("Archivo");
		menuArchivo.setMnemonic('A');
		barraDeMenus.add(menuArchivo);

		menuItemNuevo = new JMenuItem("Nuevo");
		menuItemNuevo.setMnemonic('N');
		menuArchivo.add(menuItemNuevo);
		menuItemNuevo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				mostrarNuevoDialogo();
			}
		    });
		
		menuItemSalir = new JMenuItem("Salir");
		menuItemSalir.setMnemonic('S');
		menuArchivo.add(menuItemSalir);
		menuItemSalir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				System.exit(0);
			}
		    });
		
		menuEdicion = new JMenu("Edicion");
		menuEdicion.setMnemonic('E');
		barraDeMenus.add(menuEdicion);
		menuEdicion.setEnabled(false);

		menuItemLimpiar = new JMenuItem("Limpiar");
		menuItemLimpiar.setMnemonic('L');
		menuEdicion.add(menuItemLimpiar);
		menuItemLimpiar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				borrarPanel();
			}
		    });

		menuAyuda = new JMenu("Ayuda");
		barraDeMenus.add(menuAyuda);
		
		menuItemAyuda = new JMenuItem("Ayuda");
		menuItemAyuda.setMnemonic('H');
       	menuItemAyuda.addActionListener(new ActionListener(){
       		public void actionPerformed(ActionEvent evt){
       			mostrarAyuda();
       		}
       	});
       	
       	menuAyuda.add(menuItemAyuda);
	       	
    	panelDeInformacion = new JPanel();
    	getContentPane().add(BorderLayout.SOUTH,panelDeInformacion);
    	panelDeInformacion.setLayout(new GridLayout(1,2));
    	
    	lProb = new JLabel();
    	panelDeInformacion.add(lProb);
    	lProb.setHorizontalAlignment(SwingConstants.CENTER);
    	lProb.setBackground(Color.WHITE);
    	lProb.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK));
   
    	lOrd = new JLabel();
    	panelDeInformacion.add(lOrd);
    	lOrd.setHorizontalAlignment(SwingConstants.CENTER);
    	lOrd.setBackground(Color.WHITE);
    	lOrd.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK));

    	panelConPestanyas = new JTabbedPane(JTabbedPane.TOP);
    	getContentPane().add(panelConPestanyas);
    	panelConPestanyas.setLayout(new BorderLayout());
    	panelConPestanyas.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    	
	}
	
	/**
	 * Calcula las fuentes extendidas de esa probabilidad hasta ese orden.
	 * @param probabilidad Probabilidad para la que calcular las fuentes.
	 * @param orden Orden hasta el que calcular.
	 */
	public void calcular(int probabilidad, int orden){
		//Primero borramos el panel anterior.
		borrarPanel();
		//Hacemos que se cierre el dialogo nuevo.
		nuevo.dispose();
		//Calculamos las fuentes.
		List<Fuente> _lista= new ArrayList<Fuente>();
		Fuente base = new Fuente(new Racional(1,probabilidad));
		_lista.add(base);
		for(int i = orden; i>=2; i--){
			base = base.extenderFuente();
			_lista.add(base);
		}
		//Iniciamos el resto de componentes de la GUI.
		lProb.setText("Probabilidad base: 1/" + probabilidad);
		lOrd.setText("Orden calculado: " + orden);
		
		_graf = new Grafico(_lista,this);
		panelConPestanyas.addTab("Grafico",_graf);
		JTextArea jText;
		for(int i = 0; i<orden ; i++){
			jText = new JTextArea(_lista.get(i).toString());
			jText.setEditable(false);
			panelConPestanyas.addTab("Orden " + (i+1),new JScrollPane(jText));
		}
		menuEdicion.setEnabled(true);
		//Borramos las fuentes para liberar memoria.
		_lista.clear();
	}
	
	/**
	 * Muestra un dialogo con ayuda.
	 */
	public void mostrarAyuda(){
		JOptionPane.showMessageDialog(this, ayudaMsg, "Ayuda", JOptionPane.INFORMATION_MESSAGE/*, new ImageIcon(getClass().getResource("/iconos/Ayuda.png"))*/);
	}
	
	/**
	 * Borra de la GUI todo lo que debe desaparecer al no haber nada calculado.
	 */
	public void borrarPanel(){
		panelConPestanyas.removeAll();
		_graf = null;
		menuEdicion.setEnabled(false);
		lProb.setText("");
		lOrd.setText("");
	}
	
    /** Muestra el dialogo Nuevo */
    public void mostrarNuevoDialogo(){
    	nuevo = new NuevoDialog(this,true);
    	nuevo.setVisible(true);
    }
	
    /**
     * Metodo main que arranca el programa.
     * @param args Argumentos en linea de comandos.
     */
    public static void main(String[] args){
    	try{
    		//Establecemos el estilo del sistema por defecto.
    	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (Exception e){
    	    System.out.println("No se pudo establecer el aspecto deseado: " + e);
    	}
    	//Enviamos la GUI al hilo despachador de eventos de la maquina virtual para que lo ejecute.
    	SwingUtilities.invokeLater(new Runnable(){
    		public void run(){
    		    new GUI().setVisible(true);
    		}
    	    });
        }

}

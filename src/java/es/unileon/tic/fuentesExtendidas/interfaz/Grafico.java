package es.unileon.tic.fuentesExtendidas.interfaz;

import javax.swing.*;
import java.awt.*;
import es.unileon.tic.fuentesExtendidas.huffman.*;

/**
 * Clase que representa el grafico.
 * @author Abel Mayorga Gonzalez.
 * @author Alberto Rodriguez Gomez.
 */
public class Grafico extends JPanel{
	/**
	 * Punto limite inferior derecho del espacio ocupado por el grafico.
	 */
	MyPoint _limite;
	/**
	 * GUI en la que esta incrustado el grafico.
	 */
	GUI _padre;
	/**
	 * Puntos del eje de abscisas sobre los que hay una division.
	 */
	MyPoint[] divHorizontal;
	/**
	 * Puntos del espacio de dibujo sobre los que hay un punto de la grafica.
	 */
	MyPoint[] puntos;
	/**
	 * Etiquetas sobre el eje de abscisas.
	 */
	JLabel[] etHorizontal;
	/**
	 * Etiquetas alrededor de la linea de la grafica.
	 */
	JLabel[] etVertical;
	/**
	 * Eficacias.
	 */
	float[] eficacias;
	
	/**
	 * Constructor del grafico.
	 * @param lista Lista de fuentes.
	 * @param padre GUI padre.
	 */
	public Grafico(java.util.List<Fuente> lista, GUI padre){
		this.setBackground(Color.WHITE);
		_padre = padre;
		divHorizontal = new MyPoint[lista.size()];
		etHorizontal = new JLabel[lista.size()];
		puntos = new MyPoint[lista.size()];
		etVertical = new JLabel[lista.size()];
		eficacias = new float[lista.size()];
		//Relleno de los arrays de etiquetas y eficacias.
		for(int i = 0; i < eficacias.length; i++){
			etHorizontal[i] = new JLabel(Integer.toString(i+1));
			etVertical[i] = new JLabel(Float.toString(lista.get(i).getEficacia()));
			eficacias[i]=lista.get(i).getEficacia();
		}
	}
	
	/**
	 * Metodo que se llama automaticamente cada vez que hay que repintar la grafica.
	 */
	public void paintComponent(Graphics g){
		g = (Graphics2D)g;
		//Quitamos todas las etiquetas.
		this.removeAll();
		//Calculamos los puntos limite entre los que se encuadra la grafica.
		_limite = new MyPoint(getWidth(),getHeight());
		MyPoint base = new MyPoint(Math.round(_limite.getX()*0.20f),Math.round(_limite.getY()*0.80f));
		MyPoint a = new MyPoint(_limite.getX()-base.getX(),base.getY());
		MyPoint b = new MyPoint(a.getX(),(_limite.getY()-a.getY()));
		MyPoint c = new MyPoint(base.getX(),b.getY());
		//Dibujamos los ejes
		g.drawLine(base.getX(),base.getY() , c.getX(), c.getY());
		g.drawLine(base.getX(), base.getY(), a.getX(), a.getY());

		//Calculamos los puntos de las divisiones sobre el eje de abscisas.
		MyPoint p;
		for(int i = 1; i<= divHorizontal.length; i++){
			p = new MyPoint(base.getX()+(i*(a.getX()-base.getX())/divHorizontal.length),base.getY());
			divHorizontal[i-1] = p;
			g.drawLine(p.getX(), p.getY()+10, p.getX(), p.getY()-10);
		}
		//Ponemos las etiquetas de las abscisas.
		ponerAbscisas(divHorizontal,etHorizontal);
		
		//Calculamos los puntos de la grafica
		for(int i = 0; i< divHorizontal.length; i++){
			puntos[i] = new MyPoint(divHorizontal[i].getX(),Math.round((divHorizontal[i].getY()-c.getY())*(1-eficacias[i])+c.getY()));
		}
		//Ponemos las etiquetas de la linea de la grafica.
		ponerOrdenadas(puntos,etVertical);
		//Dibujamos la grafica a color rojo.
		g.setColor(Color.RED);
		for(int i = 1; i< divHorizontal.length; i++){
			g.drawLine(puntos[i-1].getX(), puntos[i-1].getY() , puntos[i].getX(), puntos[i].getY());
			g.fillArc(puntos[i-1].getX(), puntos[i-1].getY(),5,5,0,360);
		}
		g.fillArc(puntos[puntos.length-1].getX(), puntos[puntos.length-1].getY(), 5, 5, 0, 360);
	}

	/**
	 * Pone las etiquetas en el eje de abscisas.
	 * @param divHorizontal Array con los puntos del eje de abscisas sobre los que hay una etiqueta.
	 * @param etHorizontal Array con las etiquetas a poner.
	 */
	public void ponerAbscisas(MyPoint[] divHorizontal, JLabel[] etHorizontal){
		MyPoint p;
		for(int i = 1; i <= etHorizontal.length; i++){
			p = divHorizontal[i-1];
			this.add(etHorizontal[i-1]);
			etHorizontal[i-1].setBounds(p.getX()-5,p.getY()+5,20,20);
		}
	}
	
	/**
	 * Pone las etiquetas sobre la grafica.
	 * @param puntos Array con los puntos de la grafica que se dibujan.
	 * @param etVertical Array con las etiquetas a poner.
	 */
	public void ponerOrdenadas(MyPoint[] puntos, JLabel[] etVertical){
		MyPoint p;
		for(int i = 0; i < puntos.length; i++){
			p = puntos[i];
			this.add(etVertical[i]);
			if(i%2==1){
				etVertical[i].setBounds(p.getX()-20,p.getY()-20,100,20);
			} else {
				etVertical[i].setBounds(p.getX()-20,p.getY()+5,100,20);
			}
		}
	}
}

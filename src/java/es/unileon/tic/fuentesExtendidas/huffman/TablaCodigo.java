package es.unileon.tic.fuentesExtendidas.huffman;

import java.util.*;

import es.unileon.tic.fuentesExtendidas.auxiliar.Racional;

/**
 * Clase que representa una tabla de probabilidades y codigos.
 * @author Abel Mayorga Gonzalez.
 * @author Alberto Rodriguez Gomez.
 */
public class TablaCodigo {
	
	/**
	 * Lista de parejas de probabilidad y codigo.
	 */
	private List<ParejaPCodigo> _lista;
	
	/**
	 * Construye una tabla a partir de la lista de parejas dada.
	 * @param lista Lista de parejas.
	 */
	public TablaCodigo(List<ParejaPCodigo> lista){
		_lista = lista;
		Collections.sort(_lista);
	}
	
	/**
	 * Construye una tabla juntando las dos pasadas.
	 * @param t1 Tabla 1.
	 * @param t2 Tabla 2.
	 */
	public TablaCodigo(TablaCodigo t1, TablaCodigo t2){
		this(t1.concatenar(t2).obtenerLista());
	}
	
	/**
	 * Construye una nueva tabla con la probabilidad y el codigo pasado. Se tendra solo una pareja.
	 * @param p Probabilidad.
	 * @param codigo Codigo de la probabilidad.
	 */
	public TablaCodigo(Racional p, String codigo){
		_lista = new ArrayList<ParejaPCodigo>();
		_lista.add(new ParejaPCodigo(p,codigo));
	}
	/**
	 * Concatena la tabla actual con la pasada.
	 * @param otra Tabla que concatenar a la actual.
	 * @return La tabla creada.
	 */
	public TablaCodigo concatenar(TablaCodigo otra){
		List<ParejaPCodigo> nueva = obtenerLista();
		nueva.addAll(otra.obtenerLista());
		return new TablaCodigo(nueva);
	}

	/**
	 * 
	 * @return Devuelve la lista de parejas de la tabla.
	 */
	private List<ParejaPCodigo> obtenerLista(){
		return new ArrayList<ParejaPCodigo>(_lista);
	}
	
	/**
	 * 
	 * @return Devuelve la longitud del codigo.
	 */
	public float getLongitudCodigo(){
		float longitud = 0.0f;
		//Esto es el sumatorio de las probabilidades por la longitud de su palabra codigo.
		for(ParejaPCodigo p : _lista){
			longitud += p.getProbabilidad().floatValue()*p.getCodigo().length();
		}
		return longitud;
	}
	
	/**
	 * Devuelve una representacion de la tabla.
	 */
	public String toString(){
		String cad = "";
		for(ParejaPCodigo p : _lista){
			cad += p.toString() + "\n";
		}
		return cad;
	}
}
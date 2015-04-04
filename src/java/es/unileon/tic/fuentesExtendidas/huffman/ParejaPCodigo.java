package es.unileon.tic.fuentesExtendidas.huffman;

import es.unileon.tic.fuentesExtendidas.auxiliar.*;

/**
 * Clase que relaciona una probabilidad y su codigo.
 * @author Abel Mayorga Gonzalez.
 * @author Alberto Rodriguez Gomez.
 */

public class ParejaPCodigo implements Comparable<ParejaPCodigo>{
	
	/**
	 * Probabilidad.
	 */
	private Racional _p;
	/**
	 * Codigo de la probabilidad.
	 */
	private String _codigo;
	
	/**
	 * Constructor de clase.
	 * @param p Probabilidad.
	 * @param codigo Codigo de la probabilidad.
	 */
	public ParejaPCodigo(Racional p, String codigo){
		_p = p;
		_codigo = codigo;
	}
	
	/**
	 * 
	 * @return La probabilidad.
	 */
	public Racional getProbabilidad(){
		return _p;
	}
	
	/**
	 * 
	 * @return El codigo.
	 */
	public String getCodigo(){
		return _codigo;
	}

	/**
	 * Commpara la probabilidad de la pareja actual con la de otra.
	 * Devuelve 0 si son iguales, un numero negativo si la actual es menor
	 * y un numero positivo si la actual es mayor.
	 */
	@Override
	public int compareTo(ParejaPCodigo otra) {
		return _p.compareTo(otra.getProbabilidad());
	}
	
	/**
	 * Devuelve una representacion de la pareja en formato probabilidad-->codigo.
	 */
	public String toString(){
		return _p.toString() + " --> " + _codigo;
	}

}

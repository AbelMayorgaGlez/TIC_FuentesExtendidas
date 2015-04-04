package es.unileon.tic.fuentesExtendidas.huffman;

import java.util.*;
import es.unileon.tic.fuentesExtendidas.auxiliar.*;

/**
 * Clase que modeliza una fuente.
 * @author Abel Mayorga Gonzalez
 * @author Alberto Rodriguez Gomez.
 *
 */
public class Fuente {
	
	/**
	 * Lista de probabilidades.
	 */
	private List<Racional> _probabilidades;
	/**
	 * Orden de la fuente.
	 */
	private int _orden;
	/**
	 * Codigo de la fuente.
	 */
	private TablaCodigo _codigo;
	/**
	 * Probabilidad base.
	 */
	private Racional _p;
	/**
	 * Entropia de la fuente de orden 1.
	 */
	private float _entropiaBase;
	
	/**
	 * Construye una fuente de orden 1 con el racional pasado.
	 * @param p Probabilidad de la fuente.
	 */
	public Fuente(Racional p){
		_p = p;
		_orden = 1;
		_probabilidades = new ArrayList<Racional>();
		_probabilidades.add(p);
		_probabilidades.add(new Racional(1).restar(p));
		_entropiaBase = calcularEntropia();
	}
	
	/**
	 * Construye una fuente extendida.
	 * @param prob Lista con las probabilidades.
	 * @param p Probabilidad base.
	 * @param k Orden de la fuente.
	 * @param entropiaBase Entropia de la fuente de orden 1.
	 */
	private Fuente(List<Racional> prob, Racional p, int k, float entropiaBase){
		_probabilidades = prob;
		_p = p;
		_orden = k;
		_entropiaBase = entropiaBase;
	}
	
	/**
	 * 
	 * @return Devuelve el orden de la fuente.
	 */
	public int getOrden(){
		return _orden;
	}
	
	/**
	 * Calcula el codigo de la fuente.
	 * @return La tabla con el codigo.
	 */
	public TablaCodigo calcularCodigo(){
		if(_codigo == null){
			//Ordenamos las probabilidades de menor a mayor
			Collections.sort(_probabilidades);
			//Creamos la lista de hojas
			List<Arbol> lista = new ArrayList<Arbol>();
			for(Racional r : _probabilidades){
				lista.add(new Arbol(r));
			}
			//Construimos el arbol y obtenemos el codigo.
			_codigo = Arbol.construirArbol(lista).obtenerCodigo(0,0);
		}
		return _codigo;
	}
	
	/**
	 * 
	 * @return Devuelve la eficacia del codigo.
	 */
	public float getEficacia(){
		calcularCodigo();
		return calcularEntropia()/_codigo.getLongitudCodigo();
	}
	
	/**
	 * Extiende la fuente actual en un orden.
	 * @return La fuente extendida.
	 */
	public Fuente extenderFuente(){
		List<Racional> ampliada = new ArrayList<Racional>();
		//Creamos una nueva lista multiplicando por p y por 1-p.
		for(Racional r : _probabilidades){
			ampliada.add(r.multiplicar(_p));
			ampliada.add(r.multiplicar(new Racional(1).restar(_p)));
		}
		//Construimos la fuente nueva.
		Fuente nueva = new Fuente(ampliada,_p,_orden+1,_entropiaBase);
		return nueva;
	}
	
	/**
	 * Extiende la fuente actual en tantos como indique el parametro orden.
	 * @param orden Numero de ordenes a extender.
	 * @return La fuente extendida.
	 */
	public Fuente extenderFuente(int orden){
		Fuente extendida = this;
		for(int i = _orden; i < orden; i++){
			extendida = extendida.extenderFuente();
		}
		return extendida;
	}
	
	/**
	 * 
	 * @return Devuelve la entropia de la fuente.
	 */
	public float calcularEntropia(){
		float entropia = 0.0f;
		if(_orden == 1){
			//Esto es el sumatorio de p*log(1/p) para todas las p de la fuente.
			for(Racional r : _probabilidades){
				entropia += r.floatValue()*(-Math.log(r.floatValue())/Math.log(2));
			}
		} else {
			//Si no es de orden 1, calculamos la entropia multiplicando el orden por la entropia de la fuente base.
			entropia = _entropiaBase * _orden;
		}
		return entropia;
	}

	/**
	 * Devuelve una representacion de la fuente, en formato p-->CODIGO. 
	 */
	public String toString(){
		calcularCodigo();
		return _codigo.toString();
	}
}

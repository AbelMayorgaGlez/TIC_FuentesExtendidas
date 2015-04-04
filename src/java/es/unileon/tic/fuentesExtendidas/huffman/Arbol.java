package es.unileon.tic.fuentesExtendidas.huffman;

import java.util.Collections;
import java.util.List;

import es.unileon.tic.fuentesExtendidas.auxiliar.*;

/**
 * 
 * Clase que modela un Arbol Huffman
 * @author Abel Mayorga Gonzalez
 * @author Alberto Rodriguez Gomez
 */
public class Arbol implements Comparable<Arbol>{
	
	/**
	 * Nodo raiz del arbol actual.
	 */
	private Racional _nodo;
	
	/**
	 * Subarbol izquierdo.
	 */
	private Arbol _izq;
	/**
	 * Subarbol derecho.
	 */
	private Arbol _der; 
	
	/**
	 * Constructor de clase. Construye una hoja con el nodo dado.
	 * @param nodo Numero racional contenido en la raiz.
	 */
	public Arbol(Racional nodo){
		this(nodo,null,null);
	}
	
	/**
	 * Constructor de clase. Construye un arbol con el numero racional pasado y los subarboles como hijos.
	 * @param nodo. Numero racional contenido en la raiz.
	 * @param izq. Subarbol izquierdo.
	 * @param der. Subarbol derecho.
	 */
	public Arbol(Racional nodo, Arbol izq, Arbol der){
		_nodo = nodo;
		_izq = izq;
		_der = der;
	}

	/**
	 * Construye un arbol huffman para unas probabilidades.
	 * @param arbolProbabilidades. Lista de arboles en cuya raiz hay probabilidades, ordenada de menor a mayor.
	 * @return El arbol huffman correspondiente.
	 */
	public static Arbol construirArbol(List<Arbol> arbolProbabilidades){
		//Si solo hay un arbol, es el buscado.
		if(arbolProbabilidades.size()==1){
			return arbolProbabilidades.get(0);
		} else {
			//Si no, sacamos los dos primeros arboles
			Arbol a1 = arbolProbabilidades.remove(0);
			Arbol a2 = arbolProbabilidades.remove(0);	
			//Y ponemos uno nuevo que tenga como raiz la suma de las raices y como subarboles los sacados.
			arbolProbabilidades.add(a1.sumar(a2));
			//Reordenamos la lista
			Collections.sort(arbolProbabilidades);
			//Calculamos la solucion recursivamente.
			return Arbol.construirArbol(arbolProbabilidades);
		}
	}
	
	/**
	 * 
	 * @return Devuelve el nodo raiz del arbol.
	 */
	public Racional getNodo(){
		return _nodo;
	}
	/**
	 * 
	 * @return Devuelve el subarbol izquierdo.
	 */
	public Arbol getHijoIzquierdo(){
		return _izq;
	}
	
	/**
	 * 
	 * @return Devuelve el subarbol derecho.
	 */
	public Arbol getHijoDerecho(){
		return _der;
	}
	
	/**
	 * Suma el arbol actual con el pasado.
	 * @param otro Arbol que se sumara al actual.
	 * @return Un nuevo arbol cuya raiz es la suma del actual y del pasado, y los hijos son el actual y el pasado.
	 */
	public Arbol sumar(Arbol otro){
		return new Arbol(_nodo.sumar(otro.getNodo()),otro,this);
	}
	
	/**
	 * Compara el arbol actual con el pasado. Compara las raices.
	 * Si son iguales devuelve 0, si el actual es menor, devuelve un numero negativo,
	 * si es mayor devuelve un numero positivo.
	 */
	public int compareTo(Arbol otro){
		return _nodo.compareTo(otro.getNodo());
	}
	
	/**
	 * 
	 * @return true si el arbol actual es una hoja, si no, false.
	 */
	public boolean esHoja(){
		return ((_izq == null) && (_der == null));
	}
	
	/**
	 * Obtiene la tabla de probabilidades y codigos para el arbol.
	 * @param buffer Numero cuyos bits representan el codigo de una probabilidad concreta. Debe iniciarse a 0.
	 * @param profundidad Profundidad de la probabilidad en el arbol. Indica el numero de bits de su codigo.
	 * @return la TablaCodigo con todo el codigo.
	 */
	public TablaCodigo obtenerCodigo(int buffer, int profundidad){
		//Si llegamos a una hoja
		if(esHoja()){
			//Obtenemos los bits del buffer como una cadena
			String codigo = Integer.toBinaryString(buffer);
			//Si no completa el numero de bits necesarios, rellenamos con ceros al principio.
			for(int i = codigo.length();i<profundidad;i++){
				codigo = "0" + codigo;
			}
			return new TablaCodigo(_nodo,codigo);
		} else {
			//Si no es una hoja, calculamos el codigo para los subarboles y los unimos en la misma tabla.
			return new TablaCodigo(_izq.obtenerCodigo(buffer<<1,profundidad+1),_der.obtenerCodigo((buffer<<1)+1,profundidad+1));
		}
	}
	
	public String toString(){
		if (esHoja()){
			return "[" + _nodo.toString() + "]";
		} else {
			return "[" + _nodo.toString() + ", " + _izq.toString() + ", " + _der.toString() + "]";
		}
	}

}

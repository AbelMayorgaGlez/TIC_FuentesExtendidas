package es.unileon.tic.fuentesExtendidas.auxiliar;

/** 
 *Clase que representa el tipo
 *"numeros racionales"
 *
 *@author Abel Mayorga Gonzalez
 *@author Alberto Rodriguez Gomez
 */
public class Racional implements Comparable<Racional>{
	
	/**
	 * Numerador del numero racional.
	 */
    private long numerador;
    /**
     * Denominador del numero racional.
     */
    private long denominador;

    /** Construye un numero racional equivalente
	al numero entero dado */
    public Racional(long num){
	numerador = num;
	denominador = 1;
    }
    /** Construye un numero racional con
	el numerador y el denominador dados.
	Si el denominador es 0, se envía un
	mensaje de error y el denominador se
	iguala a 1 
	* @throws ArithmeticException. Esta escepcion es lanzada cuando den = 0;
	*/ 
    public Racional(long num, long den) throws ArithmeticException{
	numerador = num;
	
	if(num == 0){
	    den = 1;
	}
	if(den == 0){
	    throw new ArithmeticException("Division por 0 no permitida");
	}
	else{
	    denominador = den;
	}
	//simplificar();//Las llamadas a simplificar estan desactivadas, ya que para este programa, nunca se va a simplificar una fraccion
    }

    /** Devuelve el maximo comun divisor de dos
       numeros por el algoritmo de euclides */
    private static long maxComunDivisor(long i, long j){
	if (j == 0){
	    return i;
	}
	else{
	    return maxComunDivisor(j, i % j);
	}
    }

    /** Devuelve el minimo comun multiplo de dos
	numeros utilizando la formula mcm(a,b)=a*b/mcd(a,b)*/
    private static long minComunMultiplo(long i, long j){
    	return (i * j) / maxComunDivisor(i,j);
    }

    /** Simplifica el numero racional */
    private void simplificar(){
    	long divisor = maxComunDivisor(numerador,denominador);
    	numerador /= divisor;
    	denominador /= divisor;
    }

    /** Devuelve un numero racional suma del numero
	racional actual con el numero racional dado */
    public Racional sumar(Racional sumando){
    	if(this.denominador == sumando.denominador){
    		return new Racional(this.numerador+sumando.numerador,this.denominador);
    	} else {
			Racional resultado = new Racional(1,1);
				
			resultado.denominador = minComunMultiplo(this.denominador, sumando.denominador);
			resultado.numerador = (this.numerador * resultado.denominador)/this.denominador + (sumando.numerador * resultado.denominador)/sumando.denominador;
		
			//resultado.simplificar();
		
			return resultado;
    	}
    }

    /** Devuelve un numero racional resta del numero
	racional actual con el numero racional dado */
    public Racional restar(Racional sustraendo){
		Racional sumando = new Racional(-sustraendo.numerador,sustraendo.denominador);
		return sumar(sumando);
    }

    /** Devuelve un numero racional producto del numero
	racional actual con el numero racional dado */
    public Racional multiplicar(Racional factor){
		Racional producto = new Racional(1,1);
	
		producto.denominador = this.denominador * factor.denominador;
		producto.numerador = this.numerador * factor.numerador;

	//producto.simplificar();

	return producto;
    }

    /** Devuelve un numero racional cociente del numero
	racional actual con el numero racional dado.
	Lanza ArithmeticException si se trata de dividir por 0 */
    public Racional dividir(Racional divisor) throws ArithmeticException{
		if (divisor.numerador == 0){
		    throw new ArithmeticException("Division por cero no permitida");
		}
		Racional aux = new Racional(divisor.denominador,divisor.numerador);
		return this.multiplicar(aux);
    }

    /** Compara el numero racional actual con el numero racional dado
	y devuelve true si son iguales. */
    public boolean equals(Racional otro){
    	return ((this.numerador == otro.numerador) && (this.denominador == otro.denominador));
    }

    /** Compara el numero racional actual con el numero racional dado
	y devuelve true si el actual es mayor que el dado. */
    public boolean mayorQue(Racional otro){

		Racional aux1 = new Racional(this.numerador,this.denominador);
		Racional aux2 = new Racional(otro.numerador,otro.denominador);
	
		aux1.denominador=aux2.denominador=minComunMultiplo(this.denominador,otro.denominador);
		aux1.numerador=(this.numerador * aux1.denominador)/this.denominador;
		aux2.numerador=(otro.numerador * aux2.denominador)/otro.denominador;
	
		return (aux1.numerador>aux2.numerador);
    }
    
    /** Compara el numero racional actual con el numero racional dado
	y devuelve true si el actual es menor que el dado. */
    public boolean menorQue(Racional otro){
	return otro.mayorQue(this);
    }

    /**
     * Compara el numero racional actual con el numero racional dado
     * y devuelve 0 si son iguales, un numero negativo si el actual es menor
     * o un numero positivo si el actual es mayor.
     */
    public int compareTo(Racional otro){
    	if(this.denominador == otro.denominador){
    		return Long.valueOf(this.numerador).compareTo(Long.valueOf(otro.numerador));
    	} else if (this.numerador == otro.numerador){
    		return -Long.valueOf(this.denominador).compareTo(Long.valueOf(otro.denominador));
    	} else {
    		return Float.valueOf(this.floatValue()).compareTo(Float.valueOf(otro.floatValue()));
    	}
    }

    /** Devuelve un String representacion del numero racional actual. */
    public String toString(){
	if(denominador == 1){
	    return "" + numerador;
	} else {
	    return numerador + "/" + denominador;
	}
    }
    
    public float floatValue(){
    	return (float)numerador/(float)denominador;
    }

}

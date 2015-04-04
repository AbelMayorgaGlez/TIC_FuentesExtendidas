package es.unileon.tic.fuentesExtendidas.huffman;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import es.unileon.tic.fuentesExtendidas.auxiliar.Racional;

public class FuenteTest {
	
	Fuente _fuente1_2, _fuente1_3, _fuente1_4;
	@Before
	public void setUp() throws Exception {
		_fuente1_2 = new Fuente(new Racional(1,2));
		_fuente1_3 = new Fuente(new Racional(1,3));
		_fuente1_4 = new Fuente(new Racional(1,4));
	}
	
	@Test
	public void longitud1_2Orden1Test(){
		System.out.println(_fuente1_2);
		assertEquals(1,_fuente1_2.getEficacia(),0);
	}
	
	@Test
	public void longitud1_2Orden2Test(){
		Fuente o2 = _fuente1_2.extenderFuente();
		System.out.println(o2);
		assertEquals(1,_fuente1_2.getEficacia(),0);
	}
	
	@Test
	public void longitud1_3Orden1Test(){
		System.out.println(_fuente1_3);
		assertEquals(0.918,_fuente1_3.getEficacia(),0.001);
	}
	
	@Test
	public void longitud1_3Orden2Test(){
		Fuente f = _fuente1_3.extenderFuente();
		assertEquals(0.972,f.getEficacia(),0.001);
	}
	
	@Test
	public void longitud1_3Orden3Test(){
		Fuente f = _fuente1_3.extenderFuente(3);
		assertEquals(0.978,f.getEficacia(),0.001);
	}
	
	@Test
	public void longitud1_3Orden4Test(){
		Fuente f = _fuente1_3.extenderFuente(4);
		System.out.println("Eficacia: " + f.getEficacia() + "\n");
	}
	
	@Test
	public void longitud1_3Orden5Test(){
		Fuente f = _fuente1_3.extenderFuente(5);
		System.out.println("Eficacia: " + f.getEficacia() + "\n");
	}
	
	@Test
	public void longitud1_3Orden6Test(){
		Fuente f = _fuente1_3.extenderFuente(6);
		System.out.println("Eficacia: " + f.getEficacia() + "\n");
	}
	
	@Test
	public void longitud1_3Orden7Test(){
		Fuente f = _fuente1_3.extenderFuente(7);
		System.out.println("Eficacia: " + f.getEficacia() + "\n");
	}
	
	@Test
	public void longitud1_3Orden8Test(){
		Fuente f = _fuente1_3.extenderFuente(8);
		System.out.println("Eficacia: " + f.getEficacia() + "\n");
	}
	
	@Test
	public void longitud1_3Orden9Test(){
		Fuente f = _fuente1_3.extenderFuente(9);
		System.out.println("Eficacia: " + f.getEficacia() + "\n");
	}
	
	@Test
	public void longitud1_3Orden10Test(){
		Fuente f = _fuente1_3.extenderFuente(10);
		System.out.println("Eficacia: " + f.getEficacia() + "\n");
	}
	
	@Test
	public void longitud1_4Orden1Test(){
		assertEquals(0.811,_fuente1_4.getEficacia(),0.001);
	}
	
	@Test
	public void longitud1_4Orden2Test(){
		Fuente f = _fuente1_4.extenderFuente();
		assertEquals(0.961,f.getEficacia(),0.001);
	}
	@Test
	public void longitud1_4Orden3Test(){
		Fuente f = _fuente1_4.extenderFuente(3);
		assertEquals(0.985,f.getEficacia(),0.001);
	}
	@Test
	public void longitud1_9Orden10Test(){
		Fuente f = new Fuente(new Racional(1,9)).extenderFuente(10);
		assertEquals(0.986,f.getEficacia(),0.001);
	}
	@Test
	public void longitud1_10Orden10Test(){
		Fuente f = new Fuente(new Racional(1,10)).extenderFuente(10);
		assertEquals(0.983, f.getEficacia(),0.001);
	}
}

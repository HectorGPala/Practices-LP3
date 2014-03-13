package Dibujo;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class FloydTest extends TestCase {

	public FloydTest(String s){
		super(s);
	}

	
	public void  testFloyd(){
		Nodo a=new Nodo(7,3,"a" );
		Nodo b=new Nodo(7,3,"b" );
		Nodo c=new Nodo(7,3,"c" );
		Nodo d=new Nodo(7,3,"d" );
		Nodo e=new Nodo(7,3,"e" );
		Grafo g= new Grafo();
		g.addArco(a,b,10);
		g.addArco(d,b,4);
		g.addArco(d,e,3);
		g.addArco(e,c,6);
		g.addArco(c,b,2);
		g.addArco(b,c,1);
		g.addArco(c,a,5);
		g.addArco(b,e,23);
		g.addNodo(a.getX(), a.getY(), "a");
		g.addNodo(b.getX(), b.getY(), "b");
		g.addNodo(c.getX(), c.getY(), "c");
		g.addNodo(d.getX(), d.getY(), "d");
		g.addNodo(e.getX(),e.getY(),"e");	
		
		
		//int[][]expected = {{0,0,0,0,0,0},{0,0,10,1000000,11,33},{0,6,0,1000000,1,23},{0,10,4,0,5,3},{0,5,2,1000000,0,25},{0,11,8,1000000,6,0}};
		Floyd f= new Floyd(g);		
		//int[][]eeee = {{0,0,0,0,0,0},{0,1,2,0,0,0},{0,0,2,0,4,5},{0,0,2,3,0,5},{0,1,2,0,4,0},{0,0,0,0,4,5}};
		//f.distancias = eeee;
		f.algFloyd();
		Grafo expected = new Grafo();
		expected.addArco(a,c,2);
		expected.addArco(c,b,1);
		expected.addArco(b,d,1);
		expected.addNodo(a.getX(),a.getY(),a.getNombre());
		expected.addNodo(c.getX(),c.getY(),c.getNombre());
		expected.addNodo(b.getX(),b.getY(),b.getNombre());
		expected.addNodo(d.getX(),d.getY(),d.getNombre());
		Grafo fl = f.dameGrafo(a,d);
		Assert.assertEquals(expected,fl);
	}
	
	/*public void  testFloyd1(){
		Nodo a=new Nodo(7,3,"a" );
		Nodo b=new Nodo(7,3,"b" );
		Nodo c=new Nodo(7,3,"c" );
		Nodo d=new Nodo(7,3,"d" );
		Nodo e=new Nodo(7,3,"e" );
		Nodo f=new Nodo(7,3,"f" );
		
		Grafo g= new Grafo();
		g.addArco(a,b,3);
		g.addArco(a,c,5);
		g.addArco(a,d,1);
		g.addArco(b,a,3);
		g.addArco(b,e,9);
		g.addArco(c,a,5);
		g.addArco(c,d,7);
		g.addArco(c,e,7);
		g.addArco(c,f,1);
		g.addArco(d,a,1);
		g.addArco(d,c,7);
		g.addArco(d,f,4);
		g.addArco(e,b,9);
		g.addArco(e,c,7);
		g.addArco(f,c,1);
		g.addArco(f,d,4);
	
		g.addNodo(a.getX(), a.getY(), "a");
		g.addNodo(b.getX(), b.getY(), "b");
		g.addNodo(c.getX(), c.getY(), "c");
		g.addNodo(d.getX(), d.getY(), "d");
		g.addNodo(e.getX(),e.getY(),"e");	
		g.addNodo(f.getX(),f.getY(),"f");
		

		int[][] expected = {{0,0,0,0,0,0,0},{0,0,3,5,1,12,5},{0,3,0,8,4,9,8},{0,5,8,0,5,7,1},{0,1,4,5,0,12,4},{0,12,9,7,12,0,8},{0,5,8,1,4,8,0}};
		
		Floyd fl= new Floyd(g);
		fl.algFloyd();
		Assert.assertEquals(expected,fl.distancias);	
	}*/

}

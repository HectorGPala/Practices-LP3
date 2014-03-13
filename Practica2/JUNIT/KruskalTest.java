package Dibujo;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class KruskalTest extends TestCase {


	public KruskalTest(String s){
		super(s);
	}

	
	public void  testKruskal(){

		Nodo a=new Nodo(7,3,"a" );
		Nodo b=new Nodo(7,3,"b" );
		Nodo c=new Nodo(7,3,"c" );
		Nodo d=new Nodo(7,3,"d" );
		Arco ar1 = new Arco(a,b,4);
		Arco ar2 = new Arco(a,c,2);
		Arco ar3 = new Arco(b,c,3);
		Arco ar4 = new Arco(c,b,1);
		Arco ar5 = new Arco(b,d,1);
		Arco ar6 = new Arco(c,d,5);
		Grafo g= new Grafo();
		g.addArco(a,b,4);
		g.addArco(a,c,2);
		g.addArco(b,c,3);
		g.addArco(c,b,1);
		g.addArco(b,d,1);
		g.addArco(c,d,5);
		g.addNodo(a.getX(), a.getY(), "a");
		g.addNodo(b.getX(), b.getY(), "b");
		g.addNodo(c.getX(), c.getY(), "c");
		g.addNodo(d.getX(), d.getY(), "d");
		
		//////////////////////////////////////////7
		
		
		Nodo a1=new Nodo(7,3,"a" );
		Nodo b1=new Nodo(7,3,"b" );
		Nodo c1=new Nodo(7,3,"c" );
		Nodo d1=new Nodo(7,3,"d" );
		Nodo e1=new Nodo(7,3,"e" );
		Grafo g1= new Grafo();
		g1.addArco(a1,b1,10);
		g1.addArco(d1,b1,4);
		g1.addArco(d1,e1,3);
		g1.addArco(e1,c1,6);
		g1.addArco(c1,b1,2);
		g1.addArco(b1,c1,1);
		g1.addArco(c1,a1,5);
		g1.addArco(b1,e1,23);
		Arco ar11 = new Arco(a1,b1,10);
		Arco ar12 = new Arco(d1,b1,4);
		Arco ar13 = new Arco(d1,e1,3);
		Arco ar14 = new Arco(e1,c1,6);
		Arco ar15 = new Arco(c1,b1,2);
		Arco ar16 = new Arco(b1,c1,1);
		Arco ar17 = new Arco(c1,a1,5);
		Arco ar18= new Arco(b1,e1,23);
		g1.addNodo(a1.getX(), a1.getY(), "a");
		g1.addNodo(b1.getX(), b1.getY(), "b");
		g1.addNodo(c1.getX(), c1.getY(), "c");
		g1.addNodo(d1.getX(), d1.getY(), "d");
		g1.addNodo(e1.getX(),e1.getY(),"e");
		
		ArrayList<Arco> expected = new ArrayList<Arco>();
		expected.add(ar4);
		expected.add(ar5);
		expected.add(ar2);
		
		
		
		
		ArrayList<Arco> expected1 = new ArrayList<Arco>();
		expected1.add(ar16);
		expected1.add(ar13);
		expected1.add(ar12);
		expected1.add(ar17);
		
		
		Kruskal k= new Kruskal(g);
		Assert.assertEquals(expected,k.getArray());	
		
		Kruskal k1= new Kruskal(g1);
		Assert.assertEquals(expected1,k1.getArray());	
		
		
		
		
		
		
	}
	
	
}

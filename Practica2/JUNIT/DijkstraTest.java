package Dibujo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class DijkstraTest extends TestCase{

	public DijkstraTest(String s) {
		super(s);
	}

	//@Test
	
public void testDijkstra() {
		Nodo a=new Nodo(7,55,"a" );
		Nodo b=new Nodo(7,33,"b" );
		Nodo c=new Nodo(7,11,"c" );
		Nodo d=new Nodo(7,22,"d" );
		Grafo g= new Grafo();
		g.addNodo(a.getX(), a.getY(), "a");
		g.addNodo(b.getX(), b.getY(), "b");
		g.addNodo(c.getX(), c.getY(), "c");
		g.addNodo(d.getX(), d.getY(), "d");
		g.addArco(a,b,4);
		g.addArco(a,c,2);
		g.addArco(b,c,3);
		g.addArco(c,b,1);
		g.addArco(b,d,1);
		g.addArco(c,d,5);
		
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
		g1.addNodo(a1.getX(), a1.getY(), "a");
		g1.addNodo(b1.getX(), b1.getY(), "b");
		g1.addNodo(c1.getX(), c1.getY(), "c");
		g1.addNodo(d1.getX(), d1.getY(), "d");
		g1.addNodo(e1.getX(),e1.getY(),"e");

		
		
		Dijkstra dijks= new Dijkstra(g, a, d);
		Dijkstra dijks1= new Dijkstra(g1, d1, a1);
		
		Map<Nodo,Nodo> expected= new HashMap<Nodo,Nodo>();
		Map<Nodo,Nodo> expected1= new HashMap<Nodo,Nodo>();
		

		expected1.put(d1, null);
		expected1.put(b1, d1);
		expected1.put(c1, b1);
		expected1.put(a1, c1);
		expected1.put(e1, d1);
		dijks1.algDijkstra();
		
		expected.put(a, null);
		expected.put(b, c);
		expected.put(c, a);
		expected.put(d, b);
		dijks.algDijkstra();
		
		
	
		
		Assert.assertEquals(expected,dijks.ruta);	
		Assert.assertEquals(expected1,dijks1.ruta);
	}
	
	
	
	
	
}

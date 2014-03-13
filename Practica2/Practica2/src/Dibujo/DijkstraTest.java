package Dibujo;

//import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import junit.framework.TestCase;
//import org.junit.Test;

public class DijkstraTest extends TestCase{

	public DijkstraTest(String s){
		super(s);
	}
	
	public void testDijkstra1(){
		Nodo a = new Nodo(7,55,"a",1);
		Nodo b = new Nodo(7,33,"b",2);
		Nodo c = new Nodo(7,11,"c",3);
		Nodo d = new Nodo(7,22,"d",4);
		Nodo e = new Nodo(7,55,"e",5);
		Grafo g= new Grafo();
		g.addNodo(a.getX(),a.getY(),"a");
		g.addNodo(b.getX(),b.getY(),"b");
		g.addNodo(c.getX(),c.getY(),"c");
		g.addNodo(d.getX(),d.getY(),"d");
		g.addNodo(e.getX(),e.getY(),"e");
		g.addArco(a,b,4);
		g.addArco(a,c,2);
		g.addArco(b,c,3);
		g.addArco(c,b,1);
		g.addArco(b,d,1);
		g.addArco(c,d,5);
		g.addArco(d,e,3);
		Map<Nodo,Nodo> expected= new HashMap<Nodo,Nodo>();
		expected.put(a,null);
		expected.put(b,c);
		expected.put(c,a);
		expected.put(d,b);
		expected.put(e,d);
		Dijkstra dijks= new Dijkstra(g,a,e);
		dijks.algDijkstra();
		Assert.assertEquals(expected,dijks.ruta);
	}
	
	public void testDijkstra2(){
		Nodo a = new Nodo(7,3,"a",1);
		Nodo b = new Nodo(7,3,"b",2);
		Nodo c = new Nodo(7,3,"c",3);
		Nodo d = new Nodo(7,3,"d",4);
		Nodo e = new Nodo(7,3,"e",5);
		Grafo g= new Grafo();
		g.addArco(a,b,10);
		g.addArco(d,b,4);
		g.addArco(d,e,3);
		g.addArco(e,c,6);
		g.addArco(c,b,2);
		g.addArco(b,c,1);
		g.addArco(c,a,5);
		g.addArco(b,e,23);
		g.addNodo(a.getX(), a.getY(),"a");
		g.addNodo(b.getX(),b.getY(),"b");
		g.addNodo(c.getX(),c.getY(),"c");
		g.addNodo(d.getX(),d.getY(),"d");
		g.addNodo(e.getX(),e.getY(),"e");		
		Map<Nodo,Nodo> expected = new HashMap<Nodo,Nodo>();
		expected.put(d,null);
		expected.put(b,d);
		expected.put(c,b);
		expected.put(a,c);
		expected.put(e,d);
		Dijkstra dijks= new Dijkstra(g,d,a);
		dijks.algDijkstra();			
		Assert.assertEquals(expected,dijks.ruta);
	}
	
	public void testDijkstra3(){
		Nodo a=new Nodo(7,3,"a",1);
		Nodo b=new Nodo(7,3,"b",2);
		Nodo c=new Nodo(7,3,"c",3);
		Nodo d=new Nodo(7,3,"d",4);
		Nodo e=new Nodo(7,3,"e",5);
		Nodo f=new Nodo(7,3,"f",6);		
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
		g.addNodo(a.getX(),a.getY(),"a");
		g.addNodo(b.getX(),b.getY(),"b");
		g.addNodo(c.getX(),c.getY(),"c");
		g.addNodo(d.getX(),d.getY(),"d");
		g.addNodo(e.getX(),e.getY(),"e");	
		g.addNodo(f.getX(),f.getY(),"f");
		Map<Nodo,Nodo> expected = new HashMap<Nodo,Nodo>();
		expected.put(a,null);
		expected.put(b,a);
		expected.put(c,a);
		expected.put(d,a);
		expected.put(e,b);
		expected.put(f,d);		
		Dijkstra dijks= new Dijkstra(g,a,f);
		dijks.algDijkstra();
		Assert.assertEquals(expected,dijks.ruta);		
	}
}
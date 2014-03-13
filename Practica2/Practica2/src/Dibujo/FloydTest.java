package Dibujo;

//import static org.junit.Assert.*;
//import java.util.ArrayList;
import junit.framework.Assert;
import junit.framework.TestCase;
//import org.junit.Test;

public class FloydTest extends TestCase{

	public FloydTest(String s){
		super(s);
	}
	
	public void testFloyd1(){
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
		int[][]expected = {{0,0,0,0,0,0},{0,0,3,2,4,7},{0,1000,0,3,1,4},{0,1000,1,0,2,5},{0,1000,1000,1000,0,3},{0,1000,1000,1000,1000,0}};
		Floyd f= new Floyd(g);
		f.algFloyd();
		Assert.assertTrue(equals(expected,f.distancias));
	}
	
	public boolean equals(int[][] a,int[][] b){
		int n = a.length;
		boolean r = true;
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				r = r && (a[i][j] == b[i][j]);
		return r;
	}
	
	public void testFloyd2(){
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
		g.addNodo(d.getX(),d.getY(),"d");
		g.addNodo(c.getX(),c.getY(),"c");		
		g.addNodo(e.getX(),e.getY(),"e");
		int[][] expected = {{0,0,0,0,0,0},{0,0,10,1000,11,33},{0,6,0,1000,1,23},{0,10,4,0,5,3},{0,5,2,1000,0,25},{0,11,8,1000,6,0}};
		Floyd f= new Floyd(g);
		f.algFloyd();
		Assert.assertTrue(equals(expected,f.distancias));		
	}
	
	public void  testFloyd3(){
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
		int[][] expected = {{0,0,0,0,0,0,0},{0,0,3,5,1,12,5},{0,3,0,8,4,9,8},{0,5,8,0,5,7,1},{0,1,4,5,0,12,4},{0,12,9,7,12,0,8},{0,5,8,1,4,8,0}};
		Floyd fl= new Floyd(g);
		fl.algFloyd();
		Assert.assertTrue(equals(expected,fl.distancias));	
	}

}
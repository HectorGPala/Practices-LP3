package Dibujo;

import java.util.*;
import javax.swing.JOptionPane;

//import javax.swing.JOptionPane;

public class Floyd {
	int[][] distancias;
	private int[][] recorrido;
	private Map<Integer,Nodo> indiceN;
	private Map<Nodo,Integer> indiceI;
	private Map<Arco,Integer> distanciasArcos;
	Grafo g;
	
	public Floyd(Grafo g){
		distancias = new int[g.numNodos()+1][g.numNodos()+1];
		recorrido = new int[g.numNodos()+1][g.numNodos()+1];
		distanciasArcos = new HashMap<Arco,Integer>();
		indiceN = new HashMap<Integer,Nodo>();
		indiceI = new HashMap<Nodo,Integer>();
		Iterator<Arco> itA = g.iteratorArco();
		while(itA.hasNext()){
			Arco aux = itA.next();
			distanciasArcos.put(aux,aux.getPeso());
		}
		this.g = g;
		Iterator<Nodo> it1 = g.iteratorNodo();
		while(it1.hasNext()){
			Nodo n1 = it1.next();
			indiceN.put(n1.getID(),n1);
			indiceI.put(n1,n1.getID());
			Iterator<Nodo> it2 = g.iteratorNodo();
			while(it2.hasNext()){
				Nodo n2 = it2.next();
				if(n1.equals(n2)){
					distancias[n1.getID()][n2.getID()] = 0;
					recorrido[n1.getID()][n2.getID()] = 0 ;
				}
				else{
				if(distanciasArcos.get(new Arco(n1,n2,0)) != null)
					distancias[n1.getID()][n2.getID()] = distanciasArcos.get(new Arco(n1,n2,0));
				else
					distancias[n1.getID()][n2.getID()] = 1000;
				}
				if(distancias[n1.getID()][n2.getID()] == 1000)
					recorrido[n1.getID()][n2.getID()] = 0;
				else
					recorrido[n1.getID()][n2.getID()] = n2.getID();
				
			}
		}
	}
	
	public void algFloyd(){
		int n_nodos = g.numNodos();
		for(int k = 1; k <= n_nodos; k++)
			for(int i = 1; i <= n_nodos; i++)
				for(int j = 1; j <= n_nodos; j++){
					if((distancias[i][k]+distancias[k][j] < distancias[i][j]) && i != k && j != k){
						distancias[i][j] = distancias[i][k] + distancias[k][j];
						recorrido[i][j] = recorrido[i][k];
					}
	            }
		
	}
	
	public Grafo dameGrafo(Nodo o,Nodo d){
		if(o.equals(d)){
			Grafo gF = new Grafo();
			gF.addNodo(o.getX(),o.getY(),o.getNombre());
			return gF;
		}		
		else
		if(recorrido[o.getID()][d.getID()] == 0){
			JOptionPane.showMessageDialog(null,"No hay camino posible");
			return null;
		}
		else{
			Nodo ant = o;
			Grafo gF = new Grafo();
			int indiceO = indiceI.get(o);
			int indiceD = indiceI.get(d);
			int i = indiceO;
			int j = indiceD;
			Nodo aux = o;
			gF.addNodo(o.getX(),o.getY(),o.getNombre());
			while(!indiceN.get(recorrido[i][j]).equals(d)){
				aux = indiceN.get(recorrido[i][j]);
				gF.addNodo(aux.getX(),aux.getY(),aux.getNombre());
				gF.addArco(ant,aux,distanciasArcos.get(new Arco(ant,aux,0)));
				i = indiceI.get(aux);
				ant = aux;
			}
			gF.addNodo(d.getX(),d.getY(),d.getNombre());
			gF.addArco(aux,d,distanciasArcos.get(new Arco(aux,d,0)));			
			return gF;
		}
	}
}

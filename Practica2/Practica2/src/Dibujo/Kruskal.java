package Dibujo;

import java.util.*;

public class Kruskal{	
	Map<Nodo,Grupo> bosque;	
	PriorityQueue<Arco> s;	
	ArrayList<Arco> solucion;
	
	public Kruskal(Grafo g){		
		bosque = new HashMap<Nodo,Grupo>(); 
		solucion= new ArrayList<Arco>();		
		s = new PriorityQueue<Arco>(1,new Comparator<Arco>(){
			public int compare(Arco a1,Arco a2){
				int i = a1.getPeso() - a2.getPeso();
				return i;
			}
		});	
		Iterator<Nodo> it = g.iteratorNodo();
		while(it.hasNext()){
			Nodo aux = it.next();
			Grupo gr = new Grupo();
			bosque.put(aux, gr);			
		}
		Iterator<Arco> it1 = g.iteratorArco();
		while(it1.hasNext()){
			Arco aux2 = it1.next();
			//int i = aux2.getPeso();		
			s.add(aux2);			
		}		
		while(!s.isEmpty()){			
			Arco aux3 = s.poll();
			Grupo g1= bosque.get(aux3.getOrigen());
			Grupo g2= bosque.get(aux3.getDestino());
			if (g1.dameGrupo()!=g2.dameGrupo()){
				g1.unionGrupo(g2);
				solucion.add(aux3);			
			}
		}	
	}	 
	
	ArrayList<Arco> getArray(){
		return solucion;
	}	
	
	public Grafo dameGrafo(){
		Grafo gK= new Grafo();
		Iterator<Arco> it3 = solucion.iterator();
		while(it3.hasNext()){			
			Arco a = it3.next();
			gK.addArco(a.getOrigen(), a.getDestino(), a.getPeso());	
		}	
		return gK;		
	}
}
	
	

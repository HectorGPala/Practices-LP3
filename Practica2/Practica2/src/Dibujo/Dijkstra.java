package Dibujo;

import java.util.*;
import javax.swing.JOptionPane;

public class Dijkstra {
	Set<Nodo> yaVisitados;
	Map<Nodo,Integer> camino;
	Map<Nodo,Nodo> ruta;
	Map<Arco,Integer> distancias;
	PriorityQueue<Nodo> aVisitar;
	Grafo g;
	Nodo origen,destino;
	
	public Dijkstra(Grafo g,Nodo origen,Nodo destino){
		this.origen = origen;
		this.destino = destino;
		this.g = g;
		yaVisitados = new HashSet<Nodo>();
		camino = new HashMap<Nodo,Integer>();
		ruta = new HashMap<Nodo,Nodo>();
		distancias = new HashMap<Arco,Integer>();
		Comparator<Nodo> miComparador = new Comparator<Nodo>(){
			public int compare(Nodo n1,Nodo n2){
				int i = camino.get(n1) - camino.get(n2);
				return i;
			}
		};
		aVisitar = new PriorityQueue<Nodo>(1,miComparador);
		Iterator<Nodo> it = g.iteratorNodo();
		while(it.hasNext()){//iniciar camino,ruta y aVisitar (yaVisitados se queda vacio)
			Nodo aux = it.next();
			if(!origen.equals(aux)){
				camino.put(aux,Integer.MAX_VALUE);
				ruta.put(aux,null);
			}
			else{
				camino.put(origen,0);
				aVisitar.add(origen);
				ruta.put(origen,null);
			}			
		}
		Iterator<Arco> it2 = g.iteratorArco();
		while(it2.hasNext()){//creo la matriz de distancias directas
			Arco aux2 = it2.next();
			distancias.put(aux2,aux2.getPeso());
		}
	}
	
	public void relajarVertices(Nodo u){
		Iterator<Nodo> it = g.iteratorNodo();
		yaVisitados.add(u);
		while(it.hasNext()){
			Nodo aux = it.next();
			if(!yaVisitados.contains(aux)){
				Integer d = distancias.get(new Arco(u,aux,0));
				if(d != null){
					int dis = camino.get(u) + d;
					if (camino.get(aux) > dis){
						camino.put(aux,dis);
						ruta.put(aux,u);
						if(aVisitar.contains(aux))
							aVisitar.remove(aux);
						aVisitar.add(aux);
					}
				}
			}
		}
	}
	public void algDijkstra(){
		while(!aVisitar.isEmpty()){
			Nodo u = aVisitar.poll();
			//JOptionPane.showMessageDialog(null,"Visito: "+u.getNombre());
			relajarVertices(u);
		}		
	}
	
	public Grafo dameGrafo(){
		Nodo o = ruta.get(destino);
		Nodo d = destino;
		if(origen.equals(destino)){
			Grafo gD = new Grafo();
			gD.addNodo(origen.getX(),origen.getY(),origen.getNombre());
			return gD;
		}
		if(o == null){
			JOptionPane.showMessageDialog(null,"No hay camino posible");
			return null;
		}
		else{
			Grafo gD = new Grafo();
			gD.addNodo(destino.getX(),destino.getY(),destino.getNombre());
			while(o != null){
			gD.addNodo(o.getX(),o.getY(),o.getNombre());
			gD.addArco(o,d,distancias.get(new Arco(o,d,0)));
			d = o;
			o = ruta.get(o);
			}
			return gD;
		}
	}
}

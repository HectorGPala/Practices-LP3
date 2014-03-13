package Dibujo;

import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Grafo{
	Set<Arco> arcos;
	Set<Nodo> nodos;
	int numArcos;
	int numNodos;
		
	public Grafo(){
		arcos = new HashSet<Arco>();
		nodos = new HashSet<Nodo>();
 		numArcos=0;
		numNodos=0;			
	}
	
	public void iniciar(){
		arcos = new HashSet<Arco>();
		nodos = new HashSet<Nodo>();
 		numArcos=0;
		numNodos=0;
	}
	
	public void abrir(String s){
		Scanner sc = null;
		try{
			File f = new File(s);
			sc = new Scanner(f);
			while (sc.hasNext()){
				int n_nodos = Integer.valueOf(sc.nextLine());
				for(int i = 0; i < n_nodos;i++){
					String nombre = sc.nextLine();
					int X = Integer.valueOf(sc.nextLine());
					int Y = Integer.valueOf(sc.nextLine());
					addNodo(X,Y,nombre);
				}
				int n_arcos = Integer.valueOf(sc.nextLine());
				for(int i = 0; i < n_arcos;i++){
					int peso = Integer.valueOf(sc.nextLine());
					String o = sc.nextLine();
					Nodo origen = buscarNodo(o);
					String d = sc.nextLine();
					Nodo destino = buscarNodo(d);
					addArco(origen, destino, peso);
				}
			}		
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error de apertura de fichero");
		}
	}
		
	public int numNodos() {return numNodos;}	
	public int numArcos() {return numArcos;}
	public Iterator<Nodo> iteratorNodo(){return nodos.iterator();}	
	public Iterator<Arco> iteratorArco(){return arcos.iterator();}
	
	public Nodo buscarNodo(String s){
		Iterator<Nodo> it = nodos.iterator();
		while(it.hasNext()){
			Nodo aux = it.next();
			if(aux.getNombre().equals(s)){
				return aux;
			}
		}
		return null;
	}
	
	public void addNodo(int posX,int posY,String nombre){
		Nodo n = new Nodo(posX,posY,nombre,numNodos+1);
		if(nodos.add(n)){
			numNodos++;
		}
		else
			JOptionPane.showMessageDialog(null,"Ya existe!");
	}
	public boolean addArco(Nodo o,Nodo d,int peso){
		Arco n = new Arco(o,d,peso);
		numArcos++;
		return arcos.add(n);
	}
	
	public void eliminarNodo(Nodo n){		
		int nID = n.getID();
		nodos.remove(n);
		Iterator<Nodo> it = iteratorNodo();
		while(it.hasNext()){
			Nodo aux = it.next();
			if(aux.getID() > nID)
				aux.setID(aux.getID()-1);
		}
		numNodos--;	
	}
		
	public void eliminarArco(Arco a){
		if(arcos.remove(a))
			numArcos--;
	}
	
	public void eliminarArco(Nodo origen,Nodo destino){
		Iterator<Arco> it = this.iteratorArco();
		boolean encontrado = false;		
		while(it.hasNext()&&!encontrado){
			Arco aux1 = it.next();
			if(aux1.equals(new Arco(origen,destino,0))){
				this.eliminarArco(aux1);
				encontrado= true;
			}			
		}
		if (!encontrado)JOptionPane.showMessageDialog(null,"No estan comunidados");
	}	
	
	public void guardar(String s){
		try{
			FileWriter f = new FileWriter(s);
	        PrintWriter pw = new PrintWriter(f);
	        Iterator<Nodo> it = nodos.iterator();
	        pw.println(numNodos);
	        while(it.hasNext()){
	        	Nodo aux = it.next();
	        	pw.println(aux.getNombre());
	        	pw.println(aux.getX());
	        	pw.println(aux.getY());
	        }
	        Iterator<Arco> itA = arcos.iterator();
	        pw.println(numArcos);
	        while(itA.hasNext()){
	        	Arco aux = itA.next();
	        	pw.println(aux.getPeso());
	        	pw.println(aux.getOrigen().getNombre());
	        	pw.println(aux.getDestino().getNombre());
	        }
	        f.close();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error de escritura");			
		}
	}	
}

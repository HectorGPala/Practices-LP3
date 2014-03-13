package Dibujo;

public class Arco implements Comparable <Arco>{
	Nodo origen;
	Nodo destino;
	int peso;
	
	public Arco(Nodo o,Nodo d,int p){
		origen = o;
		destino = d;
		peso = p;
	}
	public Nodo getOrigen(){
		return origen;
	}
	
	public Nodo getDestino(){
		return destino;
	}
	
	public int getPeso(){
		return peso;
	}
	
	public int compareTo(Arco arg0) {
		if (this.peso > arg0.peso) return 1;
		else if (this.peso < arg0.peso) return -1;		
		return 0;
	}
	
	/*public boolean mismo(Arco arg0){		
		return origen.equals(arg0.origen)&& destino.equals(arg0.destino);		
	}sobra completamente*/
	
	public boolean apareceEnArco(Nodo n){		
		return origen.equals(n)||destino.equals(n);		
	}
	/*public boolean contieneNodos(Nodo o,Nodo d){		
		return origen.equals(o)&& destino.equals(d);
	}*/
	
	public boolean equals(Object obj){
		Arco aux = (Arco) obj;
		return origen.equals(aux.origen) && destino.equals(aux.destino);
	}
	
	public int hashCode() {
		return this.getOrigen().hashCode()*33;
		}
}

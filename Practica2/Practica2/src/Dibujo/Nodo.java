package Dibujo;

public class Nodo {
	private int posX;
	private int posY;
	private String nombre;
	private int id;
 
	public Nodo(int x,int y, String n,int i){
		posX=x;
		posY=y;
		nombre=n;
		id = i;
	}
	public int getX(){return posX;}
	public int getY(){return posY;}
	public String getNombre(){return nombre;}
	public int getID(){return id;}
	public void setID(int n){id = n;}
	
	public boolean equals(Object obj){
			Nodo aux = (Nodo) obj;
			return aux.nombre.equals(nombre);
	}
	public int hashCode() {
		return this.getNombre().hashCode()*33;
		} 
}

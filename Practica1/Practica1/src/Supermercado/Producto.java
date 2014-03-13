package Supermercado;

public class Producto {
	String nombre;
	float precio;
	int cantidad;
	
	public Producto(String s, float n){
		nombre = s;
		precio = n;		
	}
	
	public String getNombre(){
		return nombre;
	}
	public float getPrecio(){
		return precio;
	}
}

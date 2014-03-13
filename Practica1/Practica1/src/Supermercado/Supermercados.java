package Supermercado;

import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;



public class Supermercados {
	private Categoria gama[];

	public Supermercados(){
		gama = new Categoria[5];
		for(int i = 0; i < 5;i++)
			gama[i]= null;
	}
	public void inicializar(File f,int i){
		Scanner sc = null;
		try{
			sc = new Scanner(f);
			gama[i] = new Categoria();
			while (sc.hasNext()){
				String l = sc.nextLine();
				String p = "0";
				if(sc.hasNext())
					p = sc.nextLine();
				float precio = Float.parseFloat(p)/100;
				if(precio < 0)
					precio *= (-1);
				Producto prod = new Producto(l,precio);
				if (gama[i].getNumProds() != gama[i].max){
					gama[i].setProductos(prod);
				}
			}		
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null,"No existe el fichero");
		}		
	}
	
	public void finalizar(){
		for(int i = 0; i < 5;i++)
			gama[i]= null;
	}
	
	public void setIesimo(Categoria o,int i) {
		gama[i] = o;
	}

	public Categoria getIesimo(int i) {
		return gama[i];
	}
}

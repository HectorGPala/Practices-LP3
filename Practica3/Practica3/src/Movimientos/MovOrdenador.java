package Movimientos;

public class MovOrdenador{
	private int valor;
	private int oX;
	private int oY;
	private int dX;
	private int dY;
	
	public MovOrdenador(){
		valor = Integer.MIN_VALUE;
		oX = -1;
		oY = -1;
		dX = -1;
		dY = -1;
	}
	
	public void setValor(int h){
		valor = h;
	}
	
	public void setMov(int x,int y,int desX,int desY){
		oX = x;
		oY = y;
		dX = desX;
		dY = desY;
	}
	
	public int getValor(){return valor;}
	public int getOrX(){return oX;}
	public int getOrY(){return oY;}
	public int getDesX(){return dX;}
	public int getDesY(){return dY;}
	public boolean hayMovimiento(){return oX != (-1);}
}

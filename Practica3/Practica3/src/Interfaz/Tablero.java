package Interfaz;

import java.awt.*;


abstract class Tablero extends Canvas{
	 int c;
	 int dimension=75;	 
	 
	public Tablero(){
		c=8;
		setSize(1200,1200);
	}	
	
	public void paint(Graphics g){		
		int inix=dimension,iniy =dimension;
		g.setColor(new Color(75,141,221));
		g.fillRect(0, 0, (c+2)*dimension,(c+2)*dimension);
		g.setColor(new Color(255,255,255));
		pintarCasillasJugador(g,inix,iniy,true);
		g.setColor(new Color(0,0,0));
		pintarCasillasJugador(g,inix+dimension,iniy,false);		
		dibujarExtra(g);		
	}
	
	int calculo(int j){return j*(dimension+1);}
	
	public abstract void dibujarExtra(Graphics g);
	
	private void pintarCasillasJugador(Graphics g,int inix,int iniy,boolean b){		
		int x=inix;		
		int y=iniy;
		for (int j=c;j>0;j--){			
			for (int i=c/2;i>0;i--){
				g.fillRect(x, y, dimension, dimension);
				x=x+dimension*2;
			}
			if (!b)
				x=inix-dimension*((j+1)%2);
			else 
				x=inix+dimension*((j+1)%2);
			y=y+dimension;
		}
	}	
}

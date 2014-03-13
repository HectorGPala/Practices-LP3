package Interfaz;

import Dibujo.*;

import java.util.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;



public class TableroCanvas extends Canvas implements MouseListener {
	Grafo grafo;
	Principal i;
	Nodo origen;
	Nodo destino;
	Grafo gD,gF,gK;
	
	public TableroCanvas(Grafo g,Principal prin){
		this.addMouseListener(this);
		setSize(1000,600);
		grafo = g;
		gD = gF = null;
		i=prin;		
	}	
	
	public void paint (Graphics g){
		pintarNodo(g,grafo,Color.BLACK);
		pintarArco(g,grafo,Color.BLACK);
		pintarNodo(g,gD,Color.RED);
		pintarArco(g,gD,Color.RED);
		pintarNodo(g,gF,Color.BLUE);
		pintarArco(g,gF,Color.BLUE);
		pintarNodo(g,gK,Color.YELLOW);
		pintarArco(g,gK,Color.YELLOW);
		
	}
	
	/*public void pintarArcoD(Graphics g){
		Iterator<Arco> it = gD.iteratorArco();
		g.setColor(Color.RED);
		while(it.hasNext()){
			Arco aux = it.next();
			Nodo o = aux.getOrigen();
			Nodo d = aux.getDestino();
			drawArrow(o.getX(),o.getY(),d.getX(),d.getY(),String.valueOf(aux.getPeso()),g);
		}
	}
	
	public void pintarNodoD(Graphics g){
		Iterator<Nodo> it = gD.iteratorNodo();
		g.setColor(Color.RED);
		while(it.hasNext()){
			Nodo aux = it.next();
			int x = aux.getX();
			int y = aux.getY();
			String nombre = aux.getNombre();
			g.fillOval(x-5,y-5,5*2,5*2);
			g.drawString(nombre,x+6,y);
		}
	}*/
	
	public void pintarArco(Graphics g,Grafo graf,Color c){
		if(graf != null){
			Iterator<Arco> it = graf.iteratorArco();
			g.setColor(c);
			while(it.hasNext()){
				Arco aux = it.next();
				Nodo o = aux.getOrigen();
				Nodo d = aux.getDestino();
				drawArrow(o.getX(),o.getY(),d.getX(),d.getY(),String.valueOf(aux.getPeso()),g);
			}
		}
	}
	
	public void pintarNodo(Graphics g,Grafo graf,Color c){
		if(graf != null){
			Iterator<Nodo> it = graf.iteratorNodo();
			g.setColor(c);
			while(it.hasNext()){
				Nodo aux = it.next();
				int x = aux.getX();
				int y = aux.getY();
				String nombre = aux.getNombre();
				g.fillOval(x-5,y-5,5*2,5*2);
				g.drawString(nombre,x+6,y);
			}
		}
	}
	
	public void drawArrow(int x0,int y0,int x1,int y1,String s,Graphics g){
		double alfa = Math.atan2(y1-y0,x1-x0);//angulo de inclinacion
		g.drawLine(x0,y0,x1,y1);
		int k=12;
		int mX = (x1+x0)/2;
		int mY =(y1+y0)/2;
		int xa=(int)(mX-k*Math.cos(alfa+0.5));
		int ya=(int)(mY-k*Math.sin(alfa+0.5));
		int xa2=(int)(mX-k*Math.cos(alfa-0.5));
		int ya2=(int)(mY-k*Math.sin(alfa-0.5));
		int[] XX = {mX,xa,xa2};
		int[] YY = {mY,ya,ya2};
		g.fillPolygon(XX,YY,3);
		if(alfa < 0)
			alfa *= (-1);
		if(alfa > 0 && alfa < (Math.PI/2))
			g.drawString(s,xa,ya);
		if(alfa > (Math.PI/2) && alfa < Math.PI)
			g.drawString(s,xa,ya+10);
		if((alfa == Math.PI) || (alfa == 0))
			g.drawString(s,xa,ya);
		if((alfa == (Math.PI/2)) || (alfa == -(Math.PI/2)))
			g.drawString(s,xa,ya);
	}
	
	public void setResultados(){
		gD = null;
		gF = null;
		gK = null;
		
	}
		
	public void mousePressed(MouseEvent e) {
		int posX = e.getX();
		int posY= e.getY();
		if( i.esKruskal()){			
			Kruskal k = new Kruskal(grafo);
			gK = k.dameGrafo();
			gD = gF = null;
			repaint();
			i.setEstado(0);
		}
		if(i.esFloyd2()){
			Iterator<Nodo> it = grafo.iteratorNodo();
			boolean noEncontrado = true;
			while(it.hasNext() && noEncontrado){
				Nodo aux = it.next();
				int X = aux.getX();
				int Y = aux.getY();
				if(((posX-5 <= X)&&(X <= posX+5))&&((posY-5 <= Y)&&(Y <= posY+5))){
					destino = aux;
					i.setEstado(0);
					Floyd a = new Floyd(grafo);
					a.algFloyd();
					gF = a.dameGrafo(origen,destino);
					gD = gK = null;
					noEncontrado = false;
				}	
			}
			repaint();			
		}
		if(i.esFloyd()){
			Iterator<Nodo> it = grafo.iteratorNodo();
			boolean noEncontrado = true;
			while(it.hasNext() && noEncontrado){
				Nodo aux = it.next();
				int X = aux.getX();
				int Y = aux.getY();
				if(((posX-5 <= X)&&(X <= posX+5))&&((posY-5 <= Y)&&(Y <= posY+5))){
					origen = aux;
					noEncontrado = false;
					i.setEstado(10);
				}
			}
		}
		if(i.esDijkstra2()){
			Iterator<Nodo> it = grafo.iteratorNodo();
			boolean noEncontrado = true;
			while(it.hasNext() && noEncontrado){
				Nodo aux = it.next();
				int X = aux.getX();
				int Y = aux.getY();
				if(((posX-5 <= X)&&(X <= posX+5))&&((posY-5 <= Y)&&(Y <= posY+5))){
					destino = aux;
					i.setEstado(0);
					Dijkstra a = new Dijkstra(grafo,origen,destino);
					a.algDijkstra();
					gD = a.dameGrafo();
					gF = gK = null;
					noEncontrado = false;
				}	
			}
			repaint();
		}
		if(i.esDijkstra()){
			Iterator<Nodo> it = grafo.iteratorNodo();
			boolean noEncontrado = true;
			while(it.hasNext() && noEncontrado){
				Nodo aux = it.next();
				int X = aux.getX();
				int Y = aux.getY();
				if(((posX-5 <= X)&&(X <= posX+5))&&((posY-5 <= Y)&&(Y <= posY+5))){
					origen = aux;
					noEncontrado = false;
					i.setEstado(8);
				}	
			}//JOptionPane.showMessageDialog(null,"Seleccione Destino");
		}
		if(i.esIntroducirNodo()){
			grafo.addNodo(posX,posY,i.dameNombre());		
			i.setEstado(0);
			repaint();			
		}
		if(i.esIntroducirArco2()){
			Iterator<Nodo> it = grafo.iteratorNodo();
			boolean noEncontrado = true;
			while(it.hasNext() && noEncontrado){
				Nodo aux = it.next();
				int X = aux.getX();
				int Y = aux.getY();
				if(((posX-5 <= X)&&(X <= posX+5))&&((posY-5 <= Y)&&(Y <= posY+5))){
					destino = aux;
					noEncontrado = false;
				}	
			}
			if(!noEncontrado && !origen.equals(destino)){
				if(!grafo.addArco(origen,destino,Integer.parseInt(i.dameNombre()))){
					JOptionPane.showMessageDialog(null,"Ya existe esa arista");
					i.setEstado(2);
				}
				else
					i.setEstado(0);
				repaint();
			}
		}
		if(i.esIntroducirArco()){
			Iterator<Nodo> it = grafo.iteratorNodo();
			boolean noEncontrado = true;
			while(it.hasNext() && noEncontrado){
				Nodo aux = it.next();
				int X = aux.getX();
				int Y = aux.getY();
				if(((posX-5 <= X)&&(X <= posX+5))&&((posY-5 <= Y)&&(Y <= posY+5))){
					origen = aux;
					i.setEstado(3);
					noEncontrado = false;					
				}	
			}
		}
		if(i.esEliminarNodo()){			
			boolean encontrado=false;
			Iterator<Nodo> it = grafo.iteratorNodo();
			while(it.hasNext() && !encontrado){
				Nodo aux = it.next();
				int X = aux.getX();
				int Y = aux.getY();
				if(((posX-5 <= X)&&(X <= posX+5))&&((posY-5 <= Y)&&(Y <= posY+5))){
					origen = aux;
					i.setEstado(0);
					encontrado = true;
					grafo.eliminarNodo(aux);
					Iterator<Arco> it1 = grafo.iteratorArco();
					while(it1.hasNext()){
						Arco aux1 = it1.next();
						if(aux1.apareceEnArco(aux)){//nodo aux existe como origen o destino
							grafo.eliminarArco(aux1);
							it1 = grafo.iteratorArco();						
						}
					}
					repaint();			
				}			
			}
		}
		
		if(i.esEliminarArco2()){
			Iterator<Nodo> it = grafo.iteratorNodo();
			boolean noEncontrado = true;
			while(it.hasNext() && noEncontrado){
				Nodo aux = it.next();
				int X = aux.getX();
				int Y = aux.getY();
				if(((posX-5 <= X)&&(X <= posX+5))&&((posY-5 <= Y)&&(Y <= posY+5))){
					destino = aux;
					noEncontrado = false;
				}	
			}
			if(!noEncontrado){
				grafo.eliminarArco(origen,destino);
				i.setEstado(0);
				repaint();
			}
		}
		
		if(i.esEliminarArco()){
			Iterator<Nodo> it = grafo.iteratorNodo();
			boolean noEncontrado = true;
			while(it.hasNext() && noEncontrado){
				Nodo aux = it.next();
				int X = aux.getX();
				int Y = aux.getY();
				if(((posX-5 <= X)&&(X <= posX+5))&&((posY-5 <= Y)&&(Y <= posY+5))){
					origen = aux;
					i.setEstado(6);
					noEncontrado = false;					
				}	
			}
		}	
	}
	//se declaran pero no se usan
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e){}
}

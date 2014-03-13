package Interfaz;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;


//import javax.imageio.ImageIO;
//import javax.swing.JOptionPane;

import Movimientos.ComprobadorMov;
import Movimientos.TableroJuego;

public class TableroFichas extends Tablero implements MouseListener{
	Image[] imgFichas ;
	ComprobadorMov movimiento;
	TableroJuego tableroJ;
	int tamaño;
	int profundidad;
	int h;
	
	public TableroFichas(int t,boolean m,int profundidad,int h){		
		tamaño=t;
		c=tamaño;
		this.profundidad = profundidad;
		this.h = h;
		this.addMouseListener(this);	
		imgFichas = new Image[9];
		ponerImagenes();	
		movimiento = new ComprobadorMov(m,profundidad,h);
		tableroJ= new TableroJuego(tamaño);
	}	
	
	public void  ponerImagenes(){//las que pone copia estan con el borde naranja como si estuvieran seleccionadas
		 URL url = getClass().getResource("/imagenes/RM.jpg");
		 imgFichas[1]= getToolkit().getImage(url);
		 url = getClass().getResource("/imagenes/cr.jpg");
		 imgFichas[3]= getToolkit().getImage(url);
		 url = getClass().getResource("/imagenes/barcelona.jpg");
		 imgFichas[2]= getToolkit().getImage(url);
		 url = getClass().getResource("/imagenes/ms.jpg");
		 imgFichas[4]= getToolkit().getImage(url);
		 url = getClass().getResource("/imagenes/RM - copia.jpg");
		 imgFichas[5]= getToolkit().getImage(url);
		 url = getClass().getResource("/imagenes/cr - copia.jpg");
		 imgFichas[7]= getToolkit().getImage(url);
		 url = getClass().getResource("/imagenes/barcelona - copia.jpg");
		 imgFichas[6]= getToolkit().getImage(url);
		 url = getClass().getResource("/imagenes/ms - copia.jpg");
		 imgFichas[8]= getToolkit().getImage(url);				
	}	
	
	public void dibujarExtra(Graphics g){	
		dibujarMatrizFichas(g);
		g.setColor(new Color(0,0,0));
		g.drawString(movimiento.dameMsj(), 20, 20);		
	}	
	
	public void dibujarMatrizFichas(Graphics g){		
		for(int i=0;i<tamaño;i++){
			for(int j=0;j<tamaño;j++){
				int estado=tableroJ.dameCasilla(i, j);
				if( estado != 0)
				 dibujarFicha(g,estado,i,j);				
			}
		}
	}	
	
	private void dibujarFicha(Graphics g, int estado, int f, int c){
		int i= pixelar(f);
		int j= pixelar(c);		
		g.drawImage(imgFichas[estado], j, i,this);		
	}
	
	public int pixelar(int i){return i*(dimension)+dimension;}
	public int despixelar(int i){
		if((i >= 0 && i < dimension) || (i >= dimension*(tamaño+1)))
			return -1;
		return (i-dimension)/dimension;
	}
	
	public void iniciar(){
		boolean m = movimiento.getMaquina();
		movimiento = new ComprobadorMov(m,profundidad,h);
		tableroJ.inicializar();
		repaint();
	}

	public void mouseClicked(MouseEvent e){	
				
	}
	
	public void mouseEntered(MouseEvent e){}	
	public void mouseExited(MouseEvent e){}	
	public void mousePressed(MouseEvent e){}	
	public void mouseReleased(MouseEvent e){
		int x=e.getX();
		int y=e.getY();
		int x1=despixelar(x);
		int y1=despixelar(y);
		movimiento.procesaMovimiento(x1,y1,tableroJ);
		repaint();
	}
}

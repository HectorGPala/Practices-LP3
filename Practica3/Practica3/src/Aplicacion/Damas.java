package Aplicacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Interfaz.TableroFichas;

public class Damas extends JPanel implements ActionListener{
	TableroFichas tablero; 
	int tamaño;
	
	public Damas(int t,boolean m,int profundidad,int h){
		tamaño = t;
		this.setLayout(new BorderLayout());		
		tablero= new TableroFichas(t,m,profundidad,h);	
		JPanel panelInferior = new JPanel();
		JButton inicio = new JButton("Inicio");
		inicio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tablero.iniciar();
			}
		});
		panelInferior.add(inicio);
		panelInferior.setBackground(new Color(75,141,221));
		this.setBackground(new Color(75,141,221));
		this.add("West",tablero);
		this.add("South",panelInferior);
		//((TableroFichas)tablero).repaint();	
	}
	/*public static void main(String[] args) {
		JFrame app= new JFrame("Juego de las Damas");
		 
		
		JComponent damas= new Damas();
		app.setVisible(true);
		app.setEnabled(true);
	
		app.setSize(1000,1000);
		app.setContentPane(damas);

	}*/
	public void actionPerformed(ActionEvent arg0){}
}

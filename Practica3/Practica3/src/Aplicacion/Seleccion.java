package Aplicacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Seleccion extends JPanel{
	int tamaño=8;
	static JFrame a;	
	private JRadioButton boton8, boton10, boton12,botonJJ,botonM;
	private ButtonGroup grupoBotonesOpcion,grupoBotonesOpcion2;
	private boolean maquina;
	private int heuristica;
	private int profundidad;
	JTextField entrada;
	
	public Seleccion(){		
		this.setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel panel = new JPanel();		
		JLabel label = new JLabel("Selecciona el tamaño del tablero");
		panel.add(label);
		boton8 = new JRadioButton( "8x8", true );
		panel.add( boton8);
		boton10 = new JRadioButton( "10x10", false );
		panel.add( boton10);
		boton12 = new JRadioButton( "12x12", false );
		panel.add( boton12);		
		grupoBotonesOpcion = new ButtonGroup();
		grupoBotonesOpcion.add(boton8);
		grupoBotonesOpcion.add(boton10);
		grupoBotonesOpcion.add(boton12);
		maquina = true;
		profundidad = 4;
		heuristica = 2;
		boton8.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					tamaño = 8;
				}
		  });		  
		boton10.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					tamaño = 10;
				}
		  });
		boton12.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					tamaño = 12;
				}
		});		
		JButton boton = new JButton("Comenzar partida");
		JPanel panel3 = new JPanel();
		panel3.add(boton);
		boton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if (tamaño != 0){						
						JFrame app= new JFrame("Juego de las Damas");
						try{
							profundidad = Integer.parseInt(entrada.getText());
						}
						catch(Exception ex){
							JOptionPane.showMessageDialog(null,"Introduzca un numero positivo");
							profundidad = -1;
						}
						if(profundidad > 0){
							a.dispose();
							JComponent damas= new Damas(tamaño,maquina,profundidad,heuristica);
							app.setVisible(true);
							app.setEnabled(true);					
							app.setSize((tamaño+2)*75,(tamaño+2)*75);
							app.setContentPane(damas);
							app.addWindowListener( new WindowAdapter(){
								public void windowClosing(WindowEvent evt){
									System.exit(0);
								}
							});					
						}
					}
				}
		});
		JPanel panel2 = new JPanel();
		botonJJ = new JRadioButton("Jugador vs Jugador",false);
		botonM = new JRadioButton("Maquina",true);
		grupoBotonesOpcion2 = new ButtonGroup();
		grupoBotonesOpcion2.add(botonJJ);
		grupoBotonesOpcion2.add(botonM);
		panel2.add(new JLabel("Tipo de Juego:"));
		panel2.add(botonJJ);
		panel2.add(botonM);
		entrada = new JTextField("4",2);
		panel2.add(new JLabel("Profundidad"));
		panel2.add(entrada);
		botonJJ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				maquina = false;
			}
		});
		botonM.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				maquina = true;
			}
		});
		JPanel panel4 = new JPanel();
		JRadioButton botonA = new JRadioButton("Ataque",false);
		JRadioButton botonD = new JRadioButton("Defensa",false);
		JRadioButton botonM = new JRadioButton("Mi heuristica",true);
		ButtonGroup grupoBotones3 = new ButtonGroup();
		botonA.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				heuristica = 0;
			}
		});
		botonD.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				heuristica = 1;
			}
		});
		botonM.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				heuristica = 2;
			}
		});
		grupoBotones3.add(botonA);
		grupoBotones3.add(botonD);
		grupoBotones3.add(botonM);
		panel4.add(botonA);
		panel4.add(botonD);
		panel4.add(botonM);
		this.add(panel);
		this.add(panel2);
		this.add(panel4);
		this.add(panel3);	
	}	
	
	public static void main(String[] args){
		a = new JFrame("Menu Seleccion");		
		JComponent seleccion= new Seleccion();
		a.setVisible(true);
		a.setEnabled(true);
		a.setSize(600,200);
		a.setContentPane(seleccion);
	}
}

package Interfaz;

import java.awt.event.*;
import javax.swing.*;
import Dibujo.Grafo;



public class Principal extends JFrame implements ActionListener{
	private Grafo grafo;
	private int estado;
	private String nombre;
	private TableroCanvas t;

	public Principal(){
		grafo= new Grafo();
		estado=0;
		this.setJMenuBar(getMenuPrincipal());
		this.setContentPane(getPanelCanvas());		
	}

	public JMenuBar getMenuPrincipal(){
		JMenuBar barraMenu = new JMenuBar();
		barraMenu.add(getMenuGrafo());
		barraMenu.add(getMenuOperaciones());
		barraMenu.add(getMenuArchivo());
		return barraMenu;
	}
	
	public JMenu getMenuGrafo(){
		JMenu selecMenu = new JMenu();
		selecMenu.setText("Grafo");
		JMenuItem introducirNodo = new JMenuItem();
		introducirNodo.setText("Introducir Nodo");
		introducirNodo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FrameNodo framenodo = new FrameNodo("Nombre: ","Introducir nombre Nodo");
				if(framenodo.cerrado()){
					nombre= framenodo.nombre();
					estado = 1;
				}
			}			
		});		
		JMenuItem introducirArista = new JMenuItem();
		introducirArista.setText("Introducir Arista");
		introducirArista.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(grafo.numNodos() == 0)
					JOptionPane.showMessageDialog(null,"No hay nodos que conectar!");
				else{
					FrameNodo framenodo = new FrameNodo("Peso: ","Introducir peso Arista");
					if(framenodo.cerrado()){
						nombre= framenodo.nombre();
						try{
							int n = Integer.parseInt(nombre);
							if(n < 0)
								throw new Exception();
							estado = 2;
						}
						catch(Exception ex){
							JOptionPane.showMessageDialog(null,"Error al leer el peso!");	
						}					
					}
				}
			}
		});		
		JMenuItem eliminarNodo = new JMenuItem();
		eliminarNodo.setText("Eliminar Nodo");		
		eliminarNodo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (grafo.numNodos()==0) {
					JOptionPane.showMessageDialog(null,"No existe ningun nodo,elija otra opcion");
					estado = 0;
				}
				else
					estado = 4;
			}	
		});				
		JMenuItem eliminarArista = new JMenuItem();
		eliminarArista.setText("Eliminar Arista");
		eliminarArista.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (grafo.numArcos()==0) {
					JOptionPane.showMessageDialog(null,"No existe ningun arco,elija otra opci�n");
					estado = 0;
				}
				else
					estado = 5;
			}	
			
		});
		selecMenu.add(introducirNodo);
		selecMenu.add(introducirArista);
		selecMenu.add(eliminarNodo);
		selecMenu.add(eliminarArista);
		return selecMenu;		
	}
	
	public JMenu getMenuOperaciones(){
		JMenu selecMenu = new JMenu();
		selecMenu.setText("Operaciones");
		JMenuItem DijkstraMenu = new JMenuItem();
		DijkstraMenu.setText("Dijkstra");
		DijkstraMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				estado = 7;
			}
		});
		selecMenu.add(DijkstraMenu);
		JMenuItem FloydMenu = new JMenuItem();
		FloydMenu.setText("Floyd");
		FloydMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				estado = 9;
			}
		});
		selecMenu.add(FloydMenu);
		JMenuItem KruskalMenu = new JMenuItem();
		KruskalMenu.setText("Kruskal");
		KruskalMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				estado = 11;
			}
		});
		selecMenu.add(KruskalMenu);
		return selecMenu;		
	}
	
	public JMenu getMenuArchivo(){
		JMenu selecMenu = new JMenu();
		selecMenu.setText("Archivo");
		JMenuItem abrir = new JMenuItem();
		abrir.setText("Abrir");
		JMenuItem guardar = new JMenuItem();
		guardar.setText("Guardar");
		selecMenu.add(abrir);
		selecMenu.add(guardar);
		guardar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					JFileChooser cajaFich = new JFileChooser();
					cajaFich.showSaveDialog(Principal.this);
					String fich = cajaFich.getSelectedFile().getPath();
					grafo.guardar(fich);
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null,"No se ha guardado!");
				}
				t.repaint();
			}
		});
		abrir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					JFileChooser cajaFich = new JFileChooser();
					cajaFich.showOpenDialog(Principal.this);
					String fich = cajaFich.getSelectedFile().getPath();
					grafo.iniciar();
					grafo.abrir(fich);
					t.setResultados();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null,"No selecciono fichero!");
				}
				t.repaint();
			}
		});
		return selecMenu;		
	}	
	
	JPanel getPanelCanvas(){
		JPanel p= new JPanel();		
		t = new TableroCanvas(grafo,this);
		p.add(t);
		return p;		
	}	
	
	public void actionPerformed(ActionEvent arg0) {}	
	
	public boolean esIntroducirNodo(){return estado==1;}	
	public boolean esIntroducirArco(){return estado == 2;}	
	public boolean esIntroducirArco2(){return estado == 3;}
	public boolean esEliminarNodo(){return estado == 4;}
	public boolean esEliminarArco(){return estado == 5;}
	public boolean esEliminarArco2(){return estado == 6;}
	public boolean esDijkstra(){return estado == 7;}
	public boolean esDijkstra2(){return estado == 8;}
	public boolean esFloyd(){return estado == 9;}
	public boolean esFloyd2(){return estado == 10;}
	public boolean esKruskal(){return estado == 11;}
	public void setEstado(int n){estado = n;}

	public String dameNombre(){
		String s = nombre;
		return s;
	}	
	
	public static void main(String[] args){		
		Principal p = new Principal();
		p.setVisible(true);
		p.setEnabled(true);
		p.setTitle("Grafos LP3");
		p.setSize(1000,600);
		
	}
}

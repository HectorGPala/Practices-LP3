package Interfaz;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.Scanner;
import Supermercado.*;
import Caja.*;

public class Principal extends JFrame implements ActionListener{
	
	//Atributos...
	private JMenuItem item[];
	private String fichero[];
	private JTabbedPane panelTabulado;
	private Boolean pestanas[];
	Supermercados superm;
	Efectivo caja;
	String directorioDefault;
	
	public void actionPerformed(ActionEvent e){
		int i = 0; boolean encontrado = false;
		while((i < 5) && (!encontrado)){
			if (e.getSource() == item[i]){
				encontrado = true;
				if (!pestanas[i]){
					JPanel tab = getPanelTab(i);
					if (tab != null){
						panelTabulado.addTab(dameNombre(i),tab);
						panelTabulado.validate();
						pestanas[i] = true;
					}
				}
			}
			i++;
		}
	}
	
	public String dameNombre(int i){
		switch (i){
			case 0: return "Frutas y Verduras";
			case 1: return "Carnes";
			case 2: return "Pescados";
			case 3: return "Bebidas";
			case 4: return "Varios";
		}
		return null;
	}
	
	public JPanel getPanelTab(int i){
		File f = new File(fichero[i]);
		superm.inicializar(f,i);
		if ((superm.getIesimo(i) != null) && (superm.getIesimo(i).getNumProds() != 0)){
			DefaultTableModel tm = new DefaultTableModel(0,3);
			tm.addRow(new String[]{"PRODUCTO","PRECIO(€)","CANTIDAD"});
			Categoria c = superm.getIesimo(i);
			int n = c.getNumProds();
			for (int j = 0; j < n; j++){
				Producto prod = c.getProductos(j);
				String nombre = prod.getNombre();
				String precio = String.valueOf(prod.getPrecio());
				tm.addRow(new String[]{nombre,precio,""});
			}
			JTable tabla = new JTable(tm);
			JPanel panelTabla = new JPanel();
			panelTabla.setLayout(new BorderLayout());
			panelTabla.add("Center", tabla);
			panelTabla.validate();
			return panelTabla;
		}
		else 
			return null;
	}
	
	public Principal(){
		pestanas = new Boolean[]{false,false,false,false,false};
		fichero = new String[5];
		item = new JMenuItem[5];
		iniciarInterfaz();
		iniciarSupermercado();
		iniciarCaja();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				caja.escribirFich(directorioDefault+"/caja.txt");//guarda en fich cuando se cierra
				System.exit(0);
			}
		});
		JOptionPane.showMessageDialog(null,"Aviso: Cargar primero Productos!");		
	}
	
	public void iniciarInterfaz(){
		this.setJMenuBar(getMenuPrincipal());
		this.setContentPane(getPanelPrincipal());
		this.setTitle("Supermercado FDI");
	}
	
	public void iniciarSupermercado(){
		superm = new Supermercados();
	}
	
	public void iniciarCaja(){
		caja = new Efectivo();
		JOptionPane.showMessageDialog(null,"Seleccione primero el directorio por defecto");
		directorioDefault = "";
		try{
			JFileChooser cajaFich = new JFileChooser();
			cajaFich.showOpenDialog(Principal.this);
			directorioDefault = cajaFich.getSelectedFile().getParent();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,"No se pudo cargar");
		}
		String fichero = directorioDefault+"/caja.txt";
		File f = new File(fichero);
		Scanner sc = null;
		try{
			sc = new Scanner(f);
			int i = 0;
			while (sc.hasNext()){
				String s = sc.nextLine();
				int cantidad = Integer.parseInt(s);
				caja.setMonedas(i, cantidad);
				i++;
			}
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null,"ERROR: No se ha podido iniciar la caja");
			System.exit(1);//no se los codigos de error pero si 0 es OK 1 es fallo...
		}		
	}
	
	public JMenuBar getMenuPrincipal(){
		JMenuBar barraMenu = new JMenuBar();
		barraMenu.add(getMenuSeleccion());
		barraMenu.add(getMenuCaja());
		barraMenu.add(getMenuConfg());
		return barraMenu;
	}
	
	public JMenu getMenuSeleccion(){
		JMenu selecMenu = new JMenu();
		selecMenu.setText("Seleccion");
		for (int i = 0; i < 5;i++)
			selecMenu.add(getMenuSItem(dameNombre(i),i));
		return selecMenu;
	}
		
	public JMenuItem getMenuSItem(String s,int i){
		item[i] = new JMenuItem();
		item[i].setText(s);
		item[i].setEnabled(false);
		item[i].addActionListener(this);
		return item[i];
	}
	
	public JMenu getMenuCaja(){
		JMenu cajaMenu = new JMenu();
		cajaMenu.setText("Caja");
		cajaMenu.add(getConslCaja());
		cajaMenu.add(getPago());
		return cajaMenu;
	}
	
	public JMenuItem getPago(){
		JMenuItem c = new JMenuItem();
		c.setText("Pago en enfectivo");
		c.setEnabled(true);
		c.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int n_pestanas = panelTabulado.getComponentCount();
				String cantidad,precio,producto;
				cantidad = precio = producto = null;
				double pago = 0;
				try{	
					for(int j = 0;j < n_pestanas;j++){
						JPanel a = (JPanel) panelTabulado.getComponent(j);
						JTable tabla = (JTable) a.getComponent(0);
						int n_filas = tabla.getRowCount();
						for (int i = 1;i < n_filas;i++){
							producto = (String) tabla.getValueAt(i,0);
							cantidad = (String) tabla.getValueAt(i,2);
							precio = (String) tabla.getValueAt(i,1);
							if(cantidad != ""){
								int can = Integer.parseInt(cantidad);
								double pre = Double.valueOf(precio);
								if(can > 0)
									pago += (can*pre);
							}	
						}
					}
					if(pago == 0)
						JOptionPane.showMessageDialog(null,"No hay nada seleccionado");
					else{
						FramePago framePago = new FramePago(pago,caja);
						if(framePago.cerradoPorPago()){
							setContentPane(getPanelPrincipal());
							superm.finalizar();//elimina las listas de cada gama de productos
							for(int i = 0; i < 5; i++)
								pestanas[i] = false;
						}
						setEnabled(true);
						validate();
					}
				}
				catch (Exception ex){
					JOptionPane.showMessageDialog(null,"La cantidad: '"+cantidad+"' no es valida para el producto "+producto);
				}
			}
		});
		return c;
	}
	
	public JMenuItem getConslCaja(){
		JMenuItem c = new JMenuItem();
		c.setText("Consultar Caja");
		c.setEnabled(true);
		c.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s ="";
				for (int i = 0; i < 12; i++)
					s += dameNombreMoneda(i)+caja.getMonedas(i);
				JOptionPane.showMessageDialog(null,s);
			}
		});
		return c;
	}
	
	public String dameNombreMoneda(int i){
		switch (i){
		case 0: return "50€ : ";
		case 1: return "||| 20€ : ";
		case 2: return "||| 10€ : ";
		case 3: return "||| 5€ : ";
		case 4: return "||| 2€ : ";
		case 5: return "||| 1€ : ";
		case 6: return "||| 0.50€ : ";
		case 7: return "||| 0.20€ : ";
		case 8: return "||| 0.10€ : ";
		case 9: return "||| 0.05€ : ";
		case 10: return "||| 0.02€ : ";
		case 11: return "||| 0.01€ : ";
	}
	return null;
	}
	
	public JMenu getMenuConfg(){
		JMenu cfgMenu = new JMenu();
		cfgMenu.setText("Configuracion");
		JMenu configuracion = new JMenu();
		configuracion.setText("Cargar");
		configuracion.setEnabled(true);
		configuracion.add(getCargar("Frutas y Verduras"));
		configuracion.add(getCargar("Carnes"));
		configuracion.add(getCargar("Pescados"));
		configuracion.add(getCargar("Bebidas"));
		configuracion.add(getCargar("Varios"));
		cfgMenu.add(configuracion);
		cfgMenu.add(defaultP());
		cfgMenu.add(operacionesCaja());
		cfgMenu.setEnabled(true);
		return cfgMenu;
	}
	
	public JMenu operacionesCaja(){
		JMenu o = new JMenu();
		o.setText("Operaciones Caja");
		o.setEnabled(true);
		JMenuItem a = new JMenuItem();
		a.setText("LLenar Caja");
		a.setEnabled(true);
		a.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				caja.llenarCaja();
			}
		});
		JMenuItem b = new JMenuItem();
		b.setText("VaciarCaja");
		b.setEnabled(true);
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				caja.vaciarCaja();
			}
		});
		o.add(a);
		o.add(b);
		return o;
	}
	
	public JMenuItem defaultP(){
		JMenuItem o = new JMenuItem();
		o.setText("Productos Default");
		o.setEnabled(true);
		o.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					fichero[0] = (String)directorioDefault+"/frutas.txt";
					item[0].setEnabled(true);
					fichero[1] = directorioDefault+"/carnes.txt";
					item[1].setEnabled(true);
					fichero[2] = directorioDefault+"/pescados.txt";
					item[2].setEnabled(true);
					fichero[3] = directorioDefault+"/bebidas.txt";
					item[3].setEnabled(true);
					fichero[4] = directorioDefault+"/varios.txt";
					item[4].setEnabled(true);
			}			
		});	
		return o;
	}

	public JMenuItem getCargar(String s){
		JMenuItem o = new JMenuItem();
		o.setText(s);
		o.setEnabled(true);
		char a = s.charAt(0);
		switch(a){
			case 'F':{
				o.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try{
							JFileChooser selecFich = new JFileChooser();
							selecFich.showOpenDialog(Principal.this);
							fichero[0] = selecFich.getSelectedFile().getPath();
							item[0].setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"No se pudo cargar");
						}
					}			
				});				
			}
			break;
			case 'C':{
				o.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try{
							JFileChooser selecFich = new JFileChooser();
							selecFich.showOpenDialog(Principal.this);
							fichero[1] = selecFich.getSelectedFile().getPath();
							item[1].setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"No se pudo cargar");
						}
					}			
				});
			}
			break;
			case 'P':{
				o.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try{
							JFileChooser selecFich = new JFileChooser();
							selecFich.showOpenDialog(Principal.this);
							fichero[2] = selecFich.getSelectedFile().getPath();
							item[2].setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"No se pudo cargar");
						}
					}			
				});
			}
			break;
			case 'B':{
				o.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try{
							JFileChooser selecFich = new JFileChooser();
							selecFich.showOpenDialog(Principal.this);
							fichero[3] = selecFich.getSelectedFile().getPath();
							item[3].setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"No se pudo cargar");
						}
					}			
				});
			}
			break;
			case 'V':{
				o.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						try{
							JFileChooser selecFich = new JFileChooser();
							selecFich.showOpenDialog(Principal.this);
							fichero[4] = selecFich.getSelectedFile().getPath();
							item[4].setEnabled(true);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(null,"No se pudo cargar");
						}
					}			
				});
			}
			break;
		}	
		return o;
	}
	
	private JPanel getPanelPrincipal(){
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		panelTabulado = new JTabbedPane();
		panelPrincipal.add("Center", panelTabulado);
		panelPrincipal.validate();
		return panelPrincipal;
	}
	
	public static void main(String args[]){
		Principal p = new Principal();
		p.setVisible(true);
		p.setEnabled(true);
		p.setSize(1000,600);
	}
}

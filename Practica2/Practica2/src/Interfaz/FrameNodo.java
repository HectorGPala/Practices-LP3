package Interfaz;

import java.awt.event.*;
import javax.swing.*;


public class FrameNodo extends JDialog{
	boolean cerrado;
	JTextField nombre;
	String n;
	
	public FrameNodo(String texto,String titulo){
		setModal(true);
		setSize(400,100);
		setTitle(titulo);
		cerrado = false;
	    JButton boton = new JButton("Ok");
	    JPanel p = new JPanel();	    
	    nombre= new JTextField("",14);
	    p.add(new JLabel(texto));
	    p.add(nombre);
	    p.add(boton);
	    add(p);
	    boton.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){		    	  
		    	n= nombre.getText();
		    	cerrado=true;
		    	dispose();
		    }
	    });
	    setVisible(true);
		setEnabled(true);
	}

	public String nombre(){		
		return nombre.getText();
	}

	public boolean cerrado(){		
		return cerrado;
	}
}

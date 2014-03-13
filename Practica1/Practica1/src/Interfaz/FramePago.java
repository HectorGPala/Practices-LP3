package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Caja.*;

public class FramePago extends JDialog{
	JButton pagar;
	JLabel etiqueta[];
	JTextField texto[];
	Efectivo cajaTemp;
	double cambio;
	double dinero;
	boolean cerradoP;//indica si se ha cerrado desde el boton pagar
	
	public FramePago(double p,Efectivo c){
		setModal(true);
		setSize(1000,100);
		setTitle("Introduzca monedas para pagar");
		cerradoP = false;
		cajaTemp = c;
		cambio = 0;
		dinero = p;
		iniciaBoton();
		iniciarLabels();
		iniciarTextFields();		
		add(damePanelSuperior());
		add(new JPanel());
		add(damePanelInferior());
		setVisible(true);
		setEnabled(true);
	}
	
	public JPanel damePanelSuperior(){
		JPanel panelSup = new JPanel();
		panelSup.setLayout(new GridLayout());	
		for (int i = 0 ; i < 12 ; i++){
			panelSup.add(etiqueta[i]);
			panelSup.add(texto[i]);
		}
		return panelSup;
	}
	
	public JPanel damePanelInferior(){
		JPanel panelInf = new JPanel();
		panelInf.setLayout(new GridLayout());
		setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		panelInf.add(new JLabel());
		dinero = Math.rint(dinero*100)/100;
		panelInf.add(new JLabel("Total a pagar : "+dinero));
		panelInf.add(pagar);
		panelInf.add(new JLabel());
		panelInf.add(new JLabel());
		return panelInf;
	}
	
	public double valor(int i){
		switch (i){
			case 0: return 50;
			case 1: return 25;
			case 2: return 20;
			case 3: return 5;
			case 4: return 1;
			case 5: return 1;
			case 6: return 0.50;
			case 7: return 0.20;
			case 8: return 0.10;
			case 9: return 0.05;
			case 10: return 0.02;
			case 11: return 0.01;
		}
		return 0;
	}
	
	public void iniciaBoton(){
		pagar = new JButton();
		pagar.setVisible(true);
		pagar.setEnabled(true);
		pagar.setText("Pagar");
		pagar.setToolTipText("Pulsar para pagar");
		Rectangle rec = new Rectangle(70,30);
		rec.setLocation(180,70);
		pagar.setBounds(rec);
		pagar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int temp = 0;
				try{
					Efectivo cajaUsuario = new Efectivo();
					for(int i = 0; i < 12; i++){
						temp = Integer.parseInt(texto[i].getText());
						if(temp > 0){
							cajaUsuario.meterMonedas(i, temp);
							cajaTemp.meterMonedas(i,temp);
						}
					}
					cambio = cajaUsuario.recaudacion()-dinero;
					if (cambio > 0){
						Solucion sParcial = new Solucion();
						Solucion sV = new Solucion();
						Efectivo cajaVoraz = new Efectivo(cajaTemp);
						Solucion sOptima = new Solucion(10000);
						Efectivo cajaParcial = new Efectivo(cajaTemp);
						sParcial.backtracking(sOptima,cajaTemp,cajaParcial,cambio,0);
						sV.AlgVoraz(cambio,cajaVoraz);
						if(sOptima.dameNmonedas() == -1)
							for (int j = 0; j < 12 ; j++)
								cajaTemp.sacarMonedas(j, cajaUsuario.getMonedas(j));
						cambio = Math.rint(cambio*100)/100;
						JOptionPane.showMessageDialog(null,"Cambio: "+cambio+"€ Sol Voraz: "+sV.dameSolucion());
						JOptionPane.showMessageDialog(null,"Cambio: "+cambio+"€ Sol Vuelta: "+sOptima.dameSolucion());
						cerradoP = true;
						dispose();
					}else
					if(cambio == 0){
						JOptionPane.showMessageDialog(null,"Se entrego la cantidad exacta");
						cerradoP = true;
						dispose();
					}
					else{
						JOptionPane.showMessageDialog(null,"Cantidad insuficiente");
						for(int i = 0; i < 12;i++)
							cajaTemp.sacarMonedas(i,cajaUsuario.getMonedas(i));
					}
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null,"ERROR de conversion");
				}
			}
		});		
	}

	
	
	public void iniciarLabels(){
		etiqueta = new JLabel[12];
		etiqueta[0] = new JLabel("50€ : ");
		etiqueta[1] = new JLabel("20€ : ");
		etiqueta[2] = new JLabel("10€ : ");
		etiqueta[3] = new JLabel("5€ : ");
		etiqueta[4] = new JLabel("2€ : ");
		etiqueta[5] = new JLabel("1€ : ");
		etiqueta[6] = new JLabel("0.50€ : ");
		etiqueta[7] = new JLabel("0.20€ : ");
		etiqueta[8] = new JLabel("0.10€ : ");
		etiqueta[9] = new JLabel("0.05€ : ");
		etiqueta[10] = new JLabel("0.02€ : ");
		etiqueta[11] = new JLabel("0.01€ : ");
	}
	
	public void iniciarTextFields(){
		texto = new JTextField[12];
		for(int i = 0; i < 12; i++)
			texto[i] = new JTextField("0");
	}
	
	public boolean cerradoPorPago(){
		return cerradoP;
	}
}
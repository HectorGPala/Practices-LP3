package Caja;

import java.io.*;

import javax.swing.JOptionPane;

public class Efectivo {
	private int monedas[];
	double recaudacion;
	
	public Efectivo(){
		monedas = new int[12];
		for (int i = 0; i < 12; i++){
			monedas[i] = 0;
		}
		recaudacion = 0;
	}
	
	public Efectivo(Efectivo o){
		monedas = new int[12];
		for (int i = 0; i < 12; i++){
			monedas[i] = o.monedas[i];
		}
		recaudacion = o.recaudacion;
	}
	
	public void setMonedas(int i,int c){
		recaudacion -= monedas[i]*valorMoneda(i);
		this.monedas[i] = c;
		recaudacion += monedas[i]*valorMoneda(i);
	}
	public int getMonedas(int i){
		return monedas[i];
	}
	
	public void copiar(Efectivo o){
		recaudacion = o.recaudacion;
		for (int i = 0; i < 12; i++)
			monedas[i] = o.monedas[i];		
	}
	
	public void llenarCaja(){
		for(int i = 0; i < 12;i++)
			monedas[i] += 25;
		recaudacion += 2222;
	}	
	
	public void vaciarCaja(){
		recaudacion = 0;
		for(int i = 0;i < 12;i++){
			if(monedas[i] > 5){
				monedas[i] = 5;
				recaudacion += valorMoneda(i)*5;
			}else
				recaudacion += valorMoneda(i)*monedas[i];
		}
	}
	
	public void escribirFich(String s){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(s);
            pw = new PrintWriter(fichero);
            for (int i = 0; i < 12; i++)
                pw.println(monedas[i]);
            fichero.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"ERROR: Fallo al escribir en la caja");
        }
	}
	
	public void sacarMoneda(int i){
		if(monedas[i] > 0){
			monedas[i]--;
			recaudacion -= valorMoneda(i);
		}
	}
	
	public void sacarMonedas(int i,int c){
		monedas[i] -= c;
		recaudacion -= valorMoneda(i)*c;
	}
	
	public void meterMoneda(int i){
		monedas[i]++;
		recaudacion += valorMoneda(i);
	}
	
	public void meterMonedas(int i,int c){
		monedas[i] += c;
		recaudacion += valorMoneda(i)*c;
	}
	
	public double recaudacion(){
		return Math.rint(recaudacion*100)/100;
	}
	
	public double valorMoneda(int i){
		switch (i){
			case 0: return 50;
			case 1: return 20;
			case 2: return 10;
			case 3: return 5;
			case 4: return 2;
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
}

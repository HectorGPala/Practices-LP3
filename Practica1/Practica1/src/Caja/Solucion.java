package Caja;

public class Solucion {
	
	private int monedas[];
	private int n_monedas;
	
	public Solucion(){
		monedas = new int[12];
		n_monedas = 0;
		for (int i = 0; i < 12; i++){
			monedas[i] = 0;
		}		
	}
	
	public Solucion(int n){
		monedas = new int[12];
		n_monedas = n;
		for (int i = 0; i < 12; i++){
			monedas[i] = 0;
		}
	}
	
	public Solucion(Solucion o){
		monedas = new int[12];
		n_monedas = o.n_monedas;
		for (int i = 0; i < 12; i++){
			monedas[i] = o.monedas[i];
		}
		
	}
	
	public void anotar(int i){
		n_monedas++;
		monedas[i]++;
	}
	
	public boolean esMejor(Solucion o){
		return this.n_monedas < o.n_monedas;
	}
	
	public boolean esSolucion(){
		return n_monedas > 0;
	}
	
	public void desanotar(int i){
		n_monedas--;
		monedas[i]--; 
	}
	
	public int dameNmonedas(){
		return n_monedas;
	}
	
	public void copiar(Solucion o){
		n_monedas = o.n_monedas;
		for(int j = 0; j < 12;j++)
			monedas[j] = o.monedas[j];
	}
	
	public void AlgVoraz(double pendiente,Efectivo c){
		int i = 0;
		pendiente = Math.rint(pendiente*100)/100;
		if(pendiente > c.recaudacion()){
			n_monedas = -1;
		}
		else{
			while((pendiente > 0) &&(i < 12)){
					if((valorMoneda(i) <= pendiente) && (c.getMonedas(i) > 0)){
						pendiente -= valorMoneda(i);
						pendiente = Math.rint(pendiente*100)/100;
						c.sacarMoneda(i);
						anotar(i);
					}
					else
						i++;
			}
			if((i == 12) && (pendiente > 0)){
				n_monedas = -1;
			}
		}
	}
	
	public void backtracking(Solucion sOptima,Efectivo cOptima,Efectivo cParcial,double cambio,int i){
		int m;
		for(m = i ; m < 12;m++){
			if((cambio >= valorMoneda(m)) && (cParcial.getMonedas(m) > 0)){
				cambio -= valorMoneda(m);
				cambio = Math.rint(cambio*100)/100;
				anotar(m);
				cParcial.sacarMoneda(m);
				if((cambio == 0) && (esMejor(sOptima))){
					sOptima.copiar(this);
					cOptima.copiar(cParcial); 
				}
				else{
					if(esMejor(sOptima)){
						backtracking(sOptima,cOptima,cParcial,cambio,m);
					}
				}
				desanotar(m);
				cParcial.meterMoneda(m);
				cambio += valorMoneda(m);
			}
		}
		if(sOptima.n_monedas > 100){
			sOptima.n_monedas = -1;
		}
	}
	
	public String dameSolucion(){
		if (n_monedas == -1)
			return "No hay cambio posible";
		if (n_monedas == 0)
			return "Se entrego la cantidad exacta";
		String s = "Cantidad Monedas: "+n_monedas+" -> ";
		for(int i = 0; i < 12; i++){
			if(monedas[i]>0){
				s += valorMoneda(i)+"€: "+monedas[i]+" || ";
			}
		}
		return s;
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

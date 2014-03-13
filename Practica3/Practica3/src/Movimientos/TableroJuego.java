package Movimientos;

public class TableroJuego {
	int [][] tablero;
	int numCyF;
	int blancaP=1;
	int blancaR=3;
	int rojaP=2;
	int rojaR=4;	
	int tama�o;
	int rojas,blancas;
	
	public TableroJuego(int t){
		tama�o = t;
		numCyF = t;		
		tablero = new int[tama�o][tama�o];
		for (int i = 0; i < tama�o/2;i++){			
			tablero[0][i*2]=rojaP;
			tablero[1][i*2+1]=rojaP;
			tablero[2][i*2]=rojaP;
			rojas += 3;
			tablero[tama�o-2][i*2]=blancaP;
			tablero[tama�o-1][i*2+1]=blancaP;
			tablero[tama�o-3][i*2+1]=blancaP;
			blancas += 3;
		}
	}
	
	public TableroJuego(TableroJuego o){
		numCyF = o.numCyF;
		blancaP=1;
		blancaR=3;
		rojaP=2;
		rojaR=4;	
		tama�o = o.tama�o;
		rojas = o.rojas;
		blancas = o.blancas;
		tablero = new int[tama�o][tama�o];
		for(int i = 0; i < tama�o;i++)
			for(int j = 0; j < tama�o;j++)
				tablero[i][j] = o.tablero[i][j];
	}
	
	public void inicializar(){
		tablero = new int[tama�o][tama�o];
		rojas = blancas = 0;
		for (int i = 0; i < tama�o/2;i++){			
			tablero[0][i*2]=rojaP;
			tablero[1][i*2+1]=rojaP;
			tablero[2][i*2]=rojaP;
			rojas += 3;
			tablero[tama�o-2][i*2]=blancaP;
			tablero[tama�o-1][i*2+1]=blancaP;
			tablero[tama�o-3][i*2+1]=blancaP;
			blancas += 3;
		}
		System.gc();//por quitar un poco de basura...
	}
	
	public int dameCasilla(int fila,int columna){return tablero[fila][columna];}
	public void ponCasilla(int fila,int columna, int estado){tablero[fila][columna]=estado;}
	public int dame_nRojas(){return rojas;}
	public int dame_nBlancas(){return blancas;}
	public void quitaBlanca(){blancas--;}
	public void quitaRoja(){rojas--;}
	public void ponBlanca(){blancas++;}
	public void ponRoja(){rojas++;}
	public int dameTamano(){return tama�o;}
}

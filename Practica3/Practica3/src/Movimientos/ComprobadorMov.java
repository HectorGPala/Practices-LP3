package Movimientos;

import javax.swing.JOptionPane;

public class ComprobadorMov{	
	boolean segundoClick;
	int filaOrigen;
	int columnaOrigen;
	int filaA;
	int columnaA;
	int jugador;
	int inicia;
	String displayString;
	boolean esGanador,ataque;
	boolean seguirTirando;
	boolean maquina;
	int profundidad;
	int tipo;
	
	public ComprobadorMov(boolean m,int p,int h){
		displayString = "Bienvenido";
		segundoClick = false;
		jugador = 1;
		inicia = jugador;
		esGanador = false;
		ataque = false;
		seguirTirando = false;
		maquina = m;
		profundidad = p;
		tipo = h;
	}	
	
	public void procesaMovimiento(int x,int y,TableroJuego tj){
		if(!esGanador){
			if(seguirTirando){
				if(esAtaque(columnaOrigen,filaOrigen,x,y,tj)){
					realizarMovimiento(x,y,tj);
					if(tj.dame_nBlancas() == 0 || tj.dame_nRojas() == 0){
						esGanador = true;
						JOptionPane.showMessageDialog(null,"FIN DE JUEGO! GANA EL JUGADOR "+jugador);
						actualizaDisplay("FIN DE JUEGO PULSE INICIO",tj);
						return;
					}
					if(!seguirTirando){
						cambiaJugador();
						actualizaDisplay("Movimiento Correcto",tj);
						if(comprobarBloqueo(tj)){
							cambiaJugador();
							JOptionPane.showMessageDialog(null,"BLOQUEO! GANA EL JUGADOR "+jugador);
							actualizaDisplay("FIN DE JUEGO PULSE INICIO",tj);
							esGanador = true;
						}							
					}
					else
						actualizaDisplay("Sigue Tirando",tj);
				}
				return;
			}
			if(jugador == 2 && maquina){
				int n = tj.dame_nBlancas();
				MovOrdenador m = mueveOrd(tj,profundidad,n,Integer.MAX_VALUE);
				if(m.hayMovimiento()){
					filaOrigen = m.getOrY();
					columnaOrigen = m.getOrX();
					realizarMovimiento(m.getDesX(),m.getDesY(),tj);
					seguirTirando = false;
					actualizaDisplay("MovOrdenador correcto",tj);
					if(tj.dame_nBlancas() == 0){
						esGanador = true;
						JOptionPane.showMessageDialog(null,"GANA EL JUGADOR "+jugador);
						actualizaDisplay("FIN DE JUEGO PULSE INICIO",tj);
						return;
					}					
				}
				else{
					esGanador = true;
					cambiaJugador();
					JOptionPane.showMessageDialog(null,"BLOQUEO! GANA EL JUGADOR "+jugador);
					actualizaDisplay("FIN DE JUEGO PULSE INICIO",tj);
					return;
				}
				cambiaJugador();
				if(tj.dame_nBlancas() == 0){
					esGanador = true;
					JOptionPane.showMessageDialog(null,"GANA EL JUGADOR "+jugador);
					actualizaDisplay("FIN DE JUEGO PULSE INICIO",tj);
					return;
				}
				else if(comprobarBloqueo(tj)){
					cambiaJugador();
					JOptionPane.showMessageDialog(null,"BLOQUEO! GANA EL JUGADOR "+jugador);
					actualizaDisplay("FIN DE JUEGO PULSE INICIO",tj);
					esGanador = true;
					return;
				}
				return;
			}
			if(x == -1 || y == -1)
				actualizaDisplay("Haga click en el tablero",tj);
			else{
				if(!segundoClick){
					int std=tj.dameCasilla(y,x);
					if(jugador == std || std == jugador+2){
						segundoClick=true;
						actualizaDisplay("Ficha seleccionada: "+x+", "+y,tj);
						tj.ponCasilla(y, x, std+4);
						filaOrigen=y;
						columnaOrigen=x;				
					}
					else
						actualizaDisplay("Seleccione ficha...",tj);			
				}
				else{
					if( x == columnaOrigen && y  == filaOrigen){
						int std = tj.dameCasilla(y,x);
						tj.ponCasilla(y, x, std-4);
						actualizaDisplay("Deseleccionada, volver a seleccionar...	",tj);
						segundoClick=false;
						return;
					}
					int std = tj.dameCasilla(y,x);
					if(std == jugador || std == jugador+2){
						tj.ponCasilla(y,x,std+4);
						tj.ponCasilla(filaOrigen,columnaOrigen,tj.dameCasilla(filaOrigen,columnaOrigen)-4);
						filaOrigen = y;
						columnaOrigen = x;
						actualizaDisplay("Ficha seleccionada: "+x+", "+y,tj);
						return;
					}
					if(comprobarMovimiento(x,y,tj)){
						realizarMovimiento(x,y,tj);
						actualizaDisplay("Movimiento correcto",tj);
						segundoClick = false;
						if(!seguirTirando){
							cambiaJugador();
						}
						else{
							actualizaDisplay("Sigue Tirando",tj);
							return;
						}
						if(tj.dame_nBlancas() == 0 || tj.dame_nRojas() == 0){
							esGanador = true;
							cambiaJugador();
							JOptionPane.showMessageDialog(null,"FIN DE JUEGO! GANA EL JUGADOR "+jugador);
							actualizaDisplay("FIN DE JUEGO PULSE INICIO",tj);
						}
						else if(comprobarBloqueo(tj)){
							esGanador = true;
							cambiaJugador();
							JOptionPane.showMessageDialog(null,"BLOQUEO! GANA EL JUGADOR "+jugador);
							actualizaDisplay("FIN DE JUEGO PULSE INICIO",tj);
						}
					}
				}
			}
		}
	}
	
	public void cambiaJugador(){
		if(jugador==1)
			jugador=2;
		else 
			jugador=1;
	}
	
	public boolean comprobarBloqueo(TableroJuego tj){
		int n = tj.dameTamano();
		boolean movNormal = false;
		boolean parar = false;
		boolean movAtaque = false;
		for(int i = 0; i < n && !parar; i++){
			for(int j = 0; j < n && !parar;j++){
				if(tj.dameCasilla(i,j) == jugador || tj.dameCasilla(i,j) ==jugador+2){
					movNormal = esAdyacente(j,i,j+1,i+1,tj) || esAdyacente(j,i,j-1,i-1,tj) || esAdyacente(j,i,j+1,i-1,tj) || esAdyacente(j,i,j-1,i+1,tj);
					movAtaque = esAtaque(j,i,j+2,i+2,tj) || esAtaque(j,i,j-2,i-2,tj) || esAtaque(j,i,j+2,i-2,tj) || esAtaque(j,i,j-2,i+2,tj);
					parar = movNormal || movAtaque;
				}
			}
		}
		return !parar;
	}
	
	public boolean comprobarMovimiento(int x,int y,TableroJuego tj){
		if (tj.dameCasilla(y, x)!=0){
			actualizaDisplay("Error casilla ocupada",tj);
			return false;
		}
		else{
			if(esAdyacente(columnaOrigen,filaOrigen,x,y,tj)){
				seguirTirando = false;
				ataque = false;
				return true;				
			}
			else{
			if(esAtaque(columnaOrigen,filaOrigen,x,y,tj)){
				ataque = true;
				return true;
			}
			else
				actualizaDisplay("Movimiento incorrecto!",tj);
				seguirTirando = false;
				return false;
			}
		}
	}
	
	public boolean esDama(TableroJuego tj,int x,int y){
		if(x>=0 && x < tj.dameTamano() && y >= 0 && y < tj.dameTamano())
			return tj.dameCasilla(y,x) == jugador+6 || tj.dameCasilla(y,x) == jugador+2;
		else
			return false;
	}
	
	public boolean esAdyacente(int cO,int fO,int x, int y,TableroJuego tj){
		int i = (int)Math.pow(-1,jugador);
		if(x >= 0 && y >= 0 && x <= tj.dameTamano()-1 && y <= tj.dameTamano()-1){
			if(tj.dameCasilla(y,x) == 0){
				if(esDama(tj,cO,fO))
					return (cO+1 == x && fO+1 == y) || (cO-1 == x && fO-1 == y) || (cO+1 == x && fO-1 == y) || (cO-1 == x && fO+1 == y);
				else
					return (cO+1 == x && fO+i == y) || (cO-1 == x && fO+i == y);
			}
		}
		return false;
	}
	
	public boolean esAtaque(int cO,int fO,int x, int y,TableroJuego tj){
		int i = (int)Math.pow(-1,jugador);
		if(x >= 0 && y >= 0 && x <= tj.dameTamano()-1 && y <= tj.dameTamano()-1){
			if(tj.dameCasilla(y,x) == 0){
				if(esDama(tj,cO,fO)){
					if((cO+2 == x && fO+2 == y) && ((tj.dameCasilla(y-1,x-1) == jugador+(-1)*i) || (tj.dameCasilla(y-1,x-1) == jugador+(-1)*i+2))){
						filaA = y-1;
						columnaA = x-1;
						ataque = true;
						return true;
					}
					if((cO-2 == x && fO-2 == y) && ((tj.dameCasilla(y+1,x+1) == jugador+(-1)*i) || (tj.dameCasilla(y+1,x+1) == jugador+(-1)*i+2))){
						filaA = y+1;
						columnaA = x+1;
						ataque = true;
						return true;
					}
					if((cO+2 == x && fO-2 == y) && ((tj.dameCasilla(y+1,x-1) == jugador+(-1)*i) || (tj.dameCasilla(y+1,x-1) == jugador+(-1)*i+2))){
						filaA = y+1;
						columnaA = x-1;
						ataque = true;
						return true;
					}
					if((cO-2 == x && fO+2 == y) && ((tj.dameCasilla(y-1,x+1) == jugador+(-1)*i) || (tj.dameCasilla(y-1,x+1) == jugador+(-1)*i+2))){
						filaA = y-1;
						columnaA = x+1;
						ataque = true;
						return true;
					}
					return false;
				}
				if((cO+2 == x && fO+2*i == y) && ((tj.dameCasilla(fO+i,cO+1) == jugador+(-1)*i) || (tj.dameCasilla(fO+i,cO+1) == jugador+(-1)*i+2))){
					filaA = fO+i;
					columnaA = cO+1;
					ataque = true;
					return true;
				}
				if((cO-2 == x && fO+2*i == y) && ((tj.dameCasilla(fO+i,cO-1) == jugador+(-1)*i) || (tj.dameCasilla(fO+i,cO-1) == jugador+(-1)*i+2))){
					filaA = fO+i;
					columnaA = cO-1;
					ataque = true;
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public void realizarMovimiento(int x,int y,TableroJuego tj){
		int ficha = tj.dameCasilla(filaOrigen,columnaOrigen);
		ataque = esAtaque(columnaOrigen,filaOrigen,x,y,tj);
		if(jugador == 1 && y == 0)
			tj.ponCasilla(y, x,3);
		else if(jugador == 2 && y == tj.dameTamano()-1)
			tj.ponCasilla(y, x,4);
		else{
			if((jugador == 1) || (jugador == 2 && !maquina))
				tj.ponCasilla(y,x,ficha-4);
			else
				tj.ponCasilla(y,x,ficha);				
		}
		tj.ponCasilla(filaOrigen, columnaOrigen,0);	
		if(ataque){
			tj.ponCasilla(filaA,columnaA, 0);
			if(jugador == 1)
				tj.quitaRoja();
			else
				tj.quitaBlanca();
			seguirTirando = seguirTirando(x,y,tj);
			if(seguirTirando){
				if(!(jugador == 2 && maquina))
					tj.ponCasilla(y,x,tj.dameCasilla(y,x)+4);
				else if(jugador == 2 && maquina){					
					if(esAtaque(columnaOrigen, filaOrigen,columnaA+1,filaA+1,tj)){x = columnaA+1;y = filaA+1;}
					else if(esAtaque(columnaOrigen, filaOrigen,columnaA-1,filaA+1,tj)){x = columnaA-1;y = filaA+1;}
					else if(esAtaque(columnaOrigen, filaOrigen,columnaA+1,filaA-1,tj)){x = columnaA+1;y = filaA-1;}
					else if(esAtaque(columnaOrigen, filaOrigen,columnaA-1,filaA-1,tj)){x = columnaA-1;y = filaA-1;}						
					realizarMovimiento(x, y, tj);				
				}	
			}
		}
	}
	
	public boolean seguirTirando(int x, int y,TableroJuego tj){
		if(esDama(tj,x,y)){
			boolean b = esAtaque(x,y,x+2,y+2,tj) || esAtaque(x,y,x-2,y-2,tj) || esAtaque(x,y,x+2,y-2,tj) || esAtaque(x,y,x-2,y+2,tj);
			if(b){
				columnaOrigen = x;
				filaOrigen = y;
			}
			return b;
		}
		else{
			boolean b = esAtaque(x,y,x+2,y+2,tj) || esAtaque(x,y,x-2,y-2,tj) || esAtaque(x,y,x+2,y-2,tj) || esAtaque(x,y,x-2,y+2,tj);
			if(b){
				columnaOrigen = x;
				filaOrigen = y;
			}
			return b;
		}
	}	
	
	public void actualizaDisplay(String s,TableroJuego tj){
		displayString = s+" Fichas RM: "+tj.dame_nBlancas()+" Fichas Barça: "+tj.dame_nRojas();
	}
	
	public String dameMsj(){return displayString;}
	public boolean getMaquina(){return maquina;}
	
	public MovOrdenador mueveOrd(TableroJuego tj,int nivel,int fichas,int Beta){
		int n = tj.dameTamano();
		int n_fichas = 0;
		int nRojas = tj.dame_nRojas();
		MovOrdenador m = new MovOrdenador();
		cambiaJugador();
		if(nivel <= 0 || tj.dame_nBlancas() == 0 || tj.dame_nRojas() == 0 || comprobarBloqueo(tj)){
			m.setValor(heuristica(tj,fichas));
			m.setMov(-1,-1,-1,-1);
			cambiaJugador();
			return m;
		}
		cambiaJugador();
		for(int j = 0; j < n && n_fichas != nRojas && m.getValor() < Beta;j++)
			for(int i = 0; i < n && n_fichas != nRojas && m.getValor() < Beta;i++){
				if(tj.dameCasilla(j,i) == jugador || tj.dameCasilla(j,i) == jugador+2){
					n_fichas++;
					if(esAtaque(i,j,i+2,j+2,tj))
						ensayaAtaque(tj,i,j,i+2,j+2,m,nivel);
					if(esAtaque(i,j,i-2,j+2,tj))
						ensayaAtaque(tj,i,j,i-2,j+2,m,nivel);
					if(esAtaque(i,j,i-2,j-2,tj))
						ensayaAtaque(tj,i,j,i-2,j-2,m,nivel);
					if(esAtaque(i,j,i+2,j-2,tj))
						ensayaAtaque(tj,i,j,i+2,j-2,m,nivel);
					if(esAdyacente(i,j,i-1,j-1,tj))
						ensayaMov(tj,i,j,i-1,j-1,m,nivel);
					if(esAdyacente(i,j,i+1,j-1,tj))
						ensayaMov(tj,i,j,i+1,j-1,m,nivel);
					if(esAdyacente(i,j,i+1,j+1,tj))
						ensayaMov(tj,i,j,i+1,j+1,m,nivel);
					if(esAdyacente(i,j,i-1,j+1,tj))
						ensayaMov(tj,i,j,i-1,j+1,m,nivel);				
				}
			}
		return m;		
	}
	
	public MovOrdenador mueveUser(TableroJuego tj,int nivel,int fichas,int alfa){
		int n = tj.dameTamano();
		int n_fichas = 0;
		int nBlancas = tj.dame_nBlancas();
		MovOrdenador m = new MovOrdenador();
		cambiaJugador();
		if(nivel <= 0 || tj.dame_nRojas() == 0 || tj.dame_nBlancas() == 0 || comprobarBloqueo(tj)){
			m.setValor(heuristica(tj,fichas));
			m.setMov(-1,-1,-1,-1);
			cambiaJugador();
			return m;
		}
		cambiaJugador();
		m.setValor(Integer.MAX_VALUE);
		for(int j = 0; j < n && n_fichas != nBlancas && m.getValor() > alfa; j++)
			for(int i = 0; i < n && n_fichas != nBlancas && m.getValor() > alfa;i++){
				if(tj.dameCasilla(j,i) == jugador || tj.dameCasilla(j,i) == jugador+2){
					n_fichas++;
					if(esAtaque(i,j,i+2,j+2,tj))
						ensayaAtaqueUSR(tj,i,j,i+2,j+2,m,nivel);
					if(esAtaque(i,j,i-2,j+2,tj))
						ensayaAtaqueUSR(tj,i,j,i-2,j+2,m,nivel);
					if(esAtaque(i,j,i-2,j-2,tj))
						ensayaAtaqueUSR(tj,i,j,i-2,j-2,m,nivel);
					if(esAtaque(i,j,i+2,j-2,tj))
						ensayaAtaqueUSR(tj,i,j,i+2,j-2,m,nivel);
					if(esAdyacente(i,j,i+1,j+1,tj))
						ensayaMovUSR(tj,i,j,i+1,j+1,m,nivel);
					if(esAdyacente(i,j,i+1,j-1,tj))
						ensayaMovUSR(tj,i,j,i+1,j-1,m,nivel);
					if(esAdyacente(i,j,i-1,j+1,tj))
						ensayaMovUSR(tj,i,j,i-1,j+1,m,nivel);
					if(esAdyacente(i,j,i-1,j-1,tj))
						ensayaMovUSR(tj,i,j,i-1,j-1,m,nivel);				
				}
			}
		
		return m;
	}
	
	public void ensayaMov(TableroJuego tj,int x1,int y1,int x2,int y2,MovOrdenador m,int nivel){
		columnaOrigen = x1;
		filaOrigen = y1;
		int estado = tj.dameCasilla(y1,x1);
		int fichas = tj.dame_nBlancas();
		realizarMovimiento(x2,y2,tj);
		cambiaJugador();
		MovOrdenador mAux = mueveUser(tj,nivel-1,fichas,m.getValor());
		cambiaJugador();
		if(mAux.getValor() > m.getValor()){
			m.setValor(mAux.getValor());
			m.setMov(x1,y1,x2,y2);
		}
		deshacerMov(tj,x1,y1,x2,y2,estado);
	}
	
	public void ensayaAtaque(TableroJuego tj,int x1,int y1,int x2,int y2,MovOrdenador m,int nivel){
		columnaOrigen = x1;
		filaOrigen = y1;
		TableroJuego tjClon = new TableroJuego(tj);
		int fichas = tjClon.dame_nBlancas();		
		realizarMovimiento(x2,y2,tjClon);
		cambiaJugador();
		MovOrdenador mAux = mueveUser(tjClon,nivel-1,fichas,m.getValor());
		cambiaJugador();
		if(mAux.getValor() > m.getValor()){
			m.setValor(mAux.getValor());
			m.setMov(x1,y1,x2,y2);
		}
	}
	
	public void ensayaMovUSR(TableroJuego tj,int x1,int y1,int x2,int y2,MovOrdenador m,int nivel){
		columnaOrigen = x1;
		filaOrigen = y1;
		int estado = tj.dameCasilla(y1,x1);
		int fichas = tj.dame_nRojas();
		realizarMovimiento(x2,y2,tj);
		cambiaJugador();
		MovOrdenador mAux = mueveOrd(tj,nivel-1,fichas,m.getValor());
		cambiaJugador();
		if(mAux.getValor() < m.getValor()){
			m.setValor(mAux.getValor());
			m.setMov(x1,y1,x2,y2);
		}
		deshacerMov(tj,x1,y1,x2,y2,estado);
	}
	
	public void ensayaAtaqueUSR(TableroJuego tj,int x1,int y1,int x2,int y2,MovOrdenador m,int nivel){
		columnaOrigen = x1;
		filaOrigen = y1;
		TableroJuego tjClon = new TableroJuego(tj);
		int fichas = tjClon.dame_nRojas();
		realizarMovimiento(x2,y2,tjClon);
		cambiaJugador();
		MovOrdenador mAux = mueveOrd(tjClon,nivel-1,fichas,m.getValor());
		cambiaJugador();
		if(mAux.getValor() < m.getValor()){
			m.setValor(mAux.getValor());
			m.setMov(x1,y1,x2,y2);
		}
	}
	
	public void deshacerMov(TableroJuego tj,int cO,int fO,int cD,int fD,int std){
		tj.ponCasilla(fD,cD,0);
		tj.ponCasilla(fO,cO,std);
	}
	
	public int heuristica(TableroJuego tj,int nf){		
		if(tipo == 0){
			int n = tj.dameTamano();
			int h = 0;
			int numFich = 0;
			int numFichC = 0; 
			if(jugador == 1){
				h = (nf-tj.dame_nRojas())*2;
				numFich = tj.dame_nBlancas();
				numFichC = tj.dame_nRojas();
			}
			if(jugador == 2){
				h = (nf-tj.dame_nBlancas())*2;
				numFich = tj.dame_nRojas();
				numFichC = tj.dame_nBlancas();
			}
			int n_fichas = 0;
			//cambiaJugador();
			if(numFichC == 0){
				return h+1;
			}
			//cambiaJugador();
			for(int j= 0; j < n && n_fichas != numFich;j++)
				for(int i = 0; i < n && n_fichas != numFich;i++){
					if(tj.dameCasilla(j,i) == jugador || tj.dameCasilla(j,i) == jugador+2){
						n_fichas++;
						if(esAtaque(i,j,i+2,j+2,tj)){h++;}
						if(esAtaque(i,j,i-2,j+2,tj)){h++;}
						if(esAtaque(i,j,i+2,j-2,tj)){h++;}
						if(esAtaque(i,j,i-2,j-2,tj)){h++;}
						if(esDama(tj,j,i)){h++;}
					}
				}
			return h;
		}
		if(tipo == 1){
			int n = tj.dameTamano();
			int h = 0;
			int numFich = 0; 
			if(jugador == 1)
				numFich = tj.dame_nBlancas();
			if(jugador == 2)
				numFich = tj.dame_nRojas();
			int n_fichas = 0;
			for(int i = 0; i < n && n_fichas != numFich; i++)
				for(int j = 0; j < n && n_fichas != numFich;j++){
					if(tj.dameCasilla(j,i) == jugador || tj.dameCasilla(j,i) == jugador+2){
						n_fichas++;
						cambiaJugador();
						if(esAtaque(i-1,j-1,i+1,j+1,tj)){h--;}
						if(esAtaque(i-1,j+1,i+1,j-1,tj)){h--;}
						if(esAtaque(i+1,j-1,i-1,j+1,tj)){h--;}
						if(esAtaque(i+1,j+1,i-1,j-1,tj)){h--;}
						cambiaJugador();
					}
				}
			return h;
		}
		if(tipo == 2){
			int n = tj.dameTamano();
			int h = 0;
			int numFich = 0;
			int numFichC = 0; 
			if(jugador == 1){
				h = (nf-tj.dame_nRojas())*2;
				numFich = tj.dame_nBlancas();
				numFichC = tj.dame_nRojas();
			}
			if(jugador == 2){
				h = (nf-tj.dame_nBlancas())*2;
				numFich = tj.dame_nRojas();
				numFichC = tj.dame_nBlancas();
			}
			int n_fichas = 0;
			if(numFichC == 0){
				return Integer.MAX_VALUE;
			}
			for(int i = 0; i < n && n_fichas != numFich; i++)
				for(int j = 0; j < n && n_fichas != numFich;j++){
					if(tj.dameCasilla(j,i) == jugador || tj.dameCasilla(j,i) == jugador+2){
						n_fichas++;
						if(esAtaque(i,j,i+2,j+2,tj)){h++;}
						if(esAtaque(i,j,i-2,j+2,tj)){h++;}
						//if(esAtaque(i,j,i+2,j-2,tj)){h++;}
						//if(esAtaque(i,j,i-2,j-2,tj)){h++;}
						if(esDama(tj,j,i)){h++;}
						cambiaJugador();
						if(esAtaque(i-1,j-1,i+1,j+1,tj)){h--;}
						if(esAtaque(i-1,j+1,i+1,j-1,tj)){h--;}
						if(esAtaque(i+1,j-1,i-1,j+1,tj)){h--;}
						if(esAtaque(i+1,j+1,i-1,j-1,tj)){h--;}
						cambiaJugador();
					}
				}
			return h;
		}
		else 
			return 0;		
	}
}
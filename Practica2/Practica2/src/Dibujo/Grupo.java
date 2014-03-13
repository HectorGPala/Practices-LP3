package Dibujo;

public class Grupo {
	Grupo padre;
	int profundidad;
	
	public Grupo(){
		padre=null;
		profundidad=0;
	}	
	
	Grupo dameGrupo(){	
		if(this.padre==null) return this;
		else return this.padre.dameGrupo();
	}
	                     
	Grupo unionGrupo(Grupo g2){
		if (this.profundidad > g2.profundidad){			
			g2.dameGrupo().padre = this;
		}
		if (this.profundidad==g2.profundidad){
			g2.dameGrupo().padre = this;
			this.profundidad++;
			g2.profundidad++;
			
		}
		else 
			if(this.profundidad < g2.profundidad) this.dameGrupo().padre=g2;		
		return g2;
	}	
}

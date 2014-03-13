package Supermercado;

public class Categoria {
	private Producto productos[];
	private int numProds;
	final static int max = 20;
	
	public Categoria(){
		productos = new Producto[max];
		numProds = 0;
	}

	public void setNumProds(int numProds) {
		this.numProds = numProds;
	}

	public int getNumProds() {
		return numProds;
	}

	public void setProductos(Producto n) {
		this.productos[numProds] = n;
		numProds++;
	}

	public Producto getProductos(int i) {
		return productos[i];
	}
}
	
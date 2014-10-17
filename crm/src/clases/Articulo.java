package clases;

public class Articulo {
	private String codigo;
	private String nombre;
	private String nombre2;
	private String pvp;
	private String stock;
	
	
	public String getCodigo(){
		return this.codigo;
	}
	
	public String getNombre(){
		return this.nombre;
	}

	
	public Articulo(String codigo, String nombre){
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public String getNombre2() {
		return nombre2;
	}


	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getPvp() {
		return pvp;
	}


	public void setPvp(String pvp) {
		this.pvp = pvp;
	}


	public String getStock() {
		return stock;
	}


	public void setStock(String stock) {
		this.stock = stock;
	}
	
	

}

package clases;

public class Cliente {
	private String codigo;
	private String nombre;
	private String direccs;
	private String[] tlfs;
	private String cp;
	private String[] ofertas;
	private int vendedor;
	private String Obser;
	private String credit;
	private String cif;
	private String tipoIVA;
	private String formaPago;
	
	
	public String getFormaPago() {
		return formaPago;
	}


	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}


	public String getTipoIVA() {
		if(!tipoIVA.trim().equals(""))
				return tipoIVA;
		else
			return "0";
	}


	public void setTipoIVA(String tipoIVA) {
		this.tipoIVA = tipoIVA;
	}


	public String getCif() {
		return cif;
	}


	public void setCif(String cif) {
		this.cif = cif;
	}


	public String getCodigo() {
		return codigo;
	}


	public String getNombre() {
		return nombre;
	}


	public String getCredit() {
		return credit;
	}


	public void setCredit(String credit) {
		this.credit = credit;
	}


	public String getObser() {
		return Obser;
	}


	public void setObser(String obser) {
		Obser = obser;
	}


	public Cliente(String codigo,String nombre){
		this.codigo = codigo;
		this.nombre = nombre;
	}


	@Override
	public String toString() {
		return "Cliente [codigo=" + codigo + "]";
	}


	public String getDireccs() {
		return direccs;
	}


	public void setDireccs(String direccs) {
		this.direccs = direccs;
	}


	public String[] getTlfs() {
		return tlfs;
	}


	public void setTlfs(String[] tlfs) {
		this.tlfs = tlfs;
	}


	public String getCp() {
		return cp;
	}


	public void setCp(String cp) {
		this.cp = cp;
	}


	public String[] getOfertas() {
		return ofertas;
	}


	public void setOfertas(String[] ofertas) {
		this.ofertas = ofertas;
	}


	public int getVendedor() {
		return vendedor;
	}


	public void setVendedor(int vendedor) {
		this.vendedor = vendedor;
	}
	
	

}

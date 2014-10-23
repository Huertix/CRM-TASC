package clases;

public class Cliente {
	private String codigo = "";
	private String nombre = "";
	private String direccs = "";
	private String poblacion = "";
	private String provincia = "";
	private String email = "";
	private String tlf = "";
	private String mov = "";
	private String cp = "";
	private String[] ofertas;
	private String vendedor = "";
	private String Obser = "";
	private String credit = "";
	private String cif = "";
	private String tipoIVA = "";
	private String formaPago = "";
	private String contact = "";
	private boolean isSaved = false;
	
	




	public Cliente(){
		super();
	
	}
	
	public Cliente(String codigo,String nombre){
		this.codigo = codigo;
		this.nombre = nombre;

	}
	
	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
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


	public String getTlf() {
		return tlf;
	}


	public void setTlf(String tlf) {
		this.tlf = tlf;
	}
	
	public String getMov() {
		return mov;
	}

	public void setMov(String mov) {
		this.mov = mov;
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


	public String getVendedor() {
		return vendedor;
	}


	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	
	public boolean minValues(){
		if(!nombre.equals("")&&!direccs.equals("")&&
				!cif.equals("")&&!tlf.equals("")&&
				!cp.equals("")&&!poblacion.equals("")&&
				!provincia.equals("")&&!contact.equals("")){
			
			return true;
		}
		return false;
	}
	
	

}

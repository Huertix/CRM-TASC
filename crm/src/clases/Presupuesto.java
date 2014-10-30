package clases;

import javax.swing.JTable;

public class Presupuesto {
	
	Cliente cliente;
	String fecha;
	String nOferta;
	String base;
	String iva;
	String totalIva;
	String total;
	JTable table;
	
	
	public Presupuesto(){
		super();
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getnOferta() {
		return nOferta;
	}
	public void setnOferta(String nOferta) {
		this.nOferta = nOferta;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getTotalIva() {
		return totalIva;
	}
	public void setTotalIva(String totalIva) {
		this.totalIva = totalIva;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}


	public JTable getTable() {
		return table;
	}


	public void setTable(JTable table) {
		this.table = table;
	}

}

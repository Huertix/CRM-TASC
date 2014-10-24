package clases;

/*
 * Clase que gestiona la conexión a la base de datos
 * Tres Objetos para la conexión:
 * 1) Connection
 * 2) Statement : Ejecuta las sentencias SQL
 * 3) ResultSet: Guarda los resultados en la tabla (tabla virtual)
 */


import java.sql.*;

import javax.swing.JOptionPane;

public class BaseDatos {
	
	//Objeto tipo Conexión
	private Connection conexion = null;
	//Conectar
	private void Conectar(){
		
		try{
			
			//Cargar el Driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conexion = DriverManager.getConnection("jdbc:sqlserver://192.168.1.106\\SQLEXPRES12:49231;databaseNAME=2014LY","admin","admin");
			//JOptionPane.showMessageDialog(null, "Connection: "+!conexion.isClosed(),"Info",JOptionPane.INFORMATION_MESSAGE);
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Fallo Conexión conector","Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	//Consultar
	public ResultSet Consultar(String SQL){
		
		this.Conectar();
		ResultSet rs = null;
		Statement sentencia = null;
		try{
			
			sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = sentencia.executeQuery(SQL);
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Fallo Conexión SQL (CONSULTAR): \n"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Fallo  (CONSULTAR): \n"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		
		return rs;
		
		
	}
	
	public void Ingresar(String sql){
		this.Conectar();
		Statement sentencia = null;
		try{
			
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sql);

			
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Fallo Conexión SQL (INGRESAR): \n"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Fallo (INGRESAR): \n"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void Actualizar(String sql){
		this.Conectar();
		Statement sentencia = null;
		try{
			
			sentencia = conexion.createStatement();
			sentencia.executeQuery(sql);
			
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Fallo Conexión SQL","Error",JOptionPane.ERROR_MESSAGE);
			
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Fallo Conexión D","Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void Eliminar(String sql){
		this.Conectar();
		Statement sentencia = null;
		try{
			
			sentencia = conexion.createStatement();
			sentencia.executeQuery(sql);
			
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Fallo Conexión SQL","Error",JOptionPane.ERROR_MESSAGE);
			
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Fallo Conexión E","Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}

}

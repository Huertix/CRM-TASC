package clases;

/*
 * Clase que gestiona la conexión a la base de datos
 * Tres Objetos para la conexión:
 * 1) Connection
 * 2) Statement : Ejecuta las sentencias SQL
 * 3) ResultSet: Guarda los resultados en la tabla (tabla virtual)
 */



import java.awt.Cursor;
import java.io.IOException;
import java.sql.*;

import javax.swing.JOptionPane;




import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class BaseDatos {
	
	boolean remote;
	
	
	public BaseDatos(){
		super();
	}
	
	public BaseDatos(boolean isRemote){
		remote = isRemote;
	}
	
	
	//Objeto tipo Conexión
	private Connection conexion = null;
	private Session session= null;
	//Conectar
	
	private void Conectar(){
		
		int lport=49256;
		//int rport=49231;
		int rport = 49256;
		int port = 0;
		String user="servidor";
		String password="Tasc2011";
		String rhost="192.168.1.6\\SQLEXPRES12";
		//String rhost="127.0.0.1\\SQLEXPRES12";
		String host="tascsl.myftp.org";
		
		try{
			
			if(remote){
		
				try{
				    String comando = "ssh -L 49256:192.168.1.6:49256 servidor@tascsl.myftp.org";
				    final Process proceso = Runtime.getRuntime().exec(comando);
				    
				} catch(IOException e){
				 
				}
	            
				//Cargar el Driver
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conexion = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1\\SQLEXPRES12:49256;databaseNAME=2014LY","comercial","comercial");
				//JOptionPane.showMessageDialog(null, "Connection: "+!conexion.isClosed(),"Info",JOptionPane.INFORMATION_MESSAGE);
				
			}

			else{
				//Cargar el Driver
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conexion = DriverManager.getConnection("jdbc:sqlserver://192.168.1.6\\SQLEXPRES12:49256;databaseNAME=2014LY","comercial","comercial");
				//JOptionPane.showMessageDialog(null, "Connection: "+!conexion.isClosed(),"Info",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(),"SQL Error",JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Fallo Conexión conector","Error",JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
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

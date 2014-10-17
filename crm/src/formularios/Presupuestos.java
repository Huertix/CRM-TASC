package formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

import clases.BaseDatos;
import clases.DatePicker;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;

import org.jdesktop.swingx.JXDatePicker;

public class Presupuestos extends JFrame {

	private JPanel contentPane;
	private final BaseDatos bd;
	private ResultSet  rs = null;
	private JTable tabla ;	
	private DefaultTableModel modelo;
	private String fechaIni;
	private String fechaFin;


	
	public Presupuestos(String codigo,BaseDatos b) {
		this.bd = b;
	
		
		
		setTitle("CRM TASC - VISOR PRESUPUESTOS");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		setBounds(0, 0, 1000, 700);
		setLocationRelativeTo(null);
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel presuLabel = new JLabel("N.PRESUPUESTO");
		presuLabel.setBounds(12, 12, 150, 24);
		presuLabel.setHorizontalAlignment(SwingConstants.LEFT);
		presuLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(presuLabel);
		
		final JLabel jFecha = new JLabel();
		jFecha.setFont(new Font("arial",Font.BOLD,18));
		jFecha.setBounds(200, 35, 250, 24);
		contentPane.add(jFecha);
		
		final JLabel aceptLabel = new JLabel();
		aceptLabel.setBounds(400, 35, 150, 24);
		aceptLabel.setHorizontalAlignment(SwingConstants.LEFT);
		aceptLabel.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(aceptLabel);
		
		final JComboBox numeroComboBox = new JComboBox();
		numeroComboBox.addItem(null);
		numeroComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int a = tabla.getRowCount();
				if(a>1){
					DefaultTableModel dm = (DefaultTableModel) tabla.getModel();
				    dm.getDataVector().removeAllElements();
				    tabla.revalidate();		
				}
				
				
				if( numeroComboBox.getSelectedItem()!=null){
					
					String st = numeroComboBox.getSelectedItem().toString();
					//JOptionPane.showMessageDialog(null, st,"ELECCION",JOptionPane.INFORMATION_MESSAGE);
					st = String.format("%10s",st);
					
					System.out.println(st);
					String sql = "SELECT articulo,definicion,unidades,precio,importe FROM d_presuv  WHERE numero = '"+st+"'"; 
					
					String sqlTrans = "SELECT d_presuv.numero,d_presuv.cliente,d_pedive.numero,d_pedive.cliente FROM d_presuv,d_pedive WHERE d_presuv.numero = '"+st
							+"' AND  d_presuv.numero = d_pedive.numero";
					String sqlFecha = "SELECT numero,fecha FROM c_presuv WHERE numero = '"+st+"'";
					
					//System.out.println(sql);
					try {
						// Consulta la Fecha del Presuspuesto
						ResultSet rs = bd.Consultar(sqlFecha);
						rs.next();
						String fecha = rs.getString("fecha");
						jFecha.setText("FECHA:  "+fecha.substring(0, 10));
						
						// Comprueba si el presupuesto corresponde a pedido
						rs = bd.Consultar(sqlTrans);
						int rows = 0;
						if (rs.last()){
							System.out.println("...................1");
							aceptLabel.setText("ACEPTADO");
						}
						else
							aceptLabel.setText("PENDIENTE");
						

							
						
						
						parseData(bd.Consultar(sql));
	
					}			
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
		
		
		modelo = new DefaultTableModel(){
		    @Override
			public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
		};
		tabla = new JTable();
		
		// Rellena el ComboBox con los nº de presupuesto
		String sql = "SELECT * FROM d_presuv WHERE cliente = "+codigo+" ORDER BY numero";
		rs = bd.Consultar(sql);
		//System.out.println(codigo);
		try{
			ArrayList<String> array = new ArrayList<String>();
			while(rs.next()){
				String numero = rs.getString("numero");
				if(!array.contains(numero))
					array.add(numero);
			}
			
			for(String a : array){
				numeroComboBox.addItem(a);
			}

		}
		catch(Exception ex){
			
		}
		contentPane.add(numeroComboBox);
		numeroComboBox.setBounds(12, 34, 143, 24);
		
		/*
		JLabel fiLabel = new JLabel("FECHA INICIO");
		fiLabel.setBounds(450, 12, 150, 24);
		fiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fiLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(fiLabel);

		JXDatePicker fechaInicioJP = new JXDatePicker();
		fechaInicioJP.setDate(Calendar.getInstance().getTime());
		fechaInicioJP.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		fechaInicioJP.setBounds(450, 34, 143, 24);
		contentPane.add(fechaInicioJP);
		
		
		JLabel ffLabel = new JLabel("FECHA FIN");
		ffLabel.setBounds(750, 12, 150, 24);
		ffLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ffLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(ffLabel);
		
		JXDatePicker fechaFinJP = new JXDatePicker();
		fechaFinJP.setDate(Calendar.getInstance().getTime());
		fechaFinJP.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		fechaFinJP.setBounds(750, 34, 143, 24);
		contentPane.add(fechaFinJP);
		
		*/
		
		
		modelo.addColumn("ARTÍCULO");
		modelo.addColumn("DEFINICIÓN");
		modelo.addColumn("UNIDADES");
		modelo.addColumn("PRECIO");
		modelo.addColumn("IMPORTE");
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(20, 70, 960, 570);
		contentPane.add(scrollPane);

		setResizable(false);
		setVisible(true);
	}
	
	
	
	// Contruye la Tabla de datos JTable
	public void  parseData(ResultSet rs) throws SQLException, ParserConfigurationException{

		
		ResultSetMetaData metaDatos = rs.getMetaData();
		
		int numeroColumnas = metaDatos.getColumnCount();

		
		
		while (rs.next())
		{
			
		   // Se crea un array que será una de las filas de la tabla.
		   Object [] fila = new Object[numeroColumnas]; // Hay 5 columnas en la tabla

		   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
		   for (int i=0;i<numeroColumnas;i++){
		      
			   Object a = rs.getObject(i+1);
			   //System.out.println(a.getClass().getName());
			   if(a.getClass().getName().equals("java.math.BigDecimal") && a.toString().equals("0.000000")){
				   a="";
			   }
			   fila[i] = a; // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			   modelo.isCellEditable(rs.getRow(), i+1);
		     
		   }
		   // Se añade al modelo la fila completa.
		   modelo.addRow(fila);
		}
		
		
		
		tabla.setModel(modelo);
				
	}
	
}

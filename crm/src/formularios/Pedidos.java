package formularios;

/**
 * QUERYS: 
 * 
 * " Comprueba si un un query está entre fecha acotada "
 * SELECT c_pedive.fecha,d_pedive.numero,d_pedive.definicion,d_pedive.unidades,d_pedive.importe FROM d_pedive,c_pedive 
 * WHERE d_pedive.cliente = 43000048 AND c_pedive.fecha > '20140110' AND c_pedive.fecha < '20140210' ORDER BY c_pedive.fecha
 * 
 * " Comprueba si un presupuesto tb está en pedido "
 * SELECT d_presuv.numero,d_presuv.cliente,d_pedive.numero,d_pedive.cliente FROM d_presuv,d_pedive 
 * WHERE d_presuv.numero = '    146024 / 146013' AND  d_presuv.numero = d_pedive.numero
 */


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;

import org.jdesktop.swingx.JXDatePicker;

import clases.BaseDatos;
import clases.Tasc;

public class Pedidos extends JFrame {

	private JPanel contentPane;
	private final BaseDatos bd;
	//private ResultSet  rs = null;
	private JTable tabla ;	
	private DefaultTableModel modelo;
	private String fechaIni;
	private String fechaFin;
	private final JXDatePicker fechaInicioJP;
	private final JXDatePicker fechaFinJP;
	private ArrayList<String> array;
	private JLabel importeIvaLabel;
	private JLabel importeLabel;
	private JLabel ivaLabel;
	private double importe;
	private double importeiva;
	private String iva = "";




	
	public Pedidos(final String codigo,BaseDatos b) {
		this.bd = b;
	
		
		setTitle("CRM TASC - VISOR PEDIDOS");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		setBounds(0, 0, 1000, 730);
		setLocationRelativeTo(null);
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel pediLabel = new JLabel("N.PEDIDO");
		pediLabel.setBounds(20, 12, 150, 24);
		pediLabel.setHorizontalAlignment(SwingConstants.LEFT);
		pediLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(pediLabel);
		
		final JLabel jFecha = new JLabel();
		jFecha.setFont(new Font("arial",Font.BOLD,18));
		jFecha.setBounds(200, 35, 250, 24);
		contentPane.add(jFecha);
		
		/*final JLabel aceptLabel = new JLabel();
		aceptLabel.setBounds(400, 35, 150, 24);
		aceptLabel.setHorizontalAlignment(SwingConstants.LEFT);
		aceptLabel.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(aceptLabel);*/
		
		final JComboBox numeroComboBox = new JComboBox();
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
					
					String sql = "SELECT articulo,unidades,definicion,precio,dto1,importe,tipo_iva,importeiva FROM d_pedive  WHERE numero = '"+st+"'"; 
					
					String sqlFecha = "SELECT c_pedive.fecha FROM d_pedive,c_pedive WHERE d_pedive.numero = c_pedive.numero AND d_pedive.numero = '"+st+"'";
					

					try {
						// Consulta la Fecha del Presuspuesto
						ResultSet rs = bd.Consultar(sqlFecha);
						rs.next();
						String  fecha = rs.getString("fecha");
						fecha = fecha.substring(0, 10);
						
						SimpleDateFormat fromDate = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat toDate = new SimpleDateFormat("dd-MM-yyyy");
						
						try {
							fecha = toDate.format(fromDate .parse(fecha));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						jFecha.setText("FECHA:  "+fecha);

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
		
		
		array = new ArrayList<String>();
		JButton updateButton = new JButton("ACTUALIZAR");
		updateButton.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				setCursor(Tasc.waitCursor);
	
				numeroComboBox.removeAllItems();
				array.removeAll(array);
				
				ResultSet  rs = null;
				
				Date date = fechaInicioJP.getDate();
				SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
				fechaIni = date_format.format(date);
				date = fechaFinJP.getDate();
				fechaFin = date_format.format(date);

				int a = tabla.getRowCount();
				if(a>1){
					DefaultTableModel dm = (DefaultTableModel) tabla.getModel();
				    dm.getDataVector().removeAllElements();
				    tabla.revalidate();				
				}
					
				String sql = "SELECT c_pedive.numero FROM c_pedive" 
						 +" WHERE c_pedive.cliente = "+codigo+" AND c_pedive.fecha >= '"+fechaIni+"' AND c_pedive.fecha <= '"+fechaFin+"' ORDER BY c_pedive.fecha";
				

				try {
					rs = bd.Consultar(sql);
					
					
					while(rs.next()){
						String numero = rs.getString("numero");
						if(!array.contains(numero))
							array.add(numero);
					}
					
					for(String b : array){
						numeroComboBox.addItem(b);
					}
					
					numeroComboBox.updateUI();				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				setCursor(Tasc.defCursor);
			}
		});
		updateButton.setBounds(780, 17, 180, 40);
		contentPane.add(updateButton);
		
		
		contentPane.add(numeroComboBox);
		numeroComboBox.setBounds(20, 34, 143, 24);
		
		

		JLabel fiLabel = new JLabel("FECHA INICIO");
		fiLabel.setBounds(400, 12, 150, 24);
		fiLabel.setHorizontalAlignment(SwingConstants.LEFT);
		fiLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(fiLabel);

		fechaInicioJP = new JXDatePicker();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		fechaInicioJP.setDate(cal.getTime());
		fechaInicioJP.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		fechaInicioJP.setBounds(400, 34, 110, 24);
		contentPane.add(fechaInicioJP);
		
		
		JLabel ffLabel = new JLabel("FECHA FIN");
		ffLabel.setBounds(600, 12, 150, 24);
		ffLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ffLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(ffLabel);
		
		fechaFinJP = new JXDatePicker();
		fechaFinJP.setDate(Calendar.getInstance().getTime());
		fechaFinJP.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		fechaFinJP.setBounds(600, 34, 110, 24);
		contentPane.add(fechaFinJP);
		
			
		importeIvaLabel = new JLabel("TOTAL   "+importeiva);
		importeIvaLabel.setBounds(750, 650, 250, 24);
		importeIvaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		importeIvaLabel.setFont(new Font("Arial", Font.BOLD, 20));
		contentPane.add(importeIvaLabel);
			
		importeLabel = new JLabel("BASE   "+importe);
		importeLabel.setBounds(250, 650, 250, 24);
		importeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		importeLabel.setFont(new Font("Arial", Font.BOLD, 20));
		contentPane.add(importeLabel);
		

		ivaLabel = new JLabel("IVA   "+iva);
		ivaLabel.setBounds(530, 650, 150, 24);
		ivaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ivaLabel.setFont(new Font("Arial", Font.BOLD, 20));
		contentPane.add(ivaLabel);
		
		
		modelo.addColumn("REFERENCIA");
		modelo.addColumn("CANTIDAD");
		modelo.addColumn("DESCRIPCION");
		modelo.addColumn("PRECIO");
		modelo.addColumn("DTO");
		modelo.addColumn("TOTAL");
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(20, 70, 960, 570);
		contentPane.add(scrollPane);

		setResizable(false);
		setVisible(true);
	}
	
	
	// Contruye la Tabla de datos JTable
	public void  parseData(ResultSet rs) throws SQLException, ParserConfigurationException{

		
		ResultSetMetaData metaDatos = rs.getMetaData();
		
		//int numeroColumnas = metaDatos.getColumnCount();
		int numeroColumnas = 6;

		DecimalFormat df = new DecimalFormat(",##0.##");
		Double value = 0.00;
		String resultImporte ="";
		String resultImporteIva ="";
		this.importe = 0;
		this.importeiva = 0;

		
		while (rs.next())
		{	
			this.importe += rs.getBigDecimal("importe").doubleValue();
			resultImporte = df.format(importe);
			
			this.importeiva += rs.getBigDecimal("importeiva").doubleValue();
			resultImporteIva = df.format(importeiva);
			
			
		   // Se crea un array que será una de las filas de la tabla.
		   Object [] fila = new Object[numeroColumnas]; // Hay 5 columnas en la tabla

		   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
		   for (int i=0;i<numeroColumnas;i++){
		      
			   Object a = rs.getObject(i+1);

			   if(a.getClass().getName().equals("java.math.BigDecimal")){
				   if(a.toString().equals("0.000000")){
					   a="";
				   }
				   
				   else{
					   this.iva = rs.getString("tipo_iva");
					   BigDecimal b = (BigDecimal) a;
					   value = b.doubleValue();
					   a = value;
				   }
			   }
			   fila[i] = a; // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			   modelo.isCellEditable(rs.getRow(), i+1);
		     
		   }
		   // Se añade al modelo la fila completa.
		   modelo.addRow(fila);
		}
		importeLabel.setText("BASE   "+resultImporte+" €");
		importeIvaLabel.setText("TOTAL   "+resultImporteIva+" €");
		ivaLabel.setText("IVA   "+this.iva+"%");
		tabla.setModel(modelo);
		
	}

}




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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.xml.parsers.ParserConfigurationException;

import org.jdesktop.swingx.JXDatePicker;

import clases.BaseDatos;
import clases.Cliente;

public class Presupuestos2 extends JFrame {

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
	private JButton modButton;
	private JButton presCopButton;
	private JButton printButton;
	private Cliente cliente;
	private String numerOferta;
	private String vendedorID;




	
	public Presupuestos2(final String codigo,String ID, Cliente c,BaseDatos b) {
		this.bd = b;
		cliente = c;
		vendedorID = ID;
		
		setTitle("CRM TASC - VISOR DE PRESUPUESTOS");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
		setBounds(0, 0, 1000, 730);
		setLocationRelativeTo(null);
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel presuLabel = new JLabel("N.PRESUPUESTO");
		presuLabel.setBounds(20, 12, 150, 24);
		presuLabel.setHorizontalAlignment(SwingConstants.LEFT);
		presuLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(presuLabel);
		
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
					numerOferta = st;
					//JOptionPane.showMessageDialog(null, st,"ELECCION",JOptionPane.INFORMATION_MESSAGE);
					st = String.format("%10s",st);
					
					System.out.println(st);
					String sql = "SELECT articulo,unidades,definicion,precio,dto1,importe,tipo_iva,importeiva FROM d_presuv  WHERE numero = '"+st+"'"; 
					
					String sqlFecha = "SELECT c_presuv.fecha FROM d_presuv,c_presuv WHERE d_presuv.numero = c_presuv.numero AND d_presuv.numero = '"+st+"'";
					
					//System.out.println(sql);
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
						
						// Comprueba si el presupuesto corresponde a pedido
						/*rs = bd.Consultar(sqlTrans);
						int rows = 0;
						if (rs.last()){
							System.out.println("...................1");
							aceptLabel.setText("ACEPTADO");
						}
						else
							aceptLabel.setText("PENDIENTE");*/
						
			
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
		        if(column==0 || column==5)
		        	return false;
		        else
		        	return true;
		    }
		};
		
		

    

		modelo.addColumn("REFERENCIA");
		modelo.addColumn("CANTIDAD");
		modelo.addColumn("DESCRIPCION");
		modelo.addColumn("PRECIO");
		modelo.addColumn("DTO");
		modelo.addColumn("TOTAL");
		
		JTextField field = createTextField();
		
			// DEfine tipo de texto admitido en la celdas
		final TableCellEditor editor = new DefaultCellEditor(field);
		tabla = new JTable(modelo){
	        @Override
	        public TableCellEditor getCellEditor(int row, int column) {
	            int modelColumn = convertColumnIndexToModel(column);
	            
	            if(column==1 || column==3 || column==4){
	                return editor;
	            } else {
	                return super.getCellEditor(row, column);
	            }
	           
	        }
	    };

		
		TableColumn col = tabla.getColumnModel().getColumn(0);
		
		col = tabla.getColumnModel().getColumn(0);
        col.setPreferredWidth(100);
        col.setMaxWidth(120);
        
        col = tabla.getColumnModel().getColumn(1);
  
        col.setPreferredWidth(60);
        col.setMaxWidth(80);
        
        col = tabla.getColumnModel().getColumn(2);
        col.setMaxWidth(75);
        col.setPreferredWidth(300);
        col.setMaxWidth(800);
        
        col = tabla.getColumnModel().getColumn(3);
        col.setPreferredWidth(100);
        col.setMaxWidth(120);
        
        col = tabla.getColumnModel().getColumn(4);
        col.setPreferredWidth(50);
        col.setMaxWidth(60);
        
        col = tabla.getColumnModel().getColumn(5);
        col.setPreferredWidth(100);
        col.setMaxWidth(120);
        
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tabla.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        tabla.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tabla.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tabla.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);	
		

		
		array = new ArrayList<String>();
		JButton updateButton = new JButton("ACTUALIZAR");
		updateButton.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				
				
				numeroComboBox.removeAllItems();
				array.removeAll(array);
				
				ResultSet  rs = null;
				
				Date date = fechaInicioJP.getDate();
				SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
				fechaIni = date_format.format(date);
				date = fechaFinJP.getDate();
				fechaFin = date_format.format(date);
				System.out.println("INI: "+fechaIni);
				System.out.println("INI: "+fechaFin);
						
				int a = tabla.getRowCount();
				if(a>1){
					DefaultTableModel dm = (DefaultTableModel) tabla.getModel();
				    dm.getDataVector().removeAllElements();
				    tabla.revalidate();				
				}
					
				String sql = "SELECT c_presuv.numero FROM c_presuv" 
						 +" WHERE c_presuv.cliente = "+codigo+" AND c_presuv.fecha >= '"+fechaIni+"' AND c_presuv.fecha <= '"+fechaFin+"' ORDER BY c_presuv.fecha";
				
				System.out.println(sql);
				
				
				
				try {
					rs = bd.Consultar(sql);
					
					modButton.setEnabled(true);
					//presCopButton.setEnabled(true);
					
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
			}
		});
		updateButton.setBounds(780, 17, 180, 40);
		contentPane.add(updateButton);
		
		numeroComboBox.setBounds(20, 34, 105, 24);
		numeroComboBox.setAlignmentX(JComponent.RIGHT_ALIGNMENT);	
		contentPane.add(numeroComboBox);
		//numeroComboBox.setBounds(20, 34, 143, 24);
		
		

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
		
		modButton = new JButton("MODIFICAR");
		modButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				try {
					ModificarPresupuesto mp = new ModificarPresupuesto(tabla, numerOferta, vendedorID, cliente,bd);
					setVisible(false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		modButton.setBounds(35, 650, 120, 30);
		modButton.setEnabled(false);
		contentPane.add(modButton);
		
		
		presCopButton = new JButton("COPIA");
		presCopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				try {
					NuevoPresupuesto mp = new NuevoPresupuesto(tabla,vendedorID, cliente,bd);
					setVisible(false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		presCopButton.setBounds(170, 650, 120, 30);
		presCopButton.setEnabled(false);
		contentPane.add(presCopButton);
		
		printButton = new JButton("IMPRIMIR");
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				
			}
		});
		printButton.setBounds(310, 650, 120, 30);
		printButton.setEnabled(false);
		contentPane.add(printButton);
		
		
			
		importeIvaLabel = new JLabel("TOTAL   "+importeiva);
		importeIvaLabel.setBounds(750, 650, 250, 24);
		importeIvaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		importeIvaLabel.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(importeIvaLabel);
			
		importeLabel = new JLabel("BASE   "+importe);
		importeLabel.setBounds(450, 650, 250, 24);
		importeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		importeLabel.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(importeLabel);
		

		ivaLabel = new JLabel("IVA "+iva);
		ivaLabel.setBounds(650, 650, 150, 24);
		ivaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ivaLabel.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(ivaLabel);
		
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

		DecimalFormat df = new DecimalFormat("#,###.##");
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
		   int columna = 0;
		   for (int i=0;i<numeroColumnas;i++){
		     
			   String a = rs.getString(i+1);
			   
			   if(iva.trim().equals("")){
				   System.out.println("SI");
				   this.iva = rs.getString("tipo_iva");
			   }
			   
			   if(a.toString().equals("0.000000")){
				   a="";
			   }
				   
			   fila[i] = a;
			   
			   String checkFila0 = (String)fila[0];
			   if(checkFila0.trim().equals("")){
				   
				   fila[4]="";
			   }
			   
			   
			   
			   
			   /* 
			   Object a = rs.getObject(i+1);
			   //System.out.println(a.getClass().getName());
			   if(a.getClass().getName().equals("java.math.BigDecimal")){
				   if(a.toString().equals("0.000000")){
					   a="";
				   }
				   
				   else{
					   if(iva.trim().equals("")){
						   System.out.println("SI");
						   this.iva = rs.getString("tipo_iva");
					   }
					   BigDecimal b = (BigDecimal) a;
					   value = b.doubleValue();
					   a = value;
				   }
			   }
		   
			   
			   fila[i] = a; // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			   */


			   
			   modelo.isCellEditable(rs.getRow(), i+1);
		     
		   }
		   // Se añade al modelo la fila completa.
		   modelo.addRow(fila);
		}
		importeLabel.setText("BASE   "+resultImporte+" €");
		importeIvaLabel.setText("TOTAL   "+resultImporteIva+" €");
		ivaLabel.setText("IVA   "+this.iva+"%");
		//tabla.setModel(modelo);
		
	}
	
	private JTextField createTextField() {
        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
                    throws BadLocationException {

                //fb.insertString(off, str.replaceAll("\\D++", ""), attr);  // remove non-digits
            	fb.insertString(off, str.replaceAll("[^0-9.]", ""), attr);
            }

            @Override
            public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)
                    throws BadLocationException {               
            	fb.replace(off, len, str.replaceAll("[^0-9.]", ""), attr);
            }
        });
        return field;
    }

}




package formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import clases.Articulo;
import clases.BaseDatos;
import clases.SqlXml;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Articulos extends JFrame implements ActionListener {

	private JPanel contentPane;
	private final BaseDatos bd;
	private Document doc;
	private String order = "codigo";
	DefaultTableModel modelo;
	JTable tabla ;	
	private String familiaString ="";
	public static ResultSet expRS;


	
	public Articulos(BaseDatos b) {
		
		this.bd = b;		
		setTitle("CRM TASC - ARTICULOS");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 1000, 700);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ButtonGroup bGroup = new ButtonGroup();
		JRadioButton codigoRadioButton = new JRadioButton("ORDENAR POR CODIGO");
		codigoRadioButton.addActionListener(this);
			
		codigoRadioButton.setBounds(20, 20, 216, 24);
		codigoRadioButton.setSelected(true);
		bGroup.add(codigoRadioButton);
		contentPane.add(codigoRadioButton);
		
		JRadioButton nombreRadioButton = new JRadioButton("ORDENAR POR NOMBRE");
		nombreRadioButton.addActionListener(this);		
		nombreRadioButton.setBounds(20, 50, 200, 24);
		bGroup.add(nombreRadioButton);
		contentPane.add(nombreRadioButton);
		
		JLabel labelFamilia = new JLabel("FILTRAR POR FAMILIA");
		labelFamilia.setBounds(260, 20, 200, 24);
		contentPane.add(labelFamilia);
		
		final JComboBox familiaComboBox = new JComboBox();
		familiaComboBox.addItem("TODOS");
		familiaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( familiaComboBox.getSelectedItem()!=null){
					String st = familiaComboBox.getSelectedItem().toString();
					if(st.equals("TODOS"))
						familiaString = "";
					else
						familiaString = "AND marcas.nombre = "+"'"+st+"'";
				}
			}

		});
		
		String sql = "SELECT nombre FROM marcas ORDER BY nombre";
		ResultSet familiasRS = bd.Consultar(sql);
		try {
			while(familiasRS.next()){
				familiaComboBox.addItem(familiasRS.getString("nombre"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		familiaComboBox.setBounds(250, 50, 200, 24);
		contentPane.add(familiaComboBox);
		
		
		
		JButton updateButton = new JButton("ACTUALIZAR");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int a = tabla.getRowCount();
				if(a>1){
					DefaultTableModel dm = (DefaultTableModel) tabla.getModel();
				    dm.getDataVector().removeAllElements();
				    tabla.revalidate();				
				}
					
				String sql = "SELECT articulo.codigo,articulo.nombre,pvp.pvp,marcas.nombre,articulo.nombre2 FROM articulo, pvp, marcas "
						+"WHERE articulo.codigo = pvp.articulo AND articulo.marca = marcas.codigo "+familiaString+" ORDER BY "+order;
				
				System.out.println(sql);
				try {
					parseData(bd.Consultar(sql));
									
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		updateButton.setBounds(500, 20, 200, 50);
		contentPane.add(updateButton);
		modelo = new DefaultTableModel(){
		    @Override
			public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
		};
		tabla = new JTable();
		tabla.setToolTipText("Doble Click para Acción");
		
		
		 tabla.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent e) {
		     if (e.getClickCount() == 2) {
		       JTable target = (JTable)e.getSource();
		       int row = target.getSelectedRow();
		       int column = target.getSelectedColumn();

		       JTextArea msg = new JTextArea((String)tabla.getModel().getValueAt(row, 4));
		       msg.setBounds(0, 0, 600, 400);
		       msg.setLineWrap(true);
		       msg.setWrapStyleWord(true);

		       JOptionPane.showMessageDialog(null, msg,"DESCRIPCIÓN",JOptionPane.INFORMATION_MESSAGE);
		      // JOptionPane.showMessageDialog(null,"<html><body><p style='width: 400px;'>"+ tabla.getModel().getValueAt(row, 4)+"</body></html>","DESCRIPCIÓN",JOptionPane.INFORMATION_MESSAGE);
		       System.out.println("row: "+row+" "+"Column: "+column);
		     }
		   }
		 });

		
		modelo.addColumn("CÓDIGO");
		modelo.addColumn("NOMBRE");
		modelo.addColumn("PVP");
		modelo.addColumn("FAMILIA");
		modelo.addColumn("DESCRIPCION");
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(20, 100, 970, 570);
		contentPane.add(scrollPane);
	
		
		setResizable(false);
		setVisible(true);
		
		
		
	}
	
	
	
	// Contruye la Tabla de datos JTable
	public void  parseData(ResultSet rs) throws SQLException{

		
		ResultSetMetaData metaDatos = rs.getMetaData();
		
		// Se obtiene el número de columnas.
		int numeroColumnas = metaDatos.getColumnCount();

		/*// Se crea un array de etiquetas para rellenar
		Object[] etiquetas = new Object[numeroColumnas];

		// Se obtiene cada una de las etiquetas para cada columna
		for (int i = 0; i < numeroColumnas; i++)
		{
		   // Nuevamente, para ResultSetMetaData la primera columna es la 1.
		   etiquetas[i] = metaDatos.getColumnLabel(i + 1);
		}
		modelo.setColumnIdentifiers(etiquetas);*/
				
		try {
			this.doc = SqlXml.toDocument(rs);	
			//SqlXml.toFile(doc);
			
			metaDatos = rs.getMetaData();
	
			
			while (rs.next())
			{
			   // Se crea un array que será una de las filas de la tabla.
			   Object [] fila = new Object[numeroColumnas]; // Hay tres columnas en la tabla

			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<numeroColumnas;i++){
			      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			      
			      if(i==2){
			    	  
			    	  NumberFormat formatter = new DecimalFormat("#0.0000");     
			    	  fila[i] = formatter.format(fila[i]);
			    	  
			      }
			      
			      modelo.isCellEditable(rs.getRow(), i+1);
			     
			   }
			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila);
			}
			
			tabla.setModel(modelo);

			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	


	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("ORDENAR POR NOMBRE")){
			this.order = "articulo.nombre";
		}
		else{
			this.order = "articulo.codigo";
		}
	    
	}

}




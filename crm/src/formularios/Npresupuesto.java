package formularios;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

public class Npresupuesto extends JFrame implements ActionListener {

	private JPanel contentPane;
	private final BaseDatos bd;
	private Document doc;
	private String order = "codigo";
	ResultSet auxrs;
	DefaultTableModel modelo;
	JTable tablaLeft ;	
	private String familiaString ="";
	public static ResultSet expRS;
	private static int WIDTH;
    private static int HEIGTH;


	
	public Npresupuesto(String cliente,BaseDatos b) {
		
		this.bd = b;		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		DisplayMode dm = gs[0].getDisplayMode();
		WIDTH = dm.getWidth();
		HEIGTH = dm.getHeight();
			
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0,WIDTH-150,HEIGTH-150);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("CMR TASC - NUEVO PRESUPUESTO");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ButtonGroup bGroup = new ButtonGroup();
		JRadioButton codigoRadioButton = new JRadioButton("ORDENAR POR CODIGO");
		codigoRadioButton.addActionListener(this);
			
		codigoRadioButton.setBounds(20, 10, 200, 24);
		codigoRadioButton.setSelected(true);
		bGroup.add(codigoRadioButton);
		contentPane.add(codigoRadioButton);
		
		JRadioButton nombreRadioButton = new JRadioButton("ORDENAR POR NOMBRE");
		nombreRadioButton.addActionListener(this);		
		nombreRadioButton.setBounds(20, 30, 200, 24);
		bGroup.add(nombreRadioButton);
		contentPane.add(nombreRadioButton);
		
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
		
		
		familiaComboBox.setBounds(20, 60, 200, 24);
		contentPane.add(familiaComboBox);
		
		
		
		JButton updateButton = new JButton("ACTUALIZAR");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int a = tablaLeft.getRowCount();
				if(a>1){
					DefaultTableModel dm = (DefaultTableModel) tablaLeft.getModel();
				    dm.getDataVector().removeAllElements();
				    tablaLeft.revalidate();				
				}
					
				String sql = "SELECT articulo.codigo,articulo.nombre,pvp.pvp,marcas.nombre,articulo.nombre2 FROM articulo, pvp, marcas "
						+"WHERE articulo.codigo = pvp.articulo AND articulo.marca = marcas.codigo "+familiaString+" ORDER BY "+order;
				
				//System.out.println(sql);
				try {
					parseData(bd.Consultar(sql));
									
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		updateButton.setBounds(230, 10, 120, 75);
		contentPane.add(updateButton);
		modelo = new DefaultTableModel(){
		    @Override
			public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
		};
		tablaLeft = new JTable();
		tablaLeft.setDragEnabled(true);
		tablaLeft.setToolTipText("Doble Click para Acción");
		
		
		 tablaLeft.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent e) {
		     if (e.getClickCount() == 2) {
		       JTable target = (JTable)e.getSource();
		       int row = target.getSelectedRow();
		       int column = target.getSelectedColumn();
		       try {
				auxrs.absolute(row+2);
				
				 JTextArea msg = new JTextArea((String)"CODIGO:\t "+auxrs.getString(1)+"\n"+
			    		   "NOMBRE:\t "+auxrs.getString(2)+"\n"+
			    		   "PVP:\t "+auxrs.getString(3)+"\n"+
			    		   "MARCA:\t "+auxrs.getString(4)+"\n"+
			    		   "DESCRIPCION:\t "+auxrs.getString(5));
			       msg.setBounds(0, 0, 600, 400);
			       msg.setLineWrap(true);
			       msg.setWrapStyleWord(true);

			       JOptionPane.showMessageDialog(null, msg,"DESCRIPCIÓN",JOptionPane.INFORMATION_MESSAGE);

			       System.out.println("row: "+row+" "+"Column: "+column);
			       
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		      
		     }
		   }
		 });

		modelo.addColumn("CÓDIGO");
		modelo.addColumn("NOMBRE");
		JScrollPane leftScrollPane = new JScrollPane(tablaLeft);
		leftScrollPane.setBounds(5, 120, (getWidth()/4)-8 , getHeight()-220);
		contentPane.add(leftScrollPane);
	
		
		setResizable(false);
		setVisible(true);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	// Contruye la Tabla de datos JTable
	public void  parseData(ResultSet rs) throws SQLException{
		auxrs = rs;

		
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
			   // Se crea un array que será una de las filas de la tablaLeft.
			   Object [] fila = new Object[numeroColumnas]; // Hay tres columnas en la tablaLeft

			   // Se rellena cada posición del array con una de las columnas de la tablaLeft en base de datos.
			   for (int i=0;i<numeroColumnas;i++){
			      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
			      modelo.isCellEditable(rs.getRow(), i+1);
			     
			   }
			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila);
			}
			
			tablaLeft.setModel(modelo);


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




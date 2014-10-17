package formularios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;

import org.jdesktop.swingx.JXDatePicker;

import clases.BaseDatos;

public class NuevoPresuOLD extends JFrame{

	private JPanel mainPanel;
	private JPanel leftPane;
	private JScrollPane scrollLeftPane;
	private JPanel rigthPane;
	private JScrollPane scrollRigthPane;
	private JTable leftTable;
	DefaultTableModel modeloLeft;
	private JTable rigthTable;
	DefaultTableModel modeloRigth;
	private JButton updateButton;
	private BaseDatos bd;
	private static int WIDTH;
    private static int HEIGTH;



	/**
	 * Create the frame.
	 */
	public NuevoPresuOLD(String cliente,BaseDatos b) {
		
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

		
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, getWidth(), getHeight());
		mainPanel.setBackground(Color.yellow);
		
		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		setLeftPanel();
		setRigthPanel();
		
		
		updateButton = new JButton("ACTUALIZAR");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int a = leftTable.getRowCount();
				if(a>1){
					DefaultTableModel dm = (DefaultTableModel) leftTable.getModel();
				    dm.getDataVector().removeAllElements();
				    leftTable.revalidate();				
				}
					
				//String sql = "SELECT articulo.codigo,articulo.nombre,pvp.pvp,marcas.nombre,articulo.nombre2 FROM articulo, pvp, marcas "
					//	+"WHERE articulo.codigo = pvp.articulo AND articulo.marca = marcas.codigo "+familiaString+" ORDER BY "+order;
				
				String sql ="SELECT articulo.codigo,articulo.nombre,pvp.pvp,marcas.nombre,articulo.nombre2 FROM articulo, pvp, marcas " 
							+"WHERE articulo.codigo = pvp.articulo  ORDER BY articulo.codigo";	
				//System.out.println(sql);
				try {
					parseData(bd.Consultar(sql));
									
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		updateButton.setBounds(20, 20, 150, 24);
		mainPanel.add(updateButton);
		
		
		
		
		
		


		

		setVisible(true);
	}
	
	public void setLeftPanel(){
		scrollLeftPane = new JScrollPane(setLeftTable());
		
		leftPane = new JPanel();
		//leftPane.setBackground(Color.black);
		leftPane.setBorder(new EmptyBorder(2,5,5,3));
		leftPane.setBounds(5, 200, (getWidth()/4)-8 , getHeight()-220);
		leftPane.setLayout(null);
		leftPane.add(scrollLeftPane);
		
		mainPanel.add(leftPane);
	
	}
	
	public void setRigthPanel(){
		scrollRigthPane = new JScrollPane(setRigthTable());
		
		rigthPane = new JPanel();
		rigthPane.setBackground(Color.BLUE);
		rigthPane.setBorder(new EmptyBorder(2,3,5,5));
		rigthPane.setBounds(getWidth()/4,200,getWidth()-(getWidth()/4)-8, getHeight()-300);
		rigthPane.setLayout(null);
		rigthPane.add(scrollRigthPane);
		
		mainPanel.add(rigthPane);
		
		
	}
	
	
	public JTable setLeftTable(){
		
		leftTable = new JTable();
		leftTable.setDragEnabled(true);
		leftTable.setToolTipText("Doble Click para Transpasar el artículo");

		modeloLeft = new DefaultTableModel(){
		    @Override
			public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
		};
		
		modeloLeft .addColumn("CÓDIGO");
		modeloLeft .addColumn("NOMBRE");
		
		modeloLeft.addRow(new Vector<Object>());
		

	

		return leftTable;
		
	}
	
	public JTable setRigthTable(){
		rigthTable = new JTable();
		rigthTable.setDragEnabled(true);
		
		modeloRigth = new DefaultTableModel();
		
		modeloRigth.addColumn("ARTICULO");
		modeloRigth.addColumn("DEFINICION");
		modeloRigth.addColumn("UNIDADES");
		modeloRigth.addColumn("PRECIO");	
		modeloRigth.addColumn("IMPORTE");
		

		
		
		return rigthTable;
		
		
	}
	
	
	// Contruye la Tabla de datos JTable
	public void  parseData(ResultSet rs) throws SQLException{

	
		// Se obtiene el número de columnas.
		int numeroColumnas = modeloLeft.getColumnCount();
		System.out.println(numeroColumnas);
		while (rs.next())
		{
		   // Se crea un array que será una de las filas de la tabla.
		   Object [] fila = new Object[numeroColumnas]; // Hay dos columnas en la tabla

		   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
		   for (int i=0;i<numeroColumnas;i++){
		      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
		      modeloLeft.isCellEditable(rs.getRow(), i+1);
		     
		   }
		   // Se añade al modelo la fila completa.
		   modeloLeft.addRow(fila);
		   System.out.println(fila);
		}
		
		leftTable.setModel(modeloLeft);
			
	}
	
	
	


}

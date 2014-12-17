package formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.*;
import java.awt.print.PrinterException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

import clases.BaseDatos;
import clases.Cliente;
import clases.Presupuesto;
import clases.Tasc;
import clases.ToPDF;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.Locale;

import com.itextpdf.text.DocumentException;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.Component;
import java.io.IOException;


public class NuevoPresupuesto extends JFrame implements ActionListener { 

	private static Action enterAction;
	private JPanel contentPane;
	private JTable lTable;
	private static JTable rTable;
	private JTextArea textArea;
	private JTextArea clienteTextArea;
	private JTextArea obserTextArea;
	private JTextArea obser2TextArea;
	private JXDatePicker fechaJP;
	private JTextField creditoTextField;
	private JComboBox familiaComboBox;
	private ResultSet rs;
	private DefaultTableModel tml;
	private DefaultTableModel tmr;
	private JButton añadirButton;
	private JButton quitarButton;
	private JButton comentButton;
	private JButton printButton;
	private JButton saveButton;
	private JTextField nOfertaTextField;
	private JButton filaButton;
	private JButton borrarButton;
	private int clientID;
	private static double total;
	private static double base;
	private static double iva;
	private Cliente cliente;
	private static int tipoIVA;
	private static JTextField baseTextField;
	private static JTextField tipoIvaTextField;
	private static JTextField ivaTextField;
	private static JTextField totalTextField;
	private String usuarioID;
	private JRadioButton nombreRadioButton;
	private JRadioButton codigoRadioButton;
	private  BaseDatos bd;
	private JTable tableImported;
	private JLabel dto1Label;
	private JLabel dto2Label;
	private static JTextField dto1TextField;
	private static JTextField dto2TextField;
	private static String dtoPronto;


	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public NuevoPresupuesto(JTable t, String ID, Cliente cliente,BaseDatos b)  throws SQLException {
		
		
		bd = b;
		this.usuarioID = ID;	
		this.cliente = cliente;
		tableImported = t;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("NUEVO PRESUPUESTO - CRM TASC");
		
		setBounds(100, 100, 600, 500);
		
		addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent e){
		
				JFrame frame = (JFrame)e.getSource();
		
				int result = JOptionPane.showConfirmDialog(frame,
						"¿Seguro Desea Abandonar la Aplicación?\n Los datos introducidos podrían perdese",
						"Abandonar Ventana",JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION)
					frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
				else
					frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			}
		});
		
		
		contentPane = new JPanel(new BorderLayout());
		
		JTextField field = createTextField();
		
		// PANEL IZQUIERDO
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
	
		ButtonGroup bGroup = new ButtonGroup();

		tml = new DefaultTableModel(){
		    @Override
			public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
		};
		
		tml.addColumn("CÓDIGO");
		tml.addColumn("NOMBRE");
		
		
		lTable = new JTable(tml);
		lTable.setDragEnabled(true);
		
		TableColumn col = lTable.getColumnModel().getColumn(0);
		col.setMinWidth(130);
        col.setMaxWidth(140);
        

        col = lTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(300);
        col.setMaxWidth(500);
        
  
		
		leftPanel.setBorder(BorderFactory.createTitledBorder("ARTICULOS"));
		

		JPanel buttonsLeftPanel = new JPanel();
		buttonsLeftPanel.setLayout(new BoxLayout(buttonsLeftPanel, BoxLayout.Y_AXIS));
		
		nombreRadioButton = new JRadioButton("ORDENAR POR NOMBRE");
		nombreRadioButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nombreRadioButton.setMinimumSize(new Dimension(220, 24));
		nombreRadioButton.setPreferredSize(new Dimension(220, 24));
		nombreRadioButton.setMaximumSize(new Dimension(220, 24));

		
		buttonsLeftPanel.add(nombreRadioButton);
		
		codigoRadioButton = new JRadioButton("ORDENAR POR CODIGO");
		codigoRadioButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		codigoRadioButton.setMinimumSize(new Dimension(220, 24));
		codigoRadioButton.setPreferredSize(new Dimension(220, 24));
		codigoRadioButton.setMaximumSize(new Dimension(220, 24));
		codigoRadioButton.setSelected(true);

		
		buttonsLeftPanel.add(codigoRadioButton);
		
		buttonsLeftPanel.add(Box.createVerticalStrut(10));
		
		familiaComboBox = new JComboBox();
		familiaComboBox.addActionListener(this);
		familiaComboBox.setMinimumSize(new Dimension(220, 24));
		familiaComboBox.setPreferredSize(new Dimension(220, 24));
		familiaComboBox.setMaximumSize(new Dimension(220, 24));
		familiaComboBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loadFamilies();
		buttonsLeftPanel.add(familiaComboBox);
		
		
		bGroup.add(codigoRadioButton);
		bGroup.add(nombreRadioButton);

		buttonsLeftPanel.add(Box.createVerticalStrut(10));
		
		JButton updateButton = new JButton("ACTUALIZAR");
		updateButton.addActionListener(this);
		updateButton.setMinimumSize(new Dimension(220, 24));
		updateButton.setPreferredSize(new Dimension(220, 24));
		updateButton.setMaximumSize(new Dimension(220, 24));
		updateButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buttonsLeftPanel.add(updateButton);
		
		//buttonsLeftPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		leftPanel.add(buttonsLeftPanel);
		
		leftPanel.add(Box.createVerticalStrut(20));
		JScrollPane lTableScrollPane = new JScrollPane(lTable);

		leftPanel.add(lTableScrollPane);
		
		leftPanel.add(Box.createVerticalStrut(10));
		
		añadirButton = new JButton("AÑADIR ARTICULO");
		añadirButton.addActionListener(this);
		añadirButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		leftPanel.add(añadirButton);
		
		
		// PANEL DERECHO
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	
		JPanel clientePanel = new JPanel();
		clientePanel.setMaximumSize(new Dimension(350,180));
		clientePanel.setBorder(BorderFactory.createTitledBorder("CLIENTE"));
		
		clienteTextArea =  new JTextArea(cliente.getNombre()+"\n"
										+"CIF: "+cliente.getCif()+"\n"
										+"CODIGO CLIENTE: "+cliente.getCodigo()+"\n"
										+"DIRECCION:\n" +cliente.getDireccs()+"\n"+
										cliente.getCp()+" - "+cliente.getPoblacion()+"\n"+
										cliente.getProvincia()+"\n"+"Contacto: "+cliente.getContact()+"\n");	
		clienteTextArea.setLineWrap(true);
		clienteTextArea.setMinimumSize(new Dimension(320,150));
		clienteTextArea.setPreferredSize(new Dimension(320,150));
		clienteTextArea.setMaximumSize(new Dimension(320,150));
		clienteTextArea.setEditable(false);
		JScrollPane scp = new JScrollPane(clienteTextArea);
		clientePanel.add(clienteTextArea);
		
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		
		JLabel fechaLabel = new JLabel("FECHA OFERTA");
		//upRightPanel.add(fechaLabel);
		panel1.add(fechaLabel);
		panel1.add(Box.createHorizontalStrut(10));
		
		fechaJP = new JXDatePicker();	
		fechaJP.setDate(Calendar.getInstance().getTime());
		fechaJP.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
		fechaJP.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel1.add(fechaJP);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		
		JLabel nOfertaLabel= new JLabel("NÚMERO OFERTA: ");
		nOfertaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel2.add(nOfertaLabel);
		
		nOfertaTextField = new JTextField();
		nOfertaTextField.setDocument(new JTextFieldLimit(10));
		nOfertaTextField.setText(getNumPresu());
		nOfertaTextField.setEditable(false);
		nOfertaTextField.setPreferredSize(new Dimension(67,24));
		nOfertaTextField.setMaximumSize(new Dimension(67,24));
		nOfertaTextField.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		panel2.add(nOfertaTextField);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		
		JLabel creditoLabel= new JLabel("CREDITO: ");
		creditoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel3.add(creditoLabel);
		panel3.add(Box.createHorizontalStrut(10));
		
		creditoTextField = createTextField();
		//creditoTextField.setDocument(new JTextFieldLimit(2));
		creditoTextField.setText(cliente.getCredit());
		creditoTextField.setEditable(false);
		creditoTextField.setPreferredSize(new Dimension(100,24));
		creditoTextField.setMaximumSize(new Dimension(100,24));
		panel3.add(creditoTextField);
		
		JPanel upRightPanel = new JPanel();
		upRightPanel.setLayout(new BoxLayout(upRightPanel, BoxLayout.Y_AXIS));
		upRightPanel.setBorder(BorderFactory.createTitledBorder("OFERTA"));
		upRightPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		upRightPanel.setPreferredSize(new Dimension(300,160));
		upRightPanel.setMaximumSize(new Dimension(380,160));
		
		upRightPanel.add(panel1);
		upRightPanel.add(Box.createVerticalStrut(20));
		upRightPanel.add(panel2);
		upRightPanel.add(Box.createVerticalStrut(20));
		upRightPanel.add(panel3);
		
		
		JPanel ObserPanel = new JPanel();	
		ObserPanel.setBorder(BorderFactory.createTitledBorder("OBSERVACIONES CLIENTE"));
		ObserPanel.setMaximumSize(new Dimension(380,180));
		obserTextArea =  new JTextArea(cliente.getObser());	
		obserTextArea.setLineWrap(true);
		obserTextArea.setBackground(Color.white);
		obserTextArea.setMinimumSize(new Dimension(350,150));
		obserTextArea.setPreferredSize(new Dimension(350,150));
		obserTextArea.setMaximumSize(new Dimension(350,150));
		ObserPanel.add(obserTextArea);
					
	
		//Define celdas editables Tabla derecha
		
		if(tableImported!=null)
			tmr = (DefaultTableModel) tableImported.getModel();
		
		else{
			tmr = new DefaultTableModel(){
			    @Override
				public boolean isCellEditable(int row, int column)
			    {
			        if(column==0 || column==5)
			        	return false;
			        else
			        	return true;
			    }
			};
			
			tmr.addColumn("REFERENCIA");
			tmr.addColumn("CANTIDAD");
			tmr.addColumn("DESCRIPCION");
			tmr.addColumn("PRECIO");
			tmr.addColumn("DTO");
			tmr.addColumn("TOTAL");
		}
		
		int columns = tmr.getColumnCount();

			// DEfine tipo de texto admitido en la celdas
		final TableCellEditor editor = new DefaultCellEditor(field);
		JTextFieldLimit jl = new JTextFieldLimit(75);
		final TableCellEditor editorLimited = new DefaultCellEditor(jl.field);
		rTable = new JTable(tmr){
            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);
                
                if(column==1 || column==3 || column==4){
                    return editor;
                } 
                
                else if(column==2){              	
                	JTextField e = new JTextField();
            		e.setDocument(new JTextFieldLimit(76));
                	return new DefaultCellEditor(e) ;
                }
            
               else {
                    return super.getCellEditor(row, column);
                }
               
            }
        };
        rTable.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	int row = 0;
            	if(rTable.getSelectedRow()>=0)
            		row = rTable.getSelectedRow();
            	else
            		row = rTable.getRowCount()-1;
            	rTable.scrollRectToVisible(rTable.getCellRect(row, 0, true));
            }
        });
		rTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);		
		rTable.setDragEnabled(true);
		rTable.setAutoscrolls(true);
		rTable.addKeyListener(new MyKeyListener());
		

		
		col = rTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(100);
        col.setMaxWidth(120);
        
        col = rTable.getColumnModel().getColumn(1);
  
        col.setPreferredWidth(60);
        col.setMaxWidth(80);
        
        col = rTable.getColumnModel().getColumn(2);
        col.setMaxWidth(75);
        col.setPreferredWidth(300);
        col.setMaxWidth(800);
        
        col = rTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(100);
        col.setMaxWidth(120);
        
        col = rTable.getColumnModel().getColumn(4);
        col.setPreferredWidth(50);
        col.setMaxWidth(60);
        
        col = rTable.getColumnModel().getColumn(5);
        col.setPreferredWidth(100);
        col.setMaxWidth(120);
        
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        rTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        rTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        rTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        rTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
       

        
        //enterAction = new EnterAction();     
       // rTable.getInputMap().put(KeyStroke.getKeyStroke(" ENTER "), "doEnterAction");
       // rTable.getActionMap().put("doEnterAction", enterAction);
        
      
        
	

		JScrollPane rTableScrollPane = new JScrollPane(rTable);

		//rTableScrollPane.setViewportView(rTable);
		
		
		JPanel buttonsRightPanel = new JPanel();
		buttonsRightPanel.setLayout(new BoxLayout(buttonsRightPanel, BoxLayout.Y_AXIS));
		
		
		filaButton = new JButton("AÑADIR FILA");
		filaButton.addActionListener(this);
		filaButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		filaButton.setMaximumSize(new Dimension(250,200));
		buttonsRightPanel.add(filaButton);
		
		borrarButton = new JButton("BORRAR FILA");
		borrarButton.addActionListener(this);
		borrarButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		borrarButton.setMaximumSize(new Dimension(250,200));
		buttonsRightPanel.add(borrarButton);
		
		comentButton = new JButton("AÑADIR CONDICIONES");
		comentButton.addActionListener(this);
		comentButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		comentButton.setMaximumSize(new Dimension(250,200));
		buttonsRightPanel.add(comentButton);
		
		printButton = new JButton("IMPRIMIR PDF");
		printButton.addActionListener(this);
		printButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		printButton.setMaximumSize(new Dimension(250,200));
		//printButton.setEnabled(false);
		buttonsRightPanel.add(printButton);
		
		saveButton = new JButton("GUARDAR BD");
		saveButton.addActionListener(this);
		saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		saveButton.setMaximumSize(new Dimension(250,200));
		buttonsRightPanel.add(saveButton);
		
		
		
	
		JPanel rTotalPanel = new JPanel();
		rTotalPanel.setLayout(new BoxLayout(rTotalPanel, BoxLayout.Y_AXIS));
		rTotalPanel.setBorder(BorderFactory.createTitledBorder("TOTALES"));
		rTotalPanel.setMinimumSize(new Dimension(300,100));
		rTotalPanel.setPreferredSize(new Dimension(300,100));
		rTotalPanel.setMaximumSize(new Dimension(500,1200));
		rTotalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);	
		
		JPanel totalBasePanel = new JPanel();
		totalBasePanel.setLayout(new BoxLayout(totalBasePanel, BoxLayout.X_AXIS));
		
		dto1Label = new JLabel("DTO1");
		totalBasePanel.add(dto1Label);
		totalBasePanel.add(Box.createHorizontalStrut(5));
		dto1TextField = createTextField();
		dto1TextField.setText(cliente.getDto1());
		dto1TextField.setPreferredSize(new Dimension(45,24));
		dto1TextField.setMaximumSize(new Dimension(45,24));
		dto1TextField.setAlignmentX(Component.LEFT_ALIGNMENT);
		dto1TextField.addKeyListener(new MyKeyListener());

		totalBasePanel.add(dto1TextField);		
		totalBasePanel.add(Box.createHorizontalStrut(10));
		dto2Label = new JLabel("DTO2");
		totalBasePanel.add(dto2Label);
		totalBasePanel.add(Box.createHorizontalStrut(5));
		dto2TextField = createTextField();
		dto2TextField.setText(cliente.getDto2());
		dto2TextField.setPreferredSize(new Dimension(45,24));
		dto2TextField.setMaximumSize(new Dimension(45,24));
		dto2TextField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		dto2TextField.addKeyListener(new MyKeyListener());

		totalBasePanel.add(dto2TextField);
		
		totalBasePanel.add(Box.createHorizontalStrut(10));
		
		JLabel baseLabel = new JLabel("BASE ");
		baseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		baseTextField = new JTextField(""+base);
		baseTextField.setPreferredSize(new Dimension(150,24));
		baseTextField.setEditable(false);
		baseTextField.setFont(new Font("Arial", Font.BOLD, 14));
		baseTextField.setHorizontalAlignment(JTextField.RIGHT);
		baseTextField.setMaximumSize(new Dimension(150,24));
		totalBasePanel.add(baseLabel);
		totalBasePanel.add(baseTextField);
		

	
				
		JPanel totalIvaPanel = new JPanel();
		totalIvaPanel.setLayout(new BoxLayout(totalIvaPanel, BoxLayout.X_AXIS));
		JLabel tipoIvaLabel = new JLabel("TIPO IVA: ");
		tipoIvaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		tipoIvaTextField = createTextField();
		tipoIvaTextField.setText("21");
		tipoIvaTextField.setFont(new Font("Arial", Font.BOLD, 14));
		tipoIvaTextField.setHorizontalAlignment(JTextField.RIGHT);
		tipoIvaTextField.setPreferredSize(new Dimension(30,24));
		tipoIvaTextField.setMaximumSize(new Dimension(30,24));	
		tipoIvaTextField.addKeyListener(new MyKeyListener());
		
		JLabel ivaLabel = new JLabel("IVA ");
		ivaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);		
		ivaTextField = new JTextField(""+iva);
		ivaTextField.setEditable(false);
		ivaTextField.setFont(new Font("Arial", Font.BOLD, 14));
		ivaTextField.setHorizontalAlignment(JTextField.RIGHT);
		ivaTextField.setPreferredSize(new Dimension(150,24));
		ivaTextField.setMaximumSize(new Dimension(150,24));
		totalIvaPanel.add(tipoIvaLabel);
		totalIvaPanel.add(tipoIvaTextField);
		totalIvaPanel.add(Box.createHorizontalStrut(20));
		totalIvaPanel.add(ivaLabel);
		totalIvaPanel.add(ivaTextField);
		
		JPanel totalTotalPanel = new JPanel();
		totalTotalPanel.setLayout(new BoxLayout(totalTotalPanel, BoxLayout.X_AXIS));
		JLabel totalLabel = new JLabel("TOTAL ");
		totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		totalTextField = new JTextField(""+total);
		totalTextField.setEditable(false);
		totalTextField.setFont(new Font("Arial", Font.BOLD, 14));
		totalTextField.setHorizontalAlignment(JTextField.RIGHT);
		totalTextField.setPreferredSize(new Dimension(150,24));
		totalTextField.setMaximumSize(new Dimension(150,24));
		totalTotalPanel.add(totalLabel);
		totalTotalPanel.add(totalTextField);
		
		rTotalPanel.add(Box.createVerticalStrut(10));
		rTotalPanel.add(totalBasePanel);
		rTotalPanel.add(Box.createVerticalStrut(10));
		rTotalPanel.add(totalIvaPanel);
		rTotalPanel.add(Box.createVerticalStrut(10));
		rTotalPanel.add(totalTotalPanel);
		rTotalPanel.add(Box.createVerticalStrut(10));
		
		
		
		
		JPanel ObserPanel2 = new JPanel();	
		ObserPanel2.setBorder(BorderFactory.createTitledBorder("OBSERVACIONES PRESUPUESTO"));
		ObserPanel2.setMaximumSize(new Dimension(300,140));
		obser2TextArea =  new JTextArea();	
		obser2TextArea.setLineWrap(true);
		obser2TextArea.setBackground(Color.white);
		obser2TextArea.setMinimumSize(new Dimension(280,110));
		obser2TextArea.setPreferredSize(new Dimension(280,110));
		obser2TextArea.setMaximumSize(new Dimension(280,110));
		ObserPanel2.add(obser2TextArea);

		
		JPanel upRPanel = new JPanel();
		upRPanel.setLayout(new BoxLayout(upRPanel, BoxLayout.X_AXIS));
		upRPanel.add(clientePanel);
		upRPanel.add(upRightPanel);
		upRPanel.add(ObserPanel);

		
		rightPanel.add(upRPanel);
		
		
		rightPanel.add(rTableScrollPane);
		
		JPanel dwRPanel = new JPanel();
		dwRPanel.setLayout(new BoxLayout(dwRPanel, BoxLayout.X_AXIS));
		
		dwRPanel.add(buttonsRightPanel);
		dwRPanel.add(rTotalPanel);
		dwRPanel.add(ObserPanel2);

		rightPanel.add(dwRPanel);
		
		

		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setDividerLocation(400);
		splitPane.setOneTouchExpandable(true);
	
		contentPane.add(splitPane,BorderLayout.CENTER);
		contentPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		setLocationRelativeTo(null);
		setContentPane(contentPane);
	
		updateButton.doClick();
		pack();
        setVisible(true);

	}
	
	

	
	
	public void loadFamilies(){
		String sql = "SELECT nombre FROM marcas ORDER BY nombre";
		ResultSet familiasRS = bd.Consultar(sql);
		try {
			familiaComboBox.addItem("TODAS LAS MARCAS");
			while(familiasRS.next()){
				familiaComboBox.addItem(familiasRS.getString("nombre"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
	
	
	public void loadLTable(String sql){
		int a = lTable.getRowCount();
		
		if(a>1){
			tml.getDataVector().removeAllElements();
		    lTable.revalidate();
		}
		    
		    try {
				rs = bd.Consultar(sql);
				
				
				int columns = tml.getColumnCount();
				while(rs.next()){
					
					Object [] fila = new Object[columns];
					for (int i=0;i<columns;i++){
					      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
					      lTable.getModel().isCellEditable(rs.getRow(), i+1);
					}
					
					tml.addRow(fila);
				
				
				}
				lTable.setModel(tml);
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	
	private String getNumPresu() throws SQLException{
		
		//String q = "SELECT numero FROM c_presuv  WHERE modificado IS NULL OR modificado < '1' ORDER BY fecha";			
		
		String q = "SELECT numero FROM c_presuv";
		ResultSet aux = bd.Consultar(q);
		
		int nextNum = 0;
		while(aux.next()){
			String s = aux.getString(1);
			s = s.trim();
			String string_out = ""; 
			
			for(int i=0;i<s.length();i++){
				
				if(!Character.isLetter(s.charAt(i)))
					string_out = string_out + s.charAt(i);
			}
			
			if(string_out.length()>5)
				string_out = string_out.substring(0, 6);
			int k = Integer.parseInt(string_out);
			if( nextNum <= k ){
				nextNum = k;
			}
		}
		
		return String.format("%10s",""+(nextNum+1));
		
		/*aux.last();	
		JTextField jt = createTextField();
		jt.setText(aux.getString(1));	
		String numero = jt.getText();
		int nextNumero = Integer.parseInt(numero)+1;		
		numero = String.format("%10s",""+nextNumero);
		return numero;*/
	}
	

	
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("ACTUALIZAR")){
			
			setCursor(Tasc.waitCursor);
			String order = "";
			String familiaString ="";
			
			if( familiaComboBox.getSelectedItem()!=null){
				String st = familiaComboBox.getSelectedItem().toString();
				if(!st.equals("TODAS LAS MARCAS"))
					familiaString = "AND marcas.nombre = "+"'"+st+"'";
			}
			
			if(nombreRadioButton.isSelected()){
				order = "articulo.nombre";		
			}
			
			else{
				order = "articulo.codigo";			
			}
					
			String sql = "SELECT articulo.codigo, articulo.nombre, pvp.pvp, articulo.nombre2 FROM articulo, pvp, marcas "
					+"WHERE articulo.codigo = pvp.articulo AND articulo.marca = marcas.codigo "+familiaString+" ORDER BY "+order;
			
			loadLTable(sql);
			setCursor(Tasc.defCursor);	
		}
	
		else if(e.getActionCommand().equals("AÑADIR ARTICULO")){
			
			setCursor(Tasc.waitCursor);	
			int totalRows = lTable.getRowCount();
			int selectedRow = lTable.getSelectedRow();
			DecimalFormat df = new DecimalFormat("#0.00");

			int selectedRowR = rTable.getSelectedRow();
		
			if(selectedRow >= 0 && selectedRow  <= totalRows){
					
				try {
					rs.absolute(selectedRow+1);
					String[] file = new String[6];
					file[0] = rs.getString(1);
					file[1] = new String("0");	
					file[2] = rs.getString(2);	
					
					double p = rs.getBigDecimal(3).doubleValue();
					
					file[3] = df.format(p);
					file[4] = new String("0");
					file[5] = new String("0");
										
					if(selectedRowR==-1){
						tmr.addRow(file);
						selectedRowR = 0;
					
					}
					else						
						tmr.insertRow(selectedRowR,file);
										
					String line = rs.getString(4);
					

					line = line.replace("\n", "");			
					file = new String[6];
					//String[] lines = line.split("\\n");
					//line = "";
					//int len = lines.length;

					int nextRow = 1;
					
					String[] words = line.split(" ");
					line = "";
					int len = words.length;
					for(int i=0;i<len;i++){
						if(line.length()+words[i].length()<=75){
							line = line.concat(" "+words[i]);						
						}
						else{							
							
							file[2] = line = line.trim();
							tmr.insertRow(selectedRowR+nextRow,file);
							line = words[i];
							nextRow++;					
						}					
					}

					file = new String[6];
					tmr.insertRow(selectedRowR+nextRow,file);
					


					ListSelectionModel selectionModel = rTable.getSelectionModel();
					int v = rTable.getRowCount();
					selectionModel.setSelectionInterval(v, v);


					
					rTable.setModel(tmr);
					rTable.validate();
			
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
			else{
				JOptionPane.showMessageDialog(null,"SELECT AN  ARTICLE FIRST", "INFO", JOptionPane.INFORMATION_MESSAGE);
			}	
		
			setCursor(Tasc.defCursor);	
		}
				
		else if(e.getActionCommand().equals("AÑADIR FILA")){
			
			int selectedRow = rTable.getSelectedRow();
			String[] file = new String[6];
			
			if(selectedRow >=0 && selectedRow < rTable.getRowCount()){
				tmr.insertRow(selectedRow, file);		
			}
			else
				tmr.addRow(file);
						
			rTable.setModel(tmr);
			rTable.validate();	
		}
		
		else if(e.getActionCommand().equals("BORRAR FILA")){
			
		    int[] selection = rTable.getSelectedRows();
			
			int confirmado = JOptionPane.showConfirmDialog(
					   this,
					   "¿Seguro desea Borrar las lineas seleccionadas?");// "+selectedRow+"?");

					if (JOptionPane.OK_OPTION == confirmado){
					   
					   int len = selection.length;

					   
					   for(int i=len-1;i>=0;i--){
						   tmr.removeRow(selection[i]);			  
					   }
					   
					   rTable.setModel(tmr);
					   rTable.validate();
				   
					}
						
		}
		
		
		
		else if(e.getActionCommand().equals("AÑADIR CONDICIONES")){
			
			int selectedRow = rTable.getSelectedRow();
			String[] file = new String[6];
			
			file[2] = "**************************************************   ";
			tmr.addRow(file);
			file[2] = "Condiciones de venta:        ";
			tmr.addRow(file);
			file[2] = "1.- IVA:  21%       ";
			tmr.addRow(file);
			file[2] = "2.- Plazo de entrega: 3 Semana     ";
			tmr.addRow(file);
			file[2] = "3.- Forma de pago: "+cliente.getFormaPago();
			tmr.addRow(file);
			file[2] = "4.- Portes: Por cuenta de Vds.    ";
			tmr.addRow(file);
			file[2] = "Por cuenta de TASC (Península) para compras superiores a 2,000€ + IVA.";
			tmr.addRow(file);
			file[2] = "5.- Inst., empalmes, montaje, programación y pruebas: Por cuenta de Vds. ";
			tmr.addRow(file);
			file[2] = "6.- Validez de esta oferta: 30 días    ";
			tmr.addRow(file);
			file[2] = "**************************************************   ";
			tmr.addRow(file);
			
				
			rTable.setModel(tmr);
			rTable.validate();
		}
		
		else if(e.getActionCommand().equals("IMPRIMIR PDF")){
			Presupuesto pres = new Presupuesto();
			
			Date date = fechaJP.getDate();
			SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
			String fecha = date_format.format(date); 

			pres.setFecha(fecha);
			pres.setnOferta(nOfertaTextField.getText());
			pres.setBase(baseTextField.getText());
			pres.setIva(tipoIvaTextField.getText());
			pres.setTotalIva(ivaTextField.getText());
			pres.setTotal(totalTextField.getText());
			pres.setTable(rTable);
			pres.setCliente(cliente);
			
			
			ToPDF pdf = new ToPDF(pres);
			
			try {
				pdf.createPDF();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		
		else if(e.getActionCommand().equals("GUARDAR BD")){
			
		    boolean existe = false;
		    
		    String q = "SELECT numero FROM c_presuv WHERE numero = '"+nOfertaTextField.getText()+"'";
		    
		    try{
		    	setCursor(Tasc.waitCursor);
		    	ResultSet ofrs = bd.Consultar(q);
		    	existe = ofrs.next();
		    	setCursor(Tasc.defCursor);
		    	
		    }
		    catch(Exception e1){
		    	
		    }
			
			if(!existe){
		    
			    int confirmado = JOptionPane.showConfirmDialog(
						   this,
						   "¿Seguro desea Guardar el presupuesto en la Base de Datos?");// "+selectedRow+"?");
	
						if (JOptionPane.OK_OPTION == confirmado){
							setCursor(Tasc.waitCursor);
						
							Date date = fechaJP.getDate();
							SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
							String fecha = date_format.format(date); 
							String usuario = ""+usuarioID;
							String articulo = "";
							String def = "";
							String importe = "0";
							String linea = "";
							String precio = "0";
							String dto = "0";
							String tipo_iva = tipoIvaTextField.getText();
							String coste = "0";
							String unidades = "0";
							String base = baseTextField.getText();
							String precioiva = ivaTextField.getText();
							String importeiva = totalTextField.getText();
							String familia = "06"; // CAMBIAR
							String observaciones = obser2TextArea.getText();
							String cli = cliente.getCodigo();
							
							base = checkDecimalString(base);
							precioiva = checkDecimalString(precioiva);
							importeiva = checkDecimalString(importeiva);
							
							int rows = rTable.getRowCount();
							
							for(int i=0;i<rows;i++){
								articulo =getString(rTable.getValueAt(i, 0),0);
								unidades = getString(rTable.getValueAt(i, 1),1);
								def = getString(rTable.getValueAt(i, 2),0);
								precio = getString(rTable.getValueAt(i, 3),1);
								dto = getString(rTable.getValueAt(i, 4),1);
								importe = getString(rTable.getValueAt(i, 5),1);
								linea = ""+(i+1);
								
								double a = Double.parseDouble(precio);
								a = a + (a*21)/100;
								double b = Double.parseDouble(importe);
								b = b + (b*21)/100;
								String precioivaRow = "" + a;
								String importeivaRow = "" + b;
								
								precioivaRow = checkDecimalString(precioivaRow);
								importeivaRow = checkDecimalString(importeivaRow);
								
								if(def.length()>74)
									def = def.substring(0, 74);
								
								
								String sqlD_Presuv = "INSERT d_presuv VALUES ('COMERCIAL#"+usuario+"','"+Tasc.EMPRESA+"','"+nOfertaTextField.getText()+"',NULL,'"+articulo+"','"+def+"','"
										+unidades+"','"+precio+"','"+dto+"','0','"+importe+"','"+tipo_iva+"','0.000000','0.000000','"+coste+"','','"+linea+"','"
										+cli+"','"+precioivaRow+"','"+importeivaRow+"','0','"+familia+"','0','"+precio+"','"
										+importe+"','0','0.0000','1','0.0000','0','','','','"+importeiva+"','"+precioiva+"','0','','','','','','','','0.00',"
										+ "'','0','','','0.000000','0.000000')";
																
								
								try{
									bd.Ingresar(sqlD_Presuv);
								}
								catch(Exception s){
									System.out.println("ERROR 1: "+s.getMessage());
								}				
							}
			
							String sqlC_Presuv = "INSERT c_presuv VALUES ('COMERCIAL#"+usuario+"','"+Tasc.EMPRESA+"','"+nOfertaTextField.getText()+"','"
									+fecha+"','"+cli+"','1',NULL,'','"+usuario+"','','0.00','0','0','"+observaciones+"','0','"
									+base+"','0','000','1.000000','"+base+"','','','0','0',NULL,'"+fecha+"','0.0000','0.0000','0',"
											+ "'','','0','','','','','0','0','','0')";
	
							
							
							try{
								bd.Ingresar(sqlC_Presuv);
							}
							catch(Exception s){
								System.out.println("ERROR 2: "+s.getMessage());
							}
							
						}
						setCursor(Tasc.defCursor);
						
			}
			else
				JOptionPane.showMessageDialog(null, "YA EXISTE ESTE NÚMERO DE PRESUPUESTO");		
		}
		else{}    
	}
	
	private String checkDecimalString(String string){
		
		int pos = 0;
		if(string.contains(","))
			string = string.replaceFirst(",", ".");
			
		return string;
	}
	
	
	private String getString(Object o, int tipo){
		
		String value = ""+o;
		
		if(value.equals("null") && tipo==0)
			return "";
		
		else if(value.equals("null") && tipo==1)
			return "0";
		else
			return value; 
	}
	
	private static void updateRTable(){

		//rTable.print();
		
		total = 0;
		iva = 0;
		base = 0;
		
		int rows = rTable.getRowCount();
		DecimalFormat df = new DecimalFormat("##0.00");
		for(int i=0;i<rows;i++){
			
			
			Object check = rTable.getValueAt(i, 0);
			String check1 = (String) rTable.getValueAt(i, 1);
			String check2 = (String) rTable.getValueAt(i, 3);
			String check3 = (String) rTable.getValueAt(i, 4) ;
			
			if(check!=null && check1!=null && check2!=null && check3!=null){
			
				Double cantidad = Double.parseDouble(check1);
				Double precio = Double.parseDouble(check2);
				Double descuento = Double.parseDouble(check3);
				Double importe = cantidad * precio;
				
				if(descuento > 0){
					importe = importe-(importe*(descuento/100)); 
				}
				
				rTable.setValueAt(df.format(importe), i, 5);
				
				base += importe;
				
			}
			//tipoIVA = Integer.parseInt(tipoIvaTextField.getText());
			
			
		}	
		
		if(dto1TextField.getText().trim().equals(""))
			dto1TextField.setText("0.00");
		if(dto2TextField.getText().trim().equals(""))
			dto2TextField.setText("0.00");
		
		Double dt0;
		Double dt1 = Double.parseDouble(dto1TextField.getText());
		Double dt2 = Double.parseDouble(dto2TextField.getText());

		base = base-(base* 0.01 * dt1);	
		base = base-(base* 0.01 * dt2);
		
		tipoIVA = Integer.parseInt(tipoIvaTextField.getText());
		iva = (base * tipoIVA)/100;
		total = base + iva;
		

		baseTextField.setText(""+df.format((double)Math.round(base * 100)/100));
		ivaTextField.setText(""+df.format((double)Math.round(iva * 100)/100));
		totalTextField.setText(""+df.format((double)Math.round(total * 100)/100));
	}
	
	
	// Control de texto introducido
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
	

	

	
	private class JTextFieldLimit extends PlainDocument{
		private int limit;
		private JTextField field = new JTextField();

		JTextFieldLimit(int limit){
			super();
			this.limit = limit;
		}
		
		JTextFieldLimit(int limit, boolean upper){
			super();
			this.limit = limit;
		}
		
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
			if( str == null)
				return;
			
			if((getLength()+ str.length()) <= limit){
				super.insertString(offset, str, attr);
			}
		}
	}
	
	
	
	private static class EnterAction extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent ac) {
					
			NuevoPresupuesto.updateRTable();
			
		}
		
	}
	
	private class MyKeyListener implements KeyListener{
		
		@Override
		public void keyPressed(KeyEvent e) {
			updateRTable();
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			updateRTable();
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			updateRTable();
			
		}
		
	}
	
	

	


}
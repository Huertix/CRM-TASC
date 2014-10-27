package formularios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import clases.BaseDatos;
import clases.Cliente;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;


//xml
import javax.xml.parsers.*;
import javax.xml.transform.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.sql.ResultSetMetaData;

public class Clientes extends JFrame {

	private Hashtable hTable;
	private final BaseDatos bd;
	private ResultSet  rs = null;
	private ResultSet  auxrs = null;
	private JPanel panelCliente;
	private JTextField codigoField;
	private String codigoSt;
	private JTextField direccionField;
	private JTextField cifField;
	private JTextField cpField;
	private JTextField poblacionField;
	private JTextField provinciaField;
	private JTextArea obserField;
	private JButton lsButton;
	private JButton nuevoButton;
	private JButton pedidosButton;
	private JButton albButton;
	private JButton futuroCliButton;
	private JTextField creditoField;
	private final JComboBox contactoComboBox;
	private Cliente cliente;
	private String vendedorID;
	private int rsLines;
	private ResultSet rsFCli;

	/**
	 * Create the frame.
	 */
	public Clientes(final String ID, BaseDatos b) {
		this.bd = b;
		this.vendedorID = ID;
		hTable = new Hashtable();
		setTitle("CRM TASC - CLIENTES");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 800, 460);
		setLocationRelativeTo(null);
		setResizable(true);
		panelCliente = new JPanel();
		panelCliente.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelCliente);
		panelCliente.setLayout(null);
		
		final JComboBox clienteComboBox = new JComboBox();
		clienteComboBox.addItem(null);
		
		clienteComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( clienteComboBox.getSelectedItem()!=null){
					String st = clienteComboBox.getSelectedItem().toString();
					int row = Integer.parseInt(hTable.get(st).toString());
					
					lsButton.setEnabled(true);
					nuevoButton.setEnabled(true);
					pedidosButton.setEnabled(true);
					albButton.setEnabled(true);
					
					try {	
						
						ResultSet aux = null;
						if(row > rsLines){
							System.out.println("lines:"+ rsLines);
							System.out.println("row:"+ row);
							System.out.println("lines-row:"+ (row-rsLines));
							rsFCli.absolute(row-rsLines);
							aux = rsFCli;
							System.out.println("rsFCli");
							
						}
						else{
							rs.absolute(row);
							aux = rs;	
							obserField.setText(aux.getString("observacio"));
							creditoField.setText(aux.getString("credito"));
							System.out.println("rs" +row);
							System.out.println("rs" +aux.getString("tipo_iva"));
							//cliente.setTipoIVA(aux.getString("tipo_iva"));
							
						}
						
						System.out.println("bueno");
						codigoField.setText(aux.getString("codigo"));
						System.out.println("bueno1");
						direccionField.setText(aux.getString("direccion"));
						System.out.println("bueno2");
						cifField.setText(aux.getString("cif"));
						System.out.println("bueno3");
						cpField.setText(aux.getString("codpost"));
						System.out.println("bueno4");
						poblacionField.setText(aux.getString("poblacion"));
						System.out.println("bueno5");
						provinciaField.setText(aux.getString("provincia"));				
						
						
						cliente = new Cliente(codigoField.getText(),st);
						cliente.setCp(cpField.getText());
						cliente.setDireccs(direccionField.getText()+"\n"
								+cliente.getCp()+" "+poblacionField.getText()+"\n"
								+provinciaField.getText());
						cliente.setObser(obserField.getText());
						cliente.setCredit(creditoField.getText());
						cliente.setCif(cifField.getText());
						System.out.println("bueno6");

						if(row < rsLines){
							// Insertamos contactos en contactoComboBox
							String sql = "SELECT * FROM cont_cli WHERE cliente = "+codigoField.getText()+" ORDER BY persona";
							auxrs = bd.Consultar(sql);
							contactoComboBox.removeAllItems();
							while(auxrs.next()){
								
								String auxname = auxrs.getString("persona")+auxrs.getString("telefono")+auxrs.getString("email");						
								System.out.println(auxname);
								contactoComboBox.addItem(auxname);
								}
							sql = "SELECT nombre FROM fpag WHERE codigo = '"+rs.getString(24)+"'";
							auxrs = bd.Consultar(sql);
							if(auxrs.next())
								cliente.setFormaPago(auxrs.getString("nombre"));
						}
						else{
							contactoComboBox.removeAllItems();
							contactoComboBox.addItem(aux.getString("contacto")+aux.getString("telefono"));
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}	
			}
		});
		
		
		contactoComboBox = new JComboBox();
		contactoComboBox.setBounds(12, 232, 728, 24);
		panelCliente.add(contactoComboBox);
		

		
		String sql = "SELECT * FROM clientes WHERE vendedor = '"+vendedorID+"' ORDER BY nombre";
		String sqlFCli = "SELECT * FROM prclient WHERE vendedor = '"+vendedorID+"' ORDER BY nombre";
		rs = bd.Consultar(sql);
		rsFCli = bd.Consultar(sqlFCli);
		
		System.out.println("Hola"+sqlFCli);
		
		try{
			rsLines = 0;
			while(rs.next()){

				Integer row = rs.getRow();
				String name = rs.getString("nombre");
				hTable.put(name, row);
				clienteComboBox.addItem(name);
				rsLines++;
				}
			
			clienteComboBox.addItem("---------FUTUROS CLIENTES---------");
			//rsLines += 1;// + 1 Porque le sumo la linea en el comboBox de aqui arriba.
			int a = 0;
			while(rsFCli.next()){
				//System.out.println(++a);
				Integer row = rsFCli.getRow();
				String name = rsFCli.getString("nombre");		
				hTable.put(name, row+rsLines);
				System.out.println("LINEAS"+(row+rsLines));
				clienteComboBox.addItem(name);
			}
			
			
			
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Fallo SQL: " + ex.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
			
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Fallo: " + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		
		clienteComboBox.setBounds(12, 30, 540, 24);
		panelCliente.add(clienteComboBox);
		
		JLabel clienteLabel = new JLabel("CLIENTE");
		clienteLabel.setBounds(12, 12, 70, 15);
		panelCliente.add(clienteLabel);
		
		JLabel codigoLabel = new JLabel("CODIGO");
		codigoLabel.setBounds(590, 12, 70, 15);
		panelCliente.add(codigoLabel);
		
		codigoField = new JTextField();
		codigoField.setFont(new Font("Arial", Font.BOLD, 14));
		codigoField.setEditable(false);
		codigoField.setHorizontalAlignment(SwingConstants.LEFT);
		codigoField.setBounds(590, 30, 150, 24);
		codigoField.setColumns(10);
		panelCliente.add(codigoField);
		
		direccionField = new JTextField();
		direccionField.setHorizontalAlignment(SwingConstants.LEFT);
		direccionField.setEditable(false);
		direccionField.setBounds(12, 100, 418, 24);
		panelCliente.add(direccionField);
		direccionField.setColumns(10);
		
		cifField = new JTextField();
		cifField.setEditable(false);
		cifField.setBounds(590, 100, 150, 24);
		panelCliente.add(cifField);
		cifField.setColumns(10);
		
		cpField = new JTextField();
		cpField.setEditable(false);
		cpField.setFont(new Font("Arial", Font.PLAIN, 14));
		cpField.setBounds(492, 172, 100, 24);
		panelCliente.add(cpField);
		cpField.setColumns(10);
		
		poblacionField = new JTextField();
		poblacionField.setEditable(false);
		poblacionField.setBounds(12, 172, 250, 24);
		panelCliente.add(poblacionField);
		poblacionField.setColumns(10);
		
		provinciaField = new JTextField();
		provinciaField.setEditable(false);
		provinciaField.setBounds(274, 172, 200, 24);
		panelCliente.add(provinciaField);
		provinciaField.setColumns(10);
		
		obserField = new JTextArea();
		obserField.setEditable(false);
		obserField.setBackground(Color.yellow);
		obserField.setBounds(357, 320, 383, 100);
		panelCliente.add(obserField);
		obserField.setColumns(10);
		
		creditoField = new JTextField();
		creditoField.setEditable(false);
		creditoField.setHorizontalAlignment(SwingConstants.RIGHT);
		creditoField.setBounds(610, 172, 150, 24);
		panelCliente.add(creditoField);
		creditoField.setColumns(10);
		
		JLabel direccionLabel = new JLabel("DIRECCIÓN");
		direccionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		direccionLabel.setBounds(12, 78, 150, 24);
		panelCliente.add(direccionLabel);
		
		JLabel cifLabel = new JLabel("CIF");
		cifLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cifLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		cifLabel.setBounds(590, 75, 150, 24);
		panelCliente.add(cifLabel);
		
		JLabel cpLabel = new JLabel("CP");
		cpLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cpLabel.setBounds(492, 150, 150, 24);
		panelCliente.add(cpLabel);
		
		JLabel poblacionLabel = new JLabel("POBLACIÓN");
		poblacionLabel.setBounds(12, 150, 150, 24);
		panelCliente.add(poblacionLabel);
		
		JLabel provinciaLabeL = new JLabel("PROVINCIA");
		provinciaLabeL.setBounds(274, 150, 150, 24);
		panelCliente.add(provinciaLabeL);
		
		JLabel contactoLabel = new JLabel("CONTACTO");
		contactoLabel.setBounds(12, 208, 150, 24);
		panelCliente.add(contactoLabel);
		
		JLabel obserLabel = new JLabel("OBSERVACIONES");
		obserLabel.setBounds(357, 320, 300, 24);
		panelCliente.add(obserLabel);
		
		JLabel creditoLabel = new JLabel("CREDITO");
		creditoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		creditoLabel.setBounds(610, 150, 150, 24);
		panelCliente.add(creditoLabel);
		
		futuroCliButton = new JButton("NUEVO FUTURO CLIENTE");
		futuroCliButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FuturoCliente ps = new FuturoCliente(vendedorID,bd);
				
			}
		}); 
		futuroCliButton.setBounds(357, 270, 230, 30);
		//futuroCliButton.setEnabled(false);
		panelCliente.add(futuroCliButton);
		
		lsButton = new JButton("LISTADO PRESUPUESTOS");
		lsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Presupuestos2 ps = new Presupuestos2(codigoField.getText(),vendedorID,cliente,bd);
				
			}
		});
		lsButton.setBounds(35, 270, 230, 30);
		lsButton.setEnabled(false);
		panelCliente.add(lsButton);
		
		nuevoButton = new JButton("NUEVO PRESUPUESTO");
		nuevoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					NuevoPresupuesto nps = new NuevoPresupuesto(vendedorID,cliente,bd);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		nuevoButton.setBounds(35, 310, 230, 30);
		nuevoButton.setEnabled(false);
		panelCliente.add(nuevoButton);
		
		pedidosButton = new JButton("PEDIDOS");
		pedidosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Pedidos p = new Pedidos(codigoField.getText(),bd);
				
			}
		});
		pedidosButton.setBounds(35, 350, 230, 30);
		pedidosButton.setEnabled(false);
		panelCliente.add(pedidosButton);
		
		albButton = new JButton("ALBARANES");
		albButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Albaranes a = new Albaranes(codigoField.getText(),bd);
				
			}
		});
		albButton.setBounds(35, 390, 230, 30);
		albButton.setEnabled(false);
		panelCliente.add(albButton);
		
		setResizable(false);
		setVisible(true);
	}
	
	public String getData(String colum, int row){
		//rs.se
		return null;
	}
}

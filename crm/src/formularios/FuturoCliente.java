package formularios;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import javax.xml.parsers.ParserConfigurationException;

import clases.BaseDatos;
import clases.Cliente;

public class FuturoCliente extends JFrame {

	private JPanel contentPane;
	private BaseDatos bd;
	private String vendedor;
	private Cliente cliente;
	private JLabel codigoLabel;
	private JLabel nombreLabel;
	private JLabel cifLabel;
	private JLabel direccLabel;
	private JLabel cpLabel;
	private JLabel poblacionLabel;
	private JLabel provinciaLabel;
	private JLabel contactoLabel;
	private JLabel telefonoLabel;
	private JLabel movilLabel;
	private JLabel faxLabel;
	private JLabel emailLabel;
	private JTextField codigoTextField;
	private JTextField nombreTextField;
	private JTextField cifTextField;
	private JTextField direccTextField;
	private JTextField cpTextField;
	private JTextField poblacionTextField;
	private JTextField provinciaTextField;
	private JTextField contactoTextField;
	private JTextField telefonoTextField;
	private JTextField movilTextField;
	private JTextField faxTextField;
	private JTextField emailTextField;
	private JButton nuevoButton;
	private JButton guardarButton;
	private JButton visitaButton;
	private NuevoPresupuesto nps;

	

	public FuturoCliente(String ID,BaseDatos b) {
		bd = b;
		vendedor = ID;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("CREACION FUTURO CLIENTE - CRM TASC");
		setBounds(100, 100, 550, 450);
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cliente = new Cliente();
		
		codigoLabel = new JLabel("CODIGO: ");
		codigoLabel.setBounds(10, 10, 100, 24);
		codigoLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(codigoLabel);
		codigoTextField = new JTextField();
		codigoTextField.setEditable(false);	
		codigoTextField.setBounds(120, 10, 30, 24);
		codigoTextField.setFont(new Font("Arial", Font.BOLD, 14));
		codigoTextField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		contentPane.add(codigoTextField);
	
		nombreLabel= new JLabel("NOMBRE: ");
		nombreLabel.setBounds(10, 40, 100, 24);
		nombreLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(nombreLabel);		
		nombreTextField = new JTextField();
		nombreTextField.setBounds(120, 40, 400, 24);
		nombreTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(nombreTextField);
		

		cifLabel= new JLabel("CIF: ");
		cifLabel.setBounds(10, 70, 100, 24);
		cifLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(cifLabel);	
		cifTextField = new JTextField();
		cifTextField.setBounds(120, 70, 150, 24);
		cifTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(cifTextField);	

		
		direccLabel = new JLabel("DIRECCION: ");
		direccLabel.setBounds(10, 100, 100, 24);
		direccLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(direccLabel);
		direccTextField = new JTextField();
		direccTextField.setBounds(120, 100, 400, 24);
		direccTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(direccTextField);
		
		cpLabel = new JLabel("CP: ");
		cpLabel.setBounds(10, 130, 100, 24);
		cpLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(cpLabel);
		cpTextField = new JTextField();
		cpTextField.setBounds(120, 130, 150, 24);
		cpTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(cpTextField);
		
		poblacionLabel = new JLabel("POBLACIÓN: ");
		poblacionLabel.setBounds(10, 160, 100, 24);
		poblacionLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(poblacionLabel);
		poblacionTextField = new JTextField();
		poblacionTextField.setBounds(120, 160, 400, 24);
		poblacionTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(poblacionTextField);
		
		provinciaLabel = new JLabel("PROVINCIA: ");
		provinciaLabel.setBounds(10, 190, 100, 24);
		provinciaLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(provinciaLabel);
		provinciaTextField = new JTextField();
		provinciaTextField.setBounds(120, 190, 150, 24);
		provinciaTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(provinciaTextField);
		
		contactoLabel = new JLabel("CONTACTO: ");
		contactoLabel.setBounds(10, 220, 100, 24);
		contactoLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(contactoLabel);
		contactoTextField = new JTextField();
		contactoTextField.setBounds(120, 220, 400, 24);
		contactoTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(contactoTextField);
		
		telefonoLabel = new JLabel("TELEFONO: ");
		telefonoLabel.setBounds(10, 250, 100, 24);
		telefonoLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(telefonoLabel);
		telefonoTextField = new JTextField();
		telefonoTextField.setBounds(120, 250, 150, 24);
		telefonoTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(telefonoTextField);
		
		movilLabel = new JLabel("MOVIL: ");
		movilLabel.setBounds(10, 280, 100, 24);
		movilLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(movilLabel);
		movilTextField = new JTextField();
		movilTextField.setBounds(120, 280, 150, 24);
		movilTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(movilTextField);
		
		faxLabel = new JLabel("FAX: ");
		faxLabel.setBounds(10, 310, 100, 24);
		faxLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(faxLabel);
		faxTextField = new JTextField();
		faxTextField.setBounds(120, 310, 150, 24);
		faxTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(faxTextField);
		
		emailLabel = new JLabel("EMAIL: ");
		emailLabel.setBounds(10, 340, 100, 24);
		emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(emailLabel);
		emailTextField = new JTextField();
		emailTextField.setBounds(120, 340, 400, 24);
		emailTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(emailTextField);
		
	
		guardarButton = new JButton("GUARDAR CLIENTE");
		guardarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
						
				cliente.setCodigo(codigoTextField.getText());
				cliente.setNombre(nombreTextField.getText());
				cliente.setVendedor(vendedor);
				cliente.setCif(cifTextField.getText());
				cliente.setCp(cpTextField.getText());
				cliente.setDireccs(direccTextField.getText());
				cliente.setPoblacion(poblacionTextField.getText());
				cliente.setProvincia(provinciaTextField.getText());
				cliente.setContact(contactoTextField.getText());
				cliente.setTlf(telefonoTextField.getText());
				cliente.setMov(movilTextField.getText());
				
				boolean existe = false;
			    int cod = Integer.parseInt(cliente.getCodigo());
			    String q = "SELECT codigo FROM prclient WHERE codigo = "+Integer.parseInt(cliente.getCodigo());
			    
			    try{
			    	ResultSet ofrs = bd.Consultar(q);
			    	existe = ofrs.next();
	
				
				int codigo = Integer.parseInt(cliente.getCodigo());
				
				String sql = "INSERT prclient VALUES ("+codigo+",'"+cliente.getCif()+"','"+cliente.getNombre()+"','"
						+cliente.getDireccs()+"','"+cliente.getCp()+"','','"+cliente.getTlf()+"','TRUE','01','','"
						+cliente.getContact()+"','','TRUE','"+cliente.getPoblacion()+"','"+cliente.getProvincia()+"','"
						+cliente.getVendedor()+"')";
				System.out.println(sql);
				
					
		
					if(cliente.minValues() && !existe){
						bd.Ingresar(sql);
						cliente.setSaved(true);
					}
					else{
						if(existe)
							JOptionPane.showMessageDialog(null, "CLIENTE YA EXISTE");
						else
							JOptionPane.showMessageDialog(null, "RELLENE TODOS LOS CAMPOS");
					}
				}
				catch(Exception s){
					System.out.println("ERROR 0: "+s.getMessage());
				}
				
			}
		});
		guardarButton.setBounds(30, 380, 200, 30);
		contentPane.add(guardarButton);

		
		
		nuevoButton = new JButton("NUEVO PRESUPUESTO");
		nuevoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(!cliente.isSaved())
						guardarButton.doClick();
					if(cliente.isSaved())
						nps = new NuevoPresupuesto(vendedor,cliente,bd);
					
				} catch(Exception s){
					System.out.println("ERROR 1: "+s.getMessage());
				}

				
			}
		});
		nuevoButton.setBounds(300,380, 200, 30);
		contentPane.add(nuevoButton);
		

		
		try {
			getCodigoCliente();
		} catch (SQLException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR getting client ID: "+e.getMessage());
		}
		

		setVisible(true);
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
	
	private JTextField createLimitedTextField(final int limit) {
		
		final JTextField field = new JTextField();
		
		PlainDocument pd = new PlainDocument();
		
		((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet attr) throws BadLocationException{
				if( str == null)
					return;
				
				if((field.getDocument().getLength()+ str.length()) <= limit){
					super.insertString(fb, off, str, attr);
				}
            }
		});
		return field;
	}
	
	
	public void  getCodigoCliente() throws SQLException, ParserConfigurationException{
	
		String sql = "SELECT codigo FROM prclient ORDER BY codigo";
		
		ResultSet rs = bd.Consultar(sql);
	
		if(rs.next()){	
			rs.last();
			int last = rs.getInt(1);
			last +=1;  
			cliente.setCodigo(""+last);
			codigoTextField.setText(cliente.getCodigo());
		}

	}	
	


	

}

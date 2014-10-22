package formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JLabel faxLabel;
	private JLabel emailLabel;
	private JTextField nombreTextField;
	private JTextField cifTextField;
	private JTextField direccTextField;
	private JTextField cpTextField;
	private JTextField poblacionTextField;
	private JTextField provinciaTextField;
	private JTextField contactoTextField;
	private JTextField telefonoTextField;
	private JTextField faxTextField;
	private JTextField emailTextField;

	

	public FuturoCliente(String ID,BaseDatos b) {
		bd = b;
		vendedor = ID;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("CREACION FUTURO CLIENTE - CRM TASC");
		setBounds(100, 100, 550, 400);
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
		
		faxLabel = new JLabel("FAX: ");
		faxLabel.setBounds(10, 280, 100, 24);
		faxLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(faxLabel);
		faxTextField = new JTextField();
		faxTextField.setBounds(120, 280, 150, 24);
		faxTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(faxTextField);
		
		emailLabel = new JLabel("EMAIL: ");
		emailLabel.setBounds(10, 310, 100, 24);
		emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(emailLabel);
		emailTextField = new JTextField();
		emailTextField.setBounds(120, 310, 400, 24);
		emailTextField.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(emailTextField);
		

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
	

}

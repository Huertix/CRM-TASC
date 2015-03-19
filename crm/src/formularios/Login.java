package formularios;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

import java.awt.Color;

import javax.swing.ImageIcon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import clases.BaseDatos;
import clases.Tasc;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JComboBox;

public class Login extends JFrame {

	private JPanel contentPane;
	private JPasswordField txtPassword;
	private BaseDatos bd = null;
	private ResultSet  rs = null;
	private String usuarioID;
	private String fecha_ini;
	private static Toolkit toolKit;
	private static double locationWidth;
    private static double locationHeight;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public Login() throws SQLException {
		
		toolKit = Toolkit.getDefaultToolkit();
        locationWidth =toolKit.getScreenSize().getWidth()/2;
        locationHeight =toolKit.getScreenSize().getHeight()/2;
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/LogoTASC.png")));
	
		setResizable(false);
		setTitle("CRM TASC - LOGIN         Version BETA 20.3.15");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 516, 285);
		setLocationRelativeTo(null);
		setFocusable(true);
		
		addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent e){

				System.exit(0);
			}
		});
		
		
		DecimalFormat df = new DecimalFormat("#0.00");
		
		String d = df.format(0.0);
		
		if(d.equals("0,00")){
			JOptionPane.showMessageDialog(this, "ESTE EQUIPO TIENE CONFIGURADO EL SEPARADOR DECIMAL "+
		"CON COMA   ','.\nESTA CONFIGURACION NO ES COMPATIBLE CON LA BASE DE DATOS DE EUROWIN\n"+
		"PUEDE UTILIZAR UNA DE LA SIGUIENTE SOLUCIONES:\n\n1 - CAMBIAR EL SEPARADO DECIMAL CON PUNTO '.' EN CONFIGURACION REGIONAL DEL PANEL DE CONTROL,\n"+
		"    CONFIGURACIONALES ADICIONALES\n\n"+"2 - SELECCIONAR FORMATO INGLES (REINO UNIDO) EN LA PARTE SUPERIOR DE CONFIGURACIONES REGIONALES");
			System.exit(0);
		}
			
		
		
		int result = JOptionPane.showConfirmDialog(this,
				"¿DESEA REALIZAR UNA CONEXION FUERA DE LA OFICINA?",
				"TASC CRM - CONEXION BD",JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION)
			bd = new BaseDatos(true);
		else
			bd = new BaseDatos();
		
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		JLabel lblNewLabel_0 = new JLabel("");
		lblNewLabel_0.setIcon(new ImageIcon(Login.class.getResource("/imagenes/LogoTASC.png")));
		lblNewLabel_0.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_0.setBounds(10, 10, 80, 27);
		contentPane.add(lblNewLabel_0);
		
		
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addItem(null);
		String sql = "SELECT nombre FROM vendedor ORDER BY nombre";
		rs = bd.Consultar(sql);
		try{
			while(rs.next()){
				comboBox.addItem(rs.getString(1));
				
			}	
		}
		catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Fallo Boto: " + ex.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
			
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Fallo Conexión boton","Error",JOptionPane.ERROR_MESSAGE);
		}
		
		comboBox.setBounds(153, 82, 177, 24);
		contentPane.add(comboBox);
		
		
			
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(31, 82, 104, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Contraseña");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1.setBounds(31, 122, 104, 15);
		contentPane.add(lblNewLabel_1);
		
		 
		JButton btnNewButton = new JButton("Conectar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				setCursor(Tasc.waitCursor);
				boolean respuesta = false;
				String usuario = comboBox.getSelectedItem().toString();
				String password = new String(txtPassword.getPassword());
				String sql = "SELECT * FROM vendedor WHERE nombre = '"+usuario+"' AND password = '"+password+"'";
				
				rs = bd.Consultar(sql);
				try{
					while(rs.next()){
						respuesta = true;					
						usuarioID = rs.getString("codigo");
					}
					if(respuesta){
						/*
						Date date = new Date();
						//Caso 1: obtener la hora y salida por pantalla con formato:
						//DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
						//System.out.println("Hora: "+hourFormat.format(date));
						//Caso 2: obtener la fecha y salida por pantalla con formato:
						//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						//System.out.println("Fecha: "+dateFormat.format(date));
						//Caso 3: obtenerhora y fecha y salida por pantalla con formato:
						DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						//System.out.println("Hora y fecha: "+hourdateFormat.format(date));
						fecha_ini = hourdateFormat.format(date);*/
						
						Hall hall = new Hall(usuario, usuarioID, bd);
						
						/*
						String sql_log_crm = "INSERT logcrm VALUES ('COMERCIAL#"+usuarioID+"','"+fecha_ini+"','INICIO')";
						
						try{
							bd.Ingresar(sql_log_crm);
						}
						catch(Exception s){
							System.out.println("ERROR INICIO LOGCRM: "+s.getMessage());
						}*/
						
						setVisible(false);
						
					}
					else{
						JOptionPane.showMessageDialog(null, "Credenciales Incorrectas","Error",JOptionPane.ERROR_MESSAGE);
						setCursor(Tasc.defCursor);
					}
					

				}
				catch(SQLException ex){
					JOptionPane.showMessageDialog(null, "Fallo Boto: " + ex.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
					
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Fallo Conexión boton","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnNewButton.setBounds(56, 169, 274, 48);
		contentPane.add(btnNewButton);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(153, 121, 177, 19);
		contentPane.add(txtPassword);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/imagenes/login1.png")));
		lblNewLabel_2.setVerticalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setBounds(350, 50, 180, 180);
		contentPane.add(lblNewLabel_2);
	
	
	}
}
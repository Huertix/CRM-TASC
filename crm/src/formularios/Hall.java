package formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import com.itextpdf.text.DocumentException;

import clases.BaseDatos;
import clases.Tasc;
import clases.ToPDF;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Hall extends JFrame {

	private JPanel contentPane;
	private final String usuarioID;
	

	/**
	 * Create the frame.
	 */
	public Hall(String usuario, String ID, final BaseDatos bd) {
		
		usuarioID = ID;
		setTitle("INICIO - CRM TASC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(0 ,0 , 520, 400);
		setLocationRelativeTo(null);
		setFocusable(true);
		
		addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				/*Date date = new Date();
				DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String fecha_fin = hourdateFormat.format(date);
				
				
				String sql_log_crm = "INSERT logcrm VALUES ('COMERCIAL#"+usuarioID+"','"+fecha_fin+"','FIN')";
				
				try{
					bd.Ingresar(sql_log_crm);
				}
				catch(Exception s){
					System.out.println("ERROR FIN LOGCRM: "+s.getMessage());
				}
				*/
				
		
				System.exit(0);
			}
		});
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("COMERCIAL:   "+usuario);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel.setBounds(120, 40, 336, 15);
		contentPane.add(lblNewLabel);
		
		JButton clientesButton = new JButton("CLIENTES");
		clientesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setCursor(Tasc.waitCursor);
				Clientes clientes = new Clientes(usuarioID, bd);
				setCursor(Tasc.defCursor);
			}
		});
		clientesButton.setFont(new Font("Arial", Font.BOLD, 20));
		clientesButton.setBounds(65, 115, 170, 70);
		contentPane.add(clientesButton);
		
		JButton presuButton = new JButton("VENTAS");
		presuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*ToPDF pdf = new ToPDF();
				try {
					pdf.createPDF();
				} catch (IOException | DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});
		presuButton.setFont(new Font("Arial", Font.BOLD, 20));
		presuButton.setBounds(65, 250, 170, 70);
		presuButton.setEnabled(false);
		contentPane.add(presuButton);
		
		JButton visitasButton = new JButton("VISITAS");
		visitasButton.setFont(new Font("Arial", Font.BOLD, 20));
		visitasButton.setBounds(300, 115, 170, 70);
		visitasButton.setEnabled(false);
		contentPane.add(visitasButton);
		
		JButton articulosButton = new JButton("ARTICULOS");
		articulosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setCursor(Tasc.waitCursor);
				Articulos articulos = new Articulos(bd);
				setCursor(Tasc.defCursor);
			}
		});
		articulosButton.setFont(new Font("Arial", Font.BOLD, 20));
		articulosButton.setBounds(300, 250, 170, 70);
		contentPane.add(articulosButton);
		
		setVisible(true);
	}
}
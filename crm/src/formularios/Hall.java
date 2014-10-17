package formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import clases.BaseDatos;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
				Clientes clientes = new Clientes(usuarioID, bd);
			}
		});
		clientesButton.setFont(new Font("Arial", Font.BOLD, 20));
		clientesButton.setBounds(65, 115, 170, 70);
		contentPane.add(clientesButton);
		
		JButton presuButton = new JButton("VENTAS");
		presuButton.setFont(new Font("Arial", Font.BOLD, 20));
		presuButton.setBounds(65, 250, 170, 70);
		contentPane.add(presuButton);
		
		JButton visitasButton = new JButton("VISITAS");
		visitasButton.setFont(new Font("Arial", Font.BOLD, 20));
		visitasButton.setBounds(300, 115, 170, 70);
		contentPane.add(visitasButton);
		
		JButton articulosButton = new JButton("ARTICULOS");
		articulosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Articulos articulos = new Articulos(bd);
			}
		});
		articulosButton.setFont(new Font("Arial", Font.BOLD, 20));
		articulosButton.setBounds(300, 250, 170, 70);
		contentPane.add(articulosButton);
		
		setVisible(true);
	}
}

package formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clases.BaseDatos;

public class Albaranes extends JFrame {

	private JPanel contentPane;
	private String cliente;
	private BaseDatos bd;

	// código es parametro de cliente
	public Albaranes(String cliente,BaseDatos b) {
		this.cliente = cliente;
		this.bd = b;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setVisible(true);
	}

}

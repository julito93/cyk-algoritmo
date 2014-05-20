package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;

import mundo.Main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTextField txtGramatica;
	private JTextField txtCadena;
	private tabGramatica tabGramatica;
	private JTextField txtRespuesta;
	private JTextField textField;
	private tabCYK tabCYK;
	
	private Main mundo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
				
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		
		Main mundo = new Main();
		this.mundo = mundo;
		setTitle("Algoritmo CYK - Informatica Teorica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 590, 364);
		contentPane.add(tabbedPane);
		
		tabGramatica = new tabGramatica(this);
		tabbedPane.addTab("Gram\u00E1tica en FNC", null, tabGramatica, null);
		
		tabCYK = new tabCYK();
		tabbedPane.addTab("Algoritmo CYK", null, tabCYK, null);

	}
	
	public Main mundo()
	{
		return mundo;
	}

	public tabCYK getTabCYK() {
		return tabCYK;
	}

	public void inicializarPanelCyk( int length )
	{
		tabCYK.inicializar( length );
	}
	
	
}

package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class tabGramatica extends JPanel implements ActionListener {

	private static final String IR = "ir";
	private JTextField txtCadena;
	private Principal principal;
	private JButton btnWPerteneceA;
	private JTextArea txtGramatica;

	/**
	 * Create the panel.
	 */
	public tabGramatica(Principal principal) {
		
		this.principal = principal;

		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblG = new JLabel("G:");
		lblG.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 30));
		lblG.setBounds(24, 148, 58, 43);
		add(lblG);
		
		JLabel labelIcon = new JLabel("");
		labelIcon.setIcon(new ImageIcon("./data/pic.jpg"));
		labelIcon.setBounds(65, 11, 71, 325);
		add(labelIcon);
		
		JLabel lblCadena = new JLabel("Cadena w:");
		lblCadena.setBounds(410, 54, 117, 14);
		add(lblCadena);
		
		txtCadena = new JTextField();
		txtCadena.setBounds(409, 79, 166, 20);
		add(txtCadena);
		txtCadena.setColumns(10);
		
		btnWPerteneceA = new JButton("\u00BF w pertenece a L(G) ?");
		btnWPerteneceA.addActionListener(this);
		btnWPerteneceA.setActionCommand(IR);
	
		btnWPerteneceA.setBounds(410, 110, 165, 23);
		add(btnWPerteneceA);
		
		txtGramatica = new JTextArea();
		txtGramatica.setBounds(124, 49, 276, 256);
		add(txtGramatica);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String cmd = e.getActionCommand();
		if(cmd.equals("ir"))
		{
			String ruta = "./data/gramatica.txt";
			try 
			{
				BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta));
				escritor.write("Productions:"+"\n");
				String gramatica = txtGramatica.getText();
				escritor.write(gramatica);
				escritor.write("String:"+"\n");
				escritor.write(txtCadena.getText());
				
				escritor.close();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String r = "./data/prueba1.txt";
			principal.mundo().SimulateGrammar(ruta);
			
			principal.getTabCYK().actualizar(txtCadena.getText());
			
		}
		
	}
}

package vista;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Prueba1 extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Create the panel.
	 */
	public Prueba1() 
	
	{
		
		textField_3 = new JTextField();
		add(textField_3);
		textField_3.setColumns(10);
		
		textField_2 = new JTextField();
		add(textField_2);
		textField_2.setColumns(10);
		
		textField_1 = new JTextField();
		add(textField_1);
		textField_1.setColumns(10);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		
		

	}

}

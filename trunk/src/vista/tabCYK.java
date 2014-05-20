package vista;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class tabCYK extends JPanel {

	private JTextField txtRespuesta;
	private JTextArea txtAreaRespuesta;

	/**
	 * Create the panel.
	 */
	public tabCYK() {

		setLayout(null);

		txtRespuesta = new JTextField();
		txtRespuesta.setEditable(false);
		txtRespuesta.setBounds(25, 34, 550, 20);
		add(txtRespuesta);
		txtRespuesta.setColumns(10);

		txtAreaRespuesta = new JTextArea();
		txtAreaRespuesta.setEditable(false);
		txtAreaRespuesta.setBounds(25, 70, 550, 255);
		add(txtAreaRespuesta);
		txtAreaRespuesta.setColumns(10);

	}

	public void actualizar(String cadenaW) {

		String cadena = "                     ";
		try
		{
			BufferedReader in = new BufferedReader(new FileReader("./data/respuesta.txt"));
			String linea = in.readLine();
			
			for (int i = 0; i < cadenaW.length(); i++) 
			{
				cadena += "j = " + (i+1) + "     ";
			}
			cadena += "\n";
			int j=0;
			while(!linea.equals("&"))
			{
				cadena = cadena+" "+ cadenaW.charAt(j) + "     i = " + (j+1) + "   ";
				String[] produccion = linea.split("%");
				for(int i =0; i<produccion.length; i++)
				{
					if(produccion[i].length() > 3)
					{
						produccion[i] = produccion[i].replace("}{", ", ");
					}
					
					cadena = cadena + "   " + produccion[i] + "   ";
				}
				cadena = cadena + "\n";
				linea = in.readLine();
				j++;
			}
			String r = in.readLine();
			if(r.equals("true"))
			{
				txtRespuesta.setText("La cadena w = " + cadenaW + "   pertenece a L(G) ");
			}
			else
			{
				txtRespuesta.setText("La cadena w = " + cadenaW + "   NO pertenece a L(G) ");
			}
			txtAreaRespuesta.setText(cadena);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

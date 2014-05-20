package vista;

import java.awt.GridLayout;
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
	private JTextField[][] matriz;

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

		
		for (int i = 1; i < matriz.length; i++) {
			JTextField mi= matriz[i][0];
			mi.setText(""+cadenaW.charAt(i-1));
			JTextField mj = matriz[0][i];
			mj.setText("j="+i);
		}
		try
		{
			
			BufferedReader in = new BufferedReader(new FileReader("./data/respuesta.txt"));
			String linea = in.readLine();
			String cadena = "";
			int j=0;
			while(!linea.equals("&"))
			{
				
				String[] produccion = linea.split("%");
				for(int i =0; i<produccion.length; i++)
				{
					if(produccion[i].length() > 3)
					{
						produccion[i] = produccion[i].replace("}{", ", ");
					}
					matriz[j+1][i+1].setText(produccion[i]);
					
				}
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

	public void inicializar( int t )
	{
		removeAll( );
		
		setLayout(null);
		txtRespuesta = new JTextField();
		txtRespuesta.setEditable(false);
		txtRespuesta.setBounds(25, 34, 550, 20);
		add(txtRespuesta);
		txtRespuesta.setColumns(10);
		
		
		JPanel panel = new JPanel();
		int f = t+1;
		int c = t+2;
		panel.setLayout( new GridLayout( f,c ) );
		matriz = new JTextField[f][c];
		for ( int i = 0; i < f; i++ )
		{
			for ( int j = 0; j < c; j++ )
			{
				matriz[i][j] = new JTextField();
				panel.add(matriz[i][j]);
			}
		}

		panel.setBounds(25, 70, 550, 255);
		add(panel);
	}
}

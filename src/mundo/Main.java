/*
 * Main.java
 *
 * Created on November 24, 2007, 2:53 PM
 *
 * @author Bob Babilon
 */
package mundo;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Bob Babilon
 */
public class Main 
{
	public Main() 
	{
	}

	// single instance of a buffered reader to handle console input
	private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	/**
	 * Runs the simulator to test if a given string is in a given grammar.
	 * Input file must have grammar in the CNF.
	 */
	public static void SimulateGrammar(String grammarPath) 
	{	
		if(grammarPath.trim().equals(""))
		{
			grammarPath = "chomsky.txt";
		}

		if(new File(grammarPath).exists() == false)
		{
			System.out.println("Unable to find \"" + grammarPath + "\"!");
			System.out.println("Verify that it exists before continuing!");
			System.out.println();
			return;
		}

		// Thus far this will print all stuff out. Might be able to use
		// and if not, will have to modify it slightly...
		new Cyk(grammarPath).checkGrammar();

	}
}




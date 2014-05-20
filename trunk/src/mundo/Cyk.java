/**
 * Class CYK algorithm: a dynamic programming algorithm developed by
 * Cocke-Younger-Kasami to determine membership of a string in a
 * given context-free grammar and, if it is a member, how it can be generated.
 *
 * @author Connie Campbell
 * @version March 12, 2004
 *
 * Modifications made by Bob Babilon
 * November 25, 2007
 * Original source code can be found at:
 * http://www.cs.montana.edu/~campbell/cs515/cyk/output/
 */
/**
 *
 *  More information can be found in the text book on page 171.
 *
 *  Or from Wikipedia:
 *      http://en.wikipedia.org/wiki/CYK_algorithm
 *
 *  Pseudo code copied from Wikipedia link (see above)
 *      Let the input string consist of n letters, a1 ... an.
 *      Let the grammar contain r terminal and nonterminal symbols R1 ... Rr.
 *      This grammar contains the subset Rs which is the set of start symbols.
 *
 *      // r is array for each terminal and nonterminal
 *      // This program uses a string for r
 *      Let P[n,n,r] be an array of booleans. Initialize all elements of P to false.
 *      For each i = 1 to n
 *              For each unit production Rj -> ai, set P[i,1,j] = true.
 *
 *      // spanning over entire input string? skipping first one?
 *      For each i = 2 to n -- Length of span
 *      // travel along diagnol
 *        For each j = 1 to n-i+1 -- Start of span
 *              For each k = 1 to i-1 -- Partition of span
 *                      For each production RA -> RB RC (A -> B C)
 *                              If P[j,k,B] and P[j+k,i-k,C] then set P[j,i,A] = true
 *
 *      // If first column, bottom row has the start symbol then...
 *      If any of P[1,n,x] is true (x is iterated over the set s, where s are all the indices for Rs)
 *        Then string is member of language
 *        Else string is not member of language
 */

package mundo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Cyk
{
	Rule rule;
	public String vTable[][];

	public Cyk(String grammar)
	{
		rule = new Rule(grammar);
	}

	/**
	 * checkGrammar calls for the input to be read, initializes the table
	 * and fills the table dynamically.  The final answer is placed in
	 * V[0][n] box of the Matrix V.  The final table is then printed.
	 */
	public void checkGrammar()
	{
		rule.readInput(true);

		// makes the double 2D array that holds the input string on the "step edges" (diagnoals)
		vTable = new String[rule.input.size()][rule.input.size()];

		// initialize table
		for (int i = 0; i < rule.input.size(); i++)
		{
			for (int j = 0; j < rule.input.size(); j++)
			{
				vTable[i][j] = "";    // "false"
			}
		}

		// insert the first line (into the V matrix)
		// This step checks if any of the rules create
		// the ith letter from the input string (rule.input)
		// If so, the production name (value.get(0)) is added
		// to the table in the first row and which ever column
		// this is working on (determined by i)
		for (int i = 0; i < rule.input.size(); i++)
		{
			for (int j = 0; j < rule.rules.size(); j++)
			{
				ArrayList value = new ArrayList();

				value = (ArrayList) rule.rules.get(j);

				if (rule.input.get(i).equals(value.get(1)))
				{
					vTable[i][0] = (String) vTable[i][0] + "{" + value.get(0) + "}";
				}
			}
		}

		// compute Vij
		// Length of span
		for (int row = 1; row < rule.input.size(); row++)
		{
			// Start of span
			for (int col = 0; col < rule.input.size() - row; col++)
			{
				// Partition of span
				for (int k = 0; k < row; k++)
				{
					// For each production A -> BC
					for (int m = 0; m < rule.rules.size(); m++)
					{
						// get all production rules for each "root production"
						// ie if S -> AA|BB|a this will get the ArrayList that holds
						// [S A A] or [S B B] or [S a], depending on m
						ArrayList prodRules = new ArrayList();

						prodRules = (ArrayList) rule.rules.get(m);

						// only come in here if the rule goes to nonterminals
						if (prodRules.size() > 2)
						{
							// check if either of the boxes is null - they shouldn't be
							if ((!vTable[col + k + 1][row - k - 1].equals("")) && (!vTable[col][k].equals("")))
							{
								// My method works a heck of a lot better (readability at the very least)
								// and should not have any limitations (besides perhaps CFG not in GNF?)
								// For each production A -> BC
								// If P[j,k,B] and P[j+k,i-k,C] then set P[j,i,A] = true
								if ((vTable[col][k].indexOf((String) prodRules.get(1)) != -1)
										&& (vTable[col + k + 1][row - k - 1].indexOf((String)prodRules.get(2)) != -1))
								{
									// add this nonterminal if it is not already in the box
									checkDuplicates(col, row, prodRules);
								}
							}// end if
						}
					}
				}// end k loop
			}// end i loop
		}// end j loop

		printV();
		isInGrammar();

	}    // end checkGrammar

	/**
	 * checkDuplicates scans through the box we are looking at and sees
	 * if the nonterminal we are about to add is already in the box.
	 * It then adds the value if not already present.  If I had used a
	 * TreeSet instead of a standard 2D array to manage this, I would
	 * have no need to do this as it does not allow duplicates.
	 * However, since the output is in the form of a 2D array, it seemed
	 * more natural to use a 2D array and write this small procedure.
	 *
	 * @param first is an integer that holds the i value of the table
	 * @param second is an integer that holds the j value of the table
	 * @param value1 is an ArrayList of the productions that are being checked
	 */
	private void checkDuplicates(int first, int second, ArrayList value1)
	{
		if (vTable[first][second].length() > 0)
		{
			boolean present = false;

			// scan what's in the table already
			for (int z = 0; z < vTable[first][second].length(); z++)
			{
				if (vTable[first][second].indexOf((String) value1.get(0)) != -1)
				{
					present = true;
				}
			}

			if (!present)
			{
				// there is no duplication, therefore put everything back in the
				// table as well as the new nonterminal
				vTable[first][second] = vTable[first][second] + "{" + (String) value1.get(0) + "}";
			}
		}
		else
		{
			vTable[first][second] = "{" + (String) value1.get(0) + "}";
		}
	}// end checkDuplicates

	// printV prints the V Matrix
	private void printV()
	{

		//		System.out.println();
		System.out.println("=====  V Matrix  =====");

		try
		{	        
			BufferedWriter wr = new BufferedWriter(new FileWriter("./data/respuesta.txt"));

			for (int k = 0; k < rule.input.size(); k++)
			{
				for (int i = 0; i < rule.input.size(); i++)
				{
					//if ((vTable[i][k].length() == 0) && (i + k < rule.input.size()))

					String matrizValue = vTable[i][k];
					if ((vTable[i][k].length() == 0) || vTable[i][k].equals(""))
					{
						vTable[i][k] = "--";
					}

					if(vTable[k][i].equals(""))
					{
						vTable[k][i] = "--";
					}
					System.out.print(vTable[k][i] + "%");
					wr.write(vTable[k][i] + "%");
				}
				System.out.println();
				wr.write("\n");
			}

			boolean is = isInGrammar();
			wr.write("&"+"\n");
			wr.write(is+"");
			wr.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("======================");
	}// end printV

	// isInGrammar checks to see if the given input is in
	// the grammar L(G)
	// and the conclusion
	// of whether or not the string is a member of L(G)
	private boolean isInGrammar()
	{
		System.out.println();

		// checks the lower left hand corner of the table for the start symbol "S"
		// if it is in here, the string is in L(G), otherwise it is not
		if (vTable[0][rule.input.size() - 1].indexOf("S") == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}// end class

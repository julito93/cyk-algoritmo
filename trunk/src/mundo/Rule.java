/**
 * Your program should take input using the format below, print
 * out the trangular array of sets, and determine whether or
 * not the string is in the language defined by the grammar.
 *
 * Grammar:
 * S->AB|BC
 * A->BA|a
 * B->CC|b
 * C->AB|a
 *
 * Input String:  baaba
 *
 * V Matrix:
 *     B    A,C    A,C      B    A,C
 *   A,S      B    C,S    A,S
 *   nil      B      B
 *   nil  A,C,S
 * A,C,S
 *
 * baaba is in L(G)
 *
 * Assume the following:
 *   Non-terminals are upper case letters
 *   Terminals are lower case letters or digits.
 *   Non-terminals only appear once on the LHS.
 *   RHS can have any non-zero number of non-terminal pairs and
 *     terminals, in any order.
 *   No RHS will contain a pair of duplicate values.
 *   S will always be the start symbol.
 *
 * @author Connie Campbell
 * @version March 12, 2004
 *
 * Modifications made by Bob Babilon
 * November 25, 2007
 * Original source code found at:
 * http://www.cs.montana.edu/~campbell/cs515/cyk/output/
 *
 */

package mundo;
import java.io.*;
import java.util.*;

public class Rule
{
	// holds the productions with each ArrayList holding possible outputs
	// S -> AA|BC|a
	// S would be stored into the rules(0) then at rules(0) is another
	// ArrayList that would hold AA, BC, and a
	public ArrayList<ArrayList<String>> rules;                  //for ArrayList of ArrayLists
	public ArrayList<String> input;                  //holding the input string
        
	private String grammar; // file name holding grammar
	public Rule(String grammar)
	{
		this.grammar = grammar;
		rules = new ArrayList<ArrayList<String>>();
		input = new ArrayList<String>();
	}
	
	/**
	 *
	 * Java does not have a copy method for ArrayList?
	 *
	 * @param dest is destination ArrayList
	 * @param src is source ArrayList
	 *
	 * Contents from src will be copied into dest
	 */
	public static void CopyArrayList(ArrayList<String> dest, ArrayList<String> src)
	{
		for(int i = 0; i < src.size(); i++)
		{
			dest.add(src.get(i));
		}
	}
	
	/**
	 * Read the input from a file and put it in the various ArrayLists
	 * for processing by the CYK algorithm
	 */
	public void readInput(boolean printOutput)
	{
		try
		{
			ArrayList<String> inputArray = new ArrayList<String>();
			File inputFile = new File(this.grammar);
			FileReader in = new FileReader(inputFile);
			
			int c;
			int counter = 0;
			//read to end of file
			while ((c = in.read()) != -1)
			{
				//this deletes the word Productions:
				if (counter > 12)
				{
					// deletes the cr, -, >
					// leaves newline for later
					if (!(c == '\r' || c == '-' || c == '>'))
					{
						inputArray.add(Character.toString((char)c));
					}
				}
				counter++;
			}
			in.close();
			
			int location = inputArray.lastIndexOf(":");			
			//push the input string into it's own ArrayList
			for (int i = location + 1; i < inputArray.size(); i++)
			{
				if(Character.isLetter(inputArray.get(i).charAt(0)))
				{
					input.add((String)inputArray.get(i));
				}
			}

			//remove the word string: and string input
			for (int i = inputArray.size() - 1; i > location - 7; i--)
			{
				inputArray.remove(i);
			}
			
			//determine the location of carriage returns and pipes
			String newline = new String("\n");
			String pipe = new String("|");
			counter = 0;
			
			//remove the first newline before counting
			if(Character.isLetter(inputArray.get(0).charAt(0)) == false)
			{
				inputArray.remove(0);
			}
			
			//count the number of production rules
			for (int i = 0; i < inputArray.size(); i++)
			{
				// This requires a newline at the end of the file
				// otherwise the last production will be ignored.
				if (inputArray.get(i).equals(newline))
				{
					counter++;
				}
			}
			
			// debug
			//System.out.println("The input grammar is: ");
			//System.out.println(inputArray.toString());
			
			ArrayList<String> tempList = new ArrayList<String>();
			int j = 0;
			for(int i = 0; i < counter; i++)
			{
				while(!inputArray.get(j).equals(newline))
				{
					if(!inputArray.get(j).equals(pipe))
					{
						String temp = (String)inputArray.get(j);
						if(temp.equals("["))
						{
							temp = "";
							while(!inputArray.get(++j).equals("]"))
							{
								temp += inputArray.get(j);
							}
							tempList.add(temp);
							j++; // move past ]
						}
						else // single letter production
						{
							tempList.add(temp);
							j++;
						}
					}
					else
					{
						// save this production
						ArrayList<String> temp = new ArrayList<String>();
						CopyArrayList(temp, tempList);
						rules.add(temp);
						tempList.clear();
						
						// add production name, this is a pipe
						// so another prod with same name is expected
						//tempList.add((String)((ArrayList<String>)rules.get(rules.size() - 1)).get(0)); 
						
						ArrayList rs = rules.get(rules.size() - 1); 
						tempList.add((String)(rs.get(0)));
						
						j += 1; // moving past the pipe
					}
				}// while loop
				
				// save the current array list
				if(!tempList.isEmpty())
				{
					ArrayList<String> temp = new ArrayList<String>();
					CopyArrayList(temp, tempList);
					rules.add(temp);
					tempList.clear();
				}
				
				//move past the newline but not past array end
				if (inputArray.get(j).equals(newline) && (j != inputArray.size() - 1))
				{
					j++;
				}
			}// for loop
			
			if(printOutput)
			{
				printR(rules);
				printInput();
			}
			
		}//end try
		catch (FileNotFoundException e)
		{
			System.out.println("File not found" + e);
		}
		catch (IOException e)
		{
			System.out.println("Input output error" + e);
		}
	}//end readInput
	
	// make public so Cyk can call but converter does not have to see this?
	private void printInput()
	{
		//print input string
		System.out.println("======  Input  ======");
		for (int i = 0; i < input.size(); i++)
		{
			System.out.print(input.get(i) + " ");
		}
		System.out.println();
		System.out.println("=====================");
		//System.out.println();
	}
	
	/**
	 * Prints the production rules
	 * @param R 2D ArrayList that holds the productions
	 */
	private void printR(ArrayList R)
	{
		System.out.println("=====  Grammar  =====");
		for (int i = 0; i < R.size(); i++)
		{
			ArrayList value = new ArrayList();
			value = (ArrayList)R.get(i);
			for (int j = 0; j < value.size(); j++)
			{
				System.out.print(value.get(j) + "  ");
			}
			System.out.println();
		}
		System.out.println("=====================");
		System.out.println();
	}//end printR
}//end class

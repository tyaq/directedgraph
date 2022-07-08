/*
 * Test Strings: 
 * 1 2 3; 1 3 3; 2 3 2; 2 4 3; 3 5 2; 4 5 4; 5 6 3; 4 6 2;
 * Flow = 5
 */

package directedGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DGTest {
	private static String save;
	private static InputStreamReader isr = new InputStreamReader(System.in);
	private static BufferedReader userInput = new BufferedReader(isr);
	
	public static void main(String[] args) {
		System.out.println("Give the path of a file or type in your data.");
		String inputFilePath;
		
		try {
			inputFilePath = userInput.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			inputFilePath = "";
		}
		
		String input = null;	//What is passed in for cleaning
		
		input = fileImport(inputFilePath, input);
		
		String[][] edges = inputCleaner(input);
		
		/* System.out.println(Arrays.deepToString(edges)); */
		
		/* Graph creation */
		Digraph g = new Digraph();
		
		/* Add edges (Could be its own method) */
		for (int i = 0; i < edges.length; i++) {
			if (edges[i].length >= 3) {
				g.addEdge(edges[i][0], edges[i][1], edges[i][2]);
			}
		}
		
		System.out.println(g);
		
		/* System.out.println(g.kruskal().edges()); */
		
		System.out.println("Max Flow: " + g.maxFlow(1, 4));
		
		System.out.println("MinSpanTree: " + g.kruskal().edges());
		
		System.out.println("Shortest Path from 1 to 4: " + g.getPath(1, 4));
		
	}
	
	
	
	public static String fileImport(String inputFilePath, String output){
		
		/* Checks if file exists if not assume Data is inputed */
				File file = new File(inputFilePath);
		
				if (file.exists() && file.canRead()) {
					FileReader fr = null;
					try {
						fr = new FileReader(inputFilePath);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					userInput = new BufferedReader(fr);
					save += "Input:\tFrom " + inputFilePath + "\n";
					String line = null;
					try {
						line = userInput.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					while (line !=null){
						output += line + " ";
						try {
							line = userInput.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}					
					}
					save += "\t" + output + "\n";
					userInput = new BufferedReader(isr);
					
			/* Assumes Data is inputed */
				} else { 
					output = inputFilePath;
					save += "Input:\tEntered Data\n" + "\t" + output + "\n";
					}
				return output;
	 }
		
	
	public static String[][] inputCleaner(String input) {
		
		/* Terminators are used to find number sets */
				String[] connections = input.split("[^0-9\\s]+");
				String [][] edges = new String[connections.length][];
				
				for (int i = 0; i < connections.length; i++) {   /* spaces are used to find numbers and their input is cleaned further */
					String edge = connections[i];
					
					/* System.out.println("This is a numberSet before split " + edge); */
					
					/* Closes Program if no input is entered */
					if (edge.isEmpty()) {
						
						/* System.out.println("I just read an empty set so there no statistics."); */
					} else {    /* Unnecessary whitespace is managed */
						edge = edge.replaceAll("\\s+"," ");
						if (edge.charAt(edge.length()-1) == ' '){
							edge = edge.substring(0, edge.length() - 1);
						}
						if (edge.charAt(0) == ' '){
							edge = edge.substring(1);
						}
						
					/* Separates numbers in a set */
						edges[i] = edge.split("\\s+");
						}
				}
				return edges;		
	}
}

//1 2 3; 2 3 4; 8 9 1; 8 2 2;


package directedGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			inputFilePath ="";
		}
		
		String input = null;//What is passed in for cleaning
		
		input = fileImport(inputFilePath,input);
		
		String[][] edges = inputCleaner(input);
		
		System.out.println(Arrays.deepToString(edges));
		
		
		//Graph creation
		Digraph g = new Digraph();
		
		//Add edges (Could be its own method)
		for (int i=0;i<edges.length;i++) {
			if (edges[i].length>=3){
				g.addEdge(edges[i][0], edges[i][1], edges[i][2]);
			}//End if
		}//End for
		
		System.out.println(g);
		
		ArrayList<Edge> path =new ArrayList<Edge>();
		System.out.println(g.getPath(g.vertices().get("1"), g.vertices().get("6"), path));
		
	}//End Main method
	
	
	
	public static String fileImport(String inputFilePath, String output){
		//Checks if file exists if not assume Data is inputed	
				File file = new File(inputFilePath);
				if (file.exists() && file.canRead()) {
					FileReader fr = null;
					try {
						fr = new FileReader(inputFilePath);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					userInput = new BufferedReader(fr);
					save += "Input:\tFrom " + inputFilePath + "\n";
					String line = null;
					try {
						line = userInput.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while(line !=null){
						output+=line + " ";
						try {
							line=userInput.readLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}					
					}
					save += "\t" + output + "\n";
					userInput = new BufferedReader(isr);
			//Assumes Data is inputed
				} else { 
					output=inputFilePath;
					save += "Input:\tEntered Data\n" + "\t" + output + "\n";
					} //close else
				return output;
	}
		
	
	public static String[][] inputCleaner(String input){
		//Terminators are used to find number sets
				String[] connections = input.split("[^0-9\\s]+");
				String [][] edges =new String[connections.length][];
				
				for (int i = 0;i<connections.length;i++) {
				//spaces are used to find numbers and their input is cleaned further	
					String edge =connections[i];
					System.out.println("This is a numberSet before split " + edge);
					
				//Closes Program if no input is entered
					if (edge.isEmpty()) {
						System.out.println("I just read an empty set so there no statistics.");
					}//Close if
					
					else {
					//Unnecessary whitespace is managed
						edge = edge.replaceAll("\\s+"," ");
						if (edge.charAt(edge.length()-1)==' '){
							edge= edge.substring(0,edge.length()-1);
						}//Close if
						
						if (edge.charAt(0)==' '){
							edge= edge.substring(1);
						}//Close if
					
					//Separates numbers in a set
						edges[i] = edge.split("\\s+");
						}//close else
				}//End for
				
				return edges;
				
	}//End inputCleaner method
}//End Test class

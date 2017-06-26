//Vladimir Ventura 6/26/2017

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class Practice1{
	
	public static void main(String[] args) throws Exception{
		/*BST justaprank = new BST(8,0);
		System.out.println("10");
		justaprank.add(7,0);
		System.out.println("1");
		justaprank.add(4,0);
		System.out.println("2");
		justaprank.add(10,0);
		System.out.println("3");
		justaprank.add(2,0);
		System.out.println("4");
		justaprank.add(6,0);
		System.out.println("5");
		justaprank.add(9,0);
		System.out.println("6");
		justaprank.add(11,0);
		System.out.println("7");
		justaprank.add(1,0);
		System.out.println("8");
		justaprank.add(3,0);
		System.out.println("9");
		justaprank.add(5,0);
		
		System.out.println("done");
		
		
		justaprank.getValues(justaprank.getRoot());
		*///works so far.
		System.out.println("Running Frequency Algorithm: \n\n");
		findMostFrequestInteger("input.txt");
		
	}
	
	
	public static void findMostFrequestInteger(String i) throws Exception{
		File inputFile = new File(i);
		try{
			Scanner input = new Scanner(inputFile);
			
			ArrayList<Integer> al = new ArrayList<Integer>();
			while (input.hasNextInt()) {
				al.add(input.nextInt());
			}
			System.out.println("Finding Most Frequest Integer in array: " + al.toString());
			/*methods to finding most freq integer:
			1) Dictionary to count and store values. Integer as key, frequency as count. Can easily be implemented with a BST.
			*/
			System.out.println("As a Dictionary using BST implementation:");
			BST dictfrequency = new BST();
			for (Integer k : al) {
				dictfrequency.addWithValues(k, 1);
			}
			dictfrequency.getValues(dictfrequency.getRoot());
			//2) either linear-sort using frequency values, or create a max-PQ and removeMax(); 
			//PQ is more algorithmically intensive, so let's do that.
			System.out.println("Building Heap from BST....");
			PQ freq = new PQ(dictfrequency);
			System.out.println("Heap complete. Highest frequency numnber is: " + freq.peek().getKey() + " with occurence of: " +
			freq.peek().getValue());
			
			
			
		} catch (FileNotFoundException e) { System.out.println(e.toString());}
	}
}
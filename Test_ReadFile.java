package group_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/* The way the file reading works is that the Scanner looks for a line of ("//")
which indicates the start of a card object.
The line after that contains the id: EX: 25
The line after that contains the card name: EX: Pikachu
The Line after that contains the attributes: EX: Pokemon,Electric,Basic,Stage 2
      - The attributes are separated by a "," which the method uses to split
        the String into a String[], which is then converted to an ArrayList
        to create the card object. 
*/



public class Test_ReadFile {

	public static void main(String[] args) throws FileNotFoundException {
		String PATH = "src/application/deck1.txt";
		readFile(PATH);
	}
	
	public static void readFile(String PATH) throws FileNotFoundException {
		CardManager manager = new CardManager();
		File file = new File(PATH);
		StringBuilder message = new StringBuilder();
		message.append("Cards Created:\n");
		message.append("  File: " + file.getName() + "\n");
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				String line = input.nextLine();
				int id = -1;
				String name = "";
				if (line.equals("//")) {
					id = Integer.parseInt(input.nextLine());
					name = input.nextLine();
					String[] arrayTemp = input.nextLine().split(",");
					ArrayList<String> atributes = new ArrayList();
					for(int i=0; i < arrayTemp.length; i++) {
						atributes.add(arrayTemp[i]);
					}
					CardObject temp = new CardObject(id, name, atributes);
					message.append(temp.toString() + "\n");
					manager.addCard(temp);
				}
			}
			input.close();
			System.out.println(message);
		}
		catch(FileNotFoundException e) {
			System.out.println("Error reading file");
		}
	}
}

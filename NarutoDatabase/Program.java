package NarutoDatabase.Processor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.PreparedStatement;
import NarutoDatabase.Logger.Output;

// This file doenst really need editing (besides connecting to the database), NarutoDatabase.java does the work
public class Program {

	public static void main(String[] args) throws Exception {
		NarutoDatabase database = new NarutoDatabase();
		database.connect("jdbc:sqlite:library.db"); // This may need to be commented out for the meantime

		runConsole(database);

		System.out.println("Finished Executing...");
	}

	public static void runConsole(CommandProcessor processor) {
		String input;

		Scanner console = new Scanner(System.in);

		while((input = console.nextLine()) != null ) {
			String[] tokens = input.split(" ");
			String command = tokens[0];
        	String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

			try{
				processor.processCommand(command, args);
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		console.close();
	}
}
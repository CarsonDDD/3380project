package NarutoDatabase;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.PreparedStatement;

// This file doenst really need editing (besides connecting to the database), NarutoDatabase.java does the work
public class Program {

	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC");
		NarutoDatabase database = new NarutoDatabase();
		database.connect("jdbc:sqlite:NarutoDatabase/Naruto.db");

		//database.test();
		System.out.println("Welcome to the NarutoDatabase (actual database!)");
		database.helpCommand();
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
				if(!processor.processCommand(command, args)) break;
			}
			catch(Exception e){
				e.printStackTrace();
			}

		}
		console.close();
	}
}
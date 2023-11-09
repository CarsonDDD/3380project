import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class Program {
	static Connection connection;

	public static void main(String[] args) throws Exception {
		//Database db = new Database("jdbc:sqlite:library.db");
		//runConsole(db);
		//System.out.println("Exiting...");

		Debug.close(); // Required to be called at end of program
	}

	public static void runConsole(Database db) {
		String input;

		Scanner console = new Scanner(System.in);
		


		while((input = console.nextLine()) != null ) {
			String[] args = input.split(" ");
			String command = args[0];
			
			// Logic


		}
		console.close();
	}
}
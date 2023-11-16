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

	// Used as a reference to the database connection to run querys on it.
	// Needed for prepared statment to avoid sql injection.
	static Connection connection; 
	static Database database;
	public static void main(String[] args) throws Exception {
		database = new Database();
		connection = database.connect("jdbc:sqlite:library.db");

		runConsole(database);
		System.out.println("Exiting...");

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

	public static void queryExample() throws SQLException{
		/*
		PreparedStatement statment = connection.prepareStatement(STRING sql);
		statment.setString(1, names[0].toLowerCase());
		statment.setString(2, names[1].toLowerCase());

		exectureQuery(statment, (resultSet) -> {PER RESULT OPERATION});
		*/
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM table WHERE thing = ?");
		statement.setString(1, "string");

		database.executeQuery(statement, (resultMeta) -> {
			//database.get
			//ColumnValuePair[] res = Database.getColumnValuesPair(resultMeta, new String[]{"name", "id", "age"}, false);

			resultMeta.getColumnType("test");
		});
	}	
}
package NarutoDatabase.Processor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class Program {

	// Used as a reference to the database connection to run querys on it.
	// Needed for prepared statment to avoid sql injection.
	static Connection connection;
	static NarutoDatabase database;

	public static void main(String[] args) throws Exception {
		database = new NarutoDatabase();
		//connection = database.connect("jdbc:sqlite:library.db");
		//runConsole(database);
		System.out.println("Exiting...");

		//Debug.close(); // Required to be called at end of program
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

	// Example
	public void exampleQuery() throws SQLException{
		String sql = "SELECT ? FROM table";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, "characterID");

		// Execute query
		// FOR EACH resultSet, we will print out the character id
		// resultSet is variable name, this variable is ALWAYS of type ResultSet, determined by paramter type "Consumer<ResultSet>"
		database.executeQuery(statement, (resultSet) -> {
			try {
				System.out.println(resultSet.getString("characterID"));
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

}
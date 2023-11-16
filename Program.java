import java.sql.Connection;
import java.sql.SQLException;
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
package NarutoDatabase.Processor;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import NarutoDatabase.Logger.Output;

public class NarutoDatabase extends Database implements CommandProcessor{
    // COMMANDS AND QUERIES GO IN THIS FILE
    @Override
    public boolean processCommand(String command, String[] args) throws Exception{
        switch (command) {
            case "test":
                exampleQuery();
                System.out.println("Hello!");
                return true;
            default:
                return false;
        }
    }

    // Example
    // 'connection' is from super class
    // 'this.executeQuery' is also from the super class
	public void exampleQuery() throws SQLException{
		String sql = "SELECT ? FROM table";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, "characterID");

		// Execute query
		// FOR EACH resultSet, we will print out the character id
		// resultSet is variable name, this variable is ALWAYS of type ResultSet, determined by paramter type "Consumer<ResultSet>"
		this.executeQuery(statement, (resultSet) -> {
			try {
				System.out.println(resultSet.getString("characterID"));
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

}

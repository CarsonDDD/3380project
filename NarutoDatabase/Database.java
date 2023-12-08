package NarutoDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.function.Consumer;// May need to remove

public class Database {

	public class ColumnValue{
		public String column;
		public Object value;

		public ColumnValue(String columnName, Object value){
			column = columnName;
			this.value = value;
		}
	}

	protected Connection connection;

	public Connection connect(String url){
		try {
			// create a connection to the database
			connection = DriverManager.getConnection(url);
		}
		catch (SQLException e) {
			e.printStackTrace(System.out);
		}
		return connection;
	}

	// Execute statment and use the operation on each entry in the result
	// This allows us to delegate work to other files and keep this Database class generic and reusable.
	public boolean executeQuery(PreparedStatement statment, Consumer<ResultSet> operation){
		boolean success = false;
		try{
			ResultSet resultSet = statment.executeQuery();

			// Check to see if resultset is not empty
			if(resultSet.isBeforeFirst()){
				success = true;
				// For every returned entry, run the operation with it
				// Normally a print, its up to the caller to determine what to do with this
				while (resultSet.next()) {
					operation.accept(resultSet);
				}
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		return success;
	}

	// Maybe pulbic? ResultSet being public is problematic
	// Should be using ResultSetMetaData, however this library is poorly made and that doesnt have all metadata.
	// Returns list of given columns with their values
	// This fuction is very specific on what it does, since from other classes being able to have access to a ResultSet only comes from the Consumer from exeQuery
	// This fuction may be useless in that case?!?!?!
	// Non static because dumb reasons
	public ColumnValue[] getColumnsValues(ResultSet rowSet, String[] columns, boolean showNullColumns){
		ArrayList<ColumnValue> output = new ArrayList<>();

		try{
			for (String column : columns) {
				Object value = rowSet.getObject(column);
				// We need to getObject from the meta data

				// Check if we should append if an attribute is null
				if(!(!showNullColumns && value == null)){
					output.add(new ColumnValue(column, value));
				}
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}

		return output.toArray(new ColumnValue[0]);// maybe to slow?
	}
}

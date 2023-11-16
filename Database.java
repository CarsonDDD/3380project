import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.function.Consumer;// May need to remove

import javafx.beans.binding.ObjectBinding;

class ColumnValuePair{
	public String column;
	public Object value;

	public ColumnValuePair(String columnName, Object value){
		column = columnName;
		this.value = value;
	}
}

class Database {
	private Connection connection;

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
	public boolean executeQuery(PreparedStatement statment, Consumer<ResultSetMetaData> operation){
		boolean success = false;
		try{
			ResultSet resultSet = statment.executeQuery();

			// Check to see if resultset is not empty
			if(resultSet.isBeforeFirst()){
				success = true;
				// For every returned entry, run the operation with it
				// Normally a print, its up to the caller to determine what to do with this
				while (resultSet.next()) {
					operation.accept(resultSet.getMetaData());
				}
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		return success;
	}

	// Maybe pulbic? ResultSet being public is problematic
	// Returns list of given columns with their values
	// This function may be broken. It is untested. Ill make it fix later
	public static ColumnValuePair[] getColumnValuesPair(ResultSetMetaData resultMeta, String[] columns, boolean showNullColumns){
		ArrayList<ColumnValuePair> output = new ArrayList<>();

		try{
			for (String column : columns) {
				//Object value = resultMeta.getObject(column);
				Object value = resultMeta.get
				// We need to getObject from the meta data

				// Check if we should append if an attribute is null
				if(!(!showNullColumns && value == null)){
					output.add(new ColumnValuePair(column, value));
				}
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}

		return output.toArray(new ColumnValuePair[0]);// maybe to slow?
	}
}

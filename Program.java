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
		Database db = new Database("jdbc:sqlite:library.db");
		runConsole(db);

		System.out.println("Exiting...");
	}

	public static void runConsole(Database db) {
		Scanner console = new Scanner(System.in);
		
		console.close();
	}

}
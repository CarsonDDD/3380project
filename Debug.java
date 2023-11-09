import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors

public class Debug {
	private static Debug instance;
	
	private final String fileName = "debug.txt";
	private boolean enabled = true;
	private FileWriter output;

	private Debug(){
		try {
			File file = new File(fileName);
			file.createNewFile();

			output = new FileWriter(file);
		}
		catch (Exception e) {
			System.err.println("Debugger: " + e.getMessage());
		}
	}

	public static Debug instance(){
		if(instance == null){
			instance = new Debug();
		}
		return instance;
	}

	// log to file to seperate things
	public static void log(String info){
		if(instance().enabled){
			try {
				instance.output.write(info + "\n");
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(){
		try {
			instance.output.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

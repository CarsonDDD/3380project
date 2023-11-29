package NarutoDatabase.Logger;
import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.HashMap;

public class Debug {
	private static Debug instance;

	private final String defaultFile = "debug.txt";
	private boolean enabled = true;
	//private FileWriter output;
	private HashMap<String, FileWriter> outputs;

	private Debug(){
		outputs = new HashMap<>();
		try {
			File file = new File(defaultFile);
			file.createNewFile();
			outputs.put(defaultFile, new FileWriter(file));
			//output = new FileWriter(file);
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
		/*if(instance().enabled){
			try {
				instance.output.write(info + "\n");
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}*/
		log(info, instance.defaultFile);
	}

	public static void log(String info, String file){
		if(instance().enabled){
			try {
				if(!instance.outputs.containsKey(file)){
					//create new file
					File newFile = new File(file);
					newFile.createNewFile();
					instance.outputs.put(file, new FileWriter(newFile));
				}
				instance.outputs.get(file).write(info + "\n");

			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}



	public static void close(){
		//instance.output.close();
		if(instance == null) return;

		instance.outputs.forEach( (file, writer) -> {
			try {
				writer.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}

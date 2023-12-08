package NarutoDatabase.Logger;
import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.HashMap;

public class Output {
	private static Output _instance;

	private final String defaultFile = "debug.txt";
	private boolean enabled = true;
	//private FileWriter output;
	private HashMap<String, FileWriter> outputs;

	private Output(){
		outputs = new HashMap<>();
		try {
			getOrCreateFileWriter(defaultFile);
		}
		catch (Exception e) {
			System.err.println("Debugger: " + e.getMessage());
		}
	}

	private FileWriter getOrCreateFileWriter(String fileName) throws IOException {
		FileWriter writer = outputs.get(fileName);
		if (writer == null) {
			File file = new File(fileName);
			file.createNewFile();
			writer = new FileWriter(file, true);
			outputs.put(fileName, writer);
		}
		return writer;
	}

	public static Output instance(){
		if(_instance == null){
			_instance = new Output();
		}
		return _instance;
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
		log(info, instance().defaultFile);
	}

	public static void log(String info, String file){
		if(instance().enabled){
			try {
				FileWriter w = instance().getOrCreateFileWriter(file);
				w.write(info + "\n");
				w.flush();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(){
		//instance.output.close();
		if(_instance == null) return;

		_instance.outputs.forEach( (file, writer) -> {
			try {
				writer.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}

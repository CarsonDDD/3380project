package NarutoDatabase.Processor;
public interface CommandProcessor {
    boolean processCommand(String command, String[] args) throws Exception;
}
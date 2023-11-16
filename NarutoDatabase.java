public class NarutoDatabase extends Database implements CommandProcessor{

    @Override
    public boolean processCommand(String command, String[] args) {
        switch (command) {
            case "test":
                System.out.println("Hello!");
                return true;
            default:
                return false;
        }
    }

}

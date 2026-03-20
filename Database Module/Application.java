import Classes.Parser;

import java.io.FileNotFoundException;

public class Application {
    static void main() throws FileNotFoundException {
        Parser commandParser = new Parser();
        System.out.println(commandParser.start(commandParser.getCommands()));
    }
}

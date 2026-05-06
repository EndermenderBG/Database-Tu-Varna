import Classes.Parser;

import java.io.FileNotFoundException;

public class Application {
    static void main() throws FileNotFoundException, InterruptedException {
        Parser commandParser = new Parser();
        while(true){
            System.out.println(commandParser.start(commandParser.getCommands()));
        }
    }
}

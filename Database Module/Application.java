import Classes.Parser;

import java.io.FileNotFoundException;

public class Application {
    /**
     * The Application class simply contains the main method.
     * <p>
     *  The main method consists of an infinite while loop which outputs into the console line depending on what is inputted into the Parser.
     * </p>
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    static void main() throws FileNotFoundException, InterruptedException {
        Parser commandParser = new Parser();
        while(true){
            System.out.println(commandParser.start(commandParser.getCommands()));
        }
    }
}

package Classes.Commands.FileCommands;

import Interfaces.Command;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

/**
 * The ExitCommand class turns off the application.
 */
public class ExitCommand implements Command {
    /**
     * The execute method within the ExitCommand notifies the user of it's successful exit if, it is indeed successful.
     * Otherwise it throws an error. It waits a little bit before exiting after sending the message.
     * @param commandLine
     * @return Notification if successful, throws an error otherwise.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder("Program successfully disabled");
        System.out.println(output);
        try {
            TimeUnit.SECONDS.sleep(1);
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error when exiting");
        }
        return null ;
    }
}

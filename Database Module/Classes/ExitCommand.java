package Classes;

import Interfaces.Command;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class ExitCommand implements Command {
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder("Program successfully disabled");
        System.out.println(output);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error when exiting");
        }
        System.exit(0);
        return null ;
    }
}

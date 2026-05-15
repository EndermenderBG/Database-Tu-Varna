package Interfaces;

import java.io.FileNotFoundException;


/**
 * The command interface contains a single execute method which all classes have.
 */
public interface Command {
    /**
     * The execute method takes user input, processes it and then returns a StringBuilder
     * @param commandLine
     * @return
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException;
}

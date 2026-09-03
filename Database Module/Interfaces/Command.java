package Interfaces;

import Classes.Structure.Database.Database;

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
    StringBuilder execute(Database database,String[] commandLine) throws FileNotFoundException, InterruptedException;
}

package Classes.Commands.DatabaseCommands;

import Classes.Commands.FileCommands.SaveCommand;
import Classes.Structure.Database.Database;
import Interfaces.Command;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The export command borrows on the save command functioning identically to the "save as" version of it.
 */

public class ExportCommand implements Command {
    /**
     * It uses the performSave method to write a new file and then save it. All based on user input.
     * @param commandLine
     * @return Notification on success, throws an error on failure.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length < 3) {
            return new StringBuilder("Error: Invalid arguments. Use: export <table name> <file name>");
        }

        String tableName = commandLine[1];
        String fileName = commandLine[2];

        StringBuilder output = SaveCommand.performSave(tableName, new File(fileName));

        if(output.toString().startsWith("Successfully saved")) {
            return new StringBuilder("Successfully exported table '" + tableName + "' to " + fileName);
        }

        return output;
    }
}
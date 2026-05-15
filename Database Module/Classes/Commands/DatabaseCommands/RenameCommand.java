package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Database.Table;
import Interfaces.Command;

import java.io.FileNotFoundException;

/**
 * The RenameCommand changes the name of a certain table given as an input by the user.
 */
public class RenameCommand implements Command {
    /**
     * Validates if the table exists and if the user has inputted both the correct old name and a new name. Then simply uses as setter method to apply the new name.
     * @param commandLine
     * @return Notification upon success, throws an error upon failure.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();
        if (commandLine.length < 3) {
            return output.append("Invalid arguments. Usage: rename <old name> <new name>");
        }

        String oldName = commandLine[1];
        String newName = commandLine[2];

        Database database = Database.getInstance();

        if (!database.checkDatabase(oldName)) {
            return output.append("Table ").append(oldName).append(" does not exist.");
        }

        if (database.checkDatabase(newName)) {
            return output.append("Error: A table with the name ").append(newName).append(" already exists. The new name must be unique.");
        }

        Table table = database.getTable(oldName);
        table.setName(newName);

        database.getTables().remove(oldName);
        database.addTable(table);

        output.append("Successfully renamed table ").append(oldName).append(" to ").append(newName).append(".");
        return output;
    }
}

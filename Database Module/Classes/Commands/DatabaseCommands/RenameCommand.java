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
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments for rename command. Expected 3, got " + commandLine.length + ".");
        }

        String oldTableName = commandLine[1];
        String newTableName = commandLine[2];

        Table table = database.getTable(database.getTableIndex(oldTableName));
        if (table == null) {
            throw new IllegalArgumentException("Table with name " + oldTableName + " not found.");
        }

        table.setName(newTableName);

        return new StringBuilder("Successfully renamed table ").append(oldTableName).append(" to ").append(newTableName).append(".");
    }
}

package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Database.Table;
import Interfaces.Command;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * The DescribeCommand outputs the columns and the type of field they input.
 */

public class DescribeCommand implements Command {
    /**
     * The execute method takes the table name as input.
     * Gets the keys from the map of columns in the first row of the table, and the inputs them after throwing them into a list for indexing.
     * @param commandLine
     * @return Returns a numbered list of the columns and their types.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 2) {
            throw new IllegalArgumentException("Invalid number of arguments for describe command. Expected 2, got " + commandLine.length + ".");
        }

        String tableName = commandLine[1];
        Table table = database.getTable(database.getTableIndex(tableName));

        if (table == null) {
            throw new IllegalArgumentException("Table with name " + tableName + " not found.");
        }

        if (table.getColumnCount() == 0) {
            return new StringBuilder("Table ").append(tableName).append(" has no columns.");
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < table.getColumnCount(); i++) {
            output.append(table.getColumnName(i))
                    .append(": ")
                    .append(table.getColumnType(i))
                    .append(System.lineSeparator());
        }

        return output;
    }
}

package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Database.Table;
import Interfaces.Command;

import java.io.FileNotFoundException;

/**
 * The ShowTablesCommand Outputs a list of all the tables in the database.
 */
public class ShowTablesCommand implements Command {
    /**
     * @param commandLine
     * @return Return an ordered list of the tables.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 1) {
            throw new IllegalArgumentException("Invalid number of arguments for showtables command. Expected 1, got " + commandLine.length + ".");
        }

        if (database.getTableCount() == 0) {
            return new StringBuilder("No tables found in the database.");
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < database.getTableCount(); i++) {
            Table table = database.getTable(i);
            output.append(table.getName()).append(System.lineSeparator());
        }

        return output;
    }
}

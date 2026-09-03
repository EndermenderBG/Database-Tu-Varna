package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Interfaces.Command;
import Interfaces.DataField;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The PrintCommand outputs the first 20 rows of a table.
 */
public class PrintCommand implements Command {
    private static int pageSize = 20;

    /**
     * The execute method within the PrintCommand class takes only the table name as input.
     * It validates to see if the table exists and calculates values for an unimplemented Pagination.
     * Finally it returns up to 20 rows of the chosen table.
     * @param commandLine
     * @return Up to 20 rows from the chosen table.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 2) {
            throw new IllegalArgumentException("Invalid number of arguments for print command. Expected 2, got " + commandLine.length + ".");
        }

        String tableName = commandLine[1];
        Table table = database.getTable(database.getTableIndex(tableName));

        if (table == null) {
            throw new IllegalArgumentException("Table with name " + tableName + " not found.");
        }

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < table.getColumnCount(); i++) {
            output.append(table.getColumnName(i));
            if (i < table.getColumnCount() - 1) {
                output.append(" | ");
            }
        }
        output.append(System.lineSeparator());

        for (int i = 0; i < table.getColumnCount(); i++) {
            output.append("------------");
        }
        output.append(System.lineSeparator());

        for (int i = 0; i < table.getRowCount(); i++) {
            Row row = table.getRow(i);
            for (int j = 0; j < table.getColumnCount(); j++) {
                output.append(row.getField(j).asString());
                if (j < table.getColumnCount() - 1) {
                    output.append(" | ");
                }
            }
            output.append(System.lineSeparator());
        }

        return output;
    }
}

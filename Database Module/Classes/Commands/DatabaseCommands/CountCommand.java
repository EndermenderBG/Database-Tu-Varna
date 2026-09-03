package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Interfaces.Command;
import Interfaces.DataField;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The CountCommand finds the number of rows within a table that contain a specific value.
 */

public class CountCommand implements Command {
    /**
     * The execute method within the CountCommand class takes a table name, column index and a value to find as input.
     * It validates input, then it searches through the table if the value exists. If it does, it increments a counter.
     * @param commandLine
     * @return The conditions and the count to say how many times it contains the value.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database,String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();

        if (commandLine.length < 4) {
            return output.append("Invalid arguments. Usage: count <table name> <search column n> <search value>");
        }

        String tableName = commandLine[1];
        int searchColIndex;

        try {
            searchColIndex = Integer.parseInt(commandLine[2]);
        } catch (NumberFormatException e) {
            return output.append("Column index must be an integer.");
        }

        String searchValue = commandLine[3];

        Database database = Database.getInstance();

        if (!database.checkDatabase(tableName)) {
            return output.append("Table ").append(tableName).append(" does not exist.");
        }

        Table table = database.getTable(tableName);

        if (table.getRows().isEmpty()) {
            return output.append("The table is empty. Found 0 rows matching the value '").append(searchValue).append("'.");
        }

        Row firstRow = table.getRows().values().iterator().next();
        List<String> columnKeys = new ArrayList<>(firstRow.getColumns().keySet());

        if (searchColIndex < 1 || searchColIndex > columnKeys.size()) {
            return output.append("Column index out of bounds.");
        }

        String searchColKey = columnKeys.get(searchColIndex - 1);
        int matchCount = 0;

        for (Row row : table.getRows().values()) {
            DataField searchField = row.getField(searchColKey);

            if (searchField != null && searchField.getAsString().equals(searchValue)) {
                matchCount++;
            }
        }

        output.append("Found ").append(matchCount).append(" row(s) in table ").append(tableName)
                .append(" where column ").append(searchColIndex).append(" equals '").append(searchValue).append("'.");

        return output;
    }
}

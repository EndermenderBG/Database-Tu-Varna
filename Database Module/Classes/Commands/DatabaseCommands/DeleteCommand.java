package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Interfaces.Command;
import Interfaces.DataField;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The DeleteCommand class allows the user to delete a row within the table with a specfic value.
 */

public class DeleteCommand implements Command {
    /**
     * The execute command within the DeleteCommand class takes an index of a column and a value as input.
     * If it finds the value in the given column, the row the value is found in is deleted.
     * If it does not find the column or the value it throws an error.
     * @param commandLine
     * @return Notification if successful, throws an error otherwise.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();

        if (commandLine.length < 4) {
            return output.append("Invalid arguments. Usage: delete <table name> <search column n> <search value>");
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
            return output.append("The table is empty, no deletions can be made.");
        }

        Row firstRow = table.getRows().values().iterator().next();
        List<String> columnKeys = new ArrayList<>(firstRow.getColumns().keySet());

        if (searchColIndex < 1 || searchColIndex > columnKeys.size()) {
            return output.append("Column index out of bounds.");
        }

        String searchColKey = columnKeys.get(searchColIndex - 1);
        int deletedCount = 0;

        Iterator<Map.Entry<String, Row>> iterator = table.getRows().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Row> entry = iterator.next();
            Row row = entry.getValue();
            DataField searchField = row.getField(searchColKey);

            if (searchField != null && searchField.getAsString().equals(searchValue)) {
                iterator.remove();
                deletedCount++;
            }
        }

        output.append("Successfully deleted ").append(deletedCount).append(" rows from table ").append(tableName).append(".");
        return output;
    }
}
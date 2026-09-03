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
 * The SelectCommand finds all rows within the table with a specific value and outputs it.
 */
public class SelectCommand implements Command {
    /**
     * Select takes as input a table name, a number of column and a value.
     * It validates if all the inputs are correct then looks if a specific row has the supposed value.
     * If it does, it outputs it.
     * @param commandLine
     * @return The conditions and rows that those conditions apply to.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database,String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length < 4) {
            return new StringBuilder("Error: Invalid arguments. Usage: select <column-n> <value> <table name>");
        }

        int columnIndex;
        try {
            columnIndex = Integer.parseInt(commandLine[1]) - 1;
        } catch (NumberFormatException e) {
            return new StringBuilder("Error: <column-n> must be a valid integer number representing the column index.");
        }

        String targetValue = commandLine[2];
        String tableName = commandLine[3];

        Database database = Database.getInstance();

        if (!database.checkDatabase(tableName)) {
            return new StringBuilder("Error: Table '" + tableName + "' does not exist. Call <showtables> to view active tables.");
        }

        Table table = database.getTable(tableName);
        Map<String, Row> rowsMap = table.getRows();

        if (rowsMap == null || rowsMap.isEmpty()) {
            return new StringBuilder("Table '" + tableName + "' is currently empty.");
        }

        StringBuilder output = new StringBuilder();
        output.append("\n=== Select Results for Table: ").append(tableName).append(" ===\n");
        output.append("Condition: Column ").append(columnIndex + 1).append(" == '").append(targetValue).append("'\n\n");

        Row firstRow = table.getRow("1");
        List<String> headers = new ArrayList<>();
        if (firstRow != null) {
            headers.addAll(firstRow.getColumns().keySet());
        }

        if (columnIndex < 0 || columnIndex >= headers.size()) {
            return new StringBuilder("Error: Column index " + (columnIndex + 1) + " is out of bounds. Table has " + headers.size() + " columns.");
        }

        String targetColumnName = headers.get(columnIndex);

        for (String header : headers) {
            output.append(String.format("%-20s", header));
        }
        output.append("\n");
        output.append("-".repeat(headers.size() * 20)).append("\n");

        boolean foundMatch = false;

        for (Row row : rowsMap.values()) {
            DataField targetField = row.getField(targetColumnName);

            if (targetField != null && targetField.getAsString().equals(targetValue)) {
                foundMatch = true;
                for (DataField field : row.getColumns().values()) {
                    output.append(String.format("%-20s", field.getAsString()));
                }
                output.append("\n");
            }
        }

        if (!foundMatch) {
            output.append("No rows matched the condition.\n");
        }

        output.append("=== End of Results ===");

        return output;
    }
}
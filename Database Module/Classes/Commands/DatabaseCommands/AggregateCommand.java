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
 * The AggregateCommand finds the Max, Min, Mul and Sum of 2 fields within a table.
 */
public class AggregateCommand implements Command {
    /**
     * The execute method within the AggregateCommand class takes a table name, 2 column indexes, 2 field values and a mathematical operation within those columns as input.
     * It validates input first. First by making sure the input exists within the tables, and second making sure the input can support mathematical operations.
     * After all that it calculates every operation. Finally it outputs the desired result based on which operation was chosen.
     * @param commandLine
     * @return The conditions and result of the mathematical operation.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 4) {
            throw new IllegalArgumentException("Invalid number of arguments for aggregate command. Expected 4, got " + commandLine.length + ".");
        }

        String tableName = commandLine[1];
        String columnName = commandLine[2];
        String operation = commandLine[3].toLowerCase();

        Table table = database.getTable(database.getTableIndex(tableName));
        if (table == null) {
            throw new IllegalArgumentException("Table with name " + tableName + " not found.");
        }

        int columnIndex = table.getColumnIndex(columnName);
        if (columnIndex == -1) {
            throw new IllegalArgumentException("Column with name " + columnName + " not found in table " + tableName + ".");
        }

        double result = 0;
        int count = 0;
        boolean first = true;

        for (int i = 0; i < table.getRowCount(); i++) {
            Row row = table.getRow(i);
            DataField field = row.getField(columnIndex);

            if (!field.isNull()) {
                double value;
                try {
                    value = Double.parseDouble(field.asString());
                } catch (NumberFormatException e) {
                    continue;
                }

                if (operation.equals("sum") || operation.equals("avg")) {
                    result += value;
                } else if (operation.equals("min")) {
                    if (first || value < result) result = value;
                } else if (operation.equals("max")) {
                    if (first || value > result) result = value;
                }

                first = false;
                count++;
            }
        }

        if (count == 0) {
            return new StringBuilder("No valid numeric values found to aggregate in column ").append(columnName).append(".");
        }

        if (operation.equals("avg")) {
            result = result / count;
        }

        return new StringBuilder("Aggregation result (").append(operation).append("): ").append(result);
    }
}
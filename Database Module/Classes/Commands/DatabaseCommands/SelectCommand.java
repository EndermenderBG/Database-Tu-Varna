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
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 4) {
            throw new IllegalArgumentException("Invalid number of arguments for select command. Expected 4, got " + commandLine.length + ".");
        }

        String tableName = commandLine[1];
        String columnName = commandLine[2];
        String value = commandLine[3];

        Table table = database.getTable(database.getTableIndex(tableName));
        if (table == null) {
            throw new IllegalArgumentException("Table with name " + tableName + " not found.");
        }

        int columnIndex = table.getColumnIndex(columnName);
        if (columnIndex == -1) {
            throw new IllegalArgumentException("Column with name " + columnName + " not found in table " + tableName + ".");
        }

        String columnType = table.getColumnType(columnIndex);
        DataField targetField = DataField.createField(columnType, value);

        StringBuilder output = new StringBuilder();
        int matchCount = 0;

        for (int i = 0; i < table.getRowCount(); i++) {
            Row row = table.getRow(i);
            DataField field = row.getField(columnIndex);
            if (field.compareTo(targetField) == 0) {
                output.append(row.toString()).append(System.lineSeparator());
                matchCount++;
            }
        }

        if (matchCount == 0) {
            return new StringBuilder("No matching records found in table ").append(tableName).append(".");
        }

        return output;
    }
}
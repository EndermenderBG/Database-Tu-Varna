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
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 4) {
            throw new IllegalArgumentException("Invalid number of arguments for count command. Expected 4, got " + commandLine.length + ".");
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

        int matchCount = 0;

        for (int i = 0; i < table.getRowCount(); i++) {
            Row row = table.getRow(i);
            DataField field = row.getField(columnIndex);

            if (field.compareTo(targetField) == 0) {
                matchCount++;
            }
        }

        return new StringBuilder("Counted ").append(matchCount).append(" row(s) matching the criteria in table ").append(tableName).append(".");
    }
}

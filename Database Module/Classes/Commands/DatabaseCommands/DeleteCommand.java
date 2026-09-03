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
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 4) {
            throw new IllegalArgumentException("Invalid number of arguments for delete command. Expected 4, got " + commandLine.length + ".");
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

        int deletedCount = 0;
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            Row row = table.getRow(i);
            DataField field = row.getField(columnIndex);
            if (field.compareTo(targetField) == 0) {
                table.deleteRow(i);
                deletedCount++;
            }
        }

        return new StringBuilder("Successfully deleted ").append(deletedCount).append(" row(s) from table ").append(tableName).append(".");
    }
}
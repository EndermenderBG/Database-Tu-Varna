package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Fields.NullField;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Interfaces.Command;
import Interfaces.DataField;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * THe AddColumnCommand adds a column to an already existing table and sets all values to null.
 */
public class AddColumnCommand implements Command {
    /**
     * The execute method within the AddColumnCommand class takes table name, column name and column typee as inputs.
     * It validates input then simply adds a new column to every row map within the table with a NullField as value.
     * @param commandLine
     * @return
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 4) {
            throw new IllegalArgumentException("Invalid number of arguments for addcolumn command. Expected 4, got " + commandLine.length + ".");
        }

        String tableName = commandLine[1];
        String columnName = commandLine[2];
        String columnType = commandLine[3];

        Table table = database.getTable(database.getTableIndex(tableName));
        if (table == null) {
            throw new IllegalArgumentException("Table with name " + tableName + " not found.");
        }

        table.addColumn(columnName, columnType);

        try {
            for (int i = 0; i < table.getRowCount(); i++) {
                Row row = table.getRow(i);
                row.addField(DataField.createField(columnType, "null"));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsupported data type provided: " + columnType);
        }

        return new StringBuilder("Successfully added column ").append(columnName).append(" of type ").append(columnType).append(" to table ").append(tableName).append(".");
    }
}

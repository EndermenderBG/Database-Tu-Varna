package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Classes.Structure.Fields.DoubleField;
import Classes.Structure.Fields.IntField;
import Classes.Structure.Fields.NullField;
import Classes.Structure.Fields.StringField;
import Interfaces.Command;
import Interfaces.DataField;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The InsertCommand allows the user to insert a row within the table.
 */
public class InsertCommand implements Command {
    /**
     * The execute method takes table name, rowID and new data for the row.
     * It validates if the table name exists and then uses the rest of the input to add a row with an incremental ID.
     * @param commandLine
     * @return Notification if successful, throws an error if there is faulty input.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length < 2) {
            throw new IllegalArgumentException("Invalid number of arguments for insert command.");
        }

        String tableName = commandLine[1];
        Table table = database.getTable(database.getTableIndex(tableName));

        if (table == null) {
            throw new IllegalArgumentException("Table with name " + tableName + " not found.");
        }

        Row newRow = new Row();

        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnType = table.getColumnType(i);
            String value = (i + 2 < commandLine.length) ? commandLine[i + 2] : "null";

            try {
                DataField field = DataField.createField(columnType, value);
                newRow.addField(field);
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to parse value '" + value + "' for column type " + columnType + ".");
            }
        }

        table.insertRow(newRow);

        return new StringBuilder("Successfully inserted 1 row into table ").append(tableName).append(".");
    }


}

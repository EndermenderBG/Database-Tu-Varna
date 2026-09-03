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
 * The UpdateCommand looks at a perticular field within the table and replaces it.
 */
public class UpdateCommand implements Command {
    /**
     * The execute command within the UpdateCommand class validates table then finds the perticular field based on user input.
     * After finding it, it overwrites it with another again using user input.
     * @param commandLine
     * @return Notification if successful, throws an error otherwise.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 6) {
            throw new IllegalArgumentException("Invalid number of arguments for update command. Expected 6, got " + commandLine.length + ".");
        }

        String tableName = commandLine[1];
        String targetColumnName = commandLine[2];
        String targetValue = commandLine[3];
        String conditionColumnName = commandLine[4];
        String conditionValue = commandLine[5];

        Table table = database.getTable(database.getTableIndex(tableName));
        if (table == null) {
            throw new IllegalArgumentException("Table with name " + tableName + " not found.");
        }

        int targetColumnIndex = table.getColumnIndex(targetColumnName);
        if (targetColumnIndex == -1) {
            throw new IllegalArgumentException("Column with name " + targetColumnName + " not found in table " + tableName + ".");
        }

        int conditionColumnIndex = table.getColumnIndex(conditionColumnName);
        if (conditionColumnIndex == -1) {
            throw new IllegalArgumentException("Column with name " + conditionColumnName + " not found in table " + tableName + ".");
        }

        String targetColumnType = table.getColumnType(targetColumnIndex);
        DataField newField = DataField.createField(targetColumnType, targetValue);

        String conditionColumnType = table.getColumnType(conditionColumnIndex);
        DataField matchField = DataField.createField(conditionColumnType, conditionValue);

        int updatedCount = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            Row row = table.getRow(i);
            DataField conditionField = row.getField(conditionColumnIndex);
            if (conditionField.compareTo(matchField) == 0) {
                row.setField(targetColumnIndex, newField);
                updatedCount++;
            }
        }

        return new StringBuilder("Successfully updated ").append(updatedCount).append(" row(s) in table ").append(tableName).append(".");
    }
}

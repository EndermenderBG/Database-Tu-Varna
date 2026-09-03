package Classes.Commands.DatabaseCommands;

import Classes.Commands.FileCommands.OpenCommand;
import Classes.Structure.Database.Database;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Classes.Structure.Fields.DoubleField;
import Classes.Structure.Fields.IntField;
import Classes.Structure.Fields.NullField;
import Classes.Structure.Fields.StringField;
import Interfaces.Command;
import Interfaces.DataField;

import java.io.*;
import java.util.*;

/**
 * The Import command takes the opened file as input and then inputs the data inside into the database.
 */

public class ImportCommand implements Command {
    /**
     * The execute method takes the information within the file, creates a new table,
     * creates rows within the new table, fills them up with fields based on the columns at the top of the file
     * and finally adds that table into the database.
     * @param commandLine
     * @return Notification if succcessful, throws error if not.
     * @throws FileNotFoundException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments for import command. Expected 3, got " + commandLine.length + ".");
        }

        String tableName = commandLine[1];
        String fileName = commandLine[2];

        Table table = database.getTable(database.getTableIndex(tableName));
        if (table == null) {
            throw new IllegalArgumentException("Table with name " + tableName + " not found.");
        }

        int importedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);
                Row newRow = new Row();

                for (int i = 0; i < table.getColumnCount(); i++) {
                    String columnType = table.getColumnType(i);
                    String value = (i < values.length) ? values[i].trim() : "null";

                    if (value.isEmpty()) {
                        value = "null";
                    }

                    try {
                        DataField field = DataField.createField(columnType, value);
                        newRow.addField(field);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Data format mismatch at row " + (importedCount + 1) + ".");
                    }
                }

                table.insertRow(newRow);
                importedCount++;
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file: " + fileName);
        }

        return new StringBuilder("Successfully imported ").append(importedCount).append(" row(s) into table ").append(tableName).append(".");
    }

}

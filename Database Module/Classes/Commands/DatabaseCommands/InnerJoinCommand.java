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
import java.util.UUID;

/**
 * InnerJoinCommand takes two tables and joins them based on matching values within 2 of their columns. It then creates a new table.
 */
public class InnerJoinCommand implements Command {
    /**
     * The execute command first validates the input, then it validates if the tables exist. After that it validates if the indexes for the columns are valid.
     * After that it goes and compares the values of the 2 chosen columns. If they match, a new row is created with additional columns from both tables.
     * Finally the new table is saved in the database.
     * @param commandLine
     * @return Notification containing the name of the new table if successful, throws an error otherwise.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 5) {
            throw new IllegalArgumentException("Invalid number of arguments for innerjoin command. Expected 5, got " + commandLine.length + ".");
        }

        String table1Name = commandLine[1];
        String column1Name = commandLine[2];
        String table2Name = commandLine[3];
        String column2Name = commandLine[4];

        Table table1 = database.getTable(database.getTableIndex(table1Name));
        if (table1 == null) {
            throw new IllegalArgumentException("Table with name " + table1Name + " not found.");
        }

        Table table2 = database.getTable(database.getTableIndex(table2Name));
        if (table2 == null) {
            throw new IllegalArgumentException("Table with name " + table2Name + " not found.");
        }

        int column1Index = table1.getColumnIndex(column1Name);
        if (column1Index == -1) {
            throw new IllegalArgumentException("Column with name " + column1Name + " not found in table " + table1Name + ".");
        }

        int column2Index = table2.getColumnIndex(column2Name);
        if (column2Index == -1) {
            throw new IllegalArgumentException("Column with name " + column2Name + " not found in table " + table2Name + ".");
        }

        StringBuilder output = new StringBuilder();
        int matchCount = 0;

        for (int i = 0; i < table1.getRowCount(); i++) {
            Row row1 = table1.getRow(i);
            DataField field1 = row1.getField(column1Index);

            for (int j = 0; j < table2.getRowCount(); j++) {
                Row row2 = table2.getRow(j);
                DataField field2 = row2.getField(column2Index);

                if (field1.compareTo(field2) == 0) {
                    output.append(row1.toString()).append(" | ").append(row2.toString()).append(System.lineSeparator());
                    matchCount++;
                }
            }
        }

        if (matchCount == 0) {
            return new StringBuilder("No matching records found for inner join.");
        }

        return output;
    }
}
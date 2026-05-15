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
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();

        if (commandLine.length < 5) {
            return output.append("Invalid arguments. Usage: innerjoin <table 1> <column n1> <table 2> <column n2>");
        }

        String table1Name = commandLine[1];
        String table2Name = commandLine[3];
        int col1Index;
        int col2Index;

        try {
            col1Index = Integer.parseInt(commandLine[2]);
            col2Index = Integer.parseInt(commandLine[4]);
        } catch (NumberFormatException e) {
            return output.append("Column indices must be integers.");
        }

        Database database = Database.getInstance();

        if (!database.checkDatabase(table1Name)) {
            return output.append("Table ").append(table1Name).append(" does not exist.");
        }
        if (!database.checkDatabase(table2Name)) {
            return output.append("Table ").append(table2Name).append(" does not exist.");
        }

        Table table1 = database.getTable(table1Name);
        Table table2 = database.getTable(table2Name);

        if (table1.getRows().isEmpty() || table2.getRows().isEmpty()) {
            return output.append("One or both tables are empty. Cannot perform Inner Join without a defined schema.");
        }

        Row firstRowT1 = table1.getRows().values().iterator().next();
        List<String> columnKeysT1 = new ArrayList<>(firstRowT1.getColumns().keySet());

        Row firstRowT2 = table2.getRows().values().iterator().next();
        List<String> columnKeysT2 = new ArrayList<>(firstRowT2.getColumns().keySet());

        if (col1Index < 1 || col1Index > columnKeysT1.size()) {
            return output.append("Column index out of bounds for table ").append(table1Name).append(".");
        }
        if (col2Index < 1 || col2Index > columnKeysT2.size()) {
            return output.append("Column index out of bounds for table ").append(table2Name).append(".");
        }

        String searchColKeyT1 = columnKeysT1.get(col1Index - 1);
        String searchColKeyT2 = columnKeysT2.get(col2Index - 1);

        String newTableName = table1Name + "_join_" + table2Name + "_" + UUID.randomUUID().toString().substring(0, 4);
        Table joinedTable = new Table(newTableName);

        int rowIdCounter = 1;

        for (Row r1 : table1.getRows().values()) {
            DataField field1 = r1.getField(searchColKeyT1);
            if (field1 == null || field1.getAsString().equals("NULL")) continue;

            for (Row r2 : table2.getRows().values()) {
                DataField field2 = r2.getField(searchColKeyT2);
                if (field2 == null || field2.getAsString().equals("NULL")) continue;

                if (field1.getAsString().equals(field2.getAsString())) {
                    Row combinedRow = new Row();

                    for (Map.Entry<String, DataField> entry : r1.getColumns().entrySet()) {
                        String newColName = table1Name + "_" + entry.getKey();
                        combinedRow.addField(newColName, entry.getValue());
                    }

                    for (Map.Entry<String, DataField> entry : r2.getColumns().entrySet()) {
                        String newColName = table2Name + "_" + entry.getKey();
                        combinedRow.addField(newColName, entry.getValue());
                    }

                    joinedTable.addRow(String.valueOf(rowIdCounter++), combinedRow);
                }
            }
        }

        database.addTable(joinedTable);

        output.append("Inner Join successful. Created new table: ").append(newTableName)
                .append(" with ").append(rowIdCounter - 1).append(" rows.");
        return output;
    }
}
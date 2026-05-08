package Classes;

import Interfaces.Command;
import Interfaces.DataField;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UpdateCommand implements Command {
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();

        if (commandLine.length < 6) {
            return output.append("Invalid arguments. Usage: update <table name> <search column n> <search value> <target column n> <target value>");
        }

        String tableName = commandLine[1];
        int searchColIndex;
        int targetColIndex;

        try {
            searchColIndex = Integer.parseInt(commandLine[2]);
            targetColIndex = Integer.parseInt(commandLine[4]);
        } catch (NumberFormatException e) {
            return output.append("Column indices must be integers.");
        }

        String searchValue = commandLine[3];
        String targetValue = commandLine[5];

        Database database = Database.getInstance();
        if (!database.checkDatabase(tableName)) {
            return output.append("Table ").append(tableName).append(" does not exist.");
        }

        Table table = database.getTable(tableName);

        Row firstRow = table.getRow("1");
        if (firstRow == null) {
            return output.append("The table is empty, no updates can be made.");
        }

        List<String> columnKeys = new ArrayList<>(firstRow.getColumns().keySet());
        if (searchColIndex < 1 || searchColIndex > columnKeys.size() || targetColIndex < 1 || targetColIndex > columnKeys.size()) {
            return output.append("Column index out of bounds.");
        }

        String searchColKey = columnKeys.get(searchColIndex - 1);
        String targetColKey = columnKeys.get(targetColIndex - 1);

        int updatedCount = 0;

        for (Row row : table.getRows().values()) {
            DataField searchField = row.getField(searchColKey);

            if (searchField != null && searchField.getAsString().equals(searchValue)) {
                DataField newField = createField(targetValue, targetColKey);
                row.addField(targetColKey, newField);
                updatedCount++;
            }
        }

        output.append("Successfully updated ").append(updatedCount).append(" rows in table ").append(tableName).append(".");
        return output;
    }

    private DataField createField(String string, String dataType) {
        if (string.equalsIgnoreCase("NULL")) {
            return new NullField();
        }

        String type = dataType.substring(dataType.indexOf("<") + 1, dataType.lastIndexOf(">"));
        if (type.equalsIgnoreCase("Int")) {
            return new IntField(Integer.parseInt(string));
        } else if (type.equalsIgnoreCase("Double")) {
            return new DoubleField(Double.parseDouble(string));
        } else if (type.equalsIgnoreCase("String")) {
            return new StringField(string);
        } else {
            throw new RuntimeException("Invalid datatype: " + type);
        }
    }
}

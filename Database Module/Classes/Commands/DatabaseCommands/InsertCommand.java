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

public class InsertCommand implements Command {
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();

        if (commandLine.length < 3) {
            return output.append("Invalid arguments. Usage: insert <table name> <value 1> <value 2> ... <value n>");
        }

        String tableName = commandLine[1];
        Database database = Database.getInstance();

        if (!database.checkDatabase(tableName)) {
            return output.append("Table ").append(tableName).append(" does not exist.");
        }

        Table table = database.getTable(tableName);

        if (table.getRows().isEmpty()) {
            return output.append("The table is empty and has no schema. Cannot insert into a schema-less table.");
        }

        Row referenceRow = table.getRows().values().iterator().next();
        List<String> columnKeys = new ArrayList<>(referenceRow.getColumns().keySet());

        int expectedValuesCount = columnKeys.size();
        int providedValuesCount = commandLine.length - 2;

        if (expectedValuesCount != providedValuesCount) {
            return output.append("Column count mismatch. Expected ").append(expectedValuesCount)
                    .append(" values, but got ").append(providedValuesCount).append(".");
        }

        Row newRow = new Row();

        try {
            for (int i = 0; i < expectedValuesCount; i++) {
                String columnKey = columnKeys.get(i);
                String valueStr = commandLine[i + 2];

                DataField newField = createField(valueStr, columnKey);
                newRow.addField(columnKey, newField);
            }
        } catch (Exception e) {
            return output.append("Error parsing values: ").append(e.getMessage());
        }

        int maxId = 0;
        for (String idStr : table.getRows().keySet()) {
            try {
                int id = Integer.parseInt(idStr);
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        String newRowId = String.valueOf(maxId + 1);

        table.addRow(newRowId, newRow);

        output.append("Successfully inserted 1 row into table ").append(tableName).append(" with ID ").append(newRowId).append(".");
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

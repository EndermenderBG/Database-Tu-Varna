package Classes;

import Interfaces.Command;
import java.io.FileNotFoundException;
import java.util.Map;

public class AddColumnCommand implements Command {
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();

        if (commandLine.length < 4) {
            return output.append("Invalid arguments. Usage: addcolumn <table name> <column name> <column type>");
        }

        String tableName = commandLine[1];
        String columnName = commandLine[2];
        String columnType = commandLine[3];

        Database database = Database.getInstance();
        if (!database.checkDatabase(tableName)) {
            return output.append("Table ").append(tableName).append(" does not exist.");
        }

        Table table = database.getTable(tableName);

        String fullColumnName = columnName + "<" + columnType + ">";

        for (Map.Entry<String, Row> entry : table.getRows().entrySet()) {
            entry.getValue().addField(fullColumnName, new NullField());
        }

        output.append("Successfully added column ").append(fullColumnName).append(" to table ").append(tableName);
        return output;
    }
}

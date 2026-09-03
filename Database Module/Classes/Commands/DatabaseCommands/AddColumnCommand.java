package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Fields.NullField;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Interfaces.Command;
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
    public StringBuilder execute(Database database,String[] commandLine) throws FileNotFoundException, InterruptedException {
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

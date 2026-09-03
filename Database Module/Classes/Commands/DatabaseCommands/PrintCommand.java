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

/**
 * The PrintCommand outputs the first 20 rows of a table.
 */
public class PrintCommand implements Command {
    private static int pageSize = 20;

    /**
     * The execute method within the PrintCommand class takes only the table name as input.
     * It validates to see if the table exists and calculates values for an unimplemented Pagination.
     * Finally it returns up to 20 rows of the chosen table.
     * @param commandLine
     * @return Up to 20 rows from the chosen table.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database,String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();
        Database database = Database.getInstance();

        if (commandLine.length < 2){
            return output.append("Error: Please write the table's name.");
        }

        String tableName = commandLine[1];

        if(!database.checkDatabase(tableName)){
            return output.append("This table does not exist. You can call the <showtables> command to see what tables have been imported.");
        }

        Table table = database.getTables().get(tableName);
        int pageIndex = 0;
        Map<String, Row> rows = table.getRows();

        if (rows.isEmpty()) {
            return output.append("Table '").append(tableName).append("' is currently empty.");
        }

        int totalPages = (int) Math.ceil((double) rows.size() / pageSize);

        output.append("\n--- Table: ").append(tableName)
                .append(" | Page: ").append(pageIndex + 1).append(" / ").append(totalPages).append(" ---\n");

        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, rows.size());

        List<Row> rowsList = new ArrayList<>(rows.values());

        for (int i = start; i < end; i++){
            Row row = rowsList.get(i);

            StringBuilder rowString = new StringBuilder();
            for (DataField field : row.getColumns().values()) {
                rowString.append(String.format("%-20s", field.getAsString()));
            }
            output.append(rowString.toString().trim()).append("\n");
        }
        return output;
    }
}

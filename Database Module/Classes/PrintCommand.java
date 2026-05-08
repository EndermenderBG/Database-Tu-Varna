package Classes;

import Interfaces.Command;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class PrintCommand implements Command {
    private static int pageSize = 10;
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();
        Database database = Database.getInstance();

        if (commandLine.length < 1){
            return output.append("Error: Please write the table's name.");
        }

        String tableName = commandLine[1];

        if(!database.checkDatabase(tableName)){
            return output.append("This table does not exist. You can call the <showtables> command to see what tables have been imported.");
        }

        Table table = database.getTables().get(tableName);
        int pageIndex = 0;
        Map<String,Row> rows = table.getRows();
        int totalPages = (int) Math.ceil((double) rows.size() / pageSize);
        output.append("\n--- Table: ").append(tableName)
                .append("Page: ").append(pageIndex + 1).append(totalPages).append("---\n");

        int start = pageIndex * pageSize;
        int end = Math.min(start + pageSize, rows.size());

        for (int i = start; i < end; i++){
            output.append(rows.get(i).toString()).append("\n");
        }
        return output;
    }
}

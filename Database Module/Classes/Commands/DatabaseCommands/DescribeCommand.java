package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Database.Table;
import Interfaces.Command;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DescribeCommand implements Command {
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        Database database = Database.getInstance();
        String tableName = commandLine[1];
        StringBuilder output;
        if(!database.checkDatabase(tableName)){
            output = new StringBuilder("This table does not exit. You can call the <showtables> command to see what tables have been imported.");
            return output;
        }
        Table chosenTable = database.getTables().get(tableName);
        ArrayList<String> columnList = new ArrayList<>(chosenTable.getRow("1").getColumns().keySet());
        output = new StringBuilder("The columns (attributes) of this table and their types are: ").append("\n");
        for (int i = 0; i < columnList.size(); i++) {
            String columnFull = columnList.get(i);
            String columnType = columnFull.substring(columnFull.indexOf("<")+1,columnFull.lastIndexOf(">"));
            String columnName = columnFull.substring(0,columnFull.indexOf("<"));
            output.append(columnName).append(" | ").append(columnType).append("\n");
        }

        return output;
    }
}

package Classes;

import Interfaces.Command;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowTablesCommand implements Command {
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        Database database = Database.getInstance();
        int num_of_tables = 1;

        StringBuilder output = new StringBuilder("The currently imported tables are: ").append('\n');
        for (String i : database.getTables().keySet()){
            output.append(num_of_tables).append(". ").append(i).append('\n');
            num_of_tables++;
        }

        return output;
    }
}

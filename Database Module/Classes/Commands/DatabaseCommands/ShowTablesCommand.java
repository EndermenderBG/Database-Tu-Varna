package Classes.Commands.DatabaseCommands;

import Classes.Structure.Database.Database;
import Interfaces.Command;

import java.io.FileNotFoundException;

/**
 * The ShowTablesCommand Outputs a list of all the tables in the database.
 */
public class ShowTablesCommand implements Command {
    /**
     * @param commandLine
     * @return Return an ordered list of the tables.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database,String[] commandLine) throws FileNotFoundException, InterruptedException {
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

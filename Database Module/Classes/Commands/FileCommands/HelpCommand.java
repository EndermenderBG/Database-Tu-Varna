package Classes.Commands.FileCommands;

import Classes.Structure.Database.Database;
import Interfaces.Command;

/**
 * The HelpCommand class outputs the user a list of all the commands and how to use them.
 */
public class HelpCommand implements Command {
    private final StringBuilder output = new StringBuilder("The following commands are supported: \n" +
            "open <file> - opens <file>\n" +
            "close - closes currently opened file\n" +
            "save - saves currently opened file\n" +
            "save as <file> - saves the currently open file in <file>\n" +
            "help - prints out the information pertaining to the commands\n" +
            "exit - exits the program\n" +
            "import <file name> - imports a new table from the specified file\n" +
            "showtables - shows a list of all imported tables\n" +
            "describe <name> - shows information about the types of columns of a given table\n" +
            "print <name> - shows all rows from a given table\n" +
            "export <name> <file name> - exports a table into a file\n" +
            "select <column-n> <value> <table name> - shows all rows containing a specific value in a given column\n" +
            "addcolumn <table name> <column name> <column type> - adds a new column to the specified table\n" +
            "update <table name> <search column n> <search value> <target column n> <target value> - updates the target column value for rows matching the search criteria\n" +
            "delete <table name> <search column n> <search value> - deletes rows matching the search criteria\n" +
            "insert <table name> <value 1> ... <value n> - inserts a new row into the table with the corresponding values\n" +
            "innerjoin <table 1> <column n1> <table 2> <column n2> - performs an inner join on two tables based on the specified columns\n" +
            "rename <old name> <new name> - renames a table\n" +
            "count <table name> <search column n> <search value> - counts the number of rows matching the search criteria\n" +
            "aggregate <table name> <search column n> <search value> <target column n> <operation> - performs an operation (sum, product, maximum, minimum) on a target column for matching rows");

    /**
     *
     * @param commandLine
     * @return The list of commands and instructions.
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) {
        return output;
    }
}

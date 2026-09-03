package Classes.Commands.FileCommands;

import Classes.Structure.Database.Database;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Interfaces.Command;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * The save command takes either a table or the currently opened file and then saves it in another file.
 */
public class SaveCommand implements Command {
    /**
     * If written as "save as" the command saves the table into another file.
     * If written just as "save" it saves it into the currently opened file.
     * @param commandLine
     * @return A notification back to the user based on whether their table was successfully saved. Otherwise throws an error.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database,String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length >= 4 && commandLine[1].equalsIgnoreCase("as")) {
            String tableName = commandLine[2];
            String fileName = commandLine[3];
            return performSave(tableName, new File(fileName));
        }
        else if (commandLine.length >= 2) {
            String tableName = commandLine[1];
            File openedFile = OpenCommand.getOpenedFile();

            if (openedFile == null) {
                return new StringBuilder("Error: No file is currently opened.");
            }
            return performSave(tableName, openedFile);
        } else {
            return new StringBuilder("Error: Invalid arguments. Use 'save <table name>' or 'save as <table name> <file name>'.");
        }
    }

    /**
     * The performSave method takes a table from the database based on user input and writes it down within a file using the PrintWriter class.
     * @param tableName
     * @param file
     * @return Notification if successful, throws an error otherwise.
     */
    public static StringBuilder performSave(String tableName, File file) {
        Database database = Database.getInstance();
        if (!database.checkDatabase(tableName)) {
            return new StringBuilder("Error: Table '" + tableName + "' does not exist.");
        }

        Table table = database.getTable(tableName);

        try (PrintWriter writer = new PrintWriter(file)) {
            Row firstRow = table.getRow("1");
            if (firstRow == null) {
                return new StringBuilder("Table is empty, nothing to save.");
            }

            int colCount = 0;
            for (String colName : firstRow.getColumns().keySet()) {
                writer.print(colName);
                colCount++;
                if (colCount < firstRow.getColumns().size()) {
                    writer.print(" ");
                }
            }
            writer.println();

            for (Map.Entry<String, Row> rowEntry : table.getRows().entrySet()) {
                Row row = rowEntry.getValue();
                int fieldCount = 0;

                for (Interfaces.DataField field : row.getColumns().values()) {
                    writer.print(field.getAsString());
                    fieldCount++;
                    if (fieldCount < row.getColumns().size()) {
                        writer.print(" ");
                    }
                }
                writer.println();
            }

            return new StringBuilder("Successfully saved " + file.getName());
        } catch (FileNotFoundException e) {
            return new StringBuilder("Error: Could not save to file " + file.getName());
        }
    }
}
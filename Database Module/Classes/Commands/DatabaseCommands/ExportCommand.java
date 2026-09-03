package Classes.Commands.DatabaseCommands;

import Classes.Commands.FileCommands.SaveCommand;
import Classes.Structure.Database.Database;
import Classes.Structure.*;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Interfaces.Command;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * The export command borrows on the save command functioning identically to the "save as" version of it.
 */

public class ExportCommand implements Command {
    /**
     * It uses the performSave method to write a new file and then save it. All based on user input.
     * @param commandLine
     * @return Notification on success, throws an error on failure.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length != 3) {
            throw new IllegalArgumentException("Invalid number of arguments for export command. Expected 3, got " + commandLine.length + ".");
        }

        String tableName = commandLine[1];
        String fileName = commandLine[2];

        Table table = database.getTable(database.getTableIndex(tableName));
        if (table == null) {
            throw new IllegalArgumentException("Table with name " + tableName + " not found.");
        }

        File file = new File(fileName);

        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < table.getColumnCount(); i++) {
                writer.print(table.getColumnName(i));
                if (i < table.getColumnCount() - 1) {
                    writer.print(",");
                }
            }
            writer.println();

            for (int i = 0; i < table.getRowCount(); i++) {
                Row row = table.getRow(i);
                for (int j = 0; j < table.getColumnCount(); j++) {
                    writer.print(row.getField(j).asString());
                    if (j < table.getColumnCount() - 1) {
                        writer.print(",");
                    }
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not open or create file for export: " + fileName);
        }

        return new StringBuilder("Successfully exported table ").append(tableName).append(" to file ").append(fileName).append(".");
    }
}
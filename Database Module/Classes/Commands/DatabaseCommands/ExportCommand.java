package Classes.Commands.DatabaseCommands;

import Classes.Commands.FileCommands.SaveCommand;
import Interfaces.Command;
import java.io.File;
import java.io.FileNotFoundException;

public class ExportCommand implements Command {
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        if (commandLine.length < 3) {
            return new StringBuilder("Error: Invalid arguments. Use: export <table name> <file name>");
        }

        String tableName = commandLine[1];
        String fileName = commandLine[2];

        StringBuilder output = SaveCommand.performSave(tableName, new File(fileName));

        if(output.toString().startsWith("Successfully saved")) {
            return new StringBuilder("Successfully exported table '" + tableName + "' to " + fileName);
        }

        return output;
    }
}
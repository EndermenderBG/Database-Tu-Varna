package Classes;

import Interfaces.Command;

import java.io.File;
import java.io.FileNotFoundException;

public class CloseCommand implements Command {
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output;
        if(OpenCommand.getOpenedFile() == null){
            output = new StringBuilder("You have not opened any file yet.");
        }
        else {
            Database.databaseClear();
            OpenCommand.setOpenedFile(null);
            output = new StringBuilder("You have closed the currently opened file.");
        }
        return output;
    }
}

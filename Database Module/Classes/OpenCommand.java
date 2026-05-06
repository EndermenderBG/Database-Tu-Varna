package Classes;

import Interfaces.Command;

import java.io.File;
import java.io.FileNotFoundException;
public class OpenCommand implements Command {
    private static File openedFile = null;
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException {
        StringBuilder output;
        openedFile = new File(commandLine[1]);
        if(openedFile.exists()){
            output = new StringBuilder("You have successfully opened the file");
            Parser.setFileOpened(true);
            return output;
        }
        else{
            throw new FileNotFoundException("The file has not been found");
        }
    }

    public static File getOpenedFile() {
        return openedFile;
    }

    public static void setOpenedFile(File openedFile) {
        OpenCommand.openedFile = openedFile;
    }
}

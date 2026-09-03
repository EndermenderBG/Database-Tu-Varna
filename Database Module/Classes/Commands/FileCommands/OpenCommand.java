package Classes.Commands.FileCommands;


import Classes.Parser;
import Classes.Structure.Database.Database;
import Interfaces.Command;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * The OpenCommand opens a file and thus allows all the other commands of the application to be usable. Some exceptions who were usable beforehand do not apply.
 */
public class OpenCommand implements Command {
    private static File openedFile = null;

    /**
     * Checks if the file exists, if it does it saves it into a File object and sets the boolean which tracks this in the Parser to true.
     * @param commandLine
     * @return Notification if successful, throws an error otherwise.
     * @throws FileNotFoundException
     */
    @Override
    public StringBuilder execute(Database database, String[] commandLine) throws FileNotFoundException {
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

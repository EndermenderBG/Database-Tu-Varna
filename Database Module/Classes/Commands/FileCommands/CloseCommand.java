package Classes.Commands.FileCommands;

import Classes.Structure.Database.Database;
import Interfaces.Command;

import java.io.FileNotFoundException;

/**
 * The close command closes the file and deletes any information in the program.
 */
public class CloseCommand implements Command {
    /**
     * The execute method within the CloseCommand class simply validates if there's any file opened. If yes, it clears the database, if not it notifies the user.
     * @param commandLine
     * @return Notification if successful, throws an error otherwise.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    @Override
    public StringBuilder execute(Database database,String[] commandLine) throws FileNotFoundException, InterruptedException {
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

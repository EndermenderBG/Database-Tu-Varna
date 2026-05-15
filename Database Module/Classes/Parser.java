package Classes;

import Classes.Commands.DatabaseCommands.*;
import Classes.Commands.FileCommands.*;
import Interfaces.Command;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Parser class reads input, contains commands and then outputs whatever those commands return.
 * <p>
 *     The input is simply read through a scanner class in the start method.
 * </p>
 * <p>
 *     Commands are contained in a HashMap using composition
 * </p>
 * <p>
 *     After some simple validation the start function returns a StringBuilder which is then read in the Application class.
 * </p>
 */

public class Parser {
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * The Parser class contains all the Objects of the Command Interface. Each being it's own class. That is done through a Hashmap which gets filled up in the constructor.
     */

    public Parser(){
        commands.put("import", new ImportCommand());
        commands.put("help", new HelpCommand());
        commands.put("open", new OpenCommand());
        commands.put("exit", new ExitCommand());
        commands.put("close", new CloseCommand());
        commands.put("showtables", new ShowTablesCommand());
        commands.put("describe", new DescribeCommand());
        commands.put("print", new PrintCommand());
        commands.put("save", new SaveCommand());
        commands.put("export", new ExportCommand());
        commands.put("select", new SelectCommand());
        commands.put("addcolumn", new AddColumnCommand());
        commands.put("update", new UpdateCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("insert", new InsertCommand());
        commands.put("innerjoin", new InnerJoinCommand());
        commands.put("rename", new RenameCommand());
        commands.put("count", new CountCommand());
        commands.put("aggregate", new AggregateCommand());
    }
    private static boolean isFileOpened = false;
    private static boolean executingPrint = false;

    /**
     * Sets a boolean based on whether a file is opened or not.
     * @param fileOpened
     *
     */
    public static void setFileOpened(boolean fileOpened) {
        isFileOpened = fileOpened;
    }

    /**
     *
     * @return The hashmap of commands
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Simply validates if the input of the user is null or not.
     * @param inputCommand
     * @return if isn't null it returns true, otherwise false
     */
    public boolean validateCommand(Command inputCommand){
        if(inputCommand != null){
            //StringBuilder output = command.execute(parts);
            //System.out.println(output);
            return true;
        }
        else {
            //System.out.println("Unknown Command: " + commandName);
            return false;
        }
    }

//    public StringBuilder printPager() throws FileNotFoundException, InterruptedException {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder output = new StringBuilder();
//
//        String input = scanner.nextLine().trim();
//        String[] commandArr = input.split(" ");
//
//        if (commandArr[0] != "next" || commandArr[0] != "previous" || commandArr[0] != "exit"){
//            output.append("Invalid command. You must use the <exit> command to exit the page view.");
//            return output;
//        }
//
//        output = commands.get("print").execute(commandArr);
//        return output;
//    }

    /**
     * The start function takes the user input and based off of it returns the output of the commands.
     * <p>
     *     It uses an object of the scanner class to read from the console, it converts what it reads into a string, it splits it up then gets the appropriate command from the HashMap
     * </p>
     * <p>
     *     If the user has given a valid input and a file has been opened the Parser will reach into the map, and call the execute method of the appropriate command.
     * </p>
     * @param commands
     * @return If the input of the user is appripriate for the command they have chosen they will recieve a confirmation of the command's actions or another appropriate output in the form of a string.
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public StringBuilder start(Map<String, Command> commands) throws FileNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        StringBuilder output = new StringBuilder();
        while (true){
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            String commandName = parts[0].toLowerCase();

            Command command = commands.get(commandName);

            if (validateCommand(command)){
                if(isFileOpened) {
                    output = command.execute(parts);
                    return output;

                } else if (commandName.equals("open") || commandName.equals("close") || commandName.equals("help") || commandName.equals("exit")) {
                    output = command.execute(parts);
                    return output;
                }
                else {
                    output.append("A file must be opened before using any other command.");
                    return output;
                }
            }
            else {
                output.append("Unknown Command: ").append(commandName);
                return output;
            }
        }
    }
}

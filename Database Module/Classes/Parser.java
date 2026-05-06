package Classes;

import Interfaces.Command;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {
    private final Map<String, Command> commands = new HashMap<>();

    public Parser(){
        commands.put("import", new ImportCommand());
        commands.put("help", new HelpCommand());
        commands.put("open", new OpenCommand());
        commands.put("exit", new ExitCommand());
        commands.put("close", new CloseCommand());
        commands.put("showtables", new ShowTablesCommand());
        commands.put("describe", new DescribeCommand());
        commands.put("print", new PrintCommand());
    }
    private static boolean isFileOpened = false;

    public static void setFileOpened(boolean fileOpened) {
        isFileOpened = fileOpened;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

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
                } else if (commandName.equals("open") || commandName.equals("close") || commandName.equals("help")) {
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

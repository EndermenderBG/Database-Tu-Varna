package Classes;

import Interfaces.Command;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {
    private final Map<String, Command> commands = new HashMap<>();

    public Parser(){
        commands.put("open", new OpenCommand());
        commands.put("help", new HelpCommand());
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

    public StringBuilder start(Map<String, Command> commands) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        StringBuilder output = new StringBuilder();
        while (true){
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            String commandName = parts[0].toLowerCase();

            Command command = commands.get(commandName);

            if (validateCommand(command)){
                output = command.execute(parts);
                return output;
            }
            else {
                output.append("Unknown Command: ").append(commandName);
                return output;
            }
        }
    }
}

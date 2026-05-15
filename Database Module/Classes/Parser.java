package Classes;

import Classes.Commands.DatabaseCommands.*;
import Classes.Commands.FileCommands.*;
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

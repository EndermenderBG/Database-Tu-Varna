package Classes;

import Interfaces.Command;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    private final Map<String, Command> commands = new HashMap<>();

    public Parser(){
        commands.put("open", new OpenCommand());
        commands.put("help", new HelpCommand());
    }
}

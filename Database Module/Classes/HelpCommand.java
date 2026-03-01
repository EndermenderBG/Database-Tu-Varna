package Classes;

import Interfaces.Command;

public class HelpCommand implements Command {
    @Override
    public StringBuilder execute(String[] args) {
        return "";
    }
}

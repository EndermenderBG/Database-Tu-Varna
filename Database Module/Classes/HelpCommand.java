package Classes;

import Interfaces.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HelpCommand implements Command {
    private final StringBuilder output = new StringBuilder("The following commands are supported: \n" +
            "open <file> - opens <file> \n" +
            "close - closes currently opened file \n" +
            "save - saves currently opened file \n" +
            "help - prints out the information pertaining to the commands \n" +
            "exit - exits the program \n" +
            "import <file name> - TBA \n" +
            "showtables - TBA \n" +
            "describe <name> - TBA \n" +
            "print <name> - TBA \n" +
            "export <name> <file name> - TBA \n" +
            "select <column-n> <value> <table name> - TBA \n" +
            "addcolumn <table name> <column name> <column type> - TBA \n" +
            "update <table name> <search column n> <search value> <target column n> <target value> - TBA \n" +
            "delete <table name> <search column n> <search value> - TBA \n"+
            "innerjoin <table name> <column 1> ... <column n> - TBA \n" +
            "rename <old name> <new name> - TBA \n" +
            "count <table name> <search column n> <search value> - TBA \n" +
            "aggrigate <table name> <search column n> <search value> <target column n> <operation> - TBA");

    @Override
    public StringBuilder execute(String[] commandLine) {
        return output;
    }
}

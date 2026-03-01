package Classes;

import Interfaces.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OpenCommand implements Command {

    @Override
    public StringBuilder execute(String[] args) throws FileNotFoundException {
        StringBuilder output = new StringBuilder("");
        File openedFile = new File(args[0]);

        try (Scanner reader = new Scanner(openedFile)){
            while(reader.hasNextLine()){
                output.append(reader.nextLine());
            }
        } catch (FileNotFoundException e){
            output.append("File Error \n");
        }
        return output;
    }
}

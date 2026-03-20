package Interfaces;

import java.io.FileNotFoundException;

public interface Command {
    StringBuilder execute(String[] commandLine) throws FileNotFoundException;
}

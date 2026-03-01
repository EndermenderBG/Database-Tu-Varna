package Interfaces;

import java.io.FileNotFoundException;

public interface Command {
    StringBuilder execute(String[] args) throws FileNotFoundException;
}

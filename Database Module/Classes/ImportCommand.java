package Classes;

import Interfaces.Command;
import Interfaces.DataField;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class OpenCommand implements Command {

    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException {

        StringBuilder output = new StringBuilder("");
        File openedFile = new File(commandLine[1]);

        Database database = Database.getInstance();
        Integer rowNum = 1;
        List<String> columnNames = new ArrayList<>();
        boolean isColumnLine = true;

        try (Scanner reader = new Scanner(openedFile)){
            while(reader.hasNextLine()){
                String input = reader.nextLine();
                String[] arr = input.split(" ");

                if (isColumnLine){
                    columnNames.addAll(Arrays.asList(arr));
                    isColumnLine = false;
                    continue;
                }
                Row row = new Row();
                for (int i = 0; i < columnNames.size(); i++){
                    DataField dataField = createField(arr[i],columnNames.get(i));
                    row.addField(columnNames.get(i), dataField);
                }

                database.addRow(rowNum.toString(), row);
                rowNum++;
            }
            output.append("File ").append(commandLine[1]).append(" successfully opened");
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found");
        }
        return output;
    }

    private DataField createField(String string,String dataType){
        DataField dataField;
        String type = dataType.substring(dataType.indexOf("<")+1,dataType.lastIndexOf(">"));
        if (type.equalsIgnoreCase("Int")){
            dataField = new IntField(Integer.parseInt(string));
        }
        else if (type.equalsIgnoreCase("Double")){
            dataField = new DoubleField(Double.parseDouble(string));
        }
        else if (type.equalsIgnoreCase("String")) {
            dataField = new StringField(string);
        }
        else {
            throw new RuntimeException("Invalid datatype");
        }
        return dataField;
    }
}

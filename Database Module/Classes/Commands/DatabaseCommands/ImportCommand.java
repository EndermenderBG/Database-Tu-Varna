package Classes.Commands.DatabaseCommands;

import Classes.Commands.FileCommands.OpenCommand;
import Classes.Structure.Database.Database;
import Classes.Structure.Database.Row;
import Classes.Structure.Database.Table;
import Classes.Structure.Fields.DoubleField;
import Classes.Structure.Fields.IntField;
import Classes.Structure.Fields.NullField;
import Classes.Structure.Fields.StringField;
import Interfaces.Command;
import Interfaces.DataField;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The Import command takes the opened file as input and then inputs the data inside into the database.
 */

public class ImportCommand implements Command {
    /**
     * The execute method takes the information within the file, creates a new table,
     * creates rows within the new table, fills them up with fields based on the columns at the top of the file
     * and finally adds that table into the database.
     * @param commandLine
     * @return Notification if succcessful, throws error if not.
     * @throws FileNotFoundException
     */
    @Override
    public StringBuilder execute(Database database,String[] commandLine) throws FileNotFoundException {

        StringBuilder output = new StringBuilder("");
        File openedFile = OpenCommand.getOpenedFile();
        String fileName = openedFile.getName();

        Database database = Database.getInstance();
        String tableName = commandLine[1].substring(0,commandLine[1].indexOf("."));
        if(database.checkDatabase(tableName)){
            output.append("This table has already been imported");
            return output;
        }
        else if(!Objects.equals(commandLine[1], fileName)){
            output.append("The file you have opened currently does not contain this table or the table does not exit. " +
                            "The currently opened file contains contains table: ").append(tableName).append('\n')
                    .append("Please import the correct table");
            return output;
        }
        Table newTable = new Table(tableName);
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

                newTable.addRow(rowNum.toString(), row);
                rowNum++;
            }
            database.addTable(newTable);
            output.append("Table from ").append(openedFile.getName()).append(" successfully imported");
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found");
        }
        return output;
    }

    /**
     * The createFields method filters what field should be created based on the column under which the field is found
     * @param string
     * @param dataType
     * @return Returns a newly created appropriate object.
     */
    private DataField createField(String string, String dataType) {
        if (string.equalsIgnoreCase("NULL")) {
            return new NullField();
        }

        DataField dataField;
        String type = dataType.substring(dataType.indexOf("<") + 1, dataType.lastIndexOf(">"));

        if (type.equalsIgnoreCase("Int")) {
            dataField = new IntField(Integer.parseInt(string));
        } else if (type.equalsIgnoreCase("Double")) {
            dataField = new DoubleField(Double.parseDouble(string));
        } else if (type.equalsIgnoreCase("String")) {
            dataField = new StringField(string);
        } else {
            throw new RuntimeException("Invalid datatype");
        }

        return dataField;
    }
}

package Classes;

import Interfaces.Command;
import Interfaces.DataField;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AggregateCommand implements Command {
    @Override
    public StringBuilder execute(String[] commandLine) throws FileNotFoundException, InterruptedException {
        StringBuilder output = new StringBuilder();

        if (commandLine.length < 6) {
            return output.append("Invalid arguments. Usage: aggregate <table name> <search column n> <search value> <target column n> <operation>");
        }

        String tableName = commandLine[1];
        int searchColIndex;
        int targetColIndex;

        try {
            searchColIndex = Integer.parseInt(commandLine[2]);
            targetColIndex = Integer.parseInt(commandLine[4]);
        } catch (NumberFormatException e) {
            return output.append("Column indices must be integers.");
        }

        String searchValue = commandLine[3];
        String operation = commandLine[5].toLowerCase();

        Database database = Database.getInstance();

        if (!database.checkDatabase(tableName)) {
            return output.append("Table ").append(tableName).append(" does not exist.");
        }

        Table table = database.getTable(tableName);

        if (table.getRows().isEmpty()) {
            return output.append("The table is empty. Cannot perform aggregation.");
        }

        Row firstRow = table.getRows().values().iterator().next();
        List<String> columnKeys = new ArrayList<>(firstRow.getColumns().keySet());

        if (searchColIndex < 1 || searchColIndex > columnKeys.size() || targetColIndex < 1 || targetColIndex > columnKeys.size()) {
            return output.append("Column index out of bounds.");
        }

        String searchColKey = columnKeys.get(searchColIndex - 1);
        String targetColKey = columnKeys.get(targetColIndex - 1);

        String targetType = targetColKey.substring(targetColKey.indexOf("<") + 1, targetColKey.lastIndexOf(">"));
        if (!targetType.equalsIgnoreCase("Int") && !targetType.equalsIgnoreCase("Double")) {
            return output.append("Error: Aggregation operations can only be performed on numeric columns (Int or Double). Target column type is ").append(targetType).append(".");
        }

        double sum = 0;
        double product = 1;
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;
        int count = 0;

        for (Row row : table.getRows().values()) {
            DataField searchField = row.getField(searchColKey);

            if (searchField != null && searchField.getAsString().equals(searchValue)) {
                DataField targetField = row.getField(targetColKey);

                if (targetField != null && !targetField.getAsString().equals("NULL")) {
                    double val;
                    try {
                        val = Double.parseDouble(targetField.getAsString());
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    sum += val;
                    product *= val;
                    if (val > max) max = val;
                    if (val < min) min = val;
                    count++;
                }
            }
        }

        if (count == 0) {
            return output.append("No valid numeric data found for the given search criteria to perform the aggregation.");
        }

        double resultValue = 0;
        switch (operation) {
            case "sum":
                resultValue = sum;
                break;
            case "product":
                resultValue = product;
                break;
            case "maximum":
                resultValue = max;
                break;
            case "minimum":
                resultValue = min;
                break;
            default:
                return output.append("Unknown operation '").append(operation).append("'. Supported operations are: sum, product, maximum, minimum.");
        }

        String formattedResult = (resultValue % 1 == 0) ? String.valueOf((long) resultValue) : String.valueOf(resultValue);

        output.append("The ").append(operation).append(" of column ").append(targetColIndex)
                .append(" where column ").append(searchColIndex).append(" equals '").append(searchValue)
                .append("' is ").append(formattedResult).append(".");

        return output;
    }
}
package Classes.Structure.Database;

import Interfaces.DataField;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The row contains all the information for a single row of a table in the database. Each field is slotted into a LinkedHashMap of columns.
 */

public class Row {
    private Map<String, DataField> columns;

    public Row(){
        this.columns = new LinkedHashMap<>();
    }

    /**
     * Takes the name of the column and field you want to add to the row and then puts it into the map using the name as key and field as value.
     * @param columnName
     * @param field
     */
    public void addField(String columnName, DataField field){
        columns.put(columnName, field);
    }

    /**
     *
     * @param columnName
     * @return The value of a specific column in a specific row object.
     */
    public DataField getField(String columnName){
        return columns.get(columnName);
    }

    /**
     *
     * @return The LinkedHashMap of the columns.
     */
    public Map<String, DataField> getColumns() {
        return columns;
    }
}

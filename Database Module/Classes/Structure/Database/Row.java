package Classes.Structure.Database;

import Interfaces.DataField;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * The row contains all the information for a single row of a table in the database. Each field is slotted into a LinkedHashMap of columns.
 */

public class Row implements Iterable<DataField> {
    private final List<DataField> fields;

    public Row(){
        this.fields = new ArrayList<>();
    }

    public Row(List<DataField> fields){
        this.fields = new ArrayList<>(fields);
    }

    public void addField(DataField field){
        fields.add(field);
    }

    public DataField getField(int colIndex){
        validateColumnIndex(colIndex);
        return fields.get(colIndex);
    }

    public void setField(int colIndex, DataField field){
        validateColumnIndex(colIndex);
        fields.set(colIndex, field);
    }

    public int size(){
        return fields.size();
    }

    private void validateColumnIndex(int colIndex){
        if (colIndex < 0 || colIndex >= fields.size()){
            throw new IndexOutOfBoundsException("Column index out of bounds: " + colIndex);
        }
    }

    @Override
    public Iterator<DataField> iterator(){
        return Collections.unmodifiableList(fields).iterator();
    }
//    private Map<String, DataField> columns;
//
//    public Row(){
//        this.columns = new LinkedHashMap<>();
//    }
//
//    /**
//     * Takes the name of the column and field you want to add to the row and then puts it into the map using the name as key and field as value.
//     * @param columnName
//     * @param field
//     */
//    public void addField(String columnName, DataField field){
//        columns.put(columnName, field);
//    }
//
//    /**
//     *
//     * @param columnName
//     * @return The value of a specific column in a specific row object.
//     */
//    public DataField getField(String columnName){
//        return columns.get(columnName);
//    }
//
//    /**
//     *
//     * @return The LinkedHashMap of the columns.
//     */
//    public Map<String, DataField> getColumns() {
//        return columns;
//    }

}

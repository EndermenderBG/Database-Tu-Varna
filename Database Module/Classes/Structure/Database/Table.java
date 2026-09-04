package Classes.Structure.Database;

import Interfaces.DataField;

import java.util.*;


public class Table {
    private String name;
    private final List<Column> columns;
    private final List<Row> rows;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void addColumn(String colName, String colType) {
        columns.add(new Column(colName, colType));
    }

    public int getColumnIndex(String colName) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getName().equalsIgnoreCase(colName)) {
                return i;
            }
        }
        return -1;
    }

    public String getColumnName(int colIndex){
        return columns.get(colIndex).getName();
    }

    public String getColumnType(int colIndex){
        return columns.get(colIndex).getType();
    }

    public void insertRow(Row row){
        if(row.size() != columns.size()){
            throw new IllegalArgumentException("Row width does not match column amount.");
        }
        rows.add(row);
    }

    public void insertRowAt(int rowIndex, Row row){
        if(rowIndex < 0 || rowIndex > rows.size()){
            throw new IndexOutOfBoundsException("Invalid row index: " + rowIndex);
        }
        if (row.size() != columns.size()){
            throw new IllegalArgumentException("Row width does not match column amount.");
        }
        rows.add(rowIndex, row);
    }

    public Row getRow(int rowIndex){
        validateRowIndex(rowIndex);
        return rows.get(rowIndex);
    }

    public void deleteRow(int rowIndex){
        validateRowIndex(rowIndex);
        rows.remove(rowIndex);
    }

    public DataField getCell(int rowIndex, int colIndex){
        validateRowIndex(rowIndex);
        return rows.get(rowIndex).getField(colIndex);
    }

    public DataField getCell(int rowIndex, String colName){
        int colIndex = getColumnIndex(colName);
        if (colIndex == -1){throw new IllegalArgumentException("Column not found: " + colName);}
        return getCell(rowIndex, colIndex);
    }

    public void setCell(int rowIndex, int colIndex, DataField value){
        validateRowIndex(rowIndex);
        rows.get(rowIndex).setField(colIndex, value);
    }

    public int getRowCount(){return rows.size();}
    public int getColumnCount(){return columns.size();}

    private void validateRowIndex(int rowIndex){
        if(rowIndex < 0 || rowIndex >= rows.size()){
            throw new IndexOutOfBoundsException("Rows index out of bounds: " + rowIndex);
        }
    }
    @Override
    public Iterator<Row> iterator(){
        return Collections.unmodifiableList(rows).iterator();
    }
}
//    private Map<String, Row> rows;
//    private String name;
//
//    public Table(String name) {
//        this.rows = new LinkedHashMap<>();
//        this.name = name;
//    }
//
//    /**
//     *
//     * @return The name of the table
//     */
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * Sets the name of the table based off the parameter.
//     * @param name
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     *
//     * @return The Map of rows for the table.
//     */
//    public Map<String, Row> getRows() {
//        return rows;
//    }
//
//    /**
//     * Adds a row into the table using an ID (usually a simple iterator) for key and an object of Row for value.
//     * @param rowID
//     * @param row
//     */
//    public void addRow(String rowID, Row row){
//        rows.put(rowID, row);
//    }
//
//    /**
//     *
//     * @param rowID
//     * @return A row from the map based on the ID put inside.
//     */
//    public Row getRow(String rowID){
//        return rows.get(rowID);
//    }


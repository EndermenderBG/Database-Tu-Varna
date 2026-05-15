package Classes.Structure.Database;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Table class contains a LinkedHashMap of rows and a name for the table object.
 */

public class Table {
    private Map<String, Row> rows;
    private String name;

    public Table(String name) {
        this.rows = new LinkedHashMap<>();
        this.name = name;
    }

    /**
     *
     * @return The name of the table
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the table based off the parameter.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The Map of rows for the table.
     */
    public Map<String, Row> getRows() {
        return rows;
    }

    /**
     * Adds a row into the table using an ID (usually a simple iterator) for key and an object of Row for value.
     * @param rowID
     * @param row
     */
    public void addRow(String rowID, Row row){
        rows.put(rowID, row);
    }

    /**
     *
     * @param rowID
     * @return A row from the map based on the ID put inside.
     */
    public Row getRow(String rowID){
        return rows.get(rowID);
    }
}

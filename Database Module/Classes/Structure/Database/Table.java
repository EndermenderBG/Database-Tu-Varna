package Classes.Structure.Database;

import java.util.LinkedHashMap;
import java.util.Map;

public class Table {
    private Map<String, Row> rows;
    private String name;

    public Table(String name) {
        this.rows = new LinkedHashMap<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Row> getRows() {
        return rows;
    }

    public void addRow(String rowID, Row row){
        rows.put(rowID, row);
    }

    public Row getRow(String rowID){
        return rows.get(rowID);
    }
}

package Classes;

import Interfaces.DataField;

import java.util.LinkedHashMap;
import java.util.Map;

public class Row {
    private Map<String, DataField> columns;

    public Row(){
        this.columns = new LinkedHashMap<>();
    }


    public void addField(String columnName, DataField field){
        columns.put(columnName, field);
    }

    public DataField getField(String columnName){
        return columns.get(columnName);
    }

    public Map<String, DataField> getColumns() {
        return columns;
    }
}

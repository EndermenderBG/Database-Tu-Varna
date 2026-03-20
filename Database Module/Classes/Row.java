package Classes;

import Interfaces.DataField;

import java.util.HashMap;
import java.util.Map;

public class Row {
    private Map<String, DataField> columns;

    public Row(){
        this.columns = new HashMap<>();
    }

    public void addFIeld(String columnName, DataField field){
        columns.put(columnName, field);
    }

    public DataField getField(String columnName){
        return columns.get(columnName);
    }
}

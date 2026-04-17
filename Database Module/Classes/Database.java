package Classes;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, Row> rows;

    private static Database databaseInstance = null;

    public Database() {
        this.rows = new HashMap<>();
    }

    public static synchronized Database getInstance(){
        if(databaseInstance == null){
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    public void addRow(String rowID, Row row){
        rows.put(rowID, row);
    }

    public Row getRow(String rowID){
        return rows.get(rowID);
    }
}

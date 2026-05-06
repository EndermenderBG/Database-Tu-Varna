package Classes;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, Table> tables;

    private static Database databaseInstance = null;

    public Database() {
        this.tables = new HashMap<>();
    }

    public static synchronized Database getInstance(){
        if(databaseInstance == null){
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    public void addTable(Table table){
        tables.put(table.getName(), table);
    }

    public Table getTable(String tableName){
        return tables.get(tableName);
    }

    public boolean checkDatabase(String tableName){
        return tables.containsKey(tableName);
    }

    public static void databaseClear(){
        databaseInstance = null;
    }

    public Map<String, Table> getTables() {
        return tables;
    }
}

package Classes.Structure.Database;

import java.util.HashMap;
import java.util.Map;

/**
 * The Database class holds all the tables in the database. It uses the Singleton Pattern for access and creation and holds the tables in a HashMap
 */

public class Database {
    private Map<String, Table> tables;

    private static Database databaseInstance = null;

    public Database() {
        this.tables = new HashMap<>();
    }

    /**
     * The Singleton Pattern access method
     * @return If an instance has already been created it returns it. If not, it creates it and then still returns it.
     */
    public static synchronized Database getInstance(){
        if(databaseInstance == null){
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    /**
     * Adds a table to the HashMap using the table name as key and a Table object as a value.
     * @param table
     */
    public void addTable(Table table){
        tables.put(table.getName(), table);
    }

    /**
     *
     * @param tableName
     * @return Table based on the parameter given for name.
     */
    public Table getTable(String tableName){
        return tables.get(tableName);
    }

    /**
     * Checks if the table with the given name exists within the database
     * @param tableName
     * @return True if it does exist. False if it does not.
     */
    public boolean checkDatabase(String tableName){
        return tables.containsKey(tableName);
    }

    /**
     * Deletes the database by setting the instance to null.
     */
    public static void databaseClear(){
        databaseInstance = null;
    }

    /**
     *
     * @return The HashMap of tables.
     */
    public Map<String, Table> getTables() {
        return tables;
    }
}

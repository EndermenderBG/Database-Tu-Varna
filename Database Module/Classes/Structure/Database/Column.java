package Classes.Structure.Database;

public class Column {
    private final String name;
    private final String type; // e.g., "Int", "Double", "String"

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }
    public String getType() { return type; }
}

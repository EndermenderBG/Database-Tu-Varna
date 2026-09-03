package Interfaces;

import Classes.Structure.Fields.DoubleField;
import Classes.Structure.Fields.IntField;
import Classes.Structure.Fields.NullField;
import Classes.Structure.Fields.StringField;

/**
 * The DataField Interface is implemented by all the types of data the database can have.
 */
public interface DataField {
    String asString();
    int compareTo(DataField other);
    boolean isNull();

    static DataField createField(String type, String value) throws IllegalArgumentException {
        if (value == null || value.equalsIgnoreCase("null")) {
            return new NullField();
        }

        switch (type.toLowerCase()) {
            case "int":
                return new IntField(Integer.parseInt(value));
            case "double":
                return new DoubleField(Double.parseDouble(value));
            case "string":
                return new StringField(value);
            default:
                throw new IllegalArgumentException("Unsupported data type: " + type);
        }
    }
}

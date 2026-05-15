package Classes.Structure.Fields;

import Interfaces.DataField;

public class NullField implements DataField {
    @Override
    public String getAsString() {
        return "NULL";
    }
}
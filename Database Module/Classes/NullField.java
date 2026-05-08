package Classes;

import Interfaces.DataField;

public class NullField implements DataField {
    @Override
    public String getAsString() {
        return "NULL";
    }
}
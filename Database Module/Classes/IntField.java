package Classes;

import Interfaces.DataField;

public class IntField implements DataField {
    private int value;

    public IntField(int value) {
        this.value = value;
    }

    @Override
    public String getAsString() {
        return Integer.toString(value);
    }
}

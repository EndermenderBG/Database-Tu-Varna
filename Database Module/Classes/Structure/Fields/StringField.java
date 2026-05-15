package Classes.Structure.Fields;

import Interfaces.DataField;

public class StringField implements DataField{
    private String value;

    public StringField(String value) {
        this.value = value;
    }

    @Override
    public String getAsString() {
        return value;
    }
}

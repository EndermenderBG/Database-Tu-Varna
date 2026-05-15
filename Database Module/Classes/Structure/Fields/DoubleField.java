package Classes.Structure.Fields;

import Interfaces.DataField;

public class DoubleField implements DataField {

    private double value;

    public DoubleField(double value) {
        this.value = value;
    }

    @Override
    public String getAsString() {
        return Double.toString(value);
    }
}

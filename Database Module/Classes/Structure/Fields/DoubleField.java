package Classes.Structure.Fields;

import Interfaces.DataField;


/**
 * DoubleField contains all values in the database of the Double primitive.
 */
public class DoubleField implements DataField {

    private double value;

    public DoubleField(double value) {
        this.value = value;
    }

    /**
     *
     * @return The values of the double as a String for output purposes.
     */
    @Override
    public String getAsString() {
        return Double.toString(value);
    }
}

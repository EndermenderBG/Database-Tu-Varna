package Classes.Structure.Fields;

import Interfaces.DataField;

/**
 * IntField contains all values in the database of the int primitive.
 */

public class IntField implements DataField {
    private int value;

    public IntField(int value) {
        this.value = value;
    }

    /**
     *
     * @return The values of the int as a String for output purposes.
     */
    @Override
    public String getAsString() {
        return Integer.toString(value);
    }
}

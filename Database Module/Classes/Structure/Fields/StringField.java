package Classes.Structure.Fields;

import Interfaces.DataField;

/**
 * StringField contains all values in the database that are Strings.
 */

public class StringField implements DataField{
    private String value;

    public StringField(String value) {
        this.value = value;
    }

    /**
     *
     * @return A getter method for the String.
     */
    @Override
    public String getAsString() {
        return value;
    }
}

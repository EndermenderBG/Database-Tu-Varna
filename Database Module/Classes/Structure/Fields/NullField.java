package Classes.Structure.Fields;

import Interfaces.DataField;

/**
 * NullField contains all values in the database that are empty, outputting "Null" in place of them.
 */

public class NullField implements DataField {
    /**
     *
     * @return Null in place of a value since the field is empty.
     */
    @Override
    public String getAsString() {
        return "NULL";
    }
}
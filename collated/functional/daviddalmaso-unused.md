# daviddalmaso-unused
###### /java/seedu/address/model/export/exceptions/IncorrectExportTypeException.java
``` java
/**
 * Exception to handle when the given export type is not valid
 */
public class IncorrectExportTypeException extends IllegalArgumentException {

    public IncorrectExportTypeException() {
        super("Unable to map given argument to a valid Export Type");
    }

}
```

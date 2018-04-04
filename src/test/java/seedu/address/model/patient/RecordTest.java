//@@author nhs-work
package seedu.address.model.patient;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RecordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Record(null, null, null, null));
    }

    @Test
    public void isValidRecord() {
        // null record
        Assert.assertThrows(NullPointerException.class, () -> Record.isValidRecord(null));

        // invalid records
        Assert.assertThrows(IllegalArgumentException.class, () -> Record.isValidRecord(new Record("", "", "", "")));
        Assert.assertThrows(IllegalArgumentException.class, () -> Record.isValidRecord(new Record(" ", " ", " ", " ")));
        Assert.assertThrows(IllegalArgumentException.class, () -> Record.isValidRecord(
                new Record("5th March 2016", " ", " ", " ")));

        // valid records
        assertTrue(Record.isValidRecord(new Record("01/04/2018", "High temperature", "Fever", "Antibiotics")));
        assertTrue(Record.isValidRecord(new Record("99/99/9999", "b", "c", "d"))); // one character
    }
}

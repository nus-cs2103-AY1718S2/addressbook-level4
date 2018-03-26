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
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Record.isValidRecord(null));

        // valid records for now
        assertTrue(Record.isValidRecord(new Record("", "", "", ""))); // empty string
        assertTrue(Record.isValidRecord(new Record(" ", " ", " ", " "))); // spaces only

        // valid addresses
        assertTrue(Record.isValidRecord(new Record("5th March 2016", "High temperature", "Fever", "Antibiotics")));
        assertTrue(Record.isValidRecord(new Record("a", "b", "c", "d"))); // one character
    }
}

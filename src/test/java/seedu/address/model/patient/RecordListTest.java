package seedu.address.model.patient;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RecordListTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Record(null, null, null, null));
    }

    @Test
    public void isValidRecordList() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> RecordList.isValidRecordList(null));

        ArrayList<Record> temp = new ArrayList<Record>();

        // valid recordLists for now
        temp.add(new Record("", "", "", ""));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp))); // empty string
        temp.remove(0);
        temp.add(new Record(" ", " ", " ", " "));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp))); // spaces only

        // valid recordLists
        temp.remove(0);
        temp.add(new Record("5th March 2016", "High temperature", "Fever", "Antibiotics"));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp)));
        temp.remove(0);
        temp.add(new Record("a", "b", "c", "d"));
        assertTrue(RecordList.isValidRecordList(new RecordList(temp))); // one character
    }
}

package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pair.PairHash;

//@@author aussiroth
public class XmlAdaptedPairHashTest {

    @Test
    public void toModelType_validPairHash_returnsPairHash() throws Exception {
        PairHash expectedPairHash = new PairHash(12341);
        XmlAdaptedPairHash xmlExpectedPairHash = new XmlAdaptedPairHash(expectedPairHash);
        assertEquals(expectedPairHash, xmlExpectedPairHash.toModelType());
    }

    @Test
    public void toModelType_invalidValue_throwsIllegalValueException() throws Exception {
        XmlAdaptedPairHash invalidValueHash = new XmlAdaptedPairHash("abcde");
        assertThrows(IllegalValueException.class, () -> invalidValueHash.toModelType());
    }

    @Test
    public void testXmlAdaptedTagEquality() {
        XmlAdaptedPairHash targetPairHash = new XmlAdaptedPairHash("12345");
        XmlAdaptedPairHash copy = new XmlAdaptedPairHash("12345");
        assertTrue(targetPairHash.equals(targetPairHash));
        //check equality if values are equal
        assertTrue(targetPairHash.equals(copy));
        //check not equal if type is different
        assertFalse(targetPairHash.equals(new PairHash("12345")));
    }
}

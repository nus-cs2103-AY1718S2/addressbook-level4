package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserPrefsTest {
    @Test
    public void testGetAddressBookName_success() {
        UserPrefs userPrefs = new UserPrefs();
        String sampleName = "book";
        userPrefs.setAddressBookName(sampleName);
        assertEquals(sampleName, userPrefs.getAddressBookName());
    }

    @Test
    public void testEquals_symmetric() {
        UserPrefs userPrefsA = new UserPrefs();
        UserPrefs userPrefsB = new UserPrefs();
        UserPrefs userPrefsC = new UserPrefs();
        UserPrefs userPrefsD = new UserPrefs();
        UserPrefs userPrefsE = new UserPrefs();
        UserPrefs userPrefsF = new UserPrefs();
        UserPrefs userPrefsG = new UserPrefs();
        String sampleName = "book";
        String samplePath = "";
        userPrefsC.setGuiSettings(1.0, 1.0, 1, 1);
        userPrefsC.setAddressBookFilePath(samplePath);
        userPrefsC.setAddressBookName(sampleName);
        userPrefsD.setGuiSettings(1.0, 1.0, 1, 1);
        userPrefsD.setAddressBookFilePath(samplePath);
        userPrefsD.setAddressBookName(sampleName);
        userPrefsE.setGuiSettings(1.0, 1.0, 1, 1);
        userPrefsF.setAddressBookFilePath(samplePath);
        userPrefsG.setAddressBookName(sampleName);

        assertTrue(userPrefsA.equals(userPrefsB));
        assertTrue(userPrefsC.equals(userPrefsD));

        assertFalse(userPrefsA.equals(userPrefsC));
        assertFalse(userPrefsA.equals(userPrefsD));
        assertFalse(userPrefsA.equals(userPrefsE));
        assertFalse(userPrefsA.equals(userPrefsF));
        assertFalse(userPrefsA.equals(userPrefsG));

        assertFalse(userPrefsC.equals(userPrefsE));
        assertFalse(userPrefsC.equals(userPrefsF));
        assertFalse(userPrefsC.equals(userPrefsG));

        assertFalse(userPrefsE.equals(userPrefsF));
        assertFalse(userPrefsE.equals(userPrefsG));

        assertFalse(userPrefsF.equals(userPrefsG));
    }

    @Test
    public void testHashcode_symmetric() {
        UserPrefs userPrefsA = new UserPrefs();
        UserPrefs userPrefsB = new UserPrefs();
        UserPrefs userPrefsC = new UserPrefs();
        UserPrefs userPrefsD = new UserPrefs();
        UserPrefs userPrefsE = new UserPrefs();
        UserPrefs userPrefsF = new UserPrefs();
        UserPrefs userPrefsG = new UserPrefs();
        String sampleName = "book";
        String samplePath = "";
        userPrefsC.setGuiSettings(1.0, 1.0, 1, 1);
        userPrefsC.setAddressBookFilePath(samplePath);
        userPrefsC.setAddressBookName(sampleName);
        userPrefsD.setGuiSettings(1.0, 1.0, 1, 1);
        userPrefsD.setAddressBookFilePath(samplePath);
        userPrefsD.setAddressBookName(sampleName);
        userPrefsE.setGuiSettings(1.0, 1.0, 1, 1);
        userPrefsF.setAddressBookFilePath(samplePath);
        userPrefsG.setAddressBookName(sampleName);

        assertEquals(userPrefsA.hashCode(), userPrefsB.hashCode());
        assertEquals(userPrefsC.hashCode(), userPrefsD.hashCode());

        assertNotEquals(userPrefsA.hashCode(), userPrefsC.hashCode());
        assertNotEquals(userPrefsA.hashCode(), userPrefsD.hashCode());
        assertNotEquals(userPrefsA.hashCode(), userPrefsE.hashCode());
        assertNotEquals(userPrefsA.hashCode(), userPrefsF.hashCode());
        assertNotEquals(userPrefsA.hashCode(), userPrefsG.hashCode());

        assertNotEquals(userPrefsC.hashCode(), userPrefsE.hashCode());
        assertNotEquals(userPrefsC.hashCode(), userPrefsF.hashCode());
        assertNotEquals(userPrefsC.hashCode(), userPrefsG.hashCode());

        assertNotEquals(userPrefsE.hashCode(), userPrefsF.hashCode());
        assertNotEquals(userPrefsE.hashCode(), userPrefsG.hashCode());

        assertNotEquals(userPrefsF.hashCode(), userPrefsG.hashCode());
    }
}

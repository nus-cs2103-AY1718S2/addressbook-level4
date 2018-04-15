package seedu.address.model.student;
//@@author samuelloh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.testutil.Assert;

public class ProgrammingLanguageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProgrammingLanguage(null));
    }

    @Test
    public void constructor_invalidProgrammingLanguage_throwsIllegalArgumentException() {
        String invalidProgrammingLanguage = "\t";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProgrammingLanguage(invalidProgrammingLanguage));
    }

    @Test
    public void isValidProgrammingLanguage() {
        // null ProgrammingLanguage
        Assert.assertThrows(NullPointerException.class, () -> new ProgrammingLanguage(null));

        // invalid ProgrammingLanguage
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\t")); // tab an invisible character
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\n")); // new line an invisible charater
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\r")); // carriage return invisible character
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\f")); // form feed an invisible character

        // valid ProgrammingLanguage
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("java"));
        // alphabets only
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("12345"));
        // numbers only
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("angular js 8"));
        // alphanumeric characters
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("C"));
        // with capital letters
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("javascript version 10 hardcore"));
        // long ProgrammingLanguages
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("C++"));
        // with special characters
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("#"));
        // special characters only
    }
}
//@@author

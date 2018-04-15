package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author shookshire
public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Grade(invalidSubject));
    }

    @Test
    public void isValidGrade() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // invalid subject
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only
        assertFalse(Grade.isValidGrade("pri5")); // invalid format
        assertFalse(Grade.isValidGrade("primary 3")); // spacing between "primary" and "3"

        // one or more invalid subject
        assertFalse(Grade.isValidGrade("pri4 p2 p1 s3")); // one invalid grade
        assertFalse(Grade.isValidGrade("pre2 asdo feiwo")); // many invalid grade
        assertFalse(Grade.isValidGrade("p2 p2")); // multiple same grade

        // valid subject
        assertTrue(Grade.isValidGrade("p3")); //alias
        assertTrue(Grade.isValidGrade("primary3")); // full grade
        assertTrue(Grade.isValidGrade("secondary3")); // full grade
        assertTrue(Grade.isValidGrade("p3 s3 s1 p4")); // multiple valid grade
    }
}

# ktingit
###### \java\seedu\address\model\patient\BloodTypeTest.java
``` java
package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BloodTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidBloodType_throwsIllegalArgumentException() {
        String invalidBloodType = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new BloodType(invalidBloodType));
    }

    @Test
    public void isValidBloodType() {
        // null blood type
        Assert.assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));

        // invalid blood type
        assertFalse(BloodType.isValidBloodType("")); // empty string
        assertFalse(BloodType.isValidBloodType(" ")); // spaces only
        assertFalse(BloodType.isValidBloodType("A")); // alphabet only
        assertFalse(BloodType.isValidBloodType("AB")); // alphabets only
        assertFalse(BloodType.isValidBloodType("+")); // sign only
        assertFalse(BloodType.isValidBloodType("+-")); // signs only

        // valid blood type
        assertTrue(BloodType.isValidBloodType("A+"));
        assertTrue(BloodType.isValidBloodType("A-"));
        assertTrue(BloodType.isValidBloodType("B+"));
        assertTrue(BloodType.isValidBloodType("B-"));
        assertTrue(BloodType.isValidBloodType("O+"));
        assertTrue(BloodType.isValidBloodType("O-"));
        assertTrue(BloodType.isValidBloodType("AB+"));
        assertTrue(BloodType.isValidBloodType("AB-"));
    }
}
```
###### \java\seedu\address\model\patient\DobTest.java
``` java
package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DobTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidDateOfBirth_throwsIllegalArgumentException() {
        String invalidDob = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDob));
    }

    @Test
    public void isValidDob() {
        // null dob
        Assert.assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDob(null));

        // invalid dob
        assertFalse(DateOfBirth.isValidDob("")); // empty string
        assertFalse(DateOfBirth.isValidDob(" ")); // spaces only
        assertFalse(DateOfBirth.isValidDob("ab/cd/efgh")); // non-digits
        assertFalse(DateOfBirth.isValidDob("99/99/9999")); // invalid range
        assertFalse(DateOfBirth.isValidDob("12/31/1990")); // non ddmmyyyy format
        assertFalse(DateOfBirth.isValidDob("1990/12/12")); // non ddmmyyyy format

        // valid dob
        assertTrue(DateOfBirth.isValidDob("11/11/1991")); // standard format
    }
}
```
###### \java\seedu\address\model\patient\NricTest.java
``` java
package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null nric
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only

        // valid nric
        assertTrue(Nric.isValidNric("S1234567A")); // standard format
    }
}
```

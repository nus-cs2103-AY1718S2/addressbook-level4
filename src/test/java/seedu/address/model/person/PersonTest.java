package seedu.address.model.person;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PersonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Person(null, null, null, null, null, null));
        Assert.assertThrows(NullPointerException.class, () -> new Person(null, null, null, null, null, null, null));
    }
}

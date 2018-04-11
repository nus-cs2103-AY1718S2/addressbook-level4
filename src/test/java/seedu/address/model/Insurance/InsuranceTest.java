package seedu.address.model.Insurance;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Sebry9
public class InsuranceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Insurance(null));
    }

    @Test
    public void isValidInsurance() {
        //null insurance name
        Assert.assertThrows(NullPointerException.class, () -> Insurance.isValidInsurance(null));
    }
}

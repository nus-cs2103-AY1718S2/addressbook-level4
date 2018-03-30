package seedu.address.model.policy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PolicyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        //all parameters are null
        Assert.assertThrows(NullPointerException.class, () -> new Policy(null, null, null, null));

        List<Issue> issues = new ArrayList<Issue>();
        issues.add(Issue.HOUSE_DAMAGE);
        issues.add(Issue.CAR_ACCIDENT);
        Coverage coverage = new Coverage(issues);
        Date begin = new Date(10, Month.JANUARY, 2018);
        Date expiration = new Date(11, Month.JANUARY, 2019);
        Price price = new Price(350.0);

        //one parameter is null
        Assert.assertThrows(NullPointerException.class, () -> new Policy(null, coverage, begin, expiration));
        Assert.assertThrows(NullPointerException.class, () -> new Policy(price, null, begin, expiration));
        Assert.assertThrows(NullPointerException.class, () -> new Policy(price, coverage, null, expiration));
        Assert.assertThrows(NullPointerException.class, () -> new Policy(price, coverage, begin, null));
    }

    @Test
    public void isValidDuration() {
        //Valid durations
        Date validBegin1 = new Date(10, Month.MARCH, 2018);
        Date validExpiration1 = new Date(10, Month.MARCH, 2018);
        Date validBegin2 = new Date(1, Month.MARCH, 2018);
        Date validExpiration2 = new Date(2, Month.MARCH, 2018);
        Date validBegin3 = new Date(10, Month.MARCH, 2018);
        Date validExpiration3 = new Date(9, Month.APRIL, 2018);

        assertTrue(Policy.isValidDuration(validBegin1, validExpiration1));
        assertTrue(Policy.isValidDuration(validBegin2, validExpiration2));
        assertTrue(Policy.isValidDuration(validBegin3, validExpiration3));

        //Invalid durations
        Date invalidBegin1 = new Date(10, Month.MARCH, 2018);
        Date invalidExpiration1 = new Date(9, Month.MARCH, 2018);
        Date invalidBegin2 = new Date(10, Month.MARCH, 2018);
        Date invalidExpiration2 = new Date(25, Month.JANUARY, 2018);
        Date invalidBegin3 = new Date(1, Month.JANUARY, 2018);
        Date invalidExpiration3 = new Date(31, Month.DECEMBER, 2017);

        assertFalse(Policy.isValidDuration(invalidBegin1, invalidExpiration1));
        assertFalse(Policy.isValidDuration(invalidBegin2, invalidExpiration2));
        assertFalse(Policy.isValidDuration(invalidBegin3, invalidExpiration3));
    }
}

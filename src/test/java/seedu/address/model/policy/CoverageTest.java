package seedu.address.model.policy;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CoverageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Coverage(null));

        List<Issue> invalidIssues = new ArrayList<Issue>();
        invalidIssues.add(null);
        invalidIssues.add(Issue.CAR_ACCIDENT);
        invalidIssues.add(Issue.HOUSE_DAMAGE);
        Assert.assertThrows(NullPointerException.class, () -> new Coverage(invalidIssues));
    }

    @Test
    public void constructor_normalList_immutable() {
        List<Issue> issues = new ArrayList<Issue>();
        issues.add(Issue.HOUSE_DAMAGE);
        issues.add(Issue.CAR_ACCIDENT);

        Coverage coverageUnderTest = new Coverage(issues);
        issues.remove(1);
        issues.set(0, Issue.THEFT);

        assertTrue(coverageUnderTest.getCoverage().get(0) == Issue.HOUSE_DAMAGE);
        assertTrue(coverageUnderTest.getCoverage().get(1) == Issue.CAR_ACCIDENT);
    }
}

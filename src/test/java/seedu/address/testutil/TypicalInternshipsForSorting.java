package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.JobbiBot;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;

/**
 * A utility class containing a list of {@code Internship} objects to be used in the sorting tests.
 */
public class TypicalInternshipsForSorting {

    public static final Internship IN1 = new InternshipBuilder().withName("AAA").withSalary("1000")
            .withEmail("XXX@XXX.com").withAddress("XXX")
            .withIndustry("IndustryA1").withRegion("RegionA").withRole("RoleA1").build();
    public static final Internship IN2 = new InternshipBuilder().withName("AAA").withSalary("1000")
            .withEmail("XXX@XXX.com").withAddress("XXX")
            .withIndustry("IndustryA1").withRegion("RegionA").withRole("RoleA2").build();
    public static final Internship IN3 = new InternshipBuilder().withName("AAA").withSalary("1000")
            .withEmail("XXX@XXX.com").withAddress("XXX")
            .withIndustry("IndustryA1").withRegion("RegionA").withRole("RoleA3").build();

    private TypicalInternshipsForSorting() {} // prevents instantiation

    /**
     * Returns an {@code JobbiBot} with all the typical internships.
     */
    public static JobbiBot getTypicalInternshipForSorting() {
        JobbiBot jb = new JobbiBot();
        for (Internship internship : getTypicalInternshipsForSorting()) {
            try {
                jb.addInternship(internship);
            } catch (DuplicateInternshipException e) {
                throw new AssertionError("not possible");
            }
        }
        return jb;
    }

    public static List<Internship> getTypicalInternshipsForSorting() {
        return new ArrayList<>(Arrays.asList(IN1, IN2, IN3));
    }
}

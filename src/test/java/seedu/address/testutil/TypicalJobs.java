package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;

//@@author ChengSashankh
/**
 * A utility class containing a list of {@code Job} objects to be used in tests.
 */
public class TypicalJobs {

    public static final Job FRONTEND = new JobBuilder().withJobTitle("Frontend Engineer")
            .withLocation("Geylang").withSkill("HTML, CSS, JavaScript")
            .withTags("FreshGrad").build();

    public static final Job BACKEND = new JobBuilder().withJobTitle("Backend Engineer")
            .withLocation("Clementi").withSkill("Java, Python, SQL")
            .withTags("Intern").build();

    public static final Job SYSTEMS = new JobBuilder().withJobTitle("Systems Engineer")
            .withLocation("Ang Mo Kio").withSkill("Java, C, Operating Systems")
            .withTags("Experienced").build();

    public static final String KEYWORD_MATCHING_FRONTEND = "Frontend"; // A keyword that matches FRONTEND

    private TypicalJobs() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical jobs.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Job job : getTypicalJobs()) {
            try {
                ab.addJob(job);
            } catch (DuplicateJobException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Job> getTypicalJobs() {
        return new ArrayList<>(Arrays.asList(FRONTEND, BACKEND));
    }
}
//@@author

// @@author kush1509
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;

/**
 * A utility class containing a list of {@code Job} objects to be used in tests.
 */
public class TypicalJobs {

    public static final Job SOFTWARE_ENGINEER = new JobBuilder().withPosition("Software Engineer")
            .withTeam("Cloud Services").withLocation("Singapore").withNumberOfPositions("2")
            .withSkills("Java", "Algorithms").build();
    public static final Job MARKETING_INTERN = new JobBuilder().withPosition("Marketing Intern")
            .withTeam("Marketing").withLocation("Kuala Lampur, Malaysia").withNumberOfPositions("1")
            .withSkills("Excel", "Writing").build();
    public static final Job DEVOPS_ENGINEER = new JobBuilder().withPosition("DevOps Engineer")
            .withTeam("DevOps").withLocation("Singapore").withNumberOfPositions("3")
            .withSkills("AWS", "SQL-Server").build();
    public static final Job PRODUCT_MANAGER = new JobBuilder().withPosition("Product Manager")
            .withTeam("Mobile Products").withLocation("India").withNumberOfPositions("1")
            .withSkills("UI/UX", "Testing").build();

    // Manually added
    public static final Job ANALYST = new JobBuilder().withPosition("Analyst").withTeam("Data Science")
            .withLocation("New Delhi, India").withNumberOfPositions("5").withSkills("Excel").build();
    public static final Job DEVELOPER_INTERN = new JobBuilder().withPosition("Developer Intern")
            .withLocation("Jakarta, Indonesia").withTeam("Web Development").withNumberOfPositions("2")
            .withSkills("JavaScript", "Algorithms").build();

    // Manually added - Job's details found in {@code CommandTestUtil}
    public static final Job INTERN = new JobBuilder().withPosition("Intern").withTeam("Human Resources")
            .withLocation("Kuala Lampur, Malaysia").withNumberOfPositions("5").withSkills("Algorithms").build();
    public static final Job DATA_SCIENTIST = new JobBuilder().withPosition("Data Scientist").withTeam("Data Science")
            .withLocation("Singapore").withNumberOfPositions("1").withSkills("Analysis").build();

    public static final String KEYWORD_MATCHING_POSITION_ENGINEER = "Engineer"; // A keyword that matches Engineer
    public static final String KEYWORD_MATCHING_LOCATION_SINGAPORE = "Singapore"; // A keyword that matches Singapore
    public static final String KEYWORD_MATCHING_SKILL_JAVA = "Java"; // A keyword that matches Java

    private TypicalJobs() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
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
        return new ArrayList<>(Arrays.asList(SOFTWARE_ENGINEER, MARKETING_INTERN, DEVOPS_ENGINEER, PRODUCT_MANAGER));
    }
}


//@@author deeheenguyen
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;

/**
 * A utility class containing a list of {@code Interview} objects to be used in tests.
 */
public class TypicalInterviews {

    public static final Interview SE_INTERVIEW = new InterviewBuilder().withInterviewTitle("SE interview")
            .withInterviewee("Kelvin").withDate("01.02.2018")
            .withInterviewLocation("NUS").build();
    public static final Interview FINANCE_INTERVIEW = new InterviewBuilder().withInterviewTitle("Finance interview")
            .withInterviewee("Bob").withDate("01.03.2018")
            .withInterviewLocation("SOC").build();
    public static final Interview TECH_INTERVIEW = new InterviewBuilder().withInterviewTitle("Tech interview")
            .withInterviewee("Lucian").withDate("01.04.2018")
            .withInterviewLocation("Science").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalInterviews() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Interview interview : getTypicalInterviews()) {
            try {
                ab.addInterview(interview);
            } catch (DuplicateInterviewException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Interview> getTypicalInterviews() {
        return new ArrayList<>(Arrays.asList(SE_INTERVIEW, FINANCE_INTERVIEW, TECH_INTERVIEW));
    }
}

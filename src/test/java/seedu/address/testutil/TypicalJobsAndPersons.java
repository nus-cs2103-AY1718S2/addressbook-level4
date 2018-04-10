// @@author kush1509
package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;

/**
 * A utility class containing a list of {@code Person} and {@code Jobs} objects to be used in tests.
 */
public class TypicalJobsAndPersons {

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = TypicalPersons.getTypicalAddressBook();
        for (Job job : TypicalJobs.getTypicalJobs()) {
            try {
                ab.addJob(job);
            } catch (DuplicateJobException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }
}

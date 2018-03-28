package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class containing a list of {@code AddressBook} objects to be used in tests.
 */
public class TypicalAddressBook {

    private TypicalAddressBook() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and typical tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Task task : TypicalTasks.getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (DuplicateTaskException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

}

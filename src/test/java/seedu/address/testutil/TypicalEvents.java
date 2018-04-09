//@@author LeonidAgarth
package seedu.address.testutil;

import static seedu.address.testutil.TypicalToDos.getTypicalToDos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event F1RACE = new EventBuilder().build();
    public static final Event GSS = new EventBuilder().withName("Great Singapore Sale").withVenue("Orchard")
            .withDate("09/06/2018").withStartTime("0900").withEndTime("2300").build();
    public static final Event HARIRAYA = new EventBuilder().withName("Hari Raya Haji").withVenue("Singapore")
            .withDate("22/08/2018").withStartTime("0000").withEndTime("2359").build();
    public static final Event ILIGHT = new EventBuilder().withName("iLight").withVenue("Marina Bay")
            .withDate("01/04/2018").withStartTime("1930").withEndTime("2359").build();
    public static final Event NDP = new EventBuilder().withName("National Day Parade").withVenue("Promenade")
            .withDate("09/08/2018").withStartTime("1700").withEndTime("1900").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
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
        for (Event event : getTypicalEvents()) {
            try {
                ab.addEvent(event);
            } catch (DuplicateEventException e) {
                throw new AssertionError("not possible");
            }
        }
        for (ToDo todo : getTypicalToDos()) {
            try {
                ab.addToDo(todo);
            } catch (DuplicateToDoException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(F1RACE, GSS, HARIRAYA, ILIGHT, NDP));
    }
}

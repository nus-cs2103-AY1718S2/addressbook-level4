package seedu.address.logic.commands;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.calendarfx.model.Calendar;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendarManager;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.exceptions.GroupNotFoundException;
import seedu.address.model.tag.exceptions.PreferenceNotFoundException;
import seedu.address.testutil.CalendarEntryBuilder;

public class AddEntryCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEntryCommand(null);
    }

    @Test
    public void execute_calendarEventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCalendarEntryAdded modelStub = new ModelStubAcceptingCalendarEntryAdded();
        CalendarEntry validEvent = new CalendarEntryBuilder().build();

        CommandResult commandResult = getAddEntryCommandForCalendarEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEntryCommand.MESSAGE_ADD_ENTRY_SUCCESS, validEvent),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.calendarEventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateCalendarEventException();
        CalendarEntry validEvent = new CalendarEntryBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEntryCommand.MESSAGE_DUPLICATE_ENTRY);

        getAddEntryCommandForCalendarEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        CalendarEntry meetBoss = new CalendarEntryBuilder().withEntryTitle("Meeting with boss").build();
        CalendarEntry getSupplies = new CalendarEntryBuilder().withEntryTitle("Get supplies").build();
        AddEntryCommand addMeetBossCommand = new AddEntryCommand(meetBoss);
        AddEntryCommand addGetSuppliesCommand = new AddEntryCommand(getSupplies);

        // same object -> returns true
        assertTrue(addMeetBossCommand.equals(addMeetBossCommand));

        // same values -> returns true
        AddEntryCommand addMeetBossCommandCopy = new AddEntryCommand(meetBoss);
        assertTrue(addMeetBossCommand.equals(addMeetBossCommandCopy));

        // different types -> returns false
        assertFalse(addMeetBossCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetBossCommand.equals(null));

        // different person -> returns false
        assertFalse(addMeetBossCommand.equals(addGetSuppliesCommand));
    }

    /**
     * Generates a new AddEntryCommand with the details of the given calendar entry.
     */
    private AddEntryCommand getAddEntryCommandForCalendarEvent(CalendarEntry calEvent, Model model) {
        AddEntryCommand command = new AddEntryCommand(calEvent);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData, ReadOnlyCalendarManager newCalendarData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public Calendar getCalendar() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyCalendarManager getCalendarManager() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateOrder(Order target, Order editedOrder)
                throws DuplicateOrderException, OrderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateOrderStatus(Order target, String orderStatus)
                throws DuplicateOrderException, OrderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredCalendarEntryList(Predicate<CalendarEntry> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group targetGroup) throws GroupNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deletePreference(Preference targetPreference) throws PreferenceNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addOrderToOrderList(Order orderToAdd) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteOrder(Order targetOrder) throws OrderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addCalendarEntry(CalendarEntry toAdd)
                throws DuplicateCalendarEntryException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteCalendarEntry(CalendarEntry entryToDelete) throws CalendarEntryNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateCalendarEntry(CalendarEntry entryToEdit, CalendarEntry editedEntry)
                throws DuplicateCalendarEntryException, CalendarEntryNotFoundException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throws a DuplicateCalendarEntryException when trying to add a calendar entry.
     */
    private class ModelStubThrowingDuplicateCalendarEventException extends ModelStub {

        @Override
        public void addCalendarEntry(CalendarEntry toAdd)
                throws DuplicateCalendarEntryException {

            throw new DuplicateCalendarEntryException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyCalendarManager getCalendarManager() {
            return new CalendarManager();
        }
    }


    /**
     * A Model stub that always accepts the calendarEvent being added.
     */
    private class ModelStubAcceptingCalendarEntryAdded extends ModelStub {
        final ArrayList<CalendarEntry> calendarEventsAdded = new ArrayList<>();

        @Override
        public void addCalendarEntry(CalendarEntry calendarEntry)
                throws DuplicateCalendarEntryException {
            requireNonNull(calendarEntry);
            calendarEventsAdded.add(calendarEntry);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyCalendarManager getCalendarManager() {
            return new CalendarManager();
        }
    }
}

package seedu.address.logic.commands;

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

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.UniqueCalendarEventList;
import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.exceptions.GroupNotFoundException;
import seedu.address.model.tag.exceptions.PreferenceNotFoundException;
import seedu.address.testutil.CalendarEventBuilder;

public class AddEventCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_calendarEventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCalendarEventAdded modelStub = new ModelStubAcceptingCalendarEventAdded();
        CalendarEvent validEvent = new CalendarEventBuilder().build();

        CommandResult commandResult = getAddEventCommandForCalendarEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_ADD_EVENT_SUCCESS, validEvent),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.calendarEventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateCalendarEventException();
        CalendarEvent validEvent = new CalendarEventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        getAddEventCommandForCalendarEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        CalendarEvent meetBoss = new CalendarEventBuilder().withEventTitle("Meeting with boss").build();
        CalendarEvent getSupplies = new CalendarEventBuilder().withEventTitle("Get supplies").build();
        AddEventCommand addMeetBossCommand = new AddEventCommand(meetBoss);
        AddEventCommand addGetSuppliesCommand = new AddEventCommand(getSupplies);

        // same object -> returns true
        assertTrue(addMeetBossCommand.equals(addMeetBossCommand));

        // same values -> returns true
        AddEventCommand addMeetBossCommandCopy = new AddEventCommand(meetBoss);
        assertTrue(addMeetBossCommand.equals(addMeetBossCommandCopy));

        // different types -> returns false
        assertFalse(addMeetBossCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetBossCommand.equals(null));

        // different person -> returns false
        assertFalse(addMeetBossCommand.equals(addGetSuppliesCommand));
    }

    /**
     * Generates a new AddEventCommand with the details of the given calendar event.
     */
    private AddEventCommand getAddEventCommandForCalendarEvent(CalendarEvent calEvent, Model model) {
        AddEventCommand command = new AddEventCommand(calEvent);
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
        public void resetData(ReadOnlyAddressBook newData) {
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
        public ObservableList<CalendarEvent> getFilteredCalendarEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateOrder(Order target, Order editedOrder)
                throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateOrderStatus(Order target, String orderStatus)
                throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredCalendarEventList(Predicate<CalendarEvent> predicate) {
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
        public void addCalendarEvent(CalendarEvent toAdd)
                throws UniqueCalendarEventList.DuplicateCalendarEventException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throws a DuplicateCalendarEventException when trying to add a calendar event.
     */
    private class ModelStubThrowingDuplicateCalendarEventException extends ModelStub {

        @Override
        public void addCalendarEvent(CalendarEvent toAdd)
                throws UniqueCalendarEventList.DuplicateCalendarEventException {

            throw new UniqueCalendarEventList.DuplicateCalendarEventException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }


    /**
     * A Model stub that always accepts the calendarEvent being added.
     */
    private class ModelStubAcceptingCalendarEventAdded extends ModelStub {
        final ArrayList<CalendarEvent> calendarEventsAdded = new ArrayList<>();

        @Override
        public void addCalendarEvent(CalendarEvent calendarEvent)
                throws UniqueCalendarEventList.DuplicateCalendarEventException {
            requireNonNull(calendarEvent);
            calendarEventsAdded.add(calendarEvent);
        }

        /* To fix later on */
        @Override
        public ObservableList<CalendarEvent> getFilteredCalendarEventList() {
            return null;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

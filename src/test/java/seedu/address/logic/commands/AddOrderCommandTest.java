//@@author amad-person
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalOrders.FACEWASH;
import static seedu.address.testutil.TypicalOrders.SHOES;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.calendarfx.model.Calendar;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendarManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;
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
import seedu.address.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for AddOrderCommand.
 */
public class AddOrderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());
    }

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddOrderCommand(null, null);
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();
        Order validOrder = new OrderBuilder().build();
        Person person = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        CommandResult commandResult = getAddOrderCommandForOrder(INDEX_FIRST_PERSON, validOrder, modelStub).execute();

        assertEquals(String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                person.getName(), validOrder), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateOrderException();

        Order validOrder = new OrderBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddOrderCommand.MESSAGE_ORDER_NOT_ADDED);

        getAddOrderCommandForOrder(INDEX_FIRST_PERSON, validOrder, modelStub).execute();
    }

    @Test
    public void equals() {
        Order firstOrder = SHOES;
        Order secondOrder = FACEWASH;

        AddOrderCommand firstAddOrderCommmand = new AddOrderCommand(INDEX_FIRST_PERSON, firstOrder);
        AddOrderCommand secondAddOrderCommand = new AddOrderCommand(INDEX_SECOND_PERSON, secondOrder);

        // same object -> returns true
        assertTrue(firstAddOrderCommmand.equals(firstAddOrderCommmand));

        // same values -> returns true
        AddOrderCommand firstAddOrderCommandCopy = new AddOrderCommand(INDEX_FIRST_PERSON, firstOrder);
        assertTrue(firstAddOrderCommmand.equals(firstAddOrderCommandCopy));

        // different types -> returns false
        assertFalse(firstAddOrderCommmand.equals(1));

        // null -> return false
        assertFalse(firstAddOrderCommandCopy.equals(null));

        // different person -> return false
        assertFalse(firstAddOrderCommmand.equals(secondAddOrderCommand));
    }

    /**
     * Generates a new AddOrderCommand with the details of the given order.
     */
    private AddOrderCommand getAddOrderCommandForOrder(Index index, Order order, Model model) {
        AddOrderCommand command = new AddOrderCommand(index, order);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that has all of the irrelevant methods failing.
     */
    private class ModelStub implements Model {
        private final FilteredList<Person> filteredPersons = new FilteredList<>(model.getFilteredPersonList());

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
        public void updateOrder(Order target, Order editedOrder) throws UniqueOrderList.DuplicateOrderException {
            fail("This method should not be called.");
        }

        @Override
        public void updateOrderStatus(Order target, String orderStatus)
                throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            return model.getFilteredOrderList();
        }

        @Override
        public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
            return model.getFilteredCalendarEntryList();
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
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            filteredPersons.setPredicate(predicate);
        }

        @Override
        public void updateFilteredCalendarEventList(Predicate<CalendarEntry> predicate) {
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
        public void addOrderToOrderList(Order orderToAdd) throws UniqueOrderList.DuplicateOrderException {
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
     * A Model stub that always throws a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateOrderException extends ModelStub {
        @Override
        public void addOrderToOrderList(Order order) throws UniqueOrderList.DuplicateOrderException {
            throw new UniqueOrderList.DuplicateOrderException();
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
     * A Model stub that always accepts the order being added.
     */
    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final ArrayList<Order> ordersAdded = new ArrayList<>();

        @Override
        public void addOrderToOrderList(Order order) throws UniqueOrderList.DuplicateOrderException {
            requireNonNull(order);
            ordersAdded.add(order);
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


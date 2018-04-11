# SuxianAlicia
###### /java/guitests/guihandles/CalendarPanelHandle.java
``` java
public class CalendarPanelHandle extends NodeHandle<StackPane> {

    public static final String CALENDAR_PANEL_ID = "#calendarPanelholder";

    private Calendar calendar;
    private CalendarSource calendarSource;
    private CalendarView calendarView;

    protected CalendarPanelHandle(StackPane rootNode) {
        super(rootNode);
        calendarView = CalendarFxUtil.returnModifiedCalendarView();
        calendarSource = new CalendarSource();
        calendar = new Calendar();
        calendar.setReadOnly(true);
        calendarSource.getCalendars().addAll(calendar);
        calendarView.getCalendarSources().setAll(calendarSource);
        getRootNode().getChildren().add(calendarView);
    }

}
```
###### /java/seedu/address/commons/util/DateUtilTest.java
``` java

import static org.junit.Assert.assertFalse;

import java.time.format.DateTimeParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidDate_nullSentence_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        DateUtil.isValidDate(null);
    }

    // Test for isValidDate is rigorously tested in StartDateTest, EndDateTest and DeliveryDateTest.
    @Test
    public void isValidDate_invalidString_returnFalse() {
        // empty string
        assertFalse(DateUtil.isValidDate("  "));

        // does not follow validation format
        assertFalse(DateUtil.isValidDate(" * * *"));
        assertFalse(DateUtil.isValidDate("6 April 2020"));
        assertFalse(DateUtil.isValidDate("06-Apr-2020"));

        // has incorrect number of digits
        assertFalse(DateUtil.isValidDate("10-5-2020"));
        assertFalse(DateUtil.isValidDate("1-05-2020"));
        assertFalse(DateUtil.isValidDate("03-04-20"));
        assertFalse(DateUtil.isValidDate("30-02-2020"));
    }

    @Test
    public void convertStringToDate_nullSentence_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        DateUtil.convertStringToDate(null);
    }

    @Test
    public void convertStringToDate_invalidString_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);

        // empty string
        DateUtil.convertStringToDate(" ");

        // does not follow validation format
        DateUtil.convertStringToDate(" * * *");
        DateUtil.convertStringToDate("6 April 2020");
        DateUtil.convertStringToDate("06-Apr-2020");

        // has incorrect number of digits
        DateUtil.convertStringToDate("10-5-2020");
        DateUtil.convertStringToDate("1-05-2020");
        DateUtil.convertStringToDate("03-04-20");
        DateUtil.convertStringToDate("30-02-2020");
    }
}
```
###### /java/seedu/address/commons/util/EntryTimeConstraintsUtilTest.java
``` java

import static junit.framework.TestCase.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_BOSS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

public class EntryTimeConstraintsUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkCalendarEntryTimeConstraints_validInputs_success() throws IllegalValueException {
        StartDate startDate = new StartDate(VALID_START_DATE_MEET_BOSS);
        EndDate endDate = new EndDate(VALID_END_DATE_MEET_BOSS);
        StartTime startTime = new StartTime(VALID_START_TIME_MEET_BOSS);
        EndTime endTime = new EndTime(VALID_END_TIME_MEET_BOSS);

        try {
            EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, startTime, endTime);
        } catch (IllegalValueException ive) {
            fail("This test should not throw any exceptions.");
        }
    }

    @Test
    public void checkCalendarEntryTimeConstraints_startDateLaterThanEndDate_throwsIllegalValueException()
            throws IllegalValueException {
        StartDate invalidStartDate = new StartDate("06-06-2100"); //Start Date is after End Date
        EndDate endDate = new EndDate(VALID_END_DATE_MEET_BOSS);
        StartTime startTime = new StartTime(VALID_START_TIME_MEET_BOSS);
        EndTime endTime = new EndTime(VALID_END_TIME_MEET_BOSS);

        String expectedMessage = EntryTimeConstraintsUtil.START_AND_END_DATE_CONSTRAINTS;
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(expectedMessage);
        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(invalidStartDate, endDate, startTime, endTime);
    }

    @Test
    public void checkCalendarEntryTimeConstraints_startTimeLaterThanEndTime_throwsIllegalValueException()
            throws IllegalValueException {

        StartDate startDate = new StartDate(VALID_START_DATE_MEET_BOSS);
        EndDate endDate = new EndDate(VALID_END_DATE_MEET_BOSS);
        StartTime invalidStartTime = new StartTime("23:59");
        EndTime endTime = new EndTime(VALID_END_TIME_MEET_BOSS);

        String expectedMessage = EntryTimeConstraintsUtil.START_AND_END_TIME_CONSTRAINTS;
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(expectedMessage);
        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, invalidStartTime, endTime);
    }

    @Test
    public void checkCalendarEntryTimeConstraints_durationShorterThanFifteenMinutes_throwsIllegalValueException()
            throws IllegalValueException {

        StartDate startDate = new StartDate("05-06-2018");
        EndDate endDate = new EndDate("06-06-2018");
        StartTime startTime = new StartTime("23:50");
        EndTime endTime = new EndTime("00:00");

        String expectedMessage = EntryTimeConstraintsUtil.ENTRY_DURATION_CONSTRAINTS;
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(expectedMessage);
        EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints(startDate, endDate, startTime, endTime);
    }
}
```
###### /java/seedu/address/commons/util/TimeUtilTest.java
``` java

import static org.junit.Assert.assertFalse;

import java.time.format.DateTimeParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TimeUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidTime_nullSentence_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        TimeUtil.isValidTime(null);
    }

    // Test for isValidTime is rigorously tested in StartTimeTest and EndTimeTest.
    @Test
    public void isValidTime_invalidString_returnFalse() {
        // empty string
        assertFalse(TimeUtil.isValidTime("  "));

        // does not follow validation format
        assertFalse(TimeUtil.isValidTime(" * * *"));
        assertFalse(TimeUtil.isValidTime("0212"));
        assertFalse(TimeUtil.isValidTime("02-12"));

        // has incorrect number of digits
        assertFalse(TimeUtil.isValidTime("12:500"));
        assertFalse(TimeUtil.isValidTime("240:50"));
        assertFalse(TimeUtil.isValidTime("2:50"));
        assertFalse(TimeUtil.isValidTime("21:5"));
    }

    @Test
    public void convertStringToTime_nullSentence_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        TimeUtil.convertStringToTime(null);
    }

    @Test
    public void convertStringToTime_invalidString_throwsDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);

        // empty string
        TimeUtil.convertStringToTime(" ");

        // does not follow validation format
        TimeUtil.convertStringToTime(" * * *");
        TimeUtil.convertStringToTime("0212");
        TimeUtil.convertStringToTime("02-12");

        // has incorrect number of digits
        TimeUtil.convertStringToTime("12:500");
        TimeUtil.convertStringToTime("240:50");
        TimeUtil.convertStringToTime("2:50");
        TimeUtil.convertStringToTime("21:5");

    }
}
```
###### /java/seedu/address/logic/commands/AddEntryCommandTest.java
``` java
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
        ModelStubAcceptingCalendarEventAdded modelStub = new ModelStubAcceptingCalendarEventAdded();
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
     * Generates a new AddEntryCommand with the details of the given calendar event.
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
     * A Model stub that always throws a DuplicateCalendarEntryException when trying to add a calendar event.
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
    private class ModelStubAcceptingCalendarEventAdded extends ModelStub {
        final ArrayList<CalendarEntry> calendarEventsAdded = new ArrayList<>();

        @Override
        public void addCalendarEntry(CalendarEntry calendarEntry)
                throws DuplicateCalendarEntryException {
            requireNonNull(calendarEntry);
            calendarEventsAdded.add(calendarEntry);
        }

        /* To fix later on */
        @Override
        public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
            return null;
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
```
###### /java/seedu/address/logic/commands/DeleteEntryCommandTest.java
``` java
public class DeleteEntryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithOrders(),
            getTypicalCalendarManagerWithEntries(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        CalendarEntry entryToDelete = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteEntryCommand deleteEntryCommand = prepareCommand(INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteEntryCommand.MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
                model.getCalendarManager(), new UserPrefs());
        expectedModel.deleteCalendarEntry(entryToDelete);

        assertCommandSuccess(deleteEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteEntryCommand deleteEntryCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        CalendarEntry entryToDelete = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteEntryCommand deleteEntryCommand = prepareCommand(INDEX_FIRST_ENTRY);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        // delete -> first order deleted
        deleteEntryCommand.execute();
        undoRedoStack.push(deleteEntryCommand);

        // undo -> reverts address book back to previous state and order list to show all calendar entries
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first order deleted again
        expectedModel.deleteCalendarEntry(entryToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEntryList().size() + 1);
        DeleteEntryCommand deleteEntryCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteEntryCommand not pushed into undoRedoStack
        assertCommandFailure(deleteEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteEntryCommand deleteFirstEntryCommand = prepareCommand(INDEX_FIRST_ENTRY);
        DeleteEntryCommand deleteSecondEntryCommand = prepareCommand(INDEX_SECOND_ENTRY);

        // same object -> returns true
        assertTrue(deleteFirstEntryCommand.equals(deleteFirstEntryCommand));

        // same values -> returns true
        DeleteEntryCommand deleteFirstEntryCommandCopy = prepareCommand(INDEX_FIRST_ENTRY);
        assertTrue(deleteFirstEntryCommand.equals(deleteFirstEntryCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstEntryCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstEntryCommand.equals(deleteFirstEntryCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstEntryCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstEntryCommand.equals(null));

        // different order -> returns false
        assertFalse(deleteFirstEntryCommand.equals(deleteSecondEntryCommand));
    }

    /**
     * Returns a {@code DeleteEntryCommand} with the parameter {@code index}.
     */
    private DeleteEntryCommand prepareCommand(Index index) {
        DeleteEntryCommand deleteEntryCommand = new DeleteEntryCommand(index);
        deleteEntryCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteEntryCommand;
    }
}
```
###### /java/seedu/address/logic/commands/DeleteGroupCommandTest.java
``` java
public class DeleteGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void execute_validGroup_success() throws Exception {
        Group groupToDelete = FRIENDS;
        DeleteGroupCommand deleteGroupCommand = prepareCommand(FRIENDS);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new CalendarManager(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unexistingGroup_throwsCommandException() throws Exception {
        Group groupToDelete = new Group("friend");
        DeleteGroupCommand deleteGroupCommand = prepareCommand(groupToDelete);

        assertCommandFailure(deleteGroupCommand, model, DeleteGroupCommand.MESSAGE_GROUP_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_validGroup_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Group groupToDelete = FRIENDS;
        DeleteGroupCommand deleteGroupCommand = prepareCommand(groupToDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), new CalendarManager(), new UserPrefs());

        // delete -> friends group deleted
        deleteGroupCommand.execute();
        undoRedoStack.push(deleteGroupCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same group deleted again
        expectedModel.deleteGroup(groupToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidPreference_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Group groupToDelete = new Group("friend");
        DeleteGroupCommand deleteGroupCommand = prepareCommand(groupToDelete);

        // execution failed -> deleteGroupCommand not pushed into undoRedoStack
        assertCommandFailure(deleteGroupCommand, model, DeleteGroupCommand.MESSAGE_GROUP_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteGroupCommand deleteFirstCommand = prepareCommand(FRIENDS);
        DeleteGroupCommand deleteSecondCommand = prepareCommand(COLLEAGUES);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteGroupCommand deleteFirstCommandCopy = prepareCommand(FRIENDS);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different preference -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteGroupCommand prepareCommand(Group group) {
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(group);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteGroupCommand;
    }
}
```
###### /java/seedu/address/logic/commands/DeletePreferenceCommandTest.java
``` java
public class DeletePreferenceCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void execute_validPreference_success() throws Exception {
        Preference prefToDelete = SHOES;
        DeletePreferenceCommand deletePrefCommand = prepareCommand(SHOES);

        String expectedMessage = String.format(DeletePreferenceCommand.MESSAGE_DELETE_PREFERENCE_SUCCESS, prefToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new CalendarManager(), new UserPrefs());
        expectedModel.deletePreference(prefToDelete);

        assertCommandSuccess(deletePrefCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unexistingPreference_throwsCommandException() throws Exception {
        Preference prefToDelete = new Preference("shoe");
        DeletePreferenceCommand deletePrefCommand = prepareCommand(prefToDelete);

        assertCommandFailure(deletePrefCommand, model, DeletePreferenceCommand.MESSAGE_PREFERENCE_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_validPreference_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Preference prefToDelete = SHOES;
        DeletePreferenceCommand deletePrefCommand = prepareCommand(prefToDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), new CalendarManager(), new UserPrefs());

        // delete -> shoes preference deleted
        deletePrefCommand.execute();
        undoRedoStack.push(deletePrefCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same preference deleted again
        expectedModel.deletePreference(prefToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidPreference_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Preference prefToDelete = new Preference("shoe");
        DeletePreferenceCommand deletePrefCommand = prepareCommand(prefToDelete);

        // execution failed -> deletePrefCommand not pushed into undoRedoStack
        assertCommandFailure(deletePrefCommand, model, DeletePreferenceCommand.MESSAGE_PREFERENCE_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeletePreferenceCommand deleteFirstCommand = prepareCommand(SHOES);
        DeletePreferenceCommand deleteSecondCommand = prepareCommand(COMPUTERS);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePreferenceCommand deleteFirstCommandCopy = prepareCommand(SHOES);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different preference -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeletePreferenceCommand prepareCommand(Preference preference) {
        DeletePreferenceCommand deletePrefCommand = new DeletePreferenceCommand(preference);
        deletePrefCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deletePrefCommand;
    }
}
```
###### /java/seedu/address/logic/commands/EditEntryCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.CalendarEntry;
import seedu.address.testutil.CalendarEntryBuilder;
import seedu.address.testutil.EditEntryDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditEntryCommand.
 */
public class EditEntryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendarManagerWithEntries() ,
            new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        CalendarEntry editedEntry = new CalendarEntryBuilder().withEntryTitle("Meet Client").build();
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(editedEntry).build();
        EditEntryCommand editEntryCommand = prepareCommand(INDEX_FIRST_ENTRY, descriptor);

        String expectedMessage = String.format(EditEntryCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateCalendarEntry(model.getFilteredCalendarEntryList().get(0), editedEntry);

        assertCommandSuccess(editEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastEntry = Index.fromOneBased(model.getFilteredCalendarEntryList().size());
        CalendarEntry lastEntry = model.getFilteredCalendarEntryList().get(indexLastEntry.getZeroBased());

        CalendarEntryBuilder entryInList = new CalendarEntryBuilder(lastEntry);
        CalendarEntry editedEntry = entryInList.withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS)
                .withStartTime(VALID_START_TIME_GET_STOCKS).build();

        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS)
                .withStartTime(VALID_START_TIME_GET_STOCKS).build();
        EditEntryCommand editEntryCommand = prepareCommand(indexLastEntry, descriptor);

        String expectedMessage = String.format(EditEntryCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateCalendarEntry(lastEntry, editedEntry);

        assertCommandSuccess(editEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws Exception {
        EditEntryCommand editEntryCommand = prepareCommand(INDEX_FIRST_ENTRY, new EditEntryDescriptor());
        CalendarEntry editedEntry = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());

        String expectedMessage = String.format(EditEntryCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateCalendarEntry(model.getFilteredCalendarEntryList().get(0), editedEntry);

        assertCommandSuccess(editEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCalendarEntryUnfilteredList_failure() {
        CalendarEntry firstEntry = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(firstEntry).build();
        EditEntryCommand editEntryCommand = prepareCommand(INDEX_SECOND_ENTRY, descriptor);

        assertCommandFailure(editEntryCommand, model, EditEntryCommand.MESSAGE_DUPLICATE_ENTRY);
    }

    @Test
    public void execute_invalidCalendarEntryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEntryList().size() + 1);
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).build();
        EditEntryCommand editEntryCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        CalendarEntry editedEntry = new CalendarEntryBuilder().build();
        CalendarEntry entryToEdit = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(editedEntry).build();
        EditEntryCommand editEntryCommand = prepareCommand(INDEX_FIRST_ENTRY, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());

        // edit -> first calendar entry edited
        editEntryCommand.execute();
        undoRedoStack.push(editEntryCommand);

        // undo -> reverts address book back to previous state and filtered calendar entry list to show all entries
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first calendar entry edited again
        expectedModel.updateCalendarEntry(entryToEdit, editedEntry);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEntryList().size() + 1);
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).build();
        EditEntryCommand editEntryCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editEntryCommand not pushed into undoRedoStack
        assertCommandFailure(editEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() throws Exception {
        final EditEntryCommand firstCommand = prepareCommand(INDEX_FIRST_ENTRY, DESC_MEET_BOSS);

        // same values -> returns true
        EditEntryDescriptor copyDescriptor = new EditEntryDescriptor(DESC_MEET_BOSS);
        EditEntryCommand firstCommandCopy = prepareCommand(INDEX_FIRST_ENTRY, copyDescriptor);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // one command preprocessed when previously equal -> returns false
        firstCommandCopy.preprocessUndoableCommand();
        assertFalse(firstCommand.equals(firstCommandCopy));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // different index -> returns false
        assertFalse(firstCommand.equals(new EditEntryCommand(INDEX_SECOND_ENTRY, DESC_MEET_BOSS)));

        // different descriptor -> returns false
        assertFalse(firstCommand.equals(new EditEntryCommand(INDEX_FIRST_ENTRY, DESC_GET_STOCKS)));
    }

    /**
     * Returns an {@code EditEntryCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditEntryCommand prepareCommand(Index index, EditEntryDescriptor descriptor) {
        EditEntryCommand editEntryCommand = new EditEntryCommand(index, descriptor);
        editEntryCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editEntryCommand;
    }
}
```
###### /java/seedu/address/logic/commands/EditEntryDescriptorTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_BOSS;

import org.junit.Test;

import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.testutil.EditEntryDescriptorBuilder;

public class EditEntryDescriptorTest {
    @Test
    public void equals() {

        // same values -> returns true
        EditEntryDescriptor descriptorWithSameValues = new EditEntryDescriptor(DESC_GET_STOCKS);
        assertTrue(DESC_GET_STOCKS.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_GET_STOCKS.equals(DESC_GET_STOCKS));

        // null -> returns false
        assertFalse(DESC_GET_STOCKS.equals(null));

        // different types -> returns false
        assertFalse(DESC_GET_STOCKS.equals(5));

        // different values -> returns false
        assertFalse(DESC_GET_STOCKS.equals(DESC_MEET_BOSS));

        // different entry title -> returns false
        EditEntryDescriptor editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS)
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));

        // different start date -> returns false
        editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS).withStartDate(VALID_START_DATE_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));

        // different end date -> returns false
        editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS).withEndDate(VALID_END_DATE_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));

        // different start time -> returns false
        editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS).withStartTime(VALID_START_TIME_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));

        // different end time -> returns false
        editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS).withEndTime(VALID_END_TIME_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));
    }
}
```
###### /java/seedu/address/logic/commands/FindGroupCommandTest.java
``` java
public class FindGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void equals() {
        GroupsContainKeywordsPredicate firstPredicate =
                new GroupsContainKeywordsPredicate(Collections.singletonList("first"));
        GroupsContainKeywordsPredicate secondPredicate =
                new GroupsContainKeywordsPredicate(Collections.singletonList("second"));

        FindGroupCommand findGroupFirstCommand = new FindGroupCommand(firstPredicate);
        FindGroupCommand findGroupSecondCommand = new FindGroupCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findGroupFirstCommand.equals(findGroupFirstCommand));

        // same values -> returns true
        FindGroupCommand findGroupFirstCommandCopy = new FindGroupCommand(firstPredicate);
        assertTrue(findGroupFirstCommand.equals(findGroupFirstCommandCopy));

        // different types -> returns false
        assertFalse(findGroupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findGroupFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findGroupFirstCommand.equals(findGroupSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindGroupCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindGroupCommand command = prepareCommand("Neighbours twitter");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, ELLE));
    }

    /**
     * Parses {@code userInput} into a {@code FindGroupCommand}.
     */
    private FindGroupCommand prepareCommand(String userInput) {
        FindGroupCommand command =
                new FindGroupCommand(new GroupsContainKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindGroupCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### /java/seedu/address/logic/commands/FindPreferenceCommandTest.java
``` java
public class FindPreferenceCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void equals() {
        PreferencesContainKeywordsPredicate firstPredicate =
                new PreferencesContainKeywordsPredicate(Collections.singletonList("first"));
        PreferencesContainKeywordsPredicate secondPredicate =
                new PreferencesContainKeywordsPredicate(Collections.singletonList("second"));

        FindPreferenceCommand findPreferenceFirstCommand = new FindPreferenceCommand(firstPredicate);
        FindPreferenceCommand findPreferenceSecondCommand = new FindPreferenceCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findPreferenceFirstCommand.equals(findPreferenceFirstCommand));

        // same values -> returns true
        FindPreferenceCommand findPreferenceFirstCommandCopy = new FindPreferenceCommand(firstPredicate);
        assertTrue(findPreferenceFirstCommand.equals(findPreferenceFirstCommandCopy));

        // different types -> returns false
        assertFalse(findPreferenceFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findPreferenceFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findPreferenceFirstCommand.equals(findPreferenceSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindPreferenceCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindPreferenceCommand command = prepareCommand("videoGames shoes");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code FindPreferenceCommand}.
     */
    private FindPreferenceCommand prepareCommand(String userInput) {
        FindPreferenceCommand command =
                new FindPreferenceCommand(new PreferencesContainKeywordsPredicate(Arrays.asList(userInput
                        .split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPreferenceCommand command, String expectedMessage,
                                      List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### /java/seedu/address/logic/commands/ListCalendarEntryCommandTest.java
``` java
public class ListCalendarEntryCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCalendarEntryCommand listCalendarEntryCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalCalendarManagerWithEntries(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        listCalendarEntryCommand = new ListCalendarEntryCommand();
        listCalendarEntryCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCalendarEntryCommand, model, ListCalendarEntryCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/ListOrderCommandTest.java
``` java
public class ListOrderCommandTest {

    private Model model;
    private Model expectedModel;
    private ListOrderCommand listOrderCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithOrders(),  new CalendarManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        listOrderCommand = new ListOrderCommand();
        listOrderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listOrderCommand, model, ListOrderCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/ViewBackCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ViewBackCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_viewBackCommand_success() {
        assertShowPreviousPageSuccess();
    }

    @Test
    public void equals() {
        ViewBackCommand viewBackCommand = new ViewBackCommand();

        // same object -> returns true
        assertTrue(viewBackCommand.equals(viewBackCommand));

        // same values -> returns true
        ViewBackCommand viewBackCommandCopy = new ViewBackCommand();
        assertTrue(viewBackCommand.equals(viewBackCommandCopy));

        // different types -> returns false
        assertFalse(viewBackCommand.equals(1));

        // null -> returns false
        assertFalse(viewBackCommand.equals(null));
    }

    /**
     * Executes a {@code ViewBackCommand},
     * and checks that {@code ChangeCalendarPageRequestEvent} is raised with the Request Type "Back".
     */
    private void assertShowPreviousPageSuccess() {
        ViewBackCommand viewBackCommand = new ViewBackCommand();

        try {
            CommandResult commandResult = viewBackCommand.execute();
            assertEquals(ViewBackCommand.MESSAGE_VIEW_CALENDAR_BACK_SUCCESS,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ChangeCalendarPageRequestEvent lastEvent = (ChangeCalendarPageRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(ViewBackCommand.REQUEST_BACK, lastEvent.getRequestType());
    }
}
```
###### /java/seedu/address/logic/commands/ViewCalendarCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.DisplayCalendarRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ViewCalendarCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_showDayView_success() {
        assertShowDayViewSuccess("Day"); // Matches exact keyword in ViewCalendarCommand.DAY_VIEW
        assertShowDayViewSuccess("DAY"); // Case insensitive -> Success
        assertShowDayViewSuccess(""); // Empty string -> Success as Calendar shows Day page by default.
        assertShowDayViewSuccess("invalidDay"); // Parameter not matching the accepted keyword -> Success
    }

    @Test
    public void execute_showWeekView_success() {
        assertShowWeekViewSuccess("Week"); // Matches exact keyword in ViewCalendarCommand.WEEK_VIEW
        assertShowWeekViewSuccess("WEEK"); // Case insensitive -> Success
        assertShowWeekViewSuccess("  Week  "); //Trailing whitespaces -> Success
    }

    @Test
    public void execute_showMonthView_success() {
        assertShowMonthViewSuccess("Month"); // Matches exact keyword in ViewCalendarCommand.MONTH_VIEW
        assertShowMonthViewSuccess("MONTH"); // Case insensitive -> Success
        assertShowMonthViewSuccess("  month  "); //Trailing whitespaces -> Success
    }

    @Test
    public void equals() {
        ViewCalendarCommand viewCalendarFirstCommand = new ViewCalendarCommand(ViewCalendarCommand.DAY_VIEW);
        ViewCalendarCommand viewCalendarSecondCommand = new ViewCalendarCommand(ViewCalendarCommand.WEEK_VIEW);

        // same object -> returns true
        assertTrue(viewCalendarFirstCommand.equals(viewCalendarFirstCommand));

        // same values -> returns true
        ViewCalendarCommand selectFirstCommandCopy = new ViewCalendarCommand(ViewCalendarCommand.DAY_VIEW);
        assertTrue(viewCalendarFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewCalendarFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewCalendarFirstCommand.equals(null));

        // different view -> returns false
        assertFalse(viewCalendarFirstCommand.equals(viewCalendarSecondCommand));
    }

    /**
     * Executes a {@code ViewCalendarCommand} with the given {@code view},
     * and checks that {@code DisplayCalendarRequestEvent} is raised with the day view.
     */
    private void assertShowDayViewSuccess(String view) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(view);

        try {
            CommandResult commandResult = viewCalendarCommand.execute();
            assertEquals(String.format(ViewCalendarCommand.MESSAGE_SHOW_CALENDAR_SUCCESS, ViewCalendarCommand.DAY_VIEW),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        DisplayCalendarRequestEvent lastEvent = (DisplayCalendarRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(ViewCalendarCommand.DAY_VIEW, lastEvent.getView());
    }

    /**
     * Executes a {@code ViewCalendarCommand} with the given {@code view},
     * and checks that {@code DisplayCalendarRequestEvent} is raised with the week view.
     */
    private void assertShowWeekViewSuccess(String view) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(view);

        try {
            CommandResult commandResult = viewCalendarCommand.execute();
            assertEquals(String.format(ViewCalendarCommand.MESSAGE_SHOW_CALENDAR_SUCCESS,
                    ViewCalendarCommand.WEEK_VIEW), commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        DisplayCalendarRequestEvent lastEvent = (DisplayCalendarRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(ViewCalendarCommand.WEEK_VIEW, lastEvent.getView());
    }

    /**
     * Executes a {@code ViewCalendarCommand} with the given {@code view},
     * and checks that {@code DisplayCalendarRequestEvent} is raised with the month view.
     */
    private void assertShowMonthViewSuccess(String view) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(view);

        try {
            CommandResult commandResult = viewCalendarCommand.execute();
            assertEquals(String.format(ViewCalendarCommand.MESSAGE_SHOW_CALENDAR_SUCCESS,
                    ViewCalendarCommand.MONTH_VIEW), commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        DisplayCalendarRequestEvent lastEvent = (DisplayCalendarRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(ViewCalendarCommand.MONTH_VIEW, lastEvent.getView());
    }

}
```
###### /java/seedu/address/logic/commands/ViewNextCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ViewNextCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_viewNextCommand_success() {
        assertShowNextPageSuccess();
    }

    @Test
    public void equals() {
        ViewNextCommand viewNextFirstCommand = new ViewNextCommand();

        // same object -> returns true
        assertTrue(viewNextFirstCommand.equals(viewNextFirstCommand));

        // same values -> returns true
        ViewNextCommand viewNextFirstCommandCopy = new ViewNextCommand();
        assertTrue(viewNextFirstCommand.equals(viewNextFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewNextFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewNextFirstCommand.equals(null));
    }

    /**
     * Executes a {@code ViewNextCommand},
     * and checks that {@code ChangeCalendarPageRequestEvent} is raised with the Request Type "Next".
     */
    private void assertShowNextPageSuccess() {
        ViewNextCommand viewNextCommand = new ViewNextCommand();

        try {
            CommandResult commandResult = viewNextCommand.execute();
            assertEquals(ViewNextCommand.MESSAGE_VIEW_CALENDAR_NEXT_SUCCESS,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ChangeCalendarPageRequestEvent lastEvent = (ChangeCalendarPageRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(ViewNextCommand.REQUEST_NEXT, lastEvent.getRequestType());
    }
}
```
###### /java/seedu/address/logic/commands/ViewTodayCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ViewTodayCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_viewTodayCommand_success() {
        assertShowTodaySuccess();
    }

    @Test
    public void equals() {
        ViewTodayCommand viewTodayCommand = new ViewTodayCommand();

        // same object -> returns true
        assertTrue(viewTodayCommand.equals(viewTodayCommand));

        // same values -> returns true
        ViewTodayCommand viewTodayCommandCopy = new ViewTodayCommand();
        assertTrue(viewTodayCommand.equals(viewTodayCommandCopy));

        // different types -> returns false
        assertFalse(viewTodayCommand.equals(1));

        // null -> returns false
        assertFalse(viewTodayCommand.equals(null));
    }

    /**
     * Executes a {@code ViewTodayCommand},
     * and checks that {@code ChangeCalendarPageRequestEvent} is raised with the Request Type "Today".
     */
    private void assertShowTodaySuccess() {
        ViewTodayCommand viewNextCommand = new ViewTodayCommand();

        try {
            CommandResult commandResult = viewNextCommand.execute();
            assertEquals(ViewTodayCommand.MESSAGE_VIEW_CALENDAR_TODAY_SUCCESS,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ChangeCalendarPageRequestEvent lastEvent = (ChangeCalendarPageRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(ViewTodayCommand.REQUEST_TODAY, lastEvent.getRequestType());
    }
}
```
###### /java/seedu/address/logic/parser/AddEntryCommandParserTest.java
``` java
public class AddEntryCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE);

    private AddEntryCommandParser parser = new AddEntryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CalendarEntry expectedCalEvent = new CalendarEntryBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(VALID_START_TIME_MEET_BOSS)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                        + START_DATE_DESC_MEET_BOSS + ENTRY_TITLE_DESC_MEET_BOSS
                        + END_DATE_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple event title strings - last event title string accepted
        assertParseSuccess(parser, ENTRY_TITLE_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple start date strings - last start date string accepted
        assertParseSuccess(parser, START_DATE_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple end date strings - last end date string accepted
        assertParseSuccess(parser, END_DATE_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple start time strings - last start time string accepted
        assertParseSuccess(parser, START_TIME_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // multiple end time strings - last end time string accepted
        assertParseSuccess(parser, END_TIME_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                        + ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // No start Date - Start Date should match End Date
        CalendarEntry expectedCalEvent = new CalendarEntryBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_END_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(VALID_START_TIME_MEET_BOSS)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();


        assertParseSuccess(parser, ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // No Start Time - Start Time equals to 00:00
        expectedCalEvent = new CalendarEntryBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(STANDARD_START_TIME)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();


        assertParseSuccess(parser, ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS
                        + END_DATE_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));

        // No Start Date and No Start Time
        expectedCalEvent = new CalendarEntryBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_END_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(STANDARD_START_TIME)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();


        assertParseSuccess(parser, ENTRY_TITLE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS,
                new AddEntryCommand(expectedCalEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing Event Title prefix
        assertParseFailure(parser,  VALID_ENTRY_TITLE_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                MESSAGE_INVALID_FORMAT);

        // Missing End Date prefix
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + VALID_END_DATE_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                MESSAGE_INVALID_FORMAT);

        // Missing End Time prefix
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + VALID_END_TIME_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                MESSAGE_INVALID_FORMAT);
        // All prefixes missing
        assertParseFailure(parser,  VALID_ENTRY_TITLE_MEET_BOSS
                        + VALID_START_DATE_MEET_BOSS + VALID_END_DATE_MEET_BOSS
                        + VALID_END_TIME_MEET_BOSS + VALID_START_TIME_MEET_BOSS,
                MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid Event Title
        assertParseFailure(parser,  INVALID_ENTRY_TITLE_DESC
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);

        // Invalid Start Date
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + INVALID_START_DATE_DESC + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                StartDate.MESSAGE_START_DATE_CONSTRAINTS);

        // Invalid End Date
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + INVALID_END_DATE_DESC
                        + END_TIME_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS,
                EndDate.MESSAGE_END_DATE_CONSTRAINTS);

        // Invalid Start Time
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + END_TIME_DESC_MEET_BOSS + INVALID_START_TIME_DESC,
                StartTime.MESSAGE_START_TIME_CONSTRAINTS);

        // Invalid End Time
        assertParseFailure(parser,  ENTRY_TITLE_DESC_MEET_BOSS
                        + START_DATE_DESC_MEET_BOSS + END_DATE_DESC_MEET_BOSS
                        + INVALID_END_TIME_DESC + START_TIME_DESC_MEET_BOSS,
                EndTime.MESSAGE_END_TIME_CONSTRAINTS);

        // Start Date later than End Date
        assertParseFailure(parser, ENTRY_TITLE_DESC_MEET_BOSS + INVALID_START_DATE_LATER_THAN_END_DATE_DESC
                        + END_DATE_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS,
                EntryTimeConstraintsUtil.START_AND_END_DATE_CONSTRAINTS);

        // Start Time later than End Time for same Start Date and End Date
        assertParseFailure(parser, ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS
                        + END_DATE_DESC_MEET_BOSS + INVALID_START_TIME_LATER_THAN_END_TIME_DESC
                        + END_TIME_DESC_MEET_BOSS,
                EntryTimeConstraintsUtil.START_AND_END_TIME_CONSTRAINTS);

        // Start Time less than 15 minutes from End Time for same Start Date and End Date
        assertParseFailure(parser, ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS
                        + END_DATE_DESC_MEET_BOSS + INVALID_START_TIME_LESS_THAN_FIFTEEN_MINUTES_FROM_END_TIME_DESC
                        + END_TIME_DESC_MEET_BOSS,
                EntryTimeConstraintsUtil.ENTRY_DURATION_CONSTRAINTS);
    }
}
```
###### /java/seedu/address/logic/parser/EditEntryCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.ENTRY_TITLE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.ENTRY_TITLE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENTRY_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_BOSS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ENTRY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;
import seedu.address.testutil.EditEntryDescriptorBuilder;

public class EditEntryCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEntryCommand.MESSAGE_USAGE);

    private EditEntryCommandParser parser = new EditEntryCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ENTRY_TITLE_MEET_BOSS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEntryCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ENTRY_TITLE_DESC_MEET_BOSS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ENTRY_TITLE_DESC_MEET_BOSS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 o/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ENTRY_TITLE_DESC,
                EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS); // invalid entry title

        assertParseFailure(parser, "1"
                + INVALID_START_DATE_DESC, StartDate.MESSAGE_START_DATE_CONSTRAINTS); // invalid start date

        assertParseFailure(parser, "1" + INVALID_END_DATE_DESC,
                EndDate.MESSAGE_END_DATE_CONSTRAINTS); // invalid end date

        assertParseFailure(parser, "1" + INVALID_START_TIME_DESC,
                StartTime.MESSAGE_START_TIME_CONSTRAINTS); // invalid start time

        assertParseFailure(parser, "1" + INVALID_END_TIME_DESC,
                EndTime.MESSAGE_END_TIME_CONSTRAINTS); // invalid end time

        // invalid entry title followed by valid start date
        assertParseFailure(parser, "1" + INVALID_ENTRY_TITLE_DESC + START_DATE_DESC_MEET_BOSS,
                EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);

        // valid start date followed by invalid start date
        assertParseFailure(parser, "1" + START_DATE_DESC_MEET_BOSS + INVALID_START_DATE_DESC,
                StartDate.MESSAGE_START_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_END_DATE_DESC + INVALID_END_TIME_DESC
                + VALID_ENTRY_TITLE_MEET_BOSS, EndDate.MESSAGE_END_DATE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ENTRY;
        String userInput = targetIndex.getOneBased() + ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS
                + END_DATE_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS;

        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(VALID_START_TIME_MEET_BOSS)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();
        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ENTRY;
        String userInput = targetIndex.getOneBased() + ENTRY_TITLE_DESC_GET_STOCKS + END_DATE_DESC_GET_STOCKS;

        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS)
                .withEndDate(VALID_END_DATE_GET_STOCKS).build();
        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_ENTRY;

        // entry title
        String userInput = targetIndex.getOneBased() + ENTRY_TITLE_DESC_GET_STOCKS;
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).build();
        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date
        userInput = targetIndex.getOneBased() + START_DATE_DESC_GET_STOCKS;
        descriptor = new EditEntryDescriptorBuilder()
                .withStartDate(VALID_START_DATE_GET_STOCKS).build();
        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end date
        userInput = targetIndex.getOneBased() + END_DATE_DESC_GET_STOCKS;
        descriptor = new EditEntryDescriptorBuilder()
                .withEndDate(VALID_END_DATE_GET_STOCKS).build();
        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start time
        userInput = targetIndex.getOneBased() + START_TIME_DESC_GET_STOCKS;
        descriptor = new EditEntryDescriptorBuilder()
                .withStartTime(VALID_START_TIME_GET_STOCKS).build();
        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end time
        userInput = targetIndex.getOneBased() + END_TIME_DESC_GET_STOCKS;
        descriptor = new EditEntryDescriptorBuilder()
                .withEndTime(VALID_END_TIME_GET_STOCKS).build();
        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_SECOND_ENTRY;
        String userInput = targetIndex.getOneBased()
                + ENTRY_TITLE_DESC_MEET_BOSS + ENTRY_TITLE_DESC_GET_STOCKS
                + START_DATE_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                + END_DATE_DESC_MEET_BOSS + END_DATE_DESC_GET_STOCKS
                + START_TIME_DESC_GET_STOCKS + START_TIME_DESC_MEET_BOSS
                + END_TIME_DESC_MEET_BOSS + END_TIME_DESC_GET_STOCKS;

        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_GET_STOCKS).withStartTime(VALID_START_TIME_MEET_BOSS)
                .withEndTime(VALID_END_TIME_GET_STOCKS).build();

        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ENTRY;
        String userInput = targetIndex.getOneBased() + INVALID_ENTRY_TITLE_DESC + ENTRY_TITLE_DESC_GET_STOCKS;
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).build();

        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + END_TIME_DESC_MEET_BOSS + INVALID_START_DATE_DESC
                + ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS;

        descriptor = new EditEntryDescriptorBuilder()
                .withEndTime(VALID_END_TIME_MEET_BOSS).withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS).build();

        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/address/logic/parser/FindGroupCommandParserTest.java
``` java
public class FindGroupCommandParserTest {

    private FindGroupCommandParser parser = new FindGroupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindGroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindGroupCommand() {
        // no leading and trailing whitespaces
        FindGroupCommand expectedFindGroupCommand =
                new FindGroupCommand(new GroupsContainKeywordsPredicate(Arrays.asList("Friends", "Colleagues")));
        assertParseSuccess(parser, "Friends Colleagues", expectedFindGroupCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Friends \n \t Colleagues  \t", expectedFindGroupCommand);
    }
}
```
###### /java/seedu/address/logic/parser/FindPreferenceCommandParserTest.java
``` java
public class FindPreferenceCommandParserTest {
    private FindPreferenceCommandParser parser = new FindPreferenceCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPreferenceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPreferenceCommand() {
        // no leading and trailing whitespaces
        FindPreferenceCommand expectedFindPreferenceCommand =
                new FindPreferenceCommand(new PreferencesContainKeywordsPredicate(Arrays.asList("Computers", "Shoes")));
        assertParseSuccess(parser, "Computers Shoes", expectedFindPreferenceCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Computers \n \t Shoes  \t", expectedFindPreferenceCommand);
    }
}
```
###### /java/seedu/address/model/event/CalendarEntryTest.java
``` java
public class CalendarEntryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new CalendarEntry(null, null, null, null, null));

    }
}
```
###### /java/seedu/address/model/event/EndDateTest.java
``` java
public class EndDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EndDate(null));
    }

    @Test
    public void constructor_invalidEndDate_throwsIllegalArgumentException() {
        String invalidEndDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EndDate(invalidEndDate));
    }

    @Test
    public void isValidEndDate() {
        // null end date
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.isValidDate(null));

        // invalid end date
        assertFalse(DateUtil.isValidDate("")); // empty string
        assertFalse(DateUtil.isValidDate(" ")); // spaces only
        assertFalse(DateUtil.isValidDate("wejo*21")); // invalid string
        assertFalse(DateUtil.isValidDate("12/12/2012")); // invalid format
        assertFalse(DateUtil.isValidDate("0-1-98")); // invalid date
        assertFalse(DateUtil.isValidDate("50-12-1998")); // invalid day
        assertFalse(DateUtil.isValidDate("10-15-2013")); // invalid month
        assertFalse(DateUtil.isValidDate("09-08-10000")); // invalid year

        // valid end date
        assertTrue(DateUtil.isValidDate("01-01-2001")); // valid date
        assertTrue(DateUtil.isValidDate("29-02-2000")); // leap year
    }
}
```
###### /java/seedu/address/model/event/EndTimeTest.java
``` java
public class EndTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EndTime(null));
    }

    @Test
    public void constructor_invalidEndTime_throwsIllegalArgumentException() {
        String invalidEndTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EndTime(invalidEndTime));
    }

    @Test
    public void isValidEndTime() {
        // null end time
        Assert.assertThrows(NullPointerException.class, () -> TimeUtil.isValidTime(null));

        // invalid end time
        assertFalse(TimeUtil.isValidTime("")); // empty string
        assertFalse(TimeUtil.isValidTime(" ")); // spaces only
        assertFalse(TimeUtil.isValidTime("wejo*21")); // invalid string
        assertFalse(TimeUtil.isValidTime("12-01")); // invalid format
        assertFalse(TimeUtil.isValidTime("24:01")); // invalid Hour
        assertFalse(TimeUtil.isValidTime("00:79")); // invalid Minute
        assertFalse(TimeUtil.isValidTime("101:04")); // invalid Hour
        assertFalse(TimeUtil.isValidTime("00:100")); // invalid Minute

        // valid end time
        assertTrue(TimeUtil.isValidTime("10:00")); // valid date
        assertTrue(TimeUtil.isValidTime("18:55")); // valid date (24Hr Format)
    }
}
```
###### /java/seedu/address/model/event/EntryTitleTest.java
``` java
public class EntryTitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EntryTitle(null));
    }

    @Test
    public void constructor_invalidEventTitle_throwsIllegalArgumentException() {
        String invalidEventTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EntryTitle(invalidEventTitle));
    }

    @Test
    public void isValidEventTitle() {
        // null event title
        Assert.assertThrows(NullPointerException.class, () -> EntryTitle.isValidEntryTitle(null));

        // invalid event title
        assertFalse(EntryTitle.isValidEntryTitle("")); // empty string
        assertFalse(EntryTitle.isValidEntryTitle(" ")); // spaces only

        // valid event title
        assertTrue(EntryTitle.isValidEntryTitle("Meet with bosses"));
        assertTrue(EntryTitle.isValidEntryTitle("Meet Client for stocks"));
        assertTrue(EntryTitle.isValidEntryTitle("Confectionery Boxes Order"));
    }
}
```
###### /java/seedu/address/model/event/StartDateTest.java
``` java
public class StartDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartDate(null));
    }

    @Test
    public void constructor_invalidStartDate_throwsIllegalArgumentException() {
        String invalidStartDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartDate(invalidStartDate));
    }

    @Test
    public void isValidStartDate() {
        // null start date
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.isValidDate(null));

        // invalid start date
        assertFalse(DateUtil.isValidDate("")); // empty string
        assertFalse(DateUtil.isValidDate(" ")); // spaces only
        assertFalse(DateUtil.isValidDate("wejo*21")); // invalid string
        assertFalse(DateUtil.isValidDate("12/12/2012")); // invalid format
        assertFalse(DateUtil.isValidDate("0-1-98")); // invalid date
        assertFalse(DateUtil.isValidDate("50-12-1998")); // invalid day
        assertFalse(DateUtil.isValidDate("10-15-2013")); // invalid month
        assertFalse(DateUtil.isValidDate("09-08-10000")); // invalid year

        // valid start date
        assertTrue(DateUtil.isValidDate("01-01-2001")); // valid date
        assertTrue(DateUtil.isValidDate("29-02-2000")); // leap year
    }
}
```
###### /java/seedu/address/model/event/StartTimeTest.java
``` java
public class StartTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartTime(null));
    }

    @Test
    public void constructor_invalidStartTime_throwsIllegalArgumentException() {
        String invalidStartTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartTime(invalidStartTime));
    }

    @Test
    public void isValidStartTime() {
        // null start time
        Assert.assertThrows(NullPointerException.class, () -> TimeUtil.isValidTime(null));

        // invalid start time
        assertFalse(TimeUtil.isValidTime("")); // empty string
        assertFalse(TimeUtil.isValidTime(" ")); // spaces only
        assertFalse(TimeUtil.isValidTime("wejo*21")); // invalid string
        assertFalse(TimeUtil.isValidTime("12-01")); // invalid format
        assertFalse(TimeUtil.isValidTime("24:01")); // invalid Hour
        assertFalse(TimeUtil.isValidTime("00:79")); // invalid Minute
        assertFalse(TimeUtil.isValidTime("101:04")); // invalid Hour
        assertFalse(TimeUtil.isValidTime("00:100")); // invalid Minute

        // valid start time
        assertTrue(TimeUtil.isValidTime("10:00")); // valid date
        assertTrue(TimeUtil.isValidTime("18:55")); // valid date (24Hr Format)
    }
}
```
###### /java/seedu/address/model/UniqueCalendarEntryListTest.java
``` java
public class UniqueCalendarEntryListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() throws DuplicateCalendarEntryException {
        UniqueCalendarEntryList firstEntriesList =  new UniqueCalendarEntryList();
        firstEntriesList.add(MEETING_BOSS);
        UniqueCalendarEntryList secondEntriesList = new UniqueCalendarEntryList();
        secondEntriesList.add(GET_STOCKS);

        // Same object -> True
        assertTrue(firstEntriesList.equals(firstEntriesList));

        // different type -> false
        assertFalse(firstEntriesList.equals(1));

        // different objects, same type -> false
        assertFalse(firstEntriesList.equals(secondEntriesList));
    }

    @Test
    public void asOrderInsensitiveList_compareListsWithSameItemsInDiffOrder_assertEqual()
            throws DuplicateCalendarEntryException {

        UniqueCalendarEntryList firstEntriesList =  new UniqueCalendarEntryList();
        firstEntriesList.add(MEETING_BOSS);
        firstEntriesList.add(GET_STOCKS);
        UniqueCalendarEntryList secondEntries = new UniqueCalendarEntryList();
        secondEntries.add(GET_STOCKS);
        secondEntries.add(MEETING_BOSS);

        assertTrue(firstEntriesList.equalsOrderInsensitive(secondEntries));
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCalendarEntryList calendarEntriesList = new UniqueCalendarEntryList();
        thrown.expect(UnsupportedOperationException.class);
        calendarEntriesList.asObservableList().remove(0);
    }

    @Test
    public void asUniqueList_addDuplicateCalendarEntries_throwsDuplicateCalendarEntryException()
            throws DuplicateCalendarEntryException {

        UniqueCalendarEntryList calendarEntriesList = new UniqueCalendarEntryList();
        thrown.expect(DuplicateCalendarEntryException.class);
        calendarEntriesList.add(MEETING_BOSS);
        calendarEntriesList.add(MEETING_BOSS);
    }
}
```
###### /java/seedu/address/model/UniqueGroupListTest.java
``` java
public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

```
###### /java/seedu/address/model/UniquePreferenceListTest.java
``` java
public class UniquePreferenceListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

```
###### /java/seedu/address/storage/XmlAdaptedCalendarEntryTest.java
``` java
public class XmlAdaptedCalendarEntryTest {
    private static final String INVALID_EVENT_TITLE = "M&&ting wi$h b@ss";
    private static final String INVALID_START_DATE = "30-02-2019";
    private static final String INVALID_END_DATE = "31-02-2019";
    private static final String INVALID_START_TIME = "24:60";
    private static final String INVALID_END_TIME = "25:100";

    private static final String VALID_EVENT_TITLE = MEETING_BOSS.getEntryTitle().toString();
    private static final String VALID_START_DATE = MEETING_BOSS.getStartDate().toString();
    private static final String VALID_END_DATE = MEETING_BOSS.getEndDate().toString();
    private static final String VALID_START_TIME = MEETING_BOSS.getStartTime().toString();
    private static final String VALID_END_TIME = MEETING_BOSS.getEndTime().toString();

    @Test
    public void toModelType_validCalendarEventDetails_returnsCalendarEvent() throws Exception {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(MEETING_BOSS);
        assertEquals(MEETING_BOSS, calEvent.toModelType());
    }

    @Test
    public void toModelType_invalidEventTitle_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(INVALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullEventTitle_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(null, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EntryTitle.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, INVALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = StartDate.MESSAGE_START_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, null,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                INVALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = EndDate.MESSAGE_END_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                null, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, INVALID_START_TIME, VALID_END_TIME);
        String expectedMessage = StartTime.MESSAGE_START_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, null, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, INVALID_END_TIME);
        String expectedMessage = EndTime.MESSAGE_END_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_EVENT_TITLE, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }
}
```
###### /java/seedu/address/storage/XmlCalendarManagerStorageTest.java
``` java
public class XmlCalendarManagerStorageTest {
    private static final String TEST_DATA_FOLDER =
            FileUtil.getPath("./src/test/data/XmlCalendarManagerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readCalendarManager_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readCalendarManager(null);
    }

    private Optional<ReadOnlyCalendarManager> readCalendarManager(String filePath) throws Exception {
        return new XmlCalendarManagerStorage(filePath).readCalendarManager(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCalendarManager("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readCalendarManager("NotXmlFormatCalendarManager.xml");
    }

    @Test
    public void readCalendarManager_invalidCalendarManager_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCalendarManager("invalidCalendarManager.xml");
    }

    @Test
    public void readCalendarManager_invalidAndValidCalendarManager_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCalendarManager("invalidAndValidCalendarManager.xml");
    }

    @Test
    public void readAndSaveCalendarManager_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        CalendarManager original = getTypicalCalendarManagerWithEntries();
        XmlCalendarManagerStorage xmlCalendarManagerStorage = new XmlCalendarManagerStorage(filePath);

        //Save in new file and read back
        xmlCalendarManagerStorage.saveCalendarManager(original, filePath);
        ReadOnlyCalendarManager readBack = xmlCalendarManagerStorage.readCalendarManager(filePath).get();
        assertEquals(original, new CalendarManager(readBack));

        //Modify data, overwrite exiting file, and read back
        original.deleteCalendarEntry(WORKSHOP);
        xmlCalendarManagerStorage.saveCalendarManager(original, filePath);
        readBack = xmlCalendarManagerStorage.readCalendarManager(filePath).get();
        assertEquals(original, new CalendarManager(readBack));

        //Save and read without specifying file path
        original.addCalendarEntry(WORKSHOP);
        xmlCalendarManagerStorage.saveCalendarManager(original); //file path not specified
        readBack = xmlCalendarManagerStorage.readCalendarManager().get(); //file path not specified
        assertEquals(original, new CalendarManager(readBack));

    }

    @Test
    public void saveCalendarManager_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCalendarManager(null, "SomeFile.xml");
    }

    /**
     * Saves {@code calendarManager} at the specified {@code filePath}.
     */
    private void saveCalendarManager(ReadOnlyCalendarManager calendarManager, String filePath) {
        try {
            new XmlCalendarManagerStorage(filePath)
                    .saveCalendarManager(calendarManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveCalendarManager(new CalendarManager(), null);
    }


}
```
###### /java/seedu/address/storage/XmlSerializableCalendarManagerTest.java
``` java
package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.CalendarManager;
import seedu.address.testutil.TypicalCalendarEntries;

public class XmlSerializableCalendarManagerTest {

    private static final String TEST_DATA_FOLDER =
            FileUtil.getPath("src/test/data/XmlSerializableCalendarManagerTest/");
    private static final File TYPICAL_EVENTS_FILE =
            new File(TEST_DATA_FOLDER + "typicalEntriesCalendarManager.xml");
    private static final File INVALID_EVENT_FILE =
            new File(TEST_DATA_FOLDER + "invalidEntriesCalendarManager.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalEntriesFile_success() throws Exception {
        XmlSerializableCalendarManager dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EVENTS_FILE,
                XmlSerializableCalendarManager.class);
        CalendarManager calendarManagerFromFile = dataFromFile.toModelType();
        CalendarManager typicalCalendarEntriesCalendarManager =
                TypicalCalendarEntries.getTypicalCalendarManagerWithEntries();
        assertEquals(calendarManagerFromFile, typicalCalendarEntriesCalendarManager);
    }

    @Test
    public void toModelType_invalidEventsFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCalendarManager dataFromFile = XmlUtil.getDataFromFile(INVALID_EVENT_FILE,
                XmlSerializableCalendarManager.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
```
###### /java/seedu/address/testutil/CalendarEntryBuilder.java
``` java
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

/**
 * A utility class to help with building CalendarEntry objects.
 */
public class CalendarEntryBuilder {

    public static final String DEFAULT_EVENT_TITLE = "Meeting with boss";
    public static final String DEFAULT_START_DATE = "10-10-2018";
    public static final String DEFAULT_END_DATE = "10-10-2018";
    public static final String DEFAULT_START_TIME = "10:00";
    public static final String DEFAULT_END_TIME = "12:00";

    private EntryTitle entryTitle;
    private StartDate startDate;
    private EndDate endDate;
    private StartTime startTime;
    private EndTime endTime;

    public CalendarEntryBuilder() {
        entryTitle = new EntryTitle(DEFAULT_EVENT_TITLE);
        startDate = new StartDate(DEFAULT_START_DATE);
        endDate = new EndDate(DEFAULT_END_DATE);
        startTime = new StartTime(DEFAULT_START_TIME);
        endTime = new EndTime(DEFAULT_END_TIME);
    }

    /**
     * Initializes the CalendarEntryBuilder with the data of {@code entryToCopy}.
     */
    public CalendarEntryBuilder(CalendarEntry entryToCopy) {
        entryTitle = entryToCopy.getEntryTitle();
        startDate = entryToCopy.getStartDate();
        endDate = entryToCopy.getEndDate();
        startTime = entryToCopy.getStartTime();
        endTime = entryToCopy.getEndTime();
    }

    /**
     * Sets the {@code EntryTitle} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withEntryTitle(String eventTitle) {
        this.entryTitle = new EntryTitle(eventTitle);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withStartDate(String startDate) {
        this.startDate = new StartDate(startDate);
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withEndDate(String endDate) {
        this.endDate = new EndDate(endDate);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withStartTime(String startTime) {
        this.startTime = new StartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code CalendarEntry} that we are building.
     */
    public CalendarEntryBuilder withEndTime(String endTime) {
        this.endTime = new EndTime(endTime);
        return this;
    }

    public CalendarEntry build() {
        return new CalendarEntry(entryTitle, startDate, endDate, startTime, endTime);
    }
}
```
###### /java/seedu/address/testutil/CalendarEntryUtil.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.model.event.CalendarEntry;

/**
 * A utility class for {@code CalendarEntry}.
 */
public class CalendarEntryUtil {

    /**
     * Returns an add entry command string for adding the {@code calendarEntry}.
     */
    public static String getAddEntryCommand(CalendarEntry calendarEntry) {
        return AddEntryCommand.COMMAND_WORD + " " + getCalendarEntryDetails(calendarEntry);
    }

    /**
     * returns part of command string for the given {@code calEvent}'s details.
     */
    public static String getCalendarEntryDetails(CalendarEntry calEntry) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ENTRY_TITLE + calEntry.getEntryTitle().toString() + " ");
        sb.append(PREFIX_START_DATE + calEntry.getStartDate().toString() + " ");
        sb.append(PREFIX_END_DATE + calEntry.getEndDate().toString() + " ");
        sb.append(PREFIX_START_TIME + calEntry.getStartTime().toString() + " ");
        sb.append(PREFIX_END_TIME + calEntry.getEndTime().toString());
        return sb.toString();
    }
}
```
###### /java/seedu/address/testutil/EditEntryDescriptorBuilder.java
``` java
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

/**
 * A utility class to help with building EditEntryDescriptor objects.
 */
public class EditEntryDescriptorBuilder {
    private EditEntryCommand.EditEntryDescriptor descriptor;

    public EditEntryDescriptorBuilder() {
        descriptor = new EditEntryDescriptor();
    }

    public EditEntryDescriptorBuilder(EditEntryDescriptor descriptor) {
        this.descriptor = new EditEntryDescriptor(descriptor);
    }

    public EditEntryDescriptorBuilder(CalendarEntry entry) {
        descriptor = new EditEntryDescriptor();
        descriptor.setEntryTitle(entry.getEntryTitle());
        descriptor.setStartDate(entry.getStartDate());
        descriptor.setEndDate(entry.getEndDate());
        descriptor.setStartTime(entry.getStartTime());
        descriptor.setEndTime(entry.getEndTime());
    }

    /**
     * Sets the {@code EntryTitle} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withEntryTitle(String entryTitle) {
        descriptor.setEntryTitle(new EntryTitle(entryTitle));
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(new StartDate(startDate));
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(new EndDate(endDate));
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new StartTime(startTime));
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(new EndTime(endTime));
        return this;
    }

    public EditEntryDescriptor build() {
        return descriptor;
    }
}
```
###### /java/seedu/address/testutil/TypicalCalendarEntries.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.CalendarManager;
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.exceptions.DuplicateCalendarEntryException;

/**
 * A utility class containing a list of {@code CalendarEntry} objects to be used in tests.
 */
public class TypicalCalendarEntries {
    public static final CalendarEntry MEETING_BOSS = new CalendarEntryBuilder()
            .withEntryTitle("Meeting with boss")
            .withStartDate("06-06-2018")
            .withEndDate("06-06-2018")
            .withStartTime("10:00")
            .withEndTime("12:00").build();

    public static final CalendarEntry GET_STOCKS = new CalendarEntryBuilder()
            .withEntryTitle("Get stocks from supplier")
            .withStartDate("01-07-2018")
            .withEndDate("01-07-2018")
            .withStartTime("08:00")
            .withEndTime("12:30").build();

    public static final CalendarEntry ROAD_SHOW = new CalendarEntryBuilder()
            .withEntryTitle("Road Show at Orchard")
            .withStartDate("02-05-2018")
            .withEndDate("06-05-2018")
            .withStartTime("09:00")
            .withEndTime("19:00").build();

    public static final CalendarEntry WORKSHOP = new CalendarEntryBuilder()
            .withEntryTitle("Workshop")
            .withStartDate("28-05-2018")
            .withEndDate("29-05-2018")
            .withStartTime("10:00")
            .withEndTime("15:00").build();

    private TypicalCalendarEntries() {} // prevents instantiation


    public static CalendarManager getTypicalCalendarManagerWithEntries() {
        CalendarManager cm = new CalendarManager();

        for (CalendarEntry calEvent : getTypicalCalendarEntries()) {
            try {
                cm.addCalendarEntry(calEvent);
            } catch (DuplicateCalendarEntryException dcee) {
                throw new AssertionError("not possible");
            }
        }
        return cm;
    }

    public static List<CalendarEntry> getTypicalCalendarEntries() {
        return new ArrayList<>(Arrays.asList(MEETING_BOSS, GET_STOCKS, ROAD_SHOW, WORKSHOP));
    }
}
```
###### /java/seedu/address/testutil/TypicalGroups.java
``` java
package seedu.address.testutil;

import seedu.address.model.tag.Group;


/**
 * A utility class containing a list of {@code Group} Objects to be used in tests.
 */
public class TypicalGroups {
    public static final Group FRIENDS = new Group("friends");
    public static final Group COLLEAGUES = new Group("colleagues");
    public static final Group BUDDIES = new Group("buddies");
    public static final Group FAMILY = new Group("family");
    public static final Group TWITTER = new Group("twitter");
    public static final Group NEIGHBOURS = new Group("neighbours");

    private TypicalGroups() {}

}
```
###### /java/seedu/address/testutil/TypicalPreferences.java
``` java
package seedu.address.testutil;

import seedu.address.model.tag.Preference;

/**
 * A utility class containing a list of {@code Preference} Objects to be used in tests.
 */
public class TypicalPreferences {

    public static final Preference SHOES = new Preference("shoes");
    public static final Preference COMPUTERS = new Preference("computers");
    public static final Preference VIDEO_GAMES = new Preference("videoGames");
    public static final Preference NECKLACES = new Preference("necklaces");

    private TypicalPreferences() {}

}
```
###### /java/seedu/address/ui/CalendarEntryCardTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.Test;

import guitests.guihandles.CalendarEntryCardHandle;
import seedu.address.model.event.CalendarEntry;
import seedu.address.testutil.CalendarEntryBuilder;

public class CalendarEntryCardTest extends GuiUnitTest {
    @Test
    public void display() {
        CalendarEntry calendarEntry = new CalendarEntryBuilder().build();
        CalendarEntryCard calendarEntryCard = new CalendarEntryCard(calendarEntry, 1);
        uiPartRule.setUiPart(calendarEntryCard);
        assertCardDisplay(calendarEntryCard, calendarEntry, 1);
    }

    @Test
    public void equals() {
        CalendarEntry calendarEntry = new CalendarEntryBuilder().build();
        CalendarEntryCard calendarEntryCard = new CalendarEntryCard(calendarEntry, 0);

        // same calendar entry, same index -> returns true
        CalendarEntryCard entryCardCopy = new CalendarEntryCard(calendarEntry, 0);
        assertTrue(calendarEntryCard.equals(entryCardCopy));

        // same object -> returns true
        assertTrue(calendarEntryCard.equals(calendarEntryCard));

        // null -> returns false
        assertFalse(calendarEntryCard.equals(null));

        // different types -> returns false
        assertFalse(calendarEntryCard.equals(1));

        // different order, same index -> returns false
        CalendarEntry differentEntry = new CalendarEntryBuilder().withEntryTitle("differentTitle").build();
        assertFalse(calendarEntryCard.equals(new CalendarEntryCard(differentEntry, 0)));

        // same order, different index -> returns false
        assertFalse(calendarEntryCard.equals(new CalendarEntryCard(calendarEntry, 1)));

    }

    /**
     * Asserts that {@code orderCard} displays the details of {@code expectedOrder} correctly and
     * matches {@code expectedId}.
     */
    private void assertCardDisplay(CalendarEntryCard calendarEntryCard, CalendarEntry expectedEntry, int expectedId) {
        guiRobot.pauseForHuman();

        CalendarEntryCardHandle entryCardHandle = new CalendarEntryCardHandle(calendarEntryCard.getRoot());

        // verify that id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", entryCardHandle.getId());

        // verify order details are displayed correctly
        assertCardDisplaysEntry(expectedEntry, entryCardHandle);
    }
}
```
###### /java/seedu/address/ui/CalendarEntryListPanelTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarEntries;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CalendarEntryCardHandle;
import guitests.guihandles.CalendarEntryListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.CalendarEntry;

public class CalendarEntryListPanelTest extends GuiUnitTest {
    private static final ObservableList<CalendarEntry> TYPICAL_CAL_ENTRIES =
            FXCollections.observableList(getTypicalCalendarEntries());

    private CalendarEntryListPanelHandle calendarEntryListPanelHandle;

    @Before
    public void setUp() {
        CalendarEntryListPanel calendarEntryListPanel = new CalendarEntryListPanel(TYPICAL_CAL_ENTRIES);
        uiPartRule.setUiPart(calendarEntryListPanel);

        calendarEntryListPanelHandle = new CalendarEntryListPanelHandle(getChildNode(calendarEntryListPanel.getRoot(),
                calendarEntryListPanelHandle.CALENDAR_ENTRY_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_CAL_ENTRIES.size(); i++) {
            calendarEntryListPanelHandle.navigateToCard(TYPICAL_CAL_ENTRIES.get(i));
            CalendarEntry expectedCalEntry = TYPICAL_CAL_ENTRIES.get(i);
            CalendarEntryCardHandle actualCard = calendarEntryListPanelHandle.getCalendarEntryCardHandle(i);

            assertCardDisplaysEntry(expectedCalEntry, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
```
###### /java/systemtests/DeleteGroupCommandSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_GROUP_NOT_FOUND;
import static seedu.address.testutil.TypicalGroups.BUDDIES;
import static seedu.address.testutil.TypicalGroups.FRIENDS;
import static seedu.address.testutil.TypicalGroups.NEIGHBOURS;
import static seedu.address.testutil.TypicalGroups.TWITTER;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.exceptions.GroupNotFoundException;

public class DeleteGroupCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_GROUP_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);

    @Test
    public void deleteGroup() {
        /* ------------- Performing delete group operation while an unfiltered list is being shown -------------- */

        /* Case: delete the group "twitter" in the list, command with leading spaces and trailing spaces ->
        deleted */
        Model expectedModel = getModel();
        Model modelBeforeDeletingLast = getModel();
        String command = "     " + DeleteGroupCommand.COMMAND_WORD + "      " + TWITTER.tagName + "       ";
        Group deletedGroup = TWITTER;
        removeGroup(expectedModel, TWITTER);
        String expectedResultMessage = String.format(MESSAGE_DELETE_GROUP_SUCCESS, deletedGroup);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: undo deleting the group "twitter" in the list -> group "twitter" restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the group "twitter" in the list -> "twitter" deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeGroup(modelBeforeDeletingLast, TWITTER);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        assertEquals(expectedModel, modelBeforeDeletingLast);
        /* -------------- Performing delete group operation while a filtered list is being shown ------------------ */

        /* Case: filtered person list, delete existing group but not in filtered person list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        expectedModel = getModel();
        deletedGroup = FRIENDS;
        removeGroup(expectedModel, FRIENDS);
        command = DeleteGroupCommand.COMMAND_WORD + " " + FRIENDS.tagName;
        expectedResultMessage = String.format(MESSAGE_DELETE_GROUP_SUCCESS, deletedGroup);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: filtered person list, delete non-existing group in address book -> rejected */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Group invalidGroup = BUDDIES;
        command = DeleteGroupCommand.COMMAND_WORD + " " + invalidGroup.tagName;
        assertCommandFailure(command, MESSAGE_GROUP_NOT_FOUND);

        /* ----------------- Performing delete group operation while a person card is selected -------------------- */

        /* Case: delete group existing in the selected person -> person list panel still selects the person */
        showAllPersons();
        expectedModel = getModel();
        deletedGroup = NEIGHBOURS;
        Index selectedIndex = Index.fromOneBased(5);
        Index expectedIndex = selectedIndex;
        selectPerson(selectedIndex);
        command = DeleteGroupCommand.COMMAND_WORD + " " + NEIGHBOURS.tagName;
        removeGroup(expectedModel, NEIGHBOURS);
        expectedResultMessage = String.format(MESSAGE_DELETE_GROUP_SUCCESS, deletedGroup);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------- Performing invalid delete preference operation ------------------------------ */

        /* Case: invalid arguments (non-alphanumeric arguments) -> rejected */
        assertCommandFailure(DeleteGroupCommand.COMMAND_WORD + " fr!end3",
                MESSAGE_INVALID_DELETE_GROUP_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteGroupCommand.COMMAND_WORD + " friends twitter",
                MESSAGE_INVALID_DELETE_GROUP_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("GrouPDeletE neighbours", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Group} in {@code model}'s address book.
     */
    private void removeGroup(Model model, Group toDelete) {
        try {
            model.deleteGroup(toDelete);
        } catch (GroupNotFoundException gnfe) {
            throw new AssertionError("Group should exist in address book.");
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteGroupCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}


```
###### /java/systemtests/DeletePreferenceCommandSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeletePreferenceCommand.MESSAGE_DELETE_PREFERENCE_SUCCESS;
import static seedu.address.logic.commands.DeletePreferenceCommand.MESSAGE_PREFERENCE_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPreferences.COMPUTERS;
import static seedu.address.testutil.TypicalPreferences.NECKLACES;
import static seedu.address.testutil.TypicalPreferences.VIDEO_GAMES;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePreferenceCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.exceptions.PreferenceNotFoundException;

public class DeletePreferenceCommandSystemTest extends AddressBookSystemTest {
    private static final String MESSAGE_INVALID_DELETE_PREFERENCE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePreferenceCommand.MESSAGE_USAGE);

    @Test
    public void deletePreference() {
        /* ------------ Performing delete preference operation while an unfiltered list is being shown -------------- */

        /* Case: delete the preference "videoGames" in the list, command with leading spaces and trailing spaces ->
        deleted */
        Model expectedModel = getModel();
        Model modelBeforeDeletingLast = getModel();
        String command = "     " + DeletePreferenceCommand.COMMAND_WORD + "      " + VIDEO_GAMES.tagName + "       ";
        Preference deletedPreference = VIDEO_GAMES;
        removePreference(expectedModel, VIDEO_GAMES);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PREFERENCE_SUCCESS, deletedPreference);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: undo deleting the preference "videoGames" in the list -> preference "videoGames" restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the preference "videoGames" in the list -> "videoGames" deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePreference(modelBeforeDeletingLast, VIDEO_GAMES);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        assertEquals(expectedModel, modelBeforeDeletingLast);
        /* ------------ Performing delete preference operation while a filtered list is being shown ---------------- */

        /* Case: filtered person list, delete existing preference but not in filtered person list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        expectedModel = getModel();
        deletedPreference = NECKLACES;
        removePreference(expectedModel, NECKLACES);
        command = DeletePreferenceCommand.COMMAND_WORD + " " + NECKLACES.tagName;
        expectedResultMessage = String.format(MESSAGE_DELETE_PREFERENCE_SUCCESS, deletedPreference);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: filtered person list, delete non-existing preference in address book -> rejected */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Preference invalidPref = NECKLACES;
        command = DeletePreferenceCommand.COMMAND_WORD + " " + invalidPref.tagName;
        assertCommandFailure(command, MESSAGE_PREFERENCE_NOT_FOUND);

        /* --------------- Performing delete preference operation while a person card is selected ------------------ */

        /* Case: delete preference existing in the selected person -> person list panel still selects the person */
        showAllPersons();
        expectedModel = getModel();
        deletedPreference = COMPUTERS;
        Index selectedIndex = Index.fromOneBased(2);
        Index expectedIndex = selectedIndex;
        selectPerson(selectedIndex);
        command = DeletePreferenceCommand.COMMAND_WORD + " " + COMPUTERS.tagName;
        removePreference(expectedModel, COMPUTERS);
        expectedResultMessage = String.format(MESSAGE_DELETE_PREFERENCE_SUCCESS, deletedPreference);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------- Performing invalid delete preference operation ------------------------------ */

        /* Case: invalid arguments (non-alphanumeric arguments) -> rejected */
        assertCommandFailure(DeletePreferenceCommand.COMMAND_WORD + " sh!es",
                MESSAGE_INVALID_DELETE_PREFERENCE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeletePreferenceCommand.COMMAND_WORD + " shoes computers",
                MESSAGE_INVALID_DELETE_PREFERENCE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("pREFDelEte shoes", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Preference} in {@code model}'s address book.
     */
    private void removePreference(Model model, Preference toDelete) {
        try {
            model.deletePreference(toDelete);
        } catch (PreferenceNotFoundException pnfe) {
            throw new AssertionError("Preference should exist in address book.");
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeletePreferenceCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### /java/systemtests/FindGroupCommandSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_TWITTER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Preference;

public class FindGroupCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void findGroup() {
        /* Case: find multiple persons in address book by their groups, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindGroupCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_TWITTER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, ELLE); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find group command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindGroupCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_TWITTER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find persons via their group tags where person list is not displaying the person we are finding
        -> 4 persons found */
        command = FindGroupCommand.COMMAND_WORD + " friends";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via group tags, 2 keywords -> 2 persons found */
        command = FindGroupCommand.COMMAND_WORD + " neighbours twitter";
        ModelHelper.setFilteredList(expectedModel, BENSON, ELLE);

        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via group tags, 2 keywords in reversed order
        -> 2 persons found */
        command = FindGroupCommand.COMMAND_WORD + " twitter neighbours";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via group tags, 2 keywords with 1 repeat -> 2 persons found */
        command = FindGroupCommand.COMMAND_WORD + " neighbours twitter neighbours";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via group tags, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindGroupCommand.COMMAND_WORD + " neighbours twitter NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons via group tags in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(BENSON));
        command = FindGroupCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_TWITTER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book via group tag,
        keyword is same as group name but of different case -> 1 person found */
        command = FindGroupCommand.COMMAND_WORD + " TWItter";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book via group tag, keyword is substring of group name -> 0 persons found */
        command = FindGroupCommand.COMMAND_WORD + " twit";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book via group tag, group name is substring of keyword -> 0 persons found */
        command = FindGroupCommand.COMMAND_WORD + " twitters";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person via group tags not in address book -> 0 persons found */
        command = FindGroupCommand.COMMAND_WORD + " Facebook";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book with FindGroupCommand -> 0 persons found */
        command = FindGroupCommand.COMMAND_WORD + " " + ALICE.getName().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindGroupCommand.COMMAND_WORD + " " + ALICE.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindGroupCommand.COMMAND_WORD + " " + ALICE.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 persons found */
        command = FindGroupCommand.COMMAND_WORD + " " + ALICE.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find preferences of person in address book -> 0 persons found */
        List<Preference> preferences = new ArrayList<>(ALICE.getPreferenceTags());
        command = FindGroupCommand.COMMAND_WORD + " " + preferences.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find via group tags while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(ELLE.getName().fullName));
        command = FindGroupCommand.COMMAND_WORD + " twitter";
        ModelHelper.setFilteredList(expectedModel, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book via group tags -> 0 persons found */
        deleteAllPersons();
        command = FindGroupCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_TWITTER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "GrOuPFiNd neighbours";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());


        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### /java/systemtests/FindPreferenceCommandSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_VIDEOGAMES;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Group;

public class FindPreferenceCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void findPreference() {
        /* Case: find multiple persons in address book by their preferences,
        command with leading spaces and trailing spaces -> 2 persons found */
        String command = "   " + FindPreferenceCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_VIDEOGAMES + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, GEORGE);
        // Both Benson and George have preferences "videoGames"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find preference command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindPreferenceCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_VIDEOGAMES;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find persons via their preferences where person list is not displaying the person we are finding
        -> 3 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " computers";
        ModelHelper.setFilteredList(expectedModel, BENSON, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via preferences, 2 keywords -> 3 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " videoGames shoes";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via preferences, 2 keywords in reversed order
        -> 3 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " shoes videoGames";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via preferences, 2 keywords with 1 repeat -> 2 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " videoGames shoes videoGames";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via preferences, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindPreferenceCommand.COMMAND_WORD + " videoGames shoes NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons via preferences in address book after deleting 1 of them -> 2 persons found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(ALICE));
        command = FindPreferenceCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_VIDEOGAMES;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book via preferences,
        keyword is same as preference name but of different case -> 2 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " VIdeOGameS";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book via preferences, keyword is substring of preference name
        -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " videogame";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book via preferences,
        preference name is substring of keyword -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " videogamess";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person via preferences not in address book -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " facialWash";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book with FindPreferenceCommand -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " " + ELLE.getName().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " " + ELLE.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " " + ELLE.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " " + ELLE.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find groups of person in address book -> 0 persons found */
        List<Group> groups = new ArrayList<>(ELLE.getGroupTags());
        command = FindPreferenceCommand.COMMAND_WORD + " " + groups.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find via preferences while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(ELLE.getName().fullName));
        command = FindPreferenceCommand.COMMAND_WORD + " videoGames";
        ModelHelper.setFilteredList(expectedModel, BENSON, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book via group tags -> 0 persons found */
        deleteAllPersons();
        command = FindPreferenceCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_VIDEOGAMES;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "PREferenCEFiNd necklaces";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
```

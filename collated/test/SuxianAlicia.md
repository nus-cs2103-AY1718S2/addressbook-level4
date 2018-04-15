# SuxianAlicia
###### /java/guitests/guihandles/CalendarEntryCardHandle.java
``` java
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a calendar entry card in the calendar entry list panel.
 */
public class CalendarEntryCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String ENTRY_TITLE_ID = "#entryTitle";
    private static final String START_DATE_ID = "#startDate";
    private static final String END_DATE_ID = "#endDate";
    private static final String TIME_DURATION_ID = "#timeDuration";

    private final Label idLabel;
    private final Label entryTitleLabel;
    private final Label startDateLabel;
    private final Label endDateLabel;
    private final Label timeDurationLabel;

    public CalendarEntryCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.entryTitleLabel = getChildNode(ENTRY_TITLE_ID);
        this.startDateLabel = getChildNode(START_DATE_ID);
        this.endDateLabel = getChildNode(END_DATE_ID);
        this.timeDurationLabel = getChildNode(TIME_DURATION_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEntryTitle() {
        return entryTitleLabel.getText();
    }

    public String getStartDate() {
        return startDateLabel.getText();
    }

    public String getEndDate() {
        return endDateLabel.getText();
    }

    public String getTimeDuration() {
        return timeDurationLabel.getText();
    }
}
```
###### /java/guitests/guihandles/CalendarEntryListPanelHandle.java
``` java
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.ui.CalendarEntryCard;

/**
 * Provides a handle for {@code CalendarEntryListPanel} containing the list of {@code CalendarEntryCard}.
 */
public class CalendarEntryListPanelHandle extends NodeHandle<ListView<CalendarEntryCard>> {

    public static final String CALENDAR_ENTRY_LIST_VIEW_ID = "#calendarEntryCardListView";

    public CalendarEntryListPanelHandle(ListView<CalendarEntryCard> rootNode) {
        super(rootNode);
    }

    /**
     * Navigates the listview to display and select the calendar entry.
     */
    public void navigateToCard(CalendarEntry calendarEntry) {
        List<CalendarEntryCard> entryCards = getRootNode().getItems();
        Optional<CalendarEntryCard> matchingCard = entryCards.stream()
                .filter(entryCard -> entryCard.calendarEntry.equals(calendarEntry)).findFirst();

        if (!matchingCard.isPresent()) {
            throw  new IllegalArgumentException("Calendar Entry does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });

        guiRobot.pauseForHuman();
    }

    /**
     * Returns the calendar entry card handle of an calendar entry associated with the {@code index} in the list.
     */
    public CalendarEntryCardHandle getCalendarEntryCardHandle (int index) {
        return getCalendarEntryCardHandle(getRootNode().getItems().get(index).calendarEntry);
    }

    /**
     * Returns the calendar entry card handle of an calendar entry
     * associated with the {@code calendarEntry} in the list.
     */
    private CalendarEntryCardHandle getCalendarEntryCardHandle(CalendarEntry calendarEntry) {
        Optional<CalendarEntryCardHandle> handle = getRootNode().getItems().stream()
                .filter(entryCard -> entryCard.calendarEntry.equals(calendarEntry))
                .map(orderCard -> new CalendarEntryCardHandle(orderCard.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Calendar Entry does not exist."));
    }
}
```
###### /java/guitests/guihandles/CalendarPanelHandle.java
``` java
import java.time.LocalDate;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.WeekPage;

import javafx.scene.layout.StackPane;

/**
 * A handler for {@code CalendarPanel} of the Ui.
 */
public class CalendarPanelHandle extends NodeHandle<StackPane> {

    public static final String CALENDAR_PANEL_ID = "#calendarPanelHolder";

    private static final String DAY_VIEW = "Day";
    private static final String MONTH_VIEW = "Month";
    private static final String WEEK_VIEW = "Week";

    private CalendarView calendarView;

    public CalendarPanelHandle(StackPane rootNode) {
        super(rootNode);

        if (getRootNode().getChildren().get(0) instanceof CalendarView) {
            calendarView = (CalendarView) getRootNode().getChildren().get(0);
        } else {
            throw new AssertionError("#calendarPanelholder should have child node of CalendarView object.");
        }
    }

    /**
     * Returns the current view of {@code calendarView}.
     */
    public String getCurrentView() {
        if (calendarView.getSelectedPage() instanceof MonthPage) {
            return MONTH_VIEW;
        } else if (calendarView.getSelectedPage() instanceof WeekPage) {
            return WEEK_VIEW;
        } else if (calendarView.getSelectedPage() instanceof DayPage) {
            return DAY_VIEW;
        }
        return null;
    }

    /**
     * Returns the current date displayed by {@code calendarView}.
     */
    public LocalDate getCurrentDate() {
        return calendarView.getDate();
    }

    /**
     * Returns today's date as stored in {@code calendarView}.
     */
    public LocalDate getTodayDate() {
        return calendarView.getToday();
    }
}
```
###### /java/guitests/guihandles/CenterPanelHandle.java
``` java

import java.time.LocalDate;

import com.calendarfx.model.Calendar;

import javafx.scene.layout.StackPane;

/**
 * A handler for {@code CenterPanel} of the Ui.
 */
public class CenterPanelHandle extends NodeHandle<StackPane> {

    public static final String CENTER_PANEL_ID = "#centerPlaceholder";

    private PersonPanelHandle personPanelHandle;
    private CalendarPanelHandle calendarPanelHandle;

    public CenterPanelHandle(StackPane rootNode) {
        super(rootNode);
    }

    /**
     * Sets Up {@code CalendarPanelHandle}.
     * This method is only invoked only after {@code CenterPanel} adds {@code CalendarPanel} to its root node.
     */
    public void setUpCalendarPanelHandle(Calendar calendar) {
        calendarPanelHandle = new CalendarPanelHandle(getChildNode(CalendarPanelHandle.CALENDAR_PANEL_ID));
    }

    /**
     * Returns the current view of the calendar.
     */
    public String getCalendarCurrentView() {
        return calendarPanelHandle.getCurrentView();
    }

    /**
     * Returns the current date displayed in the calendar.
     */
    public LocalDate getCalendarCurrentDate() {
        return calendarPanelHandle.getCurrentDate();
    }

    /**
     * Returns the date set as today in the calendar.
     */
    public LocalDate getCalendarTodayDate() {
        return calendarPanelHandle.getTodayDate();
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
import static VALID_END_DATE_MEET_SUPPLIER;
import static VALID_END_TIME_SUPPLIER;
import static VALID_START_DATE_MEET_SUPPLIER;
import static VALID_START_TIME_MEET_SUPPLIER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;

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
            fail("This exception should not be thrown as the inputs are valid.");
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
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    @Test
    public void saveDateToFile_validCalendarManagerFile_dataSaved() throws Exception {
        TEMP_CALENDARMANAGER_FILE.createNewFile();
        XmlSerializableCalendarManager dataToWrite = new XmlSerializableCalendarManager(new CalendarManager());
        XmlUtil.saveDataToFile(TEMP_CALENDARMANAGER_FILE, dataToWrite);
        XmlSerializableCalendarManager dataFromFile = XmlUtil.getDataFromFile(
                TEMP_CALENDARMANAGER_FILE, XmlSerializableCalendarManager.class);
        assertEquals(dataToWrite, dataFromFile);

        CalendarManagerBuilder builder = new CalendarManagerBuilder(new CalendarManager());
        dataToWrite = new XmlSerializableCalendarManager(builder.withEntry(new CalendarEntryBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_CALENDARMANAGER_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_CALENDARMANAGER_FILE, XmlSerializableCalendarManager.class);
        assertEquals(dataToWrite, dataFromFile);
    }
```
###### /java/seedu/address/logic/commands/AddEntryCommandTest.java
``` java
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
```
###### /java/seedu/address/logic/commands/CalendarJumpCommandTest.java
``` java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalLocalDates.LEAP_YEAR_DATE;
import static seedu.address.testutil.TypicalLocalDates.LEAP_YEAR_DATE_STRING;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE_STRING;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class CalendarJumpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CalendarJumpCommand(null, LEAP_YEAR_DATE_STRING);
    }

    @Test
    public void constructor_nullDateString_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CalendarJumpCommand(LEAP_YEAR_DATE, null);
    }

    @Test
    public void execute_validDateAndDateStringGiven_success() {
        assertSwitchDateSuccess(LEAP_YEAR_DATE, LEAP_YEAR_DATE_STRING);
    }

    @Test
    public void equals() {
        CalendarJumpCommand calendarJumpFirstCommand = new CalendarJumpCommand(LEAP_YEAR_DATE, LEAP_YEAR_DATE_STRING);
        CalendarJumpCommand calendarJumpSecondCommand = new CalendarJumpCommand(NORMAL_DATE, LEAP_YEAR_DATE_STRING);
        CalendarJumpCommand calendarJumpThirdCommand = new CalendarJumpCommand(LEAP_YEAR_DATE, NORMAL_DATE_STRING);

        // same object -> returns true
        assertTrue(calendarJumpFirstCommand.equals(calendarJumpFirstCommand));

        // same values -> returns true
        CalendarJumpCommand calendarJumpFirstCommandCopy =
                new CalendarJumpCommand(LEAP_YEAR_DATE, LEAP_YEAR_DATE_STRING);
        assertTrue(calendarJumpFirstCommand.equals(calendarJumpFirstCommandCopy));

        // different date -> returns false
        assertFalse(calendarJumpFirstCommand.equals(calendarJumpSecondCommand));

        // different dateString -> returns false
        assertFalse(calendarJumpFirstCommand.equals(calendarJumpThirdCommand));

        // different types -> returns false
        assertFalse(calendarJumpFirstCommand.equals(1));

        // null -> returns false
        assertFalse(calendarJumpFirstCommand.equals(null));
    }

    /**
     * Executes a {@code CalendarJumpCommand},
     * and checks that {@code ChangeCalendarDateRequestEvent} is raised with the given {@code LocalDate}.
     */
    private void assertSwitchDateSuccess(LocalDate date, String dateString) {
        CalendarJumpCommand calendarJumpCommand = new CalendarJumpCommand(date, dateString);

        try {
            CommandResult commandResult = calendarJumpCommand.execute();
            assertEquals(String.format(CalendarJumpCommand.MESSAGE_CALENDAR_JUMP_SUCCESS, dateString),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarDateRequestEvent);
        ChangeCalendarDateRequestEvent lastEvent = (ChangeCalendarDateRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(date, lastEvent.getDate());
    }
}
```
###### /java/seedu/address/logic/commands/DeleteEntryCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entry.CalendarEntry;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteEntryCommand}.
 */
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalGroups.COLLEAGUES;
import static seedu.address.testutil.TypicalGroups.FRIENDS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Group;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteGroupCommand}.
 */
public class DeleteGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void execute_validGroup_success() throws Exception {
        Group groupToDelete = FRIENDS;
        DeleteGroupCommand deleteGroupCommand = prepareCommand(FRIENDS);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentGroup_throwsCommandException() throws Exception {
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
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

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
    public void executeUndoRedo_invalidGroup_failure() {
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
        DeleteGroupCommand deleteGroupFirstCommand = prepareCommand(FRIENDS);
        DeleteGroupCommand deleteGroupSecondCommand = prepareCommand(COLLEAGUES);

        // same object -> returns true
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommand));

        // same values -> returns true
        DeleteGroupCommand deleteGroupFirstCommandCopy = prepareCommand(FRIENDS);
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteGroupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteGroupFirstCommand.equals(null));

        // different preference -> returns false
        assertFalse(deleteGroupFirstCommand.equals(deleteGroupSecondCommand));
    }

    /**
     * Returns a {@code DeleteGroupCommand} with the parameter {@code group}.
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPreferences.COMPUTERS;
import static seedu.address.testutil.TypicalPreferences.SHOES;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Preference;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeletePreferenceCommand}.
 */
public class DeletePreferenceCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void execute_validPreference_success() throws Exception {
        Preference prefToDelete = SHOES;
        DeletePreferenceCommand deletePrefCommand = prepareCommand(SHOES);

        String expectedMessage = String.format(DeletePreferenceCommand.MESSAGE_DELETE_PREFERENCE_SUCCESS, prefToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.deletePreference(prefToDelete);

        assertCommandSuccess(deletePrefCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentPreference_throwsCommandException() throws Exception {
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
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

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
        DeletePreferenceCommand deletePrefFirstCommand = prepareCommand(SHOES);
        DeletePreferenceCommand deletePrefSecondCommand = prepareCommand(COMPUTERS);

        // same object -> returns true
        assertTrue(deletePrefFirstCommand.equals(deletePrefFirstCommand));

        // same values -> returns true
        DeletePreferenceCommand deletePrefFirstCommandCopy = prepareCommand(SHOES);
        assertTrue(deletePrefFirstCommand.equals(deletePrefFirstCommandCopy));

        // different types -> returns false
        assertFalse(deletePrefFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deletePrefFirstCommand.equals(null));

        // different preference -> returns false
        assertFalse(deletePrefFirstCommand.equals(deletePrefSecondCommand));
    }

    /**
     * Returns a {@code DeletePreferenceCommand} with the parameter {@code preference}.
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
import static DESC_GET_BOOKS;
import static DESC_MEET_SUPPLIER;
import static VALID_ENTRY_TITLE_GET_BOOKS;
import static VALID_START_TIME_GET_BOOKS;
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
import seedu.address.model.entry.CalendarEntry;
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
import static DESC_GET_BOOKS;
import static DESC_MEET_SUPPLIER;
import static VALID_END_DATE_MEET_SUPPLIER;
import static VALID_END_TIME_SUPPLIER;
import static VALID_ENTRY_TITLE_MEET_SUPPLIER;
import static VALID_START_DATE_MEET_SUPPLIER;
import static VALID_START_TIME_MEET_SUPPLIER;

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
###### /java/seedu/address/logic/commands/EntryListClearCommandTest.java
``` java

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class EntryListClearCommandTest {

    @Test
    public void execute_emptyCalendarManager_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, EntryListClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyCalendarManager_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendarManagerWithEntries(),
                new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, EntryListClearCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code EntryListClearCommand} which upon execution,
     * clears the CalendarManager in {@code model}.
     */
    private EntryListClearCommand prepareCommand(Model model) {
        EntryListClearCommand command = new EntryListClearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/FindGroupCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.GroupsContainKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code FindGroupCommand}.
 */
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

        // different predicates -> returns false
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PreferencesContainKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPreferenceCommand}.
 */
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

        // different predicates -> returns false
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
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCalendarEntryCommand.
 */
public class ListCalendarEntryCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

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
    public void execute() {
        assertCommandSuccess(listCalendarEntryCommand, model, ListCalendarEntryCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplayCalendarEntryListEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### /java/seedu/address/logic/commands/ListOrderCommandTest.java
``` java
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.DisplayOrderListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListOrderCommand.
 */
public class ListOrderCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

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
    public void execute() {
        assertCommandSuccess(listOrderCommand, model, ListOrderCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DisplayOrderListEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### /java/seedu/address/logic/commands/ViewBackCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_BACK;

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

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarPageRequestEvent);
        ChangeCalendarPageRequestEvent lastEvent = (ChangeCalendarPageRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(REQUEST_BACK, lastEvent.getRequestType());
    }
}
```
###### /java/seedu/address/logic/commands/ViewCalendarCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.util.CalendarFxUtil.DAY_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.MONTH_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.WEEK_VIEW;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeCalendarViewRequestEvent;
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
        ViewCalendarCommand viewCalendarFirstCommand = new ViewCalendarCommand(DAY_VIEW);
        ViewCalendarCommand viewCalendarSecondCommand = new ViewCalendarCommand(WEEK_VIEW);

        // same object -> returns true
        assertTrue(viewCalendarFirstCommand.equals(viewCalendarFirstCommand));

        // same values -> returns true
        ViewCalendarCommand selectFirstCommandCopy = new ViewCalendarCommand(DAY_VIEW);
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
     * and checks that {@code ChangeCalendarViewRequestEvent} is raised with the day view.
     */
    private void assertShowDayViewSuccess(String view) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(view);

        try {
            CommandResult commandResult = viewCalendarCommand.execute();
            assertEquals(String.format(ViewCalendarCommand.MESSAGE_SHOW_CALENDAR_SUCCESS, DAY_VIEW),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarViewRequestEvent);
        ChangeCalendarViewRequestEvent lastEvent = (ChangeCalendarViewRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(DAY_VIEW, lastEvent.getView());
    }

    /**
     * Executes a {@code ViewCalendarCommand} with the given {@code view},
     * and checks that {@code ChangeCalendarViewRequestEvent} is raised with the week view.
     */
    private void assertShowWeekViewSuccess(String view) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(view);

        try {
            CommandResult commandResult = viewCalendarCommand.execute();
            assertEquals(String.format(ViewCalendarCommand.MESSAGE_SHOW_CALENDAR_SUCCESS,
                    WEEK_VIEW), commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarViewRequestEvent);
        ChangeCalendarViewRequestEvent lastEvent = (ChangeCalendarViewRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(WEEK_VIEW, lastEvent.getView());
    }

    /**
     * Executes a {@code ViewCalendarCommand} with the given {@code view},
     * and checks that {@code ChangeCalendarViewRequestEvent} is raised with the month view.
     */
    private void assertShowMonthViewSuccess(String view) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(view);

        try {
            CommandResult commandResult = viewCalendarCommand.execute();
            assertEquals(String.format(ViewCalendarCommand.MESSAGE_SHOW_CALENDAR_SUCCESS,
                    MONTH_VIEW), commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarViewRequestEvent);
        ChangeCalendarViewRequestEvent lastEvent = (ChangeCalendarViewRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(MONTH_VIEW, lastEvent.getView());
    }
}
```
###### /java/seedu/address/logic/commands/ViewNextCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_NEXT;

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

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarPageRequestEvent);
        ChangeCalendarPageRequestEvent lastEvent = (ChangeCalendarPageRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(REQUEST_NEXT, lastEvent.getRequestType());
    }
}
```
###### /java/seedu/address/logic/commands/ViewTodayCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_TODAY;

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

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeCalendarPageRequestEvent);
        ChangeCalendarPageRequestEvent lastEvent = (ChangeCalendarPageRequestEvent) eventsCollectorRule.eventsCollector
                .getMostRecent();
        assertEquals(REQUEST_TODAY, lastEvent.getRequestType());
    }
}
```
###### /java/seedu/address/logic/parser/AddEntryCommandParserTest.java
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
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_LATER_THAN_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_LATER_THAN_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_LESS_THAN_FIFTEEN_MINUTES_FROM_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_BOSS;
import static seedu.address.logic.parser.AddEntryCommandParser.STANDARD_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.util.EntryTimeConstraintsUtil;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;
import seedu.address.testutil.CalendarEntryBuilder;

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

        // multiple entry title strings - last entry title string accepted
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
###### /java/seedu/address/logic/parser/CalendarJumpCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE;
import static seedu.address.testutil.TypicalLocalDates.NORMAL_DATE_STRING;

import org.junit.Test;

import seedu.address.logic.commands.CalendarJumpCommand;

public class CalendarJumpCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarJumpCommand.MESSAGE_USAGE);

    private CalendarJumpCommandParser parser = new CalendarJumpCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing Target Date prefix
        assertParseFailure(parser, NORMAL_DATE_STRING,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTargetDate_failure() {
        assertParseFailure(parser, " " + PREFIX_TARGET_DATE + "06.06.1990", MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_validTargetDate_success() {
        assertParseSuccess(parser, " " + PREFIX_TARGET_DATE + NORMAL_DATE_STRING,
                new CalendarJumpCommand(NORMAL_DATE, NORMAL_DATE_STRING));
    }
}
```
###### /java/seedu/address/logic/parser/EditEntryCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static END_DATE_DESC_GET_BOOKS;
import static END_DATE_DESC_MEET_SUPPLIER;
import static END_TIME_DESC_GET_BOOKS;
import static END_TIME_DESC_MEET_SUPPLIER;
import static ENTRY_TITLE_DESC_GET_BOOKS;
import static ENTRY_TITLE_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENTRY_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static START_DATE_DESC_GET_BOOKS;
import static START_DATE_DESC_MEET_SUPPLIER;
import static START_TIME_DESC_GET_BOOKS;
import static START_TIME_DESC_MEET_SUPPLIER;
import static VALID_END_DATE_GET_BOOKS;
import static VALID_END_DATE_MEET_SUPPLIER;
import static VALID_END_TIME_GET_BOOKS;
import static VALID_END_TIME_SUPPLIER;
import static VALID_ENTRY_TITLE_GET_BOOKS;
import static VALID_ENTRY_TITLE_MEET_SUPPLIER;
import static VALID_START_DATE_GET_BOOKS;
import static VALID_START_DATE_MEET_SUPPLIER;
import static VALID_START_TIME_GET_BOOKS;
import static VALID_START_TIME_MEET_SUPPLIER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ENTRY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;
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
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.model.person.GroupsContainKeywordsPredicate;

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
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.model.person.PreferencesContainKeywordsPredicate;

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
###### /java/seedu/address/model/CalendarManagerTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCalendarEntries.MEETING_BOSS;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entry.CalendarEntry;

public class CalendarManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CalendarManager calendarManager = new CalendarManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), calendarManager.getCalendarEntryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        calendarManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyCalendarManager_replacesData() {
        CalendarManager newData = getTypicalCalendarManagerWithEntries();
        calendarManager.resetData(newData);
        assertEquals(newData, calendarManager);
    }

    @Test
    public void resetData_withDuplicateCalendarEntries_throwsAssertionError() {
        // Repeat MEETING_BOSS twice
        List<CalendarEntry> newEntries = Arrays.asList(MEETING_BOSS, MEETING_BOSS);
        CalendarManagerStub newData = new CalendarManagerStub(newEntries);

        thrown.expect(AssertionError.class);
        calendarManager.resetData(newData);
    }


    @Test
    public void getCalendarEntryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        calendarManager.getCalendarEntryList().remove(0);
    }


    private static class CalendarManagerStub implements ReadOnlyCalendarManager {
        private final ObservableList<CalendarEntry> calendarEntries = FXCollections.observableArrayList();

        CalendarManagerStub(Collection<CalendarEntry> calendarEntries) {
            this.calendarEntries.setAll(calendarEntries);
        }

        @Override
        public ObservableList<CalendarEntry> getCalendarEntryList() {
            return calendarEntries;
        }
    }
}
```
###### /java/seedu/address/model/entry/CalendarEntryTest.java
``` java
import org.junit.Test;

import seedu.address.testutil.Assert;

public class CalendarEntryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new CalendarEntry(null, null, null, null, null));

    }
}
```
###### /java/seedu/address/model/entry/EndDateTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.testutil.Assert;

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
###### /java/seedu/address/model/entry/EndTimeTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.TimeUtil;
import seedu.address.testutil.Assert;

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
###### /java/seedu/address/model/entry/EntryTitleTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EntryTitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EntryTitle(null));
    }

    @Test
    public void constructor_invalidEntryTitle_throwsIllegalArgumentException() {
        String invalidEntryTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EntryTitle(invalidEntryTitle));
    }

    @Test
    public void isValidEntryTitle() {

        // null entry title
        Assert.assertThrows(NullPointerException.class, () -> EntryTitle.isValidEntryTitle(null));

        // invalid entry title
        assertFalse(EntryTitle.isValidEntryTitle("")); // empty string
        assertFalse(EntryTitle.isValidEntryTitle(" ")); // spaces only

        // valid entry title
        assertTrue(EntryTitle.isValidEntryTitle("Meet with bosses"));
        assertTrue(EntryTitle.isValidEntryTitle("Meet Client for stocks"));
        assertTrue(EntryTitle.isValidEntryTitle("Confectionery Boxes Order"));
    }
}
```
###### /java/seedu/address/model/entry/StartDateTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.testutil.Assert;

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
###### /java/seedu/address/model/entry/StartTimeTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.TimeUtil;
import seedu.address.testutil.Assert;

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
###### /java/seedu/address/model/person/GroupsContainKeywordsPredicateTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalGroups.BUDDIES;
import static seedu.address.testutil.TypicalGroups.COLLEAGUES;
import static seedu.address.testutil.TypicalGroups.FRIENDS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class GroupsContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Arrays.asList(COLLEAGUES.tagName, BUDDIES.tagName);
        List<String> secondPredicateKeywordList = Collections.singletonList(FRIENDS.tagName);

        GroupsContainKeywordsPredicate firstPredicate = new GroupsContainKeywordsPredicate(firstPredicateKeywordList);
        GroupsContainKeywordsPredicate secondPredicate = new GroupsContainKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupsContainKeywordsPredicate firstPredicateCopy =
                new GroupsContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_groupsContainsKeywords_returnsTrue() {
        GroupsContainKeywordsPredicate predicate;

        // One keyword
        predicate = new GroupsContainKeywordsPredicate(Collections.singletonList(COLLEAGUES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withGroups(COLLEAGUES.tagName).build()));

        // Multiple keywords
        predicate = new GroupsContainKeywordsPredicate(Arrays.asList(COLLEAGUES.tagName, BUDDIES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withGroups(COLLEAGUES.tagName, BUDDIES.tagName).build()));

        // Only one matching keyword
        predicate = new GroupsContainKeywordsPredicate(Arrays.asList(FRIENDS.tagName, BUDDIES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withGroups(COLLEAGUES.tagName, BUDDIES.tagName).build()));

        // Mixed-case keywords
        predicate = new GroupsContainKeywordsPredicate(Arrays.asList("COlleAGUES", "BUDDIes"));
        assertTrue(predicate.test(new PersonBuilder().withGroups(COLLEAGUES.tagName, BUDDIES.tagName).build()));
    }

    @Test
    public void test_personGroupsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GroupsContainKeywordsPredicate predicate =
                new GroupsContainKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withGroups(BUDDIES.tagName).build()));

        // Non-matching keyword
        predicate = new GroupsContainKeywordsPredicate(Collections.singletonList(COLLEAGUES.tagName));
        assertFalse(predicate.test(new PersonBuilder().withGroups(BUDDIES.tagName, FRIENDS.tagName).build()));
    }
}
```
###### /java/seedu/address/model/person/PreferencesContainKeywordsPredicateTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPreferences.NECKLACES;
import static seedu.address.testutil.TypicalPreferences.SHOES;
import static seedu.address.testutil.TypicalPreferences.VIDEO_GAMES;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PreferencesContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Arrays.asList(VIDEO_GAMES.tagName, SHOES.tagName);
        List<String> secondPredicateKeywordList = Collections.singletonList(NECKLACES.tagName);

        PreferencesContainKeywordsPredicate firstPredicate =
                new PreferencesContainKeywordsPredicate(firstPredicateKeywordList);
        PreferencesContainKeywordsPredicate secondPredicate =
                new PreferencesContainKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PreferencesContainKeywordsPredicate firstPredicateCopy =
                new PreferencesContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_preferencesContainsKeywords_returnsTrue() {
        PreferencesContainKeywordsPredicate predicate;

        // One keyword
        predicate = new PreferencesContainKeywordsPredicate(Collections.singletonList(VIDEO_GAMES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withPreferences(VIDEO_GAMES.tagName).build()));

        // Multiple keywords
        predicate = new PreferencesContainKeywordsPredicate(Arrays.asList(NECKLACES.tagName, SHOES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withPreferences(NECKLACES.tagName, SHOES.tagName).build()));

        // Only one matching keyword
        predicate = new PreferencesContainKeywordsPredicate(Arrays.asList(NECKLACES.tagName, SHOES.tagName));
        assertTrue(predicate.test(new PersonBuilder().withPreferences(VIDEO_GAMES.tagName, SHOES.tagName).build()));

        // Mixed-case keywords
        predicate = new PreferencesContainKeywordsPredicate(Arrays.asList("NeCkLaCes", "ShoES"));
        assertTrue(predicate.test(new PersonBuilder().withPreferences(NECKLACES.tagName, SHOES.tagName).build()));
    }

    @Test
    public void test_personPreferencesDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PreferencesContainKeywordsPredicate predicate =
                new PreferencesContainKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPreferences(VIDEO_GAMES.tagName).build()));

        // Non-matching keyword
        predicate = new PreferencesContainKeywordsPredicate(Collections.singletonList(VIDEO_GAMES.tagName));
        assertFalse(predicate.test(new PersonBuilder().withPreferences(SHOES.tagName, NECKLACES.tagName).build()));
    }
}
```
###### /java/seedu/address/model/tag/GroupTest.java
``` java
import org.junit.Test;

import seedu.address.testutil.Assert;

public class GroupTest {

    @Test
    public void constructor_nullGroupTagName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Group(null));
    }

    @Test
    public void constructor_invalidGroupTagName_throwsIllegalArgumentException() {
        String invalidGroupTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Group(invalidGroupTagName));
    }

    @Test
    public void isValidGroupTagName() {
        // null group name
        Assert.assertThrows(NullPointerException.class, () -> Group.isValidTagName(null));
    }
}
```
###### /java/seedu/address/model/tag/PreferenceTest.java
``` java
import org.junit.Test;

import seedu.address.testutil.Assert;

public class PreferenceTest {

    @Test
    public void constructor_nullPreferenceTagName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Preference(null));
    }

    @Test
    public void constructor_invalidPreferenceTagName_throwsIllegalArgumentException() {
        String invalidPreferenceTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Preference(invalidPreferenceTagName));
    }

    @Test
    public void isValidPreferenceTagName() {
        // null preference name
        Assert.assertThrows(NullPointerException.class, () -> Preference.isValidTagName(null));
    }
}
```
###### /java/seedu/address/model/UniqueCalendarEntryListTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCalendarEntries.GET_STOCKS;
import static seedu.address.testutil.TypicalCalendarEntries.MEETING_BOSS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.entry.UniqueCalendarEntryList;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

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

        // different calendar entries, same type -> false
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
###### /java/seedu/address/storage/XmlAdaptedCalendarEntryTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedCalendarEntry.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalCalendarEntries.MEETING_BOSS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;
import seedu.address.testutil.Assert;

public class XmlAdaptedCalendarEntryTest {
    private static final String INVALID_ENTRY_TITLE = "M&&ting wi$h b@ss";
    private static final String INVALID_START_DATE = "30-02-2019";
    private static final String INVALID_END_DATE = "31-02-2019";
    private static final String INVALID_START_TIME = "24:60";
    private static final String INVALID_END_TIME = "25:100";

    private static final String VALID_ENTRY_TITLE = MEETING_BOSS.getEntryTitle().toString();
    private static final String VALID_START_DATE = MEETING_BOSS.getStartDate().toString();
    private static final String VALID_END_DATE = MEETING_BOSS.getEndDate().toString();
    private static final String VALID_START_TIME = MEETING_BOSS.getStartTime().toString();
    private static final String VALID_END_TIME = MEETING_BOSS.getEndTime().toString();

    @Test
    public void toModelType_validCalendarEntryDetails_returnsCalendarEvent() throws Exception {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(MEETING_BOSS);
        assertEquals(MEETING_BOSS, calEvent.toModelType());
    }

    @Test
    public void toModelType_invalidEntryTitle_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(INVALID_ENTRY_TITLE, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullEntryTitle_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(null, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EntryTitle.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_ENTRY_TITLE, INVALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = StartDate.MESSAGE_START_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_ENTRY_TITLE, null,
                VALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_ENTRY_TITLE, VALID_START_DATE,
                INVALID_END_DATE, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = EndDate.MESSAGE_END_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_ENTRY_TITLE, VALID_START_DATE,
                null, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_ENTRY_TITLE, VALID_START_DATE,
                VALID_END_DATE, INVALID_START_TIME, VALID_END_TIME);
        String expectedMessage = StartTime.MESSAGE_START_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_ENTRY_TITLE, VALID_START_DATE,
                VALID_END_DATE, null, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_ENTRY_TITLE, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, INVALID_END_TIME);
        String expectedMessage = EndTime.MESSAGE_END_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedCalendarEntry calEvent = new XmlAdaptedCalendarEntry(VALID_ENTRY_TITLE, VALID_START_DATE,
                VALID_END_DATE, VALID_START_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, calEvent::toModelType);
    }
}
```
###### /java/seedu/address/storage/XmlCalendarManagerStorageTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCalendarEntries.WORKSHOP;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;

import java.io.IOException;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.CalendarManager;
import seedu.address.model.ReadOnlyCalendarManager;

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
    public void toModelType_invalidEntriesFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCalendarManager dataFromFile = XmlUtil.getDataFromFile(INVALID_EVENT_FILE,
                XmlSerializableCalendarManager.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
```
###### /java/seedu/address/testutil/CalendarEntryBuilder.java
``` java
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;

/**
 * A utility class to help with building CalendarEntry objects.
 */
public class CalendarEntryBuilder {

    public static final String DEFAULT_ENTRY_TITLE = "Meeting with boss";
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
        entryTitle = new EntryTitle(DEFAULT_ENTRY_TITLE);
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
import seedu.address.model.entry.CalendarEntry;

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
###### /java/seedu/address/testutil/CalendarManagerBuilder.java
``` java
import seedu.address.model.CalendarManager;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

/**
 * A utility class to help with building CalendarManager objects.
 * Example usage: <br>
 *     {@code CalendarManager cm = new CalendarManagerBuilder().withEntries("Meet Boss", "Get Stocks").build();}
 */
public class CalendarManagerBuilder {
    private CalendarManager calendarManager;

    public CalendarManagerBuilder() {
        calendarManager = new CalendarManager();
    }

    public CalendarManagerBuilder(CalendarManager calendarManager) {
        this.calendarManager = calendarManager;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public CalendarManagerBuilder withEntry(CalendarEntry calendarEntry) {
        try {
            calendarManager.addCalendarEntry(calendarEntry);
        } catch (DuplicateCalendarEntryException dcee) {
            throw new IllegalArgumentException("Entry is expected to be unique.");
        }
        return this;
    }

    public CalendarManager build() {
        return calendarManager;
    }
}

```
###### /java/seedu/address/testutil/EditEntryDescriptorBuilder.java
``` java
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;

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
###### /java/seedu/address/testutil/TypicalBaseEvents.java
``` java

import static seedu.address.testutil.TypicalLocalDates.LEAP_YEAR_DATE;
import static seedu.address.ui.util.CalendarFxUtil.DAY_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.MONTH_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_BACK;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_NEXT;
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_TODAY;
import static seedu.address.ui.util.CalendarFxUtil.WEEK_VIEW;

import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarViewRequestEvent;

/**
 * A utility class containing a list of {@code BaseEvent} objects to be used in tests.
 */
public class TypicalBaseEvents {

    public static final ChangeCalendarViewRequestEvent CHANGE_TO_DAY_EVENT =
            new ChangeCalendarViewRequestEvent(DAY_VIEW);
    public static final ChangeCalendarViewRequestEvent CHANGE_TO_MONTH_EVENT =
            new ChangeCalendarViewRequestEvent(MONTH_VIEW);
    public static final ChangeCalendarViewRequestEvent CHANGE_TO_WEEK_EVENT =
            new ChangeCalendarViewRequestEvent(WEEK_VIEW);

    public static final ChangeCalendarPageRequestEvent CHANGE_TO_NEXT_PAGE_EVENT =
            new ChangeCalendarPageRequestEvent(REQUEST_NEXT);
    public static final ChangeCalendarPageRequestEvent CHANGE_TO_PREVIOUS_PAGE_EVENT =
            new ChangeCalendarPageRequestEvent(REQUEST_BACK);
    public static final ChangeCalendarPageRequestEvent CHANGE_TO_TODAY_EVENT =
            new ChangeCalendarPageRequestEvent(REQUEST_TODAY);

    public static final ChangeCalendarDateRequestEvent CHANGE_DATE_EVENT =
            new ChangeCalendarDateRequestEvent(LEAP_YEAR_DATE);
}
```
###### /java/seedu/address/testutil/TypicalCalendarEntries.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.CalendarManager;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;

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
###### /java/seedu/address/testutil/TypicalLocalDates.java
``` java

import java.time.LocalDate;

import seedu.address.commons.util.DateUtil;

/**
 * A utility class containing a list of {@code Preference} Objects to be used in tests.
 */
public class TypicalLocalDates {
    public static final String LEAP_YEAR_DATE_STRING = "29-02-2016";
    public static final String NORMAL_DATE_STRING = "06-06-1990";

    // Conversion to LocalDate from strings should not fail.
    public static final LocalDate LEAP_YEAR_DATE = DateUtil.convertStringToDate(LEAP_YEAR_DATE_STRING);
    public static final LocalDate NORMAL_DATE = DateUtil.convertStringToDate(NORMAL_DATE_STRING);

    private TypicalLocalDates() {}

}
```
###### /java/seedu/address/testutil/TypicalPreferences.java
``` java
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
import seedu.address.model.entry.CalendarEntry;
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

        // different calendar entry, same index -> returns false
        CalendarEntry differentEntry = new CalendarEntryBuilder().withEntryTitle("differentTitle").build();
        assertFalse(calendarEntryCard.equals(new CalendarEntryCard(differentEntry, 0)));

        // same calendar entry, different index -> returns false
        assertFalse(calendarEntryCard.equals(new CalendarEntryCard(calendarEntry, 1)));

    }

    /**
     * Asserts that {@code calendarEntryCard} displays the details of {@code expectedEntry} correctly and
     * matches {@code expectedId}.
     */
    private void assertCardDisplay(CalendarEntryCard calendarEntryCard, CalendarEntry expectedEntry, int expectedId) {
        guiRobot.pauseForHuman();

        CalendarEntryCardHandle entryCardHandle = new CalendarEntryCardHandle(calendarEntryCard.getRoot());

        // verify that id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", entryCardHandle.getId());

        // verify calendar entry details are displayed correctly
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
import seedu.address.model.entry.CalendarEntry;

public class CalendarEntryListPanelTest extends GuiUnitTest {
    private static final ObservableList<CalendarEntry> TYPICAL_CAL_ENTRIES =
            FXCollections.observableList(getTypicalCalendarEntries());

    private CalendarEntryListPanelHandle calendarEntryListPanelHandle;

    @Before
    public void setUp() {
        CalendarEntryListPanel calendarEntryListPanel = new CalendarEntryListPanel(TYPICAL_CAL_ENTRIES);
        uiPartRule.setUiPart(calendarEntryListPanel);

        calendarEntryListPanelHandle = new CalendarEntryListPanelHandle(getChildNode(calendarEntryListPanel.getRoot(),
                CalendarEntryListPanelHandle.CALENDAR_ENTRY_LIST_VIEW_ID));
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
###### /java/seedu/address/ui/CalendarPanelTest.java
``` java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_DATE_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_DAY_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_MONTH_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_NEXT_PAGE_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_PREVIOUS_PAGE_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_TODAY_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_WEEK_EVENT;
import static seedu.address.testutil.TypicalLocalDates.LEAP_YEAR_DATE;
import static seedu.address.ui.util.CalendarFxUtil.DAY_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.MONTH_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.WEEK_VIEW;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.calendarfx.model.Calendar;

import guitests.guihandles.CenterPanelHandle;

/**
 * Contains integration tests (interaction with the CenterPanel) for {@code CalendarPanel}.
 */
public class CalendarPanelTest extends GuiUnitTest {

    private static final Calendar calendar = new Calendar();

    private CenterPanelHandle centerPanelHandle;

    @Before
    public void setUp() {
        calendar.setReadOnly(true);
        calendar.setStyle(Calendar.Style.STYLE1);
        CenterPanel centerPanel = new CenterPanel(calendar);
        centerPanel.displayCalendarPanel();
        uiPartRule.setUiPart(centerPanel);

        centerPanelHandle = new CenterPanelHandle(getChildNode(centerPanel.getRoot(),
                CenterPanelHandle.CENTER_PANEL_ID));
    }

    @Test
    public void handleChangeCalendarViewRequestEvent() {
        centerPanelHandle.setUpCalendarPanelHandle(calendar);

        postNow(CHANGE_TO_MONTH_EVENT);
        guiRobot.pauseForHuman();
        assertEquals(MONTH_VIEW, centerPanelHandle.getCalendarCurrentView());

        postNow(CHANGE_TO_WEEK_EVENT);
        guiRobot.pauseForHuman();
        assertEquals(WEEK_VIEW, centerPanelHandle.getCalendarCurrentView());

        postNow(CHANGE_TO_DAY_EVENT);
        guiRobot.pauseForHuman();
        assertEquals(DAY_VIEW, centerPanelHandle.getCalendarCurrentView());
    }

    @Test
    public void handleChangeCalendarPageRequestEvent() {
        centerPanelHandle.setUpCalendarPanelHandle(calendar);

        LocalDate originalDate = centerPanelHandle.getCalendarCurrentDate();
        postNow(CHANGE_TO_NEXT_PAGE_EVENT);
        guiRobot.pauseForHuman();

        LocalDate expectedDate = originalDate.plusDays(1);
        assertEquals(expectedDate, centerPanelHandle.getCalendarCurrentDate());

        postNow(CHANGE_TO_TODAY_EVENT);
        guiRobot.pauseForHuman();

        expectedDate = centerPanelHandle.getCalendarTodayDate();
        assertEquals(expectedDate, centerPanelHandle.getCalendarCurrentDate());

        originalDate = centerPanelHandle.getCalendarCurrentDate();
        postNow(CHANGE_TO_PREVIOUS_PAGE_EVENT);
        guiRobot.pauseForHuman();

        expectedDate = originalDate.minusDays(1);
        assertEquals(expectedDate, centerPanelHandle.getCalendarCurrentDate());
    }

    @Test
    public void handleChangeCalendarDateRequestEvent() {
        centerPanelHandle.setUpCalendarPanelHandle(calendar);

        LocalDate previousDate = centerPanelHandle.getCalendarCurrentDate();
        postNow(CHANGE_DATE_EVENT);
        guiRobot.pauseForHuman();

        assertNotEquals(previousDate, LEAP_YEAR_DATE);
        assertEquals(LEAP_YEAR_DATE, centerPanelHandle.getCalendarCurrentDate());
    }
}
```
###### /java/systemtests/DeleteGroupCommandSystemTest.java
``` java
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

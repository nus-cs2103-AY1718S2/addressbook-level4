# WJY-norainu
###### \data\XmlSerializableAddressBookTest\typicalPersonsAddressBook.xml
``` xml
    <persons>
        <name>Jay Chou</name>
        <phone>134520789201</phone>
        <email>jay@gmail.com</email>
        <address>145, Taiwan</address>
        <tagged>friends</tagged>
        <tagged>celebrity</tagged>
    </persons>
    <persons>
        <name>Sakura Ayane</name>
        <phone>5201314</phone>
        <email>ayane@gmail.com</email>
        <address>Tokyo, Japan</address>
        <tagged>celebrity</tagged>
        <tagged>colleagues</tagged>
    </persons>
    <persons>
        <name>Robert Downey</name>
        <phone>19650404</phone>
        <email>ironman@firefox.com</email>
        <address>USA</address>
        <tagged>celebrity</tagged>
        <tagged>owesMoney</tagged>
    </persons>
    <tags>celebrity</tags>
    <tags>colleagues</tags>
    <tags>friends</tags>
    <tags>owesMoney</tags>
</addressbook>
```
###### \java\seedu\address\logic\commands\calendar\DeleteAppointmentCommandTest.java
``` java
public class DeleteAppointmentCommandTest {

    private Model model;

    @Test
    public void execute_validIndexListingAppointmentsWithRemainingAppointments_success()
            throws DuplicateAppointmentException {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model.addAppointmentToStorageCalendar(CONCERT);
        model.addAppointmentToStorageCalendar(DENTAL);
        model.setIsListingAppointments(true);
        model.setCurrentlyDisplayedAppointments(model.getStoredAppointmentList());
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(
                MESSAGE_SUCCESS,
                CONCERT.getTitle());

        Model expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        expectedModel.addAppointmentToStorageCalendar((new AppointmentBuilder(DENTAL)).build());
        //still have appointments in the list after deletion, should show appointment list
        expectedModel.setIsListingAppointments(true);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deletesTheOnlyAppointmentWithCombinedCalendar_successAndChangeToCombinedCalendar()
            throws DuplicateAppointmentException {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model.addAppointmentToStorageCalendar(DENTAL);
        model.setIsListingAppointments(true);
        model.setCurrentlyDisplayedAppointments(model.getStoredAppointmentList());
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(MESSAGE_SUCCESS, DENTAL.getTitle())
                + String.format(MESSAGE_APPOINTMENT_LIST_BECOMES_EMPTY, "combined");

        Model expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        //have no appointments in the list after deletion, should show calendar
        expectedModel.setIsListingAppointments(false);
        expectedModel.setCelebCalendarViewPage(ModelManager.DAY_VIEW_PAGE);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteTheOnlyAppointmentWithCelebCalendar_successAndShowCelebCalendar()
            throws DuplicateAppointmentException {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model.addAppointmentToStorageCalendar(DENTAL);
        model.setCelebCalendarOwner(JAY);
        model.setIsListingAppointments(true);
        model.setCurrentlyDisplayedAppointments(model.getStoredAppointmentList());
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(MESSAGE_SUCCESS, DENTAL.getTitle())
                + String.format(MESSAGE_APPOINTMENT_LIST_BECOMES_EMPTY, JAY.getName().toString() + "'s");

        Model expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        //have no appointments in the list after deletion, should show calendar
        expectedModel.setIsListingAppointments(false);
        expectedModel.setCelebCalendarViewPage(ModelManager.DAY_VIEW_PAGE);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexNotListingAppointments_throwsCommandException()
            throws DuplicateAppointmentException {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model.addAppointmentToStorageCalendar(CONCERT);
        model.addAppointmentToStorageCalendar(DENTAL);
        model.setCurrentlyDisplayedAppointments(model.getStoredAppointmentList());
        model.setIsListingAppointments(false);
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT);

        assertCommandFailure(deleteAppointmentCommand,
                model, Messages.MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS);
    }

    @Test
    public void execute_invalidIndexListingAppointments_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        model.setIsListingAppointments(true);
        model.setCurrentlyDisplayedAppointments(model.getStoredAppointmentList());
        Index outOfBoundIndex = Index.fromOneBased(model.getAppointmentList().size() + 1);
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        DeleteAppointmentCommand deleteFirstAppointmentCommand = prepareCommand(INDEX_FIRST_APPOINTMENT);
        DeleteAppointmentCommand deleteSecondAppointmentCommand = prepareCommand(INDEX_SECOND_APPOINTMENT);

        // same object -> returns true
        assertTrue(deleteFirstAppointmentCommand.equals(deleteFirstAppointmentCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteFirstAppointmentCommandCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstAppointmentCommand.equals(deleteFirstAppointmentCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstAppointmentCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstAppointmentCommand.equals(null));

        // different appointment -> returns false
        assertFalse(deleteFirstAppointmentCommand.equals(deleteSecondAppointmentCommand));
    }

    /**
     * Returns a {@code DeleteAppointmentCommand} with the parameter {@code index}.
     */
    private DeleteAppointmentCommand prepareCommand(Index index) {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(index);
        deleteAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAppointmentCommand;
    }

}
```
###### \java\seedu\address\logic\commands\calendar\ViewCalendarByCommandTest.java
``` java
public class ViewCalendarByCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());

    @Test
    public void execute_calendarViewByDay_success() {
        ViewCalendarByCommand viewCalendarByCommand = prepareCommand("day");

        String expectedMessage = String.format(ViewCalendarByCommand.MESSAGE_SUCCESS, DAY_VIEW_PAGE);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setCelebCalendarViewPage(DAY_VIEW_PAGE);

        model.setCelebCalendarViewPage(WEEK_VIEW_PAGE);
        assertCommandSuccess(viewCalendarByCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_calendarViewByWeek_success() {
        ViewCalendarByCommand viewCalendarByCommand = prepareCommand("week");

        String expectedMessage = String.format(ViewCalendarByCommand.MESSAGE_SUCCESS, WEEK_VIEW_PAGE);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setCelebCalendarViewPage(WEEK_VIEW_PAGE);

        assertCommandSuccess(viewCalendarByCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_calendarViewByMonth_success() {
        ViewCalendarByCommand viewCalendarByCommand = prepareCommand("month");

        String expectedMessage = String.format(ViewCalendarByCommand.MESSAGE_SUCCESS, MONTH_VIEW_PAGE);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setCelebCalendarViewPage(MONTH_VIEW_PAGE);

        assertCommandSuccess(viewCalendarByCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_changeFromAppointmentListWithNoChangeInView_success() {
        model.setIsListingAppointments(true);
        ViewCalendarByCommand viewCalendarByCommand = prepareCommand(model.getCurrentCelebCalendarViewPage());

        String expectedMessage = String.format(ViewCalendarByCommand.MESSAGE_SUCCESS, DAY_VIEW_PAGE);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setCelebCalendarViewPage(DAY_VIEW_PAGE);

        assertCommandSuccess(viewCalendarByCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_changeFromAppointmentListWithChangeInView_success() {
        model.setIsListingAppointments(true);
        ViewCalendarByCommand viewCalendarByCommand = prepareCommand(WEEK_VIEW_PAGE);

        String expectedMessage = String.format(ViewCalendarByCommand.MESSAGE_SUCCESS, WEEK_VIEW_PAGE);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setCelebCalendarViewPage(WEEK_VIEW_PAGE);

        assertCommandSuccess(viewCalendarByCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noChangeInView_throwsCommandException() {
        ViewCalendarByCommand viewCalendarByCommand = prepareCommand("day");
        assertCommandFailure(viewCalendarByCommand, model, String.format(MESSAGE_NO_CHANGE_IN_CALENDARVIEW,
                DAY_VIEW_PAGE));
    }

    @Test
    public void equals() {
        ViewCalendarByCommand viewCalendarByDayCommand = prepareCommand("day");
        ViewCalendarByCommand viewCalendarByWeekCommand = prepareCommand("week");

        // same object -> returns true
        assertTrue(viewCalendarByDayCommand.equals(viewCalendarByDayCommand));

        // same values -> returns true
        ViewCalendarByCommand viewCalendarByDayCommandCopy = prepareCommand("day");
        assertTrue(viewCalendarByDayCommand.equals(viewCalendarByDayCommandCopy));

        // different types -> returns false
        assertFalse(viewCalendarByDayCommand.equals(1));

        // null -> returns false
        assertFalse(viewCalendarByDayCommand.equals(null));

        // different view page -> returns false
        assertFalse(viewCalendarByDayCommand.equals(viewCalendarByWeekCommand));
    }

    /**
     * Returns a {@code ViewCalendarByCommand} with the parameter {@code calendarViewPage}.
     */
    private ViewCalendarByCommand prepareCommand(String calendarViewPage) {
        ViewCalendarByCommand viewCalendarByCommand = new ViewCalendarByCommand(calendarViewPage);
        viewCalendarByCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewCalendarByCommand;
    }
}
```
###### \java\seedu\address\logic\commands\calendar\ViewCalendarCommandTest.java
``` java
public class ViewCalendarCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());

    @Test
    public void execute_validCelebrityIndex_success() {
        ViewCalendarCommand viewCalendarCommand = prepareCommand(INDEX_ROBERT);

        String expectedMessage = String.format(ViewCalendarCommand.MESSAGE_SUCCESS, ROBERT.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setCelebCalendarOwner(ROBERT);

        assertCommandSuccess(viewCalendarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPersonIndex_throwsCommandException() {
        ViewCalendarCommand viewCalendarCommand = prepareCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(viewCalendarCommand, model, MESSAGE_NOT_CELEBRITY);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(model.getAddressBook().getPersonList().size() + 1);
        ViewCalendarCommand viewCalendarCommand = prepareCommand(invalidIndex);
        assertCommandFailure(viewCalendarCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_celebrityCalendarAlreadyShown_throwsCommandException() {
        model.setCelebCalendarOwner(JAY);
        ViewCalendarCommand viewCalendarCommand = prepareCommand(INDEX_JAY);
        assertCommandFailure(viewCalendarCommand,
                model,
                String.format(MESSAGE_NO_CHANGE_IN_CALENDAR, JAY.getName().toString()));
    }

    @Test
    public void execute_fromAppointmentListViewToCalendar_success() {
        model.setCelebCalendarOwner(JAY);
        model.setIsListingAppointments(true);
        ViewCalendarCommand viewCalendarCommand = prepareCommand(INDEX_JAY);

        String expectedMessage = String.format(ViewCalendarCommand.MESSAGE_SUCCESS, JAY.getName().toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setCelebCalendarOwner(JAY);

        assertCommandSuccess(viewCalendarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ViewCalendarCommand viewJayCalendarCommand = prepareCommand(INDEX_JAY);
        ViewCalendarCommand viewAyaneCalendarCommand = prepareCommand(INDEX_AYANE);

        // same object -> returns true
        assertTrue(viewJayCalendarCommand.equals(viewJayCalendarCommand));

        // same values -> returns true
        ViewCalendarCommand viewJayCalendarCommandCopy = prepareCommand(INDEX_JAY);
        assertTrue(viewJayCalendarCommand.equals(viewJayCalendarCommandCopy));

        // different types -> returns false
        assertFalse(viewJayCalendarCommand.equals(1));

        // null -> returns false
        assertFalse(viewJayCalendarCommand.equals(null));

        // different calendar -> returns false
        assertFalse(viewJayCalendarCommand.equals(viewAyaneCalendarCommand));
    }

    /**
     * Returns a {@code ViewCalendarCommand} with the parameter {@code index}.
     */
    private ViewCalendarCommand prepareCommand(Index index) {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(index);
        viewCalendarCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewCalendarCommand;
    }
}
```
###### \java\seedu\address\logic\commands\calendar\ViewCombinedCalenrCommandTest.java
``` java
public class ViewCombinedCalenrCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());

    @Test
    public void execute_notAlreadyInCombinedCalendar_success() {
        model.setCelebCalendarOwner(JAY);
        ViewCombinedCalendarCommand viewCombinedCalendarCommand = prepareCommand();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());

        assertCommandSuccess(viewCombinedCalendarCommand, model,
                ViewCombinedCalendarCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_changeFromAppointmentListAlreadyInCombinedCalendar_success() {
        model.setCelebCalendarOwner(null);
        model.setIsListingAppointments(true);
        ViewCombinedCalendarCommand viewCombinedCalendarCommand = prepareCommand();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());

        assertCommandSuccess(viewCombinedCalendarCommand, model,
                ViewCombinedCalendarCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_alreadyInCombinedCalendar_throwsCommandException() {
        model.setCelebCalendarOwner(null);
        ViewCombinedCalendarCommand viewCombinedCalendarCommand = prepareCommand();

        assertCommandFailure(viewCombinedCalendarCommand, model, MESSAGE_ALREADY_IN_COMBINED_VIEW);
    }

    /**
     * Returns a {@code ViewCombinedCalendarCommand}.
     */
    private ViewCombinedCalendarCommand prepareCommand() {
        ViewCombinedCalendarCommand viewCombinedCalendarCommand = new ViewCombinedCalendarCommand();
        viewCombinedCalendarCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewCombinedCalendarCommand;
    }
}
```
###### \java\seedu\address\logic\commands\calendar\ViewDateCommandTest.java
``` java
public class ViewDateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
    private LocalDate mayFirst2018 = LocalDate.of(2018, 5, 1);
    private LocalDate maySecond2018 = LocalDate.of(2018, 5, 2);

    @Test
    public void execute_viewADifferentDay_success() {
        model.setBaseDate(mayFirst2018);
        ViewDateCommand viewDateCommand = prepareCommand(maySecond2018);

        String expectedMessage = String.format(ViewDateCommand.MESSAGE_SUCCESS,
                maySecond2018.format(ViewDateCommand.FORMATTER));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setBaseDate(maySecond2018);

        assertCommandSuccess(viewDateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_changeFromAppointmentListViewSameDay_success() {
        model.setBaseDate(mayFirst2018);
        model.setIsListingAppointments(true);
        ViewDateCommand viewDateCommand = prepareCommand(mayFirst2018);

        String expectedMessage = String.format(ViewDateCommand.MESSAGE_SUCCESS,
                mayFirst2018.format(ViewDateCommand.FORMATTER));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setBaseDate(mayFirst2018);

        assertCommandSuccess(viewDateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_changeFromWeekViewPageViewADifferentDay_success() {
        model.setCelebCalendarViewPage(WEEK_VIEW_PAGE);
        model.setBaseDate(mayFirst2018);
        ViewDateCommand viewDateCommand = prepareCommand(maySecond2018);

        String expectedMessage = String.format(ViewDateCommand.MESSAGE_SUCCESS,
                maySecond2018.format(ViewDateCommand.FORMATTER));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(),
                new UserPrefs());
        expectedModel.setBaseDate(maySecond2018);

        assertCommandSuccess(viewDateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_changeFromWeekViewPageViewSameDay_throwsCommandException() {
        model.setCelebCalendarViewPage(WEEK_VIEW_PAGE);
        model.setBaseDate(mayFirst2018);
        ViewDateCommand viewDateCommand = prepareCommand(mayFirst2018);

        assertCommandFailure(viewDateCommand, model, String.format(MESSAGE_NO_CHANGE_IN_BASE_DATE,
                mayFirst2018.format(ViewDateCommand.FORMATTER)));
    }

    @Test
    public void execute_viewSameDay_throwsCommandException() {
        model.setBaseDate(mayFirst2018);
        ViewDateCommand viewDateCommand = prepareCommand(mayFirst2018);

        assertCommandFailure(viewDateCommand, model, String.format(MESSAGE_NO_CHANGE_IN_BASE_DATE,
                mayFirst2018.format(ViewDateCommand.FORMATTER)));
    }

    @Test
    public void equals() {
        ViewDateCommand viewDateMayFirstCommand = prepareCommand(mayFirst2018);
        ViewDateCommand viewDateMaySecondCommand = prepareCommand(maySecond2018);

        // same object -> returns true
        assertTrue(viewDateMayFirstCommand.equals(viewDateMayFirstCommand));

        // same values -> returns true
        ViewDateCommand viewDateMayFirstCommandCopy = prepareCommand(mayFirst2018);
        assertTrue(viewDateMayFirstCommand.equals(viewDateMayFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewDateMayFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewDateMayFirstCommand.equals(null));

        // different calendar -> returns false
        assertFalse(viewDateMayFirstCommand.equals(viewDateMaySecondCommand));
    }

    /**
     * Returns a {@code ViewDateCommand} with the parameter {@code index}.
     */
    private ViewDateCommand prepareCommand(LocalDate date) {
        ViewDateCommand viewDateCommand = new ViewDateCommand(date);
        viewDateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewDateCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
    private Tag nonExistingTag = new Tag("thisTagNameIsSuperLongAndThereShouldntBeAnyoneWithSuchATag");

    @Test
    public void constructor_nullTag_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveTagCommand(null);
    }

    @Test
    public void execute_celebrityTag_throwsCommandException() {
        RemoveTagCommand removeTagCommand = prepareCommand(CELEBRITY_TAG);
        assertCommandFailure(removeTagCommand, model, MESSAGE_CANNOT_REMOVE_CELEBRITY_TAG);
    }

    @Test
    public void execute_nonExistingTag_throwsCommandException() {
        RemoveTagCommand removeTagCommand = prepareCommand(nonExistingTag);
        assertCommandFailure(removeTagCommand, model,
                String.format(MESSAGE_TAG_NOT_FOUND, nonExistingTag.toString()));
    }

    @Test
    public void execute_friendsTag_success() throws Exception {
        RemoveTagCommand removeTagCommand = prepareCommand(FRIENDS_TAG);
        int count = model.countPersonsWithTag(FRIENDS_TAG);

        String expectedMessage = String.format(
                RemoveTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                FRIENDS_TAG.toString(),
                count);

        Model expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());
        expectedModel.removeTag(FRIENDS_TAG);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_friendsTagWhichThreePersonsHave_success() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), generateEmptyStorageCalendar(), new UserPrefs());

        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemoveTagCommand removeTagCommand = prepareCommand(FRIENDS_TAG);

        // removeTag -> friends tag removed
        removeTagCommand.execute();
        undoRedoStack.push(removeTagCommand);

        // undo -> reverts address book back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> friends tag deleted again
        expectedModel.removeTag(FRIENDS_TAG);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        RemoveTagCommand removeTagFirstCommand = prepareCommand(FRIENDS_TAG);
        RemoveTagCommand removeTagSecondCommand = prepareCommand(HUSBAND_TAG);

        // same object -> returns true
        assertTrue(removeTagFirstCommand.equals(removeTagFirstCommand));

        // same tag -> returns true
        RemoveTagCommand removeTagFirstCommandCopy = prepareCommand(FRIENDS_TAG);
        assertTrue(removeTagFirstCommand.equals(removeTagFirstCommandCopy));


        // different types -> returns false
        assertFalse(removeTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeTagFirstCommand.equals(null));

        // different tags -> returns false
        assertFalse(removeTagFirstCommand.equals(removeTagSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private RemoveTagCommand prepareCommand(Tag tag) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tag);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }
}
```
###### \java\seedu\address\logic\parser\calendar\DeleteAppointmentCommandParserTest.java
``` java
public class DeleteAppointmentCommandParserTest {
    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAppointmentCommand() {
        assertParseSuccess(parser, "1", new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ListAppointmentCommandParserTest.java
``` java
public class ListAppointmentCommandParserTest {
    private ListAppointmentCommandParser parser = new ListAppointmentCommandParser();

    private LocalDate aprilSecondCurrentYear = LocalDate.of(LocalDate.now().getYear(), 4, 2);
    private LocalDate mayFirstCurrentYear = LocalDate.of(LocalDate.now().getYear(), 5, 1);
    private LocalDate aprilSecond2018 = LocalDate.of(2018, 4, 2);
    private LocalDate mayFirst2018 = LocalDate.of(2018, 5, 1);

    @Test
    public void parse_noInput_returnsListAppointmentCommand() {
        assertParseSuccess(parser, "", new ListAppointmentCommand());
    }

    @Test
    public void parse_dateMonthInCorrectFormat_returnsListAppointmentCommand() {
        assertParseSuccess(parser,
                "02-04 01-05",
                new ListAppointmentCommand(aprilSecondCurrentYear, mayFirstCurrentYear));
    }

    @Test
    public void parse_dateMonthYearInCorrectFormat_returnsListAppointmentCommand() {
        assertParseSuccess(parser,
                "02-04-2018 01-05-2018",
                new ListAppointmentCommand(aprilSecond2018, mayFirst2018));
    }

    @Test
    //this method assumes test is done in the year of 2018 or after 2018
    public void parse_dateMonthAndDateMonthYearInCorrectFormat_returnsListAppointmentCommand() {
        assertParseSuccess(parser,
                "02-04-2018 01-05",
                new ListAppointmentCommand(aprilSecond2018, mayFirstCurrentYear));
    }

    @Test
    public void parse_missingDate_throwsParseException() {
        assertParseFailure(parser,
                "04-2018 05-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingMonth_throwsParseException() {
        assertParseFailure(parser,
                "31-2018 30-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_dateMonthYearInWrongFormat_throwsParseException() {
        assertParseFailure(parser,
                "02/04/2018 01/05/2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        assertParseFailure(parser, "31-02 01-03", MESSAGE_INVALID_DATE);
        assertParseFailure(parser, "01-02 32-03", MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_startDateAfterEndDate_throwsParseException() {
        assertParseFailure(parser,
                "02-04-2019 01-05-2018",
                MESSAGE_INVALID_DATE_RANGE);
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ViewCalendarByCommandParserTest.java
``` java
public class ViewCalendarByCommandParserTest {
    private ViewCalendarByCommandParser parser = new ViewCalendarByCommandParser();

    @Test
    public void parse_day_returnsViewCalendarByCommand() {
        assertParseSuccess(parser, "day", new ViewCalendarByCommand(DAY_VIEW_PAGE));
    }

    @Test
    public void parse_dayWithUpperCaseLetter_returnsViewCalendarByCommand() {
        assertParseSuccess(parser, "Day", new ViewCalendarByCommand(DAY_VIEW_PAGE));
        assertParseSuccess(parser, "DAy", new ViewCalendarByCommand(DAY_VIEW_PAGE));
        assertParseSuccess(parser, "DAY", new ViewCalendarByCommand(DAY_VIEW_PAGE));
    }

    @Test
    public void parse_week_returnsViewCalendarByCommand() {
        assertParseSuccess(parser, "week", new ViewCalendarByCommand(WEEK_VIEW_PAGE));
    }

    @Test
    public void parse_month_returnsViewCalendarByCommand() {
        assertParseSuccess(parser, "month", new ViewCalendarByCommand(MONTH_VIEW_PAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCalendarByCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ViewCalendarCommandParserTest.java
``` java
public class ViewCalendarCommandParserTest {
    private ViewCalendarCommandParser parser = new ViewCalendarCommandParser();
    private String failureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCalendarCommand.MESSAGE_USAGE);

    @Test
    public void parse_noInput_throwsParseException() {
        assertParseFailure(parser, "", failureMessage);
    }

    @Test
    public void parse_letter_throwsParseException() {
        assertParseFailure(parser, "a", failureMessage);
    }

    @Test
    public void parse_negativeInteger_throwsParseException() {
        assertParseFailure(parser, "-1", failureMessage);
    }

    @Test
    public void parse_zero_throwsParseException() {
        assertParseFailure(parser, "0", failureMessage);
    }

    @Test
    public void parse_validIndex_returnsViewCalendarCommand() {
        assertParseSuccess(parser, "1", new ViewCalendarCommand(INDEX_FIRST_PERSON));
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ViewDateCommandParserTest.java
``` java
public class ViewDateCommandParserTest {
    private ViewDateCommandParser parser = new ViewDateCommandParser();
    private LocalDate mayFirstCurrentYear = LocalDate.of(LocalDate.now().getYear(), 5, 1);
    private LocalDate mayFirst2018 = LocalDate.of(2018, 5, 1);

    @Test
    public void parse_noInput_returnsViewDateCommand() {
        assertParseSuccess(parser, "", new ViewDateCommand(LocalDate.now()));
    }

    @Test
    public void parse_dateMonthInCorrectFormat_returnsViewDateCommand() {
        assertParseSuccess(parser, "01-05", new ViewDateCommand(mayFirstCurrentYear));
    }

    @Test
    public void parse_dateMonthYearInCorrectFormat_returnsViewDateCommand() {
        assertParseSuccess(parser, "01-05-2018", new ViewDateCommand(mayFirst2018));
    }

    @Test
    public void parse_missingDate_throwsParseException() {
        assertParseFailure(parser, "05-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewDateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingMonth_throwsParseException() {
        assertParseFailure(parser, "01-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewDateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_dateMonthYearInWrongFormat_throwsParseException() {
        assertParseFailure(parser, "01 05 2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewDateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        assertParseFailure(parser, "31-02", MESSAGE_INVALID_DATE);
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParserTest.java
``` java
public class RemoveTagCommandParserTest {

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() {
        assertParseSuccess(parser, VALID_TAG_FRIEND, new RemoveTagCommand(FRIENDS_TAG));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsContainingSpecialCharacters_throwsParseException() {
        assertParseFailure(parser,
                "%#friends",
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void countPersonsWithTag_countsFriendsTag_returnNumberOfPersonsWithFriendsTag() {
        assertEquals(addressBookWithAlice.countPersonsWithTag(FRIENDS_TAG), 1);
    }

    @Test
    public void countPersonsWithTag_countsHusbandTag_returnNumberOfPersonsWithHusbandTag() {
        assertEquals(addressBookWithAlice.countPersonsWithTag(HUSBAND_TAG), 0);
    }

    @Test
    public void removeTag_existentTag_tagRemoved() throws Exception {
        addressBookWithBobAndAmy.removeTag(new Tag(VALID_TAG_FRIEND));

        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(bobWithoutFriendTag)
                .withPerson(amyWithoutFriendTag).build();

        assertEquals(expectedAddressBook, addressBookWithBobAndAmy);
    }

```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void removePerson_theOnlyPersonWithHusbandTagRemoved_tagListUpdated() throws Exception {
        addressBookWithBobAndAlice.removePerson(BOB);

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).build();
        assertEquals(expectedAddressBook, addressBookWithBobAndAlice);
    }

    @Test
    public void updatePerson_theOnlyPersonWithFriendTagUpdated_tagListUpdated() throws Exception {
        Person aliceWithoutFriendTag = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        addressBookWithAlice.updatePerson(ALICE, aliceWithoutFriendTag);

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(aliceWithoutFriendTag).build();
        assertEquals(expectedAddressBook, addressBookWithAlice);
    }

```
###### \java\seedu\address\testutil\TypicalCelebrities.java
``` java
/**
 * A utility class containing a list of {@code Celebrity} objects to be used in tests.
 */
public class TypicalCelebrities {
    public static final Celebrity JAY = new Celebrity(
            new PersonBuilder().withName("Jay Chou")
                    .withAddress("145, Taiwan")
                    .withEmail("jay@gmail.com")
                    .withPhone("134520789201")
                    .withTags("friends", "celebrity").build());

    public static final Celebrity AYANE = new Celebrity(
            new PersonBuilder().withName("Sakura Ayane")
                    .withAddress("Tokyo, Japan")
                    .withEmail("ayane@gmail.com")
                    .withPhone("5201314")
                    .withTags("celebrity", "colleagues").build());

    public static final Celebrity ROBERT = new Celebrity(
            new PersonBuilder().withName("Robert Downey")
                    .withAddress("USA")
                    .withEmail("ironman@firefox.com")
                    .withPhone("19650404")
                    .withTags("celebrity", "owesMoney").build());

    public static List<Celebrity> getTypicalCelebrities() {
        return new ArrayList<>(Arrays.asList(JAY, AYANE, ROBERT));
    }
}
```
###### \java\systemtests\RemoveTagCommandSystemTest.java
``` java
public class RemoveTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void removeTag() throws Exception {
        Model model = getModel();

        /* ----------------------------------- Perform valid removeTag operations ----------------------------------- */

        /* Case: remove tag friends from a non-empty address book that has this tag in tag list, command with leading
         * spaces and trailing spaces
         * -> tag removed and shows 8 persons affected
         */
        String command = "   " + RemoveTagCommand.COMMAND_WORD + "  " + FRIENDS_TAG.tagName + " ";
        assertCommandSuccess(command, FRIENDS_TAG);

        /* Case: undo removing tag friends from the list -> tag friends restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo removing tag friends from the list -> tag friends removed again */
        command = RedoCommand.COMMAND_WORD;
        model.removeTag(FRIENDS_TAG);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: remove tag [owesMoney] from a non-empty address book that has this tag in tag list
         * -> tag removed and shows 1 person affected
         */
        Tag owesMoney = new Tag("owesMoney");
        assertCommandSuccess(owesMoney);

        /* ----------------------------------- Perform invalid removeTag operations --------------------------------- */

        /* Case: missing tag -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + "";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "removesTag " + VALID_TAG_FRIEND;
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid tag name -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: valid tag name but tag does not exist in the address book -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND;
        assertCommandFailure(command, String.format(RemoveTagCommand.MESSAGE_TAG_NOT_FOUND, FRIENDS_TAG.toString()));

        /* Case: celebrity tag -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " " + CELEBRITY_TAG.tagName;
        assertCommandFailure(command, String.format(RemoveTagCommand.MESSAGE_CANNOT_REMOVE_CELEBRITY_TAG));
    }

    /**
     * Executes the {@code RemoveTagCommand} that removes {@code toRemove} from the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code RemoveTagCommand} with the details of
     * {@code toRemove}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the original empty model.<br>
     * 5. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Tag toRemove) throws Exception {
        assertCommandSuccess(RemoveTagCommand.COMMAND_WORD + " " + toRemove.tagName, toRemove);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Tag)}. Executes {@code command}
     * instead.
     * @see RemoveTagCommandSystemTest#assertCommandSuccess(Tag)
     */
    private void assertCommandSuccess(String command, Tag toRemove) throws Exception {
        Model expectedModel = getModel();
        int numOfPersonsAffected = expectedModel.countPersonsWithTag(toRemove);
        expectedModel.removeTag(toRemove);
        String expectedResultMessage = String.format(RemoveTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                                                    toRemove.toString(),
                                                    numOfPersonsAffected);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Tag)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see RemoveTagCommandSystemTest#assertCommandSuccess(String, Tag)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertResultDisplayAndCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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

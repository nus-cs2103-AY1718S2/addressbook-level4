# AzuraAiR
###### \java\guitests\guihandles\BirthdayListHandle.java
``` java
/**
 * A handler for the {@code BirthdayList} of the UI
 */
public class BirthdayListHandle extends NodeHandle<TextArea> {

    public static final String BIRTHDAYS_LIST_ID = "#birthdayList";

    public BirthdayListHandle(TextArea birthdayListDisplayNode) {
        super(birthdayListDisplayNode);
    }

    /**
     * Returns the text in the birthday list
     */
    public String getText() {
        return getRootNode().getText();
    }

    public boolean getFront() {
        return getRootNode().getChildrenUnmodifiable().get(0).equals(this);
    }

}
```
###### \java\guitests\guihandles\BirthdayNotificationHandle.java
``` java
/**
 * A handle for the {@code AlertDialog} of the UI.
 */
public class BirthdayNotificationHandle extends StageHandle {
    private final DialogPane dialogPane;

    public BirthdayNotificationHandle(Stage stage) {
        super(stage);

        this.dialogPane = getChildNode("#" + "birthdayDialogPane");
    }

    /**
     * Returns the text of the header in the {@code AlertDialog}.
     */
    public String getHeaderText() {
        return dialogPane.getHeaderText();
    }

    /**
     * Returns the text of the content in the {@code AlertDialog}.
     */
    public String getContentText() {
        return dialogPane.getContentText();
    }
}
```
###### \java\seedu\address\commons\util\TimetableParserUtilTest.java
``` java
public class TimetableParserUtilTest {

    private static final String EMPTY_URL = " ";
    private static final String VALID_WEEK = "Odd Week";
    private static final String VALID_DAY = "Wednesday";
    private static final int VALID_TIMESLOT = 11;

    private static final String VALID_URL = "http://modsn.us/kqUAK";
    private static final String INVALID_OTHER_URL = "http://google.com/";
    private static final String INVALID_NUSMODS_URL = "http://modsn.us/zzzzz";

    @Test
    public void parseUrl_nullUrl_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> TimetableParserUtil.parseUrl(null));
    }

    @Test
    public void parseUrl_emptyUrl_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> TimetableParserUtil.parseUrl(EMPTY_URL));
    }

    @Test
    public void parseUrl_invalidUrl_throwsIllegalArgumentAndParseException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> TimetableParserUtil.parseUrl(INVALID_OTHER_URL));
        Assert.assertThrows(ParseException.class, () -> TimetableParserUtil.parseUrl(INVALID_NUSMODS_URL));
    }

    @Test
    public void parseShortUrl_validUrl_success() {
        Timetable timetable = new TimetableBuilder().getDummy(0);
        try {
            assertEquals(timetable.getLessonFromSlot(VALID_WEEK, VALID_DAY, VALID_TIMESLOT).toString(),
                TimetableParserUtil.parseUrl(VALID_URL).getLessonFromSlot(VALID_WEEK, VALID_DAY, VALID_TIMESLOT)
                        .toString());
        } catch (IllegalValueException pe) {
            fail("Unexpected exception thrown " + pe);
        }
    }

}
```
###### \java\seedu\address\logic\commands\BirthdaysCommandTest.java
``` java
public class BirthdaysCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_emptyBirthdays_birthdaysFailure() {
        thrown.expect(NullPointerException.class);
        new BirthdaysCommand(false).execute();
    }


    @Test
    public void execute_birthdaysWithoutToday_birthdaysSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult result = prepareCommand(false, model).execute();

        assertEquals(SHOWING_BIRTHDAY_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BirthdayListEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_birthdaysWithToday_birthdaysSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult result = prepareCommand(true, model).execute();

        assertEquals(SHOWING_BIRTHDAY_NOTIFICATION, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BirthdayNotificationEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * Generates a new {@code ClearCommand} which upon execution, clears the contents in {@code model}.
     */
    private BirthdaysCommand prepareCommand(boolean today, Model model) {
        BirthdaysCommand command = new BirthdaysCommand(today);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\TimetableUnionCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code TimetableUnionCommand}.
 */
public class TimetableUnionCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        assertExecutionSuccess(indexes, ODD);

        indexes.add(lastPersonIndex);
        assertExecutionSuccess(indexes, ODD);
        assertExecutionSuccess(indexes, EVEN);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndexOne = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundsIndexTwo = Index.fromOneBased(model.getFilteredPersonList().size() + 2);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(outOfBoundsIndexOne);
        indexes.add(outOfBoundsIndexTwo);

        assertExecutionFailure(indexes, EVEN, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        indexes.add(outOfBoundsIndex);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(indexes, EVEN, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        TimetableUnionCommand timetableFirstCommand = new TimetableUnionCommand(indexes, EVEN);
        TimetableUnionCommand timetableFirstCommandCopy = new TimetableUnionCommand(indexes, EVEN);
        TimetableUnionCommand timetableSecondCommand = new TimetableUnionCommand(indexes, ODD);
        indexes.add(INDEX_THIRD_PERSON);
        TimetableUnionCommand timetableThirdCommand = new TimetableUnionCommand(indexes, ODD);

        // same object -> returns true
        assertTrue(timetableFirstCommand.equals(timetableFirstCommand));

        // same values -> returns true
        assertTrue(timetableFirstCommand.equals(timetableFirstCommandCopy));

        // different types -> returns false
        assertFalse(timetableFirstCommand.equals(1));

        // null -> returns false
        assertFalse(timetableFirstCommand.equals(null));

        // different OddEven -> returns false
        assertFalse(timetableFirstCommand.equals(timetableSecondCommand));

        // different person -> returns false
        assertFalse(timetableFirstCommand.equals(timetableThirdCommand));

    }

    /**
     * Executes a {@code TimetableUnionCommand} with the given {@code indexes}  and {@code oddEven},
     * and checks that {@code JumpToListRequestEvent}
     * is raised with the correct indexes and oddEven.
     */
    private void assertExecutionSuccess(ArrayList<Index> indexes, String oddEven) {
        TimetableUnionCommand timetableUnionCommand = prepareCommand(indexes, oddEven);
        ArrayList<Person> targets = new ArrayList<Person>();
        ArrayList<Timetable> timetables = new ArrayList<Timetable>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indexes.size(); i++) {
            Person target = model.getFilteredPersonList().get(indexes.get(i).getZeroBased());
            targets.add(target);
            timetables.add(target.getTimetable());
            sb.append(target.getName());
            if (i != indexes.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("\n");

        int oddEvenIndex = StringUtil.getOddEven(oddEven).getZeroBased();
        ArrayList<ArrayList<ArrayList<String>>> targetList = Timetable.unionTimetable(timetables);
        try {
            CommandResult commandResult = timetableUnionCommand.execute();

            assertEquals(String.format(TimetableUnionCommand.MESSAGE_SELECT_PERSON_SUCCESS, oddEven, sb.toString()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        TimeTableEvent lastEvent = (TimeTableEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(targetList.get(oddEvenIndex), lastEvent.getTimeTable());
    }

    /**
     * Executes a {@code TimetableUnionCommand} with the given {@code indexes} and {@code oddEven},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(ArrayList<Index> indexes, String oddEven, String expectedMessage) {
        TimetableUnionCommand timetableUnionCommand = prepareCommand(indexes, oddEven);

        try {
            timetableUnionCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code TimetableUnionCommand} with parameters {@code indexes} and {@code oddEven}.
     */
    private TimetableUnionCommand prepareCommand(ArrayList<Index> indexes, String oddEven) {
        TimetableUnionCommand timetableUnionCommand = new TimetableUnionCommand(indexes, oddEven);
        timetableUnionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return timetableUnionCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_birthdays() throws Exception {
        BirthdaysCommand command = (BirthdaysCommand) parser.parseCommand(
                BirthdaysCommand.COMMAND_WORD);
        assertEquals(new BirthdaysCommand(false), command);
    }

    @Test
    public void parseCommand_birthdaysToday() throws Exception {
        BirthdaysCommand command = (BirthdaysCommand) parser.parseCommand(
                BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER);
        assertEquals(new BirthdaysCommand(true), command);
    }

    @Test
    public void parseCommand_timeTableUnion() throws Exception {
        TimetableUnionCommand command = (TimetableUnionCommand) parser
                .parseCommand(TimetableUnionCommand.COMMAND_WORD + " Odd 1 2");
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        assertEquals(new TimetableUnionCommand(indexes, "Odd"), command);
    }
```
###### \java\seedu\address\logic\parser\BirthdaysCommandParserTest.java
``` java
public class BirthdaysCommandParserTest {

    private BirthdaysCommandParser parser = new BirthdaysCommandParser();

    @Test
    public void parse_todaysFieldMissing_success() {
        assertParseSuccess(parser, "", new BirthdaysCommand(false));
    }

    @Test
    public void parse_todaysFieldPresent_success() {
        assertParseSuccess(parser, "today", new BirthdaysCommand(true));
    }

    @Test
    public void parse_todaysFieldinvalid_failure() {
        assertParseFailure(parser, "tomorrow", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdaysCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    private static final String VALID_TIMETABLE = "http://modsn.us/oNZLY";
    private static final String VALID_BIRTHDAY = "01011995";
    private static final String INVALID_TIMETABLE = "http://google.com/";
    private static final String INVALID_BIRTHDAY = "31021985";
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseBirthday_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBirthday((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBirthday((Optional<String>) null));
    }

    @Test
    public void parseBirthday_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseBirthday(INVALID_BIRTHDAY));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseBirthday(Optional
                .of(INVALID_BIRTHDAY)));
    }

    @Test
    public void parseTimetable_validValue_returnsBirthday() throws Exception {
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        assertEquals(expectedBirthday, ParserUtil.parseBirthday(VALID_BIRTHDAY));
        assertEquals(Optional.of(expectedBirthday), ParserUtil.parseBirthday(Optional.of(VALID_BIRTHDAY)));
    }


    @Test
    public void parseTimetable_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTimetable((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTimetable((Optional<String>) null));
    }

    @Test
    public void parseTimetable_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTimetable(INVALID_TIMETABLE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTimetable(Optional
                .of(INVALID_TIMETABLE)));
    }

    @Test
    public void parseTimetable_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTimetable(Optional.empty()).isPresent());
    }

    @Test
    public void parseTimetable_validValueWithoutWhitespace_returnsTimetable() throws Exception {
        Timetable expectedTimetable = new Timetable(VALID_TIMETABLE);
        assertEquals(expectedTimetable, ParserUtil.parseTimetable(VALID_TIMETABLE));
        assertEquals(Optional.of(expectedTimetable), ParserUtil.parseTimetable(Optional.of(VALID_TIMETABLE)));
    }

    @Test
    public void parseTimetable_validValueWithWhitespace_returnsTrimmedTimetable() throws Exception {
        String timetableWithWhitespace = WHITESPACE + VALID_TIMETABLE + WHITESPACE;
        Timetable expectedTimetable = new Timetable(VALID_TIMETABLE);
        assertEquals(expectedTimetable, ParserUtil.parseTimetable(timetableWithWhitespace));
        assertEquals(Optional.of(expectedTimetable), ParserUtil.parseTimetable(Optional
                .of(timetableWithWhitespace)));
    }
```
###### \java\seedu\address\logic\parser\TimetableUnionCommandParserTest.java
``` java
public class TimetableUnionCommandParserTest {

    private TimetableUnionCommandParser parser = new TimetableUnionCommandParser();

    @Test
    public void parse_validArgs_returnsTimeTableCommand() {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "Odd 1 2", new TimetableUnionCommand(indexes, ODD));
    }

    @Test
    public void parse_moreThanTwoValidArgs_returnsTimeTableCommand() {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        indexes.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "Odd 1 2 3", new TimetableUnionCommand(indexes, ODD));
    }

    @Test
    public void parse_invalidNumArgs_throwsParseException() {
        assertParseFailure(parser, "Odd 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimetableUnionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "odd1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimetableUnionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedArgs_throwsParseException() {
        assertParseFailure(parser, "Odd 1 1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimetableUnionCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidBirthday_throwsIllegalArgumentException() {
        String invalidBirthday = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidBirthday_nullBirthday_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));
    }

    @Test
    public void isValidBirthday_emptyBirthday_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_BIRTHDAY_CONSTRAINTS);
        Birthday.isValidBirthday("");
    }

    @Test
    public void isValidBirthday_birthdayWithSpaces_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_BIRTHDAY_CONSTRAINTS);
        Birthday.isValidBirthday("     ");
    }

    @Test
    public void isValidBirthday_tooShortBirthday_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_BIRTHDAY_CONSTRAINTS);
        Birthday.isValidBirthday("121212");

    }

    @Test
    public void isValidBirthday_tooLongBirthday_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_BIRTHDAY_CONSTRAINTS);
        Birthday.isValidBirthday("1212121212");
    }

    @Test
    public void isValidBirthday_invalidYear_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_FUTURE_BIRTHDAY);
        Birthday.isValidBirthday("01012020");
    }

    @Test
    public void isValidBirthday_invalidDay_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_INVALID_BIRTHDAY);
        Birthday.isValidBirthday("00011995");
    }

    @Test
    public void isValidBirthday_invalidMonth_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_INVALID_BIRTHMONTH);
        Birthday.isValidBirthday("01131995");
    }

    @Test
    public void isValidBirthday_invalidDayOfMonth_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_INVALID_BIRTHDAY);
        Birthday.isValidBirthday("30021995");
    }

    @Test
    public void isValidBirthday_futureBirthdaySameYear_throwsIllegalArgumentException() {
        LocalDate today = LocalDate.now();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_FUTURE_BIRTHDAY);
        Birthday.isValidBirthday(buildBirthday(today, 10, today.getMonthValue() + 1));
    }

    @Test
    public void isValidBirthday_futureBirthdaySameMonth_throwsIllegalArgumentException() {
        LocalDate today = LocalDate.now();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_FUTURE_BIRTHDAY);

        Birthday.isValidBirthday(buildBirthday(today, today.getDayOfMonth() + 1, today.getMonthValue()));
    }

    @Test
    public void isValidBirthday_validBirthday_success() {
        try {
            assertTrue(Birthday.isValidBirthday("01011995"));
        } catch (IllegalArgumentException iae) {
            // Should never go here
        }
    }

    @Test
    public void getValidDayMonth() {
        Birthday birthdayStub = new Birthday("01121995");

        assertTrue(birthdayStub.getDay() == 1); // check Day
        assertTrue(birthdayStub.getMonth() == 12); // check Month
        assertTrue(birthdayStub.getYear() == 1995); // check Year
    }

    /**
     * Creates a stub for testing isValidBirthday with user input
     * @param today date of the computer running the rest
     * @param day day to be entered as valid date
     * @param month month to be entered as valid date
     * @return valid birthday whose values are adjusted for testing
     */
    private String buildBirthday(LocalDate today, int day, int month) {
        StringBuilder sb = new StringBuilder();
        int year = today.getYear();

        // Append day
        if (day < 10) { // Set day to 10 to avoid unnecessary trouble with preceding 0
            day = 10;
        } else if (day + 1 > 28) {  // If day exceeds the upper limit of any month, skip to next month
            month += 1;
            day = 10;
        }
        sb.append(day);

        // Append month
        if (month < 10) {   // Add the preceding 0
            sb.append("0");
        } else if (month > 12) {    // If month exceeds year, skip to next year
            year += 1;
            month = 10;
        }
        sb.append(month);
        sb.append(year);

        return sb.toString();
    }
}

```
###### \java\seedu\address\testutil\TimetableBuilder.java
``` java
/**
 * A utility class to help build dummy timetables for Persons
 * This is to prevent the tests from trying to retrieve information from NUSMods API too many times
 */
public class TimetableBuilder {

    public static final int NUM_OF_DUMMY_TIMETABLE = 2;
    public static final String DUMMY_LINK_ONE = "http://modsn.us/aaaaa";
    public static final String DUMMY_LINK_TWO = "http://modsn.us/bbbbb";

    private Timetable[] dummyTimetables;

    public TimetableBuilder() {
        dummyTimetables = new Timetable[NUM_OF_DUMMY_TIMETABLE];
        dummyTimetables[0] = fillTimetableWithDummyValues(new Timetable(DUMMY_LINK_ONE));
        dummyTimetables[1] = fillTimetableWithDummyValues(new Timetable(DUMMY_LINK_TWO));

    }

    /**
     * Gets the dummy timetable from the array of dummy timetables
     * @param index
     * @return Timetable with hardcoded values
     */
    public Timetable getDummy(int index) {
        if (index >= 0 && index < NUM_OF_DUMMY_TIMETABLE) {
            return dummyTimetables[index];
        } else {
            return new Timetable("");   // Create empty timetable as failsafe
        }
    }

    /**
     * Helper method to fill the timetable with dummy values
     * @param timetable to be filled
     * @return dummy Timetable
     */
    private Timetable fillTimetableWithDummyValues(Timetable timetable) {

        ArrayList<Lesson> dummyLessons = new ArrayList<Lesson>();
        dummyLessons.add(new Lesson("CS2103T", "T6", "Tutorial", "Every Week",
                "Wednesday", "1100", "1200"));
        if (timetable.value.equalsIgnoreCase(DUMMY_LINK_TWO)) {   // To differentiate dummy timetable 1 and 2
            dummyLessons.add(new Lesson("CS2101", "6", "Sectional Teaching",
                    "Every Week", "Tuesday", "1000", "1200"));
            dummyLessons.add(new Lesson("CS2101", "6", "Sectional Teaching",
                    "Every Week", "Thursday", "1000", "1200"));
            dummyLessons.add(new Lesson("CS2101", "6", "Sectional Teaching",
                    "Every Week", "Friday", "1600", "1800"));
        }

        try {
            for (Lesson lessonToAdd : dummyLessons) {
                timetable.addLessonToSlot(lessonToAdd);
            }
        } catch (IllegalValueException ie) {
            return new Timetable(""); // Should never happen since dummy values are hardcoded
        }

        return timetable;
    }

}
```
###### \java\seedu\address\ui\BirthdayListTest.java
``` java
public class BirthdayListTest extends GuiUnitTest {

    private List<Person> personListStub;
    private ObservableList<Person> personObservableListStub;

    private BirthdayList birthdayList;

    private BirthdayListEvent birthdayListEventStub;

    private BirthdayListHandle birthdaysListHandle;

    @Before
    public void setUp() {
        birthdayList = new BirthdayList();
        uiPartRule.setUiPart(birthdayList);

        birthdaysListHandle = new BirthdayListHandle(getChildNode(birthdayList.getRoot(),
                BirthdayListHandle.BIRTHDAYS_LIST_ID));
    }

    @Test
    public void display() {
        String expectedResult = "1/1/1995 Alice Pauline\n2/2/1993 Alice Pauline\n";
        birthdayListEventStub = new BirthdayListEvent("1/1/1995 Alice Pauline\n2/2/1993 Alice Pauline\n");

        // default birthday list text
        guiRobot.pauseForHuman();
        assertEquals("", birthdaysListHandle.getText());

        // new event received, will not go through
        postNow(birthdayListEventStub);
        guiRobot.pauseForHuman();
        assertNotEquals(expectedResult, birthdaysListHandle.getText());
    }

}
```
###### \java\systemtests\BirthdaysCommandSystemTest.java
``` java
/**
 * A system test class for the help window, which contains interaction with other UI components.
 */
public class BirthdaysCommandSystemTest extends AddressBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    // Stub from Typical Persons
    private static final String expectedResult = "1/1/1995 Alice Pauline\n"
            + "2/1/1989 Benson Meier\n"
            + "3/1/1991 Carl Kurz\n"
            + "6/1/1994 Fiona Kunz\n"
            + "4/2/1991 Daniel Meier\n"
            + "5/3/1991 Elle Meyer\n"
            + "7/10/1995 George Best\n";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openBirthdayList() {
        //use command box
        executeCommand(BirthdaysCommand.COMMAND_WORD);
        guiRobot.pauseForHuman();
        assertEquals(expectedResult, getBirthdayList().getText());
    }

    @Test
    public void assertBirthdayListWithOnePersonToday() {
        // Simulation of commands to create only one person whose birthday is today
        deleteAllPersons();
        executeCommand("   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   b/"
                + buildBirthday(true) + " " + TIMETABLE_DESC_AMY + TAG_DESC_FRIEND + " ");

        // use command
        executeCommand(BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER);
        guiRobot.waitForEvent(() -> guiRobot.isWindowShown("Birthdays Today"));

        BirthdayNotificationHandle alertDialog = new BirthdayNotificationHandle(guiRobot
                .getStage("Birthdays Today"));

        assertEquals(buildExpectedBirthday(), alertDialog.getContentText());
    }

    @Test
    public void assertBirthdayListWithZeroPersonToday() {
        // Simulation of commands to create only one person whose birthday is today
        deleteAllPersons();
        executeCommand("   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   b/"
                + buildBirthday(false) + " " + TIMETABLE_DESC_AMY + " " + TAG_DESC_FRIEND + " ");

        // use command
        executeCommand(BirthdaysCommand.COMMAND_WORD + " " + BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER);
        guiRobot.waitForEvent(() -> guiRobot.isWindowShown("Birthdays Today"));

        BirthdayNotificationHandle alertDialog = new BirthdayNotificationHandle(guiRobot
                .getStage("Birthdays Today"));

        assertEquals(BirthdaysCommand.MESSAGE_NO_BIRTHDAY_TODAY, alertDialog.getContentText());
    }

    /**
     * Builds a birthday desc for the add command
     * @param isTodayABirthday if the person is having a person today, her birthday will be set to today
     *                         Otherwise, it will be set +/- 1 day
     * @return String for " b/" portion of AddCommand
     */
    private String buildBirthday(boolean isTodayABirthday) {
        LocalDate currentDate = LocalDate.now();
        int currentDay;
        int currentMonth  = currentDate.getMonthValue();
        StringBuilder string = new StringBuilder();

        if (isTodayABirthday) {
            currentDay = currentDate.getDayOfMonth();
        } else {
            if (currentDate.getDayOfMonth() == 1 || currentDate.getDayOfMonth() < 28) {
                currentDay = currentDate.getDayOfMonth() + 1;
            } else {
                currentDay = currentDate.getDayOfMonth() - 1;
            }
        }

        // Creation of birthday to fit today
        if (currentDay <= 9) {
            string.append(0);
        }
        string.append(currentDay);
        if (currentMonth <= 9) {
            string.append(0);
        }
        string.append(currentMonth);
        string.append("1995");

        return string.toString();
    }

    /**
     * Creates the stub for the testing of Birthdays
     * @return Amy with her current age
     */
    private String buildExpectedBirthday() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        StringBuilder string = new StringBuilder();

        int age = currentYear - 1995;
        string.append(VALID_NAME_AMY);
        string.append(" (");
        string.append(age);
        if (age != 1) {
            string.append(" years old)");
        } else if (age > 0) {
            string.append(" years old)");
        }
        string.append("\n");

        return string.toString();
    }

}
```

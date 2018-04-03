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
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

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
    public void isValidBirthday() {
        // null birthday
        Assert.assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));

        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("123456")); // less than 8 numbers
        assertFalse(Birthday.isValidBirthday("12345678")); // more than 8 numbers
        assertFalse(Birthday.isValidBirthday("32011995")); // invalid day
        assertFalse(Birthday.isValidBirthday("01131995")); // invalid month
        assertFalse(Birthday.isValidBirthday("phonezzz")); // non-numeric
        assertFalse(Birthday.isValidBirthday("9011p041")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("12 04 1995")); // spaces within digits

        // valid birthday
        assertTrue(Birthday.isValidBirthday("01011995")); // exactly 6 numbers
    }

    @Test
    public void getValidDayMonth() {
        Birthday birthdayStub = new Birthday("01121995");

        assertTrue(birthdayStub.getDay() == 1); // check Day
        assertTrue(birthdayStub.getMonth() == 12); // check Month
        assertTrue(birthdayStub.getYear() == 1995); // check Year
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

        // new event received
        //postNow(birthdayListEventStub);
        //birthdayList.loadList(birthdayListEventStub.getBirthdayList()); // Manual loading
        //guiRobot.pauseForHuman();
        //assertEquals(expectedResult, birthdaysListHandle.getText());
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
    private static final String expectedResultStub = "1/1/1995 Alice Pauline\n"
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
        assertEquals(expectedResultStub, getBirthdayList().getText());
    }

    @Test
    public void assertBirthdayListWithOnePersonToday() {
        // Simulation of commands to create only one person whose birthday is today
        deleteAllPersons();
        executeCommand("   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   b/"
                + buildBirthday(true) + " " + TIMETABLE_DESC_AMY + TAG_DESC_FRIEND + " ");

        // Create expected result


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

        assertEquals("", alertDialog.getContentText());
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

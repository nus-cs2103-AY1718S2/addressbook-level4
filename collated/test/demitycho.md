# demitycho
###### \java\seedu\address\logic\commands\AddLessonCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddLessonCommand}.
 */
public class AddLessonCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), getTypicalSchedule());
    }

    @Test
    public void execute_newStudent_success() throws Exception {
        Lesson lesson = FIONA_SAT_15_17;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getSchedule());
        Student student = expectedModel.getAddressBook().getStudentList().get(INDEX_SIXTH.getZeroBased());
        expectedModel.addLesson(student, lesson.getDay(), lesson.getStartTime(), lesson.getEndTime());

        assertCommandSuccess(prepareCommand(INDEX_SIXTH, lesson), model,
                String.format(AddLessonCommand.MESSAGE_SUCCESS, student.getName()), expectedModel);
    }

    @Test
    public void execute_clashingLesson_throwsCommandException() {
        assertCommandFailure(prepareCommand(Index.fromOneBased(1),
                ALICE_WED_15_17), model, AddLessonCommand.MESSAGE_INVALID_TIME_SLOT);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code student} into the {@code model}.
     */
    private AddLessonCommand prepareCommand(Index index, Lesson lesson) {
        AddLessonCommand command = new AddLessonCommand(
                index, lesson.getDay(), lesson.getStartTime(), lesson.getEndTime());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteLessonCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteLessonCommand}.
 */
public class DeleteLessonCommandTest {

    private Model model = new ModelManager(
            getTypicalAddressBook(), new UserPrefs(), getTypicalSchedule());

    @Test
    public void execute_validIndex_success() throws Exception {
        Lesson lessonToDelete = model.getSchedule().getSchedule().get(INDEX_FIRST.getZeroBased());
        DeleteLessonCommand deleteLessonCommand = prepareCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalSchedule());
        expectedModel.deleteLesson(lessonToDelete);

        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getSchedule().getSchedule().size() + 1);
        DeleteLessonCommand deleteLessonCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndex_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Lesson lessonToDelete = model.getSchedule().getSchedule().get(INDEX_FIRST.getZeroBased());
        DeleteLessonCommand deleteLessonCommand = prepareCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalSchedule());

        // delete -> first student deleted
        deleteLessonCommand.execute();
        undoRedoStack.push(deleteLessonCommand);

        // undo -> reverts addressbook back to previous state and filtered student list to show all studentFs
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first student deleted again
        expectedModel.deleteLesson(lessonToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteLessonCommand deleteFirstCommand = prepareCommand(INDEX_FIRST);
        DeleteLessonCommand deleteSecondCommand = prepareCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteLessonCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteLessonCommand} with the parameter {@code index}.
     */
    private DeleteLessonCommand prepareCommand(Index index) {
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(index);
        deleteLessonCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteLessonCommand;
    }

}
```
###### \java\seedu\address\logic\commands\FindTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTagCommand findFirstTagCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findSecondTagCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstTagCommand.equals(findFirstTagCommand));

        // same values -> returns true
        FindTagCommand findFirstCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findFirstTagCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstTagCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstTagCommand.equals(null));

        // different student -> returns false
        assertFalse(findFirstTagCommand.equals(findSecondTagCommand));
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = String.format(MESSAGE_STUDENT_LISTED_OVERVIEW, 0);
        FindTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENT_LISTED_OVERVIEW, 1);
        FindTagCommand command = prepareCommand("owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindTagCommand prepareCommand(String userInput) {
        FindTagCommand command =
                new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Student>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindTagCommand command, String expectedMessage, List<Student> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredStudentList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\model\lesson\DayTest.java
``` java
public class DayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        String invalidDay = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Day(invalidDay));
    }

    @Test
    public void isValidDay() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // invalid days
        assertFalse(Day.isValidDay(""));        // blank
        assertFalse(Day.isValidDay("monday"));  // long form names
        assertFalse(Day.isValidDay("mo"));      // short form names
        assertFalse(Day.isValidDay("f"));       // Single character day name
        assertFalse(Day.isValidDay("MON"));     // Capital letters
        assertFalse(Day.isValidDay("funday"));  // Wrong name

        // valid days
        assertTrue(Day.isValidDay("mon"));      //Valid tests
        assertTrue(Day.isValidDay("tue"));      //Valid tests
        assertTrue(Day.isValidDay("wed"));
        assertTrue(Day.isValidDay("thu"));      //Valid tests
        assertTrue(Day.isValidDay("fri"));
        assertTrue(Day.isValidDay("sat"));      //Valid tests
        assertTrue(Day.isValidDay("sun"));      //Valid tests
    }
}
```
###### \java\seedu\address\model\lesson\TimeTest.java
``` java
public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid addresses
        assertFalse(Time.isValidTime(""));          // blank
        assertFalse(Time.isValidTime("9:00"));      // short form time
        assertFalse(Time.isValidTime("09:60"));     // Invalid minute
        assertFalse(Time.isValidTime("24:00"));     // Boundary value
        assertFalse(Time.isValidTime("09-10"));     // Using dash -
        assertFalse(Time.isValidTime("09.10"));     // Using .

        // valid addresses
        assertTrue(Time.isValidTime("10:00"));
        assertTrue(Time.isValidTime("22:00"));
        assertTrue(Time.isValidTime("00:00"));      //boundary
        assertTrue(Time.isValidTime("23:59"));
    }
}
```
###### \java\seedu\address\model\LessonListTest.java
``` java
public class LessonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        LessonList uniqueStudentList = new LessonList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueStudentList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedStudentTest.java
``` java
    @Test
    public void toModelType_invalidKey_throwsIllegalValueException() {
        XmlAdaptedStudent student =
                new XmlAdaptedStudent(INVALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_SUBJECT, VALID_TAGS, VALID_FAVOURITE, VALID_PROFILEPICTUREPATH,
                        VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = UniqueKey.MESSAGE_UNIQUE_KEY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }
```
###### \java\seedu\address\testutil\LessonBuilder.java
``` java
/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_KEY = "ffff00";
    public static final String DEFAULT_DAY = "mon";
    public static final String DEFAULT_START_TIME = "10:00";
    public static final String DEFAULT_END_TIME = "12:00";


    private UniqueKey key;
    private Day day;
    private Time startTime;
    private Time endTime;

    public LessonBuilder() {
        key = new UniqueKey(DEFAULT_KEY);
        day = new Day(DEFAULT_DAY);
        startTime = new Time(DEFAULT_START_TIME);
        endTime = new Time(DEFAULT_END_TIME);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        key = lessonToCopy.getUniqueKey();
        day = lessonToCopy.getDay();
        startTime = lessonToCopy.getStartTime();
        endTime = lessonToCopy.getEndTime();
    }

    /**
     * Sets the {@code UniqueKey} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withKey(String key) {
        this.key = new UniqueKey(key);
        return this;
    }

    /**
     * Sets the {@code Day} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withStartTime(String startTime) {
        this.startTime = new Time (startTime);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withEndTime(String endTime) {
        this.endTime = new Time(endTime);
        return this;
    }

    /**
     * Builds the lesson with given attributes
     */
    public Lesson build() {
        return new Lesson(key, day, startTime, endTime);
    }
}
```
###### \java\seedu\address\testutil\LessonUtil.java
``` java
/**
 * A utility class for Lesson.
 */
public class LessonUtil {

    /**
     * Returns an add command string for adding the {@code lesson}.
     */
    public static String getAddLessonCommand(Lesson lesson, Index targetIndex) {
        return AddLessonCommand.COMMAND_WORD + " " + getLessonDetails(lesson, targetIndex);
    }

    /**
     * Returns the part of command string for the given {@code lesson}'s details.
     */
    public static String getLessonDetails(Lesson lesson, Index targetIndex) {
        StringBuilder sb = new StringBuilder();
        sb.append(targetIndex + "  ");
        sb.append(PREFIX_DAY + lesson.getDay().value + " ");
        sb.append(PREFIX_START_TIME + lesson.getStartTime().value + " ");
        sb.append(PREFIX_END_TIME + lesson.getEndTime().value + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\ScheduleBuilder.java
``` java
/**
 * A utility class to help with building Schedule objects.
 * Example usage: <br>
 *     {@code Schedule sd = new ScheduleBuilder().withLesson.build();}
 */
public class ScheduleBuilder {

    private Schedule schedule;

    public ScheduleBuilder() {
        schedule = new Schedule();
    }

    public ScheduleBuilder(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Adds a new {@code Student} to the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withLesson(Lesson lesson) {
        try {
            schedule.addLesson(lesson);
        } catch (DuplicateLessonException dpe) {
            throw new IllegalArgumentException("lesson is expected to be unique.");
        } catch (InvalidLessonTimeSlotException iltse) {
            throw new IllegalArgumentException("Lesson is expected to be non-clashing");
        }
        return this;
    }

    public Schedule build() {
        return schedule;
    }
}
```
###### \java\seedu\address\testutil\TypicalLessons.java
``` java
/**
 * A utility class containing a list of {@code Milestone} objects to be used in tests.
 */
public class TypicalLessons {

    //Variable names in terms of <Student>_<Day>_<StartTime>_<EndTime>
    public static final Lesson ALICE_MON_10_12 = new LessonBuilder().withKey("c5daab").withDay("mon")
            .withStartTime("10:00").withEndTime("12:00").build();
    public static final Lesson ALICE_WED_14_16 = new LessonBuilder().withKey("c5daab").withDay("wed")
            .withStartTime("14:00").withEndTime("16:00").build();
    public static final Lesson ALICE_WED_15_17 = new LessonBuilder().withKey("c5daab").withDay("wed")
            .withStartTime("15:00").withEndTime("17:00").build();
    public static final Lesson CARL_THU_11_13 = new LessonBuilder().withKey("8e90ba").withDay("thu")
            .withStartTime("11:00").withEndTime("13:00").build();
    public static final Lesson FIONA_SAT_15_17 = new LessonBuilder().withKey("9d2b20").withDay("sat")
            .withStartTime("15:00").withEndTime("17:00").build();
    public static final Lesson RANDOM_THU_11_13 = new LessonBuilder().withKey("c0ffee").withDay("thu")
            .withStartTime("11:00").withEndTime("13:00").build();

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(ALICE_MON_10_12, ALICE_WED_14_16, CARL_THU_11_13));
    }

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static Schedule getTypicalSchedule() {
        Schedule sch = new Schedule();
        for (Lesson lesson : getTypicalLessons()) {
            try {
                sch.addLesson(lesson);
            } catch (DuplicateLessonException e) {
                throw new AssertionError("not possible");
            } catch (InvalidLessonTimeSlotException iltse) {
                throw new AssertionError("cannot clash");
            }
        }
        return sch;
    }
}
```
###### \java\systemtests\FindTagCommandSystemTest.java
``` java

public class FindTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find a student in address book, command with leading spaces and trailing spaces
         * -> 1 students found
         */
        String command = "   " + FindTagCommand.COMMAND_WORD + " " + TAG_MATCHING_OWESMONEY + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON); // Benson's tag is "owesMoney"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where student list is displaying the tag we are finding
         * -> 1 students found
         */
        command = FindTagCommand.COMMAND_WORD + " " + TAG_MATCHING_OWESMONEY;
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

        /* Case: find student in address book, tag is same as name but of different case -> 1 student found */
        command = FindTagCommand.COMMAND_WORD + " oWesMOney";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find student in address book, keyword is substring of tag -> 0 students found */
        command = FindTagCommand.COMMAND_WORD + " owe";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find student in address book, tag is substring of keyword -> 0 students found */
        command = FindTagCommand.COMMAND_WORD + " owesMoneys";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag not in address book -> 0 students found */
        command = FindTagCommand.COMMAND_WORD + " poor";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of student in address book -> 0 students found */
        command = FindTagCommand.COMMAND_WORD + " " + BENSON.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of student in address book -> 0 students found */
        command = FindTagCommand.COMMAND_WORD + " " + BENSON.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of student in address book -> 0 students found */
        command = FindTagCommand.COMMAND_WORD + " " + BENSON.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd owesMoney";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_STUDENT_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_STUDENT_LISTED_OVERVIEW, expectedModel.getFilteredStudentList().size());

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

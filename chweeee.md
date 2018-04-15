# chweeee
###### \src\main\java\seedu\address\logic\commands\ConversationCommand.java
``` java
/**
 * purpose of this class is to be able to send userInput as text to the agent to be processed
 **/
public class ConversationCommand extends Command {

    public static final String WELCOME_MESSAGE = "Hello";
    private static Conversation service = null;

    public static void setUpAgent() {
        service = new Conversation("2018-02-16");
        service.setUsernameAndPassword("531ab0c0-8012-4e3b-8a37-eb1ca8fa1010", "ZkCeN4YqIH3W");
    }

    public static MessageResponse getMessageResponse(String userInput) {
        MessageResponse response = null;

        InputData input = new InputData.Builder(userInput).build();
        MessageOptions option = new MessageOptions.Builder("19f7b6f4-7944-419d-83a0-6bf9837ec333").input(input).build();
        response = service.message(option).execute();

        return response;
    }

    public static String getResponseText(MessageResponse response) {
        String text = response.getOutput().getText().toString();

        return text;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult("Bye!");
    }
}
//@@
```
###### \src\main\java\seedu\address\logic\commands\FindAndDeleteCommand.java
``` java
/**
 * Finds and lists all students in address book whose name contains any of the argument keywords,
 * and deletes the first student of the list.
 * Keyword matching is case sensitive.
 */
public class FindAndDeleteCommand extends UndoableCommand {

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student to be deleted cannot be found.";

    private final NameContainsKeywordsPredicate predicate;

    private Student studentToDelete;

    public FindAndDeleteCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(studentToDelete);
        try {
            model.deleteStudent(studentToDelete);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        } catch (LessonNotFoundException lnfe) {
            throw new AssertionError("The target student's lessons cannot be missing");
        } catch (InvalidLessonTimeSlotException iltse) {
            throw new AssertionError(
                    "Removing the target student's lessons cannot result in clashing lessons");
        } catch (DuplicateLessonException dle) {
            throw new AssertionError(
                    "Removing the target student cannot result in duplicate lessons");

        }
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS,  studentToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        //look for the student
        model.updateFilteredStudentList(predicate);
        if (model.getFilteredStudentList().size() == 0) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }
        studentToDelete = lastShownList.get(0);
    }
}
```
###### \src\main\java\seedu\address\logic\commands\FindAndSelectCommand.java
``` java
/**
 * Finds and lists all students in address book whose name contains any of the argument keywords,
 * and Selects the first student of the list.
 * Keyword matching is case sensitive.
 */
public class FindAndSelectCommand extends UndoableCommand {

    public static final String MESSAGE_SELECT_STUDENT_SUCCESS = "Selected Student: %1$s";

    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student to be selected cannot be found.";

    private final NameContainsKeywordsPredicate predicate;

    private Index targetIndex;

    public FindAndSelectCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeUndoableCommand() {

        try {
            targetIndex = ParserUtil.parseIndex("1");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_STUDENT_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {

        //look for the student
        model.updateFilteredStudentList(predicate);
        if (model.getFilteredStudentList().size() == 0) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }
    }
}
```
###### \src\main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
            /**
             * aims to decipher user intention and returns the command required
             */
            //initialises the agent
            ConversationCommand.setUpAgent();
            MessageResponse response = null;
            List<RuntimeIntent> intents; //stores user intents
            List<RuntimeEntity> entities; //stores entities identified in the user's input
            String intention = "";
            String entity = "";

            //processes the userInput
            response = ConversationCommand.getMessageResponse(userInput);
            intents = response.getIntents();
            entities = response.getEntities();
            System.out.println("list of entities: " + entities);

            for (int i = 0; i < intents.size(); i++) {
                intention = intents.get(i).getIntent();
            }
            System.out.println("this is the intention of the user: " + intention);

            if (entities.size() != 0) {
                for (int i = 0; i < intents.size(); i++) {
                    entity = entities.get(i).getValue();
                }
            }
            System.out.println("this is the value of the entity: " + entity);

            switch (intention) {
            case "Clear":
                return new ClearCommand();

            case "Undo":
                return new UndoCommand();

            case "Redo":
                return new RedoCommand();

            case "Help":
                return new HelpCommand();

            case "Exit":
                return new ExitCommand();

            case "History":
                return new HistoryCommand();

            case "List":
                return new ListCommandParser().parse("");

            case "Schedule":
                return new ScheduleCommand();

            case "Delete":
                return new FindAndDeleteCommandParser().parse(entity);

            case "Select":
                return new FindAndSelectCommandParser().parse(entity);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
```
###### \src\main\java\seedu\address\logic\parser\FindAndDeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindAndDeleteCommand object
 */
public class FindAndDeleteCommandParser implements Parser<FindAndDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindAndDeleteCommand
     * and returns an FindAndDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAndDeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindAndDeleteCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \src\main\java\seedu\address\logic\parser\FindAndSelectCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindAndSelectCommand object
 */
public class FindAndSelectCommandParser implements Parser<FindAndSelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindAndSelectCommand
     * and returns a FindAndSelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAndSelectCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindAndSelectCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \src\test\java\seedu\address\logic\commands\FindAndDeleteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindAndSelectCommand}.
 */

public class FindAndDeleteCommandTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = "Student to be deleted cannot be found.";
        FindAndDeleteCommand command = prepareFadCommand(" ");
        try {
            assertCommandSuccess(command, expectedMessage, Collections.emptyList());
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    public void execute_studentDeleted() {
        String expectedMessage = "Deleted Student: " + ELLE.getName() + " Phone: " + ELLE.getPhone()
                + " Email: " + ELLE.getEmail() + " Address: "
                + ELLE.getAddress() + " Programming Language: " + ELLE.getProgrammingLanguage() + " Tags: [friends]"
                + " Favourite: " + ELLE.getFavourite() + " Profile Picture Path: "
                + ELLE.getProfilePicturePath() + " Miscellaneous Info: "
                + ELLE.getMiscellaneousInfo() + " Dashboard: " + ELLE.getDashboard();
        FindAndDeleteCommand command = prepareFadCommand("ELLE");
        try {
            assertCommandSuccess(command, expectedMessage, Collections.emptyList());
        } catch (CommandException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    /**
     * Parses {@code userInput} into a {@code FindAndSelectCommand}.
     */
    private FindAndDeleteCommand prepareFadCommand(String userInput) {
        FindAndDeleteCommand command =
                new FindAndDeleteCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Student>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindAndDeleteCommand command, String expectedMessage,
                                      List<Student> expectedList) throws CommandException {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredStudentList());
    }
}
```
###### \src\test\java\seedu\address\logic\commands\FindAndSelectCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindAndSelectCommand}.
 */

public class FindAndSelectCommandTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = "Student to be selected cannot be found.";
        FindAndSelectCommand command = prepareFasCommand(" ");
        try {
            assertCommandSuccess(command, expectedMessage, Collections.emptyList());
        } catch (CommandException e) {
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    @Test
    public void execute_studentFound() {
        String expectedMessage = "Selected Student: 1";
        FindAndSelectCommand command = prepareFasCommand("ELLE");
        try {
            assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE));
        } catch (CommandException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().equals(expectedMessage));
        }
    }

    /**
     * Parses {@code userInput} into a {@code FindAndSelectCommand}.
     */
    private FindAndSelectCommand prepareFasCommand(String userInput) {
        FindAndSelectCommand command =
                new FindAndSelectCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Student>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindAndSelectCommand command, String expectedMessage,
                                      List<Student> expectedList) throws CommandException {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredStudentList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```

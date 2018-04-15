# chweeee
###### \java\seedu\address\logic\commands\ConversationCommand.java
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
###### \java\seedu\address\logic\commands\FindAndDeleteCommand.java
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
###### \java\seedu\address\logic\commands\FindAndSelectCommand.java
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
###### \java\seedu\address\logic\parser\AddressBookParser.java
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
###### \java\seedu\address\logic\parser\FindAndDeleteCommandParser.java
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
###### \java\seedu\address\logic\parser\FindAndSelectCommandParser.java
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

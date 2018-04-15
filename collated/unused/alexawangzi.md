# alexawangzi
###### \RemoveTagCommand.java
``` java
/**
 * Deletes a tag from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeTag";
    public static final String COMMAND_WORD_ALIAS = "rmt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a tag from the addressbook.\n"
            + "Parameters: tag name(must be a valid tag existing in addressbook\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

    private Tag targetTag;

    public RemoveTagCommand(Tag targetTag) {
        this.targetTag = targetTag;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(targetTag);
        try {
            model.deleteTag(targetTag);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, targetTag));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        Set<Tag> tagsInPersons = lastShownList.stream()
                .map(Person::getTags)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
        if (!tagsInPersons.contains(targetTag)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.targetTag.equals(((RemoveTagCommand) other).targetTag)); // state check
    }
}

```
###### \RemoveTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        try {
            Tag tag = ParserUtil.parseTag(args);
            return new RemoveTagCommand(tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeValidTag_success() throws Exception {
        Tag tagToDelete = new Tag("English");
        RemoveTagCommand removeTagCommand = prepareCommand(tagToDelete);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(tagToDelete);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeInvalidTag_failure() throws Exception {
        Tag tagToDelete = new Tag("nonExistingTag");
        RemoveTagCommand removeTagCommand = prepareCommand(tagToDelete);
        String expectedMessage = String.format(MESSAGE_INVALID_TAG);
        assertCommandFailure(removeTagCommand, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validTag_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Tag tagToDelete = new Tag("English");
        RemoveTagCommand removeTagCommand = prepareCommand(tagToDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first tag deleted
        removeTagCommand.execute();
        undoRedoStack.push(removeTagCommand);

        // undo -> reverts addressbook back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same tag deleted again
        expectedModel.deleteTag(tagToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidTag_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Tag tagToDelete = new Tag("nonExistingTag");
        RemoveTagCommand removeTagCommand = prepareCommand(tagToDelete);
        String expectedMessage = String.format(MESSAGE_INVALID_TAG);

        // execution failed -> removeTagCommand not pushed into undoRedoStack
        assertCommandFailure(removeTagCommand, model, expectedMessage);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
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

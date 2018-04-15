# natania
###### /java/seedu/organizer/logic/commands/RemoveTagsCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for RemoveTagsCommand.
 */
public class RemoveTagsCommandTest {

    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    private Set<Tag> tagList;

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
            ObservableList<Tag> tags = model.getOrganizer().getTagList();
            tagList = new HashSet<>(tags);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        } catch (UserPasswordWrongException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_unfilteredList_success() throws Exception {
        RemoveTagsCommand removeTagsCommand = prepareCommand(tagList);

        String expectedMessage = String.format(RemoveTagsCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagList);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        for (Tag tag : tagList) {
            expectedModel.deleteTag(tag);
        }

        assertCommandSuccess(removeTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        RemoveTagsCommand removeTagsCommand = prepareCommand(tagList);

        String expectedMessage = String.format(RemoveTagsCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagList);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        for (Tag tag : tagList) {
            expectedModel.deleteTag(tag);
        }

        assertCommandSuccess(removeTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_unfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemoveTagsCommand removeTagsCommand = prepareCommand(tagList);
        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);

        // edit -> delete tags
        removeTagsCommand.execute();
        undoRedoStack.push(removeTagsCommand);

        // undo -> reverts organizer back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> delete the same tags
        for (Tag tag : tagList) {
            expectedModel.deleteTag(tag);
        }
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final RemoveTagsCommand standardCommand = prepareCommand(tagList);

        // same values -> returns true
        RemoveTagsCommand commandWithSameValues = prepareCommand(tagList);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    /**
     * Returns an {@code RemoveTagsCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemoveTagsCommand prepareCommand(Set<Tag> tagList) {
        RemoveTagsCommand removeTagsCommand = new RemoveTagsCommand(tagList);
        removeTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagsCommand;
    }
}
```
###### /java/seedu/organizer/logic/parser/OrganizerParserLoggedInTest.java
``` java
    @Test
    public void parseCommand_removeTags() throws Exception {
        Set<Tag> tag = new HashSet<>();
        tag.add(model.getOrganizer().getTagList().get(0));
        RemoveTagsCommand command = (RemoveTagsCommand) parser.parseCommand(
                RemoveTagsCommand.COMMAND_WORD + " "
                        + PREFIX_TAG + model.getOrganizer().getTagList().get(0).tagName);
        RemoveTagsCommand commandAlias = (RemoveTagsCommand) parser.parseCommand(
                RemoveTagsCommand.COMMAND_ALIAS + " "
                        + PREFIX_TAG + model.getOrganizer().getTagList().get(0).tagName);
        assertEquals(new RemoveTagsCommand(tag), command);
        assertEquals(new RemoveTagsCommand(tag), commandAlias);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
```
###### /java/seedu/organizer/logic/parser/RemoveTagsCommandParserTest.java
``` java
public class RemoveTagsCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private RemoveTagsCommandParser parser = new RemoveTagsCommandParser();

    @Test
    public void parse_noTagGiven_success() {
        // no field specified
        Set<Tag> emptyTagList = new HashSet<>();
        RemoveTagsCommand expectedCommand = new RemoveTagsCommand(emptyTagList);
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, TAG_DESC_FRIENDS + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser,
                TAG_EMPTY + TAG_DESC_FRIENDS + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_validValue_failure() {
        Set<Tag> tagList = new HashSet<>();
        tagList.add(new Tag(VALID_TAG_FRIENDS));
        tagList.add(new Tag(VALID_TAG_HUSBAND));
        RemoveTagsCommand expectedCommand = new RemoveTagsCommand(tagList);
        assertParseSuccess(parser, TAG_DESC_FRIENDS + TAG_DESC_HUSBAND, expectedCommand);
    }
}
```

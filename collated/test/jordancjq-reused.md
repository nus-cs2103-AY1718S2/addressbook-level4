# jordancjq-reused
###### /java/seedu/address/logic/parser/RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {

    private static final String REMARK_EMPTY = "";
    private static final String REMARK_NONEMPTY = "Some remark to test.";

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_success() throws Exception {
        // with remark
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(REMARK_NONEMPTY));
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + REMARK_NONEMPTY;
        System.out.println(userInput);
        assertParseSuccess(parser, userInput, expectedCommand);

        // without remark
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(REMARK_EMPTY));
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + REMARK_EMPTY;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // missing parameters
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);

        // missing index
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + REMARK_NONEMPTY, expectedMessage);
    }

}
```
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person remarkedPerson = personInList.withRemark(VALID_REMARK_AMY).build();

        RemarkCommand remarkCommand = prepareCommand(indexLastPerson, remarkedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, remarkedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, remarkedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person remarkedPerson = personInList.withRemark(VALID_REMARK_EMPTY).build();

        RemarkCommand remarkCommand = prepareCommand(indexLastPerson, remarkedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, remarkedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, remarkedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person remarkedPerson = new PersonBuilder(personInFilteredList).withRemark(VALID_REMARK_BOB).build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remarkedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, remarkedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personInFilteredList, remarkedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Remark filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person remarkedPerson = new PersonBuilder(personToRemark).withRemark(VALID_REMARK_AMY).build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_REMARK_AMY);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // remark -> first person remarked
        remarkCommand.execute();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person remarked again
        expectedModel.updatePerson(personToRemark, remarkedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_EMPTY);

        // execution failed -> remarkCommand not pushed into undoRedoStack
        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Updates {@code Person#remark} from a filtered list.
     * 2. Undo the update.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously updated person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the update. This ensures {@code RedoCommand} updates the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonRemarked() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_REMARK_BOB);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person remarkedPerson = new PersonBuilder(personToRemark).withRemark(VALID_REMARK_BOB).build();
        // remark -> remarks second person in unfiltered person list / first person in filtered person list
        remarkCommand.execute();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToRemark, remarkedPerson);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToRemark);
        // redo -> remarks same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_REMARK_AMY);

        // same values -> returns true
        RemarkCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, VALID_REMARK_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}.
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### /java/seedu/address/model/person/RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Test Remark");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark sameRemark = new Remark(remark.value);
        assertTrue(remark.equals(sameRemark));

        // different types -> return false
        assertFalse(remark.equals(new Phone("999")));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different remark -> returns false
        Remark differentRemark = new Remark("Another Remark");
        assertFalse(remark.equals(differentRemark));

    }
}
```

# sherlynng
###### \java\seedu\address\logic\commands\RateCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for RateCommand.
 */
public class RateCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        new RateCommand(null, rate);
    }

    @Test
    public void constructor_nullRate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RateCommand(INDEX_FIRST_PERSON, null);
    }

    @Test
    public void execute_filteredListAbsoluteRate_success() throws Exception {
        showPersonAtIndex(model, INDEX_NINTH_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        rateCommand.preprocessUndoableCommand();
        String expectedMessage = String.format(MESSAGE_RATE_PERSON_SUCCESS,
                                 editedPerson.getName(), VALID_RATE_BOB);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListAccumulatedRate_success() throws Exception {
        showPersonAtIndex(model, INDEX_NINTH_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), false);
        Rate accumulatedRate = personInFilteredList.getRate().accumulatedValue(personInFilteredList.getRate(), rate);
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRate(Double.toString(accumulatedRate.getDisplayedValue()),
                        Integer.toString(accumulatedRate.getCount())).build();

        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        rateCommand.preprocessUndoableCommand();
        String expectedMessage = String.format(MESSAGE_RATE_PERSON_SUCCESS,
                editedPerson.getName(), VALID_RATE_AMY);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, rate);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds rate in filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, rate);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withPhone("85355255")
                .withPrice("50").withSubject("math").withStatus("Not Matched").withLevel("lower Sec")
                .withRole("Tutor").withRemark("Hardworking but slow learner.")
                .withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // add rate -> adds rate to first person
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts STUtor back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person with rate added again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, rate);

        // execution failed -> rateCommand not pushed into undoRedoStack
        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Adds a rate to a {@code Person} from a filtered list.
     * 2. Undo the adding of rate.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the adding of rate. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
                .withPrice("50").withSubject("math").withStatus("Matched").withLevel("lower Sec")
                .withRole("Student").withRemark("Not self motivated.")
                .withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // add rate -> adds rate to second person in unfiltered person list / first person in filtered person list
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts STUtor back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> adds rate to same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), true);
        rate.setCount(Integer.parseInt(RATECOUNT_BOB));
        final RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, rate);

        // same values -> returns true
        Person bob = new PersonBuilder().withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        RateCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, bob.getRate());
        assertTrue(rateCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(rateCommand.equals(rateCommand));

        // null -> returns false
        assertFalse(rateCommand.equals(null));

        // different types -> returns false
        assertFalse(rateCommand.equals(new ClearCommand()));

        // different index -> returns false
        bob = new PersonBuilder().withRate(VALID_RATE_BOB, RATECOUNT_BOB).build();
        RateCommand commandWithDifferentIndex = prepareCommand(INDEX_SECOND_PERSON, bob.getRate());
        assertFalse(rateCommand.equals(commandWithDifferentIndex));

        // different rate -> returns false
        Person amy = new PersonBuilder().withRate(VALID_RATE_AMY, RATECOUNT_AMY).build();
        RateCommand commandWithDifferentPerson = prepareCommand(INDEX_FIRST_PERSON, amy.getRate());
        assertFalse(rateCommand.equals(commandWithDifferentPerson));
    }

    /**
     * Returns an {@code RateCommand} with parameters {@code index} and {@code rate}
     */
    private RateCommand prepareCommand(Index index, Rate rate) {
        RateCommand rateCommand = new RateCommand(index, rate);
        rateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return rateCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Remark remark = new Remark(REMARK_BOB);
        new RemarkCommand(null, remark);
    }

    @Test
    public void constructor_nullRemark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemarkCommand(INDEX_FIRST_PERSON, null);
    }

    @Test
    public void execute_editRemark_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Remark remark = new Remark("");
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remark, true);
        remarkCommand.preprocessUndoableCommand();
        String expectedMessage = String.format(MESSAGE_EDIT_REMARK_SUCCESS, personInFilteredList.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withRemark(REMARK_BOB).build();
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remark);
        remarkCommand.preprocessUndoableCommand();
        String expectedMessage = String.format(MESSAGE_REMARK_PERSON_SUCCESS,
                                 editedPerson.getName(), editedPerson.getRemark());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, remark);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds remark in filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, remark);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com").withPhone("85355255")
                .withPrice("50").withSubject("math").withStatus("Not Matched").withLevel("lower Sec")
                .withRole("Tutor").withRemark(REMARK_BOB).build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remark);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // add remark -> adds remark to first person
        remarkCommand.execute();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts STUtor back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person with remark added again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, remark);

        // execution failed -> remarkCommand not pushed into undoRedoStack
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Adds a remark to a {@code Person} from a filtered list.
     * 2. Undo the adding of remark.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the adding of remark. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person editedPerson = new PersonBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
                .withPrice("50").withSubject("math").withStatus("Matched").withLevel("lower Sec")
                .withRole("Student").withRemark(REMARK_BOB).withRate("2.1", "2").build();
        Remark remark = new Remark(REMARK_BOB);
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remark);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // add remark -> adds remark to second person in unfiltered person list / first person in filtered person list
        remarkCommand.execute();
        undoRedoStack.push(remarkCommand);

        // undo -> reverts STUtor back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> adds remark to same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        Remark remark = new Remark(REMARK_BOB);
        final RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, remark);

        // same values -> returns true
        Person bob = new PersonBuilder().withRemark(REMARK_BOB).build();
        RemarkCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, bob.getRemark());
        assertTrue(remarkCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(remarkCommand.equals(remarkCommand));

        // null -> returns false
        assertFalse(remarkCommand.equals(null));

        // different types -> returns false
        assertFalse(remarkCommand.equals(new ClearCommand()));

        // different index -> returns false
        bob = new PersonBuilder().withRemark(REMARK_BOB).build();
        RemarkCommand commandWithDifferentIndex = prepareCommand(INDEX_SECOND_PERSON, bob.getRemark());
        assertFalse(remarkCommand.equals(commandWithDifferentIndex));

        // different remark -> returns false
        Person amy = new PersonBuilder().withRemark(REMARK_AMY).build();
        RemarkCommand commandWithDifferentPerson = prepareCommand(INDEX_FIRST_PERSON, amy.getRemark());
        assertFalse(remarkCommand.equals(commandWithDifferentPerson));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark, boolean isEditRemark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark, isEditRemark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remark() throws Exception {
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + REMARK_AMY);
        Remark remark = new Remark(REMARK_AMY);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommand_remarkAliased() throws Exception {
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + REMARK_AMY);
        Remark remark = new Remark(REMARK_AMY);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommand_rate() throws Exception {
        RateCommand command = (RateCommand) parser.parseCommand(RateCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + VALID_RATE_AMY);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), false);
        assertEquals(new RateCommand(INDEX_FIRST_PERSON, rate), command);
    }

    @Test
    public void parseCommand_rateAliased() throws Exception {
        RateCommand command = (RateCommand) parser.parseCommand(RateCommand.COMMAND_WORD_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + VALID_RATE_AMY);
        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), false);
        assertEquals(new RateCommand(INDEX_FIRST_PERSON, rate), command);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseRemark_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark((Optional<String>) null));
    }

    @Test
    public void parseRemark_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRemark(Optional.empty()).isPresent());
    }

    @Test
    public void parseRemark_validValueWithoutWhitespace_returnsRemark() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(VALID_REMARK));
        assertEquals(Optional.of(expectedRemark), ParserUtil.parseRemark(Optional.of(VALID_REMARK)));
    }

    @Test
    public void parseRemark_validValueWithWhitespace_returnsTrimmedRemark() throws Exception {
        String remarkWithWhitespace = WHITESPACE + VALID_REMARK + WHITESPACE;
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(remarkWithWhitespace));
        assertEquals(Optional.of(expectedRemark), ParserUtil.parseRemark(Optional.of(remarkWithWhitespace)));
    }

    @Test
    public void parseRate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseRate((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseRate((Optional<String>) null));
    }

    @Test
    public void parseRate_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseRate(""));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseRate(INVALID_RATE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseRate(Optional.of(INVALID_RATE)));
    }

    @Test
    public void parseRate_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRate(Optional.empty()).isPresent());
    }

    @Test
    public void parseRate_validValueWithoutWhitespace_returnsRate() throws Exception {
        // cumulative rate
        Rate expectedRate = new Rate(Double.parseDouble(VALID_RATE), false);
        assertEquals(expectedRate, ParserUtil.parseRate(VALID_RATE));
        assertEquals(Optional.of(expectedRate), ParserUtil.parseRate(Optional.of(VALID_RATE)));

        // absolute rate
        expectedRate = new Rate(Double.parseDouble(VALID_RATE), true);
        assertEquals(expectedRate, ParserUtil.parseRate(VALID_RATE + "-"));
        assertEquals(Optional.of(expectedRate), ParserUtil.parseRate(Optional.of(VALID_RATE + "-")));
    }

    @Test
    public void parseRate_validValueWithWhitespace_returnsTrimmedRate() throws Exception {
        // cumulative rate
        String rateWithWhitespace = WHITESPACE + VALID_RATE + WHITESPACE;
        Rate expectedRate = new Rate(Double.parseDouble(VALID_RATE), false);
        assertEquals(expectedRate, ParserUtil.parseRate(rateWithWhitespace));
        assertEquals(Optional.of(expectedRate), ParserUtil.parseRate(Optional.of(rateWithWhitespace)));

        // absolute rate
        rateWithWhitespace = WHITESPACE + VALID_RATE + "-" + WHITESPACE;
        expectedRate = new Rate(Double.parseDouble(VALID_RATE), true);
        assertEquals(expectedRate, ParserUtil.parseRate(rateWithWhitespace));
        assertEquals(Optional.of(expectedRate), ParserUtil.parseRate(Optional.of(rateWithWhitespace)));
    }

```
###### \java\seedu\address\logic\parser\RateCommandParserTest.java
``` java
public class RateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = MESSAGE_INVALID_COMMAND_FORMAT + RateCommand.MESSAGE_USAGE;

    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        String userInput = PREFIX_RATE + INVALID_RATE_EXCEEDRANGE;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        String userInput = "-5" + " " + PREFIX_RATE + VALID_RATE_AMY;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // zero index
        userInput = "0" + " " + PREFIX_RATE + VALID_RATE_AMY;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidRate_failure() {
        // exceed rate range
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + INVALID_RATE_EXCEEDRANGE;
        assertParseFailure(parser, userInput, MESSAGE_RATE_CONSTRAINTS);

        // negative rate
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + INVALID_RATE_NEGATIVE;
        assertParseFailure(parser, userInput, MESSAGE_RATE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecifiedAbsoluteRate_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE + VALID_RATE_AMY + "-";

        Rate rate = new Rate(Double.parseDouble(VALID_RATE_AMY), true);
        RateCommand expectedCommand = new RateCommand(targetIndex, rate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecifiedAccumulatedRate_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE + VALID_RATE_BOB;

        Rate rate = new Rate(Double.parseDouble(VALID_RATE_BOB), false);
        RateCommand expectedCommand = new RateCommand(targetIndex, rate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE;

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, REMARK_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + PREFIX_REMARK + REMARK_AMY, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_REMARK + REMARK_AMY, MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

        // no index stated for editing remark
        assertParseFailure(parser, "edit", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validPreamble_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " edit";

        Remark remark = new Remark("");
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, remark, true);

        // edit remark
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + REMARK_AMY;

        Remark remark = new Remark(REMARK_AMY);
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, remark);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexFieldSpecifiedNoRemark_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;

        Remark remark = new Remark("");
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, remark);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\model\person\RateTest.java
``` java
public class RateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rate((Double) null, true));
    }

    @Test
    public void checkRateAccumulatedValue_success() {
        Rate oldRate = new Rate(8, true);
        oldRate.setCount(2);
        Rate newRate = new Rate(3, true);
        Rate expectedRate = new Rate(11, true);
        expectedRate.setCount(3);

        Rate actualRate = Rate.accumulatedValue(oldRate, newRate);
        assertTrue(expectedRate.equals(actualRate));
    }

    @Test
    public void getDisplayedValue_success() {
        Rate rate = new Rate(13, true);
        rate.setCount(4);
        double expectedValue = 3.3;
        double actualValue = rate.getDisplayedValue();

        assertEquals(expectedValue, actualValue, 0.001);
    }

    @Test
    public void checkRateToString() {
        assertTrue(new Rate(3, true).toString().equals("3.0")); // Integer rating
        assertTrue(new Rate(2.1, true).toString().equals("2.1")); // Rating with decimal value
    }

    @Test
    public void isValidRate() {

        // invalid rate
        assertFalse(Rate.isValidRate("-1.0")); // negative numbers
        assertFalse(Rate.isValidRate("6.0")); // exceed 5

        // valid rate
        assertTrue(Rate.isValidRate("3.3"));
        assertTrue(Rate.isValidRate("1")); // single digit
    }

    @Test
    public void checkRateEquality() {
        //test rate against non-rate type
        assertFalse(new Rate(1, true).equals(null));
        assertFalse(new Rate(1, true).equals(new Tag("100")));

        //test cumulative against absolute rate
        assertFalse(new Rate(1, true).equals(new Rate(1, false)));
        //test different rate values
        assertFalse(new Rate(2, true).equals(new Rate(2.2, true)));
        //test correctly returns equal if rate string is the same
        assertTrue(new Rate(1, true).equals(new Rate(1, true)));
    }

    @Test
    public void checkRateHashCode() {
        Rate rate = new Rate(3, true);
        assertTrue(rate.hashCode() == rate.hashCode());
        rate = new Rate(2.1, true);
        assertTrue(rate.hashCode() == rate.hashCode());
        rate = new Rate(4.5, false);
        assertTrue(rate.hashCode() == rate.hashCode());
    }
}
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void checkRemarkToString() {
        assertTrue(new Remark("Friendly and patient.").toString() == "Friendly and patient.");
    }

    @Test
    public void checkRemarkEquality() {
        //test remark against non-remark type
        assertFalse(new Remark("Friendly and patient.").equals(null));
        assertFalse(new Remark("Friendly and patient.").equals(new Address("Friendly and patient.")));

        //test correctly returns equal if remark string is the same
        assertTrue(new Remark("Friendly and patient.").equals(new Remark("Friendly and patient.")));
    }

    @Test
    public void checkRemarkHashCode() {
        Remark remark = new Remark("");
        assertTrue(remark.hashCode() == remark.value.hashCode());
        remark = new Remark("Friendly and patient.");
        assertTrue(remark.hashCode() == remark.value.hashCode());
        remark = new Remark("Late and impatient tutor.");
        assertTrue(remark.hashCode() == remark.value.hashCode());
    }
}
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    @Test
    public void handleKeyPress_addCommandPressTab_autofill() {
        String expectedOutput = "add n/ p/ e/ a/ $/ sub/ lvl/ r/";

        // checks for add command word
        commandBoxHandle.setInput("add");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for add command word alias
        commandBoxHandle.setInput("a");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks if tab works correctly
        expectedOutput = "add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 $/50"
                         + " sub/Math lvl/Lower Sec r/Student";
        actualOutput = enterPersonDetails();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_addCommandPressDelete_removePreviousPrefix() {
        String expectedOutput = "add p/ e/ a/ $/ sub/ lvl/ r/";

        // checks for add command word
        commandBoxHandle.setInput("add");
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.DELETE);

        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_selectCommandPressTab_autofill() {
        String expectedOutput = "select 1";

        // checks for select command word
        commandBoxHandle.setInput("select");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for select command word alias
        commandBoxHandle.setInput("s");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_deleteCommandPressTab_autofill() {
        String expectedOutput = "delete 1";

        // checks for delete command word
        commandBoxHandle.setInput("delete");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for delete command word alias
        commandBoxHandle.setInput("d");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_unmatchCommandPressTab_autofill() {
        String expectedOutput = "unmatch 1";

        // checks for unmatch command word
        commandBoxHandle.setInput("unmatch");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for unmatch command word alias
        commandBoxHandle.setInput("um");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_unmatchCommandChangeIndex_autofill() {
        String expectedOutput = "unmatch 2";

        // checks for unmatch command word
        commandBoxHandle.setInput("unmatch");
        guiRobot.push(KeyCode.TAB);

        // change index in unmatch command
        guiRobot.push(KeyCode.DIGIT2);

        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_matchCommandPressTab_autofill() {
        String expectedOutput = "match 1 2";

        // checks for match command word
        commandBoxHandle.setInput("match");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for match command word alias
        commandBoxHandle.setInput("m");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_matchCommandChangeIndexes_autofill() {
        String expectedOutput = "match 4 5";

        // checks for match command word
        commandBoxHandle.setInput("match");
        guiRobot.push(KeyCode.TAB);

        // change indexes in match command
        guiRobot.push(KeyCode.DIGIT4);
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.DIGIT5);

        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_editCommandPressTab_autofill() {
        String expectedOutput = "edit 1 n/ p/ e/ a/ $/ sub/ lvl/ r/";

        // checks for edit command word
        commandBoxHandle.setInput("edit");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for edit command word alias
        commandBoxHandle.setInput("e");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_editCommandPressDelete_removePreviousPrefix() {
        String expectedOutput = "edit 1 p/ e/ a/ $/ sub/ lvl/ r/";

        // checks for edit command word
        commandBoxHandle.setInput("edit");
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.TAB);

        guiRobot.push(KeyCode.DELETE);

        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // delete 6 more times for testing repetitive pressing of delete button
        int i = 0;
        while (i < 6) {
            guiRobot.push(KeyCode.DELETE);
            i++;
        }
        commandBoxHandle.insertInput("Tutor");

        expectedOutput = "edit 1 r/Tutor";
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_remarkCommandPressTab_autofill() {
        String expectedOutput = "remark 1 r/";

        // checks for remark command word
        commandBoxHandle.setInput("remark");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for remark command word alias
        commandBoxHandle.setInput("rk");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleKeyPress_rateCommandPressTab_autofill() {
        String expectedOutput = "rate 1 r/";

        // checks for rate command word
        commandBoxHandle.setInput("rate");
        guiRobot.push(KeyCode.TAB);
        String actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);

        // checks for rate command word alias
        commandBoxHandle.setInput("rt");
        guiRobot.push(KeyCode.TAB);
        actualOutput = commandBoxHandle.getInput();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Enters Person details using GUI robot
     * @return String entered by GUI robot
     */
    private String enterPersonDetails() {
        commandBoxHandle.insertInput("John Doe");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("98765432");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("johnd@example.com");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("311, Clementi Ave 2, #02-25");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("50");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("Math");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("Lower Sec");
        guiRobot.push(KeyCode.TAB);
        commandBoxHandle.insertInput("Student");

        return commandBoxHandle.getInput();
    }

    @Test
    public void editRemarkEventTest_success() {
        Person person = ALICE;

        EditRemarkEvent editRemarkEventStub = new EditRemarkEvent(COMMAND_WORD + " "
                + INDEX_FIRST_PERSON + " " + PREFIX_REMARK + person.getRemark());
        postNow(editRemarkEventStub);

        String expectedOutput = COMMAND_WORD + " " + INDEX_FIRST_PERSON + " " + PREFIX_REMARK + person.getRemark();
        String actualOutput = commandBoxHandle.getInput();

        guiRobot.pauseForHuman();
        assertEquals(expectedOutput, actualOutput);
    }
}
```
###### \java\seedu\address\ui\DetailsPanelTest.java
``` java
public class DetailsPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStubStudent;
    private PersonPanelSelectionChangedEvent selectionChangedEventStubTutor;

    private DetailsPanel detailsPanel;
    private DetailsPanelHandle detailsPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStubStudent = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        selectionChangedEventStubTutor = new PersonPanelSelectionChangedEvent(new PersonCard(BENSON, 0));

        guiRobot.interact(() -> detailsPanel = new DetailsPanel());
        uiPartRule.setUiPart(detailsPanel);

        detailsPanelHandle = new DetailsPanelHandle(detailsPanel.getRoot());
    }

    @Test
    public void display() {
        // student
        Person student = ALICE;
        postNow(selectionChangedEventStubStudent);
        assertDetailsDisplay(student);

        // tutor
        Person tutor = BENSON;
        postNow(selectionChangedEventStubTutor);
        assertDetailsDisplay(tutor);
    }

    /**
     * Asserts that {@code detailsPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertDetailsDisplay(Person expectedPerson) {
        guiRobot.pauseForHuman();

        // verify person details are displayed correctly
        assertPanelDisplaysDetails(expectedPerson, detailsPanelHandle);
    }
}
```

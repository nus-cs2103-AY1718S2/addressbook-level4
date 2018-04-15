# alexawangzi
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();

        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = getAddCommandForPerson(validStudent, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validStudent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validStudent), modelStub.personsAdded);
        assertTrue(modelStub.personsAdded.get(modelStub.personsAdded.size() - 1) instanceof Student);
    }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
    @Test
    public void execute_tutorAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Tutor validTutor = new TutorBuilder().build();

        CommandResult commandResult = getAddCommandForPerson(validTutor, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTutor), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTutor), modelStub.personsAdded);
        assertTrue(modelStub.personsAdded.get(modelStub.personsAdded.size() - 1) instanceof Tutor);
    }


    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();

        Person validPerson = new PersonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        getAddCommandForPerson(validPerson, modelStub).execute();
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void rateRemarkPerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void addPair(Person student, Person tutor) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePair(Pair target) throws PairNotFoundException {
            fail("This method should not be called.");
        }

        //dummy method
        @Override
        public ObservableList<Pair> getFilteredPairList() {
            return null;
        }

        //dummy method
        @Override
        public void updateFilteredPairList(Predicate<Pair> predicate) {

        }

    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\MatchCommandTest.java
``` java
public class MatchCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndexForPairAUnfilteredList_throwsCommandException() throws Exception {
        Index indexA = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index indexB = Index.fromOneBased(model.getFilteredPersonList().size());
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexforPairBUnfilteredList_throwsCommandException() {
        Index indexA = Index.fromOneBased(model.getFilteredPersonList().size());
        Index indexB = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_incompatibleSameRole_throwsCommandException() {
        Index indexA = Index.fromOneBased(7);
        Index indexB = Index.fromOneBased(8);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_ROLE));
    }

    @Test
    public void execute_incompatibleAlreadyMatched_throwsCommandException() {
        Index indexA = Index.fromOneBased(5);
        Index indexB = Index.fromOneBased(6);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, MESSAGE_MISMATCH_ALREADY_MATCHED);
    }

    @Test
    public void execute_incompatibleDifferentSubject_throwsCommandException() {
        Index indexA = Index.fromOneBased(8);
        Index indexB = Index.fromOneBased(9);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_SUBJECT));
    }

    @Test
    public void execute_incompatibleDifferentLevel_throwsCommandException() {
        Index indexA = Index.fromOneBased(9);
        Index indexB = Index.fromOneBased(10);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_LEVEL));
    }

    @Test
    public void execute_incompatibleDifferentPrice_throwsCommandException() {
        Index indexA = Index.fromOneBased(10);
        Index indexB = Index.fromOneBased(11);
        MatchCommand matchCommand = prepareCommand(indexA, indexB);
        assertCommandFailure(matchCommand, model, String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_PRICE));
    }

    @Test
    public void execute_pairAcceptedByModel_matchSuccessful() throws Exception {
        Index indexA = Index.fromOneBased(12);
        Index indexB = Index.fromOneBased(13);

        CommandResult commandResult = getMatchCommand(indexA, indexB, model).execute();

        assertEquals(String.format(MatchCommand.MESSAGE_MATCH_SUCCESS,
                LISA.getName().fullName + " and " + MARY.getName().fullName),
                commandResult.feedbackToUser);
    }

    /**
    * Returns a {@code MatchCommand} with the parameter {@code indexA}, {@code indexB}.
    */
    private MatchCommand prepareCommand(Index indexA, Index indexB) {
        MatchCommand matchCommand = new MatchCommand(indexA, indexB);
        matchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return matchCommand;
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private MatchCommand getMatchCommand(Index indexA, Index indexB, Model model) {
        MatchCommand command = new MatchCommand(indexA, indexB);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\UnmatchCommandTest.java
``` java
public class UnmatchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Pair pairToDelete = model.getFilteredPairList().get(INDEX_FIRST_PAIR.getZeroBased());
        //update persons in model to have the correct pairhash to test deletion
        Person alice = model.getFilteredPersonList().get(0);
        Person benson = model.getFilteredPersonList().get(1);
        Person newAlice = model.getFilteredPersonList().get(0);
        Person newBenson = model.getFilteredPersonList().get(1);
        newAlice.addPairHash(pairToDelete.getPairHash());
        newBenson.addPairHash(pairToDelete.getPairHash());
        newBenson.addPairHash(new PairHash(1234)); //ensure benson status stays matched after unmatch
        model.updatePerson(newAlice, alice);
        model.updatePerson(newBenson, benson);

        UnmatchCommand unmatchCommand = prepareCommand(INDEX_FIRST_PAIR);

        String expectedMessage = String.format(UnmatchCommand.MESSAGE_UNMATCH_PAIR_SUCCESS, pairToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePair(pairToDelete);

        assertCommandSuccess(unmatchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPairList().size() + 1);
        UnmatchCommand unmatchCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_PAIR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPairAtIndex(model, INDEX_FIRST_PAIR);

        Pair pairToDelete = model.getFilteredPairList().get(INDEX_FIRST_PAIR.getZeroBased());
        UnmatchCommand unmatchCommand = prepareCommand(INDEX_FIRST_PAIR);

        String expectedMessage = String.format(UnmatchCommand.MESSAGE_UNMATCH_PAIR_SUCCESS, pairToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePair(pairToDelete);
        showNoPair(expectedModel);

        assertCommandSuccess(unmatchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPairAtIndex(model, INDEX_FIRST_PAIR);

        Index outOfBoundIndex = INDEX_SECOND_PAIR;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPairList().size());

        UnmatchCommand unmatchCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_PAIR_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Pair pairToDelete = model.getFilteredPairList().get(INDEX_FIRST_PAIR.getZeroBased());
        UnmatchCommand unmatchCommand = prepareCommand(INDEX_FIRST_PAIR);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first pair deleted
        unmatchCommand.execute();
        undoRedoStack.push(unmatchCommand);

        // undo -> reverts addressbook back to previous state and filtered pair list to show all pairs
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first pair deleted again
        expectedModel.deletePair(pairToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPairList().size() + 1);
        UnmatchCommand unmatchCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> unmatchCommand not pushed into undoRedoStack
        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_PAIR_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        UnmatchCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_PAIR);
        UnmatchCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_PAIR);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        UnmatchCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_PAIR);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different pair -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code UnmatchCommand} with the parameter {@code index}.
     */
    private UnmatchCommand prepareCommand(Index index) {
        UnmatchCommand unmatchCommand = new UnmatchCommand(index);
        unmatchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unmatchCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPair(Model model) {
        model.updateFilteredPairList(p -> false);

        assertTrue(model.getFilteredPairList().isEmpty());
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_unmatch() throws Exception {
        UnmatchCommand targetCommand = new UnmatchCommand(Index.fromOneBased(1));
        UnmatchCommand parsedCommand = (UnmatchCommand) parser.parseCommand(UnmatchCommand.COMMAND_WORD + " 1");
        assertEquals(targetCommand, parsedCommand);
    }

```
###### \java\seedu\address\logic\parser\MatchCommandParserTest.java
``` java
public class MatchCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE);

    private MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // missing one index
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index in first
        assertParseFailure(parser, "-5 1", MESSAGE_INVALID_FORMAT);

        // zero index in first
        assertParseFailure(parser, "0 1", MESSAGE_INVALID_FORMAT);

        //second index is negative, first positive
        assertParseFailure(parser, "1 -1", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
}
```
###### \java\seedu\address\model\UniquePairHashListTest.java
``` java
public class UniquePairHashListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePairHashList uniquePairHashList = new UniquePairHashList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePairHashList.asObservableList().remove(0);
    }

    @Test
    public void checkEquality() throws UniquePairHashList.DuplicatePairHashException {
        UniquePairHashList uniquePairHashListA = new UniquePairHashList();
        UniquePairHashList uniquePairHashListB = new UniquePairHashList();
        uniquePairHashListA.add(new PairHash(1234567));
        assertFalse(uniquePairHashListA.equals(uniquePairHashListB));
        uniquePairHashListB.add(new PairHash(1234567));
        assertTrue(uniquePairHashListA.equals(uniquePairHashListB));

    }

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void checkContainPair() throws DuplicatePairException {
        UniquePairList uniquePairList = new UniquePairList();
        uniquePairList.add(RANDOM_PAIR_A);
        assertTrue(uniquePairList.contains(RANDOM_PAIR_A));
        assertFalse(uniquePairList.contains(RANDOM_PAIR_B));
    }

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void addPair() throws DuplicatePairException {
        UniquePairList uniquePairList = new UniquePairList();
        uniquePairList.add(RANDOM_PAIR_A);
        assertTrue(uniquePairList.contains(RANDOM_PAIR_A));
    }

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void setPair() throws DuplicatePairException, PairNotFoundException {
        UniquePairList listA = new UniquePairList();
        listA.add(RANDOM_PAIR_A);
        UniquePairList listB = new UniquePairList();
        listB.add(RANDOM_PAIR_B);
        listA.setPair(RANDOM_PAIR_A, RANDOM_PAIR_B);
        assertEquals(listA, listB);

    }

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void setPairs() throws DuplicatePairException, PairNotFoundException {
        UniquePairList listA = new UniquePairList();
        listA.add(RANDOM_PAIR_A);
        UniquePairList listB = new UniquePairList();
        listB.add(RANDOM_PAIR_B);
        listA.setPairs(listB);
        assertEquals(listA, listB);
    }

```
###### \java\seedu\address\model\UniquePairListTest.java
``` java
    @Test
    public void removePair() throws DuplicatePairException, PairNotFoundException {
        UniquePairList listA = new UniquePairList();
        UniquePairList listB = new UniquePairList();
        listA.add(RANDOM_PAIR_A);
        listA.add(RANDOM_PAIR_B);
        listB.add(RANDOM_PAIR_B);
        listA.remove(RANDOM_PAIR_A);
        assertEquals(listA, listB);
    }

```
###### \java\seedu\address\storage\XmlAdaptedPairTest.java
``` java
public class XmlAdaptedPairTest {
    private static final String INVALID_STUDENT_NAME = "R@chel";
    private static final String INVALID_TUTOR_NAME = "Bens[]n";

    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRICE = "-50";
    private static final String INVALID_LEVEL = "kindergarden";
    private static final String INVALID_SUBJECT = "fake news";

    private static final String VALID_STUDENT_NAME = RANDOM_PAIR_A.getStudentName().toString();
    private static final String VALID_TUTOR_NAME = RANDOM_PAIR_A.getTutorName().toString();
    private static final String VALID_SUBJECT = RANDOM_PAIR_A.getSubject().toString();
    private static final String VALID_LEVEL = RANDOM_PAIR_A.getLevel().toString();
    private static final String VALID_PRICE = RANDOM_PAIR_A.getPrice().toString();
    private static final String VALID_PAIRHASH = RANDOM_PAIR_A.getPairHash().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = RANDOM_PAIR_A.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPairDetails_returnsPair() throws Exception {
        XmlAdaptedPair pair = new XmlAdaptedPair(RANDOM_PAIR_A);
        assertEquals(RANDOM_PAIR_A, pair.toModelType());
    }

    @Test
    public void toModelType_invalidStudentName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(INVALID_STUDENT_NAME, INVALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullStudentName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(null, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidTutorName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, INVALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullTutorName_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, null,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, invalidTags, VALID_PAIRHASH);
        Assert.assertThrows(IllegalValueException.class, pair::toModelType);
    }

    @Test
    public void toModelType_invalidLevel_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, INVALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Level.MESSAGE_LEVEL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullLevel_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, null, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidSubject_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                INVALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Subject.MESSAGE_SUBJECT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                null, VALID_LEVEL, VALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, INVALID_PRICE, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, null, VALID_TAGS, VALID_PAIRHASH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void toModelType_nullPairHash_throwsIllegalValueException() {
        XmlAdaptedPair pair = new XmlAdaptedPair(VALID_STUDENT_NAME, VALID_TUTOR_NAME,
                VALID_SUBJECT, VALID_LEVEL, VALID_PRICE, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PairHash.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pair::toModelType);
    }

    @Test
    public void testXmlAdaptedPairEquality() {
        XmlAdaptedPair alice = new XmlAdaptedPair(RANDOM_PAIR_A);
        XmlAdaptedPair copy = new XmlAdaptedPair(RANDOM_PAIR_A);
        assertTrue(alice.equals(alice));
        assertTrue(alice.equals(copy)); //check equality if values are equal
        assertFalse(alice.equals(RANDOM_PAIR_A)); //check not equal if type is different
    }

}
```
###### \java\seedu\address\testutil\PairBuilder.java
``` java
/**
 * A utility class to help with building Pair objects.
 */
public class PairBuilder {

    public static final Person DEFAULT_STUDENT = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withPrice("50").withSubject("math").withStatus("not Matched").withLevel("upper Sec")
            .withRole("Student").build();
    public static final Person DEFAULT_TUTOR = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withPrice("50").withSubject("math").withStatus("not Matched").withLevel("upper Sec")
            .withRole("Tutor").withRemark("Impatient and poor in explanation.").build();
    public static final Subject DEFAULT_SUBJECT = new Subject("Math");
    public static final Level DEFAULT_LEVEL = new Level("Upper Sec");
    public static final Price DEFAULT_PRICE = new Price("50");
    public static final String DEFAULT_TAG_SUBJECT = "Math";
    public static final String DEFAULT_TAG_LEVEL = "Upper Sec";
    public static final String DEFAULT_TAG_PRICE = "50";

    private Person student;
    private Person tutor;
    private String subject;
    private String level;
    private String price;
    private Set<Tag> tags;

    public PairBuilder() {
        student = DEFAULT_STUDENT;
        tutor =  DEFAULT_TUTOR;
        subject = DEFAULT_SUBJECT.toString();
        level = DEFAULT_LEVEL.toString();
        price = DEFAULT_PRICE.toString();
        tags = SampleDataUtil.getTagSet(DEFAULT_TAG_SUBJECT, DEFAULT_TAG_LEVEL, DEFAULT_TAG_PRICE);
    }


    /**
     * Sets the {@code studentName} of the {@code Pair} that we are building.
     */
    public PairBuilder withStudent(Person student) {
        this.student = student;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Pair} that we are building.
     */
    public PairBuilder withTutor(Person tutor) {
        this.tutor = tutor;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Pair} that we are building.
     */
    public PairBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }


    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withLevel(String level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }


    /**
     * Sets the {@code Price} of the {@code Pair} that we are building.
     */
    public PairBuilder withPrice(String price) {
        this.price = price;
        return this;
    }


    /**
     * Sets the required attribute tags for the pair
     */
    private void setTags() {

        tags.add(new Tag(subject.toString(), Tag.AllTagTypes.SUBJECT));
        tags.add(new Tag(level.toString(), Tag.AllTagTypes.LEVEL));
        tags.add(new Tag(price.toString(), Tag.AllTagTypes.PRICE));
    }

    /**
     * Builds a pair based off the attributes in this class
     * @return Pair with set attributes
     */
    public Pair build() {
        setTags();
        return new Pair(student, tutor, student.getSubject(), student.getLevel(), student.getPrice());
    }

}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code pairHash} of the {@code Person} that we are building.
     */
    public PersonBuilder withPairhash(PairHash pairHash) {
        Set<PairHash> pairHashesSet = new HashSet<PairHash>();
        pairHashesSet.add(pairHash);
        this.pairHashes = pairHashesSet;
        return this;
    }

    public PersonBuilder withPairhash(int value) {
        return this.withPairhash(new PairHash(value));
    }

```
###### \java\seedu\address\testutil\TypicalPairs.java
``` java
/**
 * A utility class containing a list of {@code Pair} objects to be used in tests.
 */
public class TypicalPairs {

    public static final Pair RANDOM_PAIR_A = new PairBuilder().withStudent(BENSON).withTutor(ALICE)
            .build();

    public static final Pair RANDOM_PAIR_B = new PairBuilder().withStudent(DANIEL).withTutor(CARL)
            .build();

    public static final Pair RANDOM_PAIR_C = new PairBuilder().withStudent(FIONA).withTutor(ELLE)
            .build();



    private TypicalPairs() {} // prevents instantiation

    public static List<Pair> getTypicalPairs() {
        return new ArrayList<>(Arrays.asList(RANDOM_PAIR_A, RANDOM_PAIR_B, RANDOM_PAIR_C));
    }

}
```

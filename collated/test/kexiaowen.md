# kexiaowen
###### /java/seedu/address/model/person/UniversityTest.java
``` java
public class UniversityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new University(null));
    }

    @Test
    public void constructor_invalidUniversity_throwsIllegalArgumentException() {
        String invalidUniversity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new University(invalidUniversity));
    }

    @Test
    public void isValidUniversity() {
        // null university
        Assert.assertThrows(NullPointerException.class, () -> University.isValidUniversity(null));

        // invalid university
        assertFalse(University.isValidUniversity("")); // empty string
        assertFalse(University.isValidUniversity(" ")); // spaces only
        assertFalse(University.isValidUniversity("^")); // only non-alphanumeric characters
        assertFalse(University.isValidUniversity("N*S")); // contains non-alphanumeric characters

        // valid name
        assertTrue(University.isValidUniversity("ntu")); // alphabets only
        assertTrue(University.isValidUniversity("12345")); // numbers only
        assertTrue(University.isValidUniversity("nus the 1st")); // alphanumeric characters
        assertTrue(University.isValidUniversity("National University of Singapore")); // with capital letters
        assertTrue(University.isValidUniversity("University of California Santa Barbara")); // long names
    }
}
```
###### /java/seedu/address/model/person/JobAppliedTest.java
``` java
public class JobAppliedTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JobApplied(null));
    }

    @Test
    public void constructor_invalidJobApplied_throwsIllegalArgumentException() {
        String invalidJobApplied = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new JobApplied(invalidJobApplied));
    }

    @Test
    public void isValidJobApplied() {
        // null jobApplied
        Assert.assertThrows(NullPointerException.class, () -> JobApplied.isValidJobApplied(null));

        // invalid jobApplied
        assertFalse(JobApplied.isValidJobApplied("")); // empty string
        assertFalse(JobApplied.isValidJobApplied(" ")); // spaces only

        // valid jobApplied
        assertTrue(JobApplied.isValidJobApplied("software engineer")); // alphabets only
        assertTrue(JobApplied.isValidJobApplied("12345")); // numbers only
        assertTrue(JobApplied.isValidJobApplied("1 software engineer 2 DevOps")); // alphanumeric characters
        assertTrue(JobApplied.isValidJobApplied("Software Engineer")); // with capital letters
        assertTrue(JobApplied.isValidJobApplied("Software Engineer & Web Developer")); // long names
    }
}
```
###### /java/seedu/address/model/person/RatingTest.java
``` java
public class RatingTest {

    private final Rating rating = new Rating(3, 3,
            4, 3.5);
    @Test
    public void isValidScore() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Rating.isValidScore(null));

        // invalid scores
        assertFalse(Rating.isValidScore((double) 0));
        assertFalse(Rating.isValidScore(5.5));
        assertFalse(Rating.isValidScore((double) -3));

        // valid phone numbers
        assertTrue(Rating.isValidScore((double) 1));
        assertTrue(Rating.isValidScore(3.5));
        assertTrue(Rating.isValidScore((double) 5));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(rating.equals(rating));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Rating ratingCopy = new Rating(rating.getTechnicalSkillsScore(), rating.communicationSkillsScore,
                rating.getProblemSolvingSkillsScore(), rating.getExperienceScore());
        assertTrue(rating.equals(ratingCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(rating.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(rating.equals(null));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Rating differentRating = new Rating(1, 1,
                1, 1);
        assertFalse(rating.equals(differentRating));
    }
}
```
###### /java/seedu/address/logic/commands/RateCommandTest.java
``` java
/**
  * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
  */
public class RateCommandTest {

    public static final String TECHNICAL_SKILLS_SCORE = "4";
    public static final String COMMUNICATION_SKILLS_SCORE = "4.5";
    public static final String PROBLEM_SOLVING_SKILLS_SCORE = "3";
    public static final String EXPERIENCE_SCORE = "3.5";
    public static final Rating VALID_RATING = new Rating(4, 4.5,
            3, 3.5);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person ratedPerson = new PersonBuilder(firstPerson).withRating(TECHNICAL_SKILLS_SCORE,
                COMMUNICATION_SKILLS_SCORE, PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE).build();

        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, ratedPerson.getRating());
        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_PERSON_SUCCESS,
                ratedPerson.getName(), ratedPerson.getRating().getTechnicalSkillsScore(),
                ratedPerson.getRating().getCommunicationSkillsScore(),
                ratedPerson.getRating().getProblemSolvingSkillsScore(),
                ratedPerson.getRating().getExperienceScore(),
                ratedPerson.getRating().getOverallScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, ratedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person ratedPerson = new PersonBuilder(firstPerson).withRating(TECHNICAL_SKILLS_SCORE,
                COMMUNICATION_SKILLS_SCORE, PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE).build();

        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, ratedPerson.getRating());
        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_PERSON_SUCCESS,
                ratedPerson.getName(),
                ratedPerson.getRating().getTechnicalSkillsScore(),
                ratedPerson.getRating().getCommunicationSkillsScore(),
                ratedPerson.getRating().getProblemSolvingSkillsScore(),
                ratedPerson.getRating().getExperienceScore(),
                ratedPerson.getRating().getOverallScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, ratedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        RateCommand rateCommand = prepareCommand(outOfBoundIndex, VALID_RATING);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RateCommand rateCommand = prepareCommand(outOfBoundIndex, VALID_RATING);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withRating(TECHNICAL_SKILLS_SCORE,
                COMMUNICATION_SKILLS_SCORE, PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE).build();
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_RATING);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // rate -> first person rating changed
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person modified again
        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, VALID_RATING);

        // execution failed -> rateCommand not pushed into undoRedoStack
        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#remark} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonRated() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_RATING);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withRating(TECHNICAL_SKILLS_SCORE,
                COMMUNICATION_SKILLS_SCORE, PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE).build();
        // rate -> modifies second person in unfiltered person list / first person in filtered person list
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertNotEquals(personToModify, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> modifies same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final RateCommand standardCommand = new RateCommand(INDEX_FIRST_PERSON, VALID_RATING);

        // same values -> returns true
        RateCommand commandWithSameValues = new RateCommand(INDEX_FIRST_PERSON, VALID_RATING);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_SECOND_PERSON,
                VALID_RATING)));

        // different rating -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_PERSON,
                new Rating(1, 1,
                        1, 1))));
    }

    /**
      * Returns a {@code RateCommand}.
      */
    private RateCommand prepareCommand(Index index, Rating rating) {
        RateCommand rateCommand = new RateCommand(index, rating);
        rateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return rateCommand;
    }
}
```
###### /java/seedu/address/logic/commands/RatingDeleteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 */
public class RatingDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToDeleteRating = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        PersonBuilder firstPerson = new PersonBuilder(personToDeleteRating);
        Person editedPerson = firstPerson.withRating("-1", "-1",
                "-1", "-1").build();

        String expectedMessage = String.format(RatingDeleteCommand.MESSAGE_DELETE_RATING_SUCCESS,
                personToDeleteRating.getName());

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToDeleteRating, editedPerson);

        assertCommandSuccess(ratingDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(ratingDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDeleteRating = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        PersonBuilder firstPerson = new PersonBuilder(personToDeleteRating);
        Person editedPerson = firstPerson.withRating("-1", "-1",
                "-1", "-1").build();

        String expectedMessage = String.format(RatingDeleteCommand.MESSAGE_DELETE_RATING_SUCCESS,
                personToDeleteRating.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToDeleteRating, editedPerson);

        assertCommandSuccess(ratingDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of list of candidates
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RatingDeleteCommand ratingDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(ratingDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personNotRated_throwsCommandException() {
        Person personNotRated = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_THIRD_PERSON);

        String expectedMessage = String.format(RatingDeleteCommand.MESSAGE_PERSON_NOT_RATED,
                personNotRated.getName());

        assertCommandFailure(ratingDeleteCommand, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToDeleteRating = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonBuilder firstPerson = new PersonBuilder(personToDeleteRating);
        Person editedPerson = firstPerson.withRating("-1", "-1",
                "-1", "-1").build();


        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // deleteRating -> first person's rating deleted
        ratingDeleteCommand.execute();
        undoRedoStack.push(ratingDeleteCommand);

        // undo -> reverts HR+ back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person's rating deleted again
        expectedModel.updatePerson(personToDeleteRating, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> ratingDeleteCommand not pushed into undoRedoStack
        assertCommandFailure(ratingDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#remark} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeletedRating() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withRating("-1",
                "-1", "-1", "-1").build();

        // deleteRating -> modifies second person in unfiltered person list / first person in filtered person list
        ratingDeleteCommand.execute();
        undoRedoStack.push(ratingDeleteCommand);

        // undo -> reverts HR+ back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertNotEquals(personToModify, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> modifies same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Returns a {@code RatingDeleteCommand} with the parameter {@code index}.
     */
    private RatingDeleteCommand prepareCommand(Index index) {
        RatingDeleteCommand ratingDeleteCommand = new RatingDeleteCommand(index);
        ratingDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ratingDeleteCommand;
    }
}
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByName_sortSuccessful() throws Exception {
        SortCommand command = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.NAME);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "name", "descending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
    }

    @Test
    public void execute_sortByRating_sortSuccessful() throws Exception {
        SortCommand command = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.RATING);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "rating", "descending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                BENSON, ALICE, GEORGE, FIONA, ELLE, DANIEL, CARL));
    }

    @Test
    public void execute_sortByGradePointAverage_sortSuccessful() throws Exception {
        SortCommand command = prepareCommand(SortCommand.SortOrder.ASC, SortCommand.SortField.GPA);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "gpa", "ascending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                GEORGE, DANIEL, ELLE, BENSON, FIONA, ALICE, CARL));
    }

    @Test
    public void isValidSortOrder() {
        assertTrue(SortCommand.isValidSortOrder("asc"));
        assertTrue(SortCommand.isValidSortOrder("desc"));

        assertFalse(SortCommand.isValidSortOrder(""));
        assertFalse(SortCommand.isValidSortOrder("ascc"));

    }

    @Test
    public void isValidSortField() {
        assertTrue(SortCommand.isValidSortField("gpa"));
        assertTrue(SortCommand.isValidSortField("name"));
        assertTrue(SortCommand.isValidSortField("rating"));

        assertFalse(SortCommand.isValidSortField(""));
        assertFalse(SortCommand.isValidSortField("gpaaaa"));
        assertFalse(SortCommand.isValidSortField("ratings"));
    }

    @Test
    public void equals() {
        final SortCommand standardCommand = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.GPA);
        // same values -> returns true
        SortCommand commandWithSameValues = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.GPA);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different sort order -> returns false
        assertFalse(standardCommand.equals(prepareCommand(SortCommand.SortOrder.ASC, SortCommand.SortField.GPA)));

        // different sort field -> returns false
        assertFalse(standardCommand.equals(prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.NAME)));
    }

    /**
     * Returns a {@code SortCommand}.
     */
    private SortCommand prepareCommand(SortCommand.SortOrder sortOrder, SortCommand.SortField sortField) {
        SortCommand sortCommand = new SortCommand(sortOrder, sortField);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SortCommand command, String expectedMessage, List<Person> expectedList)
            throws Exception {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
    }
}
```
###### /java/seedu/address/logic/parser/RatingDeleteCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RatingDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RatingDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RatingDeleteCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingDeleteCommand.MESSAGE_USAGE);

    private RatingDeleteCommandParser parser = new RatingDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRatingCommand() {
        assertParseSuccess(parser, "1", new RatingDeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }
}
```
###### /java/seedu/address/logic/parser/RateCommandParserTest.java
``` java
public class RateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_RATING_DESC, Rating.MESSAGE_RATING_CONSTRAINTS);
        assertParseFailure(parser, "1" + " " + PREFIX_TECHNICAL_SKILLS_SCORE
                + " " + PREFIX_COMMUNICATION_SKILLS_SCORE
                + " " + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE
                + " " + PREFIX_EXPERIENCE_SCORE, Rating.MESSAGE_RATING_CONSTRAINTS);
    }

}
```

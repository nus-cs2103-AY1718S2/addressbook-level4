# jordancjq
###### \java\seedu\address\logic\commands\AssignCommandTest.java
``` java
public class AssignCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithPersonsAndTeams(), new UserPrefs());
    }

    @Test
    public void execute_validTeamAndIndexUnfilteredList_success() {
        // single index
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Team firstTeam = model.getAddressBook().getTeamList().get(0);
        assertExecutionSuccess(firstTeam, Collections.singletonList(lastPersonIndex));

        // multiple indexes
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        assertExecutionSuccess(firstTeam, Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
    }

    @Test
    public void execute_validTeamAndIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Team firstTeam = model.getAddressBook().getTeamList().get(0);

        assertExecutionSuccess(firstTeam, Collections.singletonList(INDEX_FIRST_PERSON));
    }

    @Test
    public void execute_assignAndUnassignValidTeamValidIndex_success() throws Exception {
        Team firstTeam = model.getAddressBook().getTeamList().get(0);

        AssignCommand assignCommand = prepareCommand(firstTeam.getTeamName(),
                Collections.singletonList(INDEX_FIRST_PERSON));
        assignCommand.execute();
        eventsCollectorRule.eventsCollector.reset();

        Team unspecifiedTeam = new Team(new TeamName(UNSPECIFIED_FIELD));

        assertExecutionSuccess(unspecifiedTeam, Collections.singletonList(INDEX_FIRST_PERSON));
    }

    @Test
    public void execute_validTeamAndInvalidIndexUnfilteredList_fail() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Team firstTeam = model.getAddressBook().getTeamList().get(0);

        assertExecutionFailure(firstTeam, Collections.singletonList(outOfBoundsIndex),
                Messages.MESSAGE_INVALID_ALL_INDEX);
    }

    @Test
    public void execute_invalidTeamAndValidIndexUnfilteredList_fail() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Team invalidTeam = ARSENAL;

        assertExecutionFailure(invalidTeam, Collections.singletonList(outOfBoundsIndex),
                Messages.MESSAGE_TEAM_NOT_FOUND);
    }

    @Test
    public void execute_validTeamAndValidIndexUnfilteredListPersonExistInTeam_fail() throws Exception {
        Team firstTeam = model.getAddressBook().getTeamList().get(0);

        AssignCommand assignCommand = prepareCommand(firstTeam.getTeamName(),
                Collections.singletonList(INDEX_FIRST_PERSON));

        String personName = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName().toString();

        String expectedMessage = AssignCommand.MESSAGE_FAILURE
                + String.format(AssignCommand.MESSAGE_DUPLICATE_PERSON, personName);

        assignCommand.execute();
        eventsCollectorRule.eventsCollector.reset();

        assertExecutionFailure(firstTeam, Collections.singletonList(INDEX_FIRST_PERSON),
               expectedMessage);
    }

    @Test
    public void equals() {
        final AssignCommand standardCommand = prepareCommand(CHELSEA.getTeamName(),
                Collections.singletonList(INDEX_FIRST_PERSON));
        final AssignCommand secondCommand = prepareCommand(LIVERPOOL.getTeamName(),
                Collections.singletonList(INDEX_SECOND_PERSON));

        // same values -> returns true
        AssignCommand commandWithSameValues = prepareCommand(CHELSEA.getTeamName(),
                Collections.singletonList(INDEX_FIRST_PERSON));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different type -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different team name and index -> returns false
        assertFalse(standardCommand.equals(secondCommand));
    }

    /**
     * Returns an {@code AssignCommand} with parameters {@code team}.
     */
    private AssignCommand prepareCommand(TeamName team, List<Index> indexes) {
        AssignCommand assignCommand = new AssignCommand(team, indexes);
        assignCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return assignCommand;
    }

    /**
     * Executes a {@code AssignCommand} with the given {@code team} and {@code indexes},
     * and checks that {@code HighlightSelectedTeamEvent} is raised with the correct team name.
     */
    private void assertExecutionSuccess(Team team, List<Index> indexes) {
        AssignCommand assignCommand = prepareCommand(team.getTeamName(), indexes);
        String expectedAssignResultMessage;

        expectedAssignResultMessage = getMultiplePlayerAssignResultMessage(team, indexes);

        try {
            CommandResult commandResult = assignCommand.execute();
            assertEquals(String.format(AssignCommand.MESSAGE_SUCCESS + expectedAssignResultMessage),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        if (!team.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
            HighlightSelectedTeamEvent lastEvent =
                    (HighlightSelectedTeamEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(team.getTeamName().toString(), lastEvent.teamName);
        }

        team.setPersons(new UniquePersonList());
    }

    /**
     * Executes a {@code AssignCommand} with the given {@code team} and {@code indexes},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Team team, List<Index> indexes, String expectedMessage) {
        AssignCommand assignCommand = prepareCommand(team.getTeamName(), indexes);

        try {
            assignCommand.execute();
            System.out.println(model.getAddressBook().getTeamList());
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
            team.setPersons(new UniquePersonList());
        }
    }

    private String getMultiplePlayerAssignResultMessage(Team team, List<Index> indexes) {
        String expectedAssignResultMessage = "";
        for (Index index : indexes) {
            Person person = model.getFilteredPersonList().get(index.getZeroBased());
            if (team.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
                expectedAssignResultMessage += String.format(MESSAGE_UNASSIGN_TEAM_SUCCESS,
                        person.getName().toString(), person.getTeamName().toString());
            } else {
                if (person.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
                    expectedAssignResultMessage += String.format(AssignCommand.MESSAGE_UNSPECIFIED_TEAM_SUCCESS,
                            person.getName().toString(), team.getTeamName().toString());
                } else {
                    expectedAssignResultMessage += String.format(AssignCommand.MESSAGE_TEAM_TO_TEAM_SUCCESS,
                            person.getName().toString(), person.getTeamName().toString(),
                            team.getTeamName().toString());
                }
            }
        }
        return expectedAssignResultMessage;
    }
}
```
###### \java\seedu\address\logic\commands\CreateCommandTest.java
``` java
public class CreateCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateCommand(null);
    }

    @Test
    public void execute_createTeam_success() throws Exception {
        Team teamToAdd = new Team(new TeamName(VALID_TEAM_ARSENAL));
        String expectedMessage = String.format(CreateCommand.MESSAGE_SUCCESS, teamToAdd.getTeamName().toString());

        CreateCommand createCommand = prepareCommand(VALID_TEAM_ARSENAL);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.createTeam(teamToAdd);

        assertCommandSuccess(createCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_createTeamDuplicateTeam_failure() throws Exception {
        Team firstTeam = model.getAddressBook().getTeamList().get(INDEX_FIRST_TEAM.getZeroBased());
        CreateCommand createCommand = prepareCommand(CHELSEA.getTeamName().toString());

        assertCommandFailure(createCommand, model, CreateCommand.MESSAGE_DUPLICATE_TEAM);
    }

    @Test
    public void executeUndoRedo_createTeam_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        CreateCommand createCommand = prepareCommand(VALID_TEAM_ARSENAL);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first team created
        createCommand.execute();
        undoRedoStack.push(createCommand);

        // undo -> reverts addressbook back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same team created again
        expectedModel.createTeam(new Team(new TeamName(VALID_TEAM_ARSENAL)));
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final CreateCommand standardCommand = prepareCommand(VALID_TEAM_ARSENAL);

        // same values -> returns true
        CreateCommand commandWithSameValues = prepareCommand(VALID_TEAM_ARSENAL);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different type -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different team name -> returns false
        assertFalse(standardCommand.equals(new CreateCommand(new Team(new TeamName(VALID_TEAM_BARCELONA)))));
    }

    /**
     * Returns an {@code CreateCommand} with parameters {@code team}.
     */
    private CreateCommand prepareCommand(String team) {
        CreateCommand createCommand = new CreateCommand(new Team(new TeamName(team)));
        createCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return createCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
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

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

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
###### \java\seedu\address\logic\commands\RemoveCommandTest.java
``` java
public class RemoveCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveCommand(null);
    }

    @Test
    public void execute_removeTeam_success() throws Exception {
        Team teamToRemove = TypicalTeams.LIVERPOOL;
        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_TEAM_SUCCESS,
                teamToRemove.getTeamName().toString());

        RemoveCommand removeCommand = prepareCommand(teamToRemove);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.removeTeam(teamToRemove.getTeamName());

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeNonExistingTeam_throwsTeamNotFoundException() throws Exception {
        Team teamToRemove = TypicalTeams.ARSENAL;
        RemoveCommand removeCommand = prepareCommand(teamToRemove);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_TEAM_NOT_FOUND);
    }

    @Test
    public void equals() {
        TeamName chelsea = TypicalTeams.CHELSEA.getTeamName();
        TeamName liverpool = TypicalTeams.LIVERPOOL.getTeamName();
        RemoveCommand removeChelseaCommand = new RemoveCommand(chelsea);
        RemoveCommand removeLiverpoolCommand = new RemoveCommand(liverpool);

        // same object -> returns true
        assertTrue(removeChelseaCommand.equals(removeChelseaCommand));

        // same values -> returns true
        RemoveCommand removeChelseaCommandCopy = new RemoveCommand(chelsea);
        assertTrue(removeChelseaCommand.equals(removeChelseaCommandCopy));

        // different types -> returns false
        assertFalse(removeChelseaCommand.equals(1));

        // null -> returns false
        assertFalse(removeChelseaCommand.equals(null));

        // different person -> returns false
        assertFalse(removeChelseaCommand.equals(removeLiverpoolCommand));
    }

    /**
     * Returns an {@code RemoveCommand} with parameters {@code team}.
     */
    private RemoveCommand prepareCommand(Team team) {
        RemoveCommand removeCommand = new RemoveCommand(team.getTeamName());
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RenameCommandTest.java
``` java
public class RenameCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBookWithTeams(), new UserPrefs());

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateCommand(null);
    }

    @Test
    public void execute_renameTeam_sucess() throws Exception {
        Index indexLastTeam = Index.fromOneBased(model.getAddressBook().getTeamList().size());
        Team targetTeam = model.getAddressBook().getTeamList().get(indexLastTeam.getZeroBased());

        TeamName renameInto = new TeamName(VALID_TEAM_ARSENAL);

        RenameCommand renameCommand = prepareCommand(targetTeam.getTeamName(), renameInto);

        String expectedMessage = String.format(RenameCommand.MESSAGE_RENAME_SUCCESS,
                targetTeam.getTeamName().toString(), renameInto.toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.renameTeam(targetTeam, renameInto);

        assertCommandSuccess(renameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_renameTeamWithExistingName_failure() throws Exception {
        Team existingTeam = TypicalTeams.LIVERPOOL;
        Team targetTeam = TypicalTeams.CHELSEA;

        RenameCommand renameCommand = prepareCommand(targetTeam.getTeamName(), existingTeam.getTeamName());

        assertCommandFailure(renameCommand, model, RenameCommand.MESSAGE_NO_CHANGE);
    }

    @Test
    public void execute_renameTeamWithSameName_failure() throws Exception {
        Team existingTeam = TypicalTeams.CHELSEA;
        Team targetTeam = TypicalTeams.CHELSEA;

        RenameCommand renameCommand = prepareCommand(targetTeam.getTeamName(), existingTeam.getTeamName());

        assertCommandFailure(renameCommand, model, RenameCommand.MESSAGE_NO_CHANGE);
    }

    @Test
    public void execute_renameNonExistingTeam_failure() throws Exception {
        Team nonExistingTeam = TypicalTeams.ARSENAL;
        Team targetTeam = TypicalTeams.BARCELONA;

        RenameCommand renameCommand = prepareCommand(targetTeam.getTeamName(), nonExistingTeam.getTeamName());

        assertCommandFailure(renameCommand, model, Messages.MESSAGE_TEAM_NOT_FOUND);
    }

    @Test
    public void equals() {
        final RenameCommand standardCommand = prepareCommand(new TeamName(VALID_TEAM_ARSENAL),
                new TeamName(VALID_TEAM_BARCELONA));

        // same values -> returns true
        RenameCommand commandWithSameValues = prepareCommand(new TeamName(VALID_TEAM_ARSENAL),
                new TeamName(VALID_TEAM_BARCELONA));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different type -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different team name -> returns false
        assertFalse(standardCommand.equals(new RenameCommand(new TeamName(VALID_TEAM_BARCELONA),
                new TeamName(VALID_TEAM_CHELSEA))));
    }

    /**
     * Returns an {@code RenameCommand} with parameters {@code targetTeam} and {@code updatedTeamName}.
     */
    private RenameCommand prepareCommand(TeamName targetTeam, TeamName updatedTeamName) {
        RenameCommand renameCommand = new RenameCommand(targetTeam, updatedTeamName);
        renameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return renameCommand;
    }

}
```
###### \java\seedu\address\logic\commands\ViewCommandTest.java
``` java
public class ViewCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBookWithPersonsAndTeams(), new UserPrefs());

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ViewCommand(null);
    }

    @Test
    public void execute_viewTeam_success() throws Exception {
        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_TEAM_SUCCESS, VALID_TEAM_CHELSEA);

        ViewCommand viewCommand = prepareCommand(VALID_TEAM_CHELSEA);

        assertEquals(expectedMessage, viewCommand.execute().feedbackToUser);

        HighlightSelectedTeamEvent lastEvent =
                (HighlightSelectedTeamEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(VALID_TEAM_CHELSEA, lastEvent.teamName);
    }

    @Test
    public void execute_viewTeamNonExist_fail() throws Exception {
        ViewCommand viewCommand = prepareCommand(VALID_TEAM_ARSENAL);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_TEAM_NOT_FOUND);

        viewCommand.execute();
    }

    @Test
    public void equals() {
        final ViewCommand standardCommand = prepareCommand(VALID_TEAM_CHELSEA);

        // same values -> returns true
        ViewCommand commandWithSameValues = prepareCommand(VALID_TEAM_CHELSEA);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different type -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different team name -> returns false
        assertFalse(standardCommand.equals(new ViewCommand(new TeamName(VALID_TEAM_ARSENAL))));
    }

    /**
     * Returns an {@code ViewCommand} with parameters {@code team}.
     */
    private ViewCommand prepareCommand(String team) {
        ViewCommand viewCommand = new ViewCommand(new TeamName(team));
        viewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AssignCommandParserTest.java
``` java
public class AssignCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AssignCommand.MESSAGE_USAGE);

    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, TEAM_DESC_ARSENAL, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid team name
        assertParseFailure(parser, INVALID_TEAM_NAME + INDEX_DESC_1, TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);

        // invalid index
        assertParseFailure(parser, VALID_TEAM_ARSENAL + INVALID_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);

        // multiple index, with 1 invalid
        assertParseFailure(parser, VALID_TEAM_ARSENAL + INDEX_DESC_1 + " -1", ParserUtil.MESSAGE_INVALID_INDEX);

        // multiple invalid values, first invalid value captured
        assertParseFailure(parser, INVALID_TEAM_NAME + INVALID_INDEX, TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // valid team name and 1 index
        TeamName targetTeam = new TeamName(VALID_TEAM_ARSENAL);
        List<Index> targetIndex = Collections.singletonList(INDEX_FIRST_PERSON);
        String userInput = VALID_TEAM_ARSENAL + INDEX_DESC_1;
        AssignCommand expectedCommand = new AssignCommand(targetTeam, targetIndex);
        assertParseSuccess(parser, userInput, expectedCommand);

        // valid team name and multiple valid indexes
        targetIndex = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        userInput = VALID_TEAM_ARSENAL + INDEX_DESC_1 + " 2 3";
        expectedCommand = new AssignCommand(targetTeam, targetIndex);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\CreateCommandParserTest.java
``` java
public class CreateCommandParserTest {

    private static final String TEAM_NAME_EMPTY = "";

    private CreateCommandParser parser = new CreateCommandParser();

    @Test
    public void parse_fieldPresent_success() {
        // with team name
        CreateCommand expectedCommand = new CreateCommand(new Team(new TeamName(VALID_TEAM_ARSENAL)));
        String userInput = VALID_TEAM_ARSENAL;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE);

        // missing team name
        assertParseFailure(parser, TEAM_NAME_EMPTY, expectedMessage);
    }

    @Test
    public void parse_invalidTeamName_failure() {
        // invalid team name
        assertParseFailure(parser, INVALID_TEAM_NAME_DESC, TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseIndexes_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndexes(Long.toString(Integer.MAX_VALUE + 1) + "2");
    }

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseIndexes_validInput_success() throws Exception {
        List<Index> expectedIndexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        assertEquals(expectedIndexes, ParserUtil.parseIndexes("1 2 3"));
    }

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseValue_emptyValue_returnsUnspecifiedField() throws Exception {
        assertEquals(Optional.of(UNSPECIFIED_FIELD),
                ParserUtil.parseValue(Optional.empty(), Phone.MESSAGE_PHONE_CONSTRAINTS));
    }

    @Test
    public void parseValue_unspecifiedValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Phone.MESSAGE_PHONE_CONSTRAINTS);
        ParserUtil.parseValue(Optional.of(UNSPECIFIED_FIELD), Phone.MESSAGE_PHONE_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
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
###### \java\seedu\address\logic\parser\RemoveCommandParserTest.java
``` java
public class RemoveCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemoveCommand.MESSAGE_USAGE);

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_missingField_failure() {
        // no team name specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTeamName_failure() {
        // invalid team name
        assertParseFailure(parser, "&-Team", TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_validTeam_success() {
        String userInput = VALID_TEAM_ARSENAL;
        RemoveCommand expectedCommand = new RemoveCommand(new TeamName(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);

        // with space in name
        userInput = VALID_TEAM_ARSENAL + " " + VALID_TEAM_BARCELONA;
        expectedCommand = new RemoveCommand(new TeamName(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\RenameCommandParserTest.java
``` java
public class RenameCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RenameCommand.MESSAGE_USAGE);

    private RenameCommandParser parser = new RenameCommandParser();

    @Test
    public void parse_missingField_failure() {
        // no field
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no new team name specified
        assertParseFailure(parser, VALID_TEAM_ARSENAL, MESSAGE_INVALID_FORMAT);

        // no specified team to rename
        assertParseFailure(parser, TEAM_DESC_ARSENAL, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_extraNewTeamName_failure() {
        // two new team name specified
        assertParseFailure(parser, VALID_TEAM_ARSENAL + TEAM_DESC_CHELSEA + TEAM_DESC_CHELSEA,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_sameNameAsSpecified_failure() {
        // new team name is same as specified team team
        assertParseFailure(parser, VALID_TEAM_ARSENAL + TEAM_DESC_ARSENAL, RenameCommand.MESSAGE_NO_CHANGE);
    }

    @Test
    public void parse_invalidTeamName_failure() {
        // invalid new team name
        assertParseFailure(parser, VALID_TEAM_ARSENAL + INVALID_TEAM_NAME_DESC,
                TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);

        // invalid given team name
        assertParseFailure(parser, "&-team" + TEAM_DESC_ARSENAL,
                TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_validTeamName_success() {
        String userInput = VALID_TEAM_ARSENAL + TEAM_DESC_CHELSEA;
        RenameCommand expectedCommand =
                new RenameCommand(new TeamName(VALID_TEAM_ARSENAL), new TeamName(VALID_TEAM_CHELSEA));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\ViewCommandParserTest.java
``` java
public class ViewCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ViewCommand.MESSAGE_USAGE);

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_missingField_failure() {
        // no team name specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTeamName_failure() {
        // invalid team name
        assertParseFailure(parser, INVALID_TEAM_NAME_DESC, TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_validTeam_success() {
        String userInput = VALID_TEAM_ARSENAL;
        ViewCommand expectedCommand = new ViewCommand(new TeamName(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);

        // with space in name
        userInput = VALID_TEAM_ARSENAL + " " + VALID_TEAM_BARCELONA;
        expectedCommand = new ViewCommand(new TeamName(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\model\person\RemarkTest.java
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

    @Test
    public void isEqualRemark() {
        Remark x  = new Remark("Another Remark");
        Remark y = new Remark("Another Remark");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}
```
###### \java\seedu\address\model\team\TeamNameTest.java
``` java
public class TeamNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TeamName(null));
    }

    @Test
    public void constructor_invalidTeamName_throwsIllegalArgumentException() {
        String invalidTeamName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TeamName(invalidTeamName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> TeamName.isValidName(null));

        // invalid name
        assertFalse(TeamName.isValidName("")); // empty string
        assertFalse(TeamName.isValidName(" ")); // spaces only
        assertFalse(TeamName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(TeamName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TeamName.isValidName("new jersey")); // alphabets only
        assertTrue(TeamName.isValidName("5566")); // numbers only
        assertTrue(TeamName.isValidName("5th cycle")); // alphanumeric characters
        assertTrue(TeamName.isValidName("Georgia Clint")); // with capital letters
        assertTrue(TeamName.isValidName("The Longest Football Team Name You Know")); // long names
    }
}
```
###### \java\seedu\address\model\UniqueTeamListTest.java
``` java
public class UniqueTeamListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTeamList uniqueTagList = new UniqueTeamList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\testutil\TypicalTeams.java
``` java
/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {

    public static final Team CHELSEA = new TeamBuilder().withTeamName("Chelsea").build();
    public static final Team LIVERPOOL = new TeamBuilder().withTeamName("Liverpool").build();

    // Manually added - Team's details found in {@code CommandTestUtil}
    public static final Team ARSENAL = new TeamBuilder().withTeamName("Arsenal").build();
    public static final Team BARCELONA = new TeamBuilder().withTeamName("Barcelona").build();

    private TypicalTeams() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical teams.
     */
    public static AddressBook getTypicalAddressBookWithTeams() {
        AddressBook ab = new AddressBook();
        for (Team team : getTypicalTeams()) {
            try {
                ab.createTeam(team);
            } catch (DuplicateTeamException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical teams and persons.
     */
    public static AddressBook getTypicalAddressBookWithPersonsAndTeams() {
        AddressBook ab = new AddressBook();
        for (Team team : getTypicalTeams()) {
            try {
                ab.createTeam(team);
            } catch (DuplicateTeamException e) {
                throw new AssertionError("not possible");
            }
        }

        for (Person person : TypicalPersons.getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }

        return ab;
    }

    public static List<Team> getTypicalTeams() {
        return new ArrayList<>(Arrays.asList(CHELSEA, LIVERPOOL));
    }
}
```

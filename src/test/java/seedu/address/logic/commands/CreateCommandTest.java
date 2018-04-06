package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_BARCELONA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TEAM;
import static seedu.address.testutil.TypicalTeams.CHELSEA;
import static seedu.address.testutil.TypicalTeams.getTypicalAddressBookWithTeams;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

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
        String expectedMessage = String.format(CreateCommand.MESSAGE_SUCCESS, teamToAdd);

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

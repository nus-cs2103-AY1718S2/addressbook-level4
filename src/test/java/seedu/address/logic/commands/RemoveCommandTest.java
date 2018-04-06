package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTeams.getTypicalAddressBookWithTeams;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;
import seedu.address.testutil.TypicalTeams;

//@@author jordancjq
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

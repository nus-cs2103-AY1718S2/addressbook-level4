package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_BARCELONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_CHELSEA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTeams.getTypicalAddressBookWithTeams;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
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

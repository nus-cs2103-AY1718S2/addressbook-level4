package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_CHELSEA;
import static seedu.address.testutil.TypicalTeams.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.HighlightSelectedTeamEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.team.TeamName;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author jordancjq
public class ViewCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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

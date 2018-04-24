package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWN_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        HelpCommand command = new HelpCommand();
        assertCommandSuccess(command, SHOWN_HELP_MESSAGE);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_helpForTask_success() {
        HelpCommand command = new HelpCommand("task");
        assertCommandSuccess(command, TaskCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForEvent_success() {
        HelpCommand command = new HelpCommand("event");
        assertCommandSuccess(command, EventCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForRemove_success()    {
        HelpCommand command = new HelpCommand("remove");
        assertCommandSuccess(command, RemoveCommand.MESSAGE_USAGE);;

        command = new HelpCommand("rm");
        assertCommandSuccess(command, RemoveCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForComplete_success()    {
        HelpCommand command = new HelpCommand("complete");
        assertCommandSuccess(command, CompleteCommand.MESSAGE_USAGE);;
    }

    //@@author Kyomian
    @Test
    public void execute_helpForHelp_success() {
        HelpCommand command = new HelpCommand("help");
        assertCommandSuccess(command, HelpCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForMan_success() {
        HelpCommand command = new HelpCommand("man");
        assertCommandSuccess(command, HelpCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForList_success() {
        HelpCommand command = new HelpCommand("list");
        assertCommandSuccess(command, ListCommand.MESSAGE_USAGE);

        command = new HelpCommand("ls");
        assertCommandSuccess(command, ListCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForOverdue_success() {
        HelpCommand command = new HelpCommand("overdue");
        assertCommandSuccess(command, OverdueCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForClear_success() {
        HelpCommand command = new HelpCommand("clear");
        assertCommandSuccess(command, ClearCommand.MESSAGE_USAGE);

        command = new HelpCommand("c");
        assertCommandSuccess(command, ClearCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForUndo_success() {
        HelpCommand command = new HelpCommand("undo");
        assertCommandSuccess(command, UndoCommand.MESSAGE_USAGE);

        command = new HelpCommand("u");
        assertCommandSuccess(command, UndoCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForRedo_success() {
        HelpCommand command = new HelpCommand("redo");
        assertCommandSuccess(command, RedoCommand.MESSAGE_USAGE);

        command = new HelpCommand("r");
        assertCommandSuccess(command, RedoCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_invalidArgs_throwsCommandException()   {
        HelpCommand command = new HelpCommand("hello");
        assertCommandFailure(command, HelpCommand.MESSAGE_USAGE);
    }

}

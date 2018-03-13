package seedu.organizer.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import java.util.List;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.task.Task;

//@@author guekling
/**
 * Represents a find command with hidden internal logic and the ability to be executed for a {@code Command} of
 * type {@code T}.
 */
public abstract class FindCommandTest<T extends Command> {
    protected Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<Task>} is equal to {@code expectedList}<br>
     * - the {@code Organizer} in model remains the same after executing the {@code command}
     *
     * @throws CommandException If an error occurs during command execution.
     */
    protected void assertCommandSuccess(T command, String expectedMessage, List<Task> expectedList)
            throws CommandException {
        Organizer expectedOrganizer = new Organizer(model.getOrganizer());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredTaskList());
        assertEquals(expectedOrganizer, model.getOrganizer());
    }
}

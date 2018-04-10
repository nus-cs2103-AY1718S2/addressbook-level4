package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;

/**
 * @@author demitycho
 * Deletes a lesson identified using it's last displayed index from the address book.
 */
public class DeleteLessonCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson identified by the index number used in the last lesson listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";

    private final Index targetIndex;

    private Lesson lessonToDelete;

    public DeleteLessonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(lessonToDelete);

        try {
            model.deleteLesson(lessonToDelete);
        } catch (LessonNotFoundException lnfe) {
            throw new AssertionError("The target lesson cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS,  lessonToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Lesson> lessonList = model.getSchedule().getSchedule();

        if (targetIndex.getZeroBased() >= lessonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        lessonToDelete = lessonList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLessonCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteLessonCommand) other).targetIndex) // state check
                && Objects.equals(this.lessonToDelete, ((DeleteLessonCommand) other).lessonToDelete));
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Day;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.StudentNotFoundException;

/**
 * Adds a lesson to the schedule for a student in the address book.
 */
public class AddLessonCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addLesson";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the schedule, "
            + "for a student who is in the address book. "
            + "Parameters: "
            + "INDEX " + PREFIX_DAY + " DAY "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + " Monday "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_END_TIME + "10:30 ";

    public static final String MESSAGE_SUCCESS = "New lesson added for %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the schedule";

    private final Index index;
    private final Day day;
    private final Time startTime;
    private final Time endTime;
    private Student studentToAddLesson;
    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(Index index, Day day, Time startTime, Time endTime) {
        requireNonNull(index);
        requireNonNull(day);
        requireNonNull(startTime);
        requireNonNull(endTime);

        this.index = index;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     *
     * TODO add model.updateSchedule();
     * @throws CommandException
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addLesson(studentToAddLesson, day, startTime, endTime);
        } catch (DuplicateLessonException dle) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentToAddLesson.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToAddLesson = lastShownList.get(index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}

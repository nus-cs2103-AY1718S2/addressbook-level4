package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CheckIndexesUtil;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

//@@author yapni
/**
 * Remove a Student from favourites
 */
public class UnfavouriteCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove a student from favourites. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Student removed from favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

    private final Index targetStudentIndex;

    private Student targetStudent;
    private Student editedStudent;

    public UnfavouriteCommand(Index targetStudentIndex) {
        requireNonNull(targetStudentIndex);
        this.targetStudentIndex = targetStudentIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(targetStudent, editedStudent);

        try {
            model.updateStudent(targetStudent, editedStudent);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        } catch (DuplicateStudentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedStudent));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            setTargetStudent();
            createEditedStudent();
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates {@code editedStudent} which is a copy of {@code targetStudent}, but with the {@code favourite}
     * attribute set to false.
     */
    private void createEditedStudent() {
        assert targetStudent != null;
        editedStudent = new StudentBuilder(targetStudent).withFavourite(false).build();
    }

    /**
     * Sets the {@code targetStudent} of this command object
     * @throws IllegalValueException if the studentIndex is invalid
     */
    private void setTargetStudent() throws IllegalValueException {
        assert targetStudentIndex != null;

        List<Student> lastShownList = model.getFilteredStudentList();
        if (!CheckIndexesUtil.isStudentIndexValid(lastShownList, targetStudentIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        targetStudent = lastShownList.get(targetStudentIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavouriteCommand // instanceof handles null
                && ((UnfavouriteCommand) other).targetStudentIndex == this.targetStudentIndex);

    }
}

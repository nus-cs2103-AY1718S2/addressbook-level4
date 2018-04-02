package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

//@@author yapni
/**
 * Adds a Milestone to a Student's Dashboard
 */
public class AddMilestoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMS";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a milestone to a student's dashboard."
            + " Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX "
            + PREFIX_DATE + "DATE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_DATE + "17/05/2018 23:59 "
            + PREFIX_DESCRIPTION + "Learn Arrays";

    public static final String MESSAGE_DUPLICATE_MILESTONE = "Milestone already exists in the student's Dashboard";
    public static final String MESSAGE_SUCCESS = "Milestone added to Student's Dashboard: %1$s";

    private final Index studentIndex;
    private final Milestone newMilestone;

    private Student targetStudent;
    private Student editedStudent;

    public AddMilestoneCommand(Index index, Milestone newMilestone) {
        requireAllNonNull(index, newMilestone);

        this.newMilestone = newMilestone;
        this.studentIndex = index;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireAllNonNull(targetStudent, editedStudent);

        try {
            model.updateStudent(targetStudent, editedStudent);
        } catch (DuplicateStudentException e) {
            /* DuplicateStudentException caught will mean that the milestone list is the same as before */
            throw new AssertionError("New milestone cannot be missing");
        } catch (StudentNotFoundException e) {
            throw new AssertionError("The target student cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, newMilestone));
    }

    @Override
    public void preprocessUndoableCommand() throws CommandException {

        try {
            setTargetStudent();
            editedStudent = createEditedStudent(targetStudent, newMilestone);
        } catch (DuplicateMilestoneException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MILESTONE);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates and return a copy of {@code Student} with the new Milestone added to its Dashboard.
     */
    private Student createEditedStudent(Student studentToEdit, Milestone newMilestone)
            throws DuplicateMilestoneException {
        requireAllNonNull(studentToEdit, newMilestone);

        return new StudentBuilder(studentToEdit).withNewMilestone(newMilestone).build();
    }

    /**
     * Sets the {@code targetStudent} object
     * @throws IllegalValueException if the studentIndex is invalid
     */
    private void setTargetStudent() throws IllegalValueException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size() || studentIndex.getZeroBased() < 0) {
            throw new IllegalValueException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        targetStudent = lastShownList.get(studentIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMilestoneCommand // instanceof handles null
                && ((AddMilestoneCommand) other).studentIndex == this.studentIndex
                && ((AddMilestoneCommand) other).newMilestone == this.newMilestone);
    }
}

package seedu.address.logic.commands;

import java.util.List;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.StudentNotFoundException;

//@@author chweeee
/**
 * Finds and lists all students in address book whose name contains any of the argument keywords,
 * and deletes the first student of the list.
 * Keyword matching is case sensitive.
 */
public class FindAndDeleteCommand extends UndoableCommand {

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student to be deleted cannot be found.";

    private final NameContainsKeywordsPredicate predicate;

    private Student studentToDelete;

    public FindAndDeleteCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(studentToDelete);
        try {
            model.deleteStudent(studentToDelete);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS,  studentToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        //look for the student
        model.updateFilteredStudentList(predicate);
        if (model.getFilteredStudentList().size() == 0) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }
        studentToDelete = lastShownList.get(0);
    }
}
//@@author

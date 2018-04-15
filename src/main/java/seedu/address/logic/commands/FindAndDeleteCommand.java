package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
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
        } catch (LessonNotFoundException lnfe) {
            throw new AssertionError("The target student's lessons cannot be missing");
        } catch (InvalidLessonTimeSlotException iltse) {
            throw new AssertionError(
                    "Removing the target student's lessons cannot result in clashing lessons");
        } catch (DuplicateLessonException dle) {
            throw new AssertionError(
                    "Removing the target student cannot result in duplicate lessons");

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

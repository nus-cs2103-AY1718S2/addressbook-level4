package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Favourite;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Add a student to favourites
 */
public class FavouriteCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a student to favourites. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Student added to favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

    private final Index targetIndex;

    private Student studentToFavourite;
    private Student editedStudent;

    public FavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(studentToFavourite, editedStudent);

        try {
            model.updateStudent(studentToFavourite, editedStudent);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        } catch (DuplicateStudentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedStudent));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToFavourite = lastShownList.get(targetIndex.getZeroBased());
        editedStudent = createEditedStudent(studentToFavourite);
    }

    /**
     * Create and return a copy of the target {@Code Student} to favourite with its' Favourite attribute set to true.
     */
    private static Student createEditedStudent(Student target) {
        assert target != null;

        Name name = target.getName();
        Phone phone = target.getPhone();
        Email email = target.getEmail();
        Address address = target.getAddress();
        Set<Tag> tags = target.getTags();
        ProgrammingLanguage programmingLanguage = target.getProgrammingLanguage();
        Favourite fav = new Favourite(true);
        Dashboard dashboard = target.getDashboard();

        return new Student(name, phone, email, address, programmingLanguage, tags, fav, dashboard);
    }
}

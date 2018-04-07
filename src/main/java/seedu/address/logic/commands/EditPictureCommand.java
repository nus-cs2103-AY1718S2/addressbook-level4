package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE_PATH;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

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
import seedu.address.model.student.UniqueKey;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;
import seedu.address.model.tag.Tag;

/**
 * Edits the profile picture of an existing student in the address book.
 */
public class EditPictureCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editPicture";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the current profile picture of a student "
            + "identified by the index number used in the last student listing. "
            + "Pictures must have file extensions ending in '.jpg' or '.png'.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + PREFIX_PICTURE_PATH + "URL OF NEW PICTURE.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_INDEX + "1 "
            + PREFIX_INDEX + "C:\\example.jpg";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited profile picture of Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Error in editing profile picture.";
    private final Index index;
    private final ProfilePicturePath newProfilePicturePath;

    private Student studentToEditPicture;
    private Student pictureEditedStudent;
    private Student finalPictureEditedStudent;

    public EditPictureCommand(Index index, ProfilePicturePath profilePicturePath) {
        requireNonNull(index);
        requireNonNull(profilePicturePath);

        this.index = index;
        this.newProfilePicturePath = profilePicturePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateProfilePicture(studentToEditPicture, pictureEditedStudent, finalPictureEditedStudent);
        } catch (DuplicateStudentException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        }
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, pictureEditedStudent));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToEditPicture = lastShownList.get(index.getZeroBased());
        pictureEditedStudent = createPictureEditedStudent(studentToEditPicture);
        finalPictureEditedStudent = createFinalEditedStudent(pictureEditedStudent);

    }
    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with the new {@code profilePicturePath}.
     */
    private Student createPictureEditedStudent(Student studentToEditPicture) {
        Name name = studentToEditPicture.getName();
        UniqueKey uniqueKey = studentToEditPicture.getUniqueKey();
        Phone phone = studentToEditPicture.getPhone();
        Email email = studentToEditPicture.getEmail();
        Address address = studentToEditPicture.getAddress();
        ProgrammingLanguage programmingLanguage = studentToEditPicture.getProgrammingLanguage();
        Set<Tag> tags = studentToEditPicture.getTags();
        Favourite isFavourite = studentToEditPicture.getFavourite();
        Dashboard dashboard = studentToEditPicture.getDashboard();
        ProfilePicturePath profilePicturePath = this.newProfilePicturePath;

        return new Student(uniqueKey, name, phone, email, address, programmingLanguage,
                tags, isFavourite, dashboard, profilePicturePath);
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with the designated path for the profile picture in addressbook.
     */
    private Student createFinalEditedStudent(Student studentToEdit) {
        Name name = studentToEdit.getName();
        UniqueKey uniqueKey = studentToEdit.getUniqueKey();
        Phone phone = studentToEdit.getPhone();
        Email email = studentToEdit.getEmail();
        Address address = studentToEdit.getAddress();
        ProgrammingLanguage programmingLanguage = studentToEdit.getProgrammingLanguage();
        Set<Tag> tags = studentToEdit.getTags();
        Favourite isFavourite = studentToEdit.getFavourite();
        Dashboard dashboard = studentToEdit.getDashboard();
        ProfilePicturePath profilePicturePath = new ProfilePicturePath("/data/profilePictures/"
                    + uniqueKey.toString() + this.newProfilePicturePath.getExtension());


        return new Student(uniqueKey, name, phone, email, address, programmingLanguage,
                tags, isFavourite, dashboard, profilePicturePath);
    }


}

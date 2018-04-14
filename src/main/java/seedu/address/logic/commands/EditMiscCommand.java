package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTOFKINNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTOFKINPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.MiscellaneousInfo;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;
import seedu.address.model.student.miscellaneousinfo.Remarks;
import seedu.address.model.tag.Tag;

//@@author samuelloh
/**
 * Edits the miscellaneous info of an existing student in the address book.
 */
public class EditMiscCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editMisc";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the miscellaneous"
            + "information of the student identified "
            + "by the index number used in the last student listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ALLERGIES + "ALLERGIES] "
            + "[" + PREFIX_NEXTOFKINPHONE + "PHONE] "
            + "[" + PREFIX_NEXTOFKINNAME + "EMAIL] "
            + "[" + PREFIX_REMARKS + "REMARKS] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NEXTOFKINPHONE + "91234567 "
            + PREFIX_ALLERGIES + "Nuts";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";


    private final Index index;
    private final EditMiscDescriptor editMiscDescriptor;

    private Student studentToEdit;
    private Student editedStudent;

    /**
     * @param index of the student in the filtered list to edit
     * @param editMiscDescriptor details to edit the student with
     */
    public EditMiscCommand(Index index, EditMiscDescriptor editMiscDescriptor) {
        requireNonNull(index);
        requireNonNull(editMiscDescriptor);

        this.index = index;
        this.editMiscDescriptor = new EditMiscDescriptor(editMiscDescriptor);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToEdit = lastShownList.get(index.getZeroBased());
        editedStudent = createEditedStudent(studentToEdit, editMiscDescriptor);
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateStudent(studentToEdit, editedStudent);
        } catch (DuplicateStudentException duplicateStudentError) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        } catch (StudentNotFoundException studentNotFoundError) {
            throw new AssertionError("The target student cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent));
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editStudentDescriptor}.
     */
    private static Student createEditedStudent(Student studentToEdit, EditMiscDescriptor editMiscDescriptor) {
        assert studentToEdit != null;

        UniqueKey uniqueKey = studentToEdit.getUniqueKey();
        Name name = studentToEdit.getName();
        Phone phone = studentToEdit.getPhone();
        Email email = studentToEdit.getEmail();
        Address address = studentToEdit.getAddress();
        ProgrammingLanguage programmingLanguage = studentToEdit.getProgrammingLanguage();
        Set<Tag> tags = studentToEdit.getTags();
        Favourite isFavourite = studentToEdit.getFavourite();
        Dashboard dashboard = studentToEdit.getDashboard();
        ProfilePicturePath profilePicturePath = studentToEdit.getProfilePicturePath();

        Allergies allergies = editMiscDescriptor.getAllergies()
                .orElse(studentToEdit.getMiscellaneousInfo().getAllergies());
        NextOfKinName nextOfKinName = editMiscDescriptor.getNextOfKinName()
                .orElse(studentToEdit.getMiscellaneousInfo().getNextOfKinName());
        NextOfKinPhone nextOfKinPhone = editMiscDescriptor.getNextOfKinPhone()
                .orElse(studentToEdit.getMiscellaneousInfo().getNextOfKinPhone());
        Remarks remarks = editMiscDescriptor.getRemarks()
                .orElse(studentToEdit.getMiscellaneousInfo().getRemarks());

        MiscellaneousInfo miscellaneousInfo = new MiscellaneousInfo(allergies, nextOfKinName, nextOfKinPhone, remarks);

        return new Student(uniqueKey, name, phone, email, address,
                programmingLanguage, tags, isFavourite, dashboard, profilePicturePath, miscellaneousInfo);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMiscCommand)) {
            return false;
        }

        // state check
        EditMiscCommand e = (EditMiscCommand) other;
        return index.equals(e.index)
                && editMiscDescriptor.equals(e.editMiscDescriptor)
                && Objects.equals(studentToEdit, e.studentToEdit);
    }


    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditMiscDescriptor {
        private Allergies allergies;
        private NextOfKinName nextOfKinName;
        private NextOfKinPhone nextOfKinPhone;
        private Remarks remarks;

        public EditMiscDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditMiscDescriptor(EditMiscDescriptor toCopy) {
            setAllergies(toCopy.allergies);
            setNextOfKinName(toCopy.nextOfKinName);
            setNextOfKinPhone(toCopy.nextOfKinPhone);
            setRemarks(toCopy.remarks);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.allergies, this.nextOfKinName, this.nextOfKinPhone, this.remarks);
        }

        public void setAllergies(Allergies allergies) {
            this.allergies = allergies;
        }

        public Optional<Allergies> getAllergies() {
            return Optional.ofNullable(allergies);
        }

        public void setNextOfKinName(NextOfKinName nextOfKinName) {
            this.nextOfKinName = nextOfKinName;
        }

        public Optional<NextOfKinName> getNextOfKinName() {
            return Optional.ofNullable(nextOfKinName);
        }

        public void setNextOfKinPhone(NextOfKinPhone nextOfKinPhone) {
            this.nextOfKinPhone = nextOfKinPhone;
        }

        public Optional<NextOfKinPhone> getNextOfKinPhone() {
            return Optional.ofNullable(nextOfKinPhone);
        }

        public void setRemarks(Remarks remarks) {
            this.remarks = remarks;
        }

        public Optional<Remarks> getRemarks() {
            return Optional.ofNullable(remarks);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMiscDescriptor)) {
                return false;
            }

            // state check
            EditMiscDescriptor e = (EditMiscDescriptor) other;

            return getAllergies().equals(e.getAllergies())
                    && getNextOfKinName().equals(e.getNextOfKinName())
                    && getNextOfKinPhone().equals(e.getNextOfKinPhone())
                    && getRemarks().equals(e.getRemarks());
        }
    }
}
//@@ author

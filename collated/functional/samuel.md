# samuel
###### \java\seedu\address\logic\commands\EditMiscCommand.java
``` java
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
```
###### \java\seedu\address\logic\commands\EditPictureCommand.java
``` java
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
```
###### \java\seedu\address\model\student\miscellaneousinfo\MiscellaneousInfo.java
``` java
/**
 * Represents a Student's miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidMiscellaneousInfo(MiscellaneousInfo)}
 */
public class MiscellaneousInfo {

    public static final String MESSAGE_MISCELLANEOUS_CONSTRAINTS =
            "Miscellaneous information should take in values according to the constraints of each of its individual"
                    + "components";

    private final Allergies allergies;
    private final NextOfKinName nextOfKinName;
    private final NextOfKinPhone nextOfKinPhone;
    private final Remarks remarks;

    /**
     * Constructs a {@code MiscellaneousInfo} object with initial default values
     */
    public MiscellaneousInfo() {
        this.allergies = new Allergies();
        this.nextOfKinName = new NextOfKinName();
        this.nextOfKinPhone = new NextOfKinPhone();
        this.remarks = new Remarks();
    }

    public MiscellaneousInfo(Allergies allergies, NextOfKinName nextOfKinName,
                             NextOfKinPhone nextOfKinPhone, Remarks remarks) {
        this.allergies = allergies;
        this.nextOfKinName = nextOfKinName;
        this.nextOfKinPhone = nextOfKinPhone;
        this.remarks = remarks;

    }

    /**
     * Returns true if a given string is a valid student allergies.
     */
    public static boolean isValidMiscellaneousInfo(MiscellaneousInfo test) {
        return isValidRemarks(test.remarks.toString()) && isValidNextOfKinPhone(test.nextOfKinPhone.toString())
                && isValidNextOfKinName(test.nextOfKinName.toString()) && isValidAllergies(test.allergies.toString());
    }

    @Override
    public String toString() {
        return "Allergies: " + allergies.toString() +  " "
                + "Next of kin name: " + nextOfKinName.toString() + " "
                + "Next of kin phone: " + nextOfKinPhone.toString() + " "
                + "Remarks: " + remarks.toString() + " ";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MiscellaneousInfo)) {
            return false;
        }

        MiscellaneousInfo otherMiscInfo = (MiscellaneousInfo) other;
        return otherMiscInfo.allergies.equals(this.allergies)
                && otherMiscInfo.nextOfKinPhone.equals(this.nextOfKinPhone)
                && otherMiscInfo.nextOfKinName.equals(this.nextOfKinName)
                && otherMiscInfo.remarks.equals(this.remarks);
    }

    public Allergies getAllergies() {
        return allergies;
    }

    public NextOfKinName getNextOfKinName() {
        return nextOfKinName;
    }

    public NextOfKinPhone getNextOfKinPhone() {
        return nextOfKinPhone;
    }

    public Remarks getRemarks() {
        return remarks;
    }
}
```
###### \java\seedu\address\model\student\miscellaneousinfo\NextOfKinPhone.java
``` java
/**
 * Represents a Student's next of kin phone number component of his/her miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class NextOfKinPhone {


    public static final String MESSAGE_NEXTOFKINPHONE_CONSTRAINTS =
            "Next of kin phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String NEXTOFKINPHONE_VALIDATION_REGEX = "\\d{3,}";

    private final String value;

    /**
     * Construct a {@code NextOfKinPhone} with initial default value
     */
    public NextOfKinPhone() {
        value = "000";
    }
    /**
     * Constructs a {@code NextOFKinPhone}.
     *
     * @param nextOfKinPhone A valid next of kin phone number.
     */
    public NextOfKinPhone(String nextOfKinPhone) {
        requireNonNull(nextOfKinPhone);
        checkArgument(isValidNextOfKinPhone(nextOfKinPhone), MESSAGE_NEXTOFKINPHONE_CONSTRAINTS);
        this.value = nextOfKinPhone;
    }

    /**
     * Returns true if a given string is a valid student allergies.
     */
    public static boolean isValidNextOfKinPhone(String test) {
        return test.matches(NEXTOFKINPHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NextOfKinPhone // instanceof handles nulls
                && this.value.equals(((NextOfKinPhone) other).value)); // state check
    }
}
//author
```
###### \java\seedu\address\model\student\miscellaneousinfo\ProfilePicturePath.java
``` java
/**
 * Represents a Student's profile picture's pathname in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class ProfilePicturePath {

    public static final String MESSAGE_PICTURE_CONSTRAINTS =
            "File URL must exist and have extensions of '.jpg' or '.png' only.";
    public static final String DEFAULT_PROFILE_PICTURE =
            "profile_photo_placeholder.png";


    public final Path profilePicturePath;

    public ProfilePicturePath(String filePath) {
        requireNonNull(filePath);
        profilePicturePath = Paths.get(filePath);
    }

    /**
     * Checks if file extension is either 'jpg' or 'png'
     *
     * @param filePath
     * @return True if extensions are as above. False if otherwise
     */
    public static boolean checkPictureExtension(String filePath) {
        String extension;

        if (filePath.lastIndexOf(".") != -1 && filePath.lastIndexOf(".") != 0) {
            extension = filePath.substring(filePath.lastIndexOf(".") + 1);
            return extension.equals("jpg") || extension.equals("png");
        }

        return false;

    }

    /**
     * Returns true if a given string is a valid file path with extensions either '.jpg' or '.png'.
     */
    public static boolean isValidPath(String test) {
        File testFile = new File(test);
        if (!testFile.exists()) {
            return false;
        }
        return ProfilePicturePath.checkPictureExtension(testFile.getPath());
    }

    public Path getProfilePicturePath() {
        return profilePicturePath;
    }

    /**
     * Returns the extension of the profile picture path.
     */
    public String getExtension() {
        int extensionSeparator = profilePicturePath.toString().lastIndexOf(".");
        return profilePicturePath.toString().substring(extensionSeparator);
    }

    @Override
    public String toString() {
        return profilePicturePath.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePicturePath // instanceof handles nulls
                && this.profilePicturePath.equals(((ProfilePicturePath) other)
                .profilePicturePath)); // state check
    }
}
```
###### \java\seedu\address\model\student\miscellaneousinfo\Remarks.java
``` java

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's remarks component of his/her miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemarks(String)}
 */
public class Remarks {

    public static final String MESSAGE_REMARKS_CONSTRAINTS =
            "Student remarks can take any values, and it should not be blank";
    /*
     * The first character of the remarks must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REMARKS_VALIDATION_REGEX = "[^\\s].*";

    private final String value;

    /**
     * Construct a {@code Remarks} instance with initial default value.
     */
    public Remarks() {
        value = "No remarks";
    }

    /**
     * Constructs a {@code Remarks} instance .
     *
     * @param remarks A valid string of remarks.
     */
    public Remarks(String remarks) {
        requireNonNull(remarks);
        checkArgument(isValidRemarks(remarks), MESSAGE_REMARKS_CONSTRAINTS);
        this.value = remarks;
    }

    /**
     * Returns true if a given string is a valid student allergies.
     */
    public static boolean isValidRemarks(String test) {
        return test.matches(REMARKS_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remarks // instanceof handles nulls
                && this.value.equals(((Remarks) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
//@@ author samuel
```

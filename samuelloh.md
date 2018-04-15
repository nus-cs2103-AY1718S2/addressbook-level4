# samuelloh
###### \build\resources\main\view\StudentInfoTheme.css
``` css
*/

html {
	height: 100%;
}

body {
	height: 100%;
}

.background {
    background-color: white;
}

#name {
	text-align: center;
	font-size: 500%;
	margin: 0 auto;
	border: 1%;
	padding: 1%;
	word-wrap: break-word;
	font-family: Arial;
}
#profilePicture {
	margin-left: 6%;
	width: 18%;
	height: 40%;
	float:left;
}

.infoPartOne {
	margin-right : 7%;
	border-style: solid;
	border-width: thick;
	width : 65%;
	float: right;
	padding: 1%;
}

.divider {
	clear: both;
	margin-bottom: 0.5%;
	padding-bottom: 0.5%;
}

.infoPartTwo {
	margin-left: 6%;
	border-style: solid;
	border-width: thick;
	width : 85%;
	padding: 1%;
}

#phone, #email, #address, #programmingLanguage, #tags, #allergies, #nextOfKinName, #nextOfKinPhone, #remarks {
	font-size: 175%;
	word-wrap: break-word;
	font-family: Arial;
	line-height: 20px;
}
/**
```
###### \build\resources\main\view\StudentMiscInfo.html
``` html
function test(){
	document.write(location.href);
}

function getIndex(){

	var xmlhttp = new XMLHttpRequest

	xmlhttp.open("GET", "../requiredStudentIndex.xml", false);
	xmlhttp.send();

	var xmlDoc = xmlhttp.responseXML;

	var index = xmlDoc.getElementsByTagName("requiredStudentIndex")[0].getElementsByTagName("requiredStudentIndex")[0].childNodes[0].nodeValue;

	return index;
}

function loadAddressBook() {
	var xmlhttp = new XMLHttpRequest();

	xmlhttp.open("GET",  "../addressbook.xml", false);
	xmlhttp.send();

	var index = getIndex();

	var xmlDoc = xmlhttp.responseXML;

// -------------------------- Reading Student Info Part One--------------------------

	var studentInfo = xmlDoc.getElementsByTagName("students");

	var name = "<b>" + studentInfo[index].getElementsByTagName("name")[0].childNodes[0].nodeValue + "</b>"
	document.getElementById("name").innerHTML = name;

	var phone = "<b>Contact</b> : " + studentInfo[index].getElementsByTagName("phone")[0].childNodes[0].nodeValue;
	document.getElementById("phone").innerHTML = phone;

	var email = "<b>Email</b> : " + studentInfo[index].getElementsByTagName("email")[0].childNodes[0].nodeValue;
	document.getElementById("email").innerHTML = email;

	var address = "<b>Address</b>: " + studentInfo[index].getElementsByTagName("address")[0].childNodes[0].nodeValue;
	document.getElementById("address").innerHTML = address;

	var programmingLanguage = "<b>Prgoramming Language</b> : " + studentInfo[index].getElementsByTagName("programmingLanguage")[0].childNodes[0].nodeValue;
	document.getElementById("programmingLanguage").innerHTML = programmingLanguage;



	var tags = studentInfo[index].getElementsByTagName("tagged");
	var showTags = "<b>Tags</b> : ";
	for (i = 0; i < tags.length; i++) {
		showTags += "[";
		if(i%2){
		showTags += "<span style='color:blue'>";
		showTags += tags[i].childNodes[0].nodeValue;
		showTags += "</span>";
		}
		else{
		showTags += "<span style='color:maroon'>";
		showTags += tags[i].childNodes[0].nodeValue;
		showTags += "</span>";
		}
		showTags += "]";

	}
	document.getElementById("tags").innerHTML = showTags;

// -------------------------- Reading profile picture --------------------------

	var profilePicturePath = studentInfo[index].getElementsByTagName("profilePicturePath")[0].childNodes[0].nodeValue;
	var imageSourcePart1;

	if (profilePicturePath == "data\\view\\profile_photo_placeholder.png") {
		var profilePicturePath = profilePicturePath.substring(10);
		imageSourcePart1 = "<img src='";
	} else {
		imageSourcePart1 = "<img src='../../";
	}

	var imageSourcePart2 = "' style='width:100%; height:100%'>";
	var finalImageSource = imageSourcePart1 + profilePicturePath + "?" + new Date().getTime() + imageSourcePart2;
	document.getElementById("profilePicture").innerHTML = finalImageSource;

// -------------------------- Reading Student Info Part Two--------------------------

	var miscInfo = studentInfo[index].getElementsByTagName("miscellaneousInfo");

	var allergies = "<b>Alleriges</b> : " + miscInfo[0].getElementsByTagName("allergies")[0].childNodes[0].nodeValue;
	document.getElementById("allergies").innerHTML = allergies;

	var nextOfKinName = "<b>Next Of Kin Name</b> : " + miscInfo[0].getElementsByTagName("nextOfKinName")[0].childNodes[0].nodeValue;
	document.getElementById("nextOfKinName").innerHTML = nextOfKinName;

	var nextOfKinPhone = "<b>Next Of Kin Phone</b> : " + miscInfo[0].getElementsByTagName("nextOfKinPhone")[0].childNodes[0].nodeValue;
	document.getElementById("nextOfKinPhone").innerHTML = nextOfKinPhone;

	var remarks = "<b>Remarks</b> : " + miscInfo[0].getElementsByTagName("remarks")[0].childNodes[0].nodeValue;
	document.getElementById("remarks").innerHTML = remarks;

	}
```
###### \data\view\StudentMiscInfo.html
``` html
function test(){
	document.write(location.href);
}

function getIndex(){

	var xmlhttp = new XMLHttpRequest

	xmlhttp.open("GET", "../requiredStudentIndex.xml", false);
	xmlhttp.send();

	var xmlDoc = xmlhttp.responseXML;

	var index = xmlDoc.getElementsByTagName("requiredStudentIndex")[0].getElementsByTagName("requiredStudentIndex")[0].childNodes[0].nodeValue;

	return index;
}

function loadAddressBook() {
	var xmlhttp = new XMLHttpRequest();

	xmlhttp.open("GET",  "../addressbook.xml", false);
	xmlhttp.send();

	var index = getIndex();

	var xmlDoc = xmlhttp.responseXML;

// -------------------------- Reading Student Info Part One--------------------------

	var studentInfo = xmlDoc.getElementsByTagName("students");

	var name = "<b>" + studentInfo[index].getElementsByTagName("name")[0].childNodes[0].nodeValue + "</b>"
	document.getElementById("name").innerHTML = name;

	var phone = "<b>Contact</b> : " + studentInfo[index].getElementsByTagName("phone")[0].childNodes[0].nodeValue;
	document.getElementById("phone").innerHTML = phone;

	var email = "<b>Email</b> : " + studentInfo[index].getElementsByTagName("email")[0].childNodes[0].nodeValue;
	document.getElementById("email").innerHTML = email;

	var address = "<b>Address</b>: " + studentInfo[index].getElementsByTagName("address")[0].childNodes[0].nodeValue;
	document.getElementById("address").innerHTML = address;

	var programmingLanguage = "<b>Prgoramming Language</b> : " + studentInfo[index].getElementsByTagName("programmingLanguage")[0].childNodes[0].nodeValue;
	document.getElementById("programmingLanguage").innerHTML = programmingLanguage;



	var tags = studentInfo[index].getElementsByTagName("tagged");
	var showTags = "<b>Tags</b> : ";
	for (i = 0; i < tags.length; i++) {
		showTags += "[";
		if(i%2){
		showTags += "<span style='color:blue'>";
		showTags += tags[i].childNodes[0].nodeValue;
		showTags += "</span>";
		}
		else{
		showTags += "<span style='color:maroon'>";
		showTags += tags[i].childNodes[0].nodeValue;
		showTags += "</span>";
		}
		showTags += "]";

	}
	document.getElementById("tags").innerHTML = showTags;

// -------------------------- Reading profile picture --------------------------

	var profilePicturePath = studentInfo[index].getElementsByTagName("profilePicturePath")[0].childNodes[0].nodeValue;
	var imageSourcePart1;

	if (profilePicturePath == "data\\view\\profile_photo_placeholder.png") {
		var profilePicturePath = profilePicturePath.substring(10);
		imageSourcePart1 = "<img src='";
	} else {
		imageSourcePart1 = "<img src='../../";
	}

	var imageSourcePart2 = "' style='width:100%; height:100%'>";
	var finalImageSource = imageSourcePart1 + profilePicturePath + "?" + new Date().getTime() + imageSourcePart2;
	document.getElementById("profilePicture").innerHTML = finalImageSource;

// -------------------------- Reading Student Info Part Two--------------------------

	var miscInfo = studentInfo[index].getElementsByTagName("miscellaneousInfo");

	var allergies = "<b>Alleriges</b> : " + miscInfo[0].getElementsByTagName("allergies")[0].childNodes[0].nodeValue;
	document.getElementById("allergies").innerHTML = allergies;

	var nextOfKinName = "<b>Next Of Kin Name</b> : " + miscInfo[0].getElementsByTagName("nextOfKinName")[0].childNodes[0].nodeValue;
	document.getElementById("nextOfKinName").innerHTML = nextOfKinName;

	var nextOfKinPhone = "<b>Next Of Kin Phone</b> : " + miscInfo[0].getElementsByTagName("nextOfKinPhone")[0].childNodes[0].nodeValue;
	document.getElementById("nextOfKinPhone").innerHTML = nextOfKinPhone;

	var remarks = "<b>Remarks</b> : " + miscInfo[0].getElementsByTagName("remarks")[0].childNodes[0].nodeValue;
	document.getElementById("remarks").innerHTML = remarks;

	}
```
###### \out\production\classes\view\StudentInfoTheme.css
``` css
*/

html {
	height: 100%;
}

body {
	height: 100%;
}

.background {
    background-color: white;
}

#name {
	text-align: center;
	font-size: 500%;
	margin: 0 auto;
	border: 1%;
	padding: 1%;
	word-wrap: break-word;
	font-family: Arial;
}
#profilePicture {
	margin-left: 6%;
	width: 18%;
	height: 40%;
	float:left;
}

.infoPartOne {
	margin-right : 7%;
	border-style: solid;
	border-width: thick;
	width : 65%;
	float: right;
	padding: 1%;
}

.divider {
	clear: both;
	margin-bottom: 0.5%;
	padding-bottom: 0.5%;
}

.infoPartTwo {
	margin-left: 6%;
	border-style: solid;
	border-width: thick;
	width : 85%;
	padding: 1%;
}

#phone, #email, #address, #programmingLanguage, #tags, #allergies, #nextOfKinName, #nextOfKinPhone, #remarks {
	font-size: 175%;
	word-wrap: break-word;
	font-family: Arial;
	line-height: 20px;
}
/**
```
###### \out\production\classes\view\StudentMiscInfo.html
``` html
function test(){
	document.write(location.href);
}

function getIndex(){

	var xmlhttp = new XMLHttpRequest

	xmlhttp.open("GET", "../requiredStudentIndex.xml", false);
	xmlhttp.send();

	var xmlDoc = xmlhttp.responseXML;

	var index = xmlDoc.getElementsByTagName("requiredStudentIndex")[0].getElementsByTagName("requiredStudentIndex")[0].childNodes[0].nodeValue;

	return index;
}

function loadAddressBook() {
	var xmlhttp = new XMLHttpRequest();

	xmlhttp.open("GET",  "../addressbook.xml", false);
	xmlhttp.send();

	var index = getIndex();

	var xmlDoc = xmlhttp.responseXML;

// -------------------------- Reading Student Info Part One--------------------------

	var studentInfo = xmlDoc.getElementsByTagName("students");

	var name = "<b>" + studentInfo[index].getElementsByTagName("name")[0].childNodes[0].nodeValue + "</b>"
	document.getElementById("name").innerHTML = name;

	var phone = "<b>Contact</b> : " + studentInfo[index].getElementsByTagName("phone")[0].childNodes[0].nodeValue;
	document.getElementById("phone").innerHTML = phone;

	var email = "<b>Email</b> : " + studentInfo[index].getElementsByTagName("email")[0].childNodes[0].nodeValue;
	document.getElementById("email").innerHTML = email;

	var address = "<b>Address</b>: " + studentInfo[index].getElementsByTagName("address")[0].childNodes[0].nodeValue;
	document.getElementById("address").innerHTML = address;

	var programmingLanguage = "<b>Prgoramming Language</b> : " + studentInfo[index].getElementsByTagName("programmingLanguage")[0].childNodes[0].nodeValue;
	document.getElementById("programmingLanguage").innerHTML = programmingLanguage;



	var tags = studentInfo[index].getElementsByTagName("tagged");
	var showTags = "<b>Tags</b> : ";
	for (i = 0; i < tags.length; i++) {
		showTags += "[";
		if(i%2){
		showTags += "<span style='color:blue'>";
		showTags += tags[i].childNodes[0].nodeValue;
		showTags += "</span>";
		}
		else{
		showTags += "<span style='color:maroon'>";
		showTags += tags[i].childNodes[0].nodeValue;
		showTags += "</span>";
		}
		showTags += "]";

	}
	document.getElementById("tags").innerHTML = showTags;

// -------------------------- Reading profile picture --------------------------

	var profilePicturePath = studentInfo[index].getElementsByTagName("profilePicturePath")[0].childNodes[0].nodeValue;
	var imageSourcePart1;

	if (profilePicturePath == "data\\view\\profile_photo_placeholder.png") {
		var profilePicturePath = profilePicturePath.substring(10);
		imageSourcePart1 = "<img src='";
	} else {
		imageSourcePart1 = "<img src='../../";
	}

	var imageSourcePart2 = "' style='width:100%; height:100%'>";
	var finalImageSource = imageSourcePart1 + profilePicturePath + "?" + new Date().getTime() + imageSourcePart2;
	document.getElementById("profilePicture").innerHTML = finalImageSource;

// -------------------------- Reading Student Info Part Two--------------------------

	var miscInfo = studentInfo[index].getElementsByTagName("miscellaneousInfo");

	var allergies = "<b>Alleriges</b> : " + miscInfo[0].getElementsByTagName("allergies")[0].childNodes[0].nodeValue;
	document.getElementById("allergies").innerHTML = allergies;

	var nextOfKinName = "<b>Next Of Kin Name</b> : " + miscInfo[0].getElementsByTagName("nextOfKinName")[0].childNodes[0].nodeValue;
	document.getElementById("nextOfKinName").innerHTML = nextOfKinName;

	var nextOfKinPhone = "<b>Next Of Kin Phone</b> : " + miscInfo[0].getElementsByTagName("nextOfKinPhone")[0].childNodes[0].nodeValue;
	document.getElementById("nextOfKinPhone").innerHTML = nextOfKinPhone;

	var remarks = "<b>Remarks</b> : " + miscInfo[0].getElementsByTagName("remarks")[0].childNodes[0].nodeValue;
	document.getElementById("remarks").innerHTML = remarks;

	}
```
###### \src\main\java\seedu\address\commons\events\model\StudentInfoChangedEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a paticular student's info has changed
 */
public class StudentInfoChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \src\main\java\seedu\address\commons\events\model\StudentInfoDisplayEvent.java
``` java
/**
 *Indicates that a particular student's full info is to be displayed
 */

public class StudentInfoDisplayEvent extends BaseEvent {

    private final Student student;


    public StudentInfoDisplayEvent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Displaying full information for " + student.getName();
    }

    public Student getStudent() {
        return student;
    }

}
```
###### \src\main\java\seedu\address\commons\events\storage\ProfilePictureChangeEvent.java
``` java
/**
 * Indicates a change in the profile picture of the student.
 */
public class ProfilePictureChangeEvent extends BaseEvent {

    private final Student student;
    private final ProfilePicturePath urlToChangeTo;
    private final UniqueKey uniqueKey;

    public ProfilePictureChangeEvent(Student student) {
        this.student = student;
        this.urlToChangeTo = student.getProfilePicturePath();
        this.uniqueKey = student.getUniqueKey();
    }

    public ProfilePicturePath getUrlToChangeTo() {
        return urlToChangeTo;
    }

    public UniqueKey getUniqueKey() {
        return uniqueKey;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "Changing Url of profile picture for student: " + student.toString();
    }
}
```
###### \src\main\java\seedu\address\commons\events\storage\RequiredStudentIndexChangeEvent.java
``` java
/**
 * Indicates a change in the index of the student whose full info is to be displayed.
 */
public class RequiredStudentIndexChangeEvent extends BaseEvent {

    private final int newIndex;

    public RequiredStudentIndexChangeEvent(int newIndex) {
        this.newIndex = newIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }

    @Override
    public String toString() {
        return "New index to be changed : " + newIndex;
    }
}

```
###### \src\main\java\seedu\address\commons\events\ui\ShowStudentProfileEvent.java
``` java
/**
 * Represents a browser display event when a student's profile page is required to be shown
 */
public class ShowStudentProfileEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \src\main\java\seedu\address\logic\commands\EditMiscCommand.java
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
```
###### \src\main\java\seedu\address\logic\commands\EditPictureCommand.java
``` java
public class EditPictureCommand extends Command {

    public static final String COMMAND_WORD = "editPicture";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the current profile picture of a student "
            + "identified by the index number used in the last student listing. "
            + "Pictures must have file extensions ending in '.jpg' or '.png'.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + PREFIX_PICTURE_PATH + "URL OF NEW PICTURE.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_INDEX + "1 "
            + PREFIX_PICTURE_PATH + "C:\\example.jpg";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited profile picture of Student: %1$s";
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
    public CommandResult execute() throws CommandException {

        preProcessCommand();

        try {
            model.updateProfilePicture(studentToEditPicture, pictureEditedStudent, finalPictureEditedStudent);
        } catch (DuplicateStudentException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        } catch (StudentNotFoundException pnfe) {
            throw new AssertionError("The target student cannot be missing");
        }
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, finalPictureEditedStudent));
    }

    /**
     * Preprocesses the {@code EditPictureCommand} before executing it.
     * @throws CommandException
     */
    protected void preProcessCommand() throws CommandException {
        studentToEditPicture = getStudentToEdit();
        pictureEditedStudent = createPictureEditedStudent(studentToEditPicture);
        finalPictureEditedStudent = createFinalEditedStudent(pictureEditedStudent);
    }

    /**
     * Gets the student with the required index from the last shown student list.
     */
    private Student getStudentToEdit() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        studentToEditPicture = lastShownList.get(index.getZeroBased());
        return studentToEditPicture;
    }
    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with the new picture from {@code profilePicturePath}.
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

        MiscellaneousInfo miscellaneousInfo = studentToEditPicture.getMiscellaneousInfo();

        return new Student(uniqueKey, name, phone, email, address, programmingLanguage,
                tags, isFavourite, dashboard, profilePicturePath, miscellaneousInfo);
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with the designated path for the profile picture in the data storage.
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
        ProfilePicturePath profilePicturePath = new ProfilePicturePath("data/profilePictures/"
                    + uniqueKey.toString() + this.newProfilePicturePath.getExtension());

        MiscellaneousInfo miscellaneousInfo = studentToEdit.getMiscellaneousInfo();

        return new Student(uniqueKey, name, phone, email, address, programmingLanguage,
                tags, isFavourite, dashboard, profilePicturePath, miscellaneousInfo);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPictureCommand)) {
            return false;
        }

        // state check
        EditPictureCommand e = (EditPictureCommand) other;
        return index.equals(e.index)
                && newProfilePicturePath.equals(e.newProfilePicturePath)
                && Objects.equals(studentToEditPicture, e.studentToEditPicture);
    }

}
```
###### \src\main\java\seedu\address\logic\commands\MoreInfoCommand.java
``` java
/**
 * Displays the full information of a student on the browser panel.
 */
public class MoreInfoCommand extends Command {

    public static final String COMMAND_WORD = "moreInfo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the full information and particulars of "
            + "a student identified by the index number used"
            + " in the last student listing. Also includes his/her profile picture(if one exists).\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_MOREINFO_STUDENT_SUCCESS = "Displayed full information for student: %1$s";

    private final Index targetIndex;

    private Student studentToGetInfoFrom;

    public MoreInfoCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            preProcessStudent();

            model.displayStudentDetailsOnBrowserPanel(studentToGetInfoFrom);

        } catch (StudentNotFoundException e) {
            throw new AssertionError("The target student cannot be missing");
        } catch (StorageFileMissingException e) {
            throw new CommandException(e.getMessage());
        }


        return new CommandResult((String.format(MESSAGE_MOREINFO_STUDENT_SUCCESS, studentToGetInfoFrom.getName())));
    }

    /**
     * This method is called before execution of {@code MoreInfoCommand}.
     * Finds the target student from the last shown list of students in the StudentListPanel by its index.
     * @throws CommandException
     */
    public void preProcessStudent() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToGetInfoFrom = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MoreInfoCommand // instanceof handles nulls
                && this.targetIndex.equals(((MoreInfoCommand) other).targetIndex) // state check
                && Objects.equals(this.studentToGetInfoFrom, ((MoreInfoCommand) other).studentToGetInfoFrom));
    }
}
```
###### \src\main\java\seedu\address\logic\commands\StudentBuilder.java
``` java
    /**
     * Sets the {@code programminglanguage} of the {@code Student} that we are building.
     */
    public StudentBuilder withProgrammingLanguage(String progLang) {
        this.programmingLanguage = new ProgrammingLanguage(progLang);
        return this;
    }

    /**

     * Sets the {@code profilePicturePath} of the {@code Student} that we are building.
     */
    public StudentBuilder withProfilePictureUrl(String url) {
        this.profilePicturePath = new ProfilePicturePath(url);
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withAllergies(String allergies) {
        this.miscellaneousInfo = new MiscellaneousInfo(new Allergies(allergies), miscellaneousInfo.getNextOfKinName(),
                miscellaneousInfo.getNextOfKinPhone(), miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withNextOfKinName(String nextOfKinName) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                new NextOfKinName(nextOfKinName), miscellaneousInfo.getNextOfKinPhone(),
                miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withNextOfKinPhone(String nextOfKinPhone) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                miscellaneousInfo.getNextOfKinName(), new NextOfKinPhone(nextOfKinPhone),
                miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withRemarks(String remarks) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                miscellaneousInfo.getNextOfKinName(), miscellaneousInfo.getNextOfKinPhone(), new Remarks(remarks));
        return this;
    }
```
###### \src\main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case EditPictureCommand.COMMAND_WORD:
            return new EditPictureCommandParser().parse(arguments);


```
###### \src\main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case MoreInfoCommand.COMMAND_WORD:
            return new MoreInfoCommandParser().parse(arguments);

```
###### \src\main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case EditMiscCommand.COMMAND_WORD:
            return new EditMiscCommandParser().parse(arguments);
```
###### \src\main\java\seedu\address\logic\parser\EditMiscCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditMiscCommand object
 */
public class EditMiscCommandParser implements Parser<EditMiscCommand> {

    @Override
    public EditMiscCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALLERGIES, PREFIX_NEXTOFKINNAME, PREFIX_NEXTOFKINPHONE,
                        PREFIX_REMARKS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMiscCommand.MESSAGE_USAGE));
        }

        EditMiscDescriptor editMiscDescriptor = new EditMiscDescriptor();
        try {
            ParserUtil.parseAllergies(argMultimap.getValue(PREFIX_ALLERGIES))
                    .ifPresent(editMiscDescriptor::setAllergies);
            ParserUtil.parseNextOfKinName(argMultimap.getValue(PREFIX_NEXTOFKINNAME))
                    .ifPresent(editMiscDescriptor::setNextOfKinName);
            ParserUtil.parseNextOfKinPhone(argMultimap.getValue(PREFIX_NEXTOFKINPHONE))
                    .ifPresent(editMiscDescriptor::setNextOfKinPhone);
            ParserUtil.parseRemarks(argMultimap.getValue(PREFIX_REMARKS)).ifPresent(editMiscDescriptor::setRemarks);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editMiscDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMiscCommand.MESSAGE_NOT_EDITED);
        }

        return new EditMiscCommand(index, editMiscDescriptor);
    }
}
```
###### \src\main\java\seedu\address\logic\parser\EditPictureCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditPictureCommand object
 */
public class EditPictureCommandParser implements Parser<EditPictureCommand> {

    @Override
    public EditPictureCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_PICTURE_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_PICTURE_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPictureCommand.MESSAGE_USAGE));
        }
        Index index;
        ProfilePicturePath profilePicturePath;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPictureCommand.MESSAGE_USAGE));
        }

        try {
            profilePicturePath = ParserUtil.parsePictureUrl(argMultimap.getValue(PREFIX_PICTURE_PATH)).get();
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage(), e);
        }

        return new EditPictureCommand(index, profilePicturePath);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \src\main\java\seedu\address\logic\parser\MoreInfoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MoreInfoCommand object
 */
public class MoreInfoCommandParser implements Parser<MoreInfoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MoreInfoCommand
     * and returns a MoreInfoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoreInfoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MoreInfoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoreInfoCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \src\main\java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> path} into an {@code Optional<ProfilePicturePath>} if {@code path} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProfilePicturePath> parsePictureUrl(Optional<String> path) throws
            IllegalValueException {

        requireNonNull(path);
        return path.isPresent() ? Optional.of(parsePictureUrl(path.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String path} into a {@code ProfilePicturePath}.
     * @throws IllegalValueException if the given {@code path} is invalid.
     */
    public static ProfilePicturePath parsePictureUrl(String path) throws IllegalValueException {
        requireNonNull(path);
        if (!ProfilePicturePath.isValidPath(path)) {
            throw new IllegalValueException(ProfilePicturePath.MESSAGE_PICTURE_CONSTRAINTS);
        }
        return new ProfilePicturePath(path);
    }

    /**
     * Parses a {@code String allergies} into an {@code allergies}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code allergies} is invalid.
     */
    public static Allergies parseAllergies(String allergies) throws IllegalValueException {
        requireNonNull(allergies);
        String trimmedAllergies = allergies.trim();
        if (!Allergies.isValidAllergies(trimmedAllergies)) {
            throw new IllegalValueException(Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);
        }
        return new Allergies(trimmedAllergies);
    }

    /**
     * Parses a {@code Optional<String> allergies} into an {@code Optional<Allergies>} if {@code allergies} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Allergies> parseAllergies(Optional<String> allergies) throws IllegalValueException {
        requireNonNull(allergies);
        return allergies.isPresent() ? Optional.of(parseAllergies(allergies.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String nextOfKinName} into a {@code NextOfKinName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code nextOfKinName} is invalid.
     */
    public static NextOfKinName parseNextOfKinName(String nextOfKinName) throws IllegalValueException {
        requireNonNull(nextOfKinName);
        String trimmedNextOfKinName = nextOfKinName.trim();
        if (!NextOfKinName.isValidNextOfKinName(trimmedNextOfKinName)) {
            throw new IllegalValueException(NextOfKinName.MESSAGE_NEXTOFKINNAME_CONSTRAINTS);
        }
        return new NextOfKinName(trimmedNextOfKinName);
    }

    /**
     * Parses a {@code Optional<String> nextOfKinName} into a {@code Optional<NextOfKinName>}
     * if {@code nextOfKinName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NextOfKinName> parseNextOfKinName(Optional<String> nextOfKinName)
            throws IllegalValueException {
        requireNonNull(nextOfKinName);
        return nextOfKinName.isPresent() ? Optional.of(parseNextOfKinName(nextOfKinName.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String nextOfKinPhone} into a {@code NextOfKinPhone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code nextOfKinPhone} is invalid.
     */
    public static NextOfKinPhone parseNextOfKinPhone(String nextOfKinPhone) throws IllegalValueException {
        requireNonNull(nextOfKinPhone);
        String trimmedNextOfKinPhone = nextOfKinPhone.trim();
        if (!NextOfKinPhone.isValidNextOfKinPhone(trimmedNextOfKinPhone)) {
            throw new IllegalValueException(NextOfKinPhone.MESSAGE_NEXTOFKINPHONE_CONSTRAINTS);
        }
        return new NextOfKinPhone(trimmedNextOfKinPhone);
    }

    /**
     * Parses a {@code Optional<String> nextOfKinPhone} into a {@code Optional<NextOfKinPhone>}
     * if {@code nextOfKinPhone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NextOfKinPhone> parseNextOfKinPhone(Optional<String> nextOfKinPhone)
            throws IllegalValueException {
        requireNonNull(nextOfKinPhone);
        return nextOfKinPhone.isPresent() ? Optional.of(parseNextOfKinPhone(nextOfKinPhone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String remarks} into a {@code Remarks} instance.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code remarks} is invalid.
     */
    public static Remarks parseRemarks(String remarks) throws IllegalValueException {
        requireNonNull(remarks);
        String trimmedRemarks = remarks.trim();
        if (!Remarks.isValidRemarks(trimmedRemarks)) {
            throw new IllegalValueException(Remarks.MESSAGE_REMARKS_CONSTRAINTS);
        }
        return new Remarks(remarks);
    }
    /**
     * Parses a {@code Optional<String> remarks} into a {@code Optional<Remarks>} instance
     * if {@code remarks} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remarks> parseRemarks(Optional<String> remarks)
            throws IllegalValueException {
        requireNonNull(remarks);
        return remarks.isPresent() ? Optional.of(parseRemarks(remarks.get())) : Optional.empty();
    }
```
###### \src\main\java\seedu\address\model\ModelManager.java
``` java
    /**
     * Displays Student details on a browser panel in the UI
     * @param target
     * @throws StudentNotFoundException
     */
    public void displayStudentDetailsOnBrowserPanel(Student target) throws StudentNotFoundException,
            StorageFileMissingException {
        addressBook.checkForStudentInAdressBook(target);
        checkIfStorageFileExists();
        indicateRequiredStudentIndexChange(filteredStudents.indexOf(target));
        indicateBrowserPanelToDisplayStudent(target);
    }

    /**
     * Checks if the xml file containing student's data exists.
     * @throws StorageFileMissingException
     */
    private void checkIfStorageFileExists() throws StorageFileMissingException {
        if (FileUtil.isFileExists(new File("data/addressBook.xml"))) {
            return;
        }
        throw new StorageFileMissingException(STORAGE_FILE_MISSING);

    }

    /** Raises an event to indicate that real xml data is required for moreInfo to function */

    /** Raises an event to indicate Browser Panel display changed to display student's information */
    private void indicateBrowserPanelToDisplayStudent(Student target) {
        raise(new StudentInfoDisplayEvent(target));
    }

    /** Raises an event to indicate a student's information has changed*/
    private void indicateStudentInfoChanged() {
        raise(new StudentInfoChangedEvent());
    }

    /** Raises an event to indicate an update of the student index required at the moment in storage */
    private void indicateRequiredStudentIndexChange(int studentIndex) {
        raise(new RequiredStudentIndexChangeEvent(studentIndex));
    }

    @Override
    public void updateProfilePicture (Student target, Student editedStudent, Student finalEditedStudent)
            throws DuplicateStudentException, StudentNotFoundException {

        requireAllNonNull(target, editedStudent);
        addressBook.updateStudent(target, editedStudent);
        indicateProfilePictureChange(editedStudent);
        addressBook.updateStudent(editedStudent, finalEditedStudent);
        indicateAddressBookChanged();
        indicateStudentInfoChanged();

    }

    /** Raises an event to indicate a student's profile picture has been changed*/
    private void indicateProfilePictureChange(Student target) {
        raise(new ProfilePictureChangeEvent(target));
    }
```
###### \src\main\java\seedu\address\model\programminglanguage\ProgrammingLanguage.java
``` java
/**
 * Represents a Student's programming language in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidProgrammingLanguage(String)}
 */
public class ProgrammingLanguage {

    public static final String MESSAGE_PROGRAMMING_LANGUAGE_CONSTRAINTS = "Programming language should be a visible "
            + "character";
    public static final String PROGRAMMING_LANGUAGE_VALIDATION_REGEX = "\\p{Print}+";

    public final String programmingLanguage;

    /**
     * Constructs a {@code programminglanguage}.
     *
     * Returns true if a given string is a valid programmingLanguage.
     */
    public ProgrammingLanguage(String programmingLanguage) {
        requireNonNull(programmingLanguage);
        checkArgument(isValidProgrammingLanguage(programmingLanguage), MESSAGE_PROGRAMMING_LANGUAGE_CONSTRAINTS);
        this.programmingLanguage = programmingLanguage;
    }

    /**
     * @param test
     * @return true if a given string is a valid programmingLanguage
     */
    public static boolean isValidProgrammingLanguage(String test) {
        return test.matches(PROGRAMMING_LANGUAGE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProgrammingLanguage // instanceof handles nulls
                && this.programmingLanguage.equals(((ProgrammingLanguage) other).programmingLanguage)); // state check
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return programmingLanguage;
    }


}
```
###### \src\main\java\seedu\address\model\RequiredStudentIndex.java
``` java
/**
 * The index of the student whose info is being required to be displayed at the moment.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RequiredStudentIndex {
    private int requiredStudentIndex;

    /**
     * Constructs a RequiredStudentIndex.
     * This is the no-arg constructor that is required by JAXB.
     */
    public RequiredStudentIndex() {}

    public RequiredStudentIndex(int requiredStudentIndex) {
        this.requiredStudentIndex = requiredStudentIndex;
    }

    public int getRequiredStudentIndex() {
        return requiredStudentIndex;
    }
}
```
###### \src\main\java\seedu\address\model\student\miscellaneousinfo\Allergies.java
``` java

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's allergies component of his/her miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidAllergies(String)}
 */
public class Allergies {

    public static final String MESSAGE_ALLERGIES_CONSTRAINTS =
            "Student allergies can take any values, and it should not be blank";
    /*
     * The first character of the allergies must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ALLERGIES_VALIDATION_REGEX = "[^\\s].*";

    private final String value;

    /**
     * Construct {@code Allergies} with initial default value
     */
    public Allergies() {
        value = "Not updated";
    }

    /**
     * Constructs an {@code Allergies} instance.
     *
     * @param allergies A valid name string of allergies.
     */
    public Allergies(String allergies) {
        requireNonNull(allergies);
        checkArgument(isValidAllergies(allergies), MESSAGE_ALLERGIES_CONSTRAINTS);
        this.value = allergies;
    }

    /**
     * Returns true if a given string is a valid student allergies.
     */
    public static boolean isValidAllergies(String test) {
        return test.matches(ALLERGIES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Allergies // instanceof handles nulls
                && this.value.equals(((Allergies) other).value)); // state check
    }
}
```
###### \src\main\java\seedu\address\model\student\miscellaneousinfo\MiscellaneousInfo.java
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
###### \src\main\java\seedu\address\model\student\miscellaneousinfo\NextOfKinPhone.java
``` java
/**
 * Represents a Student's next of kin phone number component of his/her miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidNextOfKinPhone(String)}
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
###### \src\main\java\seedu\address\model\student\miscellaneousinfo\ProfilePicturePath.java
``` java
/**
 * Represents a Student's profile picture's pathname in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class ProfilePicturePath {

    public static final String MESSAGE_PICTURE_CONSTRAINTS =
            "File URL must exist and have extensions of '.jpg' or '.png' only.";
    public static final String DEFAULT_PROFILE_PICTURE =
            "data/view/profile_photo_placeholder.png";


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
        test = test.replace('\\', '/');
        if (test.equals(ProfilePicturePath.DEFAULT_PROFILE_PICTURE)) {
            return true;
        }

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
###### \src\main\java\seedu\address\model\student\miscellaneousinfo\Remarks.java
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
```
###### \src\main\java\seedu\address\storage\ProfilePictureStorage.java
``` java
/**
 * Stores the profile pictures of students
 */
public class ProfilePictureStorage {

    private final String filePath;

    public ProfilePictureStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns true is the current storage file with {@code filePath} exists
     */
    public boolean storageFileExist() {
        File pictureStorage = new File(this.filePath);
        return pictureStorage.exists();
    }

    public String getFilePath() {
        return this.filePath;
    }


}
```
###### \src\main\java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void setupViewFiles() throws IOException {

        String miscInfoPage = "data/view/" + STUDENT_MISC_INFO_PAGE;
        String infoPageStylesheet = "data/view/" + STUDENT_INFO_PAGE_STYLESHEET;
        String defaultPhoto = "data/view/profile_photo_placeholder.png";

        FileUtil.createIfMissing(new File(xmlRequiredIndexStorage.getFilePath()));
        if (!FileUtil.isFileExists(new File(miscInfoPage))) {
            FileUtil.createFile(new File(miscInfoPage));
            exportResource("data/view/" + STUDENT_MISC_INFO_PAGE);
        }

        if (!FileUtil.isFileExists(new File(infoPageStylesheet))) {
            FileUtil.createFile((new File(infoPageStylesheet)));
            exportResource("data/view/" + STUDENT_INFO_PAGE_STYLESHEET);
        }

        if (!FileUtil.isFileExists((new File(defaultPhoto)))) {
            FileUtil.createFile(new File(defaultPhoto));
            exportResource("data/view/" + "profile_photo_placeholder.png");
        }
    }

    /**
     * Exports the resources from the jar file to the directory of the contact data
     */
    private static void exportResource(String resourceName) throws IOException {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        String resourcePage = resourceName.substring(10);
        try {
            stream = MainApp.class.getResourceAsStream(FXML_FILE_FOLDER + resourcePage);
            if (stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath()).getParentFile().getPath().replace('\\', '/');

            String destinationOfFile = jarFolder + "/" + resourceName;
            File testIfExist = new File(destinationOfFile);
            if (!testIfExist.exists()) {
                destinationOfFile = resourceName;
            }
            resStreamOut = new FileOutputStream(destinationOfFile);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            stream.close();
            resStreamOut.close();
        }

    }

    /**
     * Saves the required index of the {@code Student}
     * @param newIndex
     * @throws IOException
     */
    public void saveRequiredIndex(int newIndex) throws IOException {
        String requiredIndexFilePath = xmlRequiredIndexStorage.getFilePath();
        logger.fine("Attempting to write to data file: " + requiredIndexFilePath);
        XmlRequiredIndexStorage.updateData(newIndex, requiredIndexFilePath);
    }

    /**
     * Handles the event where the required student index for displaying misc info is changed
     */
    @Override
    @Subscribe
    public void handleRequiredStudentIndexChangedEvent(RequiredStudentIndexChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveRequiredIndex(event.getNewIndex());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    /**
     * Saves the new profile picture of the {@code Student}
     * @throws IOException
     */
    public void saveProfilePicture(ProfilePicturePath pathToChangeTo, Student student) throws IOException {
        ensureProfilePictureStorageExist();

        Path newPath = pathToChangeTo.getProfilePicturePath();
        String extension = pathToChangeTo.getExtension();

        Path studentPictureFilePath = Paths.get(profilePictureStorage.getFilePath() + "/"
                + student.getUniqueKey().toString());
        deleteExistingProfilePicture(studentPictureFilePath);
        Path studentPictureFilePathWithExtension = Paths.get(studentPictureFilePath.toString() + extension);
        logger.fine("Attempting to write to data file: data/" + student.getUniqueKey().toString());


        Files.copy(newPath, studentPictureFilePathWithExtension);

    }


    /**
     * Deletes the existing profile picture
     */
    private void deleteExistingProfilePicture(Path studentPictureFilePath) {
        File tobeReplacedWithJpg = new File(studentPictureFilePath.toString() + ".jpg");
        File tobeReplacedWithPng = new File(studentPictureFilePath.toString() + ".png");

        if (tobeReplacedWithJpg.exists()) {
            tobeReplacedWithJpg.delete();
        } else {
            tobeReplacedWithPng.delete();
        }

    }

    /**
     * Makes a picture storage folder if it does not already exist.
     */
    private void ensureProfilePictureStorageExist() {
        if (!profilePictureStorage.storageFileExist()) {
            File pictureStorage = new File(profilePictureStorage.getFilePath());
            pictureStorage.mkdir();
        }
    }

    @Override
    @Subscribe
    public void handleProfilePictureChangeEvent(ProfilePictureChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveProfilePicture(event.getUrlToChangeTo(), event.getStudent());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }

    }

```
###### \src\main\java\seedu\address\storage\XmlAdaptedMiscInfo.java
``` java
/**
 * JAXB-friendly version of the Miscellaneous info of a student.
 */
public class XmlAdaptedMiscInfo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    @XmlElement(required = true)
    private String allergies;
    @XmlElement(required = true)
    private String nextOfKinName;
    @XmlElement(required = true)
    private String nextOfKinPhone;
    @XmlElement(required = true)
    private String remarks;

    /**
     * Constructs a {@code XmlAdaptedMiscInfo}.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMiscInfo() {}

    /**
     * Constructs a {@code XmlAdaptedMiscInfo} with the given student details.
     */
    public XmlAdaptedMiscInfo(String allergies, String nextOfKinName, String nextOfKinPhone, String remarks) {
        this.allergies = allergies;
        this.nextOfKinName = nextOfKinName;
        this.nextOfKinPhone = nextOfKinPhone;
        this.remarks = remarks;
    }

    /**
     * Converts a given Milestone into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMilestone
     */
    public XmlAdaptedMiscInfo(MiscellaneousInfo source) {
        this.allergies = source.getAllergies().toString();
        this.nextOfKinName = source.getNextOfKinName().toString();
        this.nextOfKinPhone = source.getNextOfKinPhone().toString();
        this.remarks = source.getRemarks().toString();
    }

    /**
     * Converts this jaxb-friendly adapted milestone object into the model's Milestone object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted milestone
     */
    public MiscellaneousInfo toModelType() throws IllegalValueException {

        if (this.allergies == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Allergies.class.getSimpleName()));
        }
        final Allergies modelAllergies = new Allergies(this.allergies);

        if (this.nextOfKinName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NextOfKinName.class.getSimpleName()));
        }
        final NextOfKinName modelNextOfKinName = new NextOfKinName(this.nextOfKinName);

        if (this.nextOfKinPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NextOfKinPhone.class.getSimpleName()));
        }
        final NextOfKinPhone modelNextOfKinPhone = new NextOfKinPhone(this.nextOfKinPhone);

        if (this.remarks == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Remarks.class.getSimpleName()));
        }
        final Remarks modelRemarks = new Remarks(this.remarks);

        return new MiscellaneousInfo(modelAllergies, modelNextOfKinName, modelNextOfKinPhone, modelRemarks);
    }
}
//author
```
###### \src\main\java\seedu\address\storage\XmlRequiredIndexStorage.java
``` java
/**
 * Stores the XML data of the required index of the particular student.
 */
public class XmlRequiredIndexStorage {

    private String filePath;

    public XmlRequiredIndexStorage(String filePath) {
        this.filePath = filePath;

    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * Updates the XML file with the new required index.
     * @param newIndex
     * @param filePath
     * @throws IOException
     */
    public static void updateData(int newIndex, String filePath) throws IOException {
        File file = new File(filePath);
        RequiredStudentIndex ris = new RequiredStudentIndex(newIndex);
        try {
            XmlUtil.saveDataToFile(file, ris);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }

    }

    public static RequiredStudentIndex getData(String filePath) {
        File file = new File(filePath);
        try {
            RequiredStudentIndex ris = XmlUtil.getDataFromFile(file, RequiredStudentIndex.class);
            return ris;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

}
```
###### \src\main\resources\view\StudentInfoTheme.css
``` css
*/

html {
	height: 100%;
}

body {
	height: 100%;
}

.background {
    background-color: white;
}

#name {
	text-align: center;
	font-size: 500%;
	margin: 0 auto;
	border: 1%;
	padding: 1%;
	word-wrap: break-word;
	font-family: Arial;
}
#profilePicture {
	margin-left: 6%;
	width: 18%;
	height: 40%;
	float:left;
}

.infoPartOne {
	margin-right : 7%;
	border-style: solid;
	border-width: thick;
	width : 65%;
	float: right;
	padding: 1%;
}

.divider {
	clear: both;
	margin-bottom: 0.5%;
	padding-bottom: 0.5%;
}

.infoPartTwo {
	margin-left: 6%;
	border-style: solid;
	border-width: thick;
	width : 85%;
	padding: 1%;
}

#phone, #email, #address, #programmingLanguage, #tags, #allergies, #nextOfKinName, #nextOfKinPhone, #remarks {
	font-size: 175%;
	word-wrap: break-word;
	font-family: Arial;
	line-height: 20px;
}
/**
```
###### \src\main\resources\view\StudentMiscInfo.html
``` html
function test(){
	document.write(location.href);
}

function getIndex(){

	var xmlhttp = new XMLHttpRequest

	xmlhttp.open("GET", "../requiredStudentIndex.xml", false);
	xmlhttp.send();

	var xmlDoc = xmlhttp.responseXML;

	var index = xmlDoc.getElementsByTagName("requiredStudentIndex")[0].getElementsByTagName("requiredStudentIndex")[0].childNodes[0].nodeValue;

	return index;
}

function loadAddressBook() {
	var xmlhttp = new XMLHttpRequest();

	xmlhttp.open("GET",  "../addressbook.xml", false);
	xmlhttp.send();

	var index = getIndex();

	var xmlDoc = xmlhttp.responseXML;

// -------------------------- Reading Student Info Part One--------------------------

	var studentInfo = xmlDoc.getElementsByTagName("students");

	var name = "<b>" + studentInfo[index].getElementsByTagName("name")[0].childNodes[0].nodeValue + "</b>"
	document.getElementById("name").innerHTML = name;

	var phone = "<b>Contact</b> : " + studentInfo[index].getElementsByTagName("phone")[0].childNodes[0].nodeValue;
	document.getElementById("phone").innerHTML = phone;

	var email = "<b>Email</b> : " + studentInfo[index].getElementsByTagName("email")[0].childNodes[0].nodeValue;
	document.getElementById("email").innerHTML = email;

	var address = "<b>Address</b>: " + studentInfo[index].getElementsByTagName("address")[0].childNodes[0].nodeValue;
	document.getElementById("address").innerHTML = address;

	var programmingLanguage = "<b>Prgoramming Language</b> : " + studentInfo[index].getElementsByTagName("programmingLanguage")[0].childNodes[0].nodeValue;
	document.getElementById("programmingLanguage").innerHTML = programmingLanguage;



	var tags = studentInfo[index].getElementsByTagName("tagged");
	var showTags = "<b>Tags</b> : ";
	for (i = 0; i < tags.length; i++) {
		showTags += "[";
		if(i%2){
		showTags += "<span style='color:blue'>";
		showTags += tags[i].childNodes[0].nodeValue;
		showTags += "</span>";
		}
		else{
		showTags += "<span style='color:maroon'>";
		showTags += tags[i].childNodes[0].nodeValue;
		showTags += "</span>";
		}
		showTags += "]";

	}
	document.getElementById("tags").innerHTML = showTags;

// -------------------------- Reading profile picture --------------------------

	var profilePicturePath = studentInfo[index].getElementsByTagName("profilePicturePath")[0].childNodes[0].nodeValue;
	var imageSourcePart1;

	if (profilePicturePath == "data\\view\\profile_photo_placeholder.png") {
		var profilePicturePath = profilePicturePath.substring(10);
		imageSourcePart1 = "<img src='";
	} else {
		imageSourcePart1 = "<img src='../../";
	}

	var imageSourcePart2 = "' style='width:100%; height:100%'>";
	var finalImageSource = imageSourcePart1 + profilePicturePath + "?" + new Date().getTime() + imageSourcePart2;
	document.getElementById("profilePicture").innerHTML = finalImageSource;

// -------------------------- Reading Student Info Part Two--------------------------

	var miscInfo = studentInfo[index].getElementsByTagName("miscellaneousInfo");

	var allergies = "<b>Alleriges</b> : " + miscInfo[0].getElementsByTagName("allergies")[0].childNodes[0].nodeValue;
	document.getElementById("allergies").innerHTML = allergies;

	var nextOfKinName = "<b>Next Of Kin Name</b> : " + miscInfo[0].getElementsByTagName("nextOfKinName")[0].childNodes[0].nodeValue;
	document.getElementById("nextOfKinName").innerHTML = nextOfKinName;

	var nextOfKinPhone = "<b>Next Of Kin Phone</b> : " + miscInfo[0].getElementsByTagName("nextOfKinPhone")[0].childNodes[0].nodeValue;
	document.getElementById("nextOfKinPhone").innerHTML = nextOfKinPhone;

	var remarks = "<b>Remarks</b> : " + miscInfo[0].getElementsByTagName("remarks")[0].childNodes[0].nodeValue;
	document.getElementById("remarks").innerHTML = remarks;

	}
```
###### \src\test\java\seedu\address\logic\commands\EditMiscCommandTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MISC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MISC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.CARL;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditMiscCommand.EditMiscDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.testutil.EditMiscDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditMiscCommand.
 */
public class EditMiscCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Student editedMiscStudent = new StudentBuilder(ALICE).withAllergies(VALID_ALLERGIES_BOB)
                .withNextOfKinName("Bob").withNextOfKinPhone("12345678").withRemarks(VALID_REMARKS_BOB).build();
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder(editedMiscStudent).build();
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditMiscCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedMiscStudent);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());
        expectedModel.updateStudent(model.getFilteredStudentList().get(0), editedMiscStudent);

        assertCommandSuccess(editMiscCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student editedMiscStudent = studentInList.withAllergies(VALID_ALLERGIES_BOB)
                .withRemarks(VALID_REMARKS_BOB).build();

        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_BOB)
                .withRemarks(VALID_REMARKS_BOB).build();
        EditMiscCommand editMiscCommand = prepareCommand(indexLastStudent, descriptor);

        String expectedMessage = String.format(EditMiscCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedMiscStudent);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());
        expectedModel.updateStudent(lastStudent, editedMiscStudent);

        assertCommandSuccess(editMiscCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, new EditMiscDescriptor());
        Student editedMiscStudent = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditMiscCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedMiscStudent);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());

        assertCommandSuccess(editMiscCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudentUnfilteredList_failure() throws Exception {
        Student firstStudent = new StudentBuilder(ALICE).withAllergies(VALID_ALLERGIES_BOB)
                .withRemarks(VALID_REMARKS_BOB).build();
        model.updateStudent(model.getFilteredStudentList().get(INDEX_SECOND.getZeroBased()), firstStudent);
        // To modify the 2nd student to be exactly the same as the
        // 1st student, except for the students miscellaneous info.
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder(firstStudent).build();
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, descriptor);

        assertCommandFailure(editMiscCommand, model, EditMiscCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_BOB).build();
        EditMiscCommand editMiscCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editMiscCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit the misc info of a student in the filtered list where index is larger
     * than size of filtered list, but smaller than size of address book
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        EditMiscCommand editMiscCommand = prepareCommand(outOfBoundIndex,
                new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_BOB).build());

        assertCommandFailure(editMiscCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Student editedStudent = new StudentBuilder(ALICE).withAllergies(VALID_ALLERGIES_BOB).build();
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder(editedStudent).build();
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());

        // edit -> first student edited
        editMiscCommand.execute();
        undoRedoStack.push(editMiscCommand);

        // undo -> reverts addressbook back to previous state and filtered student list to show all students
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first student edited again
        expectedModel.updateStudent(studentToEdit, editedStudent);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder().withAllergies(VALID_ALLERGIES_BOB).build();
        EditMiscCommand editMiscCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editMiscCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits the miscellaneous {@code Student} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited student in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the student object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameStudentEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Student editedStudent = new StudentBuilder(CARL).withAllergies(VALID_ALLERGIES_BOB).build();
        EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder(editedStudent).build();
        EditMiscCommand editMiscCommand = prepareCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());

        showStudentAtIndex(model, INDEX_THIRD);
        Student studentToEdit = model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased());
        // edit -> edits third student in unfiltered student list / first student in filtered student list
        editMiscCommand.execute();
        undoRedoStack.push(editMiscCommand);

        // undo -> reverts addressbook back to previous state and filtered student list to show all students
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateStudent(studentToEdit, editedStudent);
        assertNotEquals(model.getFilteredStudentList().get(INDEX_FIRST.getZeroBased()), studentToEdit);
        // redo -> edits same second student in unfiltered student list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditMiscCommand standardCommand = prepareCommand(INDEX_FIRST, DESC_MISC_AMY);

        // same values -> returns true
        EditMiscDescriptor copyDescriptor = new EditMiscDescriptor(DESC_MISC_AMY);
        EditMiscCommand commandWithSameValues = prepareCommand(INDEX_FIRST, copyDescriptor);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMiscCommand(INDEX_SECOND, DESC_MISC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMiscCommand(INDEX_FIRST, DESC_MISC_BOB)));
    }


    /**
     * Returns an {@code EditMiscCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditMiscCommand prepareCommand(Index index, EditMiscDescriptor descriptor) {
        EditMiscCommand editMiscCommand = new EditMiscCommand(index, descriptor);
        editMiscCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editMiscCommand;
    }

}
```
###### \src\test\java\seedu\address\logic\commands\EditPictureCommandTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.PROFILEPICTUREPATH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROFILEPICTUREPATH_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILEPICTUREPATH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;

public class EditPictureCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    @Test
    public void execute_validFilePath_success() throws Exception {
        Student editedPictureStudent = new StudentBuilder(ALICE)
                .withProfilePictureUrl("data/profilePictures/c5daab.png").build();
        //The above is to account for the the saving of profile picture in local data using the unique key of
        //the student.

        ProfilePicturePath profilePicturePath = new ProfilePicturePath(VALID_PROFILEPICTUREPATH_AMY);
        EditPictureCommand editPictureCommand = prepareCommand(INDEX_FIRST, profilePicturePath);

        String expectedMessage = String.format(EditPictureCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedPictureStudent);

        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new Schedule());
        expectedModel.updateStudent(model.getFilteredStudentList().get(0), editedPictureStudent);

        assertCommandSuccess(editPictureCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        ProfilePicturePath profilePicturePath = new ProfilePicturePath(VALID_PROFILEPICTUREPATH_AMY);
        EditPictureCommand editPictureCommand = prepareCommand(outOfBoundIndex, profilePicturePath);

        assertCommandFailure(editPictureCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit the misc info of a student in the filtered list where index is larger
     * than size of filtered list, but smaller than size of address book
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        EditPictureCommand editPictureCommand = prepareCommand(outOfBoundIndex,
                new ProfilePicturePath(PROFILEPICTUREPATH_DESC_AMY));

        assertCommandFailure(editPictureCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }


    @Test
    public void equals() throws Exception {
        final EditPictureCommand standardCommand = prepareCommand(INDEX_FIRST,
                new ProfilePicturePath(PROFILEPICTUREPATH_DESC_AMY));


        // same values -> returns true
        ProfilePicturePath profilePicturePath = new ProfilePicturePath(PROFILEPICTUREPATH_DESC_AMY);
        EditPictureCommand commandWithSameValues = prepareCommand(INDEX_FIRST, profilePicturePath);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preProcessCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPictureCommand(INDEX_SECOND,
                new ProfilePicturePath(PROFILEPICTUREPATH_DESC_AMY))));

        // different profile picture path -> returns false
        assertFalse(standardCommand.equals(new EditPictureCommand(INDEX_FIRST,
                new ProfilePicturePath(PROFILEPICTUREPATH_DESC_BOB))));
    }

    /**
     * Returns an {@code EditPictureCommand} with parameters {@code index} and {@code profilePicturePath}
     */
    private EditPictureCommand prepareCommand(Index index, ProfilePicturePath profilePicturePath) {
        EditPictureCommand editPictureCommand = new EditPictureCommand(index, profilePicturePath);
        editPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editPictureCommand;
    }

}
```
###### \src\test\java\seedu\address\logic\commands\MoreInfoCommandTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.exceptions.StorageFileMissingException.STORAGE_FILE_MISSING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;

public class MoreInfoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());

    /**
     * NOTE: This test will succeed when there is no data around initially and hence no data folder.
     * If this test is failing at the moment, please delete any data in the local folder and try again.
     */
    @Test
    public void execute_storageFileMissing_failure() {
        MoreInfoCommand moreInfoCommand = prepareCommand(INDEX_FIRST);
        String expectedMessage = STORAGE_FILE_MISSING;
        assertCommandFailure(moreInfoCommand, model , expectedMessage);
    }

    @Test
    public void execute_storageFileMissing_success() throws Exception {
        MoreInfoCommand moreInfoCommand = prepareCommand(INDEX_FIRST);
        String expectedMessage = MoreInfoCommand.MESSAGE_MOREINFO_STUDENT_SUCCESS.substring(0, 40)
                + "Alice Pauline";


        String pathOfDataFileToCreate = "data/addressBook.xml";
        File dataFileToCreate = new File(pathOfDataFileToCreate);
        Boolean fileCreated = false;
        if (!dataFileToCreate.exists()) {
            new File("data").mkdir();
            dataFileToCreate.createNewFile();
            fileCreated = true;
        }

        assertCommandSuccess(moreInfoCommand, model , expectedMessage, model);

        if (fileCreated == true) {
            File parent = dataFileToCreate.getParentFile();
            dataFileToCreate.delete();
            parent.delete();

        }

    }

    @Test
    public void equals() throws Exception {
        final MoreInfoCommand standardCommand = prepareCommand(INDEX_FIRST);

        // same values -> returns true
        Index sameIndex = INDEX_FIRST;
        MoreInfoCommand commandWithSameValues = prepareCommand(sameIndex);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preProcessStudent();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MoreInfoCommand(INDEX_SECOND)));

    }
    /**
     * Returns a {@code MoreInfo} with parameters {@code index}
     */
    private MoreInfoCommand prepareCommand(Index index) {
        MoreInfoCommand moreInfoCommand = new MoreInfoCommand(index);
        moreInfoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return moreInfoCommand;
    }
}

```
###### \src\test\java\seedu\address\logic\parser\EditMiscCommandParserTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGIES_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGIES_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALLERGIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOKNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOKPHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARKS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NOKNAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOKNAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOKPHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOKPHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditMiscCommand;
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.Remarks;
import seedu.address.testutil.EditMiscDescriptorBuilder;


public class EditMiscCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMiscCommand.MESSAGE_USAGE);

    private EditMiscCommandParser parser = new EditMiscCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ALLERGIES_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditMiscCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ALLERGIES_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ALLERGIES_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ALLERGIES_DESC,
                Allergies.MESSAGE_ALLERGIES_CONSTRAINTS); // invalid allergies
        assertParseFailure(parser, "1" + INVALID_NOKNAME_DESC,
                NextOfKinName.MESSAGE_NEXTOFKINNAME_CONSTRAINTS); // invalid next-of-kin name
        assertParseFailure(parser, "1" + INVALID_NOKPHONE_DESC,
                NextOfKinPhone.MESSAGE_NEXTOFKINPHONE_CONSTRAINTS); // invalid next-of-kin phone
        assertParseFailure(parser, "1" + INVALID_REMARKS_DESC,
                Remarks.MESSAGE_REMARKS_CONSTRAINTS); // invalid remarks

        // invalid allergies followed by valid remarks
        assertParseFailure(parser, "1" + INVALID_ALLERGIES_DESC + NOKNAME_DESC_AMY,
                Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);

        // valid allergies followed by invalid allergies. The test case for invalid allergies followed by
        // valid allergies is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ALLERGIES_DESC_AMY + INVALID_ALLERGIES_DESC,
                Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);


        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_ALLERGIES_DESC + INVALID_NOKNAME_DESC
                + REMARKS_DESC_AMY, Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ALLERGIES_DESC_AMY + NOKNAME_DESC_AMY
                + NOKPHONE_DESC_AMY + REMARKS_DESC_AMY;

        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_AMY).withNextOfKinName(VALID_NOKNAME_AMY)
                .withNextOfKinPhone(VALID_NOKPHONE_AMY).withRemarks(VALID_REMARKS_AMY).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ALLERGIES_DESC_AMY + NOKNAME_DESC_AMY;

        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_AMY).withNextOfKinName(VALID_NOKNAME_AMY).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // allergies
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + ALLERGIES_DESC_AMY;
        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_AMY).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin name
        userInput = targetIndex.getOneBased() + NOKNAME_DESC_AMY;
        descriptor = new EditMiscDescriptorBuilder().withNextOfKinName(VALID_NOKNAME_AMY).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin phone
        userInput = targetIndex.getOneBased() + NOKPHONE_DESC_AMY;
        descriptor = new EditMiscDescriptorBuilder().withNextOfKinPhone(VALID_NOKPHONE_AMY).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remarks
        userInput = targetIndex.getOneBased() + REMARKS_DESC_AMY;
        descriptor = new EditMiscDescriptorBuilder().withRemarks(VALID_REMARKS_AMY).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()  + ALLERGIES_DESC_AMY + NOKNAME_DESC_AMY + NOKPHONE_DESC_AMY
                + REMARKS_DESC_AMY + ALLERGIES_DESC_AMY + ALLERGIES_DESC_BOB + NOKNAME_DESC_BOB + NOKNAME_DESC_BOB
                + NOKPHONE_DESC_BOB + REMARKS_DESC_BOB;

        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_BOB).withNextOfKinName(VALID_NOKNAME_BOB)
                .withNextOfKinPhone(VALID_NOKPHONE_BOB).withRemarks(VALID_REMARKS_BOB)
                .build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_ALLERGIES_DESC + ALLERGIES_DESC_BOB;
        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_BOB).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + NOKNAME_DESC_BOB + INVALID_ALLERGIES_DESC + NOKPHONE_DESC_BOB
                + ALLERGIES_DESC_BOB;
        descriptor = new EditMiscDescriptorBuilder().withNextOfKinName(VALID_NOKNAME_BOB)
                .withAllergies(VALID_ALLERGIES_BOB)
                .withNextOfKinPhone(VALID_NOKPHONE_BOB).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }




}
```
###### \src\test\java\seedu\address\logic\parser\EditPictureCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROFILEPICTUREPATH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PROFILEPICTUREPATH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILEPICTUREPATH_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.EditPictureCommand;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;

public class EditPictureCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPictureCommand.MESSAGE_USAGE);

    private EditPictureCommandParser parser = new EditPictureCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PROFILEPICTUREPATH_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, PREFIX_INDEX + "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_invalidIndex_failure() {
        //missing index prefix
        assertParseFailure(parser, "1" + PROFILEPICTUREPATH_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // negative index
        assertParseFailure(parser, PREFIX_INDEX + "-5" + PROFILEPICTUREPATH_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, PREFIX_INDEX +  "0" + PROFILEPICTUREPATH_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, PREFIX_INDEX + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, PREFIX_INDEX + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser,  " " + PREFIX_INDEX + "1 " +  PREFIX_PICTURE_PATH
                        + INVALID_PROFILEPICTUREPATH_DESC, ProfilePicturePath.MESSAGE_PICTURE_CONSTRAINTS);
    }


}
```
###### \src\test\java\seedu\address\logic\parser\MoreInfoCommandParserTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.MoreInfoCommand;

public class MoreInfoCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoreInfoCommand.MESSAGE_USAGE);

    private MoreInfoCommandParser parser = new MoreInfoCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsMoreInfoCommand() {
        assertParseSuccess(parser, "1", new MoreInfoCommand(INDEX_FIRST));
    }
}
```
###### \src\test\java\seedu\address\model\student\ProgrammingLanguageTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.testutil.Assert;

public class ProgrammingLanguageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProgrammingLanguage(null));
    }

    @Test
    public void constructor_invalidProgrammingLanguage_throwsIllegalArgumentException() {
        String invalidProgrammingLanguage = "\t";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProgrammingLanguage(invalidProgrammingLanguage));
    }

    @Test
    public void isValidProgrammingLanguage() {
        // null ProgrammingLanguage
        Assert.assertThrows(NullPointerException.class, () -> new ProgrammingLanguage(null));

        // invalid ProgrammingLanguage
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\t")); // tab an invisible character
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\n")); // new line an invisible charater
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\r")); // carriage return invisible character
        assertFalse(ProgrammingLanguage.isValidProgrammingLanguage("\f")); // form feed an invisible character

        // valid ProgrammingLanguage
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("java"));
        // alphabets only
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("12345"));
        // numbers only
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("angular js 8"));
        // alphanumeric characters
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("C"));
        // with capital letters
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("javascript version 10 hardcore"));
        // long ProgrammingLanguages
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("C++"));
        // with special characters
        assertTrue(ProgrammingLanguage.isValidProgrammingLanguage("#"));
        // special characters only
    }
}
```
###### \src\test\java\seedu\address\storage\XmlAdaptedStudentTest.java
``` java
    @Test
    public void toModelType_nullPicturePath_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_KEY, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_TAGS, VALID_FAVOURITE, INVALID_PROFILEPICTUREPATH,
                VALID_DASHBOARD, VALID_MISCELLANEOUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ProgrammingLanguage.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }
}
```
###### \src\test\java\seedu\address\storage\XmlRequiredIndexStorageTest.java
``` java
public class XmlRequiredIndexStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void saveRequiredIndexStorage_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        updateData(1, null);
    }

    @Test
    public void saveRequiredIndexStorage_invalidFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        updateData(1, "invalid");
    }

    @Test
    public void saveRequiredIndexStorage_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempRequiredIndexStorage.xml";
        FileUtil.createIfMissing(new File(filePath));
        RequiredStudentIndex original = new RequiredStudentIndex(1);
        XmlRequiredIndexStorage xmlRequiredIndexStorage = new XmlRequiredIndexStorage(filePath);

        //save in new file and read back
        xmlRequiredIndexStorage.updateData(1, filePath);
        RequiredStudentIndex readBack = xmlRequiredIndexStorage.getData(filePath);
        assertEquals(original.getRequiredStudentIndex(), readBack.getRequiredStudentIndex());
    }

}
```
###### \src\test\java\seedu\address\testutil\EditMiscDescriptorBuilder.java
``` java

import seedu.address.logic.commands.EditMiscCommand.EditMiscDescriptor;
import seedu.address.model.student.Student;
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.Remarks;

/**
 * A utility class to help with building an EditMiscDescriptor object
 */
public class EditMiscDescriptorBuilder {

    private EditMiscDescriptor descriptor;

    public EditMiscDescriptorBuilder() {
        descriptor = new EditMiscDescriptor();
    }

    public EditMiscDescriptorBuilder(EditMiscDescriptor editMiscDescriptor) {
        this.descriptor = new EditMiscDescriptor(editMiscDescriptor);
    }

    /**
     * Returns an {@code EditMiscDescriptor} object with fields containing {@code student}'s miscellaneous details
     */
    public EditMiscDescriptorBuilder(Student student) {
        this.descriptor = new EditMiscDescriptor();
        this.descriptor.setAllergies(student.getMiscellaneousInfo().getAllergies());
        this.descriptor.setNextOfKinName(student.getMiscellaneousInfo().getNextOfKinName());
        this.descriptor.setNextOfKinPhone(student.getMiscellaneousInfo().getNextOfKinPhone());
        this.descriptor.setRemarks(student.getMiscellaneousInfo().getRemarks());
    }

    /**
     * Sets the {@code Allergies} of the {@code EditMiscDescriptor} that is being built
     * @param allergies
     */
    public EditMiscDescriptorBuilder withAllergies(String allergies) {
        this.descriptor.setAllergies(new Allergies(allergies));
        return this;
    }
    /**
     * Sets the {@code NextOfKinName} of the {@code EditMiscDescriptor} that is being built
     * @param nextOfKinName
     * */
    public EditMiscDescriptorBuilder withNextOfKinName(String nextOfKinName) {
        this.descriptor.setNextOfKinName(new NextOfKinName(nextOfKinName));
        return this;
    }
    /**
     * Sets the {@code NextOfKinPhone} of the {@code EditMiscDescriptor} that is being built
     * @param nextOfKinPhone
     */
    public EditMiscDescriptorBuilder withNextOfKinPhone(String nextOfKinPhone) {
        this.descriptor.setNextOfKinPhone(new NextOfKinPhone(nextOfKinPhone));
        return this;
    }
    /**
     * Sets the {@code Remarks} of the {@code EditMiscDescriptor} that is being built
     * @param remarks
     */
    public EditMiscDescriptorBuilder withRemarks(String remarks) {
        this.descriptor.setRemarks(new Remarks(remarks));
        return this;
    }


    public EditMiscDescriptor build() {
        return descriptor;
    }

}
//@author
```
###### \src\test\java\seedu\address\testutil\StudentBuilder.java
``` java
    /**
     * Sets the {@code programminglanguage} of the {@code Student} that we are building.
     */
    public StudentBuilder withProgrammingLanguage(String progLang) {
        this.programmingLanguage = new ProgrammingLanguage(progLang);
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withAllergies(String allergies) {
        this.miscellaneousInfo = new MiscellaneousInfo(new Allergies(allergies), miscellaneousInfo.getNextOfKinName(),
                miscellaneousInfo.getNextOfKinPhone(), miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withNextOfKinName(String nextOfKinName) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                new NextOfKinName(nextOfKinName), miscellaneousInfo.getNextOfKinPhone(),
                miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withNextOfKinPhone(String nextOfKinPhone) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                miscellaneousInfo.getNextOfKinName(), new NextOfKinPhone(nextOfKinPhone),
                miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withRemarks(String remarks) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                miscellaneousInfo.getNextOfKinName(), miscellaneousInfo.getNextOfKinPhone(), new Remarks(remarks));
        return this;
    }
```

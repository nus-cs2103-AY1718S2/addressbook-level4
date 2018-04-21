# kexiaowen
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code University} of the {@code Person} that we are building.
     */
    public PersonBuilder withUniversity(String university) {
        this.university = new University(university);
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code JobApplied} of the {@code Person} that we are building.
     */
    public PersonBuilder withJobApplied(String jobApplied) {
        this.jobApplied = new JobApplied(jobApplied);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Person} that we are building.
     */
    public PersonBuilder withRating(String technicalSkillsScore, String communicationSkillsScore,
                                    String problemSolvingSkillsScore, String experienceScore) {
        this.rating = new Rating(Double.valueOf(technicalSkillsScore),
                Double.valueOf(communicationSkillsScore),
                Double.valueOf(problemSolvingSkillsScore),
                Double.valueOf(experienceScore));
        return this;
    }

```
###### /java/seedu/address/testutil/EditRatingDescriptorBuilder.java
``` java
/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditRatingDescriptorBuilder {
    private EditRatingDescriptor descriptor;

    public EditRatingDescriptorBuilder() {
        descriptor = new EditRatingDescriptor();
    }

    public EditRatingDescriptorBuilder(EditRatingDescriptor descriptor) {
        this.descriptor = new EditRatingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRatingDescriptor} with fields containing {@code rating}'s details
     */
    public EditRatingDescriptorBuilder(Rating rating) {
        descriptor = new EditRatingDescriptor();
        descriptor.setTechnicalSkillsScore(rating.getTechnicalSkillsScore());
        descriptor.setCommunicationSkillsScore(rating.getCommunicationSkillsScore());
        descriptor.setProblemSolvingSkillsScore(rating.getProblemSolvingSkillsScore());
        descriptor.setExperienceScore(rating.getExperienceScore());
    }

    /**
     * Sets the {@code technicalSkillsScore} of the {@code EditRatingDescriptor} that we are building.
     */
    public EditRatingDescriptorBuilder withTechnicalSkillsScore(double technicalSkillsScore) {
        descriptor.setTechnicalSkillsScore(technicalSkillsScore);
        return this;
    }

    /**
     * Sets the {@code communicationSkillsScore} of the {@code EditRatingDescriptor} that we are building.
     */
    public EditRatingDescriptorBuilder withCommunicationSkillsScore(double communicationSkillsScore) {
        descriptor.setCommunicationSkillsScore(communicationSkillsScore);
        return this;
    }

    /**
     * Sets the {@code problemSolvingSkillsScore} of the {@code EditRatingDescriptor} that we are building.
     */
    public EditRatingDescriptorBuilder withProblemSolvingSkillsScore(double problemSolvingSkillsScore) {
        descriptor.setProblemSolvingSkillsScore(problemSolvingSkillsScore);
        return this;
    }

    /**
     * Sets the {@code experienceScore} of the {@code EditRatingDescriptor} that we are building.
     */
    public EditRatingDescriptorBuilder withExperienceScore(double experienceScore) {
        descriptor.setExperienceScore(experienceScore);
        return this;
    }

    public EditRatingDescriptor build() {
        return descriptor;
    }
}
```
###### /java/seedu/address/model/ModelManagerTest.java
``` java
    @Test
    public void sortPersonListAscOrder() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(GEORGE).build();
        UserPrefs userPrefs = new UserPrefs();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(GEORGE)
                .withPerson(ALICE).withPerson(BENSON).build();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        modelManager.sortPersonListAscOrder(SortCommand.SortField.RATING);
        assertEquals(modelManager, new ModelManager(expectedAddressBook, userPrefs));
    }

    @Test
    public void sortPersonListDescOrder() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(GEORGE).build();
        UserPrefs userPrefs = new UserPrefs();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(BENSON)
                .withPerson(ALICE).withPerson(GEORGE).build();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        modelManager.sortPersonListDescOrder(SortCommand.SortField.RATING);
        assertEquals(modelManager, new ModelManager(expectedAddressBook, userPrefs));
    }
}
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
    @Test
    public void sortDesc_sortByRating_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortDesc(SortCommand.SortField.RATING);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(BENSON).withPerson(ALICE)
                .withPerson(CARL).withPerson(DANIEL).withPerson(ELLE).withPerson(FIONA).withPerson(GEORGE).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortAsc_sortByRating_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortAsc(SortCommand.SortField.RATING);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(CARL).withPerson(DANIEL)
                .withPerson(ELLE).withPerson(FIONA).withPerson(GEORGE).withPerson(ALICE).withPerson(BENSON).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortDesc_sortByGradePointAverage_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortDesc(SortCommand.SortField.GPA);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(CARL).withPerson(ALICE)
                .withPerson(FIONA).withPerson(BENSON).withPerson(ELLE).withPerson(DANIEL).withPerson(GEORGE).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortAsc_sortByGradePointAverage_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortAsc(SortCommand.SortField.GPA);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(GEORGE).withPerson(DANIEL)
                .withPerson(ELLE).withPerson(BENSON).withPerson(FIONA).withPerson(ALICE).withPerson(CARL).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortDesc_sortByName_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortDesc(SortCommand.SortField.NAME);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(GEORGE).withPerson(FIONA)
                .withPerson(ELLE).withPerson(DANIEL).withPerson(CARL).withPerson(BENSON).withPerson(ALICE).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

    @Test
    public void sortAsc_sortByName_sortSuccessful() {
        AddressBook newData = getTypicalAddressBook();
        newData.sortAsc(SortCommand.SortField.NAME);

        AddressBook expectedAddressbook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withPerson(CARL).withPerson(DANIEL).withPerson(ELLE).withPerson(FIONA).withPerson(GEORGE).build();
        assertEquals(expectedAddressbook.getPersonList(), newData.getPersonList());
    }

```
###### /java/seedu/address/model/person/UniversityTest.java
``` java
public class UniversityTest {

    private final University university = new University("NUS");

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new University(null));
    }

    @Test
    public void constructor_invalidUniversity_throwsIllegalArgumentException() {
        String invalidUniversity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new University(invalidUniversity));
    }

    @Test
    public void isValidUniversity() {
        // null university
        Assert.assertThrows(NullPointerException.class, () -> University.isValidUniversity(null));

        // invalid university
        assertFalse(University.isValidUniversity("")); // empty string
        assertFalse(University.isValidUniversity(" ")); // spaces only
        assertFalse(University.isValidUniversity("^")); // only non-alphanumeric characters
        assertFalse(University.isValidUniversity("N*S")); // contains non-alphanumeric characters

        // valid name
        assertTrue(University.isValidUniversity("ntu")); // alphabets only
        assertTrue(University.isValidUniversity("12345")); // numbers only
        assertTrue(University.isValidUniversity("nus the 1st")); // alphanumeric characters
        assertTrue(University.isValidUniversity("National University of Singapore")); // with capital letters
        assertTrue(University.isValidUniversity("University of California Santa Barbara")); // long names
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(university.equals(university));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        University universityCopy = new University("NUS");
        assertTrue(university.equals(universityCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(university.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(university.equals(null));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        University differentUniversity = new University("NTU");
        assertFalse(university.equals(differentUniversity));
    }
}
```
###### /java/seedu/address/model/person/JobAppliedTest.java
``` java
public class JobAppliedTest {

    private final JobApplied jobApplied = new JobApplied("Software Engineer");

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JobApplied(null));
    }

    @Test
    public void constructor_invalidJobApplied_throwsIllegalArgumentException() {
        String invalidJobApplied = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new JobApplied(invalidJobApplied));
    }

    @Test
    public void isValidJobApplied() {
        // null jobApplied
        Assert.assertThrows(NullPointerException.class, () -> JobApplied.isValidJobApplied(null));

        // invalid jobApplied
        assertFalse(JobApplied.isValidJobApplied("")); // empty string
        assertFalse(JobApplied.isValidJobApplied(" ")); // spaces only

        // valid jobApplied
        assertTrue(JobApplied.isValidJobApplied("software engineer")); // alphabets only
        assertTrue(JobApplied.isValidJobApplied("12345")); // numbers only
        assertTrue(JobApplied.isValidJobApplied("1 software engineer 2 DevOps")); // alphanumeric characters
        assertTrue(JobApplied.isValidJobApplied("Software Engineer")); // with capital letters
        assertTrue(JobApplied.isValidJobApplied("Software Engineer & Web Developer")); // long names
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(jobApplied.equals(jobApplied));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        JobApplied jobAppliedCopy = new JobApplied("Software Engineer");
        assertTrue(jobApplied.equals(jobAppliedCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(jobApplied.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(jobApplied.equals(null));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        JobApplied differentJobApplied = new JobApplied("Front-end Developer");
        assertFalse(jobApplied.equals(differentJobApplied));
    }
}
```
###### /java/seedu/address/model/person/RatingTest.java
``` java
public class RatingTest {

    private final Rating rating = new Rating(3, 3,
            4, 3.5);
    @Test
    public void isValidScore() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Rating.isValidScore(null));

        // invalid scores
        assertFalse(Rating.isValidScore((double) 0));
        assertFalse(Rating.isValidScore(5.5));
        assertFalse(Rating.isValidScore((double) -3));

        // valid phone numbers
        assertTrue(Rating.isValidScore((double) 1));
        assertTrue(Rating.isValidScore(3.5));
        assertTrue(Rating.isValidScore((double) 5));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(rating.equals(rating));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Rating ratingCopy = new Rating(rating.getTechnicalSkillsScore(), rating.communicationSkillsScore,
                rating.getProblemSolvingSkillsScore(), rating.getExperienceScore());
        assertTrue(rating.equals(ratingCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(rating.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(rating.equals(null));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Rating differentRating = new Rating(1, 1,
                1, 1);
        assertFalse(rating.equals(differentRating));
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidUniversity_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                INVALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE, VALID_EXPERIENCE_SCORE,
                VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT, VALID_INTERVIEW_DATE, VALID_STATUS,
                VALID_TAGS);
        String expectedMessage = University.MESSAGE_UNIVERSITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullUniversity_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE, VALID_EXPERIENCE_SCORE,
                VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT, VALID_INTERVIEW_DATE, VALID_STATUS,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                University.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

```
###### /java/seedu/address/storage/XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidJobApplied_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                INVALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE, VALID_COMMUNICATION_SKILLS_SCORE,
                VALID_PROBLEM_SOLVING_SKILLS_SCORE, VALID_EXPERIENCE_SCORE, VALID_RESUME,
                VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = JobApplied.MESSAGE_JOB_APPLIED_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullJobApplied_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                null, VALID_TECHNICAL_SKILLS_SCORE, VALID_COMMUNICATION_SKILLS_SCORE,
                VALID_PROBLEM_SOLVING_SKILLS_SCORE, VALID_EXPERIENCE_SCORE, VALID_RESUME,
                VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                JobApplied.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

```
###### /java/seedu/address/storage/XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_UNIVERSITY, VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR, VALID_GRADE_POINT_AVERAGE,
                VALID_JOB_APPLIED, INVALID_TECHNICAL_SKILLS_SCORE, INVALID_COMMUNICATION_SKILLS_SCORE,
                INVALID_PROBLEM_SOLVING_SKILLS_SCORE, INVALID_EXPERIENCE_SCORE,
                VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                VALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = Rating.MESSAGE_RATING_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

```
###### /java/seedu/address/commons/util/DoubleUtilTest.java
``` java
public class DoubleUtilTest {

    @Test
    public void roundToTwoDecimalPlaces_inputDouble() {
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(1) == 1.0);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(1.3424323423424) == 1.34);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(4242.3351231231) == 4242.34);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(
                3.345331231234444444232322325898788765767645436658689797676547587698) == 3.35);
    }

    @Test
    public void roundToTwoDecimalPlaces_inputString() {
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces("1") == 1.0);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces("1.3424323423424") == 1.34);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces("4242.3351231231") == 4242.34);
        assertTrue(DoubleUtil.roundToTwoDecimalPlaces(
                "3.345331231234444444232322325898788765767645436658689797676547587698") == 3.35);
    }
}
```
###### /java/seedu/address/logic/commands/RateCommandTest.java
``` java
/**
  * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
  */
public class RateCommandTest {

    public static final String TECHNICAL_SKILLS_SCORE = "4";
    public static final String COMMUNICATION_SKILLS_SCORE = "4.5";
    public static final String PROBLEM_SOLVING_SKILLS_SCORE = "3";
    public static final String EXPERIENCE_SCORE = "3.5";
    public static final Rating VALID_RATING = new Rating(4, 4.5,
            3, 3.5);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person ratedPerson = new PersonBuilder(firstPerson).withRating(TECHNICAL_SKILLS_SCORE,
                COMMUNICATION_SKILLS_SCORE, PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE).build();

        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, ratedPerson.getRating());
        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_PERSON_SUCCESS,
                ratedPerson.getName(), ratedPerson.getRating().getTechnicalSkillsScore(),
                ratedPerson.getRating().getCommunicationSkillsScore(),
                ratedPerson.getRating().getProblemSolvingSkillsScore(),
                ratedPerson.getRating().getExperienceScore(),
                ratedPerson.getRating().getOverallScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, ratedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person ratedPerson = new PersonBuilder(firstPerson).withRating(TECHNICAL_SKILLS_SCORE,
                COMMUNICATION_SKILLS_SCORE, PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE).build();

        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, ratedPerson.getRating());
        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_PERSON_SUCCESS,
                ratedPerson.getName(),
                ratedPerson.getRating().getTechnicalSkillsScore(),
                ratedPerson.getRating().getCommunicationSkillsScore(),
                ratedPerson.getRating().getProblemSolvingSkillsScore(),
                ratedPerson.getRating().getExperienceScore(),
                ratedPerson.getRating().getOverallScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, ratedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        RateCommand rateCommand = prepareCommand(outOfBoundIndex, VALID_RATING);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RateCommand rateCommand = prepareCommand(outOfBoundIndex, VALID_RATING);

        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withRating(TECHNICAL_SKILLS_SCORE,
                COMMUNICATION_SKILLS_SCORE, PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE).build();
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_RATING);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // rate -> first person rating changed
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person modified again
        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RateCommand rateCommand = prepareCommand(outOfBoundIndex, VALID_RATING);

        // execution failed -> rateCommand not pushed into undoRedoStack
        assertCommandFailure(rateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#remark} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonRated() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_RATING);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withRating(TECHNICAL_SKILLS_SCORE,
                COMMUNICATION_SKILLS_SCORE, PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE).build();
        // rate -> modifies second person in unfiltered person list / first person in filtered person list
        rateCommand.execute();
        undoRedoStack.push(rateCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertNotEquals(personToModify, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> modifies same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final RateCommand standardCommand = new RateCommand(INDEX_FIRST_PERSON, VALID_RATING);

        // same values -> returns true
        RateCommand commandWithSameValues = new RateCommand(INDEX_FIRST_PERSON, VALID_RATING);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_SECOND_PERSON,
                VALID_RATING)));

        // different rating -> returns false
        assertFalse(standardCommand.equals(new RateCommand(INDEX_FIRST_PERSON,
                new Rating(1, 1,
                        1, 1))));
    }

    /**
      * Returns a {@code RateCommand}.
      */
    private RateCommand prepareCommand(Index index, Rating rating) {
        RateCommand rateCommand = new RateCommand(index, rating);
        rateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return rateCommand;
    }
}
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public ObservableList<Person> getActivePersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setSelectedPerson(Person selectedPerson) {
            fail("This method should not be called.");
        }

        @Override
        public Person getSelectedPerson() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void filterFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersonListAscOrder(SortCommand.SortField sortField) {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersonListDescOrder(SortCommand.SortField sortField) {
            fail("This method should not be called.");
        }
```
###### /java/seedu/address/logic/commands/RatingEditCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RatingEditCommand.
 */
public class RatingEditCommandTest {
    public static final double TECHNICAL_SKILLS_SCORE = 4;
    public static final double COMMUNICATION_SKILLS_SCORE = 4.5;
    public static final double PROBLEM_SOLVING_SKILLS_SCORE = 3;
    public static final double EXPERIENCE_SCORE = 3.2;
    public static final String TECHNICAL_SKILLS_SCORE_STRING = "4";
    public static final String COMMUNICATION_SKILLS_SCORE_STRING = "4.5";
    public static final String PROBLEM_SOLVING_SKILLS_SCORE_STRING = "3";
    public static final String EXPERIENCE_SCORE_STRING = "3.2";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRating(TECHNICAL_SKILLS_SCORE_STRING,
                COMMUNICATION_SKILLS_SCORE_STRING, PROBLEM_SOLVING_SKILLS_SCORE_STRING,
                EXPERIENCE_SCORE_STRING).build();
        Rating rating = new Rating(TECHNICAL_SKILLS_SCORE, COMMUNICATION_SKILLS_SCORE,
                PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE);
        EditRatingDescriptor descriptor = new EditRatingDescriptorBuilder(rating).build();


        RatingEditCommand ratingEditCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(RatingEditCommand.MESSAGE_EDIT_RATING_SUCCESS,
                editedPerson.getName(), editedPerson.getRating().getTechnicalSkillsScore(),
                editedPerson.getRating().getCommunicationSkillsScore(),
                editedPerson.getRating().getProblemSolvingSkillsScore(),
                editedPerson.getRating().getExperienceScore(),
                editedPerson.getRating().getOverallScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(ratingEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRating(TECHNICAL_SKILLS_SCORE_STRING,
                COMMUNICATION_SKILLS_SCORE_STRING, PROBLEM_SOLVING_SKILLS_SCORE_STRING,
                Double.toString(firstPerson.getRating().experienceScore)).build();
        Rating rating = new Rating(TECHNICAL_SKILLS_SCORE, COMMUNICATION_SKILLS_SCORE,
                PROBLEM_SOLVING_SKILLS_SCORE, firstPerson.getRating().experienceScore);
        EditRatingDescriptor descriptor = new EditRatingDescriptorBuilder(rating).build();


        RatingEditCommand ratingEditCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(RatingEditCommand.MESSAGE_EDIT_RATING_SUCCESS,
                editedPerson.getName(), editedPerson.getRating().getTechnicalSkillsScore(),
                editedPerson.getRating().getCommunicationSkillsScore(),
                editedPerson.getRating().getProblemSolvingSkillsScore(),
                editedPerson.getRating().getExperienceScore(),
                editedPerson.getRating().getOverallScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(ratingEditCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code RateCommand}.
     */
    private RatingEditCommand prepareCommand(Index index, EditRatingDescriptor editRatingDescriptor) {
        RatingEditCommand ratingEditCommand = new RatingEditCommand(index, editRatingDescriptor);
        ratingEditCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ratingEditCommand;
    }
}

```
###### /java/seedu/address/logic/commands/RatingDeleteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RateCommand.
 */
public class RatingDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToDeleteRating = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        PersonBuilder firstPerson = new PersonBuilder(personToDeleteRating);
        Person editedPerson = firstPerson.withRating("-1", "-1",
                "-1", "-1").build();

        String expectedMessage = String.format(RatingDeleteCommand.MESSAGE_DELETE_RATING_SUCCESS,
                personToDeleteRating.getName());

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToDeleteRating, editedPerson);

        assertCommandSuccess(ratingDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(ratingDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDeleteRating = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        PersonBuilder firstPerson = new PersonBuilder(personToDeleteRating);
        Person editedPerson = firstPerson.withRating("-1", "-1",
                "-1", "-1").build();

        String expectedMessage = String.format(RatingDeleteCommand.MESSAGE_DELETE_RATING_SUCCESS,
                personToDeleteRating.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToDeleteRating, editedPerson);

        assertCommandSuccess(ratingDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of list of candidates
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RatingDeleteCommand ratingDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(ratingDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personNotRated_throwsCommandException() {
        Person personNotRated = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_THIRD_PERSON);

        String expectedMessage = String.format(RatingDeleteCommand.MESSAGE_PERSON_NOT_RATED,
                personNotRated.getName());

        assertCommandFailure(ratingDeleteCommand, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToDeleteRating = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonBuilder firstPerson = new PersonBuilder(personToDeleteRating);
        Person editedPerson = firstPerson.withRating("-1", "-1",
                "-1", "-1").build();


        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // deleteRating -> first person's rating deleted
        ratingDeleteCommand.execute();
        undoRedoStack.push(ratingDeleteCommand);

        // undo -> reverts HR+ back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person's rating deleted again
        expectedModel.updatePerson(personToDeleteRating, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> ratingDeleteCommand not pushed into undoRedoStack
        assertCommandFailure(ratingDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Person#remark} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeletedRating() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RatingDeleteCommand ratingDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person modifiedPerson = new PersonBuilder(personToModify).withRating("-1",
                "-1", "-1", "-1").build();

        // deleteRating -> modifies second person in unfiltered person list / first person in filtered person list
        ratingDeleteCommand.execute();
        undoRedoStack.push(ratingDeleteCommand);

        // undo -> reverts HR+ back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToModify, modifiedPerson);
        assertNotEquals(personToModify, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> modifies same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Returns a {@code RatingDeleteCommand} with the parameter {@code index}.
     */
    private RatingDeleteCommand prepareCommand(Index index) {
        RatingDeleteCommand ratingDeleteCommand = new RatingDeleteCommand(index);
        ratingDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ratingDeleteCommand;
    }
}
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByName_sortSuccessful() throws Exception {
        SortCommand command = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.NAME);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "name", "descending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
    }

    @Test
    public void execute_sortByRating_sortSuccessful() throws Exception {
        SortCommand command = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.RATING);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "rating", "descending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                BENSON, ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_sortByGradePointAverage_sortSuccessful() throws Exception {
        SortCommand command = prepareCommand(SortCommand.SortOrder.ASC, SortCommand.SortField.GPA);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "gpa", "ascending");

        assertCommandSuccess(command, expectedMessage, Arrays.asList(
                GEORGE, DANIEL, ELLE, BENSON, FIONA, ALICE, CARL));
    }

    @Test
    public void isValidSortOrder() {
        assertTrue(SortCommand.isValidSortOrder("asc"));
        assertTrue(SortCommand.isValidSortOrder("desc"));

        assertFalse(SortCommand.isValidSortOrder(""));
        assertFalse(SortCommand.isValidSortOrder("ascc"));

    }

    @Test
    public void isValidSortField() {
        assertTrue(SortCommand.isValidSortField("gpa"));
        assertTrue(SortCommand.isValidSortField("name"));
        assertTrue(SortCommand.isValidSortField("rating"));

        assertFalse(SortCommand.isValidSortField(""));
        assertFalse(SortCommand.isValidSortField("gpaaaa"));
        assertFalse(SortCommand.isValidSortField("ratings"));
    }

    @Test
    public void equals() {
        final SortCommand standardCommand = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.GPA);
        // same values -> returns true
        SortCommand commandWithSameValues = prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.GPA);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different sort order -> returns false
        assertFalse(standardCommand.equals(prepareCommand(SortCommand.SortOrder.ASC, SortCommand.SortField.GPA)));

        // different sort field -> returns false
        assertFalse(standardCommand.equals(prepareCommand(SortCommand.SortOrder.DESC, SortCommand.SortField.NAME)));
    }

    /**
     * Returns a {@code SortCommand}.
     */
    private SortCommand prepareCommand(SortCommand.SortOrder sortOrder, SortCommand.SortField sortField) {
        SortCommand sortCommand = new SortCommand(sortOrder, sortField);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SortCommand command, String expectedMessage, List<Person> expectedList)
            throws Exception {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseSortField_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseSortField("gpa name");
    }

    @Test
    public void parseSortField_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(SortCommand.SortField.GPA, ParserUtil.parseSortField("gpa"));

        // Leading and trailing whitespaces
        assertEquals(SortCommand.SortField.RATING, ParserUtil.parseSortField("   rating   "));
    }

```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseSortOrder_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSortOrder((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((Optional<String>) null));
    }

    @Test
    public void parseSortOrder_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(
            IllegalValueException.class, () -> ParserUtil.parseSortOrder(INVALID_SORT_ORDER));
        Assert.assertThrows(
            IllegalValueException.class, () -> ParserUtil.parseSortOrder(Optional.of(INVALID_SORT_ORDER)));
    }

    @Test
    public void parseSortOrder_validValueWithoutWhitespace_returnsSortOrder() throws Exception {
        SortCommand.SortOrder expectedSortOrder = SortCommand.SortOrder.ASC;
        assertEquals(expectedSortOrder, ParserUtil.parseSortOrder(VALID_SORT_ORDER));
        assertEquals(Optional.of(expectedSortOrder), ParserUtil.parseSortOrder(Optional.of(VALID_SORT_ORDER)));
    }

    @Test
    public void parseSortOrder_validValueWithWhitespace_returnsSortOrder() throws Exception {
        SortCommand.SortOrder expectedSortOrder = SortCommand.SortOrder.ASC;
        String sortOrderWithWhitespace = WHITESPACE + VALID_SORT_ORDER + WHITESPACE;
        assertEquals(expectedSortOrder, ParserUtil.parseSortOrder(sortOrderWithWhitespace));
        assertEquals(Optional.of(expectedSortOrder), ParserUtil.parseSortOrder(Optional.of(sortOrderWithWhitespace)));
    }

```
###### /java/seedu/address/logic/parser/RatingEditCommandParserTest.java
``` java
public class RatingEditCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingEditCommand.MESSAGE_USAGE);
    private static final String MESSAGE_MISSING_INDEX =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingEditCommand.MESSAGE_MISSING_INDEX);

    private RatingEditCommandParser parser = new RatingEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_MISSING_INDEX);

        // no field specified
        assertParseFailure(parser, "1", RatingEditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_RATING_DESC, Rating.MESSAGE_RATING_CONSTRAINTS);
        assertParseFailure(parser, "1" + " " + PREFIX_TECHNICAL_SKILLS_SCORE
                + " " + PREFIX_COMMUNICATION_SKILLS_SCORE
                + " " + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, RatingEditCommand.MESSAGE_EMPTY_SCORE);
    }

}
```
###### /java/seedu/address/logic/parser/RatingDeleteCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RatingDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RatingDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RatingDeleteCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingDeleteCommand.MESSAGE_USAGE);

    private RatingDeleteCommandParser parser = new RatingDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRatingCommand() {
        assertParseSuccess(parser, "1", new RatingDeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
    }
}
```
###### /java/seedu/address/logic/parser/RateCommandParserTest.java
``` java
public class RateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_RATING_DESC, Rating.MESSAGE_RATING_CONSTRAINTS);
        assertParseFailure(parser, "1" + " " + PREFIX_TECHNICAL_SKILLS_SCORE
                + " " + PREFIX_COMMUNICATION_SKILLS_SCORE
                + " " + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE
                + " " + PREFIX_EXPERIENCE_SCORE, RatingEditCommand.MESSAGE_EMPTY_SCORE);
    }

}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_rate() throws Exception {
        final Rating rating = new Rating(4.5, 4,
                3.5, 4);
        RateCommand command = (RateCommand) parser.parseCommand(RateCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_TECHNICAL_SKILLS_SCORE + rating.technicalSkillsScore + " "
                + PREFIX_COMMUNICATION_SKILLS_SCORE + rating.communicationSkillsScore + " "
                + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE + rating.problemSolvingSkillsScore + " "
                + PREFIX_EXPERIENCE_SCORE + rating.experienceScore);
        assertEquals(new RateCommand(INDEX_FIRST_PERSON, rating), command);
    }

    @Test
    public void parseCommand_deleteRating() throws Exception {
        RatingDeleteCommand command = (RatingDeleteCommand) parser.parseCommand(
                RatingDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new RatingDeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        SortCommand command =
                (SortCommand) parser.parseCommand(
                        SortCommand.COMMAND_WORD + " " + SORT_FIELD_NAME + " " + PREFIX_SORT_ORDER
                                + SortCommand.SORT_ORDER_ASC);
        assertEquals(new SortCommand(SortCommand.SortOrder.ASC, SortCommand.SortField.NAME), command);
    }

```

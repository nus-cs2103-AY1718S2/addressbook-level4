# shookshire
###### \java\seedu\address\logic\commands\AddClientCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddClientCommandIntegrationTest {


    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Client validStudent = new ClientBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addStudent(validStudent);

        assertCommandSuccess(prepareCommand(validStudent, model), model,
                String.format(AddClientCommand.MESSAGE_SUCCESS_STUDENT, validStudent), expectedModel);

        Client validTutor = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND)
                .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_GRADE_BOB)
                .withCategory(VALID_CATEGORY_TUTOR_BOB).build();

        expectedModel.addTutor(validTutor);

        assertCommandSuccess(prepareCommand(validTutor, model), model,
                String.format(AddClientCommand.MESSAGE_SUCCESS_TUTOR, validTutor), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Client personInList = model.getAddressBook().getStudentList().get(0);
        assertCommandFailure(prepareCommand(personInList, model), model, AddClientCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddClientCommand prepareCommand(Client client, Model model) {
        AddClientCommand command = new AddClientCommand(client);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\AddClientCommandTest.java
``` java
public class AddClientCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullClient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddClientCommand(null);
    }

    @Test
    public void execute_clientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingClientAdded modelStub = new ModelStubAcceptingClientAdded();
        Client validClient = new ClientBuilder().build();

        CommandResult commandResultStudent = getAddClientCommandForStudent(validClient, modelStub).execute();

        assertEquals(String.format(AddClientCommand.MESSAGE_SUCCESS_STUDENT, validClient),
                commandResultStudent.feedbackToUser);
        assertEquals(Arrays.asList(validClient), modelStub.studentsAdded);

        Client validTutor = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND)
                .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_GRADE_BOB)
                .withCategory(VALID_CATEGORY_TUTOR_BOB).build();

        CommandResult commandResultTutor = getAddClientCommandForTutor(validTutor, modelStub).execute();

        assertEquals(String.format(AddClientCommand.MESSAGE_SUCCESS_TUTOR, validTutor),
                commandResultTutor.feedbackToUser);
        assertEquals(Arrays.asList(validTutor), modelStub.tutorsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateClientException();
        Client validClient = new ClientBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddClientCommand.MESSAGE_DUPLICATE_PERSON);

        getAddClientCommandForStudent(validClient, modelStub).execute();
    }

    @Test
    public void equals() {
        Client alice = new ClientBuilder().withName("Alice").build();
        Client bob = new ClientBuilder().withName("Bob").build();
        AddClientCommand addAliceCommand = new AddClientCommand(alice);
        AddClientCommand addBobCommand = new AddClientCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddClientCommand addAliceCommandCopy = new AddClientCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddClientCommand with the details of the given student.
     */
    private AddClientCommand getAddClientCommandForStudent(Client client, Model model) {
        AddClientCommand command = new AddClientCommand(client);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new AddClientCommand with the details of the given tutor.
     */
    private AddClientCommand getAddClientCommandForTutor(Client client, Model model) {
        AddClientCommand command = new AddClientCommand(client);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteClient(Client target, Category category) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteClosedClient(Client target, Category category) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateClient(Client target, Client editedPerson, Category category)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addTutor(Client tutor) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addStudent(Client student) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addClosedTutor(Client tutor) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addClosedStudent(Client student) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredStudentList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredStudentList(Predicate<Client> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredTutorList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTutorList(Predicate<Client> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClosedTutorList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredClosedTutorList(Predicate<Client> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClosedStudentList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredClosedStudentList(Predicate<Client> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateRankedStudentList() {
            fail("This method should not be called.");
        }

        @Override
        public void updateRankedTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByNameFilteredClientTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByLocationFilteredClientTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByGradeFilteredClientTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortBySubjectFilteredClientTutorList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByNameFilteredClientStudentList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByLocationFilteredClientStudentList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortByGradeFilteredClientStudentList() {
            fail("This method should not be called.");
        }

        @Override
        public void sortBySubjectFilteredClientStudentList() {
            fail("This method should not be called.");
        }

        public void resetHighLight() {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateClientException extends AddClientCommandTest.ModelStub {
        @Override
        public void addTutor(Client tutor) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public void addStudent(Client student) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the client being added.
     */
    private class ModelStubAcceptingClientAdded extends AddClientCommandTest.ModelStub {
        final ArrayList<Client> tutorsAdded = new ArrayList<>();
        final ArrayList<Client> studentsAdded = new ArrayList<>();

        @Override
        public void addTutor(Client tutor) throws DuplicatePersonException {
            requireNonNull(tutor);
            tutorsAdded.add(tutor);
        }

        @Override
        public void addStudent(Client student) throws DuplicatePersonException {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\parser\AddClientCommandParserTest.java
``` java
public class AddClientCommandParserTest {
    private AddClientCommandParser parser = new AddClientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Client expectedPerson = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND)
                .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_GRADE_BOB)
                .withCategory(VALID_CATEGORY_BOB).build();

        Client expectedTutor = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND)
                .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_GRADE_BOB)
                .withCategory(VALID_CATEGORY_TUTOR_BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, new AddClientCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, new AddClientCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, new AddClientCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, new AddClientCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, new AddClientCommand(expectedPerson));

        // valid fields added to tutor
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_TUTOR_BOB, new AddClientCommand(expectedTutor));

        // multiple tags - all accepted
        Client expectedPersonMultipleTags = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_GRADE_BOB)
                .withCategory(VALID_CATEGORY_BOB).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, new AddClientCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Client expectedPerson = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags()
                .withLocation(VALID_LOCATION_AMY).withGrade(VALID_GRADE_AMY).withSubject(VALID_GRADE_AMY)
                .withCategory(VALID_CATEGORY_AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + LOCATION_DESC_AMY + GRADE_DESC_AMY + SUBJECT_DESC_AMY + CATEGORY_DESC_AMY,
                new AddClientCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB
                + CATEGORY_DESC_BOB, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + LOCATION_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB + CATEGORY_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + LOCATION_DESC_BOB + GRADE_DESC_BOB
                        + SUBJECT_DESC_BOB + CATEGORY_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RemoveCommandParserTest.java
``` java
public class RemoveCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveCommand() {
        assertParseSuccess(parser, "1 c/s s/math",
                new RemoveCommand(INDEX_FIRST_PERSON, new Subject("math"), new Category("s")));
        assertParseSuccess(parser, "1 c/t s/math",
                new RemoveCommand(INDEX_FIRST_PERSON, new Subject("math"), new Category("t")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing index
        assertParseFailure(parser, "c/s s/math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        // Missing subject
        assertParseFailure(parser, "1 c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        // Missing category
        assertParseFailure(parser, "1 s/math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\CategoryTest.java
``` java
public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Category(invalidSubject));
    }

    @Test
    public void isValidCategory() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // invalid subject
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only
        assertFalse(Category.isValidCategory("a")); // character apart from s or t
        assertFalse(Category.isValidCategory("st")); // more than just character s or t

        // valid subject
        assertTrue(Category.isValidCategory("s"));
        assertTrue(Category.isValidCategory("t"));
    }
}
```
###### \java\seedu\address\model\person\GradeTest.java
``` java
public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Grade(invalidSubject));
    }

    @Test
    public void isValidGrade() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // invalid subject
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only
        assertFalse(Grade.isValidGrade("pri5")); // invalid format
        assertFalse(Grade.isValidGrade("primary 3")); // spacing between "primary" and "3"

        // one or more invalid subject
        assertFalse(Grade.isValidGrade("pri4 p2 p1 s3")); // one invalid grade
        assertFalse(Grade.isValidGrade("pre2 asdo feiwo")); // many invalid grade
        assertFalse(Grade.isValidGrade("p2 p2")); // multiple same grade

        // valid subject
        assertTrue(Grade.isValidGrade("p3")); //alias
        assertTrue(Grade.isValidGrade("primary3")); // full grade
        assertTrue(Grade.isValidGrade("secondary3")); // full grade
        assertTrue(Grade.isValidGrade("p3 s3 s1 p4")); // multiple valid grade
    }
}
```
###### \java\seedu\address\model\person\LocationTest.java
``` java
public class LocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidLocation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }

    @Test
    public void isValidLocation() {
        // null location
        Assert.assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid location
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("sodv")); // invalid location
        assertFalse(Location.isValidLocation("north asdf")); // one invalid location
        assertFalse(Location.isValidLocation("fdsaob efowfds idb south")); // multiple invalid location
        assertFalse(Location.isValidLocation("north south north")); // repeated location

        // valid location
        assertTrue(Location.isValidLocation("north"));
        assertTrue(Location.isValidLocation("south"));
        assertTrue(Location.isValidLocation("west"));
        assertTrue(Location.isValidLocation("east"));
        assertTrue(Location.isValidLocation("central"));
        assertTrue(Location.isValidLocation("central north south")); // multiple valid location
    }
}
```
###### \java\seedu\address\model\person\SubjectTest.java
``` java
public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subject
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only

        // valid subject
        assertTrue(Subject.isValidSubject("math"));
        assertTrue(Subject.isValidSubject("-")); // one character
        assertTrue(Subject.isValidSubject("math, physics, chemistry, english, chinese")); // long subject
    }
}
```
###### \java\seedu\address\model\UniqueClientListTest.java
``` java
public class UniqueClientListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueClientList uniqueClientList = new UniqueClientList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueClientList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\testutil\ClientBuilder.java
``` java
/**
 * A utility class to help with building Client objects.
 */
public class ClientBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_LOCATION = "north";
    public static final String DEFAULT_GRADE = "p3";
    public static final String DEFAULT_SUBJECT = "physics";
    public static final String DEFAULT_CATEGORY = "s";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Location location;
    private Grade grade;
    private Subject subject;
    private Category category;
    private Set<Tag> tags;

    public ClientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        location = new Location(DEFAULT_LOCATION);
        grade = new Grade(DEFAULT_GRADE);
        subject = new Subject(DEFAULT_SUBJECT);
        category = new Category(DEFAULT_CATEGORY);
    }

    /**
     * Initializes the ClientBuilder with the data of {@code ClientToCopy}.
     */
    public ClientBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        phone = clientToCopy.getPhone();
        email = clientToCopy.getEmail();
        address = clientToCopy.getAddress();
        tags = new HashSet<>(clientToCopy.getTags());
        location = clientToCopy.getLocation();
        grade = clientToCopy.getGrade();
        subject = clientToCopy.getSubject();
    }

    /**
     * Sets the {@code Name} of the {@code Client} that we are building.
     */
    public ClientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Client} that we are building.
     */
    public ClientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Client} that we are building.
     */
    public ClientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Client} that we are building.
     */
    public ClientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Client} that we are building.
     */
    public ClientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Client} that we are building.
     */
    public ClientBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code Client} that we are building.
     */
    public ClientBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Client} that we are building.
     */
    public ClientBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Client} that we are building.
     */
    public ClientBuilder withCategory(String category) {
        this.category = new Category(category);
        return this;
    }

    public Client build() {
        return new Client(name, phone, email, address, tags, location, grade, subject, category);
    }

}
```
###### \java\seedu\address\testutil\ClientUtil.java
``` java
/**
 * A utility class for Client.
 */
public class ClientUtil {

    /**
     * Returns an add command string for adding the {@code client}.
     */
    public static String getAddClientCommand(Client client) {
        return AddClientCommand.COMMAND_WORD + " " + getClientDetails(client);
    }

    /**
     * Returns the part of command string for the given {@code client}'s details.
     */
    public static String getClientDetails(Client client) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CATEGORY + client.getCategory().value + " ");
        sb.append(PREFIX_NAME + client.getName().fullName + " ");
        sb.append(PREFIX_PHONE + client.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + client.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + client.getAddress().value + " ");
        client.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        sb.append(PREFIX_LOCATION + client.getLocation().value + " ");
        sb.append(PREFIX_GRADE + client.getGrade().value + " ");
        sb.append(PREFIX_SUBJECT + client.getSubject().value + " ");

        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGrade(String grade) {
        descriptor.setGrade(new Grade(grade));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSubject(String subject) {
        descriptor.setSubject(new Subject(subject));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(new Category(category));
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\ui\ClientCardTest.java
``` java
public class ClientCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Client personWithNoTags = new ClientBuilder().withTags(new String[0]).build();
        ClientCard clientCard = new ClientCard(personWithNoTags, 1);
        uiPartRule.setUiPart(clientCard);
        assertCardDisplay(clientCard, personWithNoTags, 1);

        // with tags
        Client personWithTags = new ClientBuilder().build();
        clientCard = new ClientCard(personWithTags, 2);
        uiPartRule.setUiPart(clientCard);
        assertCardDisplay(clientCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Client person = new ClientBuilder().build();
        ClientCard personCard = new ClientCard(person, 0);

        // same person, same index -> returns true
        ClientCard copy = new ClientCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Client differentPerson = new ClientBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new ClientCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new ClientCard(person, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ClientCard clientCard, Client expectedClient, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(clientCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedClient, personCardHandle);
    }
}
```

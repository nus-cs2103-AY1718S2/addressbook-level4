# aquarinte
###### \java\guitests\guihandles\CommandBoxHandle.java
``` java
    /**
     * Type text into the command box and set caret at the end of text.
     * Overwrite previous input.
     */
    public void setText(String input) {
        guiRobot.interact(() -> getRootNode().setText(input));
        guiRobot.interact(() -> getRootNode().positionCaret(getInput().length()));
    }

    /**
     * Insert text into command box.
     */
    public void insertText(String input) {
        int caretPos = getRootNode().getCaretPosition();
        guiRobot.interact(() -> getRootNode().insertText(caretPos, input));
        guiRobot.interact(() -> getRootNode().positionCaret(getInput().length()));
    }

    /**
     * Remove change listener for autocomplete,
     * so that it will not interfere with JUnit System Tests.
     */
    public void disableAutocomplete() {
        getRootNode().textProperty().removeListener(CommandBox.getAutocompleteListener());
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private final String messageAddpetpatient = "New pet patient added: %1$s \nunder owner: %2$s";
    private final String messageAddappointment = "New appointment made: %1$s\nunder owner: %2$s\nfor pet patient: %3$s";
    private final String messageAddall = "New person added: %1$s\nNew pet patient added: %2$s\n"
            + "New appointment made: %3$s";

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(prepareCommand(validPerson, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(prepareCommand(personInList, model), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

```
###### \java\seedu\address\logic\commands\AddCommandIntegrationTest.java
``` java
    @Test
    public void execute_duplicateNric_throwsCommandException() {
        Nric nric = model.getAddressBook().getPersonList().get(0).getNric();
        Person duplicateNric = new PersonBuilder().withName("Red").withPhone("90002134").withEmail("red@gmail.com")
                .withAddress("24 Pallet Town").withNric(nric.toString()).build();
        assertCommandFailure(prepareCommand(duplicateNric, model), model, AddCommand.MESSAGE_DUPLICATE_NRIC);
    }

    @Test
    public void execute_newPetPatient_success() throws Exception {
        Person validPerson = model.getAddressBook().getPersonList().get(0);
        PetPatient validPetPatient = new PetPatientBuilder().withOwnerNric(validPerson.getNric().toString()).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPetPatient(validPetPatient);

        assertCommandSuccess(prepareCommand(validPetPatient, validPerson.getNric(), model), model,
                String.format(messageAddpetpatient, validPetPatient, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePetPatient_throwsCommandException() {
        PetPatient duplicate = model.getAddressBook().getPetPatientList().get(0);
        assertCommandFailure(prepareCommand(duplicate, duplicate.getOwner(), model),
                model, AddCommand.MESSAGE_DUPLICATE_PET_PATIENT);
    }

    @Test
    public void execute_newPetPatientWithNricDoesNotExist_throwsCommandException() {
        // Nric does not exists in address book
        String nricDoesNotExist = VALID_NRIC_DION;
        PetPatient validPetPatient = new PetPatientBuilder().withOwnerNric(nricDoesNotExist).build();

        assertCommandFailure(prepareCommand(validPetPatient, validPetPatient.getOwner(), model),
                model, AddCommand.MESSAGE_INVALID_NRIC);
    }

    @Test
    public void execute_newAppointment_success() throws Exception {
        PetPatient existing = model.getAddressBook().getPetPatientList().get(0);
        Person owner = model.getPersonWithNric(existing.getOwner());
        Appointment appt = new AppointmentBuilder().withOwnerNric(owner.getNric().toString())
                .withPetPatientName(existing.getName().toString()).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAppointment(appt);

        assertCommandSuccess(prepareCommand(appt, existing.getOwner(), existing.getName(), model), model,
                String.format(messageAddappointment, appt, owner, existing), expectedModel);
    }

    @Test
    public void execute_newAppointmentWithNricDoesNotExists_throwsCommandException() {
        String nricDoesNotExist = VALID_NRIC_DION;
        String petPatientNameExists = model.getAddressBook().getPetPatientList().get(0).getName().toString();
        Appointment validAppt = new AppointmentBuilder().withOwnerNric(nricDoesNotExist)
                .withPetPatientName(petPatientNameExists).build();

        assertCommandFailure(prepareCommand(validAppt, validAppt.getOwnerNric(), validAppt.getPetPatientName(), model),
                model, AddCommand.MESSAGE_INVALID_NRIC);
    }

    @Test
    public void execute_newAppointmentWithPetPatientNameDoesNotExists_throwsCommandException() {
        String petNameDoesNotExist = VALID_NAME_NERO;
        String ownerNricExists = model.getAddressBook().getPersonList().get(0).getNric().toString();
        Appointment validAppt = new AppointmentBuilder().withOwnerNric(ownerNricExists)
                .withPetPatientName(petNameDoesNotExist).build();

        assertCommandFailure(prepareCommand(validAppt, validAppt.getOwnerNric(), validAppt.getPetPatientName(), model),
                model, AddCommand.MESSAGE_INVALID_PET_PATIENT);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() {
        Appointment duplicate = model.getAddressBook().getAppointmentList().get(0);
        assertCommandFailure(prepareCommand(duplicate, duplicate.getOwnerNric(), duplicate.getPetPatientName(), model),
                model, AddCommand.MESSAGE_DUPLICATE_APPOINTMENT);
    }

    @Test
    public void execute_duplicateDateTimeAppointment_throwsCommandException() {
        PetPatient existing = model.getAddressBook().getPetPatientList().get(0);
        Person owner = model.getPersonWithNric(existing.getOwner());
        String dupDateTime = model.getAddressBook().getAppointmentList().get(0).getFormattedLocalDateTime();

        Appointment appt = new AppointmentBuilder().withRemark(VALID_REMARK_ONE).withDateTime(dupDateTime)
                .withAppointmentTags(VALID_TAG_CHECKUP).withOwnerNric(owner.getNric().toString())
                .withPetPatientName(existing.getName().toString()).build();

        assertCommandFailure(prepareCommand(appt, owner.getNric(), existing.getName(), model),
                model, AddCommand.MESSAGE_DUPLICATE_DATETIME);
    }

    @Test
    public void execute_addAllNew_success() throws Exception {
        Person newPerson = new PersonBuilder().withName(VALID_NAME_CHARLIE).withPhone(VALID_PHONE_CHARLIE)
                .withEmail(VALID_EMAIL_CHARLIE).withAddress(VALID_ADDRESS_CHARLIE).withNric(VALID_NRIC_CHARLIE).build();

        PetPatient newPetPatient = new PetPatientBuilder().withName(VALID_NAME_NERO).withSpecies(VALID_SPECIES_NERO)
                .withBreed(VALID_BREED_NERO).withColour(VALID_COLOUR_NERO).withBloodType(VALID_BLOODTYPE_NERO)
                .withOwnerNric(newPerson.getNric().toString()).build();

        Appointment newAppt = new AppointmentBuilder().withDateTime(VALID_DATE_ONE).withRemark(VALID_REMARK_ONE)
                .withAppointmentTags(VALID_TAG_CHECKUP).withOwnerNric(newPerson.getNric().toString())
                .withPetPatientName(newPetPatient.getName().toString()).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(newPerson);
        expectedModel.addPetPatient(newPetPatient);
        expectedModel.addAppointment(newAppt);

        assertCommandSuccess(prepareCommand(newPerson, newPetPatient, newAppt, model), model,
                String.format(messageAddall, newPerson, newPetPatient, newAppt), expectedModel);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddCommand prepareCommand(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code petpatient} into the {@code model}.
     */
    private AddCommand prepareCommand(PetPatient petPatient, Nric ownerNric, Model model) {
        AddCommand command = new AddCommand(petPatient, ownerNric);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code appointment} into the {@code model}.
     */
    private AddCommand prepareCommand(Appointment appt, Nric ownerNric, PetPatientName petPatientName, Model model) {
        AddCommand command = new AddCommand(appt, ownerNric, petPatientName);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person}, {@code petpatient} and
     * {@code appointment} into the {@code model}.
     */
    private AddCommand prepareCommand(Person person, PetPatient petPatient, Appointment appt, Model model) {
        AddCommand command = new AddCommand(person, petPatient, appt);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final String messageAddpetpatient = "New pet patient added: %1$s \nunder owner: %2$s";
    private final String messageAddappointment = "New appointment made: %1$s\nunder owner: %2$s\nfor pet patient: %3$s";
    private final String messageAddall = "New person added: %1$s\nNew pet patient added: %2$s\n"
            + "New appointment made: %3$s";

    @Test
    public void constructor_nullPersonPetPatientAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand((Person) null, (PetPatient) null, (Appointment) null);
    }

    @Test
    public void constructor_nullAppointmentNricPetPatientName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand((Appointment) null, (Nric) null, (PetPatientName) null);
    }

    @Test
    public void constructor_nullPetPatientNric_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand((PetPatient) null, (Nric) null);
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand((Person) null);
    }

    @Test
    public void execute_objectsAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAllAdded modelStub = new ModelStubAcceptingAllAdded();

        //add a new person (a)
        Person validPerson = new PersonBuilder().build();
        CommandResult resultToAddPerson = getAddCommandForPerson(validPerson, modelStub).execute();
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), resultToAddPerson.feedbackToUser);
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);

        //add a new pet patient (b) under person (a)
        PetPatient validPetPatient = new PetPatientBuilder().build();
        CommandResult resultToAddPetPatient = getAddCommandForPetPatient(validPetPatient, validPerson.getNric(),
                modelStub).execute();
        assertEquals(String.format(messageAddpetpatient, validPetPatient, validPerson),
                resultToAddPetPatient.feedbackToUser);
        assertEquals(Arrays.asList(validPetPatient), modelStub.petPatientsAdded);

        //add a new appt for pet patient (b) under person (a)
        Appointment validAppointment = new AppointmentBuilder().build();
        CommandResult resultToAddAppointment = getAddCommandForAppointment(validAppointment, validPerson.getNric(),
                validPetPatient.getName(), modelStub).execute();
        assertEquals(String.format(messageAddappointment, validAppointment, validPerson, validPetPatient),
                resultToAddAppointment.feedbackToUser);
        assertEquals(Arrays.asList(validAppointment), modelStub.appointmentsAdded);

        //add new person, new pet patient and new appointment
        Person newPerson = TypicalPersons.BENSON;
        PetPatient newPetPatient = TypicalPetPatients.JEWEL;
        Appointment newAppt = TypicalAppointments.BENSON_APP;
        CommandResult resultToAddAll = getAddCommandForNewPersonPetPatientAppointment(newPerson, newPetPatient, newAppt,
                modelStub).execute();
        assertEquals(String.format(messageAddall, newPerson, newPetPatient, newAppt), resultToAddAll.feedbackToUser);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        Person validPerson = new PersonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        getAddCommandForPerson(validPerson, modelStub).execute();
    }

    @Test
    public void execute_duplicateNric_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateNricException();
        Person validPerson = new PersonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_NRIC);

        getAddCommandForPerson(validPerson, modelStub).execute();
    }

    @Test
    public void execute_duplicatePetPatient_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePetPatientException();
        Person validPerson = new PersonBuilder().build();
        PetPatient validPetPatient = new PetPatientBuilder().build();
        Appointment validAppointment = new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PET_PATIENT);

        getAddCommandForNewPersonPetPatientAppointment(validPerson, validPetPatient, validAppointment,
                modelStub).execute();
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAppointmentException();
        Person validPerson = new PersonBuilder().build();
        PetPatient validPetPatient = new PetPatientBuilder().build();
        Appointment validAppointment = new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_APPOINTMENT);

        getAddCommandForNewPersonPetPatientAppointment(validPerson, validPetPatient, validAppointment,
                modelStub).execute();
    }

    @Test
    public void execute_duplicateAppointmentDateTime_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateDateTimeException();
        Person validPerson = new PersonBuilder().build();
        PetPatient validPetPatient = new PetPatientBuilder().build();
        Appointment validAppointment = new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_DATETIME);

        getAddCommandForNewPersonPetPatientAppointment(validPerson, validPetPatient, validAppointment,
                modelStub).execute();
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").withNric("T0011223G").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        PetPatient joseph = new PetPatientBuilder().build();
        PetPatient tia = new PetPatientBuilder().withName("Tia").build();
        AddCommand addJosephCommand = new AddCommand(joseph, alice.getNric());
        AddCommand addTiaCommand = new AddCommand(tia, bob.getNric());

        Appointment appt = new AppointmentBuilder().build();
        Appointment appt2 = new AppointmentBuilder().withDateTime("2018-11-11 15:30").build();
        AddCommand addApptCommand = new AddCommand(appt, alice.getNric(), joseph.getName());
        AddCommand addAppt2Command = new AddCommand(appt2, bob.getNric(), tia.getName());

        AddCommand addAllCommand = new AddCommand(alice, joseph, appt);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));
        assertTrue(addJosephCommand.equals(addJosephCommand));
        assertTrue(addApptCommand.equals(addApptCommand));
        assertTrue(addAllCommand.equals(addAllCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        AddCommand addJosephCommandCopy = new AddCommand(joseph, alice.getNric());
        assertTrue(addJosephCommand.equals(addJosephCommandCopy));

        AddCommand addApptCommandCopy = new AddCommand(appt, alice.getNric(), joseph.getName());
        assertTrue(addApptCommand.equals(addApptCommandCopy));

        AddCommand addAllCommandCopy = new AddCommand(alice, joseph, appt);
        assertTrue(addAllCommand.equals(addAllCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));
        assertFalse(addJosephCommand.equals(2));
        assertFalse(addApptCommand.equals(new AppointmentBuilder().build()));
        assertFalse(addAllCommand.equals("hello"));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));
        assertFalse(addJosephCommand.equals(null));
        assertFalse(addApptCommand.equals(null));
        assertFalse(addAllCommand.equals(null));

        // different person/pet patient/appointment -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
        assertFalse(addJosephCommand.equals(addTiaCommand));
        assertFalse(addApptCommand.equals(addAppt2Command));

    }

    /**
     * Generates a new AddCommand with the details of the given person, pet patient and appointment.
     */
    private AddCommand getAddCommandForNewPersonPetPatientAppointment(Person person, PetPatient petPatient,
                                                                      Appointment appt, Model model) {
        AddCommand command = new AddCommand(person, petPatient, appt);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new AddCommand with the details of the given appointment, owner's nric and pet patient name.
     */
    private AddCommand getAddCommandForAppointment(Appointment appt, Nric ownerNric, PetPatientName petPatientName,
                                                   Model model) {
        AddCommand command = new AddCommand(appt, ownerNric, petPatientName);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new AddCommand with the details of the given pet patient and owner's nric.
     */
    private AddCommand getAddCommandForPetPatient(PetPatient petPatient, Nric ownerNric, Model model) {
        AddCommand command = new AddCommand(petPatient, ownerNric);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException, DuplicateNricException {
            fail("This method should not be called.");
        }

        @Override
        public Person getPersonWithNric(Nric nric) {
            return null;
        }

        @Override
        public void addPetPatient(PetPatient petPatient) throws DuplicatePetPatientException {
            fail("This method should not be called.");
        }

        @Override
        public PetPatient getPetPatientWithNricAndName(Nric nric, PetPatientName petPatientName) {
            return null;
        }

        @Override
        public void deletePetPatient(PetPatient target) {
            fail("This method should not be called.");
        }

        @Override
        public List<PetPatient> deletePetPatientDependencies(Person key) {
            return null;
        }

        @Override
        public List<Appointment> deleteAppointmentDependencies(PetPatient target) {
            return null;
        }

        @Override
        public void addAppointment(Appointment appointment) throws DuplicateAppointmentException,
                DuplicateDateTimeException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment target) {
            fail("This method should not be called.");
        }

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
        public void deletePerson(Person target) {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePetPatient(PetPatient target, PetPatient editedPetPatient)
                throws DuplicatePetPatientException {
            fail("This method should not be called.");
        }

        @Override
        public void updateAppointment(Appointment target, Appointment editedAppointment)
                throws DuplicateAppointmentException {
            fail("This method should not be called.");
        }

        @Override
        public ArrayList<PetPatient> getPetPatientsWithNric(Nric ownerNric) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ArrayList<Appointment> getAppointmentsWithNric(Nric ownerNric) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ArrayList<Appointment> getAppointmentsWithNricAndPetName(Nric ownerNric, PetPatientName petPatientName) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public Appointment getClashingAppointment(LocalDateTime dateTime) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<PetPatient> getFilteredPetPatientList() {
            return null;
        }

        @Override
        public void updateFilteredPetPatientList(Predicate<PetPatient> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public List<Tag> getTagList() {
            return null;
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a DuplicateNricException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateNricException extends ModelStub {
        @Override
        public void addPerson(Person person) throws DuplicateNricException {
            throw new DuplicateNricException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a DuplicatePetPatientException when trying to add a pet patient.
     */
    private class ModelStubThrowingDuplicatePetPatientException extends ModelStub {
        @Override
        public void addPerson(Person person) {
            //do nothing
        }

        @Override
        public void addPetPatient(PetPatient petPatient) throws DuplicatePetPatientException {
            throw new DuplicatePetPatientException();
        }

        @Override
        public void addAppointment(Appointment appt) {
            //do nothing
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a DuplicateAppointmentException when trying to add an appointment.
     */
    private class ModelStubThrowingDuplicateAppointmentException extends ModelStub {
        @Override
        public void addPerson(Person person) {
            //do nothing
        }

        @Override
        public void addPetPatient(PetPatient petPatient) {
            //do nothing
        }

        @Override
        public void addAppointment(Appointment appt) throws DuplicateAppointmentException {
            throw new DuplicateAppointmentException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a DuplicateDateTimeException when trying to add an appointment.
     */
    private class ModelStubThrowingDuplicateDateTimeException extends ModelStub {
        @Override
        public void addPerson(Person person) {
            //do nothing
        }

        @Override
        public void addPetPatient(PetPatient petPatient) {
            //do nothing
        }

        @Override
        public void addAppointment(Appointment appt) throws DuplicateDateTimeException {
            throw new DuplicateDateTimeException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the persons, pet patients and appointments being added.
     */
    private class ModelStubAcceptingAllAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<PetPatient> petPatientsAdded = new ArrayList<>();
        final ArrayList<Appointment> appointmentsAdded = new ArrayList<>();

        @Override
        public void addPerson(Person person) throws DuplicatePersonException, DuplicateNricException {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public Person getPersonWithNric(Nric nric) {
            for (Person p : personsAdded) {
                if (p.getNric().equals(nric)) {
                    return p;
                }
            }
            return null;
        }

        @Override
        public void addPetPatient(PetPatient petPatient) throws DuplicatePetPatientException {
            requireNonNull(petPatient);
            petPatientsAdded.add(petPatient);
        }

        @Override
        public PetPatient getPetPatientWithNricAndName(Nric nric, PetPatientName petPatientName) {
            for (PetPatient p : petPatientsAdded) {
                if (p.getOwner().equals(nric) && p.getName().equals(petPatientName)) {
                    return p;
                }
            }
            return null;
        }

        @Override
        public void addAppointment(Appointment appt) throws DuplicateAppointmentException, DuplicateDateTimeException {
            requireNonNull(appt);
            appointmentsAdded.add(appt);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_allPetPatientFieldsPresent_success() {

        PetPatient expectedPetPatient = new PetPatientBuilder().withName(VALID_NAME_JOKER)
                .withSpecies(VALID_SPECIES_JOKER).withBreed(VALID_BREED_JOKER).withColour(VALID_COLOUR_JOKER)
                .withBloodType(VALID_BLOODTYPE_JOKER).withTags(VALID_TAG_FIV).withOwnerNric(VALID_NRIC_FION).build();

        Nric fion = new Nric(VALID_NRIC_FION);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple names - last name accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_NERO + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                        + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple species - last species accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_NERO + SPECIES_DESC_JOKER
                        + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple breed - last breed accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_NERO
                        + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple colour - last colour accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_JOKER
                        + COLOUR_DESC_NERO + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple blood type - last blood type accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_JOKER
                        + COLOUR_DESC_JOKER + BLOODTYPE_DESC_NERO + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + OPTION_OWNER
                        + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));

        // multiple tags - all accepted
        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_JOKER
                + COLOUR_DESC_JOKER + BLOODTYPE_DESC_NERO + BLOODTYPE_DESC_JOKER + TAG_DESC_FIV + TAG_DESC_DEPRESSION
                + OPTION_OWNER + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));
    }

    @Test
    public void parse_optionalPetPatientFieldsMissing_success() {
        // zero tags
        PetPatient expectedPetPatient = new PetPatientBuilder().withName(VALID_NAME_JOKER)
                .withSpecies(VALID_SPECIES_JOKER).withBreed(VALID_BREED_JOKER).withColour(VALID_COLOUR_JOKER)
                .withBloodType(VALID_BLOODTYPE_JOKER).withOwnerNric(VALID_NRIC_FION).build();

        Nric fion = new Nric(VALID_NRIC_FION);

        assertParseSuccess(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, new AddCommand(expectedPetPatient, fion));
    }

    @Test
    public void parse_compulsoryPetPatientFieldMissing_failure() {
        String invalidPetPatient = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_PETPATIENT);
        String invalidAddCommand = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        String missingNricPrefix = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_MISSING_NRIC_PREFIX);

        // missing name prefix
        assertParseFailure(parser, OPTION_PET + VALID_NAME_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing species prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + VALID_SPECIES_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing breed prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + VALID_BREED_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing colour prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + VALID_COLOUR_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing blood type prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + VALID_BLOODTYPE_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing nric prefix
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + VALID_NRIC_FION, missingNricPrefix);

        // missing all pet patient prefixes
        assertParseFailure(parser, OPTION_PET + VALID_NAME_JOKER + VALID_SPECIES_JOKER
                + VALID_BREED_JOKER + VALID_COLOUR_JOKER + VALID_BLOODTYPE_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, invalidPetPatient);

        // missing all prefixes - only missing nric prefix reported
        assertParseFailure(parser, OPTION_PET + VALID_NAME_JOKER + VALID_SPECIES_JOKER
                + VALID_BREED_JOKER + VALID_COLOUR_JOKER + VALID_BLOODTYPE_JOKER + OPTION_OWNER
                + VALID_NRIC_FION, missingNricPrefix);

        // missing options
        assertParseFailure(parser, NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + NRIC_DESC_FION,
                invalidAddCommand);
    }

    @Test
    public void parse_invalidPetPatientValue_failure() {
        // invalid name
        assertParseFailure(parser, OPTION_PET + INVALID_NAME_DESC + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + INVALID_NRIC_DESC, Nric.MESSAGE_NRIC_CONSTRAINTS);
    }

    @Test
    public void parse_allAppointmentFieldsPresent_success() {
        Appointment appt = new AppointmentBuilder().withDateTime(VALID_DATE_ONE).withRemark(VALID_REMARK_ONE)
                .withAppointmentTags(VALID_TAG_CHECKUP).withOwnerNric(VALID_NRIC_FION)
                .withPetPatientName(VALID_NAME_JOKER).build();

        Nric fion = new Nric(VALID_NRIC_FION);
        PetPatientName joker = new PetPatientName(VALID_NAME_JOKER);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE
                + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER,
                new AddCommand(appt, fion, joker));

        // multiple date time - last one is accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_TWO + DATE_DESC_ONE + REMARK_DESC_ONE
                        + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER,
                new AddCommand(appt, fion, joker));

        // multiple remarks - last one is accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_TWO + REMARK_DESC_ONE
                        + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER,
                new AddCommand(appt, fion, joker));

        // multiple tags - all accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE + TAG_DESC_VACCINATION
                        + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER,
                new AddCommand(appt, fion, joker));

        // multiple nric - last one is accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE
                        + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_CHARLIE + NRIC_DESC_FION + OPTION_PET
                        + NAME_DESC_JOKER, new AddCommand(appt, fion, joker));

        // multiple pet name - last one is accepted
        assertParseSuccess(parser,  OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE
                + TAG_DESC_CHECKUP + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET
                + NAME_DESC_NERO + NAME_DESC_JOKER, new AddCommand(appt, fion, joker));
    }

    @Test
    public void parse_compulsoryAppointmentFieldMissing_failure() {
        String invalidAppt = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_APPOINTMENT);
        String invalidCommand = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing date time prefix
        assertParseFailure(parser, OPTION_APPOINTMENT + VALID_DATE_ONE + REMARK_DESC_ONE + TAG_DESC_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, invalidAppt);

        // missing remark prefix
        assertParseFailure(parser, OPTION_APPOINTMENT + DATE_DESC_ONE + VALID_REMARK_ONE + TAG_DESC_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, invalidAppt);

        // missing tag prefix
        assertParseFailure(parser, OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE + VALID_TAG_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, invalidAppt);

        // missing options
        assertParseFailure(parser, DATE_DESC_ONE + REMARK_DESC_ONE + TAG_DESC_CHECKUP + NRIC_DESC_FION
                + NAME_DESC_JOKER, invalidCommand);

        // missing -o, -p
        assertParseFailure(parser, OPTION_APPOINTMENT + VALID_DATE_ONE + REMARK_DESC_ONE + TAG_DESC_CHECKUP,
                invalidCommand);
    }

    @Test
    public void parse_invalidAppointmentValue_failure() {
        // invalid remark
        assertParseFailure(parser, OPTION_APPOINTMENT + DATE_DESC_ONE + INVALID_REMARK_DESC + TAG_DESC_CHECKUP
                + OPTION_OWNER + NRIC_DESC_FION + OPTION_PET + NAME_DESC_JOKER, Remark.MESSAGE_REMARK_CONSTRAINTS);
    }

    @Test
    public void parse_invalidCommandOption_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // invalid option
        assertParseFailure(parser, INVALID_OPTION + NAME_DESC_JOKER + SPECIES_DESC_JOKER
                + BREED_DESC_JOKER + COLOUR_DESC_JOKER + BLOODTYPE_DESC_JOKER + OPTION_OWNER
                + NRIC_DESC_FION, expectedMessage);

        // wrong order: -a, -p, -o
        assertParseFailure(parser, OPTION_APPOINTMENT + DATE_DESC_ONE + REMARK_DESC_ONE + TAG_DESC_CHECKUP
                        + OPTION_PET + NAME_DESC_JOKER + SPECIES_DESC_JOKER + BREED_DESC_JOKER + COLOUR_DESC_JOKER
                        + BLOODTYPE_DESC_JOKER + OPTION_OWNER + NRIC_DESC_FION, expectedMessage);
    }

}
```
###### \java\seedu\address\testutil\PetPatientUtil.java
``` java
/**
 * A utility class for Pet Patient.
 */
public class PetPatientUtil {
    /**
     * Returns an add command string for adding the {@code petpatient}.
     */
    public static String getAddCommand(PetPatient petPatient, Nric ownerNric) {
        return AddCommand.COMMAND_WORD + OPTION_PET + " " + getPetPatientDetails(petPatient)
                + OPTION_OWNER + " " + PREFIX_NRIC + ownerNric.toString();
    }

    /**
     * Returns an add command string for adding the {@code petpatient}.
     */
    public static String getAddCommandAlias(PetPatient petPatient, Nric ownerNric) {
        return AddCommand.COMMAND_ALIAS + " " + OPTION_PET + " " + getPetPatientDetails(petPatient)
                + " " + OPTION_OWNER + " " + PREFIX_NRIC + ownerNric.toString();
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPetPatientDetails(PetPatient petPatient) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + petPatient.getName().fullName + " ");
        sb.append(PREFIX_SPECIES + petPatient.getSpecies() + " ");
        sb.append(PREFIX_BREED + petPatient.getBreed() + " ");
        sb.append(PREFIX_COLOUR + petPatient.getColour() + " ");
        sb.append(PREFIX_BLOODTYPE + petPatient.getBloodType() + " ");
        petPatient.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }
}
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    @Test
    public void commandBox_autocompleteCommandWord() {
        //add command
        testAutocompleteForUserInput("a", 1, "add");
        testAutocompleteForUserInput(" ", 1, "add");

        //clear command
        testAutocompleteForUserInput("cl", 1, "clear");

        //delete command
        testAutocompleteForUserInput("d", 1, "delete");

        //edit command
        testAutocompleteForUserInput("e", 1, "edit");
        testAutocompleteForUserInput("ed", 1, "edit");

        //exit command
        testAutocompleteForUserInput("e", 2, "exit");
        testAutocompleteForUserInput("ex", 1, "exit");

        //help command
        testAutocompleteForUserInput("h", 1, "help");
        testAutocompleteForUserInput("he", 1, "help");
        testAutocompleteForUserInput("hel", 1, "help");

        //history command
        testAutocompleteForUserInput("h", 2, "history");
        testAutocompleteForUserInput("hi", 1, "history");
        testAutocompleteForUserInput("hist", 1, "history");

        //list command
        testAutocompleteForUserInput("l", 1, "list");

        //theme command
        testAutocompleteForUserInput("t", 1, "theme");

        //undo
        testAutocompleteForUserInput("u", 1, "undo");

        //redo
        testAutocompleteForUserInput("r", 1, "redo");
    }

    @Test
    public void commandBox_autocompleteOption() {
        testAutocompleteForUserInput("delete -", 1, "delete -a");
        testAutocompleteForUserInput("add -", 2, "add -o");
        testAutocompleteForUserInput("find -", 3, "find -p");
    }

    @Test
    public void commandBox_autocompletePrefix() {
        // prefix n/
        testAutocompleteForUserInput("add -o n", 1, "add -o n/");

        // prefix nr/
        testAutocompleteForUserInput("add -o n", 2, "add -o nr/");
        testAutocompleteForUserInput("add -o nr", 1, "add -o nr/");

        //prefix b/
        testAutocompleteForUserInput("add -p b", 1, "add -p b/");

        //prefix bt/
        testAutocompleteForUserInput("add -p b", 2, "add -p bt/");
    }

    @Test
    public void commandBox_autocompleteNric() {
        // autocomplete suggestions for nric for add command that follows "-o nr/"
        testAutocompleteForUserInput("add -o nr/", 1, "add -o nr/F0184556R");
        testAutocompleteForUserInput("add -o nr/F018", 1, "add -o nr/F0184556R");
        testAutocompleteForUserInput("add -o nr/", 2, "add -o nr/F2345678U");
        testAutocompleteForUserInput("add -o nr/S", 1, "add -o nr/S0123456Q");

        // no nric autocomplete suggestion if add command does not have "-o nr/"
        testAutocompleteForUserInput("add -p nr/S", 1, "add -p nr/S");
        testAutocompleteForUserInput("add -a nr/F", 1, "add -a nr/F");

        // autocomplete suggestions for nric for "edit -p" command
        testAutocompleteForUserInput("edit -p nr/", 1, "edit -p nr/F0184556R");
        testAutocompleteForUserInput("edit -p nr/F018", 1, "edit -p nr/F0184556R");
        testAutocompleteForUserInput("edit -p nr/", 2, "edit -p nr/F2345678U");
        testAutocompleteForUserInput("edit -p nr/S", 1, "edit -p nr/S0123456Q");

        // no nric autocomplete suggestion if edit command does not start with "edit -p"
        testAutocompleteForUserInput("edit -o nr/", 1, "edit -o nr/");
        testAutocompleteForUserInput("edit -o nr/S", 1, "edit -o nr/S");

        // autocomplete suggestions for nric for "find -o" command
        testAutocompleteForUserInput("find -o nr/", 1, "find -o nr/F0184556R");
        testAutocompleteForUserInput("find -o nr/", 3, "find -o nr/G1111111B");
        testAutocompleteForUserInput("find -o nr/T", 3, "find -o nr/T0120956W");
        testAutocompleteForUserInput("find -o nr/T0", 2, "find -o nr/T0123456L");

        // no nric autocomplete suggestion if edit command does not start with "find -o"
        testAutocompleteForUserInput("find -p nr/T0", 2, "find -p nr/T0");
        testAutocompleteForUserInput("find -a nr/S", 2, "find -a nr/S");
    }

    @Test
    public void commandBox_autocompleteTag() {
        testAutocompleteForUserInput("add t/", 1, "add t/Depression");
        testAutocompleteForUserInput("add t/F", 1, "add t/friends");
        testAutocompleteForUserInput("add t/", 2, "add t/Test");
        testAutocompleteForUserInput("add t/ow", 2, "add t/owesMoney");
    }

    /**
     * Checks that {@code userInput} with the {@code numOfTabs} to select an option on autocomplete's context menu
     * will result in {@code actualCommand}.
     */
    private void testAutocompleteForUserInput(String userInput, int numOfTabs, String actualCommand) {
        commandBoxHandle.setText(userInput);

        while (numOfTabs > 0) {
            guiRobot.push(KeyCode.TAB);
            numOfTabs--;
        }
        guiRobot.push(KeyCode.ENTER);

        assertEquals(actualCommand, commandBoxHandle.getInput());
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a pet patient without tags to a non-empty address book, command with leading spaces and
         * trailing spaces -> added
         */
        PetPatient toAddPet = NERO;
        Nric bobNric = BOB.getNric();
        command = "   " + AddCommand.COMMAND_WORD + "  " + OPTION_PET + "  " + NAME_DESC_NERO
                + "  " +  SPECIES_DESC_NERO + "  " + BREED_DESC_NERO + "  " +  COLOUR_DESC_NERO + "  "
                + BLOODTYPE_DESC_NERO + "  " + OPTION_OWNER + "  " + NRIC_DESC_BOB;
        assertCommandSuccess(command, toAddPet, bobNric);

        /* Case: add a pet patient, missing tags -> added */
        assertCommandSuccess(KARUPIN, KARUPIN.getOwner());

        //@author

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(Person)
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NRIC_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_PERSON));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NRIC_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_PERSON));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + NRIC_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_PERSON));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + " " + OPTION_OWNER + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + NRIC_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_PERSON));

```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Returns a command box with autocomplete function disabled.
     */
    public CommandBoxHandle getCommandBox() {
        mainWindowHandle.getCommandBox().disableAutocomplete();
        return mainWindowHandle.getCommandBox();
    }

```

# chialejing
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
/**
 * Edits the details of an existing person, pet patient or appointment in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "ed";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the contact / owner / appointment identified by their respective index numbers. "
            + "Existing values will be overwritten by the input values. "
            + "Note that INDEX must be a positive integer.\n"
            + "To edit the details of an existing contact: "
            + COMMAND_WORD + " -o " + "INDEX "
            + "[" + PREFIX_NAME + "CONTACT_NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "To edit the details of an existing pet patient: "
            + COMMAND_WORD + " -p " + "INDEX "
            + "[" + PREFIX_NAME + "PET_PATIENT_NAME] "
            + "[" + PREFIX_SPECIES + "SPECIES] "
            + "[" + PREFIX_BREED + "BREED] "
            + "[" + PREFIX_COLOUR + "COLOUR] "
            + "[" + PREFIX_BLOODTYPE + "BLOOD_TYPE] "
            + "[" + PREFIX_NRIC + "OWNER_NRIC] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "To edit the details of an existing appointment: "
            + COMMAND_WORD + " -a " + "INDEX "
            + "[" + PREFIX_DATE + "YYYY-MM-DD HH:MM] "
            + "[" + PREFIX_REMARK + "REMARK] "
            // + "[" + PREFIX_NRIC + "OWNER_NRIC] "
            // + "[" + PREFIX_NAME + "PET_PATIENT_NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "\n";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_EDIT_PET_PATIENT_SUCCESS = "Edited Pet Patient: %1$s";
    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_PET_PATIENT = "This pet patient already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book.";

    /**
     * Enum to support the type of edit command that the user wishes to execute.
     */
    public enum Type { EDIT_PERSON, EDIT_PET_PATIENT, EDIT_APPOINTMENT };

    private Index index;
    private EditPersonDescriptor editPersonDescriptor;
    private EditPetPatientDescriptor editPetPatientDescriptor;
    private EditAppointmentDescriptor editAppointmentDescriptor;
    private Type type;

    private Person personToEdit; // original
    private Person editedPerson; // edited
    private PetPatient petPatientToEdit;
    private PetPatient editedPetPatient;
    private Appointment appointmentToEdit;
    private Appointment editedAppointment;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.type = EDIT_PERSON;
    }

    /**
     * @param index of the pet patient in the filtered pet patient list to edit
     * @param editPetPatientDescriptor details to edit the pet patient with
     */
    public EditCommand(Index index, EditPetPatientDescriptor editPetPatientDescriptor) {
        requireNonNull(index);
        requireNonNull(editPetPatientDescriptor);

        this.index = index;
        this.editPetPatientDescriptor = new EditPetPatientDescriptor(editPetPatientDescriptor);
        this.type = EDIT_PET_PATIENT;
    }

    /**
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public EditCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
        this.type = EDIT_APPOINTMENT;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            switch (type) {
            case EDIT_PERSON:
                resolvePersonDependencies();
                model.updatePerson(personToEdit, editedPerson);
                System.out.println(personToEdit.getNric().toString());
                System.out.println(editedPerson.getNric().toString());
                break;
            case EDIT_PET_PATIENT:
                resolvePetPatientDependencies();
                model.updatePetPatient(petPatientToEdit, editedPetPatient);
                break;
            case EDIT_APPOINTMENT:
                checkForClashes();
                model.updateAppointment(appointmentToEdit, editedAppointment);
                break;
            default:
                break;
            }
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (DuplicatePetPatientException dppe) {
            throw new CommandException(MESSAGE_DUPLICATE_PET_PATIENT);
        } catch (PetPatientNotFoundException ppnfe) {
            throw new AssertionError("The target pet patient cannot be missing");
        } catch (DuplicateAppointmentException dae) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        } catch (AppointmentNotFoundException anfe) {
            throw new AssertionError("The target appointment cannot be missing");
        }
        switch (type) {
        case EDIT_PERSON:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
        case EDIT_PET_PATIENT:
            model.updateFilteredPetPatientList(PREDICATE_SHOW_ALL_PET_PATIENTS);
            return new CommandResult(String.format(MESSAGE_EDIT_PET_PATIENT_SUCCESS, editedPetPatient));
        case EDIT_APPOINTMENT:
            model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
            return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment));
        default:
            return null;
        }
    }

    /**
     * Checks whether person's NRIC has been modified
     * If yes, update all other relevant pet patients and appointments under the same person
     * If no, do nothing
     */
    private void resolvePersonDependencies() throws DuplicatePetPatientException, PetPatientNotFoundException,
            DuplicateAppointmentException, AppointmentNotFoundException {

        Nric oldNric = personToEdit.getNric();
        Nric newNric = editedPerson.getNric();

        if (!oldNric.equals(newNric)) {
            updatePetPatienstByOwnerNric(oldNric, newNric);
            updateAppointmentByOwnerNric(oldNric, newNric);
        }
    }

    /**
     * Checks whether pet patient's name or owner NRIC has been modified
     * If yes, update all other relevant appointments and also the update the new owner for the pet
     */
    private void resolvePetPatientDependencies() throws CommandException,
            AppointmentNotFoundException, DuplicateAppointmentException {

        Nric oldNric = petPatientToEdit.getOwner();
        Nric newNric = editedPetPatient.getOwner();
        PetPatientName oldPetName = petPatientToEdit.getName();
        PetPatientName newPetName = editedPetPatient.getName();

        if (!oldNric.equals(newNric)) { // nric edited, I want to change owner
            Person newOwner = model.getPersonWithNric(newNric); // new owner must exist
            if (newOwner == null) {
                throw new CommandException("New owner must exist first before updating pet patient's owner NRIC!");
            }
            updateAppointmentByOwnerNric(oldNric, newNric);
        }
        if (!oldPetName.equals(newPetName)) { // name edited
            updateAppointmentByPetPatientName(newNric, oldPetName, newPetName);
        }
    }

    /**
     * Checks whether there are clashes in appointment date and time
     */
    private void checkForClashes() throws CommandException {
        LocalDateTime oldDateTime = appointmentToEdit.getDateTime();
        LocalDateTime newDateTime = editedAppointment.getDateTime();

        if (!oldDateTime.equals(newDateTime)) {
            Appointment appointmentWithClash = model.getClashingAppointment(newDateTime);
            if (appointmentWithClash != null) {
                throw new CommandException("Clash in timing exists. Please change to another date / time.");
            }
        }
    }

    /**
     * Helper function to update pet patient's owner from an old nric to new nric
     */
    private void updatePetPatienstByOwnerNric(Nric oldNric, Nric newNric) throws
            PetPatientNotFoundException, DuplicatePetPatientException {

        ArrayList<PetPatient> petPatientArrayList = model.getPetPatientsWithNric(oldNric);
        EditPetPatientDescriptor eppd = new EditPetPatientDescriptor();
        eppd.setOwnerNric(newNric);

        for (PetPatient currPetPatient : petPatientArrayList) {
            PetPatient modifiedPetPatient = createEditedPetPatient(currPetPatient, eppd);
            model.updatePetPatient(currPetPatient, modifiedPetPatient);
            model.updateFilteredPetPatientList(PREDICATE_SHOW_ALL_PET_PATIENTS);
        }
    }

    /**
     * Helper function to update appointment's owner from an old nric to new nric
     */
    private void updateAppointmentByOwnerNric(Nric oldNric, Nric newNric) throws
            AppointmentNotFoundException, DuplicateAppointmentException {

        ArrayList<Appointment> appointmentArrayList = model.getAppointmentsWithNric(oldNric);
        EditAppointmentDescriptor ead = new EditAppointmentDescriptor();
        ead.setOwnerNric(newNric);

        for (Appointment currAppointment : appointmentArrayList) {
            Appointment modifiedAppointment = createEditedAppointment(currAppointment, ead);
            model.updateAppointment(currAppointment, modifiedAppointment);
            model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        }
    }

    /**
     * Helper function to update pet patient name in appointment
     */
    private void updateAppointmentByPetPatientName(Nric ownerNric, PetPatientName oldPetName,
                                                   PetPatientName newPetName) throws
            DuplicateAppointmentException, AppointmentNotFoundException {

        ArrayList<Appointment> appointmentArrayList =
                model.getAppointmentsWithNricAndPetName(ownerNric, oldPetName);
        EditAppointmentDescriptor ead = new EditAppointmentDescriptor();
        ead.setPetPatientName(newPetName);

        for (Appointment currAppointment : appointmentArrayList) {
            Appointment modifiedAppointment = createEditedAppointment(currAppointment, ead);
            model.updateAppointment(currAppointment, modifiedAppointment);
            model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        switch (type) {
        case EDIT_PERSON:
            preprocessUndoableCommandForPerson();
            break;
        case EDIT_PET_PATIENT:
            preprocessUndoableCommandForPetPatient();
            break;
        case EDIT_APPOINTMENT:
            preprocessUndoableCommandForAppointment();
            break;
        default:
            break;
        }
    }
    /**
     * Obtains the last shown person list.
     */
    protected void preprocessUndoableCommandForPerson() throws CommandException {
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        }

        personToEdit = lastShownPersonList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Obtains the last shown pet patient list.
     */
    protected void preprocessUndoableCommandForPetPatient() throws CommandException {
        List<PetPatient> lastShownPetPatientList = model.getFilteredPetPatientList();

        if (index.getZeroBased() >= lastShownPetPatientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PET_PATIENT_DISPLAYED_INDEX);
        }

        petPatientToEdit = lastShownPetPatientList.get(index.getZeroBased());
        editedPetPatient = createEditedPetPatient(petPatientToEdit, editPetPatientDescriptor);
    }

    /**
     * Obtains the last shown appointment list.
     */
    protected void preprocessUndoableCommandForAppointment() throws CommandException {
        List<Appointment> lastShownAppointmentList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownAppointmentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        appointmentToEdit = lastShownAppointmentList.get(index.getZeroBased());
        editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedNric, updatedTags);
    }

    /**
     * Creates and returns a {@code PetPatient} with the details of {@code petPatientToEdit}
     * edited with {@code editPetPatientDescriptor}.
     */
    private static PetPatient createEditedPetPatient(PetPatient petPatientToEdit,
                                                     EditPetPatientDescriptor editPetPatientDescriptor) {
        assert petPatientToEdit != null;

        PetPatientName updatedName = editPetPatientDescriptor.getName().orElse(petPatientToEdit.getName());
        String updatedSpecies = editPetPatientDescriptor.getSpecies().orElse(petPatientToEdit.getSpecies());
        String updatedBreed = editPetPatientDescriptor.getBreed().orElse(petPatientToEdit.getBreed());
        String updatedColour = editPetPatientDescriptor.getColour().orElse(petPatientToEdit.getColour());
        String updatedBloodType = editPetPatientDescriptor.getBloodType().orElse(petPatientToEdit.getBloodType());
        Nric updatedOwnerNric = editPetPatientDescriptor.getOwnerNric().orElse(petPatientToEdit.getOwner());
        Set<Tag> updatedTags = editPetPatientDescriptor.getTags().orElse(petPatientToEdit.getTags());

        return new PetPatient(
                updatedName,
                updatedSpecies,
                updatedBreed,
                updatedColour,
                updatedBloodType,
                updatedOwnerNric,
                updatedTags
        );
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor) {
        assert appointmentToEdit != null;

        Nric updatedOwnerNric = editAppointmentDescriptor.getOwnerNric()
                .orElse(appointmentToEdit.getOwnerNric());
        PetPatientName updatedPetPatientName = editAppointmentDescriptor.getPetPatientName()
                .orElse(appointmentToEdit.getPetPatientName());
        Remark updatedRemark = editAppointmentDescriptor.getRemark()
                .orElse(appointmentToEdit.getRemark());
        LocalDateTime updatedLocalDateTime = editAppointmentDescriptor.getLocalDateTime()
                .orElse(appointmentToEdit.getDateTime());
        Set<Tag> updatedTags = editAppointmentDescriptor.getTags()
                .orElse(appointmentToEdit.getAppointmentTags());

        return new Appointment(
                updatedOwnerNric,
                updatedPetPatientName,
                updatedRemark,
                updatedLocalDateTime,
                updatedTags
        );
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Nric nric;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setNric(toCopy.nric);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email,
                    this.address, this.nric, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getNric().equals(e.getNric())
                    && getTags().equals(e.getTags());
        }
    }

    /**
     * Stores the details to edit the pet patient with. Each non-empty field value will replace the
     * corresponding field value of the pet patient.
     */
    public static class EditPetPatientDescriptor {
        private PetPatientName name;
        private String species;
        private String breed;
        private String colour;
        private String bloodType;
        private Nric ownerNric;
        private Set<Tag> tags;

        public EditPetPatientDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPetPatientDescriptor(EditPetPatientDescriptor toCopy) {
            setName(toCopy.name);
            setSpecies(toCopy.species);
            setBreed(toCopy.breed);
            setColour(toCopy.colour);
            setBloodType(toCopy.bloodType);
            setOwnerNric(toCopy.ownerNric);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.species, this.breed,
                    this.colour, this.bloodType, this.ownerNric, this.tags);
        }

        public void setName(PetPatientName name) {
            this.name = name;
        }

        public Optional<PetPatientName> getName() {
            return Optional.ofNullable(name);
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public Optional<String> getSpecies() {
            return Optional.ofNullable(species);
        }

        public void setBreed(String breed) {
            this.breed = breed;
        }

        public Optional<String> getBreed() {
            return Optional.ofNullable(breed);
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public Optional<String> getColour() {
            return Optional.ofNullable(colour);
        }

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }

        public Optional<String> getBloodType() {
            return Optional.ofNullable(bloodType);
        }

        public void setOwnerNric(Nric nric) {
            this.ownerNric = nric;
        }

        public Optional<Nric> getOwnerNric() {
            return Optional.ofNullable(ownerNric);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPetPatientDescriptor)) {
                return false;
            }

            // state check
            EditPetPatientDescriptor e = (EditPetPatientDescriptor) other;

            return getName().equals(e.getName())
                    && getSpecies().equals(e.getSpecies())
                    && getBreed().equals(e.getBreed())
                    && getColour().equals(e.getColour())
                    && getBloodType().equals(e.getBloodType())
                    && getOwnerNric().equals(e.getOwnerNric())
                    && getTags().equals(e.getTags());
        }
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private Nric ownerNric;
        private PetPatientName petPatientName;
        private Remark remark;
        private LocalDateTime localDateTime;
        private Set<Tag> tags;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setOwnerNric(toCopy.ownerNric);
            setPetPatientName(toCopy.petPatientName);
            setRemark(toCopy.remark);
            setLocalDateTime(toCopy.localDateTime);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.ownerNric, this.petPatientName, this.remark,
                    this.localDateTime, this.tags);
        }

        public void setOwnerNric(Nric ownerNric) {
            this.ownerNric = ownerNric;
        }

        public Optional<Nric> getOwnerNric() {
            return Optional.ofNullable(ownerNric);
        }

        public void setPetPatientName(PetPatientName petPatientName) {
            this.petPatientName = petPatientName;
        }

        public Optional<PetPatientName> getPetPatientName() {
            return Optional.ofNullable(petPatientName);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public Optional<LocalDateTime> getLocalDateTime() {
            return Optional.ofNullable(localDateTime);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            // state check
            EditAppointmentDescriptor e = (EditAppointmentDescriptor) other;

            return getOwnerNric().equals(e.getOwnerNric())
                    && getPetPatientName().equals(e.getPetPatientName())
                    && getRemark().equals(e.getRemark())
                    && getLocalDateTime().equals(e.getLocalDateTime())
                    && getTags().equals(e.getTags());
        }
    }
}
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Pattern EDIT_COMMAND_FORMAT_PERSON = Pattern.compile("-(o)+(?<personInfo>.*)");
    private static final Pattern EDIT_COMMAND_FORMAT_PET_PATIENT = Pattern.compile("-(p)+(?<petPatientInfo>.*)");
    private static final Pattern EDIT_COMMAND_FORMAT_APPOINTMENT = Pattern.compile("-(a)+(?<appointmentInfo>.*)");

    /**
     * Parses the different types of Objects (Person, PetPatient or Appointment) based on whether user has
     * provided "-o", "-p" or "-a" in the command
     * @param args String to parse
     * @return EditCommand object to edit the object
     * @throws ParseException if invalid format is detected
     */
    public EditCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Edit existing person
        final Matcher matcherForPerson = EDIT_COMMAND_FORMAT_PERSON.matcher(trimmedArgs);
        if (matcherForPerson.matches()) {
            String personInfo = matcherForPerson.group("personInfo");
            return parsePerson(personInfo);
        }

        // Edit existing pet patient
        final Matcher matcherForPetPatient = EDIT_COMMAND_FORMAT_PET_PATIENT.matcher(trimmedArgs);
        if (matcherForPetPatient.matches()) {
            String petPatientInfo = matcherForPetPatient.group("petPatientInfo");
            return parsePetPatient(petPatientInfo);
        }

        // Edit existing appointment
        final Matcher matcherForAppointment = EDIT_COMMAND_FORMAT_APPOINTMENT.matcher(trimmedArgs);
        if (matcherForAppointment.matches()) {
            String appointmentInfo = matcherForAppointment.group("appointmentInfo");
            return parseAppointment(appointmentInfo);
        }

        // throws exception for invalid format
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    /**
     * Parses the given {@code personInfo} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parsePerson(String personInfo) throws ParseException {
        requireNonNull(personInfo);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(personInfo, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_ADDRESS, PREFIX_NRIC, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC)).ifPresent(editPersonDescriptor::setNric);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses the given {@code petPatientInfo} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parsePetPatient(String petPatientInfo) throws ParseException {
        requireNonNull(petPatientInfo);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(petPatientInfo, PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED,
                        PREFIX_COLOUR, PREFIX_BLOODTYPE, PREFIX_NRIC, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPetPatientDescriptor editPetPatientDescriptor = new EditPetPatientDescriptor();
        try {
            ParserUtil.parsePetPatientName(argMultimap.getValue(PREFIX_NAME))
                    .ifPresent(editPetPatientDescriptor::setName);
            ParserUtil.parseSpecies(argMultimap.getValue(PREFIX_SPECIES))
                    .ifPresent(editPetPatientDescriptor::setSpecies);
            ParserUtil.parseBreed(argMultimap.getValue(PREFIX_BREED))
                    .ifPresent(editPetPatientDescriptor::setBreed);
            ParserUtil.parseColour(argMultimap.getValue(PREFIX_COLOUR))
                    .ifPresent(editPetPatientDescriptor::setColour);
            ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE))
                    .ifPresent(editPetPatientDescriptor::setBloodType);
            ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC))
                    .ifPresent(editPetPatientDescriptor::setOwnerNric);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
                    .ifPresent(editPetPatientDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPetPatientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPetPatientDescriptor);
    }

    /**
     * Parses the given {@code appointmentInfo} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parseAppointment(String appointmentInfo) throws ParseException {
        requireNonNull(appointmentInfo);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(appointmentInfo, PREFIX_DATE, PREFIX_REMARK, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        try {
            // ParserUtil.parsePetPatientName(argMultimap.getValue(PREFIX_NAME))
            //        .ifPresent(editAppointmentDescriptor::setPetPatientName);
            ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE))
                    .ifPresent(editAppointmentDescriptor::setLocalDateTime);
            ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK))
                    .ifPresent(editAppointmentDescriptor::setRemark);
            //ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC))
            //        .ifPresent(editAppointmentDescriptor::setOwnerNric);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
                    .ifPresent(editAppointmentDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editAppointmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editAppointmentDescriptor);    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ArrayList<PetPatient> getPetPatientsWithNric(Nric ownerNric) {
        ArrayList<PetPatient> petPatientArrayList = new ArrayList<>();
        for (PetPatient p : addressBook.getPetPatientList()) {
            if (p.getOwner().equals(ownerNric)) {
                petPatientArrayList.add(p);
            }
        }
        return petPatientArrayList;
    }

    @Override
    public ArrayList<Appointment> getAppointmentsWithNric(Nric ownerNric) {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        for (Appointment a : addressBook.getAppointmentList()) {
            if (a.getOwnerNric().equals(ownerNric)) {
                appointmentArrayList.add(a);
            }
        }
        return appointmentArrayList;
    }

    @Override
    public ArrayList<Appointment> getAppointmentsWithNricAndPetName(Nric ownerNric, PetPatientName petPatientName) {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        for (Appointment a : addressBook.getAppointmentList()) {
            if (a.getOwnerNric().equals(ownerNric) && a.getPetPatientName().equals(petPatientName)) {
                appointmentArrayList.add(a);
            }
        }
        return appointmentArrayList;
    }

    @Override
    public Appointment getClashingAppointment(LocalDateTime dateTime) {
        for (Appointment a : addressBook.getAppointmentList()) {
            if (a.getDateTime().equals(dateTime)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void updatePetPatient(PetPatient target, PetPatient editedPetPatient)
            throws DuplicatePetPatientException, PetPatientNotFoundException {
        requireAllNonNull(target, editedPetPatient);

        addressBook.updatePetPatient(target, editedPetPatient);
        indicateAddressBookChanged();
    }

    @Override
    public void updateAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireAllNonNull(target, editedAppointment);

        addressBook.updateAppointment(target, editedAppointment);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\petpatient\PetPatient.java
``` java
/**
 * Represents a PetPatient in the address book.
 * Guarantees: details are present, field values are validated.
 */
public class PetPatient {
    private final PetPatientName name;
    private final String species; // e.g. dogs, cats, birds, etc.
    private final String breed; // different varieties of the same species
    private final String colour;
    private final String bloodType;

    private final UniqueTagList tags;

    private final Optional<Date> dateOfBirth; // can be null
    private Nric ownerNric; // can be null (initially)
    private StringBuilder medicalHistory; // can be null (initially)

    //keep this constructor, as owner NRIC can be null initially when adding a new PetPatient
    public PetPatient(PetPatientName name,
                      String species,
                      String breed,
                      String colour,
                      String bloodType,
                      Set<Tag> tags) {
        requireAllNonNull(name, species, breed, colour, bloodType, tags);
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.colour = colour;
        this.bloodType = bloodType;
        this.tags = new UniqueTagList(tags);

        this.ownerNric = null;
        this.dateOfBirth = null;
        this.medicalHistory = new StringBuilder();
    }

    public PetPatient(PetPatientName name,
                      String species,
                      String breed,
                      String colour,
                      String bloodType,
                      Nric ownerNric,
                      Set<Tag> tags) {
        requireAllNonNull(name, species, breed, colour, bloodType, tags);
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.colour = colour;
        this.bloodType = bloodType;
        this.tags = new UniqueTagList(tags);
        this.ownerNric = ownerNric;
        this.dateOfBirth = null;
        this.medicalHistory = new StringBuilder();
    }

    //keep this constructor
    public PetPatient(PetPatientName name,
                      String species,
                      String breed,
                      String colour,
                      String bloodType,
                      Nric ownerNric,
                      Optional<Date> dateOfBirth,
                      Set<Tag> tags) {
        requireAllNonNull(name, species, breed, colour, bloodType, ownerNric, dateOfBirth, tags);
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.colour = colour;
        this.bloodType = bloodType;
        this.ownerNric = ownerNric;
        this.dateOfBirth = dateOfBirth;
        this.tags = new UniqueTagList(tags);
        this.medicalHistory = new StringBuilder();
    }

    public PetPatientName getName() {
        return name;
    }

    public Optional<Date> getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    public String getColour() {
        return colour;
    }

    public String getBloodType() {
        return bloodType;
    }

    public Nric getOwner() {
        return ownerNric;
    }

    public void setOwnerNric(Nric ownerNric) {
        this.ownerNric = ownerNric;
    }

    public StringBuilder getMedicalHistory() {
        return medicalHistory;
    }

    public void updateMedicalHistory(String newContent) {
        this.medicalHistory.append(newContent);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    /**
     * Returns a list of tags as a string, for find command.
     */
    public String getTagString() {
        StringBuilder tagString = new StringBuilder();
        Set<Tag> tagSet = Collections.unmodifiableSet(tags.toSet());
        for (Tag tag : tagSet) {
            tagString.append(tag.tagName);
            tagString.append(" ");
        }
        return tagString.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PetPatient)) {
            return false;
        }

        PetPatient otherPetPatient = (PetPatient) other;
        return otherPetPatient.getName().equals(this.getName())
                && otherPetPatient.getSpecies().equals(this.getSpecies())
                && otherPetPatient.getBreed().equals(this.getBreed())
                && otherPetPatient.getColour().equals(this.getColour())
                && otherPetPatient.getBloodType().equals(this.getBloodType())
                && otherPetPatient.getOwner().equals(this.getOwner());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, species, breed, colour, bloodType, tags, ownerNric);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\t")
                .append(getName())
                .append("\tSpecies: ")
                .append(getSpecies())
                .append("\tBreed: ")
                .append(getBreed())
                .append("\tColor: ")
                .append(getColour())
                .append("\tBlood Type: ")
                .append(getBloodType())
                .append("\t\tOwner's NRIC: ")
                .append(getOwner())
                .append("\tTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\petpatient\PetPatientName.java
``` java
/**
 * Represents a PetPatient's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class PetPatientName {

    public static final String MESSAGE_PET_NAME_CONSTRAINTS =
            "Pet Patient names should only contain alphanumeric characters and spaces, and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code PetPatientName}.
     *
     * @param name A valid name.
     */
    public PetPatientName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_PET_NAME_CONSTRAINTS);
        this.fullName = name;
    }

    /**
     * Returns true if a given string is a valid pet patient name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PetPatientName // instanceof handles nulls
                && this.fullName.equals(((PetPatientName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
```
###### \java\seedu\address\model\petpatient\UniquePetPatientList.java
``` java
/**
 * A list of pet patients that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see PetPatient#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePetPatientList implements Iterable<PetPatient> {
    private final ObservableList<PetPatient> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent pet patient as the given argument.
     */
    public boolean contains(PetPatient toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a pet patient to the list.
     *
     * @throws DuplicatePetPatientException if the pet patient to add is a duplicate of an existing pet patient
     * in the list.
     */
    public void add(PetPatient toAdd) throws DuplicatePetPatientException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePetPatientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the pet patient {@code target} in the list with {@code editedPetPatient}.
     *
     * @throws DuplicatePetPatientException if the replacement is equivalent to another existing pet patient
     * in the list.
     * @throws PetPatientNotFoundException if {@code target} could not be found in the list.
     */
    public void setPetPatient(PetPatient target, PetPatient editedPetPatient)
            throws DuplicatePetPatientException, PetPatientNotFoundException {
        requireNonNull(editedPetPatient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PetPatientNotFoundException();
        }

        if (!target.equals(editedPetPatient) && internalList.contains(editedPetPatient)) {
            throw new DuplicatePetPatientException();
        }

        internalList.set(index, editedPetPatient);
    }

    /**
     * Removes the equivalent pet patient from the list.
     *
     * @throws PetPatientNotFoundException if no such pet patient could be found in the list.
     */
    public boolean remove(PetPatient toRemove) throws PetPatientNotFoundException {
        requireNonNull(toRemove);
        final boolean petPatientFoundAndDeleted = internalList.remove(toRemove);
        if (!petPatientFoundAndDeleted) {
            throw new PetPatientNotFoundException();
        }
        return petPatientFoundAndDeleted;
    }

    public void setPetPatients(UniquePetPatientList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPetPatients(List<PetPatient> petPatients) throws DuplicatePetPatientException {
        requireAllNonNull(petPatients);
        final UniquePetPatientList replacement = new UniquePetPatientList();
        for (final PetPatient petPatient : petPatients) {
            replacement.add(petPatient);
        }
        setPetPatients(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PetPatient> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<PetPatient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePetPatientList // instanceof handles nulls
                && this.internalList.equals(((UniquePetPatientList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPetPatient.java
``` java
/**
 * JAXB-friendly version of the PetPatient.
 */
public class XmlAdaptedPetPatient {
    public static final String MISSING_NAME_FIELD_MESSAGE_FORMAT = "Pet patient's name field is missing!";
    public static final String MISSING_SPECIES_FIELD_MESSAGE_FORMAT = "Pet patient's species field is missing!";
    public static final String MISSING_BREED_FIELD_MESSAGE_FORMAT = "Pet patient's breed field is missing!";
    public static final String MISSING_COLOUR_FIELD_MESSAGE_FORMAT = "Pet patient's colour field is missing!";
    public static final String MISSING_BLOODTYPE_FIELD_MESSAGE_FORMAT = "Pet patient's blood type field is missing!";
    public static final String MISSING_OWNER_FIELD_MESSAGE_FORMAT = "Pet patient's owner field is missing!";


    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String species;
    @XmlElement(required = true)
    private String breed;
    @XmlElement(required = true)
    private String colour;
    @XmlElement(required = true)
    private String bloodType;
    @XmlElement(required = true)
    private String ownerNric;

    @XmlElement
    private String dateOfBirth;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private String medicalHistory;

    /**
     * Constructs an XmlAdaptedPetPatient.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPetPatient() {}

    /**
     * Constructs an {@code XmlAdaptedPetPatient} with the given pet patient details.
     */
    public XmlAdaptedPetPatient(String name, String species, String breed, String colour,
                            String bloodType, String ownerNric, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.colour = colour;
        this.bloodType = bloodType;
        this.ownerNric = ownerNric;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given PetPatient into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPetPatient
     */
    public XmlAdaptedPetPatient(PetPatient source) {
        name = source.getName().fullName;
        species = source.getSpecies();
        breed = source.getBreed();
        colour = source.getColour();
        bloodType = source.getBloodType();
        ownerNric = source.getOwner().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted pet patient object into the model's PetPatient object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pet patient
     */
    public PetPatient toModelType() throws IllegalValueException {
        final List<Tag> petPatientTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            petPatientTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_NAME_FIELD_MESSAGE_FORMAT, PetPatientName.class.getSimpleName()));
        }
        if (!PetPatientName.isValidName(this.name)) {
            throw new IllegalValueException(PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS);
        }
        final PetPatientName name = new PetPatientName(this.name);

        if (this.species == null) {
            throw new IllegalValueException(String.format(MISSING_SPECIES_FIELD_MESSAGE_FORMAT));
        }

        if (this.breed == null) {
            throw new IllegalValueException(String.format(MISSING_BREED_FIELD_MESSAGE_FORMAT));
        }

        if (this.colour == null) {
            throw new IllegalValueException(String.format(MISSING_COLOUR_FIELD_MESSAGE_FORMAT));
        }

        if (this.bloodType == null) {
            throw new IllegalValueException(String.format(MISSING_BLOODTYPE_FIELD_MESSAGE_FORMAT));
        }

        if (this.ownerNric == null) {
            throw new IllegalValueException(
                    String.format(MISSING_OWNER_FIELD_MESSAGE_FORMAT, PetPatientName.class.getSimpleName()));
        }
        if (!Nric.isValidNric(this.ownerNric)) {
            throw new IllegalValueException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        final Nric ownerNric = new Nric(this.ownerNric);

        final Set<Tag> tags = new HashSet<>(petPatientTags);
        return new PetPatient(name, species, breed, colour, bloodType, ownerNric, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPetPatient)) {
            return false;
        }

        XmlAdaptedPetPatient otherPetPatient = (XmlAdaptedPetPatient) other;
        return Objects.equals(name, otherPetPatient.name)
                && Objects.equals(species, otherPetPatient.species)
                && Objects.equals(breed, otherPetPatient.breed)
                && Objects.equals(colour, otherPetPatient.colour)
                && Objects.equals(bloodType, otherPetPatient.bloodType)
                && Objects.equals(ownerNric, otherPetPatient.ownerNric)
                && tagged.equals(otherPetPatient.tagged);
    }
}
```

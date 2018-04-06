# wynonaK
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
/**
 * Deletes a person, pet patient or appointment from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " -[f]o/-[f]p/-a"
            + ": Deletes the person/pet/appointment identified by the index number used in the last listing.\n"
            + "Additional -[f] options indicates forcefully deleting object and all related dependencies.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -o 1";

    public static final String MESSAGE_USAGE_OWNER = COMMAND_WORD
            + " -o"
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -o 1";

    public static final String MESSAGE_USAGE_PET_PATIENT = COMMAND_WORD
            + " -p"
            + ": Deletes the pet patient identified by the index number used in the last pet patient listing.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -p 1";

    public static final String MESSAGE_USAGE_APPOINTMENT = COMMAND_WORD
            + " -a"
            + ": Deletes the appointment identified by the index number used in the last appointment listing.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -a 1";

    public static final String MESSAGE_USAGE_FORCE_OWNER = COMMAND_WORD
            + " -fo"
            + ": Forcefully deletes the person and all related dependencies "
            + "identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " -fo 1";

    public static final String MESSAGE_USAGE_FORCE_PET_PATIENT = COMMAND_WORD
            + " -fp"
            + ": Forcefully deletes the pet and all related dependencies "
            + "identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer, must not be invalid)\n"
            + "Example: " + COMMAND_WORD + " -fp 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_PET_PATIENT_SUCCESS = "Deleted Pet Patient: %1$s";
    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment: %1$s";

    private final Index targetIndex;
    private final int type;

    private Person personToDelete;
    private PetPatient petPatientToDelete;
    private Appointment appointmentToDelete;

    public DeleteCommand(int type, Index targetIndex) {
        this.type = type;
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            switch (type) {
            case 1: return deletePerson();
            case 2: return deletePetPatient();
            case 3: return deleteAppointment();
            case 4: return deleteForcePerson();
            case 5: return deleteForcePetPatient();
            default:
                throw new CommandException(MESSAGE_USAGE);
            }
        } catch (PetDependencyNotEmptyException e) {
            throw new CommandException(Messages.MESSAGE_DEPENDENCIES_EXIST);
        } catch (AppointmentDependencyNotEmptyException e) {
            throw new CommandException(Messages.MESSAGE_DEPENDENCIES_EXIST);
        }
    }
    /**
     * Deletes {@code personToDelete} from the address book.
     */
    private CommandResult deletePerson() throws PetDependencyNotEmptyException {
        try {
            requireNonNull(personToDelete);
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    private void getPersonToDelete() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        personToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Deletes the pet patient {@code petPatientToDelete} from the address book.
     */
    private CommandResult deletePetPatient() throws AppointmentDependencyNotEmptyException {
        try {
            requireNonNull(petPatientToDelete);
            model.deletePetPatient(petPatientToDelete);
        } catch (PetPatientNotFoundException ppnfe) {
            throw new AssertionError("The target pet cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PET_PATIENT_SUCCESS, petPatientToDelete));
    }

    private void getPetPatientToDelete() throws CommandException {
        List<PetPatient> lastShownList = model.getFilteredPetPatientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        petPatientToDelete = lastShownList.get(targetIndex.getZeroBased());
    }
    /**
     * Deletes the appointment {@code appointmentToDelete} from the address book.
     */
    private CommandResult deleteAppointment() {
        try {
            requireNonNull(appointmentToDelete);
            model.deleteAppointment(appointmentToDelete);
        } catch (AppointmentNotFoundException anfe) {
            throw new AssertionError("The target appointment cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointmentToDelete));
    }

    private void getAppointmentToDelete() throws CommandException {
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        appointmentToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Forcefully deletes {@code personToDelete} from the address book.
     * All related dependencies (pet patients, appointments) will be deleted as well.
     */
    private CommandResult deleteForcePerson() {
        String deleteDependenciesList = "";

        try {
            requireNonNull(personToDelete);
            List<PetPatient> petPatientsDeleted = model.deletePetPatientDependencies(personToDelete);
            List<Appointment> appointmentsDeleted = new ArrayList<>();
            for (PetPatient pp : petPatientsDeleted) {
                System.out.println(pp.getName());
                appointmentsDeleted.addAll(model.deleteAppointmentDependencies(pp));
                deleteDependenciesList += "\n" + (String.format(MESSAGE_DELETE_PET_PATIENT_SUCCESS, pp));
            }
            for (Appointment appointment : appointmentsDeleted) {
                deleteDependenciesList += "\n" + (String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointment));
            }
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person cannot be missing");
        } catch (PetDependencyNotEmptyException e) {
            throw new AssertionError("Pet dependencies still exist!");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete)
                + deleteDependenciesList);
    }

    /**
     * Forcefully deletes {@code petPatientToDelete} from the address book.
     * All related dependencies (appointments) will be deleted as well.
     */
    private CommandResult deleteForcePetPatient() {
        String deleteDependenciesList = "";

        try {
            requireNonNull(petPatientToDelete);
            List<Appointment> appointmentDependenciesDeleted = model.deleteAppointmentDependencies(petPatientToDelete);
            for (Appointment appointment : appointmentDependenciesDeleted) {
                deleteDependenciesList += "\n" + (String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointment));
            }
            model.deletePetPatient(petPatientToDelete);
        } catch (PetPatientNotFoundException ppnfe) {
            throw new AssertionError("The target pet cannot be missing");
        }  catch (AppointmentDependencyNotEmptyException e) {
            throw new AssertionError("Appointment dependencies still exist!");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PET_PATIENT_SUCCESS, petPatientToDelete)
                + deleteDependenciesList);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            switch (type) {
            case 1: getPersonToDelete();
                break;
            case 2: getPetPatientToDelete();
                break;
            case 3: getAppointmentToDelete();
                break;
            case 4: getPersonToDelete();
                break;
            case 5: getPetPatientToDelete();
                break;
            default:
                throw new CommandException(MESSAGE_USAGE);
            }
        } catch (CommandException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.personToDelete, ((DeleteCommand) other).personToDelete));
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all fields that matches any of"
            + " the specified option, prefixes & keywords (case-sensitive)"
            + " and displays them as a list with index numbers.\n"
            + "Parameters: OPTION PREFIX/KEYWORD [MORE_PREFIX/MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "-o n/alice bob charlie";

    private Predicate<Person> personPredicate = null;
    private Predicate<PetPatient> petPatientPredicate = null;
    private int type = 0;

    public FindCommand(Predicate<Person> personPredicate) {
        this.personPredicate = personPredicate;
        type = 1;
    }

    public FindCommand(Predicate<PetPatient> petPatientPredicate, int petPatientIndicator) {
        this.petPatientPredicate = petPatientPredicate;
        type = petPatientIndicator;
    }


    @Override
    public CommandResult execute() throws CommandException {
        switch (type) {
        case 1:
            return findOwner();
        case 2:
            return findPetPatient();
        default:
            throw new CommandException(MESSAGE_USAGE);
        }
    }

    /**
     * Finds owners with given {@code predicate} in this {@code addressbook}.
     */
    private CommandResult findOwner() {
        model.updateFilteredPersonList(personPredicate);
        updatePetListForOwner();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())
                + "\n"
                + getMessageForPetPatientListShownSummary(model.getFilteredPetPatientList().size()));
    }

    /**
     * Finds owners with given {@code predicate} in this {@code addressbook}.
     */
    private CommandResult findPetPatient() {
        model.updateFilteredPetPatientList(petPatientPredicate);
        updateOwnerListForPets();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())
                + "\n"
                + getMessageForPetPatientListShownSummary(model.getFilteredPetPatientList().size()));
    }

    /**
     * Updates the filtered pet list with the changed owners in this {@code addressbook}.
     */
    private void updatePetListForOwner() {
        List<String> nricKeywordsForPets = new ArrayList<>();
        for (Person person : model.getFilteredPersonList()) {
            nricKeywordsForPets.add(person.getNric().toString());
        }
        Predicate<PetPatient> petPatientNricPredicate =  petPatient -> nricKeywordsForPets.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(petPatient.getOwner().toString(), keyword));
        model.updateFilteredPetPatientList(petPatientNricPredicate);
    }

    /**
     * Updates the filtered person list with the changed pets in this {@code addressbook}.
     */
    private void updateOwnerListForPets() {
        List<String> nricKeywordsForOwner = new ArrayList<>();
        for (PetPatient petPatient : model.getFilteredPetPatientList()) {
            if (!nricKeywordsForOwner.contains(petPatient.getOwner().toString())) {
                nricKeywordsForOwner.add(petPatient.getOwner().toString());
            }
        }
        Predicate<Person> ownerNricPredicate =  person -> nricKeywordsForOwner.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getNric().toString(), keyword));
        model.updateFilteredPersonList(ownerNricPredicate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.personPredicate.equals(((FindCommand) other).personPredicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final Pattern DELETE_COMMAND_FORMAT_OWNER = Pattern.compile("-(o)+(?<index>.*)");
    private static final Pattern DELETE_COMMAND_FORMAT_PET_PATIENT = Pattern.compile("-(p)+(?<index>.*)");
    private static final Pattern DELETE_COMMAND_FORMAT_APPOINTMENT = Pattern.compile("-(a)+(?<index>.*)");
    private static final Pattern DELETE_COMMAND_FORMAT_FORCE_OWNER = Pattern.compile("-(fo)+(?<index>.*)");
    private static final Pattern DELETE_COMMAND_FORMAT_FORCE_PET_PATIENT = Pattern.compile("-(fp)+(?<index>.*)");
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * type changes depending on what pattern it matches
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        final Matcher matcherForOwner = DELETE_COMMAND_FORMAT_OWNER.matcher(trimmedArgs);
        if (matcherForOwner.matches()) {
            try {
                int type = 1;
                Index index = ParserUtil.parseIndex(matcherForOwner.group("index"));
                return new DeleteCommand(type, index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_OWNER));
            }
        }

        final Matcher matcherForPetPatient = DELETE_COMMAND_FORMAT_PET_PATIENT.matcher(trimmedArgs);
        if (matcherForPetPatient.matches()) {
            try {
                int type = 2;
                Index index = ParserUtil.parseIndex(matcherForPetPatient.group("index"));
                return new DeleteCommand(type, index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_PET_PATIENT));
            }
        }

        final Matcher matcherForAppointment = DELETE_COMMAND_FORMAT_APPOINTMENT.matcher(trimmedArgs);
        if (matcherForAppointment.matches()) {
            try {
                int type = 3;
                Index index = ParserUtil.parseIndex(matcherForAppointment.group("index"));
                return new DeleteCommand(type, index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_APPOINTMENT));
            }
        }

        final Matcher matcherForForceOwner = DELETE_COMMAND_FORMAT_FORCE_OWNER.matcher(trimmedArgs);
        if (matcherForForceOwner.matches()) {
            try {
                int type = 4;
                Index index = ParserUtil.parseIndex(matcherForForceOwner.group("index"));
                return new DeleteCommand(type, index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_FORCE_OWNER));
            }
        }

        final Matcher matcherForForcePetPatient = DELETE_COMMAND_FORMAT_FORCE_PET_PATIENT.matcher(trimmedArgs);
        if (matcherForForcePetPatient.matches()) {
            try {
                int type = 5;
                Index index = ParserUtil.parseIndex(matcherForForcePetPatient.group("index"));
                return new DeleteCommand(type, index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_FORCE_PET_PATIENT));
            }
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String remark} into an {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws IllegalValueException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(parseRemark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String dateTime} into an {@code LocalDateTime} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code dateTime} is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws IllegalValueException {
        requireNonNull(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Please follow the format of yyyy-MM-dd HH:mm");
        }
        return localDateTime;
    }

    /**
     * Parses {@code Optional<String> dateTime} into an {@code Optional<LocalDatetime>} if {@code dateTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LocalDateTime> parseDateTime(Optional<String> dateTime) throws IllegalValueException {
        requireNonNull(dateTime);
        return dateTime.isPresent() ? Optional.of(parseDateTime(dateTime.get())) : Optional.empty();
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Updates the master tag list to include tags in {@code appointment} that are not in the list.
     *
     * @return a copy of this {@code appointment} such that every tag in this appointment
     * points to a Tag object in the master list.
     */
    private Appointment syncWithMasterTagList(Appointment appointment) {
        final UniqueTagList appointmentTags = new UniqueTagList(appointment.getAppointmentTags());
        tags.mergeFrom(appointmentTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        appointmentTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Appointment(
                appointment.getOwnerNric(),
                appointment.getPetPatientName(),
                appointment.getRemark(),
                appointment.getDateTime(),
                correctTagReferences);
    }

    /**
     * Adds an appointment.
     * Also checks the new appointment's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the appointment to point to those in {@link #tags}.
     *
     * @throws DuplicateAppointmentException if an equivalent person already exists.
     */
    public void addAppointment(Appointment a) throws DuplicateAppointmentException, DuplicateDateTimeException {
        Appointment appointment = syncWithMasterTagList(a);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any appointment
        // in the appointment list.
        appointments.add(appointment);
    }

    ////Delete operations

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     * @throws PetDependencyNotEmptyException if the {@code key} still contains pet patients it is tied to.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException, PetDependencyNotEmptyException {
        petPatientDependenciesExist(key);

        if (persons.remove(key)) {
            removeUselessTags();
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Removes pet patient {@code key} from this {@code AddressBook}.
     *
     * @throws PetPatientNotFoundException if the {@code key} is not in this {@code AddressBook}.
     * @throws AppointmentDependencyNotEmptyException if the {@code key} still contains appointments it is tied to.
     */
    public boolean removePetPatient(PetPatient key)
            throws PetPatientNotFoundException, AppointmentDependencyNotEmptyException {
        appointmentDependenciesExist(key);

        if (petPatients.remove(key)) {
            removeUselessTags();
            return true;
        } else {
            throw new PetPatientNotFoundException();
        }
    }

    /**
     * Forcefully removes all pet patients dependencies on {@code key} from this {@code AddressBook}.
     */

    public List<PetPatient> removeAllPetPatientDependencies(Person key) {
        Iterator<PetPatient> petPatientIterator = petPatients.iterator();
        List<PetPatient> petPatientsDeleted = new ArrayList<>();
        while (petPatientIterator.hasNext()) {
            PetPatient petPatient = petPatientIterator.next();

            if (petPatient.getOwner().equals(key.getNric())) {
                petPatientsDeleted.add(petPatient);
                petPatientIterator.remove();
            }
        }
        return petPatientsDeleted;
    }

    /**
     * @throws AppointmentDependencyNotEmptyException if appointment dependencies of {@code key}
     * still exists in {@code AddressBook}.
     */
    private void appointmentDependenciesExist(PetPatient key) throws AppointmentDependencyNotEmptyException {
        for (Appointment appointment : appointments) {
            if (appointment.getPetPatientName().equals(key.getName())
                    && appointment.getOwnerNric().equals(key.getOwner())) {
                throw new AppointmentDependencyNotEmptyException();
            }
        }
    }

    /**
     * @throws PetDependencyNotEmptyException if pet dependencies of {@code key} still exists in {@code AddressBook}.
     */
    private void petPatientDependenciesExist(Person key) throws PetDependencyNotEmptyException {
        for (PetPatient petPatient : petPatients) {
            if (petPatient.getOwner().equals(key.getNric())) {
                throw new PetDependencyNotEmptyException();
            }
        }
    }

    /**
     * Forcefully removes all dependencies relying on pet patient {@code key} from this {@code AddressBook}.
     *
     */
    public List<Appointment> removeAllAppointmentDependencies(PetPatient key) {
        List<Appointment> appointmentsDeleted = new ArrayList<>();
        Iterator<Appointment> appointmentIterator = appointments.iterator();

        while (appointmentIterator.hasNext()) {
            Appointment appointment = appointmentIterator.next();

            if (appointment.getPetPatientName().equals(key.getName())
                    && appointment.getOwnerNric().equals(key.getOwner())) {
                appointmentsDeleted.add(appointment);
                appointmentIterator.remove();
            }
        }

        return appointmentsDeleted;
    }

    /**
     * Removes appointment {@code key} from this {@code AddressBook}.
     *
     * @throws AppointmentNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeAppointment(Appointment key) throws AppointmentNotFoundException {
        if (appointments.remove(key)) {
            removeUselessTags();
            return true;
        } else {
            throw new AppointmentNotFoundException();
        }
    }

    //// tag operations

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asObservableList();
    }

```
###### \java\seedu\address\model\appointment\Appointment.java
``` java
/**
 * Represents an Appointment.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Appointment {
    private Nric ownerNric;
    private PetPatientName petPatientName;
    private Remark remark; //remarks
    private LocalDateTime localDateTime; //date of appointment

    private UniqueTagList appointmentTags; //type of appointment

    /**
     * Every field must be present and not null.
     */
    public Appointment(Nric ownerNric, PetPatientName petPatientName, Remark remark,
                       LocalDateTime localDateTime, Set<Tag> appointmentTags) {
        requireAllNonNull(ownerNric, petPatientName, remark, localDateTime, appointmentTags);
        this.ownerNric = ownerNric;
        this.petPatientName = petPatientName;
        this.remark = remark;
        this.localDateTime = localDateTime;
        // protect internal tags from changes in the arg list
        this.appointmentTags = new UniqueTagList(appointmentTags);
    }

    /**
     * ownerNric and petName can be set later using setter methods.
     */
    public Appointment(Remark remark, LocalDateTime localDateTime, Set<Tag> type) {
        requireAllNonNull(remark, localDateTime, type);
        this.remark = remark;
        this.localDateTime = localDateTime;
        // protect internal tags from changes in the arg list
        this.appointmentTags = new UniqueTagList(type);
    }

    public Nric getOwnerNric() {
        return ownerNric;
    }

    public void setOwnerNric(Nric ownerNric) {
        this.ownerNric = ownerNric;
    }

    public PetPatientName getPetPatientName() {
        return petPatientName;
    }

    public void setPetPatientName(PetPatientName petPatientName) {
        this.petPatientName = petPatientName;
    }

    public Remark getRemark() {
        return remark;
    }

    public LocalDateTime getDateTime() {
        return localDateTime;
    }

    public String getFormattedLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getAppointmentTags() {
        return Collections.unmodifiableSet(appointmentTags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getOwnerNric().equals(this.getOwnerNric())
                && otherAppointment.getPetPatientName().equals((this.getPetPatientName()))
                && otherAppointment.getRemark().equals(this.getRemark())
                && otherAppointment.getDateTime().equals(this.getDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ownerNric, petPatientName, remark, localDateTime, appointmentTags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\t")
                .append(getFormattedLocalDateTime())
                .append("\tRemarks: ")
                .append(getRemark())
                .append("\tType(s): ");
        getAppointmentTags().forEach(builder::append);
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\appointment\exceptions\AppointmentDependencyNotEmptyException.java
``` java
/**
 * Signals that the operation is unable to continue because there are still appointments dependent.
 */

public class AppointmentDependencyNotEmptyException extends Exception {
}
```
###### \java\seedu\address\model\appointment\exceptions\AppointmentNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified person.
 */
public class AppointmentNotFoundException extends Exception {}
```
###### \java\seedu\address\model\appointment\exceptions\DuplicateAppointmentException.java
``` java
/**
 * Signals that the operation will result in duplicate Appointment objects.
 */
public class DuplicateAppointmentException extends DuplicateDataException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate appointments");
    }
}
```
###### \java\seedu\address\model\appointment\exceptions\DuplicateDateTimeException.java
``` java
/**
 * Signals that the operation will result in double booking.
 */
public class DuplicateDateTimeException extends DuplicateDataException {
    public DuplicateDateTimeException() {
        super("Operation would result in multiple bookings in the same time slot");
    }
}
```
###### \java\seedu\address\model\appointment\Remark.java
``` java
/**
 * Represents a Appointment's remarks.
 * Guarantees: is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Remarks can take any values, and it should not be blank. Leave \"nil\" for no remarks.";

    /*
     * The first character of the remark must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REMARK_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A valid address.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_REMARK_CONSTRAINTS);
        this.value = remark;
    }

    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(REMARK_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\appointment\UniqueAppointmentList.java
``` java
/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Appointment#equals(Object)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an appointment to the list.
     *
     * @throws DuplicateAppointmentException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Appointment toAdd) throws DuplicateAppointmentException, DuplicateDateTimeException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }

        for (Appointment a : internalList) {
            if (a.getDateTime().equals(toAdd.getDateTime())) {
                throw new DuplicateDateTimeException();
            }
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code editedAppointment}.
     *
     * @throws DuplicateAppointmentException if the replacement is equivalent to
     * another existing appointment in the list.
     * @throws AppointmentNotFoundException if {@code target} could not be found in the list.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireNonNull(editedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        if (!target.equals(editedAppointment) && internalList.contains(editedAppointment)) {
            throw new DuplicateAppointmentException();
        }

        internalList.set(index, editedAppointment);
    }

    /**
     * Removes the equivalent pet patient from the list.
     *
     * @throws AppointmentNotFoundException if no such pet patient could be found in the list.
     */
    public boolean remove(Appointment toRemove) throws AppointmentNotFoundException {
        requireNonNull(toRemove);
        final boolean appointmentFoundAndDeleted = internalList.remove(toRemove);
        if (!appointmentFoundAndDeleted) {
            throw new AppointmentNotFoundException();
        }
        return appointmentFoundAndDeleted;
    }

    public void setAppointments(UniqueAppointmentList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setAppointments(List<Appointment> appointments)
            throws DuplicateAppointmentException, DuplicateDateTimeException {
        requireAllNonNull(appointments);
        final UniqueAppointmentList replacement = new UniqueAppointmentList();
        for (final Appointment appointment : appointments) {
            replacement.add(appointment);
        }
        setAppointments(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && this.internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deletePetPatient(PetPatient target)
            throws PetPatientNotFoundException, AppointmentDependencyNotEmptyException {
        addressBook.removePetPatient(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized List<PetPatient> deletePetPatientDependencies(Person target) {
        List<PetPatient> petPatients = addressBook.removeAllPetPatientDependencies(target);
        indicateAddressBookChanged();
        return petPatients;
    }

    @Override
    public synchronized List<Appointment> deleteAppointmentDependencies(PetPatient target) {
        List<Appointment> dependenciesDeleted = addressBook.removeAllAppointmentDependencies(target);
        indicateAddressBookChanged();
        return dependenciesDeleted;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteAppointment(Appointment target) throws AppointmentNotFoundException {
        addressBook.removeAppointment(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addAppointment(Appointment appointment)
            throws DuplicateAppointmentException, DuplicateDateTimeException {
        addressBook.addAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        indicateAddressBookChanged();
    }


    @Override
    public void deleteTag(Tag tag) {
        addressBook.removeTag(tag);
    }

    //=========== Filtered Person List Accessors =============================================================

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return FXCollections.unmodifiableObservableList(filteredAppointments);
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    //=========== Filtered Pet Patient List Accessors =============================================================

```
###### \java\seedu\address\model\petpatient\exceptions\PetDependencyNotEmptyException.java
``` java
/**
 * Signals that the operation is unable to continue because there are still pets dependent.
 */
public class PetDependencyNotEmptyException extends Exception {
}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Nric("S0123456B"),
                getTagSet("owner")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Nric("T0123456C"),
                getTagSet("owner")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Nric("G0123456A"),
                getTagSet("owner")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Nric("F0123456B"),
                getTagSet("owner")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Nric("S0163456E"),
                getTagSet("owner")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Nric("F0123056T"),
                getTagSet("owner")),
            new Person(new Name("Fuji Syuusuke"), new Phone("90245123"), new Email("fujis@example.com"),
                new Address("Blk 106 Bukit Purmei Street 10, #20-20"), new Nric("S9015638A"),
                getTagSet("supplier"))
        };
    }

    public static PetPatient[] getSamplePetPatients() {
        return new PetPatient[] {
            new PetPatient(new PetPatientName("Ane"), "Cat", "Siamese",
                    "Brown", "A", new Nric("S0123456B"), getTagSet("Hostile")),
            new PetPatient(new PetPatientName("Bei"), "Cat", "British Shorthair",
                    "Grey", "B", new Nric("T0123456C"), getTagSet("Overfriendly")),
            new PetPatient(new PetPatientName("Nei"), "Cat", "Maine Coon",
                    "Black", "AB", new Nric("T0123456C"), getTagSet("Aggressive")),
            new PetPatient(new PetPatientName("Chae"), "Cat", "Russian Blue",
                    "Grey", "A", new Nric("G0123456A"), getTagSet("Naive")),
            new PetPatient(new PetPatientName("Don"), "Dog", "German Shepherd",
                    "Brown", "DEA 4", new Nric("F0123456B"), getTagSet("Aggressive")),
            new PetPatient(new PetPatientName("Este"), "Dog", "Golden Retriever",
                    "Golden", "DEA 6", new Nric("S0163456E"), getTagSet("Overfriendly")),
            new PetPatient(new PetPatientName("Famm"), "Dog", "Pug",
                    "Golden", "DEA 1.1-", new Nric("F0123056T"), getTagSet("3legged")),
            new PetPatient(new PetPatientName("Plan"), "Dog", "Siberian Husky",
                    "White", "DEA 1.1+", new Nric("F0123056T"), getTagSet("Hostile")),
        };
    }

    public static Appointment[] getSampleAppointments() {
        return new Appointment[] {
            new Appointment(new Nric("S0123456B"), new PetPatientName("Ane"), new Remark("nil"),
                    getLocalDateTime("2018-10-01 10:30"), getTagSet("Checkup")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Bei"), new Remark("nil"),
                    getLocalDateTime("2018-10-02 10:30"), getTagSet("Presurgery")),
            new Appointment(new Nric("F0123056T"), new PetPatientName("Famm"), new Remark("Home visit"),
                    getLocalDateTime("2018-10-03 10:30"), getTagSet("Vaccination")),
            new Appointment(new Nric("F0123056T"), new PetPatientName("Plan"), new Remark("Home visit"),
                    getLocalDateTime("2018-10-03 11:00"), getTagSet("Vaccination")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Bei"), new Remark("nil"),
                    getLocalDateTime("2018-10-06 10:30"), getTagSet("Surgery")),
            new Appointment(new Nric("G0123456A"), new PetPatientName("Chae"), new Remark("nil"),
                    getLocalDateTime("2018-10-07 09:30"), getTagSet("Checkup")),
            new Appointment(new Nric("F0123456B"), new PetPatientName("Don"), new Remark("nil"),
                    getLocalDateTime("2018-10-07 15:30"), getTagSet("Microchipping")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Bei"), new Remark("nil"),
                    getLocalDateTime("2018-10-09 15:30"), getTagSet("Postsurgery")),
            new Appointment(new Nric("T0123456C"), new PetPatientName("Nei"), new Remark("nil"),
                    getLocalDateTime("2018-10-09 16:00"), getTagSet("Checkup")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (PetPatient petPatient : getSamplePetPatients()) {
                sampleAb.addPetPatient(petPatient);
            }
            for (Appointment appointment : getSampleAppointments()) {
                sampleAb.addAppointment(appointment);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateNricException e) {
            throw new AssertionError("sample data cannot contain duplicate NRIC values", e);
        } catch (DuplicatePetPatientException e) {
            throw new AssertionError("sample data cannot contain duplicate pet patients", e);
        } catch (DuplicateDateTimeException e) {
            throw new AssertionError("sample data cannot contain double booked appointments", e);
        } catch (DuplicateAppointmentException e) {
            throw new AssertionError("sample data cannot contain duplicate appointments", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    /**
     * Returns a LocalDateTime object of the given string.
     */
    private static LocalDateTime getLocalDateTime(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(string, formatter);
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedAppointment.java
``` java
/**
 * JAXB-friendly version of an Appointment.
 */
public class XmlAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment %s field is missing!";

    @XmlElement(required = true)
    private String ownerNric;
    @XmlElement(required = true)
    private String petPatientName;
    @XmlElement(required = true)
    private String remark;
    @XmlElement(required = true)
    private String dateTime;

    @XmlElement
    private List<XmlAdaptedTag> appointmentTagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedAppointment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given appointment details.
     */
    public XmlAdaptedAppointment(String ownerNric, String petPatientName, String remark,
                                 String dateTime, List<XmlAdaptedTag> appointmentTagged) {
        this.ownerNric = ownerNric;
        this.petPatientName = petPatientName;
        this.remark = remark;
        this.dateTime = dateTime;
        if (appointmentTagged != null) {
            this.appointmentTagged = new ArrayList<>(appointmentTagged);
        }
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointment
     */
    public XmlAdaptedAppointment(Appointment source) {
        ownerNric = source.getOwnerNric().toString();
        petPatientName = source.getPetPatientName().toString();
        remark = source.getRemark().value;
        dateTime = source.getFormattedLocalDateTime();
        appointmentTagged = new ArrayList<>();
        for (Tag tag : source.getAppointmentTags()) {
            appointmentTagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment
     */
    public Appointment toModelType() throws IllegalValueException {
        final List<Tag> appointmentTags = new ArrayList<>();
        for (XmlAdaptedTag tag : appointmentTagged) {
            appointmentTags.add(tag.toModelType());
        }

        if (this.ownerNric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(this.ownerNric)) {
            throw new IllegalValueException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        final Nric ownerNric = new Nric(this.ownerNric);

        if (this.petPatientName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, PetPatientName.class.getSimpleName()));
        }
        if (!PetPatientName.isValidName(this.petPatientName)) {
            throw new IllegalValueException(PetPatientName.MESSAGE_PET_NAME_CONSTRAINTS);
        }
        final PetPatientName petPatientName = new PetPatientName(this.petPatientName);

        if (this.remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(this.remark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }

        final Remark remark = new Remark(this.remark);

        if (this.dateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = null;

        try {
            localDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Please follow the format of yyyy-MM-dd HH:mm");
        }

        final LocalDateTime dateTime = localDateTime;


        final Set<Tag> thisAppointmentTags = new HashSet<>(appointmentTags);
        return new Appointment(ownerNric, petPatientName, remark, dateTime, thisAppointmentTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }

        XmlAdaptedAppointment otherAppointment = (XmlAdaptedAppointment) other;
        return Objects.equals(ownerNric, otherAppointment.ownerNric)
                && Objects.equals(petPatientName, otherAppointment.petPatientName)
                && Objects.equals(remark, otherAppointment.remark)
                && Objects.equals(dateTime, otherAppointment.dateTime)
                && appointmentTagged.equals(otherAppointment.appointmentTagged);
    }
}
```

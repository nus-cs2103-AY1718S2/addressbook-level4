# aquarinte
###### \java\seedu\address\commons\events\ui\ChangeThemeRequestEvent.java
``` java
/**
 * Indicates a request to change Medeina's theme
 */
public class ChangeThemeRequestEvent extends BaseEvent {
    public final Theme theme;

    public ChangeThemeRequestEvent(Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
/**
 * Adds a Person, Petpatient and/or Appointment to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = "To add a new person: "
            + COMMAND_WORD + " -o " + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "To add a new pet patient: "
            + COMMAND_WORD + " -p " + PREFIX_NAME + "NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]... -o " + PREFIX_NRIC + "OWNER_NRIC\n"
            + "To add a new appointment: "
            + COMMAND_WORD + " -a " + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + "[" + PREFIX_TAG + "TYPE OF APPOINTMENT]... -o " + PREFIX_NRIC + "OWNER_NRIC -p "
            + PREFIX_NAME + " PET_NAME\n"
            + "To add all new: " + COMMAND_WORD + " -o " + PREFIX_NAME + "OWNER_NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]... -p " + PREFIX_NAME + "PET_NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]... -a "  + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_TAG + "TYPE OF APPOINTMENT...";

    public static final String MESSAGE_PERSON = "Option -o : Person details. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + "-o "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_NRIC + "S1234567Q "
            + PREFIX_TAG + "medical supplier";

    public static final String MESSAGE_APPOINTMENT = "Option -a : Appointment details. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_TAG + "TYPE OF APPOINTMENT...\n"
            + "Example: " + "-a "
            + PREFIX_DATE + "2018-12-31 12:30 "
            + PREFIX_REMARK + "nil "
            + PREFIX_TAG + "checkup "
            + PREFIX_TAG + "vaccination";

    public static final String MESSAGE_PETPATIENT = COMMAND_WORD + " -p : Pet Patient details. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + "-p "
            + PREFIX_NAME + "Jewel "
            + PREFIX_SPECIES + "Cat "
            + PREFIX_BREED + "Persian Ragdoll "
            + PREFIX_COLOUR + "Calico "
            + PREFIX_BLOODTYPE + "AB";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s\n";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in Medeina.";
    public static final String MESSAGE_DUPLICATE_NRIC = "This is already someone with this NRIC.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This particular appointment already exists in Medeina.";
    public static final String MESSAGE_DUPLICATE_DATETIME = "This date time is already taken by another appointment.";
    public static final String MESSAGE_DUPLICATE_PET_PATIENT = "This pet patient already exists in Medeina";
    public static final String MESSAGE_INVALID_NRIC = "The specified NRIC does not belong to anyone in Medeina."
            + " Please add a new person.";
    public static final String MESSAGE_MISSING_NRIC_PREFIX = "Missing prefix \"nr/\" for NRIC after -o option";
    public static final String MESSAGE_INVALID_PET_PATIENT = "The specified pet cannot be found under the specified "
            + "owner in Medeina. Please add a new pet patient.";

    private Person person;
    private PetPatient petPatient;
    private Appointment appt;
    private Nric ownerNric;
    private PetPatientName petPatientName;
    private int type;
    private String message = "New person added: %1$s\n";

    /**
     * Creates an AddCommand to add the specified {@code Person} and {@code PetPatient} and {@code Appointment}.
     */
    public AddCommand(Person person, PetPatient petPatient, Appointment appt) {
        requireNonNull(person);
        requireNonNull(petPatient);
        requireNonNull(appt);
        this.person = person;
        this.petPatient = petPatient;
        this.appt = appt;
        type = 1;
        message += "New pet patient added: %2$s\nNew appointment made: %3$s";
    }

    /**
     * Creates an AddCommand to add the specified {@code Appointment} if an existing Person object getNric() is
     * equivalent to {@code Nric}, and an existing PetPatient object getOwner() is equivalent to {@code ownerNric}
     * and getName() equivalent to {@code PetPatientName}.
     */
    public AddCommand(Appointment appt, Nric ownerNric, PetPatientName petPatientName) {
        requireNonNull(appt);
        requireNonNull(ownerNric);
        requireNonNull(petPatientName);
        this.appt = appt;
        this.ownerNric = ownerNric;
        this.petPatientName = petPatientName;
        type = 2;
        message = "New appointment made: %1$s\nunder owner: %2$s\nfor pet patient: %3$s";
    }

    /**
     * Creates an AddCommand to add the specified {@code PetPatient} if an existing Person object getNric() is
     * equivalent to {@code Nric}.
     */
    public AddCommand(PetPatient petPatient, Nric ownerNric) {
        requireNonNull(petPatient);
        requireNonNull(ownerNric);
        this.petPatient = petPatient;
        this.ownerNric = ownerNric;
        type = 3;
        message = "New pet patient added: %1$s \nunder owner: %2$s";
    }

    /**
     * Creates an AddCommand to add the specified {@code Person}.
     */
    public AddCommand(Person owner) {
        requireNonNull(owner);
        person = owner;
        type = 4;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            switch (type) {
            case 1: return addAllNew();
            case 2: return addNewAppt();
            case 3: return addNewPetPatient();
            case 4: return addNewPerson();
            default: throw new CommandException(MESSAGE_USAGE);
            }

        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (DuplicateNricException e) {
            throw new CommandException(MESSAGE_DUPLICATE_NRIC);
        } catch (DuplicatePetPatientException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PET_PATIENT);
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        } catch (DuplicateDateTimeException e) {
            throw new CommandException(MESSAGE_DUPLICATE_DATETIME);
        }
    }

    private CommandResult addNewPerson() throws DuplicatePersonException, DuplicateNricException {
        model.addPerson(person);
        return new CommandResult(String.format(message, person));
    }

    /**
     * Add a new pet patient under an existing person.
     */
    private CommandResult addNewPetPatient() throws DuplicatePetPatientException, CommandException {
        person = model.getPersonWithNric(ownerNric);
        if (person != null) {
            model.addPetPatient(petPatient);
            return new CommandResult(String.format(message, petPatient, person));
        }
        throw new CommandException(MESSAGE_INVALID_NRIC);
    }

    /**
     * Add a new appointment for an existing pet patient under an existing person.
     */
    private CommandResult addNewAppt() throws CommandException, DuplicateAppointmentException,
            DuplicateDateTimeException {
        person = model.getPersonWithNric(ownerNric);
        petPatient = model.getPetPatientWithNricAndName(ownerNric, petPatientName);

        if (person == null) {
            throw new CommandException(MESSAGE_INVALID_NRIC);
        }

        if (petPatient == null) {
            throw new CommandException(MESSAGE_INVALID_PET_PATIENT);
        }

        model.addAppointment(appt);
        return new CommandResult(String.format(message, appt, person, petPatient));
    }

    /**
     * Add a new appointment, a new pet patient and a new person.
     * (New appointment for the new patient under a new person).
     */
    private CommandResult addAllNew() throws DuplicatePersonException, DuplicateNricException,
            DuplicatePetPatientException, DuplicateAppointmentException, DuplicateDateTimeException {
        model.addPerson(person);
        model.addPetPatient(petPatient);
        model.addAppointment(appt);
        return new CommandResult(String.format(message, person, petPatient, appt));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;

        boolean personSame = isTheSame(person, otherAddCommand.person);
        boolean petPatientSame = isTheSame(petPatient, otherAddCommand.petPatient);
        boolean appointmentSame = isTheSame(appt, otherAddCommand.appt);

        if (personSame && petPatientSame && appointmentSame) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if both objects are the same.
     * Returns true if both objects are equivalent.
     * Returns true if both objects are null.
     */
    public boolean isTheSame(Object one, Object two) {
        if (one != null && two != null) {
            if (one.equals(two)) {
                return true;
            }
        }

        if (one == null && two == null) {
            return true;
        }

        return false;
    }
}
```
###### \java\seedu\address\logic\commands\ChangeThemeCommand.java
``` java
/**
 * Change the theme of Medeina
 */
public class ChangeThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change Medeina's theme to the"
            + "specified theme name (case-insensitive)\n"
            + "Parameters: THEME NAME\n"
            + "Example: " + COMMAND_WORD + " light";

    private String result;

    private final Theme theme;

    public ChangeThemeCommand(Theme theme) {
        requireNonNull(theme);
        this.theme = theme;
        result = "Current theme: " + theme.getThemeName();
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(theme));
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && theme.equals(((ChangeThemeCommand) other).theme));
    }
}
```
###### \java\seedu\address\logic\CommandSyntaxWords.java
``` java
/**
 * Stores all command syntax used in Medeina: command words, prefixes and options.
 */
public class CommandSyntaxWords {

    private static CommandSyntaxWords instance;

    private static final Set<String> commandWords = Stream.of(
            AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD, ChangeThemeCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
            HistoryCommand.COMMAND_WORD).collect(Collectors.toSet());

    private static final Set<String> prefixes = Stream.of(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_NRIC, PREFIX_BREED, PREFIX_SPECIES,
            PREFIX_COLOUR, PREFIX_BLOODTYPE, PREFIX_DATE, PREFIX_REMARK, PREFIX_TAG)
            .map(p -> p.toString())
            .collect(Collectors.toSet());

    private static final Set<String> options = Stream.of("-o", "-p", "-a").collect(Collectors.toSet());

    public static CommandSyntaxWords getInstance() {
        if (instance == null) {
            instance = new CommandSyntaxWords();
        }
        return instance;
    }

    public Set<String> getCommandWords() {
        return commandWords;
    }

    public Set<String> getPrefixes() {
        return prefixes;
    }

    public Set<String> getOptions() {
        return options;
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public Set<String> getAllCommandWords() {
        return commandSyntax.getCommandWords();
    }

    @Override
    public Set<String> getAllPrefixes() {
        return commandSyntax.getPrefixes();
    }

    @Override
    public Set<String> getAllOptions() {
        return commandSyntax.getOptions();
    }

    @Override
    public Set<String> getAllNric() {
        Set<String> allNricInModel = new HashSet<>();
        for (Person p : model.getAddressBook().getPersonList()) {
            allNricInModel.add(p.getNric().toString());
        }
        return allNricInModel;
    }

    @Override
    public Set<String> getAllPetPatientNames() {
        Set<String> allPetPatientNamesInModel = new HashSet<>();
        for (PetPatient p : model.getAddressBook().getPetPatientList()) {
            allPetPatientNamesInModel.add(p.getName().toString());
        }
        return allPetPatientNamesInModel;
    }

    @Override
    public Set<String> getAllTagNames() {
        Set<String> getTagNamesInModel = new HashSet<>();
        for (Tag t : model.getTagList()) {
            getTagNamesInModel.add(t.tagName);
        }
        return getTagNamesInModel;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Pattern ADD_COMMAND_FORMAT_OWNER_ONLY = Pattern.compile("-(o)+(?<ownerInfo>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_ALL_NEW = Pattern.compile("-(o)+(?<ownerInfo>.*)"
            + "-(p)+(?<petInfo>.*)-(a)+(?<apptInfo>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_NEW_PET_EXISTING_OWNER = Pattern.compile("-(p)+(?<petInfo>.*)"
            + "-(o)+(?<ownerNric>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_NEW_APPT_EXISTING_OWNER_PET = Pattern.compile("-(a)+(?<apptInfo>.*)"
            + "-(o)(?<ownerNric>.*)" + "-(p)+(?<petName>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the Person class
     * and returns an Person object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public Person parsePerson(String ownerInfo) throws ParseException {
        ArgumentMultimap argMultimapOwner =
                ArgumentTokenizer.tokenize(ownerInfo, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_NRIC, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimapOwner, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_NRIC)
                || !argMultimapOwner.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_PERSON));
        }

        try {
            Name ownerName = ParserUtil.parseName(argMultimapOwner.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimapOwner.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimapOwner.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimapOwner.getValue(PREFIX_ADDRESS)).get();
            Nric nric = ParserUtil.parseNric(argMultimapOwner.getValue(PREFIX_NRIC)).get();
            Set<Tag> ownerTagList = ParserUtil.parseTags(argMultimapOwner.getAllValues(PREFIX_TAG));

            Person owner = new Person(ownerName, phone, email, address, nric, ownerTagList);

            return owner;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the Appointment class
     * and returns an Appointment object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public Appointment parseAppointment(String apptInfo) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(apptInfo, PREFIX_DATE, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_REMARK, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_APPOINTMENT));
        }

        try {
            LocalDateTime localDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE)).get();
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
            Set<Tag> type = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Appointment appointment = new Appointment(remark, localDateTime, type);

            return appointment;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the PetPatient class
     * and returns an PetPatient object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public PetPatient parsePetPatient(String petInfo) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(petInfo, PREFIX_NAME, PREFIX_SPECIES, PREFIX_BREED, PREFIX_COLOUR,
                        PREFIX_BLOODTYPE, PREFIX_TAG);

        if (!arePrefixesPresent(
                argMultimap, PREFIX_NAME, PREFIX_BREED, PREFIX_SPECIES, PREFIX_COLOUR, PREFIX_BLOODTYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_PETPATIENT));
        }

        try {
            PetPatientName name = ParserUtil.parsePetPatientName(argMultimap.getValue(PREFIX_NAME)).get();
            String species = ParserUtil.parseSpecies(argMultimap.getValue(PREFIX_SPECIES)).get();
            String breed = ParserUtil.parseBreed(argMultimap.getValue(PREFIX_BREED)).get();
            String color = ParserUtil.parseColour(argMultimap.getValue(PREFIX_COLOUR)).get();
            String bloodType = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            PetPatient petPatient = new PetPatient(name, species, breed, color, bloodType, tagList);

            return petPatient;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the Nric class
     * and returns a Nric object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public Nric parseNric(String nric) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(nric, PREFIX_NRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Missing prefix \"nr/\" for NRIC after -o option"));
        }

        try {
            Nric validNric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC)).get();

            return validNric;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the PetPatientName class
     * and returns a PetPatientName object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public PetPatientName parsePetPatientName(String petName) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(petName, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCommand.MESSAGE_MISSING_NRIC_PREFIX));
        }

        try {
            PetPatientName petPatientName = ParserUtil.parsePetPatientName(argMultimap.getValue(PREFIX_NAME)).get();

            return petPatientName;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private AddCommand createNewOwnerPetAppt(String ownerInfo, String petInfo, String apptInfo)
            throws ParseException {
        Person owner = parsePerson(ownerInfo);

        PetPatient petPatient = parsePetPatient(petInfo);
        petPatient.setOwnerNric(owner.getNric());

        Appointment appt = parseAppointment(apptInfo);
        appt.setOwnerNric(owner.getNric());
        appt.setPetPatientName(petPatient.getName());

        return new AddCommand(owner, petPatient, appt);
    }

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private AddCommand createNewApptforExistingOwnerAndPet(String apptInfo, String ownerNric, String petName)
            throws ParseException {
        Nric nric = parseNric(ownerNric);
        PetPatientName petPatientName = parsePetPatientName(petName);

        Appointment appt = parseAppointment(apptInfo);
        appt.setOwnerNric(nric);
        appt.setPetPatientName(petPatientName);

        return new AddCommand(appt, nric, petPatientName);
    }

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private AddCommand createNewPetForExistingPerson(String petInfo, String ownerNric) throws ParseException {
        Nric nric = parseNric(ownerNric);

        PetPatient petPatient = parsePetPatient(petInfo);
        petPatient.setOwnerNric(nric);

        return new AddCommand(petPatient, nric);
    }

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private AddCommand parseNewOwnerOnly(String ownerInfo) throws ParseException {
        Person owner = parsePerson(ownerInfo);
        return new AddCommand(owner);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        //to add a new person (owner), new pet patient, and a new appointment
        final Matcher matcherForAllNew = ADD_COMMAND_FORMAT_ALL_NEW.matcher(trimmedArgs);
        if (matcherForAllNew.matches()) {
            String ownerInfo = matcherForAllNew.group("ownerInfo");
            String petInfo = matcherForAllNew.group("petInfo");
            String apptInfo = matcherForAllNew.group("apptInfo");
            return createNewOwnerPetAppt(ownerInfo, petInfo, apptInfo);
        }
        //add a new appointment for existing person and pet patient
        final Matcher matcherForNewAppt = ADD_COMMAND_FORMAT_NEW_APPT_EXISTING_OWNER_PET.matcher(trimmedArgs);
        if (matcherForNewAppt.matches()) {
            String apptInfo = matcherForNewAppt.group("apptInfo");
            String ownerNric = matcherForNewAppt.group("ownerNric");
            String petName = matcherForNewAppt.group("petName");
            return createNewApptforExistingOwnerAndPet(apptInfo, ownerNric, petName);
        }

        //add a new patient to an existing owner
        final Matcher matcherForNewPet = ADD_COMMAND_FORMAT_NEW_PET_EXISTING_OWNER.matcher(trimmedArgs);
        if (matcherForNewPet.matches()) {
            String petInfo = matcherForNewPet.group("petInfo");
            String ownerNric = matcherForNewPet.group("ownerNric");
            return createNewPetForExistingPerson(petInfo, ownerNric);
        }

        //add a new person
        final Matcher matcherForNewPerson = ADD_COMMAND_FORMAT_OWNER_ONLY.matcher(trimmedArgs);
        if (matcherForNewPerson.matches()) {
            String ownerInfo = matcherForNewPerson.group("ownerInfo");
            return parseNewOwnerOnly(ownerInfo);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

    }

}
```
###### \java\seedu\address\logic\parser\ChangeThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ChangeThemeCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeThemeCommand
     * and returns a ChangeThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ChangeThemeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }

        String[] splitArgs = trimmedArgs.split(" ");
        if (splitArgs.length > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }

        if (!Theme.isValidThemeName(splitArgs[0].toLowerCase())) {
            throw new ParseException(Theme.MESSAGE_THEME_CONSTRAINTS);
        }

        return new ChangeThemeCommand(new Theme(splitArgs[0].toLowerCase()));
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Person getPersonWithNric(Nric ownerNric) {
        for (Person p : addressBook.getPersonList()) {
            if (p.getNric().equals(ownerNric)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public PetPatient getPetPatientWithNricAndName(Nric ownerNric, PetPatientName petPatientName) {
        for (PetPatient p : addressBook.getPetPatientList()) {
            if (p.getOwner().equals(ownerNric) && p.getName().equals(petPatientName)) {
                return p;
            }
        }
        return null;
    }

```
###### \java\seedu\address\model\theme\Theme.java
``` java
/**
 * Represents a Theme in the address book.
 */
public class Theme {

    private static String[] themes = {"dark", "light"};
    private static String[] themesLocation = {"/view/DarkTheme.css", "/view/LightTheme.css"};

    public static final String MESSAGE_THEME_CONSTRAINTS = "Please specify one of the following themes:\n"
            + Arrays.stream(themes).collect(Collectors.joining(", "));

    public final String selectedThemePath;

    /**
     * Constructs a {@code Theme}.
     *
     * @param themeName A valid theme name.
     */
    public Theme(String themeName) {
        requireNonNull(themeName);
        checkArgument(isValidThemeName(themeName.toLowerCase()), MESSAGE_THEME_CONSTRAINTS);
        selectedThemePath = themesLocation[Arrays.asList(themes).indexOf(themeName.toLowerCase())];
    }

    /**
     * Returns true if a given string is a valid theme name.
     */
    public static boolean isValidThemeName(String themeName) {
        boolean isValid = Arrays.stream(themes).anyMatch(themeName::equals);
        return isValid;
    }

    public String getThemeName() {
        return themes[Arrays.asList(themesLocation).indexOf(selectedThemePath)];
    }

    public String getThemePath() {
        return selectedThemePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Theme // instanceof handles nulls
                && this.selectedThemePath.equals(((Theme) other).selectedThemePath)); // state check
    }

    @Override
    public int hashCode() {
        return selectedThemePath.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + selectedThemePath + ']';
    }
}
```
###### \java\seedu\address\ui\Autocomplete.java
``` java
/**
 * Handles case-insensitive autocompletion of command input such as command word, options, prefixes
 * and also some user input parameters: Nric, pet patient name and tag.
 */
public class Autocomplete {

    private static Autocomplete instance;
    private Logic logic;
    private String targetWord;

    public static Autocomplete getInstance() {
        if (instance == null) {
            instance = new Autocomplete();
        }
        return instance;
    }

    /**
     * Find suggestions for current user-input in commandTextField.
     */
    public List<String> getSuggestions(Logic logic, TextField commandTextField) {
        this.logic = logic;
        String userInput = commandTextField.getText().trim();
        String[] words = userInput.split(" ");
        targetWord = words[words.length - 1].toLowerCase();

        if (words.length == 1) {
            return suggestCommandWords();
        } else {
            if (addReferenceOwnerNric(words) || editPetPatientOwnerNric(words) || findByPersonNric(words)) {
                return suggestNrics();
            }

            if (words[words.length - 2].equals("-p") && targetWord.startsWith("n/")) {
                return suggestPetPatientNames();
            }

            if (targetWord.startsWith("t/")) {
                return suggestTagNames();
            }

            if (targetWord.startsWith("-")) {
                return suggestOptions();
            }

            return suggestPrefixes();
        }
    }

    /**
     * Checks if command input is the "add" command, and whether it has the form "-o nr/" at the end.
     */
    private boolean addReferenceOwnerNric(String[] words) {
        if (words[0].equals("add") && words[words.length - 2].equals("-o") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input is the "edit -p" command, with the last word starting with "nr/".
     */
    private boolean editPetPatientOwnerNric(String[] words) {
        if (words[0].equals("edit") && words[1].equals("-p") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if command input is the "edit -p" command, with the last word starting with "nr/".
     */
    private boolean findByPersonNric(String[] words) {
        if (words[0].equals("find") && words[1].equals("-o") && targetWord.startsWith("nr/")) {
            return true;
        }
        return false;
    }

    /**
     * Returns a sorted list of suggestions for tags.
     */
    private List<String> suggestTagNames() {
        if (targetWord.equals("t/")) {
            List<String> suggestions = logic.getAllTagNames().stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetTag = splitByPrefix[1];
            List<String> suggestions = logic.getAllTagNames().stream()
                    .filter(t -> t.toLowerCase().startsWith(targetTag) && !t.toLowerCase().equals(targetTag))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for pet patient names.
     */
    private List<String> suggestPetPatientNames() {
        if (targetWord.equals("n/")) {
            List<String> suggestions = logic.getAllPetPatientNames().stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetPetName = splitByPrefix[1];
            List<String> suggestions = logic.getAllPetPatientNames().stream()
                    .filter(pn -> pn.startsWith(targetPetName) && !pn.equals(targetPetName))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for Nric.
     */
    private List<String> suggestNrics() {
        if (targetWord.equals("nr/")) {
            List<String> suggestions = logic.getAllNric().stream()
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;

        } else {
            String[] splitByPrefix = targetWord.split("/");
            String targetNric = splitByPrefix[1].toUpperCase();
            List<String> suggestions = logic.getAllNric().stream()
                    .filter(n -> n.startsWith(targetNric))
                    .sorted()
                    .collect(Collectors.toList());
            return suggestions;
        }
    }

    /**
     * Returns a sorted list of suggestions for prefixes.
     */
    private List<String> suggestPrefixes() {
        List<String> suggestions = logic.getAllPrefixes().stream()
                .filter(p -> p.startsWith(targetWord) && !p.equals(targetWord))
                .sorted()
                .collect(Collectors.toList());
        return suggestions;
    }

    /**
     * Returns a sorted list of suggestions for options.
     */
    private List<String> suggestOptions() {
        List<String> suggestions = logic.getAllOptions().stream()
                .filter(o -> o.startsWith(targetWord) && !o.equals(targetWord))
                .sorted()
                .collect(Collectors.toList());
        return suggestions;
    }

    /**
     * Returns a sorted list of suggestions for command words.
     */
    private List<String> suggestCommandWords() {
        List<String> suggestions = logic.getAllCommandWords().stream()
                .filter(c -> c.startsWith(targetWord) && !c.equals(targetWord))
                .sorted()
                .collect(Collectors.toList());
        return suggestions;
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Change the theme of the application
     */

    @Subscribe
    public void handleChangeThemeEvent(ChangeThemeRequestEvent event) {
        String style = this.getClass().getResource(event.theme.getThemePath()).toExternalForm();
        if (!isCurrentStyleSheet(style)) {
            changeStyleSheet(style);
        }
    }

    /**
     * Returns true if none of the current stylesheets contains {@code String} theme
     */
    public Boolean isCurrentStyleSheet(String theme) {
        if (getRoot().getScene().getStylesheets().contains(theme)) {
            return true;
        }
        return false;
    }

    /**
     * Removes all existing stylesheets and add the given {@code String} theme to style sheets
     * Re-add Extensions.css to style sheets.
     */
    public void changeStyleSheet(String theme) {
        String extensions = this.getClass().getResource("/view/Extensions.css").toExternalForm();
        getRoot().getScene().getStylesheets().clear(); //removes all style sheets
        getRoot().getScene().getStylesheets().add(theme);
        getRoot().getScene().getStylesheets().add(extensions); //re-add Extensions.css
    }
}
```

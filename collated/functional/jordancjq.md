# jordancjq
###### /java/seedu/address/commons/events/ui/RemoveSelectedTeamEvent.java
``` java
/**
 * Indicates a request to remove the selected team name.
 */
public class RemoveSelectedTeamEvent extends BaseEvent {

    public final TeamName teamName;

    public RemoveSelectedTeamEvent(TeamName teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/parser/RemoveCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveCommand object
 */
public class RemoveCommandParser implements Parser<RemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveComand
     * and returns a RemoveCommand object for execution.
     * @throws ParseException if the user input des not conform to the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {
        try {
            TeamName teamToRemove = ParserUtil.parseTeamName(args);
            return new RemoveCommand(teamToRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/RenameCommandParser.java
``` java
/**
 * Parses the input arguments and creates a new RenameCommand object
 */
public class RenameCommandParser implements Parser<RenameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenameCommand
     * and returns an RenameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenameCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_TEAM_NAME);

        if (!argMultiMap.getValue(PREFIX_TEAM_NAME).isPresent() || argMultiMap.getPreamble().isEmpty()
                || argMultiMap.getAllValues(PREFIX_TEAM_NAME).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameCommand.MESSAGE_USAGE));
        }

        TeamName target;
        TeamName toRename;

        try {
            target = ParserUtil.parseTeamName(argMultiMap.getPreamble());
            toRename = ParserUtil.parseTeamName(argMultiMap.getValue(PREFIX_TEAM_NAME)).get();
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (target.equals(toRename)) {
            throw new ParseException(RenameCommand.MESSAGE_NO_CHANGE);
        }

        return new RenameCommand(target, toRename);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses {@code String oneBasedIndexes} into a {@code List<Index>} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     */
    public static List<Index> parseIndexes(String oneBasedIndexes) throws IllegalValueException {
        String trimmedIndexes = oneBasedIndexes.trim();

        String[] splitOneBasedIndexes = trimmedIndexes.split("\\s+");

        Set<String> uniqueIndexes = new HashSet<>(Arrays.asList(splitOneBasedIndexes));

        List<Index> indexList = new ArrayList<>();

        for (String index : uniqueIndexes) {
            indexList.add(parseIndex(index));
        }

        return indexList;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(parsePhone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(parseAddress(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(parseEmail(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String teamName} into an {@code TeamName}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static TeamName parseTeamName(String teamName) throws IllegalValueException {
        requireNonNull(teamName);
        String trimmedTeamName = teamName.trim();
        if (!TeamName.isValidName(trimmedTeamName)) {
            throw new IllegalValueException(TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
        }

        return new TeamName(trimmedTeamName);
    }

    /**
     * Parses a {@code Optional<String> teamName} into an {@code Optional<TeamName>} if {@code teamName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TeamName> parseTeamName(Optional<String> teamName) throws IllegalValueException {
        requireNonNull(teamName);
        return teamName.isPresent() ? Optional.of(parseTeamName(teamName.get())) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> value} into the specified value or {@code UNSPECIFIED_FIELD} if is empty
     */
    public static Optional<String> parseValue(Optional<String> value, String messageConstraints)
            throws IllegalValueException {
        if (value.isPresent() && value.get().equals(UNSPECIFIED_FIELD)) {
            throw new IllegalValueException(messageConstraints);
        } else {
            return Optional.of(value.orElse(UNSPECIFIED_FIELD));
        }
    }

```
###### /java/seedu/address/logic/parser/AssignCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns an AssignCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TEAM_NAME, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_TEAM_NAME, PREFIX_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        try {
            TeamName teamName = ParserUtil.parseTeamName(argMultimap.getValue(PREFIX_TEAM_NAME).get());
            List<Index> indexList = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_INDEX).get());

            return new AssignCommand(teamName, indexList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
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
###### /java/seedu/address/logic/parser/ViewCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ViewCommandObject
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        TeamName targetTeam = new TeamName(trimmedArgs);

        return new ViewCommand(targetTeam);
    }
}
```
###### /java/seedu/address/logic/parser/CreateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CreateCommand object
 */
public class CreateCommandParser implements Parser<CreateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns an CreateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
        }
        if (!TeamName.isValidName(trimmedArgs) || trimmedArgs.equals(UNSPECIFIED_FIELD)) {
            throw new ParseException(MESSAGE_TEAM_NAME_CONSTRAINTS);
        }

        TeamName teamName = new TeamName(trimmedArgs);

        return new CreateCommand(new Team(teamName));
    }
}
```
###### /java/seedu/address/logic/commands/ViewCommand.java
``` java
/**
 * View a team identified using it's team name from the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_ALIAS = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the team identified by the team name.\n"
            + "Parameters: TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " Arsenal";

    public static final String MESSAGE_PARAMETERS = "TEAM_NAME";
    public static final String MESSAGE_VIEW_TEAM_SUCCESS = "Viewing Team: %1$s";

    private final TeamName targetTeam;

    public ViewCommand(TeamName targetTeam) {
        requireNonNull(targetTeam);
        this.targetTeam = targetTeam;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            EventsCenter.getInstance().post(new HighlightSelectedTeamEvent(targetTeam.toString()));
            model.updateFilteredPersonList(targetTeam);
        } catch (TeamNotFoundException tnfe) {
            throw new CommandException(Messages.MESSAGE_TEAM_NOT_FOUND);
        }
        return new CommandResult(String.format(MESSAGE_VIEW_TEAM_SUCCESS, targetTeam.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.targetTeam.equals(((ViewCommand) other).targetTeam)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/RemoveCommand.java
``` java
/**
 * Removes a team identified using the team name
 */
public class RemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the team specified by the team name.\n"
            + "Parameters: TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " Arsenal";

    public static final String MESSAGE_PARAMETERS = "TEAM_NAME";

    public static final String MESSAGE_REMOVE_TEAM_SUCCESS = "Removed Team: %1$s";

    private TeamName targetTeamName;

    public RemoveCommand(TeamName targetTeamName) {
        requireNonNull(targetTeamName);
        this.targetTeamName = targetTeamName;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.removeTeam(targetTeamName);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            EventsCenter.getInstance().post(new DeselectTeamEvent());
        } catch (TeamNotFoundException tnfe) {
            throw new CommandException(Messages.MESSAGE_TEAM_NOT_FOUND);
        }
        return new CommandResult(String.format(MESSAGE_REMOVE_TEAM_SUCCESS, targetTeamName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveCommand // instanceof handles nulls
                && this.targetTeamName.equals(((RemoveCommand) other).targetTeamName)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/AssignCommand.java
``` java
/**
 * Assigns a person to a team.
 */
public class AssignCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "assign";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a person or a group of person to a team "
            + "by the index number used in the last person listing.\n"
            + "Team of the person will be updated and will be added to team.\n"
            + "Parameters: "
            + PREFIX_TEAM_NAME + "TEAM_NAME "
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + "[INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TEAM_NAME + "Arsenal "
            + PREFIX_INDEX + "1 2";

    public static final String MESSAGE_PARAMETERS = PREFIX_TEAM_NAME + "TEAM_NAME "
            + PREFIX_INDEX + "INDEX "
            + "[INDEX]...";

    public static final String MESSAGE_SUCCESS = "Person assigned to team";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the team";

    private final TeamName targetTeam;
    private final List<Index> targetIndexes;

    private List<Person> personsToAssign;

    /**
     * Creates an AssignCommand to assign person to {@code Team}
     */
    public AssignCommand(TeamName targetTeam, List<Index> targetIndexes) {
        requireNonNull(targetTeam);
        requireNonNull(targetIndexes);

        this.targetTeam = targetTeam;
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            for (Person person : personsToAssign) {
                model.assignPersonToTeam(person, targetTeam);
            }
            model.updateFilteredPersonList(targetTeam);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (TeamNotFoundException tnfe) {
            throw new AssertionError("Impossible: Team should exist in this addressbook");
        }

        EventsCenter.getInstance().post(new HighlightSelectedTeamEvent(targetTeam.toString()));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (!model.getAddressBook().getTeamList().stream().anyMatch(t -> t.getTeamName().equals(targetTeam))) {
            throw new CommandException(Messages.MESSAGE_TEAM_NOT_FOUND);
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Index> executableIndexes = new ArrayList<>();
        Boolean canAssignPerson = false;

        for (Index idx : targetIndexes) {
            if (idx.getZeroBased() < lastShownList.size()) {
                executableIndexes.add(idx);
                canAssignPerson = true;
            }
        }

        if (!canAssignPerson) {
            throw new CommandException(Messages.MESSAGE_INVALID_ALL_INDEX);
        }

        personsToAssign = new ArrayList<>();
        executableIndexes.forEach(idx -> personsToAssign.add(lastShownList.get(idx.getZeroBased())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignCommand // instanceof handles nulls
                && this.targetTeam.equals(((AssignCommand) other).targetTeam)) // state check
                && this.targetIndexes.equals(((AssignCommand) other).targetIndexes);
    }
}
```
###### /java/seedu/address/logic/commands/RenameCommand.java
``` java
/**
 * Renames the existing team in the address book.
 */
public class RenameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rename";
    public static final String COMMAND_ALIAS = "rnt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames the specified team with the new "
            + "given team name.\n"
            + "Parameters: TEAM_NAME "
            + PREFIX_TEAM_NAME + "RENAME_TEAM_NAME\n"
            + "Example: " + COMMAND_WORD
            + " Arsenal "
            + PREFIX_TEAM_NAME + "Neo Arsenal";

    public static final String MESSAGE_PARAMETERS = "TEAM_NAME"
            + PREFIX_TEAM_NAME + "RENAME_TEAM_NAME";

    public static final String MESSAGE_RENAME_SUCCESS = "\"%1$s\" is renamed to \"%2$s\".";
    public static final String MESSAGE_NO_CHANGE = "This team name is already in use, try another name.";

    private final TeamName targetTeam;
    private final TeamName updatedTeamName;

    private Team teamToRename;

    /**
     * @param
     */
    public RenameCommand(TeamName targetTeam, TeamName updatedTeamName) {
        requireAllNonNull(targetTeam, updatedTeamName);

        this.targetTeam = targetTeam;
        this.updatedTeamName = updatedTeamName;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.renameTeam(teamToRename, updatedTeamName);
            EventsCenter.getInstance().post(new HighlightSelectedTeamEvent(updatedTeamName.toString()));
            model.updateFilteredPersonList(updatedTeamName);
        } catch (TeamNotFoundException tnfe) {
            throw new CommandException(Messages.MESSAGE_TEAM_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_RENAME_SUCCESS, targetTeam.toString(),
                updatedTeamName.toString()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (!model.getAddressBook().getTeamList().stream().anyMatch(t -> t.getTeamName().equals(targetTeam))) {
            throw new CommandException(Messages.MESSAGE_TEAM_NOT_FOUND);
        }

        List<Team> teams = model.getAddressBook().getTeamList();
        teamToRename = teams.stream().filter(t -> t.getTeamName().equals(targetTeam)).findFirst().get();

        if (teamToRename.getTeamName().equals(updatedTeamName)) {
            throw new CommandException(MESSAGE_NO_CHANGE);
        }

        if (model.getAddressBook().getTeamList().stream().anyMatch(t -> t.getTeamName().equals(updatedTeamName))) {
            throw new CommandException(MESSAGE_NO_CHANGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RenameCommand)) {
            return false;
        }

        // state check
        RenameCommand e = (RenameCommand) other;
        return Objects.equals(targetTeam, e.targetTeam)
                && updatedTeamName.equals(updatedTeamName);
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Creates a team in the manager.
     * @throws DuplicateTeamException if an equivalent team already exists.
     */
    public void createTeam(Team t) throws DuplicateTeamException {
        teams.add(t);
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Assigns a {@code person} to a {@code team}.
     * @throws TeamNotFoundException if the {@code team} is not found in this {@code AddressBook}.
     */
    public void assignPersonToTeam(Person person, TeamName teamName) throws DuplicatePersonException {
        Person newPersonWithTeam =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getRemark(), teamName, person.getTags(), person.getRating(), person.getPosition(),
                        person.getJerseyNumber());
        try {
            updatePerson(person, newPersonWithTeam);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("AddressBook should not have duplicate person after assigning team");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: AddressBook should contain this person");
        }

        teams.assignPersonToTeam(newPersonWithTeam, teams.getTeam(teamName));

        try {
            removePersonFromTeam(person, person.getTeamName());
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: Team should contain of this person");
        }
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes a {@code person} from a {@code team}.
     */
    public void removePersonFromTeam(Person person, TeamName teamName) throws PersonNotFoundException {
        if (!person.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
            try {
                teams.removePersonFromTeam(person, teams.getTeam(teamName));
            } catch (PersonNotFoundException pnfe) {
                throw new PersonNotFoundException();
            }
        }
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes a {@code team} from {@code teams}.
     */
    public void removeTeam(TeamName teamName) throws TeamNotFoundException {
        if (!teams.contains(teamName)) {
            throw new TeamNotFoundException();
        }

        Team teamToRemove = teams.getTeam(teamName);

        for (Person person : teamToRemove) {
            removeTeamFromPerson(person);
        }

        teams.remove(teamToRemove);
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes {@code teamName} from {@code person} in this {@code Team}.
     */
    private void removeTeamFromPerson(Person person) {
        Person personWithRemoveTeam =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getRemark(), new TeamName(UNSPECIFIED_FIELD), person.getTags(), person.getRating(),
                        person.getPosition(), person.getJerseyNumber());

        try {
            persons.setPerson(person, personWithRemoveTeam);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("AddressBook should not have duplicate person after assigning team");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: AddressBook should contain this person");
        }
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Renames {@code Team} with {@code updatedTeamName}.
     * @return
     */
    public void renameTeam(Team targetTeam, TeamName updatedTeamName) {
        try {
            List<Person> renameTeamPersonList = new ArrayList<>();

            for (Person person : persons) {
                if (person.getTeamName().equals(targetTeam.getTeamName())) {
                    renameTeamInPerson(person, updatedTeamName, targetTeam);
                    renameTeamPersonList.add(person);
                }
            }

            Team updatedTeam = new Team(updatedTeamName, targetTeam.getTeamPlayers());

            teams.setTeam(targetTeam, updatedTeam);
        } catch (DuplicateTeamException dte) {
            throw new AssertionError("AddressBook should not have duplicate team after renaming");
        } catch (TeamNotFoundException tnfe) {
            throw new AssertionError("Impossible: Teams should contain this team");
        }
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Renames {@code teamName} in {@code person} with {@code teamName}.
     */
    private void renameTeamInPerson(Person person, TeamName teamName, Team targetTeam) {
        Person toRename = person;
        Person personWithRenameTeam =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getRemark(), teamName, person.getTags(), person.getRating(),
                        person.getPosition(), person.getJerseyNumber());

        try {
            targetTeam.setPerson(toRename, personWithRenameTeam);
            persons.setPerson(toRename, personWithRenameTeam);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("AddressBook should not have duplicate person after assigning team");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: AddressBook should contain this person");
        }
    }
```
###### /java/seedu/address/model/team/Team.java
``` java
/**
 * Represents a Team in the application.
 * Guarantees: details are present and not null, field values are validated, im!mutable
 */
public class Team extends UniquePersonList {

    public static final String MESSAGE_TEAM_CONSTRAINTS = "Team names should be a string";

    private final TeamName teamName;

    /**
     * Every field must be present and not null.
     */
    public Team(TeamName teamName) {
        this.teamName = teamName;
    }

    /**
     * Constructs {@code Team} with {@code teamName} and {@code players}.
     * Every field must be present and not null.
     */
    public Team(TeamName teamName, List<Person> players) {
        this.teamName = teamName;
        try {
            super.setPersons(players);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Team should not have duplicated person from loading from database");
        }
    }

    public TeamName getTeamName() {
        return teamName;
    }

    public ObservableList<Person> getTeamPlayers() {
        return super.asObservableList();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTeamName())
                .append(", ")
                .append(super.asObservableList().size())
                .append(" players: ")
                .append("\n");
        getTeamPlayers().forEach(builder::append);
        return builder.toString();
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Team // instanceof handles nulls
                && this.teamName.equals(((Team) other).teamName));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(teamName);
    }
}
```
###### /java/seedu/address/model/team/UniqueTeamList.java
``` java
/**
 * A list of teams that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Team#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTeamList implements Iterable<Team> {

    private final ObservableList<Team> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent team as the given argument.
     */
    public boolean contains(Team toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns true if the list contains an equivalent team as the given argument.
     */
    public boolean contains(TeamName toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(t -> t.getTeamName().equals(toCheck));
    }

    /**
     * Returns {@code Team} that is specified by {@code toGet}.
     */
    public Team getTeam(TeamName toGet) {
        requireNonNull(toGet);
        return internalList.stream().filter(t -> t.getTeamName().equals(toGet)).findFirst().get();
    }

    /**
     * Adds a team to the list.
     *
     * @throws DuplicateTeamException if the team to add is a duplicate of an existing team in the list.
     */
    public void add(Team toAdd) throws DuplicateTeamException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTeamException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the team {@code target} in the list with {@code editedTeam}.
     *
     * @throws DuplicateTeamException if the replacement is equivalent to another existing team in the list.
     * @throws TeamNotFoundException if {@code target} could not be found in the list.
     */
    public void setTeam(Team target, Team editedTeam)
            throws DuplicateTeamException, TeamNotFoundException {
        requireNonNull(editedTeam);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TeamNotFoundException();
        }

        if (!target.equals(editedTeam) && internalList.contains(editedTeam)) {
            throw new DuplicateTeamException();
        }

        internalList.set(index, editedTeam);
    }

    public void setTeams(UniqueTeamList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTeams(List<Team> teams) throws DuplicateTeamException {
        requireAllNonNull(teams);
        final UniqueTeamList replacement = new UniqueTeamList();
        for (final Team team : teams) {
            replacement.add(team);
        }
        setTeams(replacement);
    }

    /**
     * Removes the equivalent team from the list.
     *
     * @throws TeamNotFoundException if no such team could be found in the list.
     */
    public boolean remove(Team toRemove) throws TeamNotFoundException {
        requireNonNull(toRemove);
        final boolean teamFoundAndDeleted = internalList.remove(toRemove);
        if (!teamFoundAndDeleted) {
            throw new TeamNotFoundException();
        }
        return teamFoundAndDeleted;
    }

    /**
     * Assign a {@code person} to a {@code team}.
     * @throws DuplicatePersonException if person already exist in the team
     */
    public void assignPersonToTeam(Person person, Team target) throws DuplicatePersonException {
        requireAllNonNull(person, target);

        if (target.getTeamPlayers().contains(person)) {
            throw new DuplicatePersonException();
        }

        target.add(person);
    }

    /**
     * Removes a {@code person} from a {@code team}.
     */
    public void removePersonFromTeam(Person person, Team target) throws PersonNotFoundException {
        requireAllNonNull(person, target);
        try {
            target.remove(person);
        } catch (PersonNotFoundException e) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Team> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Team> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTeamList // instanceof handles nulls
                && this.internalList.equals(((UniqueTeamList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### /java/seedu/address/model/team/TeamName.java
``` java
/**
 * Represents a Team's name in the application.
 * Guarantees: imm utable; is valid as declared in {@link #isValidName(String)}
 */
public class TeamName {
    public static final String MESSAGE_TEAM_NAME_CONSTRAINTS =
            "Team name should only contain alphanumeric characters and spaces,"
            + " and it should not be blank or consist of only numbers";

    /*
     * The first character of the team name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code TeamName}.
     *
     * @param teamName A valid team name.
     */
    public TeamName(String teamName) {
        requireNonNull(teamName);
        checkArgument(isValidName(teamName), MESSAGE_TEAM_NAME_CONSTRAINTS);
        this.fullName = teamName;
    }

    /**
     * Returns true if a given string is a valid team name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TeamName // instanceof handles nulls
                && this.fullName.equals(((TeamName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
```
###### /java/seedu/address/model/team/exceptions/DuplicateTeamException.java
``` java
/**
 * Signals that the operation will result in duplicate Team objects.
 */
public class DuplicateTeamException extends DuplicateDataException {
    public DuplicateTeamException() {
        super("Operation would result in duplicate teams");
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void createTeam(Team team) throws DuplicateTeamException {
        addressBook.createTeam(team);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void assignPersonToTeam(Person person, TeamName teamName) throws DuplicatePersonException {
        addressBook.assignPersonToTeam(person, teamName);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void removePersonFromTeam(Person person, TeamName teamName) throws PersonNotFoundException {
        requireAllNonNull(person, teamName);
        addressBook.removePersonFromTeam(person, teamName);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void removeTeam(TeamName teamName) throws TeamNotFoundException {
        requireNonNull(teamName);
        raise(new RemoveSelectedTeamEvent(teamName));
        addressBook.removeTeam(teamName);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void renameTeam(Team targetTeam, TeamName updatedTeamName) {
        requireAllNonNull(targetTeam, updatedTeamName);
        addressBook.renameTeam(targetTeam, updatedTeamName);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateFilteredPersonList(TeamName targetTeam) throws TeamNotFoundException {
        requireNonNull(targetTeam);

        List<Team> teamList = addressBook.getTeamList();

        if (teamList.stream().anyMatch(target -> target.getTeamName().equals(targetTeam))) {
            filteredPersons.setPredicate(t -> t.getTeamName().equals(targetTeam));
        } else {
            throw new TeamNotFoundException();
        }
    }

```

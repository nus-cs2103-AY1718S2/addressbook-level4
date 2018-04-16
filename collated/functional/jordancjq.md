# jordancjq
###### \java\seedu\address\commons\events\ui\RemoveSelectedTeamEvent.java
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
###### \java\seedu\address\logic\commands\AssignCommand.java
``` java
/**
 * Assigns a person to a team.
 */
public class AssignCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "assign";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a player or a group of players to a team "
            + "by the index number used in the last player listing.\n"
            + "Team of the player will be updated and will be added to team.\n"
            + "Only 1 team can be assigned to each player.\n"
            + "Parameters: "
            + "[TEAM_NAME] "
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + "[INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "Arsenal "
            + PREFIX_INDEX + "1 2";

    public static final String MESSAGE_PARAMETERS = "[TEAM_NAME] "
            + PREFIX_INDEX + "INDEX "
            + "[INDEX]...";

    public static final String MESSAGE_SUCCESS = "Players successfully assigned to team.";
    public static final String MESSAGE_FAILURE = "Not all players have been successfully processed.";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "\n%1$s: There is already the same player that exists in the team.";
    public static final String MESSAGE_NO_TEAM_TO_UNASSIGN = "\n%1$s: Cannot unassign player that is not in a team.";
    public static final String MESSAGE_TEAM_TO_TEAM_SUCCESS = "\n%1$s has been assigned from %2$s to %3$s.";
    public static final String MESSAGE_UNSPECIFIED_TEAM_SUCCESS = "\n%1$s has been assigned to %2$s.";
    public static final String MESSAGE_UNASSIGN_TEAM_SUCCESS = "\n%1$s has been unassigned from %2$s.";

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
        String successfulPlayerAssignedMessage = new String();

        if (targetTeam.toString().equals(UNSPECIFIED_FIELD)) {
            try {
                for (Person person : personsToAssign) {
                    model.unassignPersonFromTeam(person);
                    successfulPlayerAssignedMessage += String.format(MESSAGE_UNASSIGN_TEAM_SUCCESS,
                            person.getName().toString(), person.getTeamName().toString());
                }
            } catch (TeamNotFoundException tnfe) {
                successfulPlayerAssignedMessage += String.format(MESSAGE_NO_TEAM_TO_UNASSIGN, tnfe.getMessage());
                throw new CommandException(MESSAGE_FAILURE + successfulPlayerAssignedMessage);
            }
        } else {
            try {
                for (Person person : personsToAssign) {
                    model.assignPersonToTeam(person, targetTeam);
                    if (person.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
                        successfulPlayerAssignedMessage += String.format(MESSAGE_UNSPECIFIED_TEAM_SUCCESS,
                                person.getName().toString(), targetTeam.toString());
                    } else {
                        successfulPlayerAssignedMessage += String.format(MESSAGE_TEAM_TO_TEAM_SUCCESS,
                                person.getName().toString(), person.getTeamName().toString(), targetTeam.toString());
                    }
                }
                model.updateFilteredPersonList(targetTeam);
                EventsCenter.getInstance().post(new HighlightSelectedTeamEvent(targetTeam.toString()));
            } catch (DuplicatePersonException e) {
                successfulPlayerAssignedMessage += String.format(MESSAGE_DUPLICATE_PERSON, e.getMessage());
                throw new CommandException(MESSAGE_FAILURE + successfulPlayerAssignedMessage);
            } catch (TeamNotFoundException tnfe) {
                throw new AssertionError("Impossible: Team should exist in this addressbook");
            }
        }

        return new CommandResult(MESSAGE_SUCCESS + successfulPlayerAssignedMessage);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {

        if (!targetTeam.toString().equals(UNSPECIFIED_FIELD)
                && !model.getAddressBook().getTeamList().stream().anyMatch(t -> t.getTeamName().equals(targetTeam))) {
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
###### \java\seedu\address\logic\commands\CreateCommand.java
``` java
/**
 * Creates a team to the application
 */
public class CreateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "create";
    public static final String COMMAND_ALIAS = "ct";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Creates a team in MTM. "
            + "Parameters: "
            + "TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + "Arsenal";

    public static final String MESSAGE_PARAMETERS = "TEAM_NAME";

    public static final String MESSAGE_SUCCESS = "New team created: %1$s";
    public static final String MESSAGE_DUPLICATE_TEAM = "This team already exist in the manager";

    private final Team toCreate;

    /**
     * Creates a CreateCommand to add the specified (@code Team)
     *
     */
    public CreateCommand(Team team) {
        requireNonNull(team);
        this.toCreate = team;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.createTeam(toCreate);
        } catch (DuplicateTeamException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }
        EventsCenter.getInstance().post(new ShowNewTeamNameEvent(toCreate.getTeamName().toString()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toCreate.getTeamName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateCommand // instanceof handles nulls
                && toCreate.equals(((CreateCommand) other).toCreate));
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Updates the remark of an existing player in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the remark of the player identified "
            + "by the index number used in the last player listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Must put on field";

    public static final String MESSAGE_PARAMETERS = "INDEX";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Remark added to: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Remark removed from: %1$s";

    private final Index index;
    private final Remark remark;

    private Person personToEdit;
    private Person editedPerson;

    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToEdit);
        requireNonNull(editedPerson);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Updating remark should not result in duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target player cannot be missing");
        }

        EventsCenter.getInstance().post(new PersonDetailsChangedEvent(editedPerson, index));
        return new CommandResult(getSuccessMessage(editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getTeamName(), personToEdit.getTags(),
                personToEdit.getRating(), personToEdit.getPosition(), personToEdit.getJerseyNumber(),
                personToEdit.getAvatar());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }

    /**
     * Gets the corresponding success message based on the remark field from {@code personToEdit} after editing.
     */
    private String getSuccessMessage(Person person) {
        String message = remark.value.isEmpty() ? MESSAGE_DELETE_REMARK_SUCCESS : MESSAGE_ADD_REMARK_SUCCESS;
        return String.format(message, personToEdit);
    }
}
```
###### \java\seedu\address\logic\commands\RemoveCommand.java
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
###### \java\seedu\address\logic\commands\RenameCommand.java
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
        List<Team> teams = model.getAddressBook().getTeamList();

        if (!teams.stream().anyMatch(t -> t.getTeamName().equals(targetTeam))) {
            throw new CommandException(Messages.MESSAGE_TEAM_NOT_FOUND);
        }

        if (teams.stream().anyMatch(t -> t.getTeamName().equals(updatedTeamName))) {
            throw new CommandException(MESSAGE_NO_CHANGE);
        }

        teamToRename = teams.stream().filter(t -> t.getTeamName().equals(targetTeam)).findFirst().get();
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
###### \java\seedu\address\logic\commands\ViewCommand.java
``` java
/**
 * View a team identified using it's team name from the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_ALIAS = "vt";

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
###### \java\seedu\address\logic\parser\AssignCommandParser.java
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent() || argMultimap.getAllValues(PREFIX_INDEX).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        TeamName teamName;
        try {
            if (!argMultimap.getPreamble().isEmpty()) {
                teamName = ParserUtil.parseTeamName(argMultimap.getPreamble());
            } else {
                teamName = new TeamName(UNSPECIFIED_FIELD);
            }
            List<Index> indexList = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_INDEX).get());

            return new AssignCommand(teamName, indexList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\CreateCommandParser.java
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

        TeamName teamName;
        try {
            teamName = ParserUtil.parseTeamName(ParserUtil.parseValue(Optional.of(trimmedArgs),
                    MESSAGE_TEAM_NAME_CONSTRAINTS)).get();
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new CreateCommand(new Team(teamName));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
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

```
###### \java\seedu\address\logic\parser\ParserUtil.java
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
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }
}
```
###### \java\seedu\address\logic\parser\RemoveCommandParser.java
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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }

        try {
            TeamName teamToRemove = ParserUtil.parseTeamName(trimmedArgs);
            return new RemoveCommand(teamToRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\RenameCommandParser.java
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
            toRename = ParserUtil.parseTeamName(ParserUtil.parseValue(argMultiMap.getValue(PREFIX_TEAM_NAME),
                    TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS)).get();
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
###### \java\seedu\address\logic\parser\ViewCommandParser.java
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

        TeamName targetTeam;
        try {
            targetTeam = ParserUtil.parseTeamName(trimmedArgs);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new ViewCommand(targetTeam);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Creates a team in the manager.
     * @throws DuplicateTeamException if an equivalent team already exists.
     */
    public void createTeam(Team t) throws DuplicateTeamException {
        teams.add(t);
    }

    /**
     * Assigns a {@code person} to a {@code team}.
     * @throws TeamNotFoundException if the {@code team} is not found in this {@code AddressBook}.
     */
    public void assignPersonToTeam(Person person, TeamName teamName) throws DuplicatePersonException {
        teams.assignPersonToTeam(person, teams.getTeam(teamName));

        if (!person.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
            try {
                removePersonFromTeam(person, person.getTeamName());
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("Impossible: Team should contain of this person");
            }
        }

        Person newPersonWithTeam =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getRemark(), teamName, person.getTags(), person.getRating(), person.getPosition(),
                        person.getJerseyNumber(), person.getAvatar());

        if (!person.getTeamName().equals(newPersonWithTeam.getTeamName())) {
            try {
                updatePerson(person, newPersonWithTeam);
            } catch (DuplicatePersonException dpe) {
                throw new AssertionError("AddressBook should not have duplicate person after assigning team");
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("Impossible: AddressBook should contain this person");
            }
        }
    }

    /**
     * Unassigns a {@code person} from team.
     * @throws TeamNotFoundException if the {@code teamName} in {@code person} is {@code UNSPECIFIED_FIELD}.
     */
    public void unassignPersonFromTeam(Person person) throws TeamNotFoundException {
        if (person.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
            throw new TeamNotFoundException(person.getName().toString());
        }

        Person newPersonWithTeam =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getRemark(), new TeamName(UNSPECIFIED_FIELD), person.getTags(), person.getRating(),
                        person.getPosition(), person.getJerseyNumber(), person.getAvatar());

        try {
            removePersonFromTeam(person, person.getTeamName());
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: Team should contain of this person");
        }

        try {
            updatePerson(person, newPersonWithTeam);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("AddressBook should not have duplicate person after assigning team");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: AddressBook should contain this person");
        }
    }

    /**
     * Immediately add a {@code person} to a {@code team}.
     * @throws TeamNotFoundException if the {@code team} is not found in this {@code AddressBook}.
     */
    public void addPersonToTeam(Person person, TeamName teamName) throws DuplicatePersonException {
        teams.assignPersonToTeam(person, teams.getTeam(teamName));
    }

    /**
     * Removes a {@code person} from a {@code team}.
     */
    private void removePersonFromTeam(Person person, TeamName teamName) throws PersonNotFoundException {
        try {
            teams.removePersonFromTeam(person, teams.getTeam(teamName));
        } catch (PersonNotFoundException pnfe) {
            throw new PersonNotFoundException();
        }
    }

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

    /**
     * Removes {@code teamName} from {@code person} in this {@code Team}.
     */
    private void removeTeamFromPerson(Person person) {
        Person personWithRemoveTeam =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getRemark(), new TeamName(UNSPECIFIED_FIELD), person.getTags(), person.getRating(),
                        person.getPosition(), person.getJerseyNumber(), person.getAvatar());

        try {
            persons.setPerson(person, personWithRemoveTeam);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("AddressBook should not have duplicate person after assigning team");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: AddressBook should contain this person");
        }
    }

    /**
     * Renames {@code Team} with {@code updatedTeamName}.
     * @return
     */
    public void renameTeam(Team targetTeam, TeamName updatedTeamName) {
        try {
            List<Person> renameTeamPersonList = new ArrayList<>();

            for (Person person : persons) {
                if (person.getTeamName().equals(targetTeam.getTeamName())) {
                    renameTeamPersonList.add(renameTeamInPerson(person, updatedTeamName, targetTeam));
                }
            }

            Team updatedTeam = new Team(updatedTeamName, renameTeamPersonList);

            teams.setTeam(targetTeam, updatedTeam);
        } catch (DuplicateTeamException dte) {
            throw new AssertionError("AddressBook should not have duplicate team after renaming");
        } catch (TeamNotFoundException tnfe) {
            throw new AssertionError("Impossible: Teams should contain this team");
        }
    }

    /**
     * Renames {@code teamName} in {@code person} with {@code teamName}.
     */
    private Person renameTeamInPerson(Person person, TeamName teamName, Team targetTeam) {
        Person personWithRenameTeam =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getRemark(), teamName, person.getTags(), person.getRating(),
                        person.getPosition(), person.getJerseyNumber(), person.getAvatar());

        try {
            persons.setPerson(person, personWithRenameTeam);
            return personWithRenameTeam;
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("AddressBook should not have duplicate person after assigning team");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: AddressBook should contain this person");
        }
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void createTeam(Team team) throws DuplicateTeamException {
        addressBook.createTeam(team);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void assignPersonToTeam(Person person, TeamName teamName) throws DuplicatePersonException {
        addressBook.assignPersonToTeam(person, teamName);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void unassignPersonFromTeam(Person person) throws TeamNotFoundException {
        addressBook.unassignPersonFromTeam(person);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void removeTeam(TeamName teamName) throws TeamNotFoundException {
        requireNonNull(teamName);
        raise(new RemoveSelectedTeamEvent(teamName));
        addressBook.removeTeam(teamName);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void renameTeam(Team targetTeam, TeamName updatedTeamName) {
        requireAllNonNull(targetTeam, updatedTeamName);
        addressBook.renameTeam(targetTeam, updatedTeamName);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
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
###### \java\seedu\address\model\person\exceptions\DuplicatePersonException.java
``` java
public class DuplicatePersonException extends DuplicateDataException {
    public DuplicatePersonException() {
        super("Operation would result in duplicate persons");
    }

    public DuplicatePersonException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid}
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Remark can contain any values, can even be blank";

    public final String value;
    private boolean isPrivate;

    /**
     * Constructs a {@code Remark}
     *
     * @param remark Any remark
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
        this.isPrivate = false;
    }

    public Remark(String remark, boolean isPrivate) {
        this(remark);
        this.setPrivate(isPrivate);
    }

    @Override
    public String toString() {
        if (isPrivate) {
            return "<Private Remarks>";
        }
        return value;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void togglePrivacy() {
        this.isPrivate = isPrivate ? false : true;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
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
###### \java\seedu\address\model\team\exceptions\DuplicateTeamException.java
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
###### \java\seedu\address\model\team\exceptions\TeamNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified team.
 */
public class TeamNotFoundException extends Exception {

    public TeamNotFoundException() {};

    public TeamNotFoundException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\model\team\Team.java
``` java
/**
 * Represents a Team in the application.
 * Guarantees: details are present and not null, field values are validated, im!mutable
 */
public class Team extends UniquePersonList {

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
###### \java\seedu\address\model\team\TeamName.java
``` java
/**
 * Represents a Team's name in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TeamName {

    public static final String MESSAGE_TEAM_NAME_CONSTRAINTS =
            "Team name should only contain alphanumeric characters and spaces, and it should not be blank";

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
###### \java\seedu\address\model\team\UniqueTeamList.java
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
            throw new DuplicatePersonException(person.getName().toString());
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

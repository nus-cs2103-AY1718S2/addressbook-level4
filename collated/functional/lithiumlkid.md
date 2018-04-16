# lithiumlkid
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
/**
 * Adds a player to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a player to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_TEAM_NAME + "TEAMNAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_POSITION + "POSITION] "
            + "[" + PREFIX_JERSEY_NUMBER + "JERSEY_NUMBER] "
            + "[" + PREFIX_AVATAR + "AVATAR] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_RATING + "0 "
            + PREFIX_POSITION + "1 "
            + PREFIX_JERSEY_NUMBER + "17 "
            + PREFIX_TAG + "goodAttitude";

    public static final String MESSAGE_PARAMETERS = PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_POSITION + "POSITION] "
            + "[" + PREFIX_JERSEY_NUMBER + "JERSEY_NUMBER] "
            + "[" + PREFIX_AVATAR + "AVATAR] "
            + "[" + PREFIX_TAG + "TAG]";

    public static final String MESSAGE_SUCCESS = "New player added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This player already exists in the address book";
    public static final String MESSAGE_FILE_NOT_FOUND = "Avatar image file specified does not exist";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            if (!toAdd.getAvatar().toString().equals(UNSPECIFIED_FIELD)) {
                toAdd.getAvatar().setFilePath(toAdd.getName().fullName);
            }
            model.addPerson(toAdd);
            if (!toAdd.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
                model.assignPersonToTeam(toAdd, toAdd.getTeamName());
                model.updateFilteredPersonList(toAdd.getTeamName());
                EventsCenter.getInstance().post(new HighlightSelectedTeamEvent(toAdd.getTeamName().toString()));
            } else {
                EventsCenter.getInstance().post(new DeselectTeamEvent());
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (TeamNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_TEAM_NOT_FOUND);
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        TeamName teamName = toAdd.getTeamName();
        if (!model.getAddressBook().getTeamList().stream().anyMatch(t -> t.getTeamName().equals(teamName))) {
            if (!teamName.toString().equals(UNSPECIFIED_FIELD)) {
                throw new CommandException((Messages.MESSAGE_TEAM_NOT_FOUND));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\CommandTrie.java
``` java
/**
 * Trie of possible commands. Stores all possible commands for the addressbook.
 * Used for autocomplete functionality by returning a possible command via the attemptAutoComplete function.
 */
public class CommandTrie {

    private TrieNode root = null;
    private Set<String> commandSet;
    private Map<String, String> commandMap;

    public CommandTrie() {
        commandSet = Stream.of(
                EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
                AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,  UndoCommand.COMMAND_WORD,
                SortCommand.COMMAND_WORD, SetCommand.COMMAND_WORD, RemarkCommand.COMMAND_WORD,
                CreateCommand.COMMAND_WORD, AssignCommand.COMMAND_WORD, ViewCommand.COMMAND_WORD,
                RemoveCommand.COMMAND_WORD, KeyCommand.COMMAND_WORD, TogglePrivacyCommand.COMMAND_WORD
        ).collect(Collectors.toSet());

        for (String command : commandSet) {
            this.insert(command);
        }

        commandMap = new HashMap<>();
        commandMap.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_PARAMETERS);
        commandMap.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_PARAMETERS);
        commandMap.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_PARAMETERS);
        commandMap.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_PARAMETERS);
        commandMap.put(SelectCommand.COMMAND_WORD, SelectCommand.MESSAGE_PARAMETERS);
        commandMap.put(SortCommand.COMMAND_WORD, SortCommand.MESSAGE_PARAMETERS);
        commandMap.put(SetCommand.COMMAND_WORD, SetCommand.MESSAGE_PARAMETERS);
        commandMap.put(RemarkCommand.COMMAND_WORD, RemarkCommand.MESSAGE_PARAMETERS);
        commandMap.put(CreateCommand.COMMAND_WORD, CreateCommand.MESSAGE_PARAMETERS);
        commandMap.put(AssignCommand.COMMAND_WORD, AssignCommand.MESSAGE_PARAMETERS);
        commandMap.put(ViewCommand.COMMAND_WORD, ViewCommand.MESSAGE_PARAMETERS);
        commandMap.put(RemoveCommand.COMMAND_WORD, RemoveCommand.MESSAGE_PARAMETERS);
        commandMap.put(KeyCommand.COMMAND_WORD, KeyCommand.MESSAGE_PARAMETERS);
        commandMap.put(TogglePrivacyCommand.COMMAND_WORD, TogglePrivacyCommand.MESSAGE_PARAMETERS);
    }

    /**
     * Inserts input into Trie
     *
     * @param input command string
     */
    private void insert(String input) {
        char[] inputArray = input.toCharArray();

        if (root == null) {
            root = new TrieNode(inputArray[0], null, null);
            TrieNode temp = root;
            for (int i = 1; i < inputArray.length; i++) {
                temp.setChild(new TrieNode(inputArray[i], null, null));
                temp = temp.getChild();
            }
        } else {
            TrieNode temp = root;
            int i = 0;
            while (temp.hasSibling()) {
                if (temp.getKey() == inputArray[i]) {
                    temp = temp.getChild();
                    i++;
                } else {
                    temp = temp.getSibling();
                }
            }

            while (i < inputArray.length - 1) {
                if (temp.getKey() == inputArray[i] && temp.hasChild()) {
                    i++;
                    temp = temp.getChild();
                } else if (temp.getKey() == inputArray[i] && !temp.hasChild()) {
                    i++;
                    temp.setChild(new TrieNode(inputArray[i], null, null));
                    temp = temp.getChild();
                } else {
                    temp.setSibling(new TrieNode(inputArray[i], null, null));
                    temp = temp.getSibling();
                }
            }
        }
    }

    /**
     * Indicates whether a node is a endOfWord
     */
    private boolean isEndOfWord(TrieNode current) {
        return !current.hasSibling() && !current.hasChild();
    }

    /**
     * @param input string to autocomplete by returning command with matching prefix
     * @return input if the command is not found, matching command word otherwise
     */
    public String attemptAutoComplete(String input) throws NullPointerException {
        StringBuilder output = new StringBuilder();

        if (commandSet.contains(input)) {
            if (commandMap.containsKey(input)) {
                output.append(" ");
                output.append(commandMap.get(input));
            }
        } else {
            char[] inputArray = input.toCharArray();
            TrieNode temp = root;
            int i = 0;

            while (!isEndOfWord(temp)) {
                if (i < inputArray.length) {
                    if (Character.toLowerCase(temp.getKey()) == Character.toLowerCase(inputArray[i])) {
                        output.append(temp.getKey());
                        temp = temp.getChild();
                        i++;
                    } else {
                        temp = temp.getSibling();
                    }
                } else if (temp.hasSibling()) {
                    return input;
                } else {
                    output.append(temp.getKey());
                    temp = temp.getChild();
                }
            }
            output.append(temp.getKey());
        }
        return output.toString();
    }

    public Set<String> getCommandSet() {
        return commandSet;
    }

    /**
     * @return a list of all possible commands for the given input
     */
    public List<String> getOptions(String input) {
        char[] inputArray = input.toLowerCase().toCharArray();
        TrieNode start = root;
        int i = 0;
        StringBuilder stub = new StringBuilder();

        while (!isEndOfWord(start) && i < inputArray.length) {
            if (start.getKey() == inputArray[i]) {
                i++;
                stub.append(start.getKey());
                start = start.getChild();
            } else {
                start = start.getSibling();
            }
        }

        return findOptions(start, stub.toString());
    }

    /**
     * Traverses the trie and gets possible options
     */
    private List<String> findOptions(TrieNode start, String stub) {
        List<String> options = new ArrayList<>();
        StringBuilder output = new StringBuilder();
        output.append(stub);

        if (isEndOfWord(start)) {
            output.append(start.getKey());
            options.add(output.toString());
            return options;
        }
        if (start.hasSibling()) {
            options.addAll(findOptions(start.getSibling(), output.toString()));
        }
        if (start.hasChild()) {
            output.append(start.getKey());
            options.addAll(findOptions(start.getChild(), output.toString()));
        }
        return options;
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
/**
 * Edits the details of an existing player in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the player identified "
            + "by the index number used in the last player listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_POSITION + "POSITION] "
            + "[" + PREFIX_JERSEY_NUMBER + "JERSEY_NUMBER] "
            + "[" + PREFIX_AVATAR + "AVATAR] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_PARAMETERS = "INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_POSITION + "POSITION] "
            + "[" + PREFIX_JERSEY_NUMBER + "JERSEY_NUMBER] "
            + "[" + PREFIX_AVATAR + "AVATAR] "
            + "[" + PREFIX_TAG + "TAG]";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This player already exists in the address book.";
    public static final String MESSAGE_FILE_NOT_FOUND = "Avatar image file specified does not exist";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the player in the filtered player list to edit
     * @param editPersonDescriptor details to edit the player with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            if (!editedPerson.getAvatar().toString().equals(UNSPECIFIED_FIELD)) {
                editedPerson.getAvatar().setFilePath(editedPerson.getName().fullName);
            }
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target player cannot be missing");
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }
```
###### \java\seedu\address\logic\commands\TrieNode.java
``` java
/**
 * Represents a Trie Node object. Contains a character, a reference to sibling Trie Node and child Trie Node .
 */
public class TrieNode {
    private TrieNode sibling;
    private TrieNode child;

    private char key;

    TrieNode(char key, TrieNode sibling, TrieNode child) {
        this.key = key;
        this.sibling = sibling;
        this.child = child;
    }

    public char getKey() {
        return key;
    }

    public boolean hasSibling() {
        return sibling != null;
    }

    public TrieNode getSibling() {
        return sibling;
    }

    public void setSibling(TrieNode sibling) {
        this.sibling = sibling;
    }

    public boolean hasChild() {
        return child != null;
    }

    public TrieNode getChild() {
        return child;
    }

    public void setChild(TrieNode child) {
        this.child = child;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TEAM_NAME, PREFIX_TAG, PREFIX_JERSEY_NUMBER, PREFIX_POSITION, PREFIX_RATING,
                        PREFIX_AVATAR);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(ParserUtil.parseValue(argMultimap.getValue(PREFIX_PHONE),
                    Phone.MESSAGE_PHONE_CONSTRAINTS)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(ParserUtil.parseValue(argMultimap
                    .getValue(PREFIX_ADDRESS), Address.MESSAGE_ADDRESS_CONSTRAINTS)).get();
            Remark remark = new Remark("");
            TeamName teamName = ParserUtil.parseTeamName(ParserUtil.parseValue(argMultimap.getValue(PREFIX_TEAM_NAME),
                    TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Rating rating = ParserUtil.parseRating(ParserUtil.parseValue(argMultimap.getValue(PREFIX_RATING),
                    Rating.MESSAGE_RATING_CONSTRAINTS)).get();
            Position position = ParserUtil.parsePosition(ParserUtil.parseValue(argMultimap
                    .getValue(PREFIX_POSITION), Position.MESSAGE_POSITION_CONSTRAINTS)).get();
            JerseyNumber jerseyNumber = ParserUtil.parseJerseyNumber(ParserUtil.parseValue(argMultimap
                    .getValue(PREFIX_JERSEY_NUMBER), JerseyNumber.MESSAGE_JERSEY_NUMBER_CONSTRAINTS)).get();
            Avatar avatar = ParserUtil.parseAvatar(ParserUtil.parseValue(argMultimap
                    .getValue(PREFIX_AVATAR), Avatar.MESSAGE_AVATAR_CONSTRAINTS)).get();
            Person person = new Person(name, phone, email, address, remark, teamName, tagList, rating, position,
                    jerseyNumber, avatar);

            return new AddCommand(person);
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
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_RATING, PREFIX_POSITION, PREFIX_JERSEY_NUMBER,
                        PREFIX_AVATAR);

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
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
            ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING)).ifPresent(editPersonDescriptor::setRating);
            ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION))
                    .ifPresent(editPersonDescriptor::setPosition);
            ParserUtil.parseJerseyNumber(argMultimap.getValue(PREFIX_JERSEY_NUMBER))
                    .ifPresent(editPersonDescriptor::setJerseyNumber);
            ParserUtil.parseAvatar(argMultimap.getValue(PREFIX_AVATAR)).ifPresent(editPersonDescriptor::setAvatar);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

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
###### \java\seedu\address\model\person\Avatar.java
``` java
/**
 * Represents a Player's avatar in the address book. Contains filepath to avatar image file.
 * Guarantees: immutable; is valid as declared in {@link #isValidAvatar(String)}
 */
public class Avatar {

    public static final String MESSAGE_AVATAR_CONSTRAINTS =
            "Please specify the absolute filepath for the avatar image eg. av/C:\\image.png\n (for Windows), "
            + "av//User/username/path/to/image.jpg (for MacOS). "
            + "Image file should be 200x200 and in jpg or png format";

    public static final String AVATAR_VALIDATION_PATTERN = "([^\\s]+(\\.(?i)(jpg|png))$)";

    private String value;

    /**
     * Constructs an {@code Avatar}.
     *
     * @param avatar A valid avatar.
     */
    public Avatar(String avatar) {
        requireNonNull(avatar);
        checkArgument(isValidAvatar(avatar), MESSAGE_AVATAR_CONSTRAINTS);
        this.value = avatar;
    }

    /**
     * Returns true if a given string is a valid player's avatar.
     */
    public static boolean isValidAvatar(String test) {
        Pattern pattern = Pattern.compile(AVATAR_VALIDATION_PATTERN);
        Matcher matcher = pattern.matcher(test);
        return matcher.matches() || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        return value;
    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }
    */

    /**
     * Copies the image file from file path entered into images/ and renames it as [name].png
     * and saves the file path in value
     * @param player player's avatar image filepath string
     * @throws IOException  thrown when file not found
     */
    public void setFilePath(String player) throws IOException {
        if (value.equals("<UNSPECIFIED>")) {
            return;
        }
        final File file = new File(value);

        Path dest = new File("images/" + player.replaceAll("\\s+", "") + ".png").toPath();
        Files.createDirectories(Paths.get("images")); // Creates missing directories if any
        Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        this.value = dest.toString();
    }

    public String getValue() {
        return value;
    }

    public static void setUpPlaceholderForTest() {
        try {
            Files.copy(Avatar.class.getResourceAsStream("/images/placeholder_test.png"),
                    Paths.get("images/placeholder_test.png"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LogsCenter.getLogger(Avatar.class).warning("placeholder image file missing");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\JerseyNumber.java
``` java
/**
 * Represents a Player's jersey number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidJerseyNumber(String)}
 */
public class JerseyNumber {

    public static final String MESSAGE_JERSEY_NUMBER_CONSTRAINTS =
            "Player's jersey number should be an integer from 0 - 99.";


    public static final String RATING_VALIDATION_REGEX = "[0-9]|[1-8][0-9]|9[0-9]";

    public final String value;

    /**
     * Constructs an {@code JerseyNumber}.
     *
     * @param jerseyNumber A valid jersey number.
     */
    public JerseyNumber(String jerseyNumber) {
        requireNonNull(jerseyNumber);
        checkArgument(isValidJerseyNumber(jerseyNumber), MESSAGE_JERSEY_NUMBER_CONSTRAINTS);
        this.value = jerseyNumber;
    }

    /**
     * Returns true if a given string is a valid player's jersey number.
     */
    public static boolean isValidJerseyNumber(String test) {
        return test.matches(RATING_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JerseyNumber // instanceof handles nulls
                && this.value.equals(((JerseyNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Position.java
``` java
/**
 * Represents a Player's position in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class Position {

    public static final String MESSAGE_POSITION_CONSTRAINTS =
            "Player's position should be an integer from 1 - 4 where 1 - Striker, 2 - Midfield, 3 - Defender, "
                   + "and 4 - Goalkeeper.";

    public static final String RATING_VALIDATION_REGEX = "[1-4]";
    private static final Map<String, String> myMap;
    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("1", "Striker");
        aMap.put("2", "Midfielder");
        aMap.put("3", "Defender");
        aMap.put("4", "Goalkeeper");
        myMap = Collections.unmodifiableMap(aMap);
    }
    public final String value;
    /**
     * Constructs an {@code Position}.
     *
     * @param position A valid position.
     */
    public Position(String position) {
        requireNonNull(position);
        checkArgument(isValidPosition(position), MESSAGE_POSITION_CONSTRAINTS);
        this.value = position;
    }

    /**
     * Returns true if a given string is a valid player's position.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(RATING_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    /**
     * Returns position name according to value
     * @return position name
     */
    public String getPositionName() {
        return myMap.get(value);
    }

    @Override
    public String toString() {
        return getPositionName();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Position // instanceof handles nulls
                && this.value.equals(((Position) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Rating.java
``` java
/**
 * Represents a Player's rating in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {

    public static final String MESSAGE_RATING_CONSTRAINTS =
            "Player's rating should be an integer from 0 - 5.";

    public static final String RATING_VALIDATION_REGEX = "[0-5]";

    public final String value;
    private boolean isPrivate;

    /**
     * Constructs an {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        requireNonNull(rating);
        checkArgument(isValidRating(rating), MESSAGE_RATING_CONSTRAINTS);
        this.value = rating;
    }

    public Rating(String rating, boolean isPrivate) {
        this(rating);
        this.setPrivate(isPrivate);
    }

    /**
     * Returns true if a given string is a valid player's rating.
     */
    public static boolean isValidRating(String test) {
        return test.matches(RATING_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        if (isPrivate) {
            return "<Private Rating>";
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
                || (other instanceof Rating // instanceof handles nulls
                && this.value.equals(((Rating) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        commandTrie = logic.getCommandTrie();
        commandSet = commandTrie.getCommandSet();
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        suggestions = new ContextMenu();
        commandTextField.setContextMenu(suggestions);
    }

    /**
     *
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();

            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        case TAB:
            keyEvent.consume();
            handleAutoComplete();
            break;
        default:
            if (suggestions.isShowing()) {
                suggestions.hide();
            }
            // let JavaFx handle the keypress
        }
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Handles the Tab button pressed event. Attempts to autocomplete current input.
     */
    private void handleAutoComplete() {
        String input = commandTextField.getText();
        try {
            String command = commandTrie.attemptAutoComplete(input);
            if (input.equals(command)) {
                setStyleToIndicateCommandFailure();
                showSuggestions(commandTrie.getOptions(input));
            } else if (commandSet.contains(command)) {
                this.replaceText(command);
            } else if (commandSet.contains(input)) {
                setStyleToIndicateCommandFailure();
                this.replaceText(input + command);
            }
        } catch (NullPointerException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Populates ContextMenu and shows it to user
     * @param options the options with matching prefix found in Command Trie
     */
    private void showSuggestions(List<String> options) {
        suggestions.getItems().clear();
        for (String option : options) {
            MenuItem item = new MenuItem(option);
            item.setOnAction(event -> replaceText(item.getText()));
            suggestions.getItems().add(item);
        }
        suggestions.show(commandTextField, Side.BOTTOM, 0.0, 0.0);
    }
}
```
###### \resources\view\PersonListCard.fxml
``` fxml
    </VBox>
      <Circle fx:id="avatar" radius="80.0" stroke="WHITE" strokeType="INSIDE" visible="false" GridPane.columnIndex="1" GridPane.halignment="CENTER" GridPane.valignment="CENTER" />
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```

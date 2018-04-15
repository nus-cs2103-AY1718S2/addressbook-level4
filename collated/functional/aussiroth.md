# aussiroth
###### \java\seedu\address\logic\AttributeTagSetter.java
``` java

/**
 * This class contains static methods for use when performing attribute tag related tasks.
 */
public class AttributeTagSetter {

    /**
     * This function takes in a person and a set of tags.
     * If present, each of the attribute tags for that person will be removed from the set of tags.
     * The resulting set of tags is then returned.
     * @param person A person to remove the attribute tags from.
     * @param updatedTags The current set of tags of that person.
     * @return A copy of attributeTags representing the Tags for that person, with all attribute tags removed.
     */
    public static Set<Tag> removePresentAttributeTags(Person person, Set<Tag> updatedTags) {
        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(updatedTags);
        //ignore if attribute is empty (not entered yet by user)
        if (!person.getPrice().toString().equals("")) {
            attributeTags.remove(new Tag(person.getPrice().toString(), Tag.AllTagTypes.PRICE));
        }
        if (!person.getLevel().toString().equals("")) {
            attributeTags.remove(new Tag(person.getLevel().toString(), Tag.AllTagTypes.LEVEL));
        }
        if (!person.getSubject().toString().equals("")) {
            attributeTags.remove(new Tag(person.getSubject().toString(), Tag.AllTagTypes.SUBJECT));
        }
        if (!person.getStatus().toString().equals("")) {
            attributeTags.remove(new Tag(person.getStatus().toString(), Tag.AllTagTypes.STATUS));
        }
        if (!person.getRole().toString().equals("")) {
            attributeTags.remove(new Tag(person.getRole().toString(), Tag.AllTagTypes.ROLE));
        }
        return new HashSet<>(attributeTags);
    }

    /**
     *
     * @param attributeTags The set of tags to update
     * @param price The new price entered
     * @param subject The new subject entered
     * @param level The new level entered
     * @param status The new status entered
     * @param role The new role entered
     * @return A copy of attributeTags representing the Tags for that person, with all the 5 attribute tags entered.
     */
    public static Set<Tag> addNewAttributeTags(Set<Tag> attributeTags, Price price, Subject subject,
        Level level, Status status, Role role) {
        requireAllNonNull(price, subject, level, status, role);
        //ignore empty strings i.e. user did not enter that field
        if (!price.toString().equals("")) {
            attributeTags.add(new Tag(price.toString(), Tag.AllTagTypes.PRICE));
        }
        if (!subject.toString().equals("")) {
            attributeTags.add(new Tag(subject.toString(), Tag.AllTagTypes.SUBJECT));
        }
        if (!level.toString().equals("")) {
            attributeTags.add(new Tag(level.toString(), Tag.AllTagTypes.LEVEL));
        }
        if (!status.toString().equals("")) {
            attributeTags.add(new Tag(status.toString(), Tag.AllTagTypes.STATUS));
        }
        if (!role.toString().equals("")) {
            attributeTags.add(new Tag(role.toString(), Tag.AllTagTypes.ROLE));
        }
        return new HashSet<>(attributeTags);
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    private static Role getUpdatedRole(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getRole().orElse(personToEdit.getRole());
    }

    private static Status getUpdatedStatus(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getStatus().orElse(personToEdit.getStatus());
    }

    private static Level getUpdatedLevel(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getLevel().orElse(personToEdit.getLevel());
    }

    private static Subject getUpdatedSubject(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getSubject().orElse(personToEdit.getSubject());
    }

    private static Price getUpdatedPrice(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getPrice().orElse(personToEdit.getPrice());
    }

    private static Set<Tag> getAttributeTags(Person personToEdit, Price updatedPrice,
        Subject updatedSubject, Level updatedLevel, Status updatedStatus, Role updatedRole, Set<Tag> updatedTags) {
        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(updatedTags);
        //clean out old person's attribute tags, then add the new ones
        attributeTags = AttributeTagSetter.removePresentAttributeTags(personToEdit, attributeTags);
        attributeTags = AttributeTagSetter.addNewAttributeTags(attributeTags, updatedPrice, updatedSubject,
                updatedLevel, updatedStatus, updatedRole);
        return attributeTags;
    }

```
###### \java\seedu\address\logic\commands\FindMissingCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindMissingCommand extends Command {

    public static final String COMMAND_WORD = "findmissing";
    public static final String COMMAND_WORD_ALIAS = "fm";
    public static final String[] ATTRIBUTE_VALUES =
            new String[] {"phone", "email", "address", "price", "level", "role", "status", "subject"};
    public static final HashSet<String> SET_ATTRIBUTE_VALUES = new HashSet<>(Arrays.asList(ATTRIBUTE_VALUES));

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filter all persons whose fields have unentered values.\n"
            + "With parameters, only those with specified fields with unentered values will be shown."
            + "With no parameters, all persons with at least one field with unentered values will be shown"
            + "Parameters: [ATTRIBUTE_NAME]\n"
            + "Example: " + COMMAND_WORD + " email phone";

    public static final String MESSAGE_INVALID_ATTRIBUTE = "The attribute %s is invalid.\n"
            + "The valid attributes are: phone, email, address, price, level, role, status, subject.";

    private final Predicate<Person> predicate;

    public FindMissingCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMissingCommand // instanceof handles nulls
                && this.predicate.equals(((FindMissingCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
        //Change here from original code is that I create a class with empty string if user did not enter a value.
        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).orElse(new Phone(""));
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).orElse(new Email(""));
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).orElse(new Address(""));
            Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).orElse(new Price(""));
            Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).orElse(new Subject(""));
            Level level = ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL)).orElse(new Level(""));
            Status status = Status.DEFAULT_STATUS;
            Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE)).orElse(new Role(""));
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            //make sure name is never set to empty string as it is the only compulsory field.
            assert(!name.equals(""));
            //Add required attributes to the tag list as in documentation
            //make tags only if the attribute has been entered by user
            tagList = AttributeTagSetter.addNewAttributeTags(tagList, price, subject, level, status, role);

            Remark remark = new Remark("");  // default remark is empty string for newly added Person
            Rate rate = Rate.initializeRate(); // default rate has 0 people rating the person

            Person person = new Person(name, phone, email, address, price, subject, level,
                                       status, role, tagList, remark, rate, PairHash.getDefaultPairHashSet());
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
###### \java\seedu\address\logic\parser\FindMissingCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FindMissingCommandParser implements Parser<FindMissingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMissingCommand
     * and returns an FindMissingCommand object for execution.
     * @throws ParseException if the user input contains non-attribute values
     */
    public FindMissingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] fieldKeywords = trimmedArgs.split("\\s+");
        //validate user input, and set all input to lowercase.
        for (int i = 0; i < fieldKeywords.length; i++) {
            fieldKeywords[i] = fieldKeywords[i].toLowerCase();
            if (!(fieldKeywords[i].equals("") || FindMissingCommand.SET_ATTRIBUTE_VALUES.contains(fieldKeywords[i]))) {
                throw new ParseException(String.format(FindMissingCommand.MESSAGE_INVALID_ATTRIBUTE, fieldKeywords[i]));
            }
        }
        //If user enters no parameters, the command is equivalent to entering ALL parameters.
        if (fieldKeywords[0].equals("")) {
            fieldKeywords = Arrays.copyOf(FindMissingCommand.ATTRIBUTE_VALUES,
                    FindMissingCommand.ATTRIBUTE_VALUES.length);
        }
        Predicate<Person> finalPredicate = new FindMissingPredicate(Arrays.asList(fieldKeywords));
        return new FindMissingCommand(finalPredicate);
    }
}
```
###### \java\seedu\address\logic\predicates\FindMissingPredicate.java
``` java
/**
 * Tests that a {@code Person}'s specified {@code Attribute} as given in {@code keyword} is an empty string.
 */
public class FindMissingPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a new FindMissingPredicate based off given list.
     * @param keywords A non-empty ArrayList of keywords containing attributes.
     */
    public FindMissingPredicate(List<String> keywords) {
        assert(keywords.size() >= 1);
        //sort to ensure equality check passes
        Collections.sort(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        for (String keyword : keywords) {
            if (keyword.equals("email") && person.getEmail().value.equals("")) {
                return true;
            } else if (keyword.equals("phone") && person.getPhone().value.equals("")) {
                return true;
            } else if (keyword.equals("address") && person.getAddress().value.equals("")) {
                return true;
            } else if (keyword.equals("price") && person.getPrice().value.equals("")) {
                return true;
            } else if (keyword.equals("level") && person.getLevel().value.equals("")) {
                return true;
            } else if (keyword.equals("subject") && person.getSubject().value.equals("")) {
                return true;
            } else if (keyword.equals("role") && person.getRole().value.equals("")) {
                return true;
            } else if (keyword.equals("status") && person.getStatus().value.equals("")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMissingPredicate // instanceof handles nulls
                && this.keywords.equals(((FindMissingPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\tag\Tag.java
``` java
    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     * @param tagType A valid tag type.
     */
    public Tag(String tagName, AllTagTypes tagType) {
        this.tagType = tagType;
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_TAG_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    /**
     * returns true if given string is a valid tag type.
     * @param test A string to test.
     */
    public static boolean isValidTagType(String test) {
        for (AllTagTypes tType : AllTagTypes.values()) {
            if (tType.toString().equals(test)) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(Tag other) {
        return this.tagType.compareTo(other.tagType);
    }

```
###### \java\seedu\address\storage\XmlAdaptedTag.java
``` java
    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName} and {@code tagType}.
     */
    public XmlAdaptedTag(String tagName, String tagType) {
        this.tagName = tagName + "," + tagType;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Tag toModelType() throws IllegalValueException {
        String[] checkTagNameType = tagName.split(",");
        if (!Tag.isValidTagName(checkTagNameType[0])) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        //additional check if the comma exists
        if (checkTagNameType.length == 1) {
            logger.warning("Could not find tag type in file. Initialising it as DEFAULT type.");
            return new Tag(checkTagNameType[0]);
        }
        if (!Tag.isValidTagType(checkTagNameType[1])) {
            logger.warning("Tag Type in file is not recognised. Initialising it as DEFAULT type.");
            checkTagNameType[1] = Tag.AllTagTypes.DEFAULT.toString();
        }
        return new Tag(checkTagNameType[0], Tag.AllTagTypes.valueOf(checkTagNameType[1]));
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Returns the color style for {@code tag}.
     * Uses the tagType value inside the Tag to determine the colour.
     */
    private String getTagColorStyleFor(Tag tag) {
        switch (tag.tagType) {
        case SUBJECT:
            return TAG_COLOR_STYLES[0]; //subject is teal
        case LEVEL:
            return TAG_COLOR_STYLES[1]; //level is red
        case STATUS:
            return TAG_COLOR_STYLES[2]; //status is yellow
        case PRICE:
            return TAG_COLOR_STYLES[3]; //price is blue
        //fall through to default
        default:
            return TAG_COLOR_STYLES[8]; //all non-attribute are black
        }
    }

```

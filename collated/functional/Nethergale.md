# Nethergale
###### \java\seedu\address\logic\commands\AddPlatformCommand.java
``` java
/**
 * Adds social media platforms to the specified person in the address book.
 */
public class AddPlatformCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addplatform";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds social media platforms to the person "
            + "identified by the index number used in the last person listing through website links.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LINK + "LINK]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINK + "www.facebook.com/johndoe";

    public static final String MESSAGE_ADD_PLATFORM_SUCCESS = "Platform(s) successfully added to %1$s.";
    public static final String MESSAGE_ADD_PLATFORM_CLEAR_SUCCESS = "Platform(s) successfully cleared for %1$s.";
    public static final String MESSAGE_LINK_COLLECTION_EMPTY = "At least 1 link field should be specified.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Map<String, Link> linkMap;
    private final Map<String, SocialMediaPlatform> socialMediaPlatformMap;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param linkMap as defined by the social media platform type as key and link as value
     */
    public AddPlatformCommand(Index index, Map<String, Link> linkMap) {
        requireNonNull(index);
        requireNonNull(linkMap);

        this.index = index;
        this.linkMap = linkMap;
        socialMediaPlatformMap = new HashMap<>();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToEdit);
        requireNonNull(editedPerson);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (socialMediaPlatformMap.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_ADD_PLATFORM_CLEAR_SUCCESS, editedPerson.getName()));
        }
        return new CommandResult(String.format(MESSAGE_ADD_PLATFORM_SUCCESS, editedPerson.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            addToSocialMediaPlatformMap();
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        addCurrentSocialMediaPlatforms();
        editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), socialMediaPlatformMap, personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPlatformCommand)) {
            return false;
        }

        // state check
        AddPlatformCommand e = (AddPlatformCommand) other;
        return index.equals(e.index)
                && linkMap.equals(e.linkMap);
    }

    /**
     * Constructs social media platform objects depending on the link type and adds them to the map.
     */
    private void addToSocialMediaPlatformMap() throws IllegalValueException {
        for (String type : linkMap.keySet()) {
            socialMediaPlatformMap.put(type, SocialMediaPlatformBuilder.build(type, linkMap.get(type)));
        }
    }

    /**
     * Adds back the social media platforms not found in the edited person into the map only if it is not empty.
     */
    private void addCurrentSocialMediaPlatforms() {
        if (!socialMediaPlatformMap.isEmpty()) {
            for (String key : personToEdit.getSocialMediaPlatformMap().keySet()) {
                socialMediaPlatformMap.putIfAbsent(key, personToEdit.getSocialMediaPlatformMap().get(key));
            }
        }
    }
}
```
###### \java\seedu\address\logic\commands\RemovePlatformCommand.java
``` java
/**
 * Removes the specified social media platforms of a person identified
 * using it's last displayed index from the address book.
 */
public class RemovePlatformCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeplatform";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes specified social media platforms "
            + "of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SOCIAL_MEDIA_PLATFORM + "PLATFORM]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_SOCIAL_MEDIA_PLATFORM + "facebook";

    public static final String MESSAGE_REMOVE_PLATFORM_SUCCESS = "Platform(s) removed from %1$s.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_PLATFORM_MAP_NOT_EDITED = "No changes can be made from the given platform info.";

    private final Index targetIndex;
    private final Set<String> platformsToRemove;
    private final Map<String, SocialMediaPlatform> socialMediaPlatformMap;

    private Person personToEdit;
    private Person editedPerson;

    public RemovePlatformCommand(Index targetIndex, Set<String> platformsToRemove) {
        this.targetIndex = targetIndex;
        this.platformsToRemove = platformsToRemove;
        socialMediaPlatformMap = new HashMap<>();
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());

        try {
            removeFromSocialMediaPlatformMap();
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), socialMediaPlatformMap, personToEdit.getTags());
    }

    /**
     * Removes the social media platform mappings from the map if specified.
     *
     * @throws IllegalValueException if no changes are made
     */
    private void removeFromSocialMediaPlatformMap() throws IllegalValueException {
        if (!platformsToRemove.isEmpty()) {
            socialMediaPlatformMap.putAll(personToEdit.getSocialMediaPlatformMap());

            for (String platform : platformsToRemove) {
                socialMediaPlatformMap.remove(platform.toLowerCase());
            }

            if (socialMediaPlatformMap.equals(personToEdit.getSocialMediaPlatformMap())) {
                throw new IllegalValueException(MESSAGE_PLATFORM_MAP_NOT_EDITED);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemovePlatformCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemovePlatformCommand) other).targetIndex) // state check
                && this.platformsToRemove.equals(((RemovePlatformCommand) other).platformsToRemove));
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Displays the current list of persons in the address book to the user sorted alphabetically.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the current list of persons in the "
            + "address book sorted in alphabetical order with index numbers.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book sorted successfully!";
    public static final String MESSAGE_FAILURE = "Address book has already been sorted.";

    private List<Person> personList;

    /**
     * Returns true if person list is sorted.
     */
    private boolean isListSorted() {
        return Ordering.from(Person.nameComparator()).isOrdered(personList);
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.sortAllPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        personList = model.getAddressBook().getPersonList();
        if (personList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_ADDRESS_BOOK_EMPTY);
        }
        if (isListSorted()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddPlatformCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddPlatformCommand object
 */
public class AddPlatformCommandParser implements Parser<AddPlatformCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPlatformCommand
     * and returns an AddPlatformCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddPlatformCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LINK);

        Index index;
        Map<String, Link> linkMap;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPlatformCommand.MESSAGE_USAGE));
        }

        try {
            linkMap = parseLinksForAddPlatform(argMultimap.getAllValues(PREFIX_LINK));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddPlatformCommand(index, linkMap);
    }

    /**
     * Parses {@code Collection<String> links} into a {@code Map<String, Link>} if {@code links} is non-empty.
     * If {@code links} contain only one element which is an empty string, it will be parsed into a
     * {@code Map<String, Link>} containing zero links.
     *
     * @throws IllegalValueException if user does not specify even a single link prefix.
     */
    private Map<String, Link> parseLinksForAddPlatform(Collection<String> links) throws IllegalValueException {
        assert links != null;

        if (links.isEmpty()) {
            throw new IllegalValueException(AddPlatformCommand.MESSAGE_LINK_COLLECTION_EMPTY);
        }
        Collection<String> linkSet = links.size() == 1 && links.contains("") ? Collections.emptySet() : links;
        return ParserUtil.parseLinks(linkSet);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String link} into a {@code Link}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code link} is invalid.
     */
    public static Link parseLink(String link) throws IllegalValueException {
        requireNonNull(link);
        String trimmedLink = link.trim();
        if (!Link.isValidLink(trimmedLink)) {
            throw new IllegalValueException(Link.MESSAGE_INVALID_LINK);
        }
        return new Link(trimmedLink);
    }

    /**
     * Parses {@code Collection<String> links} into a {@code Map<String, Link>}.
     *
     * @throws IllegalValueException if any social media platform is going to have more than one link each.
     */
    public static Map<String, Link> parseLinks(Collection<String> links) throws IllegalValueException {
        requireNonNull(links);
        final Map<String, Link> linkMap = new HashMap<>();
        for (String linkStr : links) {
            String linkType = Link.getLinkType(linkStr);
            Link link = parseLink(linkStr);
            if ((linkType.equals(Link.FACEBOOK_LINK_TYPE) || linkType.equals(Link.TWITTER_LINK_TYPE))
                    && linkMap.containsKey(linkType)) {
                throw new IllegalValueException(Link.MESSAGE_LINK_CONSTRAINTS);
            }
            linkMap.put(linkType, link);
        }
        return linkMap;
    }

```
###### \java\seedu\address\logic\parser\RemovePlatformCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemovePlatformCommand object
 */
public class RemovePlatformCommandParser implements Parser<RemovePlatformCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemovePlatformCommand
     * and returns a RemovePlatformCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemovePlatformCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SOCIAL_MEDIA_PLATFORM);

        Index index;
        Set<String> platformSet = new HashSet<>();

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            platformSet.addAll(argMultimap.getAllValues(PREFIX_SOCIAL_MEDIA_PLATFORM));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePlatformCommand.MESSAGE_USAGE));
        }

        return new RemovePlatformCommand(index, platformSet);
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts all persons by name in alphabetical order in the address book.
     */
    public void sort() {
        persons.sort();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortAllPersons() {
        addressBook.sort();
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns an immutable social media platform map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<String, SocialMediaPlatform> getSocialMediaPlatformMap() {
        return Collections.unmodifiableMap(smpMap);
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns a person comparator, which compares the names alphabetically.
     * Similar names are compared lexicographically.
     */
    public static Comparator<Person> nameComparator() {
        return Comparator.comparing((Person p) -> p.getName().toString(), (
            s1, s2) -> (s1.compareToIgnoreCase(s2) == 0) ? s1.compareTo(s2) : s1.compareToIgnoreCase(s2));
    }

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts all persons in list alphabetically. Similar names are sorted lexicographically.
     */
    public void sort() {
        internalList.sort(Person.nameComparator());
    }

```
###### \java\seedu\address\model\smplatform\Facebook.java
``` java
/**
 * Represents a facebook object.
 */
public class Facebook extends SocialMediaPlatform {
    //Code adapted from https://stackoverflow.com/questions/5205652/facebook-profile-url-regular-expression
    public static final String LINK_VALIDATION_REGEX = "(?:https?:\\/\\/)?(?:www\\.|m\\.)?facebook\\.com\\/"
            + "(?:profile.php\\?id=(?=\\d.*))?.(?:(?:\\w)*#!\\/)?(?:pages\\/)?(?:[\\w\\-]*\\/)*([\\w\\-\\.]*)/?";

    public Facebook(Link link) {
        this.link = link;
    }
}
```
###### \java\seedu\address\model\smplatform\Link.java
``` java
/**
 * Represents a SocialMediaPlatform's link.
 * Guarantees: immutable; is always valid
 */
public class Link {
    public static final String MESSAGE_LINK_CONSTRAINTS = "Only one link is allowed for each social media platform.";
    public static final String MESSAGE_INVALID_LINK = "Links should be valid Facebook or Twitter profile links.";
    public static final String FACEBOOK_LINK_TYPE = "facebook";
    public static final String TWITTER_LINK_TYPE = "twitter";
    public static final String UNKNOWN_LINK_TYPE = "unknown";

    private static final String FACEBOOK_LINK_SIGNATURE = "facebook.com";
    private static final String TWITTER_LINK_SIGNATURE = "twitter.com";
    private static final String LONGEST_VALID_LINK_PREFIX = "https://www.";

    public final String value;

    public Link(String link) {
        requireNonNull(link);
        this.value = link;
    }

    /**
     * Returns the social media platform type of the link.
     */
    public static String getLinkType(String link) {
        if (link.contains(FACEBOOK_LINK_SIGNATURE)
                && !(link.indexOf(FACEBOOK_LINK_SIGNATURE) > LONGEST_VALID_LINK_PREFIX.length())) {
            return FACEBOOK_LINK_TYPE;
        } else if (link.contains(TWITTER_LINK_SIGNATURE)
                && !(link.indexOf(TWITTER_LINK_SIGNATURE) > LONGEST_VALID_LINK_PREFIX.length())) {
            return TWITTER_LINK_TYPE;
        } else {
            return UNKNOWN_LINK_TYPE;
        }
    }

    /**
     * Returns true if a given string is a valid link.
     */
    public static boolean isValidLink(String test) {
        if (test.contains(FACEBOOK_LINK_SIGNATURE)
                && !(test.indexOf(FACEBOOK_LINK_SIGNATURE) > LONGEST_VALID_LINK_PREFIX.length())) {
            return test.matches(Facebook.LINK_VALIDATION_REGEX);
        } else if (test.contains(TWITTER_LINK_SIGNATURE)
                && !(test.indexOf(TWITTER_LINK_SIGNATURE) > LONGEST_VALID_LINK_PREFIX.length())) {
            return test.matches(Twitter.LINK_VALIDATION_REGEX);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && this.value.equals(((Link) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\smplatform\SocialMediaPlatform.java
``` java
/**
 * Represents a social media platform, which can take many forms.
 */
public abstract class SocialMediaPlatform {
    protected Link link;

    public Link getLink() {
        return link;
    }
}
```
###### \java\seedu\address\model\smplatform\SocialMediaPlatformBuilder.java
``` java
/**
 * Acts as a social media platform creator.
 * Determines the different types of social media platform objects to be created by using the link and its type.
 */
public final class SocialMediaPlatformBuilder {
    public static final String MESSAGE_BUILD_ERROR = "Social media platform cannot be constructed. "
            + "Link type is unrecognised or mismatched with link.";

    /**
     * Don't let anyone instantiate this class.
     */
    private SocialMediaPlatformBuilder() {}

    /**
     * Constructs the specific social media platform object by using the {@code type} and setting the {@code link}
     * as a parameter.
     *
     * @return the created social media platform object
     * @throws IllegalValueException if type is not recognised
     */
    public static SocialMediaPlatform build(String type, Link link) throws IllegalValueException {
        if (type.equals(Link.FACEBOOK_LINK_TYPE) && type.equals(Link.getLinkType(link.value))) {
            return new Facebook(link);
        } else if (type.equals(Link.TWITTER_LINK_TYPE) && type.equals(Link.getLinkType(link.value))) {
            return new Twitter(link);
        } else {
            throw new IllegalValueException(MESSAGE_BUILD_ERROR);
        }
    }
}
```
###### \java\seedu\address\model\smplatform\Twitter.java
``` java
    public Twitter(Link link) {
        this.link = link;
    }
}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    /**
     * Returns a social media platform map mapping to the various platforms,
     * using the list of strings given as the links. Unknown link types and invalid links will be ignored.
     * Only the first link will be added if there are multiple links of the same type.
     */
    public static Map<String, SocialMediaPlatform> getSocialMediaPlatformMap(String... strings) {
        HashMap<String, SocialMediaPlatform> smpMap = new HashMap<>();
        for (String s : strings) {
            String type = Link.getLinkType(s);
            if (type.equals(Link.FACEBOOK_LINK_TYPE) && Link.isValidLink(s)) {
                smpMap.putIfAbsent(type, new Facebook(new Link(s)));
            } else if (type.equals(Link.TWITTER_LINK_TYPE) && Link.isValidLink(s)) {
                smpMap.putIfAbsent(type, new Twitter(new Link(s)));
            }
        }

        return smpMap;
    }

```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        platforms = new ArrayList<>();
        for (String key : source.getSocialMediaPlatformMap().keySet()) {
            platforms.add(
                    new XmlAdaptedSocialMediaPlatform(key, source.getSocialMediaPlatformMap().get(key).getLink()));
        }

```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final Map<String, SocialMediaPlatform> personSocialMediaPlatforms = new HashMap<>();
        for (XmlAdaptedSocialMediaPlatform platform : platforms) {
            SocialMediaPlatform platformModel = platform.toModelType();
            personSocialMediaPlatforms.put(Link.getLinkType(platformModel.getLink().value), platformModel);
        }

```
###### \java\seedu\address\storage\XmlAdaptedSocialMediaPlatform.java
``` java
/**
 * JAXB-friendly adapted version of the SocialMediaPlatform.
 */
public class XmlAdaptedSocialMediaPlatform {

    @XmlElement
    private String type;
    @XmlElement
    private String link;

    /**
     * Constructs an XmlAdaptedSocialMediaPlatform.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSocialMediaPlatform() {}

    /**
     * Constructs a {@code XmlAdaptedSocialMediaPlatform} with the given {@code type} and {@code link}.
     */
    public XmlAdaptedSocialMediaPlatform(String type, String link) {
        this.type = type;
        this.link = link;
    }

    /**
     * Converts a given String and Link into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedSocialMediaPlatform
     */
    public XmlAdaptedSocialMediaPlatform(String type, Link source) {
        this.type = type;
        link = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted social media platform object into the model's social media platform object.
     *
     * @throws IllegalValueException if link is not valid
     */
    public SocialMediaPlatform toModelType() throws IllegalValueException {
        if (!Link.isValidLink(link)) {
            throw new IllegalValueException(Link.MESSAGE_INVALID_LINK);
        }

        return SocialMediaPlatformBuilder.build(type, new Link(link));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSocialMediaPlatform)) {
            return false;
        }

        return type.equals(((XmlAdaptedSocialMediaPlatform) other).type)
                && link.equals(((XmlAdaptedSocialMediaPlatform) other).link);
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Adds the various social media icon tab to the social media icon pane.
     */
    private void addIconsToSocialMediaIconPane() {
        for (String key : person.getSocialMediaPlatformMap().keySet()) {
            if (!person.getSocialMediaPlatformMap().get(key).getLink().value.isEmpty()) {
                socialMediaIconPane.getChildren().add(createSocialMediaIconTab(key));
            }
        }
    }

    /**
     * Creates the icon tab for the specific social media platform.
     *
     * @param type social media platform type
     * @return stack pane representing an icon tab
     */
    private StackPane createSocialMediaIconTab(String type) {
        StackPane socialMediaIconTab = new StackPane();
        Region tabBackground = new Region();
        tabBackground.setPrefSize(30, 32);
        tabBackground.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #29323C, #485563); "
                + "-fx-background-radius: 6 0 0 6; -fx-border-color: #242C35; "
                + "-fx-border-radius: 6 0 0 6; -fx-border-width: 2 0 2 2;");
        ImageView tabIcon = new ImageView();
        String url = imageUrl(type);
        if (!url.isEmpty()) {
            tabIcon.setFitWidth(20);
            tabIcon.setFitHeight(20);
            tabIcon.setImage(new Image(url));
            socialMediaIconTab.getChildren().addAll(tabBackground, tabIcon);
        }
        return socialMediaIconTab;
    }

    /**
     * Returns the location of the icon for the specific social media platform {@code type}.
     */
    private String imageUrl(String type) {
        if (type.equals(Link.FACEBOOK_LINK_TYPE)) {
            return "images/facebook_icon.png";
        } else if (type.equals(Link.TWITTER_LINK_TYPE)) {
            return "images/twitter_icon.png";
        } else {
            return "";
        }
    }
}
```
###### \resources\view\PersonListCard.fxml
``` fxml
    <VBox fx:id="socialMediaIconPane" alignment="TOP_CENTER" minHeight="105" style="-fx-background-color: #383838;" GridPane.columnIndex="0">
    </VBox>
    <VBox alignment="CENTER_LEFT" minHeight="105" style="-fx-border-color: #242C35; -fx-border-width: 2;" GridPane.columnIndex="1">
```

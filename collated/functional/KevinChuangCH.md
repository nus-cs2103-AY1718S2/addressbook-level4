# KevinChuangCH
###### \java\seedu\address\commons\events\ui\SearchPersonEvent.java
``` java
/**
 * Represents a search for person.
 */
public class SearchPersonEvent extends BaseEvent {

    private final String searchName;
    private final String platformToSearch;

    public SearchPersonEvent(String platformToSearch, String searchName) {
        this.searchName = searchName;
        this.platformToSearch = platformToSearch;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getSearchName() {
        return searchName;
    }

    public String getPlatform() {
        return platformToSearch;
    }

}
```
###### \java\seedu\address\logic\commands\FindWithTagCommand.java
``` java
/**
 * Finds and lists all persons in address book whose tags contain any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindWithTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends owesMoney";

    private final TagContainsKeywordsPredicate predicate;

    public FindWithTagCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindWithTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindWithTagCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SearchCommand.java
``` java
/**
 * Search for a person with the input name either on all available social media platforms
 * or the stated social media platform if it is available..
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search for a person with the input name on the input social media platform if it is available.\n"
            + "If no platform is stated, then the search will be performed on all available social media platforms.\n"
            + "User can use alias to indicate the social media platform.\n"
            + "Parameters: [PLATFORM], NAME (PLATFORM is case sensitive and NAME should not be blank)\n"
            + "Example: " + COMMAND_WORD + " " + Facebook.PLATFORM_KEYWORD + ", Tom\n"
            + "or\n"
            + "Example: " + COMMAND_WORD + " " + Twitter.PLATFORM_ALIAS + ", Sam\n"
            + "or\n"
            + "Example: " + COMMAND_WORD + " Jason\n";

    public static final String MESSAGE_SEARCH_PERSON_SUCCESS = "Searched Person: %1$s";

    private final String targetPlatform;
    private final String targetName;

    public SearchCommand(String targetPlatform, String targetName) {
        this.targetName = targetName;
        this.targetPlatform = targetPlatform;
    }

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new SearchPersonEvent(targetPlatform, targetName));
        return new CommandResult(String.format(MESSAGE_SEARCH_PERSON_SUCCESS, targetName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.targetName.equals(((SearchCommand) other).targetName)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\FindWithTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindWithTagCommand object
 */
public class FindWithTagCommandParser implements Parser<FindWithTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindWithTagCommand
     * and returns an FindWithTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindWithTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindWithTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FindWithTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Checks for the validation of {@code inputPlatform} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified name is invalid (not following the name regex).
     */
    public static String parsePlatformToSearch(String inputPlatform) throws IllegalValueException {
        requireNonNull(inputPlatform);
        String trimmedInputPlatform = inputPlatform.trim();
        if (!SocialMediaPlatform.isValidPlatform(trimmedInputPlatform)) {
            throw new IllegalValueException(SocialMediaPlatform.MESSAGE_PLATFORM_CONSTRAINTS);
        }
        return trimmedInputPlatform;
    }

    /**
     * Checks for the validation of {@code inputName} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified name is invalid (not following the name regex).
     */
    public static String parseSearchName(String inputName) throws IllegalValueException {
        requireNonNull(inputName);
        String trimmedInputName = inputName.trim();
        if (!trimmedInputName.matches(SocialMediaPlatform.USERNAME_VALIDATION_REGEX)) {
            throw new IllegalValueException(SocialMediaPlatform.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return trimmedInputName;
    }
```
###### \java\seedu\address\logic\parser\SearchCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        String[] splitArgs = args.split(", ");
        if (splitArgs.length == 1) {
            String platform = "all";
            try {
                String inputName = ParserUtil.parseSearchName(splitArgs[0]);
                return new SearchCommand(platform, inputName);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
            }
        } else if (splitArgs.length == 2) {
            try {
                String platform = ParserUtil.parsePlatformToSearch(splitArgs[0]);
                String inputName = ParserUtil.parseSearchName(splitArgs[1]);
                return new SearchCommand(platform, inputName);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns the tag names of tags of a person as String.
     * @return a string of all the tag names of tags of a person.
     */
    public String getTagsAsString() {
        final StringBuilder builder = new StringBuilder();
        for (String tag : tags.arrayOfTags()) {
            builder.append(tag);
            builder.append(" ");
        }
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTagsAsString().trim(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\smplatform\SocialMediaPlatform.java
``` java
/**
 * Represents a social media platform, which can take many forms.
 */
public abstract class SocialMediaPlatform {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Social media  usernames should only contain alphanumeric characters, "
                    + "underscore,  and spaces, and it should not be blank";

    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}_ ]*";

    public static final String MESSAGE_PLATFORM_CONSTRAINTS =
            "Platforms available are : \n"
                    + "1) " + Facebook.PLATFORM_KEYWORD + " (alias: " + Facebook.PLATFORM_ALIAS + ")\n"
                    + "2) " + Twitter.PLATFORM_KEYWORD + " (alias: " + Twitter.PLATFORM_ALIAS + ")\n";

```
###### \java\seedu\address\model\smplatform\SocialMediaPlatform.java
``` java
    /**
     * Returns true if a given string is a valid social platform name.
     */
    public static boolean isValidPlatform(String test) {
        if (test.equals(Facebook.PLATFORM_KEYWORD)
                || test.equals(Facebook.PLATFORM_ALIAS)
                || test.equals(Twitter.PLATFORM_KEYWORD)
                || test.equals(Twitter.PLATFORM_ALIAS)) {
            return true;
        }
        return false;
    }
}
```
###### \java\seedu\address\model\tag\UniqueTagList.java
``` java
    /**
     * Returns an array of tag names of tags of a person.
     * @return array of tag names
     */
    public ArrayList<String> arrayOfTags() {
        assert CollectionUtil.elementsAreUnique(internalList);
        ArrayList<String> tagStringArray = new ArrayList<>();
        for (Tag t : internalList) {
            tagStringArray.add(t.tagName);
        }
        return tagStringArray;
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadBrowserSearchPage(String searchName) {
        loadFacebookBrowserPage(FACEBOOK_SEARCH_PAGE_URL + searchName);
    }
    private void loadBrowser1SearchPage(String searchName) {
        loadTwitterBrowserPage(TWITTER_SEARCH_PAGE_URL + searchName);
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleSearchPersonEvent(SearchPersonEvent event) {

        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        String platformToSearch = event.getPlatform();

        if (StringUtil.containsWordIgnoreCase(platformToSearch, Facebook.PLATFORM_KEYWORD)
                || StringUtil.containsWordIgnoreCase(platformToSearch, Facebook.PLATFORM_ALIAS)) {
            updateBrowserTabs(FUNCTION_ADD, FACEBOOK_TAB_ID);
            updateBrowserTabs(FUNCTION_REMOVE, TWITTER_TAB_ID);
            loadBrowserSearchPage(event.getSearchName());
            loadTwitterBrowserPage(defaultPage.toExternalForm());
        } else if (StringUtil.containsWordIgnoreCase(platformToSearch, Twitter.PLATFORM_KEYWORD)
                || StringUtil.containsWordIgnoreCase(platformToSearch, Twitter.PLATFORM_ALIAS)) {
            updateBrowserTabs(FUNCTION_ADD, TWITTER_TAB_ID);
            updateBrowserTabs(FUNCTION_REMOVE, FACEBOOK_TAB_ID);
            loadFacebookBrowserPage(defaultPage.toExternalForm());
            loadBrowser1SearchPage(event.getSearchName());
        } else {
            updateBrowserTabs(FUNCTION_ADD, FACEBOOK_TAB_ID);
            updateBrowserTabs(FUNCTION_ADD, TWITTER_TAB_ID);
            loadBrowserSearchPage(event.getSearchName());
            loadBrowser1SearchPage(event.getSearchName());
        }
    }
}
```

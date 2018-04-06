# KevinChuangCH
###### \java\seedu\address\commons\events\ui\SearchPersonOnAllPlatformEvent.java
``` java
/**
 * Represents a search for person on all available social media platforms.
 */
public class SearchPersonOnAllPlatformEvent extends BaseEvent {

    private final String searchName;

    public SearchPersonOnAllPlatformEvent(String searchName) {
        this.searchName = searchName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getSearchName() {
        return searchName;
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
###### \java\seedu\address\logic\commands\SearchAllCommand.java
``` java
/**
 * Search for a person with the input name on all available social media platform.
 */
public class SearchAllCommand extends Command {

    public static final String COMMAND_WORD = "searchall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search for a person with the input name on all available social media platform.\n"
            + "Parameters: NAME (must only contain alphanumeric characters and spaces, and it should not be blank)\n"
            + "Example: " + COMMAND_WORD + " Tom";

    public static final String MESSAGE_SEARCH_PERSON_SUCCESS = "Searched Person: %1$s";

    private final String targetName;

    public SearchAllCommand(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new SearchPersonOnAllPlatformEvent(targetName));
        return new CommandResult(String.format(MESSAGE_SEARCH_PERSON_SUCCESS, targetName));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetName.equals(((SearchAllCommand) other).targetName)); // state check
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
###### \java\seedu\address\logic\parser\SearchAllCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SearchAllCommand object
 */
public class SearchAllCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchAllCommand
     * and returns an SearchAllCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchAllCommand parse(String args) throws ParseException {
        try {
            String inputName = ParserUtil.parseSearchName(args);
            return new SearchAllCommand(inputName);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchAllCommand.MESSAGE_USAGE));
        }
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

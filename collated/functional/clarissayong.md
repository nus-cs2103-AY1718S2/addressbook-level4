# clarissayong
###### /java/seedu/address/logic/parser/FilterCommandParser.java
``` java

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(new TagPredicate(trimmedArgs));
    }

}
```
###### /java/seedu/address/logic/commands/FilterCommand.java
``` java

/**
 * Finds and lists all persons in address book who is tagged with the provided keyword.
 * Keyword matching is case sensitive.
 */

public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all users with the specified tag "
            + "(case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " diabetes";

    private final TagPredicate predicate;

    public FilterCommand(TagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

}
```
###### /java/seedu/address/model/tag/TagPredicate.java
``` java
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagPredicate implements Predicate<Person> {
    private final String tag;

    public TagPredicate(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(keyword -> keyword.tagName.equalsIgnoreCase(tag));
    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }
    */
}
```
###### /java/seedu/address/model/tag/exceptions/TagNotFoundException.java
``` java

/**
 * Signals that the operation is unable to find the specified tag.
 */
public class TagNotFoundException extends Exception {
    public TagNotFoundException() {
        super("There are no contacts with this tag.");
    }
}
```

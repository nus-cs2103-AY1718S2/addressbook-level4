# Articho28
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    public void loadAtmSearchPage() {
        loadPage(ATM_SEARCH_PAGE_URL);
    }

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }
    private void loadPersonAddress(Person person) {
        loadPage (ADDRESS_SEARCH_PAGE_URL + person.getAddress().value);
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handleShowMapRequestEvent(ShowMapRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadAtmSearchPage();
    }

}
```
###### /java/seedu/address/commons/events/ui/ShowMapRequestEvent.java
``` java
/**
 * An event requesting to view a map showing the nearest ATM.
 */
public class ShowMapRequestEvent extends BaseEvent {

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/parser/SearchTagCommandParser.java
``` java

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.parseTags;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SearchTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses a SearchTagCommand. Verifies that tags are properly formatted before
 */
public class SearchTagCommandParser implements Parser<SearchTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ColorCommand
     * and returns an RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public SearchTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        Set<Tag> tagsToFind;
        try {
            tagsToFind = parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        return new SearchTagCommand(tagsToFind);
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case BalanceCommand.COMMAND_SHORTCUT:
            return new BalanceCommand();

        case BalanceCommand.COMMAND_WORD:
            return new BalanceCommand();
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case MinCommand.COMMAND_WORD:
            return new MinCommand();

        case MinCommand.COMMAND_SHORTCUT:
            return new MinCommand();
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case MapCommand.COMMAND_WORD:
            return new MapCommand();
        case MapCommand.COMMAND_SHORTCUT:
            return new MapCommand();
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case SearchTagCommand.COMMAND_WORD:
            return new SearchTagCommandParser().parse(arguments);
        case SearchTagCommand.COMMAND_SHORTCUT:
            return new SearchTagCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/commands/MinCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;

import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Person;
/**
 * Finds the person to which you owe the most money
 */
public class MinCommand extends Command {

    public static final String COMMAND_WORD = "min";
    public static final String COMMAND_SHORTCUT = "mn";
    public static final String MESSAGE_SUCCESS_FOUND = "The contact to which you owe the most money is: ";
    public static final String MESSAGE_SUCCESS_NO_RESULT = "Good news! You don't owe any money.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person to which you owe the most money ";
    private CommandResult result;

    public CommandResult getResult() {
        return result;
    }

    public void setResult(CommandResult result) {
        this.result = result;
    }
    @Override
    public CommandResult execute() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = Index.fromZeroBased(0);
        double lowestDebt = 0.0;

        for (int i = 0; i < lastShownList.size(); i++) {
            Person person = lastShownList.get(i);
            if (person.getMoney().balance < lowestDebt) {
                index = Index.fromZeroBased(i);
                lowestDebt = person.getMoney().balance;
            }
            if (lowestDebt == 0.0) {
                result = new CommandResult(MESSAGE_SUCCESS_NO_RESULT);
            } else {
                EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
                result = new CommandResult(MESSAGE_SUCCESS_FOUND + lastShownList.get(index.getZeroBased()).getName());
            }
        }
        return result;
    }
}
```
###### /java/seedu/address/logic/commands/BalanceCommand.java
``` java

package seedu.address.logic.commands;

import java.text.DecimalFormat;
import java.util.List;

import seedu.address.model.person.Person;
/**
 * Handles the balance command.
 */
public class BalanceCommand extends Command {

    public static final String COMMAND_WORD = "balance";
    public static final String COMMAND_SHORTCUT = "b";
    public static final String MESSAGE_SUCCESS = "Shown balance.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Displays your overall balance";
    private static double calculatedBalance;
    private static DecimalFormat twoDecimalPlaces = new DecimalFormat("0.00");

    public static DecimalFormat getFormatTwoDecimalPlaces() {
        return twoDecimalPlaces;
    }

    public static double getCalculatedBalance() {
        return calculatedBalance;
    }

    @Override
    public CommandResult execute() {

        calculatedBalance = getBalanceFromTravelBanker();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + "Your balance is "
                + twoDecimalPlaces.format(calculatedBalance) + ".");
    }

    public double getBalanceFromTravelBanker() {
        double accumulator = 0.00;

        List<Person> lastShownList = model.getFilteredPersonList();

        for (Person person : lastShownList) {
            double currentPersonBalance = person.getMoney().balance;
            accumulator = accumulator + currentPersonBalance;
        }

        return accumulator;
    }
}
```
###### /java/seedu/address/logic/commands/SearchTagCommand.java
``` java
package seedu.address.logic.commands;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Lists all the people that contain specified tags.
 */
public class SearchTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "searchtag";
    public static final String COMMAND_SHORTCUT = "st";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": finds all the people having the specified tags. "
            + "Person must have all the provided tags to be selected. "
            + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_TAG + "owesMoney "
            + PREFIX_TAG + "friends";

    public static final String MESSAGE_SUCCESS = "Found Persons with tags: ";
    public static final String MESSAGE_FAILURE = "No results found with the tag: ";

    private final Set<Tag> tagsToFind;

    /**
     * This returns a SearchTagCommand that is ready to be executed.
     * @param tags  that need to be colored
     */
    public SearchTagCommand(Set<Tag> tags) {
        this.tagsToFind = tags;
    }

    /**
     * This command lists all the persons which match the search criteria provided by the user.
     * @return
     */
    @Override
    public CommandResult executeUndoableCommand() {
        model.updateFilteredPersonList(personHasTags(tagsToFind));
        int result = model.getFilteredPersonList().size();
        String tagsFormatted = formatTagsFeedback(tagsToFind);
        if (result > 0) {
            return new CommandResult(MESSAGE_SUCCESS
                    + "\n"
                    + tagsFormatted
                    + "\n"
                    + getMessageForPersonListShownSummary(result));
        } else {
            return new CommandResult(MESSAGE_FAILURE
                    + "\n"
                    + tagsFormatted);
        }
    }

    /**
     * This function returns person Predicate that indicates if a given has the tags we are
     * looking for.
     * @return
     */
    public static Predicate<Person> personHasTags(Set<Tag> tagsToCheck) {
        return person -> person.getTags().containsAll(tagsToCheck);
    }

    /**
     * Formats the tags to a string to display clearly to user.
     * @param tagsToFormat
     * @return
     */
    public static String formatTagsFeedback(Set<Tag> tagsToFormat) {
        String tagsFormatted = tagsToFormat.toString()
                .replace("[", " ")
                .replace("]", " ")
                .replace("[,", " ");
        return tagsFormatted;
    }
}






```
###### /java/seedu/address/logic/commands/MapCommand.java
``` java
/**
 * Shows a map and searchs for the nearest ATM.
 */

public class MapCommand extends Command {

    public static final String COMMAND_WORD = "map";
    public static final String COMMAND_SHORTCUT = "mp";
    public static final String MESSAGE_SUCCESS = "Map Shown";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMapRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }


}
```

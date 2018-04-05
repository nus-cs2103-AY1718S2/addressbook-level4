# ZhangYijiong
###### \java\seedu\address\commons\events\ui\PersonPanelPathChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelPathChangedEvent extends BaseEvent {


    private final PersonCard newSelection;

    public PersonPanelPathChangedEvent(PersonCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\address\logic\commands\PathCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PersonPanelPathChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.ui.PersonCard;

/**
 * Selects a person identified using it's last displayed index from the address book
 * and show the path to the address of the person identified
 */
public class PathCommand extends Command {

    public static final String COMMAND_WORD = "path";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Show the path to the address of the person identified "
            + "by the index number used in the last person listing\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_PATH_PERSON_SUCCESS = "Path to Person: %1$s";

    private final Index targetIndex;

    public PathCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        PersonCard personToFindPath = new PersonCard(
                lastShownList.get(targetIndex.getZeroBased()), targetIndex.getOneBased());
        EventsCenter.getInstance().post(new PersonPanelPathChangedEvent(personToFindPath));
        return new CommandResult(String.format(MESSAGE_PATH_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PathCommand // instanceof handles nulls
                && this.targetIndex.equals(((PathCommand) other).targetIndex)); // state check
    }
}

```
###### \java\seedu\address\logic\parser\PathCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PathCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class PathCommandParser implements Parser<PathCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PathCommand
     * and returns an PathCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PathCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PathCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PathCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadGoogleMapAddressPage(Person person) {
        loadPage(GOOGLE_MAP_SEARCH_PAGE + person.getAddress().getGoogleMapSearchForm());
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadGoogleMapPathPage(Person person) {
        loadPage(GOOGLE_MAP_PATH_SEARCH_PAGE + Address.ADDRESS_USER_OWN
                + "/" + person.getAddress().getGoogleMapSearchForm());
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGoogleMapAddressPage(event.getNewSelection().person);
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelPathChangedEvent(PersonPanelPathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGoogleMapPathPage(event.getNewSelection().person);
    }
}
```

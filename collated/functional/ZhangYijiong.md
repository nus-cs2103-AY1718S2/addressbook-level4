# ZhangYijiong
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    private void loadGoogleMapAddressPage(Person person) {
        loadPage(GOOGLE_MAP_SEARCH_PAGE + person.getAddress().getGoogleMapSearchForm());
    }
```
###### /java/seedu/address/ui/BrowserPanel.java
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
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGoogleMapAddressPage(event.getNewSelection().person);
    }

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelPathChangedEvent(PersonPanelPathChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGoogleMapPathPage(event.getNewSelection().person);
    }
}
```
###### /java/seedu/address/ui/OrderCard.java
``` java
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class OrderCard extends UiPart<Region> {
    private static final String FXML = "OrderCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label order;
    @FXML
    private Label id;
    @FXML
    private Label address;
    @FXML
    private Label description;

    public OrderCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        bindListeners(task);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Task task) {
        order.textProperty().bind(Bindings.convert(task.orderObjectProperty()));
        address.textProperty().bind(Bindings.convert(task.addressObjectProperty()));
        description.textProperty().bind(Bindings.convert(task.descriptionObjectProperty()));

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderCard)) {
            return false;
        }

        // state check
        OrderCard card = (OrderCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
```
###### /java/seedu/address/ui/OrderQueuePanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.OrderPanelSelectionChangedEvent;
import seedu.address.model.task.Task;

/**
 * Implementation follows {@code PersonListPanel}
 * Panel containing the queue of orders.
 */
public class OrderQueuePanel extends UiPart<Region> {
    private static final String FXML = "OrderQueuePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderQueuePanel.class);

    @FXML
    private ListView<OrderCard> orderListView;

    public OrderQueuePanel(ObservableList<Task> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Task> taskList) {
        ObservableList<OrderCard> mappedList = EasyBind.map(
                taskList, (task) -> new OrderCard(task, taskList.indexOf(task) + 1));
        orderListView.setItems(mappedList);
        orderListView.setCellFactory(listView -> new OrderQueueViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        orderListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in order list panel changed to : '" + newValue + "'");
                        raise(new OrderPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code OrderCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            orderListView.scrollTo(index);
            orderListView.getSelectionModel().clearAndSelect(index);
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code OrderCard}.
     */
    class OrderQueueViewCell extends ListCell<OrderCard> {

        @Override
        protected void updateItem(OrderCard order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(order.getRoot());
            }
        }
    }
}
```
###### /java/seedu/address/commons/events/ui/OrderPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.OrderCard;

/**
 * Gets an event the panel change selection
 */
public class OrderPanelSelectionChangedEvent extends BaseEvent {

    private final OrderCard newSelection;

    public OrderPanelSelectionChangedEvent(OrderCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public OrderCard getNewSelection() {
        return newSelection;
    }
}
```
###### /java/seedu/address/commons/events/ui/PersonPanelPathChangedEvent.java
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
###### /java/seedu/address/logic/parser/PathCommandParser.java
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
###### /java/seedu/address/logic/parser/AddOrderCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.dish.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Order;
import seedu.address.model.task.Count;
import seedu.address.model.task.Distance;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns an AddOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ORDER, PREFIX_ADDRESS, PREFIX_PRICE,
                        PREFIX_DISTANCE, PREFIX_COUNT, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_ORDER, PREFIX_ADDRESS, PREFIX_PRICE,
                PREFIX_DISTANCE, PREFIX_COUNT, PREFIX_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        try {
            Order order = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).get();
            Distance distance = ParserUtil.parseDistance(argMultimap.getValue(PREFIX_DISTANCE)).get();
            Count count = ParserUtil.parseCount(argMultimap.getValue(PREFIX_COUNT)).get();
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).orElse("");

            Task task = new Task(order, address, price, distance, count, description);

            return new AddOrderCommand(task);
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
###### /java/seedu/address/logic/commands/AddOrderCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Add an order to the application's order queue
 */

public class AddOrderCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an order to the processing queue. \n"
            + "Parameters: "
            + PREFIX_ORDER + "ORDER "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_PRICE + "PRICE (of the order) "
            + PREFIX_DISTANCE + "DISTANCE (to the address) "
            + PREFIX_COUNT + "COUNT (past order count) "
            + PREFIX_DESCRIPTION + "[" + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ORDER + "CHICKEN RICE "
            + PREFIX_ADDRESS + "NUS "
            + PREFIX_PRICE + "3 "
            + PREFIX_DISTANCE + "5 "
            + PREFIX_COUNT + "1 "
            + PREFIX_DESCRIPTION + "CHILI SAUCE REQUIRED";

    public static final String MESSAGE_SUCCESS = "New Order added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This order already exists in the address book";

    private final Task toAdd;

    public AddOrderCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && toAdd.equals(((AddOrderCommand) other).toAdd));
    }

}
```
###### /java/seedu/address/logic/commands/PathCommand.java
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
###### /java/seedu/address/model/task/Distance.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Implementation follows {@code Count}
 * Represents an Task's distance
 * Guarantees: immutable; is valid as declared in {@link #isValidDistance(String)}
 */
public class Distance {


    public static final String MESSAGE_DISTANCE_CONSTRAINTS =
            "Distance numbers can only be positive integers, in terms of km";
    public static final String DISTANCE_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Distance}.
     *
     * @param distance A valid distance number.
     */
    public Distance(String distance) {
        requireNonNull(distance);
        checkArgument(isValidDistance(distance), MESSAGE_DISTANCE_CONSTRAINTS);
        this.value = distance;
    }

    /**
     * Returns true if a given string is a valid task distance number.
     */
    public static boolean isValidDistance(String test) {
        return test.matches(DISTANCE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Distance // instanceof handles nulls
                && this.value.equals(((Distance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     *  Returns distance in integer form to be able used by {@code compareTo} in Task
     */
    public int toInt() {
        return Integer.parseInt(value);
    }
}

```
###### /java/seedu/address/model/task/Count.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Implementation follows {@code Price}
 * Represents an Task's past order count number
 * Guarantees: immutable; is valid as declared in {@link #isValidCount(String)}
 */
public class Count {


    public static final String MESSAGE_COUNT_CONSTRAINTS =
            "Count numbers can only be positive integers";
    public static final String COUNT_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Count}.
     *
     * @param count A valid count number.
     */
    public Count(String count) {
        requireNonNull(count);
        checkArgument(isValidCount(count), MESSAGE_COUNT_CONSTRAINTS);
        this.value = count;
    }

    /**
     * Returns true if a given string is a valid task count number.
     */
    public static boolean isValidCount(String test) {
        return test.matches(COUNT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Count // instanceof handles nulls
                && this.value.equals(((Count) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     *  Returns count in integer form to be able used by {@code compareTo} in Task
     */
    public int toInt() {
        return Integer.parseInt(value);
    }
}
```

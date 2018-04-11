# amad-person
###### /java/seedu/address/commons/events/model/ChangeOrderStatusEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.order.Order;

/**
 * Represents a request to change the order status of an existing order in the application.
 */
public class ChangeOrderStatusEvent extends BaseEvent {
    private Index index;
    private Order targetOrder;
    private String orderStatus;

    public ChangeOrderStatusEvent(Index index, Order targetOrder, String orderStatus) {
        this.index = index;
        this.targetOrder = targetOrder;
        this.orderStatus = orderStatus;
    }

    public Index getIndexOrderForStatusChange() {
        return this.index;
    }

    public Order getOrderForStatusChange() {
        return this.targetOrder;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/ChangeThemeEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a request to change the theme of the application.
 */
public class ChangeThemeEvent extends BaseEvent {

    private String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return this.theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/OrderPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.OrderCard;

/**
 * Represents a selection change in the Order List Panel
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
###### /java/seedu/address/logic/commands/AddOrderCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayOrderListEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
// import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds an order to a person in the address book.
 */
public class AddOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "orderadd";
    public static final String COMMAND_ALIAS = "oa";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_ORDER_INFORMATION + "ORDER INFO "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_DELIVERY_DATE + "DELIVERY DATE ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an order to the selected person in the "
            + "address book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ORDER_INFORMATION + "ORDER INFO "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_DELIVERY_DATE + "DELIVERY DATE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ORDER_INFORMATION + "Books "
            + PREFIX_PRICE + "10.00 "
            + PREFIX_QUANTITY + "2 "
            + PREFIX_DELIVERY_DATE + "12-12-2018\n";

    public static final String MESSAGE_ADD_ORDER_SUCCESS = "Added order to %1$s:\n[%2$s]";
    public static final String MESSAGE_ORDER_NOT_ADDED = "Could not add order to Person.";

    private final Index index;
    private final Order orderToAdd;

    private Person person;

    /**
     * @param index of the person in the filtered person list to edit
     * @param orderToAdd order to be added to person
     */
    public AddOrderCommand(Index index, Order orderToAdd) {
        requireNonNull(index);
        requireNonNull(orderToAdd);

        this.index = index;
        this.orderToAdd = orderToAdd;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            // TODO: update model
            // model.addOrderToPerson(person, orderToAdd);
            model.addOrderToOrderList(orderToAdd);
        // } catch (PersonNotFoundException pnfe) {
            // throw new AssertionError("The target person cannot be missing");
        } catch (Exception e) { // TODO: define more specific exception
            throw new CommandException(MESSAGE_ORDER_NOT_ADDED);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new DisplayOrderListEvent());
        return new CommandResult(String.format(MESSAGE_ADD_ORDER_SUCCESS, person.getName(), orderToAdd));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        person = lastShownList.get(index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && orderToAdd.equals(((AddOrderCommand) other).orderToAdd));
    }
}
```
###### /java/seedu/address/logic/commands/ChangeOrderStatusCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditOrderCommand.MESSAGE_DUPLICATE_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_STATUS;
import static seedu.address.model.order.OrderStatus.MESSAGE_ORDER_STATUS_CONSTRAINTS;
import static seedu.address.model.order.OrderStatus.isValidOrderStatus;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.ChangeOrderStatusEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.order.exceptions.OrderNotFoundException;

/**
 * Changes the order status of an existing order in the address book.
 */
public class ChangeOrderStatusCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "orderstatus";
    public static final String COMMAND_ALIAS = "os";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " INDEX " + PREFIX_ORDER_STATUS + " ORDER STATUS";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the order status of the order identified by the index number used in the last order listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ORDER_STATUS + "ORDER STATUS\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ORDER_STATUS + "done";

    public static final String MESSAGE_ORDER_STATUS_CHANGED_SUCCESS = "Order status of order %1$s changed to %2$s.";
    public static final String MESSAGE_INVALID_ORDER_STATUS = "Order status %1$s is invalid.\n"
            + MESSAGE_ORDER_STATUS_CONSTRAINTS;

    private final Index targetIndex;
    private final String orderStatus;

    private Order orderForChangeStatus;

    public ChangeOrderStatusCommand(Index targetIndex, String orderStatus) {
        requireNonNull(targetIndex);
        requireNonNull(orderStatus);

        this.targetIndex = targetIndex;
        this.orderStatus = orderStatus;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        if (!isValidOrderStatus(orderStatus)) {
            throw new CommandException(String.format(MESSAGE_INVALID_ORDER_STATUS, orderStatus));
        }

        try {
            model.updateOrderStatus(orderForChangeStatus, orderStatus);
        } catch (UniqueOrderList.DuplicateOrderException doe) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        } catch (OrderNotFoundException onfe) {
            throw new AssertionError("The target order cannot be missing.");
        }

        EventsCenter.getInstance().post(new ChangeOrderStatusEvent(targetIndex, orderForChangeStatus, orderStatus));
        return new CommandResult(String.format(MESSAGE_ORDER_STATUS_CHANGED_SUCCESS,
                targetIndex.getOneBased(), orderStatus));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        orderForChangeStatus = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeOrderStatusCommand // instanceof handles nulls
                && this.targetIndex.equals(((ChangeOrderStatusCommand) other).targetIndex)
                && this.orderStatus.equals(((ChangeOrderStatusCommand) other).orderStatus));
    }
}
```
###### /java/seedu/address/logic/commands/ChangeThemeCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.theme.Theme.MESSAGE_THEME_CONSTRAINTS;
import static seedu.address.model.theme.Theme.isValidTheme;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.theme.Theme;

/**
 * Changes the theme of the application.
 */
public class ChangeThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the theme of the application.\n"
            + "Parameters: THEME\n"
            + "Example: " + COMMAND_WORD + " light";

    public static final String MESSAGE_THEME_CHANGED_SUCCESS = "Theme changed to %1$s.";
    public static final String MESSAGE_INVALID_THEME = "Theme %1$s not supported.\n" + MESSAGE_THEME_CONSTRAINTS;

    private final String themeVersion;

    public ChangeThemeCommand(String themeVersion) {
        requireNonNull(themeVersion);
        this.themeVersion = themeVersion;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!isValidTheme(themeVersion)) {
            throw new CommandException(String.format(MESSAGE_INVALID_THEME, themeVersion));
        }

        Theme.setCurrentTheme(themeVersion);

        EventsCenter.getInstance().post(new ChangeThemeEvent(themeVersion));
        return new CommandResult(String.format(MESSAGE_THEME_CHANGED_SUCCESS, themeVersion));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && this.themeVersion.equals(((ChangeThemeCommand) other).themeVersion)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/DeleteOrderCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayOrderListEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.OrderNotFoundException;

/**
 * Deletes an order identified using its last displayed index from the address book.
 */
public class DeleteOrderCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "orderdelete";
    public static final String COMMAND_ALIAS = "od";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by the index number used in the last order listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: %1$s";

    private final Index targetIndex;

    private Order orderToDelete;

    public DeleteOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(orderToDelete);
        try {
            model.deleteOrder(orderToDelete);
        } catch (OrderNotFoundException onfe) {
            throw new AssertionError("The target order cannot be missing");
        }

        EventsCenter.getInstance().post(new DisplayOrderListEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        orderToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteOrderCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteOrderCommand) other).targetIndex) // state check
                && Objects.equals(this.orderToDelete, ((DeleteOrderCommand) other).orderToDelete));
    }
}
```
###### /java/seedu/address/logic/commands/EditOrderCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayOrderListEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.order.exceptions.OrderNotFoundException;

/**
 * Edits the details of an existing order in the address book.
 */
public class EditOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "orderedit";
    public static final String COMMAND_ALIAS = "oe";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "INDEX "
            + "[" + PREFIX_ORDER_INFORMATION + "ORDER INFORMATION] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_DELIVERY_DATE + "DELIVERY DATE] ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
            + "by the index number used in the last order listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ORDER_INFORMATION + "ORDER INFORMATION] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_DELIVERY_DATE + "DELIVERY DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRICE + "15.00 "
            + PREFIX_DELIVERY_DATE + "18-09-2018";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in the address book.";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    private Order orderToEdit;
    private Order editedOrder;

    /**
     * @param index of the order in the filtered order list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public EditOrderCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(index);
        requireNonNull(editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateOrder(orderToEdit, editedOrder);
        } catch (UniqueOrderList.DuplicateOrderException doe) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        } catch (OrderNotFoundException onfe) {
            throw new AssertionError("The target order cannot be missing.");
        }

        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        EventsCenter.getInstance().post(new DisplayOrderListEvent());
        return new CommandResult(String.format(MESSAGE_EDIT_ORDER_SUCCESS, editedOrder));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Order> lastShownOrderList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownOrderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        orderToEdit = lastShownOrderList.get(index.getZeroBased());
        editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditOrderDescriptor editOrderDescriptor) {
        assert orderToEdit != null;

        OrderInformation updatedOrderInformation = editOrderDescriptor.getOrderInformation()
                .orElse(orderToEdit.getOrderInformation());
        Price updatedPrice = editOrderDescriptor.getPrice().orElse(orderToEdit.getPrice());
        Quantity updatedQuantity = editOrderDescriptor.getQuantity().orElse(orderToEdit.getQuantity());
        DeliveryDate updatedDeliveryDate = editOrderDescriptor.getDeliveryDate().orElse(orderToEdit.getDeliveryDate());

        return new Order(updatedOrderInformation, updatedPrice, updatedQuantity, updatedDeliveryDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditOrderCommand)) {
            return false;
        }

        // state check
        EditOrderCommand eo = (EditOrderCommand) other;
        return index.equals(eo.index)
                && editOrderDescriptor.equals(eo.editOrderDescriptor)
                && Objects.equals(orderToEdit, eo.orderToEdit);
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditOrderDescriptor {
        private OrderInformation orderInformation;
        private Price price;
        private Quantity quantity;
        private DeliveryDate deliveryDate;

        public EditOrderDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setOrderInformation(toCopy.orderInformation);
            setPrice(toCopy.price);
            setQuantity(toCopy.quantity);
            setDeliveryDate(toCopy.deliveryDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.orderInformation, this.price, this.quantity, this.deliveryDate);
        }

        public void setOrderInformation(OrderInformation orderInformation) {
            this.orderInformation = orderInformation;
        }

        public Optional<OrderInformation> getOrderInformation() {
            return Optional.ofNullable(orderInformation);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setDeliveryDate(DeliveryDate deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public Optional<DeliveryDate> getDeliveryDate() {
            return Optional.ofNullable(deliveryDate);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            // state check
            EditOrderDescriptor eo = (EditOrderDescriptor) other;

            return getOrderInformation().equals(eo.getOrderInformation())
                    && getPrice().equals(eo.getPrice())
                    && getQuantity().equals(eo.getQuantity())
                    && getDeliveryDate().equals(eo.getDeliveryDate());
        }
    }
}
```
###### /java/seedu/address/logic/CommandSyntaxListUtil.java
``` java
package seedu.address.logic;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.commands.ChangeOrderStatusCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEntryCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.commands.DeletePreferenceCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCalendarEntryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewBackCommand;
import seedu.address.logic.commands.ViewCalendarCommand;
import seedu.address.logic.commands.ViewNextCommand;
import seedu.address.logic.commands.ViewTodayCommand;

/**
 * Returns the syntax list of existing commands.
 */
public final class CommandSyntaxListUtil {
    private static ArrayList<String> commandSyntaxList;

    public static ArrayList<String> getCommandSyntaxList() {
        commandSyntaxList = new ArrayList<>();
        setCommandSyntaxList();
        return commandSyntaxList;
    }

    /**
     * Constructs commandSyntaxList for existing commands.
     */
    private static void setCommandSyntaxList() {
        commandSyntaxList.add(AddCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(AddEntryCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(AddOrderCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(ChangeThemeCommand.COMMAND_WORD);
        commandSyntaxList.add(ChangeOrderStatusCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(ClearCommand.COMMAND_WORD);
        commandSyntaxList.add(DeleteCommand.COMMAND_WORD);
        commandSyntaxList.add(DeleteEntryCommand.COMMAND_WORD);
        commandSyntaxList.add(DeleteGroupCommand.COMMAND_WORD);
        commandSyntaxList.add(DeleteOrderCommand.COMMAND_WORD);
        commandSyntaxList.add(DeletePreferenceCommand.COMMAND_WORD);
        commandSyntaxList.add(EditCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(EditEntryCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(EditOrderCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(ExitCommand.COMMAND_WORD);
        commandSyntaxList.add(FindCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(FindGroupCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(FindPreferenceCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(HelpCommand.COMMAND_WORD);
        commandSyntaxList.add(HistoryCommand.COMMAND_WORD);
        commandSyntaxList.add(ListCommand.COMMAND_WORD);
        commandSyntaxList.add(ListCalendarEntryCommand.COMMAND_WORD);
        commandSyntaxList.add(ListOrderCommand.COMMAND_WORD);
        commandSyntaxList.add(RedoCommand.COMMAND_WORD);
        commandSyntaxList.add(SelectCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(UndoCommand.COMMAND_WORD);
        commandSyntaxList.add(ViewBackCommand.COMMAND_WORD);
        commandSyntaxList.add(ViewCalendarCommand.COMMAND_WORD);
        commandSyntaxList.add(ViewNextCommand.COMMAND_WORD);
        commandSyntaxList.add(ViewTodayCommand.COMMAND_WORD);

        sortCommandList();
    }

    /**
     * Sorts commandSyntaxList in lexicographical order.
     */
    private static void sortCommandList() {
        Collections.sort(commandSyntaxList);
    }
}
```
###### /java/seedu/address/logic/parser/AddOrderCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * Parses input arguments and creates a new AddOrderCommand object
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns an AddOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ORDER_INFORMATION, PREFIX_PRICE,
                        PREFIX_QUANTITY, PREFIX_DELIVERY_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_ORDER_INFORMATION, PREFIX_PRICE,
                PREFIX_QUANTITY, PREFIX_DELIVERY_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        try {
            OrderInformation orderInformation = ParserUtil.parseOrderInformation(argMultimap
                    .getValue(PREFIX_ORDER_INFORMATION)).get();
            Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).get();
            Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY)).get();
            DeliveryDate deliveryDate = ParserUtil.parseDeliveryDate(argMultimap.getValue(PREFIX_DELIVERY_DATE)).get();

            Order order = new Order(orderInformation, price, quantity, deliveryDate);
            return new AddOrderCommand(index, order);
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
###### /java/seedu/address/logic/parser/ChangeThemeCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangeThemeCommand object.
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeThemeCommand
     * and returns an ChangeThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ChangeThemeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }

        return new ChangeThemeCommand(trimmedArgs);
    }
}
```
###### /java/seedu/address/logic/parser/DeleteOrderCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteOrderCommand object
 */
public class DeleteOrderCommandParser implements Parser<DeleteOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteOrderCommand
     * and returns an DeleteOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteOrderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteOrderCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE)
            );
        }
    }
}
```
###### /java/seedu/address/logic/parser/EditOrderCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditOrderCommand object.
 */
public class EditOrderCommandParser implements Parser<EditOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditOrderCommand
     * and returns an EditOrderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ORDER_INFORMATION, PREFIX_PRICE, PREFIX_QUANTITY,
                        PREFIX_DELIVERY_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));
        }

        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        try {
            ParserUtil.parseOrderInformation(argMultimap.getValue(PREFIX_ORDER_INFORMATION))
                    .ifPresent(editOrderDescriptor::setOrderInformation);
            ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).ifPresent(editOrderDescriptor::setPrice);
            ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY)).ifPresent(editOrderDescriptor::setQuantity);
            ParserUtil.parseDeliveryDate(argMultimap.getValue(PREFIX_DELIVERY_DATE))
                    .ifPresent(editOrderDescriptor::setDeliveryDate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditOrderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditOrderCommand(index, editOrderDescriptor);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String orderInformation} into a {@code OrderInformation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code orderInformation} is invalid.
     */
    public static OrderInformation parseOrderInformation(String orderInformation) throws IllegalValueException {
        requireNonNull(orderInformation);
        String trimmedOrderInformation = orderInformation.trim();
        if (!OrderInformation.isValidOrderInformation(trimmedOrderInformation)) {
            throw new IllegalValueException(OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);
        }
        return new OrderInformation(trimmedOrderInformation);
    }

    /**
     * Parses a {@code Optional<String> orderInformation} into an {@code Optional<OrderInformation>}
     * if {@code orderInformation} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<OrderInformation> parseOrderInformation(Optional<String> orderInformation)
            throws IllegalValueException {
        requireNonNull(orderInformation);
        return orderInformation.isPresent()
                ? Optional.of(parseOrderInformation(orderInformation.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String orderStatus} into a {@code OrderStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code orderStatus} is invalid.
     */
    public static OrderStatus parseOrderStatus(String orderStatus) throws IllegalValueException {
        requireNonNull(orderStatus);
        String trimmedOrderStatus = orderStatus.trim();
        if (!OrderStatus.isValidOrderStatus(trimmedOrderStatus)) {
            throw new IllegalValueException(OrderStatus.MESSAGE_ORDER_STATUS_CONSTRAINTS);
        }
        return new OrderStatus(trimmedOrderStatus);
    }

    /**
     * Parses a {@code Optional<String> orderStatus} into an {@code Optional<OrderStatus>}
     * if {@code orderStatus} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<OrderStatus> parseOrderStatus(Optional<String> orderStatus)
            throws IllegalValueException {
        requireNonNull(orderStatus);
        return orderStatus.isPresent()
                ? Optional.of(parseOrderStatus(orderStatus.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws IllegalValueException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code Optional<String> price} into an {@code Optional<Price>}
     * if {@code price} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Price> parsePrice(Optional<String> price)
            throws IllegalValueException {
        requireNonNull(price);
        return price.isPresent()
                ? Optional.of(parsePrice(price.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String quantity} into a {@code Quantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code quantity} is invalid.
     */
    public static Quantity parseQuantity(String quantity) throws IllegalValueException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        if (!Quantity.isValidQuantity(trimmedQuantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        return new Quantity(trimmedQuantity);
    }

    /**
     * Parses a {@code Optional<String> quantity} into an {@code Optional<Quantity>}
     * if {@code quantity} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Quantity> parseQuantity(Optional<String> quantity)
            throws IllegalValueException {
        requireNonNull(quantity);
        return quantity.isPresent()
                ? Optional.of(parseQuantity(quantity.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String deliveryDate} into a {@code DeliveryDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code deliveryDate} is invalid.
     */
    public static DeliveryDate parseDeliveryDate(String deliveryDate) throws IllegalValueException {
        requireNonNull(deliveryDate);
        String trimmedDeliveryDate = deliveryDate.trim();
        if (!DeliveryDate.isValidDeliveryDate(trimmedDeliveryDate)) {
            throw new IllegalValueException(DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);
        }
        return new DeliveryDate(trimmedDeliveryDate);
    }

    /**
     * Parses a {@code Optional<String> deliveryDate} into an {@code Optional<DeliveryDate>}
     * if {@code deliveryDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DeliveryDate> parseDeliveryDate(Optional<String> deliveryDate)
            throws IllegalValueException {
        requireNonNull(deliveryDate);
        return deliveryDate.isPresent()
                ? Optional.of(parseDeliveryDate(deliveryDate.get()))
                : Optional.empty();
    }
    //@@ author

    /**
     * Parses a {@code String eventTitle} into a {@code EntryTitle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code eventTitle} is invalid.
     */
    public static EntryTitle parseEventTitle(String eventTitle) throws IllegalValueException {
        requireNonNull(eventTitle);
        String trimmedEventTitle = eventTitle.trim();
        if (!EntryTitle.isValidEntryTitle(trimmedEventTitle)) {
            throw new IllegalValueException(EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);
        }
        return new EntryTitle(trimmedEventTitle);
    }

    /**
     * Parses a {@code Optional<String> eventTitle} into an {@code Optional<EntryTitle>}
     * if {@code eventTitle} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EntryTitle> parseEventTitle(Optional<String> eventTitle)
            throws IllegalValueException {
        requireNonNull(eventTitle);
        return eventTitle.isPresent()
                ? Optional.of(parseEventTitle(eventTitle.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String startDate} into a {@code StartDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code startDate} is invalid.
     */
    public static StartDate parseStartDate(String startDate) throws IllegalValueException {
        requireNonNull(startDate);
        String trimmedStartDate = startDate.trim();
        if (!DateUtil.isValidDate(trimmedStartDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_START_DATE_CONSTRAINTS);
        }
        return new StartDate(trimmedStartDate);
    }

    /**
     * Parses a {@code Optional<String> startDate} into an {@code Optional<StartDate>}
     * if {@code startDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<StartDate> parseStartDate(Optional<String> startDate)
            throws IllegalValueException {
        requireNonNull(startDate);
        return startDate.isPresent()
                ? Optional.of(parseStartDate(startDate.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String endDate} into a {@code EndDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code endDate} is invalid.
     */
    public static EndDate parseEndDate(String endDate) throws IllegalValueException {
        requireNonNull(endDate);
        String trimmedEndDate = endDate.trim();
        if (!DateUtil.isValidDate(trimmedEndDate)) {
            throw new IllegalValueException(EndDate.MESSAGE_END_DATE_CONSTRAINTS);
        }
        return new EndDate(trimmedEndDate);
    }

    /**
     * Parses a {@code Optional<String> endDate} into an {@code Optional<EndDate>}
     * if {@code endDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EndDate> parseEndDate(Optional<String> endDate)
            throws IllegalValueException {
        requireNonNull(endDate);
        return endDate.isPresent()
                ? Optional.of(parseEndDate(endDate.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String startTime} into a {@code StartTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code startTime} is invalid.
     */
    public static StartTime parseStartTime(String startTime) throws IllegalValueException {
        requireNonNull(startTime);
        String trimmedStartTime = startTime.trim();
        if (!TimeUtil.isValidTime(trimmedStartTime)) {
            throw new IllegalValueException(StartTime.MESSAGE_START_TIME_CONSTRAINTS);
        }
        return new StartTime(trimmedStartTime);
    }

    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<StartTime>}
     * if {@code startTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime)
            throws IllegalValueException {
        requireNonNull(startTime);
        return startTime.isPresent()
                ? Optional.of(parseStartTime(startTime.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String endTime} into a {@code EndTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code endTime} is invalid.
     */
    public static EndTime parseEndTime(String endTime) throws IllegalValueException {
        requireNonNull(endTime);
        String trimmedEndTime = endTime.trim();
        if (!TimeUtil.isValidTime(trimmedEndTime)) {
            throw new IllegalValueException(EndTime.MESSAGE_END_TIME_CONSTRAINTS);
        }
        return new EndTime(trimmedEndTime);
    }

    /**
     * Parses a {@code Optional<String> endTime} into an {@code Optional<EndTime>}
     * if {@code endTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EndTime> parseEndTime(Optional<String> endTime)
            throws IllegalValueException {
        requireNonNull(endTime);
        return endTime.isPresent()
                ? Optional.of(parseEndTime(endTime.get()))
                : Optional.empty();
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     */
    public void updateOrder(Order target, Order editedOrder)
        throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException {
        requireNonNull(editedOrder);

        orders.setOrder(target, editedOrder);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    //// order-level operations

    /**
     * Adds order to list of orders.
     */
    public void addOrderToOrderList(Order orderToAdd) throws UniqueOrderList.DuplicateOrderException {
        orders.add(orderToAdd);
    }

    /**
     * Removes order from list of orders.
     */
    public void deleteOrder(Order targetOrder) throws OrderNotFoundException {
        orders.remove(targetOrder);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asObservableList();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void addOrderToOrderList(Order orderToAdd) throws UniqueOrderList.DuplicateOrderException {
        addressBook.addOrderToOrderList(orderToAdd);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteOrder(Order targetOrder) throws OrderNotFoundException {
        addressBook.deleteOrder(targetOrder);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateOrder(Order target, Order editedOrder)
        throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException {
        requireAllNonNull(target, editedOrder);

        addressBook.updateOrder(target, editedOrder);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return FXCollections.unmodifiableObservableList(filteredOrders);
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {

        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }
```
###### /java/seedu/address/model/order/DeliveryDate.java
``` java
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents Order's delivery date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeliveryDate(String)}
 */
public class DeliveryDate {

    public static final String MESSAGE_DELIVERY_DATE_CONSTRAINTS =
            "Date should be DD-MM-YYYY, and it should not be blank";

    public static final String DELIVERY_DATE_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}"; // format
    public static final String DELIVERY_DATE_VALIDATION_DATE_FORMAT = "dd-MM-yyyy"; // legal dates

    private final String deliveryDate;

    /**
     * Constructs a {@code DeliveryDate}.
     *
     * @param date A valid Date.
     */
    public DeliveryDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDeliveryDate(date), MESSAGE_DELIVERY_DATE_CONSTRAINTS);
        this.deliveryDate = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDeliveryDate(String test) {
        requireNonNull(test);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DELIVERY_DATE_VALIDATION_DATE_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(test);
        } catch (ParseException e) {
            return false;
        }

        return test.matches(DELIVERY_DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return deliveryDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliveryDate // instanceof handles nulls
                && this.deliveryDate.equals(((DeliveryDate) other).deliveryDate)); // state check
    }

    @Override
    public int hashCode() {
        return deliveryDate.hashCode();
    }
}

```
###### /java/seedu/address/model/order/exceptions/OrderNotFoundException.java
``` java
package seedu.address.model.order.exceptions;

/**
 * Signals that the operation is unable to find the specified Order.
 */
public class OrderNotFoundException extends Exception {
}

```
###### /java/seedu/address/model/order/Order.java
``` java
package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_ONGOING;

import java.util.Objects;

/**
 * Represents an Order in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    private final OrderInformation orderInformation;
    private final OrderStatus orderStatus;
    private final Price price;
    private final Quantity quantity;
    private final DeliveryDate deliveryDate;

    /**
     * Every field must be present and not null.
     */
    public Order(OrderInformation orderInformation, Price price, Quantity quantity, DeliveryDate deliveryDate) {
        requireAllNonNull(orderInformation, price, quantity, deliveryDate);
        this.orderInformation = orderInformation;
        this.orderStatus = new OrderStatus(ORDER_STATUS_ONGOING); // default value is ongoing
        this.price = price;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
    }

    public OrderInformation getOrderInformation() {
        return orderInformation;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public DeliveryDate getDeliveryDate() {
        return deliveryDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        // TODO: orders can have the same information (just the person associated with them can be diff)
        Order otherOrder = (Order) other;
        return otherOrder.getOrderInformation().equals(this.getOrderInformation())
                && otherOrder.getOrderStatus().equals(this.getOrderStatus())
                && otherOrder.getPrice().equals(this.getPrice())
                && otherOrder.getQuantity().equals(this.getQuantity())
                && otherOrder.getDeliveryDate().equals(this.getDeliveryDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderInformation, orderStatus, price, quantity, deliveryDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getOrderInformation())
                .append(" Status: ")
                .append(getOrderStatus())
                .append(" Price: ")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Delivery Date: ")
                .append(getDeliveryDate());
        return builder.toString();
    }
}
```
###### /java/seedu/address/model/order/OrderInformation.java
``` java
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Order's information in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrderInformation(String)}
 */
public class OrderInformation {
    public static final String MESSAGE_ORDER_INFORMATION_CONSTRAINTS =
            "Order information should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String ORDER_INFORMATION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String orderInformation;

    /**
     * Constructs {@code OrderInformation}.
     *
     * @param orderInfo Valid order information.
     */
    public OrderInformation(String orderInfo) {
        requireNonNull(orderInfo);
        checkArgument(isValidOrderInformation(orderInfo), MESSAGE_ORDER_INFORMATION_CONSTRAINTS);
        this.orderInformation = orderInfo;
    }

    /**
     * Returns true if a given string is valid order information.
     */
    public static boolean isValidOrderInformation(String test) {
        requireNonNull(test);
        return test.matches(ORDER_INFORMATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return orderInformation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderInformation // instanceof handles nulls
                && this.orderInformation.equals(((OrderInformation) other).orderInformation)); // state check
    }

    @Override
    public int hashCode() {
        return orderInformation.hashCode();
    }
}
```
###### /java/seedu/address/model/order/OrderStatus.java
``` java
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents Order's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrderStatus(String)}
 */
public class OrderStatus {
    public static final String ORDER_STATUS_ONGOING = "ongoing";
    public static final String ORDER_STATUS_DONE = "done";

    public static final ArrayList<String> VALID_ORDER_STATUS = new ArrayList<>(
            Arrays.asList(ORDER_STATUS_ONGOING, ORDER_STATUS_DONE));

    public static final String MESSAGE_ORDER_STATUS_CONSTRAINTS = "Order status can only be "
            + ORDER_STATUS_ONGOING + " or " + ORDER_STATUS_DONE + ".";

    private String orderStatus;

    /**
     * Constructs a {@code OrderStatus}
     *
     * @param orderStatus a valid order status.
     */
    public OrderStatus(String orderStatus) {
        requireNonNull(orderStatus);
        checkArgument(isValidOrderStatus(orderStatus), MESSAGE_ORDER_STATUS_CONSTRAINTS);
        this.orderStatus = orderStatus;
    }

    /**
     * Returns true if a given string is a valid order status.
     */
    public static boolean isValidOrderStatus(String test) {
        requireNonNull(test);
        return VALID_ORDER_STATUS.contains(test);
    }

    /**
     * Returns the current order status.
     */
    public String getCurrentOrderStatus() {
        return this.orderStatus;
    }

    /**
     * Sets the current order status.
     */
    public void setCurrentOrderStatus(String newOrderStatus) {
        requireNonNull(newOrderStatus);
        if (isValidOrderStatus(newOrderStatus)) {
            this.orderStatus = newOrderStatus;
        }
    }

    @Override
    public String toString() {
        return orderStatus;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderStatus // instanceof handles nulls
                && this.getCurrentOrderStatus().equals(((OrderStatus) other).getCurrentOrderStatus())); // state check
    }

    @Override
    public int hashCode() {
        return orderStatus.hashCode();
    }
}
```
###### /java/seedu/address/model/order/Price.java
``` java
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
        import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Order's price in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {
    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price should only contain numeric characters, one decimal "
                    + "and at most two numeric characters after the decimal, "
                    + "and it should not be blank.\n"
                    + "Maximum value allowed for price is 1000000.00";

    public static final String PRICE_VALIDATION_REGEX = "[0-9]+([.][0-9]{1,2})?";

    private final String price;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        this.price = price;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        requireNonNull(test);

        if (!test.matches(PRICE_VALIDATION_REGEX) || Double.valueOf(test) > 1000000.00) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return price;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && this.price.equals(((Price) other).price)); // state check
    }

    @Override
    public int hashCode() {
        return price.hashCode();
    }
}
```
###### /java/seedu/address/model/order/Quantity.java
``` java
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Order's quantity in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {
    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Quantity should only contain numeric characters, and it should not be blank.\n"
                    + "Maximum value allowed for quantity is 1000000.";

    // Only positive integers are allowed
    public static final String QUANTITY_VALIDATION_REGEX = "^[0-9]*[1-9][0-9]*$";

    private final String quantity;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_QUANTITY_CONSTRAINTS);
        this.quantity = quantity;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidQuantity(String test) {
        requireNonNull(test);

        if (!test.matches(QUANTITY_VALIDATION_REGEX) || Integer.valueOf(test) > 1000000) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return quantity;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && this.quantity.equals(((Quantity) other).quantity)); // state check
    }

    @Override
    public int hashCode() {
        return quantity.hashCode();
    }
}

```
###### /java/seedu/address/model/order/UniqueOrderList.java
``` java
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.order.exceptions.OrderNotFoundException;

/**
 * A list of orders that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Order#equals(Object)
 */
public class UniqueOrderList implements Iterable<Order> {
    private final ObservableList<Order> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty OrderList.
     */
    public UniqueOrderList() {}

    /**
     * Creates a UniqueOrderList using given orders.
     * Enforces no nulls.
     */
    public UniqueOrderList(Set<Order> orders) {
        requireAllNonNull(orders);
        internalList.addAll(orders);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all orders in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Order> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the order {@code target} in the list with {@code editedOrder}.
     *
     * @throws DuplicateOrderException if the replacement is equivalent to another existing order in the list.
     * @throws OrderNotFoundException if {@code target} could not be found in the list.
     */
    public void setOrder(Order target, Order editedOrder)
            throws DuplicateOrderException, OrderNotFoundException {
        requireNonNull(editedOrder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        if (!target.equals(editedOrder) && internalList.contains(editedOrder)) {
            throw new DuplicateOrderException();
        }

        internalList.set(index, editedOrder);
    }

    /**
     * Replaces the Orders in this list with those in the argument order list.
     */
    public void setOrders(Set<Order> orders) {
        requireAllNonNull(orders);
        internalList.setAll(orders);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every order in the argument list exists in this object.
     */
    public void mergeFrom(UniqueOrderList from) {
        final Set<Order> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(order -> !alreadyInside.contains(order))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Order as the given argument.
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an Order to the list.
     *
     * @throws DuplicateOrderException if the Order to add is a duplicate of an existing Order in the list.
     */
    public void add(Order toAdd) throws DuplicateOrderException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateOrderException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes Order from list if it exists.
     */
    public void remove(Order toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
    }

    @Override
    public Iterator<Order> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueOrderList // instanceof handles nulls
                && this.internalList.equals(((UniqueOrderList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueOrderList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateOrderException extends DuplicateDataException {
        public DuplicateOrderException() {
            super("Operation would result in duplicate orders");
        }
    }

}
```
###### /java/seedu/address/model/theme/Theme.java
``` java
package seedu.address.model.theme;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Represents the current theme of the address book.
 */
public class Theme {
    public static final String DARK_THEME_KEYWORD = "dark";
    public static final String LIGHT_THEME_KEYWORD = "light";

    public static final ArrayList<String> VALID_THEMES = new ArrayList<>(
            Arrays.asList(DARK_THEME_KEYWORD, LIGHT_THEME_KEYWORD));

    public static final String MESSAGE_THEME_CONSTRAINTS = "Theme can only be "
            + DARK_THEME_KEYWORD + " or " + LIGHT_THEME_KEYWORD + ".";

    public static final String DARK_THEME_CSS_FILE_PATH = "view/DarkTheme.css";
    public static final String LIGHT_THEME_CSS_FILE_PATH = "view/LightTheme.css";

    private static String currentTheme;

    /**
     * Constructs a {@code currentTheme}.
     *
     * @param currentTheme a valid theme.
     */
    public Theme(String currentTheme) {
        requireNonNull(currentTheme);
        checkArgument(isValidTheme(currentTheme), MESSAGE_THEME_CONSTRAINTS);
        this.currentTheme = currentTheme;
    }

    /**
     * Returns true if the given string is a valid theme.
     */
    public static boolean isValidTheme(String test) {
        requireNonNull(test);
        return VALID_THEMES.contains(test);
    }

    /**
     * Returns the current theme.
     */
    public static String getCurrentTheme() {
        return currentTheme;
    }

    /**
     * Sets the current theme to the newTheme.
     */
    public static void setCurrentTheme(String newTheme) {
        requireNonNull(newTheme);
        if (isValidTheme(newTheme)) {
            currentTheme = newTheme;
        }
    }

    @Override
    public String toString() {
        return currentTheme;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Theme); // instanceof handles nulls
    }

    @Override
    public int hashCode() {
        return currentTheme.hashCode();
    }

    /**
     * Changes the current theme of the address book.
     */
    public static void changeTheme(Stage primaryStage, String newTheme) {
        if (isValidTheme(newTheme)) {
            Scene scene = primaryStage.getScene();

            // clear current styles
            scene.getStylesheets().clear();

            // new theme file path
            String newThemeCssFilePath;

            switch (newTheme) {
            case DARK_THEME_KEYWORD:
                newThemeCssFilePath = DARK_THEME_CSS_FILE_PATH;
                break;

            case LIGHT_THEME_KEYWORD:
                newThemeCssFilePath = LIGHT_THEME_CSS_FILE_PATH;
                break;

            default:
                newThemeCssFilePath = DARK_THEME_CSS_FILE_PATH;
            }

            scene.getStylesheets().add(newThemeCssFilePath);
            primaryStage.setScene(scene);
        }
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedOrder.java
``` java
package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * JAXB-friendly version of an Order.
 */
public class XmlAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    @XmlElement
    private String orderInformation;
    @XmlElement
    private String price;
    @XmlElement
    private String quantity;
    @XmlElement
    private String deliveryDate;

    /**
     * Constructs an XmlAdaptedOrder.
     */
    public XmlAdaptedOrder() {}

    /**
     * Constructs an {@code XmlAdaptedOrder} with the given order details.
     */
    public XmlAdaptedOrder(String orderInformation, String price, String quantity, String deliveryDate) {
        this.orderInformation = orderInformation;
        this.price = price;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
    }

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedOrder
     */
    public XmlAdaptedOrder(Order source) {
        orderInformation = source.getOrderInformation().toString();
        price = source.getPrice().toString();
        quantity = source.getQuantity().toString();
        deliveryDate = source.getDeliveryDate().toString();
    }

    /**
     * Converts the jaxb-friendly adapted order object into the model's Order object.
     *
     * @throws IllegalValueException if any data constraints are violated in the adapted order's fields.
     */
    public Order toModelType() throws IllegalValueException {
        if (this.orderInformation == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OrderInformation.class.getSimpleName()));
        }
        if (!OrderInformation.isValidOrderInformation(this.orderInformation)) {
            throw new IllegalValueException(OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);
        }
        final OrderInformation orderInformation = new OrderInformation(this.orderInformation);

        if (this.price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }

        if (!Price.isValidPrice(this.price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price price = new Price(this.price);

        if (this.quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }

        if (!Quantity.isValidQuantity(this.quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity quantity = new Quantity(this.quantity);

        if (this.deliveryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DeliveryDate.class.getSimpleName()));
        }

        if (!DeliveryDate.isValidDeliveryDate(this.deliveryDate)) {
            throw new IllegalValueException(DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);
        }
        final DeliveryDate deliveryDate = new DeliveryDate(this.deliveryDate);

        return new Order(orderInformation, price, quantity, deliveryDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedOrder)) {
            return false;
        }

        XmlAdaptedOrder otherOrder = (XmlAdaptedOrder) other;
        return Objects.equals(orderInformation, otherOrder.orderInformation)
                && Objects.equals(price, otherOrder.price)
                && Objects.equals(quantity, otherOrder.quantity)
                && Objects.equals(deliveryDate, otherOrder.deliveryDate);
    }
}
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Handles the Tab button pressed event by updating {@code CommandBox}'s text
     * field with the full command syntax based on {@code text} entered so far.
     */
    private void autocompleteCommand(String text) {
        ArrayList<String> commandSyntaxList = CommandSyntaxListUtil.getCommandSyntaxList();

        // get list of matches of the input entered so far
        List<String> autocompleteCommandList = commandSyntaxList.stream()
                .filter(s -> s.startsWith(text))
                .collect(Collectors.toList());

        // replace input in text field with first match
        if (!(autocompleteCommandList.isEmpty())) {
            replaceText(autocompleteCommandList.get(0));
        }
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    private void setTheme() {
        Theme.changeTheme(primaryStage, Theme.DARK_THEME_KEYWORD);
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Changes the theme of the application.
     */
    @FXML
    public void handleChangeTheme(ChangeThemeEvent event) {
        Theme.changeTheme(primaryStage, event.getTheme());
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleChangeTheme(event);
    }
```
###### /java/seedu/address/ui/OrderCard.java
``` java
package seedu.address.ui;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * A UI component that displays information of an {@code Order}.
 */
public class OrderCard extends UiPart<Region> {
    private static final String FXML = "OrderListCard.fxml";
    public final Order order;

    private final Logger logger = LogsCenter.getLogger(OrderCard.class);

    @FXML
    private HBox cardPane;

    @FXML
    private Label orderInformation;

    @FXML
    private Label orderStatus;

    @FXML
    private Label id;

    @FXML
    private Label priceAndQuantity;

    @FXML
    private Label totalPrice;

    @FXML
    private Label deliveryDate;

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        orderInformation.setText(order.getOrderInformation().toString());
        orderStatus.setText(order.getOrderStatus().getCurrentOrderStatus().toUpperCase());
        setPriceAndQuantity(order);
        setTotalPrice(order);
        deliveryDate.setText("Deliver By: " + order.getDeliveryDate().toString());
    }

    private void setPriceAndQuantity(Order order) {
        double priceValue = Double.valueOf(order.getPrice().toString());
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        priceAndQuantity.setText("S$" + String.valueOf(decimalFormat.format(priceValue))
                + " X " + order.getQuantity().toString());
    }

    private void setTotalPrice(Order order) {
        totalPrice.setText("Total: S$" + getTotalPrice(order.getPrice(), order.getQuantity()));
    }

    private String getTotalPrice(Price price, Quantity quantity) {
        double priceValue = Double.valueOf(price.toString());
        int quantityValue = Integer.valueOf(quantity.toString());

        double totalPrice = priceValue * quantityValue;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        return String.valueOf(decimalFormat.format(totalPrice));
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
                && order.equals(card.order);
    }
}
```
###### /java/seedu/address/ui/OrderListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ChangeOrderStatusEvent;
import seedu.address.commons.events.ui.OrderPanelSelectionChangedEvent;
import seedu.address.model.order.Order;

/**
 * Panel containing orders to be managed by the salesperson.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    @FXML
    private ListView<OrderCard> orderListView;

    public OrderListPanel(ObservableList<Order> orderList) {
        super(FXML);
        setConnections(orderList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Order> orderList) {
        ObservableList<OrderCard> mappedList = EasyBind.map(
                orderList, (order) -> new OrderCard(order, orderList.indexOf(order) + 1));
        orderListView.setItems(mappedList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
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
     * Scrolls to the {@code OrderCard} at {@code index} and selects it.
     * @param index index of order card to be scrolled to.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            orderListView.scrollTo(index);
            orderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @FXML
    private void handleChangeOrderStatus(ChangeOrderStatusEvent event) {
        // TODO: change background of listcell based on order status change
    }

    @Subscribe
    private void handleChangeOrderStatusEvent(ChangeOrderStatusEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleChangeOrderStatus(event);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<OrderCard> {

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
###### /resources/view/LightTheme.css
``` css
.background {
    -fx-background-color: derive(#cacaca, 20%);
    background-color: #e8e8e8; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #e8e8e8;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #cacaca;
    -fx-control-inner-background: #cacaca;
    -fx-background-color: #cacaca;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
            transparent
            transparent
            derive(-fx-base, 80%)
            transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #ffffff;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#cacaca, 20%);
    -fx-border-color: transparent transparent transparent #e8e8e8;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#cacaca, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#cacaca, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #e0e0e0;
}

.list-cell:filled:odd {
    -fx-background-color: #c2c2c2;
}

.list-cell:filled:selected {
    -fx-background-color: #8ebdcf;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #84b2c4;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.list-cell:empty {
    -fx-background-color: transparent;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #e8e8e8;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #e8e8e8;
}

.anchor-pane {
    -fx-background-color: derive(#cacaca, 20%);
}

.pane-with-border {
    -fx-background-color: derive(#cacaca, 20%);
    -fx-border-color: derive(#cacaca, 10%);
    -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#cacaca, 20%);
    -fx-text-fill: white;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: white !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#cacaca, 30%);
    -fx-border-color: derive(#cacaca, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#cacaca, 30%);
    -fx-border-color: derive(#cacaca, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#cacaca, 30%);
}

.context-menu {
    -fx-background-color: derive(#cacaca, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#cacaca, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #515151;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: white;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #1a1a1a;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #cacaca;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #1a1a1a;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: black;
    -fx-text-fill: #cacaca;
}

.button:focused {
    -fx-border-color: black, black;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #cacaca;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: black;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #cacaca;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #cacaca;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#cacaca, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: black;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: derive(#cacaca, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#cacaca, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #e8e8e8 transparent #e8e8e8;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #e8e8e8 #000000 #e8e8e8;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, white, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #e8e8e8, transparent, #e8e8e8;
    -fx-background-radius: 0;
}

#groups {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#groups .label {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}


#preferences {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#preferences .label {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#groups .teal {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
}

#groups .red {
    -fx-text-fill: black;
    -fx-background-color: red;
}

#groups .yellow {
    -fx-background-color: yellow;
    -fx-text-fill: black;
}

#groups .blue {
    -fx-text-fill: white;
    -fx-background-color: #2980B9;
}

#groups .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#groups .brown {
    -fx-text-fill: white;
    -fx-background-color: brown;
}

#groups .green {
    -fx-text-fill: black;
    -fx-background-color: #27AE60;
}

#groups .pink {
    -fx-text-fill: black;
    -fx-background-color: pink;
}

#groups .black {
    -fx-text-fill: white;
    -fx-background-color: black;
}

#groups .indigo {
    -fx-text-fill: black;
    -fx-background-color: #8E44AD;
}

#preferences .red {
    -fx-text-fill: black;
    -fx-background-color: red;
}

#preferences .yellow {
    -fx-background-color: yellow;
    -fx-text-fill: black;
}

#preferences .blue {
    -fx-text-fill: white;
    -fx-background-color: #2980B9;
}

#preferences .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#preferences .brown {
    -fx-text-fill: white;
    -fx-background-color: brown;
}

#groups .green {
    -fx-text-fill: black;
    -fx-background-color: #27AE60;
}

#preferences .pink {
    -fx-text-fill: black;
    -fx-background-color: pink;
}

#preferences .black {
    -fx-text-fill: white;
    -fx-background-color: black;
}

#preferences .indigo {
    -fx-text-fill: black;
    -fx-background-color: #8E44AD;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#cardPane #name, #cardPane #id, #cardPane #orderInformation, #cardPane #entryTitle {
    -fx-font-size: 15pt;
}

#personPanel #name {
    -fx-font-size: 40pt;
    -fx-text-fill: #000000;
}

#cardPane #tags .label {
    -fx-font-size: 12pt;
}

#personPanel #tags .label {
    -fx-font-size: 20pt;
}

#personPanel #address, #personPanel #email, #personPanel #phone {
+   -fx-font-size: 15pt;
    -fx-text-fill: #000000;
}

#personPanel .split-pane-divider {
    -fx-background-color: derive(#e8e8e8, 50%);
}
```
###### /resources/view/MainWindow.fxml
``` fxml
          <VBox fx:id="rightPanelList" minWidth="340" prefWidth="340" SplitPane.resizableWithParent="false">
            <padding>
              <Insets bottom="10" left="10" right="10" top="10" />
            </padding>
            <StackPane fx:id="rightPanelPlaceholder" VBox.vgrow="ALWAYS" />
          </VBox>
```
###### /resources/view/OrderListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="orderInformation" styleClass="cell_big_label" text="\$orderInformation" />
            </HBox>
            <Label fx:id="orderStatus" styleClass="cell_small_label" text="\$orderStatus" />
            <Label fx:id="priceAndQuantity" styleClass="cell_small_label" text="\$priceAndQuantity" />
            <Label fx:id="totalPrice" styleClass="cell_small_label" text="\$totalPrice" />
            <Label fx:id="deliveryDate" styleClass="cell_small_label" text="\$deliveryDate" />
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
    <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
    </padding>
</HBox>
```
###### /resources/view/OrderListPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="orderListView" VBox.vgrow="ALWAYS" />
</VBox>
```

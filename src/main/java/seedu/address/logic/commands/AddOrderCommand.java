//@@author amad-person
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
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.person.Person;

/**
 * Adds an order to a person in the address book.
 */
public class AddOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "orderadd";
    public static final String COMMAND_ALIAS = "oa";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " INDEX "
            + PREFIX_ORDER_INFORMATION + "ORDER_INFO "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_DELIVERY_DATE + "DELIVERY_DATE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an order to the selected person in the "
            + "address book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ORDER_INFORMATION + "ORDER_INFO "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_DELIVERY_DATE + "DELIVERY_DATE\n"
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
            model.addOrderToOrderList(orderToAdd);
        } catch (UniqueOrderList.DuplicateOrderException doe) {
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

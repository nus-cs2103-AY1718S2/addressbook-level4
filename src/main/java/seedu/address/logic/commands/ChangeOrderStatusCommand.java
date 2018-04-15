//@@author amad-person
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
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;

/**
 * Changes the order status of an existing order in the address book.
 */
public class ChangeOrderStatusCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "orderstatus";
    public static final String COMMAND_ALIAS = "os";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " INDEX " + PREFIX_ORDER_STATUS + "ORDER_STATUS";

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
        } catch (DuplicateOrderException doe) {
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

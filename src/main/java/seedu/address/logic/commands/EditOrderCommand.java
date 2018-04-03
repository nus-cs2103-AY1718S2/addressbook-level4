//@@author amad-person
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

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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

        // statc check
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

package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayOrderListEvent;

/**
 * List and display all orders in the address book to the user.
 */
//@@author SuxianAlicia
public class ListOrderCommand extends Command {
    public static final String COMMAND_WORD = "orderlist";
    public static final String COMMAND_ALIAS = "ol";

    public static final String MESSAGE_SUCCESS = "Listed all orders";


    @Override
    public CommandResult execute() {
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        EventsCenter.getInstance().post(new DisplayOrderListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

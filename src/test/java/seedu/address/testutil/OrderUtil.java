package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.model.order.Order;

/**
 * A utility class for Order.
 */
public class OrderUtil {

    /**
     * Returns an add order command string for adding the {@code order}.
     */
    public static String getAddOrderCommand(int index, Order order) {
        return AddOrderCommand.COMMAND_WORD + " " + index + " " + getOrderDetails(order);
    }

    /**
     * Returns the part of command string for the given {@code orders}'s details.
     */
    public static String getOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ORDER_INFORMATION + order.getOrderInformation().toString() + " ");
        sb.append(PREFIX_PRICE + order.getPrice().toString() + " ");
        sb.append(PREFIX_QUANTITY + order.getQuantity().toString() + " ");
        sb.append(PREFIX_DELIVERY_DATE + order.getDeliveryDate().toString());
        return sb.toString();
    }
}

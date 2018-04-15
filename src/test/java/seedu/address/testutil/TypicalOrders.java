//@@author amad-person
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_STATUS_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_STATUS_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_STATUS_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    public static final Order SHOES = new OrderBuilder()
            .withOrderInformation("Shoes")
            .withOrderStatus("ongoing")
            .withPrice("129.99")
            .withQuantity("3")
            .withDeliveryDate("10-09-2018")
            .build();

    public static final Order FACEWASH = new OrderBuilder()
            .withOrderInformation("Face Wash")
            .withOrderStatus("ongoing")
            .withPrice("24.75")
            .withQuantity("1")
            .withDeliveryDate("05-11-2018")
            .build();

    public static final Order BOOKS = new OrderBuilder()
            .withOrderInformation(VALID_ORDER_INFORMATION_BOOKS)
            .withOrderStatus(VALID_ORDER_STATUS_BOOKS)
            .withPrice(VALID_PRICE_BOOKS)
            .withQuantity(VALID_QUANTITY_BOOKS)
            .withDeliveryDate(VALID_DELIVERY_DATE_BOOKS)
            .build();

    public static final Order CHOCOLATES = new OrderBuilder()
            .withOrderInformation(VALID_ORDER_INFORMATION_CHOC)
            .withOrderStatus(VALID_ORDER_STATUS_CHOC)
            .withPrice(VALID_PRICE_CHOC)
            .withQuantity(VALID_QUANTITY_CHOC)
            .withDeliveryDate(VALID_DELIVERY_DATE_CHOC)
            .build();

    public static final Order COMPUTER = new OrderBuilder()
            .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
            .withOrderStatus(VALID_ORDER_STATUS_COMPUTER)
            .withPrice(VALID_PRICE_COMPUTER)
            .withQuantity(VALID_QUANTITY_COMPUTER)
            .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
            .build();

    public static final Order COMICBOOK = new OrderBuilder()
            .withOrderInformation("Comic Book")
            .withOrderStatus("ongoing")
            .withPrice("17.99")
            .withQuantity("1")
            .withDeliveryDate("01-01-2018")
            .build();

    private TypicalOrders() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with one person and all typical orders.
     */
    public static AddressBook getTypicalAddressBookWithOrders() {
        AddressBook ab = new AddressBook();

        try {
            ab.addPerson(ALICE);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("not possible");
        }

        for (Order order : getTypicalOrders()) {
            try {
                ab.addOrderToOrderList(order);
            } catch (DuplicateOrderException doe) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(SHOES, FACEWASH, BOOKS, CHOCOLATES));
    }
}

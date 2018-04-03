//@@author amad-person
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalOrders.BOOKS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.testutil.Assert;

public class XmlAdaptedOrderTest {
    private static final String INVALID_ORDER_INFORMATION = "Choc0l@t3s";
    private static final String INVALID_PRICE = "25.00.99";
    private static final String INVALID_QUANTITY = "-2";
    private static final String INVALID_DELIVERY_DATE = "50-12-2010";

    private static final String VALID_ORDER_INFORMATION = BOOKS.getOrderInformation().toString();
    private static final String VALID_PRICE = BOOKS.getPrice().toString();
    private static final String VALID_QUANTITY = BOOKS.getQuantity().toString();
    private static final String VALID_DELIVERY_DATE = BOOKS.getDeliveryDate().toString();

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        XmlAdaptedOrder order = new XmlAdaptedOrder(BOOKS);
        assertEquals(BOOKS, order.toModelType());
    }

    @Test
    public void toModelType_invalidOrderInformation_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(INVALID_ORDER_INFORMATION, VALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullOrderInformation_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(null, VALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OrderInformation.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, INVALID_PRICE,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, null,
                VALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_PRICE,
                INVALID_QUANTITY, VALID_DELIVERY_DATE);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_PRICE,
                null, VALID_DELIVERY_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidDeliveryDate_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_PRICE,
                VALID_QUANTITY, INVALID_DELIVERY_DATE);
        String expectedMessage = DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDeliveryDate_throwsIllegalValueException() {
        XmlAdaptedOrder order = new XmlAdaptedOrder(VALID_ORDER_INFORMATION, VALID_PRICE,
                VALID_QUANTITY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryDate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }
}

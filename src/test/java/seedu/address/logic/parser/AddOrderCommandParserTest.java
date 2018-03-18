package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_INFORMATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_CHOC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.testutil.OrderBuilder;

public class AddOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE);

    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Order expectedOrder = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_CHOC)
                .withPrice(VALID_PRICE_CHOC)
                .withQuantity(VALID_QUANTITY_CHOC)
                .withDeliveryDate(VALID_DELIVERY_DATE_CHOC)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE
                        + INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));

        // multiple order information strings - last order information string accepted
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased()
                        + ORDER_INFORMATION_DESC_BOOKS + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));

        // multiple prices - last price accepted
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased()
                        + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_BOOKS + PRICE_DESC_CHOC
                        + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));

        // multiple quantities - last quantity accepted
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased()
                        + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC
                        + QUANTITY_DESC_BOOKS + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));

        // multiple delivery dates - last delivery date accepted
        assertParseSuccess(parser, INDEX_FIRST_PERSON.getOneBased()
                        + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_BOOKS + DELIVERY_DATE_DESC_CHOC,
                new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing order information prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + VALID_ORDER_INFORMATION_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                MESSAGE_INVALID_FORMAT);

        // missing price prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + VALID_PRICE_CHOC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                MESSAGE_INVALID_FORMAT);

        // missing quantity prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + VALID_QUANTITY_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                MESSAGE_INVALID_FORMAT);

        // missing delivery date prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + VALID_DELIVERY_DATE_CHOC,
                MESSAGE_INVALID_FORMAT);

        // all prefixes missing
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + VALID_ORDER_INFORMATION_CHOC
                        + VALID_PRICE_CHOC + VALID_QUANTITY_CHOC
                        + VALID_DELIVERY_DATE_CHOC,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid order information
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + INVALID_ORDER_INFORMATION_DESC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + INVALID_PRICE_DESC + QUANTITY_DESC_CHOC
                        + DELIVERY_DATE_DESC_CHOC,
                Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + INVALID_QUANTITY_DESC
                        + DELIVERY_DATE_DESC_CHOC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // invalid delivery date
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + ORDER_INFORMATION_DESC_CHOC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + INVALID_DELIVERY_DATE_DESC,
                DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + INVALID_ORDER_INFORMATION_DESC
                        + PRICE_DESC_CHOC + QUANTITY_DESC_CHOC
                        + INVALID_DELIVERY_DATE_DESC,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + INDEX_FIRST_PERSON.getOneBased()
                + ORDER_INFORMATION_DESC_CHOC + PRICE_DESC_CHOC
                + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_CHOC, MESSAGE_INVALID_FORMAT);
    }
}

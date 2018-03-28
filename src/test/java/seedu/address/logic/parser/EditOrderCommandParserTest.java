package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_INFORMATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.testutil.EditOrderDescriptorBuilder;

public class EditOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE);

    private EditOrderCommandParser parser = new EditOrderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ORDER_INFORMATION_COMPUTER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditOrderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ORDER_INFORMATION_DESC_COMPUTER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ORDER_INFORMATION_DESC_COMPUTER, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 o/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ORDER_INFORMATION_DESC,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS); // invalid order information
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC, Price.MESSAGE_PRICE_CONSTRAINTS); // invalid price
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_DELIVERY_DATE_DESC,
                DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS); // invalid address

        // invalid order information followed by valid price
        assertParseFailure(parser, "1" + INVALID_ORDER_INFORMATION_DESC + PRICE_DESC_COMPUTER,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        // valid quantity followed by invalid quantity. The test case for invalid quantity followed by valid quantity
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + QUANTITY_DESC_COMPUTER + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC + INVALID_QUANTITY_DESC
                + VALID_ORDER_INFORMATION_COMPUTER, Price.MESSAGE_PRICE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = targetIndex.getOneBased() + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_COMPUTER)
                .withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + PRICE_DESC_BOOKS + DELIVERY_DATE_DESC_COMPUTER;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_BOOKS)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_ORDER;

        // order information
        String userInput = targetIndex.getOneBased() + ORDER_INFORMATION_DESC_CHOC;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_CHOC).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + PRICE_DESC_CHOC;
        descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_CHOC).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_CHOC;
        descriptor = new EditOrderDescriptorBuilder()
                .withQuantity(VALID_QUANTITY_CHOC).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // delivery date
        userInput = targetIndex.getOneBased() + DELIVERY_DATE_DESC_CHOC;
        descriptor = new EditOrderDescriptorBuilder()
                .withDeliveryDate(VALID_DELIVERY_DATE_CHOC).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = targetIndex.getOneBased() + ORDER_INFORMATION_DESC_CHOC + ORDER_INFORMATION_DESC_BOOKS
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_CHOC + PRICE_DESC_BOOKS + QUANTITY_DESC_COMPUTER
                + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_COMPUTER;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER).withPrice(VALID_PRICE_BOOKS)
                .withQuantity(VALID_QUANTITY_CHOC).withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
                .build();

        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ORDER;
        String userInput = targetIndex.getOneBased() + INVALID_QUANTITY_DESC + QUANTITY_DESC_CHOC;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withQuantity(VALID_QUANTITY_CHOC).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + PRICE_DESC_BOOKS + INVALID_QUANTITY_DESC + DELIVERY_DATE_DESC_COMPUTER
                + QUANTITY_DESC_BOOKS;
        descriptor = new EditOrderDescriptorBuilder()
                .withPrice(VALID_PRICE_BOOKS).withQuantity(VALID_QUANTITY_BOOKS)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

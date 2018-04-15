package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPLIT_METHOD_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPLIT_METHOD_DESC_PERCENTAGE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPLIT_METHOD_DESC_PERCENTAGE_THREE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPLIT_METHOD_DESC_PERCENTAGE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPLIT_METHOD_DESC_UNITS_ONE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPLIT_METHOD_DESC_UNITS_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TRANSACTION_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_DESC_AMOUNT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_DESC_DESCRIPTION_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_DESC_SPLIT_METHOD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_DESC_SPLIT_METHOD_THREE;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_DESC_SPLIT_METHOD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_PAYEE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_PAYER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_PAYER_DESC_THREE;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_PAYER_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_TYPE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TRANSACTION_TYPE_DESC_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRANSACTION_AMOUNT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRANSACTION_DESCRIPTION_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRANSACTION_PAYEE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRANSACTION_SPLIT_METHOD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRANSACTION_TYPE_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseTransactionFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.SplitMethod;
import seedu.address.model.transaction.TransactionType;
//@@author ongkc
public class AddTransactionCommandParserTest {
    private AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() throws CommandException, DuplicatePersonException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE);

        // missing Transaction type prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + VALID_TRANSACTION_TYPE_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);

        // missing payer prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + VALID_TRANSACTION_PAYEE_ONE
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);

        // missing amount prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + VALID_TRANSACTION_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);
        // missing description prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + VALID_TRANSACTION_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);
        // missing payee prefix
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + VALID_TRANSACTION_PAYEE_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                expectedMessage);

        // all prefix missing
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + VALID_TRANSACTION_SPLIT_METHOD_ONE
                        + VALID_TRANSACTION_PAYEE_ONE
                        + VALID_TRANSACTION_AMOUNT_ONE + VALID_TRANSACTION_DESCRIPTION_ONE
                        + VALID_TRANSACTION_PAYEE_ONE + VALID_TRANSACTION_SPLIT_METHOD_ONE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() throws CommandException, DuplicatePersonException {
        // invalid transaction type
        assertParseTransactionFailure(parser, INVALID_TRANSACTION_TYPE_DESC
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                TransactionType.MESSAGE_TRANSACTION_TYPE_CONSTRAINTS);
        // Payer or payee does not exist
        assertParseTransactionFailure(parser, TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC_TWO
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                AddTransactionCommand.MESSAGE_NONEXISTENT_PERSON);
        // Payer is payee
        assertParseTransactionFailure(parser, TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC_THREE
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                AddTransactionCommand.MESSAGE_PAYEE_IS_PAYER);
        // invalid amount
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + INVALID_AMOUNT_DESC + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                Amount.MESSAGE_AMOUNT_CONSTRAINTS);

        // invalid description
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + INVALID_DESCRIPTION_DESC
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid split method evenly
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + INVALID_SPLIT_METHOD_DESC_ONE,
                SplitMethod.MESSAGE_SPLIT_METHOD_CONSTRAINTS);
        // invalid split method percentage
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_TWO
                        + INVALID_SPLIT_METHOD_DESC_PERCENTAGE_ONE,
                "List of percentages can only take comma-separated integers");
        // invalid split method percentage
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_TWO
                        + INVALID_SPLIT_METHOD_DESC_PERCENTAGE_THREE,
                "The number of percentage values does not match the number of persons involved."
                        + " Remember to include the payer in the count.");
        // invalid split method percentage
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_TWO
                        + INVALID_SPLIT_METHOD_DESC_PERCENTAGE_TWO,
                "The sum of the percentages does not equal 100.");
        // invalid split method units
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_THREE
                        + INVALID_SPLIT_METHOD_DESC_UNITS_ONE,
                "List of units can only take comma-separated integers");
        // invalid split method units
        assertParseTransactionFailure(parser, PREAMBLE_WHITESPACE + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_THREE
                        + INVALID_SPLIT_METHOD_DESC_UNITS_TWO,
                "The number of units values does not match the number of persons involved."
                        + " Remember to include the payer in the count.");
        // two invalid values, only first invalid value reported
        assertParseTransactionFailure(parser, INVALID_TRANSACTION_TYPE_DESC
                        + TRANSACTION_PAYER_DESC
                        + INVALID_AMOUNT_DESC + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                TransactionType.MESSAGE_TRANSACTION_TYPE_CONSTRAINTS);
        // too many prefit for paydebt command
        assertParseTransactionFailure(parser, TRANSACTION_TYPE_DESC_THREE
                        + TRANSACTION_PAYER_DESC
                        + INVALID_AMOUNT_DESC + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                AddTransactionCommand.MESSAGE_TOO_MANY_PREFIXES_FOR_PAYDEBT);
        // non-empty preamble
        assertParseTransactionFailure(parser, PREAMBLE_NON_EMPTY + TRANSACTION_TYPE_DESC_ONE
                        + TRANSACTION_PAYER_DESC
                        + TRANSACTION_DESC_AMOUNT_ONE + TRANSACTION_DESC_DESCRIPTION_ONE
                        + TRANSACTION_PAYEE_DESC_ONE + TRANSACTION_DESC_SPLIT_METHOD_ONE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE));

    }

}

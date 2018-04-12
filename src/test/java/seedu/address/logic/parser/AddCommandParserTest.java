package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FAV;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FAV;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CoinBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Coin expectedCoin = new CoinBuilder().withName(VALID_NAME_BOS)
                .withTags(VALID_TAG_FAV).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOS
                + TAG_DESC_FAV, new AddCommand(expectedCoin));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMB + NAME_DESC_BOS
                + TAG_DESC_FAV, new AddCommand(expectedCoin));

        // multiple tags - all accepted
        Coin expectedCoinMultipleTags = new CoinBuilder().withName(VALID_NAME_BOS)
                .withTags(VALID_TAG_FAV, VALID_TAG_HOT).build();
        assertParseSuccess(parser, NAME_DESC_BOS
                + TAG_DESC_HOT + TAG_DESC_FAV, new AddCommand(expectedCoinMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Coin expectedCoin = new CoinBuilder().withName(VALID_NAME_AMB)
                .withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMB,
                new AddCommand(expectedCoin));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOS, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOS, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC
                + TAG_DESC_HOT + TAG_DESC_FAV, Code.MESSAGE_NAME_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOS
                + INVALID_TAG_DESC + VALID_TAG_FAV, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC, Code.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOS
                + TAG_DESC_HOT + TAG_DESC_FAV,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FAV;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FAV;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.TokenType.PREFIX_TAG;
import static seedu.address.testutil.TypicalTargets.INDEX_SECOND_COIN;
import static seedu.address.testutil.TypicalTargets.INDEX_THIRD_COIN;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TagCommand.EditCoinDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCoinDescriptorBuilder;

public class TagCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMB, TagCommand.MESSAGE_NOT_EDITED);

        // no field specified
        assertParseFailure(parser, "1", TagCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMB, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMB, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Coin} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FAV + TAG_DESC_HOT + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FAV + TAG_EMPTY + TAG_DESC_HOT, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FAV + TAG_DESC_HOT, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TAG_DESC + INVALID_TAG_DESC + VALID_TAG_FAV,
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_COIN;
        String userInput = targetIndex.getOneBased() + TAG_DESC_HOT + TAG_DESC_FAV;

        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder()
                .withTags(VALID_TAG_HOT, VALID_TAG_FAV).build();
        TagCommand expectedCommand = new TagCommand(new CommandTarget(targetIndex), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_COIN;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder().withTags().build();
        TagCommand expectedCommand = new TagCommand(new CommandTarget(targetIndex), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

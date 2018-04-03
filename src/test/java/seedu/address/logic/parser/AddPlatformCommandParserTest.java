package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACEBOOK_LINK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWITTER_LINK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWITTER_LINK_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPlatformCommand;
import seedu.address.model.smplatform.Link;

public class AddPlatformCommandParserTest {

    private static final String FACEBOOK_LINK_FIELD_AMY = " " + PREFIX_LINK + VALID_FACEBOOK_LINK_AMY;
    private static final String TWITTER_LINK_FIELD_AMY = " " + PREFIX_LINK + VALID_TWITTER_LINK_AMY;
    private static final String TWITTER_LINK_FIELD_BOB = " " + PREFIX_LINK + VALID_TWITTER_LINK_BOB;

    private static final String INVALID_LINK_1 = " " + PREFIX_LINK + "www.google.com";
    private static final String INVALID_LINK_2 = " " + PREFIX_LINK + "www.facebook.com";
    private static final String INVALID_LINK_3 = " " + PREFIX_LINK + "www.twitter.com";

    private static final String LINK_EMPTY = " " + PREFIX_LINK;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPlatformCommand.MESSAGE_USAGE);

    private AddPlatformCommandParser parser = new AddPlatformCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_FACEBOOK_LINK_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AddPlatformCommand.MESSAGE_LINK_COLLECTION_EMPTY);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + FACEBOOK_LINK_FIELD_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + FACEBOOK_LINK_FIELD_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_LINK_1, Link.MESSAGE_INVALID_LINK); // unrecognised link
        assertParseFailure(parser, "1" + INVALID_LINK_2, Link.MESSAGE_INVALID_LINK); // invalid facebook link
        assertParseFailure(parser, "1" + INVALID_LINK_3, Link.MESSAGE_INVALID_LINK); // invalid twitter link

        // valid facebook link followed by invalid facebook link
        assertParseFailure(parser, "1" + FACEBOOK_LINK_FIELD_AMY + INVALID_LINK_2, Link.MESSAGE_INVALID_LINK);
        // invalid facebook link followed by valid facebook link
        assertParseFailure(parser, "1" + INVALID_LINK_2 + FACEBOOK_LINK_FIELD_AMY, Link.MESSAGE_INVALID_LINK);

        // valid twitter link followed by invalid twitter link
        assertParseFailure(parser, "1" + TWITTER_LINK_FIELD_AMY + INVALID_LINK_3, Link.MESSAGE_INVALID_LINK);
        // invalid twitter link followed by valid twitter link
        assertParseFailure(parser, "1" + INVALID_LINK_3 + TWITTER_LINK_FIELD_AMY, Link.MESSAGE_INVALID_LINK);

        //multiple empty link fields
        assertParseFailure(parser, "1" + LINK_EMPTY + LINK_EMPTY, Link.MESSAGE_INVALID_LINK);
    }

    @Test
    public void parse_multipleLinksForSamePlatform_failure() {
        assertParseFailure(parser,
                "1" + TWITTER_LINK_FIELD_AMY + TWITTER_LINK_FIELD_BOB, Link.MESSAGE_LINK_CONSTRAINTS);
    }

    @Test
    public void parse_oneLinkFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + FACEBOOK_LINK_FIELD_AMY;
        Map<String, Link> linkMap = new HashMap<>();
        linkMap.put(Link.getLinkType(VALID_FACEBOOK_LINK_AMY), new Link(VALID_FACEBOOK_LINK_AMY));
        AddPlatformCommand expectedCommand = new AddPlatformCommand(targetIndex, linkMap);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + TWITTER_LINK_FIELD_AMY;
        linkMap.clear();
        linkMap.put(Link.getLinkType(VALID_TWITTER_LINK_AMY), new Link(VALID_TWITTER_LINK_AMY));
        expectedCommand = new AddPlatformCommand(targetIndex, linkMap);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleLinkFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + FACEBOOK_LINK_FIELD_AMY + TWITTER_LINK_FIELD_AMY;
        Map<String, Link> linkMap = new HashMap<>();
        linkMap.put(Link.getLinkType(VALID_FACEBOOK_LINK_AMY), new Link(VALID_FACEBOOK_LINK_AMY));
        linkMap.put(Link.getLinkType(VALID_TWITTER_LINK_AMY), new Link(VALID_TWITTER_LINK_AMY));
        AddPlatformCommand expectedCommand = new AddPlatformCommand(targetIndex, linkMap);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_clearPlatforms_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + LINK_EMPTY;

        AddPlatformCommand expectedCommand = new AddPlatformCommand(targetIndex, Collections.emptyMap());

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA_PLATFORM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.RemovePlatformCommand;
import seedu.address.model.smplatform.Link;

//@@author Nethergale
public class RemovePlatformCommandParserTest {

    private static String FACEBOOK_PLATFORM_FIELD = " " + PREFIX_SOCIAL_MEDIA_PLATFORM + Link.FACEBOOK_LINK_TYPE;
    private static String TWITTER_PLATFORM_FIELD = " " + PREFIX_SOCIAL_MEDIA_PLATFORM + Link.TWITTER_LINK_TYPE;

    private RemovePlatformCommandParser parser = new RemovePlatformCommandParser();
    private Set<String> platformSet = new HashSet<>();

    @Test
    public void parse_validArgsNoPlatformFieldsSpecified_returnsRemovePlatformCommand() {
        assertParseSuccess(parser, "1", new RemovePlatformCommand(INDEX_FIRST_PERSON, platformSet));
    }

    @Test
    public void parse_validArgsPlatformFieldsSpecified_returnsRemovePlatformCommand() {
        platformSet.add(Link.FACEBOOK_LINK_TYPE);
        platformSet.add(Link.TWITTER_LINK_TYPE);
        assertParseSuccess(parser, "1" + FACEBOOK_PLATFORM_FIELD + TWITTER_PLATFORM_FIELD,
                new RemovePlatformCommand(INDEX_FIRST_PERSON, platformSet));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePlatformCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
    //@@author jill858
    @Test
    public void parse_validArgs_returnsFindCommand() {

        //--------------------- Tests for name parameter --------------------------------------

        // no leading and trailing whitespaces
        FindCommand expectedFindNameCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindNameCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "n/Alice \n \t Bob  \t", expectedFindNameCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "n/  Alice \n \t Bob  \t", expectedFindNameCommand);

        //--------------------- Tests for tag parameter --------------------------------------

        FindCommand expectedFindTagCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("owesMoney", "friends")));

        assertParseSuccess(parser, "t/owesMoney friends", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "t/owesMoney\t friends  \t", expectedFindTagCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "t/   owesMoney\t friends  \t", expectedFindTagCommand);

        //--------------------- Tests for address parameter --------------------------------------

        FindCommand expectedFindAddressCommand =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("clementi", "avenue")));

        assertParseSuccess(parser, "a/clementi avenue", expectedFindAddressCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "a/clementi\t avenue  \t", expectedFindAddressCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "a/  clementi\t avenue  \t", expectedFindAddressCommand);

        //--------------------- Tests for phone parameter --------------------------------------

        FindCommand expectedFindPhoneCommand =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("94945000")));

        assertParseSuccess(parser, "p/94945000", expectedFindPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "p/94945000 \t", expectedFindPhoneCommand);
        assertParseSuccess(parser, "p/   94945000 \t", expectedFindPhoneCommand);
    }

}

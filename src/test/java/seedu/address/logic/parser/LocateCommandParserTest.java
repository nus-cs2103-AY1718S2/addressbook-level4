package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.LocateCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

public class LocateCommandParserTest {

    //@@author jonleeyz-reused
    private LocateCommandParser parser = new LocateCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LocateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSpecifier_throwsParseException() {
        //"-e"
        assertParseFailure(parser, "-z Alice Bob", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LocateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLocateCommand() {
        // no leading and trailing whitespaces
        LocateCommand expectedLocateCommand =
                new LocateCommand(new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedLocateCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedLocateCommand);

        //-all specifier
        expectedLocateCommand = new LocateCommand(new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-all Alice Bob", expectedLocateCommand);

        //-n specifier
        expectedLocateCommand = new LocateCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-n Alice Bob", expectedLocateCommand);

        //-p specifier
        expectedLocateCommand = new LocateCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-p Alice Bob", expectedLocateCommand);

        //-a specifier
        expectedLocateCommand = new LocateCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-a Alice Bob", expectedLocateCommand);

        //-t specifier
        expectedLocateCommand = new LocateCommand(new TagsContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "-t Alice Bob", expectedLocateCommand);

        //-e specifier
        expectedLocateCommand = new LocateCommand(new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com",
                "bob@example.com")));
        assertParseSuccess(parser, "-e alice@example.com bob@example.com", expectedLocateCommand);
    }
    //@@author
}

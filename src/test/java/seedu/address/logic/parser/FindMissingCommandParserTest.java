package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.commands.FindMissingCommand;
import seedu.address.logic.predicates.FindMissingPredicate;
import seedu.address.model.person.Person;

//@@author aussiroth
public class FindMissingCommandParserTest {

    private FindMissingCommandParser parser = new FindMissingCommandParser();

    @Test
    public void parse_validArgs_returnsFindMissingCommand() {
        //setup predicate correctly
        String[] keywords = {"email", "address"};
        Predicate<Person> finalPredicate = new FindMissingPredicate(Arrays.asList(keywords));
        FindMissingCommand expectedFindMissingCommand = new FindMissingCommand(finalPredicate);

        //single whitespace
        assertParseSuccess(parser, "email address", expectedFindMissingCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "email \t  address \t", expectedFindMissingCommand);
        //check case insensitive
        assertParseSuccess(parser, "eMAIl aDdReSS", expectedFindMissingCommand);

        //check parse if empty user input
        keywords = Arrays.copyOf(FindMissingCommand.ATTRIBUTE_VALUES, FindMissingCommand.ATTRIBUTE_VALUES.length);
        finalPredicate = new FindMissingPredicate(Arrays.asList(keywords));
        expectedFindMissingCommand = new FindMissingCommand(finalPredicate);
        assertParseSuccess(parser, "", expectedFindMissingCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedParseFailureMessage = FindMissingCommand.MESSAGE_INVALID_ATTRIBUTE;
        assertParseFailure(parser, "abcdefg", String.format(expectedParseFailureMessage, "abcdefg"));
        assertParseFailure(parser, "addres phon", String.format(expectedParseFailureMessage, "addres"));
    }
}

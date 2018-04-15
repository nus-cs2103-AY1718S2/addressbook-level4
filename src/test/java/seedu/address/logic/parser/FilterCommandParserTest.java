package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.KeywordPredicate;

//@@author dannyngmx94
public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new KeywordPredicate("Alice"));
        assertParseSuccess(parser, "Alice", expectedFilterCommand);

        // multiple whitespaces before and after keyword
        assertParseSuccess(parser, " \n Alice \n ", expectedFilterCommand);
    }

}

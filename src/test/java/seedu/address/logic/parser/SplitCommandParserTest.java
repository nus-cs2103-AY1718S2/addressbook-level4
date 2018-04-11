//@@author chenchongsong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SplitCommand;

public class SplitCommandParserTest {

    private static String validIndex = "1";
    private static String validIndices1 = "1 2 3";
    private static String validIndices2 = "1 1 1 2";
    private static String invalidIndex1 = "a";
    private static String invalidIndex2 = "0";
    private static String validBill1 = PREFIX_MONEY + "100.00";
    private static String validBill2 = PREFIX_MONEY + "-0.12";

    private SplitCommandParser parser = new SplitCommandParser();

    @Test
    public void parse_validArgsSingleIndex_returnsSplitCommand() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, validIndex + " " + validBill1,
                new SplitCommand(indices, 100.00));
    }

    @Test
    public void parse_validArgsMultipleIndex_returnsSplitCommand() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);
        indices.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, validIndices1 + " " + validBill2,
                new SplitCommand(indices, -0.12));

        // In this case, the first person would take 3/4 of that bill
        // and the second person would take 1/4 of that bill
        indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, validIndices2 + " " + validBill2,
                new SplitCommand(indices, -0.12));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, invalidIndex1 + " " + validBill1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));

        assertParseFailure(parser, invalidIndex2 + " " + validBill2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));
    }

}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

//@@author jordancjq-reused
public class RemarkCommandParserTest {

    private static final String REMARK_EMPTY = "";
    private static final String REMARK_NONEMPTY = "Some remark to test.";

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_success() throws Exception {
        // with remark
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(REMARK_NONEMPTY));
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + REMARK_NONEMPTY;
        System.out.println(userInput);
        assertParseSuccess(parser, userInput, expectedCommand);

        // without remark
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(REMARK_EMPTY));
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + REMARK_EMPTY;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // missing parameters
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);

        // missing index
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + REMARK_NONEMPTY, expectedMessage);
    }

}

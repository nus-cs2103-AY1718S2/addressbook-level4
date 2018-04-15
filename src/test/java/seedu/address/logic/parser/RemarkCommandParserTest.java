package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_AMY;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

//@@author sherlynng
public class RemarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE;

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, REMARK_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + PREFIX_REMARK + REMARK_AMY, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_REMARK + REMARK_AMY, MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

        // no index stated for editing remark
        assertParseFailure(parser, "edit", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validPreamble_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " edit";

        Remark remark = new Remark("");
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, remark, true);

        // edit remark
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + REMARK_AMY;

        Remark remark = new Remark(REMARK_AMY);
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, remark);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexFieldSpecifiedNoRemark_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;

        Remark remark = new Remark("");
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, remark);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

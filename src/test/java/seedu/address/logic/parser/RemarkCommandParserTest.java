package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

//@@author tiny767
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();
    private final String emptyRemark = "";
    private final String nonEmptyRemark = "some remark";

    @Test
    public void parse_validArgs_success() throws Exception {

        //has index, new remark is non-empty
        assertParseSuccess(parser, "1 r/"  + nonEmptyRemark,
            new RemarkCommand(Index.fromOneBased(1), new Remark(nonEmptyRemark)));

        //has index, new remark is empty i.e. delete remark
        assertParseSuccess(parser, "1 r/" + emptyRemark,
            new RemarkCommand(Index.fromOneBased(1), new Remark(emptyRemark)));
    }

    @Test
    public void parse_missingIndex_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        //no index
        assertParseFailure(parser, nonEmptyRemark , expectedMessage);

    }
}

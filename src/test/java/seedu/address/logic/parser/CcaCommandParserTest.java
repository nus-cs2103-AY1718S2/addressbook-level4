package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_CCA_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_CCA_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NO_CCA_POSITION_STATED;
import static seedu.address.logic.commands.CommandTestUtil.NO_CCA_STATED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_POSITION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CcaCommand;
import seedu.address.logic.commands.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

//@@author TeyXinHui
public class CcaCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CcaCommand.MESSAGE_USAGE);

    private CcaCommandParser parser = new CcaCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_CCA_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_CCA_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_CCA_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        //empty cca
        assertParseFailure(parser, "1" + EMPTY_CCA_DESC, MESSAGE_INVALID_FORMAT);
        //empty cca position
        assertParseFailure(parser, "1" + EMPTY_CCA_POSITION_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_oneFieldSpecified_failure() {
        //Cca position field not stated
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NO_CCA_POSITION_STATED;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        //Cca field not stated
        Index index = INDEX_SECOND_PERSON;
        String nextUserInput = index.getOneBased() + NO_CCA_STATED;
        assertParseFailure(parser, nextUserInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + VALID_CCA_DESC;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withCca(VALID_CCA, VALID_CCA_POSITION).build();
        CcaCommand expectedCommand = new CcaCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    //@@author
}

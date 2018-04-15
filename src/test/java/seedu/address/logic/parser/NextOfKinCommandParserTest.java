package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOK_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOK_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOK_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOK_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOK_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.Name.MESSAGE_NAME_CONSTRAINTS;
import static seedu.address.model.person.NextOfKin.MESSAGE_EMAIL_CONSTRAINTS;
import static seedu.address.model.person.NextOfKin.MESSAGE_PHONE_CONSTRAINTS;
import static seedu.address.model.person.NextOfKin.MESSAGE_REMARK_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPersonDescriptor;
import seedu.address.logic.commands.NextOfKinCommand;
import seedu.address.testutil.EditPersonDescriptorBuilder;

//@@author TeyXinHui
public class NextOfKinCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NextOfKinCommand.MESSAGE_USAGE);

    private NextOfKinCommandParser parser = new NextOfKinCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NOK_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid name
        assertParseFailure(parser, "1" + INVALID_NOK_NAME_DESC, MESSAGE_NAME_CONSTRAINTS);
        //invalid phone
        assertParseFailure(parser, "1" + INVALID_NOK_PHONE_DESC, MESSAGE_PHONE_CONSTRAINTS);
        //invalid email
        assertParseFailure(parser, "1" + INVALID_NOK_EMAIL_DESC, MESSAGE_EMAIL_CONSTRAINTS);
        //invalid remark
        assertParseFailure(parser, "1" + INVALID_NOK_REMARK_DESC, MESSAGE_REMARK_CONSTRAINTS);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + VALID_NOK_DESC;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withNextOfKin("Bob 98765433 email@gmail.com Father").build();
        NextOfKinCommand expectedCommand = new NextOfKinCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    //@@author

}

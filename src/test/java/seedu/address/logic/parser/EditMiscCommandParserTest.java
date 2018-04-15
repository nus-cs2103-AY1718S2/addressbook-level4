package seedu.address.logic.parser;
//@@author samuelloh

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGIES_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGIES_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALLERGIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOKNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOKPHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARKS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NOKNAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOKNAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOKPHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOKPHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARKS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditMiscCommand;
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.Remarks;
import seedu.address.testutil.EditMiscDescriptorBuilder;


public class EditMiscCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMiscCommand.MESSAGE_USAGE);

    private EditMiscCommandParser parser = new EditMiscCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ALLERGIES_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditMiscCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ALLERGIES_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ALLERGIES_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ALLERGIES_DESC,
                Allergies.MESSAGE_ALLERGIES_CONSTRAINTS); // invalid allergies
        assertParseFailure(parser, "1" + INVALID_NOKNAME_DESC,
                NextOfKinName.MESSAGE_NEXTOFKINNAME_CONSTRAINTS); // invalid next-of-kin name
        assertParseFailure(parser, "1" + INVALID_NOKPHONE_DESC,
                NextOfKinPhone.MESSAGE_NEXTOFKINPHONE_CONSTRAINTS); // invalid next-of-kin phone
        assertParseFailure(parser, "1" + INVALID_REMARKS_DESC,
                Remarks.MESSAGE_REMARKS_CONSTRAINTS); // invalid remarks

        // invalid allergies followed by valid remarks
        assertParseFailure(parser, "1" + INVALID_ALLERGIES_DESC + NOKNAME_DESC_AMY,
                Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);

        // valid allergies followed by invalid allergies. The test case for invalid allergies followed by
        // valid allergies is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ALLERGIES_DESC_AMY + INVALID_ALLERGIES_DESC,
                Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);


        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_ALLERGIES_DESC + INVALID_NOKNAME_DESC
                + REMARKS_DESC_AMY, Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ALLERGIES_DESC_AMY + NOKNAME_DESC_AMY
                + NOKPHONE_DESC_AMY + REMARKS_DESC_AMY;

        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_AMY).withNextOfKinName(VALID_NOKNAME_AMY)
                .withNextOfKinPhone(VALID_NOKPHONE_AMY).withRemarks(VALID_REMARKS_AMY).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ALLERGIES_DESC_AMY + NOKNAME_DESC_AMY;

        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_AMY).withNextOfKinName(VALID_NOKNAME_AMY).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // allergies
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + ALLERGIES_DESC_AMY;
        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_AMY).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin name
        userInput = targetIndex.getOneBased() + NOKNAME_DESC_AMY;
        descriptor = new EditMiscDescriptorBuilder().withNextOfKinName(VALID_NOKNAME_AMY).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin phone
        userInput = targetIndex.getOneBased() + NOKPHONE_DESC_AMY;
        descriptor = new EditMiscDescriptorBuilder().withNextOfKinPhone(VALID_NOKPHONE_AMY).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remarks
        userInput = targetIndex.getOneBased() + REMARKS_DESC_AMY;
        descriptor = new EditMiscDescriptorBuilder().withRemarks(VALID_REMARKS_AMY).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()  + ALLERGIES_DESC_AMY + NOKNAME_DESC_AMY + NOKPHONE_DESC_AMY
                + REMARKS_DESC_AMY + ALLERGIES_DESC_AMY + ALLERGIES_DESC_BOB + NOKNAME_DESC_BOB + NOKNAME_DESC_BOB
                + NOKPHONE_DESC_BOB + REMARKS_DESC_BOB;

        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_BOB).withNextOfKinName(VALID_NOKNAME_BOB)
                .withNextOfKinPhone(VALID_NOKPHONE_BOB).withRemarks(VALID_REMARKS_BOB)
                .build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_ALLERGIES_DESC + ALLERGIES_DESC_BOB;
        EditMiscCommand.EditMiscDescriptor descriptor = new EditMiscDescriptorBuilder()
                .withAllergies(VALID_ALLERGIES_BOB).build();
        EditMiscCommand expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + NOKNAME_DESC_BOB + INVALID_ALLERGIES_DESC + NOKPHONE_DESC_BOB
                + ALLERGIES_DESC_BOB;
        descriptor = new EditMiscDescriptorBuilder().withNextOfKinName(VALID_NOKNAME_BOB)
                .withAllergies(VALID_ALLERGIES_BOB)
                .withNextOfKinPhone(VALID_NOKPHONE_BOB).build();
        expectedCommand = new EditMiscCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }




}
//@@author

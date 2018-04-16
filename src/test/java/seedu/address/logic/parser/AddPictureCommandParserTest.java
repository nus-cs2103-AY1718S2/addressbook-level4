package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PIC_DESC_IMAGE_GREATER_THAN_5MB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PIC_DESC_NONIMAGE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PIC_DESC_NONIMAGE_WITH_IMAGE_FILETYPE;
import static seedu.address.logic.commands.CommandTestUtil.PICTURE_DESC_LOCAL_IMAGE;
import static seedu.address.logic.commands.CommandTestUtil.PICTURE_DESC_LOCAL_IMAGE_5MB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_JPG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPictureCommand;

//@@author dezhanglee
public class AddPictureCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPictureCommand.MESSAGE_USAGE);

    private AddPictureCommandParser parser = new AddPictureCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no index specified
        assertParseFailure(parser, VALID_LOCAL_IMAGE_JPG, MESSAGE_INVALID_FORMAT);

        //no picture specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        //no index or picture specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_LOCAL_IMAGE_JPG, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_LOCAL_IMAGE_JPG, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        //non-image file without image prefix
        assertParseFailure(parser, "1" + INVALID_PIC_DESC_NONIMAGE, MESSAGE_INVALID_FORMAT);
        //non-image file with image prefix
        assertParseFailure(parser, "1" + INVALID_PIC_DESC_NONIMAGE_WITH_IMAGE_FILETYPE,
                MESSAGE_INVALID_FORMAT);
        //image file but bigger than 5MB
        assertParseFailure(parser, "1" + INVALID_PIC_DESC_IMAGE_GREATER_THAN_5MB, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validLocalImage_success() {

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = "1" + PICTURE_DESC_LOCAL_IMAGE;
        AddPictureCommand expectedCommand = new AddPictureCommand(targetIndex, VALID_LOCAL_IMAGE_JPG);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws Exception {

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput;
        AddPictureCommand expectedCommand;

        //images for both fields valid
        userInput = targetIndex.getOneBased() + PICTURE_DESC_LOCAL_IMAGE_5MB + PICTURE_DESC_LOCAL_IMAGE;
        expectedCommand = new AddPictureCommand(targetIndex, VALID_LOCAL_IMAGE_JPG);
        assertParseSuccess(parser, userInput, expectedCommand);

        //image for first field invalid, second field valid
        userInput = targetIndex.getOneBased() + INVALID_PIC_DESC_NONIMAGE_WITH_IMAGE_FILETYPE
                + PICTURE_DESC_LOCAL_IMAGE;
        expectedCommand = new AddPictureCommand(targetIndex, VALID_LOCAL_IMAGE_JPG);
        assertParseSuccess(parser, userInput, expectedCommand);

        //image for first field valid, second field invalid
        userInput = targetIndex.getOneBased() + PICTURE_DESC_LOCAL_IMAGE
                + INVALID_PIC_DESC_NONIMAGE_WITH_IMAGE_FILETYPE;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

}

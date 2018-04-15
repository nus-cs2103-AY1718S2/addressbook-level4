package seedu.address.logic.parser;

//@@author samuelloh
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROFILEPICTUREPATH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PROFILEPICTUREPATH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROFILEPICTUREPATH_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.EditPictureCommand;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;

public class EditPictureCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPictureCommand.MESSAGE_USAGE);

    private EditPictureCommandParser parser = new EditPictureCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PROFILEPICTUREPATH_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, PREFIX_INDEX + "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_invalidIndex_failure() {
        //missing index prefix
        assertParseFailure(parser, "1" + PROFILEPICTUREPATH_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // negative index
        assertParseFailure(parser, PREFIX_INDEX + "-5" + PROFILEPICTUREPATH_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, PREFIX_INDEX +  "0" + PROFILEPICTUREPATH_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, PREFIX_INDEX + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, PREFIX_INDEX + "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser,  " " + PREFIX_INDEX + "1 " +  PREFIX_PICTURE_PATH
                        + INVALID_PROFILEPICTUREPATH_DESC, ProfilePicturePath.MESSAGE_PICTURE_CONSTRAINTS);
    }


}
//@@author

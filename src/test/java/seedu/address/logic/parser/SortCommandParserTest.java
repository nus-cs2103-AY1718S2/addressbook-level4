package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortByGradeCommand;
import seedu.address.logic.commands.SortByLocationCommand;
import seedu.address.logic.commands.SortByNameCommand;
import seedu.address.logic.commands.SortBySubjectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.Category;

//@@author olimhc
public class SortCommandParserTest {

    private static final Category CATEGORY_STUDENT = new Category("s");
    private static final Category CATEGORY_TUTOR =  new Category("t");

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "n c/s", new SortByNameCommand(CATEGORY_STUDENT));
        assertParseSuccess(parser, "n c/t", new SortByNameCommand(CATEGORY_TUTOR));

        assertParseSuccess(parser, "g c/s", new SortByGradeCommand(CATEGORY_STUDENT));
        assertParseSuccess(parser, "g c/t", new SortByGradeCommand(CATEGORY_TUTOR));

        assertParseSuccess(parser, "l c/s", new SortByLocationCommand(CATEGORY_STUDENT));
        assertParseSuccess(parser, "l c/t", new SortByLocationCommand(CATEGORY_TUTOR));

        assertParseSuccess(parser, "s c/s", new SortBySubjectCommand(CATEGORY_STUDENT));
        assertParseSuccess(parser, "s c/t", new SortBySubjectCommand(CATEGORY_TUTOR));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no sort type specified
        assertParseFailure(parser,  "   c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser,  "   c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        //no category prefix and category specified
        assertParseFailure(parser,  "n", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        //no category specified
        assertParseFailure(parser,  "n c/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid type of sort
        assertParseFailure(parser, "w c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        //invalid category
        assertParseFailure(parser, "w c/w", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        //invalid category prefix
        assertParseFailure(parser, "n z/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_KEYWORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ClassificationCommand;
import seedu.address.model.person.NameContainsClassificationPredicate;

//@@author wenhao53
public class ClassificationCommandParserTest {

    private ClassificationCommandParser parser = new ClassificationCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClassificationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsClassifcationCommand() {
        // no leading and trailing whitespaces
        ClassificationCommand expectedClassificationCommand =
                new ClassificationCommand(new NameContainsClassificationPredicate(
                        Arrays.asList("underweight", "overweight")));
        assertParseSuccess(parser, "underweight overweight", expectedClassificationCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n underweight \n \t overweight  \t", expectedClassificationCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "notAValidKeyword", String.format(MESSAGE_INVALID_KEYWORD,
                ClassificationCommand.MESSAGE_USAGE));
    }



}

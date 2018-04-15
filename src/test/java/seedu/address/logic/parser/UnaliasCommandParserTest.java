package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_DESC_ADD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNALIAS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNALIAS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.model.alias.Alias;

//@@author jingyinno
public class UnaliasCommandParserTest {

    private UnaliasCommandParser parser = new UnaliasCommandParser();
    private String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE);

    @Test
    public void parse_compulsoryArgumentMissing_failure() {

        //missing argument
        String noArgumentCommand = "";
        assertParseFailure(parser, noArgumentCommand, message);
    }

    @Test
    public void parse_unalias_success() {
        assertParseSuccess(parser, VALID_UNALIAS, new UnaliasCommand(VALID_UNALIAS));
    }

    @Test
    public void parse_unaliasMultipleArgs_failure() {
        assertParseFailure(parser, ALIAS_DESC_ADD, message);
    }


    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_UNALIAS_DESC, Alias.MESSAGE_ALIAS_CONSTRAINTS);
    }
}

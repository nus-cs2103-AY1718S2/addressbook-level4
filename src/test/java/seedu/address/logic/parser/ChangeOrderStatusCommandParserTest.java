//@@author amad-person
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_DONE;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_ONGOING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;

import org.junit.Test;

import seedu.address.logic.commands.ChangeOrderStatusCommand;

public class ChangeOrderStatusCommandParserTest {

    private ChangeOrderStatusCommandParser parser = new ChangeOrderStatusCommandParser();

    @Test
    public void parse_validArgs_returnsChangeOrderStatusThemeCommand() {
        String userInput = "1 " + PREFIX_ORDER_STATUS.toString() + " " + ORDER_STATUS_DONE;
        assertParseSuccess(parser, userInput, new ChangeOrderStatusCommand(INDEX_FIRST_ORDER, ORDER_STATUS_DONE));

        userInput = "2 " + PREFIX_ORDER_STATUS.toString() + " " + ORDER_STATUS_ONGOING;
        assertParseSuccess(parser, userInput, new ChangeOrderStatusCommand(INDEX_SECOND_ORDER, ORDER_STATUS_ONGOING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeOrderStatusCommand.MESSAGE_USAGE));
    }
}

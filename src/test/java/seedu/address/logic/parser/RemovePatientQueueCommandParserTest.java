package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RemovePatientQueueCommand;

public class RemovePatientQueueCommandParserTest {
    private RemovePatientQueueCommandParser parser = new RemovePatientQueueCommandParser();

    @Test
    public void parse_emptyArg_returnsRemovePatientQueueCommand() {
        RemovePatientQueueCommand expectedCommand = new RemovePatientQueueCommand();
        assertParseSuccess(parser, "", expectedCommand);
    }
}

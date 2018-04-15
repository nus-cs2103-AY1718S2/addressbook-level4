package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandCorrection;

public class CommandCorrectionTest {

    private static final String FIRST_INDEX = "1";

    private static final String DELETE_COMMAND_WITH_ARGUMENTS = DeleteCommand.COMMAND_WORD + " " + FIRST_INDEX;
    private static final String SELECT_COMMAND_WITH_ARGUMENTS = SelectCommand.COMMAND_WORD + " " + FIRST_INDEX;

    @Before
    public void setUp() {

    }

    @Test
    public void extractCommandWord() {
        // Extracts and returns the one command word
        assertEquals(CommandCorrection.extractCommandWord(DELETE_COMMAND_WITH_ARGUMENTS), DeleteCommand.COMMAND_WORD);
        assertEquals(CommandCorrection.extractCommandWord(SELECT_COMMAND_WITH_ARGUMENTS), SelectCommand.COMMAND_WORD);
    }

}

package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.util.UiUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

//@@author Ang-YC
public class RealTimeParserTest {

    private Logic logic;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        logic = new LogicManager(model);
    }

    @Test
    public void parseExit() {
        Command command = logic.parse("exit");
        assertEquals(command.getParsedResult(), ExitCommand.PARSED_RESULT);
    }

    @Test
    public void parseInterview() {
        String timeNow = UiUtil.formatDate(LocalDateTime.now());
        Command command = logic.parse("interview 1 " + timeNow);

        String expectedResult = String.format(InterviewCommand.PARSED_RESULT, timeNow);
        assertEquals(command.getParsedResult(), expectedResult);
    }
}

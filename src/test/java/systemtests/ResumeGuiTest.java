package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Rule;
import org.junit.Test;

import guitests.GuiRobot;
import javafx.geometry.VerticalDirection;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.MaximizeAppRequestEvent;
import seedu.address.commons.events.ui.ShowPanelRequestEvent;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.testutil.EventsUtil;
import seedu.address.ui.InfoPanel;
import seedu.address.ui.PdfPanel;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author Ang-YC
public class ResumeGuiTest extends AddressBookSystemTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void testResume() {
        // Maximize first to show resume button
        EventsUtil.postNow(new MaximizeAppRequestEvent());

        // Select first person
        String command = SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        executeCommandWaitForUi(command);


        // ===== Cancel resume loading =====
        Button infoSideButtonResume = guiRobot.lookup("#infoSideButtonResume").query();
        guiRobot.clickOn(infoSideButtonResume, MouseButton.PRIMARY);
        validateShowPanel(PdfPanel.PANEL_NAME);

        Button resumeCancelButton = guiRobot.lookup("#resumeCancelButton").query();
        guiRobot.waitForEvent(resumeCancelButton::isVisible);
        guiRobot.clickOn(resumeCancelButton, MouseButton.PRIMARY);

        validateShowPanel(InfoPanel.PANEL_NAME);


        // ===== Let resume load + Scroll =====
        guiRobot.clickOn(infoSideButtonResume, MouseButton.PRIMARY);
        validateShowPanel(PdfPanel.PANEL_NAME);

        ScrollPane resumePane = guiRobot.lookup("#resumePane").query();
        guiRobot.waitForEvent(resumePane::isVisible);

        guiRobot.clickOn(resumePane, MouseButton.PRIMARY);
        guiRobot.scroll(VerticalDirection.UP);

        // Unmaximize to trigger resize
        EventsUtil.postNow(new MaximizeAppRequestEvent());


        // ===== Exit using ESC =====
        guiRobot.push(KeyCode.ESCAPE);
        validateShowPanel(InfoPanel.PANEL_NAME);

        // What is Codacy doing, only know how to complaint but didn't analyze properly
        // that I do have assert inside the validateShowPanel function, smh
        assertTrue(resumePane.isPannable());
        // This line is written just for the sake of Codacy
        // Why can't Codacy just do proper analysis so that I don't need to do extra work and keep pushing?
    }

    /**
     * Validate the event requested to show panel
     * @param requestedPanel to be shown
     */
    private void validateShowPanel(String requestedPanel) {
        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(baseEvent instanceof ShowPanelRequestEvent);
        ShowPanelRequestEvent showPanelRequestEvent = (ShowPanelRequestEvent) baseEvent;
        assertTrue(showPanelRequestEvent.getRequestedPanel().equals(requestedPanel));
    }
}

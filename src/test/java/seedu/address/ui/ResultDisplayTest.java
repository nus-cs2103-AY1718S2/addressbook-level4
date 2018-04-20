package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

public class ResultDisplayTest extends GuiUnitTest {

    private static final NewResultAvailableEvent NEW_RESULT_EVENT_SUCCESS =
            new NewResultAvailableEvent("Success", false);

    private static final NewResultAvailableEvent NEW_RESULT_EVENT_ERROR =
            new NewResultAvailableEvent("Error", true);

    private List<String> messageDefaultStyleClasses;
    private List<String> messageErrorStyleClasses;

    private ResultDisplayHandle resultDisplayHandle;

    @Before
    public void setUp() {
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));

        //@@author Kyholmes-reused
        //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
        messageDefaultStyleClasses = new ArrayList<>(resultDisplayHandle.getStyleClass());
        messageErrorStyleClasses = new ArrayList<>(messageDefaultStyleClasses);
        messageErrorStyleClasses.add(ResultDisplay.ERROR_STYLE_CLASS);
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", resultDisplayHandle.getText());
        assertEquals(messageDefaultStyleClasses, resultDisplayHandle.getStyleClass());

        //new results received
        assertResultDisplay(NEW_RESULT_EVENT_SUCCESS);
        assertResultDisplay(NEW_RESULT_EVENT_ERROR);

    }

    //@@author Kyholmes-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
    /**
     * check if the event message and message text color is same as the expected one
     * @param event
     */
    private void assertResultDisplay(NewResultAvailableEvent event) {
        postNow(event);
        guiRobot.pauseForHuman();

        List<String> expectedStyleClass = event.isError ? messageErrorStyleClasses : messageDefaultStyleClasses;

        assertEquals(event.message, resultDisplayHandle.getText());
        assertEquals(expectedStyleClass, resultDisplayHandle.getStyleClass());
    }
}

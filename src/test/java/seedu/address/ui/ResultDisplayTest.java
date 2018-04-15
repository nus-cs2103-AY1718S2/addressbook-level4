package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

public class ResultDisplayTest extends GuiUnitTest {

    private static final NewResultAvailableEvent NEW_RESULT_EVENT_STUB = new NewResultAvailableEvent("Stub", false);
    private static final NewResultAvailableEvent NEW_RESULT_ERROR_STUB = new NewResultAvailableEvent("Stub", true);

    private ResultDisplayHandle resultDisplayHandle;

    private ArrayList<String> defaultStyleOfResultDisplay;
    private ArrayList<String> errorStyleOfResultDisplay;

    @Before
    public void setUp() {
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));

        defaultStyleOfResultDisplay = new ArrayList<>(resultDisplayHandle.getStyleClass());

        errorStyleOfResultDisplay = new ArrayList<>(defaultStyleOfResultDisplay);
        errorStyleOfResultDisplay.add(ResultDisplay.ERROR_STYLE_CLASS);
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", resultDisplayHandle.getText());
        assertEquals(defaultStyleOfResultDisplay, resultDisplayHandle.getStyleClass());

        // new result received
        assertMessageAndStyleCorrect(NEW_RESULT_EVENT_STUB, defaultStyleOfResultDisplay);
        assertMessageAndStyleCorrect(NEW_RESULT_ERROR_STUB, errorStyleOfResultDisplay);
    }

    /**
     * Posts given event and verifies that the <br>
     *     - the text is the expected message <br>
     *     - the style is the same as {@code expectedStyle}.
     * @param event
     * @param expectedStyle
     */
    private void assertMessageAndStyleCorrect(NewResultAvailableEvent event, ArrayList<String> expectedStyle) {
        postNow(event);
        guiRobot.pauseForHuman();
        assertEquals(event.message, resultDisplayHandle.getText());
        assertEquals(expectedStyle, resultDisplayHandle.getStyleClass());
    }
}

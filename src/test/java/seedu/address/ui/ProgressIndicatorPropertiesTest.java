//@@author nhatquang3112
package seedu.address.ui;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class ProgressIndicatorPropertiesTest {
    private static final String EXPECTED_PROGRESS_INDICATOR_LABEL_NAME = "TO-DO COMPLETION";
    private static final String EXPECTED_PROGRESS_INDICATOR_LABEL_COLOR = "-fx-text-fill: black;";
    private static final String EXPECTED_PROGRESS_INDICATOR_COLOR = "-fx-progress-color: #4DA194;";
    private static final int EXPECTED_PROGRESS_INDICATOR_WIDTH = 150;
    private static final int EXPECTED_PROGRESS_INDICATOR_HEIGHT = 150;

    @Test
    public void checkIfAllPropertiesAreCorrect() {
        ProgressIndicatorProperties properties = new ProgressIndicatorProperties();

        assertTrue(properties.PROGRESS_INDICATOR_LABEL_NAME.equals(EXPECTED_PROGRESS_INDICATOR_LABEL_NAME));
        assertTrue(properties.PROGRESS_INDICATOR_LABEL_COLOR.equals(EXPECTED_PROGRESS_INDICATOR_LABEL_COLOR));
        assertTrue(properties.PROGRESS_INDICATOR_COLOR.equals(EXPECTED_PROGRESS_INDICATOR_COLOR));
        assertTrue(properties.PROGRESS_INDICATOR_WIDTH == EXPECTED_PROGRESS_INDICATOR_WIDTH);
        assertTrue(properties.PROGRESS_INDICATOR_HEIGHT == EXPECTED_PROGRESS_INDICATOR_HEIGHT);
    }
}

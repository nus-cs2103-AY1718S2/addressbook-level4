package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

//@@author Ang-YC
public class UiUtilTest {

    private static final int MIN_DURATION = 1;
    private static final int MAX_DURATION = 100;

    private EventHandler<ActionEvent> eventHandler = (EventHandler<ActionEvent>) event -> { };
    private Pane invisiblePane = new Pane();
    private Pane visiblePane = new Pane();

    @Before
    public void setUp() {
        invisiblePane.setOpacity(0);
        visiblePane.setOpacity(1);
    }

    @Test
    public void fadeNode_fadeOut() {
        FadeTransition transition;

        // From visible
        transition = (FadeTransition) UiUtil.fadeNode(visiblePane, false, MAX_DURATION, eventHandler);
        assertTrue(doubleEqual(transition.getFromValue(), visiblePane.getOpacity()));
        assertTrue(doubleEqual(transition.getDuration().toMillis(), MAX_DURATION));
        assertOtherConstant(transition, false);

        // From invisible
        transition = (FadeTransition) UiUtil.fadeNode(invisiblePane, false, MAX_DURATION, eventHandler);
        assertTrue(doubleEqual(transition.getFromValue(), invisiblePane.getOpacity()));
        assertTrue(doubleEqual(transition.getDuration().toMillis(), MIN_DURATION));
        assertOtherConstant(transition, false);
    }

    @Test
    public void fadeNode_fadeIn() {
        FadeTransition transition;

        // From visible
        transition = (FadeTransition) UiUtil.fadeNode(visiblePane, true, MAX_DURATION, eventHandler);
        assertTrue(doubleEqual(transition.getFromValue(), visiblePane.getOpacity()));
        assertTrue(doubleEqual(transition.getDuration().toMillis(), MIN_DURATION));
        assertOtherConstant(transition, true);

        // From invisible
        transition = (FadeTransition) UiUtil.fadeNode(invisiblePane, true, MAX_DURATION, eventHandler);
        assertTrue(doubleEqual(transition.getFromValue(), invisiblePane.getOpacity()));
        assertTrue(doubleEqual(transition.getDuration().toMillis(), MAX_DURATION));
        assertOtherConstant(transition, true);
    }

    @Test
    public void colorToHex() {
        Color white = new Color(1, 1, 1, 0);
        String hex = UiUtil.colorToHex(white);
        String expectedWhite = "#FFFFFF";

        assertTrue(hex.equals(expectedWhite));
    }

    @Test
    public void toFixed() {
        // From double
        assertTrue(UiUtil.toFixed(1.23, 5).equals("1.23000"));

        // Cut down
        assertTrue(UiUtil.toFixed("1.23456789", 2).equals("1.23"));

        // Padding right
        assertTrue(UiUtil.toFixed("1.23", 5).equals("1.23000"));

        // Padding left
        assertTrue(UiUtil.toFixed(".01", 2).equals("0.01"));
    }

    @Test
    public void formatDate() {
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(1540814400L, 0, ZoneOffset.UTC);
        assertTrue(UiUtil.formatDate(dateTime).equals("29 Oct 2018 12:00:00pm"));
    }

    /**
     * Assert other information
     * @param transition to be assert
     * @param fadeIn If set, fade in
     */
    private void assertOtherConstant(FadeTransition transition, boolean fadeIn) {
        Interpolator interpolator = fadeIn ? Interpolator.EASE_IN : Interpolator.EASE_OUT;
        double toValue = fadeIn ? 1 : 0;

        assertTrue(doubleEqual(transition.getToValue(), toValue));
        assertTrue(transition.getCycleCount() == 1);
        assertFalse(transition.isAutoReverse());
        assertTrue(transition.getInterpolator().equals(interpolator));
    }

    /**
     * Due to the floating point error, we use this method to compare double
     * @param first double
     * @param second double
     * @return true if equal (different by 0.001)
     */
    private boolean doubleEqual(double first, double second) {
        return (Math.abs(first - second) < 1e-3);
    }
}

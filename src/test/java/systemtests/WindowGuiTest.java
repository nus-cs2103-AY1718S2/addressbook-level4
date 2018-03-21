package systemtests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.MainWindowHandle;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseButton;
import seedu.address.commons.events.ui.MaximizeAppRequestEvent;
import seedu.address.testutil.EventsUtil;

public class WindowGuiTest extends AddressBookSystemTest {

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void testDraggableTitleBar() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Point2D originalPosition = mainWindowHandle.getWindowPosition();
        Point2D expectedDelta = new Point2D(50, 50);
        Point2D dragFrom = mainWindowHandle.getTitleBarPosition();
        Point2D dragTo = dragFrom.add(expectedDelta);

        // Drag it!
        guiRobot.moveTo(dragFrom);
        guiRobot.press(MouseButton.PRIMARY);
        guiRobot.drag(dragTo);
        guiRobot.release(MouseButton.PRIMARY);
        guiRobot.pauseForHuman();

        Point2D newPosition = mainWindowHandle.getWindowPosition();
        Point2D delta = newPosition.subtract(originalPosition);

        assertTrue(delta.equals(expectedDelta));
    }

    @Test
    public void testDoubleClickMaximize() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Point2D clickPosition = mainWindowHandle.getTitleBarPosition();

        // Double click it
        guiRobot.doubleClickOn(clickPosition, MouseButton.PRIMARY);
        guiRobot.pauseForHuman();

        assertWindowMaximized();
    }

    @Test
    public void testUnMaximize() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Rectangle2D originalBound = mainWindowHandle.getWindowBound();

        // Maximize
        EventsUtil.postNow(new MaximizeAppRequestEvent());
        assertWindowMaximized();

        // UnMaximize
        EventsUtil.postNow(new MaximizeAppRequestEvent());

        Rectangle2D newBound = mainWindowHandle.getWindowBound();
        assertTrue(originalBound.equals(newBound));
    }

    @Test
    public void testResizableWindow() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Rectangle2D originalBound = mainWindowHandle.getWindowBound();
        Point2D expectedDelta = new Point2D(50, 50);
        Point2D dragFrom = mainWindowHandle.getResizablePosition();
        Point2D dragTo = dragFrom.add(expectedDelta);

        guiRobot.moveTo(dragFrom);
        guiRobot.press(MouseButton.PRIMARY);
        guiRobot.drag(dragTo);
        guiRobot.release(MouseButton.PRIMARY);
        guiRobot.pauseForHuman();

        Rectangle2D newBound = mainWindowHandle.getWindowBound();
        Point2D delta = new Point2D(newBound.getWidth() - originalBound.getWidth(),
                newBound.getHeight() - originalBound.getHeight());

        assertTrue(delta.equals(expectedDelta));
    }

    @Test
    public void testSplitPaneResponsive() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        double originalWidth = mainWindowHandle.getListPaneWidth();

        // Maximize
        EventsUtil.postNow(new MaximizeAppRequestEvent());
        assertWindowMaximized();
        guiRobot.pauseForHuman();

        // Allow floating point error
        assertTrue(Math.abs(mainWindowHandle.getListPaneWidth() - originalWidth) <= 1);

        // Reset size and position
        mainWindowHandle.setWindowDefaultPositionAndSize();
        guiRobot.pauseForHuman();

        // Allow floating point error
        assertTrue(Math.abs(mainWindowHandle.getListPaneWidth() - originalWidth) <= 1);
    }

    /**
     * Asserts that the window is maximized
     */
    private void assertWindowMaximized() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Rectangle2D windowBound = mainWindowHandle.getWindowBound();
        Rectangle2D screenBound = mainWindowHandle.getSceenBound();

        assertTrue(windowBound.equals(screenBound));
    }
}

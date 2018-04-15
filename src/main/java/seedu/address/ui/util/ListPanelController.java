package seedu.address.ui.util;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

//@@author olimhc
/**
 * Stores the type of list being displayed
 */
public class ListPanelController {
    private static final Logger logger = LogsCenter.getLogger(ListPanelController.class);
    private static ListPanelController instance = null;

    /**
     * An enum to store which the type of list displayed
     */
    public enum DisplayType {
        closedList, activeList
    }

    /**
     * Ensure that the active client list is always shown first
     */
    private static DisplayType currentlyDisplayed = DisplayType.activeList;

    public DisplayType getCurrentListDisplayed() {
        return currentlyDisplayed;
    }

    /**
     * Switch the current display
     */
    public void switchDisplay() {
        switch (currentlyDisplayed) {
        case activeList:
            currentlyDisplayed = DisplayType.closedList;
            logger.fine("Switching display to closed client list.");
            break;

        case closedList:
            currentlyDisplayed = DisplayType.activeList;
            logger.fine("Switching display to active client list.");
            break;

        default:
            throw new AssertionError("This should not be possible.");
        }
    }

    /**
     * Reset the display to its default mode showing active client list.
     */
    public void setDefault() {
        if (!isCurrentDisplayActiveList()) {
            switchDisplay();
        }
    }

    /**
     * @return true if displayed list is active list
     */
    public static boolean isCurrentDisplayActiveList() {
        if (currentlyDisplayed == DisplayType.activeList) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ensure that only one instance of ListPanelController is created
     * @return instance
     */
    public static ListPanelController getInstance() {
        if (instance == null) {
            instance = new ListPanelController();
        }

        return instance;
    }
}

//@@author ewaldhew
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code NotificationsWindow} of the application.
 */
public class NotificationsWindowHandle extends StageHandle {

    public static final String NOTIFICATIONS_WINDOW_TITLE = "Notifications";

    private static final String NOTIFICATIONS_WINDOW_PANEL_ID = "#ruleListPanelPlaceholder";

    public NotificationsWindowHandle(Stage stage) {
        super(stage);
    }

    /**
     * Returns true if the window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(NOTIFICATIONS_WINDOW_TITLE);
    }

}

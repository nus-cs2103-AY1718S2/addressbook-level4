//@@author ifalluphill-reused
//{Based on HelpWindowHandle}

package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code ErrorLog} of the application.
 */
public class ErrorLogHandle extends StageHandle {

    public static final String ERROR_LOG_TITLE = "Error Log";

    private static final String ERROR_LOG_BROWSER_ID = "#browser";

    public ErrorLogHandle(Stage errorLogStage) {
        super(errorLogStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(ERROR_LOG_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(ERROR_LOG_BROWSER_ID));
    }
}

//@@author

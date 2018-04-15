package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code BrowserWindow} of the application.
 */
public class BrowserWindowHandle extends StageHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    private static final String HELP_WINDOW_BROWSER_ID = "#browser";

    public BrowserWindowHandle(Stage browserWindowStage) {
        super(browserWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(HELP_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(HELP_WINDOW_BROWSER_ID));
    }
}

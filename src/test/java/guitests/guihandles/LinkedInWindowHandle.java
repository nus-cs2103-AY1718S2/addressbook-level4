package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

//@@author x3tsunayh

/**
 * A handle to the {@code LinkedInWindow} of the application.
 */
public class LinkedInWindowHandle extends StageHandle {

    public static final String LINKEDIN_WINDOW_TITLE = "LinkedIn";

    private static final String LINKEDIN_WINDOW_BROWSER_ID = "#browser";

    public LinkedInWindowHandle(Stage linkedInWindowStage) {
        super(linkedInWindowStage);
    }

    /**
     * Returns true if a LinkedIn window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(LINKEDIN_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(LINKEDIN_WINDOW_BROWSER_ID));
    }
}

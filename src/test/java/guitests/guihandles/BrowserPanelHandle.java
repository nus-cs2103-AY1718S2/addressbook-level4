package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

//@@author x3tsunayh

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends StageHandle {

    public static final String GOOGLE_WINDOW_TITLE = "Google";
    private static final String GOOGLE_WINDOW_BROWSER_ID = "#browser";

    private boolean isWebViewLoaded = true;

    public BrowserPanelHandle(Stage browserPanelStage) {
        super(browserPanelStage);
        WebView webView = getChildNode(GOOGLE_WINDOW_BROWSER_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));
    }

    /**
     * Returns true if a Google window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(GOOGLE_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(GOOGLE_WINDOW_BROWSER_ID));
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     * Returns the {@code URL} of the currently loaded page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}

package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.address.model.smplatform.Link;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#browser";
    public static final String BROWSER1_ID = "#browser1";
    public static final String TAB_PANE_ID = "#tabPane";

    private boolean isWebViewLoaded = true;

    private URL lastRememberedUrl;

    private WebView webView;
    private WebView webView1;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        webView = getChildNode(BROWSER_ID); // browser for facebookTab
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));

        webView1 = getChildNode(BROWSER1_ID); // browser for twitterTab
        WebEngine engine1 = webView1.getEngine();
        new GuiRobot().interact(() -> engine1.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));
    }

    /**
     * Returns the {@code URL} of the currently loaded page for the default browser tab (i.e. facebookTab).
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(webView);
    }

    /**
     * Returns the {@code URL} of the currently loaded page for the specified {@code browserTab}.
     */
    public URL getLoadedUrl(String browserTab) {
        if (browserTab.equals(Link.TWITTER_LINK_TYPE)) {
            return WebViewUtil.getLoadedUrl(webView1);
        }
        return getLoadedUrl();
    }

    /**
     * Remembers the {@code URL} of the currently loaded page.
     */
    public void rememberUrl() {
        lastRememberedUrl = getLoadedUrl();
    }

    /**
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberUrl()} call.
     */
    public boolean isUrlChanged() {
        return !lastRememberedUrl.equals(getLoadedUrl());
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}

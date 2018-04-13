package guitests.guihandles;

import java.net.MalformedURLException;
import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.address.model.smplatform.Link;
import seedu.address.ui.BrowserPanel;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    //@@author Nethergale
    public static final String FACEBOOK_BROWSER_ID = "#facebookBrowser";
    public static final String TWITTER_BROWSER_ID = "#twitterBrowser";
    public static final String TAB_PANE_ID = "#tabPane";

    private boolean isWebViewLoaded = true;

    private URL lastRememberedUrl;

    private WebView facebookWebView;
    private WebView twitterWebView;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        facebookWebView = getChildNode(FACEBOOK_BROWSER_ID); // browser for facebookTab
        WebEngine facebookEngine = facebookWebView.getEngine();
        new GuiRobot().interact(() -> facebookEngine.getLoadWorker().stateProperty().addListener((
                obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));

        twitterWebView = getChildNode(TWITTER_BROWSER_ID); // browser for twitterTab
        WebEngine twitterEngine = twitterWebView.getEngine();
        new GuiRobot().interact(() -> twitterEngine.getLoadWorker().stateProperty().addListener((
                obs, oldState, newState) -> {
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
        URL loadedUrl = WebViewUtil.getLoadedUrl(twitterWebView);
        if (Link.isValidLink(loadedUrl.toExternalForm())) {
            String completeUrl = BrowserPanel.parseUrl(loadedUrl.toExternalForm());
            try {
                loadedUrl = new URL(completeUrl);
                return loadedUrl;
            } catch (MalformedURLException mue) {
                throw new AssertionError("URL expected to be valid.");
            }
        }
        return WebViewUtil.getLoadedUrl(facebookWebView);
    }

    /**
     * Returns the {@code URL} of the currently loaded page for the specified {@code browserTab}.
     */
    public URL getLoadedUrl(String browserTab) {
        if (browserTab.equals(Link.TWITTER_LINK_TYPE)) {
            URL loadedUrl = WebViewUtil.getLoadedUrl(twitterWebView);
            if (Link.isValidLink(loadedUrl.toExternalForm())) {
                String completeUrl = BrowserPanel.parseUrl(loadedUrl.toExternalForm());
                try {
                    loadedUrl = new URL(completeUrl);
                    return loadedUrl;
                } catch (MalformedURLException mue) {
                    throw new AssertionError("URL expected to be valid.");
                }
            }
        }
        return getLoadedUrl();
    }

    //@@author
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

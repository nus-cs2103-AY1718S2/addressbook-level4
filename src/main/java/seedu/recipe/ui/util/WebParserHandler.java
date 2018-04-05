//@@author kokonguyen191
package seedu.recipe.ui.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;

import javafx.scene.web.WebView;

/**
 * Handles a WebParseRequestEvent
 */
public class WebParserHandler {

    private WebView browser;

    public WebParserHandler(WebView browser) {
        this.browser = browser;
    }

    /**
     * Returns the according WebParser to the currently loaded page in the BrowserPanel
     */
    public WebParser getWebParser() {
        String url = browser.getEngine().getLocation();
        Document document = browser.getEngine().getDocument();
        if (browser.getEngine().getTitle() != null) {
            return WebParserHandler.getWebParser(url, document);
        } else {
            return null;
        }
    }

    /**
     * Reads the {@code url}, returns the according WebParser loaded with {@code document}
     */
    public static WebParser getWebParser(String url, Document document) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            W3CDom w3CDom = new W3CDom();
            String documentString = w3CDom.asString(document);
            switch (domain) {

            case WikiaParser.DOMAIN:
                return new WikiaParser(documentString);

            default:
                return null;

            }
        } catch (URISyntaxException e) {
            return null;
        }
    }
}

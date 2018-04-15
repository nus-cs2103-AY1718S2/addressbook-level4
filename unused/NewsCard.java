//@@author Eldon-Chung-unused
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewsCardClickedEvent;

/**
 * An UI component that displays information of a news link from www.cryptopanic.com.
 */
public class NewsCard extends UiPart<Region> {

    private static final String FXML = "NewsListCard.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on CoinBook level 4</a>
     */

    @FXML
    private HBox cardNewsPane;
    @FXML
    private Label headline;
    @FXML
    private Label pubDate;
    private String url;

    public NewsCard(String title, String date, String url) {
        super(FXML);
        this.headline.setText(title);
        this.pubDate.setText(date);
        this.url = url;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NewsCard)) {
            return false;
        }

        // state check
        NewsCard card = (NewsCard) other;
        return this.headline.equals(card.headline)
                && this.pubDate.equals(card.pubDate)
                && this.url.equals(card.url);
    }

    /**
     * Handles the mouse click event, {@code mouseEvent}.
     */
    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
            logger.fine("User has clicked on " + this.getClass().getName() + " which contains URL '" + this.url + "'");
            raise(new NewsCardClickedEvent(this.url));
        }
    }
}

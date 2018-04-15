# Eldon-Chung-unused
###### \NewsCard.java
``` java
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
```
###### \NewsCardClickedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a mouse click on a news card
 */
public class NewsCardClickedEvent extends BaseEvent {

    public final String url;

    public NewsCardClickedEvent(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \NewsListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardNewsPane" fx:id="cardNewsPane" onMouseClicked="#handleMouseClick" xmlns="http://javafx.com/javafx/8.0.112" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="headline" styleClass="cell_big_label" text="\$headline" />
            </HBox>
            <FlowPane fx:id="tags" />
            <Label fx:id="pubDate" styleClass="cell_small_label" text="\$pubDate" />
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
###### \NewsListPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="newsListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \NewsListPanel.java
``` java
package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of news links.
 */
public class NewsListPanel extends UiPart<Region> {
    private static final String FXML = "NewsListPanel.fxml";

    @FXML
    private ListView<NewsCard> newsListView;

    public NewsListPanel() {
        super(FXML);
        ObservableList<NewsCard> ol = loadNewsList();
        setConnections(ol);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<NewsCard> ol) {

        newsListView.setItems(ol);
        newsListView.setCellFactory(listView -> new NewsListViewCell());
    }

    /**
     * Loads a dummy news list with fake article links
     * @return an observable NewsCard list
     */
    private ObservableList<NewsCard> loadNewsList() {
        ArrayList<NewsCard> al = new ArrayList<NewsCard>();
        al.add(new NewsCard("Newegg new accepts Bitcoin BCH through Bitpay!", "15 Mar 2018",
                "https://cryptopanic.com/news/1378058/Newegg-new-accepts-Bitcoin-BCH-through-Bitpay-0-Fees"));
        al.add(new NewsCard("Test2", "22 Feb 2018", "https://www.google.com"));
        return FXCollections.observableArrayList(al);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CoinCard}.
     */
    class NewsListViewCell extends ListCell<NewsCard> {

        @Override
        protected void updateItem(NewsCard news, boolean empty) {
            super.updateItem(news, empty);

            if (empty || news == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(news.getRoot());
            }
        }
    }
}
```

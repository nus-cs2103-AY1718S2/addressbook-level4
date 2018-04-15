//@@author Eldon-Chung-unused
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

package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CoinPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.coin.Coin;

/**
 * Panel containing the list of coins.
 */
public class CoinListPanel extends UiPart<Region> {
    private static final String FXML = "CoinListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CoinListPanel.class);

    @FXML
    private ListView<CoinCard> coinListView;

    public CoinListPanel(ObservableList<Coin> coinList) {
        super(FXML);
        setConnections(coinList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Coin> coinList) {
        ObservableList<CoinCard> mappedList = EasyBind.map(
                coinList, (coin) -> new CoinCard(coin, coinList.indexOf(coin) + 1));
        coinListView.setItems(mappedList);
        coinListView.setCellFactory(listView -> new CoinListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        coinListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in coin list panel changed to : '" + newValue + "'");
                        raise(new CoinPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code CoinCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            coinListView.scrollTo(index);
            coinListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CoinCard}.
     */
    class CoinListViewCell extends ListCell<CoinCard> {

        @Override
        protected void updateItem(CoinCard coin, boolean empty) {
            super.updateItem(coin, empty);

            if (empty || coin == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(coin.getRoot());
            }
        }
    }

}

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
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.pair.Pair;

/**
 * Panel containing the list of pairs.
 */
public class PairListPanel extends UiPart<Region> {
    private static final String FXML = "PairListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PairListPanel.class);

    @FXML
    private ListView<PairCard> pairListView;

    public PairListPanel(ObservableList<Pair> pairList) {
        super(FXML);
        setConnections(pairList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Pair> pairList) {
        ObservableList<PairCard> mappedList = EasyBind.map(
                pairList, (pair) -> new PairCard(pair, pairList.indexOf(pair) + 1));
        pairListView.setItems(mappedList);
        pairListView.setCellFactory(listView -> new PairListViewCell());
    }

    /**
     * Scrolls to the {@code PairCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            pairListView.scrollTo(index);
            pairListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PairCard}.
     */
    class PairListViewCell extends ListCell<PairCard> {

        @Override
        protected void updateItem(PairCard pair, boolean empty) {
            super.updateItem(pair, empty);

            if (empty || pair == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(pair.getRoot());
            }
        }
    }

}

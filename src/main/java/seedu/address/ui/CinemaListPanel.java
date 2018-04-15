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
import seedu.address.commons.events.ui.CinemaPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.cinema.Cinema;

/**
 * Panel containing the list of cinemas.
 */
public class CinemaListPanel extends UiPart<Region> {
    private static final String FXML = "CinemaListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CinemaListPanel.class);

    @FXML
    private ListView<CinemaCard> cinemaListView;

    public CinemaListPanel(ObservableList<Cinema> cinemaList) {
        super(FXML);
        setConnections(cinemaList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Cinema> cinemaList) {
        ObservableList<CinemaCard> mappedList = EasyBind.map(
                cinemaList, (cinema) -> new CinemaCard(cinema, cinemaList.indexOf(cinema) + 1));
        cinemaListView.setItems(mappedList);
        cinemaListView.setCellFactory(listView -> new CinemaListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        cinemaListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in cinema list panel changed to : '" + newValue + "'");
                        raise(new CinemaPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code CinemaCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            cinemaListView.scrollTo(index);
            cinemaListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CinemaCard}.
     */
    class CinemaListViewCell extends ListCell<CinemaCard> {

        @Override
        protected void updateItem(CinemaCard cinema, boolean empty) {
            super.updateItem(cinema, empty);

            if (empty || cinema == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(cinema.getRoot());
            }
        }
    }

}

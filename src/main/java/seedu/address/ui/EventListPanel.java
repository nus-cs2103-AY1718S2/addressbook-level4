//@@author jasmoon
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeselectListCellEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PanelSelectionChangedEvent;
import seedu.address.model.activity.Activity;

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<EventCard> eventListView;

    private Label emptyLabel = new Label("Event List is empty!");

    public EventListPanel(ObservableList<Activity> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
        setUpPlaceHolder();
        //maybe do not need this
        //eventListView.managedProperty().bind(eventListView.visibleProperty());
    }

    private void setConnections(ObservableList<Activity> eventList) {
        ObservableList<EventCard> mappedList = EasyBind.map(
                eventList, (event) -> new EventCard(event, eventList.indexOf(event) + 1));
        eventListView.setItems(mappedList);
        linkCell();
        setEventHandlerForSelectionChangeEvent();
    }

    private void setUpPlaceHolder()   {
        eventListView.setPlaceholder(emptyLabel);
        emptyLabel.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 25px; ");
    }

    /**
     * Links eventListView to eventListViewCell as its custom ListCell
     */
    private void linkCell() {
        eventListView.setCellFactory(listView -> {
            EventListViewCell cell = new EventListViewCell();
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                eventListView.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (eventListView.getSelectionModel().getSelectedIndices().contains(index))  {
                        logger.fine("Selection in event list panel with index '" + index
                                + "' has been deselected");
                        raise(new DeselectListCellEvent(eventListView, index));
                    } else {
                        eventListView.getSelectionModel().select(index);
                    }
                    event.consume();
                }
            });
            return cell;
        });
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new PanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code EventCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<EventCard> {

        @Override
        protected void updateItem(EventCard event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(event.getRoot());
            }
        }
    }

    //@@author jasmoon
    /**
     * Getter method for eventListView
     * @return eventListView
     */
    public ListView<EventCard> getEventListView()   {
        return eventListView;
    }
}

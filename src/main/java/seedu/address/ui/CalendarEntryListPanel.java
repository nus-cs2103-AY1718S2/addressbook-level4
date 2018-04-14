package seedu.address.ui;
//@@author SuxianAlicia
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarEntryPanelSelectionChangedEvent;
import seedu.address.model.entry.CalendarEntry;

/**
 * Panel containing calendar entries present in calendar.
 */
public class CalendarEntryListPanel extends UiPart<Region> {

    private static final String FXML = "CalendarEntryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarEntryListPanel.class);

    @FXML
    private ListView<CalendarEntryCard> calendarEntryCardListView;

    public CalendarEntryListPanel(ObservableList<CalendarEntry> calendarEntries) {
        super(FXML);
        setConnections(calendarEntries);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<CalendarEntry> calendarEntryList) {
        ObservableList<CalendarEntryCard> mappedList = EasyBind.map(calendarEntryList, (calendarEntry) ->
                        new CalendarEntryCard(calendarEntry, calendarEntryList.indexOf(calendarEntry) + 1));
        calendarEntryCardListView.setItems(mappedList);
        calendarEntryCardListView.setCellFactory(listView -> new CalendarEntryListPanel.CalendarEntryListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        calendarEntryCardListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in calendar entry list panel changed to : '" + newValue + "'");
                        raise(new CalendarEntryPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CalendarEntryCard}.
     */
    class CalendarEntryListViewCell extends ListCell<CalendarEntryCard> {

        @Override
        protected void updateItem(CalendarEntryCard calendarEntry, boolean empty) {
            super.updateItem(calendarEntry, empty);

            if (empty || calendarEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(calendarEntry.getRoot());
            }
        }
    }

}

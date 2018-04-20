//@@author Kyholmes
package seedu.address.ui;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.ImdbChangedEvent;
import seedu.address.model.patient.Patient;

/**
 * Panel containing the visiting queue.
 */
public class QueuePanel extends UiPart<Region> {

    private static final String FXML = "QueueListPanel.fxml";

    @FXML
    private ListView<QueueCard> queueCardListView;

    public QueuePanel(ObservableList<Patient> queueList, ObservableList<Integer> indexNoList) {
        super(FXML);
        setConnections(queueList, indexNoList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Patient> queueList, ObservableList<Integer> indexNoList) {
        ObservableList<QueueCard> mappedList = EasyBind.map(queueList, (patient) -> new QueueCard(patient,
                indexNoList.get(queueList.indexOf(patient)) + 1));

        queueCardListView.setItems(mappedList);
        queueCardListView.setCellFactory(listView -> new QueueListViewCell());
    }

    @Subscribe
    public void handleImdbChangedEvent(ImdbChangedEvent ice) {
        setConnections(ice.data.getUniquePatientQueue(), ice.data.getUniquePatientQueueNo());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code QueueCard}.
     */
    class QueueListViewCell extends ListCell<QueueCard> {

        @Override
        protected void updateItem(QueueCard queueCard, boolean empty) {
            super.updateItem(queueCard, empty);

            if (empty || queueCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(queueCard.getRoot());
            }
        }
    }
}

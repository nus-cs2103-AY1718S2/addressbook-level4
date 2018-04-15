package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Debtor;
//@@author ongkc
/**
 * Panel containing the list of debtors.
 */
public class DebtorListPanel extends UiPart<Region> {

    private static final String FXML = "DebtorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DebtorListPanel.class);

    @javafx.fxml.FXML
    private ListView<DebtorCard> debtorListView;

    public DebtorListPanel(ObservableList<Debtor> debtorsList) {
        super(FXML);
        setConnections(debtorsList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Debtor> debtorsList) {
        ObservableList<DebtorCard> mappedList = EasyBind.map(
                debtorsList, (debtor) -> new DebtorCard(debtor, debtorsList.indexOf(debtor) + 1));
        debtorListView.setItems(mappedList);
        debtorListView.setCellFactory(listView -> new DebtorListViewCell());
    }


    /**
     * Scrolls to the {@code DebtorCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            debtorListView.scrollTo(index);
            debtorListView.getSelectionModel().clearAndSelect(index);
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DebtorCard}.
     */
    class DebtorListViewCell extends ListCell<DebtorCard> {
        @Override
        protected void updateItem(DebtorCard debtor, boolean empty) {
            super.updateItem(debtor, empty);

            if (empty || debtor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(debtor.getRoot());
            }
        }
    }

}

//@@author sarahgoh97
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.HideMapEvent;
import seedu.address.model.cell.Cell;


/**
 * The cellMap of the App.
 */
public class MapPanel extends UiPart<Region> {

    private static final String FXML = "MapPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(MapPanel.class);

    @FXML
    private Label cellAddress11;
    @FXML
    private Label cellAddress12;
    @FXML
    private Label cellAddress13;
    @FXML
    private Label cellAddress14;
    @FXML
    private Label cellAddress15;
    @FXML
    private Label cellAddress21;
    @FXML
    private Label cellAddress22;
    @FXML
    private Label cellAddress23;
    @FXML
    private Label cellAddress24;
    @FXML
    private Label cellAddress25;
    @FXML
    private Label cellAddress31;
    @FXML
    private Label cellAddress32;
    @FXML
    private Label cellAddress33;
    @FXML
    private Label cellAddress34;
    @FXML
    private Label cellAddress35;

    public MapPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<String> list) {
        cellAddress11.setText(list.get(0));
        cellAddress12.setText(list.get(1));
        cellAddress13.setText(list.get(2));
        cellAddress14.setText(list.get(3));
        cellAddress15.setText(list.get(4));
        cellAddress21.setText(list.get(5));
        cellAddress22.setText(list.get(6));
        cellAddress23.setText(list.get(7));
        cellAddress24.setText(list.get(8));
        cellAddress25.setText(list.get(9));
        cellAddress31.setText(list.get(10));
        cellAddress32.setText(list.get(11));
        cellAddress33.setText(list.get(12));
        cellAddress34.setText(list.get(13));
        cellAddress35.setText(list.get(14));
    }

    private void setStrings(ObservableList<Cell> cellList) {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = 0; i < 15; i++) {
            list.add(Integer.toString(cellList.get(i).getNumberOfPrisoners()));
        }
        setConnections(list);
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Changing map\n"));
        setStrings(abce.data.getCellList());
    }

    @Subscribe
    private void handleHideMapRequestEvent(HideMapEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(event.list);
    }

}

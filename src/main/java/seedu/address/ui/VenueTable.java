package seedu.address.ui;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

/**
 * A ui for the info panal that is displayed when the vacant command is called.
 */
public class VenueTable extends UiPart<Region> {

    private static final String FXML = "VenueTable.fxml";

    @FXML
    private TableView venueTable;
    @FXML
    private TableColumn<ArrayList<String>, String> roomId;
    @FXML
    private TableColumn<ArrayList<String>, String> eightAm;
    @FXML
    private TableColumn<ArrayList<String>, String> nineAm;
    @FXML
    private TableColumn<ArrayList<String>, String> tenAm;
    @FXML
    private TableColumn<ArrayList<String>, String> elevenAm;
    @FXML
    private TableColumn<ArrayList<String>, String> twelvePm;
    @FXML
    private TableColumn<ArrayList<String>, String> onePm;
    @FXML
    private TableColumn<ArrayList<String>, String> twoPm;
    @FXML
    private TableColumn<ArrayList<String>, String> threePm;
    @FXML
    private TableColumn<ArrayList<String>, String> fourPm;
    @FXML
    private TableColumn<ArrayList<String>, String> fivePm;
    @FXML
    private TableColumn<ArrayList<String>, String> sixPm;
    @FXML
    private TableColumn<ArrayList<String>, String> sevenPm;
    @FXML
    private TableColumn<ArrayList<String>, String> eightPm;

    public VenueTable(ObservableList<ArrayList<String>> schedules) {
        super(FXML);
        venueTable.setItems(schedules);
        roomId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        eightAm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        nineAm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        tenAm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        elevenAm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        twelvePm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
        onePm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(6)));
        twoPm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(7)));
        threePm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(8)));
        fourPm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(9)));
        fivePm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(10)));
        sixPm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(11)));
        sevenPm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(12)));
        eightPm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(13)));
    }
}

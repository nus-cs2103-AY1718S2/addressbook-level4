package seedu.address.ui;

import com.sun.xml.internal.bind.v2.runtime.property.PropertyFactory;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import seedu.address.model.building.Room;

import java.io.FileInputStream;
import java.util.ArrayList;

public class VenueTable extends UiPart<Region> {

    @FXML
    TableView venueTable;
    @FXML
    TableColumn<ArrayList<String>, String> roomId;
    @FXML
    TableColumn<ArrayList<String>, String> eightAm;
    @FXML
    TableColumn<ArrayList<String>, String> nineAm;
    @FXML
    TableColumn<ArrayList<String>, String> tenAm;
    @FXML
    TableColumn<ArrayList<String>, String> elevenAm;
    @FXML
    TableColumn<ArrayList<String>, String> twelvePm;
    @FXML
    TableColumn<ArrayList<String>, String> onePm;
    @FXML
    TableColumn<ArrayList<String>, String> twoPm;
    @FXML
    TableColumn<ArrayList<String>, String> threePm;
    @FXML
    TableColumn<ArrayList<String>, String> fourPm;
    @FXML
    TableColumn<ArrayList<String>, String> fivePm;
    @FXML
    TableColumn<ArrayList<String>, String> sixPm;
    @FXML
    TableColumn<ArrayList<String>, String> sevenPm;
    @FXML
    TableColumn<ArrayList<String>, String> eightPm;


    private static final String FXML = "VenueTable.fxml";

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

package seedu.address.ui;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

//@@author yeggasd
/**
 * A ui for the info panel that is displayed when the timetable command is called.
 */
public class TimeTablePanel extends UiPart<Region> {
    private static final String OCCUPIED_STYLE_CLASS = "occupied";
    private static final String VACANT_STYLE_CLASS = "vacant";
    private static final String FXML = "TimeTablePanel.fxml";

    private ArrayList<TableColumn<ArrayList<String>, String>> columns;
    @FXML
    private TableView timeTable;
    @FXML
    private TableColumn<ArrayList<String>, String> modId;
    @FXML
    private TableColumn<ArrayList<String>, String> monday;
    @FXML
    private TableColumn<ArrayList<String>, String> tuesday;
    @FXML
    private TableColumn<ArrayList<String>, String> wednesday;
    @FXML
    private TableColumn<ArrayList<String>, String> thursday;
    @FXML
    private TableColumn<ArrayList<String>, String> friday;


    public TimeTablePanel() {
        super(FXML);
        timeTable.setItems(null);
    }

    public TimeTablePanel(ObservableList<ArrayList<String>> schedules) {
        super(FXML);
        timeTable.setItems(schedules);
        timeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initializeColumns();
        initializeTableColumns();
        modId.setMinWidth(100);
        modId.setMaxWidth(100);
    }

    /**
     * Initializes columns
     */
    private void initializeColumns() {
        columns = new ArrayList<>();
        columns.add(modId);
        columns.add(monday);
        columns.add(tuesday);
        columns.add(wednesday);
        columns.add(thursday);
        columns.add(friday);
    }

    /**
     * Initializes table columns
     */
    private void initializeTableColumns() {
        for (int i = 0; i < columns.size(); i++) {
            final int j = i;
            columns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(j)));
            if (j != 0) {
                columns.get(i).setMinWidth(100);
                columns.get(i).setMaxWidth(300);
            }
        }
    }

    /**
     * Sets the command box style to indicate free or having lesson
     */
    public void setStyle() {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellFactory(column -> {
                return new TableCell<ArrayList<String>, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            if (getItem() == null) {
                                setTextFill(Color.BLACK);
                                setStyle("-fx-background-color: #D3D3D3");
                            } else {
                                setTextFill(Color.BLACK);
                                setStyle("-fx-background-color: #17A589");
                            }
                        }
                    }
                };
            });
        }
    }
}

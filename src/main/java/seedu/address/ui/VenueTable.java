package seedu.address.ui;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

//@@author jingyinno
/**
 * A ui for the info panel that is displayed when the vacant command is called.
 */
public class VenueTable extends UiPart<Region> {
    private static final String FXML = "VenueTable.fxml";
    private static final String EMPTY_CELL = "";
    private static final String OCCUPIED_LABEL = "occupied";
    private static final String VACANT_LABEL = "vacant";
    private static final String OCCUPIED_STYLE_CLASS = "venueTable-cell-occupied";
    private static final String VACANT_STYLE_CLASS = "venueTable-cell-vacant";
    private static final int MIN_CELL_WIDTH = 75;
    private static final int MAX_CELL_WIDTH = 100;
    private static final int ROOM_COLUMN_INDEX = 0;

    private ArrayList<TableColumn<ArrayList<String>, String>> columns;
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

    public VenueTable() {
        this(null);
    }

    public VenueTable(ObservableList<ArrayList<String>> schedules) {
        super(FXML);
        venueTable.setItems(schedules);
        initializeColumns();
        initializeTableColumns();
        roomId.setMinWidth(MAX_CELL_WIDTH);
        roomId.setMaxWidth(MAX_CELL_WIDTH);
        venueTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Initializes columns
     */
    private void initializeColumns() {
        columns = new ArrayList<>();
        columns.add(roomId);
        columns.add(eightAm);
        columns.add(nineAm);
        columns.add(tenAm);
        columns.add(elevenAm);
        columns.add(twelvePm);
        columns.add(onePm);
        columns.add(twoPm);
        columns.add(threePm);
        columns.add(fourPm);
        columns.add(fivePm);
        columns.add(sixPm);
        columns.add(sevenPm);
        columns.add(eightPm);
    }

    /**
     * Initializes table columns
     */
    private void initializeTableColumns() {
        for (int i = 0; i < columns.size(); i++) {
            final int j = i;
            columns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(j)));
            columns.get(i).impl_setReorderable(false);
            if (j != ROOM_COLUMN_INDEX) {
                columns.get(i).setMinWidth(MIN_CELL_WIDTH);
                columns.get(i).setMaxWidth(MAX_CELL_WIDTH);
            }

        }
    }
    /**
     * Sets the command box style to indicate a vacant or occupied room.
     */
    public void setStyle() {
        for (int i = 0; i < columns.size(); i++) {
            TableColumn<ArrayList<String>, String> columnToBeSet = columns.get(i);
            setStyleForColumn(columnToBeSet);
        }
    }

    /**
     * Sets the style of each column.
     * @param columnToBeSet is the column that would be set
     */
    private void setStyleForColumn (TableColumn<ArrayList<String>, String> columnToBeSet) {
        columnToBeSet.setCellFactory(column -> {
            return setStyleForCell();
        });
    }

    /**
     * Sets the style of the cell given the data and return it
     * @return the tablecell with its style set.
     */
    private TableCell<ArrayList<String>, String> setStyleForCell () {
        return new TableCell<ArrayList<String>, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle(EMPTY_CELL);
                } else {
                    removeAllStyle(this);
                    setText(item);
                    if (getItem().equals(OCCUPIED_LABEL)) {
                        getStyleClass().add(OCCUPIED_STYLE_CLASS);
                    } else if (getItem().equals(VACANT_LABEL)) {
                        getStyleClass().add(VACANT_STYLE_CLASS);

                    }
                }
            }
        };
    }

    /**
     * Removes all styles present in cell
     * @param tableCell Cell with its style to be removed
     */
    private static void removeAllStyle(TableCell<ArrayList<String>, String> tableCell) {
        tableCell.getStyleClass().remove(OCCUPIED_STYLE_CLASS);
        tableCell.getStyleClass().remove(VACANT_STYLE_CLASS);
    }
}

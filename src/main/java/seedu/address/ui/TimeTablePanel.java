package seedu.address.ui;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import seedu.address.commons.util.StringUtil;

//@@author yeggasd
/**
 * A ui for the info panel that is displayed when the timetable command is called.
 */
public class TimeTablePanel extends UiPart<Region> {
    private static final String COLUMNHEADER_STYLE_CLASS = "column-header";
    private static final String TABLECELL_STYLE_CLASS = "table-cell";
    private static final String EMPTY_STYLE_CLASS = "timetable-cell-empty";
    private static final String[] MOD_COLOR_STYLES = { "modteal", "modsandybrown", "modplum", "modyellow",
                                                         "modyellow"};
    private static final String FXML = "TimeTablePanel.fxml";

    private ArrayList<TableColumn<ArrayList<String>, String>> columns;
    @FXML
    private TableView timeTable;
    @FXML
    private TableColumn<ArrayList<String>, String> day;
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
        day.setMinWidth(100);
        day.setMaxWidth(100);
    }

    /**
     * Initializes columns
     */
    private void initializeColumns() {
        columns = new ArrayList<>();
        columns.add(day);
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
            if (j != 0) {
                columns.get(i).setMinWidth(75);
                columns.get(i).setMaxWidth(200);
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
                            removeAllStyle(this);
                            if ("".equals(getItem())) {
                                getStyleClass().add(EMPTY_STYLE_CLASS);
                            } else if (StringUtil.isDay(getItem())) {
                                getStyleClass().add(COLUMNHEADER_STYLE_CLASS);
                            } else {
                                getStyleClass().add(getColorStyleFor(getItem()));
                            }
                        }
                    }
                };
            });
        }
    }

    /**
     * Removes all styles present in cell
     * @param tableCell Cell with its style to be removed
     */
    private static void removeAllStyle(TableCell<ArrayList<String>, String> tableCell) {
        for (String color : MOD_COLOR_STYLES) {
            tableCell.getStyleClass().remove(color);
        }
        tableCell.getStyleClass().remove(EMPTY_STYLE_CLASS);
        tableCell.getStyleClass().remove(TABLECELL_STYLE_CLASS);
    }

    /**
     * Returns a Color Style for the Module based on its hashcode.
     * @param modName
     * @return colorStyle for {@code modName}'s label.
     */
    private static String getColorStyleFor(String modName) {
        return MOD_COLOR_STYLES[Math.abs(modName.hashCode()) % MOD_COLOR_STYLES.length];
    }
}

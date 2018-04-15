package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;

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
    private static final int MAX_COLUMN_WIDTH = 200;
    private static final int MIN_COLUMN_WIDTH = 75;
    private static final String COLUMNHEADER_STYLE_CLASS = "column-header";
    private static final String TABLECELL_STYLE_CLASS = "table-cell";
    private static final String EMPTY_STYLE_CLASS = "timetable-cell-empty";
    private static final String[] MOD_COLOR_STYLES = { "modteal", "modsandybrown", "modplum", "modyellow",
                                                         "modyellow", "modcyan", "modpink", "modlightblue", "modpurple",
                                                         "modindigo", "modlightgreen", "modorange", "modgoldbrown"};
    private static final String FXML = "TimeTablePanel.fxml";
    private static final HashMap<Integer, String> TAKEN_COLOR = new HashMap<>();
    private static final int MODNAME_LENGTH = 6;

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
        TAKEN_COLOR.clear();
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
                columns.get(i).setMinWidth(MIN_COLUMN_WIDTH);
                columns.get(i).setMaxWidth(MAX_COLUMN_WIDTH);
            }
            columns.get(i).setSortable(false);
        }
    }

    /**
     * Sets the columns to the style for each value.
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
     * @param lessonName
     * @return colorStyle for {@code modName}'s label.
     */
    private static String getColorStyleFor(String lessonName) {
        String modName = lessonName.substring(0, MODNAME_LENGTH);
        int colorIndex = Math.abs(modName.hashCode()) % MOD_COLOR_STYLES.length;
        int index = 0;
        //finds the next avaliable index that is not taken.
        while (index < MOD_COLOR_STYLES.length && TAKEN_COLOR.get(colorIndex) != null
                && !TAKEN_COLOR.get(colorIndex).equals(modName)) {
            colorIndex = (colorIndex + 1) % MOD_COLOR_STYLES.length;
            index++;
        }
        TAKEN_COLOR.put(colorIndex, modName);
        return MOD_COLOR_STYLES[colorIndex];
    }
}

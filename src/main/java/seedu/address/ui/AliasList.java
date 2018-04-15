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
 * A ui for the info panel that is displayed when the list command is called.
 */
public class AliasList extends UiPart<Region> {
    private static final String FXML = "AliasList.fxml";
    private static final String EMPTY_CELL = "";
    private static final String ODD_COLUMN_STYLE_CLASS = "alias-column-odd";
    private static final String EVEN_COLUMN_STYLE_CLASS = "alias-column-even";
    private static final int SHORT_COMMAND_WIDTH = 75;
    private static final int LONG_COMMAND_WIDTH = 100;
    private static final int LONG_COMMAND_INDEX = 2;

    private ArrayList<TableColumn<ArrayList<String>, String>> columns;
    @FXML
    private TableView aliasList;
    @FXML
    private TableColumn<ArrayList<String>, String> addCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> editCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> selectCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> deleteComamnd;
    @FXML
    private TableColumn<ArrayList<String>, String> clearCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> findCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> listCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> historyCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> exitCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> helpCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> undoCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> redoCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> aliasCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> importCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> passwordCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> birthdaysCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> exportCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> mapCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> removePasswordCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> unaliasCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> vacantCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> unionCommand;
    @FXML
    private TableColumn<ArrayList<String>, String> uploadCommand;

    public AliasList()  {
        super(FXML);
    }

    /**
     * Initializes columns
     */
    private void initializeColumns() {
        columns = new ArrayList<>();
        columns.add(addCommand);
        columns.add(aliasCommand);
        columns.add(birthdaysCommand);
        columns.add(clearCommand);
        columns.add(removePasswordCommand);
        columns.add(deleteComamnd);
        columns.add(editCommand);
        columns.add(passwordCommand);
        columns.add(exitCommand);
        columns.add(exportCommand);
        columns.add(findCommand);
        columns.add(helpCommand);
        columns.add(historyCommand);
        columns.add(importCommand);
        columns.add(listCommand);
        columns.add(mapCommand);
        columns.add(redoCommand);
        columns.add(selectCommand);
        columns.add(unaliasCommand);
        columns.add(undoCommand);
        columns.add(unionCommand);
        columns.add(uploadCommand);
        columns.add(vacantCommand);
    }

    /**
     * Initializes alias list Ui
     */
    public void init(ObservableList<ArrayList<String>> aliases) {
        aliasList.setItems(aliases);
        initializeColumns();
        initializeTableColumns();
        aliasList.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Initializes table columns
     */
    private void initializeTableColumns() {
        for (int i = 0; i < columns.size(); i++) {
            final int index = i;
            columns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(index)));
            columns.get(i).impl_setReorderable(false);
            if (i == LONG_COMMAND_INDEX) {
                columns.get(i).setMinWidth(LONG_COMMAND_WIDTH);
            } else {
                columns.get(i).setMinWidth(SHORT_COMMAND_WIDTH);
            }
            columns.get(i).setSortable(false);

        }
    }
    /**
     * Sets the command box style to indicate an alias belonging to the command.
     */
    public void setStyle() {
        for (int i = 0; i < columns.size(); i++) {
            final int index = i;
            columns.get(i).setCellFactory(column -> {
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
                            if (!EMPTY_CELL.equals(getItem())) {
                                fillOddAndEvenIndexedCells();
                            }
                        }
                    }

                    private void fillOddAndEvenIndexedCells() {
                        if (index % 2 == 0) {
                            getStyleClass().add(EVEN_COLUMN_STYLE_CLASS);
                        } else {
                            getStyleClass().add(ODD_COLUMN_STYLE_CLASS);
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
        tableCell.getStyleClass().remove(ODD_COLUMN_STYLE_CLASS);
        tableCell.getStyleClass().remove(EVEN_COLUMN_STYLE_CLASS);
    }
}

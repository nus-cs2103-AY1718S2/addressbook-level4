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

//@@author jingyinno
/**
 * A ui for the info panel that is displayed when the list command is called.
 */
public class AliasList extends UiPart<Region> {
    private static final String FXML = "AliasList.fxml";

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
        this(null);
    }

    public AliasList(ObservableList<ArrayList<String>> aliases) {
        super(FXML);
        aliasList.setItems(aliases);
        initializeColumns();
        initializeTableColumns();
        aliasList.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
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
     * Initializes table columns
     */
    private void initializeTableColumns() {
        for (int i = 0; i < columns.size(); i++) {
            final int j = i;
            columns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(j)));
            columns.get(i).impl_setReorderable(false);
            if (i == 2) {
                columns.get(i).setMinWidth(100);
            } else {
                columns.get(i).setMinWidth(75);
            }
            columns.get(i).setSortable(false);

        }
    }
    /**
     * Sets the command box style to indicate a vacant or occupied room.
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
                            if (!getItem().equals("")){
                                setTextFill(Color.BLACK);
                                setStyle("-fx-background-color: #17A589");
                            } else {
                                setStyle("-fx-background-color: black");
                            }
                        }
                    }
                };
            });
        }
    }
}

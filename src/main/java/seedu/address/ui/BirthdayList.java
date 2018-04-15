package seedu.address.ui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

//@@author AzuraAiR
/**
 * A ui for the birthday list that is displayed at the InfoPanel after `birthdays` is called
 */
public class BirthdayList extends UiPart<Region> {

    private static final String FXML = "BirthdayList.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea birthdayList;
    @FXML
    private ScrollPane scrollPane;

    public BirthdayList() {
        super(FXML);
        birthdayList.textProperty().bind(displayed);
        scrollPane.setContent(birthdayList);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

    }

    public void loadList(String list) {
        Platform.runLater(() -> displayed.setValue(list));
    }

}

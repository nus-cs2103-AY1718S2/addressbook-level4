//@@author AJZ1995
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ResetPersonPanelRequestEvent;
import seedu.address.model.person.Person;

/**
 *  Displays the contact details of a selected person
 */
public class PersonPanel extends UiPart<Region> {

    private static final String FXML = "PersonPanel.fxml";
    private static final double ICON_WIDTH = 25;
    private static final double ICON_HEIGHT = 25;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private PersonCard selectedPersonCard;
    private Person person;

    @FXML
    private VBox panel;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label email;

    @FXML
    private FlowPane groups;

    @FXML
    private FlowPane preferences;

    @FXML
    private ImageView phoneIcon;

    @FXML
    private ImageView addressIcon;

    @FXML
    private ImageView emailIcon;

    @FXML
    private ImageView groupIcon;

    @FXML
    private ImageView prefIcon;

    public PersonPanel() {
        super(FXML);
        loadBlankPersonPage();
    }

    /**
     * Loads up a blank page when no contact is selected.
     */
    private void loadBlankPersonPage() {
        name.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        groups.getChildren().clear();
        preferences.getChildren().clear();
        initBlankIcons();
    }

    //@@author amad-person
    /**
     * Sets all image icons to blank.
     */
    private void initBlankIcons() {
        phoneIcon.setImage(null);
        addressIcon.setImage(null);
        emailIcon.setImage(null);
        prefIcon.setImage(null);
        groupIcon.setImage(null);
    }
    //@@author

    //@@author AJZ1995
    /**
     * Loads the given {@code Person}'s Information.
     */
    private void loadPersonPage(Person person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        email.setText(person.getEmail().toString());
        person.getGroupTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(PersonCard.getGroupTagColorStyleFor(tag.tagName));
            groups.getChildren().add(tagLabel);
        });
        person.getPreferenceTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(PersonCard.getPrefTagColorStyleFor(tag.tagName));
            preferences.getChildren().add(tagLabel);
        });
        setIcons();
        setImageSizeForAllImages();
    }
    //@@author

    //@@author amad-person
    private void setIcons() {
        Image phoneIconImage = new Image(MainApp.class.getResourceAsStream("/images/phone_icon.png"));
        phoneIcon.setImage(phoneIconImage);

        Image addressIconImage = new Image(MainApp.class.getResourceAsStream("/images/address_icon.png"));
        addressIcon.setImage(addressIconImage);

        Image emailIconImage = new Image(MainApp.class.getResourceAsStream("/images/email_icon.png"));
        emailIcon.setImage(emailIconImage);

        Image prefIconImage = new Image(MainApp.class.getResourceAsStream("/images/pref_icon.png"));
        prefIcon.setImage(prefIconImage);

        Image groupIconImage = new Image(MainApp.class.getResourceAsStream("/images/group_icon.png"));
        groupIcon.setImage(groupIconImage);
    }

    private void setImageSizeForAllImages() {
        phoneIcon.setFitWidth(ICON_WIDTH);
        phoneIcon.setFitHeight(ICON_HEIGHT);

        addressIcon.setFitWidth(ICON_WIDTH);
        addressIcon.setFitHeight(ICON_HEIGHT);

        emailIcon.setFitWidth(ICON_WIDTH);
        emailIcon.setFitHeight(ICON_HEIGHT);

        groupIcon.setFitWidth(ICON_WIDTH);
        groupIcon.setFitHeight(ICON_HEIGHT);

        prefIcon.setFitWidth(ICON_WIDTH);
        prefIcon.setFitHeight(ICON_HEIGHT);
    }
    //@@author

    //@@author AJZ1995

    /**
     * Handles the event whereby selected person in Person List Panel has changed.
     * @param event
     */
    public void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        loadBlankPersonPage();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        selectedPersonCard = event.getNewSelection();
        person = selectedPersonCard.person;
        loadPersonPage(person);
    }

    /**
     * Handles the request to reset the Person Panel, causing panel to display nothing.
     */
    public void handleResetPersonPanelRequestEvent(ResetPersonPanelRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadBlankPersonPage();
    }
    //@@author
}

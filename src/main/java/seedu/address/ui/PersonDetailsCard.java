package seedu.address.ui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;

//@@author yeggasd
/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonDetailsCard extends UiPart<Region> {

    private static final String FXML = "PersonDetailsCard.fxml";
    private static final String[] TAG_COLOR_STYLES = { "teal", "cyan", "purple", "indigo", "lightgreen", "bluegrey",
                                                         "amber", "yellow"};
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private TimeTablePanel timeTablePanel;
    private ArrayList<Label> tagLabels = new ArrayList<>();

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private FlowPane tags;
    @FXML
    private StackPane timetablePlaceholder;

    public PersonDetailsCard() {
        super(FXML);
        person = null;
    }


    public PersonDetailsCard(Person person) {
        super(FXML);
        this.person = person;
        update(person);
    }

    /**
     * Updates the {@code PersonDetailsCard} for the new person selected
     * @param person the Person that is currently selected
     */
    public void update(Person person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        birthday.setText(person.getBirthday().value);
        initializeTags(person);
        initializeTimetable(person);
    }

    /**
     * Initializes the tag labels for {@code person}.
     */
    private void initializeTags(Person person) {
        int i = 0;
        for (Tag tag : person.getTags()) {
            if (tagLabels.size() >  i) {
                Label tagLabel = tagLabels.get(i);
                tagLabel.setVisible(true);
                tagLabel.getStyleClass().remove(getColorStyleFor(tagLabel.getText()));
                tagLabels.get(i).setText(tag.tagName);
                tagLabels.get(i).getStyleClass().add(getColorStyleFor(tag.tagName));
            } else {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.getStyleClass().add(getColorStyleFor(tag.tagName));

                tagLabels.add(tagLabel);
                tags.getChildren().add(tagLabel);
            }
            i++;
        }
        for (; i < tagLabels.size(); i++) {
            tagLabels.get(i).setVisible(false);
        }
    }

    /**
     * Initializes the timetable for {@code person}.
     */
    private void initializeTimetable(Person person) {
        Timetable timeTable = person.getTimetable();
        int oddEvenIndex = StringUtil.getOddEven("odd");
        ArrayList<ArrayList<String>> personTimeTable = timeTable.getTimetable().get(oddEvenIndex);
        ObservableList<ArrayList<String>> timeTableList = FXCollections.observableArrayList(personTimeTable);
        timeTablePanel = new TimeTablePanel(timeTableList);
        timetablePlaceholder.getChildren().add(timeTablePanel.getRoot());
        timeTablePanel.setStyle();
    }

    /**
     * @param tagName
     * @return colorStyle for {@code tagName}'s label.
     */
    public static String getColorStyleFor(String tagName) {
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDetailsCard)) {
            return false;
        }

        // state check
        PersonDetailsCard card = (PersonDetailsCard) other;
        return person.equals(card.person);
    }
}

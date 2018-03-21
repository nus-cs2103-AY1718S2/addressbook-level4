package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";
    private static final String[] TAG_COLOURS =
        {"teal", "red", "blue", "orange", "yellow", "cyan", "gold", "khaki", "green", "olive"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label programmingLanguage;
    @FXML
    private Label dashboard;
    @FXML
    private FlowPane tags;



    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        if (student.isFavourite()) {
            name.setStyle("-fx-text-fill: #f4b342");
        }
        phone.setText(student.getPhone().value);
        address.setText(student.getAddress().value);
        email.setText(student.getEmail().value);
        programmingLanguage.setText(student.getProgrammingLanguage().programmingLanguage);
        dashboard.setText(student.getDashboard().toString());
        setupTags(student);
    }


    private void setupTags(Student student) {
        student.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColourFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     *
     * @param tagName
     * @return colour for {@code tagName}'s label
     */
    private String getTagColourFor(String tagName) {
        return TAG_COLOURS[Math.abs(tagName.hashCode()) % TAG_COLOURS.length];
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCard)) {
            return false;
        }

        // state check
        StudentCard card = (StudentCard) other;
        return id.getText().equals(card.id.getText())
                && student.equals(card.student);
    }
}

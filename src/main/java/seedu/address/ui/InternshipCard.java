package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.internship.Internship;

/**
 * An UI component that displays information of a {@code Internship}.
 */
public class InternshipCard extends UiPart<Region> {

    private static final String FXML = "InternshipListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/InternshipBook-level4/issues/336">The issue on JobbiBot level 4</a>
     */

    public final Internship internship;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label industryTitle;
    @FXML
    private Label industry;
    @FXML
    private Label roleTitle;
    @FXML
    private Label role;
    @FXML
    private Label salaryTitle;
    @FXML
    private Label salary;
    @FXML
    private Label regionTitle;
    @FXML
    private Label region;
    @FXML
    private FlowPane tags;

    //@@author wyinkok
    public InternshipCard(Internship internship, int displayedIndex) {
        super(FXML);
        this.internship = internship;
        id.setText(displayedIndex + ". ");
        name.setText(internship.getName().fullName);
        industryTitle.setText("Industry: ");
        industry.setText(internship.getIndustry().value);
        roleTitle.setText("Role: ");
        role.setText(internship.getRole().value);
        salaryTitle.setText("Stipend: $");
        salary.setText(internship.getSalary().value);
        regionTitle.setText("Region: ");
        region.setText(internship.getRegion().value);

        internship.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InternshipCard)) {
            return false;
        }

        // state check
        InternshipCard card = (InternshipCard) other;
        return id.getText().equals(card.id.getText())
                && internship.equals(card.internship);
    }
}

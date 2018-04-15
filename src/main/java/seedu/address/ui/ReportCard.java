// @@author anh2111
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.report.Report;

/**
 * An UI component that displays information of a {@code Report}.
 */
public class ReportCard extends UiPart<Region> {

    private static final String FXML = "ReportListCard.fxml";
    private static final String[] TAG_COLORS = {"red", "pink", "blue"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Report report;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;
    @FXML
    private Label totalPersons;
    @FXML
    private Label totalTags;

    public ReportCard(Report report) {
        super(FXML);
        this.report = report;
        totalPersons.setText("Total number of people: " + report.getTotalPersons());
        totalTags.setText("Total number of tags: " + report.getTotalTags());
        date.setText("Statistics at " + report.getDate());

        initTags(report);
    }

    /**
     * Add comment!!
     * @param report
     */
    private void initTags(Report report) {
        report.getAllProportions().forEach(p -> {
            Label label = new Label(p.tagName + "(" + p.value + ")");
            label.getStyleClass().add(getTagColorFor(p.tagName));
            tags.getChildren().add(label);
        });
    }

    /**
     * Add comment!!
     * @param tagName
     * @return
     */
    private String getTagColorFor(String tagName) {
        //some explanation
        return TAG_COLORS[Math.abs(tagName.hashCode()) % TAG_COLORS.length];
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReportCard)) {
            return false;
        }

        // state check
        ReportCard card = (ReportCard) other;
        return date.getText().equals(card.date.getText())
                && report.equals(card.report);
    }
}
// @@author

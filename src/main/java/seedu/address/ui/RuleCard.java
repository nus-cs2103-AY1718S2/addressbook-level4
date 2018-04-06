package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.rule.Rule;

/**
 * An UI component that displays information of a {@code Rule}.
 */
public class RuleCard extends UiPart<Region> {

    private static final String FXML = "RuleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RuleBook level 4</a>
     */

    public final Rule rule;

    @FXML
    private HBox cardPane;
    @FXML
    private Label value;

    public RuleCard(Rule rule, int displayedIndex) {
        super(FXML);
        this.rule = rule;
        value.setText(rule.toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RuleCard)) {
            return false;
        }

        // state check
        RuleCard card = (RuleCard) other;
        return value.getText().equals(card.value.getText())
                && rule.equals(card.rule);
    }
}

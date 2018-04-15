package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Creditor;
//@@author ongkc
/**
 * An UI component that displays information of a {@code Creditor}.
 */
public class CreditorCard extends UiPart<Region> {

    private static final String FXML = "CreditorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */
    public final Creditor creditor;

    @javafx.fxml.FXML
    private HBox cardPane;
    @javafx.fxml.FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label debt;
    @FXML
    private FlowPane tags;

    public CreditorCard(Creditor creditor, int displayedIndex) {
        super(FXML);
        this.creditor = creditor;
        id.setText(displayedIndex + ". ");
        name.setText(creditor.getCreditor().getName().fullName);
        phone.setText(creditor.getCreditor().getPhone().value);
        email.setText(creditor.getCreditor().getEmail().value);
        debt.setText(creditor.getDebt().toString());
        creditor.getCreditor().getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof CreditorCard)) {
            return false;
        }

        // state check
        CreditorCard card = (CreditorCard) other;
        return id.getText().equals(card.id.getText())
                    && creditor.equals(card.creditor); }
}

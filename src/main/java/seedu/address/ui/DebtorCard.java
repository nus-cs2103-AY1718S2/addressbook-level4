package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Debtor;
//@@author ongkc
/**
 * An UI component that displays information of a {@code Debtor}.
 */
public class DebtorCard extends UiPart<Region>  {

    private static final String FXML = "DebtorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Debtor debtor;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
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

    public DebtorCard(Debtor debtor, int displayedIndex) {
        super(FXML);
        this.debtor = debtor;
        id.setText(displayedIndex + ". ");
        name.setText(debtor.getDebtor().getName().fullName);
        phone.setText(debtor.getDebtor().getPhone().value);
        email.setText(debtor.getDebtor().getEmail().value);
        debt.setText(debtor.getDebt().toString());
        debtor.getDebtor().getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DebtorCard)) {
            return false;
        }

        // state check
        DebtorCard card = (DebtorCard) other;
        return id.getText().equals(card.id.getText())
                && debtor.equals(card.debtor);
    }

}

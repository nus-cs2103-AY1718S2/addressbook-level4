//@@author phmignot
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label idDisplay;
    @FXML
    private Label payerName;
    @FXML
    private Label amount;
    @FXML
    private Label description;
    @FXML
    private Label transactionType;
    @FXML
    private FlowPane payees;
    @FXML
    private Label date;

    public TransactionCard(Transaction transaction, int displayedIndex) {
       super(FXML);
        this.transaction = transaction;
        idDisplay.setText(displayedIndex + ". ");
        payerName.setText(transaction.getPayer().getName().fullName);
        amount.setText(transaction.getAmount().toString());
        description.setText(transaction.getDescription().toString());
        transactionType.setText(transaction.getTransactionType().toString().toLowerCase().substring(0, 1).toUpperCase()
                + transaction.getTransactionType().toString().toLowerCase().substring(1));

        int numPayees = transaction.getPayees().asObservableList().size();
        for (int i = 0; i < numPayees; i++) {
            Person payee = transaction.getPayees().asObservableList().get(i);
            payees.getChildren().add(new Label(payee.getName().fullName));
            if (i != numPayees - 1) {
                payees.getChildren().add(new Label(", "));
            }
        }
        date.setText(String.valueOf(transaction.getDateTime()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof TransactionCard)) {
            return false;
        }
        // state check
        TransactionCard card = (TransactionCard) other;
        return transaction.equals(card.transaction);
    }
}

//@@author phmignot
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.transaction.Transaction;
import seedu.address.ui.TransactionCard;



/**
 * Provides a handle for {@code TransactionListPanel} containing the list of {@code TransactionCard}.
 */
public class TransactionListPanelHandle extends NodeHandle<ListView<TransactionCard>> {
    public static final String TRANSACTION_LIST_VIEW_ID = "#transactionListView";

    private Optional<TransactionCard> lastRememberedSelectedTransactionCard;

    public TransactionListPanelHandle(ListView<TransactionCard> transactionListPanelNode) {
        super(transactionListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TransactionCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public TransactionCardHandle getHandleToSelectedCard() {
        List<TransactionCard> transactionList = getRootNode().getSelectionModel().getSelectedItems();

        if (transactionList.size() != 1) {
            throw new AssertionError("Transaction list size expected 1.");
        }

        return new TransactionCardHandle(transactionList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<TransactionCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the transaction.
     */
    public void navigateToCard(Transaction transaction) {
        List<TransactionCard> cards = getRootNode().getItems();
        Optional<TransactionCard> matchingCard = cards
                .stream()
                .filter(card -> card.transaction.equals(transaction))
                .findFirst();


        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Transaction does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the transaction card handle of a transaction associated with the {@code index} in the list.
     */
    public TransactionCardHandle getTransactionCardHandle(int index) {
        return getTransactionCardHandle(getRootNode().getItems().get(index).transaction);
    }

    /**
     * Returns the {@code TransactionCardHandle} of the specified {@code transaction} in the list.
     */
    public TransactionCardHandle getTransactionCardHandle(Transaction transaction) {
        Optional<TransactionCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.transaction.equals(transaction))
                .map(card -> new TransactionCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Transaction does not exist."));
    }

    /**
     * Selects the {@code TransactionCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code TransactionCard} in the list.
     */
    public void rememberSelectedTransactionCard() {
        List<TransactionCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedTransactionCard = Optional.empty();
        } else {
            lastRememberedSelectedTransactionCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code TransactionCard} is different from the value remembered by the most recent
     * {@code rememberSelectedTransactionCard()} call.
     */
    public boolean isSelectedTransactionCardChanged() {
        List<TransactionCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedTransactionCard.isPresent();
        } else {
            return !lastRememberedSelectedTransactionCard.isPresent()
                    || !lastRememberedSelectedTransactionCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}


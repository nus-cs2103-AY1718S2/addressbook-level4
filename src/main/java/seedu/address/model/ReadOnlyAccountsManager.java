//@@author Jason1im
package seedu.address.model;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of AccountsManager
 */
public interface ReadOnlyAccountsManager {
    /**
     * Returns an unmodifiable view of the account list.
     * This list will not contain any duplicate accounts.
     */
    public ObservableList<Account> getAccountList();
}

package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.login.User;

//@@author kaisertanqr
/**
 * Unmodifiable view of a user database
 */
public interface ReadOnlyUserDatabase {

    /**
     * Returns an unmodifiable view of the User list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<User> getUsersList();
}

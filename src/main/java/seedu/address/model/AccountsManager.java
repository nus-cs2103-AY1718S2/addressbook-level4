package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.exception.DuplicateUsernameException;
import seedu.address.model.exception.InvalidPasswordException;
import seedu.address.model.exception.InvalidUsernameException;


/**
 * Represents a database of registered user accounts
 */
public class AccountsManager implements ReadOnlyAccountsManager {
    private ObservableList<Account> accountList;


    public AccountsManager() {
        accountList = FXCollections.observableArrayList();
    }

    public boolean checkUsername(String username, Account account) {
        return account.getUsername().equals(username);
    }

    public boolean checkPassword(String password, Account account) {
        return account.getPassword().equals(password);
    }

    /**
    * adds a new account to the list of registered accounts.
    * @throws DuplicateUsernameException if the username is already used
    */
    public void register(String inputUsername, String inputPassword) throws DuplicateUsernameException {
        requireAllNonNull(inputUsername, inputPassword);
        if (!accountList.isEmpty()) {
            for (Account acc : accountList) {
                if (checkUsername(inputUsername, acc)) {
                    throw new DuplicateUsernameException();
                }
            }
        }
        Account newAccount = new Account(inputUsername, inputPassword);
        accountList.add(newAccount);
    }

    /**
     * Checks for validity of username and password.
     * @return the user account that matches the inputs.
     * @throws InvalidUsernameException if the input username cannot be found
     * @throws InvalidPasswordException if the input password does not match with the username
     */
    public Account login(String inputUsername, String inputPassword)
            throws InvalidUsernameException, InvalidPasswordException {
        requireAllNonNull(inputUsername, inputPassword);
        Account result = null;
        boolean isValidUsername = false;
        for (Account acc : accountList) {
            if (checkUsername(inputUsername, acc)) {
                if (checkPassword(inputPassword, acc)) {
                    isValidUsername = true;
                    result = acc;
                } else {
                    throw new InvalidPasswordException();
                }
            }
        }
        if (isValidUsername) {
            return result;
        } else {
            throw new InvalidUsernameException();
        }
    }

    @Override
    public ObservableList<Account> getAccountList() {
        assert CollectionUtil.elementsAreUnique(accountList);
        return FXCollections.unmodifiableObservableList(accountList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountsManager // instanceof handles nulls
                && this.accountList.equals(((AccountsManager) other).accountList));
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountList);
    }
}

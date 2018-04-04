package seedu.address.model.account;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import seedu.address.model.account.exceptions.AccountNotFoundException;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 * A list of accounts that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Account#equals(Object)
 */
public class UniqueAccountList implements Serializable, Iterable<Account> {
    private final ArrayList<Account> internalList = new ArrayList<Account>();

    /**
     * Returns true if the list contains an equivalent account as the given argument.
     */
    public boolean contains(Account toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a account to the list.
     *
     * @throws DuplicateAccountException if the account to add is a duplicate of an existing account in the list.
     */
    public void add(Account toAdd) throws DuplicateAccountException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccountException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the account {@code target} in the list with {@code editedAccount}.
     *
     * @throws DuplicateAccountException if the replacement is equivalent to another existing account in the list.
     * @throws AccountNotFoundException  if {@code target} could not be found in the list.
     */
    public void setAccount(Account target, Account editedAccount)
        throws DuplicateAccountException, AccountNotFoundException {
        requireNonNull(editedAccount);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AccountNotFoundException();
        }

        if (!target.equals(editedAccount) && internalList.contains(editedAccount)) {
            throw new DuplicateAccountException();
        }

        internalList.set(index, editedAccount);
    }

    /**
     * Removes the equivalent account from the list.
     *
     * @throws AccountNotFoundException if no such account could be found in the list.
     */
    public boolean remove(Account toRemove) throws AccountNotFoundException {
        requireNonNull(toRemove);
        final boolean accountFoundAndDeleted = internalList.remove(toRemove);
        if (!accountFoundAndDeleted) {
            throw new AccountNotFoundException();
        }
        return accountFoundAndDeleted;
    }

    /**
     * Returns the account that matches with the provided credential,
     * returns null if none exists
     *
     * @param c
     * @return
     */
    public Account authenticate(Credential c) {
        for (Account a : internalList) {
            if (a.credentialMatches(c)) {
                return a;
            }
        }
        return null;
    }

    public int size() {
        return internalList.size();
    }

    @Override
    public Iterator<Account> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueAccountList // instanceof handles nulls
            && this.internalList.equals(((UniqueAccountList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


}

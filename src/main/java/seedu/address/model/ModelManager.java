package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Coin> filteredCoins;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredCoins = new FilteredList<>(this.addressBook.getCoinList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteCoin(Coin target) throws CoinNotFoundException {
        addressBook.removeCoin(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addCoin(Coin coin) throws DuplicateCoinException {
        addressBook.addCoin(coin);
        updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateCoin(Coin target, Coin editedCoin)
            throws DuplicateCoinException, CoinNotFoundException {
        requireAllNonNull(target, editedCoin);

        addressBook.updateCoin(target, editedCoin);
        indicateAddressBookChanged();
    }

    //=========== Filtered Coin List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Coin} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Coin> getFilteredCoinList() {
        return FXCollections.unmodifiableObservableList(filteredCoins);
    }

    @Override
    public void updateFilteredCoinList(Predicate<Coin> predicate) {
        requireNonNull(predicate);
        filteredCoins.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredCoins.equals(other.filteredCoins);
    }

}

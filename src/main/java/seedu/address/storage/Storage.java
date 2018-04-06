package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.CoinBookChangedEvent;
import seedu.address.commons.events.model.RuleBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCoinBook;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CoinBookStorage, RuleBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getCoinBookFilePath();

    @Override
    Optional<ReadOnlyCoinBook> readCoinBook() throws DataConversionException, IOException;

    @Override
    void saveCoinBook(ReadOnlyCoinBook addressBook) throws IOException;

    @Override
    String getRuleBookFilePath();

    @Override
    Optional<ReadOnlyRuleBook> readRuleBook() throws DataConversionException, IOException;

    @Override
    void saveRuleBook(ReadOnlyRuleBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleCoinBookChangedEvent(CoinBookChangedEvent cbce);

    void handleRuleBookChangedEvent(RuleBookChangedEvent rbce);
}

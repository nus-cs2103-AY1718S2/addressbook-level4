//@@author Jason1im
package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Account;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.Account}.
 */
public interface AccountDataStorage {

    /**
     * Returns the account data stored in the hard disk.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Account> readAccountData() throws DataConversionException, IOException;

    /**
     * @see #readAccountData()
     */
    Optional<Account> readAccountData(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link Account} to the storage.
     * @param accountData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccountData(Account accountData) throws IOException;

    /**
     * @see #saveAccountData(Account)
     */
    void saveAccountData(Account accountData, String filePath) throws IOException;
}

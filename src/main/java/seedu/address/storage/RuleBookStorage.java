package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.RuleBook;

/**
 * Represents a storage for {@link RuleBook}.
 */
public interface RuleBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getRuleBookFilePath();

    /**
     * Returns RuleBook data as a {@link ReadOnlyRuleBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRuleBook> readRuleBook() throws DataConversionException, IOException;

    /**
     * @see #getRuleBookFilePath()
     */
    Optional<ReadOnlyRuleBook> readRuleBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRuleBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRuleBook(ReadOnlyRuleBook addressBook) throws IOException;

    /**
     * @see #saveRuleBook(ReadOnlyRuleBook)
     */
    void saveRuleBook(ReadOnlyRuleBook addressBook, String filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyRuleBook} to a fixed temporary location.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupRuleBook(ReadOnlyRuleBook addressBook) throws IOException;

}

package seedu.address.login;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;

//@@author ngshikang
/**
 * Storage for UserPass
 */
public interface UserPassStorage {
    /**
     * Returns the file path of the UserPass data file.
     */
    String getUserPassFilePath();

    /**
     * Returns UserPass data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<HashMap> readUserPassMap() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.login.UserPass} to the storage.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPassMap() throws IOException;

    /**
     * Provide similar functionality as "put" in underlying HashMap to insert new UserPass to storage
     */
    void put(UserPass userPass);

    /**
     * Provide similar functionality as "containsKey" in underlying HashMap to insert new UserPass to storage
     */
    boolean containsKey(String username);

    /**
     * Provide similar functionality as "get" in underlying HashMap to insert new UserPass to storage
     */
    String get(String username);

}

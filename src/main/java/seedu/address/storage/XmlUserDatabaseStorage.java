package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyUserDatabase;

//@@author kaisertanqr
/**
 * A class to access UserDatabase data stored as an xml file on the hard disk.
 */
public class XmlUserDatabaseStorage implements UserDatabaseStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUserDatabaseStorage.class);

    private String filePath;

    public XmlUserDatabaseStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getUserDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
        return readUserDatabase(filePath);
    }

    /**
     * Similar to {@link #readUserDatabase()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyUserDatabase> readUserDatabase(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File usersFile = new File(filePath);

        if (!usersFile.exists()) {
            logger.info("Users file "  + usersFile + " not found");
            return Optional.empty();
        }

        XmlSerializableUserDatabase xmlUsers = XmlFileStorage.loadUsersFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlUsers.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + usersFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {
        saveUserDatabase(userDatabase, filePath);
    }

    /**
     * Similar to {@link #saveUserDatabase (ReadOnlyUserDatabase)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, String filePath) throws IOException {
        requireNonNull(userDatabase);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveUsersToFile(file, new XmlSerializableUserDatabase(userDatabase));
    }

}


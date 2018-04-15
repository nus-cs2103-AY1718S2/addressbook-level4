//@@author Jason1im
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
import seedu.address.model.Account;

/**
 * A class to access the account data stored as an xml file on the hard disk.
 */
public class XmlAccountDataStorage implements AccountDataStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlAccountDataStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<Account> readAccountData() throws DataConversionException, IOException {
        return readAccountData(filePath);
    }

    /**
     * Similar to {@link #readAccountData()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<Account> readAccountData(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File accountDataFile = new File(filePath);

        if (!accountDataFile.exists()) {
            logger.info("AccountData file "  + accountDataFile + " not found");
            return Optional.empty();
        }

        XmlAdaptedAccount xmlAccountData =  XmlFileStorage.loadAccountDataFromFile(new File(filePath));
        try {
            return Optional.of(xmlAccountData.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + accountDataFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAccountData(Account accountData) throws IOException {
        saveAccountData(accountData, filePath);
    }

    /**
     * Similar to {@link #saveAccountData(Account)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveAccountData(Account accountData, String filePath) throws IOException {
        requireNonNull(accountData);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlAdaptedAccount(accountData));
    }
}

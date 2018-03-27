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
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.SecurityUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Password;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlAddressBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException,
                                                                WrongPasswordException {
        return readAddressBook(filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Password password) throws DataConversionException, IOException,
            WrongPasswordException {
        return readAddressBook(filePath, password);
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException,
                                                                                 IOException, WrongPasswordException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }
        File file = new File(filePath);
        SecurityUtil.decrypt(file);
        XmlSerializableAddressBook xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(file);
        SecurityUtil.encrypt(file);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + addressBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath, Password password)
            throws DataConversionException, IOException, WrongPasswordException {
        requireNonNull(filePath);
        requireNonNull(password);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }
        File file = new File(filePath);
        try {
            SecurityUtil.decrypt(file, password.getPassword());
        } catch (WrongPasswordException e) {
            SecurityUtil.decrypt(file, password.getPrevPassword());
        }
        XmlSerializableAddressBook xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(file);
        SecurityUtil.encrypt(file, password.getPassword());
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + addressBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Imports the specified {@code AddressBook} from the filepath to the current {@code AddressBook}.
     *
     * @param filePath      location of the specified AddressBook. Cannot be null
     * @param addressBook   current existing AddressBook
     * @return              modified AddressBook
     * @throws DataConversionException if the file is not in the correct format.
     */
    public AddressBook importAddressBook(String filePath, AddressBook addressBook, byte[] password)
            throws DataConversionException, IOException, WrongPasswordException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            throw new FileNotFoundException();
        }
        SecurityUtil.decrypt(new File(filePath), password);
        XmlSerializableAddressBook xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return xmlAddressBook.addToAddressBook(addressBook);
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + addressBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException, WrongPasswordException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException,
                                                                            WrongPasswordException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        try {
            SecurityUtil.decrypt(file, addressBook.getPassword().getPassword());
        } catch (WrongPasswordException e) {
            logger.info("Current Password don't work, trying previous password.");
            SecurityUtil.decrypt(file, addressBook.getPassword().getPrevPassword());
        }
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
        SecurityUtil.encrypt(file, addressBook.getPassword().getPassword());
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException, WrongPasswordException {
        saveAddressBook(addressBook, filePath + ".backup");
    }
}

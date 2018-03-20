package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.tag.Tag;

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
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        XmlSerializableAddressBook xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + addressBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBookBackup() throws DataConversionException, IOException {
        return readAddressBook(filePath + ".backup");
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBookBackup(String filePath) throws DataConversionException,
                                                                                       IOException {
        if (Objects.isNull(filePath)) {
            throw new NullPointerException();
        } else {
            return readAddressBook(filePath + ".backup");
        }
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        if (Objects.isNull(filePath)) {
            throw new NullPointerException();
        } else {
            saveAddressBook(addressBook, filePath + ".backup");
        }
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        if (Objects.isNull(filePath)) {
            throw new NullPointerException();
        } else {
            saveAddressBook(addressBook, filePath + ".backup");
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));

        saveTagColors(addressBook);
    }

    /**
     * Save the tags and their specified colors from {@code addressBook}
     */
    private void saveTagColors(ReadOnlyAddressBook addressBook) throws IOException {
        File oldFile = new File(Tag.TAG_COLOR_FILE_PATH);
        oldFile.delete();
        File newFile = new File(Tag.TAG_COLOR_FILE_PATH);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(Tag.TAG_COLOR_FILE_PATH, "UTF-8");
            List<Tag> tags = addressBook.getTagList();
            for (Tag tag : tags) {
                writer.write(tag.name + ":" + tag.color + "\n");
            }
        } catch (FileNotFoundException fnfe) {
            throw new AssertionError("Tag color file not found. This should never happen.\n");
        } catch (UnsupportedEncodingException uee) {
            throw new AssertionError("UTF-8 encoding not supported.\n");
        }
        writer.close();
    }

}

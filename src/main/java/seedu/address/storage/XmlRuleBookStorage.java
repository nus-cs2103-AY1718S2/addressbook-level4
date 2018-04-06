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
import seedu.address.model.ReadOnlyRuleBook;

/**
 * A class to access RuleBook data stored as an xml file on the hard disk.
 */
public class XmlRuleBookStorage implements RuleBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRuleBookStorage.class);

    private static final String backupFilePath = "data/backup.xml";

    private String filePath;

    public XmlRuleBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getRuleBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRuleBook> readRuleBook() throws DataConversionException, IOException {
        return readRuleBook(filePath);
    }

    /**
     * Similar to {@link #readRuleBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRuleBook> readRuleBook(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("RuleBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        XmlSerializableRuleBook xmlAddressBook =
                XmlFileStorage.loadDataFromSaveFile(new File(filePath), XmlSerializableRuleBook.class);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + addressBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRuleBook(ReadOnlyRuleBook addressBook) throws IOException {
        saveRuleBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveRuleBook(ReadOnlyRuleBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRuleBook(ReadOnlyRuleBook ruleBook, String filePath) throws IOException {
        requireNonNull(ruleBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableRuleBook(ruleBook));
    }

    @Override
    public void backupRuleBook(ReadOnlyRuleBook addressBook) throws IOException {
        saveRuleBook(addressBook, backupFilePath);
    }

}

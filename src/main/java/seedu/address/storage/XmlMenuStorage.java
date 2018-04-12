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
import seedu.address.model.ReadOnlyMenu;

/**
 * A class to access Menu data stored as an xml file on the hard disk.
 */
public class XmlMenuStorage implements MenuStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlMenuStorage.class);

    private String filePath;

    public XmlMenuStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getMenuFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException {
        return readMenu(filePath);
    }

    /**
     * Similar to {@link #readMenu()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMenu> readMenu(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File menuFile = new File(filePath);

        if (!menuFile.exists()) {
            logger.info("Menu file "  + menuFile + " not found");
            return Optional.empty();
        }

        XmlSerializableMenu xmlMenu = XmlFileStorage.loadMenuDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlMenu.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + menuFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu) throws IOException {
        saveMenu(menu, filePath);
    }

    /**
     * Similar to {@link #saveMenu(ReadOnlyMenu)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveMenu(ReadOnlyMenu menu, String filePath) throws IOException {
        requireNonNull(menu);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveMenuDataToFile(file, new XmlSerializableMenu(menu));
    }

}

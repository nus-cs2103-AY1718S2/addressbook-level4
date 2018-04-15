package seedu.progresschecker.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.progresschecker.commons.exceptions.DataConversionException;
import seedu.progresschecker.commons.util.XmlUtil;

/**
 * Stores progresschecker data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given progresschecker data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableProgressChecker progressChecker)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, progressChecker);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns ProgressChecker in the file or an empty ProgressChecker
     */
    public static XmlSerializableProgressChecker loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableProgressChecker.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

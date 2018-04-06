package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores book data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given book data to the specified file.
     */
    public static <T> void saveDataToFile(File file, T data)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, data);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static <T> T loadDataFromSaveFile(File file, Class<T> classToConvert)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, classToConvert);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

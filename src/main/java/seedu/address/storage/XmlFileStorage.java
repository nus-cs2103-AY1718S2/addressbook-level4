package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores bookshelf data in an XML file
 */
public class XmlFileStorage {

    /**
     * Saves the given bookshelf data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableBookShelf bookShelf)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, bookShelf);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns book shelf in the file or an empty book shelf.
     */
    public static XmlSerializableBookShelf loadDataFromSaveFile(File file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableBookShelf.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores deskboard data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given deskboard data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableDeskBoard deskBoard)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, deskBoard);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableDeskBoard loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableDeskBoard.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

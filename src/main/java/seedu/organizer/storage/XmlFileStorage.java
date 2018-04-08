package seedu.organizer.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.organizer.commons.exceptions.DataConversionException;
import seedu.organizer.commons.util.XmlUtil;

/**
 * Stores organizer data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given organizer data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableOrganizer organizer)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, organizer);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns organizer in the file or an empty organizer
     */
    public static XmlSerializableOrganizer loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableOrganizer.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores movieplanner data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given movieplanner data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableMoviePlanner moviePlanner)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, moviePlanner);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns movie planner in the file or an empty movie planner
     */
    public static XmlSerializableMoviePlanner loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableMoviePlanner.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

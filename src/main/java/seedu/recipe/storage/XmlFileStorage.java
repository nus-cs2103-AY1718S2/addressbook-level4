package seedu.recipe.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.commons.util.XmlUtil;

/**
 * Stores recipebook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given recipebook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableRecipeBook recipeBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, recipeBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns recipe book in the file or an empty recipe book
     */
    public static XmlSerializableRecipeBook loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableRecipeBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}

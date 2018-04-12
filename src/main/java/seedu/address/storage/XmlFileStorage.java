package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook data, customerstats data and menu data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableAddressBook addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableAddressBook loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    //@@author Wuhao-ooo
    /**
     * Saves the given customer stats data to the specified file.
     */
    public static void saveCustomerDataToFile(File file, XmlSerializableCustomerStats customerStats)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, customerStats);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns customer stats in the file or an empty address book
     */
    public static XmlSerializableCustomerStats loadCustomerDataFromSaveFile(File file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCustomerStats.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
    //@@author

    //@@author ZacZequn
    /**
     * Saves the given menu data to the specified file.
     */
    public static void saveMenuDataToFile(File file, XmlSerializableMenu menu)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, menu);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns menu stats in the file or an empty address book
     */
    public static XmlSerializableMenu loadMenuDataFromSaveFile(File file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableMenu.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
    //@@author

}

package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook data and calendar data in separate XML file.
 */
public class XmlFileStorage {
    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveAddressBookDataToFile(File file, XmlSerializableAddressBook addressBook)
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
    public static XmlSerializableAddressBook loadAddressBookDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given calendarManager data to the specified file.
     */
    //@@author SuxianAlicia
    public static void saveCalendarManagerDataToFile(File file, XmlSerializableCalendarManager calendarManager)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, calendarManager);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns calendar manager data in the file or an empty address book
     */
    public static XmlSerializableCalendarManager loadCalendarManagerDataFromSaveFile(File file)
            throws DataConversionException, FileNotFoundException {

        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCalendarManager.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
    //@@author
}

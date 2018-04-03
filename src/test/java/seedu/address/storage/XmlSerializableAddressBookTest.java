package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalCalendarEvents;
import seedu.address.testutil.TypicalOrders;
import seedu.address.testutil.TypicalPersons;

public class XmlSerializableAddressBookTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableAddressBookTest/");
    private static final File TYPICAL_EVENTS_FILE = new File(TEST_DATA_FOLDER + "typicalEventsAddressBook.xml");
    private static final File TYPICAL_PERSONS_FILE = new File(TEST_DATA_FOLDER + "typicalPersonsAddressBook.xml");
    private static final File TYPICAL_ORDERS_FILE = new File(TEST_DATA_FOLDER + "typicalOrdersAddressBook.xml");
    private static final File INVALID_PERSON_FILE = new File(TEST_DATA_FOLDER + "invalidPersonAddressBook.xml");
    private static final File INVALID_GROUP_FILE = new File(TEST_DATA_FOLDER + "invalidGroupAddressBook.xml");
    private static final File INVALID_PREFERENCE_FILE = new File(TEST_DATA_FOLDER + "invalidPreferenceAddressBook.xml");
    private static final File INVALID_ORDER_FILE = new File(TEST_DATA_FOLDER + "invalidOrderAddressBook.xml");
    private static final File INVALID_EVENT_FILE = new File(TEST_DATA_FOLDER + "invalidEventsAddressBook.xml");


    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    //@@author amad-person
    @Test
    public void toModelType_typicalOrdersFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ORDERS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalOrdersAddressBook = TypicalOrders.getTypicalAddressBookWithOrders();
        assertEquals(addressBookFromFile, typicalOrdersAddressBook);
    }
    //@@author

    @Test
    public void toModelType_typicalEventsFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EVENTS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalEventsAddressBook = TypicalCalendarEvents.getTypicalAddressBookWithEvents();
        assertEquals(addressBookFromFile, typicalEventsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidGroupFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_GROUP_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidPreferenceFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_PREFERENCE_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    //@@author amad-person
    @Test
    public void toModelType_invalidOrderFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ORDER_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
    //@@author

    @Test
    public void toModelType_invalidEventsFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_EVENT_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}

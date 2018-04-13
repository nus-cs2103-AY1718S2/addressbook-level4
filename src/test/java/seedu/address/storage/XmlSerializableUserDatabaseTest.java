package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.UserDatabase;
import seedu.address.testutil.TypicalUsers;

//@@author kaisertanqr
public class XmlSerializableUserDatabaseTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableUserDatabaseTest/");
    private static final File TYPICAL_USERS_FILE = new File(TEST_DATA_FOLDER + "typicalUsersUserDatabase.xml");
    private static final File INVALID_USER_FILE = new File(TEST_DATA_FOLDER + "invalidUserUserDatabase.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableUserDatabase dataFromFile = XmlUtil.getDataFromFile(TYPICAL_USERS_FILE,
                XmlSerializableUserDatabase.class);
        UserDatabase userDatabaseFromFile = dataFromFile.toModelType();
        UserDatabase typicalUsersAddressBook = TypicalUsers.getTypicalUserDatabase();
        assertEquals(userDatabaseFromFile, typicalUsersAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableUserDatabase dataFromFile = XmlUtil.getDataFromFile(INVALID_USER_FILE,
                XmlSerializableUserDatabase.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}

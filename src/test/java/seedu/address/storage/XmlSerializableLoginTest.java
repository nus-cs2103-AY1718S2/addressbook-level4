package seedu.address.storage;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.LoginManager;
import seedu.address.testutil.TypicalUsers;

//@@author Pearlissa
public class XmlSerializableLoginTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableLoginTest/");
    private static final File TYPICAL_USERS_FILE = new File(TEST_DATA_FOLDER + "typicalUsersLogin.xml");
    private static final File INVALID_USER_FILE = new File(TEST_DATA_FOLDER + "invalidUserLogin.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalUsersFile_success() throws Exception {
        XmlSerializableLogin dataFromFile = XmlUtil.getDataFromFile(TYPICAL_USERS_FILE,
                XmlSerializableLogin.class);
        LoginManager loginFromFile = dataFromFile.toModelType();
        LoginManager typicalUsersLogin = TypicalUsers.getTypicalLoginManager();
        assertTrue(checkUsers(loginFromFile, typicalUsersLogin));
    }

    /**
     * Checks if the info on the 2 Login Managers are the same.
     * @param loginManager1
     * @param loginManager2
     * @return
     */
    private boolean checkUsers(LoginManager loginManager1, LoginManager loginManager2) {
        if (loginManager1.getUserList().size() != loginManager2.getUserList().size()) {
            return false;
        }
        for (int i = 0; i < loginManager1.getUserList().size(); i++) {
            if (!loginManager1.getUserList().get(i).getUsername().getUsername()
                    .equals(loginManager2.getUserList().get(i).getUsername().getUsername())
                    || !loginManager1.getUserList().get(i).getPassword().getPassword()
                    .equals(loginManager2.getUserList().get(i).getPassword().getPassword())) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void toModelType_invalidUserFile_throwsIllegalValueException() throws Exception {
        XmlSerializableLogin dataFromFile = XmlUtil.getDataFromFile(INVALID_USER_FILE,
                XmlSerializableLogin.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}

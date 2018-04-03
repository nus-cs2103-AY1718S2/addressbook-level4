package seedu.address.login;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.storage.JsonUserPassStorage;

//@@author ngshikang
public class UserPassStorageTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void addUserPassToStorage() {
        UserPass testProfile = new UserPass("user", "pass");
        JsonUserPassStorage userPassStorage = new JsonUserPassStorage(getTempFilePath("tempUserPass"));
        userPassStorage.put(testProfile);
        Assert.assertTrue(userPassStorage.containsKey(testProfile.getUsername()));
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

}

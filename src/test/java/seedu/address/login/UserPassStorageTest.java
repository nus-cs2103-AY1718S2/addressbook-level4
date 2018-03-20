package seedu.address.login;

import org.junit.Assert;
import org.junit.Test;

public class UserPassStorageTest {

    @Test
    public void addUserPassToStorage(){
        UserPass testProfile = new UserPass("user", "pass");
        UserPassStorage testStorage = new UserPassStorage();
        testStorage.put(testProfile);
        Assert.assertTrue(testStorage.containsKey(testProfile.getUsername()));
    }

}

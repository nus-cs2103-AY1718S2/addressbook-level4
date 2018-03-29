package seedu.address.login;

import org.junit.Assert;
import org.junit.Test;

//@@author ngshikang
public class UserPassTest {

    @Test
    public void createUserPass() {
        UserPass testProfile = new UserPass("user", "pass");
        Assert.assertEquals(testProfile.getUsername(), "user");
        Assert.assertEquals(testProfile.getPassword(), UserPass.hash("pass"));
    }

}

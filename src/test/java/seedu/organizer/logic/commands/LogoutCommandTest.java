package seedu.organizer.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.user.User;

//@@author dominickenn
public class LogoutCommandTest {

    private Model model = new ModelManager();
    private User user = new User("admin", "admin");

    @Before
    public void setUp() throws Exception {
        model.addUser(user);
    }

    @Test
    public void execute() throws Exception {
        //Current user should be null
        assertEquals(getCurrentlyLoggedInUser(), null);

        //Current user should be admin
        model.loginUser(user);
        assertEquals(getCurrentlyLoggedInUser(), user);

        //Current user should be null;
        model.logout();
        assertEquals(getCurrentlyLoggedInUser(), null);
    }
}

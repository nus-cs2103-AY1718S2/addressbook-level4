//@@author Jason1im
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountsManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        AccountsManager accountsManager = new AccountsManager();
        thrown.expect(UnsupportedOperationException.class);
        accountsManager.getAccountList().remove(0);
    }
}

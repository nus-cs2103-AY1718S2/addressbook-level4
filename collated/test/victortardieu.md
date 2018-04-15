# victortardieu
###### /java/seedu/address/logic/commands/ClearAccountCommandTest.java
``` java

public class ClearAccountCommandTest {

    @Test
    public void execute_empty_accountList() throws DuplicateAccountException {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearAccountCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAccountList_success() throws DuplicateAccountException {
        Model model = new ModelManager();
        model = getTypicalAccountList();
        assertCommandSuccess(prepareCommand(model), model, ClearAccountCommand.MESSAGE_SUCCESS,
            model);
    }

    private ClearAccountCommand prepareCommand(Model model) throws DuplicateAccountException {
        ClearAccountCommand command = new ClearAccountCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/DeleteAccountCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.HARRY;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteAccountCommandTest {

    private Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void execute_foundUsername_success() throws Exception {
        Model actualModel = new ModelManager();
        actualModel = getTypicalAccountList();
        Model expectedModel = new ModelManager();
        expectedModel = getTypicalAccountList();
        expectedModel.deleteAccount(HARRY);
        assertCommandSuccess(prepareCommand("harry123", actualModel), actualModel,
                String.format(DeleteAccountCommand.MESSAGE_DELETE_ACCOUNT_SUCCESS, HARRY), expectedModel);

    }

    @Test
    public void execute_usernameNotFound_failure()  throws Exception {
        Model model = new ModelManager();
        model = getTypicalAccountList();
        assertCommandFailure(prepareCommand("harry1234", model), model,
                "Account does not exist");
    }


    /**
     * Returns a {@code DeleteAccountCommand} with the parameter {@code username}.
     */
    private DeleteAccountCommand prepareCommand(String username, Model model) {
        DeleteAccountCommand deleteAccountCommand = new DeleteAccountCommand(username);
        deleteAccountCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAccountCommand;
    }

}
```
###### /java/seedu/address/model/account/UsernameTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UsernameTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void isValidUsername() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        //invalid
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername("123")); // too short
        assertFalse(Username.isValidUsername("abc")); // too short
        assertFalse(Username.isValidUsername("!!!")); // too short and non-word characters
        assertFalse(Username.isValidUsername("!!!!!!")); // non-word characters
        assertFalse(Username.isValidUsername("abcasj!")); // too short and non-word characters
        assertFalse(Username.isValidUsername(""));

        //valid
        assertTrue(Username.isValidUsername("abcde"));
        assertTrue(Username.isValidUsername("banana"));
        assertTrue(Username.isValidUsername("addressbook"));
        assertTrue(Username.isValidUsername("abcde123"));
        assertTrue(Username.isValidUsername("FHAIgasjd123987514"));
        assertTrue(Username.isValidUsername("123123123123"));

    }

    @Test
    public void getUsername() {
        String usernameString = "username";
        Username p = new Username(usernameString);
        assertEquals(usernameString, p.getUsername());
    }

    @Test
    public void equals() {
        Username p1 = new Username("username1");
        Username p1copy = new Username("username1");
        Username p2 = new Username("username2");

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p1.equals(p1copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }
}
```
###### /java/seedu/address/model/account/PasswordTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void isValidPassword() {
        // null pointer
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        //invalid
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword("123")); // too short
        assertFalse(Password.isValidPassword("abc")); // too short
        assertFalse(Password.isValidPassword("!!!")); // too short and non-word characters
        assertFalse(Password.isValidPassword("!!!!!!")); // non-word characters
        assertFalse(Password.isValidPassword("abcasj!")); // too short and non-word characters
        assertFalse(Password.isValidPassword(""));

        //valid
        assertTrue(Password.isValidPassword("abcde"));
        assertTrue(Password.isValidPassword("banana"));
        assertTrue(Password.isValidPassword("addressbook"));
        assertTrue(Password.isValidPassword("abcde123"));
        assertTrue(Password.isValidPassword("FHAIgasjd123987514"));
        assertTrue(Password.isValidPassword("123123123123"));


    }

    @Test
    public void getPassword() {
        String passwordString = "password";
        Password p = new Password(passwordString);
        assertEquals(passwordString, p.getPassword());
    }

    @Test
    public void equals() {
        Password p1 = new Password("password1");
        Password p1copy = new Password("password1");
        Password p2 = new Password("password2");

        //equal with itself
        assertTrue(p1.equals(p1));

        //equal with an other object with same state
        assertTrue(p1.equals(p1copy));

        //not equal with null
        assertFalse(p1.equals(null));

        //not equal with other type
        assertFalse(p1.equals(1));

        //not equal with same type with different state
        assertFalse(p1.equals(p2));
    }
}
```
###### /java/seedu/address/testutil/TypicalAccounts.java
``` java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.Account;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 * A utility class containing a list of {@code Account} objects to be used in tests.
 */
public class TypicalAccounts {

    public static final Account HARRY = new AccountBuilder()
        .withName("Harry Potter")
        .withCredential("harry123", "harry123")
        .withMatricNumber("A1234567H")
        .withPrivilegeLevel("1").build();

    public static final Account JERRY = new AccountBuilder()
        .withName("Jerry Morgan ")
        .withCredential("jerry123", "jack123")
        .withMatricNumber("A1234567J")
        .withPrivilegeLevel("2").build();

    public static final Account TOM = new AccountBuilder()
        .withName("Tom Hanks")
        .withCredential("tom123", "tom123")
        .withMatricNumber("A1234567T")
        .withPrivilegeLevel("1").build();

    public static final Account EMMA = new AccountBuilder()
        .withName("Emma Thorne")
        .withCredential("emma123", "emma123")
        .withMatricNumber("A1234567E")
        .withPrivilegeLevel("2").build();

    public static final Account LARY = new AccountBuilder()
        .withName("Lary Knot")
        .withCredential("lary123", "lary123")
        .withMatricNumber("A1234567L")
        .withPrivilegeLevel("1").build();

    public static final Account MARIE = new AccountBuilder()
        .withName("Marie Johnson")
        .withCredential("marie", "marie123")
        .withMatricNumber("A1234567M")
        .withPrivilegeLevel("1").build();

    public static final Account NICOLE = new AccountBuilder()
        .withName("Nicole Soley")
        .withCredential("nicole", "nicole123")
        .withMatricNumber("A1234567N")
        .withPrivilegeLevel("1").build();

    private TypicalAccounts() {
    } // prevents instantiation

    public static Model getTypicalAccountList() {
        Model model = new ModelManager();
        for (Account account : getTypicalAccounts()) {
            try {
                model.addAccount(account);
            } catch (DuplicateAccountException e) {
                throw new AssertionError("not possible");
            }
        }
        return model;
    }

    public static UniqueAccountList getTypicalAccountListUniqueAccountList() {
        UniqueAccountList uniqueAccountList = new UniqueAccountList();
        for (Account account : getTypicalAccounts()) {
            try {
                uniqueAccountList.add(account);
            } catch (DuplicateAccountException e) {
                throw new AssertionError("not possible");
            }
        }
        return uniqueAccountList;
    }


    public static List<Account> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(HARRY, JERRY, TOM, EMMA, LARY, MARIE, NICOLE));
    }
}
```
###### /java/seedu/address/testutil/AccountBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.model.account.Account;
import seedu.address.model.account.Credential;
import seedu.address.model.account.MatricNumber;
import seedu.address.model.account.Name;
import seedu.address.model.account.PrivilegeLevel;

/**
 * A utility class to help with building Account objects.
 */
public class AccountBuilder {

    public static final String DEFAULT_NAME = "Victor Tardieu";
    public static final String DEFAULT_MATRIC_NUMBER = "A1234567N";
    public static final String DEFAULT_PRIVILEGE_LEVEL = "2";
    public static final String DEFAULT_USERNAME = "victor";
    public static final String DEFAULT_PASSWORD = "victor123";

    private Name name;
    private Credential credential;
    private MatricNumber matricNumber;
    private PrivilegeLevel privilegeLevel;

    /**
     * Constructor for account with default values
     */
    public AccountBuilder() {
        name = new Name(DEFAULT_NAME);
        credential = new Credential(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        matricNumber = new MatricNumber(DEFAULT_MATRIC_NUMBER);
        privilegeLevel = new PrivilegeLevel(Integer.parseInt(DEFAULT_PRIVILEGE_LEVEL));
    }

    /**
     * Sets the {@code Name} of the {@code Account} that we are building.
     *
     * @param name
     * @return
     */
    public AccountBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Credential} of the {@code Account} that we are building.
     *
     * @param username
     * @param password
     * @return
     */
    public AccountBuilder withCredential(String username, String password) {
        this.credential = new Credential(username, password);
        return this;
    }

    /**
     * Sets the {@code MatricNumber} of the {@code Account} that we are building.
     *
     * @param matricNumber
     * @return
     */
    public AccountBuilder withMatricNumber(String matricNumber) {
        this.matricNumber = new MatricNumber(matricNumber);
        return this;
    }

    /**
     * Sets the {@code PrivilegeLevel} of the {@code Account} that we are building.
     *
     * @param privilegeLevel
     * @return
     */
    public AccountBuilder withPrivilegeLevel(String privilegeLevel) {
        this.privilegeLevel = new PrivilegeLevel(Integer.parseInt(privilegeLevel));
        return this;
    }

    /**
     * Create an account
     *
     * @return
     */
    public Account build() {
        return new Account(name, credential, matricNumber, privilegeLevel);
    }
}
```

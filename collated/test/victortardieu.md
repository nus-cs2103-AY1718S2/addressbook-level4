# victortardieu
###### \java\seedu\address\logic\commands\ClearAccountCommandTest.java
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
###### \java\seedu\address\testutil\AccountBuilder.java
``` java
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
###### \java\seedu\address\testutil\TypicalAccounts.java
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

package seedu.address.testutil;

import seedu.address.model.account.Credential;
import seedu.address.model.account.MatricNumber;
import seedu.address.model.account.Name;
import seedu.address.model.account.Password;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.Username;

public class AccountBuilder {

    public static final String DEFAULT_NAME = "Victor Tardieu";
    public static final String DEFAULT_MATRIC_NUMBER = "A1234567N";
    public static final String DEFAULT_PRIVILEGE_LEVEL = "2";
    public static final String DEFAULT_USERNAME = "victor";
    public static final String DEFAULT_PASSWORD = "victor123";
    public static final String DEFAULT_CREDENTIAL = null;

    private Name name;
    private MatricNumber matricNumber;
    private Credential credential;
    private PrivilegeLevel privilegeLevel;
    private Username username;
    private Password password;

    public AccountBuilder() {
        name = new Name(DEFAULT_NAME);
        matricNumber = new MatricNumber(DEFAULT_MATRIC_NUMBER);
        credential = new Credential(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        privilegeLevel = new PrivilegeLevel(Integer.parseInt(DEFAULT_PRIVILEGE_LEVEL));
    }

    public AccountBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public AccountBuilder withMatricNumber(String matricNumber) {
        this.matricNumber = new MatricNumber(matricNumber);
        return this;
    }

    public AccountBuilder withCredential(String credential) {
        this.credential = new
    }
}

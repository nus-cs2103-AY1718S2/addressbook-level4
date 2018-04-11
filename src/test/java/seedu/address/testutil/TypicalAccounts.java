package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.account.Account;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.tag.Tag;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.Account;

public class TypicalAccounts {

    public static final Account HARRY = new AccountBuilder()
            .withName("Harry Potter")
            .withCredential("harry", "harry123")
            .withMatricNumber("A1234567H")
            .withPrivilegeLevel("1").build();

    public static final Account JACK = new AccountBuilder()
            .withName("Jack Morgan ")
            .withCredential("jack", "jack123")
            .withMatricNumber("A1234567J")
            .withPrivilegeLevel("2").build();

    public static final Account TOM = new AccountBuilder()
            .withName("Tom Hanks")
            .withCredential("tom", "tom123")
            .withMatricNumber("A1234567T")
            .withPrivilegeLevel("1").build();

    public static final Account EMMA = new AccountBuilder()
            .withName("Emma Thorne")
            .withCredential("emma", "emma123")
            .withMatricNumber("A1234567E")
            .withPrivilegeLevel("2").build();

    public static final Account LARY = new AccountBuilder()
            .withName("Lary Knot")
            .withCredential("lary", "lary123")
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

    private TypicalAccounts() {} // prevents instantiation

    public static UniqueAccountList getTypicalAccountList() {
        UniqueAccountList al = new UniqueAccountList();
        for (Account account : getTypicalAccounts()) {
            try {
                al.add(account);
            } catch (DuplicateAccountException e) {
                throw new AssertionError("not possible");
            }
        }
        return al;
    }


    public static List<Account> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(HARRY, JACK, TOM, EMMA, LARY, MARIE, NICOLE));
    }
}

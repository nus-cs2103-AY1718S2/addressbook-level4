package seedu.address.testutil;

import seedu.address.model.account.Account;

public class TypicalAccountList {

    private static TypicalAccountList ourInstance = new TypicalAccountList();

    public static TypicalAccountList getInstance() {
        return ourInstance;
    }

    private TypicalAccountList() {
    }

    public static final Account HARRY = new

    public static final Book ALICE = new BookBuilder().withTitle("Alice Pauline")
            .withAuthor("Pauline Alice")
            .withAvail("Available")
            .withIsbn("85355255")
            .withTags("friends").build();
}

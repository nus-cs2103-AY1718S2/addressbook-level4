package seedu.address.testutil;

public class TypicalAccountList {
    private static TypicalAccountList ourInstance = new TypicalAccountList();

    public static TypicalAccountList getInstance() {
        return ourInstance;
    }

    private TypicalAccountList() {
    }
}

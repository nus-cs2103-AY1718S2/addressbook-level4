package seedu.progresschecker.testutil;

import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.credentials.Passcode;
import seedu.progresschecker.model.credentials.Repository;
import seedu.progresschecker.model.credentials.Username;

//@@author adityaa1998
/**
 * A utility class to help with building GitDetails objects.
 */
public class GitDetailsBuilder {

    public static final String DEFAULT_REPO = "adityaa1998/samplerepo-pr-practice";
    public static final String DEFAULT_USERNAME = "anminkang";
    public static final String DEFAULT_PASSCDE = "aditya2018";

    private Repository repository;
    private Username username;
    private Passcode passcode;

    public GitDetailsBuilder() {
        repository = new Repository(DEFAULT_REPO);
        username = new Username(DEFAULT_USERNAME);
        passcode = new Passcode(DEFAULT_PASSCDE);
    }

    /**
     * Initializes the GitDetailsBuilder with the data of {@code detailsToCopy}.
     */
    public GitDetailsBuilder (GitDetails detailsToCopy) {
        repository = detailsToCopy.getRepository();
        passcode = detailsToCopy.getPasscode();
        username = detailsToCopy.getUsername();
    }

    /**
     * Sets the {@code Repository} of the {@code GitDetails} that we are building.
     */
    public GitDetailsBuilder withRepository(String repository) {
        this.repository = new Repository(repository);
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code GitDetails} that we are building.
     */
    public GitDetailsBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Passcode} of the {@code GitDetails} that we are building.
     */
    public GitDetailsBuilder withPasscode(String passcode) {
        this.passcode = new Passcode(passcode);
        return this;
    }

    public GitDetails build() {
        return new GitDetails(username, passcode, repository);
    }
}

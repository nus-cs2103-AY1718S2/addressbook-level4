package seedu.progresschecker.model.credentials;

import static seedu.progresschecker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an Issue.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class GitDetails {

    private final Username username;
    private final Repository repository;
    private final Passcode passcode;

    /**
     * Every field must be present and not null.
     */
    public GitDetails(Username username, Repository repository, Passcode passcode) {
        requireAllNonNull(username, repository, passcode);
        this.username = username;
        this.repository = repository;
        this.passcode = passcode;
    }

    public Username getUsername() {
        return username;
    }

    public Repository getRepository() {
        return repository;
    }

    public Passcode getPasscode() {
        return passcode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.progresschecker.model.credentials.GitDetails)) {
            return false;
        }

        seedu.progresschecker.model.credentials.GitDetails otherGitDetails =
                (seedu.progresschecker.model.credentials.GitDetails) other;
        return otherGitDetails.getUsername().equals(this.getUsername())
                && otherGitDetails.getRepository().equals(this.getRepository())
                && otherGitDetails.getPasscode().equals(this.getPasscode());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, repository, passcode);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Username: ")
                .append(getUsername())
                .append(" Repository: ")
                .append(getRepository());
        return builder.toString();
    }

}


package seedu.progresschecker.model.credentials;

import static java.util.Objects.requireNonNull;

/**
 * Represents a github repository
 */
public class Repository {

    public static final String MESSAGE_ASSIGNEES_CONSTRAINTS =
            "Repository address cannot start from /";

    /*
     * The first character of the repository must not be a forward-slash,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public final String gitRepo;

    /**
     * Constructs a {@code Repository}.
     *
     * @param gitRepo A valid assignees.
     */
    public Repository(String gitRepo) {
        requireNonNull(gitRepo);
        this.gitRepo = gitRepo;
    }

    @Override
    public String toString() {
        return gitRepo;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.credentials.Repository // instanceof handles nulls
                && this.gitRepo.equals(((Repository) other).gitRepo)); // state check
    }

    @Override
    public int hashCode() {
        return gitRepo.hashCode();
    }

}

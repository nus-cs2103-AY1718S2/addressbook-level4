package seedu.progresschecker.model.credentials;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

//@@author aditya1998
/**
 * Represents a github repository
 */
public class Repository {

    public static final String MESSAGE_REPOSITORY_CONSTRAINTS =
            "Repository address cannot start from /";

    /*
     * The first character of the repository must not be a forward-slash,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REPO_VALIDATION_REGEX = "^[-a-zA-Z0-9+&@#/%?=~_|!:,.;*]*[-a-zA-Z0-9+&@#/%=~_|*]";


    public final String gitRepo;

    /**
     * Constructs a {@code Repository}.
     *
     * @param gitRepo A valid assignees.
     */
    public Repository(String gitRepo) {
        requireNonNull(gitRepo);
        checkArgument(isValidRepository(gitRepo), MESSAGE_REPOSITORY_CONSTRAINTS);
        this.gitRepo = gitRepo;
    }

    /**
     * Returns true if a given string is a valid github repository.
     */
    public static boolean isValidRepository(String test) {
        return test.matches(REPO_VALIDATION_REGEX);
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

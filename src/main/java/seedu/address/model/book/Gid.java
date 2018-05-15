package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a book's google ID.
 * Guarantees: immutable.
 */
public class Gid {

    public final String gid;

    /**
     * Constructs a {@code Gid}.
     *
     * @param gid A book's google id.
     */
    public Gid(String gid) {
        requireNonNull(gid);
        this.gid = gid;
    }


    @Override
    public String toString() {
        return gid;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gid // instanceof handles nulls
                && this.gid.equals(((Gid) other).gid)); // state check
    }

    @Override
    public int hashCode() {
        return gid.hashCode();
    }
}

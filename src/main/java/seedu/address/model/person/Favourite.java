package seedu.address.model.person;

/**
 * Represent Person's favourite attribute i.e. whether the Person is in Favourites
 */
public class Favourite {
    public final boolean isFav;

    public Favourite(boolean isFav) {
        this.isFav = isFav;
    }

    @Override
    public String toString() {
        return Boolean.toString(isFav);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
            || obj instanceof Favourite
            && isFav == ((Favourite) obj).isFav;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isFav);
    }
}

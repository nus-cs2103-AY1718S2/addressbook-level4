package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.cinema.Cinema;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Cinema> PREDICATE_MATCHING_NO_CINEMAS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Cinema> toDisplay) {
        Optional<Predicate<Cinema>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredCinemaList(predicate.orElse(PREDICATE_MATCHING_NO_CINEMAS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Cinema... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Cinema} equals to {@code other}.
     */
    private static Predicate<Cinema> getPredicateMatching(Cinema other) {
        return cinema -> cinema.equals(other);
    }
}

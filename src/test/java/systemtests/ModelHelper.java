package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Person> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<Job> PREDICATE_MATCHING_NO_JOBS = unused -> false;

    /**
     * Updates {@code model}'s filtered person list to display only {@code toDisplay}.
     */
    public static void setFilteredPersonList(Model model, List<Person> toDisplay) {
        Optional<Predicate<Person>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * Updates {@code model}'s filtered job list to display only {@code toDisplay}.
     */
    public static void setFilteredJobList(Model model, List<Job> toDisplay) {
        Optional<Predicate<Job>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredJobList(predicate.orElse(PREDICATE_MATCHING_NO_JOBS));
    }

    /**
     * @see ModelHelper#setFilteredPersonList(Model, List)
     */
    public static void setFilteredPersonList(Model model, Person... toDisplay) {
        setFilteredPersonList(model, Arrays.asList(toDisplay));
    }

    //    /**
    //     * @see ModelHelper#setFilteredJobList(Model, List)
    //     */
    //    public static void setFilteredJobList(Model model, Job... toDisplay) {
    //        setFilteredJobList(model, Arrays.asList(toDisplay));
    //    }

    /**
     * Returns a predicate that evaluates to true if this {@code Person} equals to {@code other}.
     */
    private static Predicate<Person> getPredicateMatching(Person other) {
        return person -> person.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Job} equals to {@code other}.
     */
    private static Predicate<Job> getPredicateMatching(Job other) {
        return job -> job.equals(other);
    }
}

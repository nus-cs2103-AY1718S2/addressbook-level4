package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Person> PREDICATE_MATCHING_NO_PERSONS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Person> toDisplay) {
        Optional<Predicate<Person>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Person... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Updates {@code model}'s task filtered list to display only {@code toDisplay}
     */
    public static void setTaskFilteredList(Model mode, List<Task> toDisplay) {
        Optional<Predicate<Task>> predicate = toDisplay.stream().map(ModelHelper::getTaskPredicateMatching)
                .reduce(Predicate::or);
    }

    /**
     * @see ModelHelper@setTaskFilteredList(Model, List)
     */
    public static void setTaskFilteredList(Model model, Task... toDisplay) {
        setTaskFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Person} equals to {@code other}.
     */
    private static Predicate<Person> getPredicateMatching(Person other) {
        return person -> person.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Task} equals to {@code other}.
     */
    private static Predicate<Task> getTaskPredicateMatching(Task other) {
        return task -> task.equals(other);
    }
}

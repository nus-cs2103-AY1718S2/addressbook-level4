package seedu.address.model.person;

import java.util.function.Predicate;

//@@author tanhengyeow
/**
 * Immutable interface for all predicates in AllPredicate
 */
public interface FieldPredicate {
    Predicate<Person> getPredicate();
}

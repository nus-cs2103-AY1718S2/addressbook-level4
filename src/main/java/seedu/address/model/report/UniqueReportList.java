// @@author anh2111
package seedu.address.model.report;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of reports that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see #equals(Object)
 */
public class UniqueReportList implements Iterable<Report> {

    private final ObservableList<Report> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueReportList() {}

    /**
     * Creates a UniqueReportList using given reports.
     * Enforces no nulls.
     */
    public UniqueReportList(List<Report> reports) {
        requireAllNonNull(reports);
        internalList.addAll();

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all reports in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Report> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    public void setReports(UniqueReportList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setReports(List<Report>  reports) {
        requireAllNonNull(reports);
        final UniqueReportList replacement = new UniqueReportList();
        for (final Report report : reports) {
            replacement.add(report);
        }
        setReports(replacement);
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Report toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Tag to the list.
     *
     */
    public void add(Report toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes a Report from the list.
     *
     */
    public void remove(Report toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Report> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Report> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueReportList // instanceof handles nulls
                && this.internalList.equals(((UniqueReportList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueReportList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
// @@author

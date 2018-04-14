# SuxianAlicia-reused
###### \java\seedu\address\model\tag\UniqueGroupList.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of group tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Group#equals(Object)
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty GroupList.
     */
    public UniqueGroupList() {}

    /**
     * Creates a UniqueGroupList using given group tags.
     * Enforces no nulls.
     */
    public UniqueGroupList(Set<Group> groupTags) {
        requireAllNonNull(groupTags);
        internalList.addAll(groupTags);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all group tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Group> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Groups in this list with those in the argument group list.
     */
    public void setTags(Set<Group> groupTags) {
        requireAllNonNull(groupTags);
        internalList.setAll(groupTags);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every group in the argument list exists in this object.
     */
    public void mergeFrom(UniqueGroupList from) {
        final Set<Group> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(groupTag -> !alreadyInside.contains(groupTag))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Group as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Group to the list.
     *
     * @throws DuplicateGroupException if the Group to add is a duplicate of an existing Group in the list.
     */
    public void add(Group toAdd) throws DuplicateGroupException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes group from list if it exists.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
    }

    @Override
    public Iterator<Group> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && this.internalList.equals(((UniqueGroupList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueGroupList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateGroupException extends DuplicateDataException {
        public DuplicateGroupException() {
            super("Operation would result in duplicate groups");
        }
    }
}
```
###### \java\seedu\address\model\tag\UniquePreferenceList.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of preference tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Preference#equals(Object)
 */
public class UniquePreferenceList implements Iterable<Preference> {

    private final ObservableList<Preference> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PreferenceList.
     */
    public UniquePreferenceList() {}

    /**
     * Creates a UniquePreferenceList using given preference tags.
     * Enforces no nulls.
     */
    public UniquePreferenceList(Set<Preference> preferenceTags) {
        requireAllNonNull(preferenceTags);
        internalList.addAll(preferenceTags);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all preference tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Preference> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Preferences in this list with those in the argument preference tag list.
     */
    public void setTags(Set<Preference> preferenceTags) {
        requireAllNonNull(preferenceTags);
        internalList.setAll(preferenceTags);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every preference in the argument list exists in this object.
     */
    public void mergeFrom(UniquePreferenceList from) {
        final Set<Preference> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(preferenceTag -> !alreadyInside.contains(preferenceTag))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Preference as the given argument.
     */
    public boolean contains(Preference toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Preference to the list.
     *
     * @throws DuplicatePreferenceException if the Preference to add is a duplicate of an
     * existing Preference in the list.
     */
    public void add(Preference toAdd) throws DuplicatePreferenceException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePreferenceException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes preference from list if it exists
     */
    public void remove(Preference toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
    }

    @Override
    public Iterator<Preference> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Preference> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePreferenceList // instanceof handles nulls
                && this.internalList.equals(((UniquePreferenceList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniquePreferenceList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePreferenceException extends DuplicateDataException {
        protected DuplicatePreferenceException() {
            super("Operation would result in duplicate preferences");
        }
    }
}
```
###### \resources\view\CalendarEntryCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="entryTitle" styleClass="cell_big_label" text="\$first" />
            </HBox>
            <Label fx:id="startDate" styleClass="cell_small_label" text="\$startDate" />
            <Label fx:id="endDate" styleClass="cell_small_label" text="\$endDate" />
            <Label fx:id="timeDuration" styleClass="cell_small_label" text="\$timeDuration" />
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\CalendarEntryListPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="calendarEntryCardListView" VBox.vgrow="ALWAYS" />
</VBox>
```

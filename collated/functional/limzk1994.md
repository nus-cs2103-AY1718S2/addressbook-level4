# limzk1994
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all clients!";

    public SortCommand(){
    }
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        ObservableList<Person> shownList = model.getFilteredPersonList();
        if (shownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_PERSON_LIST_EMPTY);
        }
        model.sortFilteredPersonList(shownList);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/model/group/Group.java
``` java
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Person group should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String groupName;

    /**
     * Constructs a {@code Name}.
     *
     * @param group A valid group.
     */
    public Group(String group) {
        requireNonNull(group);
        checkArgument(isValidGroup(group), MESSAGE_GROUP_CONSTRAINTS);
        this.groupName = group;
    }

    /**
     * Returns true if a given string is a valid group.
     */
    public static boolean isValidGroup(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.groupName.equals(((Group) other).groupName)); // state check
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

}
```
###### /java/seedu/address/model/group/UniqueGroupList.java
``` java
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty Group List.
     */
    public UniqueGroupList() {

    }

    /**
     * Creates a UniqueGroupList using given tags.
     * Enforces no nulls.
     */
    public UniqueGroupList(Group groups) {
        requireAllNonNull(groups);
        internalList.addAll(groups);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the Groups in this list with those in the argument group list.
     */
    public void setGroups(UniqueGroupList groups) {
        requireAllNonNull(groups);
        internalList.setAll(groups.internalList);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException {
        final UniqueGroupList replacement = new UniqueGroupList();
        for (final Group group : groups) {
            replacement.add(group);
        }
        setGroups(replacement);
    }
    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueGroupList from) {
        final UniqueGroupList alreadyInside = this;
        from.internalList.stream()
                .filter(group -> !alreadyInside.contains(group))
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
     * Adds a Tag to the list.
     *
     * @throws DuplicateGroupException if the Group to add is a duplicate of an existing Tag in the list.
     */
    public void add(Group toAdd) throws DuplicateGroupException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
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

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateGroupException extends DuplicateDataException {
        protected DuplicateGroupException() {
        super("Operation would result in duplicate groups");
        }
    }
}
```
###### /java/seedu/address/model/person/ReadOnlyPersonComparator.java
``` java
public class ReadOnlyPersonComparator implements Comparator<Person> {

    @Override
    public int compare(Person personA, Person personB) {
        return personA.getName().compareTo(personB.getName());
    }
}
```

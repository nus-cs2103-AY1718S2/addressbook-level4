package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;

import seedu.address.model.tag.UniqueGroupList;
import seedu.address.model.tag.UniquePreferenceList;
import seedu.address.model.tag.exceptions.GroupNotFoundException;
import seedu.address.model.tag.exceptions.PreferenceNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniquePreferenceList prefTags;
    private final UniqueGroupList groupTags;
    private final UniqueOrderList orders;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        prefTags = new UniquePreferenceList();
        groupTags = new UniqueGroupList();
        orders = new UniqueOrderList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setGroupTags(Set<Group> groupTags) {
        this.groupTags.setTags(groupTags);
    }

    public void setPreferenceTags(Set<Preference> prefTags) {
        this.prefTags.setTags(prefTags);
    }

    public void setOrders(List<Order> orders) throws DuplicateOrderException {
        this.orders.setOrders(orders);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setGroupTags(new HashSet<>(newData.getGroupList()));
        setPreferenceTags(new HashSet<>(newData.getPreferenceList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }

        List<Order> orderList = new ArrayList<>(newData.getOrderList());

        try {
            setOrders(orderList);
        } catch (DuplicateOrderException e) {
            throw new AssertionError("AddressBooks should not have duplicate orders");
        }
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's groups and preferences and updates {@link #groupTags} and {@link #prefTags}
     * with any new groups and preferences found,
     * and updates the Group and Preference objects in the person to point to those in
     * {@link #groupTags} and {@link #prefTags} respectively.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        persons.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s group list and preference list will be updated
     * with the groups and preferences of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        persons.setPerson(target, syncedEditedPerson);
        removeUnusedGroups();
        removeUnusedPreferences();
    }

    /**
     *  Updates the master group list and master preference list to include groups and preferences
     *  in {@code person} that are not in the lists.
     *  @return a copy of this {@code person} such that every group and every preference in this person
     *  points to a Group object and Preference object in the respective master list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueGroupList personGroups = new UniqueGroupList(person.getGroupTags());
        final UniquePreferenceList personPreferences = new UniquePreferenceList(person.getPreferenceTags());
        groupTags.mergeFrom(personGroups);
        prefTags.mergeFrom(personPreferences);

        // Create map with values = group object references in the master list
        // used for checking person group references
        final Map<Group, Group> masterGroupObjects = new HashMap<>();
        groupTags.forEach(group -> masterGroupObjects.put(group, group));

        // Create map with values = preference object references in the master list
        // used for checking person preference references
        final Map<Preference, Preference> masterPreferenceObjects = new HashMap<>();
        prefTags.forEach(pref -> masterPreferenceObjects.put(pref, pref));


        // Rebuild the list of person groups and preferences to point to the relevant groups in the master group list
        // and relevant preferences in the master preference list.
        final Set<Group> correctGroupReferences = new HashSet<>();
        final Set<Preference> correctPreferenceReferences = new HashSet<>();
        personGroups.forEach(group -> correctGroupReferences.add(masterGroupObjects.get(group)));
        personPreferences.forEach(pref -> correctPreferenceReferences.add(masterPreferenceObjects.get(pref)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                correctGroupReferences, correctPreferenceReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            removeUnusedGroups();
            removeUnusedPreferences();
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addGroup(Group g) throws UniqueGroupList.DuplicateGroupException {
        groupTags.add(g);
    }

    public void addPreference(Preference p) throws UniquePreferenceList.DuplicatePreferenceException {
        prefTags.add(p);
    }

    //@@author SuxianAlicia
    /**
     * Removes group from all persons who has the group
     * @throws GroupNotFoundException if the {@code toRemove} is not in this {@code AddressBook}.
     */
    public void removeGroup(Group toRemove) throws GroupNotFoundException {
        if (groupTags.contains(toRemove)) {
            persons.removeGroupFromAllPersons(toRemove);
            groupTags.remove(toRemove);
        } else {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Removes preference from all persons who has the preference
     * @throws PreferenceNotFoundException if the {@code toRemove} is not in this {@code AddressBook}.
     */
    public void removePreference(Preference toRemove) throws PreferenceNotFoundException {
        if (prefTags.contains(toRemove)) {
            persons.removePrefFromAllPersons(toRemove);
            prefTags.remove(toRemove);
        } else {
            throw new PreferenceNotFoundException();
        }
    }

    /**
     * Solution below adapted from
     * https://github.com/se-edu/addressbook-level4/pull/790/commits/48ba8e95de5d7eae883504d40e6795c857dae3c2
     * Removes unused groups in groupTags.
     */
    private void removeUnusedGroups() {
        ObservableList<Person> list = persons.getInternalList();
        UniqueGroupList newList = new UniqueGroupList();

        for (Person p: list) {
            newList.mergeFrom(new UniqueGroupList(p.getGroupTags()));
        }
        setGroupTags(newList.toSet());
    }

    /**
     * Solution below adapted from
     * https://github.com/se-edu/addressbook-level4/pull/790/commits/48ba8e95de5d7eae883504d40e6795c857dae3c2
     * Removes unused preferences in prefTags.
     */
    private void removeUnusedPreferences() {
        ObservableList<Person> list = persons.getInternalList();
        UniquePreferenceList newList = new UniquePreferenceList();

        for (Person p: list) {
            newList.mergeFrom(new UniquePreferenceList(p.getPreferenceTags()));
        }
        setPreferenceTags(newList.toSet());
    }
    //@@author

    //@@author amad-person
    //// order-level operations

    /**
     * Adds order to list of orders.
     */
    public void addOrderToOrderList(Order orderToAdd) throws DuplicateOrderException {
        orders.add(orderToAdd);
    }

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     *
     * @throws DuplicateOrderException if updating the order's details causes the order
     * to be equivalent to another existing order in the list.
     * @throws OrderNotFoundException if {@code target} could not be found in the list.
     */
    public void updateOrder(Order target, Order editedOrder)
            throws DuplicateOrderException, OrderNotFoundException {
        requireNonNull(editedOrder);

        orders.setOrder(target, editedOrder);
    }
    //@@author

    /**
     * Updates the order status of the given order {@code target}.
     *
     * @throws DuplicateOrderException if updating the order's details causes the order
     * to be equivalent to another existing order in the list.
     * @throws OrderNotFoundException if {@code target} could not be found in the list.
     */
    public void updateOrderStatus(Order target, String orderStatus)
            throws DuplicateOrderException, OrderNotFoundException {
        requireNonNull(orderStatus);

        Order editedOrder = new Order(target.getOrderInformation(), target.getOrderStatus(),
                target.getPrice(), target.getQuantity(), target.getDeliveryDate());
        editedOrder.getOrderStatus().setOrderStatusValue(orderStatus);

        orders.setOrder(target, editedOrder);
    }

    /**
     * Removes order from list of orders.
     */
    public void deleteOrder(Order targetOrder) throws OrderNotFoundException {
        orders.remove(targetOrder);
    }
    //@@author

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + groupTags.asObservableList().size() +  " groups, "
                + prefTags.asObservableList().size() + " preferences, " + orders.asObservableList().size() + " orders";
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groupTags.asObservableList();
    }

    @Override
    public ObservableList<Preference> getPreferenceList() {
        return prefTags.asObservableList();
    }

    //@@author amad-person
    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asObservableList();
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.groupTags.equalsOrderInsensitive(((AddressBook) other).groupTags)
                && this.prefTags.equalsOrderInsensitive(((AddressBook) other).prefTags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, groupTags, prefTags, orders);
    }
}

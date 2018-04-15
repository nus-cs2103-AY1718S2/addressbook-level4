package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.address.model.Insurance.Insurance;
import seedu.address.model.Insurance.UniqueInsuranceList;
import seedu.address.model.export.exceptions.CalendarAccessDeniedException;
import seedu.address.model.export.exceptions.ConnectivityIssueException;
import seedu.address.model.export.exceptions.InvalidFileNameException;
import seedu.address.model.person.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueGroupList;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.GoogleCalendarClient;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueGroupList groups;
    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueInsuranceList insurances;
    private final UserPrefs userPrefs;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        userPrefs = new UserPrefs();
        groups = new UniqueGroupList();
        insurances = new UniqueInsuranceList();
    }
    /**
     * empty constructor
     */
    public AddressBook() {

    }

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public int getAddressBookSize() {
        return this.persons.asObservableList().size();
    }

    //// list overwrite operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setGroups(List<Group> persons) {
        this.groups.setGroups(groups);
    }

    public void setInsurances(Set<Insurance> insurances) {
        this.insurances.setInsurances(insurances);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        setInsurances(new HashSet<>(newData.getInsuranceList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
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
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
    }

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), correctTagReferences,
                person.getBirthday(), person.getAppointment(), person.getGroup(), person.getInsurance());
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    /// insurance-level operations

    public void addInsurance(Insurance i) throws UniqueInsuranceList.DuplicateInsuranceException {
        insurances.add(i);
    }

    //// export-level operations

    //@@author daviddalmaso

    /**
     * Transfers persons in reInsurance data to portfolio data
     * @return a String representing the portfolio as a csv
     */
    private String portfolioToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name,Phone,Email,Address,Group,Total Commission,Insurances,Tags\n");
        for (Person person : persons) {
            String group = "";
            String insurance = "";
            String tags = "";
            if (person.getGroup() != null) {
                group = person.getGroup().toString();
            }
            if (person.getInsurance() != null) {
                insurance = person.getInsurance().toString();
            }
            if (person.getTags() != null) {
                tags = person.getTags().toString();
            }
            sb.append("\"" + person.getName().toString() + "\",");
            sb.append("\"" + person.getPhone().toString() + "\",");
            sb.append("\"" + person.getEmail().toString() + "\",");
            sb.append("\"" + person.getAddress().toString() + "\",");
            sb.append("\"" + group + "\",");
            sb.append("\"" + person.getTotalCommission() + "\",");
            sb.append("\"" + insurance + "\",");
            sb.append("\"" + tags + "\",");
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Exports the current reInsurance data to the specified file path
     * @param filePath the file path to export the portfolio to
     */
    public void exportPortfolio(String filePath) throws InvalidFileNameException {
        try {
            PrintWriter pw = new PrintWriter(new File(filePath));
            String portfolioAsString = portfolioToString();
            pw.write(portfolioAsString);
            pw.close();
        } catch (FileNotFoundException e) {
            throw new InvalidFileNameException();
        }
    }

    /**
     * Exports the calendar events to the user's Google Calendar
     */
    public void exportCalendar() throws CalendarAccessDeniedException, ConnectivityIssueException {
        GoogleCalendarClient.insertCalendar(persons);
    }
    //@@author

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<Insurance> getInsuranceList() {
        return insurances.asObservableList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags)
                && this.insurances.equalsOrderInsensitive(((AddressBook) other).insurances));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

    /**
     * Sorts all persons from the address book.
     */
    public void sortedPersonsList() {
        persons.sortPersons();
    }
}

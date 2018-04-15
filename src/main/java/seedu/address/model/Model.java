package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {

    /** {@code Predicate} that always evaluate to true */
    Predicate<Client> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Client> PREDICATE_SHOW_ALL_TUTORS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Client> PREDICATE_SHOW_ALL_CLOSED_STUDENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Client> PREDICATE_SHOW_ALL_CLOSED_TUTORS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given client in the active list. */
    void deleteClient(Client target, Category category) throws PersonNotFoundException;

    /** Deletes the given client in the closed list. */
    void deleteClosedClient(Client target, Category category) throws PersonNotFoundException;

    /**
     * Replaces the given person {@code target} with {@code editedClient}.
     *
     * @throws DuplicatePersonException if updating the client's details causes the client to be equivalent to
     *      another existing client in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updateClient(Client target, Client editedPerson, Category category)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Adds the given tutor */
    void addTutor(Client tutor) throws DuplicatePersonException;

    /** Adds the given student */
    void addStudent(Client student) throws DuplicatePersonException;

    /** Adds the given tutor to closed tutor's list */
    void addClosedTutor(Client closedTutor) throws DuplicatePersonException;

    /** Adds the given student to closed student's list */
    void addClosedStudent(Client closedStudent) throws DuplicatePersonException;

    /**Sorts tutor list by name in alphabetical order*/
    void sortByNameFilteredClientTutorList();
    /**Sorts tutor list by location in alphabetical order*/
    void sortByLocationFilteredClientTutorList();
    /**Sorts tutor list by grade in ascending order*/
    void sortByGradeFilteredClientTutorList();
    /**Sorts tutor list by subject in alphabetical order*/
    void sortBySubjectFilteredClientTutorList();
    /**Sorts student list by name in alphabetical order*/
    void sortByNameFilteredClientStudentList();
    /**Sorts student list by location in alphabetical order*/
    void sortByLocationFilteredClientStudentList();
    /**Sorts student list by grade in ascending order*/
    void sortByGradeFilteredClientStudentList();
    /**Sorts student list by subject in alphabetical order*/
    void sortBySubjectFilteredClientStudentList();

    /** Returns an unmodifiable view of the filtered students list */
    ObservableList<Client> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Client> predicate);

    /** Returns an unmodifiable view of the filtered tutors list */
    ObservableList<Client> getFilteredTutorList();

    /**
     * Updates the filter of the filtered tutor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTutorList(Predicate<Client> predicate);

    /** Returns an unmodifiable view of the closed filtered tutors list */
    ObservableList<Client> getFilteredClosedTutorList();

    /**
     * Updates the filter of the filtered closed tutor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClosedTutorList(Predicate<Client> predicate);

    /** Returns an unmodifiable view of the closed filtered students list */
    ObservableList<Client> getFilteredClosedStudentList();

    /**
     * Updates the filter of the filtered closed tutor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClosedStudentList(Predicate<Client> predicate);

    /**
     * Rank TutorList from the most number of matched attributes to the least number of matched attributes
     */
    void updateRankedTutorList();

    /**
     * Rank StudentList from the most number of matched attributes to the least number of matched attributes
     */
    void updateRankedStudentList();

    /**
     * Reset {@code rank}, {@code MatchedGrade}, {@code MatchedLocation} and {@code MatchedSubject} in
     * all Clientlist to default value
     */
    void resetHighLight();

}


package seedu.progresschecker.model;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.exercise.UniqueExerciseList;
import seedu.progresschecker.model.exercise.exceptions.DuplicateExerciseException;
import seedu.progresschecker.model.exercise.exceptions.ExerciseNotFoundException;
import seedu.progresschecker.model.issues.GitIssueList;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.UniquePersonList;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
import seedu.progresschecker.model.person.exceptions.PersonNotFoundException;
import seedu.progresschecker.model.photo.PhotoPath;
import seedu.progresschecker.model.photo.UniquePhotoList;
import seedu.progresschecker.model.photo.exceptions.DuplicatePhotoException;
import seedu.progresschecker.model.tag.Tag;
import seedu.progresschecker.model.tag.UniqueTagList;

/**
 * Wraps all data at the progresschecker
 * Duplicates are not allowed (by .equals comparison)
 */
public class ProgressChecker implements ReadOnlyProgressChecker {

    private final UniquePersonList persons;
    private final UniquePhotoList photos;
    private final UniqueTagList tags;
    private final UniqueExerciseList exercises;
    private final GitIssueList issues;

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
        photos = new UniquePhotoList();
        exercises = new UniqueExerciseList();
        issues = new GitIssueList();
    }

    public ProgressChecker() {}

    /**
     * Creates an ProgressChecker using the Persons and Tags in the {@code toBeCopied}
     */
    public ProgressChecker(ReadOnlyProgressChecker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    //@@author iNekox3
    public void setExercises(List<Exercise> exercises) throws DuplicateExerciseException {
        this.exercises.setExercises(exercises);
    }

    //@@author
    /**
     * Resets the existing data of this {@code ProgressChecker} with {@code newData}.
     */
    public void resetData(ReadOnlyProgressChecker newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPersons(syncedPersonList);
            setExercises(newData.getExerciseList());

        } catch (DuplicatePersonException e) {
            throw new AssertionError("ProgressChecker should not have duplicate persons");
        } catch (DuplicateExerciseException e) {
            throw new AssertionError("ProgressChecker should not have duplicate exercises");
        }
    }

    //@@author Livian1107
    /**
     * Sorts the existing {@code UniquePersonList} of this {@code ProgressChecker}
     * with their names in alphabetical order.
     */
    public void sort() {
        requireNonNull(persons);
        persons.sort();
    }

    /**
     * Adds a new uploaded photo path to the the list of profile photos
     * @param photoPath of a new uploaded photo
     * @throws DuplicatePhotoException if there already exists the same photo path
     */
    public void addPhotoPath(PhotoPath photoPath) throws DuplicatePhotoException {
        photos.add(photoPath);
    }
    //@@author

    //// person-level operations

    /**
     * Adds a person to the ProgressChecker.
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
     * {@code ProgressChecker}'s tag list will be updated with the tags of {@code editedPerson}.
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

    //@@author adityaa1998

    //issue-level operations

    /**
     * Login to github
     *
     * @throws IOException is there is any problem in authentication
     *
     */
    public void loginGithub(GitDetails gitdetails) throws IOException, CommandException {
        issues.initialiseCredentials(gitdetails);
    }

    /**
     * Logout of github
     */
    public void logoutGithub() throws CommandException {
        issues.clearCredentials();
    }

    /**
     * Creates issue on github
     *
     * @throws IOException if theres any fault in the input values or the authentication fails due to wrong input
     */
    public void createIssueOnGitHub(Issue i) throws IOException, CommandException {
        issues.createIssue(i);
    }

    /**
     * Replaces the given issue at {@code index} from github with {@code editedPerson}.
     * reopens an issue on github
     *
     * @throws IOException if the index mentioned is not valid or he's closed
     */
    public void reopenIssueOnGithub(Index index) throws IOException, CommandException {
        issues.reopenIssue(index);
    }

    /**
     * closes an issue on github
     *
     * @throws IOException if the index mentioned is not valid or he's closed
     */
    public void closeIssueOnGithub(Index index) throws IOException, CommandException {
        issues.closeIssue(index);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code ProgressChecker}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws IOException if there is any problem in git authentication or parameter
     *
     */
    public void updateIssue(Index index, Issue editedIssue) throws IOException, CommandException {
        requireNonNull(editedIssue);
        issues.setIssue(index, editedIssue);
    }

    /**
     * Lists all the issues of the specified state
     */
    public void listIssues(String state) throws IllegalValueException, IOException, CommandException {
        requireNonNull(state);
        issues.listIssue(state);
    }

    //@@author

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
                person.getName(), person.getPhone(), person.getEmail(), person.getUsername(), person.getMajor(),
                person.getYear(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code ProgressChecker}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code ProgressChecker}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //@@author Livian1107
    /**
     * Uploads the profile photo path of target person
     * @param target
     * @param path
     * @throws PersonNotFoundException
     * @throws DuplicatePersonException
     */
    public void uploadPhoto(Person target, String path) throws PersonNotFoundException, DuplicatePersonException {
        Person tempPerson = target;
        target.updatePhoto(path);
        persons.setPerson(tempPerson, target);
    }
    //@@author

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author iNekox3
    //// exercise-level operations

    /**
     * Adds an exercise to the ProgressChecker.
     *
     * @throws DuplicateExerciseException if an equivalent exercise already exists.
     */
    public void addExercise(Exercise e) throws DuplicateExerciseException {
        Exercise exercise = new Exercise(
                e.getQuestionIndex(), e.getQuestionType(), e.getQuestion(),
                e.getStudentAnswer(), e.getModelAnswer());
        exercises.add(exercise);
    }

    /**
     * Replaces the given exercise {@code target} in the list with {@code editedExercise}.
     *
     * @throws ExerciseNotFoundException if {@code target} could not be found in the list.
     */
    public void updateExercise(Exercise target, Exercise editedExercise)
            throws ExerciseNotFoundException {
        requireNonNull(editedExercise);

        exercises.setExercise(target, editedExercise);
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

    //@@author iNekox3
    @Override
    public ObservableList<Exercise> getExerciseList() {
        return exercises.asObservableList();
    }
    //@@author

    @Override
    public ObservableList<Issue> getIssueList() {
        return issues.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProgressChecker // instanceof handles nulls
                && this.persons.equals(((ProgressChecker) other).persons)
                && this.tags.equalsOrderInsensitive(((ProgressChecker) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}

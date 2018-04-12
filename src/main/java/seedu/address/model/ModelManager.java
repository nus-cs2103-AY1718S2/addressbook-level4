package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.Cca;
import seedu.address.model.person.Name;
import seedu.address.model.person.Test;
import seedu.address.model.person.Nric;

import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;


/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

    public static final String PROFILE_DIRECTORY = "/StudentPage/";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /**
     * @@ author Johnny Chan
     * @param person
     * @throws IOException
     * Adds a BrowserPanel html Page into StudentPage
     */
    public void addPage(Person person) throws IOException {

        String path = new File("src/main/resources/StudentPage/template.html").getAbsolutePath();
        File htmlTemplateFile = new File(path);
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);

        Name titleName = person.getName();
        String title = titleName.toString();
        htmlString = htmlString.replace("$title", title);

        Nric identityNumberClass = person.getNric();
        String identityNumber = identityNumberClass.toString();
        htmlString = htmlString.replace("$identityNumber", identityNumber);

        Set<Tag> tagList = person.getTags();
        htmlString = htmlString.replace("Class not Included", tagList.toString());

        //ADD L1R5

        List<Subject> subjectList = person.getSubjectArray();
        int listSize = subjectList.size();
        System.out.println(person.getSubjects());
        int i = 0;
        while (i < listSize) {
            String iString = Integer.toString(i + 1);
            htmlString = htmlString.replace("Subject " + iString, subjectList.get(i).nameToString());
            htmlString = htmlString.replace("$percent" + iString, subjectList.get(i).gradeToPercent());
            htmlString = htmlString.replace("Grade " + iString, subjectList.get(i).gradeToString());
            i++;
        }

        // ADD CCA
        Cca ccaList = person.getCca();
        String ccaString = ccaList.toString();
        htmlString = htmlString.replace("CCA", ccaString);

        // ADD REMARK

        String remark = person.getRemark().toString();
        htmlString = htmlString.replace("Remarks to facilitate teaching should be included here.", remark);


        //ADD INJURY
        String injury = person.getInjuriesHistory().toString();
        htmlString = htmlString.replace("Insert injury history here", injury);

        String newPath = new File("src/main/resources/StudentPage/" + title + ".html").getAbsolutePath();
        File newHtmlFile = new File(newPath);
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
        //updatePage(person);
    }

    /**
     * @@author Johnny chan
     * Deletes BrowserPanel html
     */
    public void deletePage(Person person) {
        File deleteTemplateFile = new File("/Users/johnnychan/Documents/"
                + "GitHub/main/src/main/resources/StudentPage/" + person.getName() + ".html");
        boolean bool = deleteTemplateFile.delete();
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addNextOfKin(Test test) throws DuplicatePersonException {
        addressBook.addNextOfKin(test);

    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //@@author kengsengg
    public void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
        addressBook.addAppointment(appointment);
    }
    //@@author

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //@@author kengsengg
    @Override
    public void sortPersonList(String parameter) {
        addressBook.sort(parameter);
    }

    //@@author TeyXinHui
    @Override
    public void deleteTag(Tag tag) {
        try {
            addressBook.removeTag(tag);
        } catch (TagNotFoundException error) {
            throw new AssertionError();
        }
    }

    //@@author chuakunhong
    @Override
    public void replaceTag(List<Tag> tagSet) {
        Tag[] tagArray = new Tag[2];
        tagSet.toArray(tagArray);
        addressBook.replaceTag(tagSet);
        indicateAddressBookChanged();
    }

    //@@author
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}

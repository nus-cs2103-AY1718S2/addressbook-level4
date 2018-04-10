package seedu.address.model.email;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.email.exceptions.DuplicateTemplateException;
import seedu.address.model.email.exceptions.TemplateNotFoundException;


//@@author ng95junwei
/**
 * A list of templates that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Template#equals(Object)
 */
public class UniqueTemplateList implements Iterable<Template> {

    private final ObservableList<Template> internalList = FXCollections.observableArrayList();

    /**
     * Returns one Template closest to the template searched for
     */
    public Template search(String search) throws TemplateNotFoundException {
        for (Template t : internalList) {
            if (t.getPurpose().contains(search)) {
                return t;
            }
        }
        throw new TemplateNotFoundException();
    }

    /**
     * Returns true if the list contains an equivalent template as the given argument.
     */
    public boolean contains(Template toCheck) {
        requireNonNull(toCheck);
        String newPurpose = toCheck.getPurpose();
        for(Template t : internalList) {
            if(t.getPurpose().equals(newPurpose)){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a template to the list.
     *
     * @throws DuplicateTemplateException if the template to add is a duplicate of an existing template
     * in the list.
     */
    public void add(Template toAdd) throws DuplicateTemplateException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTemplateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent template from the list.
     *
     * @throws TemplateNotFoundException if no such template could be found in the list.
     */
    public boolean remove(Template toRemove) throws TemplateNotFoundException {
        requireNonNull(toRemove);
        final boolean templateFoundAndDeleted = internalList.remove(toRemove);
        if (!templateFoundAndDeleted) {
            throw new TemplateNotFoundException();
        }
        return templateFoundAndDeleted;
    }

    public void setTemplates(UniqueTemplateList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTemplates(List<Template> templates) throws DuplicateTemplateException {
        requireAllNonNull(templates);
        final UniqueTemplateList replacement = new UniqueTemplateList();
        for (final Template template : templates) {
            replacement.add(template);
        }
        setTemplates(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Template> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Template> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTemplateList // instanceof handles nulls
                && this.internalList.equals(((UniqueTemplateList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}


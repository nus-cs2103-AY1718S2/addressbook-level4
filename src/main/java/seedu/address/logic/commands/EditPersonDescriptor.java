package seedu.address.logic.commands;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Cca;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Remark;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */

public class EditPersonDescriptor {
    private Name name;
    private Nric nric;
    private Set<Tag> tags;
    private Set<Subject>  subjects;
    private Remark remark;
    private Cca cca;
    private InjuriesHistory injuriesHistory;
    private NextOfKin nextOfKin;

    public EditPersonDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditPersonDescriptor(EditPersonDescriptor toCopy) {
        setName(toCopy.name);
        setNric(toCopy.nric);
        setTags(toCopy.tags);
        setSubjects(toCopy.subjects);
        setRemark(toCopy.remark);
        setCca(toCopy.cca);
        setInjuriesHistory(toCopy.injuriesHistory);
        setNextOfKin(toCopy.nextOfKin);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(this.name, this.nric, this.tags, this.subjects, this.remark, this.cca,
                this.injuriesHistory, this.nextOfKin);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setNric(Nric nric) {
        this.nric = nric;
    }

    public Optional<Nric> getNric() {
        return Optional.ofNullable(nric);
    }

    public void setNextOfKin(NextOfKin nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public Optional<NextOfKin> getNextOfKin() {
        return Optional.ofNullable(nextOfKin);
    }
    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    /**
     * Sets {@code subjects} to this object's {@code subjects}.
     * A defensive copy of {@code subjects} is used internally.
     */
    public void setSubjects(Set<Subject> subjects) {
        this.subjects = (subjects != null) ? new HashSet<>(subjects) : null;
    }

    /**
     * Sets {@code remarks} to this object's {@code remarks}.
     * A defensive copy of {@code remarks} is used internally.
     */
    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    /**
     * Sets {@code remarks} to this object's {@code remarks}.
     * A defensive copy of {@code remarks} is used internally.
     */
    public Optional<Remark> getRemark() {
        return Optional.ofNullable(remark);
    }

    /**
     * Sets {@code cca} to this object's {@code cca}.
     * A defensive copy of {@code cca} is used internally.
     */
    public void setCca(Cca cca) {
        this.cca = cca;
    }

    /**
     * Sets {@code cca} to this object's {@code cca}.
     * A defensive copy of {@code cca} is used internally.
     */
    public Optional<Cca> getCca() {
        return Optional.ofNullable(cca);
    }

    /**
     * Sets {@code injuriesHistory} to this object's {@code injuriesHistory}.
     * A defensive copy of {@code injuriesHistory} is used internally.
     */
    public Optional<InjuriesHistory> getInjuriesHistory() {
        return Optional.ofNullable(injuriesHistory);
    }

    /**
     * Sets {@code injuriesHistory} to this object's {@code injuriesHistory}.
     * A defensive copy of {@code injuriesHistory} is used internally.
     */
    public void setInjuriesHistory(InjuriesHistory injuriesHistory) {
        this.injuriesHistory = injuriesHistory;
    }

    /**
     * Returns an unmodifiable subject set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code subjects} is null.
     */
    public Optional<Set<Subject>> getSubjects() {
        return (subjects != null) ? Optional.of(Collections.unmodifiableSet(subjects)) : Optional.empty();
    }

    //@@author TeyXinHui
    /**
     * Returns an unmodifiable subject set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code subjects} is null.
     */
    public Set<Subject> getSubjectsAsSet() {
        return (subjects != null) ? Collections.unmodifiableSet(subjects) : Collections.emptySet();
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonDescriptor)) {
            return false;
        }
        // state check

        EditPersonDescriptor e = (EditPersonDescriptor) other;

        return getName().equals(e.getName())
                && getNric().equals(e.getNric())
                && getTags().equals(e.getTags())
                && getSubjects().equals(e.getSubjects());
    }
}

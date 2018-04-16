package seedu.organizer.logic.commands.util;

//@@author aguss787
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.organizer.commons.util.CollectionUtil;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;

/**
 * Stores the details to edit the task with. Each non-empty field value will replace the
 * corresponding field value of the task.
 */
public class EditTaskDescriptor {
    private Name name;
    private Priority priority;
    private Deadline deadline;
    private Description description;
    private Status status;
    private Set<Tag> tags;
    private List<Subtask> subtasks;

    public EditTaskDescriptor() {
    }

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditTaskDescriptor(EditTaskDescriptor toCopy) {
        setName(toCopy.name);
        setPriority(toCopy.priority);
        setDeadline(toCopy.deadline);
        setDescription(toCopy.description);
        setStatus(toCopy.status);
        setTags(toCopy.tags);
        setSubtasks(toCopy.subtasks);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(this.name, this.priority, this.deadline, this.description, this.status,
                this.tags);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Optional<Priority> getPriority() {
        return Optional.ofNullable(priority);
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public Optional<Deadline> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Optional<Status> getStatus() {
        return Optional.ofNullable(status);
    }

    /**
     * Sets {@code subtasks} to this object's {@code subtasks}.
     * A defensive copy of {@code subtasks} is used internally.
     */
    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = (subtasks != null) ? new ArrayList<>(subtasks) : null;
    }

    /**
     * Returns an unmodifiable subtask set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code subtasks} is null.
     */
    public Optional<List<Subtask>> getSubtasks() {
        return (subtasks != null) ? Optional.of(Collections.unmodifiableList(subtasks)) : Optional.empty();
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

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskDescriptor)) {
            return false;
        }

        // state check
        EditTaskDescriptor e = (EditTaskDescriptor) other;

        return getName().equals(e.getName())
                && getPriority().equals(e.getPriority())
                && getDeadline().equals(e.getDeadline())
                && getDescription().equals(e.getDescription())
                && getTags().equals(e.getTags());
    }
}

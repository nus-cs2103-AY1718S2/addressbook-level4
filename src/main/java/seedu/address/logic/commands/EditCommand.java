package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACTIVITY;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Location;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.activity.Task;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.model.tag.Tag;

//@@author YuanQLLer
/**
 * Edits the details of an existing activity in the desk board.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the activity identified "
            + "by the index number used in the last activity listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the desk board.";

    private final Index index;
    private final EditActivityDescriptor editActivityDescriptor;

    private Activity activityToEdit;
    private Activity editedActivity;

    /**
     * @param index of the activity in the filtered activity list to edit
     * @param editActivityDescriptor details to edit the activity with
     */
    public EditCommand(Index index, EditActivityDescriptor editActivityDescriptor) {
        requireNonNull(index);
        requireNonNull(editActivityDescriptor);

        this.index = index;
        this.editActivityDescriptor = editActivityDescriptor.getCopy();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateActivity(activityToEdit, editedActivity);
        } catch (DuplicateActivityException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        } catch (ActivityNotFoundException pnfe) {
            throw new AssertionError("The target activity cannot be missing");
        }
        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITY);
        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Activity> lastShownList;
        if (editActivityDescriptor instanceof EditTaskDescriptor) {
            lastShownList = model.getFilteredTaskList();
        } else {
            lastShownList = model.getFilteredEventList();
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        activityToEdit = lastShownList.get(index.getZeroBased());
        editedActivity = createEditedActivity(activityToEdit, editActivityDescriptor);
    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}.
     */
    private static Activity createEditedActivity(Activity activityToEdit,
                                                 EditActivityDescriptor editActivityDescriptor) {
        assert activityToEdit != null;

        return editActivityDescriptor.createEditedActivity(activityToEdit);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editActivityDescriptor.equals(e.editActivityDescriptor)
                && Objects.equals(activityToEdit, e.activityToEdit);
    }

    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static interface EditActivityDescriptor {

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited();

        public EditActivityDescriptor getCopy();
        /**
         *  Creates and returns a {@code Activity} with the details of {@code activityToEdit}
         *  edited with {@code editTaskDescriptor}.
         * @param activityToEdit
         * @return
         */
        public Activity createEditedActivity(Activity activityToEdit);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static class EditTaskDescriptor implements EditActivityDescriptor {
        private Name name;
        private DateTime dateTime;
        private Remark remark;
        private Set<Tag> tags;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setName(toCopy.name);
            setDateTime(toCopy.dateTime);
            setRemark(toCopy.remark);
            setTags(toCopy.tags);
        }

        /**
         * Get a copy of this object
         */
        public EditActivityDescriptor getCopy() {
            return new EditTaskDescriptor(this);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.dateTime, this.remark, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDateTime(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<DateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
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
                    && getDateTime().equals(e.getDateTime())
                    && getRemark().equals(e.getRemark())
                    && getTags().equals(e.getTags());
        }


        /**
         * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
         * edited with {@code editTaskDescriptor}.
         */
        public Activity createEditedActivity(Activity activityToEdit) {
            assert activityToEdit != null;

            Name updatedName = this.getName().orElse(activityToEdit.getName());
            DateTime updatedDateTime = this.getDateTime().orElse(activityToEdit.getDateTime());
            Remark updatedRemark = this.getRemark().orElse(activityToEdit.getRemark());
            Set<Tag> updatedTags = this.getTags().orElse(activityToEdit.getTags());

            return new Task(updatedName, updatedDateTime, updatedRemark, updatedTags, activityToEdit.isCompleted());
        }
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor implements EditActivityDescriptor {
        private Name name;
        private DateTime startDateTime;
        private DateTime endDateTime;
        private Remark remark;
        private Location location;
        private Set<Tag> tags;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setName(toCopy.name);
            setStartDateTime(toCopy.startDateTime);
            setEndDateTime(toCopy.endDateTime);
            setRemark(toCopy.remark);
            setLocation(toCopy.location);
            setTags(toCopy.tags);
        }

        /**
         * Get a copy of this object
         */
        public EditActivityDescriptor getCopy() {
            return new EditEventDescriptor(this);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.startDateTime,
                    this.endDateTime, this.location, this.remark, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }


        public void setStartDateTime(DateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Optional<DateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public void setEndDateTime(DateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<DateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
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
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getName().equals(e.getName())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getRemark().equals(e.getRemark())
                    && getLocation().equals(e.getLocation())
                    && getTags().equals(e.getTags());
        }
        /**
         * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
         * edited with {@code editTaskDescriptor}.
         */
        public Activity createEditedActivity(Activity activityToEdit) {
            assert activityToEdit != null;
            assert activityToEdit instanceof Event;
            Event eventToEdit = (Event) activityToEdit;
            Name updatedName = this.getName().orElse(eventToEdit.getName());
            DateTime updatedStartDateTime =
                    this.getStartDateTime().orElse(eventToEdit.getStartDateTime());
            DateTime updatedEndDateTime =
                    this.getEndDateTime().orElse(eventToEdit.getEndDateTime());
            Remark updatedRemark = this.getRemark().orElse(eventToEdit.getRemark());
            Location updatedLocation = this.getLocation().orElse(eventToEdit.getLocation());
            Set<Tag> updatedTags = this.getTags().orElse(eventToEdit.getTags());

            return new Event(updatedName, updatedStartDateTime, updatedEndDateTime, updatedLocation,
                    updatedRemark, updatedTags, eventToEdit.isCompleted());
        }
    }

}

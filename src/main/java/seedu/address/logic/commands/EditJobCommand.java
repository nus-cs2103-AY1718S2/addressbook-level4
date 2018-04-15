package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RefreshReportPanelEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobTitle;
import seedu.address.model.job.Location;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

//@@author ChengSashankh
/**
 * Edits the details of an existing job in the address book.
 */
public class EditJobCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "editjob";
    public static final String COMMAND_ALIAS = "ej";
    public static final String COMMAND_OPTION_ADD_TAG = "add-tag";
    public static final String COMMAND_OPTION_DELETE_TAG = "delete-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the job identified "
            + "by the index number used in the last job listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_JOBTITLE + "JOBTITLE] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_SKILLS + "SKILLS] "
            + "[" + PREFIX_TAG + "TAG]... | \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_JOBTITLE + "Backend Engineer "
            + PREFIX_LOCATION + "Singapore\n"
            + "To modify tags (case-sensitive): \n"
            + COMMAND_WORD + " -" + COMMAND_OPTION_ADD_TAG + " INDEX t/[" + PREFIX_TAG + "TAG] to add one tag \n"
            + COMMAND_WORD + " -" + COMMAND_OPTION_DELETE_TAG + " INDEX t/[" + PREFIX_TAG + "TAG] to delete one tag";

    public static final String MESSAGE_EDIT_JOB_SUCCESS = "Edited Job: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_JOB = "This job already exists in the address book.";

    private final Index index;
    private final EditJobDescriptor editJobDescriptor;

    private Job jobToEdit;
    private Job editedJob;

    /**
     * @param index of the job in the filtered job list to edit
     * @param editJobDescriptor details to edit the job with
     */
    public EditJobCommand(Index index, EditJobDescriptor editJobDescriptor) {
        requireNonNull(index);
        requireNonNull(editJobDescriptor);

        this.index = index;
        this.editJobDescriptor = new EditJobDescriptor(editJobDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateJob(jobToEdit, editedJob);
            model.refreshReport();
            EventsCenter.getInstance().post(new RefreshReportPanelEvent());
        } catch (DuplicateJobException dje) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB);
        } catch (JobNotFoundException jnfe) {
            throw new AssertionError("The target job cannot be missing");
        }
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(String.format(MESSAGE_EDIT_JOB_SUCCESS, editedJob));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Job> lastShownList = model.getFilteredJobList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        }

        jobToEdit = lastShownList.get(index.getZeroBased());
        editedJob = editJobDescriptor.createEditedJob(jobToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditJobCommand)) {
            return false;
        }

        // state check
        EditJobCommand e = (EditJobCommand) other;
        return index.equals(e.index)
                && editJobDescriptor.equals(e.editJobDescriptor)
                && Objects.equals(jobToEdit, e.jobToEdit);
    }

    /**
     * Stores the details to edit the job with. Each non-empty field value will replace the
     * corresponding field value of the job.
     */
    public static class EditJobDescriptor {
        private JobTitle jobTitle;
        private Location location;
        private Skill skill;
        private Set<Tag> tags;
        private Set<Tag> newTags;
        private Set<Tag> deletedTags;

        public EditJobDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditJobDescriptor(EditJobDescriptor toCopy) {
            setJobTitle(toCopy.jobTitle);
            setLocation(toCopy.location);
            setSkill(toCopy.skill);
            setTags(toCopy.tags);
            setNewTags(toCopy.newTags);
            setDeletedTags(toCopy.deletedTags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.jobTitle, this.location, this.skill,
                    this.tags, this.newTags, this.deletedTags);
        }

        public void setJobTitle(JobTitle jobTitle) {
            this.jobTitle = jobTitle;
        }

        public Optional<JobTitle> getJobTitle() {
            return Optional.ofNullable(jobTitle);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
        }

        public Optional<Skill> getSkill() {
            return Optional.ofNullable(skill);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Add  {@code newTags} to this object's {@code newTags}.
         * A defensive copy of {@code newTags} is used internally.
         */
        public void setNewTags(Set<Tag> newTags) {
            this.newTags = (newTags != null) ? new HashSet<>(newTags) : null;
        }

        /**
         * Add  {@code newTags} to this object's {@code tags}.
         * A defensive copy of {@code newTags} is used internally.
         */
        public void setDeletedTags(Set<Tag> deletedTags) {
            this.deletedTags = (deletedTags != null) ? new HashSet<>(deletedTags) : null;
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
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code newTags} is null.
         */
        public Optional<Set<Tag>> getNewTags() {
            return (newTags != null) ? Optional.of(Collections.unmodifiableSet(newTags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code deletedTags} is null.
         */
        public Optional<Set<Tag>> getDeletedTags() {
            return (deletedTags != null) ? Optional.of(Collections.unmodifiableSet(deletedTags)) : Optional.empty();
        }

        /**
         * Creates and returns a {@code Person} with the details of {@code personToEdit}
         * edited with {@code editPersonDescriptor}.
         */
        public Job createEditedJob(Job jobToEdit) {
            boolean isTagsChanged = getTags().isPresent();
            boolean isNewTagsChanged = getNewTags().isPresent();
            boolean isDeletedTagsChanged = getDeletedTags().isPresent();

            boolean isMultipleTagsChanged = (isTagsChanged && isNewTagsChanged)
                    || (isTagsChanged && isDeletedTagsChanged) || (isDeletedTagsChanged && isNewTagsChanged);
            assert !isMultipleTagsChanged;

            assert jobToEdit != null;

            JobTitle updatedJobTitle = getJobTitle().orElse(jobToEdit.getJobTitle());
            Location updatedLocation = getLocation().orElse(jobToEdit.getLocation());
            Skill updatedSkill = getSkill().orElse(jobToEdit.getSkills());

            //Adapted from EditCommand - writted by @anh2111

            Set<Tag> updatedTags;
            Set<Tag> jobTags = new HashSet<>(jobToEdit.getTags());
            if (isTagsChanged) {
                updatedTags = getTags().orElse(null);
            } else if (isNewTagsChanged) {
                if (jobTags.isEmpty()) {
                    updatedTags = getNewTags().orElse(null);
                } else {
                    updatedTags = jobTags;
                    updatedTags.addAll(getNewTags().orElse(null));
                }
            } else if (isDeletedTagsChanged) {
                updatedTags = jobTags;
                if (!jobTags.isEmpty()) {
                    updatedTags.removeAll(getDeletedTags().orElse(null));
                }
            } else {
                updatedTags = jobTags;
            }

            return new Job(updatedJobTitle, updatedLocation, updatedSkill, updatedTags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditJobDescriptor)) {
                return false;
            }

            // state check
            EditJobDescriptor e = (EditJobDescriptor) other;

            return getJobTitle().equals(e.getJobTitle())
                    && getLocation().equals(e.getLocation())
                    && getSkill().equals(e.getSkill())
                    && getTags().equals(e.getTags());
        }
    }

}

//@@author

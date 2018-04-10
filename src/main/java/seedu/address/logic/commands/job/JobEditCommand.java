//@@author kush1509
package seedu.address.logic.commands.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_POSITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.job.Job;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.skill.Skill;

/**
 * Edits the details of an existing job in the address book.
 */
public class JobEditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editjob";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "[index]" + " "
            + "[" + PREFIX_POSITION + "] "
            + "[" + PREFIX_TEAM + "] "
            + "[" + PREFIX_LOCATION + "] "
            + "[" + PREFIX_NUMBER_OF_POSITIONS + "] "
            + "[" + PREFIX_SKILL + "SKILL]...";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the job identified "
            + "by the index number used in the last job listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_POSITION + "] "
            + "[" + PREFIX_TEAM + "] "
            + "[" + PREFIX_LOCATION + "] "
            + "[" + PREFIX_NUMBER_OF_POSITIONS + "] "
            + "[" + PREFIX_SKILL + "SKILL]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POSITION + "Backend Software Engineer "
            + PREFIX_TEAM + "Backend Services";

    public static final String MESSAGE_EDIT_JOB_SUCCESS = "Edited Job: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to editjob must be provided.";
    public static final String MESSAGE_DUPLICATE_JOB = "This job already exists in the address book.";

    private final Index index;
    private final EditJobDescriptor editJobDescriptor;

    private Job jobToEdit;
    private Job editedJob;

    /**
     * @param index of the job in the filtered job list to edit
     * @param editJobDescriptor details to edit the job with
     */
    public JobEditCommand(Index index, EditJobDescriptor editJobDescriptor) {
        requireNonNull(index);
        requireNonNull(editJobDescriptor);

        this.index = index;
        this.editJobDescriptor = new EditJobDescriptor(editJobDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateJob(jobToEdit, editedJob);
        } catch (DuplicateJobException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB);
        } catch (JobNotFoundException pnfe) {
            throw new AssertionError("The target job cannot be missing");
        }
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(String.format(MESSAGE_EDIT_JOB_SUCCESS, editedJob));
    }

    @Override
    public void preprocessUndoableCommand() throws CommandException {
        List<Job> lastShownList = model.getFilteredJobList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        }

        jobToEdit = lastShownList.get(index.getZeroBased());
        editedJob = createEditedJob(jobToEdit, editJobDescriptor);
    }

    /**
     * Creates and returns a {@code Job} with the details of {@code jobToEdit}
     * edited with {@code editJobDescriptor}.
     */
    private static Job createEditedJob(Job jobToEdit, EditJobDescriptor editJobDescriptor) {
        assert jobToEdit != null;

        Position updatedPosition = editJobDescriptor.getPosition().orElse(jobToEdit.getPosition());
        Team updatedTeam = editJobDescriptor.getTeam().orElse(jobToEdit.getTeam());
        Location updatedLocation = editJobDescriptor.getLocation().orElse(jobToEdit.getLocation());
        NumberOfPositions updatedNumberOfPositions = editJobDescriptor.getNumberOfPositions()
                .orElse(jobToEdit.getNumberOfPositions());
        Set<Skill> updatedSkills = editJobDescriptor.getSkills().orElse(jobToEdit.getSkills());

        return new Job(updatedPosition, updatedTeam, updatedLocation, updatedNumberOfPositions, updatedSkills);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobEditCommand)) {
            return false;
        }

        // state check
        JobEditCommand e = (JobEditCommand) other;
        return index.equals(e.index)
                && editJobDescriptor.equals(e.editJobDescriptor)
                && Objects.equals(jobToEdit, e.jobToEdit);
    }

    /**
     * Stores the details to edit the job with. Each non-empty field value will replace the
     * corresponding field value of the job.
     */
    public static class EditJobDescriptor {
        private Position position;
        private Team team;
        private Location location;
        private NumberOfPositions numberOfPositions;
        private Set<Skill> skills;

        public EditJobDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code skills} is used internally.
         */
        public EditJobDescriptor(EditJobDescriptor toCopy) {
            setPosition(toCopy.position);
            setTeam(toCopy.team);
            setLocation(toCopy.location);
            setNumberOfPositions(toCopy.numberOfPositions);
            setSkills(toCopy.skills);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.position, this.team, this.location, this.numberOfPositions,
                    this.skills);
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public Optional<Position> getPosition() {
            return Optional.ofNullable(position);
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        public Optional<Team> getTeam() {
            return Optional.ofNullable(team);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setNumberOfPositions(NumberOfPositions numberOfPositions) {
            this.numberOfPositions = numberOfPositions;
        }

        public Optional<NumberOfPositions> getNumberOfPositions() {
            return Optional.ofNullable(numberOfPositions);
        }

        /**
         * Sets {@code skills} to this object's {@code skills}.
         * A defensive copy of {@code skills} is used internally.
         */
        public void setSkills(Set<Skill> skills) {
            this.skills = (skills != null) ? new HashSet<>(skills) : null;
        }

        /**
         * Returns an unmodifiable skill set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code skills} is null.
         */
        public Optional<Set<Skill>> getSkills() {
            return (skills != null) ? Optional.of(Collections.unmodifiableSet(skills)) : Optional.empty();
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

            return getPosition().equals(e.getPosition())
                    && getTeam().equals(e.getTeam())
                    && getLocation().equals(e.getLocation())
                    && getNumberOfPositions().equals(e.getNumberOfPositions())
                    && getSkills().equals(e.getSkills());
        }
    }
}

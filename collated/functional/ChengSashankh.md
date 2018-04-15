# ChengSashankh
###### \java\seedu\address\commons\events\ui\JobPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.JobCard;


/**
 * Represents a selection change in the Job List Panel
 */
public class JobPanelSelectionChangedEvent  extends BaseEvent {
    private final JobCard newSelection;

    public JobPanelSelectionChangedEvent(JobCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public JobCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\address\logic\CommandCorrection.java
``` java
/***
 * Auto-correct and auto-completing the command words being typed by the user in the command box.
 */
public class CommandCorrection {
    public static final String MATCH_FOUND_FEEDBACK_TO_USER = "Auto-completions: %1$s";
    public static final String NO_MATCHES_FEEDBACK_TO_USER = "No matching command completion found. "
            + "Try SPACE key for auto-correct.";
    private static final int NUMBER_ALPHABET = 52;
    private static final int START_INDEX = 0;

    private static boolean isFirstCall = true;
    private static Set<String> commandDictionary;
    private static String commandParameters;
    private static String commandInput;
    private static ArrayList<String> latestSuggestionsList;
    private static String recentInput;
    private static String recentSuggestion;
    private static int tabCounter = 0;

    //==================================== Common methods ====================================================

    public CommandCorrection() {
        createDictionary();
        latestSuggestionsList = new ArrayList<String>();
    }

    /***
     * Checks if the commandText that contains the command word is already a correct command.
     * @param commandText string from the commandBox that was typed by the user.
     * @return boolean indicating whether it is a correct command.
     */
    public static boolean isCorrectCommand(String commandText) {
        return commandDictionary.contains(commandText);
    }

    /***
     * Creates a dictionary of all command words recognized by Infinity Book.
     */
    public static void createDictionary() {
        commandDictionary = new HashSet<>();
        commandDictionary.add(AddCommand.COMMAND_WORD);
        commandDictionary.add(ClearCommand.COMMAND_WORD);
        commandDictionary.add(DeleteCommand.COMMAND_WORD);
        commandDictionary.add(DeleteJobCommand.COMMAND_WORD);
        commandDictionary.add(EditCommand.COMMAND_WORD);
        commandDictionary.add(EditJobCommand.COMMAND_WORD);
        commandDictionary.add(FindCommand.COMMAND_WORD);
        commandDictionary.add(FindJobCommand.COMMAND_WORD);
        commandDictionary.add(HelpCommand.COMMAND_WORD);
        commandDictionary.add(HistoryCommand.COMMAND_WORD);
        commandDictionary.add(ListCommand.COMMAND_WORD);
        commandDictionary.add(ListJobsCommand.COMMAND_WORD);
        commandDictionary.add(MatchJobCommand.COMMAND_WORD);
        commandDictionary.add(PostJobCommand.COMMAND_WORD);
        commandDictionary.add(RedoCommand.COMMAND_WORD);
        commandDictionary.add(RemarkCommand.COMMAND_WORD);
        commandDictionary.add(SelectCommand.COMMAND_WORD);
        commandDictionary.add(ThemeCommand.COMMAND_WORD);
        commandDictionary.add(UndoCommand.COMMAND_WORD);
        commandDictionary.add(ViewCommand.COMMAND_WORD);
        commandDictionary.add(ExitCommand.COMMAND_WORD);
        commandDictionary.add(AddInterviewCommand.COMMAND_WORD);
        commandDictionary.add(DeleteInterviewCommand.COMMAND_WORD);
        commandDictionary.add(ListInterviewCommand.COMMAND_WORD);
        commandDictionary.add(FindInterviewCommand.COMMAND_WORD);
        commandDictionary.add(FacebookLoginCommand.COMMAND_WORD);
        commandDictionary.add(ThemeCommand.COMMAND_WORD);
        commandDictionary.add(HistoryCommand.COMMAND_WORD);
        commandDictionary.add(ExitCommand.COMMAND_WORD);
        commandDictionary.add(UndoCommand.COMMAND_WORD);
        commandDictionary.add(ViewReportCommand.COMMAND_WORD);
        commandDictionary.add(SaveReportCommand.COMMAND_WORD);
    }

    //==================================== Auto-complete methods ============================================

    public static void setUpCommandCompletion() {
        if (isFirstCall) {
            recentInput = "";
            recentSuggestion = "";
            commandInput = "";
            isFirstCall = false;
            createDictionary();
        }
    }

    public static void setRecentInput(String recentInput) {
        CommandCorrection.recentInput = recentInput;
    }

    public static int getTabCounter() {
        return tabCounter;
    }

    public static void resetTabCounter() {
        tabCounter = 0;
    }

    public static void incrementTabCounter() {
        tabCounter++;
    }

    /***
     * Function attempts to complete command that is consistent with the text already typed.
     * @param commandText string from the commandBox that was typed by the user.
     * @returns ArrayList containing all possible suggestions as strings.
     */
    public static ArrayList<String> getSuggestions(String commandText) {
        updateSuggestionsList(commandText);
        commandInput = commandText;

        return latestSuggestionsList;
    }

    public static boolean noTextToComplete(String textToComplete) {
        return (textToComplete.trim().equals(""));
    }

    /***
     * Updates text to complete to original user input, if it has been altered.
     * @param textToComplete stores the string in the commandBox currently.
     * @return the original user input.
     */
    public static String updateTextToComplete(String textToComplete) {
        if (textToComplete.compareTo(recentSuggestion.trim()) == 0) {
            return recentInput;
        } else {
            return textToComplete;
        }
    }

    /***
     * Picks the next auto-complete suggestion from the list of possible suggestions.
     * @param suggestions contains all possible suggestions
     * @param suggestionToChoose indicates the suggestion to pick
     * @param commandText the string input from user
     * @return the suggested completion, if available. Else the input is returned.
     */
    public static String chooseSuggestion(ArrayList<String> suggestions, int suggestionToChoose,
                                          String commandText) {
        if (suggestions.size() != 0) {
            suggestionToChoose = suggestionToChoose % suggestions.size();
            recentSuggestion = suggestions.get(suggestionToChoose);
            return recentSuggestion;
        }
        return commandText;
    }

    /***
     * Updates the tab counter based on user input either incrementing or resetting.
     * @param textToComplete stores the string input from the user.
     */
    public static void updateTabCounter(String textToComplete) {
        if (textToComplete.compareTo(recentSuggestion.trim()) == 0) {
            CommandCorrection.incrementTabCounter();
        } else {
            CommandCorrection.resetTabCounter();
        }
    }

    /***
     * Updates the suggestions list based on the input string in commandBox
     * @param commandText string from the commandBox that was typed by the user.
     */
    public static void updateSuggestionsList(String commandText) {
        if (commandText.equals(commandInput)) {
            return;
        }
        latestSuggestionsList = new ArrayList<String>();
        Iterator<String> iterator = commandDictionary.iterator();
        int commandTextLength = commandText.length();

        while (iterator.hasNext()) {
            String nextCommand = iterator.next();
            int nextCommandLength = nextCommand.length();
            if (nextCommandLength >= commandTextLength) {
                String nextCommandSnippet = nextCommand.substring(START_INDEX, commandTextLength);
                if (nextCommandSnippet.compareTo(commandText) == 0) {
                    latestSuggestionsList.add(nextCommand.concat(" "));
                }
            }
        }

        latestSuggestionsList.sort((string1, string2) -> string1.compareToIgnoreCase(string2));
    }

    //==================================== Auto-correct methods ============================================

    /***
     * Sets up CommandCorrection by creating the dictionary.
     */
    public static void setUpCommandCorrection() {
        createDictionary();
    }

    /***
     * Extracts the command word and corrects only the command word and stores the rest in {@code commandParameters}
     */
    public static String extractCommandWord(String commandText) {
        String trimmedCommandText = commandText.trim();
        String[] wordsInCommandText = trimmedCommandText.split(" ");
        commandParameters = trimmedCommandText.replace(wordsInCommandText[0], "");
        return wordsInCommandText[0];
    }

    /***
     * Finds the nearest correction that is found. If no correction is found the text is returned as is.
     * @param commandText string from the commandBox that was typed by the user.
     * @return corrected String according the rules.
     */
    public static String nearestCorrection(String commandText) {

        if (isCorrectCommand(commandText)) {
            return commandText;
        }

        String extractedcommandText = extractCommandWord(commandText);

        String nearestCorrectedCommand = removeOneCharacter(extractedcommandText);
        if (nearestCorrectedCommand != null) {
            return nearestCorrectedCommand.concat(commandParameters);
        }
        nearestCorrectedCommand = addOneCharacter(extractedcommandText);
        if (nearestCorrectedCommand != null) {
            return nearestCorrectedCommand.concat(commandParameters);
        }
        nearestCorrectedCommand = swapTwoCharacters(extractedcommandText);
        if (nearestCorrectedCommand != null) {
            return nearestCorrectedCommand.concat(commandParameters);
        }

        return commandText;
    }

    /// Methods to search for auto-corrected command words.

    /***
     * Searches for corrected command word by removing one character at a time.
     * @param commandText string from the commandBox that was typed by the user.
     * @return a corrected String, if available. Else returns the same string.
     */
    public static String removeOneCharacter(String commandText) {
        for (int index = 0; index < commandText.length(); index++) {
            StringBuilder stringBuilder = new StringBuilder(commandText);
            stringBuilder.deleteCharAt(index);

            if (isCorrectCommand(stringBuilder.toString())) {
                return stringBuilder.toString();
            }
        }

        return null;
    }

    /***
     * Searches for corrected command word that by adding one character at a time.
     * @param commandText string from the commandBox that was typed by the user.
     * @returns a corrected String, if available. Else returns the same string.
     */
    public static String addOneCharacter(String commandText) {
        String alphabetString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < NUMBER_ALPHABET; i++) {
            char alphabet = alphabetString.charAt(i);
            for (int position = 0; position <= commandText.length(); position++) {
                StringBuilder stringBuilder = new StringBuilder(commandText);
                stringBuilder.insert(position, alphabet);
                String modifiedCommand = stringBuilder.toString();

                if (isCorrectCommand(modifiedCommand)) {
                    return modifiedCommand;
                }
            }
        }
        return null;
    }

    /***
     * Returns correct word when two consecutive alphabets are swapped.
     * @param commandText string from the commandBox that was typed by the user.
     * @returns a corrected String, if available. Else returns the same string.
     */
    public static String swapTwoCharacters(String commandText) {

        for (int position = 0; position < commandText.length() - 1; position++) {
            StringBuilder stringBuilder = new StringBuilder(commandText);
            char temp = stringBuilder.charAt(position);
            stringBuilder.deleteCharAt(position);
            stringBuilder.insert(position + 1, temp);
            String modifiedCommand = stringBuilder.toString();

            if (isCorrectCommand(modifiedCommand)) {
                return modifiedCommand;
            }
        }
        return null;
    }
}

```
###### \java\seedu\address\logic\commands\DeleteJobCommand.java
``` java
/**
 * Deletes a job identified using it's last displayed index from the address book.
 */
public class DeleteJobCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletejob";
    public static final String COMMAND_ALIAS = "dj";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the job identified by the index number used in the last job listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_JOB_SUCCESS = "Deleted job: %1$s";

    private final Index targetIndex;

    private Job jobToDelete;

    public DeleteJobCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(jobToDelete);
        try {
            model.deleteJob(jobToDelete);
            model.refreshReport();
            EventsCenter.getInstance().post(new RefreshReportPanelEvent());
        } catch (JobNotFoundException jnfe) {
            throw new AssertionError("The target job cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_DELETE_JOB_SUCCESS, jobToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Job> lastShownList = model.getFilteredJobList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        }

        jobToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteJobCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteJobCommand) other).targetIndex) // state check
                && Objects.equals(this.jobToDelete, ((DeleteJobCommand) other).jobToDelete));
    }

}

```
###### \java\seedu\address\logic\commands\EditJobCommand.java
``` java
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

```
###### \java\seedu\address\logic\commands\FindJobCommand.java
``` java

/**
 * Lists all jobs in Infinity Book for which Job Title, Location or Tags contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindJobCommand extends Command {
    public static final String COMMAND_WORD = "findjob";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all jobs whose job title, location or tags"
            + "contain any of the specified keywords (case-insensitive) and displays them as a list with index "
            + "numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " frontend backend systems";

    private final Predicate<Job> predicate;

    public FindJobCommand(Predicate<Job> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredJobList(predicate);
        return new CommandResult(getMessageForJobListShownSummary(model.getFilteredJobList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindJobCommand // instanceof handles nulls
                && this.predicate.equals(((FindJobCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListJobsCommand.java
``` java
/**
 * Lists all jobs in the address book to the user.
 */
public class ListJobsCommand extends Command {
    public static final String COMMAND_WORD = "listjobs";

    public static final String MESSAGE_SUCCESS = "Listed all jobs";

    @Override
    public CommandResult execute() {
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

```
###### \java\seedu\address\logic\commands\MatchJobCommand.java
``` java
/**
 * Finds and lists all persons in address book whose location, skills or tags match the job posting.
 * Keyword matching is case sensitive.
 */
public class MatchJobCommand extends Command {
    public static final String COMMAND_WORD = "matchjob";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Matches all persons whose profiles match job posting "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: INDEX...\n"
            + "Example: " + COMMAND_WORD + " 1";

    private PersonMatchesJobPredicate predicate;
    private final Index index;

    public MatchJobCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        ArrayList<Job> listOfJobs = new ArrayList<>(model.getFilteredJobList());
        this.predicate = new PersonMatchesJobPredicate(listOfJobs.get(index.getZeroBased()));
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchJobCommand // instanceof handles nulls
                && this.predicate.equals(((MatchJobCommand) other).predicate)); // state check
    }

}

```
###### \java\seedu\address\logic\commands\MatchJobCommand.java
``` java

```
###### \java\seedu\address\logic\commands\PostJobCommand.java
``` java
/***
 * Posts/adds a job posting to the infinity book.
 */
public class PostJobCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "postjob";
    public static final String COMMAND_ALIAS = "j";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a job posting to the address book. "
            + "Parameters: "
            + PREFIX_JOBTITLE + "JOBTITLE "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_SKILLS + "SKILLS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_JOBTITLE + "Backend Engineer "
            + PREFIX_LOCATION + "Singapore "
            + PREFIX_SKILLS + "SQL, Javascript "
            + PREFIX_TAG + "FreshGrad ";

    public static final String MESSAGE_SUCCESS = "New job added: %1$s";
    public static final String MESSAGE_DUPLICATE_JOB = "This job already exists in the address book";

    private final Job toAdd;

    /**
     * Creates an PostJobCommand to add the specified {@code Job}
     */
    public PostJobCommand(Job job) {
        requireNonNull(job);
        toAdd = job;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addJob(toAdd);
            // System.out.println(model.getFilteredJobList().get(0).toString());
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateJobException e) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostJobCommand // instanceof handles nulls
                && toAdd.equals(((PostJobCommand) other).toAdd));
    }
}

```
###### \java\seedu\address\logic\commands\PostJobCommand.java
``` java

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     *  Updates the master tag list to include tags in {@code job} that are not in the list.
     *  @return a copy of this {@code job} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Job syncWithMasterTagList(Job job) {
        final UniqueTagList jobTags = new UniqueTagList(job.getTags());
        tags.mergeFrom(jobTags);

        // Create map with values = tag object references in the master list
        // used for checking job tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        jobTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Job(
                job.getJobTitle(), job.getLocation(), job.getSkills(), correctTagReferences);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java

    /**
     * Adds a job to the address book.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addJob(Job j) throws DuplicateJobException {
        Job job = syncWithMasterTagList(j);
        jobs.add(job);
    }

    /**
     * Replaces the given job {@code target} in the list with {@code editedJob}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedJob}.
     *
     * @throws DuplicateJobException if updating the job's details causes the person to be equivalent to
     *      another existing job in the list.
     * @throws JobNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateJob(Job target, Job editedJob)
            throws DuplicateJobException, JobNotFoundException {
        requireNonNull(editedJob);
        Job syncedEditedJob = syncWithMasterTagList(editedJob);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        jobs.setJob(target, syncedEditedJob);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws JobNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeJob(Job key) throws JobNotFoundException {
        if (jobs.remove(key)) {
            return true;
        } else {
            throw new JobNotFoundException();
        }
    }

```
###### \java\seedu\address\model\job\exceptions\DuplicateJobException.java
``` java

/**
 * Signals that the operation will result in duplicate Job objects.
 */
public class DuplicateJobException extends DuplicateDataException {
    public DuplicateJobException() {
        super("Operation would result in duplicate jobs");
    }
}

```
###### \java\seedu\address\model\job\exceptions\JobNotFoundException.java
``` java

/**
 * Signals that the operation is unable to find the specified job.
 */
public class JobNotFoundException extends Exception {}

```
###### \java\seedu\address\model\job\exceptions\JobNotFoundException.java
``` java

```
###### \java\seedu\address\model\job\Job.java
``` java
/***
 * Represents a Job in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Job {
    private final JobTitle jobTitle;
    private final Location location;
    private final Skill skills;
    private final UniqueTagList tags;

    public Job(JobTitle jobTitle, Location location, Skill skills, Set<Tag> tags) {
        this.jobTitle = jobTitle;
        this.location = location;
        this.skills = skills;
        this.tags = new UniqueTagList(tags);
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public Location getLocation() {
        return location;
    }

    public Skill getSkills() {
        return skills;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Job)) {
            return false;
        }

        Job otherJob = (Job) other;
        return otherJob.getJobTitle().equals(this.getJobTitle())
                && otherJob.getLocation().equals(this.getLocation())
                && otherJob.getSkills().equals(this.getSkills())
                && otherJob.getTags().equals(this.getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(jobTitle, skills, location, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getJobTitle())
                .append(" Location: ")
                .append(getLocation())
                .append(" Skills: ")
                .append(getSkills())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

```
###### \java\seedu\address\model\job\JobMatchesKeywordsPredicate.java
``` java

/**
 * Tests that a {@code Job}'s {@code JobTitle} or {@code Location} or {@code Tags} matches any of the
 * keywords given.
 */
public class JobMatchesKeywordsPredicate implements Predicate<Job> {
    private final List<String> keywords;

    public JobMatchesKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Job job) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(job.getJobTitle().fullTitle, keyword)
                        || StringUtil.containsWordIgnoreCase(job.getLocation().toString(), keyword)
                        || job.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobMatchesKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((JobMatchesKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\job\JobTitle.java
``` java
/**
 * Represents a Job's title in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */

public class JobTitle {
    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Job title should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the jobTitle must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullTitle;

    /**
     * Constructs a {@code JobTitle}.
     *
     * @param title A valid job title.
     */
    public JobTitle(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_TITLE_CONSTRAINTS);
        this.fullTitle = title;
    }

    /**
     * Returns true if a given string is a valid job title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullTitle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.job.JobTitle // instanceof handles nulls
                && this.fullTitle.compareTo(((JobTitle) other).fullTitle) == 0); // state check
    }

    @Override
    public int hashCode() {
        return fullTitle.hashCode();
    }
}
```
###### \java\seedu\address\model\job\Location.java
``` java
/**
 * Represents a Job's location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Job locations can take any values, and it should not be blank";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.value = location;
    }

    /**
     * Returns true if a given string is a valid job location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.compareTo(((Location) other).value) == 0); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\job\PersonMatchesJobPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code skills, address or tags} matches any of the keywords given.
 */
public class PersonMatchesJobPredicate implements Predicate<Person> {
    private final List<String> locationKeywords;
    private final List<String> skillsKeywords;
    private final List<String> tagsKeywords;

    private final boolean notLocationBound;
    private final boolean notTagsBound;
    private final boolean notSkillsBound;

    public PersonMatchesJobPredicate(Job job) {
        this.locationKeywords = new ArrayList<String>();
        this.tagsKeywords = new ArrayList<String>();
        this.skillsKeywords = new ArrayList<String>();

        for (String entry : job.getSkills().getSkillSet()) {
            if (!(entry.compareTo("ALL") == 0)) {
                this.skillsKeywords.add(entry);
            }
        }

        for (String entry : job.getLocation().toString().split(",")) {
            if (!(entry.compareTo("ALL") == 0)) {
                this.locationKeywords.add(entry);
            }
        }

        for (String entry : job.getTags().toString().split(",")) {
            if (!(entry.compareTo("ALL") == 0)) {
                entry = entry.trim();
                entry = entry.replaceAll("\\[", "");
                entry = entry.replaceAll("\\]", "");
                this.tagsKeywords.add(entry);
            }
        }

        notLocationBound = (job.getLocation().toString().equals("ALL"));
        notTagsBound = (job.getTags().toString().contains("ALL"));
        notSkillsBound = (job.getSkills().toString().equals("ALL"));
    }

    @Override
    public boolean test(Person person) {
        StringBuilder stringBuilder = new StringBuilder();
        String toMatchPersonAddress = setUpAddressSearch(person, stringBuilder);

        stringBuilder = new StringBuilder();
        String toMatchPersonSkills = setUpSkillsSearch(person, stringBuilder);

        stringBuilder = new StringBuilder();
        String toMatchPersonTags = setUpTagsSearch(person, stringBuilder);

        boolean locationMatch =
                locationKeywords.stream().anyMatch(keyword -> toMatchPersonAddress.contains(keyword.toLowerCase()));
        boolean skillsMatch =
                skillsKeywords.stream().anyMatch(keyword -> toMatchPersonSkills.contains(keyword.toLowerCase()));
        boolean tagsMatch =
                tagsKeywords.stream().anyMatch(keyword -> toMatchPersonTags.contains(keyword.toLowerCase()));

        locationMatch = isLocationMatchSatisfied(locationMatch);
        skillsMatch = isSkillsMatchSatisfied(skillsMatch);
        tagsMatch = isTagsMatchSatisfied(tagsMatch);

        return locationMatch && skillsMatch && tagsMatch;
    }

    /***
     * Checks if tag match conditions are satisfied.
     * @param tagsMatch is the boolean variable to be set
     * @return appropriate value of tagsMatch
     */
    private boolean isTagsMatchSatisfied(boolean tagsMatch) {
        if (notTagsBound) {
            tagsMatch = true;
        }
        return tagsMatch;
    }

    /***
     * Checks if skills match conditions are satisfied.
     * @param skillsMatch is the boolean variable to be set
     * @return appropriate value of skillsMatch
     */
    private boolean isSkillsMatchSatisfied(boolean skillsMatch) {
        if (notSkillsBound) {
            skillsMatch = true;
        }
        return skillsMatch;
    }

    /***
     * Checks if location match conditions are satisfied.
     * @param locationMatch is the boolean variable to be set
     * @return appropriate value of locationMatch
     */
    private boolean isLocationMatchSatisfied(boolean locationMatch) {
        if (notLocationBound) {
            locationMatch = true;
        }
        return locationMatch;
    }

    /***
     * Identifies the set of keywords to be matched for tags based matching of candidates.
     * @param person who is tested for a match currently
     * @param stringBuilder which accumulates the keyword string
     * @return the complete keywords string
     */
    private String setUpTagsSearch(Person person, StringBuilder stringBuilder) {
        String[] toMatchPersonTagsWords = person.getTags().toString().split(",");
        for (String entry : toMatchPersonTagsWords) {
            if (!(entry.compareTo("ALL") == 0)) {
                entry = entry.trim().toLowerCase();
                entry = entry.replaceAll("\\[", "");
                entry = entry.replaceAll("\\]", "");
                stringBuilder.append(" " + entry + " ");
            }
        }
        return stringBuilder.toString();
    }

    /***
     * Identifies the set of keywords to be matched for skills based matching of candidates.
     * @param person who is tested for a match currently
     * @param stringBuilder which accumulates the keyword string
     * @return the complete keywords string
     */
    private String setUpSkillsSearch(Person person, StringBuilder stringBuilder) {
        String[] toMatchPersonSkillsWords = person.getSkills().toString().split(",");
        for (String entry : toMatchPersonSkillsWords) {
            if (!(entry.compareTo("ALL") == 0)) {
                entry = entry.trim().toLowerCase();
                stringBuilder.append(" " + entry + " ");
            }
        }
        return stringBuilder.toString();
    }

    /***
     * Identifies the set of keywords to be matched for location based matching of candidates.
     * @param person who is tested for a match currently
     * @param stringBuilder which accumulates the keyword string
     * @return the complete keywords string
     */
    private String setUpAddressSearch(Person person, StringBuilder stringBuilder) {
        String[] toMatchPersonAddressWords = person.getAddress().toString().split(",");
        for (String entry : toMatchPersonAddressWords) {
            if (!(entry.compareTo("ALL") == 0)) {
                entry = entry.trim().toLowerCase();
                stringBuilder.append(" " + entry + " ");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonMatchesJobPredicate// instanceof handles nulls
                && this.locationKeywords.equals((
                        (PersonMatchesJobPredicate) other).locationKeywords)
                && this.skillsKeywords.equals((
                (PersonMatchesJobPredicate) other).skillsKeywords)
                && this.tagsKeywords.equals((
                (PersonMatchesJobPredicate) other).tagsKeywords)); // state check
    }
}
```
###### \java\seedu\address\model\job\UniqueJobList.java
``` java
/**
 * A list of jobs that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Job#equals(Object)
 */
public class UniqueJobList implements Iterable<Job> {

    private final ObservableList<Job> internalList = FXCollections.observableArrayList();

    /***
     * Returns true if the list contains an equivalent job as the given argument.
     */
    public boolean contains(Job toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a job to the list.
     *
     * @throws DuplicateJobException if the job to add is a duplicate of an existing person in the list.
     */
    public void add(Job toAdd) throws DuplicateJobException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateJobException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the job {@code target} in the list with {@code editedJob}.
     *
     * @throws DuplicateJobException if the replacement is equivalent to another existing person in the list.
     * @throws seedu.address.model.job.exceptions.JobNotFoundException if {@code target} could not be found in the list.
     */
    public void setJob(Job target, Job editedJob)
            throws DuplicateJobException, JobNotFoundException {
        requireNonNull(editedJob);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new JobNotFoundException();
        }

        if (!target.equals(editedJob) && internalList.contains(editedJob)) {
            throw new DuplicateJobException();
        }

        internalList.set(index, editedJob);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Job toRemove) throws JobNotFoundException {
        requireNonNull(toRemove);
        final boolean jobFoundAndDeleted = internalList.remove(toRemove);
        if (!jobFoundAndDeleted) {
            throw new JobNotFoundException();
        }
        return jobFoundAndDeleted;
    }

    public void setJobs(UniqueJobList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setJobs(List<Job> jobs) throws DuplicateJobException {
        requireAllNonNull(jobs);
        final UniqueJobList replacement = new UniqueJobList();
        for (final Job job : jobs) {
            replacement.add(job);
        }
        setJobs(replacement);
    }

    public Job getJob(Index index) {
        return internalList.get(index.getZeroBased());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Job> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Job> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueJobList // instanceof handles nulls
                && this.internalList.equals(((UniqueJobList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Filtered Job List Accessors =============================================================

    @Override
    public synchronized void addJob(Job job) throws DuplicateJobException {
        addressBook.addJob(job);
        updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateJob(Job target, Job editedJob)
            throws DuplicateJobException, JobNotFoundException {
        requireAllNonNull(target, editedJob);

        addressBook.updateJob(target, editedJob);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteJob(Job target) throws JobNotFoundException {
        addressBook.removeJob(target);
        indicateAddressBookChanged();
    }

    @Override
    public void updateFilteredJobList(Predicate<Job> predicate) {
        requireNonNull(predicate);
        filteredJobs.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Job} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Job> getFilteredJobList() {
        return FXCollections.unmodifiableObservableList(filteredJobs);
    }

```
###### \java\seedu\address\model\skill\Skill.java
``` java
/***
 * Represents a Job's and Person's required Skills in the Infinity Book.
 * Guarantees: is valid as declared in {@link #isValidSkill(String)}
 */
public class Skill {
    public static final String SKILL_VALIDATION_REGEX = "[^\\s].*";
    public static final String MESSAGE_SKILL_CONSTRAINTS = "Skills can take any values, and should not be blank";
    public final String value;

    private Set<String> skillSet;

    public Skill(String skills) {
        requireNonNull(skills);
        checkArgument(isValidSkill(skills), MESSAGE_SKILL_CONSTRAINTS);
        this.value = skills;
        String[] skillsArray = skills.split(",");

        skillSet = new HashSet<String>();
        for (String entry : skillsArray) {
            skillSet.add(entry.trim());
        }
    }

    public Set<String> getSkillSet() {
        return skillSet;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Skill // instanceof handles nulls
                && this.value.compareTo(((Skill) other).value) == 0); // state check
    }

    public String toString() {
        return value;
    }

    /***
     * Checks if a given string is a valid Skill.
     * @param test is the String to be tested for validity
     * @return true if it is a valid skill.
     */
    public static boolean isValidSkill(String test) {
        return test.matches(SKILL_VALIDATION_REGEX);
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedJob.java
``` java
/**
 * JAXB-friendly version of the Job.
 */
public class XmlAdaptedJob {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job's %s field is missing!";

    @XmlElement(required = true)
    private String jobTitle;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String skills;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedJob.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJob() {}

    /**
     * Constructs an {@code XmlAdaptedJob} with the given job details.
     */
    public XmlAdaptedJob(String jobTitle, String location, String skills, List<XmlAdaptedTag> tagged) {
        this.jobTitle = jobTitle;
        this.location = location;
        this.skills = skills;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Job into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedJob(Job source) {
        jobTitle = source.getJobTitle().fullTitle;
        location = source.getLocation().value;
        skills = source.getSkills().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted job object into the model's {@code Job} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job
     */
    public Job toModelType() throws IllegalValueException {
        final List<Tag> jobTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            jobTags.add(tag.toModelType());
        }

        if (this.jobTitle == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, JobTitle.class.getSimpleName()));
        }
        if (!JobTitle.isValidTitle(this.jobTitle)) {
            throw new IllegalValueException(JobTitle.MESSAGE_TITLE_CONSTRAINTS);
        }
        final JobTitle jobTitle = new JobTitle(this.jobTitle);

        if (this.location == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(this.location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final Location location = new Location(this.location);

        if (this.skills == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Skill.class.getSimpleName()));
        }
        if (!Skill.isValidSkill(this.skills)) {
            throw new IllegalValueException(Skill.MESSAGE_SKILL_CONSTRAINTS);
        }
        final Skill skill = new Skill(this.skills);

        final Set<Tag> tags = new HashSet<>(jobTags);
        return new Job(jobTitle, location, skill, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedJob)) {
            return false;
        }

        XmlAdaptedJob otherJob = (XmlAdaptedJob) other;
        return Objects.equals(jobTitle, otherJob.jobTitle)
                && Objects.equals(location, otherJob.location)
                && Objects.equals(skills, otherJob.skills)
                && tagged.equals(otherJob.tagged);
    }
}

```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Updates the text field with the suggested text by auto-correct,
     * if there exists a suggestion.
     */
    private void navigateToCorrectedCommand() {
        setUpCommandCorrection();
        if (CommandCorrection.isCorrectCommand(commandTextField.getText())) {
            return;
        }

        String textToCorrect = commandTextField.getText();
        replaceText(CommandCorrection.nearestCorrection(textToCorrect));
    }

    /***
     * Updates the text field with suggestion from auto-complete,
     * if there exists a suggested completion
     */
    private void navigateToCompletedCommand() {
        setUpCommandCompletion();
        String textToComplete = commandTextField.getText().trim();

        if (CommandCorrection.noTextToComplete(textToComplete)) {
            return;
        }

        updateTabCounter(textToComplete);
        textToComplete = updateTextToComplete(textToComplete);

        setRecentInput(textToComplete);
        int suggestionToChoose = CommandCorrection.getTabCounter();

        ArrayList<String> suggestions = getSuggestions(textToComplete);
        String chosenString = chooseSuggestion(suggestions, suggestionToChoose,
                commandTextField.getText());
        if (suggestions.isEmpty()) {
            raise(new CommandCorrectedEvent(
                    String.format(CommandCorrection.NO_MATCHES_FEEDBACK_TO_USER, chosenString)));
        } else {
            raise(new CommandCorrectedEvent(
                    String.format(CommandCorrection.MATCH_FOUND_FEEDBACK_TO_USER, suggestions.toString()
                            .replace(" ", ""))));
        }
        replaceText(chosenString);
    }
```
###### \java\seedu\address\ui\JobCard.java
``` java
/**
 * An UI component that displays information of a {@code Job}.
 */
public class JobCard extends UiPart<Region> {
    private static final String FXML = "JobListCard.fxml";
    private static final String[] TAG_COLORS = {"red", "pink", "blue"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Job job;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label jobTitle;
    @FXML
    private Label jobLocation;
    @FXML
    private Label jobSkills;
    @FXML
    private FlowPane tags;

    public JobCard(Job job, int displayedIndex) {
        super(FXML);
        this.job = job;
        id.setText(displayedIndex + ". ");
        jobTitle.setText(job.getJobTitle().fullTitle);
        jobLocation.setText(job.getLocation().value);
        jobSkills.setText(job.getSkills().toString());
        initTags(job);
    }

    /**
     * Creates colored tags for each tag assigned to the {@code Job}.
     * @param job is the job for which the tags are being populated.
     */
    private void initTags(Job job) {
        job.getTags().forEach(tag -> {
            Label label = new Label(tag.tagName);
            label.getStyleClass().add(getTagColorFor(tag.tagName));
            tags.getChildren().add(label);
        });
    }

    /**
     * Chooses a color for the tag based on hashcode
     * @param tagName for which color is chosen.
     * @return string containing the color name.
     */
    private String getTagColorFor(String tagName) {
        //some explanation
        return TAG_COLORS[Math.abs(tagName.hashCode()) % TAG_COLORS.length];
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobCard)) {
            return false;
        }

        // state check
        JobCard card = (JobCard) other;
        return id.getText().equals(card.id.getText())
                && job.equals(card.job);
    }
}

```
###### \java\seedu\address\ui\JobListPanel.java
``` java
/**
 * Panel containing the list of jobs.
 */
public class JobListPanel extends UiPart<Region> {
    private static final String FXML = "JobListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(JobListPanel.class);

    @FXML
    private ListView<JobCard> jobListView;

    public JobListPanel(ObservableList<Job> jobList) {
        super(FXML);
        setConnections(jobList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Job> jobList) {
        ObservableList<JobCard> mappedList = EasyBind.map(
                jobList, (job) -> new JobCard(job, jobList.indexOf(job) + 1));
        jobListView.setItems(mappedList);
        jobListView.setCellFactory(listView -> new JobListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        jobListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in job list panel changed to : '" + newValue + "'");
                        raise(new JobPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    @Subscribe
    private void handleJumpToJobListRequestEvent(JumpToJobListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Scrolls to the {@code JobCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            jobListView.scrollTo(index);
            jobListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code JobCard}.
     */
    class JobListViewCell extends ListCell<JobCard> {

        @Override
        protected void updateItem(JobCard job, boolean empty) {
            super.updateItem(job, empty);

            if (empty || job == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(job.getRoot());
            }
        }
    }
}
```
###### \resources\view\JobListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="70" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15" />
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="jobTitle" text="\$jobtitle" styleClass="cell_big_label" />
            </HBox>
            <FlowPane fx:id="tags" />
            <Label fx:id="jobLocation" styleClass="cell_small_label" text="\$location" />
            <Label fx:id="jobSkills" styleClass="cell_small_label" text="\$skills" />
        </VBox>
    </GridPane>
</HBox>
```
###### \resources\view\JobListPanel.fxml
``` fxml
<!--Referenced for <Text> https://github.com/kush1509/contactHeRo/blob/master/src/main/resources/view/PersonListPanel.fxml-->
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" style="-fx-background-color: #ede7f6;" >
    <Text strokeType="OUTSIDE" strokeWidth="0.0" text="Job Postings" textAlignment="CENTER" wrappingWidth="200.27490234375">
        <VBox.margin>
            <Insets bottom="5.0" top="5.0" />
        </VBox.margin>
    </Text>
    <ListView fx:id="jobListView" VBox.vgrow="ALWAYS" />
</VBox>

```

# Zhu-Jiahui
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        if (ListPanelController.isCurrentDisplayActiveList()) {
            model.updateFilteredStudentList(predicate);
            model.updateFilteredTutorList(predicate);
            return new CommandResult(getMessageForClientListShownSummary(
                    model.getFilteredStudentList().size(), model.getFilteredTutorList().size()));
        } else {
            model.updateFilteredClosedStudentList(predicate);
            model.updateFilteredClosedTutorList(predicate);
            return new CommandResult(getMessageForClientListShownSummary(
                    model.getFilteredClosedStudentList().size(), model.getFilteredClosedTutorList().size()));
        }
    }
```
###### \java\seedu\address\logic\commands\MatchCommand.java
``` java
/**
 * Match the entered client and lists all clients in address book that has similar attributes to the matched client.
 */
public class MatchCommand extends Command {

    public static final String COMMAND_WORD = "match";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all clients that match all the fields listed by the person entered.\n"
            + "Example: " + COMMAND_WORD + " 1" + " c/t";

    private final Index targetIndex;
    private final Category category;

    private Client clientToMatch;

    public MatchCommand(Index index, Category category) {
        this.targetIndex = index;
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }
        List<Client> lastShownList;

        if (category.isStudent()) {
            lastShownList = model.getFilteredStudentList();
        } else {
            lastShownList = model.getFilteredTutorList();
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        clientToMatch = lastShownList.get(targetIndex.getZeroBased());

        MatchContainsKeywordsPredicate predicate = new MatchContainsKeywordsPredicate(clientToMatch);
        if (category.isStudent()) {
            model.updateFilteredTutorList(predicate);
            model.updateFilteredStudentList(new MatchContainsPersonsPredicate(clientToMatch));
            model.updateRankedTutorList();

        } else {
            model.updateFilteredStudentList(predicate);
            model.updateFilteredTutorList(new MatchContainsPersonsPredicate(clientToMatch));
            model.updateRankedStudentList();

        }

        return new CommandResult(getMessageForClientListShownSummary(
                model.getFilteredStudentList().size(), model.getFilteredTutorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchCommand // instanceof handles nulls
                && this.targetIndex.equals(((MatchCommand) other).targetIndex))
                && this.category.equals(((MatchCommand) other).category); // state check
    }
}
```
###### \java\seedu\address\logic\parser\MatchCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MatchCommand object
 */
public class MatchCommandParser implements Parser<MatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MatchCommand
     * and returns an MatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_CATEGORY)
                || argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        Index index;
        Category category;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
            category = ParserUtil.parseCategory(argumentMultimap.getValue(PREFIX_CATEGORY)).get();
            return new MatchCommand(index, category);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code addressBook}
     */

    @Override
    public void updateRankedStudentList() {
        Comparator<Client> rankStudent = new RankComparator();
        sortedFilteredStudents.setComparator(rankStudent);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code addressBook}
     */

    @Override
    public void updateRankedTutorList() {
        Comparator<Client> rankTutor = new RankComparator();
        sortedFilteredTutors.setComparator(rankTutor);
    }

    /**
     * Reset {@code rank}, {@code MatchedGrade}, {@code MatchedLocation} and {@code MatchedSubject} in all
     * Clientlist to default value
     */

    @Override
    public void resetHighLight() {
        for (Client client : filteredTutors) {
            client.setRank(0);
            client.setMatchedLocation(false);
            client.setMatchedGrade(false);
            client.setMatchedSubject(false);
        }
        for (Client client : filteredStudents) {
            client.setRank(0);
            client.setMatchedLocation(false);
            client.setMatchedGrade(false);
            client.setMatchedSubject(false);
        }

        for (Client client : sortedFilteredStudents) {
            client.setRank(0);
            client.setMatchedLocation(false);
            client.setMatchedGrade(false);
            client.setMatchedSubject(false);
        }

        for (Client client : sortedFilteredTutors) {
            client.setRank(0);
            client.setMatchedLocation(false);
            client.setMatchedGrade(false);
            client.setMatchedSubject(false);
        }

    }
```
###### \java\seedu\address\model\person\MatchContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Client}'s {@code Location, Grade and Subject} matches the entered {@code Client}'s
 * {@code Location, Grade and Subject}.
 */
public class MatchContainsKeywordsPredicate implements Predicate<Client> {
    private final Client client;

    public MatchContainsKeywordsPredicate(Client client) {
        this.client = client;
    }

    @Override
    public boolean test(Client other) {
        boolean isMatch = false;
        int rank = 0;

        if (StringUtil.containsWordIgnoreCase(other.getLocation().toString(), client.getLocation().toString())) {
            isMatch = true;
            other.setMatchedLocation(isMatch);
            rank++;
        }
        if (GradeUtil.containsGradeIgnoreCase(other.getGrade().value, client.getGrade().toString()
                .split("\\s+")[0])) {
            isMatch = true;
            other.setMatchedGrade(isMatch);
            rank++;
        }
        if (StringUtil.containsWordIgnoreCase(other.getSubject().value, client.getSubject().toString()
                .split("\\s+")[0])) {
            isMatch = true;
            other.setMatchedSubject(isMatch);
            rank++;
        }
        other.setRank(rank);
        return isMatch;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchContainsKeywordsPredicate // instanceof handles nulls
                && this.client.equals(((MatchContainsKeywordsPredicate) other).client)); // state check
    }

}
```
###### \java\seedu\address\model\person\MatchContainsPersonsPredicate.java
``` java
/**
 * Tests that a {@code Client}'s attributes matches all of the attributes of the entered {@code Client}'s.
 */
public class MatchContainsPersonsPredicate implements Predicate<Client> {
    private final Client client;

    public MatchContainsPersonsPredicate(Client client) {
        this.client = client;
    }

    @Override
    public boolean test(Client other) {
        return other.toString().equals(client.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchContainsPersonsPredicate // instanceof handles nulls
                && this.client.equals(((MatchContainsPersonsPredicate) other).client)); // state check
    }

}
```
###### \java\seedu\address\model\person\SearchContainsKeywordsPredicate.java
``` java
    @Override
    public boolean test(Client client) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getName().fullName, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getEmail().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getAddress().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getPhone().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getLocation().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> GradeUtil.containsGradeIgnoreCase(client.getGrade().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getSubject().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getCategory().value, keyword));
    }
```
###### \java\seedu\address\ui\ClientCard.java
``` java
    /**
     * Initialises Location
     * If Location is matched with the client, Location field will be highlighted.
     * @param client
     */

    private void intplaces(Client client) {

        places.setText(client.getLocation().value);

        if (client.getMatchedLocation() == true) {
            places.getStyleClass().add(MATCH_COLOUR_STYLE);
        } else {
            places.getStyleClass().add(UNMATCH_COLOUR_STYLE);
        }
    }

    /**
     * Initialises Grade
     * If Grade is matched with the client, Grade field will be highlighted.
     * @param client
     */

    private void intGrades(Client client) {

        grades.setText(client.getGrade().value);

        if (client.getMatchedGrade() == true) {
            grades.getStyleClass().add(MATCH_COLOUR_STYLE);
        } else {
            grades.getStyleClass().add(UNMATCH_COLOUR_STYLE);
        }
    }

    /**
     *@author Zhu-Jiahui
     * Initialises Subject
     * If Subject is matched with the client, Subject field will be highlighted.
     * @param client
     */

    private void intSubjects(Client client) {
        subjects.setText(client.getSubject().value);

        if (client.getMatchedSubject() == true) {
            subjects.getStyleClass().add(MATCH_COLOUR_STYLE);
        } else {
            subjects.getStyleClass().add(UNMATCH_COLOUR_STYLE);
        }
    }
```

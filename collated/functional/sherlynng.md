# sherlynng
###### \java\seedu\address\logic\commands\RateCommand.java
``` java
/**
 * Adds a rate to person in STUtor.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";
    public static final String COMMAND_WORD_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds rating to person identified by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer), RATE (must be an integer between 0 and 5 (inclusive)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_RATE + "4.5";

    public static final String MESSAGE_RATE_PERSON_SUCCESS = "Added Rating to %1$s: " + "%2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in STUtor.";

    private final Index targetIndex;
    private Rate newRate;

    private Person personToEdit;
    private Person editedPerson;

    public RateCommand(Index targetIndex, Rate newRate) {
        requireNonNull(targetIndex);
        requireNonNull(newRate);

        this.targetIndex = targetIndex;
        this.newRate = newRate;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.rateRemarkPerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_RATE_PERSON_SUCCESS, editedPerson.getName(), newRate));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = createPersonWithNewRate(personToEdit, newRate);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}.
     */
    private static Person createPersonWithNewRate(Person personToEdit, Rate newRate) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Price price = personToEdit.getPrice();
        Subject subject = personToEdit.getSubject();
        Level level = personToEdit.getLevel();
        Status status = personToEdit.getStatus();
        Role role = personToEdit.getRole();
        Remark remark = personToEdit.getRemark();
        Set<PairHash> pairHashes = personToEdit.getPairHashes();

        Rate oldRate = personToEdit.getRate();

        if (newRate.getIsAbsolute()) {
            newRate.setCount(1); // reset count when set absolute
        } else {
            newRate = Rate.accumulatedValue(oldRate, newRate);
        }

        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(personToEdit.getTags());
        attributeTags = AttributeTagSetter.addNewAttributeTags(attributeTags, price, subject, level, status, role);

        return new Person(name, phone, email, address, price, subject, level, status, role,
                          attributeTags, remark, newRate, pairHashes);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RateCommand // instanceof handles nulls
                && this.targetIndex.equals(((RateCommand) other).targetIndex)
                && this.newRate.equals(((RateCommand) other).newRate));
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Adds a remark to person in STutor.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_WORD_ALIAS = "rk";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a remark to person identified by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK + "Hardworking student"
            + "\t\t OR \t\t" + COMMAND_WORD + " 1 edit" + "\t\t OR \t\t" + COMMAND_WORD + " edit 1";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Added Remark to %1$s: " + "%2$s";
    public static final String MESSAGE_EDIT_REMARK_SUCCESS = "Editing Remark of %1$s...";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in STUtor.";

    private final Index targetIndex;
    private Remark newRemark;
    private boolean isEditRemark;

    private Person personToEdit;
    private Person editedPerson;

    public RemarkCommand(Index targetIndex, Remark newRemark) {
        requireNonNull(targetIndex);
        requireNonNull(newRemark);

        this.targetIndex = targetIndex;
        this.newRemark = newRemark;
        this.isEditRemark = false;
    }

    public RemarkCommand(Index targetIndex, Remark newRemark, boolean isEditRemark) {
        requireNonNull(targetIndex);
        requireNonNull(newRemark);

        this.targetIndex = targetIndex;
        this.newRemark = newRemark;
        this.isEditRemark = isEditRemark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        if (!isEditRemark) {
            try {
                model.rateRemarkPerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS,
                    editedPerson.getName(), editedPerson.getRemark()));
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_EDIT_REMARK_SUCCESS, personToEdit.getName()));
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(targetIndex.getZeroBased());

        if (isEditRemark) {
            String formattedRemark = COMMAND_WORD + " " + targetIndex.getOneBased() + " " + PREFIX_REMARK
                                     + personToEdit.getRemark().toString();
            EventsCenter.getInstance().post(new EditRemarkEvent(formattedRemark));
        } else {
            editedPerson = createPersonWithNewRemark(personToEdit, newRemark);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}.
     */
    private static Person createPersonWithNewRemark(Person personToEdit, Remark newRemark) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Price price = personToEdit.getPrice();
        Subject subject = personToEdit.getSubject();
        Level level = personToEdit.getLevel();
        Status status = personToEdit.getStatus();
        Role role = personToEdit.getRole();
        Rate rate = personToEdit.getRate();
        Set<PairHash> pairHashes = personToEdit.getPairHashes();

        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(personToEdit.getTags());
        attributeTags = AttributeTagSetter.addNewAttributeTags(attributeTags, price, subject, level, status, role);

        return new Person(name, phone, email, address, price, subject, level, status, role,
                          attributeTags, newRemark, rate, pairHashes);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemarkCommand) other).targetIndex)
                && this.newRemark.equals(((RemarkCommand) other).newRemark)
                && this.isEditRemark == ((RemarkCommand) other).isEditRemark);

    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();

        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(parseRemark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String rate} into a {@code Rate}.
     * Leading and trailing whitespaces will be trimmed.
     * Checks if user wants absolute or cumulative rating.
     */
    public static Rate parseRate(String rate) throws IllegalValueException {
        requireNonNull(rate);

        if (rate.equals("")) {
            throw new IllegalValueException(Rate.MESSAGE_RATE_CONSTRAINTS);
        }

        String trimmedRate = rate.trim();

        boolean isAbsolute = checkRateIsAbsolute(trimmedRate);

        if (isAbsolute) {
            trimmedRate = trimmedRate.substring(0, trimmedRate.length() - 1);
        }

        if (!Rate.isValidRate(trimmedRate)) {
            throw new IllegalValueException(Rate.MESSAGE_RATE_CONSTRAINTS);
        }

        return new Rate(Double.parseDouble(trimmedRate), isAbsolute);
    }

    /**
     * Parses a {@code Optional<String> rate} into an {@code Optional<Rate>} if {@code rate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Rate> parseRate(Optional<String> rate) throws IllegalValueException {
        requireNonNull(rate);
        return rate.isPresent() ? Optional.of(parseRate(rate.get())) : Optional.empty();
    }

    /**
     * Checks if new rate is of absolute type
     * @param rate
     * @return true if rate is of absolute type
     */
    private static boolean checkRateIsAbsolute(String rate) {
        Character lastChar = rate.charAt(rate.length() - 1);

        // user wants absolute rate value
        if (lastChar.equals('-')) {
            return true;
        }
        return false;
    }
}
```
###### \java\seedu\address\logic\parser\RateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RateCommand object
 */
public class RateCommandParser implements Parser<RateCommand> {

    /**
     * Parses the given {@code String} with rate in the context of the RateCommand
     * and returns a RateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_RATE);

        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_RATE)) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE);
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE);
        }

        Rate rate;
        try {
            rate = ParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE)).get();

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new RateCommand(index, rate);
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
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} with remark in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        boolean isEditRemark;

        isEditRemark = argMultimap.getPreamble().contains("edit");

        if (!isEditRemark && !arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE);
        }

        try {
            if (isEditRemark) {
                String replacedPreamble = argMultimap.getPreamble().replace("edit", "");
                index = ParserUtil.parseIndex(replacedPreamble);
            } else {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage());
        }

        Remark remark;
        if (isEditRemark) {
            remark = ParserUtil.parseRemark("");
            return new RemarkCommand(index, remark, isEditRemark);
        } else {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).orElse(new Remark(""));
        }

        return new RemarkCommand(index, remark);
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
###### \java\seedu\address\model\person\Rate.java
``` java
/**
 * Represents a Person's rating in STUtor.
 */
public class Rate {

    public static final String RATE_VALIDATION_REGEX = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])$";
    public static final String RATE_VALIDATION_REGEX_ABSOLUTE = "^(?:5(?:\\.0)?|[0-4](?:\\.[0-9])?|0?\\.[0-9])" + "-";
    public static final String MESSAGE_RATE_CONSTRAINTS =
            "Rate must be a number between 0 and 5 (inclusive) with at most 1 decimal place";

    private double value;
    private int count;
    private boolean isAbsolute;

    /**
     * Constructs an {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rate (double rating, boolean isAbsolute) {
        requireNonNull(rating);

        this.value = rating;
        this.isAbsolute = isAbsolute;
    }

    /**
     * Initializes a person's rating.
     * @return {@code Rate} with value of 0.0 and count 0.
     */
    public static Rate initializeRate() {
        Rate initializedRate = new Rate(0.0, true);
        initializedRate.setCount(0);

        return initializedRate;
    }

    /**
     * Accumulates a person's rating value.
     * @param oldRate
     * @param newRate
     * @return {@code Rate} that contains updated value and count.
     */
    public static Rate accumulatedValue (Rate oldRate, Rate newRate) {
        double newValue;

        newValue = oldRate.getValue() + newRate.getValue();
        newRate = new Rate(newValue, true);
        newRate.setCount(oldRate.getCount() + 1);

        return newRate;
    }

    /**
     * Returns true if a given string is a valid person rate.
     */
    public static boolean isValidRate(String test) {
        return test.matches(RATE_VALIDATION_REGEX) || test.matches(RATE_VALIDATION_REGEX_ABSOLUTE);
    }

    public double getValue() {
        return this.value;
    }

    /**
     * Gets rate value to be displayed.
     * @return {@code double} rate value rounded off to nearest 1 decimal place.
     */
    public double getDisplayedValue() {
        double displayedValue = 0;

        if (count != 0) {
            displayedValue = (double) Math.round(((value / count) * 10)) / 10;
        }
        return displayedValue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getIsAbsolute() {
        return isAbsolute;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rate // instanceof handles nulls
                && this.value == ((Rate) other).value
                && this.count == ((Rate) other).count
                && this.isAbsolute == ((Rate) other).isAbsolute); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in STUtor.
 */
public class Remark {

    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        case TAB:
            keyEvent.consume();
            autofill();
            break;
        case DELETE:
            keyEvent.consume();
            deletePreviousPrefix();
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Sets {@code CommandBox}'s text field with command format and
     * if next field is present, caret is positioned to the next field.
     */
    private void autofill() {
        String input = commandTextField.getText();
        int nextCaretPosition = -1;
        boolean isFirstTime = false; // check for commands that have different behaviors between first and other tabs

        isFirstTime = autofillCommand(input, isFirstTime);
        autofillBehavior(isFirstTime);
    }

    /**
     * Autofills the command depending on user input.
     * @param input
     * @param isFirstTime
     * @return true if it is the first time tab is pressed. Else false.
     */
    private boolean autofillCommand(String input, boolean isFirstTime) {
        switch (input) {
        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(AddCommand.COMMAND_WORD + " " + PREFIX_NAME + " " + PREFIX_PHONE + " "
                    + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_PRICE + " " + PREFIX_SUBJECT + " "
                    + PREFIX_LEVEL + " " + PREFIX_ROLE);
            isFindNextField = true;
            isMatchCommand = false;
            break;
        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(EditCommand.COMMAND_WORD + " 1 " + PREFIX_NAME + " " + PREFIX_PHONE + " "
                    + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_PRICE + " " + PREFIX_SUBJECT + " "
                    + PREFIX_LEVEL + " " + PREFIX_ROLE);
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            isMatchCommand = false;
            break;
        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(RemarkCommand.COMMAND_WORD + " 1 " + PREFIX_REMARK);
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            isMatchCommand = false;
            break;
        case RateCommand.COMMAND_WORD:
        case RateCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(RateCommand.COMMAND_WORD + " 1 " + PREFIX_RATE);
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            isMatchCommand = false;
            break;
        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(SelectCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            isFindNextField = false;
            isMatchCommand = false;
            break;
        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(DeleteCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            isFindNextField = false;
            isMatchCommand = false;
            break;
        case UnmatchCommand.COMMAND_WORD:
        case UnmatchCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(UnmatchCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            isFindNextField = false;
            isMatchCommand = false;
            break;
        case MatchCommand.COMMAND_WORD:
        case MatchCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(MatchCommand.COMMAND_WORD + " 1 2");
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            break;
        default:
            // no autofill
        }
        return isFirstTime;
    }

    /**
     * Positions the caret according to command type.
     * @param isFirstTime is used to differentiate commands that have different behaviors for different tabs.
     */
    private void autofillBehavior(boolean isFirstTime) {
        int nextCaretPosition;
        if (isFindNextField) {
            nextCaretPosition = findNextField();
            if (nextCaretPosition != -1) {
                commandTextField.positionCaret(nextCaretPosition);
            }
        }

        if (isMatchCommand) {
            selectIndexToEdit();
        }

        if (isFirstTime) {
            if (commandTextField.getText().length() >= MatchCommand.COMMAND_WORD.length()
                    && commandTextField.getText().substring(0, 5).equals(MatchCommand.COMMAND_WORD)) { // match command
                isMatchCommand = true;
            } else { // all other commands that has finding next field as its subsequent behavior
                isFindNextField = true;
            }
        }
    }

    /**
     * Deletes the previous prefix from current caret position and
     * if next field is present, caret is positioned to the next field.
     */
    private void deletePreviousPrefix() {
        String text = commandTextField.getText();
        int caretPosition = commandTextField.getCaretPosition();
        int deleteStart = text.lastIndexOf(" ", caretPosition - 1);

        if (deleteStart != -1) {
            commandTextField.deleteText(deleteStart, caretPosition);
            commandTextField.positionCaret(findNextField());
        }
    }

    /**
     * Finds the next input field from current caret position and
     * if next field is present, caret is positioned to the next field.
     */
    private int findNextField() {
        String text = commandTextField.getText();
        int caretPosition = commandTextField.getCaretPosition();
        int nextFieldPosition = text.indexOf("/", caretPosition);

        return nextFieldPosition + 1;
    }

    /**
     * Positions the caret to index position and selects the index to be edited.
     */
    private void selectIndexToEdit() {
        String text = commandTextField.getText();
        int caretPosition = commandTextField.getCaretPosition();
        int indexPosition = -1;

        for (int i = caretPosition; i < text.length(); i++) {
            Character character = text.charAt(i);
            if (Character.isDigit(character)) {
                indexPosition = i;
                break;
            }
        }

        commandTextField.positionCaret(indexPosition);
        if (indexPosition != -1) {
            commandTextField.selectForward();
        }
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    @Subscribe
    private void handleEditRemarkEvent(EditRemarkEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        replaceText(event.getPersonRemark());

        isEditRemarkCommand = true;
    }
```
###### \java\seedu\address\ui\DetailsPanel.java
``` java
public class DetailsPanel extends UiPart<Region> {

    private static final String FXML = "DetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private GridPane grid;
    @FXML
    private HBox ratingBox;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label role;
    @FXML
    private Label status;
    @FXML
    private Label subject;
    @FXML
    private Label level;
    @FXML
    private Label price;
    @FXML
    private Label remark;
    @FXML
    private Label rating;
    @FXML
    private Label rateCount;

    public DetailsPanel() {
        super(FXML);

        name.setText("");
        grid.setVisible(false);
        ratingBox.setVisible(false);

        registerAsAnEventHandler(this);
    }

    /**
     * Loads a {@code person}'s details into the details panel.
     */
    public void loadPersonDetails(Person person) {
        grid.setVisible(true);
        ratingBox.setVisible(true);

        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        status.setText(person.getStatus().value);
        subject.setText(person.getSubject().value);
        level.setText(person.getLevel().value);
        price.setText("$" + person.getPrice().value + " / hr");
        role.setText(person.getRole().value);
        remark.setText(person.getRemark().value);

        if (person.getRate().getCount() == 0) {
            rating.setText("-");
        } else {
            rating.setText(Double.toString(person.getRate().getDisplayedValue()));
        }
        rateCount.setText(Integer.toString(person.getRate().getCount()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
}
```
###### \resources\view\DarkTheme.css
``` css
.details_big_label {
     -fx-font-family: "Segoe UI";
     -fx-font-size: 40px;
     -fx-text-fill: #fffacd;
     -fx-text-weight: bold
 }

.details_label {
     -fx-font-family: "Segoe UI";
     -fx-font-size: 15px;
     -fx-text-fill: #ffffff;
     -fx-text-weight: bold
 }

.details_label_details {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 14px;
    -fx-text-fill: #ffffff;
}

.details_stackPane_odd {
    -fx-background-color: #333333
}

.details_stackPane_even {
    -fx-background-color: #4C4C4C
}
```
###### \resources\view\DetailsPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.text.Font?>
<StackPane styleClass="details_stackPane_odd" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <AnchorPane fx:id="detailsPane" maxHeight="-Infinity" maxWidth="900" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="606.0">
         <children>
            <Label fx:id="name" layoutX="14.0" layoutY="56.0" prefHeight="59.0" prefWidth="462.0" styleClass="details_big_label" text="Alex Yeoh" AnchorPane.leftAnchor="0.0" AnchorPane.topAnchor="40.0">
               <font>
                  <Font name="System Bold" size="32.0" />
               </font>
            </Label>
            <GridPane fx:id="grid" layoutX="-1.0" layoutY="160.0" maxHeight="270.0" prefHeight="267.0" prefWidth="614.0" AnchorPane.bottomAnchor="3.0" AnchorPane.leftAnchor="-1.0" AnchorPane.rightAnchor="-7.0" AnchorPane.topAnchor="130.0">
              <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="270.0" minWidth="10.0" prefWidth="171.0" />
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="640" minWidth="10.0" prefWidth="379.0" />
              </columnConstraints>
              <rowConstraints>
                <RowConstraints />
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                  <RowConstraints minHeight="60.0" prefHeight="30.0" vgrow="SOMETIMES" />
              </rowConstraints>
               <children>
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="details_stackPane_odd" GridPane.rowIndex="1" />
                  <StackPane prefHeight="52.0" prefWidth="373.0" styleClass="details_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="details_stackPane_even" GridPane.rowIndex="2" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="details_stackPane_even" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="details_stackPane_even" GridPane.columnIndex="1" GridPane.rowIndex="4" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="details_stackPane_even" GridPane.rowIndex="4" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="details_stackPane_even" GridPane.rowIndex="6" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="details_stackPane_even" GridPane.columnIndex="1" GridPane.rowIndex="6" />
                  <StackPane prefHeight="22.0" prefWidth="539.0" styleClass="details_stackPane_even" GridPane.rowIndex="8" />
                  <StackPane prefHeight="50.0" prefWidth="347.0" styleClass="details_stackPane_even" GridPane.columnIndex="1" GridPane.rowIndex="8" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="details_stackPane_odd" GridPane.rowIndex="3" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="details_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="3" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="details_stackPane_odd" GridPane.rowIndex="5" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="details_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="5" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="details_stackPane_odd" GridPane.rowIndex="7" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="details_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="7" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="details_stackPane_odd" GridPane.rowIndex="9" />
                  <StackPane prefHeight="21.0" prefWidth="172.0" styleClass="details_stackPane_odd" GridPane.columnIndex="1" GridPane.rowIndex="9" />
                  <Label fx:id="remark" prefHeight="60.0" prefWidth="400.0" styleClass="details_label_details" text="\$remark" wrapText="true" GridPane.columnIndex="1" GridPane.rowIndex="9" />
                  <Label styleClass="details_label" text="Phone:" GridPane.rowIndex="1">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label styleClass="details_label" text="Address:" GridPane.rowIndex="2">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="details_label" text="Email:" GridPane.rowIndex="3">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="details_label" text="Role:" GridPane.rowIndex="4">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="details_label" text="Status:" GridPane.rowIndex="5">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="details_label" text="Subject:" GridPane.rowIndex="6">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="details_label" text="Level:" GridPane.rowIndex="7">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label styleClass="details_label" text="Price:" GridPane.rowIndex="8">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
                  <Label fx:id="phone" styleClass="details_label_details" text="\$phone" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                  <Label fx:id="address" styleClass="details_label_details" text="\\$address" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                  <Label fx:id="email" styleClass="details_label_details" text="\\$email" GridPane.columnIndex="1" GridPane.rowIndex="3" />
                  <Label fx:id="role" styleClass="details_label_details" text="\$role" GridPane.columnIndex="1" GridPane.rowIndex="4" />
                  <Label fx:id="status" styleClass="details_label_details" text="\$status" GridPane.columnIndex="1" GridPane.rowIndex="5" />
                  <Label fx:id="subject" styleClass="details_label_details" text="\$subject" GridPane.columnIndex="1" GridPane.rowIndex="6" />
                  <Label fx:id="level" styleClass="details_label_details" text="\$level" GridPane.columnIndex="1" GridPane.rowIndex="7" />
                  <Label fx:id="price" styleClass="details_label_details" text="\\$price" GridPane.columnIndex="1" GridPane.rowIndex="8" />
                  <Label styleClass="details_label" text="Remark:" GridPane.rowIndex="9">
                     <font>
                        <Font name="System Bold" size="12.0" />
                     </font>
                     <padding>
                        <Insets left="10.0" />
                     </padding>
                  </Label>
               </children>
               <padding>
                  <Insets bottom="5.0" top="5.0" />
               </padding>
            </GridPane>
         </children>
         <padding>
            <Insets bottom="5.0" top="5.0" />
         </padding>
      <HBox fx:id="ratingBox" layoutX="297.0" layoutY="95.0" prefHeight="25.0" prefWidth="193.0">
         <children>
            <Label styleClass="details_label" text="Rating:">
               <HBox.margin>
                  <Insets />
               </HBox.margin>
            </Label>
            <Label fx:id="rating" styleClass="details_label_details" text="3.9">
               <padding>
                  <Insets left="15.0" />
               </padding>
               <HBox.margin>
                  <Insets top="1.0" />
               </HBox.margin>
            </Label>
            <Label styleClass="details_label_details" text="/ 5.0">
               <opaqueInsets>
                  <Insets />
               </opaqueInsets>
               <HBox.margin>
                  <Insets top="1.0" />
               </HBox.margin>
               <padding>
                  <Insets left="5.0" />
               </padding>
            </Label>
            <Label fx:id="rateCount" styleClass="details_label_details" text="3">
               <HBox.margin>
                  <Insets left="30.0" top="8.0" />
               </HBox.margin>
            </Label>
            <ImageView fitHeight="26.0" fitWidth="27.0" pickOnBounds="true" preserveRatio="true">
               <image>
                  <Image url="@../../../../docs/images/rateCount.png" />
               </image>
               <HBox.margin>
                  <Insets left="5.0" />
               </HBox.margin>
            </ImageView>
         </children>
      </HBox>
      </AnchorPane>
   </children>
</StackPane>
```

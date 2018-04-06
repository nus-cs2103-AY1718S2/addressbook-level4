# lohtianwei
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.NoPlayerException;

/**
 * Sorts all players in address book by field. Can be done in asc or desc order.
 */

public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "so";
    public static final String BY_ASCENDING = "asc";
    public static final String BY_DESCENDING = "desc";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts players in address book "
            + "by fields in either ascending or descending order.\n"
            + "Parameters: FIELD ORDER\n"
            + "Accepted fields for sort: name, email, address, rating, jersey, pos\n"
            + "Example: " + COMMAND_WORD
            + " name " + BY_ASCENDING;

    public static final String MESSAGE_PARAMETERS = "FIELD ORDER";

    public static final String MESSAGE_SUCCESS = "Players in address book have been sorted.";
    public static final String MESSAGE_EMPTY_BOOK = "No player(s) to sort.";

    private final String field;
    private final String order;

    public SortCommand(String field, String order) {
        requireNonNull(field);
        requireNonNull(order);

        this.field = field;
        this.order = order;
    }

    public String getField() {
        return this.field;
    }

    public String getOrder() {
        return this.order;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.sortPlayers(getField(), getOrder());
        } catch (NoPlayerException npe) {
            throw new CommandException(MESSAGE_EMPTY_BOOK);
        }
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
```
###### \java\seedu\address\logic\commands\TogglePrivacyCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.JerseyNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.TeamName;

/**
 * Toggles privacy of fields of player identified using it's last displayed index from the address book.
 */
public class TogglePrivacyCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "toggleprivacy";
    public static final String COMMAND_ALIAS = "tp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles the field privacy of the person"
            + " identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PHONE + "]"
            + "[" + PREFIX_EMAIL + "]"
            + "[" + PREFIX_REMARK + "]"
            + "[" + PREFIX_RATING + "]"
            + "[" + PREFIX_ADDRESS + "]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + " "
            + PREFIX_EMAIL + " "
            + PREFIX_ADDRESS;

    public static final String MESSAGE_SUCCESS = "Changed the Privacy of the Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonPrivacy epp;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit privacy
     */
    public TogglePrivacyCommand(Index index, EditPersonPrivacy epp) {
        requireNonNull(index);
        requireNonNull(epp);

        this.index = index;
        this.epp = epp;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        try {
            editedPerson = createEditedPrivacyPerson(personToEdit, epp);
        } catch (IllegalValueException e) {
            throw new AssertionError("Person must have all fields!");
        }

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonPrivacy}.
     * NOTE: Private fields will not be edited.
     */
    private static Person createEditedPrivacyPerson(Person personToEdit, EditPersonPrivacy epp)
            throws IllegalValueException {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = createPhonePrivacy(personToEdit, epp);
        Email updatedEmail = createEmailPrivacy(personToEdit, epp);
        Address updatedAddress = createAddressPrivacy(personToEdit, epp);
        Remark updatedRemark = createRemarkPrivacy(personToEdit, epp);
        TeamName updatedTeamName = personToEdit.getTeamName();
        Set<Tag> updatedTags = personToEdit.getTags();
        Rating updatedRating = createRatingPrivacy(personToEdit, epp);
        Position updatedPosition = personToEdit.getPosition();
        JerseyNumber updatedJerseyNumber = personToEdit.getJerseyNumber();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRemark,
                updatedTeamName, updatedTags, updatedRating, updatedPosition, updatedJerseyNumber);
    }

    /**
     * Creates a new (@code Phone) based on the input (@code Person) and (@code EditPersonPrivacy)
     * @return A (@code Phone) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code EditPersonPrivacy)
     */
    private static Phone createPhonePrivacy(Person person, EditPersonPrivacy epp) {
        Phone phone;
        try {
            if (person.getPhone().isPrivate()) {
                person.getPhone().togglePrivacy();
                phone = new Phone(person.getPhone().toString());
                person.getPhone().togglePrivacy();
            } else {
                phone = new Phone(person.getPhone().toString());
            }
        } catch (Exception e) {
            throw new AssertionError("Invalid Phone");
        }
        if (epp.getPrivatePhone() != null) {
            phone.setPrivate(person.getPhone().isPrivate());
            phone.togglePrivacy();
        } else {
            phone.setPrivate(person.getPhone().isPrivate());
        }

        return phone;
    }

    /**
     * Creates a new (@code Email) based on the input (@code Person) and (@code EditPersonPrivacy)
     * @return A (@code Email) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code EditPersonPrivacy)
     */
    private static Email createEmailPrivacy(Person person, EditPersonPrivacy epp) {
        Email email;
        try {
            if (person.getEmail().isPrivate()) {
                person.getEmail().togglePrivacy();
                email = new Email(person.getEmail().toString());
                person.getEmail().togglePrivacy();
            } else {
                email = new Email(person.getEmail().toString());
            }
        } catch (Exception e) {
            throw new AssertionError("Invalid Email");
        }
        if (epp.getPrivateEmail() != null) {
            email.setPrivate(person.getEmail().isPrivate());
            email.togglePrivacy();
        } else {
            email.setPrivate(person.getEmail().isPrivate());
        }
        return email;
    }

    /**
     * Creates a new (@code Address) based on the input (@code Person) and (@code EditPersonPrivacy)
     * @return A (@code Address) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code EditPersonPrivacy)
     */
    private static Address createAddressPrivacy(Person person, EditPersonPrivacy epp) {
        Address address;
        try {
            if (person.getAddress().isPrivate()) {
                person.getAddress().togglePrivacy();
                address = new Address(person.getAddress().toString());
                person.getAddress().togglePrivacy();
            } else {
                address = new Address(person.getAddress().toString());
            }
        } catch (Exception e) {
            throw new AssertionError("Invalid Address");
        }
        if (epp.getPrivateAddress() != null) {
            address.setPrivate(person.getAddress().isPrivate());
            address.togglePrivacy();
        } else {
            address.setPrivate(person.getAddress().isPrivate());
        }
        return address;
    }

    /**
     * Creates a new (@code Remark) based on the input (@code Person) and (@code EditPersonPrivacy)
     * @return A (@code Remark) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code EditPersonPrivacy)
     */
    private static Remark createRemarkPrivacy(Person person, EditPersonPrivacy epp) {
        Remark remark;
        try {
            if (person.getRemark().isPrivate()) {
                person.getRemark().togglePrivacy();
                remark = new Remark(person.getRemark().toString());
                person.getRemark().togglePrivacy();
            } else {
                remark = new Remark(person.getRemark().toString());
            }
        } catch (Exception e) {
            throw new AssertionError("Invalid Remark");
        }
        if (epp.getPrivateRemark() != null) {
            remark.setPrivate(person.getRemark().isPrivate());
            remark.togglePrivacy();
        } else {
            remark.setPrivate(person.getRemark().isPrivate());
        }
        return remark;
    }

    /**
     * Creates a new (@code Rating) based on the input (@code Person) and (@code EditPersonPrivacy)
     * @return A (@code Rating) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code EditPersonPrivacy)
     */
    private static Rating createRatingPrivacy(Person person, EditPersonPrivacy epp) {
        Rating rating;
        try {
            if (person.getRating().isPrivate()) {
                person.getRating().togglePrivacy();
                rating = new Rating(person.getRating().toString());
                person.getRating().togglePrivacy();
            } else {
                rating = new Rating(person.getRating().toString());
            }
        } catch (Exception e) {
            throw new AssertionError("Invalid Rating");
        }
        if (epp.getPrivateRating() != null) {
            rating.setPrivate(person.getRating().isPrivate());
            rating.togglePrivacy();
        } else {
            rating.setPrivate(person.getRating().isPrivate());
        }
        return rating;
    }

    public Index getIndex() {
        return index;
    }

    public EditPersonPrivacy getEpp() {
        return epp;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TogglePrivacyCommand)) {
            return false;
        }

        // state check
        TogglePrivacyCommand e = (TogglePrivacyCommand) other;
        return index.equals(e.index)
                && epp.equals(e.epp);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonPrivacy {
        private Boolean privatePhone;
        private Boolean privateEmail;
        private Boolean privateAddress;
        private Boolean privateRemark;
        private Boolean privateRating;

        public EditPersonPrivacy() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonPrivacy (EditPersonPrivacy toCopy) {
            this.privateAddress = toCopy.privateAddress;
            this.privateEmail = toCopy.privateEmail;
            this.privatePhone = toCopy.privatePhone;
            this.privateRemark = toCopy.privateRemark;
            this.privateRating = toCopy.privateRating;
        }

        public void setPrivatePhone(boolean privatePhone) {
            this.privatePhone = privatePhone;
        }

        public Boolean getPrivatePhone() {
            return privatePhone;
        }

        public void setPrivateEmail(boolean privateEmail) {
            this.privateEmail = privateEmail;
        }

        public Boolean getPrivateEmail() {
            return privateEmail;
        }

        public void setPrivateAddress(boolean privateAddress) {
            this.privateAddress = privateAddress;
        }

        public Boolean getPrivateAddress() {
            return privateAddress;
        }

        public void setPrivateRemark(boolean privateRemark) {
            this.privateRemark = privateRemark;
        }

        public Boolean getPrivateRemark() {
            return privateRemark;
        }

        public void setPrivateRating(boolean privateRating) {
            this.privateRating = privateRating;
        }

        public Boolean getPrivateRating() {
            return privateRating;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonPrivacy)) {
                return false;
            }

            // state check
            EditPersonPrivacy e = (EditPersonPrivacy) other;

            return getPrivatePhone().equals(e.getPrivatePhone())
                    && getPrivateAddress().equals(e.getPrivateAddress())
                    && getPrivateEmail().equals(e.getPrivateEmail())
                    && getPrivateRemark().equals(e.getPrivateRemark())
                    && getPrivateRating().equals(e.getPrivateRating());
        }
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object with field and order parameters provided
 */
public class SortCommandParser implements Parser<SortCommand> {
    public static final List<String> ACCEPTED_FIELDS = new ArrayList<>(Arrays.asList(
            "name", "email", "address", "rating", "jersey", "pos"));
    public static final List<String> ACCEPTED_ORDERS = new ArrayList<>(Arrays.asList(
            "asc", "desc"));

    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        //eliminates spaces
        String[] argKeywords = trimmedArgs.split("\\s+");

        //accounts for caps entries
        for (int i = 0; i < argKeywords.length; i++) {
            argKeywords[i] = argKeywords[i].toLowerCase();
        }

        if (argKeywords.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (!ACCEPTED_FIELDS.contains(argKeywords[0])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (!ACCEPTED_ORDERS.contains(argKeywords[1])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(argKeywords[0], argKeywords[1]);
    }
}
```
###### \java\seedu\address\logic\parser\TogglePrivacyCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TogglePrivacyCommand;
import seedu.address.logic.commands.TogglePrivacyCommand.EditPersonPrivacy;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TogglePrivacyCommand object
 */
public class TogglePrivacyCommandParser implements Parser<TogglePrivacyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TogglePrivacyCommand
     * and returns an TogglePrivacyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TogglePrivacyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_REMARK, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_RATING);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TogglePrivacyCommand.MESSAGE_USAGE));
        }

        EditPersonPrivacy epp = new EditPersonPrivacy();
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            epp.setPrivatePhone(false);
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            epp.setPrivateAddress(false);
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            epp.setPrivateEmail(false);
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            epp.setPrivateRemark(false);
        }

        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            epp.setPrivateRating(false);
        }
        return new TogglePrivacyCommand(index, epp);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void sortPlayersBy(String field, String order) throws NoPlayerException {
        persons.sortBy(field, order);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortPlayers(String field, String order) throws NoPlayerException {
        addressBook.sortPlayersBy(field, order);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts players by selected field in asc or desc order.
     * @return
     */
    public void sortBy(String field, String order) throws NoPlayerException {
        if (internalList.size() < 1) {
            throw new NoPlayerException();
        }

        Comparator<Person> comparator = null;

        Comparator<Person> nameComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getName().fullName.compareTo(p2.getName().fullName);
            }
        };

        Comparator<Person> jerseyComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getJerseyNumber().value.compareTo(p2.getJerseyNumber().value);
            }
        };

        Comparator<Person> ratingComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getRating().value.compareTo(p2.getRating().value);
            }
        };

        Comparator<Person> posComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getPosition().value.compareTo(p2.getPosition().value);
            }
        };

        Comparator<Person> emailComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getEmail().value.compareTo(p2.getEmail().value);
            }
        };

        Comparator<Person> addressComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getAddress().value.compareTo(p2.getAddress().value);
            }
        };

        switch (field) {
        case "name":
            comparator = nameComparator;
            break;

        case "jersey":
            comparator = jerseyComparator;
            break;

        case "pos":
            comparator = posComparator;
            break;

        case "rating":
            comparator = ratingComparator;
            break;

        case "email":
            comparator = emailComparator;
            break;

        case "address":
            comparator = addressComparator;
            break;

        default:
            throw new AssertionError("Invalid field parameter entered...\n");
        }

        switch (order) {
        case "asc":
            Collections.sort(internalList, comparator);
            break;

        case "desc":
            Collections.sort(internalList, Collections.reverseOrder(comparator));
            break;

        default:
            throw new AssertionError("Invalid field parameter entered...\n");
        }
    }
```
###### \java\seedu\address\ui\PlayerDetails.java
``` java
    public PlayerDetails(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        jerseyNumber.setText("Jersey Number: " + person.getJerseyNumber().value);

        if (person.getPhone().isPrivate()) {
            phone.setText(person.getPhone().toString());
        } else {
            phone.setText(person.getPhone().value);
        }

        if (person.getAddress().isPrivate()) {
            address.setText(person.getAddress().toString());
        } else {
            address.setText(person.getAddress().value);
        }

        if (person.getEmail().isPrivate()) {
            email.setText(person.getEmail().toString());
        } else {
            email.setText(person.getEmail().value);
        }

        if (person.getRemark().isPrivate()) {
            remark.setText("Remarks: " + person.getRemark().toString());
        } else {
            remark.setText("Remarks: " + person.getRemark().value);
        }
    }
}

```

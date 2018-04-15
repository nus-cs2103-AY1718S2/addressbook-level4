package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.AttributeTagSetter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.pair.PairHash;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Subject;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author sherlynng
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

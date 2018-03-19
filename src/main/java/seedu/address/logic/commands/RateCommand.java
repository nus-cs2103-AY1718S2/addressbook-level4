package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMUNICATION_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPERIENCE_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROBLEM_SOLVING_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICAL_SKILLS_SCORE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Rates a candidate after an interview.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " :Rates a candidate.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TECHNICAL_SKILLS_SCORE + "TECHNICAL SKILLS SCORE "
            + PREFIX_COMMUNICATION_SKILLS_SCORE + "COMMUNICATION SKILLS SCORE "
            + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE + "PROBLEM SOLVING SKILLS SCORE "
            + PREFIX_EXPERIENCE_SCORE + "EXPERIENCE SCORE \n"
            + "EXAMPLE: " + COMMAND_WORD + " 1 "
            + PREFIX_TECHNICAL_SKILLS_SCORE + "5 "
            + PREFIX_COMMUNICATION_SKILLS_SCORE + "4.5 "
            + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE + "4 "
            + PREFIX_EXPERIENCE_SCORE + "3.5";

    public static final String MESSAGE_RATE_PERSON_SUCCESS = "Rated %1$s: \n"
            + "Technical skills: %2$s, Communication skills: %3$s, "
            + "Problem solving skills: %4$s, Experience: %5$s \n"
            + "Overall: %6$s";

    private final Index index;
    private final Rating rating;

    private Person personToRate;
    private Person ratedPerson;

    /**
     * @param index of the person in the filtered person list to rate
     * @param rating given to the student based on technical skills, communication skills,
     *               problem solving skills and experience
     */
    public RateCommand(Index index, Rating rating) {
        requireNonNull(index);
        requireNonNull(rating);

        this.index = index;
        this.rating = rating;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToRate);
        requireNonNull(ratedPerson);

        try {
            model.updatePerson(personToRate, ratedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Changing target person's rating should not result in a duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_RATE_PERSON_SUCCESS, ratedPerson.getName(),
                ratedPerson.getRating().getTechnicalSkillsScore(),
                ratedPerson.getRating().getCommunicationSkillsScore(),
                ratedPerson.getRating().getProblemSolvingSkillsScore(),
                ratedPerson.getRating().getExperienceScore(),
                ratedPerson.getRating().getOverallScore()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToRate = lastShownList.get(index.getZeroBased());
        ratedPerson = createRatedPerson(personToRate, rating);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code rating}
     */
    private static Person createRatedPerson(Person personToRate, Rating rating) {
        assert personToRate != null;

        return new Person(personToRate.getName(), personToRate.getPhone(), personToRate.getEmail(),
                personToRate.getAddress(), personToRate.getExpectedGraduationYear(), personToRate.getMajor(), rating,
                personToRate.getResume(), personToRate.getInterviewDate(), personToRate.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RateCommand)) {
            return false;
        }

        // state check
        RateCommand e = (RateCommand) other;
        return index.equals(e.index)
                && rating.equals(e.rating);
    }

}

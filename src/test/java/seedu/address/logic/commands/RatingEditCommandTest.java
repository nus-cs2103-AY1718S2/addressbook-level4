package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.RatingEditCommand.EditRatingDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.testutil.EditRatingDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

//@@author kexiaowen
/**
 * Contains integration tests (interaction with the Model) and unit tests for RatingEditCommand.
 */
public class RatingEditCommandTest {
    public static final double TECHNICAL_SKILLS_SCORE = 4;
    public static final double COMMUNICATION_SKILLS_SCORE = 4.5;
    public static final double PROBLEM_SOLVING_SKILLS_SCORE = 3;
    public static final double EXPERIENCE_SCORE = 3.2;
    public static final String TECHNICAL_SKILLS_SCORE_STRING = "4";
    public static final String COMMUNICATION_SKILLS_SCORE_STRING = "4.5";
    public static final String PROBLEM_SOLVING_SKILLS_SCORE_STRING = "3";
    public static final String EXPERIENCE_SCORE_STRING = "3.2";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRating(TECHNICAL_SKILLS_SCORE_STRING,
                COMMUNICATION_SKILLS_SCORE_STRING, PROBLEM_SOLVING_SKILLS_SCORE_STRING,
                EXPERIENCE_SCORE_STRING).build();
        Rating rating = new Rating(TECHNICAL_SKILLS_SCORE, COMMUNICATION_SKILLS_SCORE,
                PROBLEM_SOLVING_SKILLS_SCORE, EXPERIENCE_SCORE);
        EditRatingDescriptor descriptor = new EditRatingDescriptorBuilder(rating).build();


        RatingEditCommand ratingEditCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(RatingEditCommand.MESSAGE_EDIT_RATING_SUCCESS,
                editedPerson.getName(), editedPerson.getRating().getTechnicalSkillsScore(),
                editedPerson.getRating().getCommunicationSkillsScore(),
                editedPerson.getRating().getProblemSolvingSkillsScore(),
                editedPerson.getRating().getExperienceScore(),
                editedPerson.getRating().getOverallScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(ratingEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRating(TECHNICAL_SKILLS_SCORE_STRING,
                COMMUNICATION_SKILLS_SCORE_STRING, PROBLEM_SOLVING_SKILLS_SCORE_STRING,
                Double.toString(firstPerson.getRating().experienceScore)).build();
        Rating rating = new Rating(TECHNICAL_SKILLS_SCORE, COMMUNICATION_SKILLS_SCORE,
                PROBLEM_SOLVING_SKILLS_SCORE, firstPerson.getRating().experienceScore);
        EditRatingDescriptor descriptor = new EditRatingDescriptorBuilder(rating).build();


        RatingEditCommand ratingEditCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(RatingEditCommand.MESSAGE_EDIT_RATING_SUCCESS,
                editedPerson.getName(), editedPerson.getRating().getTechnicalSkillsScore(),
                editedPerson.getRating().getCommunicationSkillsScore(),
                editedPerson.getRating().getProblemSolvingSkillsScore(),
                editedPerson.getRating().getExperienceScore(),
                editedPerson.getRating().getOverallScore());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(ratingEditCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code RateCommand}.
     */
    private RatingEditCommand prepareCommand(Index index, EditRatingDescriptor editRatingDescriptor) {
        RatingEditCommand ratingEditCommand = new RatingEditCommand(index, editRatingDescriptor);
        ratingEditCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return ratingEditCommand;
    }
}


//@@author Articho28

package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

public class SearchTagCommandTest {

    private Model model;
    private Model expectedModelSingleInput;
    private Model expectedModelMultipleInput;
    private SearchTagCommand searchTagCommandSingleTagInput;
    private SearchTagCommand searchTagCommandMultipleTagsInput;
    private Set<Tag> multipleTagsAsInput;
    private Set<Tag> singleTagAsInput;



    @Before
    public void setUp() {

        singleTagAsInput = new HashSet<>();
        singleTagAsInput.add(new Tag("friends"));

        multipleTagsAsInput = new HashSet<>();
        multipleTagsAsInput.add(new Tag("friends"));
        multipleTagsAsInput.add(new Tag("classmates"));
        multipleTagsAsInput.add(new Tag("colleagues"));

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModelSingleInput = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelSingleInput.updateFilteredPersonList(SearchTagCommand.personHasTags(singleTagAsInput));
        expectedModelMultipleInput = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelMultipleInput.updateFilteredPersonList(SearchTagCommand.personHasTags(multipleTagsAsInput));

        searchTagCommandSingleTagInput = new SearchTagCommand(singleTagAsInput);
        searchTagCommandMultipleTagsInput = new SearchTagCommand((multipleTagsAsInput));
        searchTagCommandSingleTagInput.setData(model, new CommandHistory(), new UndoRedoStack());
        searchTagCommandMultipleTagsInput.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void showsAllContactWithFriendsTag() {
        int result = expectedModelSingleInput.getFilteredPersonList().size();
        assertCommandSuccess(searchTagCommandSingleTagInput, model, SearchTagCommand.MESSAGE_SUCCESS
                + "\n"
                + SearchTagCommand.formatTagsFeedback(singleTagAsInput)
                + "\n"
                + Command.getMessageForPersonListShownSummary(result), expectedModelSingleInput);
    }

    @Test
    public void showsNoContactsWithMultipleTags() {
        int result = expectedModelMultipleInput.getFilteredPersonList().size();
        assertCommandSuccess(searchTagCommandMultipleTagsInput, model, SearchTagCommand.MESSAGE_FAILURE
                + "\n"
                + SearchTagCommand.formatTagsFeedback(multipleTagsAsInput), expectedModelMultipleInput);
    }

}

package seedu.organizer.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.EXAM;
import static seedu.organizer.testutil.TypicalTasks.GROCERY;
import static seedu.organizer.testutil.TypicalTasks.SPRINGCLEAN;
import static seedu.organizer.testutil.TypicalTasks.STUDY;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.predicates.NameContainsKeywordsPredicate;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;
import seedu.organizer.testutil.OrganizerBuilder;
import seedu.organizer.testutil.TaskBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredTaskList().remove(0);
    }

    @Test
    public void deleteTag_nonExistentTag_modelUnchanged() throws Exception {
        Organizer organizer = new OrganizerBuilder().withTask(EXAM).withTask(STUDY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(organizer, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_UNUSED));

        assertEquals(new ModelManager(organizer, userPrefs), modelManager);
    }

    @Test
    public void deleteTag_tagUsedByMultipleTasks_tagRemoved() throws Exception {
        Organizer organizer = new OrganizerBuilder().withTask(EXAM).withTask(STUDY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(organizer, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_FRIEND));

        Task examWithoutFriendTag = new TaskBuilder(EXAM).withTags().build();
        Task studyWithoutFriendTag = new TaskBuilder(STUDY).withTags(VALID_TAG_HUSBAND).build();
        Organizer expectedOrganizer = new OrganizerBuilder().withTask(examWithoutFriendTag)
                .withTask(studyWithoutFriendTag).build();

        assertEquals(new ModelManager(expectedOrganizer, userPrefs), modelManager);
    }

    @Test
    public void equals() {
        Organizer organizer = new OrganizerBuilder().withTask(GROCERY).withTask(SPRINGCLEAN).build();
        Organizer differentOrganizer = new Organizer();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(organizer, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(organizer, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different organizer -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentOrganizer, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = GROCERY.getName().fullName.split("\\s+");
        modelManager.updateFilteredTaskList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        ModelManager expectedModel = new ModelManager(organizer, userPrefs);
        try {
            expectedModel.addUser(ADMIN_USER);
            expectedModel.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        } catch (UserPasswordWrongException e) {
            e.printStackTrace();
        }
        assertFalse(modelManager.equals(expectedModel));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setOrganizerName("differentName");
        ModelManager differentModel = new ModelManager(organizer, differentUserPrefs);
        try {
            differentModel.addUser(ADMIN_USER);
            differentModel.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        } catch (UserPasswordWrongException e) {
            e.printStackTrace();
        }
        assertTrue(modelManager.equals(differentModel));
    }
}

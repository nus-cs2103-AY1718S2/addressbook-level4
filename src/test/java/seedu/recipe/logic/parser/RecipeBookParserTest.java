package seedu.recipe.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.logic.commands.ChangeThemeCommand;
import seedu.recipe.logic.commands.ClearCommand;
import seedu.recipe.logic.commands.DeleteCommand;
import seedu.recipe.logic.commands.EditCommand;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.logic.commands.ExitCommand;
import seedu.recipe.logic.commands.FindCommand;
import seedu.recipe.logic.commands.HelpCommand;
import seedu.recipe.logic.commands.HistoryCommand;
import seedu.recipe.logic.commands.ListCommand;
import seedu.recipe.logic.commands.RedoCommand;
import seedu.recipe.logic.commands.SearchCommand;
import seedu.recipe.logic.commands.SelectCommand;
import seedu.recipe.logic.commands.ShareCommand;
import seedu.recipe.logic.commands.TagCommand;
import seedu.recipe.logic.commands.UndoCommand;
import seedu.recipe.logic.commands.UploadCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.recipe.NameContainsKeywordsPredicate;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.tag.TagContainsKeywordsPredicate;
import seedu.recipe.testutil.EditRecipeDescriptorBuilder;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.testutil.RecipeUtil;

public class RecipeBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RecipeBookParser parser = new RecipeBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Recipe recipe = new RecipeBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(RecipeUtil.getAddCommand(recipe));
        assertEquals(new AddCommand(recipe), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RECIPE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Recipe recipe = new RecipeBuilder().build();
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(recipe).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RECIPE.getOneBased() + " " + RecipeUtil.getRecipeDetails(recipe));
        assertEquals(new EditCommand(INDEX_FIRST_RECIPE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_RECIPE), command);
    }

    //@@author RyanAngJY
    @Test
    public void parseCommand_share() throws Exception {
        ShareCommand command = (ShareCommand) parser.parseCommand(
                ShareCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased());
        assertEquals(new ShareCommand(INDEX_FIRST_RECIPE), command);
    }
    //@@author

    //@@author nicholasangcx
    @Test
    public void parseCommand_tag() throws Exception {
        List<String> keywords = Arrays.asList("friends", "family", "owesMoney");
        TagCommand command = (TagCommand) parser.parseCommand(
                TagCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new TagCommand(new TagContainsKeywordsPredicate(keywords),
                keywords.toArray(new String[0])), command);
    }

    @Test
    public void parseCommand_upload() throws Exception {
        String filename = "RecipeBook";
        UploadCommand command = (UploadCommand) parser.parseCommand(
                UploadCommand.COMMAND_WORD + " " + filename);
        assertEquals(new UploadCommand(filename + ".xml"), command);
    }
    //@@author

    //@@author kokonguyen191
    @Test
    public void parseCommand_changeTheme() throws Exception {
        assertTrue(parser.parseCommand(ChangeThemeCommand.COMMAND_WORD) instanceof ChangeThemeCommand);
        assertTrue(parser.parseCommand(ChangeThemeCommand.COMMAND_WORD + " 3") instanceof ChangeThemeCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        assertTrue(parser.parseCommand(SearchCommand.COMMAND_WORD + " chicken rice") instanceof SearchCommand);

        String keywords = "chicken rice";
        SearchCommand command = (SearchCommand) parser.parseCommand(SearchCommand.COMMAND_WORD + " " + keywords);
        assertEquals(new SearchCommand(keywords.replaceAll("\\s+", "+")), command);

        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        parser.parseCommand(SearchCommand.COMMAND_WORD);
    }
    //@@author

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}

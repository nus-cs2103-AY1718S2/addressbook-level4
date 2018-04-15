package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POPULATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_JOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteJobCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditJobCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindJobCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListJobsCommand;
import seedu.address.logic.commands.PostJobCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SaveReportCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobMatchesKeywordsPredicate;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailFilter;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditJobDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.JobBuilder;
import seedu.address.testutil.JobUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS + " "
                + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    //@@author ChengSashankh
    @Test
    public void parseCommand_postJob() throws Exception {
        Job job = new JobBuilder().build();
        PostJobCommand command = (PostJobCommand) parser.parseCommand(JobUtil.getPostJobCommand(job));
        assertEquals(new PostJobCommand(job), command);
    }
    //@@author

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author ChengSashankh
    @Test
    public void parseCommand_deleteJob() throws Exception {
        DeleteJobCommand command = (DeleteJobCommand) parser.parseCommand(
                DeleteJobCommand.COMMAND_WORD + " " + INDEX_FIRST_JOB.getOneBased());
        assertEquals(new DeleteJobCommand(INDEX_FIRST_JOB), command);
    }

    @Test
    public void parseCommand_deleteJobAlias() throws Exception {
        DeleteJobCommand command = (DeleteJobCommand) parser.parseCommand(
                DeleteJobCommand.COMMAND_ALIAS + " " + INDEX_FIRST_JOB.getOneBased());
        assertEquals(new DeleteJobCommand(INDEX_FIRST_JOB), command);
    }
    //@@author

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    //@@author ChengSashankh
    @Test
    public void parseCommand_editJob() throws Exception {
        Job job = new JobBuilder().build();
        EditJobCommand.EditJobDescriptor descriptor = new EditJobDescriptorBuilder(job).build();
        EditJobCommand command = (EditJobCommand) parser.parseCommand(EditJobCommand.COMMAND_WORD + " "
                + INDEX_FIRST_JOB.getOneBased() + " " + JobUtil.getJobDetails(job));
        assertEquals(new EditJobCommand(INDEX_FIRST_JOB, descriptor), command);
    }

    @Test
    public void parseCommand_editJobAlias() throws Exception {
        Job job = new JobBuilder().build();
        EditJobCommand.EditJobDescriptor descriptor = new EditJobDescriptorBuilder(job).build();
        EditJobCommand command = (EditJobCommand) parser.parseCommand(EditJobCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_JOB.getOneBased() + " " + JobUtil.getJobDetails(job));
        assertEquals(new EditJobCommand(INDEX_FIRST_JOB, descriptor), command);
    }
    //@@author

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
        assertEquals(new FindCommand(new PersonContainsKeywordsPredicate(keywords)), command);
    }

    //@@author ChengSashankh
    @Test
    public void parseCommand_findJob() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindJobCommand command = (FindJobCommand) parser.parseCommand(
                FindJobCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindJobCommand(new JobMatchesKeywordsPredicate(keywords)), command);
    }
    //@@author
    // @@author anh2111
    @Test
    public void parseCommand_viewreport() throws Exception {
        String populationTag = "SEIntern";
        ViewReportCommand command = (ViewReportCommand) parser.parseCommand(
                ViewReportCommand.COMMAND_WORD + " " + PREFIX_POPULATION + populationTag);
        assertEquals(new ViewReportCommand(new Tag(populationTag)), command);
    }

    @Test
    public void parseCommand_savereport() throws Exception {
        String populationTag = "SEIntern";
        SaveReportCommand command = (SaveReportCommand) parser.parseCommand(
                SaveReportCommand.COMMAND_WORD + " " + PREFIX_POPULATION + populationTag);
        assertEquals(new SaveReportCommand(new Tag(populationTag)), command);
    }
    // @@author
    @Test
    public void parseCommand_view() throws Exception {
        String example = "john@example.com";
        Email email = new Email(example);
        ViewCommand command = (ViewCommand) parser.parseCommand(ViewCommand.COMMAND_WORD + " " + email);
        assertEquals(new ViewCommand(new EmailFilter(email)), command);
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

    //@@author ChengSashankh
    @Test
    public void parseCommand_listJob() throws Exception {
        assertTrue(parser.parseCommand(ListJobsCommand.COMMAND_WORD) instanceof ListJobsCommand);
        assertTrue(parser.parseCommand(ListJobsCommand.COMMAND_WORD + " 3") instanceof ListJobsCommand);
    }
    //@@author

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Like cooking");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD
            + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + remark.value);

        assertEquals(command, new RemarkCommand(INDEX_FIRST_PERSON, remark));

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

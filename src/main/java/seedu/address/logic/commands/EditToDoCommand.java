package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;


/**
 * Edit the content of an existing ToDo in the address book.
 */
public class EditToDoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editToDo";
    public static final String COMMAND_ALIAS = "eTD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the content of the ToDo identified "
            + "by the index number used in the last ToDo listing. "
            + "Content of the ToDo will be overwritten by the input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CONTENT + "CONTENT] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CONTENT + "Submit presentation scripts";

    public static final String MESSAGE_EDIT_TODO_SUCCESS = "Edited ToDo: %1$s";
    public static final String MESSAGE_NOT_EDITED_TODO = "The new to-do content to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TODO = "This to-do already exists in the address book.";

    private final Index index;
    private final EditToDoDescriptor editToDoDescriptor;

    private ToDo toDoToEdit;
    private ToDo editedToDo;

    /**
     * @param index of the to-do in the filtered to-do list to edit
     * @param editToDoDescriptor details to edit the to-do with
     */
    public EditToDoCommand(Index index, EditToDoDescriptor editToDoDescriptor) {
        requireNonNull(index);
        requireNonNull(editToDoDescriptor);

        this.index = index;
        this.editToDoDescriptor = new EditToDoDescriptor(editToDoDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateToDo(toDoToEdit, editedToDo);
        } catch (DuplicateToDoException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        } catch (ToDoNotFoundException pnfe) {
            throw new AssertionError("The target to-do cannot be missing");
        }
        model.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        return new CommandResult(String.format(MESSAGE_EDIT_TODO_SUCCESS, editedToDo));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<ToDo> lastShownList = model.getFilteredToDoList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        toDoToEdit = lastShownList.get(index.getZeroBased());
        editedToDo = createEditedToDo(toDoToEdit, editToDoDescriptor);
    }

    /**
     * Creates and returns a {@code ToDo} with the details of {@code toDoToEdit}
     * edited with {@code editToDoDescriptor}
     * Status of toDoToEdit is unchanged.
     */
    private static ToDo createEditedToDo(ToDo toDoToEdit, EditToDoDescriptor editToDoDescriptor) {
        assert toDoToEdit != null;

        Content updatedContent = editToDoDescriptor.getContent().orElse(toDoToEdit.getContent());
        Status updatedStatus = toDoToEdit.getStatus();

        return new ToDo(updatedContent, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditToDoCommand)) {
            return false;
        }

        // state check
        EditToDoCommand e = (EditToDoCommand) other;
        return index.equals(e.index)
                && editToDoDescriptor.equals(e.editToDoDescriptor)
                && Objects.equals(toDoToEdit, e.toDoToEdit);
    }

    /**
     * Stores the content to edit the to-do with.
     * The content should not be empty.
     */
    public static class EditToDoDescriptor {
        private Content content;

        public EditToDoDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditToDoDescriptor(EditToDoDescriptor toCopy) {
            setContent(toCopy.content);
        }

        /**
         * Returns true if the content field is edited.
         */
        public boolean isContentFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.content);
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public Optional<Content> getContent() {
            return Optional.ofNullable(content);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditToDoDescriptor)) {
                return false;
            }

            // state check
            EditToDoDescriptor e = (EditToDoDescriptor) other;

            return getContent().equals(e.getContent());
        }
    }
}

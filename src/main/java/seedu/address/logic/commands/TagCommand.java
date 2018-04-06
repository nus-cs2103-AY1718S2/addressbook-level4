package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.TokenType.PREFIXTAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing coin in the address book.
 */
public class TagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add the specified tags to the coin identified "
            + "by the index number used in the last coin listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIXTAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIXTAG + "fav";

    public static final String MESSAGE_EDIT_COIN_SUCCESS = "Tagged Coin: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one tag must be provided.";
    public static final String MESSAGE_DUPLICATE_COIN = "This coin already exists in the coin book.";

    private final Index index;
    private final EditCoinDescriptor editCoinDescriptor;

    private Coin coinToEdit;
    private Coin editedCoin;

    /**
     * @param index of the coin in the filtered coin list to edit
     * @param editCoinDescriptor details to edit the coin with
     */
    public TagCommand(Index index, EditCoinDescriptor editCoinDescriptor) {
        requireNonNull(index);
        requireNonNull(editCoinDescriptor);

        this.index = index;
        this.editCoinDescriptor = new EditCoinDescriptor(editCoinDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateCoin(coinToEdit, editedCoin);
        } catch (DuplicateCoinException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_COIN);
        } catch (CoinNotFoundException pnfe) {
            throw new AssertionError("The target coin cannot be missing");
        }
        model.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
        return new CommandResult(String.format(MESSAGE_EDIT_COIN_SUCCESS, editedCoin));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Coin> lastShownList = model.getFilteredCoinList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_TARGET);
        }

        coinToEdit = lastShownList.get(index.getZeroBased());
        editedCoin = createEditedCoin(coinToEdit, editCoinDescriptor);
    }

    /**
     * Creates and returns a {@code Coin} with the details of {@code coinToEdit}
     * edited with {@code editCoinDescriptor}.
     */
    private static Coin createEditedCoin(Coin coinToEdit, EditCoinDescriptor editCoinDescriptor) {
        assert coinToEdit != null;

        Code updatedCode = coinToEdit.getCode();
        Set<Tag> updatedTags = editCoinDescriptor.getTags().orElse(coinToEdit.getTags());

        return new Coin(coinToEdit, updatedCode, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        // state check
        TagCommand e = (TagCommand) other;
        return index.equals(e.index)
                && editCoinDescriptor.equals(e.editCoinDescriptor)
                && Objects.equals(coinToEdit, e.coinToEdit);
    }

    /**
     * Stores the details to edit the coin with. Each non-empty field value will replace the
     * corresponding field value of the coin.
     */
    public static class EditCoinDescriptor {
        private Code code;
        private Set<Tag> tags;

        public EditCoinDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCoinDescriptor(EditCoinDescriptor toCopy) {
            setCode(toCopy.code);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.code, this.tags);
        }

        public void setCode(Code code) {
            this.code = null;
        }

        public Optional<Code> getCode() {
            return Optional.ofNullable(code);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCoinDescriptor)) {
                return false;
            }

            // state check
            EditCoinDescriptor e = (EditCoinDescriptor) other;

            return getCode().equals(e.getCode())
                    && getTags().equals(e.getTags());
        }
    }
}

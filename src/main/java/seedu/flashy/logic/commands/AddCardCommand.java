package seedu.flashy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_FRONT;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.flashy.commons.core.LogsCenter;
import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.exceptions.DuplicateCardException;
import seedu.flashy.model.cardtag.DuplicateEdgeException;
import seedu.flashy.model.tag.Tag;

/**
 * Adds a card to the card bank.
 */
public class AddCardCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addc";

    public static final String PARAMS = PREFIX_FRONT + "FRONT "
            + "[" + PREFIX_OPTION + "OPTION ] ... "
            + PREFIX_BACK + "BACK "
            + "[" + PREFIX_TAG + "TAG]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a card to the card bank. "
            + "Parameters: "
            + PARAMS;

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PARAMS;

    public static final String MESSAGE_SUCCESS = "New %1$s card added: %2$s";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the card bank";

    private static final Logger logger = LogsCenter.getLogger(AddCardCommand.class);
    private final Card cardToAdd;
    private final Optional<Set<Tag>> tagsToAdd;

    /**
     * Creates an AddCardCommand to add the specified {@code Card}
     */
    public AddCardCommand(Card card) {
        this(card, Optional.empty());
    }

    /**
     * Creates an AddCardCommand to add the specified {@code Card}
     */
    public AddCardCommand(Card card, Optional<Set<Tag>> tags) {
        requireNonNull(card);
        cardToAdd = card;
        tagsToAdd = tags;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        try {
            model.addCard(cardToAdd);
        } catch (DuplicateCardException e) {
            logger.warning(MESSAGE_DUPLICATE_CARD);
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        if (tagsToAdd.isPresent()) {
            Set<Tag> tags = tagsToAdd.get();
            try {
                model.addTags(cardToAdd, tags);
            } catch (DuplicateEdgeException e) {
                throw new IllegalStateException("Should not be able to reach here.");
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, cardToAdd.getType(), cardToAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCardCommand // instanceof handles nulls
                && cardToAdd.equals(((AddCardCommand) other).cardToAdd));
    }
}

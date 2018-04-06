//@@author ewaldhew
package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;

/**
 * Represents a command target specified in one of the available modes (union type).
 */
public class CommandTarget {

    /**
     * All possible target representation modes
     */
    private enum Mode {
        INDEX,
        CODE
    }

    private final Mode mode;

    private Index index;
    private Code code;

    public CommandTarget(Index index) {
        mode = Mode.INDEX;
        this.index = index;
    }

    public CommandTarget(Code code) {
        mode = Mode.CODE;
        this.code = code;
    }

    /**
     * @param coinList to obtain index information from
     * @return
     */
    public Index toIndex(ObservableList<Coin> coinList) throws IndexOutOfBoundsException {
        switch (mode) {

        case CODE:
            // Also throws IndexOutOfBoundsException if code isn't found.
            return Index.fromOneBased(coinList.filtered(coin -> coin.getCode().equals(code)).size());

        case INDEX:
            if (index.getZeroBased() >= coinList.size()) {
                throw new IndexOutOfBoundsException();
            }
            return index;

        default:
            throw new RuntimeException("Unexpected code path!");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandTarget)) {
            return false;
        }

        // state check
        CommandTarget e = (CommandTarget) other;
        return mode.equals(e.mode)
                && ((mode == Mode.INDEX && index.equals(e.index))
                || (mode == Mode.CODE && code.equals(e.code)));
    }
}

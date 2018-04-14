//@@author ewaldhew
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.model.CoinChangedEvent;
import seedu.address.commons.events.ui.ShowNotificationRequestEvent;
import seedu.address.model.coin.Coin;

/**
 * Spawns a pop-up notification in the corner of the screen.
 */
public class SpawnNotificationCommand extends ActionCommand<Coin> {

    private final String message;
    private Coin jumpTo;
    private Index index;

    public SpawnNotificationCommand(String message) {
        this.message = message;
    }

    @Override
    public void setExtraData(Coin data, BaseEvent event) {
        assert(event instanceof CoinChangedEvent);

        jumpTo = data;
        index = ((CoinChangedEvent) event).index;
    }

    @Override
    public CommandResult execute() {
        try {
            EventsCenter.getInstance()
                    .post(new ShowNotificationRequestEvent(message, index, jumpTo.getCode().toString()));
        } catch (IndexOutOfBoundsException e) {
            // Should not throw here, but do not crash anyway
            LogsCenter.getLogger(this.getClass()).severe("Encountered invalid index in rule execute.");
        }
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SpawnNotificationCommand // instanceof handles nulls
                && this.message.equals(((SpawnNotificationCommand) other).message)); // state check
    }
}

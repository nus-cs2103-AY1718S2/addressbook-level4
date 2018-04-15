//@@author wyinkok
package guitests.guihandles;

import java.util.List;

import javafx.scene.control.ListView;
import seedu.address.ui.ChatBotCard;

/**
 * Provides a handle for {@code ChatBotListPanel} containing the list of {@code ChatBotCard}.
 */
public class ChatBotListPanelHandle extends NodeHandle<ListView<ChatBotCard>> {
    public static final String CHAT_BOT_LIST_VIEW_ID = "#chatBotListView";

    public ChatBotListPanelHandle(ListView<ChatBotCard> chatBotListPanelNode) {
        super(chatBotListPanelNode);
    }

    /**
     * Returns a handle of the welcome message for {@code ChatBotCardHandle}.
     * There is only 1 welcome message.
     *
     * @throws AssertionError if no welcome message is present or more than 1 welcome message is present.
     */
    public ChatBotCardHandle getHandleToWelcomeMessage() {
        List<ChatBotCard> chatBotCardList = getRootNode().getItems();

        if (chatBotCardList.size() != 1) {
            throw new AssertionError("Chatbot message thread with only the welcome message "
                    + "has an expected size of 1");
        }
        return new ChatBotCardHandle(chatBotCardList.get(0).getRoot());
    }
}


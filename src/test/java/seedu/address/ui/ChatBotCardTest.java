//@@author wyinkok
package seedu.address.ui;

import org.junit.Test;

import guitests.guihandles.ChatBotCardHandle;
import seedu.address.ui.testutil.GuiTestAssert;
public class ChatBotCardTest extends GuiUnitTest {

    private static final String JOBBI_WELCOME_MESSAGE = "Hello there, I am Jobbi! "
            + "I am here to help you find your ideal internship today. Type 'start' to begin your search.";

    @Test
    public void display() {
        // non undoable command (user input)
        String listCommand = "list";
        ChatBotCard chatBotCard = new ChatBotCard("list");
        uiPartRule.setUiPart(chatBotCard);
        assertCardDisplay(chatBotCard, listCommand);

        // undoable command (user input)
        String saveCommand = "save 1";
        ChatBotCard chatBotCardForUndoableCommand = new ChatBotCard("save 1");
        uiPartRule.setUiPart(chatBotCardForUndoableCommand);
        assertCardDisplay(chatBotCardForUndoableCommand, saveCommand);

        // select command (user input)
        String selectCommand = "select 1";
        ChatBotCard chatBotCardForSelectCommand = new ChatBotCard("select 1");
        uiPartRule.setUiPart(chatBotCardForSelectCommand);
        assertCardDisplay(chatBotCardForSelectCommand, selectCommand);

        // command with typo error (user input)
        String errorCommand = "sdekhgfajf 1";
        ChatBotCard chatBotCardForErrorCommand = new ChatBotCard("sdekhgfajf 1");
        uiPartRule.setUiPart(chatBotCardForErrorCommand);
        assertCardDisplay(chatBotCardForErrorCommand, errorCommand);

        // checks if Jobbi's Welcome Message is displayed on the ChatBotCard
        ChatBotCard chatBotCardForWelcomeMessage = new ChatBotCard(JOBBI_WELCOME_MESSAGE);
        uiPartRule.setUiPart(chatBotCardForWelcomeMessage);
        assertCardDisplay(chatBotCardForWelcomeMessage, JOBBI_WELCOME_MESSAGE);
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedInternship} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ChatBotCard chatBotCard, String expectedUserInput) {
        guiRobot.pauseForHuman();

        ChatBotCardHandle chatBotCardHandle = new ChatBotCardHandle(chatBotCard.getRoot());

        // verify internship details are displayed correctly
        GuiTestAssert.assertCardDisplaysMessage(expectedUserInput, chatBotCardHandle);
    }
}


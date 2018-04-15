package guitests.guihandles;

import javafx.scene.control.TextArea;

//@@author AzuraAiR
/**
 * A handler for the {@code BirthdayList} of the UI
 */
public class BirthdayListHandle extends NodeHandle<TextArea> {

    public static final String BIRTHDAYS_LIST_ID = "#birthdayList";

    public BirthdayListHandle(TextArea birthdayListDisplayNode) {
        super(birthdayListDisplayNode);
    }

    /**
     * Returns the text in the birthday list
     */
    public String getText() {
        return getRootNode().getText();
    }

    public boolean getFront() {
        return getRootNode().getChildrenUnmodifiable().get(0).equals(this);
    }

}

//@@author nicholasangcx
package seedu.recipe.commons.events.ui;

import seedu.recipe.commons.events.BaseEvent;

/**
 * Indicates a request to upload recipes to Dropbox
 */
public class UploadRecipesEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author

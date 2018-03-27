//@@author ifalluphill

package seedu.address.logic.commands;

import com.google.api.services.calendar.model.Event;
import seedu.address.logic.OAuthManager;

import java.io.IOException;
import java.util.List;

/**
* Adds an event to the user's Google Calendar
*/
public class CalendarAddCommand extends Command {

    public static final String COMMAND_WORD = "calendar-add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an event to Google Calendar. ";


    public static final String MESSAGE_SUCCESS = "New event added: %s";
    public static final String MESSAGE_ERROR = "Unable to add new event. Please try again later.";


  @Override
  public CommandResult execute() {

      try {
          String eventUrl = OAuthManager.addEvent();
          return new CommandResult(String.format(MESSAGE_SUCCESS, eventUrl));

      } catch (IOException e) {
          return new CommandResult(MESSAGE_ERROR);
      }
  }

}

//@@author

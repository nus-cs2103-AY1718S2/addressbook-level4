package seedu.address.logic.commands;

//@@author HEARTOFAL1ON
/**
 * Sends a motivational picture via the Browser Panel.
 */
public class MotivateCommand {

    public static final String COMMAND_WORD = "motivate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sends a motivational picture via Browser "
            + "Panel. ";
    public static final String MESSAGE_MOTIVATE_ACKNOWLEDGEMENT = "Picture sent!";

    /*
    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_MOTIVATE_ACKNOWLEDGEMENT);
    }
    */
    public String execute() {
        return MESSAGE_MOTIVATE_ACKNOWLEDGEMENT;
    }

}

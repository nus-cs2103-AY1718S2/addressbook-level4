package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.GradientDescent;

/**
 * predicts the amount of money a customer would spend on insurance
 */
public class PredictCommand extends Command {

    public static final String COMMAND_WORD = "predict";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": look through existing client data "
            + "and predict potential client's spending\n"
            + "Example: " + COMMAND_WORD;


    public static final String SUCCESS_MESSAGE = "Predict command finished";

    @Override
    public CommandResult execute() throws CommandException {

        GradientDescent gd = new GradientDescent(model);
        return gd.solve();
    }
}

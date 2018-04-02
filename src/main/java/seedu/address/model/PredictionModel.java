package seedu.address.model;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;


/**
 * A model that provides methods that are used specifically for prediction command
 */
//@@author SoilChang
public interface PredictionModel extends Model {
    void preparePredictionData(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> targets,
                               ArrayList<Double> normalizationConstant);

    void updatePredictionResult(ArrayList<Double> weights, ArrayList<Double> normalizationConstant)
            throws CommandException;
}

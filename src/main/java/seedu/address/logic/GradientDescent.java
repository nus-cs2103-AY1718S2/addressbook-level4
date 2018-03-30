package seedu.address.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.PredictionModel;


//@@author SoilChang

/**
 * Gradient Descent solver on Person models in prediction of purchasing power
 */
public class GradientDescent {
    public static final String MESSAGE_PREDICTION_SUCCESS = "Prediction success";
    public static final String MESSAGE_PREDICTION_FAIL = "Prediction failed";
    private static GradientDescent instance;

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private PredictionModel model;

    /**
     * The weights we are calculating
     * For more fields, please add in the default value below
     * [ x1= income, x2 = age ]
     */
    private ArrayList<Double> weights = new ArrayList<>(Arrays.asList(0.0, 0.0));

    /**
     * The learning rate
     */
    private final Double learningRate = 0.0001;

    /**
     * The amount of epoch of looping through training data
     */
    private final Integer epoch = 10000;


    /**
     * Singleton
     *
     * @param model
     */
    private GradientDescent(PredictionModel model) {
        this.model = model;
    }

    //@@author SoilChang
    public static GradientDescent getInstance(PredictionModel model) {
        if (instance == null) {
            instance = new GradientDescent(model);
        }
        return instance;
    }


    /**
     * Drives the whole algorithm to solve the problem
     */
    public CommandResult solve() throws CommandException {
        /**
         * row -> each entry of data
         * col -> [ income ]
         */
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>(new ArrayList<>());

        /**
         * The actual outcome, this "ActualSpending"
         */
        ArrayList<Double> targets = new ArrayList<>();


        //extract values
        this.model.preparePredictionData(matrix, targets);


        //solve
        descent(matrix, targets);

        //update results
        try {
            this.model.updatePredictionResult(this.weights);
            return new CommandResult(String.format(MESSAGE_PREDICTION_SUCCESS));
        } catch (CommandException e) {
            return new CommandResult(String.format(MESSAGE_PREDICTION_FAIL));
        }
    }
    //@@author SoilChang

    /**
     * Perform stochastic gradient descent on the input data
     */
    private void descent(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> targets) {
        for (int itt = 0; itt < epoch; itt++) { // fixed amount of training epoch
            for (int r = 0; r < matrix.size(); r++) { //going through each training data
                ArrayList<Double> row = matrix.get(r);
                Double outcome = predict(row);
                Double error = targets.get(r) - outcome;
                for (int i = 0; i < row.size(); i++) {
                    Double deltaW = this.learningRate * error * row.get(i);
                    this.weights.set(i, this.weights.get(i) + deltaW);
                }
            }
        }
    }


    //@@author SoilChang

    /**
     * calculate values based on the current weights
     */
    private Double predict(ArrayList<Double> row) {
        Double sum = 0.0;
        //sum income
        for (int i = 0; i < row.size(); i++) {
            sum += this.weights.get(i) * row.get(i);
        }

        return sum;
    }
}

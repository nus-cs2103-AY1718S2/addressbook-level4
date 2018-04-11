package seedu.address.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.PredictionModel;




/**
 * Gradient Descent solver on Person models in prediction of purchasing power
 */
//@@author SoilChang
public class GradientDescent {
    public static final String MESSAGE_PREDICTION_SUCCESS = "Prediction success";
    public static final String MESSAGE_PREDICTION_FAIL = "Prediction failed";
    public static final String MESSAGE_PREDICTION_DIVERGENT = "Prediction Solution is not convergent";
    private static GradientDescent instance;
    /**
     * The weights we are calculating
     * For more fields, please add in the default value below
     * [ x1= income, x2 = age ]
     */
    private static ArrayList<Double> weights = new ArrayList<>();

    /**
     * Some constants to normalize income and age so that the value don't differ so much
     */
    private static ArrayList<Double> normalizationConstant =
            new ArrayList<>(Arrays.asList(1000.0));


    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private PredictionModel model;
    /**
     * The learning rate
     */
    private final Double learningRate = 0.000001;

    /**
     * The amount of epoch of looping through training data
     */
    private final Integer epoch = 300000;


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
            instance.resetWeights();
        }
        return instance;
    }


    //@@author SoilChang

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
        this.model.preparePredictionData(matrix, targets, normalizationConstant);


        if (this.hasNaN(this.weights)) {
            resetWeights();
        }

        //solve
        descent(matrix, targets);

        if (this.hasNaN(this.weights)) {
            return new CommandResult(String.format(MESSAGE_PREDICTION_DIVERGENT));
        }

        Double confidence = this.validate(matrix, targets);
        this.logger.info("Confidence Level: " + confidence);


        //update results
        try {
            this.model.updatePredictionResult(this.getWeights());
            return new CommandResult(String.format(MESSAGE_PREDICTION_SUCCESS));
        } catch (CommandException e) {
            return new CommandResult(String.format(MESSAGE_PREDICTION_FAIL));
        }
    }

    //@@author SoilChang

    /**
     * Validate the training results
     */
    private Double validate(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> targets) {
        Double average = 0.0;
        for (int i = 0; i < matrix.size(); i++) {
            //loop thorugh each row

            Double outcome = this.predict(matrix.get(i));
            Double percentError = Math.abs((outcome - targets.get(i)) / targets.get(i));
            average += percentError;
        }

        return average / targets.size();
    }

    //@@author SoilChang

    /**
     * Perform stochastic gradient descent on the input data
     */
    private void descent(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> targets) {
        for (int itt = 0; itt < epoch; itt++) {

            //check data validity
            if (this.hasNaN(this.weights)) {
                this.logger.warning("The solution is not convergent");
                break;
            }

            // fixed amount of training iteration
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

    /**
     * Check if the ArrayList contains NaN
     *
     * @param weights
     * @return
     */
    private boolean hasNaN(ArrayList<Double> weights) {
        for (Double i : weights) {
            if (i.isNaN()) {
                return true;
            }
        }

        return false;
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


    /**
     * Weight getter
     *
     * @return
     */
    public static ArrayList<Double> getWeights() {
        //restore the un-normalized value
        ArrayList<Double> trueWeights = new ArrayList<>();
        for (int j = 0; j < GradientDescent.weights.size(); j++) {
            trueWeights.add(GradientDescent.weights.get(j) / normalizationConstant.get(j));
        }
        return trueWeights;
    }

    /**
     * reset the weights to initial values of zeros
     */
    public static void resetWeights() {
        GradientDescent.weights = new ArrayList<>(Arrays.asList(0.0));
    }
}

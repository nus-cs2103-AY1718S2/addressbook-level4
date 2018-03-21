package seedu.address.model;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;




/**
 * Gradient Descent solver on Person models in prediction of purchasing power
 */
public class GradientDescent {
    public static final String MESSAGE_PREDICTION_SUCCESS = "Prediction success";
    public static final String MESSAGE_PREDICTION_FAIL = "Prediction failed";
    private final Model model;
    private final int fieldCount = 1;

    public GradientDescent(Model model) {
        this.model = model;
    }

    /**
     * Drives the whole algorithm to solve the problem
     */
    public CommandResult solve() throws CommandException {
        ObservableList<Person> personList = this.model.getAddressBook().getPersonList();
        int rowA = personList.size();
        int entryCount = 0;
        for (int i = 0; i < rowA; i++) {
            if (personList.get(i).getActualSpending().value != 0.0) {
                entryCount++;
            }
        }

        double[][] matrix = new double[entryCount][this.fieldCount];
        double[] targets = new double[entryCount];
        double[] weights = new double[this.fieldCount];

        //extract values
        extractValues(personList, matrix, targets);

        //solve
        weights[0] = 100.0;


        //update results
        try {
            updateResult(personList, weights);
            return new CommandResult(String.format(MESSAGE_PREDICTION_SUCCESS));
        } catch (CommandException e) {
            return new CommandResult(String.format(MESSAGE_PREDICTION_FAIL));
        }
    }

    /**
     * extract values
     *
     * @param personList
     * @param matrix
     * @param targets
     */
    private void extractValues(ObservableList<Person> personList, double[][] matrix, double[] targets) {
        for (int i = 0; i < personList.size(); i++) {
            double as = personList.get(i).getActualSpending().value;

            //the person has no actual spending recorded
            if (as == 0.0) {
                continue;
            }

            //record down the actual value
            targets[i] = as;
            matrix[i][0] = personList.get(i).getIncome().value;
        }
    }

    /**
     * Update the prediction results back to the model
     *
     * @param personList
     * @throws CommandException
     */
    private void updateResult(ObservableList<Person> personList, double[] weights) throws CommandException {
        //update person model
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getActualSpending().value != 0.0) {
                //the person already has known value of spending
                continue;
            }

            //else update the person with expected spending
            Person p = personList.get(i);
            Person updatedPerson = p.mlUpdatPerson(p.getIncome().value * weights[0]);
            //update the model here


            try {
                model.updatePerson(personList.get(i), updatedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }
}

package systemtests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.GradientDescent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.PredictionModel;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;




//@author SoilChang

public class PredictCommandSystemTest extends AddressBookSystemTest {

    private Person old1 = new PersonBuilder()
            .withName("old1")
            .withIncome(20000.0)
            .withActualSpending(1000.0)
            .withAge(20)
            .build();
    private Person old2 = new PersonBuilder()
            .withName("old2")
            .withIncome(30000.0)
            .withActualSpending(1500.0)
            .withAge(20)
            .build();
    private Person old3 = new PersonBuilder()
            .withName("old3")
            .withIncome(40000.0)
            .withActualSpending(2000.0)
            .withAge(20)
            .build();

    private Person extremeLow1 = new PersonBuilder()
            .withName("extremeLow1")
            .withIncome(40.0)
            .withActualSpending(2000.0)
            .withAge(20)
            .build();
    private Person extremehigh1 = new PersonBuilder()
            .withName("extremeHigh1")
            .withIncome(1234568989.0)
            .withActualSpending(10.0)
            .withAge(20)
            .build();

    private Person new1 = new PersonBuilder()
            .withName("new1")
            .withIncome(10000.0)
            .withActualSpending(0.0)
            .withAge(20)
            .build();
    private Person new2 = new PersonBuilder()
            .withName("new2")
            .withIncome(100.0)
            .withActualSpending(0.0)
            .withAge(20)
            .build();


    //@author SoilChang
    @Test
    public void predict() {

        //Case: 1: normal prediction
        Model model = getModel();
        model.resetData(new AddressBook());
        try {
            model.addPerson(old1);
            model.addPerson(old2);
            model.addPerson(old3);
            model.addPerson(new1);
        } catch (DuplicatePersonException dpe) {
            assert(dpe == null);
            System.out.println(dpe.getMessage());

            System.out.println("Data Preparation Failed");
            return;
        }

        GradientDescent gd = GradientDescent.getInstance((PredictionModel) model);
        try {
            gd.solve();
        } catch (CommandException ce) {
            assert(ce == null);
            System.out.println(ce.getMessage());
            return;
        }

        Double p4es = model.getFilteredPersonList().get(3).getExpectedSpending().value;

        //simple prediction
        assertEquals(0.05, GradientDescent.getWeights().get(0), 0.01);
        assertEquals(500.0, p4es, 5.0);


        //test 2: 2 users
        model.resetData(new AddressBook());
        GradientDescent.resetWeights();
        //prepare data
        try {
            model.addPerson(new2);
            model.addPerson(old1);
            model.addPerson(old2);
            model.addPerson(old3);
        } catch (DuplicatePersonException dpe) {
            assert( dpe == null);
            System.out.println(dpe.getMessage());
            System.out.println("Data Preparation Failed");
            return;
        }

        try {
            gd.solve();
        } catch (CommandException ce) {
            System.out.println(ce.getMessage());
            return;
        }
        p4es = model.getFilteredPersonList().get(0).getExpectedSpending().value;
        assertEquals(0.05, GradientDescent.getWeights().get(0), 0.01);
        assertEquals(5.0, p4es, 1.0);


        //test 3: extreme value, divergent solution
        model.resetData(new AddressBook());
        GradientDescent.resetWeights();
        //prepare data
        try {
            model.addPerson(new2);
            model.addPerson(extremehigh1);
            model.addPerson(old3);
        } catch (DuplicatePersonException dpe) {
            assert(dpe == null);
            System.out.println(dpe.getMessage());

            System.out.println("Data Preparation Failed");
            return;
        }

        try {
            CommandResult result = gd.solve();
            assertEquals(result.feedbackToUser, GradientDescent.MESSAGE_PREDICTION_DIVERGENT);
        } catch (CommandException ce) {
            assert(ce == null);
            System.out.println(ce.getMessage());
            return;
        }


        p4es = model.getFilteredPersonList().get(0).getExpectedSpending().value;
        assertEquals(0.0, p4es, 1.0);

    }


}

package seedu.address.model;

/**
 * Gradient Descent solver on Person models in prediction of purchasing power
 */
public class GradientDescent {

    private final Model model;

    public GradientDescent(Model model) {
        this.model = model;

    }

    /**
     * Drives the whole algorithm to solve the problem
     */
    public void solve() {
        //        ArrayList<ArrayList<Double>> matrix = this.model.getPersonAttrMatrix() ;
        System.out.println("solved");

    }
}

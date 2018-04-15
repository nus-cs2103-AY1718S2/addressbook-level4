package seedu.address.model.person;

//@@author wenhao53
/**
 * Represents a Person's Body Mass Index (BMI) (in kg/m^2) in the Personal Trainer Pro app.
 * Guarantees: mutable; is valid long as Height as declared in {@link #isValidHeight(String)} and Weight as
 * declared in {@Link #isValidWeight(String)} is valid.
 */
public class BodyMassIndex {

    public static final String ACCEPTABLE_CLASSIFICATION = "ACCEPTABLE";
    public static final String OBESE_CLASSIFICATION = "OBESE";
    public static final String OVERWEIGHT_CLASSIFICATION = "OVERWEIGHT";
    public static final String UNDERWEIGHT_CLASSIFICATION = "UNDERWEIGHT";

    public final String value;
    public final String classification;

    private double bodyMassIndexValue;
    private double heightValue;
    private double weightValue;

    /**
     * Constructs a {@code BodyMassIndex}.
     *
     * @param height A valid height (in cm).
     * @param weight A valid weight (in kg).
     */
    public BodyMassIndex(String height, String weight) {
        bodyMassIndexValue = getBodyMassIndexValue(height, weight);
        this.classification = getBodyMassIndexClassificationFromValue(bodyMassIndexValue);
        this.value = convertBodyMassIndexValueToStringForDisplay(bodyMassIndexValue);
    }

    /*
     * Formats the BMI value of a Person into a String, displayed to two decimal places.
     */
    private String convertBodyMassIndexValueToStringForDisplay(Double value) {
        return String.format("%.2f", value);

    }

    /*
     * Returns the BMI Classification of a Person when given the BMI value.
     */
    private String getBodyMassIndexClassificationFromValue(Double bodyMassIndexValue) {
        if (bodyMassIndexValue < 18.5) {
            return UNDERWEIGHT_CLASSIFICATION;
        } else if (bodyMassIndexValue < 25) {
            return ACCEPTABLE_CLASSIFICATION;
        } else if (bodyMassIndexValue < 30) {
            return OVERWEIGHT_CLASSIFICATION;
        } else {
            return OBESE_CLASSIFICATION;
        }
    }

    /*
     * Returns the BMI value of a Person when given valid Height and Weight
     */
    private double getBodyMassIndexValue(String height, String weight) {
        heightValue = Double.parseDouble(height);
        weightValue = Double.parseDouble(weight);
        bodyMassIndexValue = calculateBodyMassIndexValueFromHeightAndWeight(heightValue, weightValue);
        return bodyMassIndexValue;

    }

    /*
     * Returns the BMI value calculated from heightValue and weightValue
     */
    private double calculateBodyMassIndexValueFromHeightAndWeight(double heightValue, double weightValue) {
        return weightValue / ((heightValue / 100) * (heightValue / 100));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

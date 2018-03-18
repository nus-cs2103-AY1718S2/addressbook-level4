package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.DoubleUtil;

/**
 * Represents a candidate's rating after an interview.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Rating {
    public static final String MESSAGE_RATING_CONSTRAINTS =
            "The scores should be numbers between 1 to 5.";
    public static final double DEFAULT_SCORE = -1;
    public static final double MAXIMUM_SCORE = 5;
    public static final double MINIMUM_SCORE = 1;
    private static final double NUMBER_OF_SCORES = 4.0;


    public final double technicalSkillsScore;
    public final double communicationSkillsScore;
    public final double problemSolvingSkillsScore;
    public final double experienceScore;
    public final double overallScore;

    /**
     * Constructs a {@code Rating}.
     * @param technicalSkillsScore A valid technical skills score.
     * @param communicationSkillsScore A valid communications skills score.
     * @param problemSolvingSkillsScore A valid problem solving skills score.
     * @param experienceScore A valid experience score.
     */
    public Rating(double technicalSkillsScore, double communicationSkillsScore, double problemSolvingSkillsScore,
                  double experienceScore) {
        requireAllNonNull(technicalSkillsScore, communicationSkillsScore, problemSolvingSkillsScore,
                experienceScore);
        checkArgument(isValidOrDefaultScore(technicalSkillsScore), MESSAGE_RATING_CONSTRAINTS);
        checkArgument(isValidOrDefaultScore(communicationSkillsScore), MESSAGE_RATING_CONSTRAINTS);
        checkArgument(isValidOrDefaultScore(problemSolvingSkillsScore), MESSAGE_RATING_CONSTRAINTS);
        checkArgument(isValidOrDefaultScore(experienceScore), MESSAGE_RATING_CONSTRAINTS);
        this.technicalSkillsScore = DoubleUtil.roundToTwoDecimalPlaces(technicalSkillsScore);
        this.communicationSkillsScore = DoubleUtil.roundToTwoDecimalPlaces(communicationSkillsScore);
        this.problemSolvingSkillsScore = DoubleUtil.roundToTwoDecimalPlaces(problemSolvingSkillsScore);
        this.experienceScore = DoubleUtil.roundToTwoDecimalPlaces(experienceScore);
        this.overallScore = calculateOverallScore(this.technicalSkillsScore,
                this.communicationSkillsScore, this.problemSolvingSkillsScore, this.experienceScore);
    }

    public double getTechnicalSkillsScore() {
        return technicalSkillsScore;
    }

    public double getCommunicationSkillsScore() {
        return communicationSkillsScore;
    }

    public double getProblemSolvingSkillsScore() {
        return problemSolvingSkillsScore;
    }

    public double getExperienceScore() {
        return experienceScore;
    }

    public double getOverallScore() {
        return overallScore;
    }

    /**
     * Returns the average of {@code technicalSkillsScore}, {@code communicationSkillsScore},
     * {@code problemSolvingSkillsScore} and {@code experienceScore}, rounded to two decimal places
     */
    public static double calculateOverallScore(double technicalSkillsScore, double communicationSkillsScore,
                                         double problemSolvingSkillsScore, double experienceScore) {
        return DoubleUtil.roundToTwoDecimalPlaces((technicalSkillsScore + communicationSkillsScore
                + problemSolvingSkillsScore + experienceScore) / NUMBER_OF_SCORES);
    }

    /**
     * Returns the average of {@code technicalSkillsScore}, {@code communicationSkillsScore},
     * {@code problemSolvingSkillsScore} and {@code experienceScore}, rounded to two decimal places as a String
     */
    public static String calculateOverallScore(String technicalSkillsScore, String communicationSkillsScore,
                                                String problemSolvingSkillsScore, String experienceScore) {
        double overallScore = DoubleUtil.roundToTwoDecimalPlaces((Double.parseDouble(technicalSkillsScore)
                + Double.parseDouble(communicationSkillsScore)
                + Double.parseDouble(problemSolvingSkillsScore)
                + Double.parseDouble(experienceScore)) / NUMBER_OF_SCORES);
        return Double.toString(overallScore);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Rating)) {
            return false;
        }

        Rating otherRating = (Rating) other;
        return otherRating.getTechnicalSkillsScore() == this.getTechnicalSkillsScore()
                && otherRating.getCommunicationSkillsScore() == this.getCommunicationSkillsScore()
                && otherRating.getProblemSolvingSkillsScore() == this.getProblemSolvingSkillsScore()
                && otherRating.getExperienceScore() == this.getExperienceScore();
    }

    /**
     * Returns true if a given double is a valid score.
     */
    public static boolean isValidScore(Double test) {
        return test >= MINIMUM_SCORE && test <= MAXIMUM_SCORE;
    }

    /**
     * Returns true if a given double is a default score.
     */
    public static boolean isDefaultScore(Double test) {
        return test == DEFAULT_SCORE;
    }

    public static boolean isValidOrDefaultScore(Double test) {
        return isValidScore(test) || isDefaultScore(test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(technicalSkillsScore, communicationSkillsScore, problemSolvingSkillsScore,
                experienceScore);
    }
}

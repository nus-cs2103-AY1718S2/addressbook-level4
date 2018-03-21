package seedu.address.model.lesson;

/**
 * Represents the Hour value in the Time.
 *
 */
public class Min implements Comparable<Min> {
    private int value;
    public Min(String min){
        this.value = Integer.parseInt(min);
    }

    /**
     * Get the integer value of the Hour string
     * @return value
     */
    public int getMin(){
        return this.value;
    }

    /**
     * Compares 2 Hour values and returns whether the comparison is larger than itself
     * @return the compareTo value
     */
    public int compareTo(Min other){
        return this.getMin() - other.getMin();
    }
}

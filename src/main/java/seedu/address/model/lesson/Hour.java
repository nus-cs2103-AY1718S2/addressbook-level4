package seedu.address.model.lesson;
/**
 * Represents the Hour value in the Time.
 *
 */
public class Hour implements Comparable<Hour> {
    private int value;
    public Hour(String hour){
        this.value = Integer.parseInt(hour);
    }

    /**
     * Get the integer value of the Hour string
     * @return value
     */
    public int getHour(){
        return this.value;
    }

    /**
     * Compares 2 Hour values and returns whether the comparison is larger than itself
     * @return the compareTo value
     */
    public int compareTo(Hour other){
        return this.getHour() - other.getHour();
    }
}

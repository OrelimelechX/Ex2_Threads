import java.util.ArrayList;
/**
 * Holds the input numbers, target sum, and shared search results.
 * Input numbers are stored in an {@code ArrayList<Integer>}.
 * The {@code boolean[]} solution array marks the selected elements.
 * Worker threads coordinate access using synchronized blocks.
 *
 * @author Or Elimelech
 */
public class SharedData {
    private ArrayList<Integer> array;
    private boolean[] winArray;
    private boolean flag;
    private final int b;

    /**
     * Constructs a new SharedData object with an ArrayList of integers and a constant integer.
     *
     * @param array the ArrayList of integers
     * @param b the constant integer
     */
    public SharedData(ArrayList<Integer> array, int b) {
        this.array = array;
        this.b = b;
        this.winArray = new boolean[array.size()];
    }

    /**
     * Returns the ArrayList of integers.
     *
     * @return the ArrayList of integers
     */
    public ArrayList<Integer> getArray() { return array; }

    /**
     * Sets the ArrayList of integers.
     *
     * @param array the ArrayList of integers to set
     */
    public void setArray(ArrayList<Integer> array) {
        this.array = array;
        this.winArray = new boolean[array.size()];
    }

    /**
     * Returns the constant integer.
     *
     * @return the constant integer
     */
    public int getB() { return b; }

    /**
     * Returns the flag indicating whether a certain condition has been met.
     *
     * @return the flag
     */
    public boolean getFlag() { return flag; }

    /**
     * Sets the flag indicating whether a certain condition has been met.
     *
     * @param flag the flag to set
     */
    public void setFlag(boolean flag) { this.flag = flag; }
    /**
     * Returns the boolean array marking the elements in the solution.
     *
     * @return the solution selection array
     */
    public boolean[] getWinArray() {
        return winArray;
    }

    /**
     * Stores the boolean array marking the elements in the solution.
     *
     * @param winArray the solution selection array
     */
    public void setWinArray(boolean[] winArray) {
        this.winArray = winArray;
    }
}

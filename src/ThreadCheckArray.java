import java.util.ArrayList;

/**
 * A {@link Runnable} task that recursively solves the Subset Sum problem:
 * it checks whether some subset of the numbers in an {@code ArrayList<Integer>}
 * adds up to the target value {@code b}.
 * <p>
 * Two threads share the work: the thread named {@code "thread1"} searches for
 * solutions that include the last number, and the other thread searches for
 * solutions without it. Access to the shared {@link SharedData} object is
 * synchronized, so once one thread finds a solution the other one stops.
 *
 * @author Eyal Hirsch
 */
public class ThreadCheckArray implements Runnable {

    /** Indicates whether this worker found a solution. */
    private boolean flag;

    /** Records which elements belong to this worker's solution. */
    private boolean[] winArray;

    /** Shared data used by the worker threads. */
    SharedData sd;

    /** Numbers available for the subset sum search. */
    ArrayList<Integer> array;

    /** Target sum. */
    int b;

    /**
     * Creates a worker using the supplied shared data.
     *
     * @param sd shared numbers, target, and search results
     */
    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;

        synchronized (sd) {
            array = new ArrayList<>(sd.getArray());
            b = sd.getB();
        }

        winArray = new boolean[array.size()];
    }

    /**
     * Recursively searches the first n elements for the remaining sum.
     * Explores including and excluding the last available element.
     *
     * @param n number of elements available in this recursive call
     * @param b remaining sum to find
     */
    void rec(int n, int b) {
        synchronized (sd) {
            if (sd.getFlag()) {
                return;
            }
        }

        if (n == 1) {
            if (b == 0 || b == array.get(n - 1)) {
                flag = true;

                synchronized (sd) {
                    sd.setFlag(true);
                }
            }

            if (b == array.get(n - 1)) {
                winArray[n - 1] = true;
            }

            return;
        }

        rec(n - 1, b - array.get(n - 1));

        if (flag) {
            winArray[n - 1] = true;
        }

        synchronized (sd) {
            if (sd.getFlag()) {
                return;
            }
        }

        rec(n - 1, b);
    }

    /**
     * Runs this worker's search and publishes a solution when found.
     * The worker named thread1 includes the final element; the other
     * worker searches without it.
     */
    @Override
    public void run() {
        if (array.size() != 1) {
            if (Thread.currentThread().getName().equals("thread1")) {
                rec(array.size() - 1, b - array.get(array.size() - 1));
            } else {
                rec(array.size() - 1, b);
            }
        }

        if (array.size() == 1) {
            if (b == array.get(0) && !flag) {
                winArray[0] = true;
                flag = true;

                synchronized (sd) {
                    sd.setFlag(true);
                }
            }
        }

        if (flag) {
            if (Thread.currentThread().getName().equals("thread1")) {
                winArray[array.size() - 1] = true;
            }

            synchronized (sd) {
                sd.setWinArray(winArray);
            }
        }
    }
}
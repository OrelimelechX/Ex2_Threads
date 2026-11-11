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
    /** {@code true} once this thread has found a valid subset. */
    private boolean flag;
    /** Solution vector: {@code winArray[i]} is {@code true} if the i-th number is part of the subset. */
    private boolean[] winArray;
    /** Data shared by both threads (input numbers, target and result). */
    SharedData sd;
    /** The input numbers, stored as an {@code ArrayList<Integer>}. */
    ArrayList<Integer> array;
    /** The target sum. */
    int b;

    /**
     * Creates a task that reads its input from the shared data object.
     *
     * @param sd the shared data holding the numbers and the target sum
     */
    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
        synchronized (sd) {
            array = sd.getArray();
            b = sd.getB();
        }
        winArray = new boolean[array.size()];
    }

    /**
     * Recursively checks whether a subset of the first {@code n} numbers sums to {@code b}.
     * Stops early if another thread has already found a solution.
     *
     * @param n how many numbers (from the start of the list) to consider
     * @param b the remaining sum to reach
     */
    void rec(int n, int b) {
        synchronized (sd) {
            if (sd.getFlag()) return;
        }
        if (n == 1) {
            if(b == 0 || b == array.get(n-1)) {
                flag = true;
                synchronized (sd) { sd.setFlag(true); }
            }
            if (b == array.get(n-1)) winArray[n-1] = true;
            return;
        }

        rec(n-1, b - array.get(n-1));
        if (flag) winArray[n-1] = true;
        synchronized (sd) {
            if (sd.getFlag()) return;
        }
        rec(n-1, b);
    }

    /**
     * Runs this thread's half of the search and, if a solution was found,
     * publishes the solution vector to the shared data object.
     */
    public void run() {
        if (array.size() != 1)
            if (Thread.currentThread().getName().equals("thread1"))
                rec(array.size()-1, b - array.get(array.size() - 1));
            else
                rec(array.size()-1, b);
        if (array.size() == 1)
            if (b == array.get(0) && !flag) {
                winArray[0] = true;
                flag = true;
                synchronized (sd) { sd.setFlag(true); }
            }
        if (flag) {
            if (Thread.currentThread().getName().equals("thread1"))
                winArray[array.size() - 1] = true;
            synchronized (sd) { sd.setWinArray(winArray); }
        }
    }
}

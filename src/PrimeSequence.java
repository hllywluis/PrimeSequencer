public class PrimeSequence implements Sequence {
    private long startNum;
    private final int OVERFLOW_DETECTED = 0; // as per specs
    private int counter = 1; // start at 1 to prevent breaking next().
    private int[] checkedPrimeResults;

    /**
     * Constructor for the <code>PrimeSequence</code> object.
     *
     * @param startNum The number that is passed in through <code>main()'s</code> <code>args[0]</code>.
     * @param numPrimes The number of prime numbers to calculate.
     */
    public PrimeSequence(int startNum, int numPrimes) {
        this.startNum = startNum;
        checkedPrimeResults = new int[numPrimes + 1]; // need to add 1 since counter starts at 1
    }

    /**
     * Gets the <code>startNum</code> object to calculate the next prime number in the sequence.
     *
     * @return The next prime number in the sequence.
     */
    @Override
    public int next() {
        startNum++; // increment startNum to prevent printing out the same number that was requested
        if (startNum == checkedPrimeResults[counter - 1]) { // verify current prime against array of stored results
            startNum++;
        }
        while (!isPrime(startNum)) {
            startNum++;
        }
        if (startNum > Integer.MAX_VALUE) {
            return OVERFLOW_DETECTED;
        }
        checkedPrimeResults[counter] = (int) startNum;
        counter++;
        return (int) startNum;
    }

    /**
     * Verifies whether a given number is prime or not, and returns a boolean value accordingly. 2 is a prime number.
     *
     * Formula: https://codereview.stackexchange.com/questions/24704/efficiently-determining-if-a-number-is-prime
     *
     * @param startNum The number passed in through <code>next()</code>.
     * @return Whether this number is prime or not.
     */
    private boolean isPrime(long startNum) {
        if (startNum == 2) {
            return true;
        }
        if (startNum % 2 == 0 || startNum < 2) {
            return false;
        }
        int top = (int) Math.sqrt(startNum);
        for (int index = 2; index <= top; index++) {
            if (startNum % index == 0) {
                return false;
            }
        }
        return true;
    }
}

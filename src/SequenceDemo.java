public class SequenceDemo {
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 2) { // verify exactly 2 arguments passed into the program
            System.out.println("Please enter two positive integers (excluding 0) after the program name!");
            System.exit(1);
        }
        int[] sequence = primeSequencer(importNumber(args[0]), importNumber(args[1]));
        displayPrimes(importNumber(args[0]), sequence);
        displayHistogram(sequence);
    }

    /**
     * Verifies whether given program arguments are positive integers and exits if they are not.
     *
     * @param argument The argument passed through <code>main()</code>.
     * @return The validated input if and only if the input is a positive integer.
     */
    private static int importNumber(String argument) {
        if (!argument.matches("^\\d+$")) { // regular expression used here to verify positive integers
            System.out.println("Please enter a positive integer (excluding 0)!");
            System.exit(1);
        } else if (Integer.parseInt(argument) == 0) {
            System.out.println("Please enter the number 1 or greater!");
            System.exit(1);
        }
        return Integer.parseInt(argument);
    }

    /**
     * Generates a sequence of <code>numberOfPrimes</code> prime numbers starting from <code>startNum</code>.
     *
     * @param startNum The number that starts the sequence.
     * @param numberOfPrimes The number of prime numbers to generate.
     * @return The completed array (sequence) of prime numbers.
     */
    private static int[] primeSequencer(int startNum, int numberOfPrimes) {
        PrimeSequence sequence = new PrimeSequence(startNum, numberOfPrimes);
        int[] primeNumbers = new int[numberOfPrimes];

        for (int index = 0; index < numberOfPrimes; index++) {
            primeNumbers[index] = sequence.next();
        }
        return primeNumbers;
    }

    /**
     * Pretty-prints out a "boxy" representation of an array (sequence) of positive integers (in this case prime numbers).
     *
     * @param start The initial number that started the sequence, found in <code>main()</code>.
     * @param sequence The array (sequence) to print.
     */
    private static void displayPrimes(int start, int[] sequence) {
        final int MAX_COLUMNS = 10; // as per specs, maximum number of columns is 10
        int primeCounter = 0;
        int whitespaceLimiter = 0;
        double rowEnd = Math.ceil(Math.sqrt(sequence.length));
        double columnEnd = Math.floor(Math.sqrt(sequence.length));
        if (columnEnd > MAX_COLUMNS) {
            columnEnd = MAX_COLUMNS;
        }
        System.out.println("Prime Sequence Table: \n" +
                "Printing a sequence of " + sequence.length + " prime number(s), starting with the first prime after"
                + " " + start + ":\n");
        for (int rowIndex = 0; rowIndex < rowEnd + columnEnd; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnEnd; columnIndex++) {
                if (primeCounter != sequence.length) {
                    System.out.printf("%10d", sequence[primeCounter]);
                    primeCounter++;
                }
                whitespaceLimiter++;
            }
            if (!(whitespaceLimiter > primeCounter)) { // prevents excess whitespace
                System.out.printf("%n");
            }
        }
        System.out.println("\n\nTable entries of 0 indicate overflow results.");
    }

    /**
     * Pretty-prints out a "histogram" of the amount of times the last digit occurred in the array (sequence).
     * <code>printf</code> is concatenated with the subtracted amount to ensure even spacing.
     *
     * @param sequence The array of positive integers (sequence of primes in this case) to print out.
     */
    private static void displayHistogram(int[] sequence) {
        final int MAX_INDEX = 10; // Numbers from 0 to 9 = 10 total.
        double numLastDigitsPercentage = 0.0;
        int totalNumbers = 0;
        int totalPercentage = 0;
        System.out.println("\nLast Digit Histogram:\n" +
                "Scaled as %, each * = 1%\n" +
                "Total percentage may vary slightly from 100% due to rounding.\n");
        for (int index = 0; index < MAX_INDEX; index++) {
            if (numberOfLastDigits(sequence, index) != 0) {
                numLastDigitsPercentage = Math.ceil(((double) numberOfLastDigits(sequence, index) / sequence.length) * 100);
            }
            if ((100 - histogramData(sequence, index).length()) > 0) {
                System.out.printf("[%d] %s %" + (100 - histogramData(sequence, index).length()) + "s (%d, %3.0f%%)%n",
                        index, histogramData(sequence, index), "", numberOfLastDigits(sequence, index),
                        numLastDigitsPercentage);
            }
            else { // prevent %0s from happening
                System.out.printf("[%d] %s %s (%d, %3.0f%%)%n",
                        index, histogramData(sequence, index), "", numberOfLastDigits(sequence, index),
                        numLastDigitsPercentage);
            }
            totalNumbers += numberOfLastDigits(sequence, index);
            totalPercentage += numLastDigitsPercentage;
            numLastDigitsPercentage = 0;
        }
        System.out.println("_________________________________________________________________________________________" +
                "_____________________________\n" +
                "Total (actual count, %)                                                                             " +
                "      (" + totalNumbers + ",  " + totalPercentage + "%)\n" +
                "Histogram entries in [0] row indicate overflow results.");
    }

    /**
     * Generates a <code>String</code> of asterisks based on the percentage of last digit occurrence.
     *
     * @param sequence The array (sequence) of prime numbers to calculate from.
     * @param number The number to compare the last digit of numbers in the array (sequence) from.
     * @return The generated <code>String</code> of asterisks.
     */
    private static String histogramData(int[] sequence, double number) {
        StringBuilder asterisks = new StringBuilder();
        int lastCounter = 0;
        for (int prime : sequence) {
            if (prime % 10 == number) {
                lastCounter++;
            }
        }
        if (lastCounter == 0) {
            return "";
        }
        for (int index = 0; index < Math.ceil(((double) lastCounter / sequence.length) * 100); index++) {
            asterisks.append("*");
        }
        return asterisks.toString();
    }

    /**
     * Generates the calculated number of each last digit occurrence.
     *
     * @param sequence The array (sequence) of prime numbers to use.
     * @param number The number to compare the last digit of numbers in the array (sequence) from.
     * @return The number of times <code>number</code> appeared as a last digit in <code>sequence</code>.
     */
    private static int numberOfLastDigits(int[] sequence, int number) {
        int numberOfLastDigits = 0;
        for (int prime : sequence) {
            if (prime % 10 == number) {
                numberOfLastDigits++;
            }
        }
        return numberOfLastDigits;
    }
}

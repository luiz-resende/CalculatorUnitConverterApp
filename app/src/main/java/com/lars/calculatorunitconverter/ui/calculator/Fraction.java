package com.lars.calculatorunitconverter.ui.calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

@SuppressWarnings({"unused", "RedundantSuppression", "DuplicateExpressions", "IfStatementWithIdenticalBranches"})
public class Fraction {


    private final int PRECISION = 100;
    private final RoundingMode ROUND_MODE = RoundingMode.HALF_UP;
    private final MathContext MC = new MathContext(this.PRECISION, this.ROUND_MODE);

    public final BigDecimal PI = new BigDecimal(
            "3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679", MC);

    public final BigDecimal PI2 = new BigDecimal(String.valueOf(Math.PI), MC);

    /*private BigInteger numerator, denominator;
    private BigInteger integerPart;*/
    private String sign = "";



    /* EMPTY CONSTRUCTOR */
    Fraction() {}



    /**
     * Method to find the fractional form of a decimal number. The fraction
     * representation of the decimal is approximated either by the 10^numr_size
     * method or by multiplication and subtraction of repeating part.
     *
     * @param number    The <code>BigDecimal</code> decimal number
     *                  to be converted to fraction form.
     *
     * @return  HtmlComp <code>String</code> containing the fractional form.
     * INPUT: BigDecimal containing the result from the mathematical expression evaluation
     * OUTPUT: String containing the
     */
    public String basicRational(BigDecimal number, boolean includePi) {

        if (number.compareTo(BigDecimal.ZERO) < 0)
        {
            number = number.abs();
            this.sign = "-";
        }

        String result = this.sign;  // Appending negative sign if number is negative

        BigInteger integerPart = number.toBigInteger();
        if(number.remainder(new BigDecimal("1.0", MC)).equals(new BigDecimal("0", MC))) {  // Checking if number is int
            result += number;
            return result;  // Supplied value is not a fraction.
        }

        int digits_decimal = String.valueOf(number).length() - String.valueOf(number).indexOf('.') - 1;

        String decimalPart = String.valueOf(number).replace((number.toBigInteger() + "."), "");
        BigInteger numerator = new BigInteger(decimalPart);  // numerator/(10^x) decimal to fraction approximation method
        BigInteger denominator = new BigInteger(String.valueOf(10)).pow(digits_decimal);

        String[] recDecimal = recurrence(decimalPart);  // Calling recurrence method to check if it is a repeating decimal
        if (!recDecimal[1].equals("-1"))
        {
            if(recDecimal[0].length() == 0) {
                numerator = new BigInteger(recDecimal[1]);
                denominator = new BigInteger(String.valueOf(10)).pow(recDecimal[1].length()).subtract(new BigInteger("1"));
            }
            else {
                String temp_num_1 = recDecimal[0] + recDecimal[1];
                String temp_num_2 = recDecimal[0];
                numerator = new BigInteger(temp_num_1).subtract(new BigInteger(temp_num_2));
                denominator = new BigInteger("10").pow(temp_num_1.length()).subtract(new BigInteger("10").pow(temp_num_2.length()));
            }
        }

        BigInteger gcd = numerator.gcd(denominator);

        denominator = denominator.divide(gcd);
        numerator = numerator.divide(gcd);

        if(integerPart.compareTo(BigInteger.ZERO) == 0 && numerator.compareTo(BigInteger.ZERO) > 0) {
            if(!includePi) {
                result += "<sup><small><small>"
                        + numerator.toString()
                        + "</small></small></sup>&frasl;<sub><small><small>"
                        + denominator.toString() + "</small></small></sub>";
            }
            else {
                result += "<sup><small><small>"
                        + numerator.toString()
                        + "&#x3c0;"
                        + "</small></small></sup>&frasl;<sub><small><small>"
                        + denominator.toString() + "</small></small></sub>";
            }
        }
        else if(integerPart.compareTo(BigInteger.ZERO) > 0 && numerator.compareTo(BigInteger.ZERO) > 0) {
            if(!includePi) {
                result += integerPart
                        + " "
                        + "<sup><small><small>"
                        + numerator.toString()
                        + "</small></small></sup>&frasl;<sub><small><small>"
                        + denominator.toString()
                        + "</small></small></sub>";
            }
            else {
                result += "<sup><small><small>"
                        + integerPart.multiply(denominator).add(numerator).toString()
                        + "&#x3c0;"
                        + "</small></small></sup>&frasl;<sub><small><small>"
                        + denominator.toString()
                        + "</small></small></sub>";
            }
        }
        else {
            result += integerPart;
        }

        return result;
    }



    /**
     * Method to find the recurrence in a repeating decimal.
     *
     * @param decimalStr    <code>String</code> containing decimal
     *                      part of a decimal number to be analysed.
     *
     * @return  Array <code>String[]</code> containing non-repeating
     *          part at position <code>String[0]</code> and the repeating
     *          part at position <code>String[1]</code>. If number is not
     *          decimal or if decimal has no repeating sequence, method
     *          returns array <code>{"-1", "-1"}</code>.
     */
    public String[] recurrence(String decimalStr)
    {
        if(decimalStr.length() < 15) {  // If number does not fill the 16 places of a double, the decimal does not repeat recurrence
            return new String[]{"-1", "-1"};
        }

        StringBuilder strBuilderRepeat = new StringBuilder();
        StringBuilder strBuilderUnique = new StringBuilder();

        for (int i = 0; i < decimalStr.length(); i++)
        {
            int uniqueBuildSize = strBuilderUnique.toString().length();  // Length of string builder with non-repeating sequence
            String tempDecimalStr = decimalStr.substring(uniqueBuildSize);  // Removing non-repeating from temp string

            strBuilderRepeat.append(decimalStr.charAt(i));  // Incrementing the string with sequence for test

            int diffLengthDiv = (tempDecimalStr.length() / strBuilderRepeat.toString().length());  // Integer division between string size and sequence size
            int diffLengthRem = (tempDecimalStr.length() % strBuilderRepeat.toString().length());  // Reminder of above division
            int occurrences = numberOccurrences(strBuilderRepeat.toString(), tempDecimalStr);  // Number of occurrences of sequence in the string

            if(occurrences <= 1) {  // Checking if the sequence repeats for pruning
                strBuilderUnique.append(strBuilderRepeat.charAt(0));  // Removing non repeating number and assigning them to another string  -> decimalStr.charAt(i)
                strBuilderRepeat.deleteCharAt(0);  // -> strBuilderRepeat.length() - 1
            }

            int repeatBuildSize = strBuilderRepeat.toString().length();  // Length of string builder with repeating sequence

            if (occurrences > 1 && ((diffLengthDiv == occurrences && diffLengthRem < repeatBuildSize) || diffLengthDiv == (occurrences - repeatBuildSize)))
            {
                String reminderStr = reminderString(strBuilderRepeat.toString(), tempDecimalStr);
                boolean stillRepeating = testContainRepeating(reminderStr);

                int testIndexRem = strBuilderRepeat.indexOf(reminderStr);
                int testIndexRemSub = -1;
                if(reminderStr.length() > 0) {
                    testIndexRemSub = strBuilderRepeat.indexOf(reminderStr.substring(0, reminderStr.length() - 1));
                }

                if(!stillRepeating || (testIndexRem == 0 || testIndexRemSub == 0)) {

                    String outSequence, inSequence;
                    if(allSameCharacters(strBuilderRepeat.toString())) {
                        inSequence = strBuilderRepeat.toString().substring(0, 1);
                        outSequence = strBuilderUnique.toString();
                    }
                    else {
                        inSequence = strBuilderRepeat.toString();
                        outSequence = strBuilderUnique.toString();
                    }
                    return new String[]{outSequence, inSequence};
                }
            }
        }
        return new String[]{"-1", "-1"};
    }



    /**
     * Method to find the number of times a given string sequence repeats
     * within another larger string.
     *
     *  @param sequenceStr	Sequence <code>String</code> to test.
     *  @param fullString	Larger <code>String</code> to be tested with sequence.
     *
     *  @return	<code>int</code> number of times the sequenceStr was found.
     */
    public static int numberOccurrences(String sequenceStr, String fullString) {
        int counter = 0;
        int size = sequenceStr.length();
        String decimalStr = fullString;

        for (int j = (decimalStr.length() / size); j > 0; j--) { // Looping through string and counting times sequence appears
            int strIndex = decimalStr.indexOf(sequenceStr);
            if (strIndex != -1) {
                counter++;

                StringBuilder builder = new StringBuilder(decimalStr);  // Reducing size of fullString
                builder.delete(strIndex, (strIndex + size));
                decimalStr = builder.toString();
            }
        }
        return counter;
    }



    /**
     * Method to find the reminder of a String after a repeating sequence
     * is deleted for all its repeating times. It is designed to help finding
     * still repeating number left behind.
     *
     *  @param sequenceStr	Sequence <code>String</code> to be removed.
     *  @param fullString	Larger <code>String</code> to remove sequence from.
     *
     *  @return	<code>String</code> composed of reminder elements in the string.
     */
    public static String reminderString(String sequenceStr, String fullString) {
        int size = sequenceStr.length();
        String decimalStr = fullString;
        for (int j = (decimalStr.length() / size); j > 0; j--) { // Looping through string and removing sequence appearances
            int strIndex = decimalStr.indexOf(sequenceStr);
            if (strIndex != -1) {
                StringBuilder builder = new StringBuilder(decimalStr);
                builder.delete(strIndex, (strIndex + size));
                decimalStr = builder.toString();
            }
        }
        return decimalStr;
    }



    /**
     * Method to check if a String contains repeating elements.
     *
     *  @param str	<code>String</code> to be checked.
     *
     *  @return	<code>true</code> if some element repeats, <code>false</code> otherwise.
     */
    public static boolean testContainRepeating(String str) {
        for(int i = 0; i < str.length(); i++) {
            if(str.indexOf(str.charAt(i)) != str.lastIndexOf(str.charAt(i)) && str.lastIndexOf(str.charAt(i)) > 0) {
                return true;
            }
        }
        return false;
    }



    /**
     * Method to check if all the characters in a string are equal.
     *
     * @param sequence  <code>String</code> String with sequence to be checked.
     *
     * @return  <code>true</code> if all characters are the same,
     *          <code>false</code> otherwise.
     */
    static boolean allSameCharacters(String sequence)
    {
        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) != sequence.charAt(0)) {
                return false;
            }
        }
        return true;
    }



    /**
     * Method to find greatest common divisor between two BigInteger numbers.
     * Overloading "get_gcd" method.
     *
     * @param numer {@link BigInteger} for numerator and denominator.
     * @param denor {@link BigInteger} for numerator and denominator.
     * @return  {@link BigInteger} for gcd
     */
    public BigInteger get_gcd(BigInteger numer, BigInteger denor){
        numer = numer.abs();
        denor = denor.abs();

        return (denor.intValue() == 0) ? numer : get_gcd(denor, numer.remainder(denor));
    }



    /**
     * Method to find greatest common divisor between two {@link Integer} numbers.
     * Overloading "get_gcd" method.
     *
     * @param numer {@link Integer} for numerator and denominator.
     * @param denor {@link Integer} for numerator and denominator.
     * @return  {@link Integer} for gcd
     */
    public int get_gcd(int numer, int denor) {
        numer = Math.abs(numer);
        denor = Math.abs(denor);

        return denor == 0 ? numer : get_gcd(denor, (numer % denor));
    }



    /**
     * Method to find the fractional form of a decimal numbers multiples of Pi or not
     *
     * @param numb  {@link BigDecimal} containing the result from the mathematical expression evaluation
     * @return  {@link String} containing the fraction representation of the decimal approximated either
     *          by the 10^numr_size method or by multiplication and subtraction of repeating part
     *          (for repeating decimals) in function of Pi or not.
     */
    public String rational(BigDecimal numb) {

        if(numb.compareTo(PI) == 0 || numb.compareTo(PI2) == 0)
        {
            return "π";
        }
        else if(numb.divide(PI, MC).remainder(BigDecimal.ONE, MC).compareTo(BigDecimal.ZERO) == 0) {
            return numb.divide(PI, MC) + "π";
        }
        else if(numb.divide(PI2, MC).remainder(BigDecimal.ONE, MC).compareTo(BigDecimal.ZERO) == 0) {
            return numb.divide(PI2, MC) + "π";
        }
        String tempRationalBasic = basicRational(numb, false);
        String tempRationalPI = basicRational(numb.divide(PI, 15, MC.getRoundingMode()), true);
        String tempRationalPI2 = basicRational(numb.divide(PI2, 15, MC.getRoundingMode()), true);

        if(tempRationalPI.length() < tempRationalBasic.length() || tempRationalPI2.length() < tempRationalBasic.length()) {
            if(tempRationalPI.length() < tempRationalPI2.length()) {
                return tempRationalPI;
            }
            else {
                return tempRationalPI2;
            }
        }
        else {
            return tempRationalBasic;
        }
    }



    /*
     ** Initial designed method to find approximate fraction form of a decimal number
     */
    public String rational0000(BigDecimal number) {

        String sign = "";
        if (number.compareTo(BigDecimal.ZERO) < 0)
        {
            number = number.abs();
            sign = "-";
        }

        String result = sign;

        BigInteger integerPart = number.toBigInteger();
        if(number.remainder(new BigDecimal("1.0")).equals(new BigDecimal("0"))) {
            result += number;
            return result;  // Supplied value is not a fraction.
        }

        int digits_decimal = String.valueOf(number).length() - 1 - String.valueOf(number).indexOf('.');

        //BigDecimal num = new BigDecimal(String.valueOf(number));
        BigDecimal num = number.remainder(BigDecimal.ONE);
        BigDecimal big10_dec = new BigDecimal(String.valueOf(10.0));

        BigInteger denominator = new BigInteger(String.valueOf(1));
        BigInteger big10_int = new BigInteger(String.valueOf(10));

        for (int i = 0; i < digits_decimal; i++) {
            num = num.multiply(big10_dec);
            denominator = denominator.multiply(big10_int);
        }
        BigInteger numerator = num.toBigInteger();

        BigInteger gcd = numerator.gcd(denominator);

        denominator = denominator.divide(gcd);
        numerator = numerator.divide(gcd).subtract(number.toBigInteger().multiply(denominator));

        if(number.toBigInteger().equals(new BigInteger("0"))) {
            result += "<sup><small><small>"
                    + numerator.toString()
                    + "</small></small></sup>&frasl;<sub><small><small>"
                    + denominator.toString() + "</small></small></sub>";
        }
        else {
            result += number.toBigInteger()
                    + " "
                    + "<sup><small><small>"
                    + numerator.toString()
                    + "</small></small></sup>&frasl;<sub><small><small>"
                    + denominator.toString()
                    + "</small></small></sub>";
        }

        return result;
    }

}

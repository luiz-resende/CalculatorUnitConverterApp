package com.lars.calculatorunitconverter.ui.calculator;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.lars.calculatorunitconverter.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


/*
####################################################################################################
** General methods used throughout the code
####################################################################################################
*/
@SuppressWarnings({"IfStatementWithIdenticalBranches", "RedundantIfStatement", "FieldCanBeLocal", "unused", "RedundantSuppression", "ConstantConditions"})
public class GeneralFunctionsCalc {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @SuppressWarnings("FieldCanBeLocal")
    public final Integer NUMBER_OF_DECIMALS = MainActivity.NUMBER_DECIMALS;
    public final Integer MAXIMUM_NUMBER_LENGTH = CalculatorFragment.MAXIMUM_NUMBER_LENGTH;

    public GeneralFunctionsCalc() {
        // Empty constructor
    }


    /**
     * Method to add a designed custom function to be later analysed in the Expression.
     *
     * @param express   {@link String} for the expression.
     * @param func   {@link String} for the custom function.
     * @return  {@link String} with custom functions correctly added to expression.
     */
    @SuppressWarnings({"StringOperationCanBeSimplified", "IfStatementWithIdenticalBranches"})
    public String addCustomFunction(String express, String func) {
        int index = indexLastOperator(express);
        if(index >= 0) {
            String result = express.substring(0, (index + 1));
            result += func;
            result += "(";
            result += express.substring((index + 1), express.length());
            result += ")";
            return result;
        }
        else {
            String result = func;
            result += "(";
            result += express;
            result += ")";
            return result;
        }
    }


    /**
     * Method to check if the input string last character is numeric. Method checks input to handle operations requiring
     * such condition
     *
     * @param str   {@link String} variable containing math expression.
     * @return  {@link Boolean} flagging if the string last character is numeric (<code>true</code>) or not (<code>false</code>)
     */
    @SuppressWarnings("PointlessBooleanExpression")
    public boolean checkLastNumeric(String str) {
        if(str != null && !str.trim().isEmpty()) {
            return (Character.isDigit(str.charAt(str.length() - 1)) == true) || ((str.charAt(str.length() - 1) == ".".charAt(0)) == true);
        }
        else {
            return false;
        }
    }


    /**
     * Method to check if the input string is not null. Method checks input to handle empty string when calling the
     * converting functions/methods.
     *
     * @param str   {@link String} variable containing math expression.
     * @return  {@link Boolean} variable flagging if the string has characters
     *          (<code>false</code>) or not (<code>true</code>)
     */
    @SuppressWarnings("RedundantIfStatement")
    public boolean checkEmptyString(String str) {
        if(str != null && !str.trim().isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }


    /**
     * Method to check if first string has second string as final characters (i.e. str2 is substring of str1).
     *
     * @param str_express   {@link String} for first sequence that may or may not contain second {@link String} sequence.
     * @param str_oper  Second {@link String} sequence.
     * @return  {@link Boolean} variable flagging if the string has characters (<code>true</code>) or not (<code>false</code>)
     */
    public boolean checkEndString(String str_express, String str_oper) {

        boolean result = false;
        if(str_express.length() > 0  && str_oper.length() > 0) {
            for(int i = 0; i < str_oper.length(); i++) {
                if(str_express.charAt(str_express.length() - 1 - i) == str_oper.charAt(str_oper.length() - 1 - i)){
                    result = true;
                }
                else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }


    /**
     * Method to check if the input TextView is not null. Method checks input to handle empty string when calling the
     * converting functions/methods.
     *
     * @param text  {@link TextView} object.
     * @return  {@link Boolean} variable flagging if the string has characters (<code>false</code>) or not (<code>true</code>)
     */
    public boolean checkTextView(TextView text) {
        String str = text.getText().toString();
        if(str != null && !str.trim().isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }


    /**
     * Method to get the index of the last mathematical operator in the string.
     *
     * @param str   {@link String} object.
     * @return  {@link Integer} variable showing the index of last operator or <code>-1</code> if no operator is found in the string
     */
    public int indexLastOperator(String str) {
        int index = 0;
        boolean flag = false;
        String[] operators = new String[] {"+", "-", "*", "/", "^", "(", ")"};

        for(String temp_str : operators){
            if (str.contains(temp_str)) {
                flag = true;
                break;
            }
        }
        if(flag) {
            for(String temp_str : operators){
                if(str.lastIndexOf(temp_str) > index) {
                    index = str.lastIndexOf(temp_str);
                }
            }
            return index;
        }
        else {
            return -1;
        }
    }


    /**
     * Method to analyze the output for the <code>decimal_flag</code>.
     *
     * @param express   {@link String} for the expression.
     * @return  {@link Boolean} showing if the last number entered is decimal (<code>true</code>)
     *             or not (<code>false</code>).
     */
    public boolean isDecimalNumber(String express) {
        int lastIndex = indexLastOperator(express);
        String temp_express = express;

        if(lastIndex >= 0) {
            temp_express = express.substring(lastIndex);
        }

        if(temp_express.contains(".")) {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Method to discover number of decimal digits in a given double number.
     *
     * @param number    {@link Double} number to extract decimals from.
     * @return  {@link Integer} representing number of decimal digits.
     */
    public int getNumberDecimals(Double number){
        String[] temp_number = number.toString().split("\\.");
        return temp_number[1].length();
    }



    /**
     * Method to discover and save indices of a given substring in a primary string.
     *
     * @param str    {@link String} expression to analyze.
     * @return  {@link List<Integer>} representing the indices of occurrence of given substring.
     */
    public ArrayList<Integer> getIndicesSubstring(String str, String find){
        String tempStr = str;
        ArrayList<Integer> tempList = new ArrayList<>();

        int strIndex = tempStr.indexOf(find);
        while(strIndex != -1){
            tempList.add(strIndex);
            StringBuilder builder = new StringBuilder(tempStr);  // Reducing size of fullString
            builder.delete(strIndex, (strIndex + find.length()));
            tempStr = builder.toString();
            strIndex = tempStr.indexOf(find);
        }

        return tempList;
    }



    /**
     * Method to replace a given {@link String} in given indices by another substring.
     *
     * @param str    {@link String} expression to analyze.
     * @param toReplace    {@link String} to be replaced.
     * @param replaceFor    {@link String} used to replace the previous.
     * @param indices   {@link List<Integer>} with indices of occurrence.
     * @return  {@link String} resulting from the substitution.
     */
    public String replaceOccur(String str, String toReplace, String replaceFor, ArrayList<Integer> indices){
        String tempStr = str;
        int numberIndices = indices.size();

        if(numberIndices > 0) {
            for(int i = 0; i < numberIndices; i++) {
                StringBuilder builder = new StringBuilder(tempStr);  // Reducing size of fullString
                builder.delete(indices.get(i), (indices.get(i) + toReplace.length()));
                builder.insert(indices.get(i), replaceFor) ;
                tempStr = builder.toString();
                if(i < numberIndices - 1) {
                    indices.add(i + 1, (indices.get(i) + (replaceFor.length() - toReplace.length())));
                    indices.remove(i + 2);
                }
            }
        }
        return tempStr;
    }



    /**
     * Method to format string representing a number to a given normal of scientific format.
     *
     * @param input {@link Double} to be formatted.
     * @return  {@link String} with resulting formatted number.
     */
    @SuppressLint("DefaultLocale")
    public String format(BigDecimal input){
        //String str = Double.toString(input);
        String str = input.toString();
        if (str.length() <= MAXIMUM_NUMBER_LENGTH) {
            return str;
        } else {
            return String.format("%3.11e", input);  // Scientific format
        }
    }


    /**
     * Method to round result by a given number of decimals. BigDecimal library used to extract precise part of double
     * number and to round UP or DOWN based on the decimal part's nearest neighbor (or up if neighbors are equidistant).
     * {@link BigDecimal} library also helps using <code>BigDecimal.valueOf()</code>, which does some extra rounding
     * based on how double would appear if you printed it.
     *
     * @param number {@link Double} to be rounded.
     * @param decDigits {@link Integer} number of decimal digits to maintain (precision)
     * @return  {@link Double} rounded to the desired number of decimal places.
     */
    public double roundDecimals(@NotNull Double number, int decDigits) {
        String[] splitter = number.toString().split("\\.");  // Splitting double number and counting decimals
        //int number_of_integers = splitter[0].length();   // Number of digits before decimal count
        int decimals_in_number = splitter[1].length();   // Number of digits after decimal count

        if(decDigits == -1) {
            decDigits = this.NUMBER_OF_DECIMALS;
        }

        BigDecimal temp_number = BigDecimal.valueOf(number);  // 'BigDecimal.valueOf()' does some extra rounding based on how double would appear if you printed it.
        if(temp_number.remainder(new BigDecimal("1")).equals(new BigDecimal("0"))) {
            return temp_number.doubleValue();  // Return the double value
        }
        else if(decimals_in_number <= decDigits) {
            return temp_number.doubleValue();  // Return the double value
        } else {
            temp_number = temp_number.setScale(decDigits, RoundingMode.HALF_UP);  // Rounding up if remainder >=0.5
            return temp_number.doubleValue();  // Return the rounded double value
        }
    }

}

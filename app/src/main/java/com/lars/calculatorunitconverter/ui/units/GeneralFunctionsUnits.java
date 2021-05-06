/*
####################################################################################################
** General methods used throughout the code
####################################################################################################
*/

package com.lars.calculatorunitconverter.ui.units;

import android.util.Log;
import android.widget.EditText;

import com.lars.calculatorunitconverter.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings({"IfStatementWithIdenticalBranches", "ConstantConditions", "RedundantSuppression"})
public class GeneralFunctionsUnits {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @SuppressWarnings("FieldCanBeLocal")
    private final Integer NUMBER_OF_DECIMALS = MainActivity.NUMBER_DECIMALS;


    /*EMPTY CONSTRUCTOR*/
    public GeneralFunctionsUnits() {}



    /*
     ** Method to import input number. Method already checks input to handle empty string input.
     * INPUT: EditText type object from given units layout.
     * OUTPUT: Java Double type variable containing the input number converted to double.
     */
    public Double get_Input_Number(EditText str) {
        Double temp_input;
        String temp_str = str.getText().toString();

        if(temp_str != null && !temp_str.trim().isEmpty()) {
            temp_input = Double.parseDouble(str.getText().toString());
        }
        else {
            temp_input = null;
        }
        return temp_input;
    }



    /*
     ** Method to check if the input TextView is not null. Method checks input to handle empty string when calling the
     * converting functions/methods.
     * INPUT: EditText object from units layout.
     * OUTPUT: Java Boolean type variable flagging if the string has numbers (true) or not (false)
     */
    @SuppressWarnings("unused")
    public boolean checkTextView(EditText text_input) {
        String str = text_input.getText().toString();

        //noinspection ConstantConditions
        if(str != null && !str.trim().isEmpty()) {
            Log.d(LOG_TAG, "        Reading TextView typed number...");
            return true;
        }
        else {
            Log.d(LOG_TAG, "        Empty string/null variable found!");
            return false;
        }
    }



    /*
     ** Method to round result by a given number of decimals. BigDecimal library used to extract precise part of double
     * number and to round UP or DOWN based on the decimal part's nearest neighbor (or up if neighbors are equidistant).
     * BigDecimal library also helps using 'BigDecimal.valueOf()', which does some extra rounding based on how double
     * would appear if you printed it.
     * INPUT: Java Double to be rounded.
     * OUTPUT: Java Double rounded to the desired number of decimal places.
     */
    public double roundDecimals(@NotNull Double number) {
        String[] splitter = number.toString().split("\\.");  // Splitting double number and counting decimals
        //int number_of_integers = splitter[0].length();   // Number of digits before decimal count
        int decimals_in_number = splitter[1].length();   // Number of digits after decimal count

        BigDecimal temp_number = BigDecimal.valueOf(number);  // 'BigDecimal.valueOf()' does some extra rounding based on how double would appear if you printed it.
        if(decimals_in_number <= this.NUMBER_OF_DECIMALS) {
            return temp_number.doubleValue();  // Return the double value
        } else {
            temp_number = temp_number.setScale(this.NUMBER_OF_DECIMALS, RoundingMode.HALF_UP);  // Rounding up if remainder >=0.5
            return temp_number.doubleValue();  // Return the rounded double value
        }
    }



    /*
     ** Method to format string representing a number to a given normal of scientific format.
     * INPUT: Java Double to be formatted.
     * OUTPUT: Java String with resulting formatted number.
     */
    public String formatNumber(Double input){
        String str = Double.toString(roundDecimals(input));
        if (str.length() <= 15) {
            return str;
        } else {
            String form = ("%6." + NUMBER_OF_DECIMALS.toString() + "e");  // Scientific format
            return String.format(form, Double.valueOf(str));
        }
    }



}

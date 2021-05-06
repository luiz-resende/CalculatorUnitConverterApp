/*
** Fragment class designed to support the calculator layout added to the application. The calculations are done using exp4j
* dependency and numbers are managed using Strings and BigDecimal module to handle large numbers avoiding imprecision issues.
** Three other classes were coded and imported working as aides to keep the code clean.
* */

package com.lars.calculatorunitconverter.ui.calculator;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.lars.calculatorunitconverter.MainActivity;
import com.lars.calculatorunitconverter.R;
import com.lars.calculatorunitconverter.ui.calculator.evalex.Expression;

import java.math.BigDecimal;
import java.util.ArrayList;


@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "ConstantConditions", "Convert2Lambda", "RedundantSuppression", "WrapperTypeMayBePrimitive", "IfStatementWithIdenticalBranches", "DuplicateExpressions", "unused"})
public class CalculatorFragment extends Fragment {

    protected static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final int MAX_INTEGER = 2147483647;
    public static final Integer MAXIMUM_NUMBER_LENGTH = 20;

    /** View object */
    private View root_calculator;

    /** Buttons, TextViews and String used to construct mathematical expression used */
    protected Button button_dot, button_equ, button_hide;
    protected ImageButton button_del;
    protected Button button_inv, button_deg, button_rem, button_sin, button_cos, button_tan, button_ln, button_log,
            button_sqrt, button_exp;
    protected TextView display_result, display_result_2, display_degree;
    protected String running_result = "";

    /** Boolean primitives and ArrayList used as flags to keep track of given operations */
    protected boolean decimal_flag, empty_text_flag, equal_flag, last_numeric, state_error;
    protected boolean degree_flag = false, inverse_btn_flag = false, arc_trig_flag = false;
    protected ArrayList<Boolean> open_parenthesis_flag = new ArrayList<>();

    /** Arrays of integer ids for the number and operator buttons used */
    protected int[] calculator_buttons_numbers = new int[] {R.id.calculation_button_00, R.id.calculation_button_01,
            R.id.calculation_button_02, R.id.calculation_button_03, R.id.calculation_button_04, R.id.calculation_button_05,
            R.id.calculation_button_06, R.id.calculation_button_07, R.id.calculation_button_08, R.id.calculation_button_09,
            R.id.calculation_button_euler_number, R.id.calculation_button_pi};
    protected int[] calculator_buttons_operators = new int[] {R.id.calculation_button_add, R.id.calculation_button_subtract,
            R.id.calculation_button_divide, R.id.calculation_button_multiply};
    protected int[] calculator_buttons_operators_hidden = new int[] {R.id.calculation_button_reminder,
            R.id.calculation_button_sine, R.id.calculation_button_cosine, R.id.calculation_button_tangent,
            R.id.calculation_button_naturalLog, R.id.calculation_button_logarithm, R.id.calculation_button_sqrt,
            R.id.calculation_button_exponential, R.id.calculation_button_open_parenthesis,
            R.id.calculation_button_close_parenthesis, R.id.calculation_button_factorial};

    /** Arrays of Strings containing the text displayed on operator buttons */
    protected String[] calculator_buttons_operators_text = new String[5];
    protected String[] calculator_buttons_operators_hidden_text = new String[18];

    /** Importing aiding methods from GeneralFunctionsCalc class */
    protected final GeneralFunctionsCalc fnc = new GeneralFunctionsCalc();


    /** EMPTY CONSTRUCTOR */
    public CalculatorFragment() {}



    /*
    ** onCreateView inflates the the calculator layout and sets methods used on both the Portrait and Landscape layouts
    */
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.root_calculator = inflater.inflate(R.layout.fragment_calculator, container, false);


        return root_calculator;
    }



    /*
     ** Method designed to delete the last entry or function from the mathematical expression building string when clicking
     * the DEL button.
     * INPUT: TextView display String and mathematical expression building String
     * OUTPUT: String array containing modified TextView display String and mathematical expression building String
     */
    protected String[] onDelete(String str_display, String str_running) {
        String[] ans = new String[2];
        if (str_display != null && str_display.length() > 0 && str_running != null && str_running.length() > 0) {
            if(str_display.charAt(str_display.length() - 1) == ".".charAt(0)) {  // Checking if last character is dot

                str_display = str_display.substring(0, str_display.length() - 1);  // Copying String minus last character,
                str_running = str_running.substring(0, str_running.length() - 1);  // deleting the dot

                this.decimal_flag = false;  // Setting decimal flag to false
                this.last_numeric = fnc.checkLastNumeric(str_display);  // Setting last numeric char flag accordingly
            }
            else if(str_display.charAt(str_display.length() - 1) == ")".charAt(0)) {  // Checking if last char is parentheses

                str_display = str_display.substring(0, str_display.length() - 1);
                str_running = str_running.substring(0, str_running.length() - 1);

                //if(this.decimal_flag) { this.decimal_flag = false; }
                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
                this.open_parenthesis_flag.add(true);  // Adding an "open parentheses" flag to avoid evaluation error
            }
            else if(str_display.charAt(str_display.length() - 1) == "!".charAt(0)) {  // Checking if last char is exclamation

                str_display = str_display.substring(0, str_display.length() - 1);
                str_running = str_running.substring(0, str_running.length() - 1);

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
            }
            else if(str_display.charAt(str_display.length() - 1) == "%".charAt(0)) {  // Checking if last char is percent symbol

                str_display = str_display.substring(0, str_display.length() - 1);
                str_running = str_running.substring(0, str_running.length() - 2);

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
            }
            else if(str_display.length() > 3 && fnc.checkEndString(str_display, "mod")) {  // Checking if 3 last char is mod

                str_display = str_display.substring(0, str_display.length() - 3);
                str_running = str_running.substring(0, str_running.length() - 1);

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
            }
            else if(str_display.length() >= 5 && (fnc.checkEndString(str_display,"asin(")
                    || fnc.checkEndString(str_display,"acos(") || fnc.checkEndString(str_display,"atan("))) {
                // Checking if 5 last characters are either of arc sine, arc cosine or arc tangent functions

                str_display = str_display.substring(0, str_display.length() - 5);

                if(degree_flag) {  // Checking if the degree flag is true and deleting the number of chars accordingly
                    str_running = str_running.substring(0, str_running.length() - 10);
                }
                else {
                    str_running = str_running.substring(0, str_running.length() - 5);
                }

                this.arc_trig_flag = false;  // Setting arc trigonometry flag to false
                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
                if(this.open_parenthesis_flag.size() > 0){
                    this.open_parenthesis_flag.remove(0);  // Removing an "open parentheses" flag
                }
            }
            else if(str_display.length() >= 4 && (fnc.checkEndString(str_display,"sin(")
                    || fnc.checkEndString(str_display,"cos(") || fnc.checkEndString(str_display,"tan("))) {
                // Checking if 3 last characters are either of sine, cosine or tangent functions

                str_display = str_display.substring(0, str_display.length() - 4);

                if(degree_flag) {
                    str_running = str_running.substring(0, str_running.length() - 9);
                }
                else {
                    str_running = str_running.substring(0, str_running.length() - 4);
                }

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
                if(this.open_parenthesis_flag.size() > 0){
                    this.open_parenthesis_flag.remove(0);
                }
            }
            else if(str_display.length() >= 3 && fnc.checkEndString(str_display,"ln(")) {
                // Checking if 3 last characters are the function for natural logarithm

                str_display = str_display.substring(0, str_display.length() - 3);
                str_running = str_running.substring(0, str_running.length() - 4);

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
                if(this.open_parenthesis_flag.size() > 0){
                    this.open_parenthesis_flag.remove(0);
                }
            }
            else if(str_display.length() >= 4 && fnc.checkEndString(str_display,"log(")) {
                // Checking if 4 last characters are the function for base 10 logarithm

                str_display = str_display.substring(0, str_display.length() - 4);
                str_running = str_running.substring(0, str_running.length() - 6);

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
                if(this.open_parenthesis_flag.size() > 0){
                    this.open_parenthesis_flag.remove(0);
                }
            }
            else if(str_display.length() >= 5 && fnc.checkEndString(str_display,"sqrt(")) {
                // Checking if 5 last characters are the function for square root

                str_display = str_display.substring(0, str_display.length() - 5);
                str_running = str_running.substring(0, str_running.length() - 5);

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
                if(this.open_parenthesis_flag.size() > 0){
                    this.open_parenthesis_flag.remove(0);
                }
            }
            else if(str_display.length() >= 4 && fnc.checkEndString(str_display,"exp(")) {
                // Checking if 4 last characters are the function for exponential

                str_display = str_display.substring(0, str_display.length() - 4);
                str_running = str_running.substring(0, str_running.length() - 4);

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
                if(this.open_parenthesis_flag.size() > 0){
                    this.open_parenthesis_flag.remove(0);
                }
            }
            else if(str_display.charAt(str_display.length() - 1) == "(".charAt(0)) {  // Checking if last char is parentheses

                str_display = str_display.substring(0, str_display.length() - 1);  // Copying String minus last character
                str_running = str_running.substring(0, str_running.length() - 1);

                //if(this.decimal_flag) { this.decimal_flag = false; }
                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
                if(this.open_parenthesis_flag.size() > 0){
                    this.open_parenthesis_flag.remove(0);
                }
            }
            else if(str_display.substring(str_display.length() - 1).equals(getActivity().getResources()
                    .getString(R.string.calculator_button_op_pi))) {  // Checking if last char is pi

                str_display = str_display.substring(0, str_display.length() - 1);
                str_running = str_running.substring(0, str_running.length() - 1);

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
            }
            else {  // Deleting any other number or non special function from the mathematical expression

                str_display = str_display.substring(0, str_display.length() - 1);
                str_running = str_running.substring(0, str_running.length() - 1);

                this.last_numeric = (fnc.checkLastNumeric(str_display) || fnc.checkEndString(str_display, ")")
                        || fnc.checkEndString(str_display, "!")
                        || fnc.checkEndString(str_display, "%"));
                this.decimal_flag = fnc.isDecimalNumber(str_display);
            }
        }
        ans[0] = str_display;
        ans[1] = str_running;
        return ans;
    }



    /*
     ** Method designed to simplify onClickListener to numeric buttons. Creates a common onClickListener, find numeric button
     * and proceed with action from click.
     * INPUT: Inflater View object.
     */
    protected void setNumericOnClickListener(View root) {
        // Create a common onClickListener for numeric buttons
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;  // Typecasting Button object for clicking action
                if (state_error || equal_flag) {  // If current state is Error, replace the error message
                    display_result.setText(button.getText());
                    running_result = button.getText().toString();
                    state_error = false;
                    equal_flag = false;
                    decimal_flag = false;
                } else {  // If not, there is already a valid expression to be extended, so append text to it
                    display_result.append(button.getText());
                    running_result += button.getText().toString();
                }
                last_numeric = true;  // In any case, set the last numeric flag to true
                onPartialResult();  // Call "onPartialResult" method to show current expression result on smaller display
            }
        };
        for (int id : calculator_buttons_numbers) {  // Loop assigning the listener to all the numeric buttons
            root.findViewById(id).setOnClickListener(listener);
        }
    }



    /*
     ** Method designed to create OnClickListener to simple operator buttons, equal button and decimal point button. Finds
     * the operator buttons and proceeds with defined actions onClick.
     * INPUT: Inflater View object
     */
    protected void setOperatorOnClickListener(View root) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the current state is Error do not append the operator
                // If the last input is number only, append the operator
                if ((last_numeric || fnc.checkEndString(display_result.getText().toString(), "(")
                        || fnc.checkEmptyString(display_result.getText().toString())) && !state_error) {
                    Button button = (Button) view;
                    String choice = button.getText().toString();

                    if(choice.equals(calculator_buttons_operators_text[1])){
                        display_result.append(getActivity().getResources()
                                .getString(R.string.calculator_button_op_add));  // Appending plus sign
                        running_result += "+";
                        last_numeric = false;  // Reset last numeric
                        decimal_flag = false;  // Reset the decimal flag
                        equal_flag = false;
                    }
                    else if(choice.equals(calculator_buttons_operators_text[2])) {
                        display_result.append(getActivity().getResources()
                                .getString(R.string.calculator_button_op_subtract));  // Appending minus sign
                        running_result += "-";
                        last_numeric = false;  // Reset last numeric
                        decimal_flag = false;  // Reset the decimal flag
                        equal_flag = false;
                    }
                    else if(choice.equals(calculator_buttons_operators_text[3])) {
                        display_result.append(getActivity().getResources()
                                .getString(R.string.calculator_button_op_divide));  // Appending divide sign
                        running_result += "/";
                        last_numeric = false;  // Reset last numeric
                        decimal_flag = false;  // Reset the decimal flag
                        equal_flag = false;
                    }
                    else if(choice.equals(calculator_buttons_operators_text[4])) {
                        display_result.append(getActivity().getResources()
                                .getString(R.string.calculator_button_op_multiply));  // Appending multiplication sign
                        running_result += "*";
                        last_numeric = false;  // Reset last numeric
                        decimal_flag = false;  // Reset the decimal flag
                        equal_flag = false;
                    }
                }
            }
        };
        for (int id : calculator_buttons_operators) {  // Loop assigning the listener to the operator buttons
            root.findViewById(id).setOnClickListener(listener);
        }

        this.button_dot = root.findViewById(R.id.calculation_button_dot);  // DOT button for decimals
        this.button_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(last_numeric && !decimal_flag && !state_error) {
                    display_result.append(".");
                    running_result += ".";
                    decimal_flag = true;
                    last_numeric = false;
                    equal_flag = false;
                }  // Else, do nothing (or it will throw an exception of number format)
            }
        });

        this.button_del = root.findViewById(R.id.calculation_button_delete);  // DEL button
        button_del.setOnClickListener(new View.OnClickListener() {  // onClickListener to erase last input or function
            @Override
            public void onClick(View view) {  // onClick calls "onDelete" method
                if(running_result != null && !equal_flag) {
                    String[] temp_arr = onDelete(display_result.getText().toString(), running_result);
                    display_result.setText(temp_arr[0]);
                    running_result = temp_arr[1];
                    onPartialResult();
                }
                else if(running_result != null && equal_flag) {
                    display_result.setText("");
                    display_result.setTextColor(getResources().getColor(R.color.black));
                    display_result_2.setText("");
                    display_result_2.setTextColor(getResources().getColor(R.color.dk_grey));
                    running_result = "";

                    decimal_flag = false;
                    last_numeric = false;
                    state_error = false;
                    equal_flag = false;
                    arc_trig_flag = false;
                    open_parenthesis_flag.clear();
                }
            }
        });
        button_del.setOnLongClickListener(new View.OnLongClickListener() {  // onLongClick to clear entire screen
            @Override
            public boolean onLongClick(View view) {
                if(running_result != null) {
                    display_result.setText("");
                    display_result.setTextColor(getResources().getColor(R.color.black));
                    display_result_2.setText("");
                    display_result_2.setTextColor(getResources().getColor(R.color.dk_grey));
                    running_result = "";

                    decimal_flag = false;
                    last_numeric = false;
                    state_error = false;
                    equal_flag = false;
                    arc_trig_flag = false;
                    open_parenthesis_flag.clear();

                    return false;
                }
                else {
                    return true;
                }
            }
        });

        this.button_equ = root.findViewById(R.id.calculation_button_equal);  // EQUAL button
        button_equ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEqual();
                equal_flag = true;
                arc_trig_flag = false;
            }
        });
    }



    /*
     ** Method designed to create OnClickListener for the operator buttons displayed on the second panel (exp, log,
     * trigonometry etc.). Finds the operator buttons and proceeds with defined actions onClick.
     * INPUT: Inflater View object
     */
    protected void setOperatorHiddenOnClickListener(View root) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If the last input is number, NO state error and inverse button is NOT clicked
                if (last_numeric && !state_error && !inverse_btn_flag) {
                    Button button = (Button) view;
                    String choice = button.getText().toString();

                    if(choice.equals(calculator_buttons_operators_hidden_text[0])){  // Percentage operator
                        display_result.append("%");
                        running_result += ("#%");
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));  // Reset last numeric
                        decimal_flag = false;  // Reset the decimal flag
                        equal_flag = false;  // Reset "equal clicked" flag
                    }
                    else if(button.getId() == R.id.calculation_button_exponential) {  // Power of y
                        display_result.append("^");
                        running_result += "^";
                        last_numeric = false;
                        decimal_flag = false;
                        equal_flag = false;
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[17])) {  // Factorial operator
                        display_result.append("!");
                        running_result += ("!");
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        equal_flag = false;
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[16]) && open_parenthesis_flag.size() > 0){
                        display_result.append(")");  // Appending close parenthesis
                        running_result += ")";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.remove(0); // Removing flag to handle open parenthesis error
                    }
                    if(equal_flag) {
                        equal_flag = false;
                        display_result.setText("");
                        running_result = "";

                        if(choice.equals(calculator_buttons_operators_hidden_text[2])){  // Sine function
                            display_result.append("sin(");
                            if(!degree_flag) {
                                running_result += "sin(";  // Sine in radians
                            }
                            else {
                                running_result += "sin(toRad";  // Sine in degrees
                            }
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                            open_parenthesis_flag.add(true); // Adding flag to handle open parenthesis error
                        }
                        else if(choice.equals(calculator_buttons_operators_hidden_text[4])){  // Cosine function
                            display_result.append("cos(");
                            if(!degree_flag) {
                                running_result += "cos(";  // Cosine in radians
                            }
                            else {
                                running_result += "cos(toRad";  // Cosine in degrees
                            }
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                            open_parenthesis_flag.add(true);
                        }
                        else if(choice.equals(calculator_buttons_operators_hidden_text[6])){  // Tangent function
                            display_result.append("tan(");
                            if(!degree_flag) {
                                running_result += "tan(";  // Tangent in radians
                            }
                            else {
                                running_result += "tan(toRad";  // Tangent in degrees
                            }
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                            open_parenthesis_flag.add(true);
                        }
                        else if(choice.equals(calculator_buttons_operators_hidden_text[8])){  // Natural logarithm function
                            display_result.append("ln(");
                            running_result += "log(";
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                            open_parenthesis_flag.add(true);
                        }
                        else if(choice.equals(calculator_buttons_operators_hidden_text[10])){  // Logarithm base 10 function
                            display_result.append("log(");
                            running_result += "log10(";
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                            open_parenthesis_flag.add(true);
                        }
                        else if(choice.equals(calculator_buttons_operators_hidden_text[12])){  // Square root function
                            display_result.append("sqrt(");
                            running_result += "sqrt(";
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                            open_parenthesis_flag.add(true);
                        }
                        else if(choice.equals(calculator_buttons_operators_hidden_text[15])){  // Open parentheses operator
                            display_result.append("(");
                            running_result += "(";
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            open_parenthesis_flag.add(true); // Flag to handle open parenthesis error
                        }
                    }
                }
                // If the last input is number, NO state error and inverse button is clicked. Some functions remain the same
                else if (last_numeric && !state_error && inverse_btn_flag) {
                    Button button = (Button) view;
                    String choice = button.getText().toString();

                    if(choice.equals(calculator_buttons_operators_hidden_text[1])){  // Modulo/Reminder function
                        display_result.append("mod");
                        running_result += "%";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        equal_flag = false;
                    }
                    else if(button.getId() == R.id.calculation_button_exponential) {  // Power of y
                        display_result.append("^");
                        running_result += "^";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        equal_flag = false;
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[17])) {  // Factorial
                        display_result.append("!");
                        running_result += ("!");
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        equal_flag = false;
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[13])){  // Power of 2
                        display_result.append("^2");
                        running_result += "^2";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        equal_flag = false;
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[16]) && open_parenthesis_flag.size() > 0){
                        display_result.append(")");  // Appending close parenthesis
                        running_result += ")";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.remove(0);
                    }
                    if(equal_flag) {
                        equal_flag = false;
                        display_result.setText("");
                        running_result = "";

                        if (choice.equals(calculator_buttons_operators_hidden_text[3])) {  // Arc sine function
                            display_result.append("asin(");
                            if (!degree_flag) {
                                running_result += "asin(";  // Arc sine returning radians
                            } else {
                                running_result += "toDegasin(";  // Arc sine returning degrees
                            }
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                            open_parenthesis_flag.add(true);
                            arc_trig_flag = true;
                        }
                        else if (choice.equals(calculator_buttons_operators_hidden_text[5])) {  // Arc cosine function
                            display_result.append("acos(");
                            if (!degree_flag) {
                                running_result += "acos(";  // Arc cosine returning radians
                            } else {
                                display_result.append("acos(");
                                running_result += "toDegacos(";  // Arc cosine returning degrees
                            }
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                            open_parenthesis_flag.add(true);
                            arc_trig_flag = true;
                        }
                        else if (choice.equals(calculator_buttons_operators_hidden_text[7])) {  // Arc tangent function
                            display_result.append("atan(");
                            if (!degree_flag) {
                                running_result += "atan(";  // Arc tangent returning radians
                            } else {
                                running_result += "toDegatan(";  // Arc tangent returning degrees
                            }
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                            open_parenthesis_flag.add(true);
                            arc_trig_flag = true;
                        }
                        else if (button.getId() == R.id.calculation_button_naturalLog) {  // Euler's number raised to the power of x
                            display_result.append("e^");
                            running_result += "e^";
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                        }
                        else if (button.getId() == R.id.calculation_button_logarithm) {  // 10 raised to the power of y
                            display_result.append("10^");
                            running_result += "10^";
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            decimal_flag = false;
                        }
                        else if(choice.equals(calculator_buttons_operators_hidden_text[15])){  // Open parentheses operator
                            display_result.append("(");
                            running_result += "(";
                            last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                    || fnc.checkEndString(display_result.getText().toString(), ")")
                                    || fnc.checkEndString(display_result.getText().toString(), "!")
                                    || fnc.checkEndString(display_result.getText().toString(), "%"));
                            open_parenthesis_flag.add(true);
                        }
                    }
                }
                // If the last input is NOT number, NO state error and inverse button is NOT clicked
                else if (!last_numeric && !state_error && !inverse_btn_flag) {
                    Button button = (Button) view;
                    String choice = button.getText().toString();

                    if(choice.equals(calculator_buttons_operators_hidden_text[2])){  // Sine function
                        display_result.append("sin(");
                        if(!degree_flag) {
                            running_result += "sin(";  // Sine in radians
                        }
                        else {
                            running_result += "sin(toRad";  // Sine in degrees
                        }
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.add(true); // Adding flag to handle open parenthesis error
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[4])){  // Cosine function
                        display_result.append("cos(");
                        if(!degree_flag) {
                            running_result += "cos(";  // Cosine in radians
                        }
                        else {
                            running_result += "cos(toRad";  // Cosine in degrees
                        }
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.add(true);
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[6])){  // Tangent function
                        display_result.append("tan(");
                        if(!degree_flag) {
                            running_result += "tan(";  // Tangent in radians
                        }
                        else {
                            running_result += "tan(toRad";  // Tangent in degrees
                        }
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.add(true);
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[8])){  // Natural logarithm function
                        display_result.append("ln(");
                        running_result += "log(";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.add(true);
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[10])){  // Logarithm base 10 function
                        display_result.append("log(");
                        running_result += "log10(";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.add(true);
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[12])){  // Square root function
                        display_result.append("sqrt(");
                        running_result += "sqrt(";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.add(true);
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[15])){  // Open parentheses operator
                        display_result.append("(");
                        running_result += "(";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        open_parenthesis_flag.add(true); // Flag to handle open parenthesis error
                    }
                }
                // If the last input is NOT number, NO state error and inverse button is clicked
                else if (!last_numeric && !state_error && inverse_btn_flag) {
                    Button button = (Button) view;
                    String choice = button.getText().toString();

                    if (choice.equals(calculator_buttons_operators_hidden_text[3])) {  // Arc sine function
                        if(equal_flag) {
                            display_result.setText("");
                            running_result = "";
                            equal_flag = false;
                        }
                        display_result.append("asin(");
                        if (!degree_flag) {
                            running_result += "asin(";  // Arc sine returning radians
                        } else {
                            running_result += "toDegasin(";  // Arc sine returning degrees
                        }
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.add(true);
                        arc_trig_flag = true;
                    }
                    else if (choice.equals(calculator_buttons_operators_hidden_text[5])) {  // Arc cosine function
                        if(equal_flag) {
                            display_result.setText("");
                            running_result = "";
                            equal_flag = false;
                        }
                        display_result.append("acos(");
                        if (!degree_flag) {
                            running_result += "acos(";  // Arc cosine returning radians
                        } else {
                            display_result.append("acos(");
                            running_result += "toDegacos(";  // Arc cosine returning degrees
                        }
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.add(true);
                        arc_trig_flag = true;
                    }
                    else if (choice.equals(calculator_buttons_operators_hidden_text[7])) {  // Arc tangent function
                        if(equal_flag) {
                            display_result.setText("");
                            running_result = "";
                            equal_flag = false;
                        }
                        display_result.append("atan(");
                        if (!degree_flag) {
                            running_result += "atan(";  // Arc tangent returning radians
                        } else {
                            running_result += "toDegatan(";  // Arc tangent returning degrees
                        }
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                        open_parenthesis_flag.add(true);
                        arc_trig_flag = true;
                    }
                    else if (button.getId() == R.id.calculation_button_naturalLog) {  // Euler's number raised to the power of x
                        if(equal_flag) {
                            display_result.setText("");
                            running_result = "";
                            equal_flag = false;
                        }
                        display_result.append("e^");
                        running_result += "e^";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                    }
                    else if (button.getId() == R.id.calculation_button_logarithm) {  // 10 raised to the power of y
                        if(equal_flag) {
                            display_result.setText("");
                            running_result = "";
                            equal_flag = false;
                        }
                        display_result.append("10^");
                        running_result += "10^";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        decimal_flag = false;
                    }
                    else if(choice.equals(calculator_buttons_operators_hidden_text[15])){  // Open parentheses operator
                        if(equal_flag) {
                            display_result.setText("");
                            running_result = "";
                            equal_flag = false;
                        }
                        display_result.append("(");
                        running_result += "(";
                        last_numeric = (fnc.checkLastNumeric(display_result.getText().toString())
                                || fnc.checkEndString(display_result.getText().toString(), ")")
                                || fnc.checkEndString(display_result.getText().toString(), "!")
                                || fnc.checkEndString(display_result.getText().toString(), "%"));
                        open_parenthesis_flag.add(true);
                    }
                }
                onPartialResult();
            }
        };
        for (int id : calculator_buttons_operators_hidden) {  // Loop assigning the listener to all the operator buttons
            root.findViewById(id).setOnClickListener(listener);
        }

        this.button_deg = root.findViewById(R.id.calculation_button_degree);  // DEG to RAD button
        this.button_deg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(degree_flag){
                    degree_flag = false;
                    button_deg.setText(getActivity().getResources().getString(R.string.calculator_button_op_degrees));
                    display_degree.setText(getActivity().getResources().getString(R.string.calculator_button_op_radians));

                    ArrayList<Integer> tempMatrixRunning = fnc.getIndicesSubstring(running_result, "asin(");
                    running_result = fnc.replaceOccur(running_result, "toDegasin(", "asin(", tempMatrixRunning);
                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "acos(");
                    running_result = fnc.replaceOccur(running_result, "toDegacos(", "acos(", tempMatrixRunning);
                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "atan(");
                    running_result = fnc.replaceOccur(running_result, "toDegatan(", "atan(", tempMatrixRunning);

                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "sin(");
                    running_result = fnc.replaceOccur(running_result, "sin(toRad", "sin(", tempMatrixRunning);
                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "cos(");
                    running_result = fnc.replaceOccur(running_result, "cos(toRad", "cos(", tempMatrixRunning);
                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "tan(");
                    running_result = fnc.replaceOccur(running_result, "tan(toRad", "tan(", tempMatrixRunning);

                    onPartialResult();
                }
                else {
                    degree_flag = true;
                    button_deg.setText(getActivity().getResources().getString(R.string.calculator_button_op_radians));
                    display_degree.setText(getActivity().getResources().getString(R.string.calculator_button_op_degrees));

                    ArrayList<Integer> tempMatrixRunning = fnc.getIndicesSubstring(running_result, "asin(");
                    running_result = fnc.replaceOccur(running_result, "asin(", "toDegasin(", tempMatrixRunning);
                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "acos(");
                    running_result = fnc.replaceOccur(running_result, "acos(", "toDegacos(", tempMatrixRunning);
                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "atan(");
                    running_result = fnc.replaceOccur(running_result, "atan(", "toDegatan(", tempMatrixRunning);

                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "sin(");
                    running_result = fnc.replaceOccur(running_result, "sin(", "sin(toRad", tempMatrixRunning);
                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "cos(");
                    running_result = fnc.replaceOccur(running_result, "cos(", "cos(toRad", tempMatrixRunning);
                    tempMatrixRunning = fnc.getIndicesSubstring(running_result, "tan(");
                    running_result = fnc.replaceOccur(running_result, "tan(", "tan(toRad", tempMatrixRunning);

                    onPartialResult();
                }
            }
        });

        this.button_inv = root.findViewById(R.id.calculation_button_inverse);  // INVERSE button
        this.button_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inverse_btn_flag) {
                    inverse_btn_flag = false;
                    button_inv.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    button_rem = root.findViewById(R.id.calculation_button_reminder);
                    button_rem.setText(getActivity().getResources().getString(R.string.calculator_button_op_reminder));
                    button_sin = root.findViewById(R.id.calculation_button_sine);
                    button_sin.setText(getActivity().getResources().getString(R.string.calculator_button_op_sine));
                    button_cos = root.findViewById(R.id.calculation_button_cosine);
                    button_cos.setText(getActivity().getResources().getString(R.string.calculator_button_op_cosine));
                    button_tan = root.findViewById(R.id.calculation_button_tangent);
                    button_tan.setText(getActivity().getResources().getString(R.string.calculator_button_op_tangent));
                    button_ln = root.findViewById(R.id.calculation_button_naturalLog);
                    button_ln.setText(getActivity().getResources().getString(R.string.calculator_button_op_naturalLog));
                    button_log = root.findViewById(R.id.calculation_button_logarithm);
                    button_log.setText(getActivity().getResources().getString(R.string.calculator_button_op_logarithm));
                    button_sqrt = root.findViewById(R.id.calculation_button_sqrt);
                    button_sqrt.setText(getActivity().getResources().getString(R.string.calculator_button_op_sqrt));
                }
                else {
                    inverse_btn_flag = true;
                    button_inv.setTextColor(ContextCompat.getColor(getContext(), R.color.custom_tomato));
                    button_rem = root.findViewById(R.id.calculation_button_reminder);
                    button_rem.setText(getActivity().getResources().getString(R.string.calculator_button_op_reminder_inverse));
                    button_sin = root.findViewById(R.id.calculation_button_sine);
                    button_sin.setText(getActivity().getResources().getString(R.string.calculator_button_op_sine_inverse));
                    button_cos = root.findViewById(R.id.calculation_button_cosine);
                    button_cos.setText(getActivity().getResources().getString(R.string.calculator_button_op_cosine_inverse));
                    button_tan = root.findViewById(R.id.calculation_button_tangent);
                    button_tan.setText(getActivity().getResources().getString(R.string.calculator_button_op_tangent_inverse));
                    button_ln = root.findViewById(R.id.calculation_button_naturalLog);
                    //String temp = getActivity().getResources().getString(R.string.calculator_button_op_naturalLog_inverse);
                    button_ln.setText(Html.fromHtml(getActivity().getResources()
                            .getString(R.string.calculator_button_op_naturalLog_inverse)));
                    button_log = root.findViewById(R.id.calculation_button_logarithm);
                    //temp = getActivity().getResources().getString(R.string.calculator_button_op_logarithm_inverse);
                    button_log.setText(Html.fromHtml(getActivity().getResources()
                            .getString(R.string.calculator_button_op_logarithm_inverse)));
                    button_sqrt = root.findViewById(R.id.calculation_button_sqrt);
                    //temp = getActivity().getResources().getString(R.string.calculator_button_op_sqrt_inverse);
                    button_sqrt.setText(Html.fromHtml(getActivity().getResources()
                            .getString(R.string.calculator_button_op_sqrt_inverse)));
                }
            }
        });
    }



    /*
     ** Method designed with logic to calculate the solution. Method is called when equal button is clicked (onClick).
     */
    @SuppressLint("DefaultLocale")
    protected void onClickEqual() {
        this.empty_text_flag = fnc.checkEmptyString(this.running_result);
        this.display_result.setTextColor(getResources().getColor(R.color.black));
        this.display_result_2.setTextColor(getResources().getColor(R.color.dk_grey));
        // Proceed only if last_numeric flag is true, if there is no state error, string is not empty and parenthesis closed
        if (this.last_numeric && !this.state_error && !this.empty_text_flag && (this.open_parenthesis_flag.size() == 0)) {
//            MathExpressionBuilder build = new MathExpressionBuilder(this.running_result);  // Mathematical expression builder
            MathExpressionBuilderEvalEx build = new MathExpressionBuilderEvalEx(this.running_result);  // Mathematical expression builder
            try {
                BigDecimal resultFull = build.getExpressionResult();  // Evaluating expression built
//                BigDecimal resultFull = BigDecimal.valueOf(build.getExpressionResult());  // Evaluating expression built
                if(resultFull.remainder(new BigDecimal("1")).equals(new BigDecimal("0"))
                        && String.valueOf(resultFull).length() <= MAXIMUM_NUMBER_LENGTH) {
                    this.display_result.setText(String.format("%.0f", resultFull));
                    this.display_result_2.setText("");
                    this.running_result = String.valueOf(resultFull);
                    this.decimal_flag = false; // Integer result does not need a decimal symbol
                } else {
                    Fraction fractional = new Fraction();  // Decimal to fraction class constructor
                    BigDecimal resultRound = BigDecimal.valueOf(fnc.roundDecimals(resultFull.doubleValue(), -1));  // Rounding result
                    this.display_result.setText(fnc.format(resultRound));  // Writing rounded result
                    String resultFraction = fractional.rational(resultFull);  // Getting result in fraction mode
//                    System.out.println("################################ resultFull=" + resultFull);
//                    System.out.println("################################ resultFraction.length()=" + resultFraction.length());
//                    System.out.println("################################ resultFraction=" + resultFraction);
                    if((resultFraction.length() - 89) < MAXIMUM_NUMBER_LENGTH) {  // Checking if result is not too long and setting it
                        this.display_result_2.setText(HtmlCompat.fromHtml(resultFraction, 0));
                        //this.display_result_2.setText(HtmlCompat.fromHtml(fractional.rational(result.doubleValue()), 0));
                    }
                    else {  // If the result fraction approximation is too large, the second display remains empty
                        this.display_result_2.setText("");
                    }
                    this.running_result = String.valueOf(resultFull);
                    this.decimal_flag = true;  // Decimal result contains a dot
                }
            } catch(ArithmeticException except01) {  // Handling exceptions
                if(except01.getMessage().equals("Division by zero!") || except01.getMessage().equals("Division by zero")) {
                    this.display_result_2.setText(getActivity().getResources()
                            .getString(R.string.calculator_error_message_zero_division));
                    this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                    this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                    this.state_error = true;
                    this.last_numeric = false;
                    this.equal_flag = true;
                }
                else {
                    System.out.println(except01.getMessage());
                    this.display_result_2.setText(getActivity().getResources()
                            .getString(R.string.calculator_error_message_generic));
                    this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                    this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                    this.state_error = true;
                    this.last_numeric = false;
                    this.equal_flag = true;
                }
            } catch(IllegalArgumentException except02) {
                switch (except02.getMessage()) {
                    case "Mismatched parentheses detected. Please check the expression":
                    case "Misplaced function separator ',' or mismatched parentheses":
                        this.display_result_2.setText(getActivity().getResources()
                                .getString(R.string.calculator_error_message_mismatch_expression));
                        this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.state_error = true;
                        this.last_numeric = false;
                        this.equal_flag = true;
                        break;
                    case "OperatorInterface is unknown for token.":
                        this.display_result_2.setText(getActivity().getResources()
                                .getString(R.string.calculator_error_message_unknown_operator));
                        this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.state_error = true;
                        this.last_numeric = false;
                        this.equal_flag = true;
                        break;
                    case "ArcSineOperandOutOfBounds":
                    case "ArcCosineOperandOutOfBounds":
                        this.display_result_2.setText(getActivity().getResources()
                                .getString(R.string.calculator_error_message_arcSine_arcCosine_op_domain));
                        this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.state_error = true;
                        this.last_numeric = false;
                        this.equal_flag = true;
                        break;
                    case "SqrtOperandOutOfBounds":
                    case "LnOperandOutOfBounds":
                    case "LogOperandOutOfBounds":
                        this.display_result_2.setText(getActivity().getResources()
                                .getString(R.string.calculator_error_message_logarithm_domain));
                        this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.state_error = true;
                        this.last_numeric = false;
                        this.equal_flag = true;
                        break;
                    case "OperandMustBeInteger":
                        this.display_result_2.setText(getActivity().getResources()
                                .getString(R.string.calculator_error_message_factorial_domain));
                        this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.state_error = true;
                        this.last_numeric = false;
                        this.equal_flag = true;
                        break;
                }
            } catch (Expression.ExpressionException except03) {
                switch (except03.getMessage()) {
                    case "Number must not be 0":
                        this.display_result_2.setText(getActivity().getResources()
                                .getString(R.string.calculator_error_message_expression_exception_1));
                        this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.state_error = true;
                        this.last_numeric = false;
                        this.equal_flag = true;
                        break;
                    case "Number must be x >= 1":
                        this.display_result_2.setText(getActivity().getResources()
                                .getString(R.string.calculator_error_message_expression_exception_domain));
                        this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.state_error = true;
                        this.last_numeric = false;
                        this.equal_flag = true;
                        break;
                    case "MAX requires at least one parameter":
                    case "MIN requires at least one parameter":
                        this.display_result_2.setText(getActivity().getResources()
                                .getString(R.string.calculator_error_message_generic));
                        this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.state_error = true;
                        this.last_numeric = false;
                        this.equal_flag = true;
                        break;
                    case "The number of added operators by this method must be at least 1":
                    case "The number of added functions by this method must be at least 1":
                    case "Mismatched parentheses":
                        this.display_result_2.setText(getActivity().getResources()
                                .getString(R.string.calculator_error_message_mismatch_expression));
                        this.display_result.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.display_result_2.setTextColor(getResources().getColor(R.color.custom_tomato_3));
                        this.state_error = true;
                        this.last_numeric = false;
                        this.equal_flag = true;
                        break;
                }
                except03.printStackTrace();
            }
        }
    }



    /*
     ** Method designed with logic to calculate the current solution for the partial mathematical expression. Method is called
     * during code's execution without the need of any specific button or operator.
     */
    @SuppressLint("DefaultLocale")
    protected void onPartialResult() {
        this.display_result.setTextColor(getResources().getColor(R.color.black));
        this.display_result_2.setTextColor(getResources().getColor(R.color.dk_grey));
        this.empty_text_flag = fnc.checkEmptyString(this.running_result);
        // If last_numeric flag is TRUE, if there is NO state error, string is NOT empty and parenthesis CLOSED
        if (this.last_numeric && !this.state_error && !this.empty_text_flag && (this.open_parenthesis_flag.size() == 0)) {
//            MathExpressionBuilder build = new MathExpressionBuilder(this.running_result);  // Mathematical expression builder
            MathExpressionBuilderEvalEx build = new MathExpressionBuilderEvalEx(this.running_result);  // Mathematical expression builder
            try {
                BigDecimal result = build.getExpressionResult();  // Evaluating expression built
//                BigDecimal result = BigDecimal.valueOf(build.getExpressionResult());  // Evaluating expression built
                result = BigDecimal.valueOf(fnc.roundDecimals(result.doubleValue(), 17));
                if(result.remainder(new BigDecimal("1")).equals(new BigDecimal("0"))
                        && String.valueOf(result).length() <= MAXIMUM_NUMBER_LENGTH) {
                    //String temp_result = String.format("%.0f", result);
                    this.display_result_2.setText(String.format("%.0f", result));
                } else {
                    this.display_result_2.setText(fnc.format(result));
                }
            } catch(ArithmeticException except01) {  // Ignoring possible exceptions without outputs or call backs
            } catch(IllegalArgumentException except02) {
                // Nothing
            }
        }
        // If last_numeric flag is FALSE, if there is NO state error, string is NOT empty, parenthesis CLOSED and the last
        // character is one of the listed operators
        else if(!this.last_numeric && !this.state_error && !this.empty_text_flag && (this.open_parenthesis_flag.size() == 0)
                && String.valueOf(this.running_result.charAt(running_result.length() - 1)).matches(".*[+\\-*/^%!]*.")) {
            String str_display = this.running_result.substring(0, this.running_result.length() - 1);  // Removing last char
//            MathExpressionBuilder build = new MathExpressionBuilder(str_display);  // Expression without last char
            MathExpressionBuilderEvalEx build = new MathExpressionBuilderEvalEx(str_display);  // Expression without last char
            try {
                BigDecimal result = build.getExpressionResult();  // Evaluating expression built
//                BigDecimal result = BigDecimal.valueOf(build.getExpressionResult());  // Evaluating expression built
                result = BigDecimal.valueOf(fnc.roundDecimals(result.doubleValue(), 17));
                if(result.remainder(new BigDecimal("1")).equals(new BigDecimal("0"))
                        && String.valueOf(result).length() <= MAXIMUM_NUMBER_LENGTH) {
                    this.display_result_2.setText(String.format("%.0f", result));
                } else {
                    this.display_result_2.setText(fnc.format(result));
                }
            } catch(ArithmeticException except01) {  // Ignoring possible exceptions without outputs or call backs
            } catch(IllegalArgumentException except02) {
                // Nothing
            }
        }
        /*else if(!this.running_result.matches(".*[\\+\\-\\/\\*\\^\\%\\!\\#]*.")) {
            this.display_result_2.setText("");
        }*/
        else if(this.running_result.equals("")) {  // If the expression String is empty, the smaller display is emptied
            this.display_result_2.setText("");
        }
        else {  // Any other case, the smaller display is emptied
            this.display_result_2.setText("");
        }
    }



    /*
    ** Method designed to programmatically change the text displayed on the power of y button when starting execution.
    * INPUT: Inflater View object
    */
    protected void onInitiate(View root){
        this.button_exp = root.findViewById(R.id.calculation_button_exponential);  // Setting text for power of y button
        String temp = getActivity().getResources().getString(R.string.calculator_button_op_exponential_2);
        button_exp.setText(HtmlCompat.fromHtml(temp, 0));
    }

}

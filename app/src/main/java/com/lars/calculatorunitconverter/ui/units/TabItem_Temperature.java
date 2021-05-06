package com.lars.calculatorunitconverter.ui.units;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lars.calculatorunitconverter.MainActivity;
import com.lars.calculatorunitconverter.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@SuppressWarnings("ConstantConditions")
public class TabItem_Temperature extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    View root_unit_temp;
    Spinner spinnerTab;
    Double input_temp_number;

    private String[] temperature_scales = new String[8];
    TextView celsius_result, fahrenheit_result, kelvin_result, rankine_result, delisle_result, newton_result, reamur_result, romer_result;
    EditText input_temp_edit;

    private final GeneralFunctionsUnits fn = new GeneralFunctionsUnits();


    /* REQUIRED EMPTY CONSTRUCTOR */
    public TabItem_Temperature() {}



    /*
    ** -> Inflate the layout for this fragment
    *
    ** -> Declaring the Spinner type object linking to the Spinner view
    *
    ** -> Creating an ArrayAdapter using the string array and the Spinner layout
    *
    ** -> If you need a Context object within your Fragment, you can call getActivity().
    *  However, be careful to call getActivity() only when the fragment is attached to an activity.
    *  When the fragment is not yet attached, or was detached during the end of its lifecycle, getActivity()
    *  will return null. 'this' will throw "incompatible types: TabItem_Temperature cannot be converted to context"
    *
    ** -> Specify the layout to use when the list of choices appears
    ** -> Apply the adapter to the spinner
    *
    ** -> Using 'getActivity().getResources().getStringArray()' to collect values from string-array declared in strings.xml
    *  file. This must be initialized at 'onCreateView', else 'getActivity()' will returning null because it will
    *  be used before fragment get attached to Activity. The array temperature_scales[] was declared outside scope with
    *  a fixed size for later initialization.
    *
    ** -> Use Spinner method 'setOnItemSelectedListener' to instantiate new
    * 'AdapterView.OnItemSelectedListener()' to enable selecting item on spinner. Then, using method
    * 'onItemSelected()', it is possible to determine the actions to be taken when a given option in the spinner
    * is selected.
    *       --> An item is selected. It is possible to retrieve the selected item using 'parent.getItemAtPosition(position)'.
    *           It is also possible to retrieve the name title of the item selected with
    *           'parent.getItemAtPosition(position).toString()'
    *
    *       --> With onNothingSelected(AdapterView<?> parent) {...}' another interface callback is created where other
    *           actions for items not selected can be defined
    *
    ** -> Bind the TextView objects and create an array of TextView objects to loops through and easy process
    *
    ** -> Switching cases and returning converted temperature values based on the 8 main converting methods
     */
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "    Temperature TabItem selected...");

        this.root_unit_temp = inflater.inflate(R.layout.fragment_tab_item_temperature, container, false);

        this.spinnerTab = this.root_unit_temp.findViewById(R.id.spinner_tabItem_temperature);

        ArrayAdapter<CharSequence> adapterArray = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_temperature_scales, android.R.layout.simple_spinner_dropdown_item);

        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTab.setAdapter(adapterArray);

        this.temperature_scales = getActivity().getResources().getStringArray(R.array.array_temperature_scales_conditionals);

        celsius_result = root_unit_temp.findViewById(R.id.temperature_celsius_result);
        fahrenheit_result = root_unit_temp.findViewById(R.id.temperature_fahrenheit_result);
        kelvin_result = root_unit_temp.findViewById(R.id.temperature_kelvin_result);
        rankine_result = root_unit_temp.findViewById(R.id.temperature_rankine_result);
        delisle_result = root_unit_temp.findViewById(R.id.temperature_delisle_result);
        newton_result = root_unit_temp.findViewById(R.id.temperature_newton_result);
        reamur_result = root_unit_temp.findViewById(R.id.temperature_reamur_result);
        romer_result = root_unit_temp.findViewById(R.id.temperature_romer_result);
        TextView[] temperature_results = new TextView[]{celsius_result, fahrenheit_result, kelvin_result,
                rankine_result, delisle_result, newton_result, reamur_result, romer_result};

        input_temp_edit = root_unit_temp.findViewById(R.id.input_temperature);


        spinnerTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "        Dropdown item selected...");

                if(position == 0) {
                    Log.d(LOG_TAG, "            Clearing values...");
                    input_temp_edit.setText("");
                    for (TextView tempV : temperature_results) {
                        tempV.setText("-");
                    }
                }
                else {

                    input_temp_number = fn.get_Input_Number(input_temp_edit);
                    setResults(position, temperature_results, input_temp_number, temperature_scales);

                    input_temp_edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            input_temp_number = fn.get_Input_Number(input_temp_edit);
                            setResults(position, temperature_results, input_temp_number, temperature_scales);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        return root_unit_temp;
    }

    

    /*
    ####################################################################################################
    ** Methods to convert the desired initial temperature to all of available temperature scales
    ####################################################################################################
    */
    private double celsius_from_to(double init_temp, @NotNull String target_temp) {
        double result = 0.0;

        if(target_temp.equals(this.temperature_scales[0])) {
            result = init_temp;  // Celsius to Celsius
        } else if(target_temp.equals(this.temperature_scales[1])) {
            result = ((init_temp * (9.0 / 5.0)) + 32.0);  // Converting Celsius to Fahrenheit
        } else if(target_temp.equals(this.temperature_scales[2])) {
            result = (init_temp + 273.15);  // Converting Celsius to Kelvin
        } else if(target_temp.equals(this.temperature_scales[3])) {
            result = ((init_temp + 273.15) * (9.0 / 5.0));  // Converting Celsius to Rankine
        } else if(target_temp.equals(this.temperature_scales[4])) {
            result = ((100.0 - init_temp) * (3.0 / 2.0));  // Converting Celsius to Delisle
        } else if(target_temp.equals(this.temperature_scales[5])) {
            result = (init_temp * (33.0 / 100.0));  // Converting Celsius to Newton
        } else if(target_temp.equals(this.temperature_scales[6])) {
            result = (init_temp * (4.0 / 5.0));  // Converting Celsius to Reaumur
        } else if(target_temp.equals(this.temperature_scales[7])) {
            result = ((init_temp * (21.0 / 40.0)) + 7.5);  // Converting Celsius to Romer
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double fahrenheit_from_to(double init_temp, @NotNull String target_temp) {
        double result = 0.0;

        if(target_temp.equals(this.temperature_scales[0])) {
            result = ((init_temp - 32.0) * (5.0 / 9.0));  // Converting Fahrenheit to Celsius
        } else if(target_temp.equals(this.temperature_scales[1])) {
            result = init_temp;  // Fahrenheit to Fahrenheit
        } else if(target_temp.equals(this.temperature_scales[2])) {
            result = ((init_temp - 32.0) * (5.0 / 9.0)) + 273.15;  // Converting Fahrenheit to Kelvin
        } else if(target_temp.equals(this.temperature_scales[3])) {
            result = (init_temp - 459.67);  // Converting Fahrenheit to Rankine
        } else if(target_temp.equals(this.temperature_scales[4])) {
            result = ((212.0 - init_temp) * (5.0 / 6.0));  // Converting Fahrenheit to Delisle
        } else if(target_temp.equals(this.temperature_scales[5])) {
            result = ((init_temp - 32.0) * (11.0 / 60.0));  // Converting Fahrenheit to Newton
        } else if(target_temp.equals(this.temperature_scales[6])) {
            result = ((init_temp - 32.0) * (4.0 / 9.0));  // Converting Fahrenheit to Reaumur
        } else if(target_temp.equals(this.temperature_scales[7])) {
            result = (((init_temp - 32.0) * (7.0 / 24.0)) + 7.5);  // Converting Fahrenheit to Romer
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double kelvin_from_to(double init_temp, @NotNull String target_temp) {
        double result = 0.0;

        if(target_temp.equals(this.temperature_scales[0])) {
            result = (init_temp - 273.15);  // Converting Kelvin to Celsius
        } else if(target_temp.equals(this.temperature_scales[1])) {
            result = ((init_temp - 273.15) * (9.0 / 5.0)) + 32.0;  // Converting Kelvin Fahrenheit
        } else if(target_temp.equals(this.temperature_scales[2])) {
            result = init_temp;  // Kelvin to Kelvin
        } else if(target_temp.equals(this.temperature_scales[3])) {
            result = (init_temp * (9.0 / 5.0));  // Converting Kelvin to Rankine
        } else if(target_temp.equals(this.temperature_scales[4])) {
            result = ((373.15 - init_temp) * (3.0 / 2.0));  // Converting Kelvin to Delisle
        } else if(target_temp.equals(this.temperature_scales[5])) {
            result = ((init_temp - 273.15) * (33.0 / 100.0));  // Converting Kelvin to Newton
        } else if(target_temp.equals(this.temperature_scales[6])) {
            result = ((init_temp - 273.15) * (4.0 / 5.0));  // Converting Kelvin to Reaumur
        } else if(target_temp.equals(this.temperature_scales[7])) {
            result = (((init_temp - 273.15) * (21.0 / 40.0)) + 7.5);  // Converting Kelvin to Romer
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double rankine_from_to(double init_temp, @NotNull String target_temp) {
        double result = 0.0;

        if(target_temp.equals(this.temperature_scales[0])) {
            result = ((init_temp - 491.67) * (5.0 / 9.0));  // Converting Rankine to Celsius
        } else if(target_temp.equals(this.temperature_scales[1])) {
            result = (init_temp - 459.67);  // Converting Rankine to Fahrenheit
        } else if(target_temp.equals(this.temperature_scales[2])) {
            result = (init_temp * (5.0 / 9.0));  // Converting Rankine to Kelvin
        } else if(target_temp.equals(this.temperature_scales[3])) {
            result = init_temp;  // Rankine to Rankine
        } else if(target_temp.equals(this.temperature_scales[4])) {
            result = ((671.67 - init_temp) * (5.0 / 6.0));  // Converting Rankine to Delisle
        } else if(target_temp.equals(this.temperature_scales[5])) {
            result = ((init_temp - 491.67) * (11.0 / 60.0));  // Converting Rankine to Newton
        } else if(target_temp.equals(this.temperature_scales[6])) {
            result = ((init_temp - 491.67) * (4.0 / 9.0));  // Converting Rankine to Reaumur
        } else if(target_temp.equals(this.temperature_scales[7])) {
            result = (((init_temp - 491.67) * (7.0 / 24.0)) + 7.5);  // Converting Rankine to Romer
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double delisle_from_to(double init_temp, @NotNull String target_temp) {
        double result = 0.0;

        if(target_temp.equals(this.temperature_scales[0])) {
            result = ((100.0 - init_temp) * (2.0 / 3.0));  // Converting Delisle to Celsius
        } else if(target_temp.equals(this.temperature_scales[1])) {
            result = ((212.0 - init_temp) * (6.0 / 5.0));  // Converting Delisle to Fahrenheit
        } else if(target_temp.equals(this.temperature_scales[2])) {
            result = ((373.15 - init_temp) * (2.0 / 3.0));  // Converting Delisle to Kelvin
        } else if(target_temp.equals(this.temperature_scales[3])) {
            result = ((671.67 - init_temp) * (6.0 / 5.0));  // Converting Delisle to Rankine
        } else if(target_temp.equals(this.temperature_scales[4])) {
            result = init_temp;  // Delisle to Delisle
        } else if(target_temp.equals(this.temperature_scales[5])) {
            result = ((33.0 - init_temp) * (11.0 / 50.0));  // Converting Delisle to Newton
        } else if(target_temp.equals(this.temperature_scales[6])) {
            result = ((80.0 - init_temp) * (8.0 / 15.0));  // Converting Delisle to Reaumur
        } else if(target_temp.equals(this.temperature_scales[7])) {
            result = ((60.0 - init_temp) * (7.0 / 20.0));  // Converting Delisle to Romer
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double newton_from_to(double init_temp, @NotNull String target_temp) {
        double result = 0.0;

        if(target_temp.equals(this.temperature_scales[0])) {
            result = (init_temp * (100.0 / 33.0));  // Converting Newton to Celsius
        } else if(target_temp.equals(this.temperature_scales[1])) {
            result = ((init_temp * (60.0 / 11.0)) + 32.0);  // Converting Newton to Fahrenheit
        } else if(target_temp.equals(this.temperature_scales[2])) {
            result = ((init_temp * (100.0 / 33.0)) + 273.15);  // Converting Newton to Kelvin
        } else if(target_temp.equals(this.temperature_scales[3])) {
            result = ((init_temp * (60.0 / 11.0)) + 491.67);  // Converting Newton to Rankine
        } else if(target_temp.equals(this.temperature_scales[4])) {
            result = ((33.0 - init_temp) * (50.0 / 11.0));  // Converting Newton to Delisle
        } else if(target_temp.equals(this.temperature_scales[5])) {
            result = init_temp;  // Newton to Newton
        } else if(target_temp.equals(this.temperature_scales[6])) {
            result = (init_temp * (80.0 / 33.0));  // Converting Newton to Reaumur
        } else if(target_temp.equals(this.temperature_scales[7])) {
            result = ((init_temp * (35.0 / 22.0)) + 7.5);  // Converting Newton to Romer
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double reaumur_from_to(double init_temp, @NotNull String target_temp) {
        double result = 0.0;

        if(target_temp.equals(this.temperature_scales[0])) {
            result = (init_temp * (5.0 / 4.0));  // Converting Reaumur to Celsius
        } else if(target_temp.equals(this.temperature_scales[1])) {
            result = ((init_temp * (9.0 / 4.0)) + 32.0);  // Converting Reaumur to Fahrenheit
        } else if(target_temp.equals(this.temperature_scales[2])) {
            result = ((init_temp * (5.0 / 4.0)) + 273.15);  // Converting Reaumur to Kelvin
        } else if(target_temp.equals(this.temperature_scales[3])) {
            result = ((init_temp * (9.0 / 4.0)) + 491.67);  // Converting Reaumur to Rankine
        } else if(target_temp.equals(this.temperature_scales[4])) {
            result = ((80.0 - init_temp) * (33.0 / 80.0));  // Converting Reaumur to Delisle
        } else if(target_temp.equals(this.temperature_scales[5])) {
            result = (init_temp * (33.0 / 80.0));  // Converting Reaumur to Newton
        } else if(target_temp.equals(this.temperature_scales[6])) {
            result = init_temp;  // Reaumur to Reaumur
        } else if(target_temp.equals(this.temperature_scales[7])) {
            result = ((init_temp * (21.0 / 32.0)) + 7.5);  // Converting Reaumur to Romer
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double romer_from_to(double init_temp, @NotNull String target_temp) {
        double result = 0.0;

        if(target_temp.equals(this.temperature_scales[0])) {
            result = ((init_temp - 7.5) * (40.0 / 21.0));  // Converting Romer to Celsius
        } else if(target_temp.equals(this.temperature_scales[1])) {
            result = (((init_temp - 7.5) * (24.0 / 7.0)) + 32.0);  // Converting Romer to Fahrenheit
        } else if(target_temp.equals(this.temperature_scales[2])) {
            result = (((init_temp - 7.5) * (40.0 / 21.0)) + 273.15);  // Converting Romer to Kelvin
        } else if(target_temp.equals(this.temperature_scales[3])) {
            result = (((init_temp - 7.5) * (24.0 / 7.0)) + 491.67);  // Converting Romer to Rankine
        } else if(target_temp.equals(this.temperature_scales[4])) {
            result = ((60.0 - init_temp) * (20.0 / 7.0));  // Converting Romer to Delisle
        } else if(target_temp.equals(this.temperature_scales[5])) {
            result = ((init_temp - 7.5) * (22.0 / 35.0));  // Converting Romer to Newton
        } else if(target_temp.equals(this.temperature_scales[6])) {
            result = ((init_temp - 7.5) * (32.0 / 21.0));  // Converting Romer to Reaumur
        } else if(target_temp.equals(this.temperature_scales[7])) {
            result = init_temp;  // Romer to Romer
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }



    /*
    ####################################################################################################
    ** Method to set the results for the converted temperature scales
    ####################################################################################################
    */
    private void setResults(int pos, TextView[] unit_rlts, Double input_numb, String[] unit_scls) {
        switch (pos) {
            // Case converting input temperature from Celsius to other temperature scales
            case 1:
                // Checking if the input temperature TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Celsius temperature...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(celsius_from_to(input_numb,
                                unit_scls[i])));
                    }
                }
                else {
                    Log.d(LOG_TAG, "            Clearing values...");
                    for(TextView textV : unit_rlts) {
                        textV.setText("-");
                    }
                }
                break;
            // Case converting input temperature from Fahrenheit to other temperature scales
            case 2:
                // Checking if the input temperature TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Fahrenheit temperature...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(fahrenheit_from_to(input_numb,
                                unit_scls[i])));
                    }
                }
                else {
                    Log.d(LOG_TAG, "            Clearing values...");
                    for(TextView textV : unit_rlts) {
                        textV.setText("-");
                    }
                }
                break;
            // Case converting input temperature from Kelvin to other temperature scales
            case 3:
                // Checking if the input temperature TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Kelvin temperature...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(kelvin_from_to(input_numb,
                                unit_scls[i])));
                    }
                }
                else {
                    Log.d(LOG_TAG, "            Clearing values...");
                    for(TextView textV : unit_rlts) {
                        textV.setText("-");
                    }
                }
                break;
            // Case converting input temperature from Rankine to other temperature scales
            case 4:
                // Checking if the input temperature TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Rankine temperature...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(rankine_from_to(input_numb,
                                unit_scls[i])));
                    }
                }
                else {
                    Log.d(LOG_TAG, "            Clearing values...");
                    for(TextView textV : unit_rlts) {
                        textV.setText("-");
                    }
                }
                break;
            // Case converting input temperature from Delisle to other temperature scales
            case 5:
                // Checking if the input temperature TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Delisle temperature...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(delisle_from_to(input_numb,
                                unit_scls[i])));
                    }
                }
                else {
                    Log.d(LOG_TAG, "            Clearing values...");
                    for(TextView textV : unit_rlts) {
                        textV.setText("-");
                    }
                }
                break;
            // Case converting input temperature from Newton to other temperature scales
            case 6:
                // Checking if the input temperature TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Newton temperature...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(newton_from_to(input_numb,
                                unit_scls[i])));
                    }
                }
                else {
                    Log.d(LOG_TAG, "            Clearing values...");
                    for(TextView textV : unit_rlts) {
                        textV.setText("-");
                    }
                }
                break;
            // Case converting input temperature from Reamur to other temperature scales
            case 7:
                // Checking if the input temperature TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Réamur temperature...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(reaumur_from_to(input_numb,
                                unit_scls[i])));
                    }
                }
                else {
                    Log.d(LOG_TAG, "            Clearing values...");
                    for(TextView textV : unit_rlts) {
                        textV.setText("-");
                    }
                }
                break;
            // Case converting input temperature from Romer to other temperature scales
            case 8:
                // Checking if the input temperature TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Romer temperature...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(romer_from_to(input_numb,
                                unit_scls[i])));
                    }
                }
                else {
                    Log.d(LOG_TAG, "            Clearing values...");
                    for(TextView textV : unit_rlts) {
                        textV.setText("-");
                    }
                }
                break;
        }
    }
}
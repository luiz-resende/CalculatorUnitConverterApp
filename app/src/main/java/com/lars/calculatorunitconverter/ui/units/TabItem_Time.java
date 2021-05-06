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


@SuppressWarnings("ConstantConditions")
public class TabItem_Time extends Fragment {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    View root_unit_time;
    Spinner spinnerTab;
    Double input_time_number;

    private String[] time_scales = new String[11];
    TextView time_nanosecond_result, time_millisecond_result, time_second_result, time_minute_result, time_hour_result,
            time_day_result, time_week_result, time_month_result, time_year_result, time_decade_result, time_eon_result;
    EditText input_time_edit;

    private final GeneralFunctionsUnits fn = new GeneralFunctionsUnits();


    /* REQUIRED EMPTY CONSTRUCTOR */
    public TabItem_Time() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "    Time TabItem selected...");

        // Inflate the layout for this fragment
        this.root_unit_time = inflater.inflate(R.layout.fragment_tab_item_time, container, false);

        this.spinnerTab = this.root_unit_time.findViewById(R.id.spinner_tabItem_time);

        ArrayAdapter<CharSequence> adapterArray = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_time_scales, android.R.layout.simple_spinner_dropdown_item);

        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTab.setAdapter(adapterArray);

        this.time_scales = getActivity().getResources().getStringArray(R.array.array_time_scales_conditionals);

        time_nanosecond_result = root_unit_time.findViewById(R.id.time_nanosecond_result);
        time_millisecond_result = root_unit_time.findViewById(R.id.time_millisecond_result);
        time_second_result = root_unit_time.findViewById(R.id.time_second_result);
        time_minute_result = root_unit_time.findViewById(R.id.time_minute_result);
        time_hour_result = root_unit_time.findViewById(R.id.time_hour_result);
        time_day_result = root_unit_time.findViewById(R.id.time_day_result);
        time_week_result = root_unit_time.findViewById(R.id.time_week_result);
        time_month_result = root_unit_time.findViewById(R.id.time_month_result);
        time_year_result = root_unit_time.findViewById(R.id.time_year_result);
        time_decade_result = root_unit_time.findViewById(R.id.time_decade_result);
        time_eon_result = root_unit_time.findViewById(R.id.time_eon_result);

        TextView[] time_results = new TextView[]{time_nanosecond_result, time_millisecond_result, time_second_result,
                time_minute_result, time_hour_result, time_day_result, time_week_result, time_month_result,
                time_year_result, time_decade_result, time_eon_result};

        input_time_edit = root_unit_time.findViewById(R.id.input_time);


        spinnerTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "        Dropdown item selected...");

                if(position == 0) {
                    Log.d(LOG_TAG, "            Clearing values...");
                    input_time_edit.setText("");
                    for (TextView tempV : time_results) {
                        tempV.setText("-");
                    }
                }
                else {

                    input_time_number = fn.get_Input_Number(input_time_edit);
                    setResults(position, time_results, input_time_number, time_scales);

                    input_time_edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            input_time_number = fn.get_Input_Number(input_time_edit);
                            setResults(position, time_results, input_time_number, time_scales);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        return root_unit_time;
    }



    /*
    ####################################################################################################
    ** Methods to convert the desired initial time to all of available time scales
    ####################################################################################################
    */
    private double nanosecond_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 1.0);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 1.0e-6);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 1.0e-9);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 1.6666666666667e-11);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 2.7777777777778e-13);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 1.1574074074074e-14);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 1.6534391534392e-15);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 3.8051750380518e-16);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 3.17097919837646e-17);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 3.17097919837646e-18);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 3.1709791983765e-26);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double millisecond_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 1.0e6);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 1.0);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 0.001);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 1.6666666666667e-5);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 2.7777777777778e-7);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 1.1574074074074e-8);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 1.6534391534392e-9);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 3.8051750380518e-10);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 3.1709791983765e-11);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 3.1705770450222e-12);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 3.1709791983765e-20);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double second_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 1.0e9);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 1.0e3);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 1.0);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 1.6666666666667e-2);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 2.7777777777778e-4);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 1.1574074074074e-5);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 1.6534391534392e-6);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 3.8051750380518e-7);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 3.1709791983765e-8);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 3.1705770450222e-9);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 3.1709791983765e-17);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double minute_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 6.0e10);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 6.0e4);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 60);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 1.0);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 1.6666666666667e-2);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 6.9444444444444e-4);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 9.9206349206349e-5);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 2.2831050228311e-5);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 1.9025875190259e-6);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 1.9025875190259E-7);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 1.9025875190259e-15);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double hour_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 3.6e12);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 3.6e6);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 3.6e3);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 60);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 1.0);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 4.1666666666667e-2);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 5.952380952381e-3);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 1.3698630136986e-3);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 1.1415525114155e-4);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 1.1415525114155e-5);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 1.1415525114155e-13);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double day_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 8.64e13);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 8.64e7);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 8.64e4);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 1440.0);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 24);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 1.0);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 0.14285714285714);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 3.2876712328767e-2);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 2.7397260273973e-3);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 2.7397260273973e-4);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 2.7397260273973e-12);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double week_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 6.048e14);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 6.048e8);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 6.048e5);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 10080.0);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 168.0);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 7.0);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 1.0);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 0.23013698630137);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 1.9178082191781e-2);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 1.9178082191781e-3);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 1.9178082191781e-11);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double month_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 2.628e15);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 2.628e9);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 2.628e6);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 4.38e4);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 730.0);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 30.416666666667);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 4.3452380952381);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 1.0);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 8.3333333333333e-2);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 8.3333333333333e-3);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 8.3333333333333e-11);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double year_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 3.1536e16);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 3.1536e10);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 3.1536e7);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 5.256e5);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 8.76e3);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 365.0);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 52.142857142857);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 12.0);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 1.0);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 1.0e-1);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 1.0e-9);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double decade_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 3.1536e17);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 3.1536e11);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 3.1536e8);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 5.256e6);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 8.76e4);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 3.65e3);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 5.2142857142857e2);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 1.20e2);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 1.0e1);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 1.0);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 1.0e-8);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double eon_from_to(double init_time, @NotNull String target_time) {
        double result = 0.0;

        if(target_time.equals(this.time_scales[0])) {
            result = (init_time * 3.1536e25);  // Converting to Nanoseconds
        } else if(target_time.equals(this.time_scales[1])) {
            result = (init_time * 3.1536e19);  // Converting to Milliseconds
        } else if(target_time.equals(this.time_scales[2])) {
            result = (init_time * 3.1536e16);  // Converting to Seconds
        } else if(target_time.equals(this.time_scales[3])) {
            result = (init_time * 5.256e14);  // Converting to Minutes
        } else if(target_time.equals(this.time_scales[4])) {
            result = (init_time * 8.76e12);  // Converting to Hours
        } else if(target_time.equals(this.time_scales[5])) {
            result = (init_time * 3.65e11);  // Converting to Days
        } else if(target_time.equals(this.time_scales[6])) {
            result = (init_time * 5.2142857142857e10);  // Converting to Weeks
        } else if(target_time.equals(this.time_scales[7])) {
            result = (init_time * 1.20e10);  // Converting to Months
        } else if(target_time.equals(this.time_scales[8])) {
            result = (init_time * 1.0e9);  // Converting to Years
        } else if(target_time.equals(this.time_scales[9])) {
            result = (init_time * 1.0e8);  // Converting to Decades
        } else if(target_time.equals(this.time_scales[10])) {
            result = (init_time * 1.0);  // Converting to Eons
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }



    /*
   ####################################################################################################
   ** Method to set the results for the converted time scales
   ####################################################################################################
   */
    private void setResults(int pos, TextView[] unit_rlts, Double input_numb, String[] unit_scls) {
        switch (pos) {
            case 1:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Nanosecond time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(nanosecond_from_to(input_numb,
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
            case 2:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Millisecond time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(millisecond_from_to(input_numb,
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
            case 3:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Second time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(second_from_to(input_numb,
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
            case 4:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Minute time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(minute_from_to(input_numb,
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
            case 5:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Hour time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(hour_from_to(input_numb,
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
            case 6:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Day time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(day_from_to(input_numb,
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
            case 7:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Week time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(week_from_to(input_numb,
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
            case 8:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Month time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(month_from_to(input_numb,
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
            case 9:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Year time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(year_from_to(input_numb,
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
            case 10:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Decade time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(decade_from_to(input_numb,
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
            case 11:
                // Checking if the input time TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Eon time...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(eon_from_to(input_numb,
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

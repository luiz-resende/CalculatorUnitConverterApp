package com.lars.calculatorunitconverter.ui.units;

import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.lars.calculatorunitconverter.MainActivity;
import com.lars.calculatorunitconverter.R;

import org.jetbrains.annotations.NotNull;


@SuppressWarnings("ConstantConditions")
public class TabItem_Speed extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    View root_unit_speed;
    Spinner spinnerTab;
    Double input_speed_number;

    private String[] speed_scales = new String[18];
    TextView speed_centimetre_second_result, speed_centimetre_minute_result, speed_centimetre_hour_result,
            speed_metre_second_result, speed_metre_minute_result, speed_metre_hour_result, speed_kilometre_second_result,
            speed_kilometre_minute_result, speed_kilometre_hour_result, speed_foot_second_result, speed_foot_minute_result,
            speed_foot_hour_result, speed_mile_second_result, speed_mile_minute_result, speed_mile_hour_result,
            speed_mach_SI_result, speed_mach_1atm_result, speed_knot_result;
    EditText input_speed_edit;

    private final GeneralFunctionsUnits fn = new GeneralFunctionsUnits();


    /* REQUIRED EMPTY CONSTRUCTOR */
    public TabItem_Speed() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "    Speed TabItem selected...");

        // Inflate the layout for this fragment
        this.root_unit_speed = inflater.inflate(R.layout.fragment_tab_item_speed, container, false);

        this.spinnerTab = this.root_unit_speed.findViewById(R.id.spinner_tabItem_speed);

        ArrayAdapter<CharSequence> adapterArray = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_speed_scales, android.R.layout.simple_spinner_dropdown_item);

        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTab.setAdapter(adapterArray);

        this.speed_scales = getActivity().getResources().getStringArray(R.array.array_speed_scales_conditionals);

        speed_centimetre_second_result = root_unit_speed.findViewById(R.id.speed_centimetre_second_result);
        speed_centimetre_minute_result = root_unit_speed.findViewById(R.id.speed_centimetre_minute_result);
        speed_centimetre_hour_result = root_unit_speed.findViewById(R.id.speed_centimetre_hour_result);
        speed_metre_second_result = root_unit_speed.findViewById(R.id.speed_metre_second_result);
        speed_metre_minute_result = root_unit_speed.findViewById(R.id.speed_metre_minute_result);
        speed_metre_hour_result = root_unit_speed.findViewById(R.id.speed_metre_hour_result);
        speed_kilometre_second_result = root_unit_speed.findViewById(R.id.speed_kilometre_second_result);
        speed_kilometre_minute_result = root_unit_speed.findViewById(R.id.speed_kilometre_minute_result);
        speed_kilometre_hour_result = root_unit_speed.findViewById(R.id.speed_kilometre_hour_result);
        speed_foot_second_result = root_unit_speed.findViewById(R.id.speed_foot_second_result);
        speed_foot_minute_result = root_unit_speed.findViewById(R.id.speed_foot_minute_result);
        speed_foot_hour_result = root_unit_speed.findViewById(R.id.speed_foot_hour_result);
        speed_mile_second_result = root_unit_speed.findViewById(R.id.speed_mile_second_result);
        speed_mile_minute_result = root_unit_speed.findViewById(R.id.speed_mile_minute_result);
        speed_mile_hour_result = root_unit_speed.findViewById(R.id.speed_mile_hour_result);
        speed_mach_SI_result = root_unit_speed.findViewById(R.id.speed_mach_SI_result);
        speed_mach_1atm_result = root_unit_speed.findViewById(R.id.speed_mach_1atm_result);
        speed_knot_result = root_unit_speed.findViewById(R.id.speed_knot_result);

        TextView[] speed_results = new TextView[]{speed_centimetre_second_result, speed_centimetre_minute_result,
                speed_centimetre_hour_result, speed_metre_second_result, speed_metre_minute_result,
                speed_metre_hour_result, speed_kilometre_second_result, speed_kilometre_minute_result,
                speed_kilometre_hour_result, speed_foot_second_result, speed_foot_minute_result,
                speed_foot_hour_result, speed_mile_second_result, speed_mile_minute_result, speed_mile_hour_result,
                speed_mach_SI_result, speed_mach_1atm_result, speed_knot_result};

        input_speed_edit = root_unit_speed.findViewById(R.id.input_speed);


        spinnerTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "        Dropdown item selected...");

                if(position == 0) {
                    Log.d(LOG_TAG, "            Clearing values...");
                    input_speed_edit.setText("");
                    for (TextView tempV : speed_results) {
                        tempV.setText("-");
                    }
                }
                else {

                    input_speed_number = fn.get_Input_Number(input_speed_edit);
                    setResults(position, speed_results, input_speed_number, speed_scales);

                    input_speed_edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            input_speed_number = fn.get_Input_Number(input_speed_edit);
                            setResults(position, speed_results, input_speed_number, speed_scales);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        return root_unit_speed;
    }



    /*
    ####################################################################################################
    ** Methods to convert the desired initial speed to all of available speed scales
    ####################################################################################################
    */
    private double centimetre_second_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 1.0);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 60.0);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 3600.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.01);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 0.6);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 36.0);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.00001);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.0006);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 0.036);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 0.03280839895013123);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 1.968503937007874);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 118.11023622047);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.0000062137119223733);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.0003728227153424004);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 0.022369362920544023);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.000033893);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.0000291036);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.019438444924406);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double centimetre_minute_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 1.0);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 60.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.00016666666666666666);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 0.01);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 0.6);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 1.6666666666666668e-7);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.00001);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 0.0006);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 0.0005468066491688539);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 0.03280839895013123);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 1.968503937007874);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 1.03561865372889e-7);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.0000062137119223733);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 0.0003728227153424004);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 5.64882902e-7);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 4.850601474e-7);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.00032397408207343);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double centimetre_hour_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 0.0002777777777777778);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 1.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.000002777777777777778);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 0.00016666666666666666);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 0.01);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 2.7777777777778e-9);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 1.6666666666666668e-7);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 0.00001);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 0.000009113444152814232);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 0.0005468066491688539);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 0.03280839895013123);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 1.7260310895481e-9);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 1.03561865372889e-7);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 0.0000062137119223733);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 9.414715033e-9);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 8.08433579e-9);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.000005399568034557236);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double metre_second_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 100.0);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 6000.0);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 360000.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 1.0);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 60.0);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 3600.0);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.001);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.06);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 3.6);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 3.280839895013123);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 196.85039370078738);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 11811.023622047);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.00062137119223733);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.03728227153424004);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 2.2369362920544025);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.0033892974);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.0029103609);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 1.9438444924406);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double metre_minute_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 1.6666666666666667);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 100.0);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 6000.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 1.0);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 60.0);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.000016666666666666667);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.001);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 0.06);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 0.05468066491688539);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 3.280839895013123);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 196.85039370078738);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.000010356186537289);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.00062137119223733);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 0.03728227153424004);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.0000564883);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.000048506);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.032397408207343);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double metre_hour_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 0.027777777777777776);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 1.6666666666666667);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 100);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.0002777777777777778);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 1.0);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 2.7777777777777776e-7);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.000016666666666666667);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 0.001);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 0.0009113444152814232);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 0.05468066491688539);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 3.280839895013123);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 1.7260310895481e-7);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.000010356186537289);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 0.00062137119223733);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 9.414715033e-7);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 8.08433579e-7);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.0005399568034557236);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double kilometre_second_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 100000.0);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 6000000.0);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 360000000.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 1000.0);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 60000.0);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 3600000.0);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 1.0);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 60.0);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 3600.0);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 3280.8398950131);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 196850.39370078742);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 11811023.622047);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.62137119223733);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 37.28227153424);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 2236.9362920544);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 3.3892974122);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 2.9103608847);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 1943.8444924406);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double kilometre_minute_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 1666.6666666666667);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 100000.0);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 6000000.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 16.666666666666668);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 1000.0);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 60000.0);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 1.0);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 60.0);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 54.680664916885);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 3280.8398950131);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 196850.39370078742);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.010356186537289);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.62137119223733);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 37.28227153424);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.0564882902);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.0485060147);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 32.397408207343);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double kilometre_hour_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 27.77777777777778);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 1666.6666666666667);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 100000.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.2777777777777778);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 16.666666666666668);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 1000.0);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.0002777777777777778);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 1.0);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 0.9113444152814232);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 54.680664916885);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 3280.8398950131);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.00017260310895481);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.010356186537289);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 0.62137119223733);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.0009414715);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.0008084336);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.5399568034557235);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double foot_second_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 30.48);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 1828.8);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 109728.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.3048);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 18.288);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 1097.28);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.0003048);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.018288);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 1.09728);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 1.0);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 60.0);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 3600.0);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.0001893939393939394);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.011363636363636364);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 0.68181818181818);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.0010330579);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.000887078);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.5924838012959);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double foot_minute_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 0.508);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 30.48);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 1828.8);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.00508);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 0.3048);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 18.288);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.00000508);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.0003048);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 0.018288);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 1.0);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 60.0);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.0000031565656565656566);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.0001893939393939394);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 0.011363636363636364);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.0000172176);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.0000147846);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.0098747300215983);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double foot_hour_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 0.0084666666666667);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 0.508);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 30.48);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.000084666666666667);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 0.00508);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 0.3048);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 8.4666666666667e-8);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.00000508);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 0.0003048);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 0.0002777777777777778);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 1.0);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 5.2609427609428e-8);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.0000031565656565656566);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 0.0001893939393939394);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 2.869605142e-7);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 2.464105549e-7);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.0001645788336933);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double mile_second_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 160934.4);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 9656064.0);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 579363840);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 1609.344);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 96560.64);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 5793638.4);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 1.609344);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 96.56064);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 5793.6384);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 5280.0);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 316800.0);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 19008000.0);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 1.0);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 60.0);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 3600.0);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 5.4545454545);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 4.6837718277);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 3128.3144708423);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double mile_minute_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 2682.24);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 160934.4);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 9656064.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 26.8224);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 1609.344);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 96560.64);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.0268224);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 1.609344);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 96.56064);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 88.0);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 5280.0);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 316800.0);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 1.0);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 60.0);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.0909090909);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.0780628638);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 52.138574514039);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double mile_hour_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 44.704);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 2682.24);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 160934.4);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.44704);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 26.8224);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 1609.344);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.00044704);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.0268224);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 1.609344);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 1.4666666666667);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 88.0);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 5280.0);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.0002777777777777778);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.016666666666666666);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 1.0);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.0015151515);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.0013010477);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 0.86897624190065);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double mach_si_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 29504.64);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 1770278.4);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 106216704.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 295.0464);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 17702.784);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 1062167.04);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.2950464);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 17.702784);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 1062.16704);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 968.0);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 58080.0);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 3484800.0);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.1833333333);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 11.0);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 660.0);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 1.0);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.8586915017);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 573.52431965);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double mach_1atm_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 34360.0);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 2061600.0);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 123696000.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 343.6);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 20616.0);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 1236960.0);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.3436);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 20.616);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 1236.96);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 1127.2965879);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 67637.795276);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 4058267.7165);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.2135031417);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 12.810188499);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 768.61130995);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 1.1645625908);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 1.0);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 667.9049676);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double knot_from_to(double init_speed, @NotNull String target_speed) {
        double result = 0.0;

        if(target_speed.equals(this.speed_scales[0])) {
            result = (init_speed * 51.444444444444);  // Converting to Centimetre per second
        } else if(target_speed.equals(this.speed_scales[1])) {
            result = (init_speed * 3086.6666666667);  // Converting to Centimetre per minute
        } else if(target_speed.equals(this.speed_scales[2])) {
            result = (init_speed * 185200.0);  // Converting to Centimetre per hour
        } else if(target_speed.equals(this.speed_scales[3])) {
            result = (init_speed * 0.51444444444444);  // Converting to Metre per second
        } else if(target_speed.equals(this.speed_scales[4])) {
            result = (init_speed * 30.866666666667);  // Converting to Metre per minute
        } else if(target_speed.equals(this.speed_scales[5])) {
            result = (init_speed * 1852.0);  // Converting to Metre per hour
        } else if(target_speed.equals(this.speed_scales[6])) {
            result = (init_speed * 0.00051444444444444);  // Converting to Kilometre per second
        } else if(target_speed.equals(this.speed_scales[7])) {
            result = (init_speed * 0.030866666666667);  // Converting to Kilometre per minute
        } else if(target_speed.equals(this.speed_scales[8])) {
            result = (init_speed * 1.852);  // Converting to Kilometre per hour
        } else if(target_speed.equals(this.speed_scales[9])) {
            result = (init_speed * 1.6878098571012);  // Converting to Foot per second
        } else if(target_speed.equals(this.speed_scales[10])) {
            result = (init_speed * 101.26859142607);  // Converting to Foot per minute
        } else if(target_speed.equals(this.speed_scales[11])) {
            result = (init_speed * 6076.1154855643);  // Converting to Foot per hour
        } else if(target_speed.equals(this.speed_scales[12])) {
            result = (init_speed * 0.00031966095778432);  // Converting to Mile per second
        } else if(target_speed.equals(this.speed_scales[13])) {
            result = (init_speed * 0.019179657467059);  // Converting to Mile per minute
        } else if(target_speed.equals(this.speed_scales[14])) {
            result = (init_speed * 1.1507794480235);  // Converting to Mile per hour
        } else if(target_speed.equals(this.speed_scales[15])) {
            result = (init_speed * 0.0017436052);  // Converting to Mach (SI)
        } else if(target_speed.equals(this.speed_scales[16])) {
            result = (init_speed * 0.001497219);  // Converting to Mach (20C, 1 atm)
        } else if(target_speed.equals(this.speed_scales[17])) {
            result = (init_speed * 1.0);  // Converting to Knot
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }



    /*
    ####################################################################################################
    ** Method to set the results for the converted speed scales
    ####################################################################################################
    */
    private void setResults(int pos, TextView[] unit_rlts, Double input_numb, String[] unit_scls) {
        switch (pos) {
            case 1:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting cm/s speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(centimetre_second_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting cm/min speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(centimetre_minute_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting cm/h speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(centimetre_hour_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting m/s speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(metre_second_from_to(input_numb,
                                unit_scls[i])));
                    }
                }
                break;
            case 5:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting m/min speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(metre_minute_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting m/h speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(metre_hour_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting km/s speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(kilometre_second_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting km/min speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(kilometre_minute_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting km/h speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(kilometre_hour_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting ft/s speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(foot_second_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting ft/min speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(foot_minute_from_to(input_numb,
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
            case 12:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting ft/h speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(foot_hour_from_to(input_numb,
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
            case 13:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting mi/s speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(mile_second_from_to(input_numb,
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
            case 14:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting mi/min speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(mile_minute_from_to(input_numb,
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
            case 15:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting mi/h speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(mile_hour_from_to(input_numb,
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
            case 16:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Mach (SI) speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(mach_si_from_to(input_numb,
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
            case 17:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Mach (20C, 1 atm) speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(mach_1atm_from_to(input_numb,
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
            case 18:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting knot speed...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(knot_from_to(input_numb,
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
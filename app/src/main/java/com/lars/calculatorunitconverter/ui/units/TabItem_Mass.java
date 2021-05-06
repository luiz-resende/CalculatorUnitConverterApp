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
public class TabItem_Mass extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    View root_unit_mass;
    Spinner spinnerTab;
    Double input_mass_number;

    private String[] mass_scales = new String[11];
    TextView mass_milligram_result, mass_gram_result, mass_kilogram_result, mass_carat_result, mass_grain_result,
            mass_ounce_result, mass_ounce_troy_result, mass_pound_result, mass_ton_si_result, mass_ton_us_result, mass_ton_uk_result;
    EditText input_mass_edit;

    private final GeneralFunctionsUnits fn = new GeneralFunctionsUnits();


    /* REQUIRED EMPTY CONSTRUCTOR */
    public TabItem_Mass() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "    Mass TabItem selected...");

        // Inflate the layout for this fragment
        this.root_unit_mass = inflater.inflate(R.layout.fragment_tab_item_mass, container, false);

        this.spinnerTab = this.root_unit_mass.findViewById(R.id.spinner_tabItem_mass);

        ArrayAdapter<CharSequence> adapterArray = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_mass_scales, android.R.layout.simple_spinner_dropdown_item);

        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTab.setAdapter(adapterArray);

        this.mass_scales = getActivity().getResources().getStringArray(R.array.array_mass_scales_conditionals);

        mass_milligram_result = root_unit_mass.findViewById(R.id.mass_milligram_result);
        mass_gram_result = root_unit_mass.findViewById(R.id.mass_gram_result);
        mass_kilogram_result = root_unit_mass.findViewById(R.id.mass_kilogram_result);
        mass_carat_result = root_unit_mass.findViewById(R.id.mass_carat_result);
        mass_grain_result = root_unit_mass.findViewById(R.id.mass_grain_result);
        mass_ounce_result = root_unit_mass.findViewById(R.id.mass_ounce_result);
        mass_ounce_troy_result = root_unit_mass.findViewById(R.id.mass_ounce_troy_result);
        mass_pound_result = root_unit_mass.findViewById(R.id.mass_pound_result);
        mass_ton_si_result = root_unit_mass.findViewById(R.id.mass_ton_si_result);
        mass_ton_us_result = root_unit_mass.findViewById(R.id.mass_ton_us_result);
        mass_ton_uk_result = root_unit_mass.findViewById(R.id.mass_ton_uk_result);

        TextView[] mass_results = new TextView[]{mass_milligram_result, mass_gram_result, mass_kilogram_result, mass_carat_result, mass_grain_result,
                mass_ounce_result, mass_ounce_troy_result, mass_pound_result, mass_ton_si_result, mass_ton_us_result, mass_ton_uk_result};

        input_mass_edit = root_unit_mass.findViewById(R.id.input_mass);


        spinnerTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "        Dropdown item selected...");

                if(position == 0) {
                    Log.d(LOG_TAG, "            Clearing values...");
                    input_mass_edit.setText("");
                    for (TextView tempV : mass_results) {
                        tempV.setText("-");
                    }
                }
                else {

                    input_mass_number = fn.get_Input_Number(input_mass_edit);
                    setResults(position, mass_results, input_mass_number, mass_scales);

                    input_mass_edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            input_mass_number = fn.get_Input_Number(input_mass_edit);
                            setResults(position, mass_results, input_mass_number, mass_scales);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        return root_unit_mass;
    }

    

    /*
    ####################################################################################################
    ** Methods to convert the desired initial mass to all of available mass scales
    ####################################################################################################
    */
    private double milligram_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 1.0);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 0.001);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 0.000001);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 0.005);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 0.015432358352941);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 0.00003527396194958);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 0.000032150746568628);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 0.0000022046226218488);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 1.0e-9);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 1.1023113109244e-9);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 9.8420652761106e-10);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double gram_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 1000.0);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 1.0);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 0.001);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 5.0);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 15.432358352941);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 0.03527396194958);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 0.032150746568628);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 0.0022046226218488);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 0.000001);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 0.0000011023113109244);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 9.8420652761106e-7);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double kilogram_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 1000000.0);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 1000.0);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 1.0);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 5000.0);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 15432.358352941);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 35.27396194958);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 32.150746568628);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 2.2046226218488);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 0.001);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 0.0011023113109244);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 0.00098420652761106);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double carat_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 200.0);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 0.2);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 0.0002);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 1.0);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 3.0864716705883);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 0.0070547923899161);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 0.0064301493137256);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 0.00044092452436976);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 2.0e-7);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 2.2046226218488e-7);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 1.9684130552221e-7);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double grain_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 64.79891);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 0.06479891);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 0.00006479891);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 0.32399455);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 1.0);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 0.002285714285714286);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 0.0020833333333333333);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 0.00014285714285714287);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 6.479891e-8);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 7.1428571428571e-8);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 6.3775510204082e-8);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double ounce_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 28349.523125);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 28.349523125);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 0.028349523125);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 141.747615625);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 437.5);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 1.0);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 0.91145833333333);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 0.0625);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 0.000028349523125);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 0.00003125);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 0.000027901785714285713);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double troy_ounce_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 31103.4768);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 31.1034768);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 0.0311034768);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 155.517384);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 480.0);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 1.0971428571429);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 1.0);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 0.068571428571429);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 0.0000311034768);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 0.000034285714285714);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 0.000030612244897959);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double pound_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 453592.37);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 453.59237);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 0.45359237);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 2267.96185);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 7000.0);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 16.0);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 14.583333333333);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 1.0);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 0.00045359237);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 0.0005);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 0.0004464285714285714);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double ton_si_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 1000000000.0);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 1000000.0);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 1000.0);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 5000000.0);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 15432358.352941);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 35273.96194958);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 32150.746568628);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 2204.6226218488);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 1.0);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 1.1023113109244);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 0.98420652761106);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double ton_us_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 907184740.0);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 907184.74);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 907.18474);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 4535923.7);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 14000000.0);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 32000.0);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 29166.666666667);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 2000.0);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 0.90718474);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 1.0);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 0.8928571428571428);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double ton_uk_from_to(double init_mass, @NotNull String target_mass) {
        double result = 0.0;

        if(target_mass.equals(this.mass_scales[0])) {
            result = (init_mass * 1016046908.8);  // Converting to Milligram
        } else if(target_mass.equals(this.mass_scales[1])) {
            result = (init_mass * 1016046.9088);  // Converting to Gram
        } else if(target_mass.equals(this.mass_scales[2])) {
            result = (init_mass * 1016.0469088);  // Converting to Kilogram
        } else if(target_mass.equals(this.mass_scales[3])) {
            result = (init_mass * 5080234.544);  // Converting to Carat
        } else if(target_mass.equals(this.mass_scales[4])) {
            result = (init_mass * 15680000.0);  // Converting to Grain
        } else if(target_mass.equals(this.mass_scales[5])) {
            result = (init_mass * 35840.0);  // Converting to Ounce
        } else if(target_mass.equals(this.mass_scales[6])) {
            result = (init_mass * 32666.666666667);  // Converting to Troy Ounce
        } else if(target_mass.equals(this.mass_scales[7])) {
            result = (init_mass * 2240.0);  // Converting to Pound
        } else if(target_mass.equals(this.mass_scales[8])) {
            result = (init_mass * 1.0160469088);  // Converting to Ton SI
        } else if(target_mass.equals(this.mass_scales[9])) {
            result = (init_mass * 1.12);  // Converting to Ton US
        } else if(target_mass.equals(this.mass_scales[10])) {
            result = (init_mass * 1.0);  // Converting to Ton UK
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }



    /*
    ####################################################################################################
    ** Method to set the results for the converted mass scales
    ####################################################################################################
    */
    private void setResults(int pos, TextView[] unit_rlts, Double input_numb, String[] unit_scls) {
        switch (pos) {
            case 1:
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Milligram mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(milligram_from_to(input_numb,
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
                    Log.d(LOG_TAG, "            Converting Gram mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(gram_from_to(input_numb,
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
                    Log.d(LOG_TAG, "            Converting Kilogram mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(kilogram_from_to(input_numb,
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
                    Log.d(LOG_TAG, "            Converting Carat mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(carat_from_to(input_numb,
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
                // Checking if the input speed TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Grain mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(grain_from_to(input_numb,
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
                    Log.d(LOG_TAG, "            Converting Ounce mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(ounce_from_to(input_numb,
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
                    Log.d(LOG_TAG, "            Converting Troy Ounce mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(troy_ounce_from_to(input_numb,
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
                    Log.d(LOG_TAG, "            Converting Pound mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(pound_from_to(input_numb,
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
                    Log.d(LOG_TAG, "            Converting Ton SI mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(ton_si_from_to(input_numb,
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
                    Log.d(LOG_TAG, "            Converting Ton US mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(ton_us_from_to(input_numb,
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
                    Log.d(LOG_TAG, "            Converting Ton UK mass...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(ton_uk_from_to(input_numb,
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
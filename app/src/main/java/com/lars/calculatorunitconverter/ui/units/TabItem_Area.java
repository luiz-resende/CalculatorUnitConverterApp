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
public class TabItem_Area extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    View root_unit_area;
    Spinner spinnerTab;
    Double input_area_number;

    private String[] area_scales = new String[10];
    TextView area_sq_millimetre_result, area_sq_centimetre_result, area_sq_metre_result, area_sq_kilometre_result,
            area_sq_inch_result, area_sq_foot_result, area_sq_yard_result, area_sq_mile_result, area_hectare_result, area_acre_result;
    EditText input_area_edit;

    private final GeneralFunctionsUnits fn = new GeneralFunctionsUnits();

    /* REQUIRED EMPTY CONSTRUCTOR */
    public TabItem_Area() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "    Area TabItem selected...");

        // Inflate the layout for this fragment
        this.root_unit_area = inflater.inflate(R.layout.fragment_tab_item_area, container, false);

        this.spinnerTab = this.root_unit_area.findViewById(R.id.spinner_tabItem_area);

        ArrayAdapter<CharSequence> adapterArray = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_area_scales, android.R.layout.simple_spinner_dropdown_item);

        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTab.setAdapter(adapterArray);

        this.area_scales = getActivity().getResources().getStringArray(R.array.array_area_scales_conditionals);

        area_sq_millimetre_result = root_unit_area.findViewById(R.id.area_sq_millimetre_result);
        area_sq_centimetre_result = root_unit_area.findViewById(R.id.area_sq_centimetre_result);
        area_sq_metre_result = root_unit_area.findViewById(R.id.area_sq_metre_result);
        area_sq_kilometre_result = root_unit_area.findViewById(R.id.area_sq_kilometre_result);
        area_sq_inch_result = root_unit_area.findViewById(R.id.area_sq_inch_result);
        area_sq_foot_result = root_unit_area.findViewById(R.id.area_sq_foot_result);
        area_sq_yard_result = root_unit_area.findViewById(R.id.area_sq_yard_result);
        area_sq_mile_result = root_unit_area.findViewById(R.id.area_sq_mile_result);
        area_hectare_result = root_unit_area.findViewById(R.id.area_hectare_result);
        area_acre_result = root_unit_area.findViewById(R.id.area_acre_result);

        TextView[] area_results = new TextView[]{area_sq_millimetre_result, area_sq_centimetre_result, area_sq_metre_result,
                area_sq_kilometre_result, area_hectare_result, area_sq_inch_result, area_sq_foot_result, area_sq_yard_result,
                area_sq_mile_result, area_acre_result};

        input_area_edit = root_unit_area.findViewById(R.id.input_area);


        spinnerTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "        Dropdown item selected...");

                if(position == 0) {
                    Log.d(LOG_TAG, "            Clearing values...");
                    input_area_edit.setText("");
                    for(TextView textV : area_results) {
                        textV.setText("-");
                    }
                }
                else {

                    input_area_number = fn.get_Input_Number(input_area_edit);
                    setResults(position, area_results, input_area_number, area_scales);

                    input_area_edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            // Do nothing
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            // Do nothing
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            input_area_number = fn.get_Input_Number(input_area_edit);
                            setResults(position, area_results, input_area_number, area_scales);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                // Do nothing
            }
        });

        return root_unit_area;
    }



    /*
    ####################################################################################################
    ** Methods to convert the desired initial area to all of available area scales
    ####################################################################################################
    */
    private double sqMilliMetre_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 1.0);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 1.0e-2);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 1.0e-6);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 1.0e-12);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 1.0e-10);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 1.5500031000062e-3);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 1.076391041671e-5);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 1.1959900463011e-6);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 3.8610215854245e-13);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 2.4710538146717e-10);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double sqCentiMetre_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 100.0);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 1.0);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 1.0e-4);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 1.0e-10);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 1.0e-8);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 1.5500031000062e-1);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 1.076391041671e-3);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 1.1959900463011e-4);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 3.8610215854245e-11);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 2.4710538146717e-8);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double sqMetre_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 1.0e6);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 1.0e4);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 1.0);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 1.0e-6);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 1.0e-4);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 1550.0031000062);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 10.76391041671);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 1.1959900463011);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 3.8610215854245e-7);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 2.4710538146717e-4);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double sqKiloMetre_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 1.0e12);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 1.0e10);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 1.0e6);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 1.0);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 1.0e2);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 1550003100.0062);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 10763910.41671);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 1195990.0463011);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 0.38610215854245);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 247.10538146717);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double hectare_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 1.0e10);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 1.0e8);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 1.0e4);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 0.01);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 1.0);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 15500031.000062);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 107639.1041671);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 11959.900463011);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 0.0038610215854245);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 2.4710538146717);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double sqInch_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 645.16);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 6.4516);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 0.00064516);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 6.4516e-10);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 6.4516e-8);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 1.0);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 0.006944444444444444);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 0.0007716049382716049);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 2.4909766860524e-10);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 1.5942250790735638e-7);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double sqFoot_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 92903.04);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 929.0304);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 0.09290304);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 9.290304e-8);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 0.000009290304);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 144.0);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 1.0);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 0.1111111111111111);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 3.5870064279155e-8);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 0.00002295684113865932);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double sqYard_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 836127.36);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 8361.2736);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 0.83612736);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 8.3612736e-7);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 0.000083612736);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 1296.0);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 9.0);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 1.0);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 3.228305785123967e-7);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 0.00020661157024793388);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double sqMile_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 2589988110336.0);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 25899881103.36);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 2589988.110336);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 2.589988110336);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 258.9988110336);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 4014489600.0);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 27878400.0);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 3097600.0);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 1.0);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 640.0);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double acre_from_to(double init_area, @NotNull String target_area) {
        double result = 0.0;

        if(target_area.equals(this.area_scales[0])) {
            result = (init_area * 4046856422.4);  // Converting to Squared Millimetre
        } else if(target_area.equals(this.area_scales[1])) {
            result = (init_area * 40468564.224);  // Converting to Squared Centimetre
        } else if(target_area.equals(this.area_scales[2])) {
            result = (init_area * 4046.8564224);  // Converting to Squared Metre
        } else if(target_area.equals(this.area_scales[3])) {
            result = (init_area * 0.0040468564224);  // Converting to Squared Kilometre
        } else if(target_area.equals(this.area_scales[4])) {
            result = (init_area * 0.40468564224);  // Converting to Hectare
        } else if(target_area.equals(this.area_scales[5])) {
            result = (init_area * 6272640.0);  // Converting to Squared Inch
        } else if(target_area.equals(this.area_scales[6])) {
            result = (init_area * 43560.0);  // Converting to Squared Foot
        } else if(target_area.equals(this.area_scales[7])) {
            result = (init_area * 4840.0);  // Converting to Squared Yard
        } else if(target_area.equals(this.area_scales[8])) {
            result = (init_area * 0.0015625);  // Converting to Squared Mile
        } else if(target_area.equals(this.area_scales[9])) {
            result = (init_area * 1.0);  // Converting to Acre
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }



    /*
    ####################################################################################################
    ** Method to set the results for the converted area scales
    ####################################################################################################
    */
    private void setResults(int pos, TextView[] unit_rlts, Double input_numb, String[] unit_scls) {
        switch(pos) {
            // Case converting input area from Sq Millimetre to other area scales
            case 1:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Sq Millimetre area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(sqMilliMetre_from_to(input_numb,
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
            // Case converting input area from Sq Centimetre to other area scales
            case 2:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Sq Centimetre area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(sqCentiMetre_from_to(input_numb,
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
            // Case converting input area from Sq Metre to other area scales
            case 3:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Sq Metre area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(sqMetre_from_to(input_numb,
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
            // Case converting input area from Sq Kilometre to other area scales
            case 4:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Sq Kilometre area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(sqKiloMetre_from_to(input_numb,
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
            // Case converting input area from Hectare to other area scales
            case 5:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Hectare area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(hectare_from_to(input_numb,
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
            // Case converting input area from Sq Inch to other area scales
            case 6:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Sq Inch area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(sqInch_from_to(input_numb,
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
            // Case converting input area from Sq Foot to other area scales
            case 7:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Sq Foot area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(sqFoot_from_to(input_numb,
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
            // Case converting input area from Sq Yard to other area scales
            case 8:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Sq Yard area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(sqYard_from_to(input_numb,
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
            // Case converting input area from Sq Mile to other area scales
            case 9:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Sq Mile area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(sqMile_from_to(input_numb,
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
            // Case converting input area from Acre to other area scales
            case 10:
                // Checking if the input area TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Acre area...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(acre_from_to(input_numb,
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
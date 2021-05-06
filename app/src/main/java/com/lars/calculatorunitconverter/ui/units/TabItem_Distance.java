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
public class TabItem_Distance extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    View root_unit_distance;
    Spinner spinnerTab;
    Double input_length_number;

    private String[] distance_scales = new String[20];
    TextView picometre_result, nanometre_result, micrometre_result, millimetre_result, centimetre_result, metre_result,
            kilometre_result, thou_result, barleycorn_result, inch_result, foot_result, yard_result, chain_result,
            furlong_result, mile_result, league_result, fathom_result, nautical_mile_result, light_year_result, parsec_result;
    EditText input_length_edit;

    private final GeneralFunctionsUnits fn = new GeneralFunctionsUnits();


    /* REQUIRED EMPTY CONSTRUCTOR */
    public TabItem_Distance() {}



    @Override
    public View onCreateView( @NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "    Distance/Length TabItem selected...");

        // Inflate the layout for this fragment
        this.root_unit_distance = inflater.inflate(R.layout.fragment_tab_item_distance, container, false);

        this.spinnerTab = this.root_unit_distance.findViewById(R.id.spinner_tabItem_distance);

        ArrayAdapter<CharSequence> adapterArray = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_distance_scales, android.R.layout.simple_spinner_dropdown_item);

        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTab.setAdapter(adapterArray);

        this.distance_scales = getActivity().getResources().getStringArray(R.array.array_distance_scales_conditionals);

        picometre_result = root_unit_distance.findViewById(R.id.distance_picometre_result);
        nanometre_result = root_unit_distance.findViewById(R.id.distance_nanometre_result);
        micrometre_result = root_unit_distance.findViewById(R.id.distance_micrometre_result);
        millimetre_result = root_unit_distance.findViewById(R.id.distance_millimetre_result);
        centimetre_result = root_unit_distance.findViewById(R.id.distance_centimetre_result);
        metre_result = root_unit_distance.findViewById(R.id.distance_metre_result);
        kilometre_result = root_unit_distance.findViewById(R.id.distance_kilometre_result);
        thou_result = root_unit_distance.findViewById(R.id.distance_thou_result);
        barleycorn_result = root_unit_distance.findViewById(R.id.distance_barleycorn_result);
        inch_result = root_unit_distance.findViewById(R.id.distance_inch_result);
        foot_result = root_unit_distance.findViewById(R.id.distance_foot_result);
        yard_result = root_unit_distance.findViewById(R.id.distance_yard_result);
        chain_result = root_unit_distance.findViewById(R.id.distance_chain_result);
        furlong_result = root_unit_distance.findViewById(R.id.distance_furlong_result);
        mile_result = root_unit_distance.findViewById(R.id.distance_mile_result);
        league_result = root_unit_distance.findViewById(R.id.distance_league_result);
        fathom_result = root_unit_distance.findViewById(R.id.distance_fathom_result);
        nautical_mile_result = root_unit_distance.findViewById(R.id.distance_nautical_mile_result);
        light_year_result = root_unit_distance.findViewById(R.id.distance_light_year_result);
        parsec_result = root_unit_distance.findViewById(R.id.distance_parsec_result);
        TextView[] distance_results = new TextView[]{picometre_result, nanometre_result, micrometre_result,
                millimetre_result, centimetre_result, metre_result, kilometre_result, thou_result,
                barleycorn_result, inch_result, foot_result, yard_result, chain_result, furlong_result, mile_result,
                league_result, fathom_result, nautical_mile_result, light_year_result, parsec_result};

        input_length_edit = root_unit_distance.findViewById(R.id.input_distance);


        spinnerTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "        Dropdown item selected...");

                if(position == 0) {
                    Log.d(LOG_TAG, "            Clearing values...");
                    input_length_edit.setText("");
                    for (TextView textV : distance_results) {
                        textV.setText("-");
                    }
                }
                else {

                    input_length_number = fn.get_Input_Number(input_length_edit);
                    setResults(position, distance_results, input_length_number, distance_scales);

                    input_length_edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            input_length_number = fn.get_Input_Number(input_length_edit);
                            setResults(position, distance_results, input_length_number, distance_scales);
                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        return root_unit_distance;
    }



    /*
    ####################################################################################################
    ** Methods to convert the desired initial length to the available length scales
    ####################################################################################################
    */
    private double picometre_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.0);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.0e-3);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.0e-6);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 1.0e-9);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.0e-10);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.0e-12);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.0e-15);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 3.9370078740157e-8);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 1.1764705882353e-10);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 3.9370078740157e-11);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 3.2808398950131e-12);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1.0936132983377e-12);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 4.9709595959596e-14);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 4.9709695378987e-15);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 6.2137119223733e-16);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 2.0712331461429e-16);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 5.4680664916885e-13);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 5.3995680345572e-16);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.0570008340246e-28);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 3.2407792700054e-29);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double nanometre_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.0e3);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.0);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.0e-3);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 1.0e-6);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.0e-7);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.0e-9);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.0e-12);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 3.9370078740157e-5);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 1.1764705882353e-7);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 3.9370078740157e-8);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 3.2808398950131e-9);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1.0936132983377e-9);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 4.9709595959596e-11);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 4.9709695378987e-12);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 6.2137119223733e-13);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 2.0712331461429e-13);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 5.4680664916885e-10);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 5.3995680345572e-13);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.0570008340246e-25);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 3.2407792700054e-26);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double micrometre_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.0e6);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.0e3);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.0);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 1.0e-3);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.0e-4);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.0e-6);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.0e-9);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 3.9370078740157e-2);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 1.1764705882353e-4);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 3.9370078740157e-5);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 3.2808398950131e-6);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1.0936132983377e-6);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 4.9709595959596e-8);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 4.9709695378987e-9);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 6.2137119223733e-10);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 2.0712331461429e-10);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 5.4680664916885e-7);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 5.3995680345572e-10);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.0570008340246e-22);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 3.2407792700054e-23);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double millimetre_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.0e9);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.0e6);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.0e3);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 1.0);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.0e-1);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.0e-3);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.0e-6);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 39.370078740157);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 1.1764705882353e-1);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 3.9370078740157e-2);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 3.2808398950131e-3);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1.0936132983377e-3);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 4.9709595959596e-5);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 4.9709695378987e-6);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 6.2137119223733e-7);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 2.0712331461429e-7);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 5.4680664916885e-4);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 5.3995680345572e-7);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.0570008340246e-19);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 3.2407792700054e-20);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double centimetre_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.0e10);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.0e7);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.0e4);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 10.0);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.0);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.0e-2);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.0e-5);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 393.70078740157);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 1.1764705882353);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 3.9370078740157e-1);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 3.2808398950131e-2);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1.0936132983377e-2);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 4.9709595959596e-4);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 4.9709695378987e-5);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 6.2137119223733e-6);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 2.0712331461429e-6);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 5.4680664916885e-3);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 5.3995680345572e-6);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.0570008340246e-18);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 3.2407792700054e-19);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double metre_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.0e12);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.0e9);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.0e6);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 1.0e3);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.0e2);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.0);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.0e-3);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 39370.078740157);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 117.64705882353);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 39.370078740157);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 3.2808398950131);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1.0936132983377);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 4.9709595959596e-2);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 4.9709695378987e-3);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 6.2137119223733e-4);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 2.0712331461429e-4);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 5.4680664916885e-1);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 5.3995680345572e-4);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.0570008340246e-16);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 3.2407792700054e-17);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double kilometre_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.0e15);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.0e12);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.0e9);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 1.0e6);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.0e5);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.0e3);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.0);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 3.9370078740157e7);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 1.1764705882353e5);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 3.9370078740157e4);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 3.2808398950131e3);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1.0936132983377e3);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 4.9709595959596e1);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 4.9709695378987);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 6.2137119223733e-1);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 2.0712331461429e-1);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 5.4680664916885e2);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 5.3995680345572e-1);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.0570008340246e-13);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 3.2407792700054e-14);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double thou_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 2.5400000000000e7);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 2.5400000000000e4);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 2.5400000000000e1);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 2.540000000000e-2);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 2.540000000000e-3);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 2.540000000000e-5);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 2.540000000000e-8);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 1.0);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 2.9882352941176e-3);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 1.0000000000000e-3);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 8.3333333333333e-5);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 2.7777777777777e-5);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 1.2626237373737e-6);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 1.2626262626262626e-7);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 1.5782828282828e-8);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 5.2609321912029e-9);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 1.388888888888889e-5);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 1.3714902807775e-8);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 2.6847821184225e-21);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 8.2315793458137e-22);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double barleycorn_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 8.500000000e9);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 8.500000000e6);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 8.500000000e3);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 8.500000000);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 8.500000000e-1);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 8.500000000e-3);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 8.500000000e-6);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 334.64566929134);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 1.0);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 0.33464566929134);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 0.027887139107612);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 0.0092957130358705);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 0.00042253156565657);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 0.000042253241072139);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 0.0000052816551340173);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 0.0000017605481742214);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 0.0046478565179353);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 0.0000045896328293737);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 8.9845070892092e-19);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 2.7546623795046e-19);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double inch_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 2.54e10);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 2.54e7);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 2.54e4);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 2.54e1);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 2.54);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 2.54e-2);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 2.54e-5);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 1000.0);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 2.9882352941176);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 1.0);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 0.08333333333333333);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 0.027777777777777776);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 0.0012626237373737);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 0.00012626262626262626);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 0.000015782828282828283);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 0.0000052609321912029);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 0.013888888888888888);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 0.000013714902807775);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 2.6847821184225e-18);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 8.2315793458137e-19);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double foot_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 3.048e11);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 3.048e8);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 3.048e5);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 3.048e2);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 3.048e1);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 3.048e-1);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 3.048e-4);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 12000.0);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 35.858823529412);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 12.0);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 1.0);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 0.3333333333333333);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 0.015151484848485);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 0.0015151515151515152);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 0.0001893939393939394);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 0.000063131186294435);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 0.16666666666666666);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 0.0001645788336933);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 3.221738542107e-17);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 9.8778952149764e-18);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double yard_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 9.144e11);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 9.144e8);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 9.144e5);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 9.144e2);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 9.144e1);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 9.144e-1);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 9.144e-4);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 3.6000e4);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 107.57647058824);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 36.0);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 3.0);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1.0);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 0.045454454545455);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 0.004545454545454545);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 0.0005681818181818182);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 0.0001893935588833);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 0.5);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 0.00049373650107991);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 9.6652156263211e-17);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 2.9633685644929e-17);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double chain_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 2.011684023368e13);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 2.011684023368e10);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 2.011684023368e7);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 2.011684023368e4);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 2.011684023368e3);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 2.011684023368e1);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 2.011684023368e-2);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 792001.58400317);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 2366.6870863153);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 792.00158400317);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 66.000132000264);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 22.000044000088);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 1.0);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 0.1000002000004);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 0.01250002500005);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 0.004166666628766);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 11.000022000044);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 0.010862224748208);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 2.126351690494e-15);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 6.5194238807322e-16);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double furlong_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 2.01168e14);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 2.01168e11);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 2.01168e8);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 2.01168e5);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 2.01168e4);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 2.01168e2);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 2.01168e-1);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 7.920000e6);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 23666.823529412);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 7920.0);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 660.0);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 220.0);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 9.99998);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 1.0);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 0.125);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 0.041666582954327);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 110.0);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 0.10862203023758);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 2.1263474377906e-14);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 6.5194108418845e-15);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double mile_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.609344e15);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.609344e12);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.609344e9);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 1.609344e6);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.609344e5);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.609344e3);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.609344);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 63360000.0);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 189334.58823529);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 63360.0);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 5280.0);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1760.0);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 79.99984);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 8.0);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 1.0);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 0.33333266363462);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 880.0);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 0.86897624190065);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.7010779502325e-13);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 5.2155286735076e-14);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double league_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 4.8280417e15);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 4.8280417e12);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 4.8280417e9);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 4.8280417e6);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 4.8280417e5);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 4.8280417e3);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 4.8280417);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 190080381.88976);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 568004.90588235);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 190080.38188976);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 15840.031824147);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 5280.010608049);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 240.00000218308);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 24.000048218405);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 3.0000060273006);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 1.0);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 2640.0053040245);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 2.6069339632829);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 5.1032441036056e-13);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 1.5646617456082e-13);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double fathom_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.8288e12);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.8288e9);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.8288e6);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 1.8288e3);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.8288e2);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.8288);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.8288e-3);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 7.2000e4);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 215.15294117647);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 72.0);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 6.0);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 2.0);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 0.090908909090909);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 0.00909090909090909);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 0.0011363636363636363);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 0.00037878711776661);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 1.0);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 0.00098747300215983);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.9330431252642e-16);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 5.9267371289859e-17);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double nauticalMile_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 1.852e15);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 1.852e12);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 1.852e9);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 1.852e6);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 1.852e5);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 1.852e3);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 1.852);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 72913385.826772);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 217882.35294118);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 72913.385826772);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 6076.1154855643);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 2025.3718285214);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 92.062171717172);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 9.2062355841883);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 1.1507794480235);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 0.38359237866566);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 1012.6859142607);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 1.0);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.9575655446136e-13);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 6.00192320805e-14);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double lightYear_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 9.4607304725808e27);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 9.4607304725808e24);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 9.4607304725808e21);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 9.4607304725808e18);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 9.4607304725808e17);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 9.4607304725808e15);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 9.4607304725808e12);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 3.7246970364491e20);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 1.1130271144213e18);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 3.7246970364491e17);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 3.1039141970409e16);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 1.0346380656803e16);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 4.7028908927463e14);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 4.7029002985469e13);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 5.8786253731836e12);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 1.9595378541533e12);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 5.1731903284016e15);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 5.1083857843309e12);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 1.0);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 0.30660139194648);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double parsec_from_to(double init_length, @NotNull String target_length) {
        double result = 0.0;

        if(target_length.equals(this.distance_scales[0])) {
            result = (init_length * 3.0856776e28);  // Converting to picometre
        } else if(target_length.equals(this.distance_scales[1])) {
            result = (init_length * 3.0856776e25);  // Converting to nanometre
        } else if(target_length.equals(this.distance_scales[2])) {
            result = (init_length * 3.0856776e22);  // Converting to micrometre
        } else if(target_length.equals(this.distance_scales[3])) {
            result = (init_length * 3.0856776e19);  // Converting to millimetre
        } else if(target_length.equals(this.distance_scales[4])) {
            result = (init_length * 3.0856776e18);  // Converting to centimetre
        } else if(target_length.equals(this.distance_scales[5])) {
            result = (init_length * 3.0856776e16);  // Converting to metre
        } else if(target_length.equals(this.distance_scales[6])) {
            result = (init_length * 3.0856776e13);  // Converting to kilometre
        } else if(target_length.equals(this.distance_scales[7])) {
            result = (init_length * 1.2148337007874e21);  // Converting to thou
        } else if(target_length.equals(this.distance_scales[8])) {
            result = (init_length * 3.6302089411765e18);  // Converting to barleycorn
        } else if(target_length.equals(this.distance_scales[9])) {
            result = (init_length * 1.2148337007874e18);  // Converting to inch
        } else if(target_length.equals(this.distance_scales[10])) {
            result = (init_length * 1.0123614173228e17);  // Converting to foot
        } else if(target_length.equals(this.distance_scales[11])) {
            result = (init_length * 3.3745380577428e16);  // Converting to yard
        } else if(target_length.equals(this.distance_scales[12])) {
            result = (init_length * 1.5338778675758e15);  // Converting to chain
        } else if(target_length.equals(this.distance_scales[13])) {
            result = (init_length * 1.5338809353376e14);  // Converting to furlong
        } else if(target_length.equals(this.distance_scales[14])) {
            result = (init_length * 1.917351169172e13);  // Converting to mile
        } else if(target_length.equals(this.distance_scales[15])) {
            result = (init_length * 6.3911577234306e12);  // Converting to league
        } else if(target_length.equals(this.distance_scales[16])) {
            result = (init_length * 1.6872690288714e16);  // Converting to fathom
        } else if(target_length.equals(this.distance_scales[17])) {
            result = (init_length * 1.6661326133909e13);  // Converting to nautical mile
        } else if(target_length.equals(this.distance_scales[18])) {
            result = (init_length * 3.2615637967311);  // Converting to light year
        } else if(target_length.equals(this.distance_scales[19])) {
            result = (init_length * 1.0);  // Converting to parsec
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }



    /*
    ####################################################################################################
    ** Method to set the results for the converted distance scales
    ####################################################################################################
    */
    private void setResults(int pos, TextView[] unit_rlts, Double input_numb, String[] unit_scls) {
        switch(pos) {
            // Case converting input length from Picometre to other distance scales
            case 1:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Picometre length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(picometre_from_to(input_numb,
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
            // Case converting input length from Nanometre to other distance scales
            case 2:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Nanometre length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(nanometre_from_to(input_numb,
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
            // Case converting input length from Micrometre to other distance scales
            case 3:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Micrometre length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(micrometre_from_to(input_numb,
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
            // Case converting input length from Millimetre to other distance scales
            case 4:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Millimetre length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(millimetre_from_to(input_numb,
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
            // Case converting input length from Centimetre to other distance scales
            case 5:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Centimetre length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(centimetre_from_to(input_numb,
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
            // Case converting input length from Metre to other distance scales
            case 6:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Metre length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(metre_from_to(input_numb,
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
            // Case converting input length from Kilometre to other distance scales
            case 7:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Kilometre length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(kilometre_from_to(input_numb,
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
            // Case converting input length from Thou to other distance scales
            case 8:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Thou length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(thou_from_to(input_numb,
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
            // Case converting input length from Barleycorn to other distance scales
            case 9:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Barleycorn length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(barleycorn_from_to(input_numb,
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
            // Case converting input length from Inch to other distance scales
            case 10:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Inch length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(inch_from_to(input_numb,
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
            // Case converting input length from Foot to other distance scales
            case 11:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Feet length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(foot_from_to(input_numb,
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
            // Case converting input length from Yard to other distance scales
            case 12:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Yard length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(yard_from_to(input_numb,
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
            // Case converting input length from Chain to other distance scales
            case 13:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Chain length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(chain_from_to(input_numb,
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
            // Case converting input length from Furlong to other distance scales
            case 14:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Furlong length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(furlong_from_to(input_numb,
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
            // Case converting input length from Mile to other distance scales
            case 15:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Mile length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(mile_from_to(input_numb,
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
            // Case converting input length from League to other distance scales
            case 16:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting League length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(league_from_to(input_numb,
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
            // Case converting input length from Fathom to other distance scales
            case 17:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Fathom length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(fathom_from_to(input_numb,
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
            // Case converting input length from Nautical Mile to other distance scales
            case 18:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Nautical Mile length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(nauticalMile_from_to(input_numb,
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
            // Case converting input length from Light Year to other distance scales
            case 19:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Light Year length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(lightYear_from_to(input_numb,
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
            // Case converting input length from Parsec to other distance scales
            case 20:
                // Checking if the input length TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Parsec length...");
                    for (int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(parsec_from_to(input_numb,
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
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
public class TabItem_Volume extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    View root_unit_volume;
    Spinner spinnerTab;
    Double input_volume_number;

    private String[] volume_scales = new String[21];
    TextView volume_milliliter_result, volume_liter_result, volume_cubic_centimetre_result, volume_cubic_decimetre_result,
            volume_cubic_metre_result, volume_ounce_us_result, volume_ounce_uk_result, volume_gallon_us_result,
            volume_gallon_uk_result, volume_gallon_us_dry_result, volume_cubic_inch_result, volume_cubic_foot_result,
            volume_cubic_yard_result, volume_pint_us_result , volume_pint_uk_result, volume_teaspoon_result,
            volume_tablespoon_result, volume_cup_result, volume_barrel_us_result, volume_barrel_uk_result,
            volume_barrel_oil_result;
    EditText input_volume_edit;

    private final GeneralFunctionsUnits fn = new GeneralFunctionsUnits();


    /* REQUIRED EMPTY CONSTRUCTOR */
    public TabItem_Volume() { }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "    Volume TabItem selected...");

        // Inflate the layout for this fragment
        this.root_unit_volume = inflater.inflate(R.layout.fragment_tab_item_volume, container, false);

        this.spinnerTab = this.root_unit_volume.findViewById(R.id.spinner_tabItem_volume);

        ArrayAdapter<CharSequence> adapterArray = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_volume_scales, android.R.layout.simple_spinner_dropdown_item);

        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTab.setAdapter(adapterArray);

        this.volume_scales = getActivity().getResources().getStringArray(R.array.array_volume_scales_conditionals);

        volume_milliliter_result = root_unit_volume.findViewById(R.id.volume_milliliter_result);
        volume_liter_result = root_unit_volume.findViewById(R.id.volume_liter_result);
        volume_cubic_centimetre_result = root_unit_volume.findViewById(R.id.volume_cubic_centimetre_result);
        volume_cubic_decimetre_result = root_unit_volume.findViewById(R.id.volume_cubic_decimetre_result);
        volume_cubic_metre_result = root_unit_volume.findViewById(R.id.volume_cubic_metre_result);
        volume_ounce_us_result = root_unit_volume.findViewById(R.id.volume_ounce_us_result);
        volume_ounce_uk_result = root_unit_volume.findViewById(R.id.volume_ounce_uk_result);
        volume_gallon_us_result = root_unit_volume.findViewById(R.id.volume_gallon_us_result);
        volume_gallon_uk_result = root_unit_volume.findViewById(R.id.volume_gallon_uk_result);
        volume_gallon_us_dry_result = root_unit_volume.findViewById(R.id.volume_gallon_us_dry_result);
        volume_cubic_inch_result = root_unit_volume.findViewById(R.id.volume_cubic_inch_result);
        volume_cubic_foot_result = root_unit_volume.findViewById(R.id.volume_cubic_foot_result);
        volume_cubic_yard_result = root_unit_volume.findViewById(R.id.volume_cubic_yard_result);
        volume_pint_us_result = root_unit_volume.findViewById(R.id.volume_pint_us_result);
        volume_pint_uk_result = root_unit_volume.findViewById(R.id.volume_pint_uk_result);
        volume_teaspoon_result = root_unit_volume.findViewById(R.id.volume_teaspoon_result);
        volume_tablespoon_result = root_unit_volume.findViewById(R.id.volume_tablespoon_result);
        volume_cup_result = root_unit_volume.findViewById(R.id.volume_cup_result);
        volume_barrel_us_result = root_unit_volume.findViewById(R.id.volume_barrel_us_result);
        volume_barrel_uk_result = root_unit_volume.findViewById(R.id.volume_barrel_uk_result);
        volume_barrel_oil_result = root_unit_volume.findViewById(R.id.volume_barrel_oil_result);

        TextView[] volume_results = new TextView[]{volume_milliliter_result, volume_liter_result,
                volume_cubic_centimetre_result, volume_cubic_decimetre_result, volume_cubic_metre_result,
                volume_ounce_us_result, volume_ounce_uk_result, volume_gallon_us_result, volume_gallon_uk_result,
                volume_gallon_us_dry_result, volume_cubic_inch_result, volume_cubic_foot_result,
                volume_cubic_yard_result, volume_pint_us_result , volume_pint_uk_result, volume_teaspoon_result,
                volume_tablespoon_result, volume_cup_result, volume_barrel_us_result, volume_barrel_uk_result,
                volume_barrel_oil_result};

        input_volume_edit = root_unit_volume.findViewById(R.id.input_volume);


        spinnerTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "        Dropdown item selected...");

                if(position == 0) {
                    Log.d(LOG_TAG, "            Clearing values...");
                    input_volume_edit.setText("");
                    for (TextView tempV : volume_results) {
                        tempV.setText("-");
                    }
                }
                else {

                    input_volume_number = fn.get_Input_Number(input_volume_edit);
                    setResults(position, volume_results, input_volume_number, volume_scales);

                    input_volume_edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            input_volume_number = fn.get_Input_Number(input_volume_edit);
                            setResults(position, volume_results, input_volume_number, volume_scales);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        return root_unit_volume;
    }



    /*
    ####################################################################################################
    ** Methods to convert the desired initial volume to all of available volume scales
    ####################################################################################################
    */
    private double ml_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 1.0);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.001);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 1.0);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.001);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.000001);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 0.033814022558919);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 0.035195079727854);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.00026417205124156);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.0002199692482990878);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.00022702074456538);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 0.0610237438368);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.000035314666572222);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.000001307950613786);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 0.0021133764099325);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 0.0017597539863927);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 0.2);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 0.06666666666666667);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 0.004);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.0000083864143251288);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.0000061102568971969);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.0000062898107438466);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double liter_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 1000.0);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 1.0);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 1000.0);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 1.0);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.001);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 33.814022558919);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 35.195079727854);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.26417205124156);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.21996924829908776);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.22702074456538);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 61.0237438368);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.035314666572222);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.001307950613786);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 2.1133764099325);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 1.7597539863927);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 200.0);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 66.66666666666667);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 4.0);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.0083864143251288);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.0061102568971969);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.0062898107438466);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double cubic_cm_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 1.0);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.001);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 1.0);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.001);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.000001);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 0.033814022558919);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 0.035195079727854);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.00026417205124156);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.0002199692482990878);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.00022702074456538);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 0.0610237438368);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.000035314666572222);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.000001307950613786);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 0.0021133764099325);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 0.0017597539863927);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 0.2);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 0.06666666666666667);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 0.004);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.0000083864143251288);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.0000061102568971969);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.0000062898107438466);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double cubic_dm_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 1000.0);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 1.0);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 1000.0);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 1.0);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.001);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 33.814022558919);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 35.195079727854);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.26417205124156);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.21996924829908776);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.22702074456538);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 61.0237438368);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.035314666572222);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.001307950613786);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 2.1133764099325);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 1.7597539863927);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 200.0);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 66.66666666666667);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 4.0);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.0083864143251288);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.0061102568971969);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.0062898107438466);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double cubic_metre_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 1000000.0);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 1000.0);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 1000000.0);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 1000.0);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 1.0);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 33814.022558919);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 35195.079727854);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 264.17205124156);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 219.96924829909);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 227.02074456538);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 61023.7438368);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 35.314666572222);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 1.307950613786);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 2113.3764099325);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 1759.7539863927);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 200000.0);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 66666.66666666667);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 4000.0);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 8.3864143251288);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 6.1102568971969);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 6.2898107438466);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double floz_us_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 29.5735296875);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.0295735296875);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 29.5735296875);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.0295735296875);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.0000295735296875);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 1.0);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 1.0408427351856);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.0078125);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.0065052670949101);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.0067138047290828);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 1.8046875);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.0010443793402778);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.000038680716306584);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 0.0625);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 0.052042136759281);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 5.9147059375);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 1.9715686458333);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 0.11829411875);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.000248015873015873);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.0001807018637475);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.00018601190476190475);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double floz_uk_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 28.4130625);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.0284130625);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 28.4130625);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.0284130625);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.0000284130625);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 0.96075993634299);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 1.0);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.0075059370026796);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.00625);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.0064503546041328);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 1.733871447619);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.0010033978284832);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.000037162882536415);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 0.060047496021437);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 0.05);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 5.6826125);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 1.8942041666667);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 0.11365225);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.00023828371437078);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.00017361111111111112);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.00017871278577809);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double gallon_us_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 3785.4118);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 3.7854118);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 3785.4118);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 3.7854118);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.0037854118);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 128.0);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 133.22787010376);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 1.0);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.8326741881485);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.85936700532259);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 231.0);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.13368055555556);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.0049511316872428);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 8.0);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 6.661393505188);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 757.08236);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 252.36078666667);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 15.1416472);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.031746031746031744);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.02312983855968);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.023809523809523808);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double gallon_uk_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 4546.09);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 4.54609);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 4546.09);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 4.54609);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.00454609);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 153.72158981488);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 160.0);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 1.2009499204287);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 1.0);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 1.0320567366612);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 277.41943161904);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.16054365255731);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.0059460612058264);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 9.6075993634299);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 8.0);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 909.218);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 303.07266666667);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 18.18436);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.038125394299325);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.027777777777777776);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.028594045724494);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double gallon_dry_us_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 4404.8838);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 4.4048838);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 4404.8838);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 4.4048838);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.0044048838);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 148.94684018262);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 155.03023653293);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 1.1636471889267);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.96893897833083);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 1.0);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 268.80250064207);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.15555700268638);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.005761370469866);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 9.3091775114137);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 7.7515118266466);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 880.97676);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 293.65892);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 17.6195352);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.036941180600848);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.026914971620301);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.027705885450636);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double cubic_in_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 16.387064069264);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.016387064069264);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 16.387064069264);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.016387064069264);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.000016387064069264);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 0.55411255411255);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 0.5767440264232);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.004329004329004329);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.003604650165145);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.003720203486245);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 1.0);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.0005787037037037037);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.00002143347050754458);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 0.03463203463203463);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 0.02883720132116);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 3.2774128138528);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 1.0924709379509);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 0.065548256277056);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.0001374287088572803);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.00010012917125403);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.00010307153164296021);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double cubic_ft_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 28316.846711688);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 28.316846711688);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 28316.846711688);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 28.316846711688);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.028316846711688);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 957.50649350649);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 996.61367765929);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 7.4805194805195);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 6.2288354853706);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 6.4285116242313);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 1728.0);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 1.0);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.037037037037037035);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 59.844155844156);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 49.830683882965);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 5663.3693423377);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 1887.7897807792);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 113.26738684675);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.23747680890538);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.17302320792696);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.17810760667904);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double cubic_yd_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 764554.86121558);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 764.55486121558);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 764554.86121558);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 764.55486121558);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.76455486121558);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 25852.675324675);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 26908.569296801);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 201.97402597403);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 168.17855810501);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 173.56981385425);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 46656.0);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 27.0);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 1.0);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 1615.7922077922);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 1345.42846484);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 152910.97224312);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 50970.324081039);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 3058.2194448623);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 6.4118738404453);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 4.6716266140279);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 4.808905380334);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double pint_us_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 473.176475);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.473176475);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 473.176475);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.473176475);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.000473176475);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 16.0);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 16.65348376297);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.125);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.10408427351856);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.10742087566532);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 28.875);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.016710069444444);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.00061889146090535);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 1.0);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 0.8326741881485);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 94.635295);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 31.545098333333);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 1.8927059);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.003968253968253968);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.0028912298199601);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.002976190476190476);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double pint_uk_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 568.26125);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.56826125);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 568.26125);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.56826125);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.00056826125);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 19.21519872686);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 20.0);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.15011874005359);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.125);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.12900709208266);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 34.67742895238);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.020067956569664);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.0007432576507283);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 1.2009499204287);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 1.0);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 113.65225);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 37.884083333333);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 2.273045);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.0047656742874156);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.003472222222222222);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.0035742557155617);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double tsp_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 5.0);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.005);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 5.0);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.005);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.000005);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 0.1690701127946);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 0.17597539863927);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.0013208602562078);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.001099846241495439);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.0011351037228269);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 0.305118719184);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.00017657333286111);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.00000653975306893);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 0.010566882049662);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 0.0087987699319635);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 1.0);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 0.3333333333333333);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 0.02);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.000041932071625644);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.000030551284485984);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.000031449053719233);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double tbs_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 15.0);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.015);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 15.0);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.015);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.000015);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 0.50721033838379);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 0.52792619591781);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.0039625807686234);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.0032995387244863);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.0034053111684808);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 0.915356157552);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.00052971999858333);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.00001961925920679);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 0.031700646148987);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 0.026396309795891);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 3.0);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 1.0);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 0.06);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.00012579621487693);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.000091653853457953);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.000094347161157699);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double cup_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 250.0);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 0.25);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 250.0);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 0.25);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.00025);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 8.4535056397299);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 8.7987699319635);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 0.06604301281039);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 0.054992312074772);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 0.056755186141346);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 15.2559359592);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 0.0088286666430556);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.0003269876534465);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 0.52834410248312);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 0.43993849659818);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 50.0);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 16.666666666666668);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 1.0);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 0.0020966035812822);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.0015275642242992);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.0015724526859617);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double bbl_us_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 119240.4717);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 119.2404717);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 119240.4717);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 119.2404717);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.1192404717);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 4032.0);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 4196.6779082684);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 31.5);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 26.229236926678);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 27.070060667662);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 7276.5);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 4.2109375);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.15596064814815);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 252.0);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 209.83389541342);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 23848.09434);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 7949.36478);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 476.9618868);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 1.0);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.72858991462993);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 0.75);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double bbl_uk_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 163659.24);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 163.65924);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 163659.24);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 163.65924);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.16365924);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 5533.9772333356);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 5760.0);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 43.234197135435);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 36.0);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 37.154042519805);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 9987.0995382854);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 5.7795714920633);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.21405820340975);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 345.87357708348);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 288.0);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 32731.848);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 10910.616);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 654.63696);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 1.3725141947757);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 1.0);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 1.0293856460818);  // Converting to Barrel Oil
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double bbl_oil_from_to(double init_volume, @NotNull String target_volume) {
        double result = 0.0;

        if(target_volume.equals(this.volume_scales[0])) {
            result = (init_volume * 158987.2956);  // Converting to Milliliter
        } else if(target_volume.equals(this.volume_scales[1])) {
            result = (init_volume * 158.9872956);  // Converting to Liter
        } else if(target_volume.equals(this.volume_scales[2])) {
            result = (init_volume * 158987.2956);  // Converting to Cubic Centimetre
        } else if(target_volume.equals(this.volume_scales[3])) {
            result = (init_volume * 158.9872956);  // Converting to Cubic Decimetre
        } else if(target_volume.equals(this.volume_scales[4])) {
            result = (init_volume * 0.1589872956);  // Converting to Cubic Metre
        } else if(target_volume.equals(this.volume_scales[5])) {
            result = (init_volume * 5376.0);  // Converting to Ounce US
        } else if(target_volume.equals(this.volume_scales[6])) {
            result = (init_volume * 5595.5705443579);  // Converting to Ounce UK
        } else if(target_volume.equals(this.volume_scales[7])) {
            result = (init_volume * 42.0);  // Converting to Gallon US
        } else if(target_volume.equals(this.volume_scales[8])) {
            result = (init_volume * 34.972315902237);  // Converting to Gallon UK
        } else if(target_volume.equals(this.volume_scales[9])) {
            result = (init_volume * 36.093414223549);  // Converting to Gallon Dry US
        } else if(target_volume.equals(this.volume_scales[10])) {
            result = (init_volume * 9702.0);  // Converting to Cubic Inch
        } else if(target_volume.equals(this.volume_scales[11])) {
            result = (init_volume * 5.6145833333333);  // Converting to Foot
        } else if(target_volume.equals(this.volume_scales[12])) {
            result = (init_volume * 0.2079475308642);  // Converting to Yard
        } else if(target_volume.equals(this.volume_scales[13])) {
            result = (init_volume * 336.0);  // Converting to Pint US
        } else if(target_volume.equals(this.volume_scales[14])) {
            result = (init_volume * 279.77852721789);  // Converting to Pint UK
        } else if(target_volume.equals(this.volume_scales[15])) {
            result = (init_volume * 31797.45912);  // Converting to Teaspoon
        } else if(target_volume.equals(this.volume_scales[16])) {
            result = (init_volume * 10599.15304);  // Converting to Tablespoon
        } else if(target_volume.equals(this.volume_scales[17])) {
            result = (init_volume * 635.9491824);  // Converting to Cup
        } else if(target_volume.equals(this.volume_scales[18])) {
            result = (init_volume * 1.3333333333333333);  // Converting to Barrel US
        } else if(target_volume.equals(this.volume_scales[19])) {
            result = (init_volume * 0.97145321950658);  // Converting to Barrel UK
        } else if(target_volume.equals(this.volume_scales[20])) {
            result = (init_volume * 1.0);  // Converting to Barrel Oil
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting milliliter volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(ml_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting liter volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(liter_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting cm3 volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(cubic_cm_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting dc3 volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(cubic_dm_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting m3 volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(cubic_metre_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting ounce US volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(floz_us_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting ounce UK volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(floz_uk_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting gallon US volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(gallon_us_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting gallon UK volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(gallon_uk_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting gallon US dry volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(gallon_dry_us_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting in3 volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(cubic_in_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting ft3 volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(cubic_ft_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting yd3 volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(cubic_yd_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting pint US volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(pint_us_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting pint UK volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(pint_uk_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting tsp volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(tsp_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting tbs volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(tbs_from_to(input_numb,
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
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting cup volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(cup_from_to(input_numb,
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
            case 19:
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting barrel US volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(bbl_us_from_to(input_numb,
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
            case 20:
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting barrel UK volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(bbl_uk_from_to(input_numb,
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
            case 21:
                // Checking if the input volume TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting barrel oil volume...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(bbl_oil_from_to(input_numb,
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
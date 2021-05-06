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
public class TabItem_Pressure extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    
    View root_unit_pressure;
    Spinner spinnerTab;
    Double input_pressure_number;

    private String[] pressure_scales = new String[11];
    TextView pressure_atmosphere_result, pressure_pascal_result, pressure_kilopascal_result, pressure_megapascal_result,
            pressure_bar_result, pressure_pound_sq_inch_result, pressure_pound_sq_foot_result,
            pressure_millimetre_mercury_result, pressure_metre_water_result, pressure_torr_result,
            pressure_kilogram_force_sq_centimetre_result;
    EditText input_pressure_edit;

    private final GeneralFunctionsUnits fn = new GeneralFunctionsUnits();


    /* REQUIRED EMPTY CONSTRUCTOR */
    public TabItem_Pressure() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "    Pressure TabItem selected...");

        // Inflate the layout for this fragment
        this.root_unit_pressure = inflater.inflate(R.layout.fragment_tab_item_pressure, container, false);

        this.spinnerTab = this.root_unit_pressure.findViewById(R.id.spinner_tabItem_pressure);

        ArrayAdapter<CharSequence> adapterArray = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_pressure_scales, android.R.layout.simple_spinner_dropdown_item);

        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTab.setAdapter(adapterArray);

        this.pressure_scales = getActivity().getResources().getStringArray(R.array.array_pressure_scales_conditionals);

        pressure_atmosphere_result = root_unit_pressure.findViewById(R.id.pressure_atmosphere_result);
        pressure_pascal_result = root_unit_pressure.findViewById(R.id.pressure_pascal_result);
        pressure_kilopascal_result = root_unit_pressure.findViewById(R.id.pressure_kilopascal_result);
        pressure_megapascal_result = root_unit_pressure.findViewById(R.id.pressure_megapascal_result);
        pressure_bar_result = root_unit_pressure.findViewById(R.id.pressure_bar_result);
        pressure_pound_sq_inch_result = root_unit_pressure.findViewById(R.id.pressure_pound_sq_inch_result);
        pressure_pound_sq_foot_result = root_unit_pressure.findViewById(R.id.pressure_pound_sq_foot_result);
        pressure_millimetre_mercury_result = root_unit_pressure.findViewById(R.id.pressure_millimetre_mercury_result);
        pressure_metre_water_result = root_unit_pressure.findViewById(R.id.pressure_metre_water_result);
        pressure_torr_result = root_unit_pressure.findViewById(R.id.pressure_torr_result);
        pressure_kilogram_force_sq_centimetre_result = root_unit_pressure.findViewById(R.id.pressure_kilogram_force_sq_centimetre_result);

        TextView[] pressure_results = new TextView[]{pressure_atmosphere_result, pressure_pascal_result,
                pressure_kilopascal_result, pressure_megapascal_result, pressure_bar_result,
                pressure_pound_sq_inch_result, pressure_pound_sq_foot_result, pressure_millimetre_mercury_result,
                pressure_metre_water_result, pressure_torr_result, pressure_kilogram_force_sq_centimetre_result};

        input_pressure_edit = root_unit_pressure.findViewById(R.id.input_pressure);


        spinnerTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "        Dropdown item selected...");

                if(position == 0) {
                    Log.d(LOG_TAG, "            Clearing values...");
                    input_pressure_edit.setText("");
                    for (TextView tempV : pressure_results) {
                        tempV.setText("-");
                    }
                }
                else {

                    input_pressure_number = fn.get_Input_Number(input_pressure_edit);
                    setResults(position, pressure_results, input_pressure_number, pressure_scales);

                    input_pressure_edit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            input_pressure_number = fn.get_Input_Number(input_pressure_edit);
                            setResults(position, pressure_results, input_pressure_number, pressure_scales);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        return root_unit_pressure;
    }



    /*
    ####################################################################################################
    ** Methods to convert the desired initial pressure to all of available pressure scales
    ####################################################################################################
    */
    private double atm_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 1.0);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 101325.0);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 101.325);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 0.101325);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 1.01325);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 14.695948775446);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 2.116218862178e3);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 760.00210017852);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 10.332559007503);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 760.000000006);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 1.0332274527999);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double pascal_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 9.8692326671601e-6);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 1.0);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 1.0e-3);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 1.0e-6);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 1.0e-5);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 1.4503773772954e-4);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 2.089e-2);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 7.5006375541921e-3);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 1.0197442889221e-4);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 7.5006168271009e-3);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 1.0197162129779e-5);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double kilopascal_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 9.8692326671601e-3);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 1.0e3);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 1.0);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 1.0e-3);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 1.0e-2);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 1.4503773772954e-1);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 2.089e1);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 7.5006375541921);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 1.0197442889221e-1);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 7.5006168271009);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 1.0197162129779e-2);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double megapascal_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 9.8692326671601);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 1.0e6);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 1.0e3);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 1.0);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 1.0e1);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 1.4503773772954e2);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 2.089e4);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 7.5006375541921e3);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 1.0197442889221e2);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 7.5006168271009e3);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 1.0197162129779e1);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double bar_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 0.98692326671601);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 1.0e5);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 1.0e2);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 1.0e-1);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 1.0);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 1.4503773772954e1);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 2.08855e3);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 7.5006375541921e2);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 1.0197442889221e1);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 7.5006168271009e2);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 1.0197162129779);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double psi_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 6.804596391019e-2);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 6.8947572932e3);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 6.8947572932);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 6.8947572932e-3);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 6.8947572932e-2);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 1.0);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 1.440e2);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 5.1715075480416e1);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 7.0308893732448e-1);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 5.1714932572153e1);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 7.0306957964239e-2);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double psf_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 4.72541e-4);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 4.7880208e1);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 4.7880208e-2);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 4.7880208e-5);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 4.78803e-4);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 6.94444e-3);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 1.0);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 0.359131);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 4.88243e-3);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 0.359131);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 4.8824311e-4);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double mmHg_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 1.3157858376511e-3);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 1.33322e2);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 1.33322e-1);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 1.33322e-4);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 1.33322e-3);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 19336721269578e-2);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 2.784499249577);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 1.0);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 1.3595434808767e-2);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 0.99999723662275);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 0.0013595060494664);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double mH2O_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 9.6781445842586e-8);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 9.80638e-3);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 9.80638e-6);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 9.80638e-9);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 9.80638e-8);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 1.4222951705162e-6);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 2.0481604446966e2);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 7.3554102098678e-5);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 1.0);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 7.3553898840946e-5);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 9.9997246766225e-8);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double torr_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 1.3157894736738e-3);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 1.3332236842e2);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 1.3332236842e-1);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 1.3332236842e-4);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 1.3332236842e-3);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 1.9336774704382e-2);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 2.7844992495775);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 1.0000027633849);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 1.3595472378186e-2);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 1.0);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 1.3595098063049e-3);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }

    private double kgf_cm_from_to(double init_pressure, @NotNull String target_pressure) {
        double result = 0.0;

        if(target_pressure.equals(this.pressure_scales[0])) {
            result = (init_pressure * 9.6784110535406e-1);  // Converting to Atmosphere
        } else if(target_pressure.equals(this.pressure_scales[1])) {
            result = (init_pressure * 9.80665e4);  // Converting to Pascal
        } else if(target_pressure.equals(this.pressure_scales[2])) {
            result = (init_pressure * 9.80665e1);  // Converting to Kilopascal
        } else if(target_pressure.equals(this.pressure_scales[3])) {
            result = (init_pressure * 9.80665e-2);  // Converting to Megapascal
        } else if(target_pressure.equals(this.pressure_scales[4])) {
            result = (init_pressure * 9.80665e-1);  // Converting to Bar
        } else if(target_pressure.equals(this.pressure_scales[5])) {
            result = (init_pressure * 1.4223343307054e1);  // Converting to PSI
        } else if(target_pressure.equals(this.pressure_scales[6])) {
            result = (init_pressure * 2.048163602741e3);  // Converting to PSF
        } else if(target_pressure.equals(this.pressure_scales[7])) {
            result = (init_pressure * 7.3556127270818e2);  // Converting to mmHg
        } else if(target_pressure.equals(this.pressure_scales[8])) {
            result = (init_pressure * 1.0000275330958e1);  // Converting to mH2O
        } else if(target_pressure.equals(this.pressure_scales[9])) {
            result = (init_pressure * 7.3555924007489e2);  // Converting to Torr
        } else if(target_pressure.equals(this.pressure_scales[10])) {
            result = (init_pressure * 1.0);  // Converting to kgf/cm2
        }

        return fn.roundDecimals(result);  // Rounding the result and returning it
    }



    /*
    ####################################################################################################
    ** Method to set the results for the converted pressure scales
    ####################################################################################################
    */
    private void setResults(int pos, TextView[] unit_rlts, Double input_numb, String[] unit_scls) {
        switch (pos) {
            case 1:
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting Atm pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(atm_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting pascal pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(pascal_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting kilopascal pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(kilopascal_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting megapascal pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(megapascal_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting bar pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(bar_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting psi pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(psi_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting psf pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(psf_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting mmHg pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(mmHg_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting meter water pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(mH2O_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting torr pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(torr_from_to(input_numb,
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
                // Checking if the input pressure TextView is not null
                if (input_numb != null) {
                    Log.d(LOG_TAG, "            Converting kgf/cm2 pressure...");
                    for(int i = 0; i < unit_rlts.length; i++) {
                        unit_rlts[i].setText(fn.formatNumber(kgf_cm_from_to(input_numb,
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
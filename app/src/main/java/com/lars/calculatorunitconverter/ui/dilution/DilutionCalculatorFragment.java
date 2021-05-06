package com.lars.calculatorunitconverter.ui.dilution;

import android.annotation.SuppressLint;
//import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lars.calculatorunitconverter.MainActivity;
import com.lars.calculatorunitconverter.R;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@SuppressWarnings({"ConstantConditions", "FieldCanBeLocal", "RedundantSuppression", "Convert2Lambda", "UnnecessaryCallToStringValueOf", "UnnecessaryLocalVariable", "IfStatementWithIdenticalBranches"})
public class DilutionCalculatorFragment extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /* View object inflater */
    private View root_dilution;

    private final int NUMBER_OF_DECIMALS = 2;


    /* EMPTY CONSTRUCTOR */
    public DilutionCalculatorFragment() {}



    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(LOG_TAG, "Dilution calculator menu selected...");

        root_dilution = inflater.inflate(R.layout.fragment_dilution, container, false);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switch_btn_volume = root_dilution.findViewById(R.id.dilution_switch_btn_init_final_volume);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switch_btn_solvent = root_dilution.findViewById(R.id.dilution_switch_btn_solvent_ingredient);
        Button solution = root_dilution.findViewById(R.id.dilution_solve_btn);
        Button clear = root_dilution.findViewById(R.id.dilution_clear_btn);
        EditText initialVolume = root_dilution.findViewById(R.id.dilution_textView_init_volume);
        EditText initialConcentration = root_dilution.findViewById(R.id.dilution_textView_init_concen);
        EditText finalConcentration = root_dilution.findViewById(R.id.dilution_textView_final_concen);
        TextView result_add_vol = root_dilution.findViewById(R.id.dilution_result_dilution);
        TextView result_tot_vol = root_dilution.findViewById(R.id.dilution_result_total_volume);
        TextView initial_volume_text = root_dilution.findViewById(R.id.dilution_textView_01);

        boolean state_of_switch = switch_btn_volume.isChecked();

        if(!state_of_switch) {
            Log.d(LOG_TAG, "Switch button volume set to 'FALSE'");

            initial_volume_text.setText(getActivity().getResources().getString(R.string.dilution_vol_initial));
            result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_result));
            result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_total));

            onCheckedSolventSwitch(switch_btn_solvent);

            solution.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    Log.d(LOG_TAG, "Calculation button clicked!");

                    ArrayList<Double> numbers = readNumbers(initialVolume, initialConcentration, finalConcentration, switch_btn_solvent.isChecked());
                    Log.d(LOG_TAG, "Numbers read!");

                    boolean flag = testNumbers(numbers);
                    Log.d(LOG_TAG, "Numbers tested!");

                    if (flag) {

                        double result_dilution = roundDecimals(get_dilution_formula(numbers));
                        double result_total = roundDecimals((result_dilution + numbers.get(0)));

                        //result_add_vol.setTextColor(Color.GREEN);
                        //noinspection UnnecessaryCallToStringValueOf
                        if(switch_btn_solvent.isChecked()) {
                            result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_1)
                                    + " "
                                    + String.valueOf(result_dilution)
                                    + " "
                                    + getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_5));
                        }
                        else {
                            result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_1)
                                    + " "
                                    + String.valueOf(result_dilution)
                                    + " "
                                    + getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_2));
                        }
                        //noinspection UnnecessaryCallToStringValueOf
                        result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_3)
                                + " "
                                + String.valueOf(result_total)
                                + " "
                                + getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_4));
                    } else {
                        //result.setTextColor(Color.RED);
                        //result.setText("FILL VALUES ABOVE");
                        Log.d(LOG_TAG, "    APPLICATION ACTIVITY RESTARTING! Please, fill all field values above!");
                    }
                }
            });

            clear.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    Log.d(LOG_TAG, "Clear button clicked!");

                    initialVolume.setText("");
                    initialConcentration.setText("");
                    finalConcentration.setText("");
                    result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_result));
                    result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_total));

                    Log.d(LOG_TAG, "    Information erased!");

                }
            });
        }

        switch_btn_volume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    Log.d(LOG_TAG, "Switch button set to 'FALSE'");

                    initial_volume_text.setText(getActivity().getResources().getString(R.string.dilution_vol_initial));
                    result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_result));
                    result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_total));

                    onCheckedSolventSwitch(switch_btn_solvent);

                    //noinspection Convert2Lambda
                    solution.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            Log.d(LOG_TAG, "Calculation button clicked!");

                            ArrayList<Double> numbers = readNumbers(initialVolume, initialConcentration, finalConcentration, switch_btn_solvent.isChecked());
                            Log.d(LOG_TAG, "Numbers read!");

                            boolean flag = testNumbers(numbers);
                            Log.d(LOG_TAG, "Numbers tested!");

                            if (flag) {

                                double result_dilution = roundDecimals(get_dilution_formula(numbers));
                                double result_total = roundDecimals((result_dilution + numbers.get(0)));

                                //result_add_vol.setTextColor(Color.GREEN);
                                if(switch_btn_solvent.isChecked()) {
                                    result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_1)
                                            + " "
                                            + String.valueOf(result_dilution)
                                            + " "
                                            + getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_5));
                                }
                                else {
                                    result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_1)
                                            + " "
                                            + String.valueOf(result_dilution)
                                            + " "
                                            + getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_2));
                                }
                                result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_3)
                                        + " "
                                        + String.valueOf(result_total)
                                        + " "
                                        + getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_false_4));
                            } else {
                                //result.setTextColor(Color.RED);
                                //result.setText("FILL VALUES ABOVE");
                                Log.d(LOG_TAG, "    APPLICATION ACTIVITY RESTARTING! Please, fill all field values above!");
                            }
                        }
                    });

                    clear.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            Log.d(LOG_TAG, "Clear button clicked!");

                            initialVolume.setText("");
                            initialConcentration.setText("");
                            finalConcentration.setText("");
                            result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_result));
                            result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_total));

                            Log.d(LOG_TAG, "    Information erased!");

                        }
                    });
                }
                else {
                    Log.d(LOG_TAG, "Switch button set to 'TRUE'");

                    initial_volume_text.setText(getActivity().getResources().getString(R.string.dilution_vol_initial_switch_true));
                    result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_result_switch_true));
                    result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_total_switch_true));

                    onCheckedSolventSwitch(switch_btn_solvent);

                    solution.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            Log.d(LOG_TAG, "Calculation button clicked!");

                            ArrayList<Double> numbers = readNumbers(initialVolume, initialConcentration, finalConcentration, switch_btn_solvent.isChecked());
                            Log.d(LOG_TAG, "Numbers read!");

                            boolean flag = testNumbers(numbers);
                            Log.d(LOG_TAG, "Numbers tested!");

                            if (flag) {
                                double result_dilution = roundDecimals(get_dilution_formula_final(numbers));
                                double result_total = roundDecimals((numbers.get(0) - result_dilution));

                                result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_true_1)
                                        + " "
                                        + String.valueOf(result_dilution)
                                        + " "
                                        + getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_true_2));
                                if(switch_btn_solvent.isChecked()) {
                                    result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_true_3)
                                            + " "
                                            + String.valueOf(result_total)
                                            + " "
                                            + getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_true_5));
                                }
                                else {
                                    result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_true_3)
                                            + " "
                                            + String.valueOf(result_total)
                                            + " "
                                            + getActivity().getResources().getString(R.string.dilution_vol_display_result_switch_true_4));
                                }
                            } else {
                                //result.setTextColor(Color.RED);
                                //result.setText("FILL VALUES ABOVE");
                                Log.d(LOG_TAG, "    APPLICATION ACTIVITY RESTARTING! Please, fill all field values above!");
                            }
                        }
                    });

                    clear.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            Log.d(LOG_TAG, "Clear button clicked!");

                            initialVolume.setText("");
                            initialConcentration.setText("");
                            finalConcentration.setText("");
                            result_add_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_result_switch_true));
                            result_tot_vol.setText(getActivity().getResources().getString(R.string.dilution_vol_total_switch_true));

                            Log.d(LOG_TAG, "    Information erased!");
                        }
                    });
                }
            }
        });


        return root_dilution;
    }



    /* Method to read numbers and crete ArrayList with values */
    private ArrayList<Double> readNumbers(EditText initVol, EditText initCon, EditText finalCon, boolean isIngredient) {

        ArrayList<Double> numbers = new ArrayList<>();

        Log.d(LOG_TAG, "    Reading numbers...");

        String temp_string = initVol.getText().toString();
        Double temp_number = checkTextView(temp_string);
        numbers.add(temp_number);

        temp_string = initCon.getText().toString();
        temp_number = convertPercent(checkTextView(temp_string));
        if(temp_number != null && isIngredient) {
            numbers.add(1.0 - temp_number);
        }
        else if(temp_number != null && !isIngredient) {
            numbers.add(temp_number);
        }
        else {
            numbers.add(temp_number);
        }

        temp_string = finalCon.getText().toString();
        temp_number = convertPercent(checkTextView(temp_string));
        if(temp_number != null && isIngredient) {
            numbers.add(1.0 - temp_number);
        }
        else if(temp_number != null && !isIngredient) {
            numbers.add(temp_number);
        }
        else {
            numbers.add(temp_number);
        }

        return numbers;
    }



    /* Method to test numbers and check with any field is empty */
    private boolean testNumbers(ArrayList<Double> numbers) {

        Log.d(LOG_TAG, "    Testing ArrayList of numbers...");

        if (numbers.get(0) != null && numbers.get(1) != null && numbers.get(2) != null) {
            Log.d(LOG_TAG, "        ALL FIELDS COMPLETED!");
            return true;
        } else {
            Log.d(LOG_TAG, "        ONE OR MORE FIELDS LEFT EMPTY!");
            return false;
        }
    }



    /* Method to check if passed strings are empty (handle NullPointer) */
    private Double checkTextView(String str) {

        if(str != null && !str.trim().isEmpty()) {
            Log.d(LOG_TAG, "        Reading TextView typed number...");
            return Double.parseDouble(str);
        }
        else {
            Log.d(LOG_TAG, "        Empty string/null variable found!");
            return null;
        }
    }



    /* Method to convert percentages to absolute values */
    private Double convertPercent(Double var) {

        if(var == null) {
            var = null;
        }
        else if(var != null && var > 1.0) {
            var = (var / 100.00);
            Log.d(LOG_TAG, "        Converting percentage to decimal number...");
        }
        else if(var != null && var <= 1.0) {
            Log.d(LOG_TAG, "        Maintaining decimal number typed...");
        }
        return var;
    }



    /* Method to round result by a given number of decimals */
    private double roundDecimals(@NotNull Double number) {
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



    /* Method to calculate dilution using initial solution volume */
    private double get_dilution_formula(ArrayList<Double> numbs) {
        double result = (((numbs.get(0) * numbs.get(1)) / numbs.get(2)) - numbs.get(0));
        return result;
    }



    /* Method to calculate dilution using final solution volume */
    private double get_dilution_formula_final(ArrayList<Double> numbs) {
        double result = (((numbs.get(0) * numbs.get(2)) / numbs.get(1)));
        return result;
    }



    /* Method to handle and follow setOnCheckedChangeListener for the solvent switch button */
    private void onCheckedSolventSwitch(@SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchBtn) {
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchBtn.isChecked()) {
                    switchBtn.setText(getActivity().getResources().getString(R.string.dilution_switch_button_solvent_text_2));
                }
                else {
                    switchBtn.setText(getActivity().getResources().getString(R.string.dilution_switch_button_solvent_text_1));
                }
            }
        });
    }

}

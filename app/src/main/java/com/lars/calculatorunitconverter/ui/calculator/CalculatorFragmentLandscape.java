package com.lars.calculatorunitconverter.ui.calculator;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.lars.calculatorunitconverter.R;


@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "ConstantConditions", "Convert2Lambda", "RedundantSuppression"})
public class CalculatorFragmentLandscape extends CalculatorFragment {

    private View root_calculator_land;


    public CalculatorFragmentLandscape() {
        // Empty constructor
    }


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Calculator landscape mode selected...");

        this.root_calculator_land = inflater.inflate(R.layout.fragment_calculator_landscape, container, false);


        // TextView displays
        this.display_result = root_calculator_land.findViewById(R.id.calculator_textView_display_input_finalResult);
        //this.display_result.setSelected(true);
        this.display_result_2 = root_calculator_land.findViewById(R.id.calculator_textView_display_current_result);
        this.display_degree = root_calculator_land.findViewById(R.id.calculator_textView_display_trigonometry);


        this.calculator_buttons_operators_text = getActivity().getResources()
                .getStringArray(R.array.calculator_operator_buttons_array);

        this.calculator_buttons_operators_hidden_text = getActivity().getResources()
                .getStringArray(R.array.calculator_operator_buttons_hidden_array);


        onInitiate(root_calculator_land);
        // Find and set OnClickListener to numeric buttons
        setNumericOnClickListener(root_calculator_land);
        // Find and set OnClickListener to operator buttons, equal button and decimal point button
        setOperatorOnClickListener(root_calculator_land);
        // Find and set OnClickListener to hidden panel buttons
        setOperatorHiddenOnClickListener(root_calculator_land);

        return root_calculator_land;
    }




    /**
     ** Methods to deleted last entry on string
     **/
    @SuppressWarnings("unused")
    private String delete_character_display(String smaller_str) {
        if (smaller_str != null && smaller_str.length() > 0) {  // && smaller_str.charAt(str.length() - 1) == 'x'
            if(smaller_str.charAt(smaller_str.length() - 1) == ".".charAt(0)) {
                this.decimal_flag = false;
            }
            smaller_str = smaller_str.substring(0, smaller_str.length() - 1);  // Copying String minus last character
        }
        return smaller_str;
    }

}

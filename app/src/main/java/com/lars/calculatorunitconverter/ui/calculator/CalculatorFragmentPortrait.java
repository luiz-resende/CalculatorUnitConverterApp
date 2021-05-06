package com.lars.calculatorunitconverter.ui.calculator;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.lars.calculatorunitconverter.R;

import org.jetbrains.annotations.NotNull;


@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "ConstantConditions", "Convert2Lambda", "RedundantSuppression"})
public class CalculatorFragmentPortrait extends CalculatorFragment {

    private View root_calculator_port;
    SlidingPaneLayout sliding_panel;


    public CalculatorFragmentPortrait() {
        // Empty constructor
    }


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Calculator portrait mode selected...");

        this.root_calculator_port = inflater.inflate(R.layout.fragment_calculator_portrait, container, false);

        // Button hide panel
        this.button_hide = root_calculator_port.findViewById(R.id.calculator_button_hide_panel);

        this.button_exp = root_calculator_port.findViewById(R.id.calculation_button_exponential);


        // TextView displays
        this.display_result= root_calculator_port.findViewById(R.id.calculator_textView_display_input_finalResult);
        //this.display_result.setSelected(true);
        this.display_result_2 = root_calculator_port.findViewById(R.id.calculator_textView_display_current_result);
        this.display_degree = root_calculator_port.findViewById(R.id.calculator_textView_display_trigonometry);


        this.calculator_buttons_operators_text = getActivity().getResources()
                .getStringArray(R.array.calculator_operator_buttons_array);

        this.calculator_buttons_operators_hidden_text = getActivity().getResources()
                .getStringArray(R.array.calculator_operator_buttons_hidden_array);


        // Sliding panel layout
        this.sliding_panel = root_calculator_port.findViewById(R.id.calculator_layout_sliding_panel);
        if(sliding_panel.isOpen()) {
            sliding_panel.openPane();
        }
        sliding_panel.setPanelSlideListener(new CalculatorFragmentPortrait.PaneListener());

        //noinspection Convert2Lambda
        button_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliding_panel.openPane();  // Button to hide panel with extra operators
            }
        });


        onInitiate(root_calculator_port);
        // Find and set OnClickListener to numeric buttons
        setNumericOnClickListener(root_calculator_port);
        // Find and set OnClickListener to operator buttons, equal button and decimal point button
        setOperatorOnClickListener(root_calculator_port);
        // Find and set OnClickListener to hidden panel buttons
        setOperatorHiddenOnClickListener(root_calculator_port);


        return root_calculator_port;
    }




    /*
     ** Method for sliding panel with hidden buttons
     **/
    private class PaneListener implements SlidingPaneLayout.PanelSlideListener {
        @Override
        public void onPanelClosed(@NotNull View view) {
            //System.out.println("Panel closed");
            //button_hide.setText(getActivity().getResources().getString(R.string.calculator_button_hide_panel));
            button_hide.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calculator_gt_sign, 0, 0, 0);
        }

        @Override
        public void onPanelOpened(@NotNull View view) {
            //System.out.println("Panel opened");
            //button_hide.setText(getActivity().getResources().getString(R.string.calculator_button_show_panel));
            button_hide.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calculator_lt_sign, 0, 0, 0);
        }

        @Override
        public void onPanelSlide(@NotNull View view, float arg1) {
            //System.out.println("Panel sliding");
        }
    }



}

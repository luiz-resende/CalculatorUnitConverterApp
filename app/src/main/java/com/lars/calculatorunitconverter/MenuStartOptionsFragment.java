package com.lars.calculatorunitconverter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lars.calculatorunitconverter.ui.calculator.CalculatorFragment;

public class MenuStartOptionsFragment extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @SuppressWarnings("FieldCanBeLocal")
    private View root_start_menu;

    Button menu_calculator_button, menu_currency_button, menu_dilution_button, menu_units_converter_button;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(LOG_TAG, "Start menu initiated");

        root_start_menu = inflater.inflate(R.layout.fragment_start_menu, container, false);

        this.menu_calculator_button = root_start_menu.findViewById(R.id.start_menu_calculator_btn);
        this.menu_currency_button = root_start_menu.findViewById(R.id.start_menu_currency_btn);
        this.menu_dilution_button = root_start_menu.findViewById(R.id.start_menu_dilution_btn);
        this.menu_units_converter_button = root_start_menu.findViewById(R.id.start_menu_units_converter_btn);

        menu_calculator_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Calculator menu button clicked!");

                final Fragment current = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                if(current == null || !(current instanceof CalculatorFragment)){
                    fragmentTransaction.replace(container.getId(), new CalculatorFragment(), "findFragmentCalculator");
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        });

        menu_currency_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Currency converter menu button clicked!");

                /*FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.app_bar_main, new CurrencyFragment(), "findFragmentCurrency");
                fragmentTransaction.addToBackStack(null).commit();*/
            }
        });

        menu_dilution_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Dilution calculator menu button clicked!");

                /*FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.app_bar_main, new DilutionCalculatorFragment(), "findFragmentDilution");
                fragmentTransaction.addToBackStack(null).commit();*/
            }
        });

        menu_units_converter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Units converter menu button clicked!");

                /*FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.app_bar_main, new UnitConversionFragment(), "findFragmentUnits");
                fragmentTransaction.addToBackStack(null).commit();*/
            }
        });


        return root_start_menu;
    }

}

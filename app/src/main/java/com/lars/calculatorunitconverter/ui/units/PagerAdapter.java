package com.lars.calculatorunitconverter.ui.units;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

@SuppressWarnings({"ConstantConditions", "deprecation"})
public class PagerAdapter extends FragmentPagerAdapter {

    private final int numberTabs;  // Integer variable to keep track of tab clicked



    public PagerAdapter(@NonNull FragmentManager frag_manager, int behavior, int tabs) {
        super(frag_manager, behavior);
        this.numberTabs = tabs;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TabItem_Area();
            case 1:
                return new TabItem_Distance();
            case 2:
                return new TabItem_Mass();
            case 3:
                return new TabItem_Pressure();
            case 4:
                return new TabItem_Speed();
            case 5:
                return new TabItem_Temperature();
            case 6:
                return new TabItem_Time();
            case 7:
                return new TabItem_Volume();
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return numberTabs;
    }



}

package com.lars.calculatorunitconverter.ui.units;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lars.calculatorunitconverter.MainActivity;
import com.lars.calculatorunitconverter.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

@SuppressWarnings({"unused", "RedundantSuppression", "ConstantConditions", "deprecation", "FieldCanBeLocal"})
public class UnitConversionFragment extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TabLayout tabsLayout;
    private TabItem tab_item_AREA, tab_item_DIST, tab_item_MASS, tab_item_PRES, tab_item_SPEE, tab_item_TEMP,
            tab_item_TIME, tab_item_VOLU;
    private ViewPager viewPager;
    private PagerAdapter pageAdapter;
    private View root_units;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(LOG_TAG, "Unit converter menu selected...");

        root_units = inflater.inflate(R.layout.fragment_units, container, false);

        this.viewPager = root_units.findViewById(R.id.viewPager_units);
        this.tabsLayout = root_units.findViewById(R.id.tabLayout);
        this.tab_item_AREA = root_units.findViewById(R.id.tab_unit_AREA);
        this.tab_item_DIST = root_units.findViewById(R.id.tab_unit_DIST);
        this.tab_item_MASS = root_units.findViewById(R.id.tab_unit_MASS);
        this.tab_item_PRES = root_units.findViewById(R.id.tab_unit_PRES);
        this.tab_item_SPEE = root_units.findViewById(R.id.tab_unit_SPEE);
        this.tab_item_TEMP = root_units.findViewById(R.id.tab_unit_TEMP);
        this.tab_item_TIME = root_units.findViewById(R.id.tab_unit_TIME);
        this.tab_item_VOLU = root_units.findViewById(R.id.tab_unit_VOLU);

        // Setting the pager adapter
        this.pageAdapter = new com.lars.calculatorunitconverter.ui.units.PagerAdapter(getFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabsLayout.getTabCount());
        this.viewPager.setAdapter(pageAdapter);

        // Changing view on tab clicking
        tabsLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());  // Setting view based on tab selected
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Getting the "onTabSelectedListener" from above
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabsLayout));


        return root_units;
    }
}

package com.lars.calculatorunitconverter;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final Integer NUMBER_DECIMALS = 10;

    private DrawerLayout drawer;
    private NavController navController;
    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;


    /*
    ** -> Setting a Toolbar to replace the ActionBar.
    *
    ** -> Finding the drawer view
    *
    ** -> Finding navigation view
    *
    ** -> Drawer toogle
    *
    ** -> Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
    *
    ** -> Finding and setting up navigation controller and configurations
    *
    ** -> The default values for the settings have already been set in the 'android:defaultValue' attribute.
    * However, the app must save the default value in the SharedPreferences file for each setting when the user
    * first opens the app. To do so, add 'PreferenceManager.setDefaultValues(this, R.xml.preferences_file, false)'
    * to the end of the existing 'onCreate()'. This ensures that the settings are properly initialized with their
    * default values. The setDefaultValues() method takes three arguments:
    *       --> The app context, such as 'this'.
    *       --> The resource ID for the XML resource file with one or more settings.
    *       --> A boolean indicating whether the default values should be set more than once.
    *          When false, the system sets the default values only if this method has never been called in the past.
    *          As long as you set this third argument to false, you can safely call this method every time
    *          the main activity starts without overriding the user's saved settings values.
    *          However, if you set it to true, the method will override any previous values with the defaults.
    *
    ** ->
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "App initiated...");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //noinspection deprecation
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_calculator, R.id.nav_currency, R.id.nav_dilution, R.id.nav_units).setDrawerLayout(drawer).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.drawer_open, R.string.drawer_close);

        drawer.addDrawerListener(toogle);
        toogle.setDrawerIndicatorEnabled(true);
        toogle.syncState();




        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }



    /*
     ** Inflate the menu; this adds items to the action bar if it is present.
     * @param menu Options menu
     * @return True if menu is inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }



    /*
    ** Navigation controller
    */
    @Override
    public boolean onSupportNavigateUp() {
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

}
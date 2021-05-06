package com.lars.calculatorunitconverter.ui.currency;

import android.annotation.SuppressLint;
//import android.graphics.Color;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lars.calculatorunitconverter.MainActivity;
import com.lars.calculatorunitconverter.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


@SuppressWarnings({"ConstantConditions", "FieldCanBeLocal", "RedundantSuppression", "SimpleDateFormat", "FieldMayBeFinal", "ResultOfMethodCallIgnored", "UnnecessaryLocalVariable", "Convert2Lambda", "IfStatementWithIdenticalBranches", "unused", "ParameterCanBeLocal", "UnusedAssignment", "rawtypes", "SameParameterValue"})
public class CurrencyFragment extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private View root_currency;

    //private final int NUMBER_OF_DECIMALS = MainActivity.NUMBER_DECIMALS;
    private final int NUMBER_OF_DECIMALS = 4;

    /* Strings with web address for the currencies and rates api on Open Exchange */
    private String BASE_URL_RATES = "https://openexchangerates.org/api/latest.json";
    private String BASE_URL_CURRENCIES = "https://openexchangerates.org/api/currencies.json";
    private String URL_API_ID_CONNECT = "?app_id=";
    private String API_ID = "";  // TYPE YOUR APP ID HERE! 
    private String TOTAL_RATES_URL = (BASE_URL_RATES + URL_API_ID_CONNECT + API_ID);
    private String TOTAL_CURRENCIES_URL = (BASE_URL_CURRENCIES + URL_API_ID_CONNECT + API_ID);

    /* Strings to store file names and paths */
    //private final String PATH_INFO = "/currency_converter/downloaded";
    private String PATH_INFO;
    private String CURRENCY_PATH;
    private String CURRENCY_FILE_NAME;
    private String RATES_FILE_NAME;

    /* Boolean variables to handle actions of success in retrieving online information */
    private boolean ratesFromInternet, currencyFromInternet;

    /* Date formats used in the time stamp */
    SimpleDateFormat simpleFormatDate = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat simpleFormatTime = new SimpleDateFormat("HH:mm");

    /* Strings to store the currencies and rates information retrieved */
    private String ratesJsonString = "";
    private String currencyJsonString = "";

    /* View objects from layout */
    private Spinner fromCurrencySpin, toCurrencySpin;
    private EditText inputTextView, outputTextView;
    private TextView inputTextSymbol, outputTextSymbol, timeStampText;
    private ImageButton changeButton;

    /* ArrayList of Strings used to update spinners and HashMap of Strings used to quickly access rates values */
    private ArrayList<String> keysList;
    private HashMap<String, String> currencyDictionary = new HashMap<>();


    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Currency converter menu selected...");

        root_currency = inflater.inflate(R.layout.fragment_currency, container, false);


        try {
            createPaths();
        } catch (IOException e) {
            e.getStackTrace();
        }

        this.fromCurrencySpin = root_currency.findViewById(R.id.currency_converter_spinner_from_currency);
        this.inputTextView = root_currency.findViewById(R.id.currency_converter_input_text_value);
        this.inputTextSymbol = root_currency.findViewById(R.id.currency_from_text_view_value);
        this.toCurrencySpin = root_currency.findViewById(R.id.currency_converter_spinner_to_currency);
        this.outputTextView = root_currency.findViewById(R.id.currency_converter_output_text_value);
        this.outputTextSymbol = root_currency.findViewById(R.id.currency_to_text_view_value);

        this.timeStampText = root_currency.findViewById(R.id.currency_update_text_view);

        if (isNetworkAccess(getContext())){

            try {
                readSavedCurrencies(true);

                initDictUpdateSpinners(161, 30);  // Initializing spinners with USD and CAD

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            if(!ratesJsonString.isEmpty() && !currencyJsonString.isEmpty()) {
                onSpinnerSelection(root_currency, fromCurrencySpin, inputTextSymbol);
                onSpinnerSelection(root_currency, toCurrencySpin, outputTextSymbol);

            /*outputTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    getResultConversion(root_currency, outputTextView, inputTextView, toCurrencySpin, fromCurrencySpin, false);
                }
            });*/

                onChangeClick(root_currency, changeButton);
            }else {
                Toast.makeText(getContext(), "NOT ABLE TO UPDATE RATES AND CURRENCIES!", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(getContext(), "No Internet Access!", Toast.LENGTH_SHORT).show();
            this.ratesFromInternet = false;
            this.currencyFromInternet = false;

            try {
                readSavedCurrencies(false);

                if(!checkString(currencyJsonString) && !checkString(ratesJsonString)) {
                    initDictUpdateSpinners(161, 30);  // Initializing spinners with USD and CAD
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            if(!ratesJsonString.isEmpty() && !currencyJsonString.isEmpty()) {
                onSpinnerSelection(root_currency, fromCurrencySpin, inputTextSymbol);
                onSpinnerSelection(root_currency, toCurrencySpin, outputTextSymbol);

                onChangeClick(root_currency, changeButton);
            }
            else {
                Toast.makeText(getContext(), "NOT ABLE TO UPDATE RATES AND CURRENCIES!", Toast.LENGTH_SHORT).show();
            }
        }


        return root_currency;
    }



    // Method to create directory and json file if they do not exist
    private void createPaths() throws IOException {
        this.PATH_INFO = getContext().getFilesDir().toString();
        this.CURRENCY_PATH = this.PATH_INFO + "/currency_converter";
        File appMenuDirectory = new File(PATH_INFO, "currency_converter");
        if (!appMenuDirectory.exists()) {
            appMenuDirectory.mkdir();
            Log.d(LOG_TAG, "    Directory \"" + CURRENCY_PATH + "\" created!");
        }
        else {
            Log.d(LOG_TAG, "    Directory \"" + CURRENCY_PATH + "\" already exists!");
        }

        this.RATES_FILE_NAME = this.CURRENCY_PATH + "/" + "downloaded_currency_rates.json";
        this.CURRENCY_FILE_NAME = this.CURRENCY_PATH + "/" + "downloaded_currencies.json";
        File ratesJsonFilePath = new File(RATES_FILE_NAME);
        File currencyJsonFilePath = new File(CURRENCY_FILE_NAME);
        if (!ratesJsonFilePath.exists()) {
            ratesJsonFilePath.createNewFile();
            Log.d(LOG_TAG, "    Rates Json file created!");
        }
        else {
            Log.d(LOG_TAG, "    Rates file already exists!");
        }
        if (!currencyJsonFilePath.exists()) {
            currencyJsonFilePath.createNewFile();
            Log.d(LOG_TAG, "    Currencies Json file created!");
        }
        else {
            Log.d(LOG_TAG, "    Currencies file already exists!");
        }
    }



    // Method to check if device has access to network
    private boolean isNetworkAccess(Context context) {
        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }



    // Method to check if passed strings are empty (handle NullPointer)
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkString(String str) {
        return (str == null || str.isEmpty());
    }



    // Method to round result by a given number of decimals
    private double roundDecimals(@NotNull Double number) {
        String[] splitter = number.toString().split("\\.");  // Splitting double number and counting decimals
        int decimals_in_number = splitter[1].length();   // Number of digits after decimal count

        BigDecimal temp_number = BigDecimal.valueOf(number);  // 'BigDecimal.valueOf()' does some extra rounding based on how double would appear if you printed it.
        //noinspection IfStatementWithIdenticalBranches
        if(decimals_in_number <= this.NUMBER_OF_DECIMALS) {
            return temp_number.doubleValue();  // Return the double value
        } else {
            temp_number = temp_number.setScale(this.NUMBER_OF_DECIMALS, RoundingMode.HALF_UP);  // Rounding up if remainder >=0.5
            return temp_number.doubleValue();  // Return the rounded double value
        }
    }



    // Method to get timestamp
    private void setTimeStampText(JSONObject jsonFileObject) throws JSONException {
        long timestamp = jsonFileObject.getLong("timestamp");
        Date dateTimestamp = new Date(timestamp * 1000);

        String timeUpdate = getActivity().getResources().getString(R.string.currency_update_date_message)
                + simpleFormatDate.format(dateTimestamp)
                + getActivity().getResources().getString(R.string.currency_update_time_message)
                + simpleFormatTime.format(dateTimestamp);
        Toast.makeText(getContext(), timeUpdate, Toast.LENGTH_SHORT).show();

        timeStampText.setText(timeUpdate);
    }



    // Method to read existing json file if online not available
    private void writeJsonFile(String siteBodyMessage, String fileName) throws IOException {
        File currencyInfoFile = new File(fileName);
        if(currencyInfoFile.exists()) {
            currencyInfoFile.delete();
            currencyInfoFile.createNewFile();
        }
        Writer saveFile = new BufferedWriter(new FileWriter(currencyInfoFile));
        if (siteBodyMessage != null) {
            saveFile.write(siteBodyMessage);
            Log.d(LOG_TAG, "    Information saved to file \"" + fileName + "\"!");
        }
        saveFile.close();
    }



    // Method to read online information from exchange API and save such information to a json file
    private void readOnlineWriteInformation(final String bodyFileInfo, boolean flagInternet, String onlineStringInfo, String fileName)
            throws JSONException, IOException {
        JSONObject jsonFileObject = new JSONObject(bodyFileInfo);

        if(jsonFileObject.has("error")) {
            Log.d(LOG_TAG, "    File read contain errors!!!!!!");
            //noinspection UnusedAssignment
            flagInternet = false;

            String errorMessage = jsonFileObject.getString("message");
            if(errorMessage.equals("invalid_app_id")) {
                Toast.makeText(getContext(), "Invalid App ID!\nCheck API!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Log.d(LOG_TAG, "    Success in reading file online!");
            flagInternet = true;

            onlineStringInfo += bodyFileInfo;

            if(jsonFileObject.has("timestamp")) {
                setTimeStampText(jsonFileObject);
            }

            writeJsonFile(onlineStringInfo, fileName);
        }
    }



    // Method to check if currency API is accessible and try to read it with above method
    private void loadCurrencies() {

        OkHttpClient client = new OkHttpClient();

        Request requestSite = new Request.Builder()
                .url(TOTAL_CURRENCIES_URL)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(requestSite).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                currencyFromInternet = false;
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                Toast.makeText(getActivity(), mMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String bodyFileInfo = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getActivity(), mMessage, Toast.LENGTH_SHORT).show();
                        try {
                            readOnlineWriteInformation(bodyFileInfo, currencyFromInternet, currencyJsonString, CURRENCY_FILE_NAME);
                        }
                        catch (JSONException e) {
                            e.getStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }



    // Method to check if currency API is accessible and try to read it with above method
    private void loadRates() {

        OkHttpClient client = new OkHttpClient();

        Request requestSite = new Request.Builder()
                .url(TOTAL_RATES_URL)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(requestSite).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                ratesFromInternet = false;
                String mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                Toast.makeText(getActivity(), mMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String bodyFileInfo = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getActivity(), mMessage, Toast.LENGTH_SHORT).show();
                        try {
                            readOnlineWriteInformation(bodyFileInfo, ratesFromInternet, ratesJsonString, RATES_FILE_NAME);
                        }
                        catch (JSONException e) {
                            e.getStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }



    // Method to read existing json file if online not available
    private String readJsonFile(String fileName) throws IOException {
        InputStream inputStreamFile = new FileInputStream(fileName);
        byte[] inputBuffer = new byte[inputStreamFile.available()];
        inputStreamFile.read(inputBuffer);
        inputStreamFile.close();
        String inputString = new String(inputBuffer);
        //this.currencyJsonString = inputString;
        Log.d(LOG_TAG, "    Information read from file \"" + fileName + "\"!");

        return inputString;
    }



    // Method to call loadCurrencies and read information from json file updated or old
    @SuppressWarnings("SameParameterValue")
    private void readSavedCurrencies(boolean internetConnection) throws IOException, JSONException {

        if(internetConnection) {
            loadRates();
            if(!ratesFromInternet) {
                this.ratesJsonString = readJsonFile(RATES_FILE_NAME);
            }
            loadCurrencies();
            if(!currencyFromInternet) {
                this.currencyJsonString = readJsonFile(CURRENCY_FILE_NAME);
            }
        }
        else {
            if(!ratesFromInternet) {
                this.ratesJsonString = readJsonFile(RATES_FILE_NAME);
                setTimeStampText(new JSONObject(this.ratesJsonString));
            }
            if(!currencyFromInternet) {
                this.currencyJsonString = readJsonFile(CURRENCY_FILE_NAME);
            }
        }


    }



    // Method to create dictionary of currencies and symbols
    private HashMap<String, String> createDict(String currencyStr, String ratesStr) throws JSONException {
        HashMap<String, String> dict = new HashMap<>();
        JSONObject jasonCurrency = new JSONObject(currencyStr);
        //JSONObject jasonRates = new JSONObject(ratesStr);
        Iterator currencyKeysAvailable = jasonCurrency.keys();

        while(currencyKeysAvailable.hasNext()) {
            String key = currencyKeysAvailable.next().toString();
            dict.put(jasonCurrency.get(key).toString(), key);
        }

        return dict;
    }



    // Method to update spinner
    @SuppressWarnings("rawtypes")
    private void updateSpinnerInfo(String bodyFileInfo) throws JSONException {
        JSONObject jsonFileObject = new JSONObject(bodyFileInfo);
        JSONObject  currency = jsonFileObject.getJSONObject("rates");

        Iterator currencyKeysAvailable = currency.keys();
        this.keysList = new ArrayList<>();

        while(currencyKeysAvailable.hasNext()) {

            String key = currencyKeysAvailable.next().toString();
            this.keysList.add(key);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, this.keysList);
        this.toCurrencySpin.setAdapter(spinnerArrayAdapter);
    }



    // Method to update spinner
    private void updateSpinnerInfoMap(HashMap<String, String> dict, Spinner spinner) {
        this.keysList = new ArrayList<>(dict.keySet());
        Collections.sort(this.keysList);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, this.keysList);
        spinner.setAdapter(spinnerArrayAdapter);
    }



    // Method to call dictionary construction and spinners update
    private void initDictUpdateSpinners(int initFromCurr, int initToCurr) throws JSONException {
        this.currencyDictionary = createDict(currencyJsonString, ratesJsonString);

        //updateSpinnerInfo(this.ratesJsonString);
        updateSpinnerInfoMap(this.currencyDictionary, this.fromCurrencySpin);
        updateSpinnerInfoMap(this.currencyDictionary, this.toCurrencySpin);

        this.fromCurrencySpin.setSelection(initFromCurr);
        this.toCurrencySpin.setSelection(initToCurr);

        this.inputTextSymbol.setText(this.currencyDictionary.get(this.fromCurrencySpin.getItemAtPosition(initFromCurr).toString()));
        this.outputTextSymbol.setText(this.currencyDictionary.get(this.toCurrencySpin.getItemAtPosition(initToCurr).toString()));
    }



    // Method to convert currencies
    @SuppressWarnings("StringOperationCanBeSimplified")
    private void convertCurrency(String fromCurrency, String toCurrency, double inputCurrValue, EditText outEdit) throws JSONException {

        JSONObject currencyJSON = new JSONObject(this.ratesJsonString);
        JSONObject rates = currencyJSON.getJSONObject("rates");

        String fCurr = this.currencyDictionary.get(fromCurrency).toString();
        String tCurr = this.currencyDictionary.get(toCurrency).toString();

        String fVal = rates.getString(fCurr);
        String tVal = rates.getString(tCurr);

        double output = inputCurrValue / Double.parseDouble(fVal);

        output *=  Double.parseDouble(tVal);

        outEdit.setText(String.valueOf(roundDecimals(output)));
    }



    // Method to get conversion result
    private void getResultConversion(View root, EditText inputValue, EditText outputValue, Spinner fromCurrency,
                                     Spinner toCurrency, boolean input) {

        if(!inputValue.getText().toString().isEmpty())
        {
            String fromCurr = fromCurrency.getSelectedItem().toString();
            String toCurr = toCurrency.getSelectedItem().toString();

            double inputValueNumber = Double.parseDouble(inputValue.getText().toString());

            //Toast.makeText(getActivity(), "Please Wait..", Toast.LENGTH_SHORT).show();
            try {
                convertCurrency(fromCurr, toCurr, inputValueNumber, outputValue);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if(input) {
            outputValue.setText("");
        }
    }



    // Method for spinner selection
    private void onSpinnerSelection(View root, Spinner spinTool, TextView symbolText) {
        spinTool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                symbolText.setText(currencyDictionary.get(spinTool.getItemAtPosition(spinTool.getSelectedItemPosition()).toString()));

                getResultConversion(root, inputTextView, outputTextView, fromCurrencySpin, toCurrencySpin, true);

                inputTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        getResultConversion(root, inputTextView, outputTextView, fromCurrencySpin, toCurrencySpin, true);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }



    // Method for result button click
    private void onChangeClick(View root, ImageButton btn) {
        btn = root.findViewById(R.id.currency_converter_change_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Change button clicked!");
                int tempInt = fromCurrencySpin.getSelectedItemPosition();
                //String tempStr = inputTextView.getText().toString();

                fromCurrencySpin.setSelection(toCurrencySpin.getSelectedItemPosition());
                //inputTextView.setText(outputTextView.getText().toString());
                inputTextSymbol.setText(currencyDictionary.get(fromCurrencySpin.
                        getItemAtPosition(toCurrencySpin.getSelectedItemPosition()).toString()));

                toCurrencySpin.setSelection(tempInt);
                //outputTextView.setText(tempStr);
                outputTextSymbol.setText(currencyDictionary.get(toCurrencySpin.getItemAtPosition(tempInt).toString()));

                getResultConversion(root, inputTextView, outputTextView, fromCurrencySpin, toCurrencySpin, true);

            }
        });
    }



}

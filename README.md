# CalculatorUnitConverterAppPrivate

### **Author**: *Resende, Luiz*
### **Date**: *April 2021*
### **Subject**: *Android Studio Application*

## **Overview**
This project aims to implement an Android Application designed to include a drawer menu with four sub-actions:

1.  Calculator: a calculator based on the Android Google native calculator app;
2.  Unit Converter: a unit converter for the main used unit conversions;
3.  Currency Converter: a currency converter with access to exchange rates API;
4.  Dilution calculator: a simple calculator for concentration of liquid solutions.   

## INSTALLATION
The project was entirely designed using [Android Studio](https://developer.android.com/studio/install), as such it is required to generate the apk file or edit functionalities.

## USAGE
The application does not need any special permission for execution.

### CALCULATOR
The *Calculator* fragment implements all the basic and advanced functions seen in native mobile calculator apps. The fragment uses a modified version of [EvalEx](https://github.com/uklimaschewski/EvalEx) module to analyze the input mathematical expression. The formulas are evaluated considering commom orders of precedence and by tokenizing the input expression ```String```. The modified version implemented also allows the use of unary postfix operators and functions without parenthesis (necessary for initial implementation of inline functions). This modified version of EvalEx can be found [here](https://github.com/luiz-resende/EvalEx/tree/modifications-xtreme). The EvalEx allows for the use of ```BigDecimal``` module when evaluating the expression, which improves precision and allows the methods implemented on ```Fractional.java``` class. The class ```Fractional.java``` was implemented to convert decimal numbers to fractional notation.

### CURRENCY CONVERTER
The *Currency Converter* fragment implements a simple interface where the user can quickly check exchange rates between two currencies. The fragment uses the exchange rates provided by [Open Exchange Rates](https://openexchangerates.org), as such it requires an App ID, which can be found after signing up for free. This ID should be replaced in the ```CurrencyFragment.java``` file at:

```java
    private String API_ID = ""  // TYPE YOUR APP ID HERE!
```

The conversions are given to the available available currencies in the API. Internet connection is required in the first access, so the currencies can be downloaded and saved, and any subsequent use with updated values. If no internet connection is available, the exchange rates saved from the last time are used.

### DILUTION
The *Dilution Calculator Fragment* presents a simple interface where the required volumes of either solvent or ingredient to reach a target concentration can be calculated based on an initial concentration and either an initial or final volume. All **volume inputs must be milliliters (mL)** and **concentration inputs must be percentages (%)**.

### UNIT CONVERTER
The ```UnitConversionFragment.java``` file implements a page adapter with different tabs for commom measures (e.g. Area, Mass, Volume etc.). Each tab presents the conversion of a given unit of measure to all other available units of that same measure.

## FUTURE IMPROVEMENTS
The next possible steps are:

1.  Add a ```Settings``` menu, where the user can modify the number of decimal places desired
2.  Add support for different languages (current support only English)
3.  Improve complexity of *Currency Converter* (refine names, add flags and save state)

## LICENSE
All files made available in this repository follow the licensing guidelines defined in the file **LICENSE.md**.

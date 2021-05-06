package com.lars.calculatorunitconverter.ui.currency;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

@SuppressWarnings("FieldMayBeFinal")
public class CurrencyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CurrencyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("UNDER CONSTRUCTION =/ \n This will be the 'currency converter' fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

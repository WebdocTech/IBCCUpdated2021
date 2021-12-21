package com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisaCreditDebit.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EasyPaisaCreditdebitViewModel extends AndroidViewModel {

    private final MutableLiveData<String> MLD_btn_nextClick = new MutableLiveData<>();

    public LiveData<String> LD_btn_next_click() {
        return MLD_btn_nextClick;
    }

    public EasyPaisaCreditdebitViewModel(@NonNull Application application) {
        super(application);
    }

    public void btnNextClick(String mobileNumber, String email) {
    }
}

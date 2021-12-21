package com.webdoc.ibcc.CompleteProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.webdoc.ibcc.Adapter.Spinner.SpinnerProvinceAdapter;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityCompleteAdrressBinding;

public class CompleteAddressActivity extends AppCompatActivity {
    private String spin_valProv, spin_valDomi, spin_valDomi_Id, spin_valProv_Id;
    private Boolean provinceCheck, domicileCheck;
    private ActivityCompleteAdrressBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityCompleteAdrressBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        //SPINNER PROVINCE
        SpinnerProvinceAdapter arrayAdapter = new SpinnerProvinceAdapter(this,
                R.layout.spinner_item, Global.pdfResponse.getResult().getProvinces());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layoutBinding.spinnerProvince.setAdapter(arrayAdapter);
        layoutBinding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                spin_valProv = Global.pdfResponse.getResult().getProvinces().get(position).getName();
                spin_valProv_Id = Global.pdfResponse.getResult().getProvinces().get(position).getId().toString();
                //Global.selectedProvinceID = spin_valProv_Id;
                provinceCheck = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //SPINNER DOMICILE
        SpinnerProvinceAdapter arrayAdapter1 = new SpinnerProvinceAdapter(this,
                R.layout.spinner_item, Global.pdfResponse.getResult().getProvinces());
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layoutBinding.spinnerCertificate.setAdapter(arrayAdapter1);
        layoutBinding.spinnerCertificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                spin_valDomi = Global.pdfResponse.getResult().getProvinces().get(position).getName();
                spin_valDomi_Id = Global.pdfResponse.getResult().getProvinces().get(position).getId().toString();
                // Global.selectedDomicileID = spin_valDomi_Id;
                domicileCheck = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        clickListeners();
    }

    private void clickListeners() {
        //NEXT BUTTON
        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(layoutBinding.edtCity.getText().toString())) {
                    Toast.makeText(CompleteAddressActivity.this, "Please complete your profile",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(layoutBinding.edtAddress.getText().toString())) {
                    Toast.makeText(CompleteAddressActivity.this, "Please complete your profile",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Global.registerUser.setProvince(spin_valProv);
                Global.registerUser.setProvinceId(spin_valProv_Id);
                Global.registerUser.setDomicile(spin_valDomi_Id);
                Global.registerUser.setCity(layoutBinding.edtCity.getText().toString());
                Global.registerUser.setAddress(layoutBinding.edtAddress.getText().toString());

                if (provinceCheck && domicileCheck) {
                    Intent intent = new Intent(CompleteAddressActivity.this,
                            CompleteProfilePic.class);
                    startActivity(intent);

                } else {
                    Global.utils.showErrorSnakeBar(CompleteAddressActivity.this, "Please complete your profile");
                }
            }
        });
    }

}
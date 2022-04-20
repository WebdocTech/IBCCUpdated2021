package com.webdoc.ibcc.CompleteProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.webdoc.ibcc.Adapter.Spinner.SpinnerCountriesAdapter;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityBasicInfoBinding;

import java.util.Calendar;

public class CompleteBasicInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private String dob, estimatedAge;
    private String spin_valTitle;
    private String[] Title = {"Please Select", "Mr", "Ms", "Mrs", "Teacher", "Dr.", "Professor"};
    private String spin_valueNat, spin_valueNat_Id;
    private boolean dobCheck, titleCheck, nationalityCheck;
    private ActivityBasicInfoBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityBasicInfoBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        //TITLE SPINNER
        ArrayAdapter<String> spin_adapter = new ArrayAdapter<>(this, R.layout.spinner_item, Title);
        layoutBinding.titleSpinner.setAdapter(spin_adapter);
        layoutBinding.titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                spin_valTitle = Title[position]; //saving the value selected
                titleCheck = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //NATIONALITY SPINNER
        Log.i("dfg", Global.pdfResponse.getResult().getCountries() + "");
        if(Global.pdfResponse.getResult().getCountries()!=null)
        {
            SpinnerCountriesAdapter arrayAdapter = new SpinnerCountriesAdapter(this, R.layout.spinner_item, Global.pdfResponse.getResult().getCountries());
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            layoutBinding.spinnerCountry.setAdapter(arrayAdapter);
            layoutBinding.spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    spin_valueNat = Global.pdfResponse.getResult().getCountries().get(position).getName();
                    spin_valueNat_Id = Global.pdfResponse.getResult().getCountries().get(position).getId().toString();

                    nationalityCheck = true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub;
                }
            });

        }

        //DATE OF BIRTH
        layoutBinding.tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });


        //NEXT BUTTON
        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.registerUser.setTitle(spin_valTitle);
                Global.registerUser.setCountry(spin_valueNat);
                Global.registerUser.setCountryId(spin_valueNat_Id);
                Global.registerUser.setDob(dob);
                Global.registerUser.setAge(estimatedAge);

                if (dobCheck && titleCheck && nationalityCheck) {
                    Intent intent = new Intent(CompleteBasicInfo.this, CompleteAddressActivity.class);
                    startActivity(intent);
                } else {
                    Global.utils.showErrorSnakeBar(CompleteBasicInfo.this, "Please complete your profile");
                }
            }
        });
    }

    public void showDatePicker() {
        Calendar c = Calendar.getInstance();
        Integer startMonth = c.get(Calendar.MONTH);
        Integer startDay = c.get(Calendar.DAY_OF_MONTH);
        Integer startYear = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CompleteBasicInfo.this, CompleteBasicInfo.this, startYear, startMonth, startDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String selectedDay = String.valueOf(day);
        String selectedMonth = String.valueOf(month + 1);
        String selectedYear = String.valueOf(year);

        getAge(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth), Integer.parseInt(selectedDay));
        String age = getAge(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth), Integer.parseInt(selectedDay));

        dob = selectedYear + "-" + selectedMonth + "-" + selectedDay;
        estimatedAge = "Age: " + age;

        layoutBinding.tvDob.setText("Dob: " + dob);
        layoutBinding.tvAge.setText(estimatedAge);
        dobCheck = true;
    }

    public String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month - 1, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        return ageS;
    }
}
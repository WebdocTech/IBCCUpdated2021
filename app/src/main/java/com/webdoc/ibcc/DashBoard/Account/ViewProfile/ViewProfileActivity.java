package com.webdoc.ibcc.DashBoard.Account.ViewProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.webdoc.ibcc.Adapter.Spinner.SpinnerCountriesAdapter;
import com.webdoc.ibcc.Adapter.Spinner.SpinnerProvinceAdapter;
import com.webdoc.ibcc.DashBoard.Account.AccountFrag;
import com.webdoc.ibcc.DashBoard.Account.ViewProfile.ProfileViewModel.ProfileViewModel;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.EducationDetails.EducationDetailsFragment;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Country;
import com.webdoc.ibcc.ResponseModels.PdfResult.Province;
import com.webdoc.ibcc.ResponseModels.UpdateProfileResult.UpdateProfileResult;
import com.webdoc.ibcc.ServerManager.VolleyListener;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.ActivityViewProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileActivity extends AppCompatActivity {
    private ArrayAdapter<CharSequence> title_spinner_adapter, spinner_nationality_adapter, spinner_province_adapter, spinner_domicile_adapter;
    private String title, country, province, country_Id, province_Id, domicile, domicile_Id, nationality;
    private int spinnerPosition, spinnerPositionTitle;
    private String firstName, lastName, dob, fatherName, userTitle, domicileID, pAddress, pCity, pProvinceId,
            pCountryId, cAddress, cCity, cProvinceId, cCountryId, userNationality;
    private ActivityViewProfileBinding layoutBinding;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityViewProfileBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        Picasso.get()
                .load(Uri.parse(Global.userLoginResponse.getResult().getCustomerProfile()
                        .getPassportSizImage()))
                .error(R.drawable.ic_user)
                .into(layoutBinding.ivUser);

        if (Global.userLoginResponse.getResult() != null) {
            layoutBinding.edtFirstName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFirstName());
            layoutBinding.edtLastName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getLastName());
            layoutBinding.edtFatherName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFatherName());
            layoutBinding.edtEmail.setText(Global.userLoginResponse.getResult().getCustomerProfile().getEmail());

            layoutBinding.edtMobileNo.setText(Global.userLoginResponse.getResult().getCustomerProfile().getMobile());
            layoutBinding.tvDob.setText(Global.userLoginResponse.getResult().getCustomerProfile().getDob());
            layoutBinding.edtCnic.setText(Global.userLoginResponse.getResult().getCustomerProfile().getCnic());
            layoutBinding.edtAddress.setText(Global.userLoginResponse.getResult().getCustomerProfile().getcAdd());
            layoutBinding.edtCity.setText(Global.userLoginResponse.getResult().getCustomerProfile().getcCity());
        }

        layoutBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //DATE OF BIRTH
        layoutBinding.tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        //SPINNER TITLE
        title_spinner_adapter = ArrayAdapter.createFromResource(this, R.array.title_array, R.layout.spinner_item);
        layoutBinding.titleSpinner.setAdapter(title_spinner_adapter);

        String compareValue1 = Global.userLoginResponse.getResult().getCustomerProfile().getTitle();
        if (compareValue1 != null) {
            spinnerPositionTitle = title_spinner_adapter.getPosition(compareValue1);
            layoutBinding.titleSpinner.setSelection(spinnerPositionTitle);
        }

        layoutBinding.titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //SPINNER NATIONALITY
        spinner_nationality_adapter = ArrayAdapter.createFromResource(this, R.array.nationality_array, R.layout.spinner_item);
        layoutBinding.spinnerNationality.setAdapter(spinner_nationality_adapter);

        String compareValue = Global.userLoginResponse.getResult().getCustomerProfile().getNationality();
        if (compareValue != null) {
            spinnerPosition = spinner_nationality_adapter.getPosition(compareValue);
            layoutBinding.spinnerNationality.setSelection(spinnerPosition);
        }

        layoutBinding.spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nationality = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        //SPINNER COUNTRY
        SpinnerCountriesAdapter arrayAdapter3 = new SpinnerCountriesAdapter(this, R.layout.spinner_item, Global.pdfResponse.getResult().getCountries());
        layoutBinding.spinnerCountry.setAdapter(arrayAdapter3);

        Country selectedCountry = null;
        for (int i = 0; i < Global.pdfResponse.getResult().getCountries().size(); i++) {
            if (Global.userLoginResponse.getResult().getCustomerProfile().getcCountryId().equalsIgnoreCase(Global.pdfResponse.getResult().getCountries().get(i).getId().toString())) {
                selectedCountry = Global.pdfResponse.getResult().getCountries().get(i);
                break;
            }
        }

        int spinnerPosition = arrayAdapter3.getPosition(selectedCountry);
        layoutBinding.spinnerCountry.setSelection(spinnerPosition);

        layoutBinding.spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                country = Global.pdfResponse.getResult().getCountries().get(position).getName();
                country_Id = Global.pdfResponse.getResult().getCountries().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });


        //PROVINCE SPINNER
        SpinnerProvinceAdapter arrayAdapter1 = new SpinnerProvinceAdapter(this, R.layout.spinner_item, Global.pdfResponse.getResult().getProvinces());
        layoutBinding.spinnerProvince.setAdapter(arrayAdapter1);

        Province selectedProvince = null;
        for (int i = 0; i < Global.pdfResponse.getResult().getProvinces().size(); i++) {
            if (Global.userLoginResponse.getResult().getCustomerProfile().getcProvinceId().equalsIgnoreCase(Global.pdfResponse.getResult().getProvinces().get(i).getId().toString())) {
                selectedProvince = Global.pdfResponse.getResult().getProvinces().get(i);
                break;
            }
        }
        int spinnerPosition1 = arrayAdapter1.getPosition(selectedProvince);
        layoutBinding.spinnerProvince.setSelection(spinnerPosition1);

        layoutBinding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                province = Global.pdfResponse.getResult().getProvinces().get(position).getName();
                province_Id = Global.pdfResponse.getResult().getProvinces().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //DOMICILE SPINNER
        SpinnerProvinceAdapter arrayAdapter2 = new SpinnerProvinceAdapter(this, R.layout.spinner_item, Global.pdfResponse.getResult().getProvinces());
        layoutBinding.spinnerCertificate.setAdapter(arrayAdapter2);

        Province selectedDomicile = null;
        for (int i = 0; i < Global.pdfResponse.getResult().getProvinces().size(); i++) {
            if (Global.userLoginResponse.getResult().getCustomerProfile().getDomicile().equalsIgnoreCase(Global.pdfResponse.getResult().getProvinces().get(i).getId().toString())) {
                selectedDomicile = Global.pdfResponse.getResult().getProvinces().get(i);
                break;
            }
        }
        int spinnerPosition2 = arrayAdapter2.getPosition(selectedDomicile);
        layoutBinding.spinnerCertificate.setSelection(spinnerPosition2);

        layoutBinding.spinnerCertificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                domicile = Global.pdfResponse.getResult().getProvinces().get(position).getName();
                domicile_Id = Global.pdfResponse.getResult().getProvinces().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        layoutBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = layoutBinding.edtFirstName.getText().toString();
                lastName = layoutBinding.edtLastName.getText().toString();
                dob = layoutBinding.tvDob.getText().toString();
                fatherName = layoutBinding.edtFatherName.getText().toString();
                userTitle = title;
                domicileID = domicile_Id;
                pAddress = layoutBinding.edtAddress.getText().toString();
                pCity = layoutBinding.edtCity.getText().toString();
                pProvinceId = province_Id;
                pCountryId = country_Id;
                cAddress = layoutBinding.edtAddress.getText().toString();
                cCity = layoutBinding.edtCity.getText().toString();
                cProvinceId = province_Id;
                cCountryId = country_Id;
                userNationality = nationality;

                /*callUpdateProfileApi(ViewProfileActivity.this, firstName, lastName, dob, fatherName,
                        userTitle, domicileID, pAddress, pCity, pProvinceId, pCountryId, cAddress, cCity,
                        cProvinceId, cCountryId, userNationality);*/

                viewModel.callUpdateProfileApi(ViewProfileActivity.this, firstName, lastName, dob, fatherName,
                        userTitle, domicileID, pAddress, pCity, pProvinceId, pCountryId, cAddress, cCity,
                        cProvinceId, cCountryId, userNationality);
            }
        });

        observers();
    }

    private void observers() {
        viewModel.getUpdateProfileResult().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.updateProfileResponse = response;

                    Global.userLoginResponse.getResult().getCustomerProfile().setFirstName(firstName);
                    Global.userLoginResponse.getResult().getCustomerProfile().setLastName(lastName);
                    Global.userLoginResponse.getResult().getCustomerProfile().setDob(dob);
                    Global.userLoginResponse.getResult().getCustomerProfile().setFatherName(fatherName);
                    Global.userLoginResponse.getResult().getCustomerProfile().setTitle(userTitle);
                    Global.userLoginResponse.getResult().getCustomerProfile().setDomicile(domicileID);
                    Global.userLoginResponse.getResult().getCustomerProfile().setpAdd(pAddress);
                    Global.userLoginResponse.getResult().getCustomerProfile().setpCity(pCity);
                    Global.userLoginResponse.getResult().getCustomerProfile().setpProvinceId(pProvinceId);
                    Global.userLoginResponse.getResult().getCustomerProfile().setpCountryId(pCountryId);
                    Global.userLoginResponse.getResult().getCustomerProfile().setcAdd(cAddress);
                    Global.userLoginResponse.getResult().getCustomerProfile().setcCity(cCity);
                    Global.userLoginResponse.getResult().getCustomerProfile().setcProvinceId(cProvinceId);
                    Global.userLoginResponse.getResult().getCustomerProfile().setcCountryId(cCountryId);
                    Global.userLoginResponse.getResult().getCustomerProfile().setNationality(userNationality);

                    AccountFrag.UserName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFirstName() + " " + Global.userLoginResponse.getResult().getCustomerProfile().getLastName());
                    Toast.makeText(ViewProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Global.utils.showErrorSnakeBar(ViewProfileActivity.this, response.getResult().getResponseMessage());
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
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String selectedDay = String.valueOf(day);
                String selectedMonth = String.valueOf(month + 1);
                String selectedYear = String.valueOf(year);

                getAge(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth), Integer.parseInt(selectedDay));
                String age = getAge(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth), Integer.parseInt(selectedDay));

                layoutBinding.tvDob.setText("Dob: " + selectedYear + "/" + selectedMonth + "/" + selectedDay);
                layoutBinding.tvAge.setText("Age: " + age);
            }
        }, startYear, startMonth, startDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
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

    public void callUpdateProfileApi(Activity activity, String firstName, String lastName, String dob, String fatherName,
                                     String title, String domicileID, String pAddress, String pCity,
                                     String pProvinceId, String pCountryId, String cAddress, String cCity,
                                     String cProvinceId, String cCountryId, String nationality) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            params.addProperty("first_name", firstName);
            params.addProperty("last_name", lastName);
            params.addProperty("dob", dob);
            params.addProperty("father_name", fatherName);
            params.addProperty("title", title);
            params.addProperty("domicile", domicileID);
            params.addProperty("p_add", pAddress);
            params.addProperty("p_city", pCity);
            params.addProperty("p_province_id", pProvinceId);
            params.addProperty("p_country_id", pCountryId);
            params.addProperty("c_add", cAddress);
            params.addProperty("c_city", cCity);
            params.addProperty("c_province_id", cProvinceId);
            params.addProperty("c_country_id", cCountryId);
            params.addProperty("passport_siz_image", " ");
            params.addProperty("nationality", nationality);

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<UpdateProfileResult> call = apiInterface.callUpdateProfile(params);

            call.enqueue(new Callback<UpdateProfileResult>() {
                @Override
                public void onResponse(Call<UpdateProfileResult> call, Response<UpdateProfileResult> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.updateProfileResponse = response.body();

                            Global.userLoginResponse.getResult().getCustomerProfile().setFirstName(firstName);
                            Global.userLoginResponse.getResult().getCustomerProfile().setLastName(lastName);
                            Global.userLoginResponse.getResult().getCustomerProfile().setDob(dob);
                            Global.userLoginResponse.getResult().getCustomerProfile().setFatherName(fatherName);
                            Global.userLoginResponse.getResult().getCustomerProfile().setTitle(userTitle);
                            Global.userLoginResponse.getResult().getCustomerProfile().setDomicile(domicileID);
                            Global.userLoginResponse.getResult().getCustomerProfile().setpAdd(pAddress);
                            Global.userLoginResponse.getResult().getCustomerProfile().setpCity(pCity);
                            Global.userLoginResponse.getResult().getCustomerProfile().setpProvinceId(pProvinceId);
                            Global.userLoginResponse.getResult().getCustomerProfile().setpCountryId(pCountryId);
                            Global.userLoginResponse.getResult().getCustomerProfile().setcAdd(cAddress);
                            Global.userLoginResponse.getResult().getCustomerProfile().setcCity(cCity);
                            Global.userLoginResponse.getResult().getCustomerProfile().setcProvinceId(cProvinceId);
                            Global.userLoginResponse.getResult().getCustomerProfile().setcCountryId(cCountryId);
                            Global.userLoginResponse.getResult().getCustomerProfile().setNationality(userNationality);

                            AccountFrag.UserName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFirstName() + " " + Global.userLoginResponse.getResult().getCustomerProfile().getLastName());
                            Toast.makeText(ViewProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Global.utils.showErrorSnakeBar(ViewProfileActivity.this, response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateProfileResult> call, Throwable t) {
                    Global.utils.hideCustomLoadingDialog();
                    Log.i("dsd", t.getMessage());
                    Toast.makeText(activity, "Ooops something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Please connect internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
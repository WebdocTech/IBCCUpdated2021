package com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.PersonalInfo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.webdoc.ibcc.Adapter.Spinner.SpinnerCountriesAdapter;
import com.webdoc.ibcc.Adapter.Spinner.SpinnerProvinceAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.AttestationApplyActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.CallCourier.CallCourier;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.EducationDetails.EducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.PdfResult.Country;
import com.webdoc.ibcc.ResponseModels.PdfResult.Province;
import com.webdoc.ibcc.ResponseModels.Step1Result.Step1Result;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragmentPersonalInfoBinding;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInfoFragment extends Fragment {
    ArrayAdapter<CharSequence> title_spinner_adapter, spinner_nationality_adapter;
    String title, nationality, province, country, country_Id, province_Id, domicile,
            domicile_Id;
    int spinnerPosition, spinnerPositionTitle;
    VolleyRequestController volleyRequestController;

    String firstName, lastName, dob, fatherName, userTitle, domicileID, pAddress, pCity,
            pProvinceId, pCountryId, cAddress, cCity, cProvinceId, cCountryId, cnic, phone,
            mobile, email;
    private FragmentPersonalInfoBinding layoutBinding;
    private HomeSharedViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentPersonalInfoBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(HomeSharedViewModel.class);

        volleyRequestController = new VolleyRequestController(getActivity());

        if (Global.userLoginResponse.getResult() != null) {
            Picasso.get()
                    .load(Uri.parse(Global.userLoginResponse.getResult().getCustomerProfile().getPassportSizImage()))
                    .error(R.drawable.ic_user)
                    .into(layoutBinding.ivUser);

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

        setUpSpinners();
        observers();
        clickListeners();

        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getGetStep1().observe(this, response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.step1Response = response;

                    Fragment educationDetailsFragment = new EducationDetailsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, educationDetailsFragment).addToBackStack(null).commit();
                    Global.caseId = response.getResult().getId();

                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), response.getResult().getResponseMessage());
                }
            }
        });
    }

    private void setUpSpinners() {
        //SPINNER TITLE
        title_spinner_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.title_array, R.layout.spinner_item);
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
        spinner_nationality_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.nationality_array, R.layout.spinner_item);
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
        SpinnerCountriesAdapter arrayAdapter3 = new SpinnerCountriesAdapter(getActivity(), R.layout.spinner_item, Global.pdfResponse.getResult().getCountries());
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
        SpinnerProvinceAdapter arrayAdapter1 = new SpinnerProvinceAdapter(getActivity(), R.layout.spinner_item, Global.pdfResponse.getResult().getProvinces());
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

        //SPINNER DOMICILE
        SpinnerProvinceAdapter arrayAdapter2 = new SpinnerProvinceAdapter(getActivity(), R.layout.spinner_item, Global.pdfResponse.getResult().getProvinces());
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
    }

    private void clickListeners() {
        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttestationApplyActivity.stepIndicator.setCurrentStepPosition(1);

                firstName = layoutBinding.edtFirstName.getText().toString();
                lastName = layoutBinding.edtLastName.getText().toString();
                dob = layoutBinding.tvDob.getText().toString();
                fatherName = layoutBinding.edtFatherName.getText().toString();
                cnic = layoutBinding.edtCnic.getText().toString();
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
                phone = layoutBinding.edtMobileNo.getText().toString();
                mobile = layoutBinding.edtMobileNo.getText().toString();
                email = layoutBinding.edtEmail.getText().toString();

                //Global.utils.showCustomLoadingDialog(getActivity());
                //volleyRequestController.step1(firstName, lastName, dob, fatherName, cnic, userTitle, domicileID, pAddress,
                //pCity, pProvinceId, pCountryId, cAddress, cCity, cProvinceId, cCountryId, phone, mobile, email);
                /*callStep1Api(getActivity(), firstName, lastName, dob, fatherName, cnic,
                        userTitle, domicileID, pAddress, pCity, pProvinceId, pCountryId,
                        cAddress, cCity, cProvinceId, cCountryId, phone, mobile, email);*/

                viewModel.callStep1Api(getActivity(), firstName, lastName, dob, fatherName, cnic,
                        userTitle, domicileID, pAddress, pCity, pProvinceId, pCountryId,
                        cAddress, cCity, cProvinceId, cCountryId, phone, mobile, email);

            }
        });

        //DATE OF BIRTH
        layoutBinding.tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    public void showDatePicker() {
        Calendar c = Calendar.getInstance();
        Integer startMonth = c.get(Calendar.MONTH);
        Integer startDay = c.get(Calendar.DAY_OF_MONTH);
        Integer startYear = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), new DatePickerDialog.OnDateSetListener() {
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

    public void callStep1Api(Activity activity, String firstName, String lastName, String dob,
                             String fatherName, String cnic, String title, String domicileId, String pAddress,
                             String pCity, String pProvinceId, String pCountryId, String cAddress, String cCity,
                             String cProvinceId, String cCountryId, String phone,
                             String mobileNo, String email) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("id", Global.userLoginResponse.getResult().getCustomerProfile().getId());   //2004
            params.addProperty("first_name", firstName);
            params.addProperty("last_name", lastName);
            params.addProperty("dob", dob);
            params.addProperty("father_name", fatherName);
            params.addProperty("cnic", cnic);
            params.addProperty("title", title);
            params.addProperty("domicile", domicileId);
            params.addProperty("p_add", pAddress);
            params.addProperty("p_city", pCity);
            params.addProperty("p_province_id", pProvinceId);
            params.addProperty("p_country_id", pCountryId);
            params.addProperty("c_add", cAddress);
            params.addProperty("c_city", cCity);
            params.addProperty("c_province_id", cProvinceId);
            params.addProperty("c_country_id", cCountryId);
            params.addProperty("phone", phone);
            params.addProperty("mobile", mobileNo);
            params.addProperty("email", email);
            params.addProperty("passport_siz_image", " ");

            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<Step1Result> call = apiInterface.callStep1(params);

            call.enqueue(new Callback<Step1Result>() {
                @Override
                public void onResponse(Call<Step1Result> call, Response<Step1Result> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.step1Response = response.body();

                            Fragment educationDetailsFragment = new EducationDetailsFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, educationDetailsFragment).addToBackStack(null).commit();
                            Global.caseId = response.body().getResult().getId();

                        } else {
                            Global.utils.showErrorSnakeBar(getActivity(), response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Step1Result> call, Throwable t) {
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
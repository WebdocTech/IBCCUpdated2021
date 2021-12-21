package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.PersonalInfo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.CountriesAdapter;
import com.webdoc.ibcc.Adapter.Spinner.Equivalence.ProvinceAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.EquivalenceEducationDetailsFragment;
import com.webdoc.ibcc.DashBoard.Home.HomeSharedViewModel.HomeSharedViewModel;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.Country;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.Province;
import com.webdoc.ibcc.ResponseModels.IntiateCase.IntiateCase;
import com.webdoc.ibcc.ResponseModels.RemoveQualificationEQ.RemoveQualificationEQ;
import com.webdoc.ibcc.ServerManager.VolleyRequestController;
import com.webdoc.ibcc.api.APIClient;
import com.webdoc.ibcc.api.APIInterface;
import com.webdoc.ibcc.databinding.FragmentEquivalencePersonalInfoBinding;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquivalencePersonalInfoFragment extends Fragment {

    ArrayAdapter<CharSequence> title_spinner_adapter;
    String title, nationality, country, country_Id, domicile, domicile_Id, province, province_Id;
    int spinnerPositionTitle;
    VolleyRequestController volleyRequestController;

    String parentsEmployment = "Private";
    String firstName, lastName, dob, pob, userTitle, domicileID, pAddress, pCity, pCountryId,
            cAddress, cCity, cCountryId, cnic, phone, mobile, email,
            fatherName, fatherCnic, nameOfOrganization, fatherAddress, passportSizImage, password, pProvinceId, cProvinceId, incomAppLink;
    private FragmentEquivalencePersonalInfoBinding layoutBinding;
    private HomeSharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentEquivalencePersonalInfoBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(HomeSharedViewModel.class);

        volleyRequestController = new VolleyRequestController(getActivity());

        if (Global.userLoginResponse.getResult() != null) {
            Picasso.get()
                    .load(Global.userLoginResponse.getResult().getCustomerProfile().getPassportSizImage())
                    .error(R.drawable.ic_user)
                    .into(layoutBinding.ivUser);

            layoutBinding.edtFirstName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFirstName());
            layoutBinding.edtLastName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getLastName());
            layoutBinding.edtPob.setText(Global.userLoginResponse.getResult().getCustomerProfile().getBirthPlace());
            layoutBinding.edtEmail.setText(Global.userLoginResponse.getResult().getCustomerProfile().getEmail());
            layoutBinding.edtMobileNo.setText(Global.userLoginResponse.getResult().getCustomerProfile().getMobile());
            layoutBinding.tvDob.setText(Global.userLoginResponse.getResult().getCustomerProfile().getDob());
            layoutBinding.edtCnic.setText(Global.userLoginResponse.getResult().getCustomerProfile().getCnic());
            layoutBinding.edtAddress.setText(Global.userLoginResponse.getResult().getCustomerProfile().getcAdd());
            layoutBinding.edtCity.setText(Global.userLoginResponse.getResult().getCustomerProfile().getcCity());
        }

        layoutBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstName = layoutBinding.edtFirstName.getText().toString();
                lastName = layoutBinding.edtLastName.getText().toString();
                dob = layoutBinding.tvDob.getText().toString();
                fatherName = layoutBinding.edtFatherName.getText().toString();
                cnic = layoutBinding.edtCnic.getText().toString();
                userTitle = title;
                domicileID = domicile_Id;
                pob = layoutBinding.edtPob.getText().toString();
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
                passportSizImage = Global.userLoginResponse.getResult().getCustomerProfile().getPassportSizImage();
                password = Global.loginUser.getPassword();
                nationality = nationality;
                parentsEmployment = parentsEmployment;
                fatherCnic = layoutBinding.edtFatherCnic.getText().toString();
                fatherAddress = layoutBinding.edtFatherAddress.getText().toString();
                nameOfOrganization = layoutBinding.edtNameOfOrganization.getText().toString();
                incomAppLink = "";

                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(fatherName)
                        || TextUtils.isEmpty(cnic) || TextUtils.isEmpty(userTitle) || TextUtils.isEmpty(domicileID) || TextUtils.isEmpty(pob)
                        || TextUtils.isEmpty(pAddress) || TextUtils.isEmpty(pCity) || TextUtils.isEmpty(pProvinceId) || TextUtils.isEmpty(pCountryId)
                        || TextUtils.isEmpty(cAddress) || TextUtils.isEmpty(cCity) || TextUtils.isEmpty(cProvinceId) || TextUtils.isEmpty(cCountryId)
                        || TextUtils.isEmpty(phone) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(email) || TextUtils.isEmpty(passportSizImage)
                        || TextUtils.isEmpty(password) || TextUtils.isEmpty(nationality) || TextUtils.isEmpty(parentsEmployment)) {
                    Global.utils.showErrorSnakeBar(getActivity(), "Please fill all fields");
                    return;
                }

                Global.equivalenceInitiateCase.setFirstName(firstName);
                Global.equivalenceInitiateCase.setLastName(lastName);
                Global.equivalenceInitiateCase.setDob(dob);
                Global.equivalenceInitiateCase.setFatherName(fatherName);
                Global.equivalenceInitiateCase.setCnic(cnic);
                Global.equivalenceInitiateCase.setUserTitle(userTitle);
                Global.equivalenceInitiateCase.setDomicileID(domicileID);
                Global.equivalenceInitiateCase.setPob(pob);
                Global.equivalenceInitiateCase.setpAddress(pAddress);
                Global.equivalenceInitiateCase.setpCity(pCity);
                Global.equivalenceInitiateCase.setpProvinceId(pProvinceId);
                Global.equivalenceInitiateCase.setpCountryId(pCountryId);
                Global.equivalenceInitiateCase.setcAddress(cAddress);
                Global.equivalenceInitiateCase.setcCity(cCity);
                Global.equivalenceInitiateCase.setcProvinceId(cProvinceId);
                Global.equivalenceInitiateCase.setcCountryId(cCountryId);
                Global.equivalenceInitiateCase.setPhone(phone);
                Global.equivalenceInitiateCase.setMobile(mobile);
                Global.equivalenceInitiateCase.setEmail(email);
                Global.equivalenceInitiateCase.setPassportSizImage(passportSizImage);
                Global.equivalenceInitiateCase.setPassword(password);
                Global.equivalenceInitiateCase.setNationality(nationality);
                Global.equivalenceInitiateCase.setParentsEmployment(parentsEmployment);
                Global.equivalenceInitiateCase.setFatherCnic(fatherCnic);
                Global.equivalenceInitiateCase.setFatherAddress(fatherAddress);
                Global.equivalenceInitiateCase.setNameOfOrganization(nameOfOrganization);
                Global.equivalenceInitiateCase.setIncomAppLink(incomAppLink);
                Global.equivalenceInitiateCase.setThirdParty(Global.eqType);

                //Global.utils.showCustomLoadingDialog(getActivity());
                //volleyRequestController.equivalenceInitiateCase();
                //callEquivalenceInitiateCaseApi(getActivity());
                viewModel.callEquivalenceInitiateCaseApi(getActivity());
            }
        });

        layoutBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                parentsEmployment = radioButton.getText().toString();
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
        title_spinner_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.title_array, R.layout.spinner_item);
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
        CountriesAdapter spinner_nationality_adapter = new CountriesAdapter(getActivity(), R.layout.spinner_item, Global.getDetailsEquivalence.getResult().getCountries());
        layoutBinding.spinnerNationality.setAdapter(spinner_nationality_adapter);

        Country selectedNationality = null;
        for (int i = 0; i < Global.getDetailsEquivalence.getResult().getCountries().size(); i++) {
            if (Global.userLoginResponse.getResult().getCustomerProfile().getcCountryId().equalsIgnoreCase(Global.getDetailsEquivalence.getResult().getCountries().get(i).getId().toString())) {
                selectedNationality = Global.getDetailsEquivalence.getResult().getCountries().get(i);
                break;
            }
        }
        int spinnerNationalityPosition = spinner_nationality_adapter.getPosition(selectedNationality);
        layoutBinding.spinnerNationality.setSelection(spinnerNationalityPosition);

        layoutBinding.spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nationality = Global.getDetailsEquivalence.getResult().getCountries().get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //SPINNER COUNTRY
        CountriesAdapter arrayAdapter3 = new CountriesAdapter(getActivity(), R.layout.spinner_item,
                Global.getDetailsEquivalence.getResult().getCountries());
        layoutBinding.spinnerCountry.setAdapter(arrayAdapter3);

        Country selectedCountry = null;
        for (int i = 0; i < Global.getDetailsEquivalence.getResult().getCountries().size(); i++) {
            if (Global.userLoginResponse.getResult().getCustomerProfile().getcCountryId().equalsIgnoreCase(Global.getDetailsEquivalence.getResult().getCountries().get(i).getId().toString())) {
                selectedCountry = Global.getDetailsEquivalence.getResult().getCountries().get(i);
                break;
            }
        }
        int spinnerPosition = arrayAdapter3.getPosition(selectedCountry);
        layoutBinding.spinnerCountry.setSelection(spinnerPosition);

        layoutBinding.spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                country = Global.getDetailsEquivalence.getResult().getCountries().get(position).getName();
                country_Id = Global.getDetailsEquivalence.getResult().getCountries().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //PROVINCE SPINNER
        ProvinceAdapter arrayAdapter1 = new ProvinceAdapter(getActivity(), R.layout.spinner_item, Global.getDetailsEquivalence.getResult().getProvince());
        layoutBinding.spinnerProvince.setAdapter(arrayAdapter1);

        Province selectedProvince = null;
        for (int i = 0; i < Global.getDetailsEquivalence.getResult().getProvince().size(); i++) {
            if (Global.userLoginResponse.getResult().getCustomerProfile().getcProvinceId().equalsIgnoreCase(Global.getDetailsEquivalence.getResult().getProvince().get(i).getId().toString())) {
                selectedProvince = Global.getDetailsEquivalence.getResult().getProvince().get(i);
                break;
            }
        }
        int spinnerPosition1 = arrayAdapter1.getPosition(selectedProvince);
        layoutBinding.spinnerProvince.setSelection(spinnerPosition1);

        layoutBinding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                province = Global.getDetailsEquivalence.getResult().getProvince().get(position).getName();
                province_Id = Global.getDetailsEquivalence.getResult().getProvince().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        //SPINNER DOMICILE
        ProvinceAdapter arrayAdapter2 = new ProvinceAdapter(getActivity(), R.layout.spinner_item, Global.getDetailsEquivalence.getResult().getProvince());
        layoutBinding.spinnerCertificate.setAdapter(arrayAdapter2);

        Province selectedDomicile = null;
        for (int i = 0; i < Global.getDetailsEquivalence.getResult().getProvince().size(); i++) {
            if (Global.userLoginResponse.getResult().getCustomerProfile().getDomicile().equalsIgnoreCase(Global.getDetailsEquivalence.getResult().getProvince().get(i).getId().toString())) {
                selectedDomicile = Global.getDetailsEquivalence.getResult().getProvince().get(i);
                break;
            }
        }
        int spinnerPosition2 = arrayAdapter2.getPosition(selectedDomicile);
        layoutBinding.spinnerCertificate.setSelection(spinnerPosition2);

        layoutBinding.spinnerCertificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                domicile = Global.getDetailsEquivalence.getResult().getProvince().get(position).getName();
                domicile_Id = Global.getDetailsEquivalence.getResult().getProvince().get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub;
            }
        });

        observers();
        return layoutBinding.getRoot();
    }

    private void observers() {
        viewModel.getGetEquivalenceInitialCase().observe(getActivity(), response -> {
            if (response != null) {
                if (response.getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                    Global.equivalenceInitiateCaseResponse = response;
                    Global.caseIdQualificationEQ = response.getResult().getIntiateCaseResponseDetails().getCaseId().toString();

                    ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(2);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.equivalence_fragment_container, new EquivalenceEducationDetailsFragment()).addToBackStack(null).commit();

                } else {
                    Global.utils.showErrorSnakeBar(getActivity(), response.getResult().getResponseMessage());
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

    public void callEquivalenceInitiateCaseApi(Activity activity) {
        if (Constants.isInternetConnected(activity)) {
            Global.utils.showCustomLoadingDialog(activity);

            JsonObject params = new JsonObject();
            params.addProperty("firstName", Global.equivalenceInitiateCase.getFirstName());
            params.addProperty("lastName", Global.equivalenceInitiateCase.getLastName());
            params.addProperty("dob", Global.equivalenceInitiateCase.getDob());
            params.addProperty("fatherName", Global.equivalenceInitiateCase.getFatherName());
            params.addProperty("cnic", Global.equivalenceInitiateCase.getCnic());
            params.addProperty("title", Global.equivalenceInitiateCase.getUserTitle());
            params.addProperty("domicile", Global.equivalenceInitiateCase.getDomicileID());
            params.addProperty("birthPlace", Global.equivalenceInitiateCase.getPob());
            params.addProperty("pAdd", Global.equivalenceInitiateCase.getpAddress());
            params.addProperty("pCity", Global.equivalenceInitiateCase.getpCity());
            params.addProperty("pProvinceId", Global.equivalenceInitiateCase.getpProvinceId());
            params.addProperty("pCountryId", Global.equivalenceInitiateCase.getpCountryId());
            params.addProperty("cAdd", Global.equivalenceInitiateCase.getcAddress());
            params.addProperty("cCity", Global.equivalenceInitiateCase.getcCity());
            params.addProperty("cProvinceId", Global.equivalenceInitiateCase.getcProvinceId());
            params.addProperty("cCountryId", Global.equivalenceInitiateCase.getcCountryId());
            params.addProperty("phone", Global.equivalenceInitiateCase.getPhone());
            params.addProperty("mobile", Global.equivalenceInitiateCase.getMobile());
            params.addProperty("email", Global.equivalenceInitiateCase.getEmail());
            params.addProperty("passport_siz_image", Global.equivalenceInitiateCase.getPassportSizImage());
            params.addProperty("password", Global.equivalenceInitiateCase.getPassword());
            params.addProperty("nationality", Global.equivalenceInitiateCase.getNationality());
            params.addProperty("presentEmploymentOfParents", Global.equivalenceInitiateCase.getParentsEmployment());
            params.addProperty("fatherCnic", Global.equivalenceInitiateCase.getFatherCnic());
            params.addProperty("parentsPermanentAddress", Global.equivalenceInitiateCase.getFatherAddress());
            params.addProperty("parentsNameOfTheOrganization", Global.equivalenceInitiateCase.getNameOfOrganization());
            params.addProperty("incomAppLink", Global.equivalenceInitiateCase.getIncomAppLink());
            params.addProperty("ThirdParty", Global.equivalenceInitiateCase.getThirdParty());


            APIInterface apiInterface = APIClient.getClient(Constants.BASE_URL);
            Call<IntiateCase> call = apiInterface.callEquivalenceIntiateCase(params);

            call.enqueue(new Callback<IntiateCase>() {
                @Override
                public void onResponse(Call<IntiateCase> call, Response<IntiateCase> response) {
                    Global.utils.hideCustomLoadingDialog();

                    if (response.isSuccessful()) {
                        if (response.body().getResult().getResponseCode().equals(Constants.IBCC_SUCCESS_CODE)) {
                            Global.equivalenceInitiateCaseResponse = response.body();
                            Global.caseIdQualificationEQ = response.body().getResult().getIntiateCaseResponseDetails().getCaseId().toString();

                            ApplyEquivalenceActivity.stepIndicator.setCurrentStepPosition(2);

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.equivalence_fragment_container, new EquivalenceEducationDetailsFragment()).addToBackStack(null).commit();

                        } else {
                            Global.utils.showErrorSnakeBar(getActivity(), response.body().getResult().getResponseMessage());
                        }
                    } else {
                        Toast.makeText(activity, "Oopps! something went wrong / Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<IntiateCase> call, Throwable t) {
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
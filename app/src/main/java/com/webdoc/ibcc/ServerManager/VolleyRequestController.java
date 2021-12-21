package com.webdoc.ibcc.ServerManager;

import android.app.Activity;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.DocDetailEQ_Model;
import com.webdoc.ibcc.DataModel.EducationDetailModel;
import com.webdoc.ibcc.DataModel.LoginUser;
import com.webdoc.ibcc.DataModel.RegisterUser;
import com.webdoc.ibcc.Essentails.Constants;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VolleyRequestController {
    Activity context;
    PostApiResultManager postApiResultManager;
    GetApiResultManager getApiResultManager;

    public VolleyRequestController(Activity context) {
        this.context = context;
        postApiResultManager = new PostApiResultManager(context);
        getApiResultManager = new GetApiResultManager(context);
    }

    public void Pdf() {
        JSONObject params = new JSONObject();
        getApiResultManager.jsonParse(Constants.PDF, params);
    }

    public void GetDetailsEquivalence() {
        JSONObject params = new JSONObject();
        postApiResultManager.jsonParse(Constants.GETDETAILSEQUIVALENCE, params);
    }

    public void CheckUser(String email, String cnic) {
        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("cnic", cnic);

        } catch (JSONException e) {
            System.out.println("Error : Check User Registration " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.CHECKREGISTRATION, params);
    }

    public void UserLogin(LoginUser dataModel) {
        JSONObject params = new JSONObject();
        try {
            params.put("password", dataModel.getPassword());
            params.put("type", dataModel.getType());

            if (dataModel.getType().equalsIgnoreCase("Email")) {
                params.put("email", dataModel.getEmail());
            } else {
                params.put("email", dataModel.getCnic());
            }

        } catch (JSONException e) {
            System.out.println("Error : Login user" + e.toString());
        }
        postApiResultManager.jsonParse(Constants.USERLOGIN, params);
    }

    public void UserRegister(RegisterUser myDataModel) {
        JSONObject params = new JSONObject();
        try {
            params.put("first_name", Global.registerUser.getFirstName());
            params.put("password", Global.registerUser.getPassword());
            params.put("last_name", Global.registerUser.getLastName());
            params.put("dob", Global.registerUser.getDob());
            params.put("father_name", Global.registerUser.getFatherName());
            params.put("cnic", Global.registerUser.getCnic());
            params.put("title", Global.registerUser.getTitle());
            params.put("domicile", Global.registerUser.getDomicile());
            // params.put("birth_place", Global.registerUser.getCountry());
            params.put("p_add", Global.registerUser.getAddress());                      //p= permanent address
            params.put("p_city", Global.registerUser.getCity());
            params.put("p_province_id", Global.registerUser.getProvinceId());
            params.put("p_country_id", Global.registerUser.getCountryId());
            params.put("c_add", Global.registerUser.getAddress());                      //c= current address
            params.put("c_city", Global.registerUser.getCity());
            params.put("c_province_id", Global.registerUser.getProvinceId());
            params.put("c_country_id", Global.registerUser.getCountryId());
            params.put("phone", Global.registerUser.getPhoneNumber());
            params.put("mobile", Global.registerUser.getPhoneNumber());
            params.put("email", Global.registerUser.getEmail());
            params.put("passport_siz_image", Global.timestamp + ".png");
            params.put("nationality", Global.registerUser.getCountry());

        } catch (JSONException e) {
            System.out.println("Error : Register User" + e.toString());
        }
        postApiResultManager.jsonParse(Constants.USERREGISTER, params);
    }

    public void UpdateProfile(String firstName, String lastName, String dob, String fatherName,
                              String title, String domicileID, String pAddress, String pCity,
                              String pProvinceId, String pCountryId, String cAddress, String cCity,
                              String cProvinceId, String cCountryId, String nationality) {
        JSONObject params = new JSONObject();
        try {
            // params.put("id", String.valueOf(Global.userLoginResponse.getResult().getCustomerProfile().getId()));
            params.put("id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            params.put("first_name", firstName);
            params.put("last_name", lastName);
            params.put("dob", dob);
            params.put("father_name", fatherName);
            params.put("title", title);
            params.put("domicile", domicileID);
            params.put("p_add", pAddress);
            params.put("p_city", pCity);
            params.put("p_province_id", pProvinceId);
            params.put("p_country_id", pCountryId);
            params.put("c_add", cAddress);
            params.put("c_city", cCity);
            params.put("c_province_id", cProvinceId);
            params.put("c_country_id", cCountryId);
            params.put("passport_siz_image", " ");
            params.put("nationality", nationality);

            // System.out.println("Params : UpdateProfile" + params);
        } catch (JSONException e) {
            System.out.println("Error : UpdateProfile" + e.toString());
        }
        postApiResultManager.jsonParse(Constants.UPDATEPROFILE, params);
    }

    public void step1(String firstName, String lastName, String dob, String fatherName, String cnic,
                      String title, String domicileId, String pAddress, String pCity, String pProvinceId,
                      String pCountryId, String cAddress, String cCity, String cProvinceId, String cCountryId,
                      String phone, String mobileNo, String email) {
        JSONObject params = new JSONObject();
        try {
            params.put("id", Global.userLoginResponse.getResult().getCustomerProfile().getId());   //2004
            params.put("first_name", firstName);
            params.put("last_name", lastName);
            params.put("dob", dob);
            params.put("father_name", fatherName);
            params.put("cnic", cnic);
            params.put("title", title);
            params.put("domicile", domicileId);
            params.put("p_add", pAddress);
            params.put("p_city", pCity);
            params.put("p_province_id", pProvinceId);
            params.put("p_country_id", pCountryId);
            params.put("c_add", cAddress);
            params.put("c_city", cCity);
            params.put("c_province_id", cProvinceId);
            params.put("c_country_id", cCountryId);
            params.put("phone", phone);
            params.put("mobile", mobileNo);
            params.put("email", email);
            params.put("passport_siz_image", " ");

        } catch (JSONException e) {
            System.out.println("Error : Check Personal Info " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.STEP1, params);
    }

    public void addEducation(EducationDetailModel dataModel) {
        JSONObject params = new JSONObject();
        try {
            params.put("certificate_id", dataModel.getCertificate_id());
            params.put("program_id", dataModel.getProgram_id());
            params.put("group_id", dataModel.getGroup_id());
            params.put("board_id", dataModel.getBoard_id());
            params.put("year", dataModel.getPassing_year());
            params.put("marks", dataModel.getMarks_obtained());
            params.put("total", dataModel.getTotal_marks());
            params.put("session", dataModel.getSession());
            params.put("roll_number", dataModel.getRoll_number());
            params.put("case_id", dataModel.getCase_id());

            System.out.println("Params: Add Education " + params);
        } catch (JSONException e) {
            System.out.println("Error : Add Education " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.ADDEDUCATION, params);
    }

    public void DeleteEducation(String eDocId, String eCaseId) {
        JSONObject params = new JSONObject();
        try {
            params.put("document_id", eDocId);
            params.put("case_id", eCaseId);

        } catch (JSONException e) {
            System.out.println("Error : Delete Education " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.DELETEEDUCATION, params);
    }

    public void EditEducation(String eDocId, String certId, String proId, String groId, String broId,
                              String year, String obtainedMarks, String totalMarks, String session,
                              String rollNo, String eCaseId) {
        JSONObject params = new JSONObject();
        try {
            params.put("document_id", eDocId);
            params.put("certificate_id", certId);
            params.put("program_id", proId);
            params.put("group_id", groId);

            params.put("board_id", broId);
            params.put("year", year);
            params.put("marks", obtainedMarks);
            params.put("total", totalMarks);
            params.put("session", session);
            params.put("roll_number", rollNo);
            params.put("case_id", eCaseId);

        } catch (JSONException e) {
            System.out.println("Error : Edit Education " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.EDITEDUCATION, params);
    }

    public void AddDocumentDetails(List<DocumentSelectionModel> documentSelectionModelList) {
        JSONObject params = new JSONObject();
        try {
            JSONArray documentArray = new JSONArray();
            for (int i = 0; i < documentSelectionModelList.size(); i++) {
                JSONObject docItem = new JSONObject();

                //docItem.put("document_id", documentSelectionModelList.get(i).getDocId());
                docItem.put("document_id", documentSelectionModelList.get(i).getDocId());
                docItem.put("case_id", documentSelectionModelList.get(i).getCaseId());
                docItem.put("totalAmount", documentSelectionModelList.get(i).getTotalAmount());   //all document total amount

                JSONArray document_detailsArray = new JSONArray();
                for (DocumentDetailModel item : documentSelectionModelList.get(i).getDetailModelList()) {
                    JSONObject docDetailItem = new JSONObject();
                    docDetailItem.put("certificate_type", item.getCertificateType());
                    docDetailItem.put("original_included", item.getOriginalIncluded());
                    docDetailItem.put("document_type", item.getDocumentType());

                    docDetailItem.put("ticket_number", item.getTicketNo());
                    docDetailItem.put("ticket_date", item.getDate());
                    /*docDetailItem.put("ticket_number", "dfg");
                    docDetailItem.put("ticket_date", "0000-00-0");*/

                    docDetailItem.put("no_of_copies", item.getTotalCopies());
                    docDetailItem.put("amount", String.valueOf(item.getAmount()));     //only item ki amount

                    document_detailsArray.put(docDetailItem);
                }

                //params.put("document_details", document_detailsArray);
                docItem.put("document_details", document_detailsArray);
                documentArray.put(docItem);
            }

            params.put("document", documentArray);
            System.out.println("my selected documents" + params);

        } catch (JSONException e) {
            System.out.println("Error : Add Document Details " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.ADDDOCUMENTDETAILS, params);
    }

    public void ViewDetails() {
        JSONObject params = new JSONObject();
        try {
            params.put("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            //params.put("user_id", "1995");

        } catch (JSONException e) {
            System.out.println("Error : ViewDetails" + e.toString());
        }
        postApiResultManager.jsonParse(Constants.VIEWDETAILS, params);
    }

    public void CancelAppointment(String caseId) {
        JSONObject params = new JSONObject();
        try {
            params.put("case_id", caseId);
            params.put("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());

        } catch (JSONException e) {
            System.out.println("Error : Cancel Appointment" + e.toString());
        }
        postApiResultManager.jsonParse(Constants.CANCELAPPOINTMENT, params);
    }

    public void ViewIncomplete() {
        JSONObject params = new JSONObject();
        try {
            params.put("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            // params.put("user_id", "1995");//2019

        } catch (JSONException e) {
            System.out.println("Error : View Incomplete" + e.toString());
        }
        postApiResultManager.jsonParse(Constants.VIEWINCOMPLETE, params);
    }

    public void ViewIncompleteDetails(String caseID) {
        JSONObject params = new JSONObject();
        try {
            params.put("case_id", caseID);

        } catch (JSONException e) {
            System.out.println("Error : View Incomplete Details" + e.toString());
        }
        postApiResultManager.jsonParse(Constants.VIEWINCOMPLETEDETAILS, params);
    }

    public void saveCourierDetails() {
        JSONObject params = new JSONObject();
        try {
            params.put("center", String.valueOf(Global.center));
            params.put("case_id", Global.caseId);
            params.put("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            params.put("consignment_id", Global.saveBookingResponse.getCnno());

            System.out.println("Save Courier Details " + params);

        } catch (JSONException e) {
            System.out.println("Error : Save Courier Details " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.SAVECOURIERDETIALS, params);
    }

    public void saveCourierDetialsEQ() {
        JSONObject params = new JSONObject();
        try {
            params.put("center", String.valueOf(Global.equivalenceGenerateAppCenter.getName()));
            params.put("case_id", Global.caseId);
            params.put("user_id", Global.userLoginResponse.getResult().getCustomerProfile().getId());
            params.put("consignment_id", Global.saveBookingResponse.getCnno());

            System.out.println("Save Courier Details EQ " + params);

        } catch (JSONException e) {
            System.out.println("Error : Save Courier Details EQ " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.SAVECOURIERDETIALSEQ, params);
    }

    /*TODO: EQUIVALENCE*/
    public void equivalenceInitiateCase() {
        JSONObject params = new JSONObject();
        try {
            params.put("firstName", Global.equivalenceInitiateCase.getFirstName());
            params.put("lastName", Global.equivalenceInitiateCase.getLastName());
            params.put("dob", Global.equivalenceInitiateCase.getDob());
            params.put("fatherName", Global.equivalenceInitiateCase.getFatherName());
            params.put("cnic", Global.equivalenceInitiateCase.getCnic());
            params.put("title", Global.equivalenceInitiateCase.getUserTitle());
            params.put("domicile", Global.equivalenceInitiateCase.getDomicileID());
            params.put("birthPlace", Global.equivalenceInitiateCase.getPob());
            params.put("pAdd", Global.equivalenceInitiateCase.getpAddress());
            params.put("pCity", Global.equivalenceInitiateCase.getpCity());
            params.put("pProvinceId", Global.equivalenceInitiateCase.getpProvinceId());
            params.put("pCountryId", Global.equivalenceInitiateCase.getpCountryId());
            params.put("cAdd", Global.equivalenceInitiateCase.getcAddress());
            params.put("cCity", Global.equivalenceInitiateCase.getcCity());
            params.put("cProvinceId", Global.equivalenceInitiateCase.getcProvinceId());
            params.put("cCountryId", Global.equivalenceInitiateCase.getcCountryId());
            params.put("phone", Global.equivalenceInitiateCase.getPhone());
            params.put("mobile", Global.equivalenceInitiateCase.getMobile());
            params.put("email", Global.equivalenceInitiateCase.getEmail());
            params.put("passport_siz_image", Global.equivalenceInitiateCase.getPassportSizImage());
            params.put("password", Global.equivalenceInitiateCase.getPassword());
            params.put("nationality", Global.equivalenceInitiateCase.getNationality());
            params.put("presentEmploymentOfParents", Global.equivalenceInitiateCase.getParentsEmployment());
            params.put("fatherCnic", Global.equivalenceInitiateCase.getFatherCnic());
            params.put("parentsPermanentAddress", Global.equivalenceInitiateCase.getFatherAddress());
            params.put("parentsNameOfTheOrganization", Global.equivalenceInitiateCase.getNameOfOrganization());
            params.put("incomAppLink", Global.equivalenceInitiateCase.getIncomAppLink());
            params.put("ThirdParty", Global.equivalenceInitiateCase.getThirdParty());

            System.out.println("Equivalence Initiate Case " + params.toString());
        } catch (JSONException e) {
            System.out.println("Error : Equivalence Initiate Case " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.INTIATECASE, params);
    }

    public void equivalenceAddQualification() {
        JSONObject params = new JSONObject();
        try {
            params.put("countryId", Integer.parseInt(Global.equivalenceAddQualification.getCountryId()));
            params.put("session", Global.equivalenceAddQualification.getSession());
            params.put("fatherCnic", Global.equivalenceAddQualification.getFatherCnic());
            params.put("fatherName", Global.equivalenceAddQualification.getFatherName());
            params.put("email", Global.equivalenceAddQualification.getEmail());
            params.put("titleOfQualification", Global.equivalenceAddQualification.getTitleOfQualification());
            params.put("mailingAddress", Global.equivalenceAddQualification.getMailingAddress());
            params.put("gradingSystemId", Integer.parseInt(Global.equivalenceAddQualification.getGradingSystemId()));
            params.put("groupId", Integer.parseInt(Global.equivalenceAddQualification.getGroupId()));
            params.put("presentEmploymentOfParents", Global.equivalenceAddQualification.getPresentEmploymentOfParents());
            params.put("purposeOfEquivalence", Global.equivalenceAddQualification.getPurposeOfEquivalence());
            params.put("parentsNameOfTheOrganization", Global.equivalenceAddQualification.getParentsNameOfTheOrganization());
            params.put("qualificationId", Integer.parseInt(Global.equivalenceAddQualification.getQualificationId()));
            params.put("otherExaminingBody", Global.equivalenceAddQualification.getOtherExaminingBody());
            params.put("examinationSystem", Global.equivalenceAddQualification.getExaminationSystem());
            params.put("caseId", Integer.parseInt(Global.equivalenceAddQualification.getCaseId()));
            params.put("parentsPermanentAddress", Global.equivalenceAddQualification.getPresentEmploymentOfParents());
            params.put("nameOfTheInstitution", Global.equivalenceAddQualification.getNameOfTheInstitution());
            params.put("telNo", Global.equivalenceAddQualification.getTelNo());
            params.put("examiningBody", Global.equivalenceAddQualification.getExaminingBody());

            // params.put("imagesEductaion", Global.equivalenceAddQualification.getImagesEductaionList());
            //params.put("subjectEductaion", Global.equivalenceAddQualification.getSubjectEducationList());

            JSONArray documentArray = new JSONArray();
            for (int i = 0; i < Global.equivalenceAddQualification.getImagesEductaionList().size(); i++) {
                JSONObject imgItem = new JSONObject();
                imgItem.put("imagename", Global.imagesEducationlList.get(i));
                // imgItem.put("name", Global.selectedFilesList.get(i).getFileName());   //Global.timestamp + ".png"
                //imgItem.put("type", Global.selectedFilesList.get(i).getFileType());   //all document total amount
                documentArray.put(imgItem);
            }
            params.put("imagesEductaion", documentArray);


            //working on this travelling part....
            JSONArray documentTravellingArray = new JSONArray();

            for (int k = 0; k < Global.equivalenceAddQualification.getImagesTravellingList().size(); k++) {
                JSONObject imgItem = new JSONObject();
                imgItem.put("imagename", Global.imagesTravellinglList.get(k));  //20210702065122.png
                documentTravellingArray.put(imgItem);
            }
            params.put("imagestravelling", documentTravellingArray);

            JSONArray subjectArray = new JSONArray();
            for (int j = 0; j < Global.equivalenceAddQualification.getSubjectEducationList().size(); j++) {
                JSONObject subjItem = new JSONObject();
                subjItem.put("subjectId", Global.selectedGradeList.get(j).getId());
                subjItem.put("marksgrades", Global.selectedGradeList.get(j).getName());

                subjectArray.put(subjItem);
            }
            params.put("subjectEductaion", subjectArray);


            System.out.println("AddQualification Params: " + params);

        } catch (JSONException e) {
            System.out.println("Error : Equivalence Add Qualification " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.ADDQUALIFICATIONEQ, params);
    }

    public void equivalenceEditQualification() {
        JSONObject params = new JSONObject();
        try {
            params.put("docId", Integer.parseInt(Global.equivalenceAddQualification.getQualificationId()));
            params.put("countryId", Integer.parseInt(Global.equivalenceAddQualification.getCountryId()));
            params.put("session", Global.equivalenceAddQualification.getSession());
            params.put("fatherCnic", Global.equivalenceAddQualification.getFatherCnic());
            params.put("fatherName", Global.equivalenceAddQualification.getFatherName());
            params.put("email", Global.equivalenceAddQualification.getEmail());
            params.put("titleOfQualification", Global.equivalenceAddQualification.getTitleOfQualification());
            params.put("mailingAddress", Global.equivalenceAddQualification.getMailingAddress());
            params.put("gradingSystemId", Integer.parseInt(Global.equivalenceAddQualification.getGradingSystemId()));
            params.put("groupId", Integer.parseInt(Global.equivalenceAddQualification.getGroupId()));
            params.put("presentEmploymentOfParents", Global.equivalenceAddQualification.getPresentEmploymentOfParents());
            params.put("purposeOfEquivalence", Global.equivalenceAddQualification.getPurposeOfEquivalence());
            params.put("parentsNameOfTheOrganization", Global.equivalenceAddQualification.getParentsNameOfTheOrganization());
            params.put("qualificationId", Integer.parseInt(Global.equivalenceAddQualification.getQualificationId()));
            params.put("otherExaminingBody", Global.equivalenceAddQualification.getOtherExaminingBody());
            params.put("examinationSystem", Global.equivalenceAddQualification.getExaminationSystem());
            params.put("caseId", Integer.parseInt(Global.equivalenceAddQualification.getCaseId()));
            params.put("parentsPermanentAddress", Global.equivalenceAddQualification.getPresentEmploymentOfParents());
            params.put("nameOfTheInstitution", Global.equivalenceAddQualification.getNameOfTheInstitution());
            params.put("telNo", Global.equivalenceAddQualification.getTelNo());
            params.put("examiningBody", Global.equivalenceAddQualification.getExaminingBody());

            // params.put("imagesEductaion", Global.equivalenceAddQualification.getImagesEductaionList());
            //params.put("subjectEductaion", Global.equivalenceAddQualification.getSubjectEducationList());


            JSONArray documentArray = new JSONArray();
            for (int i = 0; i < Global.equivalenceAddQualification.getImagesEductaionList().size(); i++) {
                JSONObject imgItem = new JSONObject();
                imgItem.put("imagename", Global.imagesEducationlList.get(i));

                documentArray.put(imgItem);
            }
            params.put("imagesEductaion", documentArray);


            //working on this travelling part....
            JSONArray documentTravellingArray = new JSONArray();

            for (int k = 0; k < Global.equivalenceAddQualification.getImagesTravellingList().size(); k++) {
                JSONObject imgItem = new JSONObject();
                imgItem.put("imagename", Global.imagesTravellinglList.get(k));  //20210702065122.png
                documentTravellingArray.put(imgItem);
            }
            params.put("imagestravelling", documentTravellingArray);

            JSONArray subjectArray = new JSONArray();
            for (int j = 0; j < Global.equivalenceAddQualification.getSubjectEducationList().size(); j++) {
                JSONObject subjItem = new JSONObject();
                subjItem.put("subjectId", Global.selectedGradeList.get(j).getId());
                subjItem.put("marksgrades", Global.selectedGradeList.get(j).getName());

                subjectArray.put(subjItem);
            }
            params.put("subjectEductaion", subjectArray);


            System.out.println("AddQualification Params: " + params);

        } catch (JSONException e) {
            System.out.println("Error : Equivalence Add Qualification " + e.toString());
        }

        postApiResultManager.jsonParse(Constants.EDITQUALIFICATIONEQ, params);
    }

    public void removeQualification(int docId, int caseId) {
        JSONObject params = new JSONObject();
        try {
            params.put("docId", docId);
            params.put("caseId", caseId);

        } catch (JSONException e) {
            System.out.println("Error : REMOVE QUALIFICATION  " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.REMOVEQUALIFICATION, params);
    }

    public void saveDocumentDetails(List<DocDetailEQ_Model> dataModel) {
        JSONObject params = new JSONObject();
        try {
            params.put("caseId", dataModel.get(0).getCaseID());

            JSONArray docArray = new JSONArray();
            for (int j = 0; j < Global.equivalenceQualificationList.size(); j++) {

                JSONObject docItem = new JSONObject();
                docItem.put("docId", dataModel.get(j).getDocId());
                docItem.put("amount", dataModel.get(j).getAmount());
                docItem.put("averageMarks", 0);
                docItem.put("documentType", dataModel.get(j).getDocumentType());
                docItem.put("equivalenceOfCertificates", dataModel.get(j).getQofCert());
                docItem.put("equivalenceOfCertificatesType", dataModel.get(j).getqOfCertType());

                docItem.put("finalObtainedMarks", dataModel.get(j).getfObtMarks());
                docItem.put("finalTotalMarks", dataModel.get(j).getfTotalMarks());
                docItem.put("obtainedMarks", dataModel.get(j).getObtainedMarks());
                docItem.put("totalMarks", dataModel.get(j).getTotalMarks());
                docItem.put("percentage", dataModel.get(j).getPercentage());

                docItem.put("previousTicketNumber", "");
                docItem.put("ticketDate", "2021-06-19T06:43:05.343Z");
                docItem.put("ticketNumber", "");
                docItem.put("urgent", dataModel.get(j).getUrgent());

                docArray.put(docItem);
            }
            params.put("saveDocumentDetailsList", docArray);

            System.out.println("saveDocumentDetailsParams: " + params);

        } catch (JSONException e) {
            System.out.println("Error : Save Document Details  " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.SAVEDOCUMENTDETAILS, params);
    }

    public void ViewAppointmentsEQ() {

        JSONObject params = new JSONObject();
        try {
          /*  params.put("email", "faiza12@gmail.com");
            params.put("cnic","3740594999501");*/

            params.put("email", Global.userLoginResponse.getResult().getCustomerProfile().getEmail());
            params.put("cnic", Global.userLoginResponse.getResult().getCustomerProfile().getCnic());

        } catch (JSONException e) {
            System.out.println("Error : VIEW APPOINTMENTS EQ " + e.toString());
        }
        postApiResultManager.jsonParse(Constants.VIEWAPPOINTMENTSEQ, params);
    }

    public void ViewIncompleteAppointmentEQ() {
        JSONObject params = new JSONObject();
        try {
            params.put("email", Global.userLoginResponse.getResult().getCustomerProfile().getEmail());
            params.put("cnic", Global.userLoginResponse.getResult().getCustomerProfile().getCnic());

        } catch (JSONException e) {
            System.out.println("Error : View Incomplete Details EQ" + e.toString());
        }
        postApiResultManager.jsonParse(Constants.VIEWINCOMPLETEAPPOINTMENTEQ, params);
    }

    public void incompleteDetailsEQ(String caseID) {
        JSONObject params = new JSONObject();
        try {
            params.put("case_id", caseID);

        } catch (JSONException e) {
            System.out.println("Error :Incomplete Details EQ" + e.toString());
        }
        postApiResultManager.jsonParse(Constants.INCOMPLETEDETAILSEQ, params);
    }
}

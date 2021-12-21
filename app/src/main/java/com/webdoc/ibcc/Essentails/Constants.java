package com.webdoc.ibcc.Essentails;

import android.content.Context;
import android.net.ConnectivityManager;

public class Constants {
    /*TODO: URLS FOR DOWNLOADS */
    public static final String EQUIVALENCE_FORM_URL = "https://portal.webdoc.com.pk/Documents/Equivalence/equivalence%20Form%20-%202020.pdf";
    public static final String ATTESTATION_FORM_URL = "https://portal.webdoc.com.pk/Documents/Attestation/Attestation_Form.pdf";
    public static final String CONVERSION_FORM_URL = "https://portal.webdoc.com.pk/Documents/Download/Conversion%20Formula.pdf";
    public static final String EQUIVALENCE_FORM = "Equivalence Form";
    public static final String ATTESTATION_FORM = "Attestation Form";
    public static final String CONVERSION_FORMULA = "Conversion Formula";

    /*Live URL*/
    public static final String BASE_URL = "https://ibcc.webddocsystems.com/api/";

    //Testing URL
    //public static final String BASE_URL = "https://test.webddocsystems.com/api/";

    /*TODO: REST RESPONSES*/
    public static final String IBCC_SUCCESS_CODE = "0000";
    public static final String IBCC_FAILURE_CODE = "0001";
    public static final String IBCC_SUCCESS_MESSAGE = "Request successful.";

    /*TODO: IBCC API CONSTANTS */
    public static final String PDF = "Pdf";  //changed
    public static final String CHECKREGISTRATION = "Login/CheckRegistration"; //changed
    public static final String USERLOGIN = "Login/UserLogin";  //changed
    public static final String USERREGISTER = "Login/UserRegister";  //changed
    public static final String UPDATEPROFILE = "Login/UpdateProfile"; //changed
    public static final String STEP1 = "Steps/Step1"; //changed
    public static final String ADDEDUCATION = "Steps/AddEducation"; //changed
    public static final String DELETEEDUCATION = "Steps/DeleteEducation"; //changed
    public static final String EDITEDUCATION = "Steps/EditEducation"; //changed
    public static final String ADDDOCUMENTDETAILS = "Steps/AddDocumentDetails"; //changed
    public static final String VIEWDETAILS = "Steps/ViewDetails"; //changed
    public static final String CANCELAPPOINTMENT = "Steps/CancelAppointment"; //changed
    public static final String VIEWINCOMPLETEDETAILS = "Steps/ViewIncompleteDetails"; //changed
    public static final String VIEWINCOMPLETE = "Steps/ViewIncomplete";  //changed

    /*EQUIVALENCE*/
    public static final String GETDETAILSEQUIVALENCE = "Equivalence/GetDetailsEquivalence";  //changed
    public static final String INTIATECASE = "Equivalence/IntiateCase"; //changed
    public static final String ADDQUALIFICATIONEQ = "Equivalence/AddQualificationEQ";
    public static final String EDITQUALIFICATIONEQ = "Equivalence/EditQualificationEQ";
    public static final String REMOVEQUALIFICATION = "Equivalence/RemoveQualification"; //changed
    public static final String SAVEDOCUMENTDETAILS = "Equivalence/SaveDocumentDetails"; //changed
    public static final String VIEWAPPOINTMENTSEQ = "Equivalence/ViewAppointmentsEQ"; //changed
    public static final String VIEWINCOMPLETEAPPOINTMENTEQ = "Equivalence/ViewIncompleteAppointment"; //changed
    public static final String INCOMPLETEDETAILSEQ = "Equivalence/IncompleteDetailsEQ"; //changed

    /*https://attest.ibcc.edu.pk/images/uploadibcc.php*/
    public static final String PICTURE_UPLOAD_URL = "http://gmcars.co/Expense/uploadibcc.php";

    public static final String DOUCMENT_UPLOAD_URL = "http://equivalence.ibcc.edu.pk/uploaddocument.php";
    public static final String TRAVELLING_URL = "http://equivalence.ibcc.edu.pk/";

    /*CALL COURIER*/
    public static final String INQUIRY_API_BASEURL = "https://easypay.easypaisa.com.pk/easypay-service/rest/v4/";
    public static final String INQUIRYAPIMETHODNAME = "inquire-transaction";
    public static final String CALLCOURIER_BASEURL = "https://cod.callcourier.com.pk/";

    public static final String GETCITYLISTBYSERVICE = "API/CallCourier/GetCityListByService?serviceID=1"; //changed
    public static final String GETAREASBYCITY = "api/callcourier/GetAreasByCity?CityID="; //changed
    public static final String SAVEBOOKING = "api/CallCourier/SaveBooking?"; //changed
    public static final String SAVECOURIERDETIALS = "Steps/SaveCourierDetials"; //changed
    public static final String GETTACKINGHISTORY = "api/CallCourier/GetTackingHistory?cn=";
    public static final String SAVECOURIERDETIALSEQ = "Equivalence/SaveCourierDetialsEQ"; //changed

    //todo: JS Bank
    public static final String JSBANK_BASE_URL = "https://sandbox.jsbl.com/";
    public static final String JSAPI_PAYMENT_INQUIRY = "v2/accountinquiry-blb";

    public static boolean isInternetConnected(Context cntx) {
        ConnectivityManager cm = (ConnectivityManager) cntx.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
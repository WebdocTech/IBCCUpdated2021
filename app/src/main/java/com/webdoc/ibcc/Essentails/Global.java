package com.webdoc.ibcc.Essentails;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.DocumentSelection.DocDetailEQ_Model;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.EducationDetails.AddQualification.totalMarksModel;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels.DetailsEquivalenceNewModel;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.ReassignedCaseDetailsModels.CaseUploadedDocumentResponse;
import com.webdoc.ibcc.DashBoard.reAssignedCasses.modelclasses.SubjectsGradeModel;
import com.webdoc.ibcc.DataModel.EducationDetailModel;
import com.webdoc.ibcc.DataModel.LoginUser;
import com.webdoc.ibcc.DataModel.RegisterUser;
import com.webdoc.ibcc.Model.AddQualificationModel;
import com.webdoc.ibcc.Model.CategoriesModel;
import com.webdoc.ibcc.Model.DeleteParams;
import com.webdoc.ibcc.Model.DocumentDetailModel;
import com.webdoc.ibcc.Model.DocumentSelectionModel;
import com.webdoc.ibcc.Model.EquivalenceAddQualification;
import com.webdoc.ibcc.Model.EquivalenceFileModel;
import com.webdoc.ibcc.Model.EquivalenceInitiateCase;
import com.webdoc.ibcc.Model.EquivalenceTravelFileModel;
import com.webdoc.ibcc.Model.EventImageShowModel;
import com.webdoc.ibcc.Model.EventsModel;
import com.webdoc.ibcc.Model.InstructionsModel;
import com.webdoc.ibcc.Model.PaymentModel;
import com.webdoc.ibcc.Model.VideosModel;
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.ResoponseModel.easypaisaPAymentResponse.EasypaisaPAymentResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JsBankAuthApi;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.jsDebitPaymentModel.JsDebitPaymentResponseModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.jsbankdebitresponsemodel.JsDebitInqueryResponseModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JazzCash.ResponseModels.JazzCashResponseNew;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ResponseModels.JsGenerateOtpResponse.JsGenerateOtpResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.JsBankAccount.ResponseModels.SecondApiJSAccountResponse.SecondApiJSAccountResponse;
import com.webdoc.ibcc.Payment.PaymentMethods.OTCPayment.ResponseModels.OtcPaymentResponse;
import com.webdoc.ibcc.ResponseModels.AddDocumentDetailsResult.AddDocumentDetailsResult;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.AddEducationResult;
import com.webdoc.ibcc.ResponseModels.AddEducationResult.EducationDetail;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.AddQualificationEQ;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.CaseUploadedTravellingDocumentResponse;
import com.webdoc.ibcc.ResponseModels.AddQualificationEQ.QualificationSubjectResponse;
import com.webdoc.ibcc.ResponseModels.CancelAppointmentResult.CancelAppointmentResult;
import com.webdoc.ibcc.ResponseModels.DeleteEducationResult.DeleteEducationResult;
import com.webdoc.ibcc.ResponseModels.EditEducationResult.EditEducationResult;
import com.webdoc.ibcc.ResponseModels.EditQualificationEQ.EditQualificationEQ;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceGrade;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.EquivalenceSubject;
import com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence.GetDetailsEquivalence;
import com.webdoc.ibcc.ResponseModels.IncompleteDetailsEQ.IncompleteDetailsEQ;
import com.webdoc.ibcc.ResponseModels.IntiateCase.IntiateCase;
import com.webdoc.ibcc.ResponseModels.PdfResult.Center;
import com.webdoc.ibcc.ResponseModels.PdfResult.Cerftificate;
import com.webdoc.ibcc.ResponseModels.PdfResult.PdfResult;
import com.webdoc.ibcc.ResponseModels.PdfResult.Program;
import com.webdoc.ibcc.ResponseModels.RemoveQualificationEQ.RemoveQualificationEQ;
import com.webdoc.ibcc.ResponseModels.SaveBooking.SaveBooking;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetials.SaveCourierDetials;
import com.webdoc.ibcc.ResponseModels.SaveCourierDetialsEQ.SaveCourierDetialsEQ;
import com.webdoc.ibcc.ResponseModels.SaveDocumentDetailsEQ.SaveDocumentDetailsEQ;
import com.webdoc.ibcc.ResponseModels.SavePaymentInfo.SavePaymentInfo;
import com.webdoc.ibcc.ResponseModels.Step1Result.Step1Result;
import com.webdoc.ibcc.ResponseModels.UpdateProfileResult.UpdateProfileResult;
import com.webdoc.ibcc.ResponseModels.UserLoginResult.UserLoginResult;
import com.webdoc.ibcc.ResponseModels.UserRegisterResult.UserRegisterResult;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQ;
import com.webdoc.ibcc.ResponseModels.ViewAppointmentsEQ.ViewAppointmentsEQD;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.Detail;
import com.webdoc.ibcc.ResponseModels.ViewDetailsResult.ViewDetailsResult;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteAppointmentEQ.ViewIncompleteAppointmentEQ;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteDetailsResult.ViewIncompleteDetails;
import com.webdoc.ibcc.ResponseModels.ViewIncompleteResult.ViewIncompleteResult;
import com.webdoc.ibcc.ResponseModels.inquiryResult.InquiryResult;
import com.webdoc.ibcc.ResponseModels.updatePAymentInfoResult.UpdatePAymentInfoResult;
import com.webdoc.ibcc.Testing.phpResp.PhpResp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Global {

    public static ArrayList<CategoriesModel> categoryTitle = new ArrayList<>();
    public static ArrayList<PaymentModel> paymentTitle = new ArrayList<>();
    public static List<VideosModel> videosModel = new ArrayList<>();
    public static ArrayList<EventsModel> eventsModels = new ArrayList<>();
    public static SavePaymentInfo savePaymentInfo = new SavePaymentInfo();
    public static ArrayList<EventImageShowModel> eventImageShowModels = new ArrayList();
    public static ArrayList<InstructionsModel> instructionsModels = new ArrayList();
    public static EasypaisaPAymentResponse easypaisaPAymentResponse = new EasypaisaPAymentResponse();
    public static OtcPaymentResponse otcPaymentResponse = new OtcPaymentResponse();
    public static JsBankAuthApi authApiResp = new JsBankAuthApi();
    public static JsDebitInqueryResponseModel paymentInquiry = new JsDebitInqueryResponseModel();
    public static JsGenerateOtpResponse jsAccountGenerateOtp = new JsGenerateOtpResponse();
    public static SecondApiJSAccountResponse secondApiJSAccountResponse = new SecondApiJSAccountResponse();

    public static JsDebitPaymentResponseModel jsPaymentFinal = new JsDebitPaymentResponseModel();
    public static List<EquivalenceSubject> equivalenceSubjectList = new ArrayList<>();

    public static List<SubjectsGradeModel> subjectReassignGradeList = new ArrayList<>();
    public static  ArrayList<SubjectsGradeModel> marksList = new ArrayList<>();


    public static Detail selectedApptStatus = new Detail();

    /* TODO: Constant..*/
    public static int selectedImage;
    public static String selectedImageTitle;
    public static int selectedDoc;
    public static String loginType;
    public static String caseId, incompleteCaseId;
    public static int editEduPosition;
    public static int deleteEduPosition;
    public static int amount;
    public static String price;
    public static int ibccAmount, webdocAmount;
    public static String center = "";
    public static int cancelIncompleteApptPosition;
    public static int cancelAppointmentPosition;

    public static String courierFeeForReceipt, secuirtyFeeForReceipt, bankChargesForReceipt;

    public static InquiryResult inquiryResult = new InquiryResult();

    public static UpdatePAymentInfoResult updatePAymentInfoResult = new UpdatePAymentInfoResult();


    /* TODO: DATA MODELS..*/
    public static RegisterUser registerUser = new RegisterUser();
    public static LoginUser loginUser = new LoginUser();
    public static EducationDetailModel userEduDetail = new EducationDetailModel();
    /*TODO: Utils */
    public static Utils utils = new Utils();

    /*TODO: RESPONSE MODELS*/
    public static PdfResult pdfResponse = new PdfResult();
    public static UserRegisterResult userRegisterResponse = new UserRegisterResult();
    public static UserLoginResult userLoginResponse = new UserLoginResult();
    public static UpdateProfileResult updateProfileResponse = new UpdateProfileResult();
    public static Cerftificate selectedCertificate = new Cerftificate();
    public static Program ProgramList = new Program();
    public static Step1Result step1Response = new Step1Result();
    public static AddEducationResult addEducationResponse = new AddEducationResult();
    public static DeleteEducationResult deleteEducationResponse = new DeleteEducationResult();
    public static EducationDetail educationDetail = new EducationDetail();
    public static EditEducationResult editEducationResponse = new EditEducationResult();

    public static JazzCashResponseNew jazzCashResponseNew = new JazzCashResponseNew();

    public static AddDocumentDetailsResult addDocumentDetailsResponse = new AddDocumentDetailsResult();
    public static ViewDetailsResult viewDetailsResponse = new ViewDetailsResult();
    public static CancelAppointmentResult cancelAppointmentResponse = new CancelAppointmentResult();
    public static ViewIncompleteDetails viewIncompleteDetailsResponse = new ViewIncompleteDetails();
    public static ViewAppointmentsEQ viewAppointmentsEQResponse = new ViewAppointmentsEQ();

    public static ViewAppointmentsEQD selectedAppointEQ = new ViewAppointmentsEQD();
    public static Detail selectedAppointAtt = new Detail();
    public static IncompleteDetailsEQ incompleteDetailsEQResponse = new IncompleteDetailsEQ();


    public static ViewIncompleteResult viewIncompleteResponse = new ViewIncompleteResult();
    public static ViewIncompleteAppointmentEQ viewIncompleteAppointmentEQ = new ViewIncompleteAppointmentEQ();


    public static List<Cerftificate> selectedCerti = new ArrayList<>();
    public static List<DocumentSelectionModel> selDocument = new ArrayList<>();
    public static boolean isFromEquivalence = true;
    public static String newToken;
    public static String JS_Wallet_Account_Number;
    public static String EasyPaisaCreditDebitOrderId;
    public static String eqDateTime;
    public static boolean isOnline = true;
    public static String caseIdQualificationEQ;
    public static boolean isFromEditQualitifcation;
    public static boolean widthFromUpdateQualification;
    public static String UserIdForCase;
    public static String JSBankAccountNo;//for js bank account pAYMENT
    public static boolean ispdf = false;
    public static Uri documentUri;
    public static String qualificationId;
    public static ArrayList<String> qualificationIdArray = new ArrayList<String>();



    //todo:email validation check
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean isPaymmentSuccesful;
    public static Activity applicationContext;
    public static String timestamp;
    public static Context context;

    public static boolean enableEditEducation;
    public static boolean enableAddDocument;

    public static boolean isIncompleteAppointment;
    public static String stepNumber;
    public static List<DocumentDetailModel> DMList = new ArrayList<>();

    //EQ:
    public static boolean isIncompleteAppointmentEQ = false;
    public static String stepNumberEQ;


    /*TODO: EQUIVALENCE*/
    public static GetDetailsEquivalence getDetailsEquivalence = new GetDetailsEquivalence();
    public static boolean equivalenceOnline;

    //NEW Implementation of this equivalence education:
    public static DetailsEquivalenceNewModel detailsEquivalenceNewModel = new DetailsEquivalenceNewModel();

    /*public static List<EquivalenceGrade> equivalenceMarksList = new ArrayList<>();*/
    public static List<totalMarksModel> equivalenceMarksList = new ArrayList<>();

    public static List<EquivalenceGrade> equivalenceGradeList = new ArrayList<>();
    public static List<EquivalenceFileModel> selectedFilesList = new ArrayList<>();
    public static List<CaseUploadedDocumentResponse> caseSelectedFilesList = new ArrayList<>();
    public static List<EquivalenceGrade> selectedGradeList = new ArrayList<>();
    public static List<QualificationSubjectResponse> selectedMarksList = new ArrayList<>();
    public static List<EquivalenceGrade> TempselectedGradeList = new ArrayList<>();
    public static List<EquivalenceTravelFileModel> selectedTravelList = new ArrayList<>();

    public static final List<AddQualificationModel> equivalenceQualificationList = new ArrayList<>();

    public static List<CaseUploadedDocumentResponse> documentList = new ArrayList<>();
    public static List<CaseUploadedTravellingDocumentResponse> travelDocumentList = new ArrayList<>();
    public static List<QualificationSubjectResponse> qualificationSubjectResponses = new ArrayList<>();

    public static int selectedQualificationIndex;
    public static int[] documentAmountArray;
    public static int documentsTotalFee;

    public static String appointment_method, trx_id, bank_name, payment_status;
    public static Center attestationGenerateAppCenter, equivalenceGenerateAppCenter;
    public static int attestationTotalDocuments = 0;
    public static int equivalenceTotalDocument = 0;


    public static SaveBooking saveBookingResponse = new SaveBooking();
    public static SaveCourierDetials saveCourierDetials = new SaveCourierDetials();
    public static SaveCourierDetialsEQ saveCourierDetialsEQ = new SaveCourierDetialsEQ();

    /*Models Class Objects*/
    public static EquivalenceInitiateCase equivalenceInitiateCase = new EquivalenceInitiateCase();
    public static EquivalenceAddQualification equivalenceAddQualification = new EquivalenceAddQualification();
    public static List<DocDetailEQ_Model> eqModel = new ArrayList<>();

    /*Response Model Objects*/
    public static IntiateCase equivalenceInitiateCaseResponse = new IntiateCase();
    public static AddQualificationEQ addQualificationEQResponse = new AddQualificationEQ();
    public static RemoveQualificationEQ removeQualificationEQResponse = new RemoveQualificationEQ();
    public static EditQualificationEQ editQualificationEQResponse = new EditQualificationEQ();
    public static SaveDocumentDetailsEQ saveDocumentDetailsEQResponse = new SaveDocumentDetailsEQ();

    public static List<DeleteParams> deleteParams = new ArrayList<>();
    public static int caseId_Equ, docId_Equ;
    public static int deleteEquQualificationPosition;
    public static String equivalenceGradingSystemName;
    public static int documentUrgentCheck;
    public static Boolean checkMarks = false;
    public static String bankChargesForReceiptEQ, courierFeeForReceiptEQ, secuirtyFeeForReceiptEQ, ibbcChargesForReceiptEQ;
    public static int bankChargeEQ;
    public static String allDocumentsFeeEQ, selEqCenter;


    public static List<String> imagesEducationlList = new ArrayList<>();
    public static List<String> imagesTravellinglList = new ArrayList<>();
    public static List<EquivalenceFileModel> selectedFilesListTraveling = new ArrayList<>();
    public static boolean isRequired = false;
    public static Boolean isFromDocument = true;
    public static int Count = 1;
    public static PhpResp phpResp = new PhpResp();
    public static int CountTraveling = 1;
    public static String order_id;
    public static String eqType;

    public static ArrayList<Uri> phpfiles = new ArrayList<>();
    public static ArrayList<Uri> phptravellingFiles = new ArrayList<>();


}

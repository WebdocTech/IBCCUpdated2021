package com.webdoc.ibcc.DashBoard.Home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.webdoc.ibcc.Adapter.CategoriesAdapter;
import com.webdoc.ibcc.DashBoard.Home.ApplyAttestation.Instructions.FormInstructionActivity;
import com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.ApplyEquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.Attestation.AttestationActivity;
import com.webdoc.ibcc.DashBoard.Home.Downloads.DownloadsActivity;
import com.webdoc.ibcc.DashBoard.Home.Equivalence.EquivalenceActivity;
import com.webdoc.ibcc.DashBoard.Home.EventsGallery.EventsGalleryActivity;
import com.webdoc.ibcc.DashBoard.Home.Verification.VerificationActivity;
import com.webdoc.ibcc.DashBoard.Home.VideoGallery.VideoGalleryActivity;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.JsMainActivities.JsBankActivity;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFrag extends Fragment {
    private AlertDialog selectProcessAlertDialog;
    private FragmentHomeBinding layoutBinding;

    public HomeFrag() {
        // Required empty public constructor
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutBinding = FragmentHomeBinding.inflate(inflater, container, false);

        layoutBinding.imageSlider.setIndicatorAnimation(SliderLayout.Animations.FILL);
        layoutBinding.imageSlider.setScrollTimeInSec(1);
        setSliderViews();

        clickListeners();
        return layoutBinding.getRoot();
    }

    private void clickListeners() {
        layoutBinding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //webview
                Intent intent = new Intent(getActivity(), JsBankActivity.class);
                startActivity(intent);
            }
        });

        //CATEGORY LAYOUTS
        layoutBinding.EquivalenceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EquivalenceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        layoutBinding.AttestationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AttestationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        layoutBinding.VerificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VerificationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });

        layoutBinding.DownLoadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DownloadsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        layoutBinding.btnApplyOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProcessChooser();
                //ApplyOnlineAttestation();
            }
        });

        layoutBinding.animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if--- not available for appointment = false, else--available for appointment = true
                showProcessChooser();
                //ApplyOnlineAttestation();
            }
        });

        layoutBinding.VideosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VideoGalleryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        layoutBinding.EventsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventsGalleryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void setSliderViews() {
        for (int i = 0; i <= 2; i++) {
            SliderView sliderView = new SliderView(getActivity());
            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.slider1);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.slider2);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.slider3);
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            layoutBinding.imageSlider.addSliderView(sliderView);
        }
    }//slider

    public void showProcessChooser() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.alert_select_process, null);
        dialogBuilder.setView(v);

        selectProcessAlertDialog = dialogBuilder.create();
        selectProcessAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView iv_attestation_process = v.findViewById(R.id.iv_attestation_process);
        ImageView iv_equivalence_process = v.findViewById(R.id.iv_equivalence_process);
        TextView tv_attestation_process = v.findViewById(R.id.tv_attestation_process);
        TextView tv_equivalence_process = v.findViewById(R.id.tv_equivalence_process);

        iv_attestation_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Online Attestation process is not Available.please make appointment using web-portal")
                        .setConfirmText("Apply")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                String url = "http://attest.ibcc.edu.pk/";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        }).setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                        .show();

                //  ApplyOnlineAttestation();
                //  selectProcessAlertDialog.dismiss();
            }
        });

        tv_attestation_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Online Attestation process is not Available.please make appointment using web-portal")
                        .setConfirmText("Apply")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                String url = "http://attest.ibcc.edu.pk/";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        }).setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                        .show();
            }
        });

        iv_equivalence_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyOnlineEquivalence();
                selectProcessAlertDialog.dismiss();
            }
        });

        tv_equivalence_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyOnlineEquivalence();
                selectProcessAlertDialog.dismiss();
            }
        });

        selectProcessAlertDialog.show();
    }//alert


    private void ApplyOnlineAttestation() {
        Global.isFromEquivalence = false;
    /*    if (!Global.userLoginResponse.getResult().getCustomerProfile().getIsAppointmentAvailabe()) {
            Global.utils.showErrorSnakeBar(getActivity(), "Your Appointment is already booked");
        }*/
        Global.isIncompleteAppointment = false;
        Global.enableEditEducation = true;
        Global.enableAddDocument = true;

        Global.selectedCerti.clear();
        for (int i = 0; i < Global.pdfResponse.getResult().getCerftificates().size(); i++) {
            Global.selectedCerti.add(Global.pdfResponse.getResult().getCerftificates().get(i));


            Global.addEducationResponse.setResult(null);
            Global.DMList.clear();
            Global.selDocument.clear();

            Intent intent = new Intent(getActivity(), FormInstructionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    private void ApplyOnlineEquivalence() {
        Global.isFromEquivalence = true;
        Global.equivalenceOnline = true;
        Global.equivalenceQualificationList.clear();
        Intent intent = new Intent(getActivity(), ApplyEquivalenceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


}
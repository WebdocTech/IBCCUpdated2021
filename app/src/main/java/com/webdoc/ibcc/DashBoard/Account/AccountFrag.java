package com.webdoc.ibcc.DashBoard.Account;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.webdoc.ibcc.CompleteProfile.SuccessRegistration;
import com.webdoc.ibcc.DashBoard.Account.AboutUs.AboutUsActivity;
import com.webdoc.ibcc.DashBoard.Account.AimsObjective.Aims_ObjectiveActivity;
import com.webdoc.ibcc.DashBoard.Account.Appointment.AttestationAppointment.CompletedAppointment;
import com.webdoc.ibcc.DashBoard.Account.Appointment.MainCompletedApptActivity;
import com.webdoc.ibcc.DashBoard.Account.ContactUs.ContactUsActivity;
import com.webdoc.ibcc.DashBoard.Account.Organization.OrganizationActivity;
import com.webdoc.ibcc.DashBoard.Account.ViewProfile.ViewProfileActivity;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.Essentails.Preferences;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.UserLogin.LoginActivity;
import com.webdoc.ibcc.databinding.FragmentAccountBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import xyz.hasnat.sweettoast.SweetToast;

public class AccountFrag extends Fragment {
    Button btn_aims;
    public static TextView UserName;
    private FragmentAccountBinding layoutBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        layoutBinding = FragmentAccountBinding.inflate(inflater, container, false);

        btn_aims = view.findViewById(R.id.btn_aims);

        UserName = layoutBinding.UserName;
        UserName.setText(Global.userLoginResponse.getResult().getCustomerProfile().getFirstName() + " " + Global.userLoginResponse.getResult().getCustomerProfile().getLastName());

        clickListeners();

        return layoutBinding.getRoot();
    }

    private void clickListeners() {
        layoutBinding.ProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        layoutBinding.btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainCompletedApptActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        layoutBinding.btnOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.utils.HaveNetwork(getActivity())) {
                    Intent intent = new Intent(getActivity(), OrganizationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else {
                    String message = "Cannot connect to Internet.";
                    SweetToast.error(getActivity(), message);
                }
            }
        });

        btn_aims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.utils.HaveNetwork(getActivity())) {
                    Intent intent = new Intent(getActivity(), Aims_ObjectiveActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else {
                    String message = "Cannot connect to Internet.";
                    SweetToast.error(getActivity(), message);
                }
            }
        });

        layoutBinding.btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.utils.HaveNetwork(getActivity())) {
                    Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else {
                    String message = "Cannot connect to Internet.";
                    SweetToast.error(getActivity(), message);
                }
            }
        });

        layoutBinding.btnReportIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "interboardcommitteeofcharimen@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Message");

                if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                } else {
                    Toast.makeText(getActivity(), " Error connecting to mail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        layoutBinding.btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.utils.HaveNetwork(getActivity())) {
                    Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else {
                    String message = "Cannot connect to Internet.";
                    SweetToast.error(getActivity(), message);
                }
            }
        });

        layoutBinding.ivFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ibcc.edu.pk")));
            }
        });

        layoutBinding.ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Ibcc_official")));
            }
        });

        layoutBinding.ivYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCK9Uu2V6vrrzBRCNrmVc1DQ")));
            }
        });

        layoutBinding.ivLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/authwall?trk=gf&trkInfo=AQHCZPII8r85WQAAAXiWtSRIsrI89UN5pmOyNngIPuYHxm1CkeAd7N8J1BpPT0H_iJM1c9wJOXuUMGk8KL_QYFXuAWKbSqt0SnQfqFgEDI6T3pDPfs5xuQMhgpIUDeDj26bBsSA=&originalReferer=https://ibcc.edu.pk/functions/&sessionRedirect=https%3A%2F%2Fwww.linkedin.com%2Fin%2Fibcc-pakistan-70469b202%2F")));
            }
        });

        layoutBinding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Do you want to SignOut?")
                        .setConfirmText("Yes!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                               /* Global.utils.showCustomLoadingDialog(getActivity());
                                volleyRequestController.LogOut();*/
                                Preferences.getInstance(getActivity()).clearSharedPreferences();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
    }

}
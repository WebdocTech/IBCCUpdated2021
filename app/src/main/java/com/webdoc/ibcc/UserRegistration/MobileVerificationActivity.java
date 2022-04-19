package com.webdoc.ibcc.UserRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.webdoc.ibcc.Essentails.Global;
import com.webdoc.ibcc.R;
import com.webdoc.ibcc.databinding.ActivityMobileVerificationBinding;

import java.util.concurrent.TimeUnit;

public class MobileVerificationActivity extends AppCompatActivity {
    private String verificationID;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken codeResendToken;
    private ActivityMobileVerificationBinding layoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityMobileVerificationBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        Global.utils.showErrorSnakeBar(this, "Please wait for Human Verification");

        layoutBinding.etGetCode2.setFocusable(false);
        layoutBinding.etGetCode3.setFocusable(false);
        layoutBinding.etGetCode4.setFocusable(false);
        layoutBinding.etGetCode5.setFocusable(false);
        layoutBinding.etGetCode6.setFocusable(false);

        deleteListener(layoutBinding.etGetCode2);
        deleteListener(layoutBinding.etGetCode3);
        deleteListener(layoutBinding.etGetCode4);
        deleteListener(layoutBinding.etGetCode5);
        deleteListener(layoutBinding.etGetCode6);

        editTextTouchListener(layoutBinding.etGetCode1);
        editTextTouchListener(layoutBinding.etGetCode2);
        editTextTouchListener(layoutBinding.etGetCode3);
        editTextTouchListener(layoutBinding.etGetCode4);
        editTextTouchListener(layoutBinding.etGetCode5);
        editTextTouchListener(layoutBinding.etGetCode6);

        pinMovementListener();
        userSignUpStatus();
        clickListeners();

    }

    private void clickListeners() {
        layoutBinding.btnVerifyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = layoutBinding.etGetCode1.getText().toString() +
                        layoutBinding.etGetCode2.getText().toString() +
                        layoutBinding.etGetCode3.getText().toString() +
                        layoutBinding.etGetCode4.getText().toString() +
                        layoutBinding.etGetCode5.getText().toString() +
                        layoutBinding.etGetCode6.getText().toString();

                if (code.isEmpty() || code.length() < 6) {
                    Toast.makeText(MobileVerificationActivity.this, "Please enter code", Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyCode(code);
            }
        });

        layoutBinding.btnResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendVerificationCode(Global.registerUser.getPhoneNumber(), codeResendToken);
                Toast.makeText(MobileVerificationActivity.this, "Code will be send soon",
                        Toast.LENGTH_LONG).show();
            }
        });

    }


    public void editTextTouchListener(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (editText.getId()) {
                    case R.id.et_getCode1:
                        if (!TextUtils.isEmpty(layoutBinding.etGetCode1.getText().toString())) {
                            layoutBinding.etGetCode1.setText("");
                            layoutBinding.etGetCode2.setText("");
                            layoutBinding.etGetCode3.setText("");
                            layoutBinding.etGetCode4.setText("");
                            layoutBinding.etGetCode5.setText("");
                            layoutBinding.etGetCode6.setText("");
                        }
                        break;

                    case R.id.et_getCode2:
                        if (!TextUtils.isEmpty(layoutBinding.etGetCode2.getText().toString())) {
                            layoutBinding.etGetCode2.setText("");
                            layoutBinding.etGetCode3.setText("");
                            layoutBinding.etGetCode4.setText("");
                            layoutBinding.etGetCode5.setText("");
                            layoutBinding.etGetCode6.setText("");
                        }
                        break;

                    case R.id.et_getCode3:
                        if (!TextUtils.isEmpty(layoutBinding.etGetCode3.getText().toString())) {
                            layoutBinding.etGetCode3.setText("");
                            layoutBinding.etGetCode4.setText("");
                            layoutBinding.etGetCode5.setText("");
                            layoutBinding.etGetCode6.setText("");
                        }
                        break;

                    case R.id.et_getCode4:
                        if (!TextUtils.isEmpty(layoutBinding.etGetCode4.getText().toString())) {
                            layoutBinding.etGetCode4.setText("");
                            layoutBinding.etGetCode5.setText("");
                            layoutBinding.etGetCode6.setText("");
                        }
                        break;
                    case R.id.et_getCode5:
                        if (!TextUtils.isEmpty(layoutBinding.etGetCode5.getText().toString())) {
                            layoutBinding.etGetCode5.setText("");
                            layoutBinding.etGetCode6.setText("");
                        }
                        break;
                    case R.id.et_getCode6:
                        if (!TextUtils.isEmpty(layoutBinding.etGetCode6.getText().toString())) {
                            layoutBinding.etGetCode6.setText("");
                        }
                        break;
                    default:
                        break;
                }

                return false;   /* keyboard pops up on false */
            }
        });
    }

    public void deleteListener(final EditText editText) {
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed by checking keyCode value with KeyEvent.KEYCODE_
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    switch (editText.getId()) {
                        case R.id.et_getCode2:
                            if (layoutBinding.etGetCode2.getText().toString().isEmpty()) {
                                layoutBinding.etGetCode1.setText("");
                                layoutBinding.etGetCode1.requestFocus();
                            }
                            break;

                        case R.id.et_getCode3:
                            if (layoutBinding.etGetCode3.getText().toString().isEmpty()) {
                                layoutBinding.etGetCode2.setFocusableInTouchMode(true);  /* enables edittext editing */
                                layoutBinding.etGetCode2.setText("");
                                layoutBinding.etGetCode2.requestFocus();
                            }
                            break;

                        case R.id.et_getCode4:
                            if (layoutBinding.etGetCode4.getText().toString().isEmpty()) {
                                layoutBinding.etGetCode3.setFocusableInTouchMode(true);  /* enables edittext editing */
                                layoutBinding.etGetCode3.setText("");
                                layoutBinding.etGetCode3.requestFocus();
                            }
                            break;
                        case R.id.et_getCode5:
                            if (layoutBinding.etGetCode5.getText().toString().isEmpty()) {
                                layoutBinding.etGetCode4.setFocusableInTouchMode(true);  /* enables edittext editing */
                                layoutBinding.etGetCode4.setText("");
                                layoutBinding.etGetCode4.requestFocus();
                            }
                            break;
                        case R.id.et_getCode6:
                            if (layoutBinding.etGetCode6.getText().toString().isEmpty()) {
                                layoutBinding.etGetCode5.setFocusableInTouchMode(true);  /* enables edittext editing */
                                layoutBinding.etGetCode5.setText("");
                                layoutBinding.etGetCode5.requestFocus();
                            }
                            break;

                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }//delete

    public void pinMovementListener() {
        layoutBinding.etGetCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    /* disable all next edittexts */
                    layoutBinding.etGetCode2.setFocusable(false);
                } else {
                    if (s.length() == 1) {
                        /* enables next edittext */
                        layoutBinding.etGetCode2.setFocusableInTouchMode(true);
                        /* move focus to next edittext */
                        layoutBinding.etGetCode2.requestFocus(View.FOCUS_DOWN);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        layoutBinding.etGetCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    /* disable all next edittexts */
                    layoutBinding.etGetCode3.setFocusable(false);
                } else {
                    if (s.length() == 1) {
                        /* enables next edittext */
                        layoutBinding.etGetCode3.setFocusableInTouchMode(true);

                        /* move focus to next edittext */
                        layoutBinding.etGetCode3.requestFocus(View.FOCUS_DOWN);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        layoutBinding.etGetCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    /* disable all next edittexts */
                    layoutBinding.etGetCode4.setFocusable(false);
                } else {
                    if (s.length() == 1) {
                        /* enables next edittext */
                        layoutBinding.etGetCode4.setFocusableInTouchMode(true);

                        /* move focus to next edittext */
                        layoutBinding.etGetCode4.requestFocus(View.FOCUS_DOWN);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        layoutBinding.etGetCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    /* disable all next edittexts */
                    layoutBinding.etGetCode5.setFocusable(false);
                } else {
                    if (s.length() == 1) {
                        /* enables next edittext */
                        layoutBinding.etGetCode5.setFocusableInTouchMode(true);

                        /* move focus to next edittext */
                        layoutBinding.etGetCode5.requestFocus(View.FOCUS_DOWN);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        layoutBinding.etGetCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    /* disable all next edittexts */
                    layoutBinding.etGetCode6.setFocusable(false);
                } else {
                    if (s.length() == 1) {
                        /* enables next edittext */
                        layoutBinding.etGetCode6.setFocusableInTouchMode(true);

                        /* move focus to next edittext */
                        layoutBinding.etGetCode6.requestFocus(View.FOCUS_DOWN);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signInWithCredential(credential);

        /*try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
            signInWithCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), "Verification Code is wrong, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }*/
    }

    private void signInWithCredential(final PhoneAuthCredential credential) {
        mAuth.createUserWithEmailAndPassword(Global.registerUser.getPhoneNumber() + "@ibcc.com.pk", Global.registerUser.getPhoneNumber())
                .addOnCompleteListener(MobileVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth.getCurrentUser().linkWithCredential(credential)
                                    .addOnCompleteListener(MobileVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseAuth.getInstance().signOut();
                                                //   progressBar.setVisibility(View.GONE);
                                                Global.utils.hideCustomLoadingDialog();

                                                // Global.utils.hideCustomLoadingDialog();
                                                // SuccessAlertDialog();

                                                Intent intent = new Intent(MobileVerificationActivity.this, SetPinActivity.class);
                                                startActivity(intent);

                                                //Toast.makeText(MobileVerificationActivity.this, Global.registerUser.getPhoneNumber(), Toast.LENGTH_LONG).show();

                                            } else {
                                                deleteUser(false);
                                                Toast.makeText(MobileVerificationActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                                                // Global.utils.hideCustomLoadingDialog();
                                            }
                                        }
                                    });

                        }
                    }
                });
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
            codeResendToken = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                Global.utils.showCustomLoadingDialog(MobileVerificationActivity.this);
                //  progressBar.setVisibility(View.VISIBLE);
                char[] codeSplit = code.toCharArray();

                layoutBinding.etGetCode1.setText(String.valueOf(codeSplit[0]));
                layoutBinding.etGetCode2.setText(String.valueOf(codeSplit[1]));
                layoutBinding.etGetCode3.setText(String.valueOf(codeSplit[2]));
                layoutBinding.etGetCode4.setText(String.valueOf(codeSplit[3]));
                layoutBinding.etGetCode5.setText(String.valueOf(codeSplit[4]));
                layoutBinding.etGetCode6.setText(String.valueOf(codeSplit[5]));

                layoutBinding.etGetCode1.setEnabled(false);
                layoutBinding.etGetCode2.setEnabled(false);
                layoutBinding.etGetCode3.setEnabled(false);
                layoutBinding.etGetCode4.setEnabled(false);
                layoutBinding.etGetCode5.setEnabled(false);
                layoutBinding.etGetCode6.setEnabled(false);

                layoutBinding.btnVerifyNow.setEnabled(false);
                signInWithCredential(phoneAuthCredential);
            } else {
                Global.utils.showCustomLoadingDialog(MobileVerificationActivity.this);
                //       progressBar.setVisibility(View.VISIBLE);

                layoutBinding.etGetCode1.setText("*");
                layoutBinding.etGetCode2.setText("*");
                layoutBinding.etGetCode3.setText("*");
                layoutBinding.etGetCode4.setText("*");
                layoutBinding.etGetCode5.setText("*");
                layoutBinding.etGetCode6.setText("*");

                layoutBinding.etGetCode1.setEnabled(false);
                layoutBinding.etGetCode2.setEnabled(false);
                layoutBinding.etGetCode3.setEnabled(false);
                layoutBinding.etGetCode4.setEnabled(false);
                layoutBinding.etGetCode5.setEnabled(false);
                layoutBinding.etGetCode6.setEnabled(false);

                signInWithCredential(phoneAuthCredential);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            //Global.utils.hideCustomLoadingDialog();
            Toast.makeText(MobileVerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallBack,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }


    public void deleteUser(final boolean resendCode) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(Global.registerUser.getPhoneNumber() + "@ibcc.com.pk", Global.registerUser.getPhoneNumber())
                .addOnCompleteListener(MobileVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = task.getResult().getUser();

                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(Global.registerUser.getPhoneNumber() + "@ibcc.com.pk", Global.registerUser.getPhoneNumber());

                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                user.delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    //FirebaseAuth.getInstance().signOut();

                                                                    if (resendCode) {
                                                                        sendVerificationCode(Global.registerUser.getPhoneNumber());
                                                                    }
                                                                } else {
                                                                    Toast.makeText(MobileVerificationActivity.this, "User account deletion unsuccessful.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(MobileVerificationActivity.this, "Deletion Authentication failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }


    public void userSignUpStatus() {
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(Global.registerUser.getPhoneNumber()
                + "@ibcc.com.pk").addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getSignInMethods().size() == 0) {
                        sendVerificationCode(Global.registerUser.getPhoneNumber()); // email not existed

                    } else {
                        deleteUser(true);    // email existed
                    }
                } else {
                    Toast.makeText(MobileVerificationActivity.this, task.getException().toString(),
                            Toast.LENGTH_LONG).show();
                    //dialog.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }


}
package com.badrul.user.machbazarfinalver5;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import android.support.v7.app.ActionBar;

public class Home extends AppCompatActivity {
    EditText editText;
    EditText editText1;
    Button button;
    Button button1;

    TextView textView1;
    FirebaseAuth mAuth;
    String codeSent;
    private PhoneAuthProvider.ForceResendingToken resendCode;

    Connection_Deetector connection_deetector;
    ActionBar actionBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3D4238")));
        Intent intent=getIntent();
        connection_deetector=new Connection_Deetector(this);
        mAuth= FirebaseAuth.getInstance();
        editText=findViewById(R.id.ph);
        editText1=findViewById(R.id.otp);
        button=findViewById(R.id.phc);
        button1=findViewById(R.id.log);

        textView1=findViewById(R.id.resend);

        button1.setEnabled(false);
        button1.setVisibility(View.INVISIBLE);
        textView1.setEnabled(false);
        textView1.setVisibility(View.INVISIBLE);
        editText1.setVisibility(View.INVISIBLE);



    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() !=null){



            finish();
            startActivity(new Intent(this,Menu.class));

        }
    }



    public void otpSent(View view) {

        //button.setEnabled(false);
        //String num=editText.getText().toString().trim();
        //PhoneAuthProvider.getInstance().verifyPhoneNumber(num,60, TimeUnit.SECONDS,Home.this,mCallbacks);
        if (connection_deetector.isConnected()){
            sendVeificationCode();
        }
        else {
            Toast.makeText(this,"Not Connected",Toast.LENGTH_LONG).show();
        }




    }
    private void sendVeificationCode(){
        String num="+88";
        num=num.trim()+editText.getText().toString().trim();
        if (num.isEmpty()){
            editText.setError("Mobile number required");
            editText.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(num).matches()){
            editText.setError("Enter valid mobile number");
            editText.requestFocus();
            return;


        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(num,60,TimeUnit.SECONDS,this,mCallbacks);
        button.setEnabled(false);
        button.setVisibility(View.INVISIBLE);

        textView1.setVisibility(View.VISIBLE);
        textView1.setEnabled(true);


    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException){
                Toast.makeText(getApplicationContext(),"Enter 11 digit Number as 01*****",Toast.LENGTH_LONG).show();
            }
            else if (e instanceof FirebaseTooManyRequestsException){
                Toast.makeText(getApplicationContext(),"SMS Quota exceeded",Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent=s;
            resendCode=forceResendingToken;
            editText1.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);


            button1.setEnabled(true);
        }
    };



    public void logIn(View view) {
        if (connection_deetector.isConnected()){
            verifySignInCode();
        }
        else {
            Toast.makeText(this,"Not Connected",Toast.LENGTH_LONG).show();
        }

    }
    private void verifySignInCode(){
        String code=editText1.getText().toString().trim();
        if (code.isEmpty()){
            editText1.setError("OTP required");
            editText1.requestFocus();
            return;
        }

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codeSent,code);
        signInWithPhoneAuthCredential(credential);




    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    button1.setEnabled(false);
                    String numberSend=editText.getText().toString().trim();



                    Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_LONG).show();


                    Intent intent=new Intent(getApplicationContext(),Menu.class);
                    intent.putExtra("contact",numberSend);
                    startActivity(intent);
                    finish();


                }
                else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        editText1.setError("Invlid OTP");
                        editText1.requestFocus();
                        return;
                    }

                }

            }
        });

    }

    public void resend(View view) {
        if (connection_deetector.isConnected()){
            sendVeificationCode();
        }
        else {
            Toast.makeText(this,"Not Connected",Toast.LENGTH_LONG).show();
        }


    }
}

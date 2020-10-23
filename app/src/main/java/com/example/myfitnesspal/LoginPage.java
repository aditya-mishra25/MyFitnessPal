package com.example.myfitnesspal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginPage extends AppCompatActivity {
    EditText email,password, em1;
    Button signin, ok, cncl;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        fauth = FirebaseAuth.getInstance();

//        Email
        final EditText email=findViewById(R.id.email);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (email.getText().toString().length() == 0){
                        email.setError(" Email cannot be blank");
                        if (email.getText().toString().isEmpty())
                            email.setError(" Invalid Email ");
                    } else {
                        if (email.getText().toString().trim().matches(emailPattern)) {
                        } else {
                            email.setError(" Invalid Email ");
                        }

                    }
                }
            }
        });

//      Password
        final EditText password=findViewById(R.id.password);
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    String pass = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
                    if (password.getText().toString().length() < 8) {
                        password.setError(" Minimum length of Password should be 8");
                    }
                    if (password.getText().toString().isEmpty()) {
                        password.setError(" Invalid Password ");
                    } else {

                        if (password.getText().toString().trim().matches(pass)) {
//                        email.setError( " Invalid Password " );
//                        Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                        } else {
//                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                            password.setError(" Invalid Password ");
                        }
                    }
                }
            }
        });
    }
    public void onButtonClick(View view){
        EditText email=(EditText) findViewById(R.id.email);
        EditText password=(EditText) findViewById(R.id.password);
        String em = email.getText().toString();
        String pwd = password.getText().toString();

        if( em.length() == 0 ) {
            email.setError(" Email is required.!");
        }
        else{
            if( pwd.length() == 0 ){
                password.setError( " Password is required.!" );
        }else{
                fauth.signInWithEmailAndPassword(em,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(LoginPage.this,"Logged In Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Log.w("Error", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginPage.this,"Incorrect Credentials",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }


        //Authentication

    }

    public void forgotPassword(View view) {
//        EditText email=(EditText) findViewById(R.id.email);
//        String em = email.getText().toString();
        final ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(LoginPage.this).inflate(R.layout.forgot_pass, viewGroup, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ok = dialogView.findViewById(R.id.sendlink);
        cncl = dialogView.findViewById(R.id.cancel);
        em1 = dialogView.findViewById(R.id.emaillink);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emaillink = em1.getText().toString();
                if(emaillink.equals("")){
                    Toast.makeText(LoginPage.this,"Please input some value",Toast.LENGTH_LONG).show();
                }
                else{
                    fauth.sendPasswordResetEmail(emaillink).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginPage.this,"loggedin",Toast.LENGTH_SHORT).show();
                                Log.d("Sucess","logged");
                            }
                            else{
                                Toast.makeText(LoginPage.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                Log.d("error",task.getException().toString());
                            }
                        }
                    });
                    alertDialog.dismiss();//change code here
                }
            }
        });
        cncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }
    public void goToReg(View view){
        startActivity(new Intent(getApplicationContext(),Registration.class));
    }
}


package com.example.myfitnesspal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.auth.FirebaseAuth;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
//import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.*;

public class Registration extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth=FirebaseAuth.getInstance();

//      Name
        final EditText name=findViewById(R.id.name);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (name.getText().toString().length() < 5)
                        name.setError(" Name should be atleast of length 5!");
                }
            }
        });


//      Age
        final EditText age = (EditText) findViewById((R.id.age));
        age.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (age.getText().toString().length() < 1 || age.getText().toString().length() > 2)
                        age.setError(" Invalid Age!");
                }
//                String x=age.getText().toString();
//                if( Integer.parseInt(x)==0){
//                    age.setError(" 0 Age!");
//                }
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

//      Phone
        final EditText phone=findViewById(R.id.phone);
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
           if (phone.getText().toString().length() != 10)
               phone.setError(" Invalid Phone!");

            }}
        });


//      Email
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

//      Height
        final EditText height=findViewById(R.id.height);

        height.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
//                    EditText height = (EditText) findViewById((R.id.height));
                    if (height.getText().toString().length() < 2 || height.getText().toString().length() > 3)
                        height.setError(" Invalid Height!");
                }
            }
        });

//      Weight
        final EditText weight=findViewById(R.id.weight);
        weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
//                    EditText weight = (EditText) findViewById((R.id.weight));
                if (weight.getText().toString().length() < 2 || weight.getText().toString().length() > 3)
                    weight.setError(" Invalid Weight!");
            }}
        });

    }
   public void onButtonClick(View view){


       EditText name= (EditText) findViewById(R.id.name);
       EditText email=(EditText) findViewById(R.id.email);
       EditText phone=(EditText) findViewById(R.id.phone);
       EditText password=(EditText) findViewById(R.id.password);
       EditText age=(EditText) findViewById(R.id.age);
       EditText height=(EditText) findViewById(R.id.height);
       EditText weight=(EditText) findViewById(R.id.weight);


       if( name.getText().toString().length() == 0 )
           name.setError( " Name is required.!" );

       if( email.getText().toString().length() == 0 )
           email.setError( " Email is required.!" );

       if( phone.getText().toString().length() == 0 )
           phone.setError( " Phone Number is required.!" );

       if( password.getText().toString().length() == 0 )
           password.setError( " Password is required.!" );

       if( age.getText().toString().length() == 0 )
           age.setError( " Age is required.!" );

       if( height.getText().toString().length() == 0 )
           height.setError( " Height is required.!" );

       if( weight.getText().toString().length() == 0 )
           weight.setError( " Weight is required.!" );

       final String em=email.getText().toString();
       final String pass=password.getText().toString();
       firebaseAuth.createUserWithEmailAndPassword(em,pass)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
//                       Log.d("Error",em);
//                       Log.d("Error",pass);
                       if (task.isSuccessful()){
                           Toast.makeText(Registration.this,"Registration Success", LENGTH_LONG).show();
                       }
                       else{
                           Log.w("Error", "createUserWithEmail:failure", task.getException());
                           Toast.makeText(Registration.this,"Registration Failed", LENGTH_LONG).show();
                       }
                   }
               });



    }
//    private void registerUser(){
////        firebaseAuth.createUserWithEmailAndPassword(email,password)
//    }


}




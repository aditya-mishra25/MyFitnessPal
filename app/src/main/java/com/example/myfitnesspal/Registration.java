package com.example.myfitnesspal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.*;

public class Registration extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

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
                    if(name.getText().toString().matches(".*\\d.*")){
                        name.setError("Name cannot contain Numbers");
                    }
                }
            }
        });


//      Age
        final EditText age = (EditText) findViewById((R.id.age));
        age.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if (age.getText().toString().length() < 2 || age.getText().toString().length() > 2)

//                        age.setError(" Invalid Age!");
                    age.setError("Age should be between 10 - 99");
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
                        password.setError("Invalid Password, Minimum length of Password should be 8 ");
                    } else {
                        if (password.getText().toString().trim().matches(pass)) {
//                        email.setError( " Invalid Password " );
//                        Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                        } else {
//                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
//                            password.setError(" Invalid Password ");
                            if (password.getText().toString().length() < 8) {
                                password.setError(" Minimum length of Password should be 8");
                            }
                                password.setError("Use the format : @,%,A,a,1");
//                            password.setText();
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
                    if(height.getText().toString().length()<2){
                        height.setError("Height should be atleast 100cm");
                    }
//                    EditText height = (EditText) findViewById((R.id.height));
                    if (height.getText().toString().length() ==2 ){
                        if (Integer.parseInt(height.getText().toString())<100){
                            height.setError("Height cannot be below 100 cm");
                        }
                    }


                    if(height.getText().toString().length() > 2){
                        if (Integer.parseInt(height.getText().toString())>220){
                            height.setError("Height cannot be above 220 cm");
                        }

                    }
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
                if (weight.getText().toString().length() < 2  ){
                    weight.setError(" Weight cannot be below 10 kgs");
                }
                if(weight.getText().toString().length()>2){
                    if (Integer.parseInt(weight.getText().toString())>250){
                        weight.setError("Weight cannot be more than 250kgs");
                    }
                }
            }}
        });

    }
   public void onButtonClick(View view){


       final EditText name= (EditText) findViewById(R.id.name);
       final EditText email=(EditText) findViewById(R.id.email);
       final EditText phone=(EditText) findViewById(R.id.phone);
       EditText password=(EditText) findViewById(R.id.password);
       final EditText age=(EditText) findViewById(R.id.age);
       final EditText height=(EditText) findViewById(R.id.height);
       final EditText weight=(EditText) findViewById(R.id.weight);





       if( name.getText().toString().length() == 0 ) {
           name.setError("Name cannot be blank");
       }
           else{
                   if(email.getText().toString().length() == 0 ){
                       email.setError("Email cannot be blank");
                   }
                   else{
                           if(phone.getText().toString().length() == 0 ){
                               phone.setError("Phone cannot be blank");
                           }
                           else{
                                   if(password.getText().toString().length() == 0 ){
                                       password.setError("Password cannot be blank");
                                   }
                                   else{
                                           if(age.getText().toString().length() == 0 ){
                                               age.setError("Age cannot be blank");
                                           }
                                           else{
                                                   if(height.getText().toString().length() == 0 ){
                                                       height.setError("Height cannot be blank");
                                                   }
                                                   else{
                                                           if(weight.getText().toString().length() == 0 ){
                                                               weight.setError("Weight cannot be blank");
                                                           }
                                                           else{
                                                               final String em=email.getText().toString();
                                                               final String pass=password.getText().toString();
                                                               firebaseAuth.createUserWithEmailAndPassword(em,pass)
                                                                       .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                                                           @Override
                                                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                                    //                       Log.d("Error",em);
                                                    //                       Log.d("Error",pass);
                                                                               if (task.isSuccessful()){
                                                                                   String uid = firebaseAuth.getUid();
                                                                                   Log.d("uid",uid);
                                                                                   DatabaseReference Ref = database.getReference("Users");
                                                                                   DatabaseReference myRef = Ref.child(uid);
                                                                                   myRef.child("name").setValue(name.getText().toString());
                                                                                   myRef.child("email").setValue(email.getText().toString());
                                                                                   myRef.child("mobile").setValue(phone.getText().toString());
                                                                                   myRef.child("age").setValue(age.getText().toString());
                                                                                   myRef.child("height").setValue(height.getText().toString());
                                                                                   myRef.child("weight").setValue(weight.getText().toString());
                                                                                   final int bmi = Integer.parseInt(weight.getText().toString())/(Integer.parseInt(height.getText().toString()))^2;
                                                                                   myRef.child("bmi").setValue(bmi);
                                                                                   Toast.makeText(Registration.this,"Registration Success", LENGTH_LONG).show();
                                                                                   startActivity(new Intent(getApplicationContext(),LoginPage.class));

                                                                               }
                                                                               else{
                                                                                  String log_msg=task.getException().toString().substring(61);
                                                                                   Log.w("Error", "createUserWithEmail:failure", task.getException());
                                                                                   Toast.makeText(Registration.this,  log_msg, LENGTH_LONG).show();
                                                                               }
                                                                           }
                                                                       });

                                                           }
                                                   }
                                           }
                                   }
                           }

               }
       }


    }


}




package com.example.myfitnesspal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
//import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.*;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        EditText name = (EditText) findViewById(R.id.name);
        EditText email = (EditText) findViewById(R.id.email);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText name = (EditText) findViewById(R.id.name);
//                String namepattern = "[a-z]";
//                EditText email=(EditText) findViewById(R.id.email);
                if (name.getText().toString().length() == 0)
                    name.setError(" Name is required!");
//                if (name.getText().toString().isEmpty()) {
//                    name.setError(" Invalid Name ");
////                    Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
//                } else {
//                    if (name.getText().toString().trim().matches(namepattern)) {
////                        email.setError( " Invalid Name " );
////                        Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
//                    } else {
////                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
//                        name.setError(" Invalid Name ");
//                    }
//                if( email.getText().toString().length() == 0 )
//                    email.setError( " Email is required!" );
//                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                EditText name = (EditText) findViewById(R.id.name);
                EditText email = (EditText) findViewById(R.id.email);

                if (email.hasFocus() && name.getText().toString().length() == 0)
                    name.setError(" Name is required!");

            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText name = (EditText) findViewById(R.id.name);
                if (name.getText().toString().length() < 5)
                    name.setError(" Name should be atleast of length 5!");

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                EditText email = (EditText) findViewById(R.id.email);
                if (email.getText().toString().length() == 0)
                    email.setError(" Email cannot be blank");
                if (email.getText().toString().isEmpty()) {
                    email.setError(" Invalid Email ");
//                    Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
                } else {
                    if (email.getText().toString().trim().matches(emailPattern)) {
//                        email.setError( " Invalid Email " );
//                        Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                        email.setError(" Invalid Email ");
                    }

                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EditText phone = (EditText) findViewById((R.id.phone));
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText phone = (EditText) findViewById((R.id.phone));
                if (phone.getText().toString().length() != 10)
                    phone.setError(" Invalid Phone!");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EditText age = (EditText) findViewById((R.id.age));
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText age = (EditText) findViewById((R.id.age));
                if (age.getText().toString().length() < 1 || age.getText().toString().length() > 2)
                    age.setError(" Invalid Age!");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EditText password = (EditText) findViewById((R.id.password));
        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText password = (EditText) findViewById((R.id.password));
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

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        EditText height = (EditText) findViewById((R.id.password));
        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText height = (EditText) findViewById((R.id.height));
                if (height.getText().toString().length() < 2 || height.getText().toString().length() > 3)
                    height.setError(" Invalid Height!");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EditText weight = (EditText) findViewById((R.id.password));
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText weight = (EditText) findViewById((R.id.weight));
                if (weight.getText().toString().length() < 2 || weight.getText().toString().length() > 3)
                    weight.setError(" Invalid Weight!");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
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



    }
       }




package com.example.myfitnesspal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings extends Fragment {
    Button log;
    FirebaseAuth fAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    Switch notification;
    DatabaseReference fdb;
    EditText oldpass,newpass;
    Button update;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settings.
     */
    // TODO: Rename and change types and number of parameters
    public static settings newInstance(String param1, String param2) {
        settings fragment = new settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        log = (Button) getView().findViewById(R.id.logout);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                if(fAuth.getCurrentUser() == null){
                    startActivity(new Intent(getActivity(), LoginPage.class));
                }
                else{
                    Toast.makeText(getActivity(),"Signout unsucessful",Toast.LENGTH_LONG).show();
                }
            }
        });

        //notification toggle button
        String uid=fAuth.getUid();
        notification = (Switch) getView().findViewById(R.id.toggle);
        fdb = FirebaseDatabase.getInstance().getReference("Water").child(uid);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(notification.isChecked()){
                    fdb.child("00 toggle").setValue("True");
                    editor.putBoolean("switchValue", true).commit();
                }
                else{
                    fdb.child("00 toggle").setValue("False");
                    editor.putBoolean("switchValue", false).commit();
                }
            }
        });

        oldpass = (EditText) getView().findViewById(R.id.oldpassword);
        newpass = (EditText) getView().findViewById(R.id.newpassword);
        update = (Button) getView().findViewById(R.id.updatepassword);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old = oldpass.getText().toString();
                final String newPass = newpass.getText().toString();
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail();
                AuthCredential credential = EmailAuthProvider.getCredential(email, old);
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                oldpass.setText("");
                                                newpass.setText("");
                                                hideKeyBoard();
                                                Toast.makeText(getActivity(),"Your password is updated successfully",Toast.LENGTH_LONG).show();
                                            } else {
                                                oldpass.setText("");
                                                newpass.setText("");
                                                hideKeyBoard();
                                                Toast.makeText(getActivity(),"Error while resetting password",Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                                } else {
                                    oldpass.setText("");
                                    newpass.setText("");
                                    hideKeyBoard();
                                    Toast.makeText(getActivity(),"Authentication Failed",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
    public void hideKeyBoard() {
        View view1 = getActivity().getCurrentFocus();
        if(view1 != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean def = sharedPref.getBoolean("switchValue", false);
        Switch notification;
        notification = (Switch) view.findViewById(R.id.toggle);
        notification.setChecked(def);
        return view;
    }
}